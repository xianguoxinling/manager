<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<title>订单详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="/css/store.css" type="text/css" />
	<link rel="stylesheet" href="/css/layout.css" type="text/css" />
	<link rel="stylesheet" href="/css/jquery.validation.css" type="text/css" />
	<link rel="stylesheet" href="/css/ie_style.css" type="text/css" />
	<link rel="stylesheet" href="/css/bootstrap.css" media="screen" type="text/css" />

	 <link href="/css/orderDetail.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="/js/jquery-2.1.0.min.js"></script>
	<script type="text/javascript" src="/js/easyform.js"></script>
	<script type="text/javascript" src="/js/bootstrap.js"></script>
	<script type="text/javascript" src="/js/classify.js"></script>

	<script type="text/javascript">
		$(function() {
			$('#header').load('/load/header.html');
			$('#header2').load('/load/header2.html');
			$('#foot').load('/load/foot.html');
		});

		$(document).ready(function() {
			$('#addproduct').easyform();
		});
	</script>
</head>

<body>
	<div id="main">
		<div id="header"></div>
		<div id="content">
			<div id="header2"></div>
			<div class="wrapper">

			<div class="detail-steps">

				<div class="step-tit">
					<span>订单号：${order.id}</span>
				</div>
				<div class="step-cont">
					<div class="info-form">
						<div class="layui-form-item">
							<label class="layui-form-label">订单更新时间：</label>
							<label class="layui-form-label item-info">${order.updateTime}</label>
						</div>

						<c:if test="${order.state == 'payed'}">
						<div class="layui-form-item">
							<label class="layui-form-label">付款成功：</label>
							<label class="layui-form-label item-info">${order.updateTime}</label>
						</div>
						</c:if>

						<c:if test="${order.state == 'on_road'}">
						<div class="layui-form-item">
							<label class="layui-form-label">商家发货：</label>
							<label class="layui-form-label item-info">${order.updateTime}</label>
						</div>
						</c:if>

						<c:if test="${order.state == 'finished'}">
						<div class="layui-form-item">
							<label class="layui-form-label">确认收货：</label>
							<label class="layui-form-label item-info">${order.updateTime}</label>
						</div>
						</c:if>

						<c:if test="${order.state == 'cancle'}">
						<div class="layui-form-item">
							<label class="layui-form-label">用户取消：</label>
							<label class="layui-form-label item-info">${order.updateTime}</label>
						</div>
						</c:if>
					</div>
				</div>

				<div class="hr"></div>


				<c:if test="${not empty deliveryInfo}">
				<!-- 物流信息 -->
				<div class="step-tit">
					<span>物流信息</span>
				</div>
				<div class="step-cont">

					<div class="info-form">

						<div class="layui-form-item">
							<label class="layui-form-label">物流公司：</label>
							<label class="layui-form-label item-info">${deliveryInfo.deliveryCompany}</label>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">物流单号：</label>
							<label class="layui-form-label item-info">${deliveryInfo.deliveryNumber}</label>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">备注：</label>
							<label class="layui-form-label item-info">${deliveryInfo.remark}</label>
						</div>
					</div>

				</div>

				<div class="hr"></div>
				</c:if>

				<div class="step-tit">
					<span>收货信息</span>
				</div>
				<div class="step-cont">

					<div class="info-form">

						<div class="layui-form-item">
							<label class="layui-form-label">收货人：</label>
							<label class="layui-form-label item-info">${order.address.contactName}</label>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">地址：</label>
							<label class="layui-form-label item-info">${order.address.city} ${order.address.address}</label>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">手机号码：</label>
							<label class="layui-form-label item-info">${order.address.mobile}</label>
						</div>
					</div>

				</div>

				<div class="hr"></div>
				<div class="step-cont">
					<div class="cart-main">
						<div class="cart-thead">
							<div class="column t-goods">商品名称</div>
							<div class="column t-price">单价(元)</div>
							<div class="column t-quantity">数量</div>
							<div class="column t-sum">小计</div>
							<div class="column t-action"></div>
						</div>
						<div class="cart-item-list">
							<div class="cart-tbody">
								<c:forEach items="${order.productionList}" var="detail" varStatus="status">
								<div class="item-single  item-item">
									<div class="item-form">
										<div class="cell p-goods">
											<div class="goods-item">
												<div class="p-img">
														<img class="img-auto" alt="${detail.name}" src="${detail.mainPic}"/>
													</a>
												</div>
												<div class="item-msg">
													<div class="p-name">
														<a>${detail.name}</a>
													</div>
												</div>
											</div>
										</div>
										<div class="cell p-price">
											<strong>${detail.price}</strong>
										</div>
										<div class="cell p-quantity">
											<strong>x${detail.num}</strong>
										</div>
										<div class="cell p-sum">
<%-- 											<strong>¥ ${${detail.price}*${detail.num}}</strong> --%>
									    </div>
									    <div class="cell p-ops">
											<div class="quantity-txt" _stock="stock_${detail.id}"></div>
										</div>
									</div>
									<div class="item-line"></div>
								</div>

							<!-- 循环 end -->
							</c:forEach>

							</div>
						</div>
					</div>

				</div>

				<div class="hr"></div>
			</div>

			<div class="order-payInfo">
				<div class="payInfo-wrapper">
					<div class="payInfo-shadow">
						<div class="order-realPay">
							<div>
								<span class="realPay-title">商品总额：</span>
								<span class="order-price">￥</span>
								<span class="realPay-price">${order.totalPrice}</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
		<!-- footer -->
		<div id="foot"></div>
	</div>

	<script type="text/javascript" src="http://kefu.puckart.com/mibew/js/compiled/chat_popup.js"></script>
	<script type="text/javascript" src="/js/kf.js"></script>
</body>
</html>
