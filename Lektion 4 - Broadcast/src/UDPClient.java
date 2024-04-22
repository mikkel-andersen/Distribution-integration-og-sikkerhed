import java.io.*;
import java.net.*;

class UDPClient {
	static DatagramSocket clientSocket;
	public static void main(String args[]) throws Exception {
//		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
//				System.in));
		clientSocket = new DatagramSocket();
		clientSocket.setBroadcast(true);
		udsendBroadcoast("Hallooo, hvor er I?");

	}

	public static void udsendBroadcoast(String s) throws IOException {
		InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = s;
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length, broadcastAddress, 5959);
		clientSocket.send(sendPacket);
		boolean modtaget = false;
		int numberTries = 0;
		clientSocket.setSoTimeout(3000);
		while (!modtaget && numberTries < 3) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			try {
				clientSocket.receive(receivePacket);
				InetAddress IPAdressOther = receivePacket.getAddress();
				System.out.println("Modtaget: " + IPAdressOther.toString());

			} catch (SocketTimeoutException e){
				numberTries++;
			}

		}
	}
}