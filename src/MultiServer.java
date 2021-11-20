import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class MultiServer {
    private ServerSocket serverSocket;
    private Socket socket;
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public MultiServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started");
        System.out.println("Waiting for the client");
        while (true) {
            socket = serverSocket.accept();
            ClientHandler ch = new ClientHandler(socket);
            clientHandlers.add(ch);
            ch.start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private DataOutputStream out;
        private DataInput in = null;
        private String userName;

        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;
                this.out = new DataOutputStream(socket.getOutputStream());
                this.in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                this.userName = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                System.out.println("User " + this.userName + " entered the chat.");
                broadcastMessage("User " + this.userName + " entered the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            String messageFromTheClient;
            while (socket.isConnected()) {
                try {
                    messageFromTheClient = in.readUTF();
                    System.out.println("SERVER RECEIVED: " + messageFromTheClient);
                    broadcastMessage(messageFromTheClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void broadcastMessage(String message) throws IOException {
            for (ClientHandler clientHandler : clientHandlers) {
                if (!clientHandler.userName.equals(userName)){
                    clientHandler.out.writeUTF(message);
                    clientHandler.out.flush();
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        MultiServer server = new MultiServer(5000);
    }
}
