<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>我的任务</title>
</head>
<body style="padding:3px;border:0px">
<input type="hidden" name="plan_id" value=""/>
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">计划名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="plan_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">巡检人员：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="inspector" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">任务开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=start_time id="start_time" 
								onClick="WdatePicker();" />
							</div>
						</td>
						<td width="12%">任务结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=complete_time id="complete_time"
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="8%">区域：</td>
						<td width="18%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<c:if test="${GLY == 'true' || LXXJ_ADMIN =='true' || LXXJ_ADMIN_AREA=='true'}">
			<div class="btn-operation" onClick="deleteTask()">删除</div>
			</c:if>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【我的任务】" style="width:100%;height:480px">
	</table>
	<div id="win_staff"></div>

	<script type="text/javascript">
		$(document).ready(function() {
			getSonAreaList();
			$("input[name='plan_name']").autocomplete(
					webPath + "Care/task_queryPlans.do",
					{
						delay : 300,
						minChars : 0,
						matchSubset : 1,
			            matchContains : true,
						cacheLength : 10,
						formatItem : function(row, i, num) {
							return row[0];
						},
						onItemSelect : function(li) {
							if(!! li && !! li.extra){
								$("input[name='plan_id']").val(li.extra[0]);
							}
						}
					});
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
			var plan_name = $("input[name='plan_name']").val();
			var inspector = $("input[name='inspector']").val();
			var start_time = $("input[name='start_time']").val();
			var complete_time = $("input[name='complete_time']").val();
			var plan_id = $("input[name='plan_id']").val();
			var son_area_id = $("select[name='son_area']").val();
			var obj = {
				plan_name : plan_name,
				inspector : inspector,
				start_time : start_time,
				complete_time : complete_time,
				plan_id : plan_id,
				son_area_id:son_area_id
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Care/task_query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
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
					field : 'STAFF_NAME',
					title : '巡检人员',
					width : "10%",
					align : 'center'
				}, {
					field : 'STATUS_NAME',
					title : '状态',
					width : "10%",
					align : 'center'
				} , {
					field : 'ACTUAL_COMPLETE_TIME',
					title : '实际结束时间',
					width : "15%",
					align : 'center'
				}, {
					field : 'CREATE_TIME',
					title : '创建时间',
					width : "15%",
					align : 'center'
				}, {
					field : 'PLANID',
					title : '地图',
					width : "8%",
					align : 'center',
					formatter : function(value, rowData, rowIndex){
		            	return "<a href=\"javascript:showPlan(" + rowData.PLAN_ID + ");\">地图</a>";
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
				}
			});
		}
		
		function showPlan(plan_id) {
			addTab("查看外力点任务", webPath+"Care/show.do?plan_id="+plan_id);
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
			$("input[name='plan_name']").val("");
			$("input[name='inspector']").val("");
			$("input[name='start_time']").val("");
			$("input[name='complete_time']").val("");
			$("input[name='plan_id']").val("");
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
							url : webPath + "Care/task_delete.do",
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