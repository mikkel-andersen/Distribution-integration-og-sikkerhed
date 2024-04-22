import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class Bank {

    public static void main(String[] args) {
    Connection minConnection;
        BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
        try {
            minConnection = DriverManager.getConnection
                    ("jdbc:sqlserver://localhost;databaseName=Bank;user=sa;password=Appelsin13Appelsin");

            System.out.println("Indtast frakonto nummer: ");
            String frakontoNr = inLine.readLine();

            System.out.println("Indtast tilkonto nummer: ");
            String tilKontoNr = inLine.readLine();

            System.out.println("Indtast beløb: ");
            double beløb = Double.parseDouble(inLine.readLine());

            Statement stmt = minConnection.createStatement();

            ResultSet res = stmt.executeQuery("select saldo from Konto where ktoNr = '" + frakontoNr + "'");
            res.next();
            double saldo1 = res.getDouble(1);
            ResultSet res2 = stmt.executeQuery("select saldo from Konto where ktoNr = '" + tilKontoNr + "'");
            res2.next();
            double saldo2 = res2.getDouble(1);

            if (saldo1 >= beløb) {
                double newSaldo1 = saldo1 - beløb;
                stmt.executeUpdate("Update konto " +
                                        "set saldo = " + newSaldo1 + " " +
                                        "WHERE ktoNr = '" + frakontoNr + "'");

                double newSaldo2 = saldo2 + beløb;
                stmt.executeUpdate(
                        "Update konto " +
                                "set saldo = " + newSaldo2 + " " +
                                "WHERE ktoNr = '" + tilKontoNr + "'");
            }

        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }



    }


}
