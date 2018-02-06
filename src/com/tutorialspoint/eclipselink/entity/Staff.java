package com.tutorialspoint.eclipselink.entity;

import javax.persistence.*;

@Entity
@Table
@Inheritance( strategy = InheritanceType.SINGLE_TABLE) //Single table, you can create sub classes who extends from this class and inserts into DB
@DiscriminatorColumn(name = "type") // This one differs from sub classes
public class Staff{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sid;
   private String sname;
   
   public Staff( int sid, String sname ) {
      super( );
      this.sid = sid;
      this.sname = sname;
   }
   
   public Staff( ) {
      super( );
   }
   
   public int getSid( ) {
      return sid;
   }
   
   public void setSid( int sid ) {
      this.sid = sid;
   }
   
   public String getSname( ) {
      return sname;
   }
   
   public void setSname( String sname ) {
      this.sname = sname;
   }
}
