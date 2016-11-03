package com.jinda.common;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//http://blog.csdn.net/lufeng20/article/details/7598564
@Component("ConfigDatas2")
public class ConfigDatas2 {

	public static String webPath;
	
	public static String databasedbdriver;
	public static String databaseUrl;
	public static String databaseUser;
	public static String databasePasswd;
	
	@PostConstruct
	public void Init()
	{
		webPath=rootPath;
		databasedbdriver=dbdriver;
		databaseUrl=dburl;
		databaseUser=dbuser;
		databasePasswd=dbpasswd;
		System.out.println("ConfigDatas2  @PostConstruct  : " + webPath);
	}
	
    @Value("${rootPath}")
    private String rootPath;
    
    @Value("${dbdriver}")
    private String dbdriver;
    
    @Value("${dburl}")
    private String dburl;
    
    @Value("${dbuser}")
    private String dbuser;
    
    @Value("${dbpasswd}")
    private String dbpasswd;

    public ConfigDatas2() {
    	System.out.println("ConfigDatas2 初始化");
	}
}
