<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>短信模板编辑</title>

<script type="text/javascript">

</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formEdit" method="post">
            <input type="hidden" id="mms_id" name="mms_id" value="${MMS_ID}">
           
			<table>
				<tr>
					<td>模板名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="mss_name" id="mss_name" value="${MSS_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>模板类型：</td>
					<td>
						<div>
							<select name="mms_type" id="mms_type" class="condition-select">
								
								<c:forEach items="${modalTypeList}" var="res">

									<option value='${res.TYPE_ID}' <c:if test="${res.TYPE_ID==MMS_TYPE}">selected</c:if>>${res.TYPE_NAME}</option>
									
									
									
								</c:forEach>

							</select>


						</div></td>
				</tr>
				
				<tr>
					<td>模板内容：</td>
					<td>
						<div class="">

							<textarea id="mm_content" name="mm_content" rows="8" cols="50">${MM_CONTENT}</textarea>

						</div>
					</td>
				</tr>
                <tr>
					<td></td>
					<td>
						<input id="" type="button" value="插入模板项目" onclick="insertToContent()"/>
						
					</td>
				</tr>



			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="updateForm()">保存</div>
			
		</div>
	</div>
</body>
</html>
