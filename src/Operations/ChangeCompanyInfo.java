package Operations;

import ObjectsPackage.Company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Henrik on 2016-03-22.
 */
public class ChangeCompanyInfo extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private Company company;


    public ChangeCompanyInfo(Socket socket, ObjectOutputStream oos, ObjectInputStream ois)
            throws IOException {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        start();


    }

    public void run() {
        System.out.println("tråden startar");
        String url = "jdbc:mysql://localhost:3306/autowork";
        String username = "root";
        String password = "hejhej";
        Statement st = null;
        Statement tt = null;
        System.out.println("tråden startar");
        try {
            company = (Company) ois.readObject();
            try {
                String companyName = company.getCompanyName();
                double hourlyWage = company.getHourlyWage();
                int companyId = company.getCompanyId();

                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();

                st.executeUpdate("update company set companyname = '" + companyName + "', hourlywage = '" + hourlyWage + "' where companyid = " + companyId + ";");




                oos.writeObject(company);


            } catch (SQLException ex) {
                oos.writeObject("Something went wrong");

                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }


}
