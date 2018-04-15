<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>外力点看护</title>
</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">计划编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="plan_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">计划名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="plan_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=plan_start_time id="plan_start_time" 
								onClick="WdatePicker();" />
							</div>
						</td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=plan_end_time id="plan_end_time"
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'plan_start_time\')}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">区域：</td>
						<td width="20%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">点类型：</td>
						<td width="20%">
							<select name="point_type" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="2">
										隐患点
									</option>
									<option value="3">
										看护点
									</option>
							</select>
						</td>
						<td width="10%">隐患类型：</td>
						<td width="20%">
							<select name="trouble" class="condition-select">
									<option value="">
										请选择
									</option>
									<c:forEach items="${trouble}" var="t">
										<option value="${t.TYPE_ID }">${t.TYPE_NAME }</option>
									</c:forEach>
							</select>
						</td>
						<td width="10%">是否完成整治：</td>
						<td width="20%">
							<select name="if_complete" class="condition-select">
									<option value="2">
										请选择
									</option>
									<option value="1">
										是
									</option>
									<option value="0">
										否
									</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addPlan(2)">新增检查</div>
			<div class="btn-operation" onClick="addPlan(3)">新增看护</div>
			<div class="btn-operation" onClick="editStaff()">编辑</div>
			<c:if test="${GLY == 'true' || LXXJ_ADMIN =='true' || LXXJ_ADMIN_AREA=='true'}">
				<div class="btn-operation" onClick="delPlan()">删除</div>
			</c:if>
			<div class="btn-operation" onClick="createTask()">生成任务</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【外力点看护】" style="width:100%;height:480px">
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
			var plan_no = $("input[name='plan_no']").val();
			var plan_name = $("input[name='plan_name']").val();
			var plan_start_time = $("input[name='plan_start_time']").val();
			var plan_end_time = $("input[name='plan_end_time']").val();
			var son_area_id = $("select[name='son_area']").val();
			var point_type = $("select[name='point_type']").val();
			var trouble = $("select[name='trouble']").val();
			var if_complete = $("select[name='if_complete']").val();
			var obj = {
				plan_no : plan_no,
				plan_name : plan_name,
				plan_start_time : plan_start_time,
				plan_end_time : plan_end_time,
				son_area_id:son_area_id,
				point_type:point_type,
				trouble:trouble,
				if_complete:if_complete
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Care/plan_query.do",
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
					field : 'PLAN_ID',
					title : '计划ID',
					checkbox : true
				}, {
					field : 'PLAN_NAME',
					title : '计划名称',
					width : "15%",
					align : 'center'
				}, {
					field : 'PLAN_TYPE',
					title : '计划类型',
					width : "8%",
					align : 'center'
				}, {
					field : 'PLAN_START_TIME',
					title : '开始时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'PLAN_END_TIME',
					title : '结束时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'PLAN_CIRCLE',
					title : '周期',
					width : "5%",
					align : 'center'
				},{
					field : 'CUSTOM_TIME',
					title : '检查时间',
					width : "10%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
						var newStr="";
						if(value){
							var plan_circle=rowData.PLAN_CIRCLE;
							switch(plan_circle)
							{
								case '周':
									var dateArr = value.split(",");
									for(var i=0;i<dateArr.length-1;i++){
										var date=dateArr[i];
										switch(date)
										{
											case '7':
												newStr += "周日,";
												break;
											case '1':
												newStr += "周一,";
												break;
											case '2':
												newStr += "周二,";
												break;
											case '3':
												newStr += "周三,";
												break;
											case '4':
												newStr += "周四,";
												break;
											case '5':
												newStr += "周五,";
												break;
											case '6':
												newStr += "周六,";
												break;
										}
									}
									break;
								case '月':
									var dateArr = value.split(",");
									for(var i=0;i<dateArr.length-1;i++){
										newStr += dateArr[i]+"日,";
									}
									break;
							}
						}
	                	return newStr;
	                }
				}, {
					field : 'PLAN_FREQUENCY',
					title : '次数',
					width : "5%",
					align : 'center'
				}, {
					field : 'PLAN_KIND',
					title : '计划类别',
					width : "8%",
					align : 'center'
				}, {
					field : 'AREA',
					title : '地市',
					width : "6%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区县',
					width : "6%",
					align : 'center'
				}, {
					field : 'ISDISTRIBUTED',
					title : '任务分配',
					width : "8%",
					align : 'center'
				}, {
					field : 'INSPECTOR',
					title : '看护人',
					width : "8%",
					align : 'center'
				}, {
					field : 'PLANID',
					title : '地图',
					width : "8%",
					align : 'center',
					formatter : function(value, rowData, rowIndex){
		            	return "<a href=\"javascript:showPlan(" + value + ");\">地图</a>";
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
		
		function showPlan(plan_id) {
			addTab("查看外力点计划",webPath+"Care/show.do?plan_id="+plan_id);
		}

		function clearCondition(form_id) {
			$("input[name='plan_no']").val("");
			$("input[name='plan_name']").val("");
			$("input[name='plan_start_time']").val("");
			$("input[name='plan_end_time']").val("");
		}

		function addPlan(plan_kind) {
			location.href = "plan_add.do?plan_kind=" + plan_kind;
		}
		function editStaff() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show'
				});
				return;
			} else {
			
				var type =selected[0].PLAN_KIND;
				var plan_id = selected[0].PLAN_ID;
				if('看护'==type)
				{
					//location.href = webPath + "Care/planEditTakeCare.do?plan_id=" + plan_id;
					addTab("编辑",webPath + "Care/planEditTakeCare.do?plan_id=" + plan_id);
				}
				else
				{
					
					//location.href = webPath + "Care/plan_edit.do?plan_id=" + plan_id;
					addTab("编辑",webPath + "Care/plan_edit.do?plan_id=" + plan_id);
				}
			}

		}

		function delPlan() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要删除的数据!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要删除计划吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].PLAN_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "Care/plan_delete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除计划!',
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}
		function createTask(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取用于生成任务的计划!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].PLAN_ID;
					arr[arr.length] = value;
				}
				//检查所选计划中是否有已分配的计划
				$.ajax({
						type : 'GET',
						url : webPath + "Care/plan_check.do?ids=" + arr,
						dataType : 'json',
						success : function(json) {
							if(json.status){
								$('#win_staff').window({
									title : "【选择任务执行人】",
									href : webPath + "Care/task_add.do?planSelected=" + arr,
									width : 460,
									height : 470,
									zIndex : 2,
									region : "center",
									collapsible : false,
									cache : false,
									modal : true
								});
							} else {
								$.messager.show({
									title : '提  示',
									msg : '所选计划中存在已分配的计划!',
									showType : 'show'
								});
							}
						}
				});
			}
		}
	</script>
</body>
</html>