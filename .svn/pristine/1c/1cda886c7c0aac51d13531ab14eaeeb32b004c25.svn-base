<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<head>
<%@include file="../../../util/head.jsp"%>
</head>
<body style="padding: 3px; border: 0px">
	<div class="datagrid-header" style="width: 385px; height: 40px;">
		<div class="datagrid-header-inner" style="display: block;">
		<table class="datagrid-htable" border="0" cellspacing="0" cellpadding="0" style="height: 40px;">
		<tbody>
		<tr class="datagrid-header-row">
		<td><div class="datagrid-cell" style="width: 385px; text-align: center;">
		<span>步巡段</span><span class="datagrid-sort-icon">&nbsp;</span></div>
		</td>
		</tr>
		</tbody>
		</table>
		</div></div>
		<div class="datagrid-body" style="width: 385px; margin-top: 0px; height: 200px;">
		<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0">
		<c:forEach items="${detailList}" var="v" varStatus="status">
		<tr class="datagrid-row" style="height: 30px;">
		<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">${status.index+1}</div></td>
		<td><div style="width: 350px;text-align:center;;white-space:normal;height:auto;" class="datagrid-cell">${v.STEPPART_NAME}</div></td> 
		</tr>
		</c:forEach>
		</table>
		</div>
</body>