/*     */ package com.sinosoft.sa.cognos.auth;
/*     */ 
/*     */

import com.cognos.CAM_AAA.authentication.*;
import org.apache.log4j.Logger;

import java.util.Locale;

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
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public class JDBCAuth extends Namespace
/*     */   implements INamespaceAuthenticationProvider2
/*     */ {
	        private Logger log4j =Logger.getLogger(JDBCVisa.class);
	
/*     */   public void init(INamespaceConfiguration theNamespaceConfiguration)
/*     */     throws UnrecoverableException
/*     */   {
/*  33 */     super.init(theNamespaceConfiguration);
/*     */ 
/*  35 */     addName(Locale.getDefault(), theNamespaceConfiguration
/*  36 */       .getDisplayName());
/*     */     try
/*     */     {
/*  39 */       String configPath = theNamespaceConfiguration.getInstallLocation() + 
/*  40 */         "/configuration";
/*  41 */       new ConnectionFactory(configPath);
/*     */     }
/*     */     catch (Exception e) {
/*  44 */       throw new UnrecoverableException("Configuration Error", 
/*  45 */         "Provider initialization failure. Reason: " + e.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public IVisa logon(IBiBusHeader2 theAuthRequest)
/*     */     throws UserRecoverableException, SystemRecoverableException, UnrecoverableException
/*     */   {
/*  57 */     JDBCVisa visa = null;
/*  58 */     String[] username = (String[])null;
/*  59 */     String[] password = (String[])null;
/*     */ 
/*  61 */     username = theAuthRequest.getTrustedCredentialValue("username");
/*  62 */     password = theAuthRequest.getTrustedCredentialValue("password");
/*  63 */     if ((username == null) && (password == null))
/*     */     {
/*  65 */       username = theAuthRequest.getCredentialValue("username");
/*  66 */       password = theAuthRequest.getCredentialValue("password");
/*     */     }
/*  68 */     if ((username == null) && (password == null))
/*     */     {
/*  70 */       username = theAuthRequest.getFormFieldValue("CAMUsername");
/*  71 */       password = theAuthRequest.getFormFieldValue("CAMPassword");
/*     */     }
/*     */ 
/*  78 */     if ((username == null) || (password == null)) {
/*  79 */       UserRecoverableException e = new UserRecoverableException(
/*  80 */         "Please type your credentials for authentication.", 
/*  81 */         "The provided credentials are invalid.");
/*  82 */       e
/*  83 */         .addDisplayObject(new TextDisplayObject("User ID:", 
/*  84 */         "CAMUsername"));
/*  85 */       e.addDisplayObject(
/*  86 */         new TextNoEchoDisplayObject("Password:", 
/*  86 */         "CAMPassword"));
/*  87 */       throw e;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  93 */       visa = new JDBCVisa();
				if (SimpleQuery.isExistUser(username[0], password[0])) { //将密码和数据库进行比对，相同就不需要进行加密。
					visa.init(this, username[0], password[0]);
				}else{
					visa.init(this, username[0], TripleDES.getEncryptStr(password[0]));
				}
/*  94 */       
/*     */     }
/*     */     catch (UnrecoverableException ex)
/*     */     {
/*  98 */       UserRecoverableException e = new UserRecoverableException(
/*  99 */         "Username or password error. Please type your credentials for authentication.","您提供的信用凭证存在问题,请重新输入!"); 
/* 103 */       e
/* 104 */         .addDisplayObject(new TextDisplayObject("User ID:", 
/* 105 */         "CAMUsername"));
/* 106 */       e.addDisplayObject(
/* 107 */         new TextNoEchoDisplayObject("Password:", 
/* 107 */         "CAMPassword"));
/* 108 */       throw e;
/*     */     }
/*     */ 
/* 111 */     return visa;
/*     */   }
/*     */ 
/*     */   public void logoff(IVisa theVisa, IBiBusHeader theAuthRequest)
/*     */   {
/*     */     try
/*     */     {
/* 123 */       JDBCVisa visa = (JDBCVisa)theVisa;
/* 124 */       visa.destroy();
/*     */     }
/*     */     catch (Throwable localThrowable)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public IQueryResult search(IVisa theVisa, IQuery theQuery)
/*     */     throws UnrecoverableException
/*     */   {
/* 138 */     JDBCVisa visa = (JDBCVisa)theVisa;
/* 139 */     QueryResult result = new QueryResult();
/*     */     try
/*     */     {
/* 145 */       ISearchExpression expression = theQuery.getSearchExpression();
/*     */ 
/* 181 */       String objectID = expression.getObjectID();
/* 182 */       ISearchStep[] steps = expression.getSteps();
/*     */ 
/* 185 */       if (steps.length != 1) {
/* 186 */         throw new UnrecoverableException(
/* 187 */           "Internal Error", 
/* 188 */           "Invalid search expression. Multiple steps is not supported for this namespace.");
/*     */       }
/* 190 */       StringBuffer sqlCondition = new StringBuffer();
/* 191 */       int searchType = steps[0].getAxis();
/* 192 */       ISearchFilter filter = steps[0].getPredicate();
/*     */ 
/* 194 */       switch (searchType) {
/*     */       case 5:
/*     */       case 7:
/* 197 */         if (objectID == null) {
/* 198 */           if ((filter == null) || (matchesFilter(filter))) {
/* 199 */             result.addObject(this);
/*     */           }
/*     */ 
/* 202 */           if (searchType == 7) {
/* 203 */             return result;
/*     */           }
/* 205 */           sqlCondition.append(QueryUtil.getSqlCondition(filter));
/*     */         }
/*     */         else
/*     */         {
/* 209 */           if ((objectID.startsWith("u:")) && 
/* 210 */             (objectID.equals(visa.getAccount().getObjectID())))
/*     */           {
/* 212 */             if ((filter == null) || (matchesFilter(filter))) {
/* 213 */               result.addObject(visa.getAccount());
/*     */             }
/*     */ 
/* 216 */             return result;
/* 217 */           }if ((!objectID.startsWith("u:")) && 
/* 218 */             (!objectID.startsWith("r:")))
/*     */             break;
/* 220 */           String sqlID = objectID.substring(2);
/* 221 */           sqlCondition.append(QueryUtil.getSqlCondition(filter));
/* 222 */           if (sqlCondition.length() > 0) {
/* 223 */             sqlCondition.append(" AND ");
/*     */           }
/* 225 */           sqlCondition.append(" usercode = '" + sqlID + "'");
/*     */         }
/*     */ 
/* 229 */         break;
/*     */       case 6:
/*     */       default:
/* 235 */         sqlCondition.append(QueryUtil.getSqlCondition(filter));
/*     */ 
/* 237 */         if ((objectID == null) || (!objectID.startsWith("u:")))
/*     */           break;
/* 239 */         if (sqlCondition.length() > 0)
/*     */         {
/* 241 */           sqlCondition.append(" AND ");
/*     */         }
/*     */ 
/* 244 */         sqlCondition.append(" usercode = '" + objectID.substring(2) + "'");
/*     */       }
/*     */ 
/* 253 */       QueryUtil.query(sqlCondition.toString(), theQuery.getQueryOption(), 
/* 254 */         theQuery.getProperties(), theQuery.getSortProperties(), 
/* 255 */         result, this);
/*     */     }
/*     */     catch (Throwable t) {
/* 258 */       t.printStackTrace();
/*     */     }
/*     */ 
/* 268 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.JDBCAuth
 * JD-Core Version:    0.6.0
 */