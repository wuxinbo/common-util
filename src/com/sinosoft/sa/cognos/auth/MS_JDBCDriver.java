/*     */ package com.sinosoft.sa.cognos.auth;
/*     */ 
/*     */

import java.io.PrintStream;
import java.sql.*;
import java.util.Vector;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public class MS_JDBCDriver
/*     */ {
/*  16 */   protected static MS_JDBCDriver driver = new MS_JDBCDriver();
/*     */ 	private static final String URL="jdbc:oracle:thin:@10.1.2.37:1521:dmis";
			private static final String USERNAME ="finance";
			private static final String PASSWORD ="dmis";
/*     */   public MS_JDBCDriver()
/*     */   {
/*     */     try
/*     */     {
/*  26 */       Class.forName("oracle.jdbc.driver.OracleDriver");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  30 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getConnectionString(String theServer, String theDatabase)
/*     */   {
/*  37 */     String connectionString = "jdbc:microsoft:sqlserver://" + theServer;
/*  38 */     connectionString = connectionString + ";DatabaseName=" + theDatabase;
/*  39 */     return connectionString;
/*     */   }
/*     */ 
/*     */   public void checkDbConnection(String theConnectionString)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/*  48 */       connection = 
/*  49 */         DriverManager.getConnection(theConnectionString);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       Connection connection;
/*  57 */       if (e.getErrorCode() == 0)
/*     */       {
/*  59 */         throw e;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Connection getConnection(String theConnectionString, String theUsername, String thePassword)
/*     */     throws SQLException
/*     */   {
/*  68 */     return DriverManager.getConnection(theConnectionString, theUsername, 
/*  69 */       thePassword);
/*     */   }
/*     */ 
/*     */   public Vector query(Connection theConnection, String theSqlQuery)
/*     */     throws SQLException
/*     */   {
/*  76 */     Vector retval = new Vector();
/*  77 */     Statement stmt = theConnection.createStatement();
/*  78 */     ResultSet result = stmt.executeQuery(theSqlQuery);
/*  79 */     ResultSetMetaData rsmd = result.getMetaData();
/*     */ 
/*  81 */     Vector metadata = new Vector();
/*  82 */     for (int i = 1; i <= rsmd.getColumnCount(); i++)
/*     */     {
/*  84 */       metadata.add(rsmd.getColumnName(i));
/*     */     }
/*  86 */     retval.add(metadata);
/*  87 */     while (result.next())
/*     */     {
/*  89 */       Vector row = new Vector();
/*  90 */       for (int i = 1; i <= rsmd.getColumnCount(); i++)
/*     */       {
/*  92 */         row.add(result.getString(i));
/*     */       }
/*  94 */       retval.add(row);
/*     */     }
/*  96 */     return retval;
/*     */   }
/*     */ 
/*     */   public void dumpData(Vector theData, PrintStream thePrintStream)
/*     */   {
/* 102 */     for (int i = 0; i < theData.size(); i++)
/*     */     {
/* 104 */       Vector row = (Vector)theData.elementAt(i);
/* 105 */       for (int j = 0; j < row.size(); j++)
/*     */       {
/* 107 */         thePrintStream.print("\t" + row.elementAt(j));
/*     */       }
/* 109 */       thePrintStream.println();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.MS_JDBCDriver
 * JD-Core Version:    0.6.0
 */