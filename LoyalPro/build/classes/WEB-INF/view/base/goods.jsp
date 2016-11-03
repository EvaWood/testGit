<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% request.setAttribute("webPath", com.jinda.common.ConfigDatas2.webPath); %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品管理</title>

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
	<table class="easyui-datagrid" id="dg" title="" url="goodslist.do" toolbar="#toolbar" pagination="false" rownumbers="true" fitColumns="true" singleSelect="true"
	   style="width:600px;height:465px" >
		<thead>
			<tr>
				<th field="code" width="50">商品编码</th>
				<th field="name" width="50">商品名称</th>
				<th field="spec" width="100">规格</th>
				<th field="unitname" width="50">单位</th>
				<th field="sateqty" width="50" align="right">安全库存</th>
				<th field="bar_code" width="50">条形码</th>
				<th field="nowqty" width="50" align="right">现存</th>
			</tr>
		</thead>
	</table>
	
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="goods_new()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="goods_edit()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="goods_destroy()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="$('#dg').datagrid('reload')">刷新</a>
	</div>
		
	<div id="dlg" class="easyui-dialog" style="width:400px;height:350px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle"></div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>商品编码:</label>
				<input name="code" id="code" class="easyui-textbox" required="true" >
			</div>
			<div class="fitem">
				<label>商品名称:</label>
				<input name="name" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>规格:</label>
				<input name="spec" class="easyui-textbox">
			</div>
			<div class="fitem">
				<label>单位:</label>
				<input name="unit" id="unit" class="easyui-combobox"  value="${inoutmain.supplierCode}"
					data-options="required:true,valueField:'code',textField:'name',url:'${webPath}/base/unitlistbox.do'"   />
			</div>
			<div class="fitem">
				<label>安全库存:</label>
				<input name="sateqty" class="easyui-textbox" data-options="required:true,validType:'intOrFloat'">
			</div>
			<div class="fitem">
				<label>条形码:</label>
				<input name="bar_code" class="easyui-textbox" >
			</div>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="goods_save()" style="width:90px">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDialog()" style="width:90px">取消</a>
	</div>
	
	<script type="text/javascript">

		var url;	
		
		function goods_new(){
			$('#dlg').dialog('open').dialog('setTitle','新增 商品');
			$('#fm').form('clear');
			url = '/JindaERP/home/goodsadd.do';
		}

		function goods_edit(){
			var row = $('#dg').datagrid('getSelected');
			
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','编辑 商品');
				$('#fm').form('load',row);
				url = '/JindaERP/home/goodsupdate.do';
				
				$("#code").textbox("disable");
			}
			else{
				$.messager.alert('提示','请选中需要处理的数据！');
				return;
			}
		}

		function goods_save(){
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

		function goods_destroy() {
			var row = $('#dg').datagrid('getSelected');
			
			if (row) {
				$.messager.confirm('Confirm', '确认删除此数据?', function(r) {
					
					if (r) {
						$.post('/JindaERP/home/goodsdelete.do', {code:row.code,random:new Date()}, function(result) {
							
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