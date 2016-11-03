package com.jinda.common;

import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;

import org.springframework.beans.factory.annotation.Autowired;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * 原则：凡是返回 false 的，返回之前需要调用 End处理，并且记录错误信息。
 */

public class DBHelperClass {
	
	private Connection conn=null;
	
	private Statement stmt=null;
	private CallableStatement stmtmulit=null;

	public CallableStatement getStmtmulit() {
		return stmtmulit;
	}


	private  ResultSet rs=null;
	private  String dbErrorMsg;
	public String getDbErrorMsg() {
		return dbErrorMsg;
		/*
		try {
			return new String(dbErrorMsg.getBytes("GB2312"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return dbErrorMsg;
		}
		*/
	}
	
	public DBHelperClass()
	{
		;
	}
	
	public ResultSet getResultSet()
	{
		
		return rs;
		
	}
	
	public void setConn(Connection conn) {
		this.conn = conn;
		
		System.out.println(conn.hashCode());
	}

	/*
	public Boolean GetConnection(String driver, String url, String user, String passwd)
	{
		dbErrorMsg="";
		try{
			Class.forName(driver);
			conn=DriverManager.getConnection(url,user,passwd);
		}
		catch (ClassNotFoundException e1)
		{
			dbErrorMsg=e1.getMessage();
			e1.printStackTrace();
			return false;
		}
		catch (SQLException e1)
		{
			dbErrorMsg=e1.getMessage();
			e1.printStackTrace();
			return false;
		}
		catch (Exception e1)
		{
			dbErrorMsg=e1.getMessage();
			e1.printStackTrace();
			
			return false;
		}
		
		return true;
		
	}
	*/
	
	public Boolean ExecuteNonQuery(String sql) 
	{
		System.out.println("sql begin :"+sql);
		
		dbErrorMsg="";

		if (conn==null)
		{
			dbErrorMsg="No db connections found.";

			EndExecute();
			return false;
		}
		
		stmt=null;
		try{
			stmt=conn.createStatement();
			stmt.executeUpdate(sql);
		}
		catch (SQLException ex)
		{
			dbErrorMsg=ex.getMessage();
			ex.printStackTrace();

			EndExecute();
			return false;
		}

		System.out.println("sql end :"+sql);
		
		return true;
	}
	
	public Boolean ExecuteQuery(String sql) 
	{
		System.out.println("sql begin :"+sql);
		
		dbErrorMsg="";
		rs=null;
		
		if (conn==null)
		{
			dbErrorMsg="No db connections found.";

			EndExecute();
			return false;
		}
		
		stmt=null;
		try{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
		}
		catch (SQLException ex)
		{
			dbErrorMsg=ex.getMessage();
			ex.printStackTrace();

			EndExecute();
			return false;
		}

		System.out.println("sql end :"+sql);
	
		return true;
	}
	
	public Boolean Execute(String sql) 
	{
		System.out.println("sql begin :"+sql);
		
		dbErrorMsg="";
		rs=null;
		
		if (conn==null)
		{
			dbErrorMsg="No db connections found.";

			EndExecute();
			return false;
		}
		
		stmt=null;
		try{
			stmt=conn.createStatement();
			stmt.execute(sql);
		}
		catch (SQLException ex)
		{
			dbErrorMsg=ex.getMessage();
			ex.printStackTrace();

			EndExecute();
			return false;
		}

		System.out.println("sql end :"+sql);
	
		return true;
	}

	
	public Boolean ExecuteQueryMultiResult(String sql) 
	{
		dbErrorMsg="";
		rs=null;
		
		System.out.println("sql begin :"+sql);
		
		if (conn==null)
		{
			dbErrorMsg="No db connections found.";

			EndExecute();
			return false;
		}
		
		stmtmulit=null;
		try{
			stmtmulit=conn.prepareCall(sql);
			stmtmulit.execute(sql);
		}
		catch (SQLException ex)
		{
			dbErrorMsg=ex.getMessage();
			ex.printStackTrace();

			EndExecute();
			return false;
		}

		System.out.println("sql end :"+sql);
		
		return true;
	}
	
	
	public void EndExecute()
	{
		System.out.println("EndExecute");
		
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		rs=null;
		stmt=null;
		conn=null;
	}

}
