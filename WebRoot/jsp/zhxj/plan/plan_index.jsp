<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>计划管理</title>

</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">计划编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="plan_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">计划名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="plan_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=plan_start_time id="plan_start_time" 
								onClick="WdatePicker();" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">结束时间：</td>
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
						<td width="10%">计划类型：</td>
						<td width="20%">
							<select name="plan_type" class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${types}" var="types">
									<option value="${types.TYPE_ID}">${types.TYPE_NAME}</option>
								</c:forEach>
							</select>
						</td>
						<td width="10%">计划来源：</td>
						<td width="20%">
							<select name="plan_orgin" class="condition-select">
							<option value="">请选择</option>
							<option value="1">干线巡检</option>
							<option value="2">缆线巡检</option>
							<option value="3">光网助手</option>
							</select>
						</td>
					</tr>
						
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addPlan()">新增计划</div>
			<div class="btn-operation" onClick="editPlan()">编辑</div>
			<div class="btn-operation" onClick="delPlan()">删除</div>
			<div class="btn-operation" onClick="createTask()">生成任务</div>
			<div style="float:right;"class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
				<input name="area_id" type="hidden" value=""/>
				<input name="son_area_id" type="hidden" value=""/>
		</div>
	</div>
	<table id="dg" title="【计划管理】" style="width:98%;height:480px">
	</table>
	<div id="win_staff"></div>
	<div id="win_Plan"></div>
	<div id="win_selected"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			//searchData();
			 $("select[name='son_area']").change(function() {
				//根据area_id，获取区县
				$("input[name='son_area_id']").val($("select[name='son_area']").val());
			}); 
			selectSelected();
			getSonAreaList();
		});
		function selectSelected(){
		$.ajax({
				type : 'POST',
				url : webPath + "Staff/selectSelected.do",
				dataType : 'json',
				async:false,
				success : function(json) {
				$("input[name='area_id']").val("${areaId}");
				$("input[name='son_area_id']").val("");
				if(json[0].ifGly!=1){
				//$("input[name='son_area_id']").val(${sonAreaId});
				}
			}
			});
		}
		function getSonAreaList() {
		var areaId=$("input[name='area_id']").val();
		if(null==areaId||""==areaId)
		{
			$("select[name='son_area'] option").remove();
			return;
		}
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
				var isSonAreaAdmin ='${LXXJ_ADMIN_SON_AREA}';
				//alert(${isSonAreaAdmin});
				if (undefined !=isSonAreaAdmin && isSonAreaAdmin!='true'){
				$("select[name='son_area']").append(
							"<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID=="${sonAreaId}"){
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
			var son_area_id=$("select[name='son_area']").val().trim();
			var plan_type=$("select[name='plan_type']").val().trim();
			var plan_orgin=$("select[name='plan_orgin']").val().trim();
			var obj = {
				plan_no : plan_no,
				plan_name : plan_name,
				plan_start_time : plan_start_time,
				plan_end_time : plan_end_time,
				son_area_id:son_area_id,
				plan_type:plan_type,
				plan_orgin:plan_orgin
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "ZhxjPlan/plan_query.do",
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
					field : 'PLAN_ID',
					title : '计划ID',
					checkbox : true
				}, {
					field : 'PLAN_NAME',
					title : '计划名称',
					width : "12%",
					align : 'center'
				}, {
					field : 'TYPE_NAME',
					title : '计划类型',
					width : "8%",
					align : 'center'
				}, {
					field : 'PLAN_ORGIN_NAME',
					title : '来源',
					width : "5%",
					align : 'center'
				}, {
					field : 'PLAN_START_DATE',
					title : '开始时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'PLAN_END_DATE',
					title : '结束时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'AREA',
					title : '地市',
					width : "5%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区县',
					width : "7%",
					align : 'center'
				}, {
					field : 'GRID_NAME',
					title : '计划所属网格',
					width : "8%",
					align : 'center'
				}, {
					field : 'CREATE_TIME',
					title : '创建时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'CREATE_STAFF',
					title : '创建人',
					width : "7%",
					align : 'center'
				},{
					field : 'ISDISTRIBUTED',
					title : '分配状态',
					width : "6%",
					align : 'center'
				},{
					field : 'INSPECTOR',
					title : '巡检人',
					width : "6%",
					align : 'center'
				}, {
					field : 'PLANID',
					title : '操作',
					width : "6%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
						if(rowData.PLAN_TYPE==201||rowData.PLAN_TYPE==202||rowData.PLAN_TYPE==203){
							return "<a href=\"javascript:onClickRow(" + value + ");\">地图</a>";
						}else if(rowData.PLAN_TYPE==204){
							return "<a href=\"javascript:showPlan(" + value + ");\">地图</a>";
						}
	                	
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
		/**选择行触发**/
		function onClickRow(plan_id) {
		addTab("查看缆线计划",webPath+"CablePlan/show.do?planId="+plan_id);
		}
		
		function showPlan(plan_id) {
			addTab("查看外力点计划",webPath+"Care/show.do?plan_id="+plan_id);
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
			$("input[name='plan_no']").val("");
			$("input[name='plan_name']").val("");
			$("input[name='plan_start_time']").val("");
			$("input[name='plan_end_time']").val("");
		}
		
		
		function addPlan() {
			$('#win_selected').window({
						title : "【选择计划】",
						href : webPath + "ZhxjPlan/planAddSelected.do",
						width : 400,
						height : 200,
						zIndex : 2,
						region : "center",
						collapsible : false,
						cache : false,
						modal : true
			});
		}
		
		function jumpPlanAdd(){
			var type = $("#plan_type_selection").val();
			if(type==201||type==202){
				//location.href = webPath+"CablePlan/plan_add.do";
				addTab("缆线计划新增",webPath+"CablePlan/plan_add.do");
			}else if(type==203){
				//location.href =  webPath+"CablePlan/addPointPlan.do";
				addTab("缆线点计划新增",webPath+"CablePlan/addPointPlan.do");
			}else if(type==204){
				//location.href = webPath + "Care/plan_add.do?plan_kind=" + 3;
				addTab("缆线看护新增",webPath+"Care/plan_add.do?plan_kind=" + 3);
			//其他巡检
			}else{
			
			}
			cancelPlanAdd();
		}
		
		function cancelPlanAdd(){
			$('#win_selected').window('close');
		}
		
		function editPlan() {
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
				var plan_id = selected[0].PLAN_ID;
				var type = selected[0].PLAN_TYPE;
				if(type==203){
					addTab("编辑",webPath + "CablePlan/editPointPlan.do?plan_id=" + plan_id);
				}else if(type==201||type==202){
					addTab("编辑",webPath + "CablePlan/plan_edit.do?plan_id=" + plan_id);
				}else if(type==204){
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
			}else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要删除计划吗?', function(r) {
					if (r) {
						var planId;
						var planType;
						var zhxjId;
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							planType = selected[i].PLAN_TYPE;
							zhxjId = selected[i].ZHXJ_PLAN_ID;
							var value = selected[i].PLAN_ID;
							arr[arr.length] = value;
						}
						if(planType==201||planType==202||planType==203){
							$("input[name='selected']").val(arr);
							$('#form').form('submit', {
								url : webPath + "CablePlan/plan_delete.do",
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
						}else if(planType==204){
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
						//其他巡检
						}else{
						
						}
						$('#form').form('submit', {
							url : webPath + "ZhxjPlan/planDelete.do?zhxj_id="+zhxj_id,
							onSubmit : function() {
								$.messager.progress();
							},
							success : function(json) {
								var rs=eval('('+json+')');
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : rs.msg,
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
					msg : '请选取需要生成任务的计划!',
					showType : 'show'
				});
				return;
			}else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show'
				});
				return;
			}  else {
				var arr = new Array();
				var planType;
				for ( var i = 0; i < selected.length; i++) {
					planType = selected[i].PLAN_TYPE;
					var value = selected[i].PLAN_ID;
					arr[arr.length] = value;
				}
				if(planType==201||planType==202||planType==203){
					$("input[name='selected']").val(arr);
					$('#win_staff').window({
						title : "【选择任务执行人】",
						href : webPath + "CablePlan/task_add.do",
						width : 500,
						height : 500,
						zIndex : 2,
						region : "center",
						collapsible : false,
						cache : false,
						modal : true
					});
				}else if(planType==204){
				//缆线看护
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
				//其他巡检
				}else{

				}
			}
		}
		
	</script>
</body>
</html>