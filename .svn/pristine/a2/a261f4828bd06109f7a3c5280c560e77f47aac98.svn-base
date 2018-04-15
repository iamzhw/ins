<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<script type="text/javascript">

</script>

<title>巡线点信息</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		
		
		
		<div>
			   <p><span>巡线现场图片</span></p>   
			<c:if test="${photosize>0}">
				<div id="photo"  data-options="closable:false" style="overflow:auto;">
				<c:forEach items="${photoList}" var="p">
				  <a href="${p.PHOTO_PATH }" title="照片预览" class="nyroModal">
					<img src="${p.MICRO_PHOTO}" style="width: 100px;"/>
				</c:forEach>
					
				</div>
			</c:if>  
			
		</div>		 	
		
		
	</div>  
	<div id="photo_show"></div>
	<script type="text/javascript">
	$(function(){
		$('.nyroModal').nyroModal();
	});
	openwindow = function (){
	document.getElementById("more_picture").style.display="block" ; 
	document.getElementById("photo").style.display="none" ; 
	}
	</script> 
</body>
</html>
