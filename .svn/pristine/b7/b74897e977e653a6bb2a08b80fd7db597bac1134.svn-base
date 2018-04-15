<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<script type="text/javascript">

</script>
<title>编辑员工</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<input type="hidden" name="vstaff_id" value="${staff.STAFF_ID}" />
			<table>
				<tr>
					<td>员工姓名：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vstaff_name" value="${staff.STAFF_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>员工帐号：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								value="${staff.STAFF_NO}" type="text" name="vstaff_no"
								id="staff_no" required="true" missingMessage='必填项' 
								validType="Unique_validation['${staff.STAFF_NO}']" />
						</div></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								value="${staff.PASSWORD}" type="password" name="vpassword_1"
								id="pwd" required="true" missingMessage='必填项' />
						</div></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								value="${staff.PASSWORD}" type="password" name="vpassword_2"
								id="rpwd" required="true" missingMessage='必填项' validType="equals['#pwd']"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>手机号码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								value="${staff.TEL}" type="text" name="vtel" required="true" validType="mobile['']"/>
						</div></td>
				</tr>
				<tr>
					<td>证件号码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								value="${staff.ID_NUMBER}" type="text" 
								name="vid_number" data-options="required:true,validType:['idcard[]']"/>
						</div></td>
				</tr>
				<tr>
					<td>邮箱：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								value="${staff.EMAIL}" type="text" name="vemail" />
						</div></td>
				</tr>
				<tr>
					<td>地市：</td>
					<td><select name="varea" class="condition-select condition"
						onchange="getSonAreaList('add')"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" 
						<c:if test="${ifGly=='0'}">disabled</c:if>>							
							<c:forEach items="${areaList}" var="al">
								<option
									<c:if test="${al.AREA_ID==staff.AREA_ID}">selected</c:if>
									value="${al.AREA_ID}">${al.NAME}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>区县：</td>
					<td><select name="vson_area"
						class="condition-select condition"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
						
									
							
											
							<c:forEach items="${sonAreaList}" var="al">
								<option value="${al.AREA_ID}"
									<c:if test="${al.AREA_ID==staff.SON_AREA_ID}">selected</c:if>>${al.NAME}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>组织关系：</td>
					<td><select name="own_company" id="own_company"
						class="condition-select condition"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
					   <option value="">--请选择--</option>
						<c:forEach items="${companyList}" var="al">
								<option value="${al.ORG_ID}"
									<c:if test="${al.ORG_ID==staff.ORG_ID}">selected</c:if>>${al.ORG_NAME}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select name="vstatus" class="condition-select condition">
							<option value="0" <c:if test="${staff.STATUS==0}">selected</c:if>>正常</option>
							<option value="1" <c:if test="${staff.STATUS==1}">selected</c:if>>停用</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>维护员类型：</td>
					<td>
						<select name="maintor_type" class="condition-select condition">
								<option value="0" <c:if test="${staff.MAINTOR_TYPE==0}">selected</c:if>>自维</option>
								<option value="1" <c:if test="${staff.MAINTOR_TYPE==1}">selected</c:if>>代维</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 90px">
			<div class="btn-operation" onClick="updateForm()">更新</div>
		</div>
	</div>
</body>
</html>
