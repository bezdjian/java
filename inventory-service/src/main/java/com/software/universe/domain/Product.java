package com.software.universe.domain;

import com.software.universe.serdeser.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@Document
public class Product {

    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String name;
    private Long price;
    private Integer stock;

}
