package com.tutorialspoint.eclipselink.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ScalarandAggregateFunctions {

	public static void main(String args[]) {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("tutorialspoint_JPA_Eclipselink");
		EntityManager eManager = emFactory.createEntityManager();
		
		//Scalar function
		Query query = eManager.createQuery("SELECT UPPER(e.name) FROM Employee e");
		List<String> list = query.getResultList();
		
		//Loop names
		for(String e:list) {
			System.out.println("Employee name: " + e);
		}
		
		//Aggregate function
		Query query1 = eManager.createQuery("SELECT MAX(e.salary) FROM Employee e");
		Double result = (Double) query1.getSingleResult();
		
		System.out.println("Max employee salary is " + result);
		
	}
}
