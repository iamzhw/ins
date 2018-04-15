<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../../util/head.jsp"%>
	<title>选择任务执行人</title>
</head>
<body>
	<input name="billIds" value="${billIds}" type="hidden"/>
	<input name="operate" value="${operate}" type="hidden"/>
	<input name="area" id="area" value="3" type="hidden">
	<input name="son_area" id="son_area" type="hidden">
	<div id="tb_soft" style="padding: 5px; height: auto">
		<div class="condition-container" style="height: auto;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<c:if test="${operate == 'distributeBill' or operate == 'transmit' }">
						<tr>
							<td width="12%">整改结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name=complete_time id="complete_time" onClick="WdatePicker();"
										readonly="readonly" />
								</div>
							</td>
						</tr>
						<tr>
							<td width="12%">整改要求：</td>
							<td width="20%"><textarea id="reform_demand" rows="3"
									cols="20"></textarea></td>
						</tr>
					</c:if>
					<c:if test="${operate == 'distributeZqBill' or operate == 'transmitZq' }">
						<tr>
							<td width="12%">检查结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name=complete_time id="complete_time" onClick="WdatePicker();"
										readonly="readonly" />
								</div>
							</td>
						</tr>
					</c:if>

					<c:if test="${operate == 'zhuanfa'}">
						<tr>
						<td width=2	>
								角色：
						</td>
						
						<td width=5>
							<select name="roleid" id="roleid" class="condition-select">
								<option value="367">
									综维
								</option>
								<option value="368">
									装维
								</option>
								<option value="386">
									网维
								</option>
								<option value="388">
									工程
								</option>
								<option value="391">
									无线
								</option>
								<option value="394">
									政支
								</option>								
							</select>
						</td>
						</tr>
						</c:if>
						<tr>
						<td width=2>
							区域：
						</td>
						<td width=5>
							<select name="sonarea" id="sonarea" class="condition-select">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width=2>姓名：</td>
						<td width=5>
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					<c:if test="${operate == 'zhuanfa'}">
						<tr>
							<td width="12%">整改要求：</td>
							<td width="20%"><textarea id="reform_demand_ZF" rows="3"
									cols="20"></textarea></td>
						</tr>
					</c:if>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="clearCondition()"
				style="float: right;">重置</div>
			<div class="btn-operation" onClick="searchData1()"
				style="float: right;">查询</div>
			<div class="btn-operation" onClick="saveTask()" style="float: right;">确定</div>
		</div>

	</div>
	<table id="dg_soft" title="【选择人员】" style="width: 100%; height: 480px">
	</table>
	<script type="text/javascript">
		$(document).ready(function() {
		
			getSonAreaList1();
	searchData1();
	
		});
		$("select[name='sonarea']").change(function() {
				$("input[name='son_area']").val($("select[name='sonarea']").val());//根据area_id，获取区县
			}); 
	function getSonAreaList1() {
	var areaId = $("input[name='area']").val();
	var sonAreaId = $("#son_area").val();
	
	$.ajax({
			type : 'POST',
			async:false,
			url : webPath + "General/getSonAreaList.do",
			data : {
				areaId : areaId
			},
			dataType : 'json',
			success : function(json) 
			{		
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				var result = json.sonAreaList;
				$("select[name='sonarea'] option").remove();
				$("select[name='sonarea']").append("<option value=''>请选择</option>");
				if(true ==isAdmin1 || isAdmin1 =='true'){	
					for ( var i = 0; i < result.length; i++) {					
						$("select[name='sonarea']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");												
					}
				}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true'){
					for ( var i = 0; i < result.length; i++) {
						$("select[name='sonarea']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
					}
				}else{
					/*for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
						$("select[name='sonarea'] option").remove();
							$("select[name='sonarea']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}						
					}**/

					for ( var i = 0; i < result.length; i++) {
						$("select[name='sonarea']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
					}
				}
			}
		});
}  
		function searchData1(){
			var staff_name = $("input[name='staff_name']").val();
			var operate = $("input[name='operate']").val();
			var billIds = $("input[name='billIds']").val();
			var sonAreaId = $("select[name='sonarea']").val();
			var role_id = $("select[name='roleid']").val();
			//var complete_time = $("input[name='complete_time']").val();
			var obj = {
				staff_name:staff_name,
				operate:operate,
				billIds:billIds,
				sonAreaId : sonAreaId,
				role_id:role_id
				//complete_time:complete_time
			};
			$('#dg_soft').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 440,
				height : 460,
				toolbar : '#tb_soft',
				url : webPath + "CableCheck/queryHandlerByNj.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20,30 ],
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '用户ID',
					checkbox : true
				},  {
					field : 'STAFF_NO',
					title : '工号',
					width : "5%",
					align : 'center'
				},  {
					field : 'STAFF_NAME',
					title : '姓名',
					width : "5%",
					align : 'center'
				},  {
					field : 'NAME',
					title : '区域',
					width : "5%",
					align : 'center'
				},  {
					field : 'ROLEID',
					title : '部门',
					width : "5%",
					align : 'center'
				}] ],
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
		function clearCondition() {
			$("input[name='staff_name']").val("");
			$("#reform_demand_ZF").val("");
		}
		function saveTask(){
			var selected = $('#dg_soft').datagrid('getChecked');
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
				var receiver = selected[0].STAFF_ID;
				var billIds = $("input[name='billIds']").val();
				var operate = $("input[name='operate']").val();
				var complete_time = $("input[name='complete_time']").val();
				var reform_demand = $("#reform_demand").val();
				var reform_demand_ZF = $("#reform_demand_ZF").val();
				
				$.ajax({
					type : 'POST',
					//url : webPath + "CableCheck/updateTask.do?operate=" + operate + "&ids=" + billIds + "&receiver=" + receiver + "&complete_time=" + complete_time+"&reform_demand="+reform_demand,
					url : webPath + "CableCheck/updateTask.do",
					dataType : 'json',
					data : {
						operate : operate,
						ids : billIds,
						receiver : receiver,
						complete_time : complete_time,
						reform_demand : reform_demand,
						reform_demand_ZF:reform_demand_ZF
					},
					success : function(json) {
						if(json.status){
							$.messager.show({
								title : '提  示',
								msg : '操作成功!',
								showType : 'show'
							});
						} else {
							$.messager.show({
								title : '提  示',
								msg : json.message,
								showType : 'show'
							});
						}
						$('#win_staff').window('close');
						searchData();
					}
				});
			}
		}
	</script>
</body>
</html>