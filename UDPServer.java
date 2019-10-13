import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
	
	public static final int PORT_NUMBER = 1235;
	
    public static void main(String[] args) throws IOException {
    	
    	System.out.println("Server is running on system with IP " + InetAddress.getLocalHost().getHostAddress());
    	
    	//Create a socket to listen to port mentioned
        DatagramSocket socket = new DatagramSocket(PORT_NUMBER);
        
       // Allocate memory for receiving data bytes.
        DatagramPacket packet = null;
        
        int count = 0;
        
        while (true) {
        	
        	// Clear the buffer after every message.
            byte[] data = new byte[65536];
            
            // Create a DatgramPacket to receive the data.
            packet = new DatagramPacket(data, data.length);
            
            socket.receive(packet);
            String clientInput = new String(packet.getData(), 0, packet.getLength());
            
            //Terminate the server on command exit
            if(clientInput.equalsIgnoreCase("exit")) {
            	System.out.println("Client says: "+ clientInput);
            	 String reply="Bye!!";
            	 byte[] data2 = reply.getBytes();
            	 InetAddress address = packet.getAddress();
                 int port = packet.getPort();
            	 packet = new DatagramPacket(data2, data2.length, address, port);
            	 socket.send(packet);
            	 break;
            }
            
            //Create new thread for a client
            Thread thread = new Thread(new UDPThread(socket, packet));
            thread.start();
            count++;
            System.out.println("Sockets connected: " + count);
            InetAddress address = packet.getAddress();
            System.out.println("IP of current client is -" + address.getHostAddress());

        }

    }
}
