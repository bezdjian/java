package com.example.apigateway.controller;

import com.example.apigateway.client.CourseServiceClient;
import com.example.apigateway.client.ItemServiceClient;
import com.example.apigateway.dto.CourseDTO;
import com.example.apigateway.dto.ItemDTO;
import com.example.apigateway.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ItemController {

  private final ItemServiceClient itemClient;
  private final CourseServiceClient courseClient;

  @Autowired
  public ItemController(ItemServiceClient itemClient, CourseServiceClient courseClient) {
    this.itemClient = itemClient;
    this.courseClient = courseClient;
  }

  @GetMapping("/top-brands")
  //@HystrixCommand(fallbackMethod = "fallbackItemDTO")
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

  @GetMapping("/top-products")
  //@HystrixCommand(fallbackMethod = "fallbackProductDTO")
  public ResponseEntity<Object> topProducts() {
    try {
      Collection<ProductDTO> products = itemClient.readProducts().getContent();
      return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/top-courses")
  //@HystrixCommand(fallbackMethod = "fallbackProductDTO")
  public ResponseEntity<Object> topCourses() {
    try {
      Collection<CourseDTO> courses = courseClient.readCourses().getContent();
      return new ResponseEntity<>(courses, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /*public Collection<ItemDTO> fallbackItemDTO() {
    return new ArrayList<>();
  }

  public Collection<ProductDTO> fallbackProductDTO() {
    return new ArrayList<>();
  }*/
}
