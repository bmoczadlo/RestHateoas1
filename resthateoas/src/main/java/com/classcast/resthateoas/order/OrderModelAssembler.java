package com.classcast.resthateoas.order;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public @NonNull EntityModel<Order> toModel(@NonNull Order order) {

        EntityModel<Order> orderModel = EntityModel.of(order,
            linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
            linkTo(methodOn(OrderController.class).all()).withRel("orders"));

        if (order.getStatus() == OrderStatus.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).completeOrder(order.getId())).withRel("complete"));
        }

        return orderModel;
    }

    @Override
    public @NonNull CollectionModel<EntityModel<Order>> toCollectionModel(@NonNull Iterable<? extends Order> orders) {

        return StreamSupport.stream(orders.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
            .add(linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }
}
