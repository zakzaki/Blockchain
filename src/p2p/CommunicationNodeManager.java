package p2p;



import p2p.Wallet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import Architecture.Block;
import Architecture.Blockchain;
import Architecture.Sendable;
import Architecture.Transaction;



public class CommunicationNodeManager {

    private Node node;


    private ServerSocket serverSocket;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);
    private boolean listening = true;

    /******************************************** Constructors ********************************************************/
    /**
     *
     * @param name
     * @param address
     * @param port
     * @param wallet
     * @param root
     * @param peers
     */
    public CommunicationNodeManager(final String name, final String address, final int port, Wallet wallet,
                                    final Block root, final List<Node> peers){

        this.node = new Node(name,address,port,wallet,root,peers);
    }

    /**
     *
     * @param node
     */
    public CommunicationNodeManager(Node node){
        this.node = node;
    }

    /*************************************** Getters and setters ******************************************************/

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ScheduledThreadPoolExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(ScheduledThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    /*************************************** Auther methods ***********************************************************/

    public void startHost() {

        executor.execute(() -> {
            try {
                serverSocket = new ServerSocket(node.getPort());
                System.out.println(" Node started "+node);
                listening = true;
                while (listening) {
                    try {
                        Socket s = serverSocket.accept();

                        String str = s.getInetAddress().toString();
                        String strNew = str.replace("/", "");
                        System.out.println("adress "+s.getLocalSocketAddress() + " remote  "+s.getRemoteSocketAddress());
                        Node a = new Node(strNew,s.getLocalPort());
                        this.getNode().addPeer(a);

                        final NodeServerThread thread = new NodeServerThread(this, s);
                        thread.start();
                    }catch (SocketException e){

                    }
                }
                serverSocket.close();
            } catch (Exception e) {
                System.err.println("Could not listen to port " + node.getPort());
                e.printStackTrace();
            }
        });

      broadcast("requestBlockchain", null);
    }

    /**
     *
     */
    public void stopHost() {
        listening = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * broadcast message in the p2p network
     * @param type
     * @param data
     */
   /* public void broadcast(Message.MESSAGE_TYPE type, final Sendable data) {
        List<Node> peers = node.getPeers();
        Iterator it = peers.iterator();
        while (it.hasNext()) {
            try {
                Node peer = (Node) it.next();
                sendMessage(type, peer.getAddress(), peer.getPort(), data);
            }
            catch (Exception e){

            }
        }
    }*/

    public void broadcast(String  b, final Sendable data) {
        List<Node> peers = node.getPeers();
        Iterator it = peers.iterator();
        while (it.hasNext()) {
            try {
                Node peer = (Node) it.next();
                sendMessage(b, peer.getAddress(), peer.getPort(), data);
            }
            catch (Exception e){

            }
        }
    }


    /**
     * send message to all peers
     * @param type
     * @param host
     * @param port
     * @param data
     */
    public void sendMessage(Message.MESSAGE_TYPE type, String host, int port, Sendable data) {
        try (
                final Socket peer = new Socket(host, port);
                final ObjectOutputStream out = new ObjectOutputStream(peer.getOutputStream());
                final ObjectInputStream in = new ObjectInputStream(peer.getInputStream())) {
            Object fromPeer;
            while ((fromPeer = in.readObject()) != null) {
                if (fromPeer instanceof Message) {
                    final Message msg = (Message) fromPeer;
                    System.out.println(String.format(" Received: %s", msg.toString())+node);
                    if (Message.MESSAGE_TYPE.READY == msg.type) {
                        Message.MessageBuilder builder = new Message.MessageBuilder()
                                .withType(type)
                                .withReceiver(host,port)
                                .withSender(this.node.getAddress(),this.node.getPort());
                        if (type == Message.MESSAGE_TYPE.SEND_TRANSACTION)
                            builder.withTransaction((Transaction) data);
                        else if (type == Message.MESSAGE_TYPE.SEND_NEW_BLOCK)
                            builder.withBlock((Block) data);
                        out.writeObject(builder.build());
                    } else if (Message.MESSAGE_TYPE.RSPONSE_BLOCKCHAIN == msg.type) {
                        Blockchain recievedBC = msg.blockchain;
                        node.updateBlockchain(recievedBC);
                        break;
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println(String.format("Unknown host %s %d", host, port));
        } catch (IOException e) {

              try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(String message, String host, int port, Sendable data) {

        final Socket peer;

        try {
            peer = new Socket(host, port);

            OutputStream mmOutStream;
            mmOutStream = peer.getOutputStream();
            mmOutStream.write(message.getBytes());

            if (peer.getInputStream().available() > 0) {

                byte[] buffer;
                buffer = new byte[peer.getInputStream().available()];
                peer.getInputStream().read(buffer);
                Node n = new Node(host, port);
                
                System.out.println(String.format(" Received: %s", new String(buffer))+n);
        
                if(new String(buffer).equals("requestBlockchain")){
                    mmOutStream.write("this is Blockchain version".getBytes());
                }
            }


        } catch (IOException e) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }



    }


}