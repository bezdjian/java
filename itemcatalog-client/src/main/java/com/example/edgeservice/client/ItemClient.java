package com.example.edgeservice.client;

import com.example.edgeservice.dto.ItemDTO;
import com.example.edgeservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("item-catalog-service")
public interface ItemClient {

    @GetMapping("/items") // /items is pre-defined endpoint in itemCatalog service
    Resources<ItemDTO> readItems();

    @GetMapping("/products") // /products is pre-defined endpoint in itemCatalog service
    Resources<ProductDTO> readProducts();
}