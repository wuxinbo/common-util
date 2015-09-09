/*     */ package com.sinosoft.sa.cognos.auth;
/*     */ 
/*     */

import com.cognos.CAM_AAA.authentication.IBaseClass;
import com.cognos.CAM_AAA.authentication.IUiClass;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public class UiClass
/*     */   implements IUiClass
/*     */ {
/*     */   private String objectID;
/*     */   private HashMap<Locale, String> names;
/*     */   private HashMap<Locale, String> descriptions;
/*     */   private Stack<IBaseClass> ancestors;
/*     */ 
/*     */   public UiClass(String theObjectID)
/*     */   {
/*  21 */     this.names = null;
/*  22 */     this.descriptions = null;
/*  23 */     this.ancestors = null;
/*  24 */     this.objectID = theObjectID;
/*     */   }
/*     */ 
/*     */   public void addDescription(Locale theLocale, String theDescription)
/*     */   {
/*  34 */     if (this.descriptions == null)
/*     */     {
/*  36 */       this.descriptions = new HashMap();
/*     */     }
/*  38 */     this.descriptions.put(theLocale, theDescription);
/*     */   }
/*     */ 
/*     */   public String getDescription(Locale theLocale)
/*     */   {
/*  49 */     if (this.descriptions != null)
/*     */     {
/*  51 */       return (String)this.descriptions.get(theLocale);
/*     */     }
/*  53 */     return null;
/*     */   }
/*     */ 
/*     */   public Locale[] getAvailableDescriptionLocales()
/*     */   {
/*  64 */     if (this.descriptions != null)
/*     */     {
/*  66 */       Set keySet = this.descriptions.keySet();
/*  67 */       Locale[] array = new Locale[keySet.size()];
/*  68 */       return (Locale[])keySet.toArray(array);
/*     */     }
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   public void addAncestors(IBaseClass theAncestor)
/*     */   {
/*  79 */     if (this.ancestors == null)
/*     */     {
/*  81 */       this.ancestors = new Stack();
/*     */     }
/*  83 */     this.ancestors.push(theAncestor);
/*     */   }
/*     */ 
/*     */   public IBaseClass[] getAncestors()
/*     */   {
/*  94 */     if (this.ancestors != null)
/*     */     {
/*  96 */       IBaseClass[] array = new IBaseClass[this.ancestors.size()];
/*  97 */       return (IBaseClass[])this.ancestors.toArray(array);
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   public void addName(Locale theLocale, String theName)
/*     */   {
/* 109 */     if (this.names == null)
/*     */     {
/* 111 */       this.names = new HashMap();
/*     */     }
/* 113 */     this.names.put(theLocale, theName);
/*     */   }
/*     */ 
/*     */   public boolean getHasChildren()
/*     */   {
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   public String getName(Locale theLocale)
/*     */   {
/* 135 */     if (this.names != null)
/*     */     {
/* 137 */       return (String)this.names.get(theLocale);
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */   public Locale[] getAvailableNameLocales()
/*     */   {
/* 150 */     if (this.names != null)
/*     */     {
/* 152 */       Set keySet = this.names.keySet();
/* 153 */       Locale[] array = new Locale[keySet.size()];
/* 154 */       return (Locale[])keySet.toArray(array);
/*     */     }
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */   public String getObjectID()
/*     */   {
/* 167 */     return this.objectID;
/*     */   }
/*     */ 
/*     */   protected void setObjectID(String theObjectID)
/*     */   {
/* 176 */     this.objectID = theObjectID;
/*     */   }
/*     */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.UiClass
 * JD-Core Version:    0.6.0
 */