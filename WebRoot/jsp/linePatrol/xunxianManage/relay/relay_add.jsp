<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title>中继段新增</title>

<script type="text/javascript">

</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formAdd" method="post">

			<table>

				<tr>
					<td>中继段名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="relay_name" id="relay_name"
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

									<option value='${res.CABLE_ID}'>${res.CABLE_NAME}</option>
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
								<option value='A'>A</option>
								<option value='B'>B</option>
								<option value='C'>C</option>



							</select>

						</div></td>
				</tr>
                <tr>
					<td>所属地区：</td>
					<td>
						<div id="area_ids">
							 <c:forEach items="${areaList}" var="res">
								    <input type="checkbox" name="area_id" value="${res.AREA_ID}"/>${res.NAME}<br/>
							 </c:forEach>
							
						</div>
					</td>
				</tr>
                

			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>

		</div>
	</div>
</body>
</html>
