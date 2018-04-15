<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>设备</title>
</head>
<body style="padding:3px;border:0px" >
	<div id="tb_staff" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" id="area_id" value="${AREA_ID}">
				<input type="hidden" id="son_area_id" value="${SON_AREA_ID}">
				<input type="hidden" id="whwg" value="${WHWG}">
				<input type="hidden" id="plan_type" value="${PLAN_TYPE}">
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">设备类型：</td>
						<td width="20%">
								<select id="select_res_type">
									<option value="">--请选择--</option>
									<option value="411">ODF</option>
									<option value="703">光交接箱</option>
									<option value="704">光分纤箱</option>
									<option value="414">综合配线箱</option>
								</select>
						</td>
						<td width="10%" nowrap="nowrap">设备名称：</td>
						<td width="18%">
							<div class="condition-text-container">
								<input id="eq_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">设备编码：</td>
						<td width="18%">
							<div class="condition-text-container">
								<input id="eq_code" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" >
			<div class="btn-operation" onClick="doClose()" style="float: right;">返回</div>
			<div class="btn-operation" onClick="doSelectForPreview()" style="float: right;">确定</div>
			<div class="btn-operation" onClick="searchEqData()" style="float: right;">查询</div>
		</div>
	</div>
	<table id="dg_plan" title="设备" style="width:100%;height:480px">
	</table>
	<script type="text/javascript">
		$(document).ready(function() {
			searchEqData();
		});
		function searchEqData() {
			var area_id = $("#area_id").val();
			var son_area_id = $("#son_area_id").val();
			var whwg = $("#whwg").val();
			var res_type_id = $("#select_res_type").val();
			var eq_name = $("#eq_name").val();
			var eq_code = $("#eq_code").val(); 
			
			var plan_type = $("#plan_type").val();
			
			var obj = {
					area_id : area_id,
					son_area_id : son_area_id,
					whwg : whwg,
					res_type_id : res_type_id,
					eq_name : eq_name,
					eq_code : eq_code,
					plan_type : plan_type
			};
			$('#dg_plan').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 600,
				height : 450,
				toolbar : '#tb_staff',
				url : webPath + "patrolPlan/queryEqForPreview.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 20,
				pageList : [ 20, 50, 100, 200 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [{
					field : 'EQUIPMENT_ID',
					title : '设备ID',
					checkbox : true
				} ,{
					field : 'EQUIPMENT_CODE',
					title : '设备编码',
					width : "18%",
					align : 'center'
				}, {
					field : 'EQUIPMENT_NAME',
					title : '设备名称',
					width : "20%",
					align : 'center'
				}, {
					field : 'RES_TYPE',
					title : '设备类型',
					width : "10%",
					align : 'center'
				}
				] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
			
		}
		
		function doClose(){
			$('#win_eq').window("close");
			searchData();
		}
		
		function doSelectForPreview(){
			var plan_type = $("#plan_type").val();
			
			var selected = $('#dg_plan').datagrid('getChecked');
		    var count = selected.length;
		    if (count == 0) {
		        $.messager.show({
		            title: '提  示',
		            msg: '请选择至少一条记录!',
		            showType: 'show'
		        });
		        return;
		   
			} else {
				var ids;
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].EQUIPMENT_ID;
					if (i == 0) {
						ids = value;
					} else {
						ids += "," + value;
					}
				}
				$.messager.progress();
				$.ajax({
					type : 'POST',
					url : webPath + "patrolPlan/addEqForPreview.do",
					data : {
						ids : ids,
						plan_type : plan_type
					},
					dataType : 'json',
					success : function(json) {
						$.messager.progress('close');
						var result = json.result;
						if ('success' == result) {
							$.messager.show({
								title : '提  示',
								msg : '新增设备成功!',
								showType : 'show'
							});
							$('#win_eq').window("close");
							searchData();
						} else if ("fail" == result) {
							$.messager.show({
								title : '提  示',
								msg : '新增设备失败!',
								showType : 'show'
							});
						}
					}

				})

			}
		}
	</script>
</body>
</html>