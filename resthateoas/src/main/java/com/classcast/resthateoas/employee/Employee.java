package com.classcast.resthateoas.employee;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
class Employee {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NonNull
    private String firstName;
    @NotNull
    @NonNull
    private String lastName;
    @NotNull
    @NonNull
    private String role;

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public void setName(String name) {
        String[] parts = name.split(" ");
        this.firstName = parts[0];
        this.lastName = parts[1];
    }
}

