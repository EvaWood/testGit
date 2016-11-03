<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<title>金达办公用品管理平台</title>	
	<link rel="stylesheet" type="text/css" href="/JindaERP/static/css/easyui.css?version=<%=new Date()%>">
		<link rel="stylesheet" type="text/css" href="/JindaERP/static/css/icon.css?version=<%=new Date()%>">
		<link rel="stylesheet" type="text/css" href="/JindaERP/static/css/color.css?version=<%=new Date()%>">
		<script type="text/javascript" src="/JindaERP/static/js/jquery-1.7.2.min.js?version=<%=new Date()%>"></script>
		<script type="text/javascript" src="/JindaERP/static/js/jquery.easyui.min.js?version=<%=new Date()%>"></script>
		<script type="text/javascript" src="/JindaERP/static/js/jquery.edatagrid.js?version=<%=new Date()%>"></script>
		<script type="text/javascript" src="/JindaERP/static/js/common.js?version=<%=new Date()%>"></script>
		<script type="text/javascript" src="/JindaERP/static/js/bg1.js?version=<%=new Date()%>"></script>
		<script type="text/javascript" src="/JindaERP/static/js/bg2.js?version=<%=new Date()%>"></script>
		
		<% 
	request.setAttribute("webPath", com.jinda.common.ConfigDatas2.webPath);%>
<style>

#top {
    text-shadow: 3px 3px 3px red, 6px 6px 6px black, 10px 10px 10px Blue;
    text-align: center;
}

#body {
    overflow-x: hidden;
    overflow-y: hidden;
}

.write_a a {
    color: #FFF;
    text-decoration: none;
}
</style>

<script>
    $(function() {
        initPage();
    });

    function initPage() {
        //读取动态变化的时间
        ReadDateTimeShow();

        //绑定菜单按钮的点击事件
        BindMenuClickEvent();

        //这里实现对时间动态的变化
        var setTimeInterval = setInterval(ReadDateTimeShow, 1000);

        //Tab页签的实现
        $("#worktab").tabs({});
    }

    
    //读取动态变化的时间
    function ReadDateTimeShow() {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1;
        var day = new Date().getDate();
        var time = new Date().toLocaleTimeString();
        var addDate = year + "年" + month + "月" + day + "日,时间:" + time;
        $("#date").text(addDate);
    }
</script>

</head>

<body class="easyui-layout">

    <!--------------------------------------网站头部开始（TOP）-------------------------------------->
    <div data-options="region:'north',split:false" style="height: 50px;">
        <div class="easyui-layout" data-options="fit:true" style="background: #3591c1;">
            <div style="text-align: left; background: #3591c1;">
                <div style="margin-left: 20px; float: left;">
                   	<span style="font-size: 19px; color: #FFF;"></span>
                    <span style="font-size: 15px; color: #FFF;" class="write_a">您好：${loginUserInfo.userName}</span>
                </div>
                <div style="clear: both;"></div>
            </div>
            <div style="text-align: right;">
                                    当前时间：<b id="date"></b>
            </div>
        </div>
    </div>

    <!--------------------------------------网站头部结束（TOP）-------------------------------------->

    <!--------------------------------------网站菜单栏开始（Left）-------------------------------------->
    <div data-options="region:'west',split:false" title="菜单导航" style="width: 170px; padding: 1px; overflow: hidden;">
        <div id="menubox" class="easyui-accordion" data-options="fit:true,border:false,selected:true,animat:true">
			<jsp:include page="/WEB-INF/view/base/daohang.jsp"></jsp:include>
        </div>
    </div>
    <!--------------------------------------网站结束（Left）-------------------------------------->

    <!--------------------------------------网站中间部分开始（Center）-------------------------------------->
    <div data-options="region:'center'" style="overflow: hidden;">
        <div id="worktab" fit="true">
            <div title="欢迎页面" data-options="cache:false">
                <iframe id="frmWorkArea" width="100%" height="99%" frameborder="0" scrolling="yes"  
                	src="${webPath}/base/welcome.do">
                </iframe>
            </div>
        </div>
    </div>
    <!--------------------------------------网站中间部分结束（Center）-------------------------------------->

    <!--------------------------------------网站右边菜单开始（Right）-------------------------------------->
    <div  data-options="region:'east',iconCls:'',split:false" title="" style="width: 240px;display:none">
        <div class="easyui-calendar" style="width: 230px; height: 230px;"></div>
        <div style="width: 230px"></div>
    </div>
    <!--------------------------------------网站右边菜单结束（Right）-------------------------------------->

    <!-------------------------------------网站头部开始（Foot）-------------------------------------->
    <div data-options="region:'south',split:false" title="" style="height: 30px; background: #3591c1;">
        <div class="easyui-layout" style="background: #3591c1;">
            <div style="text-align: center; padding: 5px;">
            	版权所有：深圳市众诚品业有限公司
            </div>
        </div>
    </div>
    <!--------------------------------------网站头部结束（Foot）-------------------------------------->

</body>
</html>