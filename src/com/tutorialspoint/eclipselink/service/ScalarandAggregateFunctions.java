package com.tutorialspoint.eclipselink.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tutorialspoint.eclipselink.entity.Employee;
import com.tutorialspoint.eclipselink.entity.StaffJoined;

public class ScalarandAggregateFunctions {

	public static void main(String args[]) {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("tutorialspoint_JPA_Eclipselink");
		EntityManager eManager = emFactory.createEntityManager();
		
		/*
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
		*/
		
		//Query query = eManager.createNamedQuery("myquery");
		Query query = eManager.createNativeQuery("SELECT sf.*, tf.qualification FROM STAFFJOINED sf left join TEACHINGSTAFFJOINED tf on tf.sid = sf.sid left join NONTEACHINGSTAFFJOINED ntf on ntf.sid = sf.sid");
		List<Object[]> list = query.getResultList();
		
		//Loop names
		for(Object[] e : list) {
			System.out.println( e[0] + " - " + e[1] + " - " + e[2] + " - " + e[3]);
		}
		
	}
}
