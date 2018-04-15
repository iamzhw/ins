<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>巡线点删除</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 30px;">
				<form id="form" action="" method="post">
					<table class="condition">
						<tr>
							<td align="left" width="15%">光缆名称：</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input class="condition-text" value="${cableName}" readonly="readonly"/>
								</div>
							</td>
							<td align="left" width="15%">中继段名称：</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input class="condition-text" value="${relayName}" readonly="readonly"/>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
			    <div style="float: right;" class="btn-operation" onClick="doDelete()">删除</div>
			</div>
		</div>
		<table id="dg" class="easyui-datagrid">
		    <thead>
				<tr>
					<th data-options="field:'site',width:100,checkbox:true"></th>
					<th data-options="field:'area_name',width:100">地市</th>
					<th data-options="field:'cable_name',width:100">光缆</th>
					<th data-options="field:'relay_name',width:100">中继段</th>
					<th data-options="field:'site_id',width:100">巡线点ID</th>
					<th data-options="field:'site_name',fitColumns:true">巡线点名称</th>
					<th data-options="field:'site_type',width:100">巡线点类型</th>
					<th data-options="field:'longitude',width:100">经度</th>
					<th data-options="field:'latitude',width:100">纬度</th>
				</tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${siteList }" var="site" varStatus="porStatus">
					<tr>
						<td>${site.SITE_ID }</td>
						<td>${site.AREA_NAME }</td>
						<td>${site.CABLE_NAME }</td>
						<td>${site.RELAY_NAME }</td>
						<td>${site.SITE_ID }</td>
						<td>${site.SITE_NAME }</td>
						<td>${site.SITE_TYPE }</td>
						<td>${site.LONGITUDE }</td>
						<td>${site.LATITUDE }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
	<script type="text/javascript">
		function doDelete(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择巡线点!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('确认','是否删除，删除后无法恢复',function(r){
					if(r){
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].site_id;
							arr[arr.length] = value;
						}
						$.ajax({
							type : 'POST',
							url : webPath + "siteController/doDelete.do?ids=" + arr,
							dataType : 'json',
							success : function(result) {
								var json = result.msg; 
								if("001" == json){
									$.messager.alert("操作提示", "操作成功！");
								}else if("002" == json){
									$.messager.alert("操作提示", "有设施点在巡线段中，请先到此巡线段中解除此设施点！");
								}else{
									$.messager.alert("操作提示", "操作失败，请联系管理员！");
								}
							}
						});
					}
					
					
				});
				
			}
		}
		
	</script>
</html>