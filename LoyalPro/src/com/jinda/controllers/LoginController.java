package com.jinda.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinda.common.ConfigDatas2;
import com.jinda.common.DBHelperClass;
import com.jinda.models.UserInfo;
import com.jinda.models.UserRightsInfo;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

@Controller
@RequestMapping("/home")
public class LoginController {

	@Autowired
	private ComboPooledDataSource pooledDataSource;
	
	@RequestMapping(value="/erpLogin.do", method=RequestMethod.GET)
	public String erpLogin(Model model, String UserCode, HttpSession httpSession)
	{
		if (UserCode==null){
			UserCode="";
		}
        
        Connection connection=null;
		try {
			connection=(Connection) pooledDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
        	model.addAttribute("errorPage",e.getMessage());
        	return "/home/error";
		}
        
        DBHelperClass myDb= new DBHelperClass();
        Boolean dbret=false;
        
        myDb.setConn(connection);
        
        String sql="select PersonCode as UserCode,PersonName as UserName,DeptCode from Gy_Person where PersonCode='"+UserCode+"'";
        dbret=myDb.ExecuteQuery(sql);
        if (dbret==false)
        {
        	model.addAttribute("errorPage",myDb.getDbErrorMsg());
        	return "/home/error";
        }
        
        ResultSet rs=myDb.getResultSet();
        Boolean hasResult=false;
        UserInfo loginUserInfo=new UserInfo();
        
        try {
            while (rs.next()) 
            {
            	loginUserInfo.setUserCode(rs.getString("UserCode").trim());
            	loginUserInfo.setUserName(rs.getString("UserName").trim());
            	loginUserInfo.setDeptCode(rs.getString("DeptCode").trim());
            	
            	model.addAttribute("loginUserInfo",loginUserInfo);
            	
            	hasResult=true;
            }
			
		} catch (SQLException e) {
        	model.addAttribute("errorPage",e.getMessage());
			e.printStackTrace();
			myDb.EndExecute();
			return "/home/error";
		}

        if (hasResult==false)
        {
        	model.addAttribute("errorPage","没有这个用户");
        	return "/home/error";
        }        
        
        //获取权限表
        sql=" exec Gy_GetBGRights '"+UserCode+"'";
        dbret=myDb.ExecuteQuery(sql);
        if (dbret==false)
        {
        	model.addAttribute("errorPage",myDb.getDbErrorMsg());
        	return "/home/error";
        }
        rs=myDb.getResultSet();
        hasResult=false;
        UserRightsInfo userRightsInfo=new UserRightsInfo();
        try {
            while (rs.next()) 
            {
            	String kk=rs.getString("RightIndex");
            	userRightsInfo.addNew(kk);
            	
            	hasResult=true;
            }
			
		} catch (SQLException e) {
			// TODO: handle exception
        	model.addAttribute("errorPage",e.getMessage());
			e.printStackTrace();
			
			myDb.EndExecute();
			return "/home/error";
		}
        
    	myDb.EndExecute();
        if (hasResult==false)
        {
        	model.addAttribute("errorPage","没有办公用品权限");
        	return "/home/error";
        }        
		
        httpSession.setAttribute("UserCode", UserCode);
        httpSession.setAttribute("DeptCode", loginUserInfo.getDeptCode());
        httpSession.setAttribute("UserRightsInfo", userRightsInfo);
        
		return "/base/index";
	}
}
