package com.sreeshmallya.banking;

import java.sql.Connection;
import java.sql.DriverManager;

class Connector {
    private static Connection con;

    static void connect(String databaseName, String user, String pwd) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + databaseName, user, pwd
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void disconnect() {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Connection getConnection() {
        return con;
    }
}
