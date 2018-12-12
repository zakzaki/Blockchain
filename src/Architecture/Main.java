package Architecture;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import p2p.CommunicationNodeManager;
import p2p.Message;
import p2p.Node;
import p2p.Wallet;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		
		ArrayList<String> donne=new ArrayList<>();
		String root="";
		donne.add("transaction 1");
		donne.add("transaction 2");
		/*donne.add("transaction 3");
	    donne.add("transaction 4");
		donne.add("transaction 5");
		donne.add("transaction 6");
		donne.add("transaction 7");
		donne.add("transaction 8");
		donne.add("transaction 9");
		donne.add("transaction 10");*/
		//donne.add("transaction 5");	
		
		System.out.println(arbre_merkel.arbre2(donne));
		
		Wallet w=new Wallet();
		
		Transaction t1=new Transaction(w.getPublicKey(), "creation");
		
		ArrayList<Transaction>transactions=new ArrayList<>();
		transactions.add(t1);
		
		System.out.println("ARBRE TRANSACTION  "+arbre_merkel.arbre(transactions));
		
		Block b=new Block(w.getPublicKey(), transactions, "2");
		b.sign(w.getPrivateKey());
		
		System.out.println("CLE PUBLIQUE "+w.getPublicKey().toString()+" CLE PRIVE "+w.getPrivateKey().toString());		
		System.out.println("LE BLOC");
		System.out.println("current hash "+b.getCurrentHash()+"\n ROOT "+b.getRoot()+"\n SIGN "+b.getCreatorSignature());
		
		/*Wallet w1=new Wallet();
		Wallet w2=new Wallet();
		
		List<Node>peer1=new ArrayList<>();
		
		Block b=new Block("fff", "fgg");
		
		Node n1 = new Node("127.0.0.1", 3000); 
			
			
		CommunicationNodeManager c1=new CommunicationNodeManager("C1 ", "127.0.0.1", 3000, w1, b, peer1);
		CommunicationNodeManager c2=new CommunicationNodeManager("C2 ", "127.0.0.1", 3001, w2, b, peer1);
		c1.startHost();
		
		c2.getNode().addPeer(n1);
		c2.startHost();
		
		
		Transaction t = new Transaction(w1.getPublicKey(),"creation","1"); 
		
		c2.broadcast(Message.MESSAGE_TYPE.SEND_TRANSACTION, t);
		
		
		
		Blockchain blockchain=new Blockchain();*/
		
		

	}

}
