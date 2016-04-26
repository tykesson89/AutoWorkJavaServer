package Operations;

import UserPackage.Workpass;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.JSONFunctions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Henrik on 2016-04-21.
 */
public class CreateWorkpass extends Thread {

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private Workpass workpass;



    public CreateWorkpass(Socket socket, ObjectOutputStream oos, ObjectInputStream ois)
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
            date = fmt.parse(str);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        TimeZone timeZone = TimeZone.getTimeZone("GMT+1");
        GregorianCalendar cal = new GregorianCalendar(timeZone);
        cal.setTime(date);

        return cal;
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
            workpass = (Workpass)ois.readObject();
            try {
                long workpassid = workpass.getWorkpassID();
                int serverId;
                int userId = workpass.getUserId();
                String title = workpass.getTitle();
                long companyId = workpass.getCompanyID();
                int companyServerid = workpass.getCompanyServerID();
                TimeZone timeZone = TimeZone.getTimeZone("GMT+1");
                workpass.getEndDateTime().setTimeZone(timeZone);
                workpass.getStartDateTime().setTimeZone(timeZone);
                String starttime = formatCalendarToString(workpass.getStartDateTime());
                String endtime = formatCalendarToString(workpass.getEndDateTime());
                double breaktime = workpass.getBreaktime();
                double salary = workpass.getSalary();
                String note = workpass.getNote();
                double workinghours = workpass.getWorkingHours();
                conn = DriverManager.getConnection(url, username, password);
                st = conn.createStatement();
                if(companyServerid == -1){
                    ResultSet rs = st.executeQuery("Select companyid FROM company where userid = '"+userId+"' and localcompanyid = '"+ companyId+"';");
                    rs.first();
                    companyServerid = rs.getInt("companyid");
                }
                String query = "INSERT INTO workpass(title, salary, breaktime, notes, hours, companyid, userid, starttime, endtime, localworkpassid, localcompanyId) VALUES( '"+
                        title+"','"+salary+"','"+breaktime+"','"+note+"','"+workinghours+"','"+companyServerid+"','"+userId+"','"+starttime+"','"+endtime+"','"+workpassid+"','"+companyId+"');";

                st.executeUpdate(query);
                serverId  = -1;
                ResultSet rs = null;
                rs = st.executeQuery("SELECT LAST_INSERT_ID()");
                if (rs.next()) {
                    serverId = rs.getInt(1);
                }

                oos.writeObject(String.valueOf(serverId));
                System.out.println("Skickad!");







            } catch (SQLException ex) {
                oos.writeObject("Something went wrong");

                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
