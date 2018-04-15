<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>步巡段管理</title>
</head>

<body style="padding: 3px; border: 0px">
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<c:if test="${isAdmin==0}">
						  <td width="10%">地区：</td>
							<td width="20%">
								<div>
									<select name="p_area_id" id="p_area_id" class="condition-select" onchange="SelCableByAreaID(this.value)" >
										<option value=''>--请选择--</option>
										<c:forEach items="${areaList}" var="res">
											<option value='${res.AREA_ID}'>${res.NAME}</option>
										</c:forEach>
									</select>
								</div>
							</td>
						</c:if>
						
						<td width="10%">光缆段：</td>
						<td width="20%">
							<div>
								<select name="p_cable_id" id="p_cable_id" class="condition-select" onchange="getRelay(this.value)">
									<option value=''>--请选择--</option>
									<c:forEach items="${cableList}" var="res">
										<option value='${res.CABLE_ID}'>${res.CABLE_NAME}</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<td width="10%">中继段：</td>
						<td width="20%">
							<div>
								<select name="p_relay_id" id="p_relay_id" class="condition-select">
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">步巡段名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="steppart_name" id="steppart_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">巡线人：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input name="p_inspect_name" id="p_inspect_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<c:if test="${isAdmin==1}">
				<div class="btn-operation" onClick="addStepPart()">增加</div>
				<div class="btn-operation" onClick="editStepPart()">编辑</div>
				<div class="btn-operation" onClick="delStepPart()">删除</div>
			</c:if>
			<div style="float: right;" class="btn-operation" onClick="insertStepEquip()">设施点插入</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【步巡段管理】" style="width: 100%; height: 480px">
	</table>
	<div id="win_part"></div>
	<div id="win_distribute"></div>
	<script type="text/javascript">
		$(document).ready(function() {
// 			searchData();
		});
		
		function searchData() {
			
			if('${isAdmin}'=='0'){
				if($("#p_area_id").val()==''){
					$.messager.alert("提示","地区不能为空");
					return false;
				}
			}
			var obj=makeParamJson('form');
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "StepPart/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : true,
				//
				columns : [ [ {
					field : 'ALLOT_ID',
					title : '步巡段ID',
					checkbox : true
				}, {
					field : 'STEPPART_NAME',
					title : '步巡段名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'START_EQUIP',
					title : '起始设施点',
					width : "10%",
					align : 'center'
				}, {
					field : 'END_EQUIP',
					title : '结束设施点',
					width : "10%",
					align : 'center'
				}, {
					field : 'INSPECT_NAME',
					title : '巡检人员',
					width : "10%",
					align : 'center'
				},{
					field : 'CIRCLE_ID',
					title : '周期',
					width : "6%",
					align : 'center'
				},{
					field : 'CREATOR_NAME',
					title : '创建者',
					width : "10%",
					align : 'center'
				},{
					field : 'CREATE_TIME',
					title : '创建时间',
					width : "10%",
					align : 'center'
				},{
					field : 'CABLE_NAME',
					title : '所属光缆',
					width : "14%",
					align : 'center'
				},{
					field : 'RELAY_NAME',
					title : '所属中继段',
					width : "14%",
					align : 'center'
				},{
					field : 'AREA_NAME',
					title : '城市',
					width : "6%",
					align : 'center'
				}	
// 				,{
// 					field : 'OPERATION',title : '操作',width : "6%",align : 'center',
// 					formatter: function(value,row,index){
// 					   if(row.IS_TASK!= undefined && row.IS_TASK!= ""){
// 						return "<font color='red'>已生成任务</font>";
// 					   }else{
// 					   	return "<a href='javascript:void(0)' onclick='showDetail("+row.ALLOT_ID+","+$("select[name='p_area_id']").val()+")'>生成任务</a>"
// 					   }
// 					}
// 				}
				] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
// 				onClickRow:onClickRow,
// 				onCheck:onCheck,
// 				onSelect:onSelect,
// 				onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
       			// $(this).datagrid("fixRownumber");
       			 
				
				}
				
			});
			
			if('${isAdmin}'=='0'){
				 $('#dg').datagrid('hideColumn','OPERATION');
			}
		}
		
