
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>外力点维护方案管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【外力点维护方案管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<input type="hidden" name="area_id" value="${area_id}" />
				<table class="condition">
					<tr>
						<td width="10%">方案名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_scheme_name" id="p_scheme_name" type="text" class="condition-text" />
							</div></td>
						<td width="10%">外力点名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_out_site_name" id="p_out_site_name" type="text" class="condition-text" />
							</div></td>
<!-- 						<td width="10%">方案状态：</td> -->
<!-- 						<td width="20%"> -->

<!-- 							<div> -->
<!-- 								<select name="p_status" id="p_status" class="condition-select"> -->
									
<!-- 									<option value=''>--请选择--</option> -->
<!-- 									<option value='0'>未确认</option> -->
<!-- 									<option value='1'>已确认</option> -->
									
<!-- 								</select> -->


<!-- 							</div> -->
							
<!-- 							</td> -->
					</tr>
					
					
					
					
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
<%--			<div class="btn-operation" onClick="osMaintainSchemeAddUI()">增加</div>--%>
			<div class="btn-operation" onClick="osMaintainSchemeEditUI()">编辑</div>
			<div class="btn-operation" onClick="osMaintainSchemeDeleteUI()">删除</div>
			

			<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win"></div>
	<div id="win_detailUI"></div>
	
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		
			searchData();
		});
		
		
		function searchData() {
			
			var obj=makeParamJson('form');
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "osMaintainSchemeController/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				columns : [ [  {

		            field : 'SCHEME_ID',
					title : 'ID',
					checkbox : true
				}, {
					field : 'SCHEME_NAME',
					title : '方案名称',
					width : "20%",
					align : 'center'
				}, {
					field : 'SITE_NAME',
					title : '外力点名称',
					width : "15%",
					align : 'center'
				},  {
					field : 'CON_STATUS',
					title : '外力点状态',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						if (value==1) {
							return "外力施工中";
						} else {
							return "外力施工结束";
						}
						
					}
				}, {
					field : 'CREATED_PERSON',
					title : '创建人',
					width : "10%",
					align : 'center'
				},{
					field : 'CREATION_TIME',
					title : '创建时间',
					width : "15%",
					align : 'center'
				},{
					field : 'TS_RES', 
					title : '推送结果',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						if(value==='000'){
							return "推送成功";
						}else{
							return "推送失败";
						}
						
					}
				},{
					field : 'opr',
					title : '操作',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						return "&nbsp;&nbsp;<a  style='color:blue' onclick='detailUI(\""
						+ rec.SCHEME_ID
						+ "\")'>详情</a>&nbsp;&nbsp;";
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

		function osMaintainSchemeAddUI() {
			/*
			var url=webPath + "osMaintainSchemeController/osMaintainSchemeAddUI.do";
			 var res=window.showModalDialog(url, null,
							"dialogWidth=400px;dialogHeight=300px;status:no;resizable:yes;");
			 if(res==1){
				 searchData();
			 } */
			$('#win')
					.window(
							{
								title : "【新增外力点维护方案】",
								href : webPath
										+ "osMaintainSchemeController/osMaintainSchemeAddUI.do",
								width : 400,
								height : 200,
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
								'您确定要新增外力点维护方案吗?',
								function(r) {
									if (r) {
										var data = makeParamJson('formAdd');
										$
												.ajax({
													type : 'POST',
													url : webPath
															+ "osMaintainSchemeController/osMaintainSchemeSave.do",
													data : data,
													dataType : 'json',
													success : function(json) {
														if (json.status) {

															$.messager
																	.show({
																		title : '提  示',
																		msg : '新增外力点维护方案成功！',
																		showType : 'show',
																		timeout : '1000'//ms

																	});
														} else {
															$.messager
																	.alert(
																			"提示",
																			"新增外力点维护方案失败！",
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

		function osMaintainSchemeEditUI() {
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
				
				var status = selected[0].STATUS;
				if(status==1){
					alert("该方案已经确认，不能更改！");
				    return ;
				}
				var scheme_id = selected[0].SCHEME_ID;

				$('#win')
						.window(
								{
									title : "【编辑外力点维护方案】",
									href : webPath
											+ "osMaintainSchemeController/osMaintainSchemeEditUI.do?id="
											+ scheme_id,
									width : 400,
									height : 500,
									zIndex : 2,
									region : "center",
									collapsible : false,
									cache : false,
									modal : true
								});

				

			}

		}

		function osMaintainSchemeDeleteUI() {
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
								'您确定要删除外力点维护方案吗?',
								function(r) {
									if (r) {
										var arr = new Array();
										for ( var i = 0; i < selected.length; i++) {
											var value = selected[i].SCHEME_ID;
											arr[arr.length] = value;
										}
										$("input[name='selected']").val(arr);
										$.messager.progress();
										$
												.ajax({
													async : false,
													type : "post",
													url : webPath
															+ "osMaintainSchemeController/osMaintainSchemeDelete.do",
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
																		msg : '成功删除外力点维护方案!',
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
								'您确定要更新外力点维护方案吗?',
								function(r) {
									if (r) {
										var obj = makeParamJson('formEdit');
										$
												.ajax({
													type : 'POST',
													url : webPath
															+ "osMaintainSchemeController/osMaintainSchemeUpdate.do",
													data : obj,
													dataType : 'json',
													success : function(json) {
														if (json.status) {
															$.messager
																	.show({
																		title : '提  示',
																		msg : '更新外力点维护方案成功!',
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
		
		
			function detailUI(scheme_id){
				$('#win_detailUI').window({
					title : "【详情】",
					href : webPath+ "osMaintainSchemeController/detailUI.do?scheme_id="+scheme_id,
					width : 400,
					height : 500,
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
