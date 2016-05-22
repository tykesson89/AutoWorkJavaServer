package Operations;

import Server.Server;
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
		String password = Server.DATABASE_PASSWORD;
        Statement st = null;
        Statement tt = null;
        System.out.println("tråden startar");
		Gson gson = new GsonBuilder().create();
        try {
			System.out.println(1);
			String looptimes = (String) ois.readObject();


			for(int i = 0; i < Integer.parseInt(looptimes); i++){
				System.out.println(21);
				String jObject = (String)ois.readObject();
				System.out.println(22);
				company = gson.fromJson(jObject, Company.class);
				System.out.println(company.getActionTag());
				if(company.getActionTag().equals("Change Company")){
					System.out.println(24);
					new ChangeCompanyInfo(socket, oos, company);
					System.out.println(25);
				}else if(company.getActionTag().equals("Delete Company")){
					System.out.println(26);
					new DeleteCompany(socket, oos, company);
					System.out.println(27);
				}else {
					try {
						System.out.println(3);
						double houelywage = company.getHourlyWage();
						System.out.println(4);
						String companyName = company.getCompanyName();
						System.out.println(5);
						int userId = company.getUserId();
						System.out.println(6);
						long localId = company.getCompanyId();
						conn = DriverManager.getConnection(url, username, password);
						System.out.println(7);

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

						oos.writeObject(companyId);


					} catch (SQLException ex) {
						oos.writeObject("Something went wrong");
						System.out.println("SQLException: " + ex.getMessage());
						System.out.println("SQLState: " + ex.getSQLState());
						System.out.println("VendorError: " + ex.getErrorCode());
					}
				}
			}



        } catch (Exception e) {
            System.out.println(e.getStackTrace());
			System.out.println(e.getCause());
			System.out.println(e.fillInStackTrace());

        }
    }
}
