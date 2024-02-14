

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws Exception {

        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("Serveren venter på klient");
        Socket connectionSocket = welcomeSocket.accept();
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        System.out.println("Klient forbundet til Server");
        clientSentence = inFromClient.readLine();
        System.out.println(clientSentence);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(System.in));
        capitalizedSentence = fromServer.readLine();
        outToClient.writeBytes(capitalizedSentence);

    }
}
