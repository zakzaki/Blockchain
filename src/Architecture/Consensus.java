package Architecture;

public class Consensus {

	public static boolean verifierTransaction(Transaction t,String s) {	
			
		boolean b=false;
		
		try {
			
			if(!t.verifiersignature(s)) {
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
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
