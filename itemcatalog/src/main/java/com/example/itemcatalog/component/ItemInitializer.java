package com.example.itemcatalog.component;

import com.example.itemcatalog.entity.Item;
import com.example.itemcatalog.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ItemInitializer implements CommandLineRunner {

    private final ItemRepository itemRepository;

    public ItemInitializer(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create some Strings and save as Item.
        Stream.of("Lining", "PUMA", "Bad boy", "Air Jordan", "Nike", "Adidas")
                .forEach(item -> itemRepository.save(new Item(item)));

        //Find all Items
        System.out.println("***** Displaying items:");
        itemRepository.findAll().forEach(System.out::println);
        System.out.println("***** EO: Displaying items:");
    }
}
