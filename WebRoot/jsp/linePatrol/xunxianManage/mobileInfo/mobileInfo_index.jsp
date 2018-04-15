
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>手机信息管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【手机信息管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">手机号码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_tel_no" id="p_tel_no" type="text"
									class="condition-text" />
							</div>
						</td>
						<td width="10%">手机串号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_imei" id="p_imei" type="text"
									class="condition-text" />
							</div>
						</td>

					</tr>
					<tr>
						<td width="10%">手机卡串号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_card_id" id="p_card_id" type="text"
									class="condition-text" />
							</div>
						</td>


					</tr>



				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="mobileInfoAddUI()">增加</div>
			<div class="btn-operation" onClick="mobileInfoEditUI()">编辑</div>
			<div class="btn-operation" onClick="mobileInfoDeleteUI()">删除</div>


			<%--<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			--%><div style="float: right;" class="btn-operation"
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
				url : webPath + "mobileInfoController/query.do",
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
					field : 'MOBILE_ID',
					title : '手机信息ID',
					checkbox : true
				}, {
					field : 'TEL_NO',
					title : '手机号码',
					width : "10%",
					align : 'center'
				}, {
					field : 'STATUS',
					title : '状态',
					width : "10%",
					align : 'center'
				}, {
					field : 'IMEI',
					title : '手机串号',
					width : "10%",
					align : 'center'
				}, {
					field : 'CARD_ID',
					title : '手机卡串号',
					width : "10%",
					align : 'center'
				}, {
					field : 'MOBILETYPE',
					title : '手机型号',
					width : "10%",
					align : 'center'
				}, {
					field : 'BATTERYNUM',
					title : '电池数量',
					width : "10%",
					align : 'center'
				}, {
					field : 'MOBILESTATUS',
					title : '手机状态',
					width : "10%",
					align : 'center'
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

		function mobileInfoAddUI() {
			
			$('#win').window({
				title : "【新增手机信息】",
				href : webPath + "mobileInfoController/mobileInfoAddUI.do",
				width : 400,
				height : 300,
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
				
				var regexp=/^1\d{10}$/;
				var tel_no=$("#tel_no").val();
				if(!regexp.test(tel_no)){
					alert("手机号码格式不正确！");
					return ;
				}
			
				$.messager
						.confirm(
								'系统提示',
								'您确定要新增手机信息吗?',
								function(r) {
									if (r) {
										var data = makeParamJson('formAdd');
										$
												.ajax({
													type : 'POST',
													url : webPath
															+ "mobileInfoController/mobileInfoSave.do",
													data : data,
													dataType : 'json',
													success : function(json) {
														if (json.status) {

															$.messager
																	.show({
																		title : '提  示',
																		msg : '新增手机信息成功！',
																		showType : 'show',
																		timeout : '1000'//ms

																	});
														} else {
															$.messager
																	.alert(
																			"提示",
																			"新增手机信息失败！",
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

		function mobileInfoEditUI() {
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
				var mobile_id = selected[0].MOBILE_ID;

				$('#win')
						.window(
								{
									title : "【编辑手机信息】",
									href : webPath
											+ "mobileInfoController/mobileInfoEditUI.do?id="
											+ mobile_id,
									width : 400,
									height : 300,
									zIndex : 2,
									region : "center",
									collapsible : false,
									cache : false,
									modal : true
								});

				/*
				var url=webPath + "mobileInfoController/mobileInfoEditUI.do?id="+mobile_id;
				 var res=window.showModalDialog(url, null,
								"dialogWidth=400px;dialogHeight=300px;status:no;resizable:yes;");
				 if(res==1){
					 searchData();
				 } */

			}

		}

		function mobileInfoDeleteUI() {
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
								'您确定要删除手机信息吗?',
								function(r) {
									if (r) {
										var arr = new Array();
										for ( var i = 0; i < selected.length; i++) {
											var value = selected[i].MOBILE_ID;
											arr[arr.length] = value;
										}
										$("input[name='selected']").val(arr);
										$.messager.progress();
										$
												.ajax({
													async : false,
													type : "post",
													url : webPath
															+ "mobileInfoController/mobileInfoDelete.do",
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
																		msg : '成功删除手机信息!',
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
				
				var regexp=/^1\d{10}$/;
				var tel_no=$("#tel_no").val();
				if(!regexp.test(tel_no)){
					alert("手机号码格式不正确！");
					return ;
				}
				
				$.messager
						.confirm(
								'系统提示',
								'您确定要更新手机信息吗?',
								function(r) {
									if (r) {
										var obj = makeParamJson('formEdit');
										$
												.ajax({
													type : 'POST',
													url : webPath
															+ "mobileInfoController/mobileInfoUpdate.do",
													data : obj,
													dataType : 'json',
													success : function(json) {
														if (json.status) {
															$.messager
																	.show({
																		title : '提  示',
																		msg : '更新手机信息成功!',
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
