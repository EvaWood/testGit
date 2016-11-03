/*
 * 供应商分类
 * */
var url;

function add_suppliercategory(operType) {
	$('#dlg').dialog('open').dialog('setTitle', '新增  供应商分类');
	$('#fm').form('clear');
	url = 'Oper_suppliercategory.do';
	$("#operType").val(operType);
}

function edit_suppliercategory(operType) {
	var row = $('#dg').datagrid('getSelected');

	if (row) {
		$('#dlg').dialog('open').dialog('setTitle', '编辑  供应商分类');
		$('#fm').form('load', row);
		url = 'Oper_suppliercategory.do';
		$("#operType").val(operType);
	} else {
		alert("请勾选需要处理的数据");
	}
}

function save_suppliercategory() {
	$('#fm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var result = eval('(' + result + ')');

			if (result.result == false) {
				alert(result.errormessage);
			} else {
				$('#dlg').dialog('close'); // close the dialog
				$('#dg').datagrid('reload'); // reload the user data
			}
		}
	});
}

function destroy_suppliercategory(operType) {
	var row = $('#dg').datagrid('getSelected');

	if (row) {
		$.messager.confirm('Confirm', '确认删除此数据?', function(r) {

			if (r) {
				$.post('Oper_suppliercategory.do', {
					code : row.code,
					name : row.name,
					operType:operType,
					random : new Date()
				}, function(result) {

					var result = eval('(' + result + ')');

					if (result.result == true) {
						$('#dg').datagrid('reload');
					}

					else {
						$.messager.show({
							title : 'Error',
							msg : result.errormessage
						});
					}
				});
			}

		});
	} else {
		alert("请勾选需要处理的数据");
	}
}


function StatusOper(Type){
	var billCode=$("#billCode").val();
	var inoutMainID=$("#inoutMainID").val();
	
	//特别事项
	//若是采购单据，显示供应商，供应商不能为空
	if (billCode!="02" && billCode!="03" && billCode!="11"){
		$("#supplierCodeshow").hide();
	}
	
	//新增 进入编辑状态 暂不提供放弃操作
	if(Type==1){
		$("#Save").linkbutton("enable");
		$("#Update").linkbutton("disable");
		$("#Cancel").linkbutton("disable");
		$("#Delete").linkbutton("disable");
		$("#Check").linkbutton("disable");
		$("#UnCheck").linkbutton("disable");
		$("#print").linkbutton("disable");
		
		$("#inOutCode").textbox("disable");
		$("#billDate").datebox("enable");
		$("#deptCode").combobox("enable");
		$("#personCode").combobox("enable");
		$("#supplierCode").combobox("enable");
		$("#remark").textbox("enable");
		
		$("#flag").val(1);
	}
	
	//双击 进入查看状态
	else if(Type==2){
		var checker=$("#checker").val();
		//根据审核状态，处理按钮状态
		if(checker==""){
			$("#Update").linkbutton("enable");
			$("#Delete").linkbutton("enable");
			$("#Check").linkbutton("enable");
			$("#Save").linkbutton("disable");
    		$("#Cancel").linkbutton("disable");
    		$("#UnCheck").linkbutton("disable");
    		$("#print").linkbutton("disable");
		}
		else {
			$("#UnCheck").linkbutton("enable");
			$("#Update").linkbutton("disable");
			$("#Delete").linkbutton("disable");
			$("#Check").linkbutton("disable");
			$("#Save").linkbutton("disable");
    		$("#Cancel").linkbutton("disable");
    		$("#print").linkbutton("enable");
    		
    		if(billCode=="01"){
    			$("#lingyong").show();
    		}
    		if(billCode=="04"){
    			$("#tuihuan").show();
    		}
    		if(billCode=="11"){
    			$("#ruku").show();
    		}
    		if(billCode=="02"){
    			$("#tuiku").show();
    		}
		}
		
		$("#inOutCode").textbox("disable");
		$("#billDate").datebox("disable");
		$("#deptCode").combobox("disable");
		$("#personCode").combobox("disable");
		$("#supplierCode").combobox("disable");
		$("#remark").textbox("disable");
		
		$("#flag").val(0);
	}
	
	//修改 进入编辑状态 可取消
	else if(Type==3){
		$("#Save").linkbutton("enable");
		$("#Cancel").linkbutton("enable");
		$("#Update").linkbutton("disable");
		$("#Delete").linkbutton("disable");
		$("#Check").linkbutton("disable");
		$("#UnCheck").linkbutton("disable");
		$("#print").linkbutton("disable");
		
		$("#inOutCode").textbox("disable");
		$("#billDate").datebox("enable");
		$("#deptCode").combobox("enable");
		$("#personCode").combobox("enable");
		$("#supplierCode").combobox("enable");
		$("#remark").textbox("enable");
		
		$("#flag").val(1);
	}
	
	//取消  进入查看状态
	else if(Type==4){
		//应该是reload，改错了要查看原本的数据
		location.href = "/JindaERP/home/inout.do?billcode="+billCode+"&inoutMainID="+inoutMainID+"&isQuote=0";
	}
}