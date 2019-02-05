package Architecture;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Block implements Serializable , Sendable{

	
	public String currentHash;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private PublicKey pub_key;
    private byte[] creatorSignature;
    
    private Block_serialiser serialiser=new Block_serialiser();
    
    
    public Block(boolean genesis) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if(genesis){
            
            this.serialiser.setLevel(0);
            this.serialiser.setTime((System.currentTimeMillis()/1000)); 
            this.serialiser.setHash_prev_block(HashUtil.applySha256("genesis"));
			this.currentHash = HashUtil.applySha256("genesis") ;
        }
    }
    
    public Block(int level, long time) { 
    	this.serialiser.setLevel(level);
    	this.serialiser.setTime(time);
    }
    
    public Block(String previousHash , String currentHash) { 
    	this.serialiser.setHash_prev_block(previousHash);
      
        this.currentHash = HashUtil.applySha256(currentHash);
    }
    

    public Block(PublicKey pub_key, ArrayList<Transaction> transactions, String previousHash) throws NoSuchAlgorithmException, JsonProcessingException {

        this.transactions = transactions;
        this.serialiser.setRoot_hash(arbre_merkel.arbre(transactions));
        this.serialiser.setHash_prev_block(previousHash);
        this.pub_key = pub_key;
        this.currentHash = this.calculateHash();
        
        this.serialiser.setPub_key(HashUtil.bytesToHex(pub_key.getEncoded()));
        this.serialiser.setTransactions(getTransactionJson());
    }
    
    public Block() {
	
	}

	public static Block creer(String msg) throws JsonParseException, JsonMappingException, IOException {
    	String s=receivejson(msg);
    	
    	Block b=new Block();
    	ObjectMapper objectMapper = new ObjectMapper();
    	Block_serialiser ser= objectMapper.readValue(s,Block_serialiser.class); 
 	    b.setSerialiser(ser);
 	    b.setPub_key(HashUtil.createPublicEncryptionKey(ser.getPub_key()));
 	    
 	   for(int i=0;i<ser.getTransactions().size();i++) {
 		   
 		   Transaction t= new Transaction();
 		   t.setSerialiser(ser.getTransactions().get(i));
 		  t.setPub_key(HashUtil.createPublicEncryptionKey(ser.getTransactions().get(i).getPub_key()));
 		   
 		   b.getTransactions().set(i, t);
 	   }
 	    
    	return b;
    }
	
    
    public String transform() throws JsonProcessingException {
    	
    	ObjectMapper mapper = new ObjectMapper();
		String json1 = mapper.writeValueAsString(serialiser);
		return json1;
    	
	}  
    
    public ArrayList<Serialiser>getTransactionJson(){
    	ArrayList<Serialiser> l=new ArrayList<>();
    	 	
			for(int i=0;i<transactions.size();i++)
			l.add(transactions.get(i).getSerialiser());
    	
    	return l;
    }
    
    public String signBlock(PrivateKey key) throws JsonProcessingException, SignatureException, NoSuchAlgorithmException{
            
        String s =transform();
		s=HashUtil.applySha256(s);
		this.currentHash=s;
		this.creatorSignature =  HashUtil.signECDSA(s.getBytes(), key);
        
        return new String (creatorSignature);
    }
    
    public boolean verifierSignature() throws SignatureException, NoSuchAlgorithmException, JsonProcessingException {
       
        if(this.getCreatorSignature() != null){

          //  String currentHash = HashUtil.hmac(receivejson(s), "0");
        	String currentHash = HashUtil.applySha256(transform());
			this.currentHash = currentHash;
			
            if(HashUtil.verifierSignature(this.currentHash.getBytes(), this.getCreatorSignature(), this.getPub_key())){
                return true;
            }
            else{
                System.out.println("signature fausse ");
                return false;
            }

        }else{
            System.out.println("le block n'est pas signé ");
            return false;
        }
    }
    
    
    public static String receivesignature(String s) {
    	
    	
    	String t=s.substring(0, 32);	    		    	
    	int taille=getTaille(t);
    	
    	String signature_binaire=s.substring(taille+32);
    	String signature=converttostring(signature_binaire);
    	return signature;
    }
    
    public String sendjson(PrivateKey privateKey) throws UnsupportedEncodingException, JsonProcessingException, NoSuchAlgorithmException {
    	
    	String data=transform();
    	String json_binary=stringToBinary(data);
    	String taille=taille(json_binary);
    	try {
			signBlock(privateKey);
		} catch (SignatureException e) {
			e.printStackTrace();
		}
    	String signature_binaire=stringToBinary( new String(creatorSignature));
    	
    	return taille+json_binary+signature_binaire;
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
    

    public String getCurrentHash() {
        return currentHash;
    }



    public void setCurrentHash(String currentHash) {
        this.currentHash = currentHash;
    }


	public PublicKey getPub_key() {
		return pub_key;
	}


	public void setPub_key(PublicKey pub_key) {
		this.pub_key = pub_key;
		this.serialiser.setPub_key(HashUtil.bytesToHex(pub_key.getEncoded()));
		
	}


	public byte[] getCreatorSignature() {
		return creatorSignature;
	}


	public void setCreatorSignature(byte[] creatorSignature) {
		this.creatorSignature = creatorSignature;
	}


	public void addTransaction(Transaction transation)
    {
        transactions.add(transation);
        serialiser.getTransactions().add(transation.getSerialiser());
    }
	
	

    public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
		this.serialiser.setTransactions(getTransactionJson());
		
	}

	public Block_serialiser getSerialiser() {
		return serialiser;
	}

	public void setSerialiser(Block_serialiser serialiser) {
		this.serialiser = serialiser;
	}

	//Calculer le Haché du bloc
    public String calculateHash() throws NoSuchAlgorithmException, JsonProcessingException {
      //  return HashUtil.applySha256( serialiser.getHash_prev_block() + transactions + pub_key);
        
        String calculatedhash= HashUtil.applySha256(this.transform());
        return calculatedhash;

    }
    
    public boolean isGenesis() {
    	
    	 if (serialiser.getLevel()== 0){
             return true;
         }else{

             return  false;
         }
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block block = (Block) o;

        if (currentHash != null ? !currentHash.equals(block.currentHash) : block.currentHash != null) return false;
        return creatorSignature != null ? creatorSignature.equals(block.creatorSignature) : block.creatorSignature == null;
    }
    
    
    
    public void mineBlock(int d) throws NoSuchAlgorithmException, JsonProcessingException {
        
        String t= new String(new char[d]).replace('\0', '0');
        if( this.currentHash == null)
        {
            this.currentHash = calculateHash();
        }
        while(!new String(this.currentHash).substring( 0, d).equals(t)) {
            this.serialiser.setNonce(this.serialiser.getNonce()+1);
            this.currentHash = calculateHash();            
        }
        System.out.println("  Block miné avec succès, le nonce = " + this.serialiser.getNonce() );
    }
    
}
