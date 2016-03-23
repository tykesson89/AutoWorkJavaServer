package Operations;

import ObjectsPackage.Company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

/**
 * Created by Henrik on 2016-03-22.
 */
public class CreateCompany extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private Company company;

    public CreateCompany(Socket socket, ObjectOutputStream oos, ObjectInputStream ois)
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
            company = (Company)ois.readObject();
            try {
                double houelywage = company.getHourlyWage();
                String companyName = company.getCompanyName();
                int userId = company.getUserId();
                conn = DriverManager.getConnection(url, username, password);

                tt = conn.createStatement();
                tt.executeUpdate("INSERT INTO company(userid, companyname, hourlywage) VALUES('" + userId + "','" + companyName + "','" + houelywage + "' );");
                int companyId = -1;
                ResultSet co = null;
                co = tt.executeQuery("SELECT LAST_INSERT_ID()");
                if (co.next()) {
                    companyId = co.getInt(1);
                    company = new Company(companyName, houelywage, userId, companyId);
                }
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
