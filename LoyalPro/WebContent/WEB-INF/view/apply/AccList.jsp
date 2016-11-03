<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("webPath", com.jinda.common.ConfigDatas2.webPath); %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>办公用品</title>
	<link rel="stylesheet" type="text/css"href="/JindaERP/static/css/easyui.css?version=<%=new Date()%>">
	<link rel="stylesheet" type="text/css"href="/JindaERP/static/css/icon.css?version=<%=new Date()%>">
	<link rel="stylesheet" type="text/css"href="/JindaERP/static/css/color.css?version=<%=new Date()%>">
	<link rel="stylesheet" type="text/css"href="/JindaERP/static/css/demo.css?version=<%=new Date()%>">
	<script type="text/javascript"src="/JindaERP/static/js/jquery-1.7.2.min.js?version=<%=new Date()%>"></script>
	<script type="text/javascript"src="/JindaERP/static/js/jquery.easyui.min.js?version=<%=new Date()%>"></script>
	<script type="text/javascript" src="/JindaERP/static/js/common.js?version=<%=new Date()%>"></script>
	<script type="text/javascript" src="/JindaERP/static/js/bg2.js?version=<%=new Date()%>"></script>
</head>

<body>
	<table id="dg">
	</table>

	<div id="toolbar">
		<form name="searchform" method="post" action="" id="searchform">
			<input type="hidden" name="BillCode" value="${BillCode}" />
			
			单据日期
			<input name="BillDate1" id="BillDate1"  value="2015-01-01" class="easyui-datebox"  data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
			--
			<input name="BillDate2" id="BillDate2"  value="getdate()" class="easyui-datebox"  data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
		
			供应商<input name="SupplierCode" id="SupplierCode" class="easyui-combobox"  data-options="valueField:'code',textField:'name',url:'${webPath}/home/supplierbox.do'" >
			
			<a id="submit_search">查询</a>
			
			<%String billcode_=(String)request.getAttribute("BillCode");%>
			<%if(billcode_.equals("02")){ %>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="add_AccList()">新增</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  plain="true" onclick="update_AccList()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  plain="true" onclick="delete_AccList()">删除</a>
		<%} %>
		</form>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle"></div>
		<form id="fm" method="post" novalidate>
			<input type="hidden" name="BillMainID" id="BillMainID"/>
			<div class="fitem">
				<label>单据号:</label> <input name="BillMainCode" id="BillMainCode"  class="easyui-textbox"  style="width:150px;">
			</div>			
			<div class="fitem">
				<label>单据日期:</label> <input name="BillDate" id="BillDate" required="true"　value="2015-01-01" class="easyui-datebox"  data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:150px;">
			</div>
			<div class="fitem">
				<label>供应商　:</label> 
				<input name="SupplierCode" id="SupplierCode" required="true" class="easyui-combobox"  data-options="valueField:'code',textField:'name',url:'${webPath}/home/supplierbox.do'" style="width:150px;">
			</div>
			<div class="fitem">
				<label>金额　　:</label> 
				<input name="TotalMoney" class="easyui-textbox" required="true" style="width:150px;">
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save_AccList()" style="width: 90px">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"style="width: 90px">取消</a>
	</div>
	
</body>

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

<script type="text/javascript">
	$(function() {
		$("#dg").datagrid({
			url : "/JindaERP/home/Bg_AccList.do",
			queryParams : form2Json("searchform"),
			toolbar:"#toolbar", 
			
			width: 1200,
			height: 500, 
			pagination:false,//分页
			rownumbers:true,//行序号
			fitColumns:false, //内容适应列
			singleSelect:true,//选上多行
			
	        //其他列
			columns: [[
				{field: "BillMainID",title: "单据号",width:120,hidden:true},
				{field: "BillMainCode",title: "单据号",width:120},
				{field: "BillDate",title: "单据日期",width:120},
				{field: "SupplierCode",title: "供应商编码",width:150},
				{field: "SupplierName",title: "供应商名称",width:150},
				{field: "TotalMoney",title: "金额",width:80},
				{field: "MakerName",title: "制单人",width:80},
				{field: "MakeDate",title: "制单日期",width:120}
				]],
								
				onLoadSuccess:function(data){
					if(data.total=="-1"){
						$.messager.show({
							title:'出错了',
							msg:data.rows[0].errormessage,
							showType:'show',
							timeout:2000,
							style:{
								right:300,
								top:document.body.scrollTop+document.documentElement.scrollTop+100,
								bottom:'OK'
							}
						});
					}
					
					 //添加“合计”列
		             $('#dg').datagrid('appendRow', {
		            	 BillMainCode: '合计',
		            	 BillDate: '',
		            	 SupplierCode: '',
		            	 SupplierName: '',
		            	 TotalMoney: '<span class="subtotal">' + compute("TotalMoney") + '</span>',
		            	 MakerName: '',
		            	 MakeDate: ''
		             });
				},
		});
	});
	
	function compute(colName) {
        var rows = $('#dg').datagrid('getRows');
        var total = 0;
        for (var i = 0; i < rows.length; i++) {
            total += parseFloat(rows[i][colName]);
        }
        return total;
    }
	 
	 $("#submit_search").linkbutton({
			iconCls : 'icon-search',
			plain : false
		}).click(function() {
			$('#dg').datagrid({
				queryParams : form2Json("searchform")
			}); //点击搜索
		});
	
	var url;

	function add_AccList() {
		$('#dlg').dialog('open').dialog('setTitle', '新增付款单据');
		$('#fm').form('clear');
		url = 'add_AccList.do';
	}

	function update_AccList() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改付款单据');
			$('#fm').form('load', row);
			url = 'update_AccList.do';
			$("#BillMainCode").textbox("disable");
		}
		else{
			alert("请勾选需要处理的数据");	
			return;
		}
	}

	function save_AccList() {
		$("#BillMainCode").textbox("enable");
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				alert(result.errormessage);
				
				if (result.result == true) {
					$('#dlg').dialog('close'); // close the dialog
					$('#dg').datagrid('reload'); // reload the user data
				}
			}
		});
	}

	function delete_AccList() {
		var row = $('#dg').datagrid('getSelected');
		
		if (row) {
			$.messager.confirm('Confirm', '确认删除此数据?', function(r) {
				
				if (r) {
					$.post('delete_AccList.do', {BillMainCode:row.BillMainCode,random:new Date()}, function(result) {
						var result = eval('(' + result + ')');
						$.messager.show({ title : 'Error',msg : result.errormessage});
						if (result.result==true) {
							$('#dg').datagrid('reload'); 
						} 
					});
				}
				
			});
		}
		else{
			alert("请勾选需要处理的数据");
		}
	}

</script>
</html>