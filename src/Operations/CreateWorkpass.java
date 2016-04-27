package Operations;

import UserPackage.Workpass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Henrik on 2016-04-21.
 */
public class CreateWorkpass extends Thread {

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private Workpass workpass1;



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

        GregorianCalendar cal = new GregorianCalendar();
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
            System.out.println(1);
           workpass1 = (Workpass) ois.readObject();


            System.out.println(1);
            try {
               // for(Workpass workpass1  : workpasses ) {
                    workpass1 = new Workpass();
                    long workpassid = workpass1.getWorkpassID();
                    int serverId;
                    int userId = workpass1.getUserId();
                    System.out.println(2);
                    String title = workpass1.getTitle();
                    long companyId = workpass1.getCompanyID();
                    int companyServerid = workpass1.getCompanyServerID();
                    System.out.println(3);
                    String starttime = formatCalendarToString(workpass1.getStartDateTime());
                    String endtime = formatCalendarToString(workpass1.getEndDateTime());
                    double breaktime = workpass1.getBreaktime();
                    double salary = workpass1.getSalary();
                    System.out.println(4);
                    String note = workpass1.getNote();
                    double workinghours = workpass1.getWorkingHours();
                    conn = DriverManager.getConnection(url, username, password);
                    st = conn.createStatement();
                    System.out.println(5);
                    System.out.println(workpass1.toString());
//                    if (companyServerid == -1) {
//                        ResultSet rs = st.executeQuery("Select companyid FROM company where userid = " + userId + " and localcompanyid = '" + companyId + "';");
//                        rs.first();
//                        companyServerid = rs.getInt("companyid");
//
//                    }

                    System.out.println(6);
                    String query = "INSERT INTO workpass(title, salary, breaktime, notes, hours, companyid, userid, starttime, endtime, localworkpassid, localcompanyid) VALUES( '" +
                            title + "', " + salary + ", " + breaktime + ", '" + note + "', " + workinghours + ", " + companyServerid + ", " + userId + ", '" + starttime + "', '" + endtime + "', " + workpassid + ", " + companyId + ");";

                    st.executeUpdate(query);
                    serverId = -1;
                    System.out.println(1);
                    ResultSet rs = null;
                    rs = st.executeQuery("SELECT LAST_INSERT_ID()");
                    if (rs.next()) {
                        serverId = rs.getInt(1);
                    }

                    workpass1.setServerID(serverId);
                    workpass1.setActionTag(null);
                    workpass1.setIsSynced(1);
              //  }
                System.out.println(7);
                oos.writeObject(workpass1);







            } catch (SQLException ex) {
               // oos.writeObject("Something went wrong");

                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getSuppressed());
            System.out.println(e.getCause());
            System.out.println(e.fillInStackTrace());
        }
    }
}
