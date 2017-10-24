package com.sreeshmallya.banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Transaction {
    private static String tableName = "transaction";

    public static void addRecord(String type, int accNo, int amount, String mode) {
        try {
            Connection con = Connector.getConnection();

            PreparedStatement stmt = con.prepareStatement("insert into " + tableName + "(type, id, account_no, amount, mode) values(?, ?, ?, ?, ?)");

            // Generate 8-digit transaction ID
            int id = HashCodeGenerator.generateHash(9);
            stmt.setString(1, type);
            stmt.setInt(2, id);
            stmt.setInt(3, accNo);
            stmt.setInt(4, amount);
            stmt.setString(5, mode);
            int i = stmt.executeUpdate();
            System.out.println("\nTransaction done.");
        } catch (Exception e) {
            System.out.println("\nTransaction failed!\n");
            e.printStackTrace();
        }
    }

    public static void all() {
        try {
            Connection con = Connector.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from " + tableName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                System.out.println(rs.getString(1) + "\t\t" + rs.getInt(2) + "\t\t" + rs.getInt(3) + "\t\t" + rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6));
        } catch (Exception e) {
            System.out.println("\nTransaction failed!\n");
            e.printStackTrace();
        }
    }
}
