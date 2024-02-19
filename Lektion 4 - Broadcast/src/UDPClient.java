import java.io.*;
import java.net.*;

class UDPClient {
	public static void main(String args[]) throws Exception {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		clientSocket.setBroadcast(true); // Enable broadcast
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = inFromUser.readLine();
		sendData = sentence.getBytes();
		InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255"); // Broadcast address
		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length, broadcastAddress, 9876); // Send to broadcast address
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData,
				receiveData.length);
		clientSocket.receive(receivePacket);

		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);
		clientSocket.close();
	}
}