/*    */ package com.sinosoft.sa.cognos.auth;
/*    */ 
/*    */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ 
/*    */ public class ConnectionFactory
/*    */ {
/* 14 */   public static String USER = "user";
/*    */ 
/* 16 */   public static String OTH = "oth";
/*    */   private static String configFilePath;
/*    */   private static Properties nsProperties;
/*    */ 
/*    */   public ConnectionFactory(String configFilePath)
/*    */   {
/* 24 */     if ((configFilePath == null) || ("".equals(configFilePath))) {
/* 25 */       throw new NullPointerException("配置文件路径不可用！");
/*    */     }
/* 27 */     init(configFilePath);
/*    */   }
/*    */ 
/*    */   private void init(String configPath)
/*    */   {
/* 32 */     configFilePath = configPath;
/*    */ 
/* 34 */     nsProperties = new Properties();
/*    */     try {
/* 36 */       nsProperties.load(new FileInputStream(new File(configFilePath, "JDBC_Config_dbAuth.properties")));
/*    */     }
/*    */     catch (FileNotFoundException e) {
/* 39 */       e.printStackTrace();
/*    */     }
/*    */     catch (IOException e) {
/* 42 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   private static Connection getUserConnection() {
/* 47 */     Connection userConnection = null;
/*    */     try {
/* 49 */       Class.forName(nsProperties.getProperty("oracle.jdbc.driver.OracleDriver"));
/* 50 */       userConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.1.2.37:1521:dmis", 
/* 52 */         "finance", 
/* 53 */         "dmis");
/*    */     }
/*    */     catch (ClassNotFoundException e) {
/* 56 */       e.printStackTrace();
/*    */     }
/*    */     catch (SQLException e) {
/* 59 */       e.printStackTrace();
/*    */     }
/* 61 */     return userConnection;
/*    */   }
/*    */ 
/*    */   private static Connection getOthConnection() {
/* 65 */     Connection othConnection = null;
/*    */     try {
				   Class.forName(nsProperties.getProperty("oracle.jdbc.driver.OracleDriver"));
	/* 50 */       othConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.1.2.37:1521:dmis", 
	/* 52 */         "finance", 
	/* 53 */         "dmis");
/*    */     }
/*    */     catch (ClassNotFoundException e) {
/* 74 */       e.printStackTrace();
/*    */     }
/*    */     catch (SQLException e) {
/* 77 */       e.printStackTrace();
/*    */     }
/* 79 */     return othConnection;
/*    */   }
/*    */ 
/*    */   public static Connection getConnection(String dbname) {
/* 83 */     if (dbname.equalsIgnoreCase(USER))
/* 84 */       return getUserConnection();
/* 85 */     if (dbname.equalsIgnoreCase(OTH)) {
/* 86 */       return getOthConnection();
/*    */     }
/* 88 */     return getUserConnection();
/*    */   }
/*    */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.ConnectionFactory
 * JD-Core Version:    0.6.0
 */