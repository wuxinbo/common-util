package com.wu.model;

import java.util.Date;

import com.wu.annotation.ValidateAnnotation;


/**
 * dmis用户模型类。
 * @author wuxinbo
 *
 */
public class User {
	@ValidateAnnotation(isBlank=false)
	private String userName;
	private String password;
	private String deptCde;
	private String cognosPwd;
	private String userCde;
	private String state;
	private String createUser;
	private Date createDate;
	private int age;
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getUserCde() {
		return userCde;
	}
	public void setUserCde(String userCde) {
		this.userCde = userCde;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDeptCde() {
		return deptCde;
	}
	public void setDeptCde(String deptCde) {
		this.deptCde = deptCde;
	}
	public String getCognosPwd() {
		return cognosPwd;
	}
	public void setCognosPwd(String cognosPwd) {
		this.cognosPwd = cognosPwd;
	}
	
}
