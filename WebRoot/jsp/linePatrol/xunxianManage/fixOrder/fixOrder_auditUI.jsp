<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title>整治单审核页面</title>

<script type="text/javascript">

</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formAudit" method="post">
			<input type="hidden" id="fixorder_id" name="fixorder_id" value="${FIXORDER_ID}"/>
			<table>
				
				<tr>
					<td>隐患描述：</td>
					<td>
						<div class="">
						    <textarea rows="3" cols="19" disabled="disabled">${FIXORDER_CODE}</textarea>
						</div>
					</td>
					
				</tr>
				<tr>
					<td>回单人 ：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition" disabled="disabled"
								type="text" name="" id="" value="${HANDLE_PERSON}"
								 />
						</div>
					</td>
				</tr>
				<tr>
					<td>回单时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition" disabled="disabled"
								type="text" name="" id="" value="${REPAIR_TIME}"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>处理情况：</td>
					<td>
						<div class="">
						    <textarea rows="3" cols="19" disabled="disabled">${REPAIR_REMARK}</textarea>
						</div>
					</td>
				</tr>
				<tr>
				   <td>审核意见 ：</td>
					<td>
						<div class="">
						    <textarea rows="3" cols="19" id="repair_remark" name="repair_remark"></textarea>
						</div>
					</td>
				</tr>
				
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="audit(1)">通过</div>
			<div class="btn-operation" onClick="reject(0)">驳回</div>
		</div>
	</div>
</body>
</html>
