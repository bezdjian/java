package com.tutorialspoint.eclipselink.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tutorialspoint.eclipselink.entity.Employee;

public class ScalarandAggregateFunctions {

	public static void main(String args[]) {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("tutorialspoint_JPA_Eclipselink");
		EntityManager eManager = emFactory.createEntityManager();
		
		//Scalar function
		Query query = eManager.createQuery("SELECT e FROM Employee e");
		List<Employee> list = query.getResultList();
		
		//Loop names
		for(Employee e:list) {
			System.out.println( e );
		}
		
		//Aggregate function
		Query query1 = eManager.createQuery("SELECT MAX(e.salary) FROM Employee e");
		Double result = (Double) query1.getSingleResult();
		
		System.out.println("Max employee salary is " + result);
		
	}
}
