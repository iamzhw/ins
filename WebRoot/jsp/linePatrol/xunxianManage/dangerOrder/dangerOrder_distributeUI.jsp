<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title></title>

<script type="text/javascript">

</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formDistribute" method="post">
			<input type="hidden"  id="order_id"  value="${order_id}" />
			<table>
				<tr>
					<td>接单人 ：</td>
					<td>
						
						 <div>
								<select name="" id="p_handle_person" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${handlePerson}" var="res">
										<option value='${res.STAFF_ID}' <c:if test="${res.STAFF_ID==founder_uid}">selected</c:if> >${res.STAFF_NAME}</option>
									</c:forEach>
								</select>
							</div>
					</td>
				</tr>
				<tr>
					<td>处理期限 ：</td>
					<td>
						<div class="condition-text-container">
								<input class="condition-text  condition"
									type="text" name="" id="limit_time"
									onClick="WdatePicker();"
									 />
							</div>
					</td>
				</tr>
				<tr>
					<td>派单内容 ：</td>
					<td>
						<div class="">
							
						    <textarea rows="4" cols="30" id="distribute_remark"></textarea>
						</div>
					</td>
				</tr>
				
				
				<%--<tr>
					<td>隐患描述 ：</td>
					<td>
						
						<div class="">
							
						    <textarea rows="4" cols="30" disabled="disabled">${danger_question}</textarea>
						</div>
					</td>
				</tr>

			--%></table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="paidan()">派单</div>

		</div>
		
	</div>
</body>
</html>
