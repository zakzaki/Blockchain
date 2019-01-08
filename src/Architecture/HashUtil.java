package Architecture;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.asymmetric.ec.EC5Util;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECCurve;

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
 	
 	
 	 public static String signECDSA(byte[] data, PrivateKey privateKey) throws SignatureException {

         Signature ecdsaSign = null;
         byte[] signature = null;
         try {
             ecdsaSign = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());
             ecdsaSign.initSign(privateKey);
             ecdsaSign.update(data);
             signature = ecdsaSign.sign();
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
         } catch (InvalidKeyException e) {
             e.printStackTrace();
         }

         return new String(signature);

     }
 	 
 	public static  boolean verifyECDSASignature(byte[] data, byte[] signature, PublicKey publicKey) throws SignatureException {

 		 boolean result = false;
         Signature ecdsaVerify = null;
         try {        	 
             ecdsaVerify = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());
             ecdsaVerify.initVerify(publicKey);
             ecdsaVerify.update(data);            
             result = ecdsaVerify.verify(signature);
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
         } catch (SignatureException e) {
             e.printStackTrace();
         } catch (InvalidKeyException e) {
             e.printStackTrace();
         }

         return result;
    }
 	
    public static String getPublicKeyAsHex(PublicKey publicKey) {
        ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
        ECPoint ecPoint = ecPublicKey.getW();

        byte[] affineXBytes = ecPoint.getAffineX().toByteArray();
        byte[] affineYBytes = ecPoint.getAffineY().toByteArray();

        String hexX = bytesToHex(affineXBytes);
        String hexY = bytesToHex(affineYBytes);

        return hexX+":"+hexY;
    }
    
    
    public static ECPublicKey createPublicEncryptionKey(String hexPubKey) {

        String[] parts = hexPubKey.split(":");
        String xHex = parts[0];
        String yHex = parts[1];

        BigInteger x = new BigInteger(xHex, 16);

        BigInteger y = new BigInteger(yHex, 16);

        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            java.security.spec.ECPoint w = new java.security.spec.ECPoint(x, y);
            ECNamedCurveParameterSpec params = ECNamedCurveTable.getParameterSpec("secp256k1");
            KeyFactory fact = KeyFactory.getInstance("ECDSA", "BC");
            ECCurve curve = params.getCurve();
            java.security.spec.EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, params.getSeed());
            java.security.spec.ECParameterSpec params2 = EC5Util.convertSpec(ellipticCurve, params);
            java.security.spec.ECPublicKeySpec keySpec = new java.security.spec.ECPublicKeySpec(w, params2);
            return (ECPublicKey) fact.generatePublic(keySpec);
        } catch (InvalidKeySpecException e ) {
            System.out.println( "invalid key exception .");
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println( "no such algo  .");
            return null;
        } catch (NoSuchProviderException e) {
            System.out.println( "no such provider .");
            e.printStackTrace();
            return null;
        }
    }


}
