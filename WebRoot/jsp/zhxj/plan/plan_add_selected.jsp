<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>选择计划</title>
</head>
<body>
<input name="billIds" value="${billIds}" type="hidden"/>
<input name="operate" value="${operate}" type="hidden"/>
	<div class="condition-container" style="height: 100px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
						<tr>
								<td width="10%">请选择计划:</td>
								<td width="20%">
									<select id='plan_type_selection'>
										<option value='201'>缆线计划</option>
										<option value='202'>区域计划</option>
										<option value='203'>关键点计划</option>
										<option value='204'>看护计划</option>
									</select>
								</td>
						</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="jumpPlanAdd()" style="float: right;">确定</div>
			<div class="btn-operation" onClick="cancelPlanAdd()" style="float: right;">取消</div>
		</div>
	</div>
	<script type="text/javascript">
	</script>
</body>
</html>