package com.sreeshmallya.banking;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /* TODO : User input validation */

        // Database credentials
        String databaseName = "bank";
        String user = "root";
        String pwd = "root";

        String name, fathersName, email, PAN, accountType, mode;
        int _accountType, amount, _mode, accNo = 0, opt;
        Connector.connect(databaseName, user, pwd); // Connect to database
        Scanner scn = new Scanner(System.in);

        System.out.print("\n\n -- Banking Portal -- \n" +
                "Are you a :\n1. New Customer or \n2. An Existing Customer ?\n>>> "
        );

        opt = scn.nextInt();

        if (opt == 1) {

            System.out.print("\nPlease enter your details : ");
            System.out.print("\n\nName : ");
            name = scn.next();
            System.out.print("Father's Name : ");
            fathersName = scn.next();
            System.out.print("e-mail : ");
            email = scn.next();
            System.out.print("PAN : ");
            PAN = scn.next();
            System.out.print("\nOpen 1. SB account 2. RD account ?\n>>> ");
            _accountType = scn.nextInt();
            if (_accountType == 1) accountType = "SB"; else accountType = "RD";
            System.out.print("\nDeposit amount (>=1000): ");
            amount = scn.nextInt();
            System.out.print("\nMode : 1. Cheque or 2. Cash\n>>> ");
            _mode = scn.nextInt();
            if (_mode == 1) mode = "CHQ"; else mode = "CSH";
            accNo = Customer.add(name, fathersName, email, PAN, accountType, amount, mode);
            System.out.println("Your A/C no. is : " + accNo);
        } else {
            System.out.print("\nEnter your A/C no. : ");
            accNo = scn.nextInt();
            if (!Account.exists(accNo)) {
                System.out.println("Account does not exist. Please try again.\n");
                System.exit(1);
            }
        }

        while (true) {
            try {
                System.out.print("\nWhat do you wanna do?\n1. Deposit\n2. Withdraw\n3. Transfer Funds\n4. Log off\n>>> ");
                opt = scn.nextInt();
                if (opt == 1) {
                    System.out.print("\nDeposit Amount -- Amount to deposit : ");
                    amount = scn.nextInt();
                    System.out.print("\nMode : 1. Cheque or 2. Cash\n>>> ");
                    _mode = scn.nextInt();
                    if (_mode == 1) mode = "CHQ";
                    else mode = "CSH";
                    Account.credit(accNo, amount, mode);
                } else if (opt == 2) {
                    System.out.print("\nWithdraw Amount -- Amount to Withdraw : ");
                    amount = scn.nextInt();
                    System.out.print("\nMode : 1. Cheque or 2. Cash\n>>> ");
                    _mode = scn.nextInt();
                    if (_mode == 1) mode = "CHQ";
                    else mode = "CSH";
                    Account.debit(accNo, amount, mode);
                } else if (opt == 4) {
                    System.out.println("\nLogging off...");
                    Connector.disconnect();
                    scn.close();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
