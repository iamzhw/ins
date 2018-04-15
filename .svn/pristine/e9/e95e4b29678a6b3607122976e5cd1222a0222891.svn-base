
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title></title>
	</head>
	<body style="padding: 3px; border: 0px">
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<input type="hidden" name="area_id" value="${area_id}" />
					<table class="condition">
					<tr>
						<td width="10%">
								光缆段：
							</td>
							<td width="20%">
								<div>
									<select name="p_cable_id" id="p_cable_id"
										class="condition-select" onchange="getRelay(this.value)">
										<option value=''>
											--请选择--
										</option>
										<c:forEach items="${cableList}" var="res">
											<option value='${res.CABLE_ID}'>
												${res.CABLE_NAME}
											</option>
										</c:forEach>
									</select>
								</div>
							</td>
							<td width="10%">
								中继段：
							</td>
							<td width="20%">
								<div>
									<select name="p_relay_id" id="p_relay_id"
										class="condition-select">
									</select>
								</div>
							</td>
						<td width="10%">
							设施类型：
						</td>
						<td width="20%">
							<div>
								<select name=equip_type id="equip_type" class="condition-select">
									<option value="">
										--请选择--
									</option>
									<option value="1">
										标石
									</option>
									<option value="3">
										地标
									</option>
									<option value="4">
										宣传牌
									</option>
								</select>
							</div>
						</td>
						</tr>
						<tr>
						<td width="10%">设施点采集人：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">是否加入统计：</td>
						<td width="20%">
							<select name=remark id="remark" class="condition-select">
								<option value="">--请选择--</option>
								<option value="1">否</option>
								<option value="0">是</option>
							</select>
						</td>
						</tr>
						
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="equipDiatanceDelete()">删除</div>
				<div class="btn-operation" onClick="equipDiatanceCancel()">撤回</div>
				<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
			</div>
		</div>
		<table id="dg" title="【】" style="width: 100%" class="common-table">
			<thead id="tsh">

			</thead>
			<tbody id="tsb">
			</tbody>
		</table>
		<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
		<script type="text/javascript">
	$(document).ready(function() {
		//searchData();
		});
	function searchData() {
	
					if($("#equip_type").val()==''){
						alert("设施类型不能为空");
						return false;
					}
				
		var obj = makeParamJson('form');
		//return;
		$('#dg').datagrid( {
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			fitColumns : true,
			toolbar : '#tb',
			url : webPath + "xxdReportController/getDetailUI.do",
			queryParams : obj,
			method : 'post',
			rownumbers : true,
			singleSelect : false,
			columns : [ [{
				field : 'START_EQUIP_ID',
				title : '开始设施编号ID',
				checkbox : true
				}, {
				field : 'START_EQUIP_CODE',
				title : '开始设施编号',
				width : 80,
				align : 'center'
			},{
				field : 'START_CREATOR_NAME',
				title : '开始设施采集人',
				width : 80,
				align : 'center'
			},{
				field : 'END_EQUIP_CODE',
				title : '结束设施编号',
				width : 80,
				align : 'center'
			},{
				field : 'END_CREATOR_NAME',
				title : '结束设施采集人',
				width : 80,
				align : 'center'
			},{	
				field : 'DISTANCES',
				title : '设施间距离',
				width : 80,
				align : 'center'
				
			},{
				field : 'REMARK',
				title : '间距是否加入统计',
				width : 80,
				align : 'center',
				formatter : function(value, rowData, index){
					if(value == "0"){
						return "加入";
					}else {
						return "不加入";
					}
				}
			},{
				field : 'CABLE_NAME',
				title : '光缆名称',
				width : 80,
				align : 'center'
			},{
				field : 'RELAY_NAME',
				title : '中继断名称',
				width : 80,
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
       			 $(this).datagrid("fixRownumber");
				
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
		
	function equipDiatanceDelete(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		//var select =selected[0].REMARK;
		if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取不要统计的距离的数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
				} else if (count) {
					var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].REMARK;
							if(value==1){
							$.messager.show({
								title : '提  示',
								msg : '选取的数据包含已经不统计的距离的数据!',
								showType : 'show',
								timeout : '1000'//ms
							});
							return;
							}
						}
			
			
			} 
				$.messager.confirm('系统提示', '您确定要不统计设施间距离吗?', function(r) {
					if (r) {
							var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].START_EQUIP_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "xxdReportController/equipDiatanceDelete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '这段设施间的距离不统计!',
									showType : 'show'
								});
							}
						});
						
					}
				});
			
	}
	
		
	function equipDiatanceCancel(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取撤回不加入统计的距离的数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
				} 
				$.messager.confirm('系统提示', '您确定要撤回不加入统计设施间距离吗?', function(r) {
					if (r) {
							var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].START_EQUIP_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "xxdReportController/equipDiatanceCancel.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '这段设施间的距离重新加入统计!',
									showType : 'show'
								});
							}
						});
						
					}
				});
			
	}
		
		
		
	function getRelay(cable_id){
		    var area_id=$("input[name='area_id']").val();
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "lineInfoController/getRelayById.do",
				  data:{cable_id:cable_id,area_id:area_id},
				  dataType:"json",
				  success:function(data){
					  $("#p_relay_id").empty();
					  $("#p_relay_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.relayList,function(i,item){
						  $("#p_relay_id").append("<option value='"+item.RELAY_ID+"'>"+item.RELAY_NAME+"</option>");		
					  });
				  }
			  });
		}
		
	
	
	
	
</script>

	</body>
</html>

