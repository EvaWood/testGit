package com.jinda.models;

public class UserInfo {
	
	private String userCode;
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserCode() {
		return userCode;
	}
	
	private String userName;
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}

	private String deptCode;
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}
