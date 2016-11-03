package com.jinda.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.erp.models.Gy_CodeName;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GetCodeNameBySQL {
	
	private Boolean ret;
	private String errorMsg;
	private String sql;
	private List<Gy_CodeName> gy_CodeNames;
	private Connection connection;
	private Boolean p4=true;  //是否录入全部
	
	public Boolean getRet() {
		return ret;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public List<Gy_CodeName> getGy_CodeNames() {
		return gy_CodeNames;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void setP4(Boolean p4) {
		this.p4 = p4;
	}
	
	public void Init(){
		gy_CodeNames=new ArrayList<Gy_CodeName>();
		ret=false;
		errorMsg="";
		connection=null;
		p4=true;
	}

	public Boolean getNow(){
		
		if (sql==null || sql.trim().length()==0) {
			setErrorMsg("sql 为空");
			return false;
		}
		
		if (p4)
		//运行选空
		{
			Gy_CodeName item=new Gy_CodeName();
			
			item.setCode("");
			item.setName("<全部>");
			
			gy_CodeNames.add(item);

		}
		
		DBHelperClass dbHelperClass=new DBHelperClass();
		dbHelperClass.setConn(connection);
		
		Boolean dbret=dbHelperClass.ExecuteQuery(sql);
		
		ResultSet resultSet=dbHelperClass.getResultSet();
		
		try {
			
			while (resultSet.next()) {

				Gy_CodeName item=new Gy_CodeName();
				
				item.setCode(resultSet.getString(1));
				item.setName(resultSet.getString(2));
				
				gy_CodeNames.add(item);
			}
		}catch (Exception e) {
			setErrorMsg(e.getMessage());
			dbHelperClass.EndExecute();
			return false;
		}
		
		dbHelperClass.EndExecute();
		return true;
	}
	
}
