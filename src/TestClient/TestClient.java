package TestClient;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

/**
 * Created by Henrik on 2016-03-15.
 */
public class TestClient {
    private String ip;
    private int port;
    private Listener l = new Listener();
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public TestClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        l.start();
    }




    private class Listener extends Thread {

        public void run() {
            try {

                    String response;
                    Socket socket = new Socket(ip, port);
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());

                while(true){

                }




            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

public static void main(String[] args){
    TestClient ts = new TestClient("127.0.0.1", 40001);
}

}
