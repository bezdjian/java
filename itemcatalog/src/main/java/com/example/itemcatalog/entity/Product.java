package com.example.itemcatalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Product(String name){this.name = name;}
}
