
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>设备工单信息</title>
</head>
	<body style="padding: 3px; border: 0px">
		<h3>设备信息</h3>
		<table class="easyui-datagrid" style="width:1000px;height:80px" data-options="remoteSort: false">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'equipment_code',sortable:true">设备编码</th>   
		            <th data-options="field:'equipment_name',sortable:true">设备名称</th>   
		            <th data-options="field:'address'">设备地址</th>
		            <th data-options="field:'grid_name'">综合化维护网格</th>
		            <th data-options="field:'res_type'">设备类型</th>
		            <th data-options="field:'manage_area'">管理区名称</th>
		        </tr>   
		    </thead>   
		    <tbody>
		    	<c:forEach items="${eqpInfo}" var="eqp">
	            	<tr>
	            		<td>${eqp.EQUIPMENT_CODE }</td>
	            		<td>${eqp.EQUIPMENT_NAME }</td>
	            		<td>${eqp.ADDRESS }</td>
	            		<td>${eqp.GRID_NAME }</td>
	            		<td>${eqp.RES_TYPE }</td>
	            		<td>${eqp.MANAGE_AREA }</td>
	            		<td></td>
	            	</tr>
	            </c:forEach>
		    </tbody>   
		</table>
		<h3>工单信息</h3>
		<table class="easyui-datagrid" style="width:1400px;height:300px" data-options="remoteSort: false">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'rownum'" ><strong>序号</strong></th>
					<th data-options="field:'dzbm',sortable:true" ><strong>端子编码</strong></th>
					<th data-options="field:'sbbm',sortable:true" ><strong>设备编码</strong></th>
					<th data-options="field:'SBMC',sortable:true" ><strong>设备名称</strong></th>
					<th data-options="field:'GLBH',sortable:true" ><strong>光路编码</strong></th>
					<th data-options="field:'GLMC'" ><strong>光路名称</strong></th>
					<th data-options="field:'WORK_ORDER_NO'" ><strong>工单号</strong></th>
					<th data-options="field:'ACTION_TYPE'" ><strong>工单类型</strong></th>
					<th data-options="field:'mark'" ><strong>工单来源</strong></th>
					<th data-options="field:'bdsj'" ><strong>取数时间</strong></th>
					<th data-options="field:'ARCHIVE_TIME'" ><strong>工单竣工时间</strong></th>
		        </tr>   
		    </thead>   
		    <tbody>
		    	<c:forEach items="${orderInfo}" var="order" varStatus="orderStatus">
	            	<tr>
						<td>${orderStatus.index + 1}</td>
						<td>${order.DZBM}</td>
						<td>${order.SBBM}</td>
						<td>${order.SBMC}</td>
						<td>${order.GLBH}</td>
						<td>${order.GLMC}</td>
						<td>${order.WORK_ORDER_NO}</td>
						<td>${order.ACTION_TYPE}</td>
						<td>${order.MARK}</td>
						<td>${order.BDSJ}</td>
						<td>${order.ARCHIVE_TIME}</td>
	            	</tr>
	            </c:forEach>
		    </tbody>   
		</table>
	</body>
</html>
