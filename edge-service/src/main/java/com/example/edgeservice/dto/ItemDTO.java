package com.example.edgeservice.dto;

import lombok.Data;

@Data
public class ItemDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
