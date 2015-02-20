/*
package com.codeit.priorityrouting.DBSupport;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by FZDDFL on 2/17/2015.

public class c3PoService{

    public static void main(String[] args) throws SQLException {

        Connection connection = null;

        ComboPooledDataSource cpds = new ComboPooledDataSource();

        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306");
        cpds.setUser("root");
        cpds.setPassword("root");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);


        connection = cpds.getConnection();

        try {
            connection = cpds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from localtest.employee");

            while (resultSet.next()) {
                String hashValue = resultSet.getString("address");
                System.out.println("address = " + hashValue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
            connection.close();


    }
}
*/
