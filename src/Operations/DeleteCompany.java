package Operations;

import UserPackage.Company;

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
public class DeleteCompany extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private Company company;


    public DeleteCompany(Socket socket, ObjectOutputStream oos, ObjectInputStream ois, Company company)
            throws IOException {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        this.company = company;
        start();


    }

    public void run() {
        System.out.println("tråden startar");
        String url = "jdbc:mysql://localhost:3306/autowork";
        String username = "root";
        String password = "hejhej89";
        Statement st = null;
        Statement tt = null;
        System.out.println("tråden startar");
        try {

            try {
                int companyId = company.getServerID();

                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();

               st.executeQuery("DELETE FROM company where companyid = '" + companyId + "';");




                oos.writeObject(1);


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
