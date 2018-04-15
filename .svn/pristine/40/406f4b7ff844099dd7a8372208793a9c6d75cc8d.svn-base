
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片预览</title>
<script src="http://www.codefans.net/ajaxjs/jquery-1.6.2.min.js" type="text/javascript"></script>
<script>
/* 标题按钮事件 */
	
$(function(){

	var ul = $(".lxfscroll ul");
	var li = $(".lxfscroll li");
	var tli = $(".lxfscroll-title li");	
	var speed = 350;
	var autospeed = 4500;	
	var i=1;
	var index = 0;
	var n = 0;
    function lxfscroll() {
				var index = tli.index($(this));	
				tli.removeClass("cur");
				$(this).addClass("cur");
				
				ul.css({"left":"0px"});	
				li.css({"left":"0px"}); 
				li.eq(index).css({"z-index":i});	
				li.eq(index).css({"left":"800px"});	
				ul.animate({left:"-800px"},speed); 	
				i++;	
			
    };
	/* 自动轮换 */
	function autoroll() {
					if(n >= li.length) {
						n = 0;
					}
					tli.removeClass("cur");
				tli.eq(n).addClass("cur");
					ul.css({"left":"0px"});	
				li.css({"left":"0px"}); 
				li.eq(n).css({"z-index":i});	
				li.eq(n).css({"left":"800px"});	
				 	
					n++;
					i++;
					timer = setTimeout(autoroll, autospeed);
					ul.animate({left:"-800px"},speed);
				};
	/* 鼠标悬停即停止自动轮换 */
				function stoproll() {
					li.hover(function() {
						clearTimeout(timer);
						n = $(this).prevAll().length+1;
					}, function() {
						timer = setTimeout(autoroll, autospeed);
					});
					tli.hover(function() {
						clearTimeout(timer);
						n = $(this).prevAll().length+1;
					}, function() {
						timer = setTimeout(autoroll, autospeed);
					});
				};			
	tli.mouseenter(lxfscroll);
	autoroll();
	stoproll();
});
</script>
<style type="text/css">
* {
	font-size:12px;
	color:#333;
	text-decoration:none;
	padding:0;
	margin:0;
	list-style:none;
	font-style: normal;
	font-family: Arial, Helvetica, sans-serif;
}
.lxfscroll {
	width:800px;
	margin-left:auto;
	margin-right:auto;
	margin-top: 20px;
	position: relative;
	height: 450px;
	border: 4px solid #EFEFEF;
	overflow: hidden;
}

.lxfscroll ul li {
	height: 450px;
	width: 800px;
	text-align: center;
	line-height: 450px;
	position: absolute;
	font-size: 40px;
	font-weight: bold;
}
.lxfscroll-title{
	width: 800px;
	margin-right: auto;
	margin-left: auto;
}
.lxfscroll-title li{
	height: 20px;
	width: 20px;
	float: left;
	line-height: 20px;
	text-align: center;
	border: 1px dashed #CCC;
	margin-top: 2px;
	cursor: pointer;
	margin-right: 2px;
}
.cur{
	color: #FFF;
	font-weight: bold; background:#000;
	
	
}
.lxfscroll ul {
	position: absolute;
}
</style>
</head>
<body >
<div class="lxfscroll">
  <ul>
  	<c:forEach items="${photoList}" var="photo" >
		<li><img src="${photo.PHOTO_PATH}" width="800" height="450"/><li>
	</c:forEach>
  </ul>
</div>
<div class="lxfscroll-title">
  <ul>
    <li class="cur">1</li>
  </ul>
</div>
</body>
</html>
<script>
	var len= $(".lxfscroll li").length;
	var st=2;
	for(var k=0;k<len;k++){
	 var sr=($(".lxfscroll li").eq(k).find("img").attr('src'));
	 if(sr==""||sr==null){
	 $(".lxfscroll li").eq(k).remove();
	 len=len-1
	 }
	}
	
	for(var jk=1;jk<len;jk++){
		$(".lxfscroll-title ul").append("<li>"+st+"</li>")
		st=st+1;
	}
</script>