package com.sreeshmallya.banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Account {
    private static String tableName = "account";

    static void add(String type, int customer_id, int balance, String mode) {
        try {
            if (balance < 1000) {
                System.out.println("You need more than a 1000 bucks to open an account.");
                return;
            }

            Connection con = Connector.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into " + tableName + "(type, customer_id, account_no, balance) values(?, ?, ?, ?)"
            );

            // Generate account number
            int accNo = HashCodeGenerator.generateHash(8);

            stmt.setString(1, type);
            stmt.setInt(2, customer_id);
            stmt.setInt(3, accNo);
            stmt.setInt(4, balance);

            int i = stmt.executeUpdate();

            Transaction.addRecord("CREDIT", accNo, balance, mode);

            System.out.println("\nAdded " + i + " row(s).\nAccount was successfully opened. " +
                    "(account no. : " + accNo + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void all() {
        try {
            Connection con = Connector.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from " + tableName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                System.out.println(rs.getString(1) + "\t\t" +
                        rs.getInt(2) + "\t\t" +
                        rs.getInt(3) + "\t\t" +
                        rs.getInt(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transfer(int fromAccNo, int toAccNo, int amount) {

    }

    static void debit(int accNo, int amount, String mode) {
        String type = "DEBIT";

        try {
            Connection con = Connector.getConnection();

            // Check for sufficient funds
            PreparedStatement stmt = con.prepareStatement(
                    "select balance from " + tableName + " where account_no=" + accNo
            );
            ResultSet rs = stmt.executeQuery();
            int balance = rs.getInt(1);
            if (balance < 1000 || balance - amount < 1000) {
                System.out.println("\nTransaction failed!" +
                        " You don't have sufficient funds in your account to make this transaction.\n");
                return;
            }

            // Debit amount from account
            stmt = con.prepareStatement(
                    "update " + tableName + " set balance=" + (balance - amount) + " where account_no=" + accNo
            );
            int i = stmt.executeUpdate();
            System.out.println("\nUpdated " + i + " row(s) in " + tableName + "\n");

            // Add a record in transactions table
            Transaction.addRecord(type, accNo, amount, mode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void credit(int accNo, int amount, String mode) {
        String type = "CREDIT";

        // If amount < 1000, deny transaction
        if (amount < 1000) {
            System.out.println("\nTransaction failed! You cannot credit funds less than 1000.\n");
            return;
        }

        try {
            Connection con = Connector.getConnection();

            // Add amount to account
            PreparedStatement stmt = con.prepareStatement(
                    "update " + tableName + " set balance=balance+" + amount + " where account_no=" + accNo
            );
            int i = stmt.executeUpdate();

            // Add a record in transactions table
            Transaction.addRecord(type, accNo, amount, mode);
            System.out.println("\nAccount ( A/C no. : " + accNo + ") has been credited with " + amount + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
