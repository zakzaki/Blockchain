package p2p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Architecture.Block;
import Architecture.Blockchain;
import Architecture.Transaction;

public class Node implements Serializable {

    private Wallet wallet;
    private int token;
   
    private String name;
    private String address;
    private int port;
    private List<Node> peers;
    private Blockchain blockchain = new Blockchain();
    private List<Transaction> pendingTransactions = new ArrayList<>();
    private List<Transaction> passivelyProcessedTransactions  = new ArrayList<>();
    private List<Transaction> concernedTransactions  = new ArrayList<>();

    public Node(final String name, final String address, final int port, Wallet wallet, final Block root, final List<Node> peers) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.peers = peers;
        this.wallet = wallet;
        blockchain.addBlock(root);
      
    }
    //Sans le champ Peers pour la fonction de génération du jeu de test
    public Node(final String name, final String address, final int port,Wallet wallet, final Block root) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.wallet = wallet;
        this.peers = new ArrayList<>();
        blockchain.addBlock(root);
       
    }

    public Node(String senderHost, int senderPort) {
        this.address = senderHost;
        this.port = senderPort;
    }

    public List<Node> getPeers() {
        return peers;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public void setBlockchain(Blockchain bc){
        blockchain = bc;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public String getName() {
        return name;
    }

    public void addPeer(Node a) {
        if (!peers.contains(a))
            peers.add(a);
    }


    public Wallet getWallet() {
        return wallet;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (port != node.port) return false;
        return address != null ? address.equals(node.address) : node.address == null;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }

    public void addValidTransaction(Transaction transaction) {
        if (!pendingTransactions.contains(transaction))
            pendingTransactions.add(transaction);
    }

    public boolean alreadyAdded(Transaction transaction) {
        return pendingTransactions.contains(transaction);
    }

    public void updatePendingTransactions(List<Transaction> transactions) {
        pendingTransactions.removeAll(transactions);
    }

    public boolean alreadyAdded(Block block) {
        return blockchain.getBlocks().contains(block);
    }


    public List<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public int getToken() {
        return token;
    }


    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", port=" + port +
                ", peers=" + displayPeers() +
                '}';
    }

    private String displayPeers(){
        String res = "  ";
        for (Node n:peers
             ) {
            res = res+n.getAddress()+":"+n.getPort()+", ";
        }
        res = res.substring(0,res.length()-2);
        return "{"+res+"}";
    }
    public void setPeers(ArrayList<Node> peerList) {
        this.peers = peerList;
    }

    public void addConcernedTransaction(Transaction t){
        this.concernedTransactions.add(t);
    }
    public ArrayList<Transaction> getConcernedTransactions(){
        return (ArrayList<Transaction>) this.concernedTransactions;
    }

    public void updateBlockchain(Blockchain recievedBC) {
        if (blockchain == null){
            blockchain = recievedBC;
        }else{
            if (!(blockchain.getBlocks().contains(recievedBC.getLatestBlock())) && recievedBC.isBlockChainValid()){
                blockchain = recievedBC;
            }
        }
    }
}