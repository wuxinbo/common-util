package com.wu.test;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DesTest {
	private  String Name;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public  static String encodeCipher(String digest) {

		if (digest == null)
			return null;

		byte[] cipher = null; /* ���ܺ��Byte[] */
		MessageDigest messDigest = null;

		try {
			messDigest = MessageDigest.getInstance("MD5"); /* ����MD5�����㷨 */
			messDigest.update(digest.getBytes("GBK")); /* UTF8 Byte */
			cipher = messDigest.digest();
		} catch (NoSuchAlgorithmException algException) {
		} catch (UnsupportedEncodingException unEncodingExp) {
		} finally {
			/* �ͷŶ��� */
			if (messDigest != null)
				messDigest = null;
		}

		return new BASE64Encoder().encode(cipher);

	}
	public static void main(String [] arg){
//		QC8v05NdrCrYqhhyYYZaeQ==
		System.out.println(encodeCipher("010010551"));
		
	}
}
