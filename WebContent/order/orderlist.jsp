<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>烛照小程序管理系统 | 查询订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="/css/store.css" type="text/css" />
<link rel="stylesheet" href="/css/ie_style.css" type="text/css" />
<link rel="stylesheet" href="/css/layout.css" type="text/css" />
<link rel="stylesheet" href="/css/ie_style.css" type="text/css" />
<link rel="stylesheet" href="/css/orderList.css" type="text/css" />
<link href="/js/layui/css/layui.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/css/bootstrap.css" type="text/css" />
</head>

<body>
	<div id="main">
		<!-- header -->

		<div id="header"></div>
		<div id="content" style="padding-bottom: 0px;">
			<div id="header2"></div>
		</div>

		<div class="clear"></div>
		<div class="mod-main mod-comm lefta-box" id="order02">
			<div class="mc">
				<table class="td-void order-tb">
					<colgroup>
						<col class="number-col" />
						<col class="consignee-col" />
						<col class="amount-col" />
						<col class="status-col" />
						<col class="operate-col" />
					</colgroup>
					<thead>
						<tr>
							<th>订单详情</th>
							<th>收货人</th>
							<th>总金额(元)</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>

					<c:forEach items="${orderList}" var="order">
						<tbody>
							<tr class="sep-row">
								<td colspan="5"></td>
							</tr>
							<tr class="tr-th">
								<td colspan="5"><span class="gap"></span> <span
									class="dealtime" title="${order.updateTime}">${order.updateTime}</span>
									<span class="number">订单号： <a
										href="/manager/order/detail.action?orderID=${order.id}">${order.id}</a>
								</span></td>
							</tr>

							<c:forEach items="${order.productionList}" var="detail"
								varStatus="status">

								<tr class="tr-bd">
									<td>
										<div class="goods-item">
											<div class="p-img">
												<a> <img
													src="${detail.mainPic}" title="${detail.name}"
													data-lazy-img="done" width="60" height="60" /></a>
											</div>
											<div class="p-msg">
												<div class="p-name">
													<a class="a-link" title="${detail.name}">${detail.name}</a>
												</div>
											</div>
										</div>
										<div class="goods-number">x${detail.num}</div>
										<div class="goods-repair"></div>
										<div class="clr"></div>
									</td>

									<c:if test="${status.index == 0}">
										<td rowspan="${order.productionList.size()}">
											<div class="consignee contactName">
												<span class="txt">${order.address.contactName}</span> <span
													class="txt">${order.address.mobile}</span> <span
													class="txt">${order.address.city}</span> <span class="txt">${order.address.address}</span>
											</div>
										</td>
										<td rowspan="${order.productionList.size()}">
											<div class="amount">
												<span>${order.totalPrice}</span> <br>
											</div>
										</td>
										<td rowspan="${order.productionList.size()}">
											<div class="status">
												<span> <c:choose>
														<c:when test="${order.state == 'pre_pay'}">未付款</c:when>
														<c:when test="${order.state == 'payed'}">已付款</c:when>
														<c:when test="${order.state == 'cancle'}">已取消</c:when>
														<c:when test="${order.state == 'on_road'}">配送中</c:when>
														<c:when test="${order.state == 'finished'}">已结束</c:when>
														<c:otherwise>订单状态异常</c:otherwise>
													</c:choose>
												</span> <br> <a
													href="/manager/order/querydetail.action?orderID=${order.id}"
													>订单详情</a>
											</div>
										</td>
										<td rowspan="${order.productionList.size()}">
											<div class="operate">
												<c:choose>
													<c:when test="${order.state == 'payed'}">
														<button class="btn btn-primary btn-sm"
															onclick="location.href='/manager/order/delivery.action?orderID=${order.id}';">发货</button>
													</c:when>
													<c:when test="${order.state == 'pre_pay'}">
														<button class="btn btn-primary btn-sm"
															onclick="location.href='/manager/order/detail.action?orderID=${order.id}';">查看</button>
														<button class="btn btn-primary btn-sm" style="margin-top:8px"
															onclick="location.href='/manager/order/cancle.action?orderID=${order.id}';">取消</button>
													</c:when>
													<c:when test="${order.state == 'on_road'}">
														<button class="btn btn-primary btn-sm" onclick="location.href='/manager/order/finished.action?orderID=${order.id}';">完结</button>
													</c:when>
													<c:otherwise>
														<button class="btn btn-primary btn-sm"
															onclick="location.href='/manager/order/detail.action?orderID=${order.id}';">查看</button>
													</c:otherwise>
												</c:choose>
											</div>
										</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</c:forEach>
				</table>
				<c:if test="${ empty orderList}">
					<div class="empty-box">
						<div class="e-cont">
							<h5>暂时没有订单记录</h5>
						</div>
					</div>
				</c:if>
			</div>
		</div>

		<div class="paging">
			<ul class="pager">
				<li id="preLi"><a id="pre" href="#">上一页</a></li>
				<li><a id="next"
					href="/manager/order/queryall.action?begin=2&num=20">下一页</a></li>
			</ul>
		</div>

		<div id="foot"></div>
	</div>

	<script type="text/javascript" src="/js/jquery-2.1.0.min.js"></script>
	<script type="text/javascript" src="/js/layui/layui.js"></script>
	<script type="text/javascript" src="/js/bootstrap.js"></script>
	<script type="text/javascript" src="/js/cookies.js"></script>
	<script type="text/javascript" src="/js/move-top.js"></script>
	<script type="text/javascript" src="/js/easing.js"></script>
	<script type="text/javascript" src="/js/sellerOrders.js"></script>

	<script type="text/javascript">
    $(function () {
    	$('#header').load('/load/header.html');
    	$('#header2').load('/load/header2.html');
    	$('#foot').load('/load/foot.html');
    });

    $(function () {
    	var begin = parseInt(GetQueryString("begin"));
    	var num = '20';
    	var url = '';
    	<%String s2 = (String) request.getAttribute("magiczz");%>
    	var s = '<%=s2%>';
			if (s == 'all') 
			{
				url = '/manager/order/queryall.action';
			}
			if (s == 'payed') 
			{
				url = '/manager/order/querynew.action';
			}
			if (s == 'cancle') 
			{
				url = '/manager/order/querycancle.action';
			}
			if (s == 'delivery') 
			{
				url = '/manager/order/querydeliver.action';
			}
			if (s == 'prepay') 
			{
				url = '/manager/order/querypre.action';
			}
			if (s == 'finished') 
			{
				url = '/manager/order/queryfinish.action';
			}

			if (begin && begin > 1) 
			{
				$("#pre").attr('href',
						url + '?begin=' + (begin - 1) + '&num=20');
				$("#next").attr('href',
						url + '?begin=' + (begin + 1) + '&num=20');
			} else 
			{
				$("#preLi").addClass("disabled");
				$("#pre").attr('href', 'javascript:void(0);');
				$("#next").attr('href', url + '?begin=2&num=20');
			}
		});

		function GetQueryString(name) 
		{
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return (r[2]);
			return null;
		}
	</script>

	<script type="text/javascript"
		src="http://kefu.puckart.com/mibew/js/compiled/chat_popup.js"></script>
	<script type="text/javascript" src="/js/kf.js"></script>

</body>
</html>
