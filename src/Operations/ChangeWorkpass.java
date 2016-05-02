package Operations;

import UserPackage.Company;
import UserPackage.Workpass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Henrik on 2016-04-21.
 */
public class ChangeWorkpass extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private Workpass workpass;


    public ChangeWorkpass(Socket socket, ObjectOutputStream oos, ObjectInputStream ois, Workpass workpass)
            throws IOException {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        this.workpass = workpass;
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

                try {
                    long workpassid = workpass.getWorkpassID();
                    int serverId = workpass.getServerID();
                    int userId = workpass.getUserId();
                    String title = workpass.getTitle();
                    long companyId = workpass.getCompanyID();
                    int companyServerid = workpass.getCompanyServerID();
                    String starttime = formatCalendarToString(workpass.getStartDateTime());
                    String endtime = formatCalendarToString(workpass.getEndDateTime());
                    double breaktime = workpass.getBreaktime();
                    double salary = workpass.getSalary();
                    String note = workpass.getNote();
                    double workinghours = workpass.getWorkingHours();
                    conn = DriverManager.getConnection(url, username, password);
                    st = conn.createStatement();
                    if (companyServerid == -1) {
                        ResultSet rs = st.executeQuery("Select companyid FROM company where userid = '" + userId + "' and localcomapnyid = '" + companyId + "';");
                        rs.first();
                        companyServerid = rs.getInt("companyid");
                    }

                    st.executeUpdate("update workpass set title = '" + title + "', salary = '" + salary + "', breaktime = '" + breaktime + "', notes = '" + note + "', hours = '" +
                            workinghours + "', companyid = '" + companyServerid + "', userid = '" + userId + "', starttime = '" + starttime + "', endtime = '" + endtime + "', localworkpassid ='" +
                            workpassid + "', localcompanyId = '" + companyId + "' where workpassid = '" + serverId + "';");


                    oos.writeObject(String.valueOf(serverId));

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
