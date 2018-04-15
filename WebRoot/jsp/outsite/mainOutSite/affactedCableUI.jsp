<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>外力点新增</title>


</head>
<body>
	<div style="padding:20px 0 10px 0px">
		<form id="ac" method="post">

			<table>

				<c:forEach items="${cableList}" var="res">
					<tr>
						<td><input type="radio" name="tbcable" value="${res.CABLE_ID}:${res.CABLE_NAME}" onclick="showTheCheckedCable()"/>${res.CABLE_NAME}</td>
					</tr>
				</c:forEach>




			</table>
		</form>
		
	</div>
	
	<script type="text/javascript">
   $(function(){
	   var affected_fiber='${affected_fiber}';
	  
	   if(affected_fiber!=''){
		   var afIds =affected_fiber.split(",");
		  // alert(afIds[0]);
		   $("[name=tbcable]").each(function(){
			   var v=this.value.split(":");
			   for(var i in afIds){
				   
				   if(v[0]===afIds[i]){
					   this.checked='checked';
					   continue;
				   }
			   }
		   });
	   }
	  
   });

   
   
</script>
</body>
</html>
