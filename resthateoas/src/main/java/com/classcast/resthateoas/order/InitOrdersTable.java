package com.classcast.resthateoas.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InitOrdersTable {

    private static final Logger log = LoggerFactory.getLogger(InitOrdersTable.class);

    @Bean
    CommandLineRunner initOrders(OrderRepository orderRepository) {

        return __ -> {
            orderRepository.save(new Order("MacBook Pro", OrderStatus.COMPLETED));
            orderRepository.save(new Order("iPhone", OrderStatus.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> log.info("Preloaded {}", order));
        };

    }
}