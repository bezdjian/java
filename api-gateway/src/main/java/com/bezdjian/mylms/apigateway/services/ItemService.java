package com.bezdjian.mylms.apigateway.services;

import com.bezdjian.mylms.apigateway.client.ItemServiceClient;
import com.bezdjian.mylms.apigateway.model.Item;
import com.bezdjian.mylms.apigateway.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ItemService {

  private final ItemServiceClient itemClient;
  @Value("${course.service.url}")
  private String courseServiceUrl;
  @Value("${course.service.port}")
  private String courseServicePort;

  @Autowired
  public ItemService(ItemServiceClient itemClient) {
    this.itemClient = itemClient;
  }

  public Collection<Item> topBrands() {
    return itemClient.readItems().getContent();
    //.stream()
    //.filter(this::isGreat)
    //.collect(Collectors.toList());
  }

  public Collection<Product> topProducts() {
    return itemClient.readProducts().getContent();
  }

  public Item save(){
    return Item.builder().build();
  }
}
