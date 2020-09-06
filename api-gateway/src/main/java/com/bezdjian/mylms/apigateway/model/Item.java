package com.bezdjian.mylms.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private String name;
    private double price;
    private String description;
    private String imageName;
    private String imageUrl;
}
