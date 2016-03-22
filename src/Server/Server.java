package Server;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * Created by Henrik on 2016-03-15.
 */
public class Server extends Thread {
    private ServerSocket serversocket;
    private Thread thread = new Thread(this);
    private CreateUser createUser;
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
                tag = ois.readObject().toString();
                if(tag.equals("Create User") ){
                   new CreateUser(socket, oos, ois);
                }else if(tag.equals("Login")){
                    new Login(socket, ois, oos);
                }else if(tag.equals("Change Info")){
                    new ChangeUserInfo(socket, oos, ois);
                }else if(tag.equals("Change Company Info")){

                }else if(tag.equals("Create Company")){

                }else if(tag.equals("Delete Company")){

                }else if(tag.equals("Delete User")){

                }
            }
            }catch(Exception e){

            }
        }



    public static void main(String[] args) {
        Server server = new Server(45001);
    }


}


