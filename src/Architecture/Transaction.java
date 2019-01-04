package Architecture;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Transaction implements Serializable, Sendable{

	 private String senderSignature;
	 private String souscriptionHash;
	 private Serialiser serialiser;
	 private String hash;
	 private PublicKey pub_key;
	  
	 public Transaction() {
		 
	 }
	 
	    public Transaction( String souscriptionHash, Serialiser serialiser, PublicKey pub_key) {
		
	    	super();
		this.souscriptionHash = souscriptionHash;
		this.serialiser = serialiser;
		this.pub_key=pub_key;
	}
	    
	    
	    public String transform() throws JsonProcessingException {
			    	
	    	ObjectMapper mapper = new ObjectMapper();
			String json1 = mapper.writeValueAsString(serialiser);
			return json1;
	    	
		}
	    
	    public boolean isValid() {
	    		    	
	    	if(this.getSerialiser().getPayload().getLimits().getMax() < this.getSerialiser().getPayload().getLimits().getMin()) 
	    	{
			System.out.println("la limite est fausse");
			return false;
	    	}
	    	
	        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        String cur_date = format.format(new java.util.Date());

	        java.util.Date begin_date = null;
	        java.util.Date end_date = null;
	        java.util.Date end_souscription_date =  null;
	        java.util.Date current_date =  null;

	        try {
	             begin_date = format.parse(this.serialiser.getPayload().getDate().getBegin());
	             end_date = format.parse(this.serialiser.getPayload().getDate().getEnd());
	             end_souscription_date = format.parse(this.serialiser.getPayload().getDate().getEnd_souscription());
	             current_date = format.parse(cur_date);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        if (begin_date.compareTo(end_date) <= 0 && current_date.compareTo(end_date) < 0
	                && end_souscription_date.compareTo(begin_date) < 0 )
	        {
	            return true;
	        }else{
	        	System.out.println("la date est fausse");
	            return false;
	        }
	    }
	    
	    public String signTransaction(PrivateKey privateKey) throws SignatureException, NoSuchAlgorithmException {
	       
			try {
				String s =transform();
				s=HashUtil.hmac(s, "0");
				this.senderSignature =  HashUtil.signECDSA(s.getBytes(), privateKey);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}		 
			
	        return this.senderSignature;
	    }
	    
	    public boolean verifiersignature(String s) throws NoSuchAlgorithmException, JsonProcessingException, SignatureException {
	    	
	    	String eventHash = null;
	        if(serialiser.getType_transaction().equals("CREATION")){
	          	          
	        	String s1=receivejson(s);
	        	
	        	eventHash= HashUtil.hmac(s1, "0");	  
	          
	        }else if(serialiser.getType_transaction().equals("INSCRIPTION")){
	            eventHash = getHash();
	        }
	        
	        String signa=receivesignature(s);
	        
	        return HashUtil.verifyECDSASignature(eventHash.getBytes(), signa.getBytes(), pub_key);
	        
	    }
	    
	    public static String receivesignature(String s) {
	    	
	    	
	    	String t=s.substring(0, 32);	    		    	
	    	int taille=getTaille(t);
	    	
	    	String signature=s.substring(taille+32);
	    	return signature;
	    }
	    
	    
	    public static String receivejson(String s) {
	  
	    	String t=s.substring(0, 32);	    	
	    	
	    	
	    	int taille=getTaille(t);
	    	
	    	String json=s.substring(32,taille+32);	    		    	
	    	    
	    	if(taille!=json.length()) {
	    		System.out.println("ERREUR DANS LA TAILLE");
	    		return null;
	    	}
	    	json=converttostring(json);
	    	return json;
	    }	      
	    
	    
	    public String sendjson(PrivateKey privateKey) throws UnsupportedEncodingException, JsonProcessingException, NoSuchAlgorithmException {
	    	
	    	String data=transform();
	    	String json_binary=stringToBinary(data);
	    	String taille=taille(json_binary);
	    	try {
				signTransaction(privateKey);
			} catch (SignatureException e) {
				e.printStackTrace();
			}
	    	return taille+json_binary+senderSignature;
	    }
	    	    
	    
	    public String stringToBinary(String data) throws JsonProcessingException{

	        byte[] bytes = data.getBytes();
	        StringBuilder binary = new StringBuilder();
	        for (byte b : bytes)
	        {
	            int val = b;
	            for (int i = 0; i < 8; i++)
	            {
	                binary.append((val & 128) == 0 ? 0 : 1);
	                val <<= 1;
	            }	          
	        }
	        return binary.toString();
	    }
	    
	    public static String converttostring(String b) {
	    		    	
	    	byte[] bval = new BigInteger(b, 2).toByteArray();
	    //	byte[]bval= Integer.parseInt(b);  
	    	
	    	String str="";
	    	try {	    		
				str = new String(bval, "UTF-8");
			} catch (UnsupportedEncodingException e) {				
				e.printStackTrace();
			}
	    	
	    	 return str;
	    }
	    
	    public static String taille(String s) {
	    	
	    	int taille=s.length();
	    	String r=Integer.toBinaryString(taille);
	    	if(r.length()<32) {
	    		int bourrer=32-r.length();
	    		for(int i=0;i<bourrer;i++) r="0"+r;
	    	}
	    	return r;
	    }
	    
	    
	    public static int getTaille(String s) {
	    	int t = Integer.parseInt(s, 2);
	    	return t;
	    }
	    
		 public Serialiser getSerialiser() {
			return serialiser;
		}

		public void setSerialiser(Serialiser serialiser) {
			this.serialiser = serialiser;
		}

		public String getHash() {
			return hash;
		}

		public void setHash(String hash) {
			this.hash = hash;
		}
		
		public String getSenderSignature() {
	        return senderSignature;
	    }

	    public void setSenderSignature(String senderSignature) {
	        this.senderSignature = senderSignature;
	    }
	    

		public PublicKey getPub_key() {
			return pub_key;
		}

		public void setPub_key(PublicKey pub_key) {
			this.pub_key = pub_key;
		}
 
}
