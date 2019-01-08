package p2p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import Architecture.Block;
import Architecture.Block_serialiser;
import Architecture.Serialiser;
import Architecture.Transaction;

public class NodeServerThread extends Thread {


    private Socket client;
    private final CommunicationNodeManager nodeManager;

    /*********************************** Constructors *****************************************************************/

    /**
     *
     * @param nodeManager
     * @param client
     */
    NodeServerThread(final CommunicationNodeManager nodeManager, final Socket client) {
        super(nodeManager.getNode().getName() + System.currentTimeMillis());
        this.nodeManager = nodeManager;
        this.client = client;
    }

    /********************************Getters and setters **************************************************************/

    /**
     *
     * @return
     */
    public Socket getClient() {
        return client;
    }

    /**
     *
     * @param client
     */
    public void setClient(Socket client) {
        this.client = client;
    }

    /**
     *
     * @return
     */
    public CommunicationNodeManager getNodeManager() {
        return nodeManager;
    }

    /*************************************************** Auther methods ***********************************************/


    @Override
    public void run() {

        String message = "j'accepte ta connexion";
        try {
            client.getOutputStream().write("test".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
       try {
           if (client.getInputStream().available() > 0) {
               byte[] buffer;
               buffer = new byte[client.getInputStream().available()];
               client.getInputStream().read(buffer);
               System.out.println(" Reçu de la part de : "+ nodeManager.getNode());
               System.out.println("from : "+client.getRemoteSocketAddress()+" ------ "+client.getLocalSocketAddress()+"\n");
           
              
               String msg=new String(buffer);
               
               if(!msg.equals("requestBlockchain")) {
            	   
        		   System.out.println("/********************************/");
        		   System.out.println(" MESSAGE RECU EN BINAIRE");
        		   System.out.println(msg);
            	   
            	   try {
            	             	        
        		   System.out.println("\n /********************************/");
        		   System.out.println(" MESSAGE TRANSFORME EN CLAIR");
        		   String s=Transaction.receivejson(msg);
        		   System.out.println(s+"\n");          		   
        		   System.out.println("/********************************/");
            	   
            	   ObjectMapper objectMapper = new ObjectMapper();
            	   Serialiser ser= objectMapper.readValue(s,Serialiser.class); 
            	   
            	   System.out.println(s+"\n");
            	   
            	 //  System.out.println(ser.getPayload().toString());
            	   }catch(Exception e) {
            		   
            		   
            		   System.out.println("\n /********************************/");
            		   System.out.println(" MESSAGE TRANSFORME EN CLAIR");
            		   String s=Block.receivejson(msg);
            		   System.out.println(s+"\n");          		   
            		   System.out.println("/********************************/");
            		               		  
                	   
                	   ObjectMapper objectMapper = new ObjectMapper();
                	   Block_serialiser ser= objectMapper.readValue(s,Block_serialiser.class); 
                	                   	 
            	   }
            	   
               }
              
               
           }
           client.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}