<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String serverName = request.getServerName();
	String serverPort = ""+request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>爱运维综合巡检</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.0.min.js"></script>
<!-- <script type="text/javascript" src="<%=path%>/js/imgSlider.js"></script> -->

<!-- <script type="text/javascript" src="./aes/core.js"></script> -->
<!-- <script type="text/javascript" src="./aes/sha256.js"> -->
<script type="text/javascript" src="./aes/aes.js"></script>
<script type="text/javascript" src="./aes/mode-ecb.js"></script>

<script type="text/javascript" src="login.js"></script>
<link rel="stylesheet" type="text/css" href="style.css"></link>
<link rel="stylesheet" type="text/css" href="easySlider"></link>
<!-- <link rel="stylesheet" type="text/css"
	href="<%=path%>/css/imgSlider.css"></link> -->
</head>
<script>
	var serverName = "<%=serverName%>";
	var serverPort = "<%=serverPort%>";
	//console.log(serverName + ":"+serverPort);
	if("61.160.128.47" == serverName && "8080"==serverPort){
		window.location.href="http://zhxj.telecomjs.com:9081/ins/jsp/frame/login_new/login.jsp";
	}else if("zhxj.telecomjs.com" == serverName && "8080"==serverPort){
		window.location.href="http://zhxj.telecomjs.com:9081/ins/jsp/frame/login_new/login.jsp";
	}else if("132.228.237.107" == serverName && "8080"==serverPort){
		window.location.href="http://132.228.125.45:9081/ins/jsp/frame/login_new/login.jsp";
	}else if("132.252.6.38" ==serverName && "8080"==serverPort){
		window.location.href="http://132.228.125.45:9081/ins/jsp/frame/login_new/login.jsp";
	}
	else if("132.228.176.96" ==serverName && "8080"==serverPort){
		window.location.href="http://132.228.125.45:9081/ins/jsp/frame/login_new/login.jsp";
	}
	else{
		<%-- window.location.href="<%=path%>/jsp/frame/login_new/login.jsp"; --%>
	}
</script>
<body>
<input type="hidden" id="sjxjurl"/>
	<div class="header">
		<div class="logo-zone">
			<div class="logo"></div>
		</div>
	</div>
	<div class="middle">
		<div class="login-info">
			<div class="login-info-background-back"></div>
			<div class="login-info-background">
				<div class="more-info"></div>
				<div class="download"></div>
				<div class="login-info-input">
					<div class="login-title">
						<span class="span-login-title">登录巡检系统</span>
					</div>
					<div class="login-text-bg">
						<div id="empty-info-userName" class="empty-info">请输入用户名</div>
						<input class="login-text" type="text"   name="userName" />
					</div>
					<div class="login-text-bg">
						<div id="empty-info-password" class="empty-info">请输入密码</div>
						<input class="login-text" type="password"  name="password" />
					</div>
					<div class="login-forgot-password">
						<div id="button-forgot-password" class="btn-forgot-password"></div>
					</div>
					<div class="login-err-msg"></div>
					<div class="login-button-zone">
						<button id="button-login" class="btn-login"></button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="bottom-background">
			<div class="bottom-background-inner">
				<div class="bottom-download bottom-download-sjxj"></div>
				<div class="bottom-download bottom-download-zwsx"></div>
				<div class="bottom-download bottom-download-yszk"></div>
			</div>
		    <div style="color:#330033" align="center"><strong>备案号 : <a href="http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action
		    " style="text-decoration: none;">苏ICP备16066289号-3</a></strong></div>	
		</div>
	</div>
</body>
</html>

<!-- <div id="imageShow">
	<div id="imgshow_mask"></div>
	<ul class="imagebg" id="imagebg">
		<li data-sPic="<%=path%>/images/index_ya_s.jpg"><a
			href="#" class="bannerbg_main"
			style="background:url(<%=path%>/images/index_ya.jpg) 50% 50% no-repeat;"></a>
		</li>
		<li data-sPic="<%=path%>/images/index_uc9_s.jpg"><a
			href="#" class="bannerbg_main"
			style="background:url(<%=path%>/images/index_uc9.jpg) 50% 50% no-repeat;"></a>
		</li>
		<li data-sPic="<%=path%>/images/index_newlogo_s.jpg"><a
			href="#" class="bannerbg_main"
			style="background:url(<%=path%>/images/index_newlogo.jpg) 50% 50% no-repeat;"></a>
		</li>
		<li data-sPic="<%=path%>/images/index_iPad2.0_s.jpg"><a
			href="#" class="bannerbg_main"
			style="background:url(<%=path%>/images/index_iPad2.0.jpg) 50% 50% no-repeat;"></a>
		</li>
		<li data-sPic="<%=path%>/images/index_hundred_million_s.jpg"><a
			href="#" class="bannerbg_main"
			style="background:url(<%=path%>/images/index_hundred_million.jpg) 50% 50% no-repeat;"></a>
		</li>
		<li data-sPic="<%=path%>/images/index_idg_s.jpg"><a
			href="#" class="bannerbg_main"
			style="background:url(<%=path%>/images/index_idg.jpg) 50% 50% no-repeat;"></a>
		</li>
		<li data-sPic="<%=path%>/images/index_callmaster5.0_s.jpg"><a
			href="#" class="bannerbg_main"
			style="background:url(<%=path%>/images/index_callmaster5.0.jpg) 50% 50% no-repeat;"></a>
		</li>
		<li data-sPic="<%=path%>/images/banner_home_1_s.jpg"><a
			href="#" class="bannerbg_main"
			style="background:url(<%=path%>/images/banner_home_1.jpg) 50% 50% no-repeat;"></a>
		</li>
		<li data-sPic="<%=path%>/images/banner_home_3_s.jpg"
			style="background:#a9a9a9 url(<%=path%>/images/banner_home_3.jpg) center no-repeat;">
			<a href="#" class="bannerbg_main"
			style="background:url(<%=path%>/images/banner_home_3.jpg) 50% 50% no-repeat;"></a>
		</li>
	</ul>
	<div class="scrollbg">
		<div class="scroll">
			<a id="left_img_btn" class="s_pre" href="javascript:void(0)"></a>
			<div class="current" id="current"></div>
			<div class="outScroll_pic">
				<ul class="scroll_pic cls" id="small_pic"></ul>
			</div>
			<a id="right_img_btn" class="s_next" href="javascript:void(0)"></a>
		</div>
	</div>
</div> -->
