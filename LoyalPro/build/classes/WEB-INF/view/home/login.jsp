<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<div>
	<form action="home/loginSubmit.do" method="post" >
		<table>
			<tr>
				<td>用户名</td>
				<td>密码</td>
			</tr>
			<tr>
				<td><input id="UserCode" name="UserCode" type="text" value="" style="width:100px" /></td>
				<td><input id="Passwd"  name="Passwd" type="password" value="" style="width:100px" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="登录" /></td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>