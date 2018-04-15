<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../util/head.jsp"%>
		<title>代维公司管理</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
		<table id="dg" title="【代维公司管理】" style="width: 100%; height: 480px">
		</table>
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="10%">
								公司名称：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="company_name" type="text" class="condition-text" />
								</div>
							</td>
							<td width="10%">
								地市：
							</td>
							<td width="20%">
								<select name="area" class="condition-select">
									<option value="">
										请选择
									</option>
									<c:forEach items="${areaList}" var="al">
										<option value="${al.AREA_ID}">
											${al.NAME}
										</option>
									</c:forEach>
								</select>
							</td>
							<td width="10%">
								区县：
							</td>
							<td width="20%">
								<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
								</select>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="addCompany()">
					增加
				</div>
				<div class="btn-operation" onClick="editCompany()">
					编辑
				</div>
				<div class="btn-operation" onClick="delCompany()">
					删除
				</div>
				<div class="btn-operation" onClick="clearCondition('form')">
					重置
				</div>
				<div style="float: right;" class="btn-operation"
					onClick="searchData()">
					查询
				</div>
			</div>
		</div>
		<div id="win_company"></div>

		<script type="text/javascript">
		$(document).ready(function() {
		selectSelected();
			searchData();
		});
		
		function selectSelected(){
		$.ajax({
				type : 'POST',
				url : webPath + "Staff/selectSelected.do",
				dataType : 'json',
				async:false,
				success : function(json) {
				if(json[0].ifGly==1){
				$("#ifGLY").val("0");
				}else{
				$("select[name='area']").val(${areaId});
				$("select[name='area']").attr("disabled","disabled");
				$("#ifGLY").val("1");
				}

				}
			})
		}
		function searchData() {
			var company_name = $("input[name='company_name']").val().trim();
			var area = $("select[name='area']").val();
			var son_area = $("select[name='son_area']").val();
			var obj = {
				company_name : company_name,
				area : area,
				son_area : son_area
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Company/query.do",
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
					field : 'COMPANY_ID',
					title : '公司ID',
					checkbox : true
				}, {
					field : 'COMPANY_NAME',
					title : '公司名称',
					width : "30%",
					align : 'center'
				},{
					field : 'AREA_NAME',
					title : '地市',
					width : "15%",
					align : 'center'
				}, {
					field : 'SON_AREA_NAME',
					title : '区县',
					width : "15%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '创建人',
					width : "25%",
					align : 'center'
				} , {
					field : 'CREATE_TIME',
					title : '创建时间',
					width : "20%",
					align : 'center'
				} ] ],
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

		$(function() {
			//查询
			if($("#ifGLY").val()=="1"){
				getSonAreaList('query');
			}else{
			$("select[name='area']").change(function() {
				//根据area_id，获取区县
				getSonAreaList('query');
			});
			}
		});

		function getSonAreaList(operator) {
			//	var areaId = $("select[name='area']").val();
			var area_id = 0;
			if ('query' == operator) {
				area_id = $("select[name='area']").find("option:selected")
						.val();
			} else if ('add' == operator) {
				area_id = $("select[name='varea']").find("option:selected")
						.val();
			}
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : area_id
				},
				dataType : 'json',
				success : function(json) {
					var result = json.sonAreaList;
					if ('query' == operator) {
						$("select[name='son_area'] option").remove();
						$("select[name='son_area']").append(
								"<option value=''>请选择</option>");
						for ( var i = 0; i < result.length; i++) {
							$("select[name='son_area']").append(
									"<option value=" + result[i].AREA_ID + ">"
											+ result[i].NAME + "</option>");
						}
					} else if ('add' == operator) {
					if($("#ifGLY").val()=="1"){
					$("select[name='varea']").attr("disabled","disabled")
					}
						$("select[name='vson_area'] option").remove();
						//$("select[name='vson_area']").append(
						//		"<option value=''>请选择</option>");
						for ( var i = 0; i < result.length; i++) {
							$("select[name='vson_area']").append(
									"<option value=" + result[i].AREA_ID + ">"
											+ result[i].NAME + "</option>");
						}
					}

				}
			})
		}

		function clearCondition(form_id) {
			//$(form_id).form('reset','none');
			$("input[name='company_name']").val('');
			$("input[name='company_no']").val('');
			if($("#ifGLY").val()=="0"){
			$("select[name='area']").val('');
			$("select[name='son_area'] option").remove();
			$("select[name='son_area']").append("<option value=''>请选择</option>");
			}else{
			$("select[name='son_area']").val('');
			}
			
			
			
		}

		function addCompany() {
			$('#win_company').window({
				title : "【新增代维公司】",
				href : webPath + "Company/add.do",
				width : 400,
				height : 200,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function editCompany() {
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
				var company_id = selected[0].COMPANY_ID;
				$('#win_company').window({
					title : "【编辑代维公司】",
					href : webPath + "Company/edit.do?company_id=" + company_id,
					width : 400,
					height : 200,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}

		}

		function delCompany() {
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
				$.messager.confirm('系统提示', '您确定要删除代维公司吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].COMPANY_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "Company/delete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除代维公司!',
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}


		//company-add.jsp
		function clearForm() {
			$("input[name='vcompany_name']").val('');
			$("select[name='varea']").val('');
			$("select[name='vson_area']").val('');
		}
		function saveForm() {
			var pass = $("#ff").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要新增代维公司吗?', function(r) {
					if (r) {
						var company_name = $("input[name='vcompany_name']").val();
						var area = $("select[name='varea']").val();
						var son_area = $("select[name='vson_area']").val();
						$.ajax({
							type : 'POST',
							url : webPath + "Company/save.do",
							data : {
								company_name : company_name,
								area : area,
								son_area : son_area
							},
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '新增代维公司成功!',
										showType : 'show'
									});
								}
								$('#win_company').window('close');
								searchData();

							}
						})
					}
				});
			}
		}
		function updateForm() {
			var pass = $("#ff").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要更新代维公司吗?', function(r) {
					if (r) {
						var company_id = $("input[name='vcompany_id']").val();
						var company_name = $("input[name='vcompany_name']").val();
						var area = $("select[name='varea']").val();
						var son_area = $("select[name='vson_area']").val();
						$.ajax({
							type : 'POST',
							url : webPath + "Company/update.do",
							data : {
								company_id : company_id,
								company_name : company_name,
								area : area,
								son_area : son_area
							},
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新代维公司成功!',
										showType : 'show'
									});
								}
								$('#win_company').window('close');
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