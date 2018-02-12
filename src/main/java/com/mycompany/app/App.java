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
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //2. Create the connection
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "javadeveloper", "javadeveloper");

            //3. Create the statement object.
            Statement statement = connection.createStatement();

            //4. Execute query.
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

            while(resultSet.next()){
                display += "ID: " + resultSet.getInt("ID");
                display += "FIRSTNAME: " + resultSet.getString("FIRSTNAME");
                display += "LASTNAME: " + resultSet.getString("LASTNAME");
                display += "EMAIL: " + resultSet.getString("EMAIL");
            }
        }catch(Exception e){
            System.out.println("- Exception ------ ");
            e.printStackTrace();
        }

        return display;
    }
}
