<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>供应商分类</title>

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

	<table title="供应商分类" url="list_suppliercategory.do"
		id="dg" toolbar="#toolbar" class="easyui-datagrid" style="width: 500px; height: 450px" pagination="false" rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="code" width="50">供应商分类编码</th>
				<th field="name" width="50">供应商分类名称</th>
			</tr>
		</thead>
	</table>

	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="add_suppliercategory('1')">新增</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  plain="true" onclick="edit_suppliercategory('3')">编辑</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  plain="true" onclick="destroy_suppliercategory('2')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="$('#dg').datagrid('reload')">刷新</a>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">供应商分类</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<input type="hidden" name="operType" id="operType"/>
				<label>供应商分类编码:</label> <input name="code" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>供应商分类名称:</label> <input name="name" class="easyui-textbox" required="true">
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save_suppliercategory()" style="width: 90px">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"style="width: 90px">取消</a>
	</div>

	<style type="text/css">
		#fm {
			margin: 0;
			padding: 10px 30px;
		}
		
		.ftitle {
			font-size: 14px;
			font-weight: bold;
			padding: 5px 0;
			margin-bottom: 10px;
			border-bottom: 1px solid #ccc;
		}
		
		.fitem {
			margin-bottom: 5px;
		}
		
		.fitem label {
			display: inline-block;
			width: 100px;
		}
		
		.fitem input {
			width: 160px;
		}
	</style>

</body>
</html>