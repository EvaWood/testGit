package com.jinda.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jinda.models.*;
import com.erp.models.*;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.jinda.canteen.models.*;
import com.jinda.common.DBHelperClass;

@Controller
@RequestMapping(value = "/app")
public class AppController {
	
	@Autowired
	private ComboPooledDataSource pooledDataSource;
	
	//APP登录
	@ResponseBody
	@RequestMapping(value = { "/UserLogin.do" }, method = RequestMethod.POST)
	public String UserLogin(String userCode, String passWord,String deviceModel, String devicePlatForm, String deviceOsVersion, String deviceUuid) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
		ResultSet rs=null;
        
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}
        
		sql = new StringBuilder("exec App_sp_UserLogin '");
		sql.append(userCode);
		sql.append("','");
		sql.append(passWord);
		sql.append("','");
		sql.append(devicePlatForm);
		sql.append("','");
		sql.append(deviceOsVersion);
		sql.append("','");
		sql.append(deviceUuid);
		sql.append("','");
		sql.append(deviceModel);
		sql.append("'");
		
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			hashmap.put("result", "NO");
			hashmap.put("message", myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}
		
		rs = myDb.getResultSet();
		try {
			if (rs.next()) {
				hashmap.put("result", "YES");
				hashmap.put("linkID", rs.getString("LinkID").toString());
				hashmap.put("AppMenuCode", rs.getString("AppMenuCode").toString());
				hashmap.put("userCode", userCode);
				hashmap.put("passWord", passWord);
			}
		} catch (SQLException e) {
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			
			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		return gson.toJson(hashmap);
	}

	//获取当天的报餐信息【获取单个数据】
	@ResponseBody
	@RequestMapping(value = { "/getBaoCanInfo.do" }, method = RequestMethod.GET)
	public String getBaoCanInfo(Model model,String userCode,String WorkDay) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
		ResultSet rs=null;
        
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		try {
			connection = (Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		sql = new StringBuilder("exec bg_sp_getStaffMeal '");
		sql.append(userCode);
		sql.append("','");
		sql.append(WorkDay);
		sql.append("'");
		
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			hashmap.put("result", "NO");
			hashmap.put("message", myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}

	    rs = myDb.getResultSet();
	    HashMap<String, Object> info = new HashMap<String, Object>();
		try {
			if (rs.next()) {
				info.put("WuCan", rs.getBoolean("WuCan"));
				info.put("wuCanTime", rs.getString("wuCanTime"));
				info.put("WanCan", rs.getBoolean("WanCan"));
				info.put("wanCanTime", rs.getString("wanCanTime"));
				
				hashmap.put("result", "YES");
				hashmap.put("info", info);
			}
		} catch (SQLException e) {
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			
			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();
		
		return gson.toJson(hashmap);
	}
	
	//APP更新报餐信息【POST更新数据】
	@ResponseBody
	@RequestMapping(value = { "/updateBaoCanInfo.do" }, method = RequestMethod.GET)
	public String updateBaoCanInfo(String userCode, String iswucan,String chosetext,String date) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
        
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		try {
			connection = (Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}
		
		sql = new StringBuilder("exec bg_sp_updateSatffMeal '");
		sql.append(userCode);
		sql.append("','");
		sql.append(iswucan);
		sql.append("','");
		sql.append(chosetext);
		sql.append("','");
		sql.append(date);
		sql.append("'");
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			hashmap.put("result", "NO");
			hashmap.put("message", myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();
		
		hashmap.put("result", "YES");
		
		return gson.toJson(hashmap);
	}
	
	@RequestMapping(value = { "/calendar.do" }, method = RequestMethod.GET)
	public String calendar(Model model) {
		
		return "/Canteen/Calendar";
	}
	
	@ResponseBody
	@RequestMapping(value = { "/calendardata.do" }, method = RequestMethod.GET)
	public String calendardata(Model model ,String start,String end) {		
		Gson gson = new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			return gson.toJson(e.getMessage());
		}

		StringBuilder sql = new StringBuilder();
		sql = new StringBuilder("exec bg_sp_getWorkday '");
		sql.append(start);
		sql.append("','");
		sql.append(end);
		sql.append("'");
		
		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;
		
		myDb.setConn(connection);
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			
			myDb.EndExecute();
			
			return gson.toJson(myDb.getDbErrorMsg());
		}

		ResultSet rs = myDb.getResultSet();
		List<Bg_Workday> workdaylist=new ArrayList<Bg_Workday>();
		 
		try {
			while (rs.next()) {
				Bg_Workday info=new Bg_Workday();
				
				info.setId(rs.getString("id"));
				info.setTitle(rs.getString("title"));
				info.setStart(rs.getString("start"));
				
				workdaylist.add(info);
			}
		} catch (SQLException e) {
			myDb.EndExecute();
			return gson.toJson(e.getMessage());
		}

		myDb.EndExecute();

		return gson.toJson(workdaylist);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/updateCalendarData.do" }, method = RequestMethod.POST)
	public String updateCalendarData(String WorkDay, String Banci ) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
        
		ExecuteStatus es = new ExecuteStatus();
		es.setResult("0");
		es.seteMessage("执行成功");
		
		Gson gson = new Gson();

		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult("-1");
			es.seteMessage(e.getMessage());
			return gson.toJson(es);
		}
        
		sql = new StringBuilder("update Bg_Workday set Banci='");
		sql.append(Banci);
		sql.append("' where WorkDay='");
		sql.append(WorkDay);
		sql.append("'");
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			
			myDb.EndExecute();
			
			es.setResult("-1");
			es.seteMessage(myDb.getDbErrorMsg());
			return gson.toJson(es);
		}

		myDb.EndExecute();

		return gson.toJson(es);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/getLatestVersion.do" }, method = RequestMethod.GET)
	public String getLatestVersion(HttpServletRequest request) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String line;
		StringBuilder sb=new StringBuilder();
		
		try{
			System.out.println(request.getSession().getServletContext().getRealPath(""));
			File file=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/AppVersion.json"));
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
			BufferedReader br=new BufferedReader(read);
			while((line=br.readLine())!=null){
			    sb.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}
		
		HashMap<String, Object> ps = gson.fromJson(sb.toString(), new TypeToken<HashMap<String, Object>>(){}.getType());
		
		hashmap.put("result", "YES");
		hashmap.put("message", ps);
		return gson.toJson(hashmap);
	}
	
	private HashMap<String, Object> getappconfig(HttpServletRequest request){
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String line;
		StringBuilder sb=new StringBuilder();
		
		try{
			File file=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/AppVersion.json"));
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
			BufferedReader br=new BufferedReader(read);
			while((line=br.readLine())!=null){
			    sb.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return hashmap;
		}
		
		HashMap<String, Object> ps = gson.fromJson(sb.toString(), new TypeToken<HashMap<String, Object>>(){}.getType());
		
		hashmap.put("result", "YES");
		hashmap.put("message", ps.get("VersionCode").toString().replace(".0", ""));
		return hashmap; 
	}
	
	@RequestMapping(value = { "/getapk.do" }, method = RequestMethod.GET)
	public void getapk(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> abc=getappconfig(request);
		if(abc.get("result").equals("NO")){
			System.out.println(abc.get("message"));
			return ;
		}
		
		String VersionCode=abc.get("message").toString();
		String apkName="update_"+VersionCode+".apk";
		String apkpath="/WEB-INF/download/"+apkName;
		
		try{
			//设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(apkName, "UTF-8"));
			
			//读取要下载的文件，保存到文件输入流
			File file=new File(request.getSession().getServletContext().getRealPath(apkpath));
			FileInputStream in = new FileInputStream(file);
			
			//创建输出流
			OutputStream out = response.getOutputStream();
			
			//创建缓冲区
			byte buffer[] = new byte[1024];
		    int len = 0;
			//循环将输入流中的内容读取到缓冲区当中
			while((len=in.read(buffer))>0){
			    //输出缓冲区的内容到浏览器，实现文件下载
			    out.write(buffer, 0, len);
			}
			
			in.close();
			out.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return ;
		}
		return ;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/NowQuantityQuery.do" }, method = RequestMethod.POST)
	public String NowQuantityQuery(String MaterialCode,String GradeName) {		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			hashmap.put("result", -1);
			hashmap.put("message", e.getMessage()); //在log记录
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
	    sql = new StringBuilder("exec sp_getNowQuantityList 1,'");
		sql.append(MaterialCode);
		sql.append("','");
		sql.append(GradeName);
		sql.append("','");
		sql.append("006");
		sql.append("','");
		sql.append("1");
		sql.append("',1");
		
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			
			myDb.EndExecute();
			
			hashmap.put("result", "-1");
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Kf_NowQuantity> goodslist = new ArrayList<Kf_NowQuantity>();

		try {
			while (rs.next()) {
				Kf_NowQuantity goods = new Kf_NowQuantity();
				
				goods.setMaterialcode(rs.getString("MaterialCode"));
				goods.setGradecode(rs.getString("GradeCode"));
				goods.setGradename(rs.getString("GradeName"));
				goods.setMaterialareacode(rs.getString("MaterialAreaCode"));
				goods.setNowquantity(rs.getString("NowQuantity"));
				goods.setFactoutquantity(rs.getString("FactoutQuantity"));
				goods.setFactinquantity(rs.getString("FactInQuantity"));
				goods.setStartquantity(rs.getString("StartQuantity"));

				goodslist.add(goods);
				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			
			hashmap.put("result", -1);
			//hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", goodslist.size());
		hashmap.put("rows", goodslist);

		return gson.toJson(hashmap);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/NowQiHuoQuantity.do" }, method = RequestMethod.POST)
	public String NowQiHuoQuantity(String MaterialCode,String GradeName) {		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			hashmap.put("result", -1);
			hashmap.put("message", e.getMessage()); //在log记录
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		StringBuilder sql = new StringBuilder();
	    sql = new StringBuilder("exec sp_getMaterialFutureList_NewForApp '1','");
		sql.append(MaterialCode);
		sql.append("','");
		sql.append(GradeName);
		sql.append("','");
		sql.append("6'");
		
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			hashmap.put("result", "-1");
			hashmap.put("message", myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Kf_NowQiHuoQuantity> goodslist = new ArrayList<Kf_NowQiHuoQuantity>();

		try {
			while (rs.next()) {
				Kf_NowQiHuoQuantity goods = new Kf_NowQiHuoQuantity();
				
				goods.setMaterialCode(rs.getString("MaterialCode"));
				goods.setGradeCode(rs.getString("GradeCode"));
				goods.setGradeName(rs.getString("GradeName"));
				goods.setNowQuantity(rs.getString("NowQuantity"));
				goods.setOnOcean(rs.getString("OnOcean"));
				goods.setInWareHouse(rs.getString("InWareHouse"));
				goods.setInformQuantity(rs.getString("InformQuantity"));
				goods.setNowQuantityJiShouCang(rs.getString("NowQuantityJiShouCang"));
				goods.setNowQuantityOtherCang(rs.getString("NowQuantityOtherCang"));
				goods.setNext1(rs.getString("Next1"));
				goods.setNext2(rs.getString("Next2"));
				goods.setNext3(rs.getString("Next3"));
				goods.setNext4(rs.getString("Next4"));
				goods.setNext5(rs.getString("Next5"));
				goods.setNext6(rs.getString("Next6"));

				goodslist.add(goods);
				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			
			hashmap.put("result", -1);
			//hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", goodslist.size());
		hashmap.put("rows", goodslist);

		return gson.toJson(hashmap);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/PriceApplyList.do" }, method = RequestMethod.POST)
	public String PriceApplyList(HttpServletRequest request) {		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			hashmap.put("result", -1);
			hashmap.put("message", e.getMessage()); //在log记录
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		String UserCode=request.getHeader("userCode");
		
		Calendar cal=Calendar.getInstance();
		cal.add(java.util.Calendar.DATE, -20);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String stratdate = sf.format(cal.getTime());
		
		StringBuilder sql = new StringBuilder();
	    sql = new StringBuilder("exec Xs_sp_PriceApplyCheck 0,88,'','','");
		sql.append(stratdate);
		sql.append("','");
		sql.append("");
		sql.append("','");
		sql.append("006");
		sql.append("'");
		
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			
			myDb.EndExecute();
			
			hashmap.put("result", "-1");
			hashmap.put("message", myDb.getDbErrorMsg());
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Xs_PriceApply> infolist = new ArrayList<Xs_PriceApply>();

		try {
			while (rs.next()) {
				Xs_PriceApply info = new Xs_PriceApply();
				
				info.setCusCode(rs.getString("CusCode"));
				info.setCusName(rs.getString("CusName"));
				info.setPriceApplyCode(rs.getString("PriceApplyCode"));
				info.setPriceApplyMainID(rs.getString("PriceApplyMainID"));
				info.setMaker(rs.getString("Maker"));
				info.setMakerName(rs.getString("MakerName"));
				info.setChecker(rs.getString("Checker"));
				info.setCheckerName(rs.getString("CheckerName"));
				info.setResultMsg(rs.getString("ResultMsg"));

				infolist.add(info);
				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			
			hashmap.put("result", -1);
			//hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", infolist.size());
		hashmap.put("rows", infolist);

		return gson.toJson(hashmap);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/PriceApplyDetails.do" }, method = RequestMethod.POST)
	public String PriceApplyDetails(String PriceApplyMainID) {		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			hashmap.put("result", -1);
			hashmap.put("message", e.getMessage()); //在log记录
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		
		Calendar cal=Calendar.getInstance();
		cal.add(java.util.Calendar.DATE, -20);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String stratdate = sf.format(cal.getTime());
		
		StringBuilder sql = new StringBuilder();
	    sql = new StringBuilder("select * from Xs_V_PriceApplySub where PriceApplyMainID=");
		sql.append(PriceApplyMainID);
		
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			hashmap.put("result", "-1");
			hashmap.put("message", myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Xs_PriceApplyDetails> infolist = new ArrayList<Xs_PriceApplyDetails>();

		try {
			while (rs.next()) {
				Xs_PriceApplyDetails info = new Xs_PriceApplyDetails();
				
				info.setMaterialName(rs.getString("MaterialName"));
				info.setGradeName(rs.getString("GradeName"));
				info.setPriceApplySubID(rs.getString("PriceApplySubID"));
				info.setPriceApplyMainID(rs.getString("PriceApplyMainID"));
				info.setMaterialCode(rs.getString("MaterialCode"));
				info.setGradeCode(rs.getString("GradeCode"));
				info.setPriceApply(rs.getString("PriceApply"));
				info.setFee(rs.getString("Fee"));
				info.setQtyExpected(rs.getString("QtyExpected"));
				
				info.setOriginalRemark(rs.getString("OriginalRemark"));
				info.setPriceCheck1(rs.getString("PriceCheck1"));
				info.setPriceCheck2(rs.getString("PriceCheck2"));
				info.setFeeCheck(rs.getString("FeeCheck"));
				info.setEffecDate(rs.getString("EffecDate"));
				info.setQtyCheck(rs.getString("QtyCheck"));
				info.setRemark(rs.getString("Remark"));
				info.setPriceMainID(rs.getString("PriceMainID"));
				info.setNormalPrice(rs.getString("NormalPrice"));
				
				info.setCusAcceptPrice(rs.getString("CusAcceptPrice"));
				info.setComparePrice(rs.getString("ComparePrice"));
				info.setSellPrice(rs.getString("SellPrice"));
				info.setCostPrice(rs.getString("CostPrice"));
				info.setProfitRate(rs.getString("ProfitRate"));
				info.setIsCouDan(rs.getString("isCouDan"));

				infolist.add(info);
				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			
			hashmap.put("result", -1);
			//hashmap.put("message", e.getMessage());
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", infolist.size());
		hashmap.put("rows", infolist);

		return gson.toJson(hashmap);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/PriceCheckSubmit.do" }, method = RequestMethod.POST)
	public String PriceCheckSubmit(String PriceApplyMainID, String CheckResult,String isChaiFen,String ResultRemark,String EffecDate,String UserCode) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
        
		ExecuteStatus es = new ExecuteStatus();
		es.setResult("0");
		es.seteMessage("执行成功");
		
		Gson gson = new Gson();

		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult("-1");
			es.seteMessage(e.getMessage());
			return gson.toJson(es);
		}

		sql = new StringBuilder("exec Xs_sp_AppPriceApplyCheck ");
		sql.append(PriceApplyMainID);
		sql.append(",");
		sql.append(CheckResult);
		sql.append(",");
		sql.append(isChaiFen);
		sql.append(",'");
		sql.append(ResultRemark);
		sql.append("','");
		sql.append(EffecDate);
		sql.append("','");
		sql.append(UserCode);
		sql.append("'");
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			es.setResult("-1");
			es.seteMessage(myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(es);
		}

		myDb.EndExecute();

		return gson.toJson(es);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/PriceApplySubCheckSubmit.do" }, method = RequestMethod.POST)
	public String PriceApplySubCheckSubmit(String PriceApplyMainID, String PriceApplySubID,String PriceCheck1,String QtyCheck,String FeeCheck,String PriceCheck2,String EffecDate,String UserCode) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
        
		ExecuteStatus es = new ExecuteStatus();
		es.setResult(0);
		es.seteMessage("执行成功");
		
		Gson gson = new Gson();

		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(-1);
			es.seteMessage(e.getMessage());
			return gson.toJson(es);
		}

		sql = new StringBuilder("exec Xs_sp_AppPriceApplySubCheck ");
		sql.append(PriceApplyMainID);
		sql.append(",");
		sql.append(PriceApplySubID);
		sql.append(",");
		sql.append(PriceCheck1);
		sql.append(",'");
		sql.append(QtyCheck);
		sql.append("','");
		sql.append(FeeCheck);
		sql.append("','");
		sql.append(PriceCheck2);
		sql.append("','");
		sql.append(EffecDate);
		sql.append("','");
		sql.append(UserCode);
		sql.append("'");
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			es.setResult(-1);
			es.seteMessage(myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(es);
		}

		myDb.EndExecute();

		return gson.toJson(es);
	}

	
	@ResponseBody
	@RequestMapping(value = { "/PeiTaoJiaQuery.do" }, method = RequestMethod.POST)
	public String PeiTaoJiaQuery(HttpServletRequest request ,String CusCode) {
		String userCode=request.getHeader("userCode");
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			hashmap.put("result", -1);
			hashmap.put("message", e.getMessage()); //在log记录
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);
		
		StringBuilder sql = new StringBuilder();
	    sql = new StringBuilder("exec Xs_Sp_GetAllPeiTaoJia '");
		sql.append(CusCode);
		sql.append("','");
		sql.append(userCode);
		sql.append("',1,''");
		
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			hashmap.put("result", "-1");
			hashmap.put("message", myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Xs_PeiTaoJia> infolist = new ArrayList<Xs_PeiTaoJia>();

		try {
			while (rs.next()) {
				Xs_PeiTaoJia info = new Xs_PeiTaoJia();
				
				info.setMaterialCode(rs.getString("MaterialCode"));
				info.setGradeName(rs.getString("GradeName"));
				info.setGradeCode(rs.getString("GradeCode"));
				info.setSellPrice(rs.getString("SellPrice"));
				info.setStartDate(rs.getString("StartDate"));
				info.setEndDate(rs.getString("EndDate"));

				infolist.add(info);
				hasResult = true;
			}

		} catch (SQLException e) {
			myDb.EndExecute();
			
			hashmap.put("result", -1);
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", infolist.size());
		hashmap.put("rows", infolist);

		return gson.toJson(hashmap);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/QianKuanShenPiList.do" }, method = RequestMethod.POST)
	public String QianKuanShenPiList(HttpServletRequest request) {		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		Gson gson = new Gson();

		Connection connection = null;
		try {
			connection = (Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			hashmap.put("result", -1);
			hashmap.put("message", e.getMessage()); //在log记录
			return gson.toJson(hashmap);
		}

		DBHelperClass myDb = new DBHelperClass();
		Boolean dbret = false;

		myDb.setConn(connection);

		String UserCode=request.getHeader("userCode");
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		
		cal.add(java.util.Calendar.DATE, 1);
		String nowdate = sf.format(cal.getTime());
		
		cal.add(java.util.Calendar.DATE, -7);
		String stratdate = sf.format(cal.getTime());
		
		StringBuilder sql = new StringBuilder();
	    sql = new StringBuilder("exec XS_sp_QianKuanShenPiQuery '','','3','0','");
		sql.append("2016-07-01");
		sql.append("','");
		sql.append(nowdate);
		sql.append("','");
		sql.append(UserCode);
		sql.append("','");
		sql.append("4'");
		
		dbret = myDb.ExecuteQuery(sql.toString());
		if (dbret == false) {
			hashmap.put("result", -1);
			hashmap.put("message", myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}

		ResultSet rs = myDb.getResultSet();
		Boolean hasResult = false;

		List<Xs_QianKuanShenPi> infolist = new ArrayList<Xs_QianKuanShenPi>();

		try {
			while (rs.next()) {
				Xs_QianKuanShenPi info = new Xs_QianKuanShenPi();
				
				info.setInOutMainID(rs.getString("InOutMainID"));
				info.setInOutCode(rs.getString("InOutCode"));
				info.setCusCode(rs.getString("CusCode"));
				info.setCusName(rs.getString("CusName"));
				info.setBillMoney(rs.getString("BillMoney"));
				info.setGuoQiMoney(rs.getString("GuoQiMoney"));
				info.setGuoQiDays(rs.getString("GuoQiDays"));
				info.setReturnMsg(rs.getString("ReturnMsg"));
				
				info.setPromisePayMoney(rs.getString("PromisePayMoney"));
				info.setPromisePayDate(rs.getString("PromisePayDate"));
				info.setApplyMsg(rs.getString("ApplyMsg"));
				info.setMaker(rs.getString("Maker"));
				info.setMakerName(rs.getString("MakerName"));
				info.setMakerEmail(rs.getString("MakerEmail"));
				info.setMakeDate(rs.getString("MakeDate"));
				
				info.setDeptMsg(rs.getString("DeptMsg"));
				info.setDeptFlag(rs.getString("DeptFlag"));
				info.setDeptChecker(rs.getString("DeptChecker"));
				info.setDeptCheckerName(rs.getString("DeptCheckerName"));
				info.setDeptCheckDate(rs.getString("DeptCheckDate"));
				
				info.setCompanyMsg(rs.getString("CompanyMsg"));
				info.setCompanyFlag(rs.getString("CompanyFlag"));
				info.setCompanyChecker(rs.getString("CompanyChecker"));
				info.setCompanyCheckerName(rs.getString("CompanyCheckerName"));
				info.setCompanyCheckDate(rs.getString("CompanyCheckDate"));
				
				info.setManagerCode(rs.getString("ManagerCode"));
				info.setManagerName(rs.getString("ManagerName"));
				info.setManagerEmail(rs.getString("ManagerEmail"));
				info.setRealPayMoney(rs.getString("RealPayMoney"));
				info.setRealPayDate(rs.getString("RealPayDate"));
				
				info.setCWFlag(rs.getString("CWFlag"));
				info.setCWMsg(rs.getString("CWMsg"));
				info.setCWFeedBackPerson(rs.getString("CWFeedBackPerson"));
				info.setCWFeedBackPersonName(rs.getString("CWFeedBackPersonName"));
				info.setCWFeedBackDate(rs.getString("CWFeedBackDate"));

				infolist.add(info);
				hasResult = true;
			}

		} catch (SQLException e) {
			hashmap.put("result", -1);
			hashmap.put("message", e.getMessage());

			myDb.EndExecute();
			
			return gson.toJson(hashmap);
		}

		myDb.EndExecute();

		hashmap.put("result", infolist.size());
		hashmap.put("rows", infolist);

		return gson.toJson(hashmap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = { "/QianKuanShenPiCheckSubmit.do" }, method = RequestMethod.POST)
	public String QianKuanShenPiCheckSubmit(String InOutMainID,String CompanyFlag, String CompanyMsg,String UserCode) {
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
        
		ExecuteStatus es = new ExecuteStatus();
		es.setResult(0);
		es.seteMessage("执行成功");
		
		Gson gson = new Gson();

		try {
			connection=(Connection) pooledDataSource.getConnection();
			myDb.setConn(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			es.setResult(-1);
			es.seteMessage(e.getMessage());
			return gson.toJson(es);
		}

		sql = new StringBuilder("exec Xs_sp_QianKuanShenPiAction 3,");
		sql.append(InOutMainID);
		sql.append(",'");
		sql.append(UserCode);
		sql.append("','");
		sql.append(CompanyMsg);
		sql.append("',");
		sql.append(CompanyFlag);
		sql.append(",");
		sql.append(0);
		sql.append(",'");
		sql.append("2000-01-01");
		sql.append("'");
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			es.setResult("-1");
			es.seteMessage(myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return gson.toJson(es);
		}

		myDb.EndExecute();

		return gson.toJson(es);
	}

	
	@ResponseBody
	@RequestMapping(value = { "/test.do" }, method = RequestMethod.GET)
	public String test(HttpServletRequest request) throws IOException {
		
		System.out.println("请求的URL地址"+request.getRequestURL());
		System.out.println("请求的资源"+request.getRequestURI());
		System.out.println("URL地址中附带的参数"+request.getQueryString());
		System.out.println("来访者的IP地址"+request.getRemoteAddr());
		System.out.println("来访的主机名"+request.getRemoteHost());
		System.out.println("端口号"+request.getRemotePort());
		System.out.println("?"+request.getRemoteUser());
		System.out.println(request.getMethod());
		System.out.println("获取WEB服务器的IP地址"+request.getLocalAddr());
		System.out.println("获取WEB服务器的主机名"+request.getLocalName());
		
		System.out.println(request.getServletPath());
		System.out.println(request.getContextPath());
		
		ServletContext context= request.getServletContext();
		InputStream in=context.getResourceAsStream("/WEB-INF/classes/config/dbconfig.properties");
		Properties prop=new Properties();  
		prop.load(in);
		String dburl = prop.getProperty("dburl");
        String dbuser = prop.getProperty("dbuser");
        System.out.println(dburl);
        System.out.println(dbuser);
        
        String path = context.getRealPath("/WEB-INF/classes/config/application.properties");
        InputStream in1 = new FileInputStream(path);
        prop.load(in1);
        System.out.println(path);
        System.out.println(prop.getProperty("msg"));
        System.out.println(prop.getProperty("rootPath"));
					
		return "";
	}

}
