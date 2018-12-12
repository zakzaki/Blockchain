package Architecture;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.Date;

public class Transaction implements Serializable, Sendable{

	 private String senderIdentity;
	 private PublicKey  senderAddress;
	 private String senderSignature;
	 private long timestamp;
	 private String type; //creation, annulation, inscription
	 private String id_type; //id de la creation/annulation/inscription de la transaction
	 
	 
	 public Transaction(PublicKey senderAddress ) {
	        this.senderAddress = senderAddress;
	        this.timestamp = new Date().getTime();
	    }
	 
	 public Transaction(PublicKey senderAddress, String type, String id_type ) {
	        this.senderAddress = senderAddress;
	        this.timestamp = new Date().getTime();
	        this.type=type;
	        this.id_type=id_type;
	    }
	 
	 public String getSenderIdentity() {
	        return senderIdentity;
	    }

	    public void setSenderIdentity(String senderIdentity) {
	        this.senderIdentity = senderIdentity;
	    }

	    public String getSenderSignature() {
	        return senderSignature;
	    }

	    public void setSenderSignature(String senderSignature) {
	        this.senderSignature = senderSignature;
	    }

	    public PublicKey getSenderAddress() {
	        return senderAddress;
	    }

	    public void setSenderAddress(PublicKey senderAddress) {
	        this.senderAddress = senderAddress;
	    }


	    public long getTimestamp() {
	        return timestamp;
	    }

	    public void setTimestamp(long timestamp) {
	        this.timestamp = timestamp;
	    }


	 

}
