package Operations;

import UserPackage.Company;
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
    private Company company;
    private String url = "jdbc:mysql://localhost:3306/autowork";
   private  String username = "root";
    private String password = "hejhej89";
    public CreateUser(Socket socket, ObjectOutputStream oos, ObjectInputStream ois)
            throws IOException {
        this.socket = socket;
        EncryptPassword encryptPassword = new EncryptPassword();
        this.ois = ois;
        this.oos = oos;
        start();


    }


    public void run() {
        System.out.println("tråden startar");
        EncryptPassword encryptPassword = new EncryptPassword();
        Statement st = null;
        Statement tt = null;
        System.out.println("tråden startar");
        try {
            user = (User) ois.readObject();
            company = (Company)ois.readObject();
            try {
                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();
                String firstname = user.getFirstname();
                String lastname = user.getLastname();
                String email = user.getEmail();
                String oldPassword = user.getOldPassword();
              String encryptedPassword =  encryptPassword.encryptPassword(oldPassword);
                double houelywage = company.getHourlyWage();
                String companyName = company.getCompanyName();

                if(checkEmail(email)==false){
                    oos.writeObject("User Already Exists");
                }else if(checkEmail(email)==true) {
                    String subject = "Welcome to AutoWork " + firstname + "!";
                    String message = "Thank you for using AutoWork " + "\n Your password is " + oldPassword + ".";
                    SendMail sendMail = new SendMail();
                    String response = sendMail.SendMail(email, subject, message);

                    if (response.equals("No Email")) {
                        oos.writeObject("No Email");
                    } else {


                        st.executeUpdate("INSERT INTO users(firstname, lastname, email, password) VALUES('" + firstname + "','" + lastname + "','" + email + "','" + encryptedPassword + "' );");
                        int userId = -1;
                        ResultSet rs = null;
                        rs = st.executeQuery("SELECT LAST_INSERT_ID()");

                        if (rs.next()) {
                            userId = rs.getInt(1);
                        }
                        tt = conn.createStatement();
                        tt.executeUpdate("INSERT INTO company(userid, companyname, hourlywage) VALUES('" + userId + "','" + companyName + "','" + houelywage + "' );");
                        oos.writeObject("Success");
                    }
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
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
        }
    }
    public Boolean checkEmail(String email){
        try {
            Statement st = null;
            conn = DriverManager.getConnection(url, username, password);
            st = conn.createStatement();
            ResultSet h;
            h = st.executeQuery("SELECT email from users where email='"+email+"';");
            h.first();
            String testMail = h.getString("email");


        }catch (SQLException e){
            return true;
        }
        return false;
    }

}