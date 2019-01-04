package Architecture;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import p2p.CommunicationNodeManager;
import p2p.Message;
import p2p.Node;
import p2p.Wallet;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, UnsupportedEncodingException, JsonProcessingException, SignatureException {	
		
		/*ArrayList<String> donne=new ArrayList<>();
		String root="";
		donne.add("transaction 1");
		donne.add("transaction 2");
		donne.add("transaction 3");
	    donne.add("transaction 4");
		donne.add("transaction 5");
		donne.add("transaction 6");
		donne.add("transaction 7");
		donne.add("transaction 8");
		donne.add("transaction 9");
		donne.add("transaction 10");
		donne.add("transaction 5");			
	    System.out.println(arbre_merkel.arbre2(donne));*/
		
		Wallet w1=new Wallet();
		Wallet w2=new Wallet();
		
		Limits limits=new Limits(10, 500);
		Date_p d=new Date_p("14/12/2018", "16/12/2018", "15/12/2018");
		Payload payload=new Payload("zak", "TEST TEST", d, "Marseille", limits);
		Payload payload2=new Payload("zak22", "TEST TEST 2", d, "Paris", limits);
		Serialiser serialiser=new Serialiser(w1.getPublicKey(), "CREATION", payload);
		Serialiser serialiser2=new Serialiser(w2.getPublicKey(), "CREATION", payload2);
		
		Transaction t1=new Transaction("T1", serialiser,w1.getPublicKey());
		Transaction t2=new Transaction("T2", serialiser2,w2.getPublicKey());
				
			/*	String s="";		
					
					try {
						s=t1.stringToBinary();
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					System.out.println(s);;
					System.out.println(t1.converttostring(s)); */
	
		
	/*	ArrayList<Transaction>transactions=new ArrayList<>();
		transactions.add(t1);
		
		System.out.println("ARBRE TRANSACTION  "+arbre_merkel.arbre(transactions));
		
		Block b=new Block(w.getPublicKey(), transactions, "2");
		b.sign(w.getPrivateKey());
		
		System.out.println("CLE PUBLIQUE "+w.getPublicKey().toString()+" CLE PRIVE "+w.getPrivateKey().toString());		
		System.out.println("LE BLOC");
		System.out.println("current hash "+b.getCurrentHash()+"\n ROOT "+b.getRoot()+"\n SIGN "+b.getCreatorSignature());
		*/
		
	
		Node n1 = new Node("127.0.0.1", 3005); 
		Node n2 = new Node("127.0.0.1", 3006); 
		
		List<Node>peer1=new ArrayList<>();
		//peer1.add(n2);
		
		List<Node>peer2=new ArrayList<>();
		peer2.add(n1);
		
		//Block b=new Block("fff", "fgg");
		
		ArrayList<Transaction>t=new ArrayList<>();
		t.add(t1);
		t.add(t2);
		Block b=new Block(w1.getPublicKey(), t,"hhh");
		
		CommunicationNodeManager c1=new CommunicationNodeManager("C1 ", "127.0.0.1", 3005, w1, b, peer1);
		CommunicationNodeManager c2=new CommunicationNodeManager("C2 ", "127.0.0.1", 3006, w2, b, peer2);
			
		
		c1.startHost();	
		//c2.getNode().addPeer(n1);
		c2.startHost();
			
		String bbb=b.sendjson(w1.getPrivateKey());
		System.out.println(bbb);
		c1.broadcast(bbb, null);
		//Transaction t = new Transaction(w1.getPublicKey(),"creation","1"); 		
		//c1.broadcast(Message.MESSAGE_TYPE.READY, t1);
	/*	try {
			String s=t1.sendjson(w1.getPrivateKey());
			c1.broadcast(s,null);
		//	String json=t1.receivejson(s);
			
			System.out.println(t1.verifiersignature(s));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		//Blockchain blockchain=new Blockchain();
		
		

	}

}
