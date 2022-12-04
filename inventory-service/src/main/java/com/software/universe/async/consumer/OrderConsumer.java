package com.software.universe.async.consumer;

import java.io.IOException;

import com.software.universe.constants.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.software.universe.async.producer.OrderProducer;
import com.software.universe.domain.Order;
import com.software.universe.reactive.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderConsumer {

    @Autowired
    ProductService productService;

    @Autowired
    OrderProducer orderProducer;

    @KafkaListener(topics = "orders", groupId = "inventory")
    public void consume(Order order) throws IOException {
        log.info("Order received to process: {}", order);
        if (OrderStatus.RESERVE_INVENTORY.equals(order.getOrderStatus())) {
            productService.handleOrder(order)
                .doOnSuccess(o -> {
                    log.info("Order processed succesfully.");
                    orderProducer.sendMessage(order.setOrderStatus(OrderStatus.INVENTORY_SUCCESS));
                })
                .doOnError(e -> {
                    if (log.isDebugEnabled())
                        log.error("Order failed to process: " + e);
                    orderProducer.sendMessage(order.setOrderStatus(OrderStatus.INVENTORY_FAILURE)
                        .setResponseMessage(e.getMessage()));
                })
                .subscribe();
        } else if (OrderStatus.REVERT_INVENTORY.equals(order.getOrderStatus())) {
            productService.revertOrder(order)
                .doOnSuccess(o -> {
                    log.info("Order reverted succesfully.");
                    orderProducer.sendMessage(order.setOrderStatus(OrderStatus.INVENTORY_REVERT_SUCCESS));
                })
                .doOnError(e -> {
                    if (log.isDebugEnabled())
                        log.error("Order failed to revert: " + e);
                    orderProducer.sendMessage(order.setOrderStatus(OrderStatus.INVENTORY_REVERT_FAILURE)
                        .setResponseMessage(e.getMessage()));
                })
                .subscribe();
        }
    }
}