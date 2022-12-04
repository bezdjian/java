package com.software.universe.reactive.service;

import com.software.universe.async.producer.OrderProducer;
import com.software.universe.constants.OrderStatus;
import com.software.universe.domain.Order;
import com.software.universe.reactive.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderProducer orderProducer;

    public Mono<Order> createOrder(Order order) {
        log.info("Create order invoked with: {}", order);
        return Mono.just(order)
            .map(o -> o.setLineItems(o.getLineItems()
                .stream()
                .filter(l -> l.getQuantity() > 0)
                .collect(Collectors.toList())))
            .flatMap(orderRepository::save)
            .map(this::sendInitiationSuccessMessage)
            .onErrorResume(err -> Mono.just(order.setOrderStatus(OrderStatus.FAILURE)
                .setResponseMessage(err.getMessage())))
            .flatMap(orderRepository::save);
    }

    public Flux<Order> getOrders() {
        log.info("Get all orders invoked.");
        return orderRepository.findAll();
    }

    private Order sendInitiationSuccessMessage(Order o) {
        orderProducer.sendMessage(o.setOrderStatus(OrderStatus.INITIATION_SUCCESS));
        return o;
    }
}
