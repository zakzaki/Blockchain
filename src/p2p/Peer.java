package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Scanner;

import Architecture.Block;
import Architecture.Blockchain;
import Architecture.Date_p;
import Architecture.Limits;
import Architecture.Payload;
import Architecture.Serialiser;
import Architecture.Transaction;

public class Peer {
	

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, SignatureException {
				
		System.out.println("entrer username ");
		Scanner sc = new Scanner(System.in);		
		String usern=sc.nextLine();
		
		System.out.println("entrer votre port ");
		String port=sc.nextLine();
		
		ServerThread serverThread = new ServerThread(Integer.valueOf(port));	
		serverThread.start();
	
		BufferedReader b =new BufferedReader(new InputStreamReader(System.in));
		/*****************************/
		Wallet k = new Wallet("myWallet.ser");
		
		Limits limits=new Limits(10, 500);
		Date_p d=new Date_p("14/12/2018", "16/12/2018", "15/12/2018");
		Payload payload=new Payload("zak", "TEST TEST", d, "Marseille", limits);
		
		Serialiser serialiser=new Serialiser(k.getPublicKey(), "CREATION", payload);
		
		Transaction t1=new Transaction( serialiser,k.getPublicKey());
		
		
		String s1=t1.sendjson(k.getPrivateKey());
		System.out.println("Validation de la transaction T1: "+t1.verifiersignature());
		/****************************************/
		
		
		
		Payload payload2=new Payload("zak22", "TEST TEST 2", d, "Paris", limits);
		Serialiser serialiser2=new Serialiser(k.getPublicKey(), "CREATION", payload2);
		
		Transaction t2=new Transaction( serialiser2,k.getPublicKey());	
		
		String s2=t1.sendjson(k.getPrivateKey());
		
		
		Block block_g=new Block(true);
		Block block= new Block();
		
		block.addTransaction(t1);
		block.addTransaction(t2);
		
		Blockchain blockchain = new Blockchain();
		blockchain.addBlock(block_g);
		blockchain.addBlock(block);
		
		
		
		new Peer().updateListenToPeer(b,usern, serverThread, s1);

	}
	
	public void updateListenToPeer(BufferedReader b, String username, ServerThread serverThread,String s) throws IOException {
		
		
		System.out.println("entrer ip:port ip:port ip:port ... de vos correspondants");
		
		String input =b.readLine();
		String[]inputvalue=input.split(" ");	
		
		
		for(int i=0;i<inputvalue.length;i++) {
			String[] address=inputvalue[i].split(":");
			Socket socket = null;
			try {	
				
				socket = new Socket(address[0],Integer.valueOf(address[1]));
				new PeerThread(socket).start();
				
			}catch(Exception e) {
				if(socket!=null) socket.close();
				else System.out.println("erreur");
			}
		}
			
		communicate(b, username, serverThread,s);
	}
	
	
	
	public void communicate(BufferedReader b, String username, ServerThread serverThread,String s) {
		
		try {
			System.out.println("Vous pouvez maintenant comuniquer (e to exit or c to change) ");
			boolean flag=true;
			
			while(flag) {						
				serverThread.sendmessage(s);						
			}			
		System.exit(0);	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
