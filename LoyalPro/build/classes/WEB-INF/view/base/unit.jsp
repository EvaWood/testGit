<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>计量单位</title>
		
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
		<table class="easyui-datagrid"  id="dg"  url="unitlist.do" toolbar="#toolbar"  title=""  pagination="false"  rownumbers="true" fitColumns="true" singleSelect="true" style="width: 500px; height: 465px" >
			<thead>
				<tr>
					<th field="code" width="50">计量单位编码</th>
					<th field="name" width="50">计量单位名称</th>
				</tr>
			</thead>
		</table>
	
		<div id="toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="NewUnit()">新增</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  plain="true" onclick="EditUnit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  plain="true" onclick="DeleteUnit()">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="$('#dg').datagrid('reload')">刷新</a>
		</div>
	
		<div class="easyui-dialog" id="dlg" buttons="#dlg-buttons" closed="true" style="width: 400px; height: 280px; padding: 10px 20px" >
			<div class="ftitle"></div>
			<form id="fm" method="post" novalidate>
				<div class="fitem">
					<label>计量单位编码:</label> <input name="code" id="code" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>计量单位名称:</label> <input name="name" id="name" class="easyui-textbox" required="true">
				</div>
			</form>
		</div>
	
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width: 90px">Save</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDialog()"style="width: 90px">Cancel</a>
		</div>
	
		<script type="text/javascript">
			var url;
			
			function NewUnit() {
				$('#dlg').dialog('open').dialog('setTitle', '新增 计量单位');
				$('#fm').form('clear');
				url = '/JindaERP/base/NewUnit.do';
			}
	
			function EditUnit() {
				var row = $('#dg').datagrid('getSelected');
				if (row) {
					$('#dlg').dialog('open').dialog('setTitle', '编辑 计量单位');
					$('#fm').form('load', row);
					url = '/JindaERP/base/EditUnit.do';
					$("#code").textbox("disable");
				}
				else{
					$.messager.alert('提示','请选中需要处理的数据！');
					return;
				}
			}
	
			function saveUser() {
				$("#code").textbox("enable");
				
				$('#fm').form('submit', {
					url : url,
					
					onSubmit : function() {
						return $(this).form('validate');
					},
					
					success : function(result) {
						var result = eval('(' + result + ')');
	
						$.messager.alert('提示',result.errormessage);
						
						if (result.result == true) {
							$('#dlg').dialog('close'); 
							$('#dg').datagrid('reload'); 
						}
					}
					
				});
			}
	
			function DeleteUnit() {
				var row = $('#dg').datagrid('getSelected');
				
				if (row) {
					$.messager.confirm('Confirm', '确认删除此数据?', function(r) {
						
						if (r) {
							$.post('/JindaERP/base/DeleteUnit.do', {code:row.code,random:new Date()}, function(result) {
								
								var result = eval('(' + result + ')');
								
								$.messager.alert('提示',result.errormessage);
								if (result.result==true) {
									$('#dg').datagrid('reload'); 
								} 
							
							});
						}
						
					});
				}
				else{
					$.messager.alert('提示','请选中需要处理的数据！');
					return;
				}
			}
			
			function closeDialog(){
				$("#code").textbox("enable");
				$('#dlg').dialog('close'); 
			}
		</script>
	
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
				width: 80px;
			}
		
			.fitem input {
				width: 160px;
			}
		</style>
	</body>
</html>