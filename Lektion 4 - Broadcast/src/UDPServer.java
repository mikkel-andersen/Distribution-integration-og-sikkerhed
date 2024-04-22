import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class UDPServer {
    private DatagramSocket broadcastSocket;
    private DatagramSocket svarSocket;

	public static void main(String[] args) throws Exception {
		UDPServer server = new UDPServer();
	}

    public UDPServer() throws Exception {
        this.broadcastSocket = new DatagramSocket(5959);
        this.svarSocket = new DatagramSocket();
		lytEfterBroadcast();
    }

    public void lytEfterBroadcast() throws Exception {
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            boolean modtaget = false;
            while (!modtaget) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                broadcastSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                System.out.println(sentence);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                svarSocket.send(sendPacket);
                modtaget = true;
            }
            svarSocket.close();
            }
    }

