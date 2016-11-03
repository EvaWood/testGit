<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>供应商</title>
	
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
	<table id="dg" title="" url="supplierlist.do" class="easyui-datagrid" toolbar="#toolbar"  pagination="false" rownumbers="true" fitColumns="true" singleSelect="true"
	  style="width:1300px;height:465px" >
		<thead>
			<tr>
				<th field="code" width="90">供应商编码</th>
				<th field="name" width="120">供应商名称</th>
				<th field="contact" width="70">联系人</th>
				<th field="qq" width="100">QQ</th>
				<th field="tel" width="100">电话</th>
				<th field="fax" width="100">传真</th>
				<th field="email" width="100">邮箱</th>
				<th field="addr" width="250">地址</th>
				<th field="bank" width="70">银行</th>
				<th field="bank_account" width="120">银行账号</th>
				<th field="remark" width="200">备注</th>
			</tr>
		</thead>
	</table>
	
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="supplier_new('0')">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="supplier_edit('1')">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="supplier_destroy()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="$('#dg').datagrid('reload')">刷新</a>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:430px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle"></div>
		<form id="fm" method="post" novalidate>
		<input name="operType" id="operType" type="hidden"/>
			<div class="fitem">
				<label>编码:</label>
				<input name="code" class="easyui-textbox" required="true"  id="code" >
			</div>
			<div class="fitem">
				<label>名称:</label>
				<input name="name" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>联系人:</label>
				<input name="contact" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>QQ:</label>
				<input name="qq" class="easyui-textbox">
			</div>
			<div class="fitem">
				<label>电话:</label>
				<input name="tel" class="easyui-textbox">
			</div>
			<div class="fitem">
				<label>传真:</label>
				<input name="fax" class="easyui-textbox">
			</div>
			<div class="fitem">
				<label>邮箱:</label>
				<input name="email" class="easyui-textbox" data-options="validType:'email'">
			</div>
			<div class="fitem">
				<label>地址:</label>
				<input name="addr" class="easyui-textbox" >
			</div>
			<div class="fitem">
				<label>银行:</label>
				<input name="bank" class="easyui-textbox" >
			</div>
			<div class="fitem">
				<label>银行账号:</label>
				<input name="bank_account" class="easyui-textbox" >
			</div>
			<div class="fitem">
				<label>备注:</label>
				<input name="remark" class="easyui-textbox" >
			</div>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="supplier_save()" style="width:90px">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDialog()" style="width:90px">取消</a>
	</div>
	
	<script type="text/javascript">
		function supplier_new(operType){
			$('#dlg').dialog('open').dialog('setTitle','新增 供应商');
			$('#fm').form('clear');
			url = '/JindaERP/home/supplieroper.do';
			$("#operType").val(operType);
		}

		function supplier_edit(operType){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','修改 供应商');
				$('#fm').form('load',row);
				url = '/JindaERP/home/supplieroper.do';
				
				$("#operType").val(operType);
				$("#code").textbox("disable");
			}
			else{
				$.messager.alert('提示','请选中需要处理的数据！');
				return;
			}
		}

		function supplier_save(){
			$("#code").textbox("enable");
			
			$('#fm').form('submit',{
				url: url,
				
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					
					$.messager.alert('提示',result.errormessage);
					
					if (result.result==true){
						$('#dlg').dialog('close');	
						$('#dg').datagrid('reload');
					}
				}
			});
		}

		function supplier_destroy(operType) {
			var row = $('#dg').datagrid('getSelected');
			var operType="2";
			if (row) {
				$.messager.confirm('Confirm', '确认删除此数据?', function(r) {
					if (r) {
						$.post('/JindaERP/home/supplieroper.do', {code:row.code,operType:operType,random:new Date()}, function(result) {
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
		#fm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:80px;
		}
		.fitem input{
			width:160px;
		}
	</style>
</body>
</html>