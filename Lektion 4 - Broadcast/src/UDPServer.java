import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class UDPServer {
    private DatagramSocket broadcastSocket;
    private DatagramSocket svarSocket;

	public static void main(String[] args) throws SocketException {
		UDPServer server = new UDPServer();
	}

    public UDPServer() throws SocketException {
        this.broadcastSocket = new DatagramSocket(1234);
        this.svarSocket = new DatagramSocket();

		lytEfterBroadcast();
    }

    public void lytEfterBroadcast() {
        new Thread(() -> {
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    broadcastSocket.receive(receivePacket);
                    String sentence = new String(receivePacket.getData());
                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();
                    sendData = sentence.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    svarSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
