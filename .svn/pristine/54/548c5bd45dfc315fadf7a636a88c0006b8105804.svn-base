<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../util/head.jsp"%>
		<title>员工列表</title>

	</head>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
		<table id="dg" title="【员工管理】">
		</table>
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="10%">
								姓名：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="staff_name" type="text" class="condition-text" />
								</div>
							</td>
							<td width="10%">
								帐号：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="staff_no" type="text" class="condition-text" />
								</div>
							</td>
						</tr>
						<tr>
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
				<div class="btn-operation" onClick="selectStaff()">选择</div>
				<div style="float: right;" class="btn-operation" onClick="searchData()">
					查询
				</div>
			</div>
		</div>
		<div id="win_staff"></div>

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
			var staff_name = $("input[name='staff_name']").val().trim();
			var staff_no = $("input[name='staff_no']").val().trim();
			var area = $("select[name='area']").val();
			var son_area = $("select[name='son_area']").val();
			var obj = {
				staff_name : staff_name,
				staff_no : staff_no,
				area : area,
				son_area : son_area
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Staff/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers :  false,
			
				singleSelect : true,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '员工ID',
					checkbox:true
				},{
					field : 'STAFF_NAME',
					title : '员工姓名',
					width : "10%",
					align : 'center'
				}, {
					field : 'STAFF_NO',
					title : '员工帐号',
					width : "25%",
					align : 'center'
				}, {
					field : 'ROLE_NAME',
					title : '角色',
					width : "30%",
					align : 'center'
				}, {field : 'AREA',
					title : '地市',
					width : "10%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区县',
					width : "10%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
       			 $(this).datagrid("fixRownumber");
				
				}
			});
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
			$("input[name='staff_name']").val('');
			$("input[name='staff_no']").val('');
			if($("#ifGLY").val()=="0"){
			$("select[name='area']").val('');
			$("select[name='son_area'] option").remove();
			$("select[name='son_area']").append("<option value=''>请选择</option>");
			}else{
			$("select[name='son_area']").val('');
			}
		}
		
	function selectStaff()
		{
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
				$("#CHECK_STAFF_ID").val(selected[0].STAFF_ID);
				$("#CHECK_STAFF_NAME").val(selected[0].STAFF_NAME);
				$('#win_staff').window('close');
			}
		}
	</script>

	</body>
</html>