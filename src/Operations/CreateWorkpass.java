package Operations;

import UserPackage.Workpass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm");
        format.setTimeZone(TimeZone.getDefault());

        String dateFormatted = format.format(cal.getTime());

        return dateFormatted;
    }

    private GregorianCalendar formatStringToCalendar(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm");

        Date date = null;
        try {
            date = format.parse(str);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }


        GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
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
		Gson gson = new GsonBuilder().create();
        try {
            String looptimes = (String) ois.readObject();
            System.out.println(looptimes);

            for (int i = 0; i < Integer.parseInt(looptimes); i++) {
                //workpass = (Workpass) ois.readObject();
				String jObject = (String)ois.readObject();
				workpass = gson.fromJson(jObject, Workpass.class);
                System.out.println(workpass.getActionTag());
                if(workpass.getActionTag().equals("Change Workpass")){
                    new ChangeWorkpass(socket, oos, ois, workpass);
                }else if(workpass.getActionTag().equals("Delete Workpass")) {
                    new DeleteWorkpass(socket, oos, ois, workpass);
                }else{
                    try {
                        long workpassid = workpass.getWorkpassID();
                        int serverId;
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
                        if (companyServerid == 0) {
                            ResultSet rs = st.executeQuery("Select companyid FROM company where userid = '" + userId + "' and localcompanyid = '" + companyId + "';");
                            rs.first();
                            companyServerid = rs.getInt("companyid");
                        }
                        String query = "INSERT INTO workpass(title, salary, breaktime, notes, hours, companyid, userid, starttime, endtime, localworkpassid, localcompanyId) VALUES( '" +
                                title + "','" + salary + "','" + breaktime + "','" + note + "','" + workinghours + "','" + companyServerid + "','" + userId + "','" + starttime + "','" + endtime + "','" + workpassid + "','" + companyId + "');";

                        st.executeUpdate(query);
                        serverId = -1;
						String name = null;
                        ResultSet rs = null;
                        rs = st.executeQuery("SELECT LAST_INSERT_ID()");
                        rs.first();

                            workpass.setServerID(rs.getInt(1));
							workpass.setCompanyServerID(companyServerid);
							workpass.setIsSynced(1);
							workpass.setActionTag("Synced");

                            //serverId = rs.getInt("workpassId");
							//name = rs.getString("title");



						oos.writeObject(gson.toJson(workpass));
                        //System.out.println("Skickad!");
                    } catch (SQLException ex) {
                        // oos.writeObject("Something went wrong");

                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());
                    }
                }
            }


        } catch (Exception e) {
			e.printStackTrace();
            System.out.println(e.getStackTrace());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getSuppressed());
            System.out.println(e.getCause());
            System.out.println(e.fillInStackTrace());
        }
    }
}
