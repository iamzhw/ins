<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title>查询光缆段</title>

</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formsel" method="post">
			<table>
				<tr>
					<td width="10%">光缆段：</td>
						<td width="20%">
							<div>
								<select name="cable_id" id="cable_id" class="condition-select" onchange="getRelayByCableID(this.value)">
									<option value=''>--请选择--</option>
									<c:forEach items="${cableList}" var="res">
										<option value='${res.CABLE_ID}'>${res.CABLE_NAME}</option>
									</c:forEach>
								</select>
							</div>
						</td>
						
				</tr>
				<tr>
					<td width="10%">中继段：</td>
						<td width="20%">
							<div>
								<select name="relay_id" id="relay_id" class="condition-select">
								</select>
							</div>
						</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="selByCableRelay()">查询</div>
			
		</div>
	</div>
	
	<script type="text/javascript">
		
		function getRelayByCableID(cable_id){
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "StepPart/getRelay.do",
				  data:{cable_id:cable_id},
				  dataType:"json",
				  success:function(data){
					  $("#relay_id").empty();
					  $("#relay_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.relayList,function(i,item){
						  $("#relay_id").append("<option value='"+item.RELAY_ID+"'>"+item.RELAY_NAME+"</option>");		
					  });
				  }
			  });
		}
		
	</script>
</body>
</html>