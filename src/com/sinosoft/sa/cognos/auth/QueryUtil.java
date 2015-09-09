/*     */ package com.sinosoft.sa.cognos.auth;
/*     */ 
/*     */

import com.cognos.CAM_AAA.authentication.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Vector;

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
/*     */ public class QueryUtil
/*     */ {
/*     */   public static Account createAccount(INamespace theNamespace, String usercode)
/*     */     throws SQLException, UserRecoverableException
/*     */   {
/*  24 */     String userID = SimpleQuery.getIt(
/*  25 */       "select usercode from v_s_cdbauth where usercode ='" + usercode + 
/*  26 */       "'", "usercode", ConnectionFactory.USER);
/*  27 */     if (userID != null) {
/*  28 */       String userSearchPath = "u:" + userID;
/*  29 */       Account account = new Account(userSearchPath);
/*  30 */       account = setAccountProperties(account);
/*  31 */       return account;
/*     */     }
/*  33 */     return null;
/*     */   }
/*     */ 
/*     */   private static String getSingleStringResult(Vector theData)
/*     */   {
/*  38 */     if (theData.size() == 2) {
/*  39 */       Vector row = (Vector)theData.elementAt(1);
/*  40 */       if (row.size() == 1) {
/*  41 */         return (String)row.elementAt(0);
/*     */       }
/*     */     }
/*  44 */     return null;
/*     */   }
/*     */ 
/*     */   public static Account setAccountProperties(Account theAccount)
/*     */     throws SQLException, UserRecoverableException
/*     */   {
/*  72 */     String username = SimpleQuery.getIt(
/*  73 */       "select username from v_s_cdbauth where usercode ='" + 
/*  74 */       theAccount.getObjectID().substring(2) + "' ", 
/*  75 */       "username", ConnectionFactory.USER);
/*  76 */     theAccount.setUserName(username);
/*     */ 
/*  83 */     Locale locale = getLocale("2052");
/*  84 */     theAccount.setProductLocale(locale);
/*  85 */     theAccount.setContentLocale(locale);
/*     */ 
/*  87 */     theAccount.addName(locale, username);
/*  88 */     return theAccount;
/*     */   }
/*     */ 
/*     */   private static Locale getLocale(String theLocaleID) {
/*  92 */     if (theLocaleID != null) {
/*  93 */       switch (new Integer(theLocaleID).intValue()) {
/*     */       case 1033:
/*  95 */         return Locale.US;
/*     */       case 1031:
/*  97 */         return Locale.GERMAN;
/*     */       case 1036:
/*  99 */         return Locale.FRENCH;
/*     */       case 1041:
/* 101 */         return Locale.JAPANESE;
/*     */       case 2057:
/* 103 */         return Locale.UK;
/*     */       case 1028:
/* 105 */         return Locale.TRADITIONAL_CHINESE;
/*     */       case 1042:
/* 107 */         return Locale.KOREAN;
/*     */       case 2052:
/* 109 */         return Locale.SIMPLIFIED_CHINESE;
/*     */       }
/* 111 */       return Locale.SIMPLIFIED_CHINESE;
/*     */     }
/*     */ 
/* 114 */     return Locale.ENGLISH;
/*     */   }
/*     */ 
/*     */   public static void updateMembership(JDBCVisa_bak theVisa)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static void queryMembers(MS_JDBCDriver theDriver, Connection theConnection, ISortProperty[] theSortProperties, Role theRole, INamespace theNamespace)
/*     */     throws SQLException
/*     */   {
/* 145 */     StringBuffer sqlStatement = new StringBuffer();
/* 146 */     String roleID = theRole.getObjectID();
/* 147 */     if (roleID == null) {
/* 148 */       return;
/*     */     }
/* 150 */     roleID = roleID.substring(2);
/* 151 */     sqlStatement
/* 152 */       .append("SELECT [uid], [name], [issqluser], [issqlrole] FROM sysmembers INNER JOIN sysusers ON sysmembers.memberuid = sysusers.uid  WHERE sysmembers.groupuid=" + 
/* 153 */       roleID);
/* 154 */     String theSortClause = new String();
/* 155 */     if (theSortProperties != null) {
/* 156 */       for (int i = 0; i < theSortProperties.length; i++) {
/* 157 */         ISortProperty property = theSortProperties[i];
/* 158 */         if (property.getPropertyName().compareTo("name") == 0) {
/* 159 */           if (theSortClause.length() > 0) {
/* 160 */             theSortClause = theSortClause + ", ";
/*     */           }
/* 162 */           theSortClause = theSortClause + "name";
/* 163 */           if (property.getSortOrder() == 0)
/* 164 */             theSortClause = theSortClause + " ASC";
/*     */           else {
/* 166 */             theSortClause = theSortClause + " DESC";
/*     */           }
/*     */         }
/*     */       }
/* 170 */       if (theSortClause.length() > 0) {
/* 171 */         sqlStatement.append("ORDER BY ");
/* 172 */         sqlStatement.append(theSortClause);
/*     */       }
/*     */     }
/* 175 */     Vector data = theDriver.query(theConnection, sqlStatement.toString());
/* 176 */     if (data.size() > 1)
/* 177 */       for (int i = 1; i < data.size(); i++) {
/* 178 */         Vector row = (Vector)data.elementAt(i);
/*     */ 
/* 181 */         String objectID = (String)row.elementAt(0);
/* 182 */         String objectName = (String)row.elementAt(1);
/* 183 */         String isSqlUser = (String)row.elementAt(2);
/* 184 */         String isSqlRole = (String)row.elementAt(3);
/* 185 */         if (isSqlUser.compareTo("1") == 0) {
/* 186 */           String searchPath = "u:" + objectID;
/* 187 */           Account account = new Account(searchPath);
/* 188 */           account.addName(Locale.getDefault(), objectName);
/* 189 */           account.setUserName(objectName);
/* 190 */           theRole.addMember(account);
/* 191 */         } else if (isSqlRole.compareTo("1") == 0) {
/* 192 */           String searchPath = "r:" + objectID;
/* 193 */           Role role = new Role(searchPath);
/* 194 */           role.addName(Locale.getDefault(), objectName);
/* 195 */           theRole.addMember(role);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void query(MS_JDBCDriver theDriver, Connection theConnection, String theSqlCondition, IQueryOption theQueryOption, String[] theProperties, ISortProperty[] theSortProperties, QueryResult theResult, INamespace theNamespace)
/*     */     throws SQLException
/*     */   {
/* 205 */     StringBuffer sqlStatement = new StringBuffer();
/*     */ 
/* 207 */     sqlStatement
/* 208 */       .append("SELECT [uid], [name], [issqluser], [issqlrole] FROM sysusers ");
/* 209 */     if (theSqlCondition.length() > 0) {
/* 210 */       sqlStatement.append("WHERE ");
/* 211 */       sqlStatement.append(theSqlCondition);
/*     */     }
/* 213 */     long maxCount = theQueryOption.getMaxCount();
/* 214 */     long skipCount = theQueryOption.getSkipCount();
/* 215 */     String theSortClause = new String();
/* 216 */     if (theSortProperties != null) {
/* 217 */       for (int i = 0; i < theSortProperties.length; i++) {
/* 218 */         ISortProperty property = theSortProperties[i];
/* 219 */         if (property.getPropertyName().compareTo("name") == 0) {
/* 220 */           if (theSortClause.length() > 0) {
/* 221 */             theSortClause = theSortClause + ", ";
/*     */           }
/* 223 */           theSortClause = theSortClause + "name";
/* 224 */           if (property.getSortOrder() == 0)
/* 225 */             theSortClause = theSortClause + " ASC";
/*     */           else {
/* 227 */             theSortClause = theSortClause + " DESC";
/*     */           }
/*     */         }
/*     */       }
/* 231 */       if (theSortClause.length() > 0) {
/* 232 */         sqlStatement.append("ORDER BY ");
/* 233 */         sqlStatement.append(theSortClause);
/*     */       }
/*     */     }
/*     */ 
/* 237 */     Vector data = theDriver.query(theConnection, sqlStatement.toString());
/* 238 */     if (data.size() > 1) {
/* 239 */       long curSkip = 0L; long curMax = 0L;
/*     */ 
/* 241 */       for (int i = 1; i < data.size(); i++) {
/* 242 */         Vector row = (Vector)data.elementAt(i);
/*     */ 
/* 244 */         boolean bIsUser = ((String)row.elementAt(2)).compareTo("1") == 0;
/* 245 */         boolean bIsRole = ((String)row.elementAt(3)).compareTo("1") == 0;
/*     */ 
/* 248 */         if (((!bIsUser) && (!bIsRole)) || 
/* 249 */           (curSkip++ < skipCount)) {
/*     */           continue;
/*     */         }
/* 252 */         if ((curMax >= maxCount) && (maxCount != -1L))
/*     */         {
/*     */           break;
/*     */         }
/*     */ 
/* 264 */         curMax += 1L;
/*     */ 
/* 269 */         String objectID = (String)row.elementAt(0);
/* 270 */         String objectName = (String)row.elementAt(1);
/*     */ 
/* 272 */         if (bIsUser) {
/* 273 */           String searchPath = "u:" + objectID;
/* 274 */           Account account = new Account(searchPath);
/* 275 */           account.addName(Locale.getDefault(), objectName);
/* 276 */           account.setUserName(objectName);
/*     */ 
/* 280 */           account.addCustomProperty("newProp1", "value1");
/* 281 */           account.addCustomProperty("newProp2", "value2");
/*     */ 
/* 283 */           theResult.addObject(account);
/* 284 */         } else if (bIsRole) {
/* 285 */           String searchPath = "r:" + objectID;
/* 286 */           Role role = new Role(searchPath);
/* 287 */           role.addName(Locale.getDefault(), objectName);
/* 288 */           queryMembers(theDriver, theConnection, 
/* 289 */             theSortProperties, role, theNamespace);
/* 290 */           theResult.addObject(role);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void query(String theSqlCondition, IQueryOption theQueryOption, String[] theProperties, ISortProperty[] theSortProperties, QueryResult theResult, INamespace theNamespace)
/*     */   {
/* 301 */     StringBuffer sqlStatement = new StringBuffer();
/*     */ 
/* 303 */     sqlStatement.append("SELECT usercode, username FROM v_s_cdbauth ");
/* 304 */     if (theSqlCondition.length() > 0) {
/* 305 */       sqlStatement.append("WHERE ");
/* 306 */       sqlStatement.append(theSqlCondition);
/*     */     }
/*     */ 
/* 309 */     long maxCount = theQueryOption.getMaxCount();
/* 310 */     long skipCount = theQueryOption.getSkipCount();
/* 311 */     String theSortClause = new String();
/* 312 */     if (theSortProperties != null) {
/* 313 */       for (int i = 0; i < theSortProperties.length; i++) {
/* 314 */         ISortProperty property = theSortProperties[i];
/* 315 */         if (property.getPropertyName().compareTo("username") == 0) {
/* 316 */           if (theSortClause.length() > 0) {
/* 317 */             theSortClause = theSortClause + ", ";
/*     */           }
/* 319 */           theSortClause = theSortClause + " username ";
/* 320 */           if (property.getSortOrder() == 0)
/* 321 */             theSortClause = theSortClause + " ASC";
/*     */           else {
/* 323 */             theSortClause = theSortClause + " DESC";
/*     */           }
/*     */         }
/*     */       }
/* 327 */       if (theSortClause.length() > 0) {
/* 328 */         sqlStatement.append("ORDER BY ");
/* 329 */         sqlStatement.append(theSortClause);
/*     */       }
/*     */     }
/*     */ 
/* 333 */     Connection con = 
/* 334 */       ConnectionFactory.getConnection(ConnectionFactory.USER);
/* 335 */     Statement stmt = null;
/* 336 */     ResultSet rs = null;
/*     */     try {
/* 338 */       stmt = con.createStatement();
/* 339 */       rs = stmt.executeQuery(sqlStatement.toString());
/* 340 */       int curSkip = 0; int curMax = 0;
/* 341 */       while (rs.next()) {
/* 342 */         if (curSkip++ < skipCount)
/*     */           continue;
/* 344 */         if ((curMax >= maxCount) && (maxCount != -1L)) {
/*     */           break;
/*     */         }
/* 347 */         curMax++;
/*     */ 
/* 349 */         String objectID = rs.getString("usercode");
/* 350 */         String objectName = rs.getString("username");
/* 351 */         String searchPath = "u:" + objectID;
/* 352 */         Account account = new Account(searchPath);
/* 353 */         account.addName(Locale.getDefault(), objectName);
/* 354 */         account.setUserName(objectName);
/* 355 */         theResult.addObject(account);
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 360 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 363 */         rs.close();
/* 364 */         stmt.close();
/* 365 */         con.close();
/*     */       }
/*     */       catch (SQLException e) {
/* 368 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String escapeSpecialChars(String str)
/*     */   {
/* 419 */     StringBuffer escapedString = new StringBuffer(str);
/*     */ 
/* 421 */     for (int i = 0; i < escapedString.length(); ) {
/* 422 */       char c = escapedString.charAt(i);
/*     */ 
/* 424 */       switch (c) {
/*     */       case '\'':
/* 426 */         escapedString.insert(i, "!'");
/* 427 */         i += 3;
/* 428 */         break;
/*     */       case '%':
/* 430 */         escapedString.insert(i, "!%");
/* 431 */         i += 3;
/* 432 */         break;
/*     */       case '&':
/*     */       default:
/* 434 */         i++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 439 */     return escapedString.toString();
/*     */   }
/*     */ 
/*     */   public static String getSqlCondition(ISearchFilter theSearchFilter) {
/* 443 */     StringBuffer sqlCondition = new StringBuffer();
			  ISearchFilterConditionalExpression item =null;
/* 444 */     if (theSearchFilter != null) {
/* 445 */       switch (theSearchFilter.getSearchFilterType()) {
/*     */       case 3:
/* 447 */          item = (ISearchFilterConditionalExpression)theSearchFilter;
/* 448 */         String operator = item.getOperator();
/* 449 */         ISearchFilter[] filters = item.getFilters();
/* 450 */         if (filters.length <= 0) break;
/* 451 */         sqlCondition.append("( ");
/* 452 */         sqlCondition.append(getSqlCondition(filters[0]));
/* 453 */         for (int i = 1; i < filters.length; i++) {
/* 454 */           sqlCondition.append(' ');
/* 455 */           sqlCondition.append(operator);
/* 456 */           sqlCondition.append(' ');
/* 457 */           sqlCondition.append(getSqlCondition(filters[i]));
/*     */         }
/* 459 */         sqlCondition.append(" )");
/*     */ 
/* 462 */         break;
/*     */       case 2:
/* 464 */         ISearchFilterFunctionCall IC = (ISearchFilterFunctionCall)theSearchFilter;
/* 465 */         String functionName = IC.getFunctionName();
/* 466 */         if (functionName.equals("contains")) {
/* 467 */           String[] parameter = IC.getParameters();
/* 468 */           String propertyName = parameter[0];
/* 469 */           String value = parameter[1];
/* 470 */           if (propertyName.compareTo("@objectClass") == 0) {
/* 471 */             if ("account".indexOf(value) > 0)
/* 472 */               sqlCondition.append(" 1 = 1 ");
/* 473 */             else if ("role".indexOf(value) > 0) {
/* 474 */               sqlCondition.append(" ( 99 = 1 ) ");
/*     */             }
/*     */             else
/*     */             {
/* 479 */               sqlCondition.append(" 1 = 0 ");
/*     */             }
/* 481 */           } else if ((propertyName.equals("@defaultName")) || 
/* 482 */             (propertyName.equals("@userName")) || 
/* 483 */             (propertyName.equals("@name"))) {
/* 484 */             sqlCondition.append(" username LIKE '%" + 
/* 485 */               escapeSpecialChars(value) + "%' ESCAPE '!'");
/*     */           }
/*     */           else
/*     */           {
/* 491 */             sqlCondition.append(" 1 = 1 ");
/*     */           }
/* 493 */         } else if (functionName
/* 494 */           .compareTo("starts-with") == 0) {
/* 495 */           String[] parameter = IC.getParameters();
/* 496 */           String propertyName = parameter[0];
/* 497 */           String value = parameter[1];
/* 498 */           if (propertyName.compareTo("@objectClass") == 0) {
/* 499 */             if ("account".startsWith(value))
/* 500 */               sqlCondition.append(" 1 = 1 ");
/* 501 */             else if ("role".startsWith(value)) {
/* 502 */               sqlCondition.append(" ( 99 = 1 ) ");
/*     */             }
/*     */             else
/*     */             {
/* 507 */               sqlCondition.append(" 1 = 0 ");
/*     */             }
/* 509 */           } else if ((propertyName.compareTo("@defaultName") == 0) || 
/* 510 */             (propertyName.compareTo("@userName") == 0) || 
/* 511 */             (propertyName.compareTo("@name") == 0)) {
/* 512 */             sqlCondition.append(" username LIKE '" + 
/* 513 */               escapeSpecialChars(value) + "%' ESCAPE '!'");
/*     */           }
/*     */           else
/*     */           {
/* 519 */             sqlCondition.append(" 1 = 1 ");
/*     */           }
/* 521 */         } else if (functionName
/* 522 */           .compareTo("ends-with") == 0) {
/* 523 */           String[] parameter = IC.getParameters();
/* 524 */           String propertyName = parameter[0];
/* 525 */           String value = parameter[1];
/* 526 */           if (propertyName.compareTo("@objectClass") == 0) {
/* 527 */             if ("account".endsWith(value))
/* 528 */               sqlCondition.append(" 1 = 1 ");
/* 529 */             else if ("role".endsWith(value)) {
/* 530 */               sqlCondition.append(" ( 99 = 1 ) ");
/*     */             }
/*     */             else
/*     */             {
/* 535 */               sqlCondition.append(" 1 = 0 ");
/*     */             }
/* 537 */           } else if ((propertyName.compareTo("@defaultName") == 0) || 
/* 538 */             (propertyName.compareTo("@userName") == 0) || 
/* 539 */             (propertyName.compareTo("@name") == 0)) {
/* 540 */             sqlCondition.append(" username LIKE '" + 
/* 541 */               escapeSpecialChars(value) + "%' ESCAPE '!'");
/*     */           }
/*     */           else
/*     */           {
/* 547 */             sqlCondition.append(" 1 = 1 ");
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 553 */           sqlCondition.append(" 1 = 1 ");
/*     */         }
/*     */ 
/* 556 */         break;
/*     */       case 1:
/* 558 */         ISearchFilterRelationExpression irf = (ISearchFilterRelationExpression)theSearchFilter;
/* 559 */         String propertyName = irf.getPropertyName();
/* 560 */         String constraint = irf.getConstraint();
/* 561 */         String operator = irf.getOperator();
/* 562 */         if (propertyName.equals("@objectClass")) {
/* 563 */           if (constraint.equals("account"))
/*     */           {
/* 565 */             if (operator
/* 565 */               .equals("=")) {
/* 566 */               sqlCondition.append(" 1 = 1 ");
/*     */             }
/* 568 */             else if (operator
/* 568 */               .equals("!=")) {
/* 569 */               sqlCondition.append(" 99 = 0 ");
/*     */             }
/*     */             else
/*     */             {
/* 574 */               sqlCondition.append(" 1 = 0 ");
/*     */             }
/* 576 */           } else if (constraint.equals("role"))
/*     */           {
/* 578 */             if (operator
/* 578 */               .equals("=")) {
/* 579 */               sqlCondition.append(" ( 99 = 1 ) ");
/*     */             }
/* 581 */             else if (operator
/* 581 */               .equals("!=")) {
/* 582 */               sqlCondition.append(" ( 99 = 0 ) ");
/*     */             }
/*     */             else
/*     */             {
/* 587 */               sqlCondition.append(" 1 = 0 ");
/*     */             }
/*     */           }
/* 590 */           else sqlCondition.append(" 1 = 0 ");
/*     */         }
/* 592 */         else if ((propertyName.equals("@defaultName")) || 
/* 593 */           (propertyName.equals("@userName")) || 
/* 594 */           (propertyName.equals("@name"))) {
/* 595 */           sqlCondition.append(" username " + operator + " '" + constraint + 
/* 596 */             "'");
/*     */         }
/*     */         else
/*     */         {
/* 601 */           sqlCondition.append(" 1 = 1 ");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 607 */     return sqlCondition.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.QueryUtil
 * JD-Core Version:    0.6.0
 */