package Architecture;

public class Consensus {

	public static boolean verifierTransaction(Transaction t) {	
			
		boolean b=false;
		
		try {
			
			if(!t.verifiersignature()) {
				System.out.println("Signature incorrect");
			}else {
				if(t.getSerialiser().getType_transaction().equals("CREATION")) {
					if(t.isValid()) b=true;
					return b;
				}else if(t.getSerialiser().getType_transaction().equals("INSCRIPTION")) {
					b=true;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	
	public static boolean verifierBlock(Block block, Blockchain blockchain, String root) {
		
		boolean b=false;
		
		try {
			 if(block.verifierSignature() && verifierHashPrecedent(block, blockchain) && blockchain.checkLevel(block)
	                     && verifierBlockTime(block) && verifierTransacDunBloc(block))    b = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	 public static boolean verifierTransacDunBloc(Block block){
	        if(block.isGenesis()){
	            return true;
	        }else{
	            boolean valid = false;
	            int i = 0;
	            while (!valid && i<block.getTransactions().size()){
	                if(!verifierTransaction(block.getTransactions().get(i))){
	                    valid = true;
	                }
	                i++;
	            }
	            return !valid;
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
	
	 /*  public static Boolean verifierLevelPrecedent(Block block, Blockchain blockchain){

	        if(block.getSerialiser().getLevel() == blockchain.getLatestBlock().getSerialiser().getLevel()+1 ){
	            return true;
	        }else{
	            System.out.println("Pervious level is not valid");
	            return false;
	        }

	    }*/
	   
	   public static Boolean verifierBlockTime(Block block)
	    {
	        if(block.getSerialiser().getTime() < System.currentTimeMillis() + 2){
	            return true;
	        }else{
	            System.out.println("block time is not valid ");
	            return false;
	        }
	    }
}
