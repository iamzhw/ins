<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>流程详情</title>
</head>
<body>
<div style="padding:20px 0 10px 50px">
	<form id="allot_troubleId" method="post">
		<table>			
			<tr class="datagrid-header">
				<th width="5%"><div class="datagrid-cell">序号</div></th>
				<th width="10"><div class="datagrid-cell">操作人</div></th>
				<th width="15"><div class="datagrid-cell">所属公司</div></th>
				<th width="25"><div class="datagrid-cell">操作描述</div></th>
				<th width="10"><div class="datagrid-cell">目标岗位</div></th>
				<th width="15%"><div class="datagrid-cell">所属公司</div></th>
				<th width="20%"><div class="datagrid-cell">操作时间</div></th>
			</tr>
		
			<c:forEach items="${historyList}" var="v" varStatus="status">
				<tr class="datagrid-body" style="height: 37px;">
					<td width="5%"><div class="datagrid-cell">${status.index + 1}</div></td>
					<td width="10%"><div class="datagrid-cell">${v.STAFF_NAME}</div></td>
					<td width="15%"><div class="datagrid-cell">${v.OWN_COMPANY}</div></td>
					<td width="25%" ><div class="datagrid-cell">${v.DESCRIPTION}</div></td>
					<td width="10%"><div class="datagrid-cell">${v.GOAL_STAFF_NAME}</div></td>
					<td width="15%"><div class="datagrid-cell">${v.GOAL_OWN_COMPANY}</div></td>
					<td width="20%" ><div class="datagrid-cell">${v.ACTION_DATE}</div></td>
				</tr>
			</c:forEach>
		</table>
	</form>
	<div style="text-align:left;padding:10px 0 10px 90px">
		<div class="btn-operation" onClick="backup()">返回</div>
	</div>
</div>

<script type="text/javascript">
	function backup(){
		$('#win_trouble').window('close');
	}
</script>
</body>
</html>