// 		function showDetail(allot_id,area_id){
// 			$.messager.confirm('系统提示', '您确定要生产任务吗?', function(r) {
// 					if (r) {
// 						$.ajax({
// 								type : 'POST',
// 								url : webPath + "StepPart/createTask.do",
// 								data : {
// 									allot_id 		: 	allot_id,
// 									area_id   		:   area_id
// 								},
// 								dataType : 'json',
// 								success : function(data) {
// 								  if(data.status){
// 									$.messager.alert("提示","生成成功");
// 									searchData();
// 								  }else{
// 									$.messager.alert("提示","生成失敗");
// 								  }	 
// 								}
// 						})
// 				   }
// 			    });
			
// 		}
		
		
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
			
			$("#"+form_id+"").form('reset', 'none');
			
		}
		
		function delStepPart(){
		 var selected = $('#dg').datagrid('getChecked');
// 		console.dir(selected);
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			}else {
				var allot_id = selected[0].ALLOT_ID;
				$.messager.confirm('系统提示', '您确定要删除该步巡段吗?', function(r) {
					if (r) {
						$.ajax({
								type : 'POST',
								url : webPath + "StepPart/delStepPart.do",
								data : {
									allot_id 		: 	allot_id
								},
								dataType : 'json',
								success : function(data) {
								  if(data.status){
									$.messager.alert("提示","删除成功");
									searchData();
								  }else{
									$.messager.alert("提示","删除失敗");
								  }	 
								}
							})
					
				   }
			    });
			}
		
		
		}
		
		
		function editStepPart(){
		var selected = $('#dg').datagrid('getChecked');
// 		console.dir(selected);
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			}else {
				var ALLOT_ID = selected[0].ALLOT_ID;
				location.href = webPath +"StepPart/toUpdate.do?ALLOT_ID="+ALLOT_ID;
			}
		
		
		}
		
		
		function addStepPart() {
				$('#win_part').window({
					title : "【新增步巡段】",
					href : webPath + "StepPart/SelCableAndRelay.do",
					width : 400,
					height : 300,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
		}
		
		function selByCableRelay(){
		  var information=makeParamJson('formsel');
		  var cable_id =information.cable_id;
		  var relay_id=information.relay_id;
		  if( relay_id!= undefined &&  relay_id != ""){
	        location.href="addStepPart.do?relay_id="+relay_id+"&cable_id="+cable_id;	  
		  }else{    
		     $.messager.alert("提示","请选择具体段落");
		  } 
		}
		
		var areaid='';
		function SelCableByAreaID(area_id){
			areaid=area_id;
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "StepPart/getGldByAreaId.do",
				  data:{area_id:area_id},
				  dataType:"json",
				  success:function(data){
					  $("#p_cable_id").empty();
					  $("#p_cable_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.cableList,function(i,item){
						  $("#p_cable_id").append("<option value='"+item.CABLE_ID+"'>"+item.CABLE_NAME+"</option>");		
					  });
				  }
			  });
		}
		
		function getRelay(cable_id){
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "StepPart/getRelay.do",
				  data:{cable_id:cable_id,area_id:areaid},
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
		
		//设施点插入
		function insertStepEquip(){
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
			}
			var cable_id = selected[0].CABLE_ID;
			var relay_id = selected[0].RELAY_ID;
			var allot_id = selected[0].ALLOT_ID;
			$('#win_distribute').window({
				title : "【设施点列表】",
				href : webPath + "StepPart/addStepEquip.do?cableId="+cable_id+"&relayId="+relay_id+"&allotId="+allot_id,
				width : 820,
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

