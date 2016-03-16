package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;


/**
 * Created by Henrik on 2016-03-15.
 */
public class Server extends Thread {
    private HashMap<String, String> map;
    private Connection conn;
    private ServerSocket serversocket;
    private Thread thread = new Thread(this);

    public Server(int port) {
        try {
            serversocket = new ServerSocket(port);
            thread.start();
        } catch (IOException o) {
        }
    }

    public void run() {
        System.out.println("Server startad");
            try {
                while(true){
                String tag;
                System.out.println("Waiting for client to connect!");
                Socket socket = serversocket.accept();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    new HashMap<String, String>();
                    String url = "jdbc:mysql://localhost:3306/autowork";
                    String user = "root";
                    String password = "hejhej";
                    Statement st = null;

                    try {
                        tag = ois.readObject().toString();
                        if(tag.equals("Create User")) {
                            map = (HashMap) ois.readObject();
                            String firstname = map.get("Firstname");
                            String lastname = map.get("Lastname");
                            String email = map.get("Email");
                            String userPassword = map.get("Password");
                            String salary = map.get("HourlyWage");
                            String companyName = map.get("CompanyName");
                            conn = DriverManager.getConnection(url, user, password);
                            st = conn.createStatement();
                            st.executeUpdate("INSERT INTO Users(Firstname, Lastname, Email, Password, Salary, Workplace) VALUES('"+firstname+"','"+ lastname+"','"+ email+"','"+ userPassword+"','"+ salary+"','"+ companyName+"' );");

                        }
                    } catch (SQLException ex) {

                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());
                    }
               }

            } catch (Exception e) {

            }
        }



    public static void main(String[] args) {
        Server server = new Server(40001);
    }


}


