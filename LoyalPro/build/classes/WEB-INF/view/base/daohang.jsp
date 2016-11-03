
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jinda.models.UserRightsInfo"%>
<% 
	request.setAttribute("webPath", com.jinda.common.ConfigDatas2.webPath);
	HttpServletRequest httpRequest = (HttpServletRequest)request;  
	HttpSession httpSession = httpRequest.getSession(true);
	UserRightsInfo userRightsInfo=(UserRightsInfo)httpSession.getAttribute("UserRightsInfo");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style type="text/css">
a{
text-decoration:none;
}
</style>

</head>
<body>
	<% if (userRightsInfo.findRight("Ywgn_BGBaseInfo")==true) { %>
		<div title="基础设置"  style="padding: 10px;overflow: hidden">
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/base/unit.do">计量单位</a></br></br>
			
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/goods.do">商品管理</a></br></br>
			
			<%-- <a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/base/suppliercategory.do">供应商分类</a></br></br>--%>
			
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/supplier.do">供应商</a></br></br>
		</div>
		<% } %>
		
		<% if (userRightsInfo.findRight("Ywgn_BGInit")==true) { %>
		<div title="期初设置"  style="padding: 10px;overflow: hidden">
			<a href="#" class="MenuLink easyui-linkbutton"  width="100px" src="${webPath}/home/inout.do?inoutMainID=0&billcode=00&isQuote=0&random=<%=new Date()%>">期初单</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutlist.do?billcode=00&random=<%=new Date()%>">期初单列表</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutmingxi.do?billcode=00&random=<%=new Date()%>">期初明细</a></br></br>
		</div>
		<% } %>
		
		
		<div title="商品请购"  style="padding: 10px;overflow: hidden">
		<% if (userRightsInfo.findRight("Ywgn_BGApply")==true) { %>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inout.do?inoutMainID=0&billcode=01&isQuote=0&random=<%=new Date()%>">请购单</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutlist.do?billcode=01&random=<%=new Date()%>">请购单列表</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutmingxi.do?billcode=01&random=<%=new Date()%>">请购明细</a></br></br>
			<% } %>
			
			<% if (userRightsInfo.findRight("Ywgn_BGApplyHuiZong")==true) { %>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutcount.do?billcode=01&random=<%=new Date()%>">请购统计</a></br></br>
			<% } %>
			</br></br>
		</div>
		
		<% if (userRightsInfo.findRight("Ywgn_BGOrder")==true) { %>
		<div title="采购订单"  style="padding: 10px;overflow: hidden">
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inout.do?inoutMainID=0&billcode=11&isQuote=0&random=<%=new Date()%>">订单</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/orderreferapply.do?random=<%=new Date()%>">订单(引用请购数据)</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutlist.do?billcode=11&random=<%=new Date()%>">订单列表</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutmingxi.do?billcode=11&random=<%=new Date()%>">订单明细</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutcount.do?billcode=11&random=<%=new Date()%>">订单统计</a></br></br>
			</br></br>
		</div><% } %>
		
		<% if (userRightsInfo.findRight("Ywgn_BGBuy")==true) { %>
		<div title="采购出入库"  style="padding: 10px;overflow: hidden">
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inout.do?inoutMainID=0&billcode=02&isQuote=0&random=<%=new Date()%>">采购入库单</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutlist.do?billcode=02&random=<%=new Date()%>">采购入库列表</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutmingxi.do?billcode=02&random=<%=new Date()%>">采购入库明细</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutcount.do?billcode=02&random=<%=new Date()%>">采购入库统计</a></br></br>
			
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inout.do?inoutMainID=0&billcode=03&isQuote=0&random=<%=new Date()%>">采购退库单</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutlist.do?billcode=03&random=<%=new Date()%>">采购退库列表</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutmingxi.do?billcode=03&random=<%=new Date()%>">采购退库明细</a></br></br>
		<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutcount.do?billcode=03&random=<%=new Date()%>">采购退库统计</a></br></br>
		</div><% } %>
		
		<% if (userRightsInfo.findRight("Ywgn_BGTakeOut")==true) { %>
		<div title="商品领取"  style="padding: 10px;overflow: hidden">
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inout.do?inoutMainID=0&billcode=04&isQuote=0&random=<%=new Date()%>">领用单</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutlist.do?billcode=04&random=<%=new Date()%>">领用列表</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutmingxi.do?billcode=04&random=<%=new Date()%>">领用明细</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutcount.do?billcode=04&random=<%=new Date()%>">领用统计</a></br></br>
			
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inout.do?inoutMainID=0&billcode=05&isQuote=0&random=<%=new Date()%>">退还单</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutlist.do?billcode=05&random=<%=new Date()%>">退还列表</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutmingxi.do?billcode=05&random=<%=new Date()%>">退还明细</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutcount.do?billcode=05&random=<%=new Date()%>">退还统计</a></br></br>
		</div><% } %>
		
		<% if (userRightsInfo.findRight("Ywgn_BGOther")==true) { %>
		<div title="其他出入库"  style="padding: 10px;overflow: hidden">
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inout.do?inoutMainID=0&billcode=06&isQuote=0&random=<%=new Date()%>">其它入库单</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutlist.do?billcode=06&random=<%=new Date()%>">其它入库列表</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutmingxi.do?billcode=06&random=<%=new Date()%>">其它入库明细</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutcount.do?billcode=06&random=<%=new Date()%>">其它入库统计</a></br></br>
			
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inout.do?inoutMainID=0&billcode=07&isQuote=0&random=<%=new Date()%>">其它出库单</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutlist.do?billcode=07&random=<%=new Date()%>">其它出库列表</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutmingxi.do?billcode=07&random=<%=new Date()%>">其它出库明细</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/inoutcount.do?billcode=07&random=<%=new Date()%>">其它出库统计</a></br></br>
		</div><% } %>
		
		<% if (userRightsInfo.findRight("Ywgn_BGDuiZhang")==true) { %>
		<div title="采购对账"  style="padding: 10px;overflow: hidden">
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/AccList.do?BillCode=01&random=<%=new Date()%>">应付单据</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/AccList.do?BillCode=02&random=<%=new Date()%>">付款单据</a></br></br>
			<a href="#" class="MenuLink easyui-linkbutton" src="${webPath}/home/DuiZhang.do?random=<%=new Date()%>">对账</a></br></br>
		</div>
		<% } %>
</body>

</html>