<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>我的任务</title>

</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">任务编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="task_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">任务名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="task_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">任务开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=start_time id="start_time" 
								onClick="WdatePicker();" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">任务结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=complete_time id="complete_time"
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">区域：</td>
						<td width="2s0%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">任务类型：</td>
						<td width="20%">
							<select name="plan_type" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="0">
										缆线任务
									</option>
									<option value="1">
										区域任务
									</option>
									<option value="3">
										点任务
									</option>
							</select>
						</td>
					</tr>
						
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<c:if test="${isAreaAdmin||admin}">
			<div class="btn-operation" onClick="deleteTask()">删除</div>
			</c:if>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【我的任务】" style="width:98%;height:480px">
	</table>
	<div id="win_staff"></div>

	<script type="text/javascript">
		$(document).ready(function() {
			//searchData();
			getSonAreaList();
		});
		function getSonAreaList() {
		var areaId='${areaId}';
		$.ajax({
			type : 'POST',
			url : webPath + "General/getSonAreaList.do",
			data : {
				areaId : areaId
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json.sonAreaList;
				$("select[name='son_area'] option").remove();
				var GLY ='${GLY}';
				var LXXJ_ADMIN ='${LXXJ_ADMIN}';
				var LXXJ_ADMIN_AREA ='${LXXJ_ADMIN_AREA}';
				if (GLY == 'true' || LXXJ_ADMIN =='true' || LXXJ_ADMIN_AREA=='true')
				{
					$("select[name='son_area']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
			}
		});
	}
		function searchData() {
			var task_no = $("input[name='task_no']").val();
			var task_name = $("input[name='task_name']").val();
			var start_time = $("input[name='start_time']").val();
			var complete_time = $("input[name='complete_time']").val();
			var son_area_id = $("select[name='son_area']").val();
			var plan_type = $("select[name='plan_type']").val();
			var obj = {
				task_no : task_no,
				task_name : task_name,
				start_time : start_time,
				complete_time : complete_time,
				son_area_id:son_area_id,
				plan_type:plan_type
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "CableTask/task_query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit:true,
				singleSelect : false,
				columns : [ [ {
					field : 'TASK_ID',
					title : '任务ID',
					checkbox : true
				}, {
					field : 'TASK_NO',
					title : '任务编码',
					width : "15%",
					align : 'center'
				}, {
					field : 'TASK_NAME',
					title : '任务名称',
					width : "15%",
					align : 'center'
				}, {
					field : 'START_TIME',
					title : '任务开始时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'COMPLETE_TIME',
					title : '任务结束时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'INSPECTOR',
					title : '巡检人',
					width : "10%",
					align : 'center'
				}, {
					field : 'STATUS_NAME',
					title : '状态',
					width : "6%",
					align : 'center'
				}, {
					field : 'LINE_TYPE',
					title : '任务类型',
					width : "7%",
					align : 'center'
				} , {
					field : 'ACTUAL_COMPLETE_TIME',
					title : '实际结束时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'CREATE_TIME',
					title : '创建时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '创建人',
					width : "6%",
					align : 'center'
				}, {
					field : 'IF_POST',
					title : '是否推送至集约化',
					width : "10%",
					align : 'center'
				}, {
					field : 'PLANID',
					title : '操作',
					width : "6%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
	                	return "<a href=\"javascript:openMap(" + rowData.PLAN_ID + ");\">地图</a>";
	                } 
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
					$("body").resize();
				}
			});
		}
		
		function openMap(planId) {
			addTab("查看缆线任务", webPath+"CablePlan/show.do?planId="+planId);
		}
		
		/**选择行触发**/
		function onClickRow(index, row) {
			alert("onClickRow");
		}
		/**点击checkbox触发**/
		function onCheck(index, row) {
			alert("onCheck");
		}

		function onSelect(index, row) {
			alert("onSelect");
		}

		function onSelectAll(rows) {
			alert(rows);
			alert("onSelectAll");
		}

		function clearCondition(form_id) {
			$("input[name='task_no']").val("");
			$("input[name='task_name']").val("");
			$("input[name='start_time']").val("");
			$("input[name='complete_time']").val("");
		}
		
		function deleteTask() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要删除任务吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].TASK_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "CableTask/task_delete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除任务!',
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}
	</script>
</body>
</html>