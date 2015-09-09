/*    */ package com.sinosoft.sa.cognos.auth;
/*    */ 
/*    */

import com.cognos.CAM_AAA.authentication.IBaseClass;
import com.cognos.CAM_AAA.authentication.IGroup;
import com.cognos.CAM_AAA.authentication.UserRecoverableException;

import java.util.Vector;

/*    */
/*    */
/*    */
/*    */ 
/*    */ public class Group extends UiClass
/*    */   implements IGroup
/*    */ {
/*    */   private Vector<IBaseClass> members;
/*    */ 
/*    */   public Group(String theObjectID)
/*    */     throws UserRecoverableException
/*    */   {
/* 18 */     super(theObjectID);
/*    */   }
/*    */ 
/*    */   public void addMember(IBaseClass theMember)
/*    */     throws UserRecoverableException
/*    */   {
/* 29 */     if (this.members == null)
/*    */     {
/* 31 */       this.members = new Vector();
/*    */     }
/* 33 */     this.members.add(theMember);
/*    */   }
/*    */ 
/*    */   public IBaseClass[] getMembers()
/*    */   {
/* 44 */     if (this.members != null)
/*    */     {
/* 46 */       IBaseClass[] array = new IBaseClass[this.members.size()];
/* 47 */       return (IBaseClass[])this.members.toArray(array);
/*    */     }
/* 49 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.Group
 * JD-Core Version:    0.6.0
 */