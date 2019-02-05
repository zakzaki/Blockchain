package Architecture;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;

import p2p.Wallet;

public class Main {
	
	public static byte[] read(String path) {
		File file;
        file = new File(path);

        byte[] fileContent = null;
        try {
            fileContent = Files.readAllBytes(file.toPath());
        } catch (IOException e) {e.printStackTrace();}

        return fileContent;
	}
	
	/*public static PrivateKey getPrivKeyFromCurve(byte[] privKey) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256r1");
        KeyFactory kf = KeyFactory.getInstance("ECDSA", new BouncyCastleProvider());
        ECNamedCurveSpec params = new ECNamedCurveSpec("secp256r1", spec.getCurve(), spec.getG(), spec.getN());
        ECPrivateKeySpec priKey = new ECPrivateKeySpec(new BigInteger(privKey), params);
        return kf.generatePrivate(priKey);
    }*/

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, SignatureException, IOException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {	
		
		byte [] d0=read("data");
		
		System.out.println(HashUtil.bytesToHex(d0));
		
	/*			
		     // ARBRE DE MERKLE EXEMPLE  
		ArrayList<String> donne=new ArrayList<>();
		String root="";
		byte [] d0=read("data_00");
		byte [] d1=read("data_01");
		byte [] d2=read("data_02");
		byte [] d3=read("data_03");
		byte [] d4=read("data_04");
		byte [] d5=read("data_05");
		byte [] d6=read("data_06");
		byte [] d7=read("data_07");
		byte [] d8=read("data_08");
		byte [] d9=read("data_09");
		byte [] d10=read("data_10");
		byte [] d11=read("data_11");
		byte [] d12=read("data_12");
		byte [] d13=read("data_13");
		byte [] d14=read("data_14");
		
		donne.add(new String (d0));
		donne.add(new String (d1));
		donne.add(new String (d2));
		donne.add(new String (d3));
	    donne.add(new String (d4));
		donne.add(new String (d5));
		donne.add(new String (d6));
		donne.add(new String (d7));
		donne.add(new String (d8));
		donne.add(new String (d9));
		donne.add(new String (d10));
		donne.add(new String (d11));
		donne.add(new String (d12));
		donne.add(new String (d13));
		donne.add(new String (d14));
	    System.out.println(arbre_merkel.arbre2(donne));	  */ 	    
		
	/*	Wallet w1=new Wallet();
		Wallet w2=new Wallet();
		Wallet k = new Wallet("myWallet.ser");
		
		Limits limits=new Limits(10, 500);
		Date_p d=new Date_p("14/12/2018", "16/12/2018", "15/12/2018");
		Payload payload=new Payload("zak", "TEST TEST", d, "Marseille", limits);
		Payload payload2=new Payload("zak22", "TEST TEST 2", d, "Paris", limits);
		Serialiser serialiser=new Serialiser(k.getPublicKey(), "CREATION", payload);
		Serialiser serialiser2=new Serialiser(w2.getPublicKey(), "CREATION", payload2);
		

		Transaction t1=new Transaction( serialiser,k.getPublicKey());
		Transaction t2=new Transaction( serialiser2,w2.getPublicKey());		
				
		t2.signTransaction(w2.getPrivateKey());
							
		
		ArrayList<Transaction>t=new ArrayList<>();
		t.add(t1);
		t.add(t2);
		
		Block b=new Block(w1.getPublicKey(), t,"test");
		
		
		b.mineBlock(4); // on mine un block avec une difficulté de 4 pour aller plus vite
		*/

	}

}
