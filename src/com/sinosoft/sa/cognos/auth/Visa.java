/*     */ package com.sinosoft.sa.cognos.auth;
/*     */ 
/*     */

import com.cognos.CAM_AAA.authentication.*;

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
/*     */ public class Visa
/*     */   implements IVisa
/*     */ {
/*     */   private Vector<IRole> roles;
/*     */   private Vector<IGroup> groups;
/*     */   private IAccount account;
/*     */   private boolean valid;
/*     */ 
/*     */   public Visa()
/*     */   {
/*  26 */     this.roles = null;
/*  27 */     this.groups = null;
/*     */   }
/*     */ 
/*     */   public void init(IAccount theAccount)
/*     */     throws UnrecoverableException
/*     */   {
/*  37 */     this.account = theAccount;
/*  38 */     this.valid = true;
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */     throws UnrecoverableException
/*     */   {
/*  47 */     this.roles = null;
/*  48 */     this.groups = null;
/*  49 */     this.account = null;
/*  50 */     this.valid = false;
/*     */   }
/*     */ 
/*     */   public ITrustedCredential generateTrustedCredential(IBiBusHeader theAuthRequest)
/*     */     throws UserRecoverableException, SystemRecoverableException, UnrecoverableException
/*     */   {
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */   public ICredential generateCredential(IBiBusHeader theAuthRequest)
/*     */     throws UserRecoverableException, SystemRecoverableException, UnrecoverableException
/*     */   {
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isValid()
/*     */   {
/*  87 */     return this.valid;
/*     */   }
/*     */ 
/*     */   public IAccount getAccount()
/*     */   {
/*  98 */     return this.account;
/*     */   }
/*     */ 
/*     */   public void addGroup(IGroup theGroup)
/*     */   {
/* 107 */     if (this.groups == null)
/*     */     {
/* 109 */       this.groups = new Vector();
/*     */     }
/* 111 */     this.groups.add(theGroup);
/*     */   }
/*     */ 
/*     */   public IGroup[] getGroups()
/*     */   {
/* 122 */     if (this.groups != null)
/*     */     {
/* 124 */       IGroup[] array = new IGroup[this.groups.size()];
/* 125 */       return (IGroup[])this.groups.toArray(array);
/*     */     }
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */   public void addRole(IRole theRole)
/*     */   {
/* 136 */     if (this.roles == null)
/*     */     {
/* 138 */       this.roles = new Vector();
/*     */     }
/* 140 */     this.roles.add(theRole);
/*     */   }
/*     */ 
/*     */   public IRole[] getRoles()
/*     */   {
/* 151 */     if (this.roles != null)
/*     */     {
/* 153 */       IRole[] array = new IRole[this.roles.size()];
/* 154 */       return (IRole[])this.roles.toArray(array);
/*     */     }
/* 156 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.Visa
 * JD-Core Version:    0.6.0
 */