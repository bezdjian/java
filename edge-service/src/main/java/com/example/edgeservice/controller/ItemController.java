package com.example.edgeservice.controller;

import com.example.edgeservice.client.ItemClient;
import com.example.edgeservice.dto.ItemDTO;
import com.example.edgeservice.dto.ProductDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class ItemController {

    private final ItemClient itemClient;

    public ItemController(ItemClient itemClient) {
        this.itemClient = itemClient;
    }

    @GetMapping("/top-brands")
    @HystrixCommand(fallbackMethod = "fallbackItemDTO")
    public Collection<ItemDTO> topItems(){
        return itemClient.readItems()
                .getContent();
                //.stream()
                //.filter(this::isGreat)
                //.collect(Collectors.toList());
    }

    @GetMapping("/top-products")
    @HystrixCommand(fallbackMethod = "fallbackProductDTO")
    public Collection<ProductDTO> topProducts(){
        return itemClient.readProducts()
                .getContent();
    }

    public Collection<ItemDTO> fallbackItemDTO(){
        return new ArrayList<>();
    }
    public Collection<ProductDTO> fallbackProductDTO(){
        return new ArrayList<>();
    }
}
