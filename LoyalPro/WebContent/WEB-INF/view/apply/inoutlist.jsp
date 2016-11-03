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
		<input name="billcode" id="billcode"  value="${billcode}" type="hidden">
			单据日期
			<input name="billdate1" id="billdate1"  value="2015-10-01" class="easyui-datebox"  data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
			--
			<input name="billdate2" id="billdate2"  value="getdate()" class="easyui-datebox"  data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
			单据号<input type="text" name="inoutCode" id="inoutCode" class="easyui-textbox" style="width:100px;"/>
			部门<input name="deptcode" id="deptcode" class="easyui-combobox"  data-options="valueField:'deptCode',textField:'deptName',url:'${webPath}/home/deptmentlist.do'" style="width:90px;">
			业务员<input name="personcode" id="personcode" select="8003" class="easyui-combobox"  data-options="valueField:'personcode',textField:'personname',url:'${webPath}/home/personlist.do'" style="width:100px;" >
			供应商<input name="supplierCode" id="supplierCode" class="easyui-combobox"  data-options="valueField:'code',textField:'name',url:'${webPath}/home/supplierbox.do'" >
			<a id="submit_search">查询</a>
		</form>
	</div>
</body>
<script type="text/javascript">
	$("#submit_search").linkbutton({
		iconCls : 'icon-search',
		plain : true
	}).click(function() {
		$('#dg').datagrid({
			queryParams : form2Json("searchform")
		}); //点击搜索
	});

	$(function() {
		var billcode=$("#billcode").val();
		var billName="";
		if(billcode=="00")
			billName="期初单";
		if(billcode=="01")
			billName="请购单";
		if(billcode=="02")
			billName="采购入库单";
		if(billcode=="03")
			billName="采购退库单";
		if(billcode=="04")
			billName="领用单";
		if(billcode=="05")
			billName="退还单";
		if(billcode=="06")
			billName="其它入库单";
		if(billcode=="07")
			billName="其它出库单";
		if(billcode=="11")
			billName="订单";
		
		document.title =billName;
		
		$("#dg").datagrid({
			url : "/JindaERP/home/inoutlistdata.do",
			queryParams : form2Json("searchform"),
			title:billName,
			toolbar:"#toolbar", 
			
			width: 1200,
			height: 500, 
			pagination:false,//分页
			rownumbers:true,//行序号
			fitColumns:false, //内容适应列
			singleSelect:true,//选上多行
			//固定列
			frozenColumns:[[
			
	        	]],
	        //其他列
			columns: [[
				{field: "inOutCode",title: "单据号",width:120,sortable:true},
				{field: "billDate",title: "单据日期",width:120,sortable:true},
				{field: "personName",title: "业务员",width:80,sortable:true},
				{field: "deptName",title: "部门",width:70,sortable:true},
				{field: "supplierName",title: "供应商",width:150,sortable:true},
				{field: "remark",title: "备注",width:150},
				{field: "makerName",title: "制单人",width:80},
				{field: "makeDate",title: "制单日期",width:120},
				{field: "checkerName",title: "审核人",width:80},
				{field: "checkDate",title: "审核日期",width:120}
				]],
				
				onDblClickRow: function (rowIndex, rowData) { 
					inoutMainID=rowData.inoutMainID;
					var src="${webPath}/home/inout.do?billcode="+billcode+"&inoutMainID="+inoutMainID+"&isQuote=0";
					parent.OpenNewTab(src,billName);
				},
				
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
				},
		});
	});
</script>
</html>