package com.example.itemcatalogservice.component;

import com.example.itemcatalogservice.entity.Item;
import com.example.itemcatalogservice.entity.Product;
import com.example.itemcatalogservice.repository.ItemRepository;
import com.example.itemcatalogservice.repository.ProductRepository;
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
    public void run(String... args) throws Exception {
        // Create some Strings and save as Item.
        Stream.of("Lining", "PUMA", "Bad boy", "Air Jordan", "Nike", "Adidas")
                .forEach(item -> itemRepository.save(new Item(item)));

        // Create some String and save as Product
        Stream.of("Laptop", "PC", "Mac", "Tablets", "Smart phones", "Smart watches")
                .forEach(product -> productRepository.save(new Product(product)));

        //Find all Items
        log.info("***** Displaying items:");
        itemRepository.findAll().forEach(System.out::println);
        log.info("***** EO: Displaying items:");

        // Find all Products
        log.info("***** Displaying products");
        productRepository.findAll().forEach(System.out::println);
        log.info("***** EO: Displaying products");
    }
}
