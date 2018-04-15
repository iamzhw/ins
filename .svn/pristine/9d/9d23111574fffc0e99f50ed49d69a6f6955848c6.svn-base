<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/jsp/util/head.jsp"%>

<title>新增员工</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<table>
				<tr>
					<td>员工姓名：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition" type="text" name="vstaff_name" data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>员工帐号：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition" type="text" name="vstaff_no" id="vstaff_no" required="true" missingMessage='必填项' validType="Unique_validation['']"  />
						</div></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition" type="password" name="vpassword_1" id="pwd" required="true" missingMessage='必填项'/>
						</div>
					</td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition" type="password" name="vpassword_2" id="rpwd" required="true" missingMessage='必填项' validType="equals['#pwd']"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>手机号码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition mobile" type="text" name="vtel" required="true" validType="mobile['']"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>证件号码：</td>
					<td><!--  required="true" missingMessage='必填项' -->
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vid_number" data-options="required:true,validType:['idcard[]','validate_IdCard[]']"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>邮箱：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vemail"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>地市：</td>
					<td>
						<select name="varea" class="condition-select condition" onchange="getSonAreaList('add')" data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
							<c:forEach items="${areaList}" var="al" varStatus="vs">
								<option value="${al.AREA_ID}"
									<c:if test="${al.AREA_ID==staff.AREA_ID}">selected</c:if>>${al.NAME}</option>
							</c:forEach>
						</select> 
						<script>
							$(function() {
								getSonAreaList('add');
							})
						</script>
					</td>
				</tr>
				<tr>
					<td>区县：</td>
					<td><select name="vson_area"
						class="condition-select condition"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
					</select>
					</td>
				</tr>
				<tr>
					<td>组织关系：</td>
					<td><select name="own_company" id="own_company"
						class="condition-select condition"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
						
					</select>
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select name="vstatus" class="condition-select condition">
							<option value="0" selected>可用</option>
							<option value="1">不可用</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>维护员类型：</td>
					<td><select name="maintor_type" class="condition-select condition">
							<option value="0" selected>自维</option>
							<option value="1">代维</option>
					</select>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			<div class="btn-operation" onClick="clearForm()">清空</div>
		</div>
	</div>
</body>
</html>