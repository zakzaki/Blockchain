package p2p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

	        try (
	                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
	                final ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
	            Message message = new Message.MessageBuilder().withSender(nodeManager.getNode().getAddress(),nodeManager.getNode().getPort())
	                    .withReceiver(client.getInetAddress().getHostAddress().toString(),client.getPort()).withType(Message.MESSAGE_TYPE.READY).build();
	            out.writeObject(message);
	            Object fromClient;
	            while ((fromClient = in.readObject()) != null) {
	                if (fromClient instanceof Message) {

	                    System.out.println(String.format(" Received: %s", fromClient.toString())+nodeManager.getNode());

	                }
	            }
	            client.close();
	        } catch (ClassNotFoundException | IOException e) {
	            e.printStackTrace();
	        }
	    }

	}

