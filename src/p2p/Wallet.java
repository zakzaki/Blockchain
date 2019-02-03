package p2p;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;



public class Wallet implements Serializable{

    private PrivateKey privateKey;
    private PublicKey publicKey;


    private SecurityManager securityManager;


    /******************************************* Constructor **********************************************************/
   
    public Wallet(){
        KeyPair pair = generateECDSAKeyPair();

        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

  
    public Wallet(String path){

        Wallet serializedWallet =null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
          
            serializedWallet = (Wallet) in.readObject();
            in.close();

        }catch(Exception e) {  e.printStackTrace();}

        privateKey = (ECPrivateKey) serializedWallet.getPrivateKey();
        publicKey = (ECPublicKey) serializedWallet.getPublicKey();
    }
    

    /********************************************* Getters and setters ************************************************/
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(ECPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(ECPublicKey publicKey) {
        this.publicKey = publicKey;
    }


    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    /****************************************** Other methods *********************************************************/

    
    public KeyPair generateRSAKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048, new SecureRandom());
            KeyPair keyPair = generator.generateKeyPair();
            return keyPair;
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public KeyPair generateECDSAKeyPair(){

        KeyPair pair = null;
        ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
        KeyPairGenerator g = null;
        try {
            g = KeyPairGenerator.getInstance("ECDSA", new BouncyCastleProvider());
            g.initialize(ecSpec, new SecureRandom());
            pair = g.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return pair;
    }

    static private String adjustTo64(String s) {
        switch(s.length()) {
            case 62: return "00" + s;
            case 63: return "0" + s;
            case 64: return s;
            default:
                throw new IllegalArgumentException("not a valid key: " + s);
        }
    }

}