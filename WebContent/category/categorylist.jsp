<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑作品</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
	<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon"/>
<link href="/css/store.css" rel="stylesheet" type="text/css" />
<link href="/css/layout.css" rel="stylesheet" type="text/css" />
<link href="/css/jquery.validation.css" rel="stylesheet" type="text/css" />
<link href="/css/ie_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/css/bootstrap.css" media="screen" type="text/css" />
<link rel="stylesheet" href="/css/normalize.css" type="text/css" />

<script type="text/javascript" src="/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>

	<script type="text/javascript">
	$(function() {
	$('#header').load('/load/header.html');
	$('#header2').load('/load/header2.html');
	$('#foot').load('/load/foot.html');
	});
	</script>

</head>

<body>
	<div id="main">
		<div id="header"></div>
		<div id="content">
			<div id="header2"></div>
			<div class="wrapper">
<!-- 		<h1 class="page_title">分类管理</h1> -->
		<table class="cart_product">
			<tr class="bg">
			    <th class="images"></th>
				<th class="name" style="text-align: center">分类名称</th>
				<th class="name" style="text-align: center">分类编号</th>
				<th class="name" style="text-align: center">简要描述号</th>
				<th class="edit2" style="text-align: center">删除</th>
			</tr>

			<c:forEach items="${categoryList}" var="category">
				<tr>
				<td class="images">
								<img src="${category.pic}" title=""/>
							</td>
					<td class="name"><c:out value="${category.name}" /></td>
					<td class="name"><c:out value="${category.id}" /></td>
					<td class="name"><c:out value="${category.introduction}" /></td>
					<td class="edit2"><a title="close" href="delete.action?id=${category.id}">删除</a></td>
				</tr>
			</c:forEach>

		</table>

		<div id="foot"></div>
	</div>
	<script type="text/javascript" src="http://kefu.puckart.com/mibew/js/compiled/chat_popup.js"></script>
	<script type="text/javascript" src="/js/kf.js"></script>
</body>
</html>
