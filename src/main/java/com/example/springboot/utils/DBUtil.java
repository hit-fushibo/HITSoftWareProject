package com.example.springboot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DBUtil {

    @Value("${spring.datasource.url}")
    private String dbPath;
    private Connection connection;
    private Statement statement;
    public void getConnection()
    {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            this.statement = this.connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeUpdate(String sql) {
        try {
            if(this.statement==null){
                System.out.println("db closed");
            }
            this.statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String sql) {
        try {
            ResultSet rs = this.statement.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void close() {
        try {
            if (this.statement != null) {
                this.statement.close();
                this.statement=null;
            }
            if (this.connection != null) {
                this.connection.close();
                this.connection=null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
