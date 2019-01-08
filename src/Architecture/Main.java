package Architecture;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
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
		
		/*
		      ARBRE DE MERKLE EXEMPLE  
		ArrayList<String> donne=new ArrayList<>();
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
		
	//	Wallet k = new Wallet("myWallet.ser");
	
		Transaction t1=new Transaction("T1", serialiser,w1.getPublicKey());
		Transaction t2=new Transaction("T2", serialiser2,w2.getPublicKey());		
				
		t1.signTransaction(w1.getPrivateKey());
		t2.signTransaction(w2.getPrivateKey());
							
		
		Node n1 = new Node("127.0.0.1", 3003); 
		Node n2 = new Node("127.0.0.1", 3004); 
		
		List<Node>peer1=new ArrayList<>();
		//peer1.add(n2);
		
		List<Node>peer2=new ArrayList<>();
		peer2.add(n1);
		
		ArrayList<Transaction>t=new ArrayList<>();
		t.add(t1);
		t.add(t2);
		Block b=new Block(w1.getPublicKey(), t,"test");
		
		CommunicationNodeManager c1=new CommunicationNodeManager("C1 ", "127.0.0.1", 3003, w1, b, peer1);
		CommunicationNodeManager c2=new CommunicationNodeManager("C2 ", "127.0.0.1", 3004, w2, b, peer2);
			
		
		c1.startHost();	
		//c2.getNode().addPeer(n1);
		c2.startHost();
			
		String bbb=b.sendjson(w1.getPrivateKey());
		
		
		c1.broadcast(bbb, null);
			
		try {
			String s=t1.sendjson(w1.getPrivateKey());
			c1.broadcast(s,null);
		//	String json=t1.receivejson(s);
			
			System.out.println(t1.verifiersignature());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		b.mineBlock(4); // on mine un block avec une difficulté de 4 pour aller plus vite
		
		

	}

}
