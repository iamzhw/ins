<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>看护时间段模板新增</title>

<script type="text/javascript">

</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formAdd" method="post">
			
			<table>
				
				<tr>
					<td>开始时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="start_time" id="start_time"  onfocus="WdatePicker({dateFmt:'HH:mm'})"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="end_time" id="end_time" onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'start_time\')}'})"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				
				<%--<tr>
					<td>城市：</td>
					<td>
						<div class="">
						<select name="parent_city" id="parent_city" class="condition-select">
									
									<c:forEach items="${cityList}" var="res">
										<option value='${res.CITY_ID}'>${res.CITY_NAME}</option>
									</c:forEach>
								</select>
						
						</div></td>
				</tr>
				--%><tr>
					<td>是否默认：</td>
					<td>
						<div class="">
						<select name="is_pritermission" id="is_pritermission" class="condition-select">
									<option value='1'>是</option>
									<option value='0'>否</option>
									
						</select>
						
						</div></td>
				</tr>
				
				
				

			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>

		</div>
	</div>
</body>
</html>
