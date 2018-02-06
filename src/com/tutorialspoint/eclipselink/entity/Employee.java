package com.tutorialspoint.eclipselink.entity;

import javax.persistence.*;

@Entity
@Table
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	private double salary;
	private String deg;
	
	public Employee(int id, String name, double salary, String deg) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.deg = deg;
	}
	
	public Employee( ) {
	      super();
	   }

	   public int getId( ) {
	      return id;
	   }
	   
	   public void setId(int id) {
	      this.id = id;
	   }
	   
	   public String getName( ) {
	      return name;
	   }
	   
	   public void setName(String name) {
	      this.name = name;
	   }

	   public double getSalary( ) {
	      return salary;
	   }
	   
	   public void setSalary(double salary) {
	      this.salary = salary;
	   }

	   public String getDeg( ) {
	      return deg;
	   }
	   
	   public void setDeg(String deg) {
	      this.deg = deg;
	   }
	   
	   @Override
	   public String toString() {
	      return "Employee [Id=" + id + ", Name=" + name + ", salary=" + salary + ", deg=" + deg + "]";
	   }
}
