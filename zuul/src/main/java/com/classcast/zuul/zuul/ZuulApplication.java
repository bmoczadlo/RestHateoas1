package com.classcast.zuul.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy

//@EnableWebMvc
//@LoadBalancerClient()
public class ZuulApplication {

    // http :8081/hello
    @Bean
    public RouterFunction<ServerResponse> helloRoute() {
        return RouterFunctions.route()
            .GET("/hello", request -> ServerResponse.ok().body("Hello World"))
            .build();
    }

    // http :8081/hi
    @Bean
    public RouterFunction<ServerResponse> hiRoute() {
        return RouterFunctions.route()
            .GET("/hi", this::hi).build();
    }

    public ServerResponse hi(ServerRequest request) {
        return ServerResponse.ok().body("Hi World");
    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
