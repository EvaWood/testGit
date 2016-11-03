package com.jinda.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.CallableStatement;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.jinda.common.DBHelperClass;
import com.jinda.models.*;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Controller
@RequestMapping(value = "/base")
public class BaseController {
	
    @Autowired
    private ComboPooledDataSource pooledDataSource;
	

	@RequestMapping(value = { "/index.do" }, method = RequestMethod.GET)
	public String material(Model model) {
		return "/base/index";
	}
	
	@RequestMapping(value = { "/welcome.do" }, method = RequestMethod.GET)
	public String welcome(Model model) {
		return "/base/welcome";
	}
	
	@RequestMapping(value = { "/unit.do" }, method = RequestMethod.GET)
	public String unit(Model model) {
		return "/base/unit";
	}

	@ResponseBody
	@RequestMapping(value = { "/unitlist.do" }, method = RequestMethod.POST)
	public String unit_list() {
		HashMap<String,Object> hashmap  = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		//报错信息
		List<OldExecuteStatus> OldExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		OldExecuteStatuslist.add(es);
		
        Connection connection=null;
		try {
			connection=(Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total",-1);
			hashmap.put("rows",OldExecuteStatuslist);
			return gson.toJson(hashmap);
		}
        
        DBHelperClass myDb= new DBHelperClass();
        Boolean dbret=false;
        
        myDb.setConn(connection);
		String sql = "select * from bg_goods_unit";
		dbret = myDb.ExecuteQuery(sql);
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total",-1);
			hashmap.put("rows",OldExecuteStatuslist);
			return gson.toJson(hashmap);
		}
		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;
		List<Bg_goods_unit> unitList = new ArrayList<Bg_goods_unit>();

		try {
			while (rs.next()) {
				Bg_goods_unit unit = new Bg_goods_unit();
				unit.setCode(rs.getString("code"));
				unit.setName(rs.getString("name"));

				unitList.add(unit);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		myDb.EndExecute();

		hashmap.put("total",unitList.size());
		hashmap.put("rows",unitList);
         
		return gson.toJson(hashmap);	
	}
	
	@ResponseBody
	@RequestMapping(value = { "/unitlistbox.do" }, method = RequestMethod.POST)
	public String unit_listbox() {
		HashMap<String,Object> hashmap  = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		//报错信息
		List<OldExecuteStatus> OldExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		OldExecuteStatuslist.add(es);
		
        Connection connection=null;
		try {
			connection=(Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total",-1);
			hashmap.put("rows",OldExecuteStatuslist);
			return gson.toJson(hashmap);
		}
        
        DBHelperClass myDb= new DBHelperClass();
        Boolean dbret=false;
        
        myDb.setConn(connection);
		String sql = "select * from bg_goods_unit";
		dbret = myDb.ExecuteQuery(sql);
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total",-1);
			hashmap.put("rows",OldExecuteStatuslist);
			return gson.toJson(hashmap);
		}
		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;
		List<Bg_goods_unit> unitList = new ArrayList<Bg_goods_unit>();

		try {
			while (rs.next()) {
				Bg_goods_unit unit = new Bg_goods_unit();
				unit.setCode(rs.getString("code"));
				unit.setName(rs.getString("name"));

				unitList.add(unit);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		myDb.EndExecute();
         
		return gson.toJson(unitList);	
	}


	@ResponseBody
	@RequestMapping(value = { "/NewUnit.do" }, method = RequestMethod.POST)
	public String NewUnit(String code, String name ) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
        
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");
		
		Gson gson = new Gson();

		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}
        
		sql = new StringBuilder("exec bg_sp_addunit '");
		sql.append(code);
		sql.append("','");
		sql.append(name);
		sql.append("'");
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			return gson.toJson(es);
		}

		myDb.EndExecute();

		return gson.toJson(es);
	}

	@ResponseBody
	@RequestMapping(value = { "/EditUnit.do" }, method = RequestMethod.POST)
	public String EditUnit(@RequestParam("code") String code, @RequestParam("name") String name) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
        
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");
		
		Gson gson = new Gson();

		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		sql = new StringBuilder("update bg_goods_unit set name='");
		sql.append(name);
		sql.append("' where code='");
		sql.append(code);
		sql.append("'");
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			return gson.toJson(es);
		}

		myDb.EndExecute();

		return gson.toJson(es);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/DeleteUnit.do" }, method = RequestMethod.POST)
	public String DeleteUnit(String code) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
        
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");
		
