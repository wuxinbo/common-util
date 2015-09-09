/*    */ package com.sinosoft.sa.cognos.auth;
/*    */ 
/*    */

import com.cognos.CAM_AAA.authentication.ICredential;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

/*    */
/*    */
/*    */
/*    */ 
/*    */ public class Credential
/*    */   implements ICredential
/*    */ {
/*    */   private HashMap<String, Vector<String>> credentials;
/*    */ 
/*    */   public Credential()
/*    */   {
/* 19 */     this.credentials = null;
/*    */   }
/*    */ 
/*    */   public String[] getCredentialNames()
/*    */   {
/* 30 */     if (this.credentials != null)
/*    */     {
/* 32 */       Set keySet = this.credentials.keySet();
/* 33 */       String[] array = new String[keySet.size()];
/* 34 */       return (String[])keySet.toArray(array);
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   public void addCredentialValue(String theName, String theValue)
/*    */   {
/* 46 */     if (this.credentials == null)
/*    */     {
/* 48 */       this.credentials = new HashMap();
/*    */     }
/* 50 */     Vector v = (Vector)this.credentials.get(theName);
/* 51 */     if (v == null)
/*    */     {
/* 53 */       v = new Vector();
/* 54 */       this.credentials.put(theName, v);
/*    */     }
/* 56 */     v.add(theValue);
/*    */   }
/*    */ 
/*    */   public String[] getCredentialValue(String theName)
/*    */   {
/* 67 */     if (this.credentials != null)
/*    */     {
/* 69 */       Vector v = (Vector)this.credentials.get(theName);
/* 70 */       if (v != null)
/*    */       {
/* 72 */         return (String[])v.toArray(new String[v.size()]);
/*    */       }
/*    */     }
/* 75 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.Credential
 * JD-Core Version:    0.6.0
 */