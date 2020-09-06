package com.bezdjian.mylms.apigateway.client;

import com.bezdjian.mylms.apigateway.model.Item;
import com.bezdjian.mylms.apigateway.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("item-service")
public interface ItemServiceClient {

  @GetMapping("/item-service/items")
    // /items is pre-defined endpoint in item service
  Resources<Item> readItems();

  @GetMapping("/item-service/products")
    // /products is pre-defined endpoint in item service
  Resources<Product> readProducts();
}