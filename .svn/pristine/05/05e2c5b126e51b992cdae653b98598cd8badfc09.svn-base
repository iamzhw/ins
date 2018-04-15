function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}
var contextPath = getContextPath();


var tiptext = "";
var inactiveCheck = false;
var mobileCheck = '';
var loginstatus = '';
// 定时器
var time = 0;
var inteer;
// var mobileCheck = false;

$(document)
		.ready(
				function() {
					// var $menu = $('<div class="w200 h30 pd2"><div
					// class="gray6 mright5
					// tleft">请输入邮箱或OA号或手机号登录</div></div>');
					/*
					 * $(".remarkname").menulist({ "type": 2, "offset": {top:
					 * -70, left: -3}, "$menu": $menu });
					 */
					$('.remarkname').bind('click', function() {
						$('#tipdiv').show();
					});
					$('#email').bind('blur', function() {
						$('#tipdiv').hide();
					});

					// 动态添加 滚动图片
					$('#slider > ul')
							.append(
									'<li><img src="image/pic002.jpg" width="940" height="420" alt="移动办公,快乐工作" /></li><li><img src="image/pic003.jpg" width="940" height="420" alt="移动办公,低碳环保" /></li>');

					$("#slider").easySlider({
						auto : true,// 自动滚动
						pause : 3000,// 延时
						numeric : true,//
						continuous : true
					// 设置为true时,滚动到最后一页后会跳到第一页继续滚动
					});
				});

