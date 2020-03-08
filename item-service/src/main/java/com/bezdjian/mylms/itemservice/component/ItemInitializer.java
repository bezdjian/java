package com.bezdjian.mylms.itemservice.component;

import com.bezdjian.mylms.itemservice.entity.Item;
import com.bezdjian.mylms.itemservice.entity.Product;
import com.bezdjian.mylms.itemservice.repository.ItemRepository;
import com.bezdjian.mylms.itemservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Slf4j
public class ItemInitializer implements CommandLineRunner {

  private final ItemRepository itemRepository;
  private final ProductRepository productRepository;

  public ItemInitializer(ItemRepository itemRepository, ProductRepository productRepository) {
    this.itemRepository = itemRepository;
    this.productRepository = productRepository;
  }

  @Override
  public void run(String... args) {
    // Create some Strings and save as Item.
    Stream.of("Lining", "PUMA", "Bad boy", "Air Jordan", "Nike", "Adidas")
      .forEach(item -> itemRepository.save(new Item(item)));

    // Create some String and save as Product
    Stream.of("Laptop", "PC", "Mac", "Tablets", "Smart phones", "Smart watches")
      .forEach(product -> productRepository.save(new Product(product)));

    //Find all Items
    log.info("***** Displaying items:");
    itemRepository.findAll().forEach(
      item -> log.info("Item: {}", item.getName())
    );
    log.info("***** EO: Displaying items:");

    // Find all Products
    log.info("***** Displaying products");
    productRepository.findAll().forEach(
      product -> log.info("Product: {}", product.getName())
    );
    log.info("***** EO: Displaying products");
  }
}
