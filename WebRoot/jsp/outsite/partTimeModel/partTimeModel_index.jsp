
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>看护时间段模板管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【看护时间段模板管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_start_time" id="p_start_time" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm'})"
									class="condition-text" />
							</div>
						</td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_end_time" id="p_end_time" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm'})"
									class="condition-text" />
							</div>
						</td>

					</tr>

				</table>
			</form>
		</div>
		<div class="btn-operation-container">
		<c:if test="${isAdmin==1}">
		   <div class="btn-operation" onClick="partTimeModelAddUI()">增加</div>
			<div class="btn-operation" onClick="partTimeModelEditUI()">编辑</div>
			<div class="btn-operation" onClick="partTimeModelDeleteUI()">删除</div>
		</c:if>
			<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win"></div>
	<div id="win_distribute"></div>

	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {

			searchData();
		});

		function searchData() {

			var obj = makeParamJson('form');

			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "partTimeModelController/query.do",
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
					field : 'PART_TIME_ID',
					title : 'ID',
					checkbox : true
				}, {
					field : 'START_TIME',
					title : '开始时间',
					width : "15%",
					align : 'center'
				}, {
					field : 'END_TIME',
					title : '结束时间',
					width : "15%",
					align : 'center'
				},  {
					field : 'CITY_NAME',
					title : '城市',
					width : "15%",
					align : 'center'
				}, {
					field : 'IS_PRITERMISSION',
					title : '是否默认',
					width : "15%",
					align : 'center',
					formatter:function(value, rec,index) {
					if(value=='0'){
						return "否";
					}else{
						return "是";
					}
				}
				}

				] ],
				//width : 'auto',
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

			$("#" + form_id + "").form('reset', 'none');

		}

		function partTimeModelAddUI() {
			
			$('#win')
					.window(
							{
								title : "【新增看护时间段模板】",
								href : webPath
										+ "partTimeModelController/partTimeModelAddUI.do",
								width : 400,
								height : 250,
								zIndex : 2,
								region : "center",
								collapsible : false,
								cache : false,
								modal : true
							});
		}

		function saveForm() {
			var pass = $("#formAdd").form('validate');
			if (pass) {
				$.messager
						.confirm(
								'系统提示',
								'您确定要新增看护时间段模板吗?',
								function(r) {
									if (r) {
										var data = makeParamJson('formAdd');
										$
												.ajax({
													type : 'POST',
													url : webPath
															+ "partTimeModelController/partTimeModelSave.do",
													data : data,
													dataType : 'json',
													success : function(json) {
														if (json.status) {

															$.messager
																	.show({
																		title : '提  示',
																		msg : '新增看护时间段模板成功！',
																		showType : 'show',
																		timeout : '1000'//ms

																	});
														} else {
															$.messager
																	.alert(
																			"提示",
																			"新增看护时间段模板失败！",
																			"info");
															return;
														}
														$('#win').window(
																'close');
														searchData();

													}
												})
									}
								});
			}
		}

		function partTimeModelEditUI() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
			} else {
				var part_time_id = selected[0].PART_TIME_ID;

				$('#win')
						.window(
								{
									title : "【编辑看护时间段模板】",
									href : webPath
											+ "partTimeModelController/partTimeModelEditUI.do?id="
											+ part_time_id,
									width : 400,
									height : 250,
									zIndex : 2,
									region : "center",
									collapsible : false,
									cache : false,
									modal : true
								});

				/*
				var url=webPath + "partTimeModelController/partTimeModelEditUI.do?id="+part_time_id;
				 var res=window.showModalDialog(url, null,
								"dialogWidth=400px;dialogHeight=300px;status:no;resizable:yes;");
				 if(res==1){
					 searchData();
				 } */

			}

		}

		function partTimeModelDeleteUI() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要删除的数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
			} else {
				$.messager
						.confirm(
								'系统提示',
								'您确定要删除看护时间段模板吗?',
								function(r) {
									if (r) {
										var arr = new Array();
										for ( var i = 0; i < selected.length; i++) {
											var value = selected[i].PART_TIME_ID;
											arr[arr.length] = value;
										}
										$("input[name='selected']").val(arr);
										$.messager.progress();
										$
												.ajax({
													async : false,
													type : "post",
													url : webPath
															+ "partTimeModelController/partTimeModelDelete.do",
													data : {
														ids : $(
																"input[name='selected']")
																.val()
													},
													dataType : "json",
													success : function(data) {
														$.messager
																.progress('close');
														if (data.status) {

															searchData();
															$.messager
																	.show({
																		title : '提  示',
																		msg : '成功删除看护时间段模板!',
																		showType : 'show',
																		timeout : '1000'//ms

																	});
														} else {
															alert("删除失败");
														}
													}
												});

									}
								});
			}
		}

		function updateForm() {
			var pass = $("#formEdit").form('validate');
			if (pass) {
				$.messager
						.confirm(
								'系统提示',
								'您确定要更新看护时间段模板吗?',
								function(r) {
									if (r) {
										var obj = makeParamJson('formEdit');
										$
												.ajax({
													type : 'POST',
													url : webPath
															+ "partTimeModelController/partTimeModelUpdate.do",
													data : obj,
													dataType : 'json',
													success : function(json) {
														if (json.status) {
															$.messager
																	.show({
																		title : '提  示',
																		msg : '更新看护时间段模板成功!',
																		showType : 'show',
																		timeout : '1000'//ms
																	});
														}
														$('#win').window(
																'close');
														searchData();
													}
												})
									}
								});
			}
		}
	</script>

</body>
</html>
