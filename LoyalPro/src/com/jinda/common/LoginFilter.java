package com.jinda.common;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {
	
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("doFilter here");

		if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {  
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");  
        }  
        HttpServletRequest httpRequest = (HttpServletRequest) request;  
        HttpServletResponse httpResponse = (HttpServletResponse) response;  
        HttpSession session = httpRequest.getSession(true);  
  
        StringBuffer url = httpRequest.getRequestURL();  
        System.out.println(url.toString());
 
        if (
        		(url.indexOf(".do")>=0)
        		&& (url.indexOf("/home/login.do")<0) 
        		&& (url.indexOf("/home/error.do")<0) 
        		&& (url.indexOf("/home/erpLogin.do")<0) 
        		&& (url.indexOf("/home/loginSubmit.do")<0) 
        		&& (url.indexOf("/app")<0)
        		&& (url.indexOf("/Attendance")<0)
        	)
        {
	        Object object = session.getAttribute("UserCode");  
	        if (object == null) {  
	            boolean isAjaxRequest = isAjaxRequest(httpRequest);  
	            if (isAjaxRequest) {  
	                httpResponse.setCharacterEncoding("UTF-8");  
	                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),  "您已经太长时间没有操作,请刷新页面");  
	            }  
	            
                httpResponse.setCharacterEncoding("UTF-8");  
                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),  "你未登陆，或者太长时间没有操作,请刷新重新登录");  
	            return;  
	        }
        }
        
        //除了登录，所有的app操作都得检测连接ID的合法性
        if(
        	(
        	url.indexOf("/app")>=0 
        	&&  url.indexOf("/app/UserLogin.do")<0 
        	&&  url.indexOf("/app/calendar.do")<0
       		&&  url.indexOf("/app/calendardata.do")<0
       		&&  url.indexOf("/app/updateCalendarData.do")<0
       		&&  url.indexOf("/app/getLatestVersion.do")<0
       		&&  url.indexOf("/app/getapk.do")<0
       		&& url.indexOf("/app/test.do")<0
       		)
        	||
        	url.indexOf("/Attendance")>=0
        ){    
        	String userCode=httpRequest.getHeader("userCode");
        	String linkID=httpRequest.getHeader("linkID");
        	
        	HashMap<String, Object> hashmap=App_UserLinkIDValid(userCode,linkID);
        	Gson gson = new Gson();
        	
        	if(hashmap.get("result").toString().equals("NO")){
        		
        		hashmap.put("result", "InValid");//替换result值，InValid表示ID失效。用以区分其他执行失败
                String data =gson.toJson(hashmap);
        		
        		httpResponse.setHeader("Content-type","text/html;charset=UTF-8");
                OutputStream stream = httpResponse.getOutputStream();
                stream.write(data.getBytes("UTF-8"));

                return;  
        	}
        }
        
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	private HashMap<String, Object> App_UserLinkIDValid(String userCode,String linkID){
		Connection connection=null;
		DBHelperClass myDb= new DBHelperClass();
		StringBuilder sql = new StringBuilder();
		Boolean dbret=false;
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
				
		//从连接池获取出错！！2015年11月15日
		try {
			Class.forName(ConfigDatas2.databasedbdriver);
			connection=DriverManager.getConnection(ConfigDatas2.databaseUrl,ConfigDatas2.databaseUser,ConfigDatas2.databasePasswd);
			myDb.setConn(connection);
		} catch (Exception e) {
			hashmap.put("result", "NO");
			hashmap.put("message", e.getMessage());
			return hashmap;
		}
		
		sql = new StringBuilder("exec App_sp_UserCheck '");
		sql.append(userCode);
		sql.append("','");
		sql.append(linkID);
		sql.append("'");
		
		dbret = myDb.ExecuteNonQuery(sql.toString());
		if (dbret == false) {
			hashmap.put("result", "NO");
			hashmap.put("message", myDb.getDbErrorMsg());
			
			myDb.EndExecute();
			
			return hashmap;
		}
		
		myDb.EndExecute();
		
		hashmap.put("result", "YES");
        
		return hashmap;
	}
	
	/** 
     * 判断是否为Ajax请求 
     * 
     * @param request HttpServletRequest 
     * @return 是true, 否false 
     */  
    public static boolean isAjaxRequest(HttpServletRequest request) {  
        return request.getRequestURI().startsWith("/api");  
		//        String requestType = request.getHeader("X-Requested-With");  
		//        return requestType != null && requestType.equals("XMLHttpRequest");  
    }  
  

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
