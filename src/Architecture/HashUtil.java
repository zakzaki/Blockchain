package Architecture;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HashUtil {

    public static String applySha256(String input){

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            //Applies sha256 to our input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));

            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static String bytesToHex(byte[] bytes) {
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

}
