<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
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
									<option value="">
										请选择
									</option>
									<option value="0">
										缆线计划
									</option>
									<option value="1">
										区域计划
									</option>
									<option value="3">
										点计划
									</option>
							</select>
						</td>
					</tr>
						
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addPlan()">新增缆线计划</div>
			<div class="btn-operation" onClick="addPointPlan()">新增点计划</div>
			<div class="btn-operation" onClick="editStaff()">编辑</div>
			<c:if test="${isAreaAdmin or isAdmin}">
				<div class="btn-operation" onClick="delPlan()">删除</div>
			</c:if>
			<c:if test="${isSonAreaAdmin or isAreaAdmin or isAdmin}">
			<div class="btn-operation" onClick="editInspector()">修改巡检人</div>
			</c:if>
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
			var obj = {
				plan_no : plan_no,
				plan_name : plan_name,
				plan_start_time : plan_start_time,
				plan_end_time : plan_end_time,
				son_area_id:son_area_id,
				plan_type:plan_type
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "CablePlan/plan_query.do",
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
					field : 'LINE_TYPE',
					title : '计划类型',
					width : "8%",
					align : 'center'
				}, {
					field : 'PLAN_TYPE',
					title : '周期类型',
					width : "5%",
					align : 'center'
				}, {
					field : 'PLAN_START_TIME',
					title : '开始时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'PLAN_END_TIME',
					title : '结束时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'PLAN_CIRCLE',
					title : '周期',
					width : "4%",
					align : 'center'
				},
				{
					field : 'CUSTOM_TIME',
					title : '检查时间',
					width : "8%",
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
					width : "4%",
					align : 'center'
				} , {
					field : 'AREA_NAME',
					title : '地市',
					width : "5%",
					align : 'center'
				}, {
					field : 'SON_AREA_NAME',
					title : '区县',
					width : "7%",
					align : 'center'
				}, {
					field : 'DEPT_NAME',
					title : '计划所属网格',
					width : "8%",
					align : 'center'
				}, {
					field : 'CREATE_TIME',
					title : '创建时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
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
					align : 'center',
					formatter:function(value,rowData,rowIndex){
	                	if(rowData.FLAG==1){
	                		return value+'<br><span style="color:red">[非集约化帐号]</span>';
	                	}else{
	                		
	                		return value;
	                	}
	                } 
				},{
					field : 'TYPE',
					title : '计划类型',
					width : "6%",
					hidden : true
				}, {
					field : 'PLANID',
					title : '操作',
					width : "6%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
	                	return "<a href=\"javascript:onClickRow(" + value + ");\">地图</a>";
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
			//location.href = "show.do?planId="+row.PLAN_ID;
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
		//addTab("新增任务",webPath+"CablePlan/plan_add.do");
			location.href = "plan_add.do";
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
				var plan_id = selected[0].PLAN_ID;
				//addTab("编辑",webPath + "CablePlan/plan_edit.do?plan_id=" + plan_id);
				//location.href = webPath + "CablePlan/plan_edit.do?plan_id=" + plan_id;
				var type = selected[0].TYPE;
				//addTab("编辑任务",webPath + "CablePlan/plan_edit.do?plan_id=" + plan_id);
				if(3==type){
					//location.href = webPath + "CablePlan/editPointPlan.do?plan_id=" + plan_id;
					addTab("编辑",webPath + "CablePlan/editPointPlan.do?plan_id=" + plan_id);
				}else{
					//location.href = webPath + "CablePlan/plan_edit.do?plan_id=" + plan_id;
					addTab("编辑",webPath + "CablePlan/plan_edit.do?plan_id=" + plan_id);
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
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].PLAN_ID;
					arr[arr.length] = value;
				}
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
			}
		}
		
		function editInspector(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取需要修改的计划!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].PLAN_ID;
					arr[arr.length] = value;
					if(selected[i].ISDISTRIBUTED=='未分配'){
						$.messager.show({
							title : '提  示',
							msg : '请选取已分配的计划!',
							showType : 'show'
						});
						return;
					}
					/*if(selected[i].SEND_TYPE='1'){
						if(selected[i].INSPECTOR_TYPE=='1'){
							$.messager.show({
								title : '提  示',
								msg : '请选取已分配和个人的计划!',
								showType : 'show'
							});
							return;
						}
					} */
				}
				$("input[name='selected']").val(arr);
				$('#win_staff').window({
					title : "【选择任务执行人】",
					href : webPath + "CablePlan/task_edit.do",
					width : 500,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
		
		function addPointPlan(){
			location.href = "addPointPlan.do";
			/* $('#win_Plan').window({
					title : "新增关键点计划",
					href : webPath + "jsp/cableinspection/point/pointPlan/add.jsp",
					width : 1000,
					height : 480,
					zIndex : 2,
					top : "20px",
					collapsible : false,
					cache : false,
					modal : true
				}); */
		}
		
	</script>
</body>
</html>