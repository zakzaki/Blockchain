package Architecture;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Block implements Serializable , Sendable{

	
	public String currentHash;
  //  public String previousHash;
    private ArrayList<Transaction> transactions = new ArrayList<>();
  //  private String root_hash;
    private PublicKey pub_key;
    private String creatorSignature;
  //  private int level;
  //  private long time;   
  //  private int nonce;
    
    private Block_serialiser serialiser=new Block_serialiser();
    
    
    public Block(boolean genesis) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if(genesis){
            
            this.serialiser.setLevel(0);
            this.serialiser.setTime((System.currentTimeMillis()/1000)); 
            this.serialiser.setHash_prev_block(HashUtil.hmac("genesis", "0"));
			this.currentHash = HashUtil.hmac("genesis", "0") ;
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
    


    public Block(PublicKey pub_key, ArrayList<Transaction> transactions, String previousHash) {

        this.transactions = transactions;
        this.serialiser.setRoot_hash(arbre_merkel.arbre(transactions));
        this.serialiser.setHash_prev_block(previousHash);
        this.pub_key = pub_key;
        this.currentHash = this.calculateHash();
        
        this.serialiser.setTransactions(getTransactionJson());
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
		s=HashUtil.hmac(s, "0");
		this.currentHash=s;
		this.creatorSignature =  HashUtil.signECDSA(s.getBytes(), key);
        
        return creatorSignature;
    }
    
    public boolean verifierSignature(String s) throws SignatureException, NoSuchAlgorithmException {
       
        if(this.getCreatorSignature() != null){

            String currentHash = HashUtil.hmac(receivejson(s), "0");
			this.currentHash = currentHash;
			
            if(HashUtil.verifyECDSASignature(this.currentHash.getBytes(), this.getCreatorSignature().getBytes(), this.getPub_key())){
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
    	
    	String signature=s.substring(taille+32);
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
    	
    	return taille+json_binary+creatorSignature;
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
	}


	public String getCreatorSignature() {
		return creatorSignature;
	}


	public void setCreatorSignature(String creatorSignature) {
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
    public String calculateHash() {
        return HashUtil.applySha256( serialiser.getHash_prev_block() + transactions + pub_key);

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
}
