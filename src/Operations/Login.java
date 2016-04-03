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

/**
 * Created by Henrik on 2016-03-21.
 */
public class Login extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private User user;

    public Login(Socket socket, ObjectInputStream ois, ObjectOutputStream oos)
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
        Statement tt = null;
        System.out.println("tråden startar");
        try {
            user = (User) ois.readObject();
            System.out.println("tar emot user");
            try {
                String oldPassword = user.getOldPassword();
                String email = user.getEmail();
                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT userid, firstname, lastname, email FROM users WHERE email = '" + email + "' AND password = '" + oldPassword +"';");
               System.out.println("query skapad");
                rs.first();
                int userID = rs.getInt(1);
                String firstname = rs.getString(2);
                String lastname = rs.getString(3);
                email = rs.getString(4);
                User user = new User(firstname, lastname, email, userID);
                oos.writeObject(user);
                ArrayList<Company>list = new ArrayList<Company>();
                ArrayList<WorkpassModel>workpassList = new ArrayList<WorkpassModel>();
                rs = st.executeQuery("SELECT companyid, companyname, hourlywage FROM company WHERE userid = "+userID+";");
                while(rs.next()){
                    int id = rs.getInt("companyid");
                    String companyname = rs.getString("companyname");
                    double hourlyWage = rs.getDouble("hourlywage");
                    Company company = new Company(companyname, hourlyWage, userID, id);
                    list.add(company);
                }
                oos.writeObject(list);
                rs = st.executeQuery("SELECT workpassid, title, companyname, hourlywage, starttime, endtime, salary, breaktime, notes FROM workpass WHERE userid ="+userID+";");
                while(rs.next()){
                    long companyid = rs.getLong("workpassid");
                    String title = rs.getString("title");
                    String companyname = rs.getString("companyname");
                    double hourlyWage = rs.getDouble("hourlywage");
                    Timestamp starttime = rs.getTimestamp("starttime");
                    Timestamp endtime = rs.getTimestamp("endtime");
                    double salary = rs.getDouble("salary");
                    int breaktime = rs.getInt("breaktime");
                    String notes = rs.getString("notes");
                    Company company = new Company(companyname, hourlyWage);
                    WorkpassModel workpassModel = new WorkpassModel(companyid, userID, title, salary, company, starttime,
                            endtime, breaktime, notes);
                    workpassList.add(workpassModel);
                }
                oos.writeObject(workpassList);

            } catch (SQLException ex) {
                oos.writeObject(ex.getMessage());

                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

}