function onEmailBlur() {
	var email = $('#email').val();
	// 判断是否是手机号码
	if (email.length != 11 && email.indexOf('@') > -1) {
		checkemailstatus();
	} else {
		checkmobilestatus();
	}
}
// 检查完整性
function checkform2() {
	// var res = false;
	if (inactiveCheck) {
		return false;
	}
	// setTimeout(function(){
	if ($('#email').val() == "" || $('#email').val() == tiptext) {
		showErroMsg('请输入登录帐号!');
		$('#email').val('');
		$('#email').get(0).focus();
		return false;
	}
	if ($('#password').val() == "" && inactiveCheck == false) {
		showErroMsg('请输入登录密码!');
		$('#password').get(0).focus();
		return false;
	}
	var mobilereg = /^(((13[0-9]{1})|15[0-9]{1}|18[0-9]{1}|)+\d{8})$/;
	// 手机号码
	if ($('#email').val().length == 11 && $('#email').val().indexOf('@') == -1) {
		if (!mobilereg.test($('#email').val())) {
			showErroMsg('手机号码错误！');
			return false;
		} else {
			if (mobileCheck != 'go') {
				checkmobilestatus('login', $('#password').val());// alert('e');
			}
			return false;
		}
	} else {
		if (loginstatus == '') {
			checkUserStatus($('#email').val(), $('#password').val());
		}
		return false;
	}
	// setTimeout(function(){
	// alert(res);
	// if(!res && res != undefined){
	// return false;
	// }
	// var base64 = new Base64();
	// setTimeout(function(){
	// $('#form2').attr('action', "http://www.oa.cn/home/submitlogin?v=1.0");

	$('#signIn').val('加载中');
	return true;
	// },500);

}
// 显示错误信息 o为jQuery对象
function showErroMsg(msg, o) {
	if (o == undefined) {
		var p = $('#signIn').offset();
	} else {
		var p = o.offset();
	}
	var left = p.left - 120;
	var top = p.top - 50;
	dynamicCreationPopup({
		msg : msg,
		classname : 'link_tooltip',
		vtop : top,
		vleft : left,
		position : 'absolute'
	});
}
// 显示登录状态
function showloginstatusdiv() {
	// $('#loginstatusdiv').fadeIn(1000);
	$('#loginstatusdiv').show();
	$('#inactivestatusdiv').hide();
	$('#bindmobileidv').hide();
	// $('#email').val(tiptext);
	$('#login_code').val('');
	inactiveCheck = false;
	mobileCheck = '';
	loginstatus = '';
	// clearInterval(inteer);
	// time=0;
}
// 显示未激活状态
function showinactivestatusdiv() {
	$('#loginstatusdiv').hide();
	$('#inactivestatusdiv').fadeIn(1000);
	// $('#inactivestatusdiv').show();
}
// 检查用户的状态
function checkemailstatus() {
	var email = $('#email').val();
	var url = 'http://www.oa.cn/user/regactivation?e=' + email;
	$.post('http://www.oa.cn/user/regecheckemail', {
		email : email
	}, function(data) {
		if (data == null)
			return;
		try {
			if (!data.success) {
				if (data.type == 'Inactive') {
					$('#inactivestatusdiv_email').html(email);
					$('#immediatelyActivatedHandel').attr('href', url);
					showinactivestatusdiv();
					inactiveCheck = true;
				} else {
					inactiveCheck = false;
				}
			} else {
				inactiveCheck = false;
			}
		} catch (e) {

		}
	}, 'json');
}
// 检测用户手机
function checkmobilestatus(type, pwd) {
	var res = false;
	var url = 'http://www.oa.cn/user/regecheckmobile';
	var mobile = $('#email').val();
	$.post(url, {
		mobile : mobile,
		pwd : pwd
	}, function(data) {
		if (!data.success) {
			if (data.type == 'unbind') {
				// 没有绑定
				mobileCheck = 'send';
			} else {
				// res = true;
				mobileCheck = 'go';
			}
		} else {
			if (data.type == 'unbind') {
				mobileCheck = 'send';
			} else {
				mobileCheck = 'error';
			}

			// showErroMsg('不存在该手机号码！');
		}
		if (type != undefined && pwd != undefined && type == 'login'
				&& mobileCheck == 'send' && data.pwderror == 'yes') {
			showErroMsg('密码错误！');
			return false;
		}
		if (type != undefined && pwd != undefined && type == 'login'
				&& mobileCheck == 'send' && data.pwderror == 'no') {
			showbindmobilediv($('#email').val());
			sendcode($('#email').val());
			return false;
		}
		if (type != undefined && pwd != undefined && type == 'login'
				&& mobileCheck == 'error') {
			showErroMsg('不存在该手机号码！');
			return false;
		}
		if (type != undefined && pwd != undefined && type == 'login'
				&& mobileCheck == 'go') {
			$('#form2').attr('action',
					"http://www.oa.cn/home/submitlogin?v=1.0");
			// $('#form2').attr('action',"http://www.oa.cn/home/loadinglogin?user="+base64.encode($('#email').val())+"&pwd="+base64.encode($('#password').val()));
			$('#signIn').val('加载中');
			$('#form2').submit();
			return true;
		}
	}, 'json');

	// return res;
}
// 显示手机验证页面
function showbindmobilediv(mobile) {
	$('#bindmobile').html(mobile); // 手机号码
	$('#loginstatusdiv').hide();
	$('#bindmobileidv').fadeIn(1000);
}
// 发送验证码
function sendcode(mobile) {// alert('g');
	if (time == 0) {
		if (mobile == undefined) {
			mobile = $.trim($('#bindmobile').html());
		}
		time = 60;
		var url = 'http://www.oa.cn/user/regegetmobilecode';
		$.post(url, {
			mobile : mobile
		}, function(data) {
			if (!data.success) {
				if (data.type == 'phone_error') {
					showloginstatusdiv();
					showErroMsg('手机号码错误！', jQuery('#singin_code'));
				}
				if (data.type == 'send_error') {
					showErroMsg('发送验证码错误,请您稍后再试。', jQuery('#singin_code'));
				}
			} else {
				inteer = setInterval("settime()", 1000);
				$('#login_getmobilecode_handle').hide();
				$('#login_ungetmobilecode_handle').show();
			}
		}, 'json');
	}
}
// 确认并登陆
function mobile_gologin() {
	var pw = $('#password').val();
	var code = $('#login_code').val();
	var mobile = $.trim($('#bindmobile').html());
	if (code == '') {
		showErroMsg('验证码不可为空！', jQuery('#singin_code'));
	} else {
		$.post('http://www.oa.cn/user/regecheckmobilecode', {
			mobile : mobile,
			code : code
		}, function(data) {
			if (data.success) {
				// 登陆
				// $.post('http://www.oa.cn/home/submitlogin',{email:mobile,password:pw,code:code});
				createForm(mobile, pw, code);
				$('singin_code').unbind('click', mobile_gologin);
				$('#singin_code').val('加载中');
			} else {
				showErroMsg('验证码错误或已过期！', jQuery('#singin_code'));
			}
		}, 'json')
	}
}
function createForm(mobile, pw, code) {

	var form = $("<form id='mobile_form'></form>");
	form.attr('action', "http://www.oa.cn/home/submitlogin?v=1.0");
	form.attr('method', 'post');
	form.attr('enctype', "multipart/form-data");
	var input1 = $("<input type='hidden' name='email' />");
	input1.attr('value', mobile);
	var input2 = $("<input type='text' name='password'/>");
	input2.attr('value', pw);
	var input3 = $("<input type='text' name='code'/>");
	input3.attr('value', code);
	form.append(input1);
	form.append(input2);
	form.append(input3);
	form.appendTo("body");
	form.css('display', 'none');
	form.submit();
}
// 控制发送时间
function settime() {
	if (time > 0) {
		time = time - 1;
		$("#login_ungetmobilecode_second").html('获取验证码(' + time + ')');
	} else {
		$("#login_ungetmobilecode_second").html('获取验证码(60)');
		clearInterval(inteer);
		$('#login_getmobilecode_handle').show();
		$('#login_ungetmobilecode_handle').hide();
		return;
	}
}
// 检测用户状态
function checkUserStatus(value, pwd) {
	$.post('http://www.oa.cn/user/logingetuserinfo', {
		value : value,
		pwd : pwd
	}, function(resdata) {
		loginstatus = resdata['type'];
		if (resdata['type'] == 'go') {
			showbindmobilediv(resdata['data']);
			sendcode(resdata['data']);
			return false;
		} else {
			/*
			 * if(resdata['type'] == 'pwderror'){ showErroMsg('密码错误!');
			 * $('#password').get(0).focus(); return false; } else{
			 */
			$('#form2').attr('action',
					"http://www.oa.cn/home/submitlogin?v=1.0");
			// $('#form2').attr('action',"http://www.oa.cn/home/loadinglogin?user="+base64.encode($('#email').val())+"&pwd="+base64.encode($('#password').val()));
			$('#signIn').val('加载中');
			$('#form2').submit();
			return true;
			// }

		}
	}, 'json');
}
// 绑定页面的回车事件
/*
 * $(document).keydown(function(event){ //if(browser.msie)
 * if(event.keyCode==13)submitformlogin(); });
 */

