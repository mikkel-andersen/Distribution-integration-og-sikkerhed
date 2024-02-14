

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
        while (!connectionSocket.isClosed()) {
            clientSentence = inFromClient.readLine();
            if (!clientSentence.equals("stop")) {
                System.out.println(clientSentence);
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Indtast et ord - eller 'stop' for at afslutte:");
                capitalizedSentence = fromServer.readLine() + '\n';
                outToClient.writeBytes(capitalizedSentence);
            } else {
                System.out.println("Klienten har afsluttet forbindelsen");
                connectionSocket.close();
                welcomeSocket.close();
            }
        }


    }
}
