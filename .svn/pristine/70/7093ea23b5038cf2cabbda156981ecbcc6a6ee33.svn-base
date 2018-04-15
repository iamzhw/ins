<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>巡检计划</title>

</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<input type="hidden" name="selected_planId" value="" />
				
				<table class="condition">
					<tr>
						<!-- <td width="10%" nowrap="nowrap">计划编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="plan_no" type="text" class="condition-text" />
							</div>
						</td> -->
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
							<input type="hidden" name="plan_type" value=""/>
						</td>
					</tr>
					
					<!-- <tr>
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
					</tr> -->
						
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			
			<c:if test="${!isSonAreaAdmin}">
				<div class="btn-operation" onClick="viewOrder()">网格归属工单</div>
				<div class="btn-operation" onClick="addPatrolPlan()">新增任务计划</div>
			    <div class="btn-operation" onClick="patrolPlanEdit()">编辑</div>
				<div class="btn-operation" onClick="delPlan()">删除</div>
				<div class="btn-operation" onClick="createNewTask()">生成任务</div>
			</c:if>
            
			<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
				<input name="area_id" type="hidden" value=""/>
<!-- 				<input name="son_area_id" type="hidden" value=""/> -->
		</div>
	</div>
	<table id="dg" title="【计划管理】" style="width:98%;height:480px">
	</table>
	<div id="win_staff"></div>
	<div id="win_Plan"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			/* searchData(); */
			 /* $("select[name='son_area']").change(function() {
				//根据area_id，获取区县
				$("input[name='son_area_id']").val($("select[name='son_area']").val());
			});  */
			//selectSelected();
			//getSonAreaList();
		});
		/* function selectSelected(){
			$.ajax({
					type : 'POST',
					url : webPath + "Staff/selectSelected.do",
					dataType : 'json',
					async:false,
					success : function(json) {
					    var thisVal = '${areaId}';
						$("input[name='area_id']").val(thisVal);
						$("input[name='son_area_id']").val("");
						if(json[0].ifGly!=1){
						//$("input[name='son_area_id']").val(${sonAreaId});
						}
					}
			});
		} */
		
		/* function getSonAreaList() {
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
							if(result[i].AREA_ID=='${sonAreaId}'){
								$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
											+ result[i].NAME + "</option>");
							}
						}
					}
				}
			});
		} */
		
		function searchData() {
			var plan_no = $("input[name='plan_no']").val();
			var plan_name = $("input[name='plan_name']").val();
			var plan_start_time = $("input[name='plan_start_time']").val();
			var plan_end_time = $("input[name='plan_end_time']").val();
			//var son_area_id=$("input[name='son_area_id']").val().trim();
			/* var plan_type=$("select[name='plan_type']").val().trim(); */
			var plan_type = $("input[name='plan_type']").val().trim();
			var obj = {
				plan_no : plan_no,
				plan_name : plan_name,
				plan_start_time : plan_start_time,
				plan_end_time : plan_end_time,
				//son_area_id:son_area_id,
				plan_type:plan_type
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "patrolPlan/plan_query.do",
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
				
				//自定义行的颜色。当任务快要过期的时候，变成红色
				/* rowStyler: function(index,row){
			 		if (row.PLAN_TYPE == 1){
						return 'background-color:red;color:#fff;';
					}
				}, */
				
				columns : [ [ {
					field : 'PLAN_ID',
					title : '计划ID',
					checkbox : true
				},{
					field : 'PLAN_TYPE',
					title : '计划类型',
					width : "10%",
					align:'center',
					formatter:function(value,rowData,rowIndex){
						if(null != value && "" != value){
							if(value == '1'){
								return '承包人检查';
							}else if(value == '2'){
								return '网格质量检查员检查';
							}else if(value == '3'){
								return '市县公司专业中心';
							}else if(value == '4'){
								return '市公司资源中心';
							}
						}else{
							return '';
						}
						
					}
				}, {
					field : 'PLAN_NAME',
					title : '计划名称',
					width : "12%",
					align : 'center'
				}/* , {
					field : 'LINE_TYPE',
					title : '任务类型',
					width : "10%",
					align : 'center'
				} *//* , {
					field : 'PLAN_TYPE',
					title : '计划类型',
					width : "6%",
					align : 'center'
				} */, {
					field : 'PLAN_START_TIME',
					title : '计划开始时间',
					width : "8%",
					align : 'center'
				} , {
					field : 'PLAN_END_TIME',
					title : '计划结束时间',
					width : "8%",
					align : 'center'
				} , {
					field : 'TASK_START_TIME',
					title : '任务开始时间',
					width : "8%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
						var date_ = new Date();  
						var year = date_.getYear();  
						var month = date_.getMonth() + 1;
						var firstDate = (year+1900) + '-' + month + '-01';  
						return firstDate;
	                }
				} , {
					field : 'TASK_END_TIME',
					title : '任务结束时间',
					width : "8%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
						/* return getCurrentMonthLast().format("yyyy-MM-dd"); */
						/* return "2017-10-31"; */
						var date_ = new Date();  
						var year = date_.getYear();  
						var month = date_.getMonth() + 1;
						var day = new Date(year,month,0);      
						var lastDate = (year+1900) + '-' + month + '-' + day.getDate();  
						return lastDate;
	                }
				} ,  {
					field : 'PLAN_CIRCLE',
					title : '周期',
					width : "5%",
					align : 'center'
				},
// 				{
// 					field : 'CUSTOM_TIME',
// 					title : '检查时间',
// 					width : "10%",
// 					align : 'center',
// 					formatter:function(value,rowData,rowIndex){
// 						var newStr="";
// 						if(value){
// 							var plan_circle=rowData.PLAN_CIRCLE;
// 							switch(plan_circle)
// 							{
// 								case '周':
// 									var dateArr = value.split(",");
// 									for(var i=0;i<dateArr.length-1;i++){
// 										var date=dateArr[i];
// 										switch(date)
// 										{
// 											case '7':
// 												newStr += "周日,";
// 												break;
// 											case '1':
// 												newStr += "周一,";
// 												break;
// 											case '2':
// 												newStr += "周二,";
// 												break;
// 											case '3':
// 												newStr += "周三,";
// 												break;
// 											case '4':
// 												newStr += "周四,";
// 												break;
// 											case '5':
// 												newStr += "周五,";
// 												break;
// 											case '6':
// 												newStr += "周六,";
// 												break;
// 										}
// 									}
// 									break;
// 								case '月':
// 									var dateArr = value.split(",");
// 									for(var i=0;i<dateArr.length-1;i++){
// 										newStr += dateArr[i]+"日,";
// 									}
// 									break;
// 							}
// 						}
// 	                	return newStr;
// 	                } 
// 				}, 
				{
					field : 'PLAN_FREQUENCY',
					title : '次数',
					width : "5%",
					align : 'center'
				} , {
					field : 'AREA_NAME',
					title : '地市',
					width : "8%",
					align : 'center'
				}/* , {
					field : 'SON_AREA_NAME',
					title : '区县',
					width : "8%",
					align : 'center'
				} */, {
					field : 'CREATE_TIME',
					title : '创建时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '创建人',
					width : "8%",
					align : 'center'
				},{
					field : 'ISDISTRIBUTED',
					title : '分配状态',
					width : "6%",
					align : 'center'
				}/* ,{
					field : 'INSPECTOR',
					title : '巡检人',
					width : "6%",
					align : 'center'
				} */,{//0已完成，1未开始，,2进行中，4问题上报,带派单 ，5退单,待派单，6待回单，7已整改回单,待审核，8归档
					field : 'PLAN_TASK_COUNT',
					title : '作业总数',
					width : "6%",
					align : 'center'
				}/* ,{
					field : 'PLAN_TASK_COUNT0',
					title : '已完成',
					width : "6%",
					align : 'center'
				} */
				/* ,{
					field : 'PLAN_TASK_COUNT1',
					title : '未开始',
					width : "6%",
					align : 'center'
				},{
					field : 'PLAN_TASK_COUNT2',
					title : '进行中',
					width : "6%",
					align : 'center'
				},{
					field : 'PLAN_TASK_COUNT4',
					title : '问题上报',
					width : "6%",
					align : 'center'
				} */,{
					field : 'PLAN_TASK_COUNT5',
					title : '退单',
					width : "6%",
					align : 'center'
				},{
					field : 'PLAN_TASK_COUNT6',
					title : '待检查',
					width : "6%",
					align : 'center'
				},
// 				{
// 					field : 'PLAN_TASK_COUNT7',
// 					title : '已整改回单,待审核',
// 					width : "11%",
// 					align : 'center'
// 				},
				{
					field : 'PLAN_TASK_COUNT8',
					title : '已检查',
					width : "6%",
					align : 'center'
				},{
					field : 'PLAN_NOCHECK_COUNT',
					title : '预警数',
					width : "6%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
						var myDate = new Date();
						var day = myDate.getDate();
						if(31-day < 6 ){
							return value;
						}else{
							return 0;
						}
	                }
				}
				/* , {
					field : 'PLANID',
					title : '操作',
					width : "6%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
	                	return "<a href=\"javascript:onClickRow(" + value + ");\">地图</a>";
	                } 
				} */
				] ],
				//width : 'auto',
				nowrap : false,
				striped : true,  //隔行换色
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
					$("body").resize();
				}
			});
		}
		
		
		function getCurrentMonthLast(){
			 var date=new Date();
			 var currentMonth=date.getMonth();
			 var nextMonth=++currentMonth;
			 var nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
			 var oneDay=1000*60*60*24;
			 return new Date(nextMonthFirstDay-oneDay);
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

		//网格工单查询
		function viewOrder() {
			
			addTab("网格工单信息", "<%=path%>/cableCheckStatictis/teamOrderIndex.do");
			
			//location.href = webPath+"/cableCheckStatictis/teamOrderIndex.do";
		}
		
		function addPatrolPlan() {
		//addTab("新增任务",webPath+"CablePlan/plan_add.do");
			location.href = "addPatrolPlan.do";
		}
		function patrolPlanEdit() {
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
				$.ajax({
					type : 'POST',
					url : webPath + "/patrolPlan/queryIfHavaTask.do",
					data : {
						plan_id : plan_id
					},
					dataType : 'json',
					success : function(json) 
					{
						var result = json.result;
						if ('fail' == result) {
							location.href = "patrolPlanEdit.do?plan_id=" + plan_id;
						} else if ("success" == result) {
							$.messager.show({
								title : '提  示',
								msg : '此计划已生成任务，无法修改!',
								showType : 'show'
							});
						}
					}
				});
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
							url : webPath + "patrolPlan/plan_delete.do",
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
		function addPointPlan(){
			location.href = "addPointPlan.do";
		}
		
		function createNewTask(){
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
			//	$.messager.confirm('系统提示', '您确定要生成任务吗?', function(r) {
				//	if(r){
						var plan_id = selected[0].PLAN_ID;
						location.href = "createNewTask.do?plan_id=" + plan_id;
						$.messager.progress();
					//}
				//})
			}
				
		}
	</script>
</body>
</html>