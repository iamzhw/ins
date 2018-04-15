<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../util/head.jsp"%>
		<title>班组管理</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
		<table id="dg" title="【班组管理】" style="width: 100%; height: 480px">
		</table>
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<input type="hidden" name="inspact_id" id="inspact_id" />
					<table class="condition">
						<tr>
							<td width="10%">
								班组编码：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="dept_no" type="text" class="condition-text" />
								</div>
							</td>
							<td width="10%">
								班组名称：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="dept_name" type="text" class="condition-text" />
								</div>
							</td>
						<td width="10%">区域：</td>
						<td width="30%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="addDept()">
					增加
				</div>
				<div class="btn-operation" onClick="editDept()">
					编辑
				</div>
				<div class="btn-operation" onClick="delDept()">
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
		<div id="win_dept"></div>
		<div id="win_staff"></div>
		<script type="text/javascript">
		$(document).ready(function() {
			getSonAreaList();
			selectSelected();
			searchData();
		});
		var areaId='${areaId}';
		function getSonAreaList() {
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
				var GLY ='${GLY}';
				var LXXJ_ADMIN ='${LXXJ_ADMIN}';
				var LXXJ_ADMIN_AREA ='${LXXJ_ADMIN_AREA}';
				if (GLY == 'true' || LXXJ_ADMIN =='true' || LXXJ_ADMIN_AREA=='true')
				{
					$("select[name='son_area']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
			}
		});
	}
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
			var dept_name = $("input[name='dept_name']").val().trim();
			var dept_no = $("input[name='dept_no']").val().trim();
			var area = $("select[name='area']").val();
			var son_area = $("select[name='son_area']").val();
			var obj = {
				dept_name : dept_name,
				dept_no : dept_no,
				area : area,
				son_area : son_area
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Dept/query.do",
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
					field : 'DEPT_ID',
					title : '班组ID',
					checkbox : true
				}, 
				{
					field : 'AREA_NAME',
					title : '地市',
					width : "8%",
					align : 'center'
				}, {
					field : 'SON_AREA_NAME',
					title : '区域',
					width : "12%",
					align : 'center'
				},{
					field : 'DEPT_NO',
					title : '班组编码',
					width : "10%",
					align : 'center'
				},{
					field : 'DEPT_NAME',
					title : '班组名称',
					width : "12%",
					align : 'center'
				},
				{
					field : 'PEOPLENUM',
					title : '班组人数',
					width : "8%",
					align : 'center'
				},
				 {
					field : 'STAFF_NAME',
					title : '创建人',
					width : "15%",
					align : 'center'
				} , {
					field : 'CREATE_TIME',
					title : '创建时间',
					width : "10%",
					align : 'center'
				}
				, {
				field : 'operation',
				title : '操作',
				width : "10%",
				align : 'center',
				formatter:function(value,rowData,rowIndex){
                	return "<a href=\"javascript:distribute(" + rowData.DEPT_ID + ");\">分配人员</a>";
                } 
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

		function clearCondition(form_id) {
			//$(form_id).form('reset','none');
			$("input[name='class_name']").val('');
			//$("input[name='CLASS_no']").val('');
			if($("#ifGLY").val()=="0"){
			$("select[name='area']").val('');
			$("select[name='son_area'] option").remove();
			$("select[name='son_area']").append("<option value=''>请选择</option>");
			}else{
			$("select[name='son_area']").val('');
			}
			
		}

		function addDept() {
			$('#win_dept').window({
				title : "【新增班组】",
				href : webPath + "Dept/add.do",
				width : 400,
				height : 200,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function editDept() {
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
				var dept_id = selected[0].DEPT_ID;
				$('#win_dept').window({
					title : "【编辑班组】",
					href : webPath + "Dept/edit.do?dept_id=" + dept_id,
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

		function delDept() {
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
				$.messager.confirm('系统提示', '您确定要删除班组吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].DEPT_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "Dept/delete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除班组!',
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
			$("input[name='vclass_name']").val('');
			$("select[name='varea']").val('');
			$("select[name='vson_area']").val('');
		}
		function saveForm() {
			var pass = $("#ff").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要新增班组吗?', function(r) {
					if (r) {
						var dept_no = $("input[name='vdept_no']").val();
						var dept_name = $("input[name='vdept_name']").val();
						var son_area = $("select[name='vson_area']").val();
						$.ajax({
							type : 'POST',
							url : webPath + "Dept/save.do",
							data : {
								dept_no : dept_no,
								dept_name : dept_name,
								area : areaId,
								son_area : son_area
							},
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '新增班组成功!',
										showType : 'show'
									});
								}
								$('#win_dept').window('close');
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
				$.messager.confirm('系统提示', '您确定要更新班组信息吗?', function(r) {
					if (r) {
						var dept_id = $("input[name='edept_id']").val();
						var dept_no = $("input[name='edept_no']").val();
						var dept_name = $("input[name='edept_name']").val();
						var son_area = $("select[name='eson_area']").val();
						$.ajax({
							type : 'POST',
							url : webPath + "Dept/update.do",
							data : {
								dept_id : dept_id,
								dept_name : dept_name,
								dept_no : dept_no,
								son_area : son_area
							},
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新班组成功!',
										showType : 'show'
									});
								}
								$('#win_dept').window('close');
								searchData();
							}
						})
					}
				});
			}
		}
		function distribute(arr)
		{
			$('#win_staff').window({
				title : "【选择班组成员】",
				href : webPath + "Dept/staffSelected.do?dept_id=" + arr,
				width : 480,
				height : 480,
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