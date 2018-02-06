package com.tutorialspoint.eclipselink.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.tutorialspoint.eclipselink.entity.NonTeachingStaffJoined;
import com.tutorialspoint.eclipselink.entity.TeachingStaffJoined;

public class SaveClientJoined {

	public static void main(String args[]) {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("tutorialspoint_JPA_Eclipselink");
		EntityManager eManager = emFactory.createEntityManager();
		eManager.getTransaction().begin();
		
		//Teaching staff entity
		TeachingStaffJoined tStaff = new TeachingStaffJoined(10, "Gopal", "Msc Med", "Maths");
		TeachingStaffJoined tstaff1 = new TeachingStaffJoined(11, "Manisha", "BSc MEd", "English");
		
		//Non-teaching staff entity
		NonTeachingStaffJoined ntStaff = new NonTeachingStaffJoined(12,"Satish", "Accounts");
		NonTeachingStaffJoined ntStaff1 = new NonTeachingStaffJoined(13, "Krishna", "Office Admin");
		
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
