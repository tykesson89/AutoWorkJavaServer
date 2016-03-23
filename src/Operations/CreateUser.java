package Operations;

import ObjectsPackage.Company;
import ObjectsPackage.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;


/**
 * Created by Henrik on 2016-03-16.
 */
public class CreateUser extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private User user;
    private Company company;

    public CreateUser(Socket socket, ObjectOutputStream oos, ObjectInputStream ois)
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
            user = (User) ois.readObject();
            company = (Company)ois.readObject();
            try {
                String firstname = user.getFirstname();
                String lastname = user.getLastname();
                String email = user.getEmail();
                String oldPassword = user.getOldPassword();
                double houelywage = company.getHourlyWage();
                String companyName = company.getCompanyName();

                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();

                st.executeUpdate("INSERT INTO users(firstname, lastname, email, password) VALUES('" + firstname + "','" + lastname + "','" + email + "','" + oldPassword + "' );");
                int userId = -1;
                ResultSet rs = null;
                rs = st.executeQuery("SELECT LAST_INSERT_ID()");

                if (rs.next()) {
                    userId = rs.getInt(1);
                }
                tt = conn.createStatement();
                tt.executeUpdate("INSERT INTO company(userid, companyname, hourlywage) VALUES('" + userId + "','" + companyName + "','" + houelywage + "' );");
                int companyId = -1;
                ResultSet co = null;
                co = tt.executeQuery("SELECT LAST_INSERT_ID()");
                if (co.next()) {
                    companyId = co.getInt(1);
                    user = new User(firstname, lastname, email, null, userId);
                }
                oos.writeObject(user);
                System.out.println(userId);
                System.out.println(companyId);
                System.out.println(user.toString());


            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate")) {
                    oos.writeObject("User Already Exists");
                }
                else{
                    oos.writeObject("Something went wrong");
                }
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

}