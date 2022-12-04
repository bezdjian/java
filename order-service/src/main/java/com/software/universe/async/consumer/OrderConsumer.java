package com.software.universe.async.consumer;

import com.software.universe.async.producer.OrderProducer;
import com.software.universe.constants.OrderStatus;
import com.software.universe.domain.Order;
import com.software.universe.reactive.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderConsumer {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProducer orderProducer;

    @KafkaListener(topics = "orders", groupId = "orders")
    public void consume(Order order) {
        log.info("Order received to process: {}", order);
        if (OrderStatus.INITIATION_SUCCESS.equals(order.getOrderStatus())) {
            orderRepository.findById(order.getId())
                .map(o -> {
                    orderProducer.sendMessage(o.setOrderStatus(OrderStatus.RESERVE_INVENTORY));
                    return o.setOrderStatus(order.getOrderStatus())
                        .setResponseMessage(order.getResponseMessage());
                })
                .flatMap(orderRepository::save)
                .subscribe();
        } else if (OrderStatus.INVENTORY_SUCCESS.equals(order.getOrderStatus())) {
            orderRepository.findById(order.getId())
                .map(o -> {
                    orderProducer.sendMessage(o.setOrderStatus(OrderStatus.PREPARE_SHIPPING));
                    return o.setOrderStatus(order.getOrderStatus())
                        .setResponseMessage(order.getResponseMessage());
                })
                .flatMap(orderRepository::save)
                .subscribe();
        } else if (OrderStatus.SHIPPING_FAILURE.equals(order.getOrderStatus())) {
            orderRepository.findById(order.getId())
                .map(o -> {
                    orderProducer.sendMessage(o.setOrderStatus(OrderStatus.REVERT_INVENTORY));
                    return o.setOrderStatus(order.getOrderStatus())
                        .setResponseMessage(order.getResponseMessage());
                })
                .flatMap(orderRepository::save)
                .subscribe();
        } else {
            orderRepository.findById(order.getId())
                .map(o -> o.setOrderStatus(order.getOrderStatus())
                    .setResponseMessage(order.getResponseMessage()))
                .flatMap(orderRepository::save)
                .subscribe();
        }
    }
}