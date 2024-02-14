import java.net.*;
import java.io.*;

public class ServerThread extends Thread{
	Socket connSocket;
	
	public ServerThread(Socket connSocket) {
		this.connSocket = connSocket;
	}
	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());
			
			// Do the work and the communication with the client here	
			// The following two lines are only an example
			String clientSentence = inFromClient.readLine();

			System.out.println("Received: " + clientSentence);

			String[] a = null;
			if (clientSentence != null) {
				a = clientSentence.split("\\s+");
			}

			if (a != null && !a[1].endsWith(".ico")) {
				outToClient.writeBytes("HTTP/1.1 200 OK\n"); // status line
				outToClient.writeBytes(ContentType(a[1])); // header line
				outToClient.writeBytes("Connection: close\n"); // header line
				outToClient.writeBytes("\n"); // blank line

				byte[] file = read("/Users/mikkel/Documents/Datamatiker/3. semester/DIS/WebServer" + a[1]);
				outToClient.write(file); // body
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public byte[] read(String aInputFileName) throws FileNotFoundException {
		// returns the content of a file in a binary array
		System.out.println("Reading in binary file named : " + aInputFileName);
		File file = new File(aInputFileName);
		System.out.println("File size: " + file.length());
		byte[] result = new byte[(int) file.length()];
		InputStream input = null;
		try {
			int totalBytesRead = 0;
			input = new BufferedInputStream(new FileInputStream(file));
			while (totalBytesRead < result.length) {
				int bytesRemaining = result.length - totalBytesRead;
				int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
				// input.read() returns -1, 0, or more :
				if (bytesRead > 0) {
					totalBytesRead = totalBytesRead + bytesRead;
				}
			}
			System.out.println("Num bytes read: " + totalBytesRead);
		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			System.out.println("Closing input stream.");
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public void write(byte[] aInput, String aOutputFileName) {
		System.out.println("Writing binary file...");
		try {
			OutputStream output = null;
			try {
				output = new BufferedOutputStream(new FileOutputStream(aOutputFileName));
				output.write(aInput);
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public java.lang.String ContentType(String docuname) {
		//returns the Content-Type headerline for a given document-name
		if (docuname.endsWith(".html")){
			return ("Content-Type: text/html\n");
		} else if (docuname.endsWith(".gif")) {
			return ("Content-Type: image/gif\n");
		} else if (docuname.endsWith(".png")) {
			return ("Content-Type: image/png\n");
		} else if (docuname.endsWith(".jpg")) {
			return ("Content-Type: image/jpg\n");
		} else if (docuname.endsWith(".js")) {
			return ("Content-Type: text/javascript\n");
		} else if (docuname.endsWith(".css")) {
			return ("Content-Type: text/css\n");
		} else if (docuname.endsWith(".jpeg")) {
			return ("Content-Type: text/jpeg\n");
		} else {
			return ("Content-Type: text/plain\n");
		}
	}
}
