package com.classcast.resthateoas.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CUSTOMER_ORDER")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
class Order {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NonNull
    private String description;
    @NotNull
    @NonNull
    private OrderStatus status;
}