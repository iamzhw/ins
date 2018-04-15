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
	<input name="type"  type="hidden"/>
	<input type="hidden" name="inspact_id_a" id="inspact_id_a" />
	<input type="hidden" name="inspact_id_b" id="inspact_id_b" />
	<div id="tb_soft" style="padding: 5px; height: auto">
		<div class="condition-container" style="height: auto;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
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

					
					<tr>
						<td >维护员姓名：</td>
						<td><div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="inspact_name_a" id="inspact_name_a"
									onClick="getInspactPerson(0)"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			
			<div class="btn-operation" onClick="saveTask()" style="float: right;">派发</div>
		</div>

	</div>
	
		<div id="tb_plan" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="20%">
								帐号：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="staff_no" type="text" class="condition-text" />
								</div>
							</td>
							<td width="20%"	>
								姓名：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="staff_name" type="text" class="condition-text" />
								</div>
							</td>
							
						</tr>
						<tr>
						<td width="20%"	>
								角色：
							</td>
							<td width="20%">
								<select name="roleid" class="condition-select">
									<option value="369">
										综维
									</option>
									<option value="370">
										装维
									</option>
									<option value="387">
										网维
									</option>
									<option value="393">
										工程
									</option>
									<option value="392">
										无线
									</option>
									<option value="395">
										政支
									</option>
									
									
									<!-- <option value="8">
										已归档
									</option> -->
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="submitStaff()">
					确定
				</div>
			</div>
		</div>
		<table id="dg_plan" title="【选择员工】" style="width: 100%; height: 480px">
		</table>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function saveTask(){
			
				var receiver = $("input[name='inspact_id_a']").val();
				var receiver_b = $("input[name='inspact_id_b']").val();
				var billIds = $("input[name='billIds']").val();
				var operate = $("input[name='operate']").val();
				var complete_time = $("input[name='complete_time']").val();
				var reform_demand = $("#reform_demand").val();
				$.ajax({
					type : 'POST',
					//url : webPath + "CableCheck/updateTask.do?operate=" + operate + "&ids=" + billIds + "&receiver=" + receiver + "&complete_time=" + complete_time+"&reform_demand="+reform_demand,
					url : webPath + "CableCheck/updateTask.do",
					dataType : 'json',
					data : {
						operate : operate,
						ids : billIds,
						receiver : receiver,
						receiver_b : receiver_b,
						complete_time : complete_time,
						reform_demand : reform_demand
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
		
		
		function getInspactPerson(type) {
			$('#win_SHY').window(
			{  
				title : "【选择人员】",
				href : webPath + "CableCheck/getInspactPersonIndex.do?type="+type,
				width : 450,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
			$("input[name='type']").val(type);
			var staff_name = $("input[name='staff_name']").val().trim();
			var staff_no = $("input[name='staff_no']").val().trim();
			var role_id = $("select[name='roleid']").val();
			var operate = $("input[name='operate']").val();
			//alert(staff_name+":"+staff_no);
			var obj = {
				staff_name : staff_name,
				staff_no : staff_no,
				type:type,
				role_id:role_id,
				operate:operate
			};
			//return;
			$('#dg_plan').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 450,
				height : 380,
				toolbar : '#tb_plan',
				url : webPath + "CableCheck/queryHandlerByNj.do",
				queryParams : obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '员工ID',
					checkbox : true
				}, {
					field : 'STAFF_NO',
					title : '员工帐号',
					width : "6%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '员工名称',
					width : "6%",
					align : 'center'
				}, {
					field : 'ROLEID',
					title : '角色',
					width : "8%",
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
		
			
function submitStaff() {
    var selected = $('#dg_plan').datagrid('getChecked');
    var count = selected.length;
    var type = $("input[name='type']").val();
   
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取查询员工!',
            showType: 'show'
        });
        return;
    } else if (type == 0 ){
                
                $("input[name='inspact_name_a']").val(selected[0].STAFF_NAME);
                 $("input[name='inspact_id_a']").val(selected[0].STAFF_ID);
                
    }else{
             $("input[name='inspact_name_b']").val(selected[0].STAFF_NAME);
              $("input[name='inspact_id_b']").val(selected[0].STAFF_ID);
    }
}
		
		
		
	</script>
</body>
</html>