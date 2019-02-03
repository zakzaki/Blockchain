package Architecture;

import java.util.ArrayList;

public class Block_serialiser {

	private String pub_key;
	private String hash_prev_block;
	private String root_hash;
	private int level;
	private long time;  
	private ArrayList<Serialiser> transactions = new ArrayList<>();
	private int nonce;
	
	
	public Block_serialiser(String pub_key, String hash_prev_block, String root_hash, int level,
			ArrayList<Serialiser> transactions, int nonce) {
		super();
		this.pub_key = pub_key;
		this.hash_prev_block = hash_prev_block;
		this.root_hash = root_hash;
		this.level = level;
		this.time = (System.currentTimeMillis()/1000);
		this.transactions = transactions;
		this.nonce = nonce;
	}
	
	
	public Block_serialiser() {
		this.time = (System.currentTimeMillis()/1000);
	}
	
	


	/********************  GETTETS AND SETTERS ******************/
	public String getPub_key() {
		return pub_key;
	}
	public void setPub_key(String pub_key) {
		this.pub_key = pub_key;
	}
	public String getHash_prev_block() {
		return hash_prev_block;
	}
	public void setHash_prev_block(String hash_prev_block) {
		this.hash_prev_block = hash_prev_block;
	}
	public String getRoot_hash() {
		return root_hash;
	}
	public void setRoot_hash(String root_hash) {
		this.root_hash = root_hash;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public ArrayList<Serialiser> getTransactions() {
		return transactions;
	}
	public void setTransactions(ArrayList<Serialiser> transactions) {
		this.transactions = transactions;
	}
	public int getNonce() {
		return nonce;
	}
	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	
	
	
	
}
