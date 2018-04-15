<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="../../js/jquery-1.8.0.min.js"></script>
<%@include file="../../util/head.jsp"%>
<title>坐标采集管理</title>
</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">区域：</td>
						<td width="20%">
							<select name="son_area" class="condition-select">
								<option value="">请选择 </option>
							</select>
						</td>
						<td width="10%">设备编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="equip_code" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">设备名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="equip_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">设备类型：</td>
						<td width="20%">
							<select name="equip_type" class="condition-select">
								<option value="">请选择 </option>
								<c:forEach items="${equipTypes }" var="type1">
								<option value="${type1.EQUIPMENT_TYPE_ID }">${type1.EQUIPMENT_TYPE_NAME }</option>
							</c:forEach>
							</select>
						</td>
						
					</tr>
					<tr>
					<td width="10%">审核状态：</td>
						<td width="20%">
							<select name="type" class="condition-select">
								<option value="">请选择</option>
								<option value="0">未审核</option>
								<option value="1">已审核</option>
							</select>
					</td>
					<td width="10%">上报时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=create_time onClick="WdatePicker();" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<!-- <div class="btn-operation" onClick="openInfo()">审核</div> -->
			<c:if test="${admin || !isSonAreaAdmin}">
			<div class="btn-operation" onClick="deletePoints()">删除</div>
			</c:if>
			<div class="btn-operation" onClick="exportPoints()">导出EXCEL</div>
			<div class="btn-operation" style="float:right;" onClick="clearCondition('')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【坐标采集管理】" style="width:100%;height:480px">
	</table>
	<div id="win_staff"></div>

	<script type="text/javascript">
		$(document).ready(function() {
			//searchData();
			getSonAreaList();
		});
		function searchData() {
			var equip_code = $("input[name='equip_code']").val();
			var equip_name = $("input[name='equip_name']").val();
			var equip_type = $("select[name='equip_type']").val();
			var create_time = $("input[name='create_time']").val();
			var son_area = $("select[name='son_area']").val();
			var type = $("select[name='type']").val();
			var obj = {
					equip_code : equip_code,
					equip_name : equip_name,
					equip_type : equip_type,
					create_time : create_time,
					type:type,
					son_area:son_area
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Lxxj/coordinate/query.do",
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
					field : 'RECORD_ID',
					title : '记录ID',
					checkbox : true
				}, 
				{
					field : 'AREA_NAME',
					title : '地区',
					width : "5%",
					align : 'center'
				}, {
					field : 'SON_AREA_NAME',
					title : '区域',
					width : "7%",
					align : 'center'
				},{
					field : 'EQUIP_CODE',
					title : '设备编码',
					width : "16%",
					align : 'center'
				}, {
					field : 'EQUIP_NAME',
					title : '设备名称',
					width : "16%",
					align : 'center'
				}, {
					field : 'EQUIPMENT_TYPE_NAME',
					title : '设备类型',
					width : "6%",
					align : 'center'
				}, {
					field : 'LONGITUDE',
					title : '经度',
					width : "8%",
					align : 'center'
				}, {
					field : 'LATITUDE',
					title : '纬度',
					width : "8%",
					align : 'center'
				}, {
					field : 'POINT_LEVEL',
					title : '维护等级',
					width : "6%",
					align : 'center'
				},{
					field : 'INSPECTOR',
					title : '上报人员',
					width : "6%",
					align : 'center'
				}, {
					field : 'CREATE_TIME',
					title : '上报时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'TYPENAME',
					title : '审核状态',
					width : "5%",
					align : 'center'
				} 
				, {
					field : 'COMMITNAME',
					title : '审核人',
					width : "6%",
					align : 'center'
				} 
				, {
					field : 'COMMIT_DATE',
					title : '审核时间',
					width : "8%",
					align : 'center'
				} , 
				{
		   			field : 'operation',
					title : '操作',
		   			formatter: function(value,row,index){
					
						return '<a href ="javascript:open('+row.RECORD_ID+')">审核</a>';
					
					}
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
			$("input[name='equip_code']").val("");
			$("input[name='equip_name']").val("");
			$("select[name='equip_type']").val("");
			$("input[name='create_time']").val("");
			$("select[name='son_area']").val("");
			$("select[name='type']").val("");
		}
		
		function open(recordIds)
		{
			addTab("审核详情", webPath + "Lxxj/coordinate/getDetail.do?recordIds=" + recordIds);
		}
		function openInfo(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择需要审核的隐患点!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].RECORD_ID;
					arr[arr.length] = value;
				}
				$("input[name='selected']").val(arr);
				location.href = webPath + "Lxxj/coordinate/getDetail.do?recordIds=" + arr;
			}
		}
		function deletePoints(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择坐标点!',
					showType : 'show'
				});
				return;
			} 
			else 
			{
				var admin ='${admin}';
				var arr = new Array();
				var commitedArr = new Array();
				for ( var i = 0; i < selected.length; i++) 
				{
					var value = selected[i].RECORD_ID;
					if (undefined !=admin && admin!='true'){
						var commitVal=selected[i].TYPE;
						if(commitVal=='1')
						{
							$.messager.show({
							title : '提  示',
							msg : '所选坐标包含已审核的坐标点!',
							showType : 'show'});
							return;
						}
					}
					arr[arr.length] = value;
				}
				$.messager.confirm('确认','删除采集坐标点后，会删除与此点相关的所有信息（包括：已审核的关键点、所创建的计划和任务）。您确认想要删除所选记录吗？',function(r)
				{    
   					 if (r)
   					 {    
   					 	$("input[name='selected']").val(arr);
        				$.ajax({
							type : 'POST',
							data : {},
							url : webPath + "Lxxj/coordinate/deletePoints.do?recordIds=" + arr,
							dataType : 'json',
							success : function(data) 
							{  
								$.messager.show({
								title : '提  示',
								msg : '删除成功',
								showType : 'show'});
								searchData();  
    				        }
				       });  
			  		}
				});
			}
		}
		function exportPoints()
		{
			var equip_code = $("input[name='equip_code']").val();
			var equip_name = $("input[name='equip_name']").val();
			var equip_type = $("select[name='equip_type']").val();
			var create_time = $("input[name='create_time']").val();
			var type = $("select[name='type']").val();
			var son_area = $("input[name='son_area']").val();
			var obj = {
					equip_code : equip_code,
					equip_name : equip_name,
					equip_type : equip_type,
					create_time : create_time,
					type:type,
					son_area:son_area
			};
			$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            	if (r) {
                
                	$('#form').form('submit', {
                    url: webPath + "Lxxj/coordinate/exportPoints.do",
                    queryParams : obj,
                    onSubmit: function () {
                      //  $.messager.progress();
                    },
                    success: function () {
                      //  $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '导出成功!',
                            showType: 'show'
                        });
                        
                    }
                });
            }
        });
        
		}
		
		function getSonAreaList() {
		var areaId='${areaId}';
		if(null==areaId||""==areaId)
		{
			$("select[name='son_area'] option").remove();
			return;
		}
		$.ajax({
			type : 'POST',
			url : webPath + "General/getSonAreaList.do",
			data : {
				areaId : areaId
			},
			dataType : 'json',
			success : function(json) {
				var result = json.sonAreaList;
				$("select[name='son_area'] option").remove();
				var admin ='${admin}';
				if (undefined !=admin && admin!='true'){
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
				else{
					$("select[name='son_area']").append(
							"<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
				}
			}
		});
	}
	</script>
</body>
</html>