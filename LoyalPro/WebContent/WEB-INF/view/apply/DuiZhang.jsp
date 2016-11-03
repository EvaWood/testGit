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
			<input name="BillDate1" id="BillDate1"  value="2015-10-01" class="easyui-datebox"  data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
			--
			<input name="BillDate2" id="BillDate2"  value="getdate()" class="easyui-datebox"  data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
		
			供应商<input name="SupplierCode" id="SupplierCode" class="easyui-combobox"  data-options="valueField:'code',textField:'name',url:'${webPath}/home/supplierbox.do'" >
			
			<a id="submit_search">搜索</a>
			
		</form>
	</div>
	
</body>


<script type="text/javascript">
	$(function() {
		$("#dg").datagrid({
			url : "/JindaERP/home/DuiZhangList.do",
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
				{field: "SupplierCode",title: "供应商编码",width:150},
				{field: "SupplierName",title: "供应商名称",width:150},
				{field: "InitMoney",title: "期初金额",width:120},
				{field: "YingFu",title: "本期应付",width:120},
				{field: "FuKuan",title: "本期付款",width:120},
				{field: "EndMoney",title: "期末余额",width:80}
				
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
		            	 SupplierCode: '合计',
		            	 SupplierName: '',
		            	 InitMoney:  '<span class="subtotal">' + compute("InitMoney") + '</span>',
		            	 YingFu:  '<span class="subtotal">' + compute("YingFu") + '</span>',
		            	 FuKuan: '<span class="subtotal">' + compute("FuKuan") + '</span>',
		            	 EndMoney:  '<span class="subtotal">' + compute("EndMoney") + '</span>'
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
			plain : true
		}).click(function() {
			$('#dg').datagrid({
				queryParams : form2Json("searchform")
			}); //点击搜索
		});
</script>
</html>