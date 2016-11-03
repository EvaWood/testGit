<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	
	<link rel="stylesheet" type="text/css" href="/JindaERP/static/css/fullcalendar.css?version=<%=new Date()%>">
	<link rel="stylesheet" type="text/css" href="/JindaERP/static/css/fullcalendar.print.css?version=<%=new Date()%>">
	<script type="text/javascript" src="/JindaERP/static/js/jquery-1.7.2.min.js?version=<%=new Date()%>"></script>
	<script type="text/javascript" src="/JindaERP/static/js/jquery-ui-1.10.2.custom.min.js?version=<%=new Date()%>"></script>
	<script type="text/javascript" src="/JindaERP/static/js/fullcalendar.min.js?version=<%=new Date()%>"></script>
</head>
<body>
	<div id='calendar'></div>
</body>

	<script>
		$(document).ready(function() {
			var date = new Date();
			var d = date.getDate();
			var m = date.getMonth();
			var y = date.getFullYear();
	
			var calendar = $('#calendar').fullCalendar({
				header : {
					left : 'prev,next, today',
					center : 'title',
					right:'prevYear,nextYear'
				},
				
				titleFormat:{
				    month: 'MMMM yyyy'
				},
				
				height: 600,
				defaultView:'month',
				weekends:true,
				
				selectable : true,
				editable : false,
				
				//calEvent是日程（事件）对象，jsEvent是个javascript事件，view是当前视图对象。
				eventClick: function(calEvent, jsEvent, view){
					var WorkDay=date2str(calEvent.start,"yyyy-MM-dd");
					
					var title = prompt(WorkDay,calEvent.title);
					
					if (title!='A' && title!='B' && title!='AB' && title!=''){
						alert('班次只能设置为 A、B、AB或为空！');
						return;
					}
					
					$.post("/JindaERP/app/updateCalendarData.do",{"WorkDay":WorkDay,"Banci":title,"Random":new Date()},function(req){
						var result = eval('(' + req + ')');
						if (result.result == false) {
							alert(result.errormessage);
						} 

						window.location.reload();	
					});
				}, 
			
				//events: '/JindaERP/app/calendardata.do?Year=2015&Month=11',
				eventColor: '#378006',
				
				viewDisplay: function(view) {    
		            var viewStart = $.fullCalendar.formatDate(view.start,"yyyy-MM-dd HH:mm:ss");  
		            var viewEnd = $.fullCalendar.formatDate(view.end,"yyyy-MM-dd HH:mm:ss");    
		            $("#calendar").fullCalendar('removeEvents');    
		            $.getJSON('/JindaERP/app/calendardata.do?',{start:viewStart,end:viewEnd,random:new Date()},function(data) {    
		               for(var i=0;i<data.length;i++) {    
		                   var obj = new Object();    
		                   obj.id = data[i].id;    
		                   obj.title = data[i].title;                   
		                   obj.start = $.fullCalendar.parseDate(data[i].start);    
		                   obj.textColor='#378006';
		                   $("#calendar").fullCalendar('renderEvent',obj,true);                     
		               }    
		             }); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示  
		           }		
			});
	
			$(".fc-button-prev").click(function () {
				var start=$('#calendar').fullCalendar('getView').start;
				var end=$('#calendar').fullCalendar('getView').end;
			});
			
		});
		
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
	</script>
	<style>
	body {
		margin-top: 40px;
		text-align: center;
		font-size: 14px;
		font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
	}
	
	#calendar {
		width: 900px;
		margin: 0 auto;
	}
	</style>
</html>