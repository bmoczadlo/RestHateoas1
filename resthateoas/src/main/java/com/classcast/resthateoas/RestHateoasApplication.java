package com.classcast.resthateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestHateoasApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestHateoasApplication.class, args);
    }

}
