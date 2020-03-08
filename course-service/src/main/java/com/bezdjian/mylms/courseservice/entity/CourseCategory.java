package com.bezdjian.mylms.courseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CourseCategory {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String name;
  @Column
  private String category_description;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Course> course;

  public CourseCategory(String name, String category_description) {
    this.name = name;
    this.category_description = category_description;
  }
}
