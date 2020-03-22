package com.bezdjian.mylms.itemservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Item {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String name;
  @Column
  private double price;
  @Column
  private String description;
  @Column
  private String imageName;
  @Column
  private String imageUrl;

  public Item(String name) {
    this.name = name;
  }
}
