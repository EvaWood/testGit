<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
		<%request.setAttribute("webPath", com.jinda.common.ConfigDatas2.webPath);%>
		
		<div style="width: 1000px; height: 300px; padding: 5px 5px">
		
		<%--按钮 --%>		 
		<a href="#" id="Save" class="easyui-linkbutton" iconCls="icon-save" plain="false" onclick="javascript:applyinout_saveAll('11')">保存</a>
		
		<%--表头数据 --%>
		<form id="fm" method="post" novalidate>
			<%--测试  确定刷新效果--%>
			<input name="inoutMainID" id="inoutMainID" value="0" type="hidden"/>
			<input name="billCode" id="billCode" value="11" type="hidden"/>
			<input name="datalist" id="datalist" type="hidden"/>
			
			<table style="border:1px solid #95B8E7; width: 1000px;">
				<tr>
					<th style="text-align: left;">
						<label>单据号:</label>
						<input name="inOutCode" id="inOutCode" class="easyui-textbox"  />
					</th>
					<th style="text-align: left;">
						<label>单据日期:</label> 
						<input name="billDate" id="billDate" class="easyui-datebox" 
						required="true" 
						value="getdate()"
						data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" />
					</th>
					<th style="text-align: left;">
						<label>部门:</label> 
						<input name="deptCode" id="deptCode" class="easyui-combobox" 
						required="true" 
						value="${deptcode}"  
						data-options="valueField:'deptCode',textField:'deptName',url:'${webPath}/home/deptmentlist.do'" />
					</th>
					<th style="text-align: left;">
						<label>业务员:</label> 
						<input name="personCode" id="personCode" class="easyui-combobox" 
						required="true" 
						value="${usercode}"
						data-options="valueField:'personcode',textField:'personname',url:'${webPath}/home/personlist.do'" />
					</th>
				</tr>
				<tr>
					<th style="text-align: left;" id="supplierCodeshow">
						<label>供应商:</label> 
						<input name="supplierCode" id="supplierCode" class="easyui-combobox" 
						required="true"
						data-options="valueField:'code',textField:'name',url:'${webPath}/home/supplierbox.do'" />
					</th>
					<th style="text-align: left;">
						<label>备注:</label> 
						<input name="remark" id="remark" class="easyui-textbox" />
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
					<th field="safeqty" width="70px" >安全库存</th>
					<th field="applyquantity" width="70px" >申请数量</th>
					<th data-options="field:'quantity',width:70,align:'left',editor:{type:'numberbox',options:{precision:2}}">数量</th>
					<th data-options="field:'price',width:70,align:'left',editor:{type:'numberbox',options:{precision:2}}">价格</th>
				</tr>
			</thead>
		</table>
		
		<div id="toolbar">
		<form name="searchform" method="post" action="" id="searchform">
			单据日期
			<input name="billdate1" id="billdate1" value="2015-10-01" 
			class="easyui-datebox" required data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
			--
			<input name="billdate2" id="billdate2" value="getdate()" 
			class="easyui-datebox" required data-options="validType:'md[\'2015-01-01\']',formatter:myformatter,parser:myparser" style="width:100px;">
			
			商品名称
			<input name="materialcode" id="materialcode" class="easyui-combobox"  
			data-options="valueField:'code',textField:'name',url:'${webPath}/home/goodsbox.do'" style="width:80px;">
			
			部门
			<input name="deptcode" id="deptcode" class="easyui-combobox"  
			data-options="valueField:'deptCode',textField:'deptName',url:'${webPath}/home/deptmentlist.do'" style="width:100px;">
			
			业务员
			<input name="personcode" id="personcode" select="" class="easyui-combobox" 
			 data-options="valueField:'personcode',textField:'personname',url:'${webPath}/home/personlist.do'" style="width:100px;" >
			
			<a id="submit_search">查询</a>
		</form>
	</div>
		
		<%--制单人数据 --%>
		<div style="padding:5px"></div>
		<div>
			<span style="padding-right:10px">
				制单人:
				<input id="maker" name="maker" class="easyui-textbox"  style="width:50px" />
				<input id="makerName" name="makerName" class="easyui-textbox" style="width:100px"/>
			</span>
			
			<span style="padding-right:10px">
				制单日期:
				<input id="makeDate" name="makeDate" class="easyui-textbox" />
			</span>
			
			<span style="padding-right:10px">审核人:
				<input id="checker" name="checker" class="easyui-textbox" style="width:50px" />
				<input id="checkerName" name="checkerName" class="easyui-textbox" style="width:100px"/>
			</span>
			
			<span style="padding-right:10px">
				审核日期:
				<input id="checkDate" name="checkDate" class="easyui-textbox" />
			</span>
		</div>
		
		<script type="text/javascript">
      
		$(function(){        	
			$("#submit_search").linkbutton({
				iconCls : 'icon-search',
				plain : false
			}).click(function() {
				$('#dg').datagrid({
					queryParams : form2Json("searchform")
				}); //点击搜索
			});
        	
        	//加载明细数据
            $('#dg').edatagrid({
                url: '${webPath}/home/ApplyInfo.do?&random='+new Date(),
                queryParams : form2Json("searchform"),
                toolbar:"#toolbar",
                
                onBeforeEdit:function(index,row){
					return true;
        	    }
            });
        	
            $("#inOutCode").textbox("disable");
        	$("#maker").textbox("disable");
        	$("#makerName").textbox("disable");
        	$("#makeDate").textbox("disable");
        	$("#checker").textbox("disable");
        	$("#checkerName").textbox("disable");
        	$("#checkDate").textbox("disable");
         });
    </script>
	</div>
</body>
</html>