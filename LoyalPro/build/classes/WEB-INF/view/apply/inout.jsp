<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jinda.models.UserRightsInfo"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
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
			HttpServletRequest httpRequest = (HttpServletRequest)request;  
			HttpSession httpSession = httpRequest.getSession(true);
			UserRightsInfo userRightsInfo=(UserRightsInfo)httpSession.getAttribute("UserRightsInfo");
		%>
		
		<div style="width: 1000px; height: 280px; padding: 5px 5px">
		
		<%--按钮 --%>		 
		<a href="#" id="print" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="javascript:print()">打印</a>
		<a href="#" id="Update" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:StatusOper(3)">修改</a>
		<a href="#" id="Cancel" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:StatusOper(4)">取消</a>
		<a href="#" id="Save" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:applyinout_saveAll('${billcode}')">保存</a>
		<a href="#" id="Delete" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:applyinout_Delete('${billcode}')">删除</a>	
		
		<%String billcode_=(String)request.getAttribute("billcode");%>
		
		<%if(
				!billcode_.equals("01")//非请购单不检查权限 
				||
				(
					billcode_.equals("01") 
					&& 
					userRightsInfo.findRight("Ywgn_BGApplyCheck")==true)
				) 
		{%>
		<a href="#" id="Check" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="javascript:applyinout_Action('${billcode}',3)">审核</a>
		<a href="#" id="UnCheck"  class="easyui-linkbutton"  iconCls="icon-no" plain="true" onclick="javascript:applyinout_Action('${billcode}',4)">弃审</a>
		<%} %>
		
		
		<a href="#" id="lingyong" style="display:none"  class="easyui-linkbutton"  iconCls="icon-add" plain="true" onclick="javascript:bg_YinYong('04','领用单')">请购转领用</a>
		<a href="#" id="tuihuan"  style="display:none" class="easyui-linkbutton"  iconCls="icon-add" plain="true" onclick="javascript:bg_YinYong('05','退还单')">领用转退还</a>
		<a href="#" id="ruku"  style="display:none"class="easyui-linkbutton"  iconCls="icon-add" plain="true" onclick="javascript:bg_YinYong('02','采购入库单')">订单转入库</a>
		<a href="#" id="tuiku"   style="display:none" class="easyui-linkbutton"  iconCls="icon-add" plain="true" onclick="javascript:bg_YinYong('03','采购退库单')">入库转退库</a>
		
		
		<div style="padding:5px"></div>
		<div id="printArea">
		<%--表头数据 --%>
		<form id="fm" method="post" novalidate>
			<%--测试  确定刷新效果--%>
			<input name="inoutMainID" id="inoutMainID" value="${inoutMainID}" type="hidden"/>
			<input name="billCode" id="billCode" value="${billcode}" type="hidden"/>
			<input name="datalist" id="datalist" type="hidden"/>
			<input type="text" id="flag" style="display:none" />
			<input name="isQuote" id="isQuote" value="${isQuote}" type="hidden"/>
			
			<table style="border:1px solid #95B8E7; width: 1000px;">
				<tr>
					<th style="text-align: left;">
						<label>单据号:</label>
						<input name="inOutCode" id="inOutCode" class="easyui-textbox"  
						value="${inoutmain.inOutCode}" />
					</th>
					<th style="text-align: left;">
						<label>单据日期:</label> 
						<input name="billDate" id="billDate" class="easyui-datebox" 
						value="${inoutmain.billDate}"  
						required="true" 
						data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" />
					</th>
					<th style="text-align: left;">
						<label>部门:</label> 
						<input name="deptCode" id="deptCode" class="easyui-combobox" 
						value="${inoutmain.deptCode}"
						required="true" 
						data-options="valueField:'deptCode',textField:'deptName',url:'${webPath}/home/deptmentlist.do'" />
					</th>
					<th style="text-align: left;">
						<label>业务员:</label> 
						<input name="personCode" id="personCode" class="easyui-combobox" 
						value="${inoutmain.personCode}"
						required="true" 
						data-options="valueField:'personcode',textField:'personname',url:'${webPath}/home/personlist.do'" />
					</th>
				</tr>
				<tr>
					<th style="text-align: left;" id="supplierCodeshow">
						<label>供应商:</label> 
						<input name="supplierCode" id="supplierCode" class="easyui-combobox" 
						value="${inoutmain.supplierCode}"
						data-options="valueField:'code',textField:'name',url:'${webPath}/home/supplierbox.do'" />
					</th>
					<th style="text-align: left;">
						<label>备注:</label> 
						<input name="remark" id="remark" class="easyui-textbox" 
						value="${inoutmain.remark}" />
					</th>
				</tr>
			</table>
		</form>
		
		<div style="padding:5px"></div>
		
		<%--明细数据 --%>
		<table id="dg" style="width: 1000px; height: 320px;" rownumbers="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="materialcode" width="100px">商品编码</th>
					<th field="name" width="100px">商品名称</th>
					<th field="spec" width="100px">规格</th>
					<th field="unitname" width="70px">单位</th>
					<th field="nowqty" width="70px" >现存</th>
					<th data-options="field:'quantity',width:70,align:'left',editor:{type:'numberbox',options:{precision:2}}">数量</th>
					<th data-options="field:'price',width:70,align:'left',editor:{type:'numberbox',options:{precision:2}}">价格</th>
				</tr>
			</thead>
		</table>
		</div>
		<%--制单人数据 --%>
		<div style="padding:5px"></div>
		<div>
			<span style="padding-right:10px">
				制单人:
				<input id="maker" name="maker" class="easyui-textbox"  value="${inoutmain.maker}" style="width:50px" />
				<input id="makerName" name="makerName" class="easyui-textbox"  value="${inoutmain.makerName}" style="width:100px"/>
			</span>
			
			<span style="padding-right:10px">
				制单日期:
				<input id="makeDate" name="makeDate" class="easyui-textbox" value="${inoutmain.makeDate}" style="width:120px"/>
			</span>
			
			<span style="padding-right:10px">审核人:
				<input id="checker" name="checker" class="easyui-textbox" value="${inoutmain.checker}" style="width:50px" />
				<input id="checkerName" name="checkerName" class="easyui-textbox" value="${inoutmain.checkerName}" style="width:100px"/>
			</span>
			
			<span style="padding-right:10px">
				审核日期:
				<input id="checkDate" name="checkDate" class="easyui-textbox" value="${inoutmain.checkDate}" style="width:120px"/>
			</span>
		</div>
		
		<script type="text/javascript">
        //页面初始化
		$(function(){        	
        	var billCode=$("#billCode").val();
        	var inoutMainID=$("#inoutMainID").val();
        	var isQuote=$("#isQuote").val();
        	
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
        			var flag=$("#flag").val();
        			if(flag==1){
                		return true;
               		}
        			else{
        				return false;
        			}
        	    }
            });
        	
          //页面状态控制
          //正常新增 或 查看
        	if(isQuote==0){
	        	if(inoutMainID==0){
	        		StatusOper(1);
	        	}
	        	else {
	        		StatusOper(2);
	        	}
        	}
          //引用，使用被引用单据的id获取数据，但进入编辑页面，清空inoutmainid和inoutcode
        	else if(isQuote==1){
        		StatusOper(1);
        		$("#inoutMainID").val(0);
        		$("#inOutCode").textbox('setValue','');//赋值 
        	}
          
         });
        
        
    </script>
	</div>
</body>
</html>