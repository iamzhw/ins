<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+
					  request.getServerName()+":"+
					  request.getServerPort()+path+"/";
	String serverName = request.getServerName();
	String serverPort = ""+request.getServerPort();
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/umeng/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/new_style.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/main.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/nyroModal.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/jquery.autocomplete.css">
<!--
<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/demo.css">11
-->
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
<!-- <script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script> -->
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.modified.js"></script>
<script type="text/javascript" src="<%=path%>/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.nyroModal.custom.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/jscolor/jscolor.js"></script>
<script type="text/javascript">
$.extend($.fn.datagrid.methods, {
    fixRownumber : function (jq) {
        return jq.each(function () {
            var panel = $(this).datagrid("getPanel");
            //获取最后一行的number容器,并拷贝一份
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
            clone.css({
                "position" : "absolute",
                left : -1000
            }).appendTo("body");
            var width = clone.width("auto").width();
            //默认宽度是25,所以只有大于25的时候才进行fix
            if (width > 25) {
                //多加5个像素,保持一点边距
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                $(this).datagrid("resize");
                //一些清理工作
                clone.remove();
                clone = null;
            } else {
                //还原成默认状态
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
            }
        });
    }
});


var webPath="<%=path%>/";
$.extend($.fn.validatebox.defaults.rules, {
	equals: {    
        validator: function(value,param){    
            return value == $(param[0]).val();    
        },    
        message: '输入的密码不一致'   
    },
	//移动手机号码验证
	mobile: { //value值为文本框中的值
	    validator: function(value) {
	        var reg = /^1[0-9]\d{9}$/;
	        return reg.test(value);
	    },
	    message: '输入手机号码格式不准确'
	},
	//验证邮编  
	zipcode: {
	    validator: function(value) {
	        var reg = /^[1-9]\d{5}$/;
	        return reg.test(value);
	    },
	    message: '邮编必须是非0开始的6位数字'
	},
	idcard: { // 验证身份证
	    validator: function(value) {
	    	//isIDCard=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/; 
	        return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
	    },
	    message: '身份证号码格式不正确'
	},
	phone: { // 验证电话号码
	    validator: function(value) {
	        return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
	    },
	    message: '格式不正确,请使用下面格式:020-88888888'
	},
	msn: {
	    validator: function(value) {
	        return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
	    },
	    message: '请输入有效的msn账号'
	},
	qq: { // 验证QQ,从10000开始
	    validator: function(value) {
	        return /^[1-9]\d{4,9}$/i.test(value);
	    },
	    message: 'QQ号码格式不正确'
	},
	integer: { // 验证整数
	    validator: function(value) {
	        return /^[+]?[1-9]+\d*$/i.test(value);
	    },
	    message: '请输入整数'
	},
	doubleTest: { // 验证正实数
	    validator: function(value) {
	        return /^[+-]?\d+(\.\d*)?$/i.test(value); 
// 	        /^[0-9]+(.[0-9]{1,3})?$/i.test(value);
	    },
	    message: '请输入数字'   
	},
	faxno: { // 验证传真
	    validator: function(value) {
	        return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
	    },
	    message: '传真号码不正确'
	},
	enstr: { // 验证之只能输入英文
	    validator: function(value) {
	        return /^([u4e00-u9fa5]|[ufe30-uffa0]|[a-za-z0-9_])*$/i.test(value);
	    },
	    message: '只能输入英文'
	},
	zhstr: { // 验证之只能输入中文
	    validator: function(value) {
	        return /^[u4E00-u9FA5]+$/i.test(value);
	    },
	    message: '只能输入中文'
	},
	
	//时间区间验证
	
	isAfter: {
	    validator: function(value, param) {
	        var dateA = $.fn.datebox.defaults.parser(value);
	        var dateB = $.fn.datebox.defaults.parser($(param[0]).datebox('getValue '));
	        return dateA > new Date() && dateA > dateB;
	    },
	    message: '结束时间不能小于开始时间'
	},
	isLaterToday: {
	    validator: function(value, param) {
	        var date = $.fn.datebox.defaults.parser(value);
	        return date > new Date();
	    },
	    message: '开始时间不能小于今天'
	},
	
	//ajax 验证唯一性
	Unique_validation: {
	    validator: function(value, param) {
	        var bl = false;
	        if(param[0]!=''){
	        	//alert(param[0]);
	        }
	        $.ajax({
	            type: 'POST',
	            async: false,
	            dateType: 'json',
	            url: webPath + "unifiedPage/proveUniqueness.do",
	            data: {
					staff_no:value,notstaff_no:param[0]
				},
	            success: function(result) {
	                bl = result.status;
	            }
	        });
	        return bl;
	    },
	    message: '帐号已存在'
	
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
	            url: webPath + "unifiedPage/validateIdCard.do",
	            data: {
					id_number:value,newId_number:param[0]
				},
	            success: function(result) {
	                bl = result.status;
	            }
	        });
	        return bl;
	    },
	    message: '身份证号码已存在'
	}, 
	Unique_role: {
	    validator: function(value, param) {
	        var bl = false;
	        if(param[0]!=''){
	        	//alert(param[0]);
	        }
	        $.ajax({
	            type: 'POST',
	            async: false,
	            dateType: 'json',
	            url: webPath + "Role/proveUniqueness.do",
	            data: {
					role_no:value,notrole_no:param[0]
				},
	            success: function(result) {
	                bl = result.status;
	            }
	        });
	        return bl;
	    },
	    message: '角色编码已存在'
	
	}
});
// wangyan add @ 20140513 for ie has not trim()
if(!"".trim){
	String.prototype.trim = function(){ return Trim(this);};
	function LTrim(str)
	{
	    var i;
	    for(i=0;i<str.length;i++)
	    {
	        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
	    }
	    str=str.substring(i,str.length);
	    return str;
	}
	function RTrim(str)
	{
	    var i;
	    for(i=str.length-1;i>=0;i--)
	    {
	        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
	    }
	    str=str.substring(0,i+1);
	    return str;
	}
	function Trim(str)
	{
	    return LTrim(RTrim(str));
	}
}
function CacheMap(){
	this.keys = new Array();
	this.data = new Object();
	
	this.put = function(key, value) {     
        if(this.data[key] == null){     
            this.keys.push(key);     
        }     
        this.data[key] = value;     
    };
    
    this.get = function(key) {
    	return this.data[key];
    };
    
    this.remove = function(key) {     
        this.keys.remove(key);     
        this.data[key] = null;     
    }; 
    
    this.removeAll = function(){
    	this.keys = new Array();
    	this.data = new Object();
    };
    
    this.each = function(fn){
        if(typeof fn != 'function'){     
            return;     
        }     
        var len = this.keys.length;     
        for(var i=0;i<len;i++){     
            var k = this.keys[i];     
            fn(k,this.data[k],i);     
        }
    }; 
    
    this.length = function(){
    	return this.keys.length;
    };
}
Array.prototype.remove = function(s) {     
    for (var i = 0; i < this.length; i++) {     
        if (s == this[i])     
            this.splice(i, 1);     
    }     
};
function addTab(subtitle,url){
	if(!parent.$('#tabs').tabs('exists',subtitle)){
		parent.$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			width:parent.$('#mainPanle').width()-10,
			height:parent.$('#mainPanle').height()-26
		});
		//$('#tabs').tabs('add',{    
		    //title:subtitle,    
		    //content:createFrame(url),    
		    //closable:true,    
		    //tools:[{    
		        //iconCls:'icon-mini-refresh',    
		        //handler:function(){    
		            //alert('refresh');    
		        //}    
		    //}]    
		//});  
	}else{
		parent.$('#tabs').tabs('select',subtitle);
	}
	tabClose();
}
function createFrame(url)
{
	var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}
function closeTab(){
var subtitle = parent.$('.tabs-selected').text();
	parent.$('#tabs').tabs('close',subtitle);
}
function tabClose()
{
	/*双击关闭TAB选项卡*/
	parent.$(".tabs-inner").dblclick(function(){
		var subtitle = parent.$(this).children("span").text();
		parent.$('#tabs').tabs('close',subtitle);
	})

	parent.$(".tabs-inner").bind('contextmenu',function(e){
		parent.$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
		
		var subtitle =parent.$(this).children("span").text();
		parent.$('#mm').data("currtab",subtitle);
		
		return false;
	});
}
/*企信要求：为提升各系统的使用感知，对各系统需求上线后的使用情况进行验证，请各位安排在相关系统中加入用户采集的脚本*/
	var serverName = "<%=serverName%>";
	var serverPort = "<%=serverPort%>";
	var url="http://132.228.12.35:9099/monitor/";
	if("61.160.128.47" == serverName||"zhxj.telecomjs.com" == serverName)
	{
		url="http://61.160.128.75:9099/monitor/";
	}
  	var _paq = _paq || [];
  	_paq.push(['trackPageView']);
  	_paq.push(['enableLinkTracking']);
  	_paq.push(['accessPage', true]);
  	_paq.push(['leavePage', true]);
  	_paq.push(['clickPage', true]);
  	_paq.push(['rightClick', true]);
  	_paq.push(['keymonitor', true]);
  	_paq.push(['onJSError', true]);
  	_paq.push(['onAjaxError', true]);
  	(function() {
    	
    	_paq.push(['setTrackerUrl', url]);
    	_paq.push(['setSiteId', 1047]);
    	var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];
    	g.type='text/javascript'; g.async=true; g.defer=true; g.src=url+'uam_public_kafka.js'; s.parentNode.insertBefore(g,s);
  	})();

</script>