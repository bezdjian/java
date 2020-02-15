package com.mylms.courseservice.entity;

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

  public Course(String coursename){
    this.coursename = coursename;
  }
}
