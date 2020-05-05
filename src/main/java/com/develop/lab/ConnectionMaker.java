package com.develop.lab;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMaker {
    private static final String url="jdbc:mysql://lab_mysqlnew_1:3306/example_schema";
    private static final String username="root";
    private static final String password="1234";
    private Connection connection ;

    public ConnectionMaker(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(url,username,password);
        }
        catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }

}