		Gson gson = new Gson();

		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		sql = new StringBuilder("delete bg_goods_unit where code='");
		sql.append(code);
		sql.append("'");
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			return gson.toJson(es);
		}

		myDb.EndExecute();

		return gson.toJson(es);
	}
	
	@RequestMapping(value = { "/suppliercategory.do" }, method = RequestMethod.GET)
	public String suppliercategory(Model model) {
		return "/base/suppliercategory";
	}
	
	@ResponseBody
	@RequestMapping(value = { "/list_suppliercategory.do" }, method = RequestMethod.POST)
	public String list_suppliercategory() {
		HashMap<String,Object> hashmap  = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		//报错信息
		List<OldExecuteStatus> OldExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		OldExecuteStatuslist.add(es);
		
        Connection connection=null;
		try {
			connection=(Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total",-1);
			hashmap.put("rows",OldExecuteStatuslist);
			return gson.toJson(hashmap);
		}
        
        DBHelperClass myDb= new DBHelperClass();
        Boolean dbret=false;
        
        myDb.setConn(connection);
        
		String sql = "select * from Bg_supplier_category";
		dbret = myDb.ExecuteQuery(sql);
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total",-1);
			hashmap.put("rows",OldExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;
		List<Bg_supplier_category> List = new ArrayList<Bg_supplier_category>();

		try {
			while (rs.next()) {
				Bg_supplier_category info = new Bg_supplier_category();
				info.setCode(rs.getString("code"));
				info.setName(rs.getString("name"));
				List.add(info);

				hasResult = true;
			}

		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total",-1);
			hashmap.put("rows",OldExecuteStatuslist);
			myDb.EndExecute();
		}

		myDb.EndExecute();

		hashmap.put("total",List.size());
		hashmap.put("rows",List);
         
		return gson.toJson(hashmap);
	}

	@ResponseBody
	@RequestMapping(value = { "/Oper_suppliercategory.do" }, method = RequestMethod.POST)
	public String Oper_suppliercategory(@RequestParam("operType") String operType,@RequestParam("code") String code, @RequestParam("name") String name) {

		Gson gson = new Gson();
		
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

        Connection connection=null;
		try {
			connection=(Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			
			return gson.toJson(es);
		}
        
        DBHelperClass myDb= new DBHelperClass();
        Boolean dbret=false;
        
        myDb.setConn(connection);
		
		StringBuilder sql=new StringBuilder("exec bg_sp_suppliercategory '");
		sql.append(operType);
		sql.append("','");
		sql.append(code);
		sql.append("','");
		sql.append(name);
		sql.append("'");
	
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			myDb.EndExecute();
			return gson.toJson(es);
		}

		myDb.EndExecute();
		
		return gson.toJson(es);
	}
	
	/**
	 * 测试
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = { "/unitnew.do" }, method = RequestMethod.GET)
	public String unitnew(Model model, HttpSession httpSession) {
		return "/base/unitnew";
	}
	
	@ResponseBody
	@RequestMapping(value = { "/unitnewlist.do" }, method = RequestMethod.POST)
	public String unitnew_list(Model model, HttpSession httpSession,String page,String row) {
		Gson gson = new Gson();
		
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");
        Connection connection=null;
		try {
			connection=(Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			
			return gson.toJson(es);
		}
        
        DBHelperClass myDb= new DBHelperClass();
        Boolean dbret=false;
        
        myDb.setConn(connection);
		
        if(row==null){
        	row="10";
        }

		String sql = "select * from bg_goods_unit";

		StringBuilder sql2=new StringBuilder();
		sql2=new StringBuilder("exec Page_bg_goods_unit  '");
		sql2.append(sql);
		sql2.append("','");
		sql2.append(page);
		sql2.append("','");
		sql2.append(row);
		sql2.append("'");
		dbret = myDb.ExecuteQuery(sql2.toString());
		
		dbret = myDb.ExecuteQuery(sql2.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			myDb.EndExecute();
			return gson.toJson(es);
		}
		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;
		List<Bg_goods_unit> unitList = new ArrayList<Bg_goods_unit>();

		try {
			while (rs.next()) {
				Bg_goods_unit unit = new Bg_goods_unit();
				unit.setCode(rs.getString("code"));
				unit.setName(rs.getString("name"));

				unitList.add(unit);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		myDb.EndExecute();
		String json_txt = gson.toJson(unitList);

		return json_txt;
	}

}
