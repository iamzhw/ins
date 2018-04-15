<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>系统监控</title>
		<%@include file="../../util/head.jsp"%>
	</head>
	<body style="padding: 3px; border: 0px">
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 30px;">
				<form id="form" action="" method="post">
					<table class="condition">
						<tr>
							<td align="left" width="15%">服务器：</td>
							<td width="25%" align="left">
								<select name="hostName" class="condition-select">
									<option value="">请选择</option>
									<c:forEach items="${serverConfigList}" var="serverConfig">
										<option value="${serverConfig.HOST_NAME}">${serverConfig.HOST_NAME}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div style="float: left;" class="btn-operation" onClick="getDBStatus()">查看表空间</div>
				<div style="float: left;" class="btn-operation" onClick="clearLogs()">清理表空间</div>
			    <div style="float: right;" class="btn-operation" onClick="reloadServer()">重启服务</div>
			    <div style="float: right;" class="btn-operation" onClick="executeCommand()">优化缓存</div>
			    <div style="float: right;" class="btn-operation" onClick="findStatus()">查看服务器</div>
			</div>
		</div>
		<table id="dg" title="【服务器监控】" style="width: 100%"></table>
		<script type="text/javascript">
			function getDBStatus(){
				$('#dg').datagrid({
					autoSize : true,
					fit : true,
					fitColumns : true,
					autoRowHeight : true,
					url : webPath + "monitor/getDBStatus.do",
					columns : [[
						{field : 'TABLESPACE_NAME',title : '表空间',align : 'left'},
						{field : 'TOTAL',title : '总量',align : 'left'},
						{field : 'USED',title : '已使用',align : 'left'},
						{field : 'FREE',title : '空闲',align : 'left'},
						{field : '%USED',title : '已使用(%)',align : 'left'},
						{field : '%FREE',title : '空闲(%)',align : 'left'}
					]],
					onLoadSuccess : function(data) {
						$("body").resize();
					}
				});
			}
			function findStatus(){
				var hostName = $("select[name='hostName']").val();
				$('#dg').datagrid({
					autoSize : true,
					fit : true,
					fitColumns : true,
					autoRowHeight : true,
					url : webPath + "monitor/findStatus.do",
					queryParams : {
						hostName : hostName
					},
					columns : [[
						{field : 'hostName',width : "4%",title : '主机',align : 'left'},
						{field : 'command',width : "10%",title : '命令',align : 'left'},
						{field : 'exitCode',width : "4%",title : '返回结果',align : 'left'},
						{field : 'details',title : '详情',align : 'left',width:'20%'}
					]],
					nowrap : false,
					striped : true,
					onLoadSuccess : function(data) {
						$("body").resize();
					}
				});
			}
			function executeCommand(){
				var hostName = $("select[name='hostName']").val();
				$.ajax({
					url : webPath + "monitor/executeCommand.do",
					type : 'POST',
					data : {
						hostName : hostName
					},
					success : function(){
						$.messager.alert('提示','执行成功！');
					}
				});
			}
			function clearLogs(){
				$.ajax({
					url : webPath + "monitor/clearLogs.do",
					type : 'POST',
					success : function(){
						$.messager.alert('提示','执行成功！');
					}
				});
			}
		
		</script>
		
	</body>
</html>