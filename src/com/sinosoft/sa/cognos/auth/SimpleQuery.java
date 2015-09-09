/*    */ package com.sinosoft.sa.cognos.auth;
/*    */ 
/*    */

import com.cognos.CAM_AAA.authentication.UserRecoverableException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ 
/*    */ public class SimpleQuery
/*    */ {
			private static Logger log4j =Logger.getLogger(SimpleQuery.class);
/*    */   public static String getIt(String queryString, String columnName, String dbname)
/*    */   {
/* 17 */     Connection con = ConnectionFactory.getConnection(dbname);
/* 18 */     Statement stmt = null;
/* 19 */     ResultSet rs = null;
/*    */     try {
/* 21 */       stmt = con.createStatement();
/* 22 */       rs = stmt.executeQuery(queryString);
/* 23 */       if (rs.next()) {
/* 24 */         String simpleresult = rs.getString(columnName);
/* 25 */         String str1 = simpleresult;
/*    */         return str1;
/*    */       }
/*    */     } catch (SQLException e) {
/* 28 */       e.printStackTrace();
/*    */     } finally {
/*    */       try {
/* 31 */         rs.close();
/* 32 */         stmt.close();
/* 33 */         con.close();
/*    */       }
/*    */       catch (SQLException e) {
/* 36 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */     try
/*    */     {
/* 31 */       rs.close();
/* 32 */       stmt.close();
/* 33 */       con.close();
/*    */     }
/*    */     catch (SQLException e) {
/* 36 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 39 */     return null;
/*    */   }
/*    */ 
		/**
		 * 查找是否存在该用户。
		 * @param userName 用户名
		 * @param password 密码
		 * @return 如果存在返回true，否则返回false
		 */
		public static boolean  isExistUser(String userName,String password){
			String Encodepassword = SimpleQuery.getIt(
			"select password from v_s_cdbauth where usercode = '"+userName.trim()+"'",
					 "password", ConnectionFactory.USER);
			log4j.info("isExistUser username:"+userName+" password:"+password+"Encodepassword: "+Encodepassword);
			if (Encodepassword.equals(password)) {
				return true;
			}else{
				return false;
			}
		}

/*    */   public static List<String> getThem(String queryString, String columnName, String dbname)
/*    */     throws UserRecoverableException
/*    */   {
/* 45 */     Connection con = ConnectionFactory.getConnection(dbname);
/* 46 */     Statement stmt = null;
/* 47 */     ResultSet rs = null;
/*    */     try {
/* 49 */       stmt = con.createStatement();
/* 50 */       rs = stmt.executeQuery(queryString);
/* 51 */       ArrayList result = new ArrayList();
/* 52 */       while (rs.next()) {
/* 53 */         result.add(rs.getString(columnName));
/*    */       }
/* 55 */       ArrayList localArrayList1 = result;
/*    */       return localArrayList1;
/*    */     } catch (SQLException e) {
/* 57 */       e.printStackTrace();
/*    */     } finally {
/*    */       try {
/* 60 */         rs.close();
/* 61 */         stmt.close();
/* 62 */         con.close();
/*    */       }
/*    */       catch (SQLException e) {
/* 65 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.SimpleQuery
 * JD-Core Version:    0.6.0
 */