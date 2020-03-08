package com.bezdjian.mylms.apigateway.services;

import com.bezdjian.mylms.apigateway.client.ItemServiceClient;
import com.bezdjian.mylms.apigateway.dto.ItemDTO;
import com.bezdjian.mylms.apigateway.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class ItemService {

  private final ItemServiceClient itemClient;

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
}
