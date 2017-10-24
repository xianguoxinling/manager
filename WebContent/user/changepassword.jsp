<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>烛照小程序管理系统 | 修改密码</title>
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
<link href="/css/bootstrapValidator.min.css" rel="stylesheet" />
</head>

<body>
	<div id="main">
		<!-- header -->

		<div id="header"></div>
		<div id="content" style="padding-bottom: 0px;">
			<div id="header2"></div>
		</div>

		<div class="clear"></div>
		
 <div style="width:600px;margin-left:150px;margin-top:70px">
      

                <form id="defaultForm" method="post" class="form-horizontal" action="http://magic.puckart.com/manager/user/changepassword.action">

                   
                  

                        <div class="alert alert-success form-group" style="display: none;"></div>

                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon">原密码&nbsp;&nbsp;：<i class="fa faaddon fa-unlock-alt"></i></span>
                               <input type="password" class="form-control" id="oldpassword" name="oldpassword" placeholder="请输入原密码" />
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon">新密码&nbsp;&nbsp;：<i class="fa faaddon fa-unlock-alt"></i></span>
                                <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" />
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon">确认密码：<i class="fa faaddon fa-unlock-alt"></i></span>
               <input type="password" class="form-control" id="confirmPwd" name="confirmPwd" placeholder="请再次输入密码" />
                            </div>
                        </div>

                    
                

                        <input type="hidden" id="vertifyCode" name="vertifyCode" value="">

                        <div class="text-center">
                            <button type="submit" class="btn btn-embossed btn-primary btn-min">确认修改</button>
                        </div>

                
             
                </form>
         
        </div>


		<div id="foot" style="width:940px;position:fixed;bottom:0"></div>
	</div>

	<script type="text/javascript" src="/js/jquery-2.1.0.min.js"></script>
	<script type="text/javascript" src="/js/layui/layui.js"></script>
	<script type="text/javascript" src="/js/bootstrap.js"></script>
	<script type="text/javascript" src="/js/cookies.js"></script>
	<script type="text/javascript" src="/js/move-top.js"></script>
	<script type="text/javascript" src="/js/easing.js"></script>
	<script type="text/javascript" src="/js/sellerOrders.js"></script>
    <script src="/js/bootstrapValidator.min.js"></script>

	<script type="text/javascript">
    $(function () {
    	$('#header').load('/load/header.html');
    	$('#header2').load('/load/header2.html');
    	$('#foot').load('/load/foot.html');
    });

    $(document).ready(function() {
        $('#defaultForm').bootstrapValidator({
                message: '用户名或密码错误',

                feedbackIcons: {
                    valid: 'fa fa-check',
                    invalid: 'fa fa-close',
                    validating: 'fa fa-circle-o-notch fa-spin'
                },
                fields: {
                    password: {
                        message: '密码无效',
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },
                            stringLength: {
                                min: 6,
                                message: '至少输入6位密码'
                            },
                        }
                    },
                    confirmPwd: {
                        notEmpty: '密码无效',
                        validators: {
                            notEmpty: {
                                message: '用户名不能为空'
                            },
                            stringLength: {
                                min: 6,
                                message: '至少输入6位密码'
                            },
                            identical: {
                                field: 'password',
                                message: '两次输入的密码不一致'
                            },
                        }
                    },
                }
            })
    });
</script>

	<script type="text/javascript"
		src="http://kefu.puckart.com/mibew/js/compiled/chat_popup.js"></script>
	<script type="text/javascript" src="/js/kf.js"></script>

</body>
</html>
