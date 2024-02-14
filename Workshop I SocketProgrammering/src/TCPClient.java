
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.SQLOutput;


public class TCPClient {

	public static void main(String[] args) throws Exception, IOException {
		
		String sentence;
		String modifiedSentence;

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


		while (!clientSocket.isClosed()) {
			System.out.println("Indtast et ord - eller 'stop' for at afslutte:");
			sentence = inFromUser.readLine();
			outToServer.writeBytes(sentence + '\n');
			modifiedSentence = inFromServer.readLine();

			if (!modifiedSentence.equals("stop")) {
			System.out.println(modifiedSentence);
		} else {
				System.out.println("Forbindelsen er afsluttet");
				clientSocket.close();
			}
		}
	}

	public void listenForMessage() {
		new Thread(() -> {
			try {
				while(true) {
					String message = inFromServer.readLine();
					System.out.println("FROM SERVER: " + message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
