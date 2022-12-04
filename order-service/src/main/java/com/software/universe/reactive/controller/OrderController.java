package com.software.universe.reactive.controller;

import com.software.universe.constants.OrderStatus;
import com.software.universe.domain.Order;
import com.software.universe.reactive.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    public Mono<Order> create(@RequestBody Order order) {
        log.info("Create order invoked with: {}", order);
        return orderService.createOrder(order).flatMap(o -> {
            if (OrderStatus.FAILURE.equals(o.getOrderStatus())) {
                return Mono.error(new RuntimeException("Order processing failed, please try again later. "
                    + o.getResponseMessage()));
            } else {
                return Mono.just(o);
            }
        });
    }

    @GetMapping
    public Flux<Order> getAll() {
        log.info("Get all orders invoked.");
        return orderService.getOrders();
    }

}