<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title>代巡休假管理新增</title>


</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formAdd" method="post">

			<table>

				<tr>
					<td>日期：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="holiday_date" id="holiday_date" onClick="WdatePicker();"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>人员：</td>
					<td>
						<div>
							<select name="user_id" id="user_id" class="condition-select">
								
								<c:forEach items="${localInspactStaff}" var="res">
									<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
								</c:forEach>
							</select>


						</div></td>
				</tr>
				<tr>
					<td>类型：</td>
					<td>
						

						<div>
							<select name="holiday_type" id="holiday_type" onchange="disableReplaceByAdd(this.value)"
								class="condition-select">
								<option value='0'>休假</option>
								<option value='1'>代巡</option>

							</select>


						</div></td>
				</tr>
				<tr>
					<td>代巡人：</td>
					<td>


						<div>
							<select name="replace_id" id="replace_id" disabled="disabled"
								class="condition-select">
								<option value=''>--请选择--</option>
								<c:forEach items="${localInspactStaff}" var="res">
									<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
								</c:forEach>
							</select>


						</div></td>
				</tr>
				
				<tr>
					<td>休息类型：</td>
					<td>
						

						<div>
							<select name="rest_type" id="rest_type" 
								class="condition-select">
								<option value='0'>事假</option>
								<option value='1'>调休</option>

							</select>


						</div></td>
				</tr>
		
				<tr>
					<td>休假原因：</td>
					<td>
						<div>
							<textarea rows="3" cols="14" style="resize:none;" name="holiday_desc"></textarea>
						</div>
					</td>
				</tr>
	
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>

		</div>
	</div>
	
	<script type="text/javascript">
		function disableReplaceByAdd(type) {
			if (type == 0) {
				$("#formAdd #replace_id")[0].disabled = true;
			} else {
				$("#formAdd #replace_id")[0].disabled = false;
			}
		}
	</script>

</body>
</html>
