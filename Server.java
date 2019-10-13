// Java program to illustrate Server side 

// Implementation using DatagramSocket 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



public class Server {

	public static final int PORT_NUMBER = 1235;
	


	public static void main(String[] args) throws IOException {

		System.out.println("Server is running on system with IP " + InetAddress.getLocalHost().getHostAddress());

		// Create a socket to listen at port 1234

		DatagramSocket ds = new DatagramSocket(PORT_NUMBER);

		InetAddress ip = InetAddress.getLocalHost();

		// Allocate memory for receiving data bytes.

		DatagramPacket DpReceive = null;

		byte[] receiveBuff = new byte[65535];

		DatagramPacket DpSend = null;

		//byte sendBuf[] = null;
		Operations operation=new Operations();
		 ReadWriteLock rwlock = new ReentrantReadWriteLock();
		
		while (true) {

			// Create a DatgramPacket to receive the data.
			
			DpReceive = new DatagramPacket(receiveBuff, receiveBuff.length);
			ds.receive(DpReceive);// Receive the data in byte buffer.
			String clientInput = data(receiveBuff).toString();// Modify this command to obtain opcode.
			String reply = "";
			receiveBuff = new byte[65535]; // Clear the buffer after every message.
			System.out.println(clientInput);
			String command  = "";
			String fileName = "";
			StringTokenizer st=new StringTokenizer(clientInput, " ");
			//String[] clientRequest=clientInput.split(" ");
			 
			
			
			
			if(st.countTokens()==2)
			{
				command  = st.nextToken();
				 fileName = st.nextToken();
				if (command.equalsIgnoreCase("read")) {
	
					reply=operation.readFile(fileName);
				}
	
				else if (command.equalsIgnoreCase("delete")) {
	
					reply=operation.deleteFile(fileName);
				}
				else if(command.equalsIgnoreCase("write")){
					 String reply1="";	
					  String filePath = "E://ProjectFiles/"+fileName;
					  File file = new File(filePath);
					  BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
					  if(file.exists())
					  { 
					  	reply1=	"Write Content :";
					 
							byte replyBuf[] = reply1.getBytes();
							DpSend = new DatagramPacket(replyBuf, replyBuf.length, DpReceive.getAddress(), DpReceive.getPort());
							// Invoke the send call to actually send the data.
							ds.send(DpSend);
							replyBuf = null;
							DpReceive = new DatagramPacket(receiveBuff, receiveBuff.length);
	
							// Receive the data in byte buffer.
							ds.receive(DpReceive);
							// Data to be appended
							String data = data(receiveBuff).toString();
							writer.newLine(); 
							writer.write(data);
							writer.close();
							reply="Content successfully added to the file.";
					  }
					}else {
						reply="Hello!! Please give the command in the form : command filename (read/write/delete abc.txt)";
							
					}
			}else {
				reply="Hello!! Please give the command in the form : command filename (read/write/delete abc.txt)";
			}
				//reply="";
			
				byte replyBuf[] = reply.getBytes();
	
				DpSend = new DatagramPacket(replyBuf, replyBuf.length, DpReceive.getAddress(), DpReceive.getPort());
	
				ds.send(DpSend);// Invoke the send call to actually send the data.
	
				replyBuf = null;
			
				
			
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