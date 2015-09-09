/*    */ package com.sinosoft.sa.cognos.auth;
/*    */ 
/*    */ import com.cognos.CAM_AAA.authentication.INamespaceFolder;
/*    */ 
/*    */ public class NamespaceFolder extends UiClass
/*    */   implements INamespaceFolder
/*    */ {
/*    */   public NamespaceFolder(String theSearchPath)
/*    */   {
/* 14 */     super(theSearchPath);
/*    */   }
/*    */ 
/*    */   public boolean getHasChildren()
/*    */   {
/* 25 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.NamespaceFolder
 * JD-Core Version:    0.6.0
 */