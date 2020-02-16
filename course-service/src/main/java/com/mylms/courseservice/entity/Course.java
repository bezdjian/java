package com.mylms.courseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Course {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "course_name")
  private String coursename;

  @Column(name = "description")
  private String description;

  @Column(name = "id_number")
  private String idnumber;

  @Column(name = "image")
  private String image;

  @Column(name = "price")
  private Long price;


  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private CourseCategory courseCategory;

  public Course(String coursename, CourseCategory category) {
    this.coursename = coursename;
    this.courseCategory = category;
  }
}
