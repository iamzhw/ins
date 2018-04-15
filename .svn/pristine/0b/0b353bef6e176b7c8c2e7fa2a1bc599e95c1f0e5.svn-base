<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>外力点维护方案编辑</title>

<script type="text/javascript">

</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formEdit" method="post">
            <input type="hidden" id="scheme_id" name="scheme_id" value="${SCHEME_ID}">
           
			<table>
				<tr>
					<td>方案名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="scheme_name" id="scheme_name" value="${SCHEME_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>方案内容：</td>
					<td>
						<div class="">
							
						   <textarea id="ms_content" name="ms_content" rows="3" cols="19">${MS_CONTENT}</textarea>
						  
						
						</div></td>
				</tr>
				<tr>
					<td>外力点：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="" id=""  value="${SITE_NAME}"  
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				
				
				
				
				<tr>
					<td>制定人员：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="" id=""  value="${CREATED_PERSON}" 
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>方案状态：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="" id="" value="未确认" 
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>确认人员：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="" id="" value="${COMMIT_NAME}"  
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>确认时经度：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="" id=""  value="${COMMIT_X}" 
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>确认时纬度：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="" id=""  value="${COMMIT_Y}" 
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>确认时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="" id=""  value="${COMMIT_DATE}" 
								/>
						</div></td>
				</tr>
				
				<tr>
					<td>是否超期：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="" id=""  value="${IS_TIMEOUT==1?'未超期':'超期'}" 
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				
				
				

			</table>
		</form>
		
	</div>
</body>
</html>
