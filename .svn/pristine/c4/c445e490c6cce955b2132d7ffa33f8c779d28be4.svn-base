
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>外力点计划管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【看护计划管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						
						<td >外力点名称：</td>
						<td >
							<div class="condition-text-container">
								<input name="SITE_NAME" id="SITE_NAME"  class="condition-text" />
							</div>
						</td>
						<td >开始时间：</td>
						<td >
							<div class="condition-text-container">
								<input name="model.param_start_date" id="param_start_date" class="easyui-datebox" />
							</div>
						</td>
						<td >结束时间：</td>
						<td >
							<div class="condition-text-container">
								<input name="model.param_end_date" id="param_end_date"  class="easyui-datebox" />
							</div>
						</td>
					</tr>
					<tr>
						<td >看护员账号 ：</td>
						<td >
							<div class="condition-text-container">
								<input name="GUARD_NO" id="GUARD_NO"  class="condition-text" />
							</div>
						</td>
						<td >看护员 ：</td>
						<td >
							<div class="condition-text-container">
								<input name="GUARD_NAME" id="GUARD_NAME"  class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addJob()">增加</div>
			<div class="btn-operation" onClick="editJob()">编辑</div>
			<div class="btn-operation" onClick="detail_Job()">计划详情</div>
			<div class="btn-operation" onClick="add_elebarUI()">添加电子围栏</div>
			<div class="btn-operation" onClick="edit_elebarUI()">修改电子围栏</div>
			
			<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win_job_detail"></div>
	<div id="win_job"></div>
	<div id="win_add_plan">
	<div id="win_outsite"></div>
	<div id=win_kan_name></div>
	<div id=win_jianguan_name></div>
	<div id=win_select_kanhu_shijian></div>
	</div>
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		function searchData() {
			var param_start_date =$("#param_start_date").datebox('getValue');
			var param_end_date =$("#param_end_date").datebox('getValue'); 
			var site_name=$("#SITE_NAME").val();
			var guard_name=$("#GUARD_NAME").val();
			var guard_no=$("#GUARD_NO").val();
			
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "outsitePlanManage/queryPlans.do",
				queryParams : {param_start_date:param_start_date,param_end_date:param_end_date,site_name:site_name,guard_name:guard_name,guard_no:guard_no},
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
					width : '10%',
					align : 'center',
					checkbox : true
				},{
					field : 'START_DATE',
					title : '开始时间',
					width : '20%',
					align : 'center'
				}, {
					field : 'END_DATE',
					title : '结束时间',
					width : "15%",
					align : 'center'
				},{
					field : 'STAFF_NO',
					title : '看护员账号',
					width : "15%",
					align : 'center'
				},{
					field : 'STAFF_NAME',
					title : '看护员',
					width : "15%",
					align : 'center'
				},{
					field : 'SITE_NAME',
					title : '外力点',
					width : "30%",
					align : 'center'
				},{
					field : 'IS_ADD_ELEBAR',
					title : '是否添加电子围栏',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						if(value==1){
							return "是";
						}else{
							return "否";
						}
						
					}
				}] ],
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
       			// $(this).datagrid("fixRownumber");
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

		function clearCondition(form_id) {
			$("#"+form_id+"").form('reset', 'none');
		}

		function addJob() {
			 $('#win_job').window({
					title : "【新增外力看护计划】",
					href : webPath + "outsitePlanManage/toAdd_outsite.do",
					width : 400,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
		}
		
		function saveForm() {
			if ($("#formAdd").form('validate')) {
				if(!$("#kanhu_shijian_id").val()){
					$.messager.alert('提示','请选择看护时间段');
					return false;
				}
				$.messager.confirm('系统提示', '您确定要新增外力看护计划吗?', function(r) {
					if (r) {
						var data=makeParamJson('formAdd');
						$.ajax({
							type : 'POST',
							url : webPath + "outsitePlanManage/validateJob.do",
							data : data,
							dataType : 'json',	
							success : function(json2) {
								if (json2.status) {
									$.ajax({
										type : 'POST',
										url : webPath + "outsitePlanManage/add_outsite.do",
										data : data,
										dataType : 'json',
										success : function(json) {
											if (json.status) {
												$.messager.show({
													title : '提  示',
													msg : '新增计划成功！',
													showType : 'show',
													timeout:'1000'//ms
												});
											}
											else{
												$.messager.alert("提示","新增计划失败！","info");
												return;
											}
											$('#win_job').window('close');
											searchData();
										}
									});
								}
								else{
									$.messager.alert("提示","当前人员计划时间内已经存在看护计划,看护时间不可交叉!","info");
									return;
								}
							}
						});
					}
				});
			}
		}
		function showPlanDetail(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					 timeout:'1000'//ms
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				var PLAN_ID2 = selected[0].PLAN_ID2;
				addTab("计划详情",webPath + "outsitePlanManage/showPlanDetail.do?PLAN_ID2=" + PLAN_ID2);
			}
		}
		
		//页面跳转
		function editJob() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					 timeout:'1000'//ms
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				var plan_id = selected[0].PLAN_ID;
				
				var PLAN_ID2 = selected[0].PLAN_ID2;
				$('#win_job').window({
					title : "【编辑外力点计划】",
					href : webPath + "outsitePlanManage/toUpdate.do?plan_id=" + plan_id,
					width : 400,
					height : 400,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				}); 
			}

		}

		function stopJob() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要暂停的计划!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要暂停计划吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].JOB_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$.messager.progress();
						$.ajax({async:false,
							  type:"post",
							  url : webPath + "lineJob/stop.do",
							  data:{ids:$("input[name='selected']").val()},
							  dataType:"json",
							  success:function(data){
								  $.messager.progress('close');
								  if(data.status){
										
										searchData();
										$.messager.show({
											title : '提  示',
											msg : '成功停止计划!',
											showType : 'show',
											timeout:'1000'//ms
										});
									}else{
										alert("停止计划失败");
									}
							  }
						  });
					}
				});
			}
		}
		
		//更新数据
		function saveForm_update() {
			var pass = $("#form_update").form('validate');
			if (pass) {
				if(!$("#kanhu_shijian").val()){
					$.messager.alert('提示','请选择看护时间段');
					return false;
				}
				$.messager.confirm('系统提示', '您确定要更新计划吗?', function(r) {
					if (r) {
						var obj=makeParamJson('form_update');
						
						$.ajax({
							type : 'POST',
							url : webPath + "outsitePlanManage/validateJob.do",
							data : obj,
							dataType : 'json',	
							success : function(json2) {
								if (json2.status) {
									$.ajax({
										type : 'POST',
										url : webPath + "outsitePlanManage/update_outsite_plan.do",
										data : obj,
										dataType : 'json',
										success : function(json) {
											if (json.status) {
												$.messager.show({
													title : '提  示',
													msg : '更新计划成功!',
													showType : 'show',
													timeout:'1000'//ms
												});
											}
											$('#win_job').window('close');
											searchData();
										}
									});
								}
								else{
									alert(json2.status);
									$.messager.alert("提示","当前人员计划时间内已经存在看护计划,看护时间不可交叉!","info");
									return;
								}
							}
						});
					}
				});
			}
		}
		
	function get_outsite() {
			$('#win_outsite').window(
							{
								title : "【选择外力点】",
								href : webPath + "outsitePlanManage/get_outsite.do",
								width : 780,
								height : 500,
								zIndex : 2,
								region : "center",
								collapsible : false,
								cache : false,
								modal : true
							});
		}
		
	function get_kan_name() {
			$('#win_kan_name').window(
							{
								title : "【选择看护人员】",
								href : webPath
										+ "outsitePlanManage/get_kan_name.do",
								width : 650,
								height : 400,
								zIndex : 2,
								region : "center",
								collapsible : false,
								cache : false,
								modal : true
							});
		}
	function get_jianguan_name() {
			$('#win_jianguan_name').window(
							{
								title : "【选择监管人员】",
								href : webPath
										+ "outsitePlanManage/get_jianguan_name.do",
								width : 650,
								height : 400,
								zIndex : 2,
								region : "center",
								collapsible : false,
								cache : false,
								modal : true
							});
		}
