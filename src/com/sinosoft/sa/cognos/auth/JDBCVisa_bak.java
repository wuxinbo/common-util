/*     */ package com.sinosoft.sa.cognos.auth;
/*     */ 
/*     */

import com.cognos.CAM_AAA.authentication.*;

import java.sql.Connection;
import java.sql.SQLException;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public class JDBCVisa_bak extends Visa
/*     */ {
/*     */   private Connection connection;
/*     */   private String connectionString;
/*     */   private String username;
/*     */   private String password;
/*     */ 
/*     */   public void init(JDBCAuth theNamespace, String theUsername, String thePassword)
/*     */     throws UnrecoverableException
/*     */   {
			try{
				String password =SimpleQuery.getIt("select password from v_s_cdbauth where usercode = '" + theUsername,
						thePassword, ConnectionFactory.USER);
				System.out.println(thePassword);
				if(!password.equals(thePassword)){
				if (SimpleQuery.getIt(
			         "select usercode from v_s_cdbauth where usercode = '" + theUsername + 
			        "' and password = '" + thePassword + "'", "usercode", 
			        ConnectionFactory.USER) == null) {
			         UnrecoverableException e = new UnrecoverableException(
			           "Could not generate credentials for the user.", 
			           "Visa contains invalid credentials.");
			         throw e;
			      }
				}
/*  39 */       Account account = QueryUtil.createAccount(theNamespace, theUsername);
/*  40 */       super.init(account);
/*     */ 
/*  42 */       this.username = theUsername;
/*  43 */       this.password = thePassword;
/*     */     } catch (SQLException e) {
/*  45 */       throw new UnrecoverableException("Connection Error", 
/*  46 */         "Database connection failure. Reason: " + e.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void destroy() throws UnrecoverableException {
/*     */     try {
/*  52 */       this.connection.close();
/*     */     } catch (SQLException e) {
/*  54 */       throw new UnrecoverableException("Disconnection Error", 
/*  55 */         "Database connection failure. Reason: " + e.toString());
/*     */     }
/*  57 */     super.destroy();
/*     */   }
/*     */ 
/*     */   public Connection getConnection() {
/*  61 */     return this.connection;
/*     */   }
/*     */ 
/*     */   private boolean validateConnection(String username, String password) {
/*  65 */     boolean bConnectionValid = false;
/*     */     try {
/*  67 */       Connection newConnection = MS_JDBCDriver.driver.getConnection(
/*  68 */         this.connectionString, username, password);
/*     */ 
/*  70 */       bConnectionValid = true;
/*  71 */       this.connection.close();
/*  72 */       this.connection = newConnection;
/*  73 */       this.username = username;
/*  74 */       this.password = password;
/*     */     } catch (SQLException ex) {
/*  76 */       ex.printStackTrace();
/*     */     }
/*  78 */     return bConnectionValid;
/*     */   }
/*     */ 
/*     */   public ICredential generateCredential(IBiBusHeader theAuthRequest)
/*     */     throws UserRecoverableException, SystemRecoverableException, UnrecoverableException
/*     */   {
/*  92 */     if (SimpleQuery.getIt(
/*  93 */       "select usercode from v_s_cdbauth where usercode = '" + this.username + 
/*  94 */       "' and password = '" + this.password + "'", "usercode", 
/*  95 */       ConnectionFactory.USER) != null) {
/*  96 */       UnrecoverableException e = new UnrecoverableException(
/*  97 */         "Could not generate credentials for the user.", 
/*  98 */         "Visa contains invalid credentials.");
/*  99 */       throw e;
/*     */     }
/*     */ 
/* 107 */     Credential credentials = new Credential();
/* 108 */     credentials.addCredentialValue("username", this.username);
/* 109 */     credentials.addCredentialValue("password", this.password);
/* 110 */     return credentials;
/*     */   }
/*     */ 
/*     */   public ITrustedCredential generateTrustedCredential(IBiBusHeader theAuthRequest)
/*     */     throws UserRecoverableException, SystemRecoverableException, UnrecoverableException
/*     */   {
/* 130 */     boolean isValidCredentials = true;
/* 131 */     String[] theUsername = (String[])null;
/* 132 */     String[] thePassword = (String[])null;
/*     */ 
/* 134 */     theUsername = theAuthRequest.getCredentialValue("username");
/* 135 */     thePassword = theAuthRequest.getCredentialValue("password");
/* 136 */     if ((theUsername == null) && (thePassword == null))
/*     */     {
/* 138 */       theUsername = theAuthRequest.getFormFieldValue("CAMUsername");
/* 139 */       thePassword = theAuthRequest.getFormFieldValue("CAMPassword");
/*     */     }
/* 141 */     if ((theUsername != null) && (theUsername.length == 1) && 
/* 142 */       (theUsername[0].equals(this.username)) && (thePassword.length == 1))
/* 143 */       isValidCredentials = validateConnection(theUsername[0], 
/* 144 */         thePassword[0]);
/*     */     else {
/* 146 */       isValidCredentials = validateConnection(this.username, 
/* 147 */         this.password);
/*     */     }
/* 149 */     if (!isValidCredentials) {
/* 150 */       UserRecoverableException e = new UserRecoverableException(
/* 151 */         "Please type your credentials for authentication.", 
/* 152 */         "The provided credentials are invalid.");
/* 153 */       e.addDisplayObject(
/* 154 */         new ReadOnlyDisplayObject("User ID:", 
/* 154 */         "CAMUsername", this.username));
/* 155 */       e.addDisplayObject(
/* 156 */         new TextNoEchoDisplayObject("Password:", 
/* 156 */         "CAMPassword"));
/* 157 */       throw e;
/*     */     }
/* 159 */     TrustedCredential tc = new TrustedCredential();
/* 160 */     tc.addCredentialValue("username", this.username);
/* 161 */     tc.addCredentialValue("password", this.password);
/* 162 */     return tc;
/*     */   }
/*     */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.JDBCVisa
 * JD-Core Version:    0.6.0
 */