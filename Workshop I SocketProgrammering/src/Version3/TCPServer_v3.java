package Version3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer_v3 {

    public static void main(String[] args) {
        try (ServerSocket welcomeSocket = new ServerSocket(6789)) {
            System.out.println("Serveren venter på klient");
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Klient forbundet til Server");
                ClientHandler clientHandler = new ClientHandler(connectionSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Fejl i serveren: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket connectionSocket;

        public ClientHandler(Socket connectionSocket) {
            this.connectionSocket = connectionSocket;
        }

        @Override
        public void run() {
            try (BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                 DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream())) {

                listenForMessage(inFromClient);

                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(System.in));
                while (!connectionSocket.isClosed()) {
                    String serverMessage = inFromServer.readLine();
                    outToClient.writeBytes(serverMessage + '\n');
                }
            } catch (IOException e) {
                System.err.println("Fejl i serveren: " + e.getMessage());
            }
        }

        private void listenForMessage(BufferedReader inFromClient) {
            new Thread(() -> {
                try {
                    while (true) {
                        String message = inFromClient.readLine();
                        System.out.println("FROM CLIENT: " + message);
                    }
                } catch (IOException e) {
                    System.err.println("Fejl i lytning af beskeder fra klienten: " + e.getMessage());
                }
            }).start();
        }
    }
}
