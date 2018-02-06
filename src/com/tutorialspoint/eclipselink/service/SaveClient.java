package com.tutorialspoint.eclipselink.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.tutorialspoint.eclipselink.entity.NonTeachingStaff;
import com.tutorialspoint.eclipselink.entity.TeachingStaff;

public class SaveClient {

	public static void main(String args[]) {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("tutorialspoint_JPA_Eclipselink");
		EntityManager eManager = emFactory.createEntityManager();
		eManager.getTransaction().begin();
		
		//Teaching staff entity
		TeachingStaff tStaff = new TeachingStaff(1, "Gopal", "Msc Med", "Maths");
		TeachingStaff tstaff1 = new TeachingStaff(2, "Manisha", "BSc MEd", "English");
		
		//Non-teaching staff entity
		NonTeachingStaff ntStaff = new NonTeachingStaff(3,"Satish", "Accounts");
		NonTeachingStaff ntStaff1 = new NonTeachingStaff(4, "Krishna", "Office Admin");
		
		//Storing all entities
		eManager.persist(tStaff);
		eManager.persist(tstaff1);
		eManager.persist(ntStaff);
		eManager.persist(ntStaff1);
		
		eManager.getTransaction().commit();
		
		eManager.close();
		emFactory.close();
	}
}
