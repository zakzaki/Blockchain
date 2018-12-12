package Architecture;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Block implements Serializable , Sendable{

	
	public String currentHash;
    public String previousHash;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private String root;
    private PublicKey creatorIdentity;
    private String creatorSignature;
    
    private long timeStamp;
    
    public Block(String previousHash , String currentHash) { 
        this.previousHash = previousHash;
      
        this.currentHash = HashUtil.applySha256(currentHash);
    }


    public Block(PublicKey creatorIdentity, ArrayList<Transaction> transactions, String previousHash) {

        this.transactions = transactions;
        this.root=arbre_merkel.arbre(transactions);
        this.previousHash = previousHash;     
        this.creatorIdentity = creatorIdentity;
        this.currentHash = this.calculateHash();
    }
    
    public void sign(PrivateKey key){
        try {
			this.creatorSignature= HashUtil.hmac(this.getCurrentHash(),key.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    public String getCurrentHash() {
        return currentHash;
    }



    public void setCurrentHash(String currentHash) {
        this.currentHash = currentHash;
    }



    public String getPreviousHash() {
        return previousHash;
    }



    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }


    
    public void addTransaction(Transaction transation)
    {
        transactions.add(transation);
    }

    //Calculer le Haché du bloc
    public String calculateHash() {
        return HashUtil.applySha256( previousHash + transactions + creatorIdentity);

    }
    
    public boolean isGenesis() {
        return getCurrentHash().equals(HashUtil.applySha256("genesis"));
    }
    
    public long getTimeStamp() {
        return timeStamp;
    }


    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block block = (Block) o;

        if (currentHash != null ? !currentHash.equals(block.currentHash) : block.currentHash != null) return false;
        return creatorSignature != null ? creatorSignature.equals(block.creatorSignature) : block.creatorSignature == null;
    }
    
    @Override
    public int hashCode() {
        int result = currentHash != null ? currentHash.hashCode() : 0;
        result = 31 * result + (creatorSignature != null ? creatorSignature.hashCode() : 0);
        return result;
    }


}
