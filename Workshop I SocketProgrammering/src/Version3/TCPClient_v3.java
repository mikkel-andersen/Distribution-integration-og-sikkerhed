package Version3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient_v3 {

    public static void main(String[] args) {
        try (BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
             Socket dnsSocket = new Socket("localhost", 9090);
             BufferedReader inFromDNS = new BufferedReader(new InputStreamReader(dnsSocket.getInputStream()));
             DataOutputStream outToDNS = new DataOutputStream(dnsSocket.getOutputStream())) {

            // Send the name to the DNS server
            String name = inFromUser.readLine();
            outToDNS.writeBytes(name + '\n');

            // Receive the IP address from the DNS server
            String ipAddress = inFromDNS.readLine();
            System.out.println("IP address received from DNS server: " + ipAddress);

            // Close the connection with the DNS server
            dnsSocket.close();

            try (Socket clientSocket = new Socket(ipAddress, 6789);
                 DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                System.out.println("Indtast et ord");
                listenForMessage(inFromServer);

                String sentence;
                while (!clientSocket.isClosed()) {
                    sentence = inFromUser.readLine();
                    outToServer.writeBytes(sentence + '\n');
                }
            } catch (IOException e) {
                System.err.println("Fejl i klienten: " + e.getMessage());
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Fejl i klienten: " + e.getMessage());
        }
    }

    private static void listenForMessage(BufferedReader inFromServer) {
        new Thread(() -> {
            try {
                while (true) {
                    String serverMessage = inFromServer.readLine();
                    if (serverMessage != null) {
                        System.out.println("FROM SERVER: " + serverMessage);
                    }
                }
            } catch (IOException e) {
                System.err.println("Fejl i lytning af beskeder fra serveren: " + e.getMessage());
            }
        }).start();
    }
}