//计划详情
function detail_Job(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					 timeout:'1000'//ms
				});
				return;
			}else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			}
			var plan_id = selected[0].PLAN_ID;
			location.href = webPath + "outsitePlanManage/detail_plan.do?plan_id=" + plan_id;
		}
//看护时间段
function select_kanhu_shijian() {
			 $('#win_select_kanhu_shijian').window({
					title : "【选择时间模板】",
					href : webPath + "outsitePlanManage/select_kanhu_shijian.do",
					width : 650,
					height : 400,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
		}
function del_kanhu_shijian(){
	
			$('#kanhu_shijian_id').val("");
			
			$('#kanhu_shijian').val("");
		}
					

			function test() {
						$.ajax({async:false,
							  type:"post",
							  url : webPath + "outsitePlanManage/test.do",
							  //data:{ids:$("input[name='selected']").val()},
							  dataType:"json",
							  success:function(data){
							  }
						  });
		}
			
			
	function add_elebarUI(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		if (count == 0) {
			$.messager.show({
				title : '提  示',
				msg : '请选取一条数据!',
				showType : 'show',
				 timeout:'1000'//ms
			});
			return;
		} else if (count > 1) {
			$.messager.show({
				title : '提  示',
				msg : '仅能选取一条数据!',
				showType : 'show',
				timeout:'1000'//ms
			});
			return;
		} else {
			
			
			var is_add_elebar = selected[0].IS_ADD_ELEBAR;
			if(is_add_elebar==1){
				$.messager.show({
					title : '提  示',
					msg : '该外力点已经添加过电子围栏!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return false;
			}
			
			var plan_id = selected[0].PLAN_ID;
			var out_site_id = selected[0].OUT_SITE_ID;
		
			//addTab("添加电子围栏",webPath + "outsitePlanManage/add_elebarUI.do?out_site_id=" + out_site_id);
			location.href="add_elebarUI.do?plan_id=" + plan_id+"&out_site_id="+out_site_id;
			
		}
	}
	function edit_elebarUI(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		if (count == 0) {
			$.messager.show({
				title : '提  示',
				msg : '请选取一条数据!',
				showType : 'show',
				 timeout:'1000'//ms
			});
			return;
		} else if (count > 1) {
			$.messager.show({
				title : '提  示',
				msg : '仅能选取一条数据!',
				showType : 'show',
				timeout:'1000'//ms
			});
			return;
		} else {
			var is_add_elebar = selected[0].IS_ADD_ELEBAR;
			if(is_add_elebar==0){
				$.messager.show({
					title : '提  示',
					msg : '该外力点还没有添加电子围栏!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return false;
			}
			
			var out_site_id = selected[0].OUT_SITE_ID;
			var plan_id = selected[0].PLAN_ID;
		
			//addTab("修改电子围栏",webPath + "outsitePlanManage/edit_elebarUI.do?out_site_id=" + out_site_id);
			
			location.href="edit_elebarUI.do?out_site_id=" + out_site_id+"&plan_id="+plan_id;
		}
	}
	
	
	</script>

</body>
</html>
