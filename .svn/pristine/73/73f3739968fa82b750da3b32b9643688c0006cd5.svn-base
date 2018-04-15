<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link href="css/net.css" type="text/css" rel="stylesheet"></link>
<link href="css/css.css" type="text/css" rel="stylesheet"></link>
<link href="css/login.css" type="text/css" rel="stylesheet"></link>

<link rel="shortcut icon" type="image/x-icon" href="image/fav.ico"></link>
<link rel="icon" type="image/x-icon" href="image/fav.ico"></link>

<script type="text/javascript" src="<%=path%>/qui/libs/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/easySlider1.7.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.menu.js"></script>
<script type="text/javascript" src="js/login.js"></script>
</head>
<body>
	<div id="ie6_warning" style="display:none">
		<span class="ie6_info">您正在使用 Internet Explorer
			6，在本页面的显示效果可能有差异。建议您升级到 <a
			href="http://view.atdmt.com/action/mrtiex_FY12IE9StaPrdIE8WWpageforXPFNL_1?href=http://view.atdmt.com/action/mrtiex_FY12IE9StaPrdIE8WWpageforXPFNL_1?href=http://download.microsoft.com/download/1/6/1/16174D37-73C1-4F76-A305-902E9D32BAC9/IE8-WindowsXP-x86-CHS.exe"
			target="_blank">Internet Explorer 8</a> 或以下浏览器： <a
			href="http://download.firefox.com.cn/releases/webins3.0/official/zh-CN/Firefox-latest.exe">Firefox</a>，<a
			href="http://www.google.cn/chrome/eula.html?hl=zh-CN&brand=CHMA&platform=win">Chrome
				浏览器</a>，<a
			href="http://cn.opera.com/download/get.pl?id=34136&location=270&nothanks=yes&sub=marine">Opera
				浏览器</a> </span> <span class="del_ie" onclick="$('#ie6_warning').hide()">X</span>
	</div>
	<!-- 登录 -->
	<div style="width:1000px;margin:0 auto;" class="relative">
		<div class="index_main_right absolute fright tleft"
			style="top:552px;right:0%;">
			<div id="loginstatusdiv">
				<div id="tipdiv" style="position: absolute; top: -29px; left: 49px;"
					class="w200 h30 pd2 hidden">
					<div class="gray6 mright5 tleft">请输入邮箱或OA号或手机号登录</div>
				</div>
				<ul class="mtop100 mleft90 h35 relative">
					<li class="login_tit" style="position: relative">账号：</li>
					<li class="remarkname w180 fleft">
						<div id="empty-info-userName" class="empty-info">请输入用户名</div> <input
						type="text" class="input1" name="userName" id="userName"
						tabindex="1" value="" />
					</li>
				</ul>
				<ul class="mtop10 mleft90 h35">
					<li class="login_tit">密码：</li>
					<li class="w180 fleft">
						<div id="empty-info-password" class="empty-info">请输入密码</div> <input
						type="password" class="input1" name="password" id="password"
						maxlength="20" tabindex="2" />
					</li>
				</ul>
				<ul class="mtop3 mleft90 h20">
					<li class="login_tit">&nbsp;</li>
					<li class="w180 fleft">
						<div class="login-err-msg"></div></li>
				</ul>

				<div class="mtop10 mleft150">
					<button id="signIn" class="register_new  pointer white fb"
						tabindex="3">立刻登录</button>
				</div>
			</div>
			<!-- 未激活 -->
			<div id="inactivestatusdiv" class="hidden">
				<div class="mtop140 mleft70 w245 h25 lh25 f12 textcenter gray6">
					<span class="mright5" id="inactivestatusdiv_email"></span>帐号未激活
				</div>
				<div class="h25 lh25 textcenter mleft60 w245 gray9">
					<a href="http://www.oa.cn/#" class="green mright10"
						id="immediatelyActivatedHandel">马上激活</a>|<a href="javascript:;"
						class="mleft10 green" onclick="showloginstatusdiv()">返回</a>
				</div>

				<div class="h35"></div>
			</div>
			<!-- 未激活 -->
			<!--手机验证-->
			<div id="bindmobileidv" class="hidden"
				style="margin-top:88px;margin-left:80px; width:239px;">
				<div class="mtop120 w245 lh17 f12 tleft gray6">
					<span id="bindmobile"></span>未验证,无法登录,我们已将验证码通过短信发送到您的手机,请查收。
				</div>
				<div class="clear h20"></div>

				<ul class=" h35">
					<li style="position: relative" class="w50 fleft tright h35">验证码：</li>
					<li class=" fleft"><input type="text" value="" tabindex="1"
						maxlength="4" class="pd5 bdc w35" id="login_code" /></li>
					<li class="fleft" style="">
						<!-- time_gray_botton  短信发送中样式 --> <a
						class="time_blue_botton disp mleft5 mtop5 hidden"
						onclick="sendcode()" href="javascript:void(0);"
						id="login_getmobilecode_handle"><span>获取验证码</span> </a> <a
						class="time_gray_botton disp mleft5 mtop5"
						href="javascript:void(0);" id="login_ungetmobilecode_handle"><span
							id="login_ungetmobilecode_second">获取验证码(60)</span> </a></li>

				</ul>
				<div class="clear"></div>
				<div class="mtop10 mleft110 mtop20"
					style="margin-left:48px;margin-top:20px;">
					<input type="button" tabindex="3"
						class="register_new  pointer white fb" onclick="mobile_gologin()"
						id="singin_code" value="确认并登录" /> <a class="green mleft5"
						href="javascript:void(0);" onclick="showloginstatusdiv()">返回</a>
				</div>
			</div>
		</div>
	</div>
	<!-- 登录end -->



	<div class="ie6_out">
		<div class="ie6_in">
			<div class="wrapper ie6_main">
				<div class="index_head tcenter" title="OA">
					<ul id="index">
						<li><a href="image/移动办公,移动OA,办公软件,OA办公系统,工作就上oa.cn.htm"
							title="OA首页">首页</a>
						</li>
						<!--<li class="fleft mleft20">Android客户端</li>
                            <li class="fleft mleft20">本地模式</li>-->
					</ul>
					<ul id="login">
						<li><a href="http://www.oa.cn/home/login" title="登录OA">登录</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="ie6_out">
		<div class="ie6_in">
			<div class="wrapper ie6_main index_main">
				<div id="container"
					style="display:inline-block;width:1000px; height:470px; overflow:hidden; position:relative; margin-top:43px;">
					<div id="slider" class="tcenter"
						style="width: 940px; height: 425px; overflow: hidden;">
						<ul style="width: 3760px; margin-left: 0px;">
							<li style="margin-left: -940px; float: left;"><img
								src="image/pic003.jpg" width="940" height="420" alt="移动办公,低碳环保" />
							</li>
							<li style="float: left;"><img src="image/pic001.jpg"
								width="940" height="420" alt="移动办公,移动OA" /></li>

							<li style="float: left;"><img src="image/pic002.jpg"
								width="940" height="420" alt="移动办公,快乐工作" /></li>
							<li style="float: left;"><img src="image/pic003.jpg"
								width="940" height="420" alt="移动办公,低碳环保" /></li>
							<li style="float: left;"><img src="image/pic001.jpg"
								width="940" height="420" alt="移动办公,移动OA" /></li>
						</ul>
					</div>
					<ol id="controls">
						<li id="controls1" class="current"><a rel="0"
							href="javascript:void(0);">1</a>
						</li>
						<li id="controls2" class=" "><a rel="1"
							href="javascript:void(0);">2</a>
						</li>
						<li id="controls3" class=" "><a rel="2"
							href="javascript:void(0);">3</a>
						</li>
					</ol>
					<span id="prevbtn"><a href="javascript:void(0);">previous</a>
					</span> <span id="nextbtn"><a href="javascript:void(0);">next</a> </span>
				</div>
			</div>
		</div>
	</div>

	<div class="ie6_out" style="margin-top:-90px;">
		<div class="ie6_in">
			<!--                <form method="post" name="form2" id="form2" onsubmit="return checkform2();" enctype="multipart/form-data">
