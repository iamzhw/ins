<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title>中继段编辑</title>


</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formUpdate" method="post">
            <input type="hidden" id="relay_id" name="relay_id" value="${RELAY_ID}">
           
			<table>
				
				<tr>
					<td>中继段名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="relay_name" id="relay_name" value="${RELAY_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>所属光缆：</td>
					<td>
						<div>


							<select name="cable_id" id="cable_id" class="condition-select" onchange="getOwnArea(this.value)">
								<option value=''>--请选择--</option>
								<c:forEach items="${cableList}" var="res">

									<option value='${res.CABLE_ID}' <c:if test="${res.CABLE_ID==CABLE_ID}">selected</c:if>>${res.CABLE_NAME}</option>
									
									
								</c:forEach>


							</select>


						</div></td>
				</tr>
				<tr>
					<td>维护等级：</td>
					<td>
						<div class="">

							<select name="protect_grade" id="protect_grade"
								class="condition-select">
								<option value='A' <c:if test="${PROTECT_GRADE=='A'}">selected</c:if>>A</option>
								<option value='B' <c:if test="${PROTECT_GRADE=='B'}">selected</c:if>>B</option>
								<option value='C' <c:if test="${PROTECT_GRADE=='C'}">selected</c:if>>C</option>



							</select>

						</div></td>
				</tr>
				 <tr>
					<td>所属地区：</td>
					<td>
						<div id="area_ids">
							 <c:forEach items="${cableAreaList}" var="res">
								    <input type="checkbox" name="area_id" value="${res.AREA_ID}"/>${res.AREA_NAME}<br/>
							 </c:forEach>
							
						</div>
					</td>
				</tr>
				

			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="updateForm()">保存</div>
			
		</div>
	</div>
	
	<script type="text/javascript">
  $(function(){
	  var relayArea='${relayArea}';
	 
	  relayArea=relayArea.split(',');
		
		  $("input[name=area_id]").each(function(){
			  var  v=this.value;
			  for(var i in relayArea){//
				  if(v==relayArea[i]){
						this.checked='checked';//选中checkbox
	                    continue;
					}
				}
			
		  });
		
  });
</script>
</body>
</html>
