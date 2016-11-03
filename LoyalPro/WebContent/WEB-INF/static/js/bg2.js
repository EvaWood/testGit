//新增 修改共用
function applyinout_saveAll(billCode){
	$('#fm').form('submit', {
		url : '/JindaERP/home/applyinout_saveAll.do',
		
		onSubmit : function() {
			//若是采购单据，显示供应商，供应商不能为空
			if (billCode=="02" || billCode=="03" || billCode=="11"){
    			if($("#supplierCode").textbox('getValue')==""){
    				alert("供应商不能为空！");
    				return false;
    			}
    		}
			
			//检测数据
			if($(this).form('validate')==false){
				alert('false');
				return false;
			}
			
			//获取明细数据
			var rows = $("#dg").datagrid("getRows"); 
			var datalist="";
			for(var i=0;i<rows.length;i++)
			{
				//调用endedit方法，把编辑后的数据更新到网格
				$('#dg').datagrid('endEdit', i); 
				
				if(rows[i].quantity>0){
					datalist=datalist+rows[i].materialcode+'-'+rows[i].quantity+'-'+rows[i].price+'*';
				}
			}
			
			if(datalist.length==0){
				alert("请录入明细数据！");
				return false;
			}
			
			$("#datalist").val(datalist);
		},
		
		success : function(result) {
			var result = eval('(' + result + ')');
			//新增或修改成功，必须刷新，更新InOutMainID
			if (result.result==true) {
				location.href = "/JindaERP/home/inout.do?billcode="+billCode+"&inoutMainID="+result.InOutMainID+"&isQuote=0";
			} 
			alert(result.message);		
		}
	});
}

//删除
function applyinout_Delete(billcode){
	var inoutMainID=$("#inoutMainID").val();
	
	var Confirmmsg="确定删除此单据？";
	if (confirm(Confirmmsg) == false) {
        return;
    }
	
	$.post('/JindaERP/home/applyinout_Delete.do', {inoutMainID:inoutMainID,random:new Date()}, function(result) {
		var result = eval('(' + result + ')');
		//删除后，查询相关类型单据列表
		if (result.result==true) {
			location.href = "/JindaERP/home/inout.do?billcode="+billcode+"&inoutMainID=0"+"&isQuote=0";
		} 
		alert(result.message);
	});
}

//审核 弃审
function applyinout_Action(billCode,opertype){
	var inoutMainID=$("#inoutMainID").val();
	
	var Confirmmsg="";
	if(opertype==3){
		Confirmmsg="确定审核此单据？"
	}
	if(opertype==4){
		Confirmmsg="确定弃审此单据？"
	}
	if (confirm(Confirmmsg) == false) {
        return;
    }
	 
	$.post('/JindaERP/home/applyinout_Action.do', {inoutMainID:inoutMainID,opertype:opertype,random:new Date()}, function(result) {
		var result = eval('(' + result + ')');
		if (result.result==true) {
			//同意需要刷新，显示审核字段的变化
			location.href = "/JindaERP/home/inout.do?billcode="+billCode+"&inoutMainID="+inoutMainID+"&isQuote=0";
		} 
		alert(result.message);
	});
}

//inoutMainID 被引用单据的id ,
//目标单据类型、名称
function bg_YinYong(BillCode,Title){
	var inoutMainID=$("#inoutMainID").val();
	var src="/JindaERP/home/inout.do?billcode="+BillCode+"&inoutMainID="+inoutMainID+"&isQuote=1";
	parent.OpenNewTab(src,Title);
}

function bg_ShowQingGou(billCode,inoutMainID){
	$('#dg').edatagrid({
        url: '/JindaERP/home/inoutgoodslist.do?billcode='+billCode+'&inoutMainID='+inoutMainID+'&random='+new Date(),
        
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
}
