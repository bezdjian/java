package com.mycompany.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        display();
    }

    public static String display(){
        String display = "";
        try{
            //1. Load driver class
            //Class.forName("oracle.jdbc.driver.OracleDriver");
        	Class.forName("com.mysql.jdbc.Driver");

            //2. Create the connection
            //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "javadeveloper", "javadeveloper");
        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hibernate5", "root", "root");

            //3. Create the statement object.
            Statement statement = connection.createStatement();

            //4. Execute query.
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLOYEE");

            while(resultSet.next()){
                display += "EMP ID: " + resultSet.getInt("EMP_ID");
                display += "\t\tEMP_NAME: " + resultSet.getString("EMP_NAME");
                display += "\t\tEMP_NO: " + resultSet.getString("EMP_NO");
                display += "\t\tMANAGER: " + resultSet.getString("MNG_ID");
                display += "\n";
            }
            
            System.out.println(display);
            
        }catch(Exception e){
            System.out.println("- Exception ------ ");
            e.printStackTrace();
        }

        return display;
    }
}
