package Architecture;

import java.security.PublicKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serialiser {
	
	 	private String  pub_key;
		private String type_transaction; //creation, REGISTER
		private Payload payload;
	
	
	public Serialiser(PublicKey pub_key, String type_transaction, Payload payload) {
	
		super();
		this.pub_key = HashUtil.bytesToHex(pub_key.getEncoded()) ;
		this.type_transaction = type_transaction;
		this.payload = payload;
	}
	

	public String getPub_key() {
		return pub_key;
	}

	public void setPub_key(PublicKey pub_key) {
		this.pub_key = HashUtil.bytesToHex(pub_key.getEncoded());
	}

	public String getType_transaction() {
		return type_transaction;
	}

	public void setType_transaction(String type_transaction) {
		this.type_transaction = type_transaction;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
public void transform() throws JsonProcessingException {
		
		
    	ObjectMapper mapper = new ObjectMapper();
    	String json1 = mapper.writeValueAsString(this);
	//	String json2 = mapper.writeValueAsString(this.getPayload());
		//String json3 = mapper.writeValueAsString(this.getPub_key());
		System.out.println(json1);
		//System.out.println(json3);
	}
	 
	
}
