import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Operations {
	
	
	public String readFile(String fileName){
		
		String filePath = "E://ProjectFiles/"+fileName;
		String reply = "";
		try {
			
			FileReader fileReader= new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line=null;
			while((line=bufferedReader.readLine())!=null) {
				if(reply.isEmpty()) {
					reply=line;
				}else {
				reply=reply.concat("\n").concat(line);
				}
			}
				bufferedReader.close();
			}
			catch(IOException ex){
				
				reply="File not found..!!";
			}
		
		return reply;
	}

	public String deleteFile(String fileName) {
		
		String reply = "";
		String filePath = "E://ProjectFiles/"+fileName;
		
		File file=new File(filePath);
		if(file.delete()) {
			reply="File deleted sucsessfully..!!";
		}
		else {
		
			reply="File not found..!!";
		}
	//reply = "Reading the file";
		return reply;
		
	}

	
	public String writeFile(String fileName) 
	{ 
	  String reply = "";
	  String filePath = "E://ProjectFiles/"+fileName;
	  File file = new File(filePath);
	  
	  try {
		if(file.createNewFile())
		{ 
		  	reply=	"File has been created..!!";
		  } 
		else 
		{ 
			reply="File already exists..!!";
		}
	} catch (IOException e) {
		reply="File already exists..!!";
	}
	  
	  return reply; }
	 
}
