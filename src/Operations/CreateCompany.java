package Operations;

import UserPackage.Company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.List;

/**
 * Created by Henrik on 2016-03-22.
 */
public class CreateCompany extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private Company company;

    public CreateCompany(Socket socket, ObjectOutputStream oos, ObjectInputStream ois)
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
		Gson gson = new GsonBuilder().create();
        try {

			String looptimes = (String) ois.readObject();


			for(int i = 0; i < Integer.parseInt(looptimes); i++){

				String jObject = (String)ois.readObject();
				company = gson.fromJson(jObject, Company.class);
				if(company.getActionTag().equals("Change Company")){
					new ChangeCompanyInfo(socket, oos, ois, company);
				}else if(company.getActionTag().equals("Delete Company")){
					new DeleteCompany(socket, oos, ois, company);
				}else {
					try {
						double houelywage = company.getHourlyWage();
						String companyName = company.getCompanyName();
						int userId = company.getUserId();
						long localId = company.getCompanyId();
						conn = DriverManager.getConnection(url, username, password);

						tt = conn.createStatement();
						tt.executeUpdate("INSERT INTO company(userid, companyname, hourlywage, localcompanyid) VALUES('" + userId + "','" + companyName + "','" + houelywage + "','" + localId + "' );");
						int companyId = -1;
						ResultSet co = null;
						co = tt.executeQuery("SELECT LAST_INSERT_ID()");
						if (co.next()) {
							companyId = co.getInt(1);
                    /*company.setServerID(companyId);
                    company.setIsSynced(1);
                    company.setActionTag(null);*/
						}

						oos.writeObject(String.valueOf(companyId));


					} catch (SQLException ex) {
						oos.writeObject("Something went wrong");
						System.out.println("SQLException: " + ex.getMessage());
						System.out.println("SQLState: " + ex.getSQLState());
						System.out.println("VendorError: " + ex.getErrorCode());
					}
				}
			}

			ois.close();
			oos.close();
			socket.close();

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
