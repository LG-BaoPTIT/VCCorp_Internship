package com.ite.authservice.security.encrypt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AES {

    @Value("${aes.admin.secretKey}")
    private String ADMIN_SECRET;

    @Value("${aes.ecommerce.secretKey}")
    private String ECOMMERCE_SECRET;

    private SecretKeySpec secretKeySpec;
    private byte[] key;

    public String adminEncrypt(final String strToEncrypt){
        return encrypt(strToEncrypt,this.ADMIN_SECRET);
    }

    public String adminDecrypt(final String strToEncrypt){
        return decrypt(strToEncrypt,this.ADMIN_SECRET);
    }

    public String ecomEncrypt(final String strToEncrypt){
        return encrypt(strToEncrypt,this.ECOMMERCE_SECRET);
    }

    public String ecomDecrypt(final String strToEncrypt){
        return decrypt(strToEncrypt,this.ECOMMERCE_SECRET);
    }

    private void setKey(final String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(final String strToEncrypt,String secret) {
        try {
            System.out.println(secret);
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.getMessage());
        }
        return null;
    }

    public String decrypt(final String strToDecrypt,String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.getMessage());
        }
        return null;
    }
}
