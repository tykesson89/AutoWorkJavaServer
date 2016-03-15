package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;


/**
 * Created by Henrik on 2016-03-15.
 */
public class Server extends Thread {

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
                System.out.println("Waiting for client to connect!");
                Socket socket = serversocket.accept();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                    String url = "jdbc:mysql://localhost:3306/autowork";
                    String user = "root";
                    String password = "lösenord";
                    Statement st = null;
                    try {

                        conn = DriverManager.getConnection(url, user,password);
                        st = conn.createStatement();
                        st.executeUpdate("INSERT INTO tablename(row1,row) VALUES('value1', 'value2');");


                    } catch (SQLException ex) {

                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());
                    }
               // }

            } catch (Exception e) {

            }
        }



    public static void main(String[] args) {
        Server server = new Server(40001);
    }


}