-->
			<div class="wrapper ie6_main w1000" style="width:1000px;">
				<div class="index_main_left">

					<ul style=" margin-top: 17px;">
						<li id="title"><span title="OA 今天">今天</span>
						</li>
						<li id="describe">oa.cn自动整理您的工作碎片，告诉您今天要做的事情，接受您的安排。</li>
					</ul>

					<ul style="margin-top: 17px;">
						<li id="title"><span title="OA 计划">计划</span>
						</li>
						<li id="describe">oa.cn让您轻松安排工作计划，时间有弹性。</li>
					</ul>

					<ul style=" margin-top: 20px;">
						<li id="title"><span title="OA 汇报">汇报</span>
						</li>
						<li id="describe">oa.cn帮您及时汇报与总结，Show出您的成绩。</li>
					</ul>
					<ul style=" margin-top: 16px; ">
						<li id="title"><span title="OA 会议">开会</span>
						</li>
						<li id="describe">oa.cn协助您实现“会而有议，议而有决，决而有行，行而有果”。</li>
					</ul>
					<ul style=" margin-top: 17px; ">
						<li id="title"><span title="OA 审批">审批</span>
						</li>
						<li id="describe">烦透了流程的烦琐和乱糟糟的操作？oa.cn的审批给您前所未有的清晰。</li>
					</ul>
					<ul style=" margin-top: 20px; ">
						<li id="title"><span title="OA 手机客户端">手机客户端</span>
						</li>
						<li id="describe">oa.cn支持Android &amp; iPhone客户端，让您随时随地移动办公。</li>
					</ul>


				</div>

				<!--  这里插入快速登录start -->
				<!--  这里插入快速登录end -->

			</div>
			<!--<input type="submit" id="btn_save" name="btn_save" value="登　录" class="login_btn h22 mleft56 f14 fb hidden" tabindex="4" />-->
			<!--     </form>-->
		</div>
	</div>
	<div class="clear"></div>

	<div class="wrapper ie6_main" style="width:960px;">		
		<p class="textcenter h100  gray6 f12">
			©2011 ZBITI All right reserved.
		</p>



	</div>

</body>
</html>