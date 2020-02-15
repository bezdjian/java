package com.example.apigateway.client;

import com.example.apigateway.dto.ItemDTO;
import com.example.apigateway.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("item-service")
public interface ItemServiceClient {

  @GetMapping("/item-service/items")
    // /items is pre-defined endpoint in item service
  Resources<ItemDTO> readItems();

  @GetMapping("/item-service/products")
    // /products is pre-defined endpoint in item service
  Resources<ProductDTO> readProducts();
}