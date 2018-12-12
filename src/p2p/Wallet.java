package p2p;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.*;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;



public class Wallet implements Serializable {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private SecurityManager securityManager;

    //Creation d'un compte utilisateur
    public Wallet() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException{
        KeyPair pair = generateKeys();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
    }

    public Wallet(String path){

        Wallet serializedWallet =null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            serializedWallet = (Wallet) in.readObject();
            in.close();

        }catch(Exception e) {e.printStackTrace();}

        privateKey = serializedWallet.getPrivateKey();
        publicKey = serializedWallet.getPublicKey();
    }
    
    
    public static KeyPair generateKeys() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
        KeyPairGenerator g = (KeyPairGenerator) KeyPairGenerator.getInstance("ECDSA", new BouncyCastleProvider());
        g.initialize(ecSpec, new SecureRandom());
        KeyPair pair = g.generateKeyPair();
        return pair;
    }
	
    public static void sign(String t) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
    	KeyPair pair = generateKeys();
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());
        ecdsaSign.initSign(pair.getPrivate());
        ecdsaSign.update(t.getBytes("UTF-8"));
        byte[] signature = ecdsaSign.sign();
        System.out.println("Message: " + t);
        System.out.println("Signature: " + DatatypeConverter.printHexBinary(signature).toLowerCase());
        System.out.println("Public Key: " + DatatypeConverter.printHexBinary(pair.getPublic().getEncoded()).toLowerCase());
    }
    
    public static void verify(String t, byte[] signature, KeyPair pair) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
    	Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());
        ecdsaVerify.initVerify(pair.getPublic());
        ecdsaVerify.update(t.getBytes("UTF-8"));
        System.out.println("Valid? " + ecdsaVerify.verify(signature));
    }
    
    
    

    // generation de la pair de clés rsa ( publique est privée) du compte utilisateur
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048, new SecureRandom());
            KeyPair keyPair = generator.generateKeyPair();
            return keyPair;
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }



}