$(document).ready(function() {
	// 提示框初始化
	try {
		$("#msgtip").tipui();
	} catch (e) {
	}
});

// 无应答提示
// 参数传递通过对象
// eg:
// dynamicCreationPopup({msg:'',classname:'',vleft:'',vtop:'',vtime:'',callback:''})
// by jeffli
var public_canpopup = true;
function dynamicCreationPopup(obj) {
	if (!public_canpopup) {
		return;
	}
	public_canpopup = false;

	var popupmsg = obj.msg; // 消息内容
	var classname = obj.classname; // 样式
	var vleft = obj.vleft; // left值
	var vtop = obj.vtop; // top值
	var vtime = obj.vtime; // 消失时间
	var callback = obj.callback; // 回调函数
	var clickDisappearance = obj.clickDisappearance // 是否点击其他地方消失
	var position = obj.position;
	var _width = obj.width == undefined ? "340" : obj.width; // 宽度
	var html = '';
	html = '<div class="black_tooltip mcenter link_tooltip"  style="width:'
			+ _width
			+ 'px;" id="popupdiv"><div class="black_tooltip_top"><div class="tooltip_left"><div class="tooltip_right"><div class="tooltip_mid"></div></div></div></div><div class="black_tooltip_left"><div class="black_tooltip_right"><div class="back_black3 lh25 light_green mleft10 mright10">'
			+ popupmsg
			+ '</div></div></div><div class="black_tooltip_bottom"><div class="tooltip_left"><div class="tooltip_right"><div class="tooltip_mid"></div></div></div></div></div>';

	/*
	 * html +='<div class="black_tooltip_top">'; html +='<div
	 * class="tooltip_left">'; html += '<div class="tooltip_right">'; html += '<div
	 * class="tooltip_mid"></div>'; html += '</div>'; html += '</div>'; html += '</div>';
	 * html +='<div class="black_tooltip_left">'; html += '<div
	 * class="black_tooltip_right">'; html += '<div class="back_black3 mcenter
	 * w98p lh25 gray9">'+popupmsg+'</div>'; html +='</div>'; html +='</div>';
	 * html += '<div class="black_tooltip_bottom">'; html += '<div
	 * class="tooltip_left">'; html += '<div class="tooltip_right">'; html += '<div
	 * class="tooltip_mid"></div>'; html += '</div>'; html += '</div>'; html += '</div>';
	 * html +='</div>';
	 */
	$(document.body).append(html);
	// $('#popupdiv').css({'left':200,'top':200});return;
	var msgdivobj = $('#popupdiv');
	// 计算屏幕正中间的位置
	var browserwidth = $(window).width();
	var browserheight = $(window).height();
	// 有滚动条的情况
	var scrollLeft = $(window).scrollLeft();
	var scrollTop = $(window).scrollTop();
	// 提示内容高度宽度
	var cwinwidth = msgdivobj.width();
	var cwinheight = msgdivobj.height();

	if (vleft == undefined) {
		var left = scrollLeft + (browserwidth - cwinwidth) / 2;
	} else {
		var left = vleft;
	}

	if (vtop == undefined) {
		var top = scrollTop + (browserheight - cwinheight) / 2;
	} else {
		var top = vtop;
	}

	if (vtime == undefined) {
		var time = 1500;
	} else {
		var time = vtime;
	}
	if (position != undefined) {
		msgdivobj.css('position', position);
	}
	msgdivobj.css({
		top : top + 50,
		left : left,
		opacity : 1,
		'z-index' : 9999
	}).animate({
		top : top
	}, "slow");
	setTimeout(function() {
		msgdivobj.animate({
			top : top + 50,
			opacity : 0
		}, "slow", function() {
			msgdivobj.remove();
			public_canpopup = true;
		});
	}, time);
	if (clickDisappearance != undefined && clickDisappearance) {
		$(document).click(function() {
			msgdivobj.animate({
				top : top + 50,
				opacity : 0
			}, "slow", function() {
				msgdivobj.remove();
				public_canpopup = true;
			});
		});
	}
	if (callback != undefined) {
		callback();
	}
}
// eg:dateFormat("Y-m-d",'2008-02-21 08:00:00');dateFormat("Y-m-d
// H:s:i",'2008/02/21');dateFormat("Y-m-d H:s",'02/03/2001');
// date <可以为空，为空时为默认当前时间，格式：>
// <"2008/02/21 08:00:00"；"02/03/2001 08:00:00";"2008-02-21
// 08:00:00";"02-03-2001 08:00:00">
// d <月份中的第几天，有前导零的 2 位数字> 01 到 31
// j <月份中的第几天，没有前导零> 1 到 31
// m <数字表示的月份，有前导零> 01 到 12
// n <数字表示的月份，没有前导零> 1 到 12
// Y <4 位数字完整表示的年份> 例如：1999 或 2003
// y <2 位数字表示的年份> 例如：99 或 03
// H <小时，24 小时格式，有前导零> 00 到 23
// G <小时，24 小时格式，没有前导零> 0 到 23
// //还没实现
// g <小时，12 小时格式，没有前导零> 1 到 12
// h <小时，12 小时格式，有前导零> 01 到 12
// ////还没实现
// i <有前导零的分钟数> 00 到 59
// s <秒数，有前导零> 00 到 59

