package Operations;

import UserPackage.Company;
import UserPackage.User;
import UserPackage.WorkpassModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Henrik on 2016-04-04.
 */
public class GetNewPassword extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private String response;
    public GetNewPassword(){

    }

    public GetNewPassword(Socket socket, ObjectInputStream ois, ObjectOutputStream oos)
            throws IOException {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        start();
    }

    @Override
    public void run() {
        System.out.println("tråden startar");
        String url = "jdbc:mysql://localhost:3306/autowork";
        String username = "root";
        String password = "hejhej89";
        Statement st = null;
        try {
            String email = (String)ois.readObject();
            System.out.println("tar emot user");
            try {

                Random rand = new Random();
                int value = rand.nextInt(899999)+100000;
                String subject = "New Password for autowork";
                String text = "Your new password is "+ value + ". Please change it.";
                SendMail sendMail = new SendMail(email, subject, text);
                String str = String.valueOf(value);
                System.out.println(str);
                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();
                st.executeUpdate("update users set password = '" + str + "' where email = '" + email + "';");
                response = "success";
                oos.writeObject(response);

            } catch (SQLException ex) {
                response = "fail";
                oos.writeObject(response);

                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getStackTrace().toString());
            try {
                oos.writeObject(response);
            }catch (Exception f){

            }
        }
    }

public void getMessage(String str){
    this.response = str;
}

    }




