package com.flipkart.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (isValid()) {
                return connection;
            } else {
                try {
                    Properties prop = new Properties();
//                    commented out the below code because, maven is not able to find config file

//                    InputStream inputStream = JDBCConnection.class.getClassLoader().getResourceAsStream("./config.properties");
//                    prop.load(inputStream);
//                    String driver = prop.getProperty("driver");
//                    String url = prop.getProperty("url");
//                    String user = prop.getProperty("user");
//                    String password = prop.getProperty("password");
                    String driver = "com.mysql.cj.jdbc.Driver";
                    String url = "jdbc:mysql://127.0.0.1:3306/flipfit_Schema_G";
                    String user = "root";
                    String password = "" ;
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    System.out.println("Connected to database");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();


            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed");
        }
        return connection;
    }
    public static boolean isValid() throws SQLException {
        return connection != null && !connection.isClosed();
    }
}
