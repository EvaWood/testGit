$.extend($.fn.validatebox.defaults.rules, {
	intOrFloat: {// 验证整数或小数
		validator: function (value) {
			return /^\d+(\.\d+)?$/i.test(value);
		},
		message: '请输入数字，并确保格式正确'
    }
});

$.extend($.fn.datagrid.defaults.editors, {
	    text: {
	        init: function(container, options){
	            var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
	            return input;
	        },
	        destroy: function(target){
	            $(target).remove();
	        },
	        getValue: function(target){
	            return $(target).val();
	        },
	        setValue: function(target, value){
	            $(target).val(value);
	        },
	        resize: function(target, width){
	            $(target)._outerWidth(width);
	        }
	    }
});

//日期格式
function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function myparser(s){
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
}

function getdate() {
    var today = new Date();
    var y = today.getFullYear();
    //为了兼容谷歌和火狐所以使用getFullYear，IE可以直接识别getFullYear为大日期，
    var m = today.getMonth() + 1;
    var mm = m > 9 ? String(m) : "0" + String(m);
    var d = today.getDate();
    var dd = d > 9 ? String(d) : "0" + String(d);
    //var date = String(y) + "-" + mm + "-" + dd;
    var date = dd + "/" + mm + "/" + String(y);
    return date;
}

$.extend($.fn.validatebox.defaults.rules, {
	md: {
		validator: function(value, param){
			 var reg = /^(\d{4})-([0-9]{2})-([0-9]{2})$/;
			  var r = value.match(reg);
			  return   r != null;
		},
		message: '请输入日期的正确格式，例 {0}.'
	}
})

//将表单数据转为json
function form2Json(id) {
	var arr = $("#" + id).serializeArray()
	var jsonStr = "";

	jsonStr += '{';
	for (var i = 0; i < arr.length; i++) {
		jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
	}
	jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
	jsonStr += '}';

	var json = JSON.parse(jsonStr);
	return json;
}

//绑定菜单按钮的点击事件  MenuLink
function BindMenuClickEvent() {
    $(".MenuLink").click(function() {
		//获取按钮里面的src属性
		var src = $(this).attr("src");
		
        //得到节点的名字来放去Title
        var title = $(this).text();
        
        //拼接一个Iframe标签
        var str = '<iframe id="frmWorkArea" width="100%" height="100%" frameborder="0" scrolling="yes" src="'+ $(this).attr("src") + '"></iframe>'

        $("#worktab").tabs('add', {
            title : title,
            content : str,
            closable : true
        });
        
//        //首先判断用户是否已经单击了此项，如果单击了直接获取焦点，负责打开
//        var isExist = $("#worktab").tabs('exists', title);
//        
//        if (!isExist) {
//            //添加tab的节点，调用easyUITab标签的方法
//            $("#worktab").tabs('add', {
//                title : title,
//                content : str,
//                closable : true
//            });
//        } else {
//            //如果存在则获取焦点
//            $("#worktab").tabs('select', title);
//        }
    });
}

function OpenNewTab(src,title) {
	var str = '<iframe id="frmWorkArea" width="100%" height="100%" frameborder="0" scrolling="yes" src="'+ src + '"></iframe>';
    
	$("#worktab").tabs('add', {
      title : title,
      content : str,
      closable : true
  });
}

function updateTab(url)
{
    var tab = $('#worktab').tabs('getSelected');

    $("#worktab").tabs('update',{
        tab: tab,
        options:{
            href: url
        }
    });
    tab.panel('refresh');
}


function print(){	
	var inoutMainID=$("#inoutMainID").val();
	var billCode=$("#billCode").val();
	var turl="/JindaERP/home/inoutprint.do?billcode="+billCode+"&inoutMainID="+inoutMainID+"&random="+new Date();  
    var newW = window.open(turl);
    newW.print();
}

function date2str(x, y) {
	   var z = {
	      y: x.getFullYear(),
	      M: x.getMonth() + 1,
	      d: x.getDate(),
	      h: x.getHours(),
	      m: x.getMinutes(),
	      s: x.getSeconds()
	   };
	   return y.replace(/(y+|M+|d+|h+|m+|s+)/g, function(v) {
	      return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-(v.length > 2 ? v.length : 2))
	   });
}