function dateFormat(format, date) {
	var date_and_time = new Date();
	var date_string = '';
	var regS = new RegExp("-", "gi");
	if (date) {
		date_string = date.replace(regS, "/");
		date_and_time = new Date(Date.parse(date_string))
	}

	format = format.replace(/d/gi, timeFormat(date_and_time.getDate()));
	format = format.replace(/j/gi, date_and_time.getDate());
	format = format.replace(/m/gi, timeFormat(date_and_time.getMonth() + 1));
	format = format.replace(/n/gi, date_and_time.getMonth() + 1);
	format = format.replace(/Y/gi, date_and_time.getFullYear());
	format = format.replace(/y/gi, date_and_time.getYear());
	format = format.replace(/H/gi, timeFormat(date_and_time.getHours()));
	format = format.replace(/G/gi, date_and_time.getHours());
	// format = format.replace(/g/gi,timeFormat(date_and_time.getDate()));
	// format = format.replace(/h/gi,timeFormat(date_and_time.getDate()));
	format = format.replace(/i/gi, timeFormat(date_and_time.getMinutes()));
	format = format.replace(/s/gi, timeFormat(date_and_time.getSeconds()));
	// alert(format);
	return format;
}
/*
 * Base64 encode / decode
 * 
 * 
 */

function Base64() {
	// private property
	_keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

	// public method for encoding
	this.encode = function(input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
		input = _utf8_encode(input);
		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output + _keyStr.charAt(enc1) + _keyStr.charAt(enc2)
					+ _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
		}
		return output;
	}

	// public method for decoding
	this.decode = function(input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
		while (i < input.length) {
			enc1 = _keyStr.indexOf(input.charAt(i++));
			enc2 = _keyStr.indexOf(input.charAt(i++));
			enc3 = _keyStr.indexOf(input.charAt(i++));
			enc4 = _keyStr.indexOf(input.charAt(i++));
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
			output = output + String.fromCharCode(chr1);
			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
		}
		output = _utf8_decode(output);
		return output;
	}

	// private method for UTF-8 encoding
	_utf8_encode = function(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";
		for ( var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}
		return utftext;
	}

	// private method for UTF-8 decoding
	_utf8_decode = function(utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
		while (i < utftext.length) {
			c = utftext.charCodeAt(i);
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			} else if ((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i + 1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			} else {
				c2 = utftext.charCodeAt(i + 1);
				c3 = utftext.charCodeAt(i + 2);
				string += String.fromCharCode(((c & 15) << 12)
						| ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}
		return string;
	}
}

$(document).ready(function() {
	var isIE = !!window.ActiveXObject;
	var isIE6 = isIE && !window.XMLHttpRequest;
	if (isIE6) {
		$("#ie6_warning").show();
	}
});

$(document).ready(function() {
	// if ($.cookie('oaie6brower') == 'true') {
	// $('#ie6_warning').hide();
	// } else {
	// $('#ie6_warning').show();
	// }
});
function disStartie6() {
	// $.cookie('oaie6brower', 'true', {
	// path : '/',
	// expires : 100
	// });
	// $('#ie6_warning').hide();

}

function onEmailFocus() {
	var email = $('#email').val();
	// if (email != '') $('#labEmail').hide();
	if (email === '')
		$('#labEmail').show();
	else
		$('#labEmail').hide();

	/*
	 * if(email.length == 11 && email.indexOf('@') == -1){ checkmobilestatus(); }
	 */
}

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
});

function lockBtnLogin() {
	var olg = $("#signIn");
	olg.html("登录中...");
	olg.attr("disabled", "disabled");
}
function unlockBtnLogin() {
	var olg = $("#signIn");
	olg.html("立刻登录");
	olg.removeAttr("disabled");
}
function setErrMsg(msg) {
	$(".login-err-msg").html(msg);
}
function setErrMsg(msg) {
	$(".login-err-msg").html(msg);
}

function clearErrMsg() {
	setErrMsg("");
}

// bind login btn event handler
$(function() {
	$("#signIn").click(function() {
		login();
	});
});

function login() {
	var errorMsg = "";
	var onm = $("input[name='userName']");
	var nm = onm.val();
	var ops = $("input[name='password']");
	var ps = ops.val();
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
			if (result == null) {
				setErrMsg("登陆失败！");
				unlockBtnLogin();
			} else if (result["status"] == "true" || result.status == true) {
				$("#signIn").html("登录成功");
				window.location.href = contextPath + "/jsp/frame/main.jsp";
			} else {
				setErrMsg(result["message"]);
				unlockBtnLogin();
			}
		}, "json");
	}
}