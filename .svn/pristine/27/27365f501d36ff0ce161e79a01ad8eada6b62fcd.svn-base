
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>短信模板管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【短信模板管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">模板名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_mss_name" id="p_mss_name" type="text" class="condition-text" />
							</div></td>
						<td width="10%">模板类型：</td>
						<td width="20%">
							
							
							<div>
								<select name="p_mms_type" id="p_mms_type" class="condition-select">
									   <option value=''>--请选择--</option>
									<c:forEach items="${modalTypeList}" var="res">
								    
								        <option value='${res.TYPE_ID}'>${res.TYPE_NAME}</option>
								   </c:forEach>
									
								</select>


							</div>
							
							</td>

					</tr>
					
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="mmsModelAddUI()">增加</div>
			<div class="btn-operation" onClick="mmsModelEditUI()">编辑</div>
			<div class="btn-operation" onClick="mmsModelDeleteUI()">删除</div>
			

			<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win"></div>
	<div id="insertItem"></div>

	
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
				url : webPath + "mmsModelController/query.do",
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
									
			            field : 'MMS_ID',
						title : 'ID',
						checkbox : true
					}, {
						field : 'MSS_NAME',
						title : '模板名称',
						width : "10%",
						align : 'center'
					}, {
						field : 'MM_CONTENT',
						title : '模板内容',
						width : "80%",
						align : 'center'
					}, {
						field : 'TYPE_NAME',
						title : '模板类型',
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

		function mmsModelAddUI() {
			
			$('#win').window({
				title : "【新增短信模板】",
				href : webPath + "mmsModelController/mmsModelAddUI.do",
				width : 600,
				height : 400,
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
								'您确定要新增短信模板吗?',
								function(r) {
									if (r) {
										var data = makeParamJson('formAdd');
										$
												.ajax({
													type : 'POST',
													url : webPath
															+ "mmsModelController/mmsModelSave.do",
													data : data,
													dataType : 'json',
													success : function(json) {
														if (json.status) {

															$.messager
																	.show({
																		title : '提  示',
																		msg : '新增短信模板成功！',
																		showType : 'show',
																		timeout : '1000'//ms

																	});
														} else {
															$.messager
																	.alert(
																			"提示",
																			"新增短信模板失败！",
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

		function mmsModelEditUI() {
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
				var mms_id = selected[0].MMS_ID;

				$('#win')
						.window(
								{
									title : "【编辑短信模板】",
									href : webPath
											+ "mmsModelController/mmsModelEditUI.do?id="
											+ mms_id,
											width : 600,
											height : 400,
									zIndex : 2,
									region : "center",
									collapsible : false,
									cache : false,
									modal : true
								});

				

			}

		}

		function mmsModelDeleteUI() {
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
				$.messager.confirm('系统提示', '您确定要删除短信模板吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].MMS_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$.messager.progress();
						$.ajax({
							async : false,
							type : "post",
							url : webPath
									+ "mmsModelController/mmsModelDelete.do",
							data : {
								ids : $("input[name='selected']").val()
							},
							dataType : "json",
							success : function(data) {
								$.messager.progress('close');
								if (data.status) {

									searchData();
									$.messager.show({
										title : '提  示',
										msg : '成功删除短信模板!',
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
				$.messager.confirm('系统提示', '您确定要更新短信模板吗?', function(r) {
					if (r) {
						var obj = makeParamJson('formEdit');
						$.ajax({
							type : 'POST',
							url : webPath
									+ "mmsModelController/mmsModelUpdate.do",
							data : obj,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新短信模板成功!',
										showType : 'show',
										timeout : '1000'//ms
									});
								}
								$('#win').window('close');
								searchData();
							}
						})
					}
				});
			}
		}
		
		 function insertToContent(){
			 $('#insertItem').window({
					title : "",
					href : webPath + "/jsp/system/shortMessage/mmsModel_insertItem.html",
					width : 150,
					height : 300,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			 
		  }
		 
		 function getItem(obj,ih){
			 var str="{"+ih+"}"
			 
			 obj.focus();
			 if (document.selection) {
			     var sel = document.selection.createRange();
			     sel.text = str;
			 } else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
			     var startPos = obj.selectionStart,
			      endPos = obj.selectionEnd,
			      cursorPos = startPos,
			      tmpStr = obj.value;
			     obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
			     cursorPos += str.length;
			     obj.selectionStart = obj.selectionEnd = cursorPos;
			 } else {
			     obj.value += str;
			 }
			 
			 $('#insertItem').window('close');
		 }
		 
		
		
	</script>

</body>
</html>
