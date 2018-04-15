<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <jsp:include page="/jsp/util/head.jsp" /> --%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script>

<%-- <script type="text/javascript" src="<%=path%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.pack.js"></script> --%>
<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
<script type="text/javascript" src="<%=path%>/js/ZTree/jquery.ztree-2.6.js"></script>
<script type="text/javascript" src="<%=path%>/js/ZTree/ZTree.js"></script>
<script type="text/javascript" src="<%=path%>/js/outlook2.js"></script>
<script type="text/javascript" src="<%=path%>/js/main.js"></script>

<!--  -->
<link href="<%=path%>/js/themes/icon.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/css/default.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/js/themes/umeng/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/main.css"></link>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/new_style.css"></link>

<!-- 验证引用 -->
<link rel="stylesheet" type="text/css" href="<%=path%>/css/nyroModal.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/jquery.autocomplete.css">

<script type="text/javascript" src="<%=path%>/js/jquery.jqprint-0.3.js"></script>
<%-- <script type="text/javascript" src="<%=path%>/js/json2.js"></script>--%>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.modified.js"></script>
<script type="text/javascript" src="<%=path%>/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.nyroModal.custom.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/jscolor/jscolor.js"></script>


<script type="text/javascript">
	$(function() {
		var webPath="<%=path%>/";
		$.extend($.fn.validatebox.defaults.rules, {
			equals: {
		        validator: function(value,param){    
		            return value == $(param[0]).val();    
		        },    
		        message: '输入的密码不一致'   
		    },
		    mobile: { //value值为文本框中的值
			    validator: function(value) {
			        var reg = /^1[0-9]\d{9}$/;
			        return reg.test(value);
			    },
			    message: '输入手机号码格式不准确'
			},
			idcard: { // 验证身份证
			    validator: function(value) {
			        return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
			    },
			    message: '身份证号码格式不正确'
			},
			//ajax 验证身份证是否唯一
			validate_IdCard: {
			    validator: function(value, param) {
			        var bl = false;
			        if(param[0]!=''){
			        	//alert(param[0]);
			        }
			        $.ajax({
			            type: 'POST',
			            async: false,
			            dateType: 'json',
			            url: webPath + "Staff/validateIdCard.do",
			            data: {
							id_number:value,newId_number:param[0]
						},
			            success: function(result) {
			                bl = result.status;
			            }
			        });
			        return bl;
			    },
			    message: '帐号已存在'
			}
		});
    });
     
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden;background-color:#eeeeee;" scroll="no">
	<div region="north" split="true" style="background-color:#eeeeee;height: 59px;" border="false" class="title">
		<img class="title-logo" src="../../images/new/main_jsp_logo.png" />
		<input type="hidden" id="staffName" value="${sessionScope.staffName}"/>
		<span style="float:right; padding-right:20px;padding-top:16px;" class="head">欢迎 ${sessionScope.staffName}&nbsp;&nbsp;&nbsp; <a href="#" id="editpass" style="line-height: 20px;color: #fff;">修改密码</a> <a href="#" id="loginOut" style="line-height: 20px;color: #fff;">安全退出</a></span>
	</div>
	<div title="功能列表" class="easyui-accordion" region="west" split="true" style="width:160px;background-color:#eeeeee;margin-top:0px;padding: 0px;" id="west">
	<ul id="leftMenu" class="tree"></ul>
	</div>
	
	<div id="mainPanle"  region="center" split="true" style="background: #eee; overflow-y:hidden;">
		<div id="tabs" class="easyui-tabs" fit="true" border="false" >
			<div title="首页" style="padding:0px;overflow:hidden;" id="home">
				<iframe id="iframe-home" style="border: 0px solid;height: 100%;width: 100%; overflow: hidden" frameborder="no"></iframe>
			</div>
		</div>
	</div>
	<div region="south" split="true"   style="height:18px;background:#eeeeee;text-align: center; ">
	<div style="font-weight: bold;font-family: 微软雅黑;color: #1542A8;font-size: xx-small;" >中博信息技术研究院有限公司©2014-2017</div>
	</div>
	<!--修改密码窗口-->
	<div id="w" style="padding:20px 0 10px 50px;">
		<form id="ff" method="post">
			<table>
				<tr>
					<td>新密码：</td>
					<td>
						<div class="condition-text-container">
							<input id="txtNewPass" type="password" class="condition-text" />
						</div>
					</td>
				</tr>
				<tr>
					<td width="30%">确认密码：</td>
					<td>
						<div class="condition-text-container">
							<input id="txtRePass" type="password" class="condition-text" />
						</div>
					</td>
				</tr>
				<tr style="font-size:12px;">
					<td>提示：</td>
					<td>
						<div class="">
							密码至少8位，且必须包含数字、大小写字母、特殊符号！
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" id="btnEp">确定</div>
			<div class="btn-operation" id="btnCancel">取消</div>
		</div>
	</div>
	
	<div id="idNumberWindow" style="padding:0 0 0 0">
		<form id="idNumberForm" method="post">
			<table>
				<tr>
					<td>真实姓名：</td>
					<td><!--  required="true" missingMessage='必填项' -->
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition" type="text" id="realName" name="realName" data-options="required:true"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>证件号码：</td>
					<td><!--  required="true" missingMessage='必填项' -->
						<div class="condition-text-container">
							<c:choose>
								<c:when test="${'null'==id_number }">
									<input class="condition-text easyui-validatebox condition" type="text" id="idNumber" name="vid_number" data-options="required:true,validType:['idcard[]']"/>
								</c:when>
								<c:otherwise>
									<input class="condition-text easyui-validatebox condition" type="text" id="idNumber" name="vid_number" value="${id_number }" data-options="required:true,validType:['idcard[]']"/>
								</c:otherwise>
							</c:choose>
							
						</div>
					</td>
				</tr>
				<tr>
					<td>手机号码：</td>
					<td>
						<div class="condition-text-container">
							<c:choose>
								<c:when test="${'null'==tel }">
									<input class="condition-text easyui-validatebox condition mobile" type="text" id="mobileNumber" name="mobileNumber" required="true" validType="mobile['']"/>
								</c:when>
								<c:otherwise>
									<input class="condition-text easyui-validatebox condition mobile" type="text" id="mobileNumber" name="mobileNumber" value="${tel }" required="true" validType="mobile['']"/>
								</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" id="btnUpdateIdCard">确定</div>
			<!-- <div class="btn-operation" id="btnCancelByIdNumber">取消</div> -->
		</div>
	</div>
	
	<div id="win-pop-whole"></div>
	<!--  
<div id="mm" class="easyui-menu" style="width:150px;">
  <div id="mm-tabclose">关闭</div>
  <div id="mm-tabcloseall">全部关闭</div>
  <div id="mm-tabcloseother">除此之外全部关闭</div>
  <div class="menu-sep"></div>
  <div id="mm-tabcloseright">当前页右侧全部关闭</div>
  <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
  <div class="menu-sep"></div>
  <div id="mm-exit">退出</div>
</div>
-->
<div  id="windowcontent" style="display:block; "> 

</div>
</body>
</html>
