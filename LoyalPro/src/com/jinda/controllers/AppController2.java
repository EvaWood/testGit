package com.jinda.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.models.Xs_CusQianKuan;
import com.google.gson.Gson;
import com.jinda.common.DBHelperClass;
import com.jinda.common.GetCodeNameBySQL;
import com.jinda.models.Cg_FuKuan;
import com.jinda.models.CommonTools;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Controller
@RequestMapping(value="/app")
public class AppController2 {
	
	@Autowired 
	private ComboPooledDataSource pooledDataSource;

	@ResponseBody
	@RequestMapping(value={"/CusQianKuanQuery.do"} , method=RequestMethod.POST)
	public String CusQianKuanQuery(HttpServletRequest request, String DeptCode, String PersonCode,String CusCodeName) {
		
		if (CusCodeName==null) CusCodeName="";
		if (DeptCode==null) DeptCode="";
		if (PersonCode==null) PersonCode="";
		
		String userCode=request.getHeader("userCode");
		HashMap<String,Object> hashMap=new HashMap<String,Object>();
		Gson gson=new Gson();
		
		Connection connection=null;
		try {
			connection=pooledDataSource.getConnection();
		}catch(Exception ex) {
			hashMap.put("result", -1);
			hashMap.put("message", ex.getMessage());
			return gson.toJson(hashMap);
		}
		
		DBHelperClass myDb=new DBHelperClass();
		Boolean dbret=false;
		
		myDb.setConn(connection);
		
		StringBuilder sql=new StringBuilder("exec dbo.[Ar_Sp_AgeState_byGotDate_App] '");
		sql.append(DeptCode.trim());
		sql.append("','");
		sql.append(PersonCode.trim());
		sql.append("','");
		sql.append(CusCodeName.trim());
		sql.append("','");
		sql.append(userCode);
		sql.append("',0");
		
		dbret=myDb.ExecuteQuery(sql.toString());
		
		if (dbret==false) {
			
			myDb.EndExecute();
			
			hashMap.put("result", -1);
			hashMap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashMap);
		}
		
		ResultSet rs=myDb.getResultSet();
		Boolean hasResultSet=false;
		
		List<Xs_CusQianKuan> infoList=new ArrayList<Xs_CusQianKuan>();
		
		try {
			while(rs.next()) {

				Xs_CusQianKuan info=new Xs_CusQianKuan();
				
				info.setCusCode(rs.getString("CusCode"));
				info.setCusName(rs.getString("CusName"));
				info.setCusMoney(rs.getString("CusMoney"));
				info.setMoney0_30(rs.getString("D01_30"));
				info.setMoney31_60(rs.getString("D31_60"));
				info.setMoney61_90(rs.getString("D61_90"));
				info.setMoney91_120(rs.getString("D91_120"));
				info.setMoney121_150(rs.getString("D121_150"));
				info.setMoney151_180(rs.getString("D151_180"));
				info.setMoney181_210(rs.getString("D181_210"));
				info.setMoney211(rs.getString("D211Up"));
				
				infoList.add(info);
				
				hasResultSet=true;
			}
			
		}catch(Exception ex) {
			myDb.EndExecute();
			hashMap.put("result", -1);
			hashMap.put("message", ex.getMessage());
			return gson.toJson(hashMap);
		}
		
		myDb.EndExecute();
		
/*		if (hasResultSet==false) {
			hashMap.put("result", -1);
			return gson.toJson(hashMap);
		}*/
		
		hashMap.put("result", infoList.size());
		hashMap.put("rows", infoList);
		
