package com.bezdjian.mylms.apigateway.resource;

import com.bezdjian.mylms.apigateway.services.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/items", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Items")
public class ItemResource {

  private final ItemService itemService;

  @Autowired
  public ItemResource(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping("/brands")
  public ResponseEntity<Object> topItems() {
    try {
      return new ResponseEntity<>(itemService.topBrands(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/products")
  public ResponseEntity<Object> topProducts() {
    try {
      return new ResponseEntity<>(itemService.topProducts(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
