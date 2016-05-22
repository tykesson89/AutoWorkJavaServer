package Operations;

import Server.Server;
import UserPackage.User;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

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
	private String url = "jdbc:mysql://localhost:3306/autowork";
	private String username = "root";
	String password = Server.DATABASE_PASSWORD;
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
        Statement st = null;
        System.out.println("tråden startar");
        try {
            user = (User) ois.readObject();
            try {
                String firstname = user.getFirstname();
                String lastname = user.getLastname();
                String email = user.getEmail();
                String oldPassword = user.getOldPassword();
                int userId = user.getUserid();
                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();
              ResultSet rs =  st.executeQuery("SELECT password FROM users WHERE userid ="+userId);
                rs.first();
                String pass = rs.getString("password");
                System.out.println(pass);
                System.out.println(oldPassword);
                if(!checkPassword(email, oldPassword)){
                    oos.writeObject("Password is incorrect");

                }else {
                    st.executeUpdate("update users set firstname = '" + firstname + "', lastname = '" + lastname + "', email = '" + email + "' where userid = " + userId + ";");
                    oos.writeObject("Success");
                }


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

	public boolean checkPassword(String email, String oldPassword){
		try {
			EncryptPassword encryptPassword = new EncryptPassword();
			Statement st = null;
			conn = DriverManager.getConnection(url, username, password);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT password FROM users WHERE email = '" + email + "';");
			rs.first();
			try {
				String testPass = encryptPassword.decryptPassword(rs.getString("password"));

				if(testPass.equals(oldPassword)){
					return true;
				}else{
					return false;
				}
			}catch(EncryptionOperationNotPossibleException e){
				return false;
			}

		}catch(SQLException w){
			return false;
		}

	}

}











