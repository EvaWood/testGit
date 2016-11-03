package com.jinda.controllers;


import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.jinda.models.*;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.jinda.canteen.models.*;
import com.jinda.common.DBHelperClass;

@Controller
@RequestMapping(value = "/Attendance")
public class AttendanceController {
	
	@Autowired
	private ComboPooledDataSource pooledDataSource;

	@ResponseBody
	@RequestMapping(value = { "/getActivityList.do" }, method = RequestMethod.GET)
	public String getActivityList(Model model) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson= new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Hr_sp_getAttendanceActivity");
		
		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;
		
		myDb.setConn(connection);
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			myDb.EndExecute();		
			hashmap.put("result", "NO");
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		List<Gy_CodeName> list = new ArrayList<Gy_CodeName>();
		 
		
		try {
			while (rs.next()) {
				Gy_CodeName info=new Gy_CodeName();
				info.setCode(rs.getString("ItemCode"));
				info.setName(rs.getString("ItemName"));
				list.add(info);				
			}
		} catch (SQLException e) {
			myDb.EndExecute();
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();
		
		hashmap.put("result", "YES");
		hashmap.put("message", list);
		
		return gson.toJson(hashmap);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/getqingxiuList.do" }, method = RequestMethod.GET)
	public String getqingxiuList(Model model,String userCode) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Hr_sp_AttendanceApplyQueryApp '");
		sql.append(userCode);
		sql.append("'");
		
		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;
		
		myDb.setConn(connection);
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			myDb.EndExecute();		
			hashmap.put("result", "NO");
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		List<Hr_Attendanceqx> list = new ArrayList<Hr_Attendanceqx>();
		 
		
		try {
			while (rs.next()) {
				Hr_Attendanceqx info=new Hr_Attendanceqx();
				info.setStartdate(rs.getString("StartDate"));
				info.setEnddate(rs.getString("EndDate"));
				info.setActivity(rs.getString("AffairTypeName"));
				info.setDays(rs.getString("ApplyDays"));
				info.setRemark(rs.getString("Remark"));
				info.setChecker(rs.getString("CheckerName1"));
				list.add(info);				
			}
		} catch (SQLException e) {
			myDb.EndExecute();
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();
		
		hashmap.put("result", "YES");
		hashmap.put("message", list);
		
		return gson.toJson(hashmap);
	}
	 
	@ResponseBody
	@RequestMapping(value = { "/SaveQingXiuApply.do" }, method = RequestMethod.GET)
	public String SaveQingXiuApply(String userCode, String sdate,String stime,String edate,String etime,String activity,String remark) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();
        		

		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}
        
		String startdate=sdate+" "+stime;
		String enddate=edate+" "+etime;
		
		sql = new StringBuilder("exec Hr_sp_AttendanceApplyAction 0,'");		
		sql.append(userCode);
		sql.append("','");
		sql.append(3);
		sql.append("','");
		sql.append(userCode);
		sql.append("','");
		sql.append("01");
		sql.append("','");
		sql.append(startdate);
		sql.append("','");
		sql.append(enddate);
		sql.append("','");
		sql.append(activity);
		sql.append("','");
		sql.append(remark);
		sql.append("'");
				
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			myDb.EndExecute();		
			hashmap.put("result", "NO");
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", "YES");
		return gson.toJson(hashmap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = { "/getBuLuList.do" }, method = RequestMethod.GET)
	public String getBuLuList(Model model,String userCode) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Hr_sp_AttendanceBuLuApplyQueryAPP '");
		sql.append(userCode);
		sql.append("'");
		
		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;
		
		myDb.setConn(connection);
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			myDb.EndExecute();		
			hashmap.put("result", "NO");
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		List<Hr_AttendanceBL> list = new ArrayList<Hr_AttendanceBL>();
		 
		
		try {
			while (rs.next()) {
				Hr_AttendanceBL info=new Hr_AttendanceBL();
				info.setNianyue(rs.getString("ApplyPeriod"));
				info.setApplyDate(rs.getString("ApplyDate"));
				info.setAffairType(rs.getString("AffairTypeName"));
				info.setRemark(rs.getString("Remark"));
				info.setChecker(rs.getString("CheckerName"));
				list.add(info);				
			}
		} catch (SQLException e) {
			myDb.EndExecute();
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();
		
		hashmap.put("result", "YES");
		hashmap.put("message", list);
		
		return gson.toJson(hashmap);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/SaveBuLuApply.do" }, method = RequestMethod.GET)
	public String SaveBuLuApply(String userCode, String date,String time,String workactivity,String remark) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();
		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}
        
		String applydate=date+" "+time;
		
		sql = new StringBuilder("exec Hr_sp_AttendanceBuLuApplyOper 0,'");		
		sql.append(userCode);
		sql.append("','");
		sql.append(3);
		sql.append("','");
		sql.append(userCode);
		sql.append("','");
		sql.append("01");
		sql.append("','");
		sql.append(applydate);
		sql.append("','");		
		sql.append(workactivity);
		sql.append("','");
		sql.append(remark);
		sql.append("'");
		
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			myDb.EndExecute();		
			hashmap.put("result", "NO");
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();		
		hashmap.put("result", "YES");
		return gson.toJson(hashmap);
	}
	

}
