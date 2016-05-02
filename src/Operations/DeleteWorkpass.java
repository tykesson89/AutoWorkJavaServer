package Operations;

import UserPackage.Workpass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

/**
 * Created by Henrik on 2016-04-21.
 */
public class DeleteWorkpass extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private Workpass workpass;

    public DeleteWorkpass(Socket socket, ObjectOutputStream oos, ObjectInputStream ois, Workpass workpass)
        throws IOException {
            this.socket = socket;
            this.ois = ois;
            this.oos = oos;
            this.workpass = workpass;
            start();

    }

    @Override
    public void run() {
        System.out.println("tråden startar");
        String url = "jdbc:mysql://localhost:3306/autowork";
        String username = "root";
        String password = "g17sk44D";
        Statement st = null;
        Statement tt = null;
        System.out.println("tråden startar");

        try {

            try {
                int workpassid = workpass.getServerID();
                long id = workpass.getWorkpassID();
                System.out.println(workpassid);
                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();


                st.executeUpdate("DELETE FROM workpass where workpassid = '" + workpassid + "';");
                System.out.println(workpassid);
                oos.writeObject(String.valueOf(id));

            } catch (SQLException ex) {
                oos.writeObject("Something went wrong");

                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }



        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.fillInStackTrace());
            System.out.println(e.getCause());
        }




    }
}
