package Server;

import UserPackage.Company;
import UserPackage.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

/**
 * Created by Henrik on 2016-03-22.
 */
public class ChangeUserInfo extends Thread {

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private User user;


    public ChangeUserInfo(Socket socket, ObjectOutputStream oos, ObjectInputStream ois)
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
            try {
                String firstname = user.getFirstname();
                String lastname = user.getLastname();
                String email = user.getEmail();
                String userPassword = user.getPassword();
                int userId = user.getUserid();

                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();

                st.executeUpdate("update users set firstname = '" + firstname + "', lastname = '" + lastname + "', email = '" + email + "', password = '" + password + "' where userid = " + userId + ";");


                user = new User(firstname, lastname, email, null, userId);

                oos.writeObject(user);


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











