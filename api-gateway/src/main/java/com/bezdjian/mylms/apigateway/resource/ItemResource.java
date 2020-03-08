package com.bezdjian.mylms.apigateway.resource;

import com.bezdjian.mylms.apigateway.client.ItemServiceClient;
import com.bezdjian.mylms.apigateway.dto.ItemDTO;
import com.bezdjian.mylms.apigateway.dto.ProductDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/items", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Items")
public class ItemResource {

  private final ItemServiceClient itemClient;

  @Autowired
  public ItemResource(ItemServiceClient itemClient) {
    this.itemClient = itemClient;
  }

  @GetMapping("/brands")
  public ResponseEntity<Object> topItems() {
    try {
      Collection<ItemDTO> items = itemClient.readItems()
        .getContent();
      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //.stream()
    //.filter(this::isGreat)
    //.collect(Collectors.toList());
  }

  @GetMapping("/products")
  public ResponseEntity<Object> topProducts() {
    try {
      Collection<ProductDTO> products = itemClient.readProducts().getContent();
      return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
