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



public class UDPThread implements Runnable {

    DatagramSocket socket = null;
    DatagramPacket packet = null;

    public UDPThread(DatagramSocket socket, DatagramPacket packet) {
        this.socket = socket;
        this.packet = packet;
    }

    @Override
    public void run() {
    	String reply = null;
        String clientInput = null;
        InetAddress address = null;
        int port = 8800;
        byte[] data2 = null;
        DatagramPacket packet2 = null;
        Operations operation=new Operations();
		
        try {
        	clientInput = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Client Says: " + clientInput);
            //
            String command  = "";
			String fileName = "";
			StringTokenizer st=new StringTokenizer(clientInput, " "); 
            address = packet.getAddress();
            port = packet.getPort();
            if(st.countTokens()>=2)
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
					  String filePath = "C://ProjectFiles/"+fileName;
					  File file = new File(filePath);
					  BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
					  if(file.exists())
					  { 
							byte replyBuf[] = reply1.getBytes();
							int first_space = clientInput.indexOf(' ');
							String tmp = clientInput.substring(first_space+1);
							
							int second_space = tmp.indexOf(' ');
							String data = tmp.substring(second_space+1);
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
            data2 = reply.getBytes();
            packet2 = new DatagramPacket(data2, data2.length, address, port);
            
            //Invoke the send call to send the data
            socket.send(packet2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
