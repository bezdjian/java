package com.mylms.itemservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

  private String name;

  public Item(String name) {
    this.name = name;
  }
}
