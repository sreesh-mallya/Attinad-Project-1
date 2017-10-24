package com.sreeshmallya.banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private static String tableName = "customer";

    static void add(String name, String fathersName, String email, String PAN, String type, int amount, String mode) {
        // TODO Validation
        if (amount < 1000) {
            System.out.println("You need more than a 1000 bucks to open an account.");
            return;
        }


        try {
            Connection con = Connector.getConnection();
            PreparedStatement stmt = con.prepareStatement("insert into " + tableName + "(name, fathers_name, email, PAN, id) values(?, ?, ?, ?, ?)");
            int id = HashCodeGenerator.generateHash(6);
            stmt.setString(1, name);
            stmt.setString(2, fathersName);
            stmt.setString(3, email);
            stmt.setString(4, PAN);
            stmt.setInt(5, id);
            int i = stmt.executeUpdate();
            System.out.println("\nYour Customer ID is " + id);
            Account.add(type, id, amount, mode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void all() {
        try {
            Connection con = Connector.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from " + tableName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                System.out.println(rs.getString(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t" + rs.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
