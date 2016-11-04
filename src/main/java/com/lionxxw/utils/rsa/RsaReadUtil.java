package com.lionxxw.utils.rsa;


import sun.misc.BASE64Decoder;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

/**
 * <b>公私钥读取工具</b><br>
 * <br>
 */
@SuppressWarnings("restriction")
public final class RsaReadUtil {

	/**
	 * 根据Cer文件读取公钥
	 * 
	 * @param pubCerPath
	 * @return
	 */
	public static PublicKey getPublicKeyFromFile(String pubCerPath) {
		FileInputStream pubKeyStream = null;
		try {
			pubKeyStream = new FileInputStream(pubCerPath);
			byte[] reads = new byte[pubKeyStream.available()];
			pubKeyStream.read(reads);
			return getPublicKeyByText(new String(reads));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("公钥文件不存在");
		} catch (IOException e) {
			throw new RuntimeException("公钥文件读取失败");
		} finally {
			if (pubKeyStream != null) {
				try {
					pubKeyStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据公钥Cer文本串读取公钥
	 * 
	 * @param pubKeyText
	 * @return
	 */
	public static PublicKey getPublicKeyByText(String pubKeyText) {
		try {
			CertificateFactory certificateFactory = CertificateFactory.getInstance(RsaConst.KEY_X509);
			BufferedReader br = new BufferedReader(new StringReader(pubKeyText));
			String line = null;
			StringBuilder keyBuffer = new StringBuilder();
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("-")) {
					keyBuffer.append(line);
				}
			}
			Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(keyBuffer.toString())));
			return certificate.getPublicKey();
		} catch (Exception e) {
			throw new RuntimeException("解析公钥内容失败");
		}
	}

	/**
	 * 根据私钥路径读取私钥
	 * 
	 * @param pfxPath
	 * @param priKeyPass
	 * @return
	 */
	public static PrivateKey getPrivateKeyFromFile(String pfxPath, String priKeyPass) {
		InputStream priKeyStream = null;
		try {
			priKeyStream = new FileInputStream(pfxPath);
			byte[] reads = new byte[priKeyStream.available()];
			priKeyStream.read(reads);
			return getPrivateKeyByStream(reads, priKeyPass);
		} catch (Exception e) {
			throw new RuntimeException("解析文件，读取私钥失败");
		} finally {
			if (priKeyStream != null) {
				try {
					priKeyStream.close();
				} catch (Exception e) {
					//
				}
			}
		}
	}

	/**
	 * 根据PFX私钥字节流读取私钥
	 * 
	 * @param pfxBytes
	 * @param priKeyPass
	 * @return
	 */
	public static PrivateKey getPrivateKeyByStream(byte[] pfxBytes, String priKeyPass) {
		try {
			KeyStore ks = KeyStore.getInstance(RsaConst.KEY_PKCS12);
			char[] charPriKeyPass = priKeyPass.toCharArray();
			ks.load(new ByteArrayInputStream(pfxBytes), charPriKeyPass);
			Enumeration<String> aliasEnum = ks.aliases();
			String keyAlias = null;
			if (aliasEnum.hasMoreElements()) {
				keyAlias = (String) aliasEnum.nextElement();
			}
			return (PrivateKey) ks.getKey(keyAlias, charPriKeyPass);
		} catch (IOException e) {
			// 加密失败
			throw new RuntimeException("读取私钥，读取私钥失败");
		} catch (KeyStoreException e) {
			throw new RuntimeException("读取私钥，私钥存储异常");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("读取私钥，不存在的解密算法");
		} catch (CertificateException e) {
			throw new RuntimeException("读取私钥，证书异常");
		} catch (UnrecoverableKeyException e) {
			throw new RuntimeException("读取私钥，不可恢复的秘钥异常");
		}
	}
}