		return gson.toJson(hashMap);
		
	}
	
	@ResponseBody
	@RequestMapping(value={"/getCodeName.do"}, method=RequestMethod.POST)
	public String getCodeName(HttpServletRequest httpServletRequest ,String CodeNameType, String p1, String p2, String p3, String p4) {
		
		if (p1==null) p1="";
		if (p2==null) p2="";
		if (p3==null) p3="";
		if (p4==null) p4="";

		String userCode=httpServletRequest.getHeader("userCode");
		
		StringBuilder sql=new StringBuilder();
		sql.append("EXEC DBO.App_sp_GetCodeName '");
		sql.append(CodeNameType.trim());
		sql.append("','");
		sql.append(userCode.trim());
		sql.append("','");
		sql.append(p1.trim());
		sql.append("','");
		sql.append(p2.trim());
		sql.append("','");
		sql.append(p3.trim());
		sql.append("','");
		sql.append(p4.trim());
		sql.append("'");
		
		HashMap<String,Object> hashMap=new HashMap<String,Object>();
		Gson gson=new Gson();
		
		Connection connection=null;
		try {
			connection=pooledDataSource.getConnection();
		}catch(Exception ex) {
			hashMap.put("result", -1);
			hashMap.put("message",ex.getMessage());
			return gson.toJson(hashMap);
		}
		
		GetCodeNameBySQL getCodeNameBySQL=new GetCodeNameBySQL();
		getCodeNameBySQL.Init();
		
		if (p4.trim()=="NotALL") {
			getCodeNameBySQL.setP4(false);
		}
		
		getCodeNameBySQL.setConnection(connection);
		
		
		getCodeNameBySQL.setSql(sql.toString());
		Boolean ret=getCodeNameBySQL.getNow();
	
		if (!ret) {
			hashMap.put("result", -1);
			hashMap.put("message", getCodeNameBySQL.getErrorMsg());
			return gson.toJson(hashMap);
		}
		hashMap.put("result", getCodeNameBySQL.getGy_CodeNames().size());
		hashMap.put("rows", getCodeNameBySQL.getGy_CodeNames());
		
		return gson.toJson(hashMap);
	}

	@ResponseBody
	@RequestMapping(value={"/FuKuanShenPiList.do"}, method=RequestMethod.POST)
	public String FuKuanShenPiList(HttpServletRequest request, String fkoption) {
		String userCode=request.getHeader("userCode");
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

		HashMap<String,Object> hashMap=new HashMap<String,Object>();
		Gson gson=new Gson();
		
		Connection connection=null;
		try {
			connection=pooledDataSource.getConnection();
		}catch(Exception ex) {
			hashMap.put("result", -1);
			hashMap.put("message", ex.getMessage());
			return gson.toJson(hashMap);
		}
		
		DBHelperClass myDb=new DBHelperClass();
		Boolean dbret=false;
		
		myDb.setConn(connection);
		
		StringBuilder sql=new StringBuilder();
		fkoption=fkoption.trim();
		switch(fkoption){
		case "2":
			sql.append("exec Cg_sp_PayCheck '','','',1,2,0,0,0, '");
			break;
		case "3":
			sql.append("exec Cg_sp_PayCheck '','','',1,1,2,0,0, '");
			break;
		case "4":
			sql.append("exec Cg_sp_PayCheck '','','',1,1,1,2,0, '");
			break;
		}
		sql.append(CommonTools.getFirstDay());
		sql.append("','");
		sql.append(CommonTools.getLastDay());
		sql.append("',1,0,'0','','");
		sql.append(userCode);
		sql.append("'");
		
		dbret=myDb.ExecuteQuery(sql.toString());
		
		if (dbret==false) {
			
			myDb.EndExecute();
			
			hashMap.put("result", -1);
			hashMap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashMap);
		}
		
		ResultSet rs=myDb.getResultSet();
		Boolean hasResultSet=false;
		
		List<Cg_FuKuan> infoList=new ArrayList<Cg_FuKuan>();
		
		try {
			while(rs.next()) {

				Cg_FuKuan info=new Cg_FuKuan();
				
				info.setPayCheckCode(rs.getString("PayCheckCode"));
				info.setWillPayDate(rs.getString("WillPayDate"));
				info.setSupplierName(rs.getString("SupplierName"));
				info.setMoneyTotalYb(rs.getString("MoneyTotalYb"));
				info.setForeignCurrName(rs.getString("ForeignCurrName"));
				info.setPayTypeName(rs.getString("PayTypeName"));
				info.setRemark(rs.getString("Remark"));
				info.setChecker(rs.getString("Checker"));
				info.setCheckDate(rs.getString("CheckDate"));
				info.setCheckerName2(rs.getString("CheckerName2"));
				info.setCheckerRemark2(rs.getString("CheckerRemark2"));
				info.setCheckDate2(rs.getString("CheckDate2"));
				info.setCheckerName3(rs.getString("CheckerName3"));
				info.setCheckerRemark3(rs.getString("CheckerRemark3"));
				info.setCheckDate3(rs.getString("CheckDate3"));
				info.setCheckerName4(rs.getString("CheckerName4"));
				info.setCheckerRemark4(rs.getString("CheckerRemark4"));
				info.setCheckDate4(rs.getString("CheckDate4"));
				info.setCheckerName5(rs.getString("CheckerName5"));
				info.setCheckerRemark5(rs.getString("CheckerRemark5"));
				info.setCheckDate5(rs.getString("CheckDate5"));
				info.setPayCheckMainID(rs.getString("PayCheckMainID"));
				
				infoList.add(info);
				
				hasResultSet=true;
			}
			
		}catch(Exception ex) {
			myDb.EndExecute();
			hashMap.put("result", -1);
			hashMap.put("message", ex.getMessage());
			return gson.toJson(hashMap);
		}
		
		myDb.EndExecute();
		
/*		if (hasResultSet==false) {
			hashMap.put("result", -1);
			return gson.toJson(hashMap);
		}*/
		
		hashMap.put("result", infoList.size());
		hashMap.put("rows", infoList);
		
		return gson.toJson(hashMap);
		
	}

	@ResponseBody
	@RequestMapping(value={"/Cg_FuKuanShenPiCheckSubmit.do"}, method=RequestMethod.POST)
	public String Cg_FuKuanShenPiCheckSubmit(HttpServletRequest request, String PayCheckMainID, String fkoption, String FuKuanCheckRemark) {
		String userCode=request.getHeader("userCode");
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

		HashMap<String,Object> hashMap=new HashMap<String,Object>();
		Gson gson=new Gson();
		
		Connection connection=null;
		try {
			connection=pooledDataSource.getConnection();
		}catch(Exception ex) {
			hashMap.put("result", -1);
			hashMap.put("message", ex.getMessage());
			return gson.toJson(hashMap);
		}
		
		DBHelperClass myDb=new DBHelperClass();
		Boolean dbret=false;
		
		myDb.setConn(connection);
		
		StringBuilder sql=new StringBuilder("exec Cg_sp_PayCheckAction ");
		sql.append(PayCheckMainID);
		sql.append(",");
		sql.append(fkoption);
		sql.append(",'");
		sql.append(userCode);
		sql.append("','");
		sql.append(FuKuanCheckRemark);
		sql.append("',null");
		
		dbret=myDb.ExecuteNonQuery(sql.toString());
		
		if (dbret==false) {
			
			myDb.EndExecute();
			
			hashMap.put("result", -1);
			hashMap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashMap);
		}
		
		myDb.EndExecute();
		
/*		if (hasResultSet==false) {
			hashMap.put("result", -1);
			return gson.toJson(hashMap);
		}*/
		
		hashMap.put("result", 0);
		hashMap.put("message", "执行成功");
		
		return gson.toJson(hashMap);
		
	}
	
	
}
