package Architecture;

import java.io.Serializable;
import java.security.PublicKey;




public class Serialiser implements Serializable, Sendable {
	
	 	private String  pub_key;
		private String type_transaction; //creation, REGISTER
		private Payload payload;
		
	
	
	public Serialiser(PublicKey pub_key, String type_transaction, Payload payload) {
	
		super();
		this.pub_key = HashUtil.bytesToHex(pub_key.getEncoded()) ;
		this.type_transaction = type_transaction;
		this.payload = payload;
	}
	
	
	public Serialiser(String pub_key, String type_transaction, String name, String description, String begin, String end, String end_souscription, String location, int min, int max, String event_hash) {
		
		super();
		this.pub_key = pub_key;
		this.type_transaction = type_transaction;
		
		if(type_transaction.equals("CREATION")) {
			Date_p d=new Date_p(begin, end, end_souscription);
			Limits l=new Limits(min, max);
			this.payload = new Payload(name, description, d, location, l);
		}else {
			this.payload=new Payload(event_hash);
		}	
		
	}
	
	public Serialiser() {
		
	}
	
	

	public String getPub_key() {
		return pub_key;
	}

	public void setPub_key(String pub_key) {
		this.pub_key = pub_key;
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
	
}
