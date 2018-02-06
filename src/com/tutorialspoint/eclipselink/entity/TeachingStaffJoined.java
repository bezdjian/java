package com.tutorialspoint.eclipselink.entity;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="sid")
public class TeachingStaffJoined extends StaffJoined{
	 private String qualification;
	   private String subjectexpertise;

	   public TeachingStaffJoined( int sid, String sname, 
	   String qualification,String subjectexpertise ) {
	      super( sid, sname );
	      this.qualification = qualification;
	      this.subjectexpertise = subjectexpertise;
	   }

	   public TeachingStaffJoined( ) {
	      super( );
	   }

	   public String getQualification( ){
	      return qualification;
	   }

	   public void setQualification( String qualification ){
	      this.qualification = qualification;
	   }

	   public String getSubjectexpertise( ) {
	      return subjectexpertise;
	   }

	   public void setSubjectexpertise( String subjectexpertise ){
	      this.subjectexpertise = subjectexpertise;
	   }

}
