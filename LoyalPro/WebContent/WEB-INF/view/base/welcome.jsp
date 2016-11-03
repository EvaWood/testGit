<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% request.setAttribute("webPath", com.jinda.common.ConfigDatas2.webPath); %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<link rel="stylesheet" type="text/css" href="/JindaERP/static/css/easyui.css?version=<%=new Date()%>">
	<link rel="stylesheet" type="text/css" href="/JindaERP/static/css/icon.css?version=<%=new Date()%>">
	<link rel="stylesheet" type="text/css" href="/JindaERP/static/css/color.css?version=<%=new Date()%>">
	<script type="text/javascript" src="/JindaERP/static/js/jquery-1.7.2.min.js?version=<%=new Date()%>"></script>
	<script type="text/javascript" src="/JindaERP/static/js/jquery.easyui.min.js?version=<%=new Date()%>"></script>
	<script type="text/javascript" src="/JindaERP/static/js/jquery.edatagrid.js?version=<%=new Date()%>"></script>
	<script type="text/javascript" src="/JindaERP/static/js/common.js?version=<%=new Date()%>"></script>
	<script type="text/javascript" src="/JindaERP/static/js/bg1.js?version=<%=new Date()%>"></script>
	<script type="text/javascript" src="/JindaERP/static/js/bg2.js?version=<%=new Date()%>"></script>
</head>

<body>
	<h2>大华轴承 办公用品管理平台</h2>

	<table class="easyui-datagrid" id="dg" title="商品库存" url="/JindaERP/home/goodslist.do" toolbar="#toolbar" pagination="false" rownumbers="true" fitColumns="true" singleSelect="true" style="width:600px;height:400px" >
		<thead>
			<tr>
				<th field="code" width="50">商品编码</th>
				<th field="name" width="50">商品名称</th>
				<th field="spec" width="100">规格</th>
				<th field="unitname" width="50">单位</th>
				<th field="nowqty" width="50" align="right">现存</th>
			</tr>
		</thead>
	</table>
	
	<div id="toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="$('#dg').datagrid('reload')">刷新</a>
		</div>
</body>
</html>