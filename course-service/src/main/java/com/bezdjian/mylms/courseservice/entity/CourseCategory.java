package com.bezdjian.mylms.courseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
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

  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "category")
  private List<Course> course;

  public CourseCategory(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
