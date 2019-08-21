package com.example.edgeservice.client;

import com.example.edgeservice.dto.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("item-catalog-service")
public interface ItemClient {

    @GetMapping("/items")
    Resources<ItemDTO> readItems();
}
