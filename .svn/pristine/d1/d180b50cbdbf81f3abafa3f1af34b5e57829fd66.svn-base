<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>日常巡检</title>
</head>

<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container" style="height:60px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<input type="hidden" name="paras_equpAreaId" value="${areaId}"/>
				<table class="condition">
				<tr></tr>
					<tr>
						<td align="left" width="15%">
							资源编码：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="paras_equCode" class="condition-text" />
							</div>
						</td>
						<td >
						<div class="btn-operation-container">
							<div class="btn-operation btnzhuan" onClick="searchData()">查询</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
	<table id="dg" title="【日常问题上报】" style="width:100%;height:auto;"></table>
	<div id=win_role></div>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function searchData() {
			var equCode = $("input[name='paras_equCode']").val().trim();
			var areaId = $("input[name='paras_equpAreaId']").val().trim();		
			var obj = {
					//taskName : taskName,
					equCode : equCode,
					areaId:areaId
					//staffNo : staffNo
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "inspect/resTrouTable.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'EQUIPMENT_CODE',
					title : '资源编码',
					width : "25%",
					align : 'center'
				}, {
					field : 'EQUIPMENT_NAME',
					title : '资源名称',
					width : "30%",
					align : 'center'
				}, {
					field : 'ADDRESS',
					title : '地址',
					width : "40%",
					align : 'center'
				},{
					field : 'button',
					title : '操作',
					width : "10%",
					align : 'center',
					formatter: function(value,row,index){
								return "<input type='button' class='button1 btncheck' value='填报' onclick='btncheck("+row.EQUIPMENT_ID+","+row.RES_TYPE_ID+")'/>";
					}
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}
	function btncheck(equipemtId,resTypeId){
		 $("#win_role").window({
            title: "【问题填报】",
            href : webPath + "inspect/oneEquTarget.do?equipmentId="+equipemtId+"&resTypeId="+resTypeId,
			width : 660,
			height : 400,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: true,
            modal: true
        });
	}
	</script>
</body>
</html>