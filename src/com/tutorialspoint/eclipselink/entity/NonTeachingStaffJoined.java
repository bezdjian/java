package com.tutorialspoint.eclipselink.entity;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="sid")

public class NonTeachingStaffJoined extends StaffJoined {
   private String areaexpertise;

   public NonTeachingStaffJoined( int sid, String sname, String areaexpertise ) {
      super( sid, sname );
      this.areaexpertise = areaexpertise;
   }

   public NonTeachingStaffJoined( ) {
      super( );
   }

   public String getAreaexpertise( ) {
      return areaexpertise;
   }

   public void setAreaexpertise( String areaexpertise ) {
      this.areaexpertise = areaexpertise;
   }
}