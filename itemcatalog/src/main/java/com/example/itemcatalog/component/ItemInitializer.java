package com.example.itemcatalog.component;

import com.example.itemcatalog.entity.Item;
import com.example.itemcatalog.entity.Product;
import com.example.itemcatalog.repository.ItemRepository;
import com.example.itemcatalog.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
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
        System.out.println("***** Displaying items:");
        itemRepository.findAll().forEach(System.out::println);
        System.out.println("***** EO: Displaying items:");

        // Find all Products
        System.out.println("***** Displaying products");
        productRepository.findAll().forEach(System.out::println);
        System.out.println("***** EO: Displaying products");
    }
}
