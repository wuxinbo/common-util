package com.wu;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * des三重加密类。
 * 
 * @author Administrator
 *
 */
public class TriplDES {


/*     */   private static final String Algorithm = "DESede";
/*  12 */   private static final byte[] keyBytes = getKeyByStr("AF684A2F31E6E5ADD163DF42D520EBDF92F8F78FECB90746");
/*     */ 
/*     */   private static byte[] encryptMode(byte[] keybyte, byte[] src)
/*     */   {
/*     */     try
/*     */     {
/*  19 */       SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
/*     */ 
/*  21 */       Cipher c1 = Cipher.getInstance("DESede");
/*  22 */       c1.init(1, deskey);
/*  23 */       return c1.doFinal(src);
/*     */     }
/*     */     catch (NoSuchAlgorithmException e1) {
/*  26 */       e1.printStackTrace();
/*     */     }
/*     */     catch (NoSuchPaddingException e2) {
/*  29 */       e2.printStackTrace();
/*     */     }
/*     */     catch (Exception e3) {
/*  32 */       e3.printStackTrace();
/*     */     }
/*  34 */     return null;
/*     */   }
/*     */ 
/*     */   private static byte[] decryptMode(byte[] keybyte, byte[] src) {
/*     */     try {
/*  39 */       SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
/*     */ 
/*  41 */       Cipher c1 = Cipher.getInstance("DESede");
/*  42 */       c1.init(2, deskey);
/*  43 */       return c1.doFinal(src);
/*     */     }
/*     */     catch (NoSuchAlgorithmException e1) {
/*  46 */       e1.printStackTrace();
/*     */     }
/*     */     catch (NoSuchPaddingException e2) {
/*  49 */       e2.printStackTrace();
/*     */     }
/*     */     catch (Exception e3) {
/*  52 */       e3.printStackTrace();
/*     */     }
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   public static String byte2hex(byte[] b)
/*     */   {
/*  59 */     String hs = "";
/*  60 */     String stmp = "";
/*  61 */     for (int n = 0; n < b.length; n++) {
/*  62 */       stmp = Integer.toHexString(b[n] & 0xFF);
/*  63 */       if (stmp.length() == 1)
/*  64 */         hs = hs + "0" + stmp;
/*     */       else
/*  66 */         hs = hs + stmp;
/*     */     }
/*  68 */     return hs.toUpperCase();
/*     */   }
/*     */ 
/*     */   private static byte[] getKeyByStr(String str) {
/*  72 */     byte[] bRet = new byte[str.length() / 2];
/*  73 */     for (int i = 0; i < str.length() / 2; i++) {
/*  74 */       Integer itg = new Integer(16 * getChrInt(str.charAt(2 * i)) + 
/*  75 */         getChrInt(str.charAt(2 * i + 1)));
/*  76 */       bRet[i] = itg.byteValue();
/*     */     }
/*  78 */     return bRet;
/*     */   }
/*     */ 
/*     */   private static int getChrInt(char chr) {
/*  82 */     int iRet = 0;
/*  83 */     if (chr == "0".charAt(0))
/*  84 */       iRet = 0;
/*  85 */     if (chr == "1".charAt(0))
/*  86 */       iRet = 1;
/*  87 */     if (chr == "2".charAt(0))
/*  88 */       iRet = 2;
/*  89 */     if (chr == "3".charAt(0))
/*  90 */       iRet = 3;
/*  91 */     if (chr == "4".charAt(0))
/*  92 */       iRet = 4;
/*  93 */     if (chr == "5".charAt(0))
/*  94 */       iRet = 5;
/*  95 */     if (chr == "6".charAt(0))
/*  96 */       iRet = 6;
/*  97 */     if (chr == "7".charAt(0))
/*  98 */       iRet = 7;
/*  99 */     if (chr == "8".charAt(0))
/* 100 */       iRet = 8;
/* 101 */     if (chr == "9".charAt(0))
/* 102 */       iRet = 9;
/* 103 */     if (chr == "A".charAt(0))
/* 104 */       iRet = 10;
/* 105 */     if (chr == "B".charAt(0))
/* 106 */       iRet = 11;
/* 107 */     if (chr == "C".charAt(0))
/* 108 */       iRet = 12;
/* 109 */     if (chr == "D".charAt(0))
/* 110 */       iRet = 13;
/* 111 */     if (chr == "E".charAt(0))
/* 112 */       iRet = 14;
/* 113 */     if (chr == "F".charAt(0))
/* 114 */       iRet = 15;
/* 115 */     return iRet;
/*     */   }
/*     */ 
/*     */   public static String getEncryptStr(String str) {
/* 119 */     return byte2hex(encryptMode(keyBytes, str.getBytes()));
/*     */   }
/*     */ 
/*     */   public static String getDecryptStr(String str) {
/* 123 */     return new String(decryptMode(keyBytes, getKeyByStr(str)));
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 176 */     //Security.addProvider(new SunJCE());
/*     */ 
/* 186 */     String szSrc = "21E521C73A7BFD06";
/* 187 */     System.out.println("加密前字符串" + szSrc + "  --   " + szSrc.length());
/* 188 */     String ew = getEncryptStr(szSrc);
/* 189 */     System.out.println("加密之后的字符串" + ew + "  --   " + ew.length());
/* 190 */     String ds = getDecryptStr("98AE79CAE1F4D235");
/* 191 */     System.out.println("加密" + ds + "  --   " + ew.length());
/*     */   }
	
}
