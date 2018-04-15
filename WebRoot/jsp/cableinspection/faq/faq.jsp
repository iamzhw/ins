<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../util/head.jsp"%>
			<title>使用说明</title>
	</head>
  <body>
  <div>
  <img alt="" src="<%=path%>/images/faq/1.GIF" id="gif">
  </div>
  <div>
    <ul class="page" id="page" style="float:left;">
  <li id="shouye" class="p-prev disabled">
   <a href='javascript:indexpage(1);' onclick="jumpToPage(1);">首 页</a>
  </li>
  <li id="shangyiye" class="p-prev disabled" >
   <a href='javascript:indexpage(-1);' onclick="jumpToPage(-1);"><i></i>上一页</a>
  </li>
  <li ><a id="one" href="javascript:indexpage(1);" onclick="jumpToPage(1);">1</a></li>
  <li><a id="two" href="javascript:indexpage(2);" onclick="jumpToPage(2);">2</a></li>
  <li><a id="three" href="javascript:indexpage(3);" onclick="jumpToPage(3);">3</a></li>
  <li class="more"><a id="five" href="javascript:void(0);" >...</a></li>
  <li><a id="last" href="javascript:indexpage(57);" onclick="jumpToPage(57);">57</a></li>
  <li class='p-next'>
   <a id="p-next" href='javascript:indexpage(-2);' onclick="jumpToPage(-2);">下一页<i></i></a>
  </li>
  <li id="weiye" class='p-next'>
   <a href='javascript:indexpage(57);' onclick="jumpToPage(57);">尾 页</a>
  </li>
  <li class="total">
      <span id="span_number">共57页 到第<input type="text" id="input_number" class="page-txtbox" />页
       <input name="" value="确定" type="button" onclick="jumpToPage(-3)" class="page-btn"/>
      </span>
  </li>
  <li><a href="javascript:void(0);" onclick="downloadppt();">下载说明文档</a></li>
 </ul>
 </div>
  <script type="text/javascript">

 function indexpage(index){
 	var gif=$("#gif")[0].src; 
  	var list = gif.split("/");
  	var gifnum=parseInt(list[list.length-1].substring(0,list[list.length-1].indexOf(".")));
  	var gifsrc="<%=path%>/images/faq/";
	if(index==-1&&gifnum!=1){
	$("#gif").attr('src',gifsrc+(gifnum-1)+'.GIF');
	}else if(index==-2&&gifnum!=57){
	$("#gif").attr('src',gifsrc+(gifnum+1)+'.GIF');
	}else if(index>0&&index<58){
	$("#gif").attr('src',gifsrc+index+'.GIF');
	}
 }
function jumpToPage(num){
   	var gif=$("#gif")[0].src; 
  	var list = gif.split("/");
  	var gifnum=parseInt(list[list.length-1].substring(0,list[list.length-1].indexOf(".")));
  if(num==57){
   $('#one').text(num-2);
   $('#one').attr('href','javascript:indexpage("'+(num-2)+'");');
   $('#two').text(num-1);
   $('#two').attr('href','javascript:indexpage("'+(num-1)+'");');
   $('#three').text(num);
   $('#three').attr('href','javascript:indexpage("'+(num)+'");');
   }else if(num==1){
   $('#one').text(num);
   $('#one').attr('href','javascript:indexpage("'+num+'");');
   $('#two').text(num+1);
   $('#two').attr('href','javascript:indexpage("'+(num+1)+'");');
   $('#three').text(num+2);
   $('#three').attr('href','javascript:indexpage("'+(num+2)+'");');
   }else if(num==-3){
	   var mypage=$("#input_number").val();
	   if(mypage>0&&mypage<=57){
	   	jumpToPage(mypage);
		indexpage(mypage);
	   }
   }else{
   if(gifnum>1&&gifnum<57){
    $('#one').text(gifnum-1);
   $('#one').attr('href','javascript:indexpage("'+(gifnum-1)+'");');
   $('#two').text(gifnum);
   $('#two').attr('href','javascript:indexpage("'+gifnum+'");');
   $('#three').text(gifnum+1);
   $('#three').attr('href','javascript:indexpage("'+(gifnum+1)+'");');
   }
   	
   }

}
function downloadppt() {
			$("#hiddenIframe").attr("src", "./download.do");
}
 </script>
 <style>
 ul li{
 list-style:none;
 float:left;
 margin-left:5px;
 }
 ul{
 padding:0px;
 }
 img{
 height:500px;
 }
#input_number {
 width:20px;
 }
 </style>
 <iframe src="" style="display: none;" id="hiddenIframe"></iframe>
  </body>
</html>
