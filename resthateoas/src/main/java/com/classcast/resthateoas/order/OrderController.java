package com.classcast.resthateoas.order;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderModelAssembler assembler;

    @GetMapping
    CollectionModel<EntityModel<Order>> all() {

        return assembler.toCollectionModel(orderRepository.findAll());
    }

    @GetMapping("/{id}")
    EntityModel<Order> one(@PathVariable Long id) {

        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));
        return assembler.toModel(order);
    }

    @PostMapping
    ResponseEntity<?> newOrder(@RequestBody Order newOrder) {

        newOrder.setStatus(OrderStatus.IN_PROGRESS);
        EntityModel<Order> orderModel = assembler.toModel(orderRepository.save(newOrder));

        return ResponseEntity
            .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
            .body(orderModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateOrder(@RequestBody Order newOrder, @PathVariable Long id) {

        Order updatedOrder = orderRepository.findById(id)
            .map(order -> {
                order.setDescription(newOrder.getDescription());
                order.setStatus(newOrder.getStatus());
                return orderRepository.save(order);
            })
            .orElseGet(() -> orderRepository.save(newOrder));

        EntityModel<Order> orderModel = assembler.toModel(updatedOrder);

        return ResponseEntity.created(orderModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(orderModel);
    }

    @DeleteMapping("/{id}/cancel")
    ResponseEntity<?> cancelOrder(@PathVariable Long id) {

        Order canceledOrder = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));

        if (canceledOrder.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            canceledOrder.setStatus(OrderStatus.CANCELLED);

            return ResponseEntity
                .ok(assembler.toModel(orderRepository.save(canceledOrder)));
        }

        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
            .body(Problem.create()
                .withTitle("Method not allowed")
                .withDetail("You can't cancel an order that is in the " + canceledOrder.getStatus() + " status"));
    }

    @PutMapping("/{id}/complete")
    ResponseEntity<?> completeOrder(@PathVariable Long id) {

        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            order.setStatus(OrderStatus.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
        }

        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
            .body(Problem.create()
                .withTitle("Method not allowed") //
                .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
    }

}
