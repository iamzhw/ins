function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}
var contextPath = getContextPath();

// bind event handler
$(function() {
	$("#empty-info-userName").click(function() {
		$("input[name='userName']").focus();
	});
	$("#empty-info-password").click(function() {
		$("input[name='password']").focus();
	});
	$("input[name='userName']").focus(function() {
		$("#empty-info-userName").hide();
	});
	$("input[name='password']").focus(function() {
		$("#empty-info-password").hide();
	});
	$("input[name='userName']").blur(function() {
		if (!$("input[name='userName']").val().length) {
			$("#empty-info-userName").show();
		}
	});
	$("input[name='password']").blur(function() {
		if (!$("input[name='password']").val().length) {
			$("#empty-info-password").show();
		}
	});
	$("input[name='userName']").keydown(function(event) {
		if (event.keyCode == 13) {
			login();
		}
	});
	$("input[name='password']").keydown(function(event) {
		if (event.keyCode == 13) {
			login();
		}
	});
	$("body").mousedown(function() {
		clearErrMsg();
	});
	$.ajax({
		url:"../../../mobile/init/getSjxjUrl.do",
	   	contentType:"application/json",
	    type:"get",
	    dataType:"json",
	    //data:{"line_id":lineId},
	    success:function(datas){
		var url=window.location.href+"";
		var sjxjUrl=datas.sjxjUrl;
		var h=url.indexOf("132.228.237.107");
		if(h!=-1){
		//value="http://61.160.128.47:8080/files/zhxjAPK/2014-06-25/vpn.apk";
			sjxjUrl=sjxjUrl.replace('61.160.128.47','132.228.237.107');
		//alert(value);
		}
	    	$("#sjxjurl").val(sjxjUrl);
	    }
	});
});

// bind button event handler
$(function() {
	$(".btn-login").click(function() {
		login();
	});
	$(".btn-forgot-password").click(function() {
		alert("忘记密码？");
	});
	$(".more-info").click(function() {
		alert("more-info");
	});
	$(".download").click(function() {
		alert("download");
	});
	$(".bottom-download-sjxj").click(function() {
		//alert($("#sjxjurl").val())
		window.open($("#sjxjurl").val());
	});
	$(".bottom-download-zwsx").click(function() {
		alert("正在建设中");
	});
	$(".bottom-download-yszk").click(function() {
		alert("正在建设中");
	});
});

// bind image scroller
$(function() {
	// img.init();
	// img.play(0);
});

function lockBtnLogin() {
	var olg = $("#button-login");
	olg.addClass("btn-login-disabled"); //
	olg.attr("disabled", "disabled");
}
function unlockBtnLogin() {
	var olg = $("#button-login");
	olg.removeClass("btn-login-disabled");
	olg.removeAttr("disabled");
}
function setErrMsg(msg) {
	$(".login-err-msg").html(msg);
}

function clearErrMsg() {
	setErrMsg("");
}


//AES加密
function Encrypt(word){  
    var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");   
    var srcs = CryptoJS.enc.Utf8.parse(word);  
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});  
    return encrypted.toString();  
} 

function login() {
	var errorMsg = "";
	var onm = $("input[name='userName']");
	var nm = onm.val();
	nm = Encrypt(nm);
	var ops = $("input[name='password']");
	var ps = ops.val();
	ps = Encrypt(ps);
	
	if (!nm.length) {
		errorMsg += "用户名不能为空！";
	}
	if (!ps.length) {
		errorMsg += "密码不能为空！";
	}
	if (errorMsg != "") {
		setErrMsg(errorMsg);
	} else {
		lockBtnLogin();
		// 登录处理
		$.post(contextPath + "/Login/login.do", {
			"username" : nm,
			"password" : ps
		}, function(result) {
			//debugger;
			if (result == null) {
				setErrMsg("登陆失败！");
				unlockBtnLogin();
			} else if (result["status"] == "true" || result.status == true) {
				window.location.href = contextPath + "/jsp/frame/main.jsp?isSimplePwd=" + result.isSimplePwd + 
													"&isTrueName="+result.isTrueName + 
													"&hasTel=" +result.hasTel + 
													"&isCheckOk=" +result.isCheckOk + 
													"&hasRealName=" + result.hasRealName;
			} else {
				setErrMsg(result["message"]);
				unlockBtnLogin();
			}
			
		}, "json");
	}
}