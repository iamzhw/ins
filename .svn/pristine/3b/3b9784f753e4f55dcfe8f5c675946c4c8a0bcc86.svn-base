<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title>代巡休假管理新增</title>

<script type="text/javascript">

</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formEdit" method="post">
           <input id="holiday_id"  name="holiday_id"  type='hidden' value="${HOLIDAY_ID}"/>
			<table>

				<tr>
					<td>日期：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="holiday_date" id="holiday_date" onClick="WdatePicker();" value="${HOLIDAY_DATE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>人员：</td>
					<td>
						<div>
							<select name="user_id" id="user_id" class="condition-select">
<!-- 								<option value=''>--请选择--</option> -->
								<c:forEach items="${localInspactStaff}" var="res">
									<option value='${res.STAFF_ID}' <c:if test="${res.STAFF_ID==USER_ID}">selected</c:if>>${res.STAFF_NAME}</option>
								   
								</c:forEach>
							</select>


						</div></td>
				</tr>
				<tr>
					<td>类型：</td>
					<td>
						

						<div>
							<select name="holiday_type" id="holiday_type"
								class="condition-select" onchange="disableReplaceByEdit(this.value)">
								<option value='0' <c:if test="${HOLIDAY_TYPE==0}">selected</c:if>>休假</option>
								<option value='1' <c:if test="${HOLIDAY_TYPE==1}">selected</c:if>>代巡</option>

							</select>


						</div></td>
				</tr>
				<tr>
					<td>代巡人：</td>
					<td>


						<div>
							<select name="replace_id" id="replace_id" <c:if test="${HOLIDAY_TYPE==0}">disabled</c:if>
								class="condition-select">
								<option value=''>--请选择--</option>
								<c:forEach items="${localInspactStaff}" var="res">
									<option value='${res.STAFF_ID}' <c:if test="${res.STAFF_ID==REPLACE_ID}">selected</c:if>>${res.STAFF_NAME}</option>
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
								<option value='0' <c:if test='${REST_TYPE eq 0 }'>selected</c:if> >事假</option>
								<option value='1' <c:if test='${REST_TYPE eq 1 }'>selected</c:if> >调休</option>

							</select>


						</div></td>
				</tr>
		
				<tr>
					<td>休假原因：</td>
					<td>
						<div>
								<textarea rows="3" cols="14" style="resize:none;" name="holiday_desc"><c:out value="${HOLIDAY_DESC }"/> </textarea>
						</div>
					</td>
				</tr>
				


			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="updateForm()">修改并保存</div>
			<div class="btn-operation" onClick="replaceHolidayDeleteUI(${HOLIDAY_ID})">删除</div>

		</div>
	</div>
<script type="text/javascript">
function disableReplaceByEdit(type) {
	if (type == 0) {
		$("#formEdit #replace_id")[0].disabled = true;
	} else {
		$("#formEdit #replace_id")[0].disabled = false;
	}
}

</script>
</body>
</html>
