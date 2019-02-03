package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import Architecture.Block;
import Architecture.Block_serialiser;
import Architecture.Serialiser;
import Architecture.Transaction;

public class PeerThread extends Thread{

	private BufferedReader bufferRearder;
	
	public PeerThread(Socket socket) throws IOException {
		bufferRearder= new BufferedReader(new InputStreamReader(socket.getInputStream()));		
	}
	
	public void run() {
					
		try {			
			String msg=bufferRearder.readLine();			
		   
		   System.out.println("/********************************/");
		   System.out.println(" MESSAGE RECU EN BINAIRE");
		   System.out.println(msg);
		   
		   try {
		   
		   String s=Transaction.receivejson(msg);
     	   ObjectMapper objectMapper = new ObjectMapper();
     	   Serialiser ser= objectMapper.readValue(s,Serialiser.class);
     	   
     	   String sign=Transaction.receivesignature(msg);          	  
     	             	        
 		   System.out.println("\n /********************************/");
 		   System.out.println(" MESSAGE TRANSFORME EN CLAIR (TRANSACTION)");
 		   
 		   System.out.println(s+"\n");          		   
 		   System.out.println("********************************");
 		   
		   }catch(Exception e) {
    		   
    		   String s=Block.receivejson(msg);
    		   ObjectMapper objectMapper = new ObjectMapper();
        	   Block_serialiser ser= objectMapper.readValue(s,Block_serialiser.class);
    		   
    		   System.out.println("\n ********************************");
    		   System.out.println(" MESSAGE TRANSFORME EN CLAIR (BLOCK) ");
    		   
    		   System.out.println(s+"\n");          		   
    		   System.out.println("********************************");
    		      		               		              	                   	 
    	   }
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
