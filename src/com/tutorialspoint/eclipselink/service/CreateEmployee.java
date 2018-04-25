package com.tutorialspoint.eclipselink.service;

import java.util.Scanner;

import javax.persistence.*;

import com.tutorialspoint.eclipselink.entity.Employee;

public class CreateEmployee {
	
	private static EntityManagerFactory emfactory;
	private static EntityManager eManager;

	
	public static void main(String args[]) {
		System.out.println("Create Employee main started");
		
		//System.out.println("Update Employee");
		updateEmployee();
		//System.out.println("Create Employee");
		//createEmployee();
	}
	
	static void createEmployee() {		
		transactionBegin();
		Employee emp = new Employee(5,"Luisz Figoz", 400000, "Captainz");
		
		eManager.persist(emp);
		eManager.getTransaction().commit();
		
		transactionClose();
	}
	
	static void updateEmployee() {
		transactionBegin();
		//User input
		Scanner empid = new Scanner(System.in);
		Scanner salaryInput = new Scanner(System.in);
		
		System.out.println("Input Employee ID:");
		int empID = empid.nextInt();
		Employee emp = eManager.find(Employee.class, empID); // find with ID = 1
		
		//Before update 
		System.out.println("-------------- " + emp);
		
		System.out.println("Input new Salary");
		double salary = salaryInput.nextDouble();
		emp.setSalary(salary);
		eManager.getTransaction().commit();
		
		//After update
		System.out.println("-------------- " + emp);
		transactionClose();
	}
	
	public static void transactionBegin() {
		// the name of the persistence-unit name="tutorialspoint_JPA_Eclipselink" in persistence.xml
		emfactory = Persistence.createEntityManagerFactory("tutorialspoint_JPA_Eclipselink");
		
		eManager = emfactory.createEntityManager();
		eManager.getTransaction().begin();
	}
	
	public static void transactionClose() {
		eManager.close();
		emfactory.close();
	}
}
