import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    //initializing socket
    private Socket socket;
    private Scanner scanner;
    private DataOutputStream out;
    private DataInput in;
    private String userName;

    public Client(Socket socket, String userName) throws IOException {

        try {
            this.socket = socket;
            this.in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            this.out = new DataOutputStream(socket.getOutputStream());
            this.userName = userName;
            out.writeUTF(this.userName);
            out.flush();
        } catch (Exception i) {
            System.out.println(i);
        }
    }

    public void sendMessage() throws IOException {
        scanner = new Scanner(System.in);
        while (socket.isConnected()) {
            String messageToSend = scanner.nextLine();
            out.writeUTF(userName + ": " + messageToSend);
            out.flush();
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            String messageFromGroupChat;

            while (socket.isConnected()) {
                try {
                    messageFromGroupChat = in.readUTF();
                    System.out.println(messageFromGroupChat);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your user name: ");
        String userName = scanner.nextLine();
        Socket sc = new Socket("localhost", 5000);
        Client client = new Client(sc, userName);
        client.listenForMessage();
        client.sendMessage();
    }

}
