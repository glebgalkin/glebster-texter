import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    //initializing socket
    private Socket socket = null;
    private Scanner scanner = null;
    private DataOutputStream out = null;

    public Client(String address, int port) throws IOException {

        try {
            socket = new Socket(address, port);
            System.out.print("Connected");

            //takes input from terminal
            scanner = new Scanner(System.in);

            //sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());


        } catch (Exception i) {
            System.out.println(i);
        }


        // string to read message from input
        String line = "";

        while (!line.equals("Stop")) {

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
