package Architecture;

public class Consensus {

	public static boolean verifierTransaction(Transaction t, Blockchain blockchain) {	
			
		boolean b=false;
		
		try {
			
			if(!t.verifiersignature()) {
				System.out.println("Signature incorrect");
			}else {
				if(t.getSerialiser().getType_transaction().equals("CREATION")) {
					if(t.isValid()) b=true;
					return b;
				}else if(t.getSerialiser().getType_transaction().equals("INSCRIPTION")) {
					
					if(exist_transaction(t.getSerialiser().getPayload().getEvent_hash(), blockchain)) b=true;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	public static boolean exist_transaction(String t, Blockchain b) {
		
		boolean bool=false;
		int i=0;
		
		while(!bool && b.getBlocks().size()>i) {
					
			int j=0;
			
			while( !bool && b.getBlock(i).getTransactions().size()>j ) {
				
				Transaction t1=b.getBlock(i).getTransactions().get(j);
				String hash1 = HashUtil.applySha256(t1.toString());
				
				if(hash1.equals(t)) return true;
				
				j++;
			}
			
			i++;
		}
		
		return bool;
	}
	
	public static boolean verifierBlock(Block block, Blockchain blockchain) {
		
		boolean b=false;
		
		try {
			 if(block.verifierSignature() && verifierHashPrecedent(block, blockchain) && blockchain.checkLevel(block)
	                     && verifierBlockTime(block) && verifierTransacDunBloc(block,blockchain))    b = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	 public static boolean verifierTransacDunBloc(Block block, Blockchain blockchain){
	        if(block.isGenesis()){
	            return true;
	        }else{
	            boolean b = true;
	            int i = 0;
	            while (b && i<block.getTransactions().size()){
	                if(!verifierTransaction(block.getTransactions().get(i),blockchain))   b = false;
	                
	                i++;
	            }
	            return b;
	        }

	    }
	
	public static Boolean verifierHashPrecedent(Block block, Blockchain blockchain){

        if (new String (block.getSerialiser().getHash_prev_block()).equals(new String(blockchain.getLatestBlock().getCurrentHash()))){
            return true;
        }else{
            System.out.println("previous hash of the block is not valid ");
            return false;
        }
    }
	
	   
	   public static Boolean verifierBlockTime(Block block)
	    {
	        if(block.getSerialiser().getTime() < System.currentTimeMillis() + 2){
	            return true;
	        }else{
	            System.out.println("Time du block n'est pas valide ");
	            return false;
	        }
	    }
}
