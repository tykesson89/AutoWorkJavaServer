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
                System.out.println("Client Connected");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    new HashMap<String, String>();
                    String url = "jdbc:mysql://localhost:3306/autowork";
                    String user = "root";
                    String password = "hejhej";
                    Statement st = null;
                    Statement tt = null;

                    try {
                        tag = ois.readObject().toString();
                        System.out.println(tag);
                        if(tag.equals("Create User")) {
                            map = (HashMap) ois.readObject();
                            System.out.println("Hashmap mottagen");
                            String firstname = map.get("Firstname");
                            String lastname = map.get("Lastname");
                            String email = map.get("Email");
                            String userPassword = map.get("Password");
                            String salary = map.get("HourlyWage");
                            String companyName = map.get("CompanyName");
                            conn = DriverManager.getConnection(url, user, password);
                            st = conn.createStatement();
                            st.executeUpdate("INSERT INTO Users(Firstname, Lastname, Email, Password, Salary, Workplace) VALUES('"+firstname+"','"+ lastname+"','"+ email+"','"+ userPassword+"','"+ salary+"','"+ companyName+"' );");
                            int userId = -1;
                            ResultSet rs = null;
                            rs = st.executeQuery("SELECT LAST_INSERT_ID()");

                            if (rs.next()) {
                                userId = rs.getInt(1);
                            }
                            tt = conn.createStatement();
                                tt.executeUpdate("INSERT INTO Workplace(Userid, Workplace, Salary) VALUES('"+userId+"','"+ companyName+"','"+ salary+"' );");
                                int companyId = -1;
                                ResultSet co = null;
                                co = tt.executeQuery("SELECT LAST_INSERT_ID()");
                                if (co.next()) {
                                    companyId = co.getInt(1);
                                    int[] args = new int[2];
                                    args[0]=userId;
                                    args[1]=companyId;
                                    oos.writeObject(args);
                                    System.out.println(userId);
                                    System.out.println(companyId);
                                }





                        }
                    } catch (SQLException ex) {
                            if(ex.getMessage().contains("Duplicate")){
                                oos.writeObject("User Already Exists");
                            }if(ex.getMessage().contains("Data truncated for column 'Salary'")){
                                oos.writeObject("Only Int");
                        }
                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());
                    }
               }

            } catch (Exception e) {

            }
        }



    public static void main(String[] args) {
        Server server = new Server(45001);
    }


}


