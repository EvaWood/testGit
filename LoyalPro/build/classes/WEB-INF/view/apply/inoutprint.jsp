<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jinda.models.UserRightsInfo"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>订单打印</title>
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
		<% 
			request.setAttribute("webPath", com.jinda.common.ConfigDatas2.webPath);
			String billcode_=(String)request.getAttribute("billcode");
			
			String billName="";
			if(billcode_.equals("00"))
				billName="期初单";
			if(billcode_.equals("01"))
				billName="请购单";
			if(billcode_.equals("02"))
				billName="采购入库单";
			if(billcode_.equals("03"))
				billName="采购退库单";
			if(billcode_.equals("04"))
				billName="领用单";
			if(billcode_.equals("05"))
				billName="退还单";
			if(billcode_.equals("06"))
				billName="其它入库单";
			if(billcode_.equals("07"))
				billName="其它出库单";
			if(billcode_.equals("11"))
				billName="订单";
		%>
	
	<div style="width: 800px; height: 280px; padding: 5px 5px">
		
		<input name="inoutMainID" id="inoutMainID" value="${inoutMainID}" type="hidden"/>
		<input name="billCode" id="billCode" value="${billcode}" type="hidden"/>
		
		<h1 style="padding-left:280px"><%=billName %></h1>
			
		<table style="width: 800px;">
				<tr>
					<th style="text-align: left;">
						<label>单据号:${inoutmain.inOutCode}</label>
					</th>
					<th style="text-align: left;">
						<label>单据日期:${inoutmain.billDate}</label> 
					</th>
				</tr>
				<tr><th></th><th></th></tr>
				<tr>
					<th style="text-align: left;" id="supplierCodeshow">
						<label>供应商:${inoutmain.supplierName}</label> 
					</th>
					<th style="text-align: left;">
						<label>备注:${inoutmain.remark}</label> 
					</th>
				</tr>
			</table>

		<div style="padding:5px"></div>
		
		<table id="dg" style="width: 800px; height: 320px;" rownumbers="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="materialcode" width="60px">商品编码</th>
					<th field="name" width="60px">商品名称</th>
					<th field="spec" width="60px">规格</th>
					<th field="unitname" width="60px">单位</th>
					<th field="quantity" width="60px">数量</th>
					<th field="price" width="60px">单价</th>
					<th field="totalMoney" width="60px">金额</th>
				</tr>
			</thead>
		</table>
		
		<script type="text/javascript">
		$(function(){        	
        	var billCode=$("#billCode").val();
        	var inoutMainID=$("#inoutMainID").val();
        	
        	$("#maker").textbox("disable");
        	$("#makerName").textbox("disable");
        	$("#makeDate").textbox("disable");
        	$("#checker").textbox("disable");
        	$("#checkerName").textbox("disable");
        	$("#checkDate").textbox("disable");
        	
        	//加载明细数据
            $('#dg').edatagrid({
                url: '${webPath}/home/inoutgoodslist.do?billcode='+billCode+'&inoutMainID='+inoutMainID+'&random='+new Date(),
                
                onBeforeEdit:function(index,row){
        			return false;
        	    },
        	    
        	    onLoadSuccess:function(data){
        	    	//添加“合计”列
                    $('#dg').datagrid('appendRow', {
                   	 materialcode: '合计',
                   	 name: '',
                   	 spec: '',
                   	 unitname: '',
                   	 quantity: '',
                   	 price: '',
                   	 totalMoney: '<span class="subtotal">' + compute("totalMoney") + '</span>'
                    });
        	    }
            });
        	
         	//查看
	        StatusOper(2);
         });
		
		function compute(colName) {
	        var rows = $('#dg').datagrid('getRows');
	        var total = 0;
	        for (var i = 0; i < rows.length; i++) {
	            total += parseFloat(rows[i][colName]);
	        }
	        return total;
	    }
    </script>
	</div>
	</body>
</html>