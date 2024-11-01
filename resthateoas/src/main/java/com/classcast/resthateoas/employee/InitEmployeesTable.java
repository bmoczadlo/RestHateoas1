package com.classcast.resthateoas.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InitEmployeesTable {

    private static final Logger log = LoggerFactory.getLogger(InitEmployeesTable.class);

    @Bean
    CommandLineRunner initEmployees(EmployeeRepository employeeRepository) {

        return __ -> {
            log.info("Preloading {}", employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar")));
            log.info("Preloading {}", employeeRepository.save(new Employee("Frodo", "Baggins", "thief")));

            employeeRepository.findAll().forEach(employee -> log.info("Preloaded {}", employee));
        };

    }
}