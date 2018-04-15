//wangyan add and modified @ 20140522 for new menu
function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}
function getBasePath() {
	var obj = window.location;
	var basePath = obj.protocol + "//" + obj.host + getContextPath();
	return basePath;
}
//get context path and the base path
var contextPath = getContextPath();
var basePath = getBasePath();
$(function() {
	$("#w").hide();
	$("#idNumberWindow").hide();
	$("#logout").hide();
});
//$(function() {
//	openInfomation();
//});
$(function() {
	$.ajax({
		type : 'POST',
		url : contextPath + "/Staff/queryByStaffId.do",
		data : {
			
		},
		dataType : 'json',
		success : function(json) {
			
		}
	});
});

//修改密码窗口
function openPwd() {
	$('#w').window({
		title : '修改密码',
		width : 350,
		modal : true,
		shadow : true,
		closed : true,
		height : 250,
		resizable : false,
		closable:false
	});
}
//关闭修改密码窗口
function closePwd() {
	$('#w').window('close');
}
//修改密码
function serverLogin() {
	var $newpass = $('#txtNewPass');
	var $rePass = $('#txtRePass');
	if ($newpass.val() == '') {
		msgShow('系统提示', '请输入密码！', 'warning');
		return false;
	}
	if ($rePass.val() == '') {
		msgShow('系统提示', '请再一次输入密码！', 'warning');
		return false;
	}
	if ($newpass.val() != $rePass.val()) {
		msgShow('系统提示', '两次密码不一致！请重新输入', 'warning');
		return false;
	}

	var password = $newpass.val();
	$.ajax({
		type : 'POST',
		url : contextPath + "/Staff/modifyPassword.do",
		data : {
			password:password
		},
		dataType : 'json',
		success : function(json) {
			if (json.status) {
				msgShow('系统提示', '密码修改成功', 'info');
				isSimplePwd = true;
				$('#w').window('close');
			}else{
				msgShow('系统提示', json.info, 'warning');
			}

		}
	});
}

function openIdNumberWindow(){
	$('#idNumberWindow').window({
		title : '实名认证',
		width : 350,
		modal : true,
		shadow : true,
		closed : true,
		height : 150,
		resizable : false,
		closable:false
	});
}

function closeIdNumberWindow(){
	$('#idNumberWindow').window('close');
}

//function openInfomation(){
//	
//	var info =  "10月1号：彭云云 15755139767<br/>" +
//				"10月2号：李晓亮 18512528462<br/>" +
//				"10月3号：宁若凡 17749585320<br/>" +
//				"10月4号：王翔宇 17625991216<br/>" +
//				"10月5号：方俊松 13951945866<br/>" +
//				"10月6号：杨欣豪 13390922029<br/>" +
//				"10月7号：刘海旭 13851838521<br/>" +
//				"10月8号：童伟 18951970872<br/>";
//	$.messager.show({
//		title : '国庆节值班表',
//		msg : info,
//		showType : 'show',
//		width: '250',
//		height: '200'
//	});
//};

$(function() {
	openPwd();
	openIdNumberWindow();
	$('#editpass').click(function() {
		$('#w').show();
		$('#w').window('open');
	});
	$('#btnEp').click(function() {
		serverLogin();
	});
	$('#btnCancel').click(function() {
		closePwd();
	});	
	$('#btnCancelByIdNumber').click(function() {
		closeIdNumberWindow();
	});
	$('#loginOut').click(function() {
		if (confirm("您是否确定退出本次登录？")) {
			location.href = contextPath + "/logout.do";
		}
	});
	$("#iframe-home").attr("src", contextPath + "/jsp/frame/home/home.jsp");
	
	$("#btnUpdateIdCard").click(function(){
		//验证
		var validate = $("#idNumberForm").form('validate');
		if(validate){
			var real_name = $("#realName").val();
			var id_number = $("#idNumber").val();
			var user_name = $("#staffName").val();
			$.ajax({
				type:'POST',
				url:contextPath + "/check/checkIdNumber.do",
				data:{
					id_number:id_number,
					real_name:real_name
				},
				dataType:'json',
				success:function(json){
					if(json.result != "00"){
						$.messager.alert('警告','姓名与身份证号码不一致，请核对！');
					}else{
						$.ajax({
							type : 'POST',
							url : contextPath + "/Staff/modifyIdNumber.do",
							data : {
								ID_NUMBER:id_number,
								mobileNumber:$("#mobileNumber").val(),
								real_name:real_name
							},
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									msgShow('系统提示', '实名认证成功', 'info');
									isSimplePwd = true;
									$('#idNumberWindow').window('close');
								}else{
									msgShow('系统提示', json.info, 'warning');
								}
							}
						});
					}
				}
			});
			
			
			
		}
	});
});
var isSimplePwd= false;
$(function() {
	var  thisURL = document.URL;    
	var  getval =thisURL.split('?')[1];  
	var showval= getval.split("=")[1];
	var SimplePwd = getval.split("&")[0].split("=")[1];
	var TrueName = getval.split("&")[1].split("=")[1];
	var hasTel = getval.split("&")[2].split("=")[1];
	var isCheckOk = getval.split("&")[3].split("=")[1];
	var hasRealName = getval.split("&")[4].split("=")[1];
	if (SimplePwd=="false") {
		if (confirm("密码过于简单，请修改密码,否则会影响正常使用！")) {
			$('#w').show();
			$('#w').window('open');
		}
		else
		{
			$('#w').show();
			$('#w').window('open');
		}
	}else{
		if (TrueName=="false" || hasTel=="false" || hasRealName=="false") {
			//alert(TrueName+" "+SimplePwd);
			//msgShow('系统提示', '密码修改成功', 'info');btnUpdateIdCard
			if (confirm("该帐号未实名认证或未录入手机号码，请完善信息后重新登录，否则会影响正常使用！")) {
				$('#idNumberWindow').show();
				$('#idNumberWindow').window('open');
			}else{
				$('#idNumberWindow').show();
				$('#idNumberWindow').window('open');
			}
		}
   }
});
var demoTree;
$(function() {
	// wangyan modified @ 20140519 for session time out
	$.get(contextPath + "/Login/getGns.do", function(data) {
		if (data.length == 0) {
			window.location.href = contextPath;
			top.location.href = contextPath;
		} else {
			initMenu(data);
		}
	});
});

function initMenu(data) {
	var o = $("#west");
	o.empty();
	for ( var i in data) {
		var sub = data[i];
		// append a main div for every first menu, and an empty content div for
		// the second menus
		o.append("<div class='first-menu' onclick='firstMenu_onClick(this,"
				+ sub["id"] + ");'><span>" + sub["name"]
				+ "</span></div><div class='first-menu-content'></div>");
	}
}
function firstMenu_onClick(o, id) {
	var content = $(o).next();
	if (content.html() == "") {
		// when first click, query second menus from server
		$.get(contextPath + "/Login/getGns.do", {
			"id" : id
		}, function(data) {
			for ( var i in data) {
				var sub = data[i];
				// append the second menus to the content div
				content.append("<div class='second-menu' "
						+ "onclick='secondMenu_onClick(this,\"" + sub["name"]
						+ "\",\"" + sub["actionName"] + "\");'>" + sub["name"]
						+ "</div>");
			}
			// register click event handlers for expand or hide first menu
			$(o).toggle(
					function() {
						var oo = o;
						$(oo).addClass("first-menu-expanded");
						var allHeight = 0;
						// calculate the height of the content div
						content.find("div.second-menu").each(
								function() {
									allHeight += parseInt($(this).css("height")
											.replace(/px/g, ""));
									allHeight += parseInt($(this).css(
									"padding-top").replace(/px/g, ""));
									$(this).show();
								});
						content.animate({
							"height" : allHeight + "px"
						});
					}, function() {
						var oo = o;
						$(oo).removeClass("first-menu-expanded");
						content.animate({
							"height" : "0px"
						});
						// hide all the second menu
						var beforeIe8 = parseFloat($.browser.version) <= 8.0;
						content.find("div.second-menu").each(function() {
							if ($.browser.msie && beforeIe8) {
								$(this).hide();
							} else {
								$(this).fadeOut();
							}
						});
					});
			// when first click, call the click event to expand the first menu
			$(o).click();
		});
	}
}

function secondMenu_onClick(o, name, url) {
	addTab(name, basePath + url);
}

//打开一个全局的panelwindow
/**
 * url:远程数据地址
 * loadingMessage:加载时的提示内容
 */
function showWindowDialog(url,loadingMessage){
	var panelwindow=$("#windowcontent");
	if(panelwindow==null){
		return;
	}
	panelwindow.panel({
		href:url,
		loadingMessage:loadingMessage
	});
	panelwindow.panel("open");
	return panelwindow;
}
/*
 * function onClick(event, treeId, treeNode) { if (!treeNode["isParent"]) { var
 * tabTitle = treeNode["name"]; var url = contextPath + treeNode["actionName"];
 * addTab(tabTitle, url); } }
 */
