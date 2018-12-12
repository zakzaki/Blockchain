package p2p;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.*;


public class Wallet implements Serializable {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private SecurityManager securityManager;

    //Creation d'un compte utilisateur
    public Wallet(){
        KeyPair pair = generateKeyPair();
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