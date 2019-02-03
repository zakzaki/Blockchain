package Architecture;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class arbre_merkel {
	
	
  /* public static String bytesToHex(byte[] bytes) {
       StringBuffer result = new StringBuffer();
       for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
       return result.toString();
   }
	
	static String hmac(String input ,String secret ) throws NoSuchAlgorithmException {
		
		
		Mac sha256_HMAC = null;
	    String result = input;
	    String key =  secret;
		
		
		try{
	        byte [] byteKey = key.getBytes("UTF-8");
	        final String HMAC_SHA256 = "HmacSHA256";
	        sha256_HMAC = Mac.getInstance(HMAC_SHA256);      
	        SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA256);
	        sha256_HMAC.init(keySpec);
	        byte [] mac_data = sha256_HMAC.
	         doFinal(input.getBytes("UTF-8"));
	        //result = Base64.encode(mac_data);
	        result = bytesToHex(mac_data);
	       // System.out.println(result);
	    } catch (UnsupportedEncodingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (NoSuchAlgorithmException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (InvalidKeyException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		return result;
		
	}
	
	static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }*/
	
	
	static String arbre(ArrayList<Transaction> donne2) {
			
		ArrayList<String> res_final=new ArrayList<>();

		if(donne2.size()==0)
			try {
				return HashUtil.hmac("".getBytes(), "\002".getBytes());
			} catch (NoSuchAlgorithmException e1) {			
				e1.printStackTrace();
			}	
		
		ArrayList<String> donne=new ArrayList<>();
		for(int i=0;i<donne2.size();i++) {
			try {
				donne.add(HashUtil.hmac( (donne2.get(i).toString()).getBytes(),"\000".getBytes() ));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
				
		if(donne.size()==1) return donne.get(0);
		
							
			res_final.addAll(donne);
						
			while(res_final.size()!=1) {					
							
			ArrayList<String> res=new ArrayList<>();
					
			int i=0;
			while(i+1<res_final.size()) {
				
				try {
					byte[] hash1=HashUtil.hexStringToByteArray(res_final.get(i));
					byte[] hash2=HashUtil.hexStringToByteArray(res_final.get(i+1));
					
					res.add(HashUtil.hmac( HashUtil.concatenate(hash1, hash2),"\001".getBytes() ));
					i=i+2;
					
				} catch (NoSuchAlgorithmException e) {				
					e.printStackTrace();
				}				
			}
			if(i==res_final.size()-1) res.add(res_final.get(i));
			
			res_final.clear();
			res_final.addAll(res);
			
			}

			
		return res_final.get(0);
		
	}
	
	
	/*****************************************/
	
static String arbre2(ArrayList<String> donne2) {
		
		ArrayList<String> res_final=new ArrayList<>();		

		if(donne2.size()==0) try {
			return HashUtil.hmac("".getBytes(),"\002".getBytes());
		} catch (NoSuchAlgorithmException e1) {			
			e1.printStackTrace();
		}		
		
		ArrayList<String> donne=new ArrayList<>();
		for(int i=0;i<donne2.size();i++) {
			try {
				donne.add(HashUtil.hmac(donne2.get(i).getBytes(),"\000".getBytes() ));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
			
		if(donne.size()==1) return donne.get(0);		
							
			res_final.addAll(donne);
						
			while(res_final.size()!=1) {					
							
			ArrayList<String> res=new ArrayList<>();
						
			int i=0;
			while(i+1<res_final.size()) {
				
				try {
					byte[] hash1=HashUtil.hexStringToByteArray(res_final.get(i));
					byte[] hash2=HashUtil.hexStringToByteArray(res_final.get(i+1));
					
					res.add(HashUtil.hmac( HashUtil.concatenate(hash1, hash2),"\001".getBytes() ));
					
					i=i+2;
				} catch (NoSuchAlgorithmException e) {				
					e.printStackTrace();
				}
				
			}
			if(i==res_final.size()-1)  res.add(res_final.get(i));
			
			
			res_final.clear();
			res_final.addAll(res);
			
			}
										
		return res_final.get(0);		
	}
	
	/****************************************/
	
	static boolean check(String root, ArrayList<Transaction>donne) {
		
			String r=arbre(donne);
			
			if(r.equals(root)) return true;
	
		    return false;
	
	}

	

}
