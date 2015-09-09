/*    */ package com.sinosoft.sa.cognos.auth;
/*    */ 
/*    */

import com.cognos.CAM_AAA.authentication.IBaseClass;
import com.cognos.CAM_AAA.authentication.IRole;

import java.util.Vector;

/*    */
/*    */
/*    */ 
/*    */ public class Role extends UiClass
/*    */   implements IRole
/*    */ {
/*    */   private Vector<IBaseClass> members;
/*    */ 
/*    */   public Role(String theSearchPath)
/*    */   {
/* 17 */     super(theSearchPath);
/* 18 */     this.members = null;
/*    */   }
/*    */ 
/*    */   public void addMember(IBaseClass theMember)
/*    */   {
/* 27 */     if (this.members == null)
/*    */     {
/* 29 */       this.members = new Vector();
/*    */     }
/* 31 */     this.members.add(theMember);
/*    */   }
/*    */ 
/*    */   public IBaseClass[] getMembers()
/*    */   {
/* 42 */     if (this.members != null)
/*    */     {
/* 44 */       IBaseClass[] array = new IBaseClass[this.members.size()];
/* 45 */       return (IBaseClass[])this.members.toArray(array);
/*    */     }
/* 47 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.Role
 * JD-Core Version:    0.6.0
 */