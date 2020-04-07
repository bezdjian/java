package com.bezdjian.mylms.apigateway.services;

import com.bezdjian.mylms.apigateway.client.ItemServiceClient;
import com.bezdjian.mylms.apigateway.dto.ItemDTO;
import com.bezdjian.mylms.apigateway.dto.ProductDTO;
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

  public Collection<ItemDTO> topBrands() {
    return itemClient.readItems().getContent();
    //.stream()
    //.filter(this::isGreat)
    //.collect(Collectors.toList());
  }

  public Collection<ProductDTO> topProducts() {
    return itemClient.readProducts().getContent();
  }

  public ItemDTO save(){
    return ItemDTO.builder().build();
  }
}
