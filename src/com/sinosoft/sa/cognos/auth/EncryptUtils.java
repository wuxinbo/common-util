/*    */ package com.sinosoft.sa.cognos.auth;
/*    */ 
/*    */ public class EncryptUtils
/*    */ {
/*  5 */   private static MD5 md5 = new MD5();
/*    */ 
/*    */   public static String md5(String str) {
/*  8 */     return md5.getMD5ofStr(str);
/*    */   }
/*    */ 
/*    */   public static String encryption(String src)
/*    */   {
/* 18 */     String BLANKSTRING = "        ";
/* 19 */     StringBuffer des = new StringBuffer("        ");
/* 20 */     int MAXLENGTH = 8;
/*    */ 
/* 22 */     String key1 = "43065271";
/* 23 */     StringBuffer tmdes = new StringBuffer("        ");
/*    */ 
/* 25 */     if (src.length() < 8)
/* 26 */       for (int i = 0; i < src.length(); i++) tmdes.setCharAt(i, src.charAt(i));
/*    */     else {
/* 28 */       for (int i = 0; i < 8; i++) tmdes.setCharAt(i, src.charAt(i));
/*    */     }
/* 30 */     for (int i = 0; i < 8; i++) {
/* 31 */       int tmIndex = Integer.valueOf(String.valueOf(key1.charAt(i))).intValue();
/* 32 */       des.setCharAt(i, tmdes.charAt(tmIndex));
/*    */     }
/*    */ 
/* 36 */     String key2 = "1e4s2dj6l38 5097vw.";
/* 37 */     String key3 = "ab cdefghijklmn102p";
/* 38 */     String tmdes = new String(des);
/* 39 */     for (int i = 0; i < 8; i++) {
/* 40 */       for (int k = 0; k < 19; k++) { if (tmdes.charAt(i) != key2.charAt(k)) continue; des.setCharAt(i, key3.charAt(k));
/*    */       }
/*    */     }
/* 43 */     return des.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.EncryptUtils
 * JD-Core Version:    0.6.0
 */