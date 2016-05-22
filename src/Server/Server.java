package Server;



import Operations.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * Created by Henrik on 2016-03-15.
 */
public class Server extends Thread {
	public static String DATABASE_PASSWORD = "g17sk44d";
    private ServerSocket serversocket;
    private Thread thread = new Thread(this);
    public Server(int port) {
        try {
            serversocket = new ServerSocket(port);
            thread.start();
        } catch (IOException o) {
        }
    }

    public void run() {
        System.out.println("Server startad");
        try {
            while (true) {
                String tag;
                System.out.println("Waiting for client to connect!");
                Socket socket = serversocket.accept();
                System.out.println("Client Connected");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                try {
                    tag = ois.readObject().toString();
                    if (tag.equals("Create User")) {
                        new CreateUser(socket, oos, ois);
                    } else if (tag.equals("Login")) {
                        new Login(socket, ois, oos);
                    } else if (tag.equals("Change User Info")) {
                        new ChangeUserInfo(socket, oos, ois);
                    } else if (tag.equals("Create Company")) {
                        new CreateCompany(socket, oos, ois);
                    } else if (tag.equals("Delete User")) {
                        new DeleteUser(socket, oos, ois);
                    } else if (tag.equals("New Password")) {
                        new GetNewPassword(socket, ois, oos);
                    } else if (tag.equals("Change Password")) {
                        new ChangePassword(socket, oos, ois);
                    } else if (tag.equals("Create Workpass")) {
                        new CreateWorkpass(socket, oos, ois);
                    }
                }catch (EOFException e){
                    e.printStackTrace();
                }
            }
            }catch(Exception e){
				e.printStackTrace();
            }
        }


    public static void main(String[] args) {
        Server server = new Server(45001);
    }
}
