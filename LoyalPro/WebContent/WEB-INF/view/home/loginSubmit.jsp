<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<div>
	欢迎您 Welcome：${loginUserInfo.getUserName()}
</div>

<div>
	已经设置了Session,<a target="self" href="/JindaERP/home/testsession.do"  >去看看</a>
</div>

<div>
<label>—基础数据—</label>
<br>
<a target="_blank" href="../home/goods.do">办公用品</a>
	<a target="_blank" href="../base/unit.do">计量单位</a>
	<a target="_blank" href="../base/suppliercategory.do">供应商分类</a>
		<a target="_blank" href="../home/supplier.do">供应商</a>
	
</div>
<div>
<label>—期初设置—</label>
<br>
	<a target="_blank" href="../home/inout.do?inoutMainID=0&billcode=00">期初设置</a>
</div>
<div>
<label>—请购业务—</label>
<br>
<a target="_blank" href="../home/inoutlist.do?billcode=01">请购单列表</a>
<a target="_blank" href="../home/inout.do?inoutMainID=0&billcode=01">请购单</a>
</div>
<div>
<label>—采购入库—</label>
<br>
<a target="_blank" href="../home/inoutlist.do?billcode=02">采购入库列表</a>
<a target="_blank" href="../home/inout.do?inoutMainID=0&billcode=02">采购入库单</a>
</div>
<div>
<label>—采购退库—</label>
<br>
<a target="_blank" href="../home/inoutlist.do?billcode=03">采购退库列表</a>
<a target="_blank" href="../home/inout.do?inoutMainID=0&billcode=03">采购退库单</a>
</div>
<div>
<label>—领用业务—</label>
<br>
<a target="_blank" href="../home/inoutlist.do?billcode=04">领用列表</a>
<a target="_blank" href="../home/inout.do?inoutMainID=0&billcode=04">领用单</a>
<a target="_blank" href="../home/inoutlist.do?billcode=05">退还列表</a>
<a target="_blank" href="../home/inout.do?inoutMainID=0&billcode=05">退还单</a>
</div>
<div>
<label>—其它入库—</label>
<br>
<a target="_blank" href="../home/inoutlist.do?billcode=06">其它入库列表</a>
<a target="_blank" href="../home/inout.do?inoutMainID=0&billcode=06">其它入库单</a>
</div>
<div>
<label>—其它出库—</label>
<br>
<a target="_blank" href="../home/inoutlist.do?billcode=07">其它出库列表</a>
<a target="_blank" href="../home/inout.do?inoutMainID=0&billcode=07">其它出库单</a>
</div>
</body>
</html>