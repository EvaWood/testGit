package com.jinda.models;

public class ExecuteStatus {
	
	private String result;
	
	public void setResult(Integer result) {
		this.result = result.toString();
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult() {
		return result;
	}
	
	private String message;
	public void seteMessage(String errormessage) {
		this.message = errormessage;
	}
	public String getMessage() {
		return message;
	}
	
	private String OutMainID;
	public void setOutMainID(String outMainID) {
		OutMainID = outMainID;
	}
	public String getOutMainID() {
		return OutMainID;
	}

}
