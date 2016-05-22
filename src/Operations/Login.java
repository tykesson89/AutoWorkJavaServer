package Operations;




import Server.Server;
import UserPackage.Company;
import UserPackage.User;
import UserPackage.Workpass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import netscape.javascript.JSException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import sun.plugin.javascript.JSObject;
import java.util.Date;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by Henrik on 2016-03-21.
 */
public class Login extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private User user;
    private String url = "jdbc:mysql://localhost:3306/autowork";
    private String username = "root";
	String password = Server.DATABASE_PASSWORD;
    private Workpass workpass;
    List workpasses = new ArrayList<>();
    ArrayList<Workpass> workpasslist;

    public Login(Socket socket, ObjectInputStream ois, ObjectOutputStream oos)
            throws IOException {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        start();
    }
    private String formatCalendarToString(GregorianCalendar cal) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy MM dd HH:mm");

        String dateFormatted = fmt.format(cal.getTime());

        return dateFormatted;
    }

    private GregorianCalendar formatStringToCalendar(String str) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy MM dd HH:mm");

        Date date = null;
        try {
            date =  fmt.parse(str);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        return cal;
    }
    public void run() {
        System.out.println("tråden startar");
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
                if(checkEmail(email)==false){
                    oos.writeObject("Wrong Email");
                }else if(checkPassword(email, oldPassword)==false){
                    oos.writeObject("Wrong password");
                }else {
                    ResultSet rs = st.executeQuery("SELECT userid, firstname, lastname, email FROM users WHERE email = '" + email + "';");
                    System.out.println("query skapad");
                    rs.first();
                    int userID = rs.getInt(1);
                    String firstname = rs.getString(2);
                    String lastname = rs.getString(3);
                    email = rs.getString(4);
                    User user = new User(firstname, lastname, email, userID);
                    oos.writeObject(user);
                    ArrayList<Company> list = new ArrayList<Company>();
                    rs = st.executeQuery("SELECT companyid, companyname, hourlywage FROM company WHERE userid = " + userID + ";");
                    while (rs.next()) {
                        int serverid = rs.getInt("companyid");
                        String companyname = rs.getString("companyname");
                        double hourlyWage = rs.getDouble("hourlywage");
                        Company company = new Company();
                        company.setCompanyName(companyname);
                        company.setHourlyWage(hourlyWage);
                        company.setServerID(serverid);
                        company.setUserId(userID);
                        company.setIsSynced(1);
                        list.add(company);
                    }
                    oos.writeObject(list);
                rs = st.executeQuery("SELECT workpassid, title, companyid, starttime, endtime," +
                        " salary, breaktime, notes, localcompanyid, hours FROM workpass WHERE userid ="+userID+";");
                while(rs.next()) {
                    workpasslist = new ArrayList<>();
                    workpass = new Workpass();
                    workpass.setServerID(rs.getInt("workpassid"));
                    workpass.setTitle(rs.getString("title"));
                    workpass.setStartDateTime(formatStringToCalendar(rs.getString("starttime")));
                    workpass.setNote(rs.getString("notes"));
                    workpass.setEndDateTime(formatStringToCalendar(rs.getString("endtime")));
                    workpass.setBreaktime(rs.getDouble("breaktime"));
                    int localcompanyid = rs.getInt("localcompanyid");
                    long companyid = Long.parseLong(String.valueOf(localcompanyid));
                    workpass.setCompanyID(companyid);
                    workpass.setCompanyServerID(rs.getInt("companyid"));
                    workpass.setWorkingHours(rs.getDouble("hours"));
                    workpass.setSalary(rs.getDouble("salary"));
                    workpass.setUserId(userID);

                    Gson gson = new GsonBuilder().create();

                    workpasses.add(gson.toJson(workpass));

                }

                oos.writeObject(workpasses);
                }
            } catch (SQLException ex) {
                oos.writeObject("Something went wrong");

                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getStackTrace().toString());
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
        }
    }
    public boolean checkEmail(String email){
        try {
            Statement st = null;
            conn = DriverManager.getConnection(url, username, password);
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT email FROM users WHERE email = '" + email + "';");
            rs.first();
            String testMail = rs.getString("email");

        }catch (SQLException e){
            return false;
        }
        return true;
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
