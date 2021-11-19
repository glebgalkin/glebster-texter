import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    //initializing socket
    private Socket socket = null;
    private Scanner scanner = null;
    private DataOutputStream out = null;
    private DataInput in = null;
    String serverResponse = "";

    public Client(String address, int port) throws IOException {

        try {
            //taking input from the client socket
            socket = new Socket(address, port);
            System.out.println("Connected");

            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            //takes input from terminal
            scanner = new Scanner(System.in);

            //sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());

            serverResponse = in.readUTF();
            System.out.println(serverResponse);

        } catch (Exception i) {
            System.out.println(i);
        }


        // string to read message from input
        String line = "";

        while (!line.equals(".")) {

            try {
                line = scanner.nextLine();
                out.writeUTF(line);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        //close the connection
        try {
            scanner.close();
            out.close();
            socket.close();
        } catch (Exception o) {
            System.out.println(o);
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("127.0.0.1", 5000);
    }

}
