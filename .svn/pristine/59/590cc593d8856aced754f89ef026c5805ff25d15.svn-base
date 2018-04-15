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
		<form id="form" action="" method="post">
			<div class="btn-operation-container">
					<div style="float:right;" class="btn-operation" onClick="back()">返回</div>
					<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
					<div style="float:right;" class="btn-operation" onClick="distributeTask()">派发任务</div>
					<input id="selectedPlanId" type="hidden" value="${PLAN_ID}"/>
			</div>
		</form>
	</div>
		<table id="dg" title="【计划管理】" style="width:98%;height:480px">
		</table>
	<div id="win_staff"></div>
	<div id="win_Plan"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
	
		function searchData() {
			var plan_id = $("#selectedPlanId").val();
			var obj = {
				plan_id : plan_id
			};
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "patrolPlan/queryNewTask.do",
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
					field : 'MONTH',
					title : '月份',
					width : "10%",
					align : 'center'
				}, {
					field : 'PLAN_NAME',
					title : '计划类型',
					width : "15%",
					align : 'center'
				},
				{
					field : 'AREA_NAME',
					title : '地区',
					width : "8%",
					align : 'center'
				} , {
					field : 'SON_AREA_NAME',
					title : '区县',
					width : "8%",
					align : 'center'
				}, {
					field : 'GRID_NAME',
					title : '网格',
					width : "18%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '检查人',
					width : "8%",
					align : 'center'
				},{
					field : 'EPNUM',
					title : '设备数量',
					width : "6%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
	                	return "<a href=\"javascript:showEqForUpdate("+rowData.PLAN_ID+","+rowData.CHECK_STAFF+");\">"+value+"</a>";
	                } 
				},{
					field : 'PLAN_START_TIME',
					title : '计划开始时间',
					width : "15%",
					align : 'center'
				},{
					field : 'PLAN_END_TIME',
					title : '计划结束时间',
					width : "15%",
					align : 'center'
				},{
					field : 'MONTH',
					title : '要求完成时间',
					width : "10%",
					align : 'center'
				},{
					field : 'CHECK_STAFF',
					title : '检查人Id',	
					width : "15%",
					hidden : true
				},{
					field : 'XQ_END_TIME',
					title : '需求完成时间',
					width : "12%",
					align : 'center'
				},{
					field : 'STATE',
					title : '派发状态',
					width : "8%",
					formatter:function(value,row,index){
				  		if(value == 0){
					  		return "未派发";
				  		}else if(value == 1){
				  			return "已派发";
					  	}
				  	}
				}
				] ],
				frozenColumns:[[  
				 	 {field:'PLAN_ID',checkbox:true}  
			    ]],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					 if (data.rows.length > 0) {
			             for (var i = 0; i < data.rows.length; i++) {
			                 if (data.rows[i].STATE == 1) {
			                      $("input[type='checkbox']")[i + 1].disabled = true;
			                  }
			                }
			            }
				
					$("body").resize();
				},
				 onClickRow: function(rowIndex, rowData){
					 var flag = true;
					 $("input[type='checkbox']").each(function(index, el){
						 if (el.disabled == true) { 
		                	 $('#dg').datagrid('unselectRow', index - 1); 
		                     if ((rowIndex+1) == index) { 
		                     	flag = false;
		                     } 
		                 } 
			         })
		            var msg = "此任务已派发！";
		             if(!flag){
		            	 $.messager.show({ 
		                     title:"提示", 
		                     msg:msg 
		                  }); 
		             }
		         }
			});
		}
		/**点击checkbox触发**/
		function onCheck(index, row) {
			alert("onCheck");
		}

		function onSelect(index, row) {
			alert("onSelect");
		}
		
		function back() {
			location.href = 'index.do';
		}
		
		//派发任务
		function distributeTask(){
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
				$.messager.confirm('系统提示', '您确定要派发任务吗?', function(r) {
					if(r){
						var plan_id = selected[0].PLAN_ID;
						var check_staff;
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].CHECK_STAFF;
							if(i==0){
								check_staff = value;
							}else{
								check_staff += ","+value;
							}
						}
						$.messager.progress();
						$.ajax({
							type : 'POST',
							url : webPath + "patrolPlan/distributeTask.do",
							data : {
								plan_id : plan_id,
								check_staff : check_staff
							},
							dataType : 'json',
							success : function(json) 
							{
								$.messager.progress('close');
								var result = json.result;
								if('success' == result){
									$.messager.show({
										title : '提  示',
										msg : '派发任务成功!',
										showType : 'show'
									});
									searchData();
								}else if("fail" == result){
									$.messager.show({
										title : '提  示',
										msg : '派发任务失败!',
										showType : 'show'
									});
								}
							}
						});
					}
					
				})
			}
		}
		
		function showEqForUpdate(plan_id,check_staff){
			$('#win_staff').window({
				title : "【选择设备】",
				href : webPath + "patrolPlan/showEqForUpdate.do?plan_id="+plan_id+"&check_staff="+check_staff,
				width : 600,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
	</script>
</body>
</html>