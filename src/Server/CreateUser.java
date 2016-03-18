package Server;

import UserPackage.User;

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

    public CreateUser(Socket socket, User user)
            throws IOException {
        this.socket = socket;
    this.user = user;


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
            System.out.println("tråden startar");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Tagit emot user");
            try {
                String firstname = user.getFirstname();
                String lastname = user.getLastname();
                String email = user.getEmail();
                String userPassword = user.getPassword();
                double salary = user.getHourlyWage();
                String companyName = user.getCompanyName();

                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();

                st.executeUpdate("INSERT INTO Users(Firstname, Lastname, Email, Password, Salary, Workplace) VALUES('" + firstname + "','" + lastname + "','" + email + "','" + userPassword + "','" + salary + "','" + companyName + "' );");
                int userId = -1;
                ResultSet rs = null;
                rs = st.executeQuery("SELECT LAST_INSERT_ID()");

                if (rs.next()) {
                    userId = rs.getInt(1);
                }
                tt = conn.createStatement();
                tt.executeUpdate("INSERT INTO Workplace(Userid, Workplace, Salary) VALUES('" + userId + "','" + companyName + "','" + salary + "' );");
                int companyId = -1;
                ResultSet co = null;
                co = tt.executeQuery("SELECT LAST_INSERT_ID()");
                if (co.next()) {
                    companyId = co.getInt(1);
                    user = new User(firstname, lastname, email, null, companyName, salary, userId, companyId);
                    oos.writeUTF("Hej");
                    oos.writeObject(user);
                    System.out.println(userId);
                    System.out.println(companyId);
                    System.out.println(user.toString());
                }


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