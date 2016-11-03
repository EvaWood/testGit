package com.jinda.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping(value = "/home")
public class HomeController {

	@Autowired
	private ComboPooledDataSource pooledDataSource;

	@RequestMapping(value = { "/error.do" }, method = RequestMethod.GET)
	public String error(Model model) {
		return "/home/error";
	}

	@RequestMapping(value = { "/login.do" }, method = RequestMethod.GET)
	public String login(Model model) {
		return "/home/login";
	}

	@RequestMapping(value = { "/loginSubmit.do" }, method = RequestMethod.POST)
	public String loginSubmit(String UserCode, String Passwd, Model model, HttpSession httpSession) {
		if (UserCode == null){
			UserCode = "";
		}
		if (Passwd == null){
			Passwd = "";
		}
		Gson gson = new Gson();

//		OldExecuteStatus es = new OldExecuteStatus();
//		es.setResult(true);
//		es.seteErorMessage("执行成功");
//
//		Connection connection = null;
//		try {
//			connection = (Connection) pooledDataSource.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			
//			model.addAttribute("errorPage", e.getMessage());
//			return "/home/error";
//		}
//
//		DBHelperClass myDb = new DBHelperClass();
//		Boolean dbret = false;
//
//		myDb.setConn(connection);
//
//		String sql = "select UserCode,UserName from Gy_User where UserCode='" + UserCode + "'";
//		dbret = myDb.ExecuteQuery(sql);
//		if (dbret == false) {
//			model.addAttribute("errorPage", myDb.getDbErrorMsg());
//			return "/home/error";
//		}
//
//		ResultSet rs = myDb.getResultSet();
//		Boolean hasResult = false;
//		try {
//			while (rs.next()) {
//				UserInfo loginUserInfo = new UserInfo();
//				loginUserInfo.setUserCode(rs.getString("UserCode"));
//				loginUserInfo.setUserName(rs.getString("UserName"));
//
//				model.addAttribute("loginUserInfo", loginUserInfo);
//
//				hasResult = true;
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			myDb.EndExecute();
//			
//			model.addAttribute("errorPage", e.getMessage());
//			return "/home/error";
//		}
//
//		if (hasResult == false) {
//			model.addAttribute("errorPage", "没有这个用户");
//			return "/home/error";
//		}
//
//		// 获取权限表
//		sql = " exec Gy_GetBGRights '" + UserCode + "'";
//		dbret = myDb.ExecuteQuery(sql);
//		if (dbret == false) {
//			model.addAttribute("errorPage", myDb.getDbErrorMsg());
//			return "/home/error";
//		}
//		rs = myDb.getResultSet();
//		hasResult = false;
//		UserRightsInfo userRightsInfo = new UserRightsInfo();
//		try {
//			while (rs.next()) {
//				String kk = rs.getString("RightIndex");
//				userRightsInfo.addNew(kk);
//
//				hasResult = true;
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			myDb.EndExecute();
//			
//			model.addAttribute("errorPage", e.getMessage());
//			return "/home/error";
//		}
//
//		if (hasResult == false) {
//			model.addAttribute("errorPage", "没有办公用品权限");
//			return "/home/error";
//		}
//
//		httpSession.setAttribute("UserCode", UserCode);
//		httpSession.setAttribute("UserRightsInfo", userRightsInfo);
//
//		myDb.EndExecute();
		
		return "redirect:/base/index.do";
	}

	@RequestMapping(value = { "testsession.do" }, method = RequestMethod.GET)
	public String testsession(Model model, HttpSession httpSession) {
		Object obj = httpSession.getAttribute("UserCode");
		if (obj == null) {
			model.addAttribute("session", "该用户没有登录");
		} else {
			model.addAttribute("session", (String) obj);
		}

		return "/home/testsession";
	}

	@RequestMapping(value = { "/goods.do" }, method = RequestMethod.GET)
	public String goods(Model model, HttpSession httpSession) {
		return "/base/goods";
	}

	@ResponseBody
	@RequestMapping(value = { "/goodslist.do" }, method = RequestMethod.POST)
	public String goodslistFormField() {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		// 报错信息
		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		String sql = "select * from bg_v_goods";
		dbret = myDb.ExecuteQuery(sql);
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_goods> goodslist = new ArrayList<Bg_goods>();

		try {
			while (rs.next()) {
				Bg_goods goods = new Bg_goods();
				goods.setcode(rs.getString("code"));
				goods.setname(rs.getString("name"));
				goods.setSpec(rs.getString("spec"));
				goods.setUnit(rs.getString("unit"));
				goods.setUnitname(rs.getString("unitname"));
				goods.setBar_code(rs.getString("bar_code"));
				goods.setSateqty(rs.getDouble("sateqty"));
				goods.setNowqty(rs.getDouble("nowqty"));

				goodslist.add(goods);
				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		myDb.EndExecute();

		hashmap.put("total", goodslist.size());
		hashmap.put("rows", goodslist);

		return gson.toJson(hashmap);
	}

	/**
	 * 办公用品combobox用
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/goodsbox.do" }, method = RequestMethod.POST)
	public String goodsbox() {
		Gson gson = new Gson();

		// 报错信息
		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		String sql = "select * from bg_v_goods";
		dbret = myDb.ExecuteQuery(sql);
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			return gson.toJson(es);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_goods> goodslist = new ArrayList<Bg_goods>();

		try {
			while (rs.next()) {
				Bg_goods goods = new Bg_goods();
				goods.setcode(rs.getString("code"));
				goods.setname(rs.getString("name"));
				goods.setSpec(rs.getString("spec"));
				goods.setUnit(rs.getString("unit"));
				goods.setUnitname(rs.getString("unitname"));
				goods.setBar_code(rs.getString("bar_code"));
				goods.setSateqty(rs.getDouble("sateqty"));
				goods.setNowqty(rs.getDouble("nowqty"));

				goodslist.add(goods);
				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		myDb.EndExecute();

		String json_txt = gson.toJson(goodslist);
		return json_txt;
	}

	@ResponseBody
	@RequestMapping(value = { "/goodsadd.do" }, method = RequestMethod.POST)
	public String goods_add(String code, String name, String spec, String unit, String bar_code, Double sateqty,Model model, HttpSession httpSession) {
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
        
		sql = new StringBuilder("exec bg_sp_addgoods '");
		sql.append(code);
		sql.append("','");
		sql.append(name);
		sql.append("','");
		sql.append(spec);
		sql.append("','");
		sql.append(unit);
		sql.append("','");
		sql.append(bar_code);
		sql.append("','");
		sql.append(sateqty);
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
	@RequestMapping(value = { "/goodsupdate.do" }, method = RequestMethod.POST)
	public String goods_update(String code, String name, String spec, String unit, String bar_code, Double sateqty,Model model, HttpSession httpSession) {
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
		
		sql = new StringBuilder("update bg_goods set ");
		sql.append("name='");
		sql.append(name);
		sql.append("',spec='");
		sql.append(spec);
		sql.append("',unit='");
		sql.append(unit);
		sql.append("',bar_code='");
		sql.append(bar_code);
		sql.append("',sateqty='");
		sql.append(sateqty);
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
	@RequestMapping(value = { "/goodsdelete.do" }, method = RequestMethod.POST)
	public String goods_delete(@RequestParam("code") String code, Model model, HttpSession httpSession) {
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
        
		sql = new StringBuilder("delete bg_goods where code='");
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

	@RequestMapping(value = { "/supplier.do" }, method = RequestMethod.GET)
	public String supplier(Model supplier, Model model, HttpSession httpSession) {
		return "/base/supplier";
	}

	@ResponseBody
	@RequestMapping(value = { "/supplierlist.do" }, method = RequestMethod.POST)
	public String supplierlistFormField() {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		// 报错信息
		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		String sql = "exec Bg_sp_supplierlist";
		dbret = myDb.ExecuteQuery(sql);
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;
		List<Bg_supplier> supplierlist = new ArrayList<Bg_supplier>();

		try {
			while (rs.next()) {
				Bg_supplier supplier = new Bg_supplier();
				supplier.setCode(rs.getString("code"));
				supplier.setName(rs.getString("name"));
				supplier.setContact(rs.getString("contact"));
				supplier.setQq(rs.getString("qq"));
				supplier.setTel(rs.getString("tel"));
				supplier.setFax(rs.getString("fax"));
				supplier.setEmail(rs.getString("email"));
				supplier.setAddr(rs.getString("addr"));
				supplier.setBank(rs.getString("bank"));
				supplier.setBank_account(rs.getString("bank_account"));
				supplier.setRemark(rs.getString("remark"));

				supplierlist.add(supplier);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			return gson.toJson(e);
		}

		myDb.EndExecute();

		hashmap.put("total", supplierlist.size());
		hashmap.put("rows", supplierlist);

		return gson.toJson(hashmap);
	}

	@ResponseBody
	@RequestMapping(value = { "/supplieroper.do" }, method = RequestMethod.POST)
	public String supplier_oper(Model model, HttpSession httpSession, String code, String name, String contact,
			String qq, String tel, String fax, String addr, String bank, String bank_account, String remark,
			String email, int operType) {
		Gson gson = new Gson();
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());

			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);
		String sql = "exec Bg_sp_supplierOper  " + operType + ",'" + code + "','" + name + "','" + contact + "','" + qq
				+ "','" + tel + "','" + fax + "','" + addr + "','" + bank + "','" + bank_account + "','" + remark
				+ "','" + email + "'";
		dbret = myDb.ExecuteNonQuery(sql);
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
	 * 供应商 combobox用
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/supplierbox.do" }, method = RequestMethod.POST)
	public String supplierbox() {

		Gson gson = new Gson();
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());

			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		String sql = "exec Bg_sp_supplierlist";
		dbret = myDb.ExecuteQuery(sql);
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			return gson.toJson(es);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_supplier> supplierlist = new ArrayList<Bg_supplier>();

		try {
			while (rs.next()) {
				Bg_supplier supplier = new Bg_supplier();
				supplier.setCode(rs.getString("code"));
				supplier.setName(rs.getString("name"));
				supplier.setContact(rs.getString("contact"));
				supplier.setQq(rs.getString("qq"));
				supplier.setTel(rs.getString("tel"));
				supplier.setFax(rs.getString("fax"));
				supplier.setEmail(rs.getString("email"));
				supplier.setAddr(rs.getString("addr"));
				supplier.setBank(rs.getString("bank"));
				supplier.setBank_account(rs.getString("bank_account"));
				supplier.setRemark(rs.getString("remark"));

				supplierlist.add(supplier);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			return gson.toJson(e);
		}

		myDb.EndExecute();

		String json_txt = gson.toJson(supplierlist);

		return json_txt;
	}

	/**
	 * 用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/personlist.do" }, method = RequestMethod.POST)
	public String personlist() {

		Gson gson = new Gson();
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());

			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		String sql = "exec Bg_sp_getpersonlist";
		dbret = myDb.ExecuteQuery(sql);
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			myDb.EndExecute();
			return gson.toJson(es);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_person> personlist = new ArrayList<Bg_person>();

		try {
			while (rs.next()) {
				Bg_person person = new Bg_person();
				person.setpersonCode(rs.getString("PersonCode"));
				person.setpersonName(rs.getString("PersonName"));
				person.setDeptCode(rs.getString("DeptCode"));
				person.setDeptName(rs.getString("DeptName"));

				personlist.add(person);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			return gson.toJson(e);
		}

		myDb.EndExecute();

		String json_txt = gson.toJson(personlist);

		return json_txt;
	}

	/**
	 * 部门
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/deptmentlist.do" }, method = RequestMethod.POST)
	public String deptmentlist() {

		Gson gson = new Gson();
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());

			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);
		String sql = "exec Bg_sp_getdeptmentlist";
		dbret = myDb.ExecuteQuery(sql);
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			myDb.EndExecute();
			return gson.toJson(es);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_person> deptmentlist = new ArrayList<Bg_person>();

		try {
			while (rs.next()) {
				Bg_person dept = new Bg_person();
				dept.setDeptCode(rs.getString("DeptCode"));
				dept.setDeptName(rs.getString("DeptName"));

				deptmentlist.add(dept);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			return gson.toJson(e);
		}

		myDb.EndExecute();
		String json_txt = gson.toJson(deptmentlist);

		return json_txt;
	}

	// main
	@RequestMapping(value = { "/inout.do" }, method = RequestMethod.GET)
	public String inout(Model model, HttpSession httpSession, String billcode, int inoutMainID, int isQuote) {
		String usercode = (String) httpSession.getAttribute("UserCode");
		String deptcode = (String) httpSession.getAttribute("DeptCode");

		Gson gson = new Gson();
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());

			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Bg_sp_getInoutMainDetail '");
		sql.append(inoutMainID);
		sql.append("'");
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			myDb.EndExecute();
			return gson.toJson(es);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;
		Bg_inout inoutmain = new Bg_inout();
		try {
			if (rs.next()) {
				inoutmain.setBillCode(rs.getString("BillCode"));
				inoutmain.setInOutCode(rs.getString("InOutCode"));
				inoutmain.setInoutMainID(rs.getInt("InoutMainID"));
				inoutmain.setBillDate(rs.getString("BillDate"));
				inoutmain.setPersonCode(rs.getString("PersonCode"));
				inoutmain.setPersonName(rs.getString("PersonName"));
				inoutmain.setDeptCode(rs.getString("DeptCode"));
				inoutmain.setDeptName(rs.getString("DeptName"));
				inoutmain.setSupplierCode(rs.getString("SupplierCode"));
				inoutmain.setSupplierName(rs.getString("SupplierName"));
				inoutmain.setMaker(rs.getString("Maker"));
				inoutmain.setMakerName(rs.getString("MakerName"));
				inoutmain.setMakeDate(rs.getDate("Makedate"));
				inoutmain.setChecker(rs.getString("Checker"));
				inoutmain.setCheckerName(rs.getString("CheckerName"));
				inoutmain.setCheckDate(rs.getDate("CheckDate"));
				inoutmain.setWarehouseCode(rs.getString("WareHouseCode"));
				inoutmain.setRemark(rs.getString("Remark"));
			}

			hasResult = true;
		} catch (SQLException e) {
			e.printStackTrace();
			myDb.EndExecute();
			return gson.toJson(e);
		}

		if (inoutMainID == 0) {
			inoutmain.setPersonCode(usercode);
			inoutmain.setDeptCode(deptcode);

			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			inoutmain.setBillDate(df.format(date));
		}

		myDb.EndExecute();

		model.addAttribute("inoutmain", inoutmain);
		model.addAttribute("inoutMainID", inoutMainID);
		model.addAttribute("billcode", billcode);
		model.addAttribute("isQuote", isQuote);
		
		return "/apply/inout";
	}

	@RequestMapping(value = { "/inoutprint.do" }, method = RequestMethod.GET)
	public String inoutprint(Model model, HttpSession httpSession, String billcode, int inoutMainID) {
		String usercode = (String) httpSession.getAttribute("UserCode");
		String deptcode = (String) httpSession.getAttribute("DeptCode");

		Gson gson = new Gson();
		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());

			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Bg_sp_getInoutMainDetail '");
		sql.append(inoutMainID);
		sql.append("'");
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			myDb.EndExecute();
			return gson.toJson(es);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;
		Bg_inout inoutmain = new Bg_inout();
		try {
			if (rs.next()) {
				inoutmain.setBillCode(rs.getString("BillCode"));
				inoutmain.setInOutCode(rs.getString("InOutCode"));
				inoutmain.setInoutMainID(rs.getInt("InoutMainID"));
				inoutmain.setBillDate(rs.getString("BillDate"));
				inoutmain.setPersonCode(rs.getString("PersonCode"));
				inoutmain.setPersonName(rs.getString("PersonName"));
				inoutmain.setDeptCode(rs.getString("DeptCode"));
				inoutmain.setDeptName(rs.getString("DeptName"));
				inoutmain.setSupplierCode(rs.getString("SupplierCode"));
				inoutmain.setSupplierName(rs.getString("SupplierName"));
				inoutmain.setMaker(rs.getString("Maker"));
				inoutmain.setMakerName(rs.getString("MakerName"));
				inoutmain.setMakeDate(rs.getDate("Makedate"));
				inoutmain.setChecker(rs.getString("Checker"));
				inoutmain.setCheckerName(rs.getString("CheckerName"));
				inoutmain.setCheckDate(rs.getDate("CheckDate"));
				inoutmain.setWarehouseCode(rs.getString("WareHouseCode"));
				inoutmain.setRemark(rs.getString("Remark"));
			}

			hasResult = true;
		} catch (SQLException e) {
			e.printStackTrace();
			myDb.EndExecute();
			return gson.toJson(e);
		}

		myDb.EndExecute();

		model.addAttribute("inoutmain", inoutmain);
		model.addAttribute("inoutMainID", inoutMainID);
		model.addAttribute("billcode", billcode);

		return "/apply/inoutprint";
	}

	// sub
	@ResponseBody
	@RequestMapping(value = { "/inoutgoodslist.do" }, method = RequestMethod.POST)
	public String inoutgoodslistFormField(String billcode, int inoutMainID) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		// 报错信息
		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec bg_sp_getinoutgoods '");
		sql.append(billcode);
		sql.append("','");
		sql.append(inoutMainID);
		sql.append("'");
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_inoutgoods> goodslist = new ArrayList<Bg_inoutgoods>();
		try {
			while (rs.next()) {
				Bg_inoutgoods goods = new Bg_inoutgoods();
				goods.setMaterialcode(rs.getString("MaterialCode"));
				goods.setName(rs.getString("name"));
				goods.setSpec(rs.getString("spec"));
				goods.setUnit(rs.getString("unit"));
				goods.setUnitname(rs.getString("unitname"));
				goods.setSateqty(rs.getDouble("sateqty"));
				goods.setNowqty(rs.getDouble("nowqty"));
				goods.setPrice(rs.getDouble("Price"));
				goods.setTotalmoney(rs.getString("totalmoney"));
				if (inoutMainID > 0) {
					goods.setInoutMainID(rs.getInt("InoutMainID"));
					goods.setInoutSubID(rs.getInt("InoutSubID"));
					goods.setQuantity(rs.getDouble("Quantity"));
				}

				goodslist.add(goods);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		myDb.EndExecute();

		hashmap.put("total", goodslist.size());
		hashmap.put("rows", goodslist);

		return gson.toJson(hashmap);
	}

	// 新增 修改共用
	@ResponseBody
	@RequestMapping(value = { "/applyinout_saveAll.do" }, method = RequestMethod.POST)
	public String applyinout_saveAll(Model model, HttpSession httpSession, int inoutMainID, String billCode,
			String billDate, String deptCode, String personCode, String supplierCode, String remark, String datalist) {
		String userCode = (String) httpSession.getAttribute("UserCode");

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		DBHelperClass myDb = new DBHelperClass();
		Connection connection = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		Boolean dbret = false;
		Boolean hasResult = false;

		try {
			connection = (Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			hashmap.put("result", false);
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		// 新增
		String wareHouseCode = "01";
		if (inoutMainID == 0) {
			sql = new StringBuilder("exec Bg_sp_InoutInsert '");
			sql.append(billCode);
			sql.append("','");
			sql.append(billDate);
			sql.append("','");
			sql.append(personCode);
			sql.append("','");
			sql.append(deptCode);
			sql.append("','");
			sql.append(userCode);
			sql.append("','");
			sql.append(remark);
			sql.append("','");
			sql.append(wareHouseCode);
			sql.append("','");
			sql.append(supplierCode);
			sql.append("','");
			sql.append(datalist);
			sql.append("'");
		} else {
			sql = new StringBuilder("exec Bg_sp_InoutUpdate '");
			sql.append(inoutMainID);
			sql.append("','");
			sql.append(billCode);
			sql.append("','");
			sql.append(billDate);
			sql.append("','");
			sql.append(personCode);
			sql.append("','");
			sql.append(deptCode);
			sql.append("','");
			sql.append(userCode);
			sql.append("','");
			sql.append(remark);
			sql.append("','");
			sql.append(wareHouseCode);
			sql.append("','");
			sql.append(supplierCode);
			sql.append("','");
			sql.append(datalist);
			sql.append("'");
		}

		System.out.println(sql.toString());
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			myDb.EndExecute();

			hashmap.put("result", false);
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		String InOutMainID = "";
		rs = myDb.getResultSet();
		try {
			if (rs.next()) {
				InOutMainID = rs.getString("BillID");
				hasResult = true;
			}
		} catch (SQLException e) {
			myDb.EndExecute();

			hashmap.put("result", false);
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", true);
		hashmap.put("message", "执行成功！");
		hashmap.put("InOutMainID", InOutMainID);
		return gson.toJson(hashmap);
	}

	// 删除
	@ResponseBody
	@RequestMapping(value = { "/applyinout_Delete.do" }, method = RequestMethod.POST)
	public String applyinout_Delete(Model model, HttpSession httpSession, int inoutMainID) {
		String usercode = (String) httpSession.getAttribute("UserCode");

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		DBHelperClass myDb = new DBHelperClass();
		Connection connection = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		Boolean dbret = false;
		Boolean hasResult = false;

		try {
			connection = (Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			hashmap.put("result", false);
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		sql = new StringBuilder("exec Bg_sp_InoutDelete '");
		sql.append(inoutMainID);
		sql.append("','");
		sql.append(usercode);
		sql.append("'");

		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			myDb.EndExecute();

			hashmap.put("result", false);
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", true);
		hashmap.put("message", "执行成功！");

		return gson.toJson(hashmap);
	}

	// 审核 弃审
	@ResponseBody
	@RequestMapping(value = { "/applyinout_Action.do" }, method = RequestMethod.POST)
	public String applyinout_Action(Model model, HttpSession httpSession, int inoutMainID, int opertype) {
		String usercode = (String) httpSession.getAttribute("UserCode");

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		DBHelperClass myDb = new DBHelperClass();
		Connection connection = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		Boolean dbret = false;
		Boolean hasResult = false;

		try {
			connection = (Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			hashmap.put("result", false);
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		sql = new StringBuilder("exec Bg_sp_InoutAction '");
		sql.append(inoutMainID);
		sql.append("','");
		sql.append(opertype);
		sql.append("','");
		sql.append(usercode);
		sql.append("'");

		dbret = myDb.Execute(sql.toString());
		
		if (dbret == false) {
			myDb.EndExecute();

			hashmap.put("result", false);
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", true);
		hashmap.put("message", "执行成功！");

		return gson.toJson(hashmap);

	}

	/**
	 * 请购单列表查询
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/inoutlist.do" }, method = RequestMethod.GET)
	public String inoutlist(Model model, HttpSession httpSession, String billcode) {
		model.addAttribute("billcode", billcode);
		return "/apply/inoutlist";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = { "/inoutlistdata.do" }, method = RequestMethod.POST)
	public String inoutlistdata(String billcode, String billdate1, String billdate2, String inoutCode,String personcode, String deptcode, String supplierCode, String sort, String order) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		// 报错信息
		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Bg_sp_getInoutMain '");
		sql.append(billcode);
		sql.append("','");
		sql.append(billdate1);
		sql.append("','");
		sql.append(billdate2);
		sql.append("','");
		sql.append(personcode);
		sql.append("','");
		sql.append(deptcode);
		sql.append("','");
		sql.append(supplierCode);
		sql.append("','");
		sql.append(inoutCode);
		sql.append("','");
		sql.append(sort);
		sql.append("','");
		sql.append(order);
		sql.append("'");

		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_inout> inoutlist = new ArrayList<Bg_inout>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		try {
			while (rs.next()) {
				Bg_inout inout = new Bg_inout();
				inout.setBillCode(rs.getString("BillCode"));
				inout.setInOutCode(rs.getString("InOutCode"));
				inout.setInoutMainID(rs.getInt("InoutMainID"));
				inout.setBillDate(rs.getString("BillDate"));
				inout.setPersonCode(rs.getString("PersonCode"));
				inout.setPersonName(rs.getString("PersonName"));
				inout.setDeptCode(rs.getString("DeptCode"));
				inout.setDeptName(rs.getString("DeptName"));
				inout.setSupplierCode(rs.getString("SupplierCode"));
				inout.setSupplierName(rs.getString("SupplierName"));
				inout.setMaker(rs.getString("Maker"));
				inout.setMakerName(rs.getString("MakerName"));
				inout.setMakeDate(rs.getDate("Makedate"));
				inout.setChecker(rs.getString("Checker"));
				inout.setCheckerName(rs.getString("CheckerName"));
				inout.setCheckDate(rs.getDate("CheckDate"));
				inout.setRemark(rs.getString("Remark"));
				inout.setWarehouseCode(rs.getString("WareHouseCode"));

				inoutlist.add(inout);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("total", inoutlist.size());
		hashmap.put("rows", inoutlist);

		return gson.toJson(hashmap);
	}

	/**
	 * 请购单明细
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/inoutmingxi.do" }, method = RequestMethod.GET)
	public String inoutmingxi(Model model, HttpSession httpSession, String billcode) {
		model.addAttribute("billcode", billcode);
		return "/apply/inoutmingxi";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = { "/inoutmingxidata.do" }, method = RequestMethod.POST)
	public String inoutmingxidata(String billcode, String billdate1, String billdate2, String inoutCode,String personcode, String deptcode, String supplierCode, String materialcode, String sort, String order) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		// 报错信息
		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Bg_sp_getInoutMainMX '");
		sql.append(billcode);
		sql.append("','");
		sql.append(billdate1);
		sql.append("','");
		sql.append(billdate2);
		sql.append("','");
		sql.append(personcode);
		sql.append("','");
		sql.append(materialcode);
		sql.append("','");
		sql.append(deptcode);
		sql.append("','");
		sql.append(supplierCode);
		sql.append("','");
		sql.append(inoutCode);
		sql.append("','");
		sql.append(sort);
		sql.append("','");
		sql.append(order);
		sql.append("'");

		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_inout> inoutlist = new ArrayList<Bg_inout>();

		try {
			while (rs.next()) {
				Bg_inout inout = new Bg_inout();
				inout.setBillCode(rs.getString("BillCode"));
				inout.setInOutCode(rs.getString("InOutCode"));
				inout.setInoutMainID(rs.getInt("InoutMainID"));
				inout.setBillDate(rs.getString("BillDate"));
				inout.setPersonCode(rs.getString("PersonCode"));
				inout.setPersonName(rs.getString("PersonName"));
				inout.setDeptCode(rs.getString("DeptCode"));
				inout.setDeptName(rs.getString("DeptName"));
				inout.setSupplierCode(rs.getString("SupplierCode"));
				inout.setSupplierName(rs.getString("SupplierName"));
				inout.setMaker(rs.getString("Maker"));
				inout.setMakerName(rs.getString("MakerName"));
				inout.setMakeDate(rs.getDate("Makedate"));
				inout.setChecker(rs.getString("Checker"));
				inout.setCheckerName(rs.getString("CheckerName"));
				inout.setCheckDate(rs.getDate("CheckDate"));
				inout.setRemark(rs.getString("Remark"));
				inout.setMaterialcode(rs.getString("MaterialCode"));
				inout.setName(rs.getString("name"));
				inout.setUnitname(rs.getString("unitname"));
				inout.setPrice(rs.getDouble("Price"));
				inout.setQuantity(rs.getDouble("Quantity"));
				inout.setAmount(rs.getDouble("Amount"));

				inoutlist.add(inout);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			return gson.toJson(e);
		}

		myDb.EndExecute();

		hashmap.put("total", inoutlist.size());
		hashmap.put("rows", inoutlist);

		return gson.toJson(hashmap);
	}

	/**
	 * 请购单统计
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/inoutcount.do" }, method = RequestMethod.GET)
	public String inoutcount(Model model, HttpSession httpSession, String billcode) {
		model.addAttribute("billcode", billcode);
		return "/apply/inoutcount";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = { "/inoutcountdata.do" }, method = RequestMethod.POST)
	public String inoutcountdata(String billcode, String billdate1, String billdate2, String personcode,String deptcode, String supplierCode, String materialcode, String sort, String order, String groupby) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		// 报错信息
		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Bg_sp_getInoutCount '");
		sql.append(billcode);
		sql.append("','");
		sql.append(billdate1);
		sql.append("','");
		sql.append(billdate2);
		sql.append("','");
		sql.append(personcode);
		sql.append("','");
		sql.append(materialcode);
		sql.append("','");
		sql.append(deptcode);
		sql.append("','");
		sql.append(supplierCode);
		sql.append("','");
		sql.append(sort);
		sql.append("','");
		sql.append(order);
		sql.append("','");
		sql.append(groupby);
		sql.append("'");

		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_inout> inoutlist = new ArrayList<Bg_inout>();

		try {
			while (rs.next()) {
				Bg_inout inout = new Bg_inout();
				inout.setDeptCode(rs.getString("DeptCode"));
				inout.setDeptName(rs.getString("DeptName"));
				inout.setSupplierCode(rs.getString("SupplierCode"));
				inout.setSupplierName(rs.getString("SupplierName"));
				inout.setMaterialcode(rs.getString("MaterialCode"));
				inout.setName(rs.getString("name"));
				inout.setSpec(rs.getString("spec"));
				inout.setUnitname(rs.getString("unitname"));
				inout.setNowqty(rs.getDouble("nowqty"));
				inout.setQuantity(rs.getDouble("Quantity"));
				inout.setAmount(rs.getDouble("Amount"));
				inoutlist.add(inout);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			return gson.toJson(e);
		}

		myDb.EndExecute();

		hashmap.put("total", inoutlist.size());
		hashmap.put("rows", inoutlist);

		return gson.toJson(hashmap);
	}

	@RequestMapping(value = { "/orderreferapply.do" }, method = RequestMethod.GET)
	public String material(Model model, HttpSession httpSession) {
		String usercode = (String) httpSession.getAttribute("UserCode");
		String deptcode = (String) httpSession.getAttribute("DeptCode");

		model.addAttribute("usercode", usercode);
		model.addAttribute("deptcode", deptcode);

		return "/apply/orderreferapply";
	}

	@ResponseBody
	@RequestMapping(value = { "/ApplyInfo.do" }, method = RequestMethod.POST)
	public String ApplyInfo(String billdate1, String billdate2, String materialcode, String deptcode,String personcode) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		// 报错信息
		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Bg_sp_getApplyInfo '");
		sql.append(billdate1);
		sql.append("','");
		sql.append(billdate2);
		sql.append("','");
		sql.append(personcode);
		sql.append("','");
		sql.append(materialcode);
		sql.append("','");
		sql.append(deptcode);
		sql.append("'");

		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_inout> inoutlist = new ArrayList<Bg_inout>();

		try {
			while (rs.next()) {
				Bg_inout inout = new Bg_inout();

				inout.setMaterialcode(rs.getString("materialCode"));
				inout.setName(rs.getString("name"));
				inout.setSpec(rs.getString("spec"));
				inout.setUnitname(rs.getString("unitname"));
				inout.setNowqty(rs.getDouble("nowqty"));
				inout.setSafeQty(rs.getDouble("safeqty"));
				inout.setApplyQuantity(rs.getDouble("applyquantity"));
				inout.setQuantity(rs.getDouble("quantity"));
				inout.setPrice(rs.getDouble("price"));

				inoutlist.add(inout);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();

			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("total", inoutlist.size());
		hashmap.put("rows", inoutlist);

		return gson.toJson(hashmap);
	}

	@RequestMapping(value = { "/AccList.do" }, method = RequestMethod.GET)
	public String Bg_AccList(Model model, String BillCode) {
		model.addAttribute("BillCode", BillCode);
		return "/apply/AccList";
	}

	@ResponseBody
	@RequestMapping(value = { "/Bg_AccList.do" }, method = RequestMethod.POST)
	public String Bg_AccList(String BillCode, String BillDate1, String BillDate2, String SupplierCode) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Bg_sp_AccList '");
		sql.append(BillCode);
		sql.append("','");
		sql.append(BillDate1);
		sql.append("','");
		sql.append(BillDate2);
		sql.append("','");
		sql.append(SupplierCode);
		sql.append("'");

		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_AccList> inoutlist = new ArrayList<Bg_AccList>();

		try {
			while (rs.next()) {
				Bg_AccList inout = new Bg_AccList();

				inout.setBillMainID(rs.getString("BillMainID"));
				inout.setBillMainCode(rs.getString("BillMainCode"));
				inout.setBillCode(rs.getString("BillCode"));
				inout.setBillDate(rs.getString("BillDate"));
				inout.setSupplierCode(rs.getString("SupplierCode"));
				inout.setSupplierName(rs.getString("SupplierName"));
				inout.setTotalMoney(rs.getString("TotalMoney"));
				inout.setMaker(rs.getString("Maker"));
				inout.setMakerName(rs.getString("MakerName"));
				inout.setMakeDate(rs.getString("MakeDate"));
				inout.setReInoutMainID(rs.getString("ReInoutMainID"));
				inout.setReInoutMainID(rs.getString("InOutCode"));

				inoutlist.add(inout);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("total", inoutlist.size());
		hashmap.put("rows", inoutlist);

		return gson.toJson(hashmap);
	}

	@ResponseBody
	@RequestMapping(value = { "/add_AccList.do" }, method = RequestMethod.POST)
	public String add_AccList(HttpSession httpSession, String BillDate, String SupplierCode, String TotalMoney) {
		String UserCode = (String) httpSession.getAttribute("UserCode");
		Gson gson = new Gson();

		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Bg_sp_AccListInsert '");
		sql.append("02");
		sql.append("','");
		sql.append(BillDate);
		sql.append("','");
		sql.append(SupplierCode);
		sql.append("','");
		sql.append(TotalMoney);
		sql.append("','");
		sql.append(UserCode);
		sql.append("','");
		sql.append("0");
		sql.append("'");

		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());

			myDb.EndExecute();

			return gson.toJson(es);
		}

		myDb.EndExecute();

		return gson.toJson(es);
	}

	@ResponseBody
	@RequestMapping(value = { "/update_AccList.do" }, method = RequestMethod.POST)
	public String update_AccList(HttpSession httpSession, String BillMainID, String BillDate, String SupplierCode,
			String TotalMoney) {
		String UserCode = (String) httpSession.getAttribute("UserCode");
		Gson gson = new Gson();

		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec Bg_sp_AccListUpdate '");
		sql.append(BillMainID);
		sql.append("','");
		sql.append(BillDate);
		sql.append("','");
		sql.append(SupplierCode);
		sql.append("','");
		sql.append(TotalMoney);
		sql.append("'");

		System.out.print(sql.toString());
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

	@ResponseBody
	@RequestMapping(value = { "/delete_AccList.do" }, method = RequestMethod.POST)
	public String delete_AccList(HttpSession httpSession, String BillMainCode) {
		String UserCode = (String) httpSession.getAttribute("UserCode");
		Gson gson = new Gson();

		OldExecuteStatus es = new OldExecuteStatus();
		es.setResult(true);
		es.seteErorMessage("执行成功");

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			return gson.toJson(es);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("delete Bg_AccList where BillMainCode='");
		sql.append(BillMainCode);
		sql.append("'");

		System.out.println(sql.toString());

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

	@RequestMapping(value = { "/DuiZhang.do" }, method = RequestMethod.GET)
	public String DuiZhang(Model model) {
		return "/apply/DuiZhang";
	}

	@ResponseBody
	@RequestMapping(value = { "/DuiZhangList.do" }, method = RequestMethod.POST)
	public String DuiZhangList(String BillDate1, String BillDate2, String SupplierCode) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		List<OldExecuteStatus> ExecuteStatuslist = new ArrayList<OldExecuteStatus>();
		OldExecuteStatus es = new OldExecuteStatus();
		ExecuteStatuslist.add(es);

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec bg_getDuiZhang '");
		sql.append(BillDate1);
		sql.append("','");
		sql.append(BillDate2);
		sql.append("','");
		sql.append(SupplierCode);
		sql.append("'");

		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			es.setResult(false);
			es.seteErorMessage(myDb.getDbErrorMsg());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Bg_DuiZhang> inoutlist = new ArrayList<Bg_DuiZhang>();

		try {
			while (rs.next()) {
				Bg_DuiZhang inout = new Bg_DuiZhang();

				inout.setSupplierCode(rs.getString("SupplierCode"));
				inout.setSupplierName(rs.getString("SupplierName"));
				inout.setInitMoney(rs.getString("InitMoney"));
				inout.setYingFu(rs.getString("YingFu"));
				inout.setFuKuan(rs.getString("FuKuan"));
				inout.setEndMoney(rs.getString("EndMoney"));

				inoutlist.add(inout);

				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			
			es.setResult(false);
			es.seteErorMessage(e.getMessage());
			hashmap.put("total", -1);
			hashmap.put("rows", ExecuteStatuslist);
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("total", inoutlist.size());
		hashmap.put("rows", inoutlist);

		return gson.toJson(hashmap);
	}

}
