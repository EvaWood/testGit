<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("webPath", com.jinda.common.ConfigDatas2.webPath); %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>办公用品</title>
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
	<table id="dg">
	</table>

	<div id="toolbar">
		<form name="searchform" method="post" action="" id="searchform">
		<input name="billcode" id="billcode"  value="${billcode}" type="hidden">
			单据日期<input name="billdate1" id="billdate1"  value="2015-10-01" class="easyui-datebox" required data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
			--<input name="billdate2" id="billdate2"  value="getdate()" class="easyui-datebox" required data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
			商品名称<input name="materialcode" id="materialcode" class="easyui-combobox"  data-options="valueField:'code',textField:'name',url:'${webPath}/home/goodsbox.do'" style="width:80px;">
			部门<input name="deptcode" id="deptcode" class="easyui-combobox"  data-options="valueField:'deptCode',textField:'deptName',url:'${webPath}/home/deptmentlist.do'" style="width:100px;">
			业务员<input name="personcode" id="personcode" select="8003" class="easyui-combobox"  data-options="valueField:'personcode',textField:'personname',url:'${webPath}/home/personlist.do'" style="width:100px;" >
			供应商<input name="supplierCode" id="supplierCode" class="easyui-combobox"  data-options="valueField:'code',textField:'name',url:'${webPath}/home/supplierbox.do'" >
			统计方式<select class="easyui-combobox" name="groupby" id="groupby"style="width:120px;" required>
	<option value="0" selected>按商品</option>
	<option value="1">按部门和商品</option>
	<option value="2">按供应商和商品</option>
			</select>
			<a id="submit_search">查询</a>
		</form>
	</div>
</body>
<script type="text/javascript">
	$("#submit_search").linkbutton({
		iconCls : 'icon-search',
		plain : false
	}).click(function() {
		$('#dg').datagrid({
			queryParams : form2Json("searchform")
		}); //点击搜索
	});
	
		
	$(function() {
		var billcode=$("#billcode").val();
		var billName="";
		if(billcode=="00")
			billName="期初统计";
		if(billcode=="01")
			billName="请购统计";
		if(billcode=="02")
			billName="采购入库统计";
		if(billcode=="03")
			billName="采购退库统计";
		if(billcode=="04")
			billName="领用统计";
		if(billcode=="05")
			billName="退还统计";
		if(billcode=="06")
			billName="其它入库统计";
		if(billcode=="07")
			billName="其它出库统计";
		if(billcode=="11")
			billName="采购订单统计";
		
		document.title =billName;
		
		$("#dg").datagrid({
			url : "/JindaERP/home/inoutcountdata.do",
			queryParams : form2Json("searchform"),
			title:billName,
			toolbar:"#toolbar", 			
			width: 1200,
			height: 500, 
			pagination:false,//分页
			rownumbers:true,//行序号
			fitColumns:false, //内容适应列
			singleSelect:true,//选上多行
	        //其他列
			columns: [[
				{field: "deptName",title: "部门",width:120,sortable:true},
				{field: "supplierName",title: "供应商",width:120,sortable:true},
				{field: "name",title: "商品名称",width:120,sortable:true},
				{field: "spec",title: "规格",width:120,sortable:true},
				{field: "nowqty",title: "现存",width:70,align:'right'},
				{field: "unitname",title: "单位",width:70},
				{field: "quantity",title: "数量",width:70,align:'right'},
				{field: "amount",title: "金额",width:70,align:'right'}
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
					
				},
		});
		
	});
</script>
</html>