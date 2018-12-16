package Architecture;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Transaction implements Serializable, Sendable{

	 private String senderSignature;
	 private String souscriptionHash;
	 private Serialiser serialiser;
	  
	 
	    public Transaction(String senderSignature, String souscriptionHash, Serialiser serialiser) {
		
	    	super();
		this.senderSignature = senderSignature;
		this.souscriptionHash = souscriptionHash;
		this.serialiser = serialiser;
	}
	    
	    

		public String getSenderSignature() {
	        return senderSignature;
	    }

	    public void setSenderSignature(String senderSignature) {
	        this.senderSignature = senderSignature;
	    }

	    
	    public String transform() throws JsonProcessingException {
			    	
	    	ObjectMapper mapper = new ObjectMapper();
			String json1 = mapper.writeValueAsString(serialiser);
			return json1;
	    	
		}


		 @Override
		    public String toString() {

		        String res = null;
		        if(serialiser.getType_transaction().equals("CREATION")){

		            res =   "Transaction{\n" +
		                    "   pub_key='" + serialiser.getPub_key() + "\',\n" +
		                    "   type_transaction='creation'\n" +
		                    "   "+ serialiser.getPayload().toString()+
		                    "\n}";

		        }else if (serialiser.getType_transaction().equals("REGISTER")){
		            res =   "Transaction{" +
		                    "   pub_key='" + serialiser.getPub_key() + "\',\n" +
		                    "   type_transaction='register'\n" +
		                    "   payload{\n" +
		                    "       event_hash='" + souscriptionHash + '\'' + "\n"+
		                    "       }\n"+
		                    "\n}";
		        }
		        return  res;
		    }
 
}
