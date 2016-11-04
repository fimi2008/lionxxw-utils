package com.lionxxw.utils.rsa;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class CertificateUtil {
 
	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 128;
	/** */
	/**
	 * RSA最大解密密文大小
	 */
//	private static final int MAX_DECRYPT_BLOCK = 128;
	
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	
	private static KeyStore getKeyStore(String keyStorePath,
			String keyStorePassword) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {

		FileInputStream in = new FileInputStream(keyStorePath);
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(in, keyStorePassword.toCharArray());
		in.close();
		return keyStore;
	}

	private static Certificate getCertificate(String certificatePath)
			throws CertificateException, IOException {
		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X509");
		FileInputStream in = new FileInputStream(certificatePath);
		Certificate certificate = certificateFactory.generateCertificate(in);
		in.close();
		return certificate;
	}

	public static PrivateKey getPrivateKey(KeyStore keyStore, String alias,
			String keyStorePassword) throws UnrecoverableKeyException,
			KeyStoreException, NoSuchAlgorithmException {

		PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias,
				keyStorePassword.toCharArray());
		return privateKey;
	}

	public static PublicKey getPublicKey(String certificatePath)
			throws CertificateException, IOException {

		Certificate certificate = getCertificate(certificatePath);
		PublicKey publicKey = certificate.getPublicKey();
		return publicKey;
	}

	public static PublicKey getPublicKeyByCertificate(String certificateContent)
			throws CertificateException {

		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X509");
		ByteArrayInputStream is = new ByteArrayInputStream(
				certificateContent.getBytes());
		Certificate certificate = certificateFactory.generateCertificate(is);
		PublicKey publicKey = certificate.getPublicKey();

		return publicKey;
	}
	
	/**
	 * Transform the specified byte into a Hex String form.
	 */
	public static final String bytesToHexStr(
		byte[] bcd)
	{
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++)
		{
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}


	/**
	 * Transform the specified Hex String into a byte array.
	 */
	public static final byte[] hexStrToBytes(
		String s)
	{
		byte[]	bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = (byte) Integer.parseInt(
					s.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}
	private static final char[] bcdLookup =
	{
		'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
	};

	/** 
     * 由KeyStore获得私钥 
     *  
     * @param keyStorePath 
     * @param alias 
     * @param password 
     * @return 
     * @throws Exception
     */  
    public static PrivateKey getPrivateKey(String keyStorePath, String alias,
            String password) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, password);
        PrivateKey key = (PrivateKey) ks.getKey(alias, password.toCharArray());
        return key;  
    }  
	
	/** 
     * 私钥加密 
     *  
     * @param data 
     * @param keyStorePath 
     * @param alias 
     * @param password 
     * @return 
     * @throws Exception
     */  
    public static byte[] encryptByPrivateKey2(byte[] data, String keyStorePath,
            String alias, String password) throws Exception {
        // 取得私钥  
        PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
  
        return cipher.doFinal(data);  
    } 
    
    public static byte[] encryptByPrivateKey(byte[]data, PrivateKey privateKey) throws Exception {
    	Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
  
        return cipher.doFinal(data);
    }
    
    public static byte[] encryptByPublicKey(byte[]data, PublicKey pulbicKey) throws Exception {
    	Cipher cipher = Cipher.getInstance(pulbicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pulbicKey);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
			byte[] b = subarray(data, i, i + MAX_ENCRYPT_BLOCK);
			byte[] input = cipher.doFinal(b);
			sb.append(bytesToHexStr(input));
		}
        
        return hexStrToBytes(sb.toString());  
    }
	
	/**
	 * 私钥加密
	 * 
	 * @param data
	 * @param keyStore
	 * @param privateKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath,
			String keyStorePassword, String alias)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException, KeyStoreException,
			CertificateException, UnrecoverableKeyException {

		KeyStore keyStore = getKeyStore(keyStorePath, keyStorePassword);
		PrivateKey privateKey = getPrivateKey(keyStore, alias, keyStorePassword);

		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] encryptedData = cipher.doFinal(data);
//		out.close();
		return encryptedData;
	}

	/**
	 * 公钥解密
	 * 
	 * @param encryptedData
	 * @param certificatePath
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData,
			String certificatePath) throws CertificateException, IOException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		PublicKey publicKey = getPublicKey(certificatePath);
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encryptedData.length; i += MAX_ENCRYPT_BLOCK) {
			byte[] b = subarray(encryptedData, i, i + MAX_ENCRYPT_BLOCK);
			byte[] input = cipher.doFinal(b);
			sb.append(bytesToHexStr(input));
		}
		
		//byte[] decryptedData = cipher.doFinal(encryptedData);
		return hexStrToBytes(sb.toString());
	}
	
	/** 
     * 公钥加密 
     *  
     * @param data 
     * @param certificatePath 
     * @return 
     * @throws Exception
     */  
    public static byte[] encryptByPublicKey(byte[] data, String certificatePath)
            throws Exception {
  
        // 取得公钥  
        PublicKey publicKey = getPublicKey(certificatePath);
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
			byte[] b = subarray(data, i, i + MAX_ENCRYPT_BLOCK);
			byte[] input = cipher.doFinal(b);
			sb.append(bytesToHexStr(input));
		}
        
        return hexStrToBytes(sb.toString());  
  
    } 
	
	/**
	 * 公钥解密
	 * 
	 * @param encryptedData
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData,
			PublicKey publicKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] decryptedData = cipher.doFinal(encryptedData);
		return decryptedData;
	}
	
	private static byte[] subarray(byte[] array, int startIndexInclusive,
			int endIndexExclusive) {
		if (array == null) {
			return null;
		}
		if (startIndexInclusive < 0) {
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length) {
			endIndexExclusive = array.length;
		}
		int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0) {
			return EMPTY_BYTE_ARRAY;
		}

		byte[] subarray = new byte[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}
	
}
