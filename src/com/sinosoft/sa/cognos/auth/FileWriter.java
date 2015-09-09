/*    */ package com.sinosoft.sa.cognos.auth;
/*    */ 
/*    */

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/*    */
/*    */
/*    */ 
/*    */ public class FileWriter
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*    */     try
/*    */     {
/* 20 */       File f = new File("c:\\myfile.txt");
/* 21 */       if (!f.exists())
/*    */       {
/* 23 */         f.createNewFile();
/*    */       }
/* 25 */       FileOutputStream out = new FileOutputStream(f);
/*    */ 
/* 28 */       PrintStream p = new PrintStream(out);
/*    */ 
/* 30 */       p.println("This is written to a file");
/* 31 */       p.println("line 2");
/*    */ 
/* 33 */       p.close();
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 37 */       System.err.println("Error writing to file");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.FileWriter
 * JD-Core Version:    0.6.0
 */