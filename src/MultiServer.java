import java.io.*;
import java.net.*;

public class MultiServer {
    private ServerSocket serverSocket;
    private Socket socket;

    public MultiServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started");
        System.out.println("Waiting for the client");
        while (true) {
            socket = serverSocket.accept();
            System.out.println("Client Accepted");
            ClientHandler ch = new ClientHandler(socket);
            ch.start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private DataOutputStream out = null;
        private DataInput in = null;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new DataOutputStream(socket.getOutputStream());
                String hi = "Welcome to faggot server";
                out.writeUTF(hi);
                in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String line = "";
            try {
                while (!line.equals("Over")) {

                    line = in.readUTF();
                    System.out.println(line);
                }
                System.out.println("Closing connection");
                socket.close();
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MultiServer server = new MultiServer(5000);
    }
}
