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
		<div style="padding: 20px 0 10px 50px">
			<form id="formAudit" method="post">
				<input type="hidden" id="order_id" name="order_id"
					value="${ORDER_ID}" />
				<table>

					<tr>
						<td>
							隐患描述：
						</td>
						<td>
							<div class="">

								<textarea rows="3" cols="19" disabled="disabled">${DANGER_QUESTION}</textarea>
							</div>
						</td>

					</tr>



					<tr>
						<td>
							派单意见：
						</td>
						<td>
							<div class="">

								<textarea rows="3" cols="19" disabled="disabled">${DISTRIBUTE_REMARK}</textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							回单人 ：
						</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text  condition" disabled="disabled"
									type="text" name="" id="" value="${HANDLE_PERSON}" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							回单时间：
						</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text  condition" disabled="disabled"
									type="text" name="" id="" value="${REPAIR_TIME}" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							处理情况：
						</td>
						<td>
							<div class="">

								<textarea rows="3" cols="19" disabled="disabled">${REPAIR_REMARK}</textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							审核意见 ：
						</td>
						<td>
							<div class="">

								<textarea rows="3" cols="19" id="audit_remark"
									name="audit_remark"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							审核时间 ：
						</td>
						<td>
							<div class="">
								<input type="text" id="audit_time" name="audit_time"
									class="easyui-datetimebox">
							</div>
						</td>
					</tr>
				</table>
			</form>
			<div style="text-align: left; padding: 10px 0 10px 50px">
				<div class="btn-operation" onClick=
	audit();
>
					审核
				</div>

			</div>
		</div>
	</body>
</html>
