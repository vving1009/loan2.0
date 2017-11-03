package com.jiaye.loan.cashloan.utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by boxuanjia.
 */
public class RSAUtil {

    // ALGORITHM
    public static final String RSA = "RSA";

    // ALGORITHM/MODE/PADDING
    public static final String RSA_ECB_PKCS1PADDING = "RSA/ECB/PKCS1Padding";

    private RSAUtil() {
    }

    /**
     * 公钥加密
     *
     * @param data      data
     * @param publicKey publicKey
     * @return Base64
     */
    public static String encryptByPublicKeyToBase64(String data, String publicKey) {
        return encryptByPublicKeyToBase64(data.getBytes(), publicKey);
    }

    /**
     * 公钥加密
     *
     * @param data      data
     * @param publicKey publicKey
     * @return Base64
     */
    public static String encryptByPublicKeyToBase64(byte[] data, String publicKey) {
        return Base64Util.encode(encryptByPublicKey(data, publicKey));
    }

    /**
     * 公钥加密
     *
     * @param data      data
     * @param publicKey publicKey
     * @return byte[]
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) {
        byte[] keyBytes = Base64Util.decode(publicKey);
        return encryptByPublicKey(data, keyBytes);
    }

    /**
     * 公钥加密
     *
     * @param data      data
     * @param publicKey publicKey
     * @return byte[]
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);

        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        PublicKey publicKeyObject;
        try {
            publicKeyObject = keyFactory.generatePublic(x509KeySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(RSA_ECB_PKCS1PADDING);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        try {
            cipher.init(1, publicKeyObject);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        try {
            return cipher.doFinal(data);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 私钥解秘
     *
     * @param base64       base64
     * @param privateKey privateKey
     * @return String
     */
    public static String decryptByPrivateKeyToString(String base64, String privateKey) {
        return decryptByPrivateKeyToString(Base64Util.decode(base64), privateKey);
    }

    /**
     * 私钥解秘
     *
     * @param data       data
     * @param privateKey privateKey
     * @return String
     */
    public static String decryptByPrivateKeyToString(byte[] data, String privateKey) {
        return new String(decryptByPrivateKey(data, privateKey));
    }

    /**
     * 私钥解秘
     *
     * @param data       data
     * @param privateKey privateKey
     * @return byte[]
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) {
        byte[] keyBytes = Base64Util.decode(privateKey);
        return decryptByPrivateKey(data, keyBytes);
    }

    /**
     * 私钥解秘
     *
     * @param data       data
     * @param privateKey privateKey
     * @return byte[]
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static byte[] decryptByPrivateKey(byte[] data, byte[] privateKey) {
        try {
            PKCS8EncodedKeySpec e = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKeyObject = keyFactory.generatePrivate(e);
            Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1PADDING);
            cipher.init(2, privateKeyObject);
            return cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

}
