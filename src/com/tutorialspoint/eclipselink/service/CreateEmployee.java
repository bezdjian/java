package com.tutorialspoint.eclipselink.service;

import java.util.Scanner;

import javax.persistence.*;

import com.tutorialspoint.eclipselink.entity.Employee;

public class CreateEmployee {

	
	public static void main(String args[]) {
		System.out.println("Create Employee main started");
		
		//System.out.println("Update Employee");
		//updateEmployee();
		
		System.out.println("Create Employee");
		createEmployee();
		
	}
	
	static void createEmployee() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("tutorialspoint_JPA_Eclipselink");
		
		EntityManager eManager  = emfactory.createEntityManager();
		eManager.getTransaction().begin();
		
		Employee emp = new Employee(4,"Luis Figo", 40000, "Captain");
		
		eManager.persist(emp);
		eManager.getTransaction().commit();
		
		eManager.close();
		emfactory.close();
	}
	
	static void updateEmployee() {
		
		//User input
		Scanner input = new Scanner(System.in);
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("tutorialspoint_JPA_Eclipselink");
		
		EntityManager eManager = emFactory.createEntityManager();
		eManager.getTransaction().begin();
		
		Employee emp = eManager.find(Employee.class, 1); // find with ID = 1
		
		//Before update 
		System.out.println("-------------- " + emp);
		
		System.out.println("Input new Salary");
		double salary = input.nextDouble();
		emp.setSalary(salary);
		eManager.getTransaction().commit();
		
		//After update
		System.out.println("-------------- " + emp);
		
		eManager.close();
		emFactory.close();
	}
}
