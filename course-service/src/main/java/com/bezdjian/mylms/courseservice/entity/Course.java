package com.bezdjian.mylms.courseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "course_name")
  private String coursename;

  @Column
  private String description;

  @Column(name = "id_number")
  private String idnumber;

  @Column
  private String image;

  @Column
  private Long price;

  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private CourseCategory category;

  public Course(String coursename, String description, String idnumber, CourseCategory category) {
    this.coursename = coursename;
    this.description = description;
    this.idnumber = idnumber;
    this.category = category;
  }

  @Override
  public String toString() {
    return "{" +
      "id=" + id +
      ", coursename='" + coursename + '\'' +
      ", description='" + description + '\'' +
      ", idnumber='" + idnumber + '\'' +
      ", image='" + image + '\'' +
      ", price=" + price +
      ", category=" + category.getName() +
      '}';
  }
}
