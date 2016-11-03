package com.jinda.models;

import java.util.ArrayList;

public class UserRightsInfo {

	private ArrayList<String> rightCode;

	public UserRightsInfo() {
		rightCode=new ArrayList<String>();
	}
	
	public ArrayList<String> getRightCode() {
		return rightCode;
	}
	
	public void setRightCode(ArrayList<String> rightCode) {
		this.rightCode = rightCode;
	}
	
	public void addNew(String rightIndex)
	{
		rightCode.add(rightIndex);
	}
	
	public Boolean findRight(String rightIndex)
	{
		return rightCode.contains(rightIndex);
	}
}
