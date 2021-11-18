import java.io.*;
import java.net.*;

public class Server {

    //initializing socket
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInput in = null;

    public Server(int port){
        try{
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for the client");

            socket = server.accept();
            System.out.println("Client Accepted");

            //taking input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            String line = "";

            //read message from client until "Over" is sent
            while(!line.equals("Over")){

                try{

                    line = in.readUTF();
                    System.out.println(line);

                }catch(IOException i){
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            //close connection
            socket.close();
            //in.close();

        }catch(IOException i){
            System.out.print(i);
        }
    }

    public static void main(String[] args){
        Server server = new Server(5000);
    }
}
