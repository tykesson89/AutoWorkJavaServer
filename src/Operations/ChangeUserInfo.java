package Operations;

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
        String password = "hejhej89";
        Statement st = null;
        System.out.println("tråden startar");
        try {
            user = (User) ois.readObject();
            try {
                String firstname = user.getFirstname();
                String lastname = user.getLastname();
                String email = user.getEmail();
                String oldPassword = user.getOldPassword();
                String newPassword = user.getNewPassword();
                int userId = user.getUserid();
                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();
              ResultSet rs =  st.executeQuery("SELECT password FROM users WHERE userid ="+userId);
                rs.first();
                String pass = rs.getString("Password");
                System.out.println(pass);
                System.out.println(oldPassword);
                if(!pass.equals(oldPassword)){
                    oos.writeObject("Password is incorrect");

                }else {
                    st.executeUpdate("update users set firstname = '" + firstname + "', lastname = '" + lastname + "', email = '" + email + "' where userid = " + userId + ";");
                    oos.writeObject("Success");
                }
                user = new User(firstname, lastname, email, userId);

            } catch (SQLException ex) {
                oos.writeObject("Something went wrong");

                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            System.out.println(e.getStackTrace());
        }
    }


}











