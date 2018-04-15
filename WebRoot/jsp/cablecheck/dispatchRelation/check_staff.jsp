<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>确认检查实施人</title>
</head>
<body style="padding:3px;border:0px" >
	<form id="form_task" action="" method="post">
		<input name="selected_staffId" type="hidden" />
		<input name="selected_planId" type="hidden"/>
		<input name="inspector_type" type="hidden" />
	</form>
	<table id="dg_plan" title="【确认检查实施人】" style="width:100%;height:480px">
	</table>
	<div id="tb_staff" style="padding:5px;height:auto">
	<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
					<td width="10%">帐号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="STAFF_NO" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">姓名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="STAFF_NAME" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">区域：</td>
						<td width="20%">
							<select id="SON_AREA_ID" name="SON_AREA_ID" class="condition-select">
									<option value="">
										请选择
									</option>
									<c:forEach items="${sonAreaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
							<input type="hidden" id="AREA_ID" name="AREA_ID" value="${AREA_ID}"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" >
			<div class="btn-operation" onClick="queryStaffby()" >查询</div>
			<div class="btn-operation" onClick="sureCheckStaff()">确定实施人</div>
		</div>
	</div>
    <div id="win_staff" ></div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#AREA_ID").val("${AREA_ID}");
			$("#SON_AREA_ID").val("${SON_AREA_ID}");//默认给区域下拉框选中
			queryStaff();
		});
		function queryStaffby(){
			queryStaff();
		}
		function queryStaff() {
			var STAFF_NAME = $("input[name='STAFF_NAME']").val().trim();
			var STAFF_NO = $("input[name='STAFF_NO']").val().trim();
			var SON_AREA_ID = $("select[name='SON_AREA_ID']").val().trim();
			var AREA_ID = $("#AREA_ID").val();
			var obj = {
					STAFF_NAME : STAFF_NAME,
					STAFF_NO : STAFF_NO,
					SON_AREA_ID : SON_AREA_ID,
					AREA_ID : AREA_ID
			};
			//return;
			$('#dg_plan').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 600,
				height : 600,
				toolbar : '#tb_staff',
				url : webPath + "dispatchRelation/query_staff.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '员工ID',
					checkbox : true
				}, {
					field : 'STAFF_NO',
					title : '员工编码',
					width : "10%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '员工名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '员工名称',
					width : "10%",
					align : 'center'
				}
				/* ,{
					field : 'STAFF_TYPE_NAME',
					title : '人员类型',
					width : "10%",
					align : 'center'
				} */
				] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
					<%--var rows = $("#dg_plan").datagrid("getRows"); 
					var roles = ${roles};
					alert(roles);
					for(var i=0;i<rows.length;i++){
					    var role_id = rows[i].STAFF_ID;
					    var flag = 0;
						for(var j=0;j<roles.length;j++){
							if(role_id==roles[j]){
								flag = 1;
							}
						}
						if(flag == 1){
							$('#dg_plan').datagrid('selectRow',i);
						}
					}
					--%>
				}
			});
			
		}
		function sureCheckStaff() {
		    var selected = $('#dg_plan').datagrid('getChecked');
		    var count = selected.length;
		    if (count == 0) {
		        $.messager.show({
		            title: '提  示',
		            msg: '请选取检查实施人!',
		            showType: 'show'
		        });
		        return;
		    } else {
		    	var arr = new Array();
		    	var staffName;
		        for (var i = 0; i < selected.length; i++) {
		            var value = selected[i].STAFF_ID;
		            staffName = selected[i].STAFF_NAME;
		            arr[arr.length] = value;
		        }
		        $("input[id='STAFF_NAME']").val(staffName);
		        $("input[id='STAFF_ID']").val(arr);
		        $('#win_staff').window("close");
		    }
		}

	</script>
</body>
</html>