package com.software.universe.reactive.service;

import java.time.LocalDate;
import java.time.LocalTime;

import com.software.universe.constants.OrderStatus;
import com.software.universe.reactive.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.universe.domain.Order;
import com.software.universe.domain.Shipment;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ShippingService {

    @Autowired
    ShipmentRepository shipmentRepository;

    public Mono<Order> handleOrder(Order order) {
        log.info("Handle order invoked with: {}", order);
        return Mono.just(order)
            .flatMap(o -> {
                LocalDate shippingDate = null;
                if (LocalTime.now()
                    .isAfter(LocalTime.parse("10:00"))
                    && LocalTime.now()
                        .isBefore(LocalTime.parse("18:00"))) {
                    shippingDate = LocalDate.now()
                        .plusDays(1);
                } else {
                    return Mono.error(new RuntimeException("The current time is off the limits to place order."));
                }
                return shipmentRepository.save(new Shipment().setAddress(order.getShippingAddress())
                    .setShippingDate(shippingDate));
            })
            .map(s -> order.setShippingDate(s.getShippingDate())
                .setOrderStatus(OrderStatus.SUCCESS));
    }

}