
// Java program to illustrate Client side 
// Implementation using DatagramSocket 
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
	public static final int PORT_NUMBER = 1236;
	public static final int SERVER_PORT_NUMBER = 1235;
	public static final String SERVER_IP = "10.1.20.35";

	public static void main(String args[]) throws IOException {

		System.out.println("Client is running on system with IP " + InetAddress.getLocalHost().getHostAddress());
		System.out.println("Enter a message to be sent to the Server : ");

		// Initialize scanner object to read user input.
		Scanner sc = new Scanner(System.in);

		InetAddress ip = InetAddress.getLocalHost();

		// Create the socket object for carrying the data.
		DatagramSocket ds = new DatagramSocket(PORT_NUMBER);

		// Allocate memory for receiving data bytes.
		DatagramPacket DpReceive = null;
		byte[] receiveBuff = new byte[65535];

		DatagramPacket DpSend = null;
		byte sendBuf[] = null;

		// loop while user not enters "bye"
		while (true) {
			// Create a DatgramPacket to receive the data.
			DpReceive = new DatagramPacket(receiveBuff, receiveBuff.length);

			String inp = sc.nextLine();

			// Convert the String input into the byte array.
			sendBuf = inp.getBytes();

			// Create the datagramPacket for sending the data.
			DpSend = new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(SERVER_IP),
					SERVER_PORT_NUMBER);

			// Invoke the send call to actually send the data.
			ds.send(DpSend);

			// break the loop if user enters "bye"
			if (inp.equals("bye")) {
				break;
			}

			// Receive the data in byte buffer.
			ds.receive(DpReceive);

			System.out.println("Server : " + data(receiveBuff));
			receiveBuff = new byte[65535];
		}
	}

	// A utility method to convert the byte array
	// data into a string representation.
	public static StringBuilder data(byte[] a) {
		if (a == null)
			return null;
		StringBuilder ret = new StringBuilder();
		int i = 0;
		while (a[i] != 0) {
			ret.append((char) a[i]);
			i++;
		}
		return ret;
	}
}
