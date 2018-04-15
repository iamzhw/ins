<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>我的工单</title>
</head>
<body style="padding:3px;border:0px">
	<input type="hidden" name="admin" value="${admin}"/>
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="8%">区域：</td>
						<td width="18%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">隐患点编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="point_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">上报时间段：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=create_time id="start_time" 
								onClick="WdatePicker();" readonly="readonly"/>
							</div>-
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=end_time id="end_time" 
								onClick="WdatePicker();" readonly="readonly"/>
							</div>
						</td>
						<td width="10%">工单状态：</td>
						<td width="20%">
							<select name="bill_status" class="condition-select">
									<option value="">
										请选择
									</option>
									<c:forEach items="${statusList}" var="sl">
										<option value="${sl.STATUS_VALUE}">
											${sl.STATUS_NAME}
										</option>
									</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="10%">所属网格：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="grid_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
		<c:if test="${maintor}">
			<div class="btn-operation" onClick="doBill('receipt')">回单</div>
			<div class="btn-operation" onClick="openWindow('remarkW','请选择退单说明')">退单</div>
			
		</c:if>
		<c:if test="${admin ||auditor}">
			<div class="btn-operation" onClick="doBill('distributeBill')">派单</div>
			<div class="btn-operation" onClick="doBill('audit')">审核</div>
			<div class="btn-operation" onClick="doBill('audit_error')">审核不通过</div>
			<div class="btn-operation" onClick="doBill('forward')">转派</div>
			<div class="btn-operation" onClick="doBill('archive')">归档</div>
		</c:if>
		<c:if test="${admin && !auditor}">
			<div class="btn-operation" onClick="doBill('forward')">转派</div>
		</c:if>
			<div style="float:right;" class="btn-operation" onClick="clearCondition()">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
			<div style="float:right;" class="btn-operation"
				onClick="exportBill()">导出</div>
			<input style="float:right;" type="checkbox" name="pic"/>
			<div style="float:right;">是否导出图片</div>
		</div>
	</div>
	<table id="dg" title="【我的工单】" style="width:100%;height:480px">
	</table>
	<div id="win_staff"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<!--填写备注窗口-->
	<div id="remarkW" style="padding:20px 0 10px 50px;">
		<form id="ff" method="post">
			<table>
				<tr>
					<td width="10%">备注：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="remark" type="remark" class="condition-text" />
							</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" id="btnEp">确定</div>
			<div class="btn-operation" id="btnCancel">取消</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
		$("#remarkW").hide();
			//searchData();
			getSonAreaList();

			$('#btnEp').click(function() {
				doBill('return');
				closleWindow('remarkW');
			});
			$('#btnCancel').click(function() {
				closleWindow('remarkW');
			});
			
		});
		function getSonAreaList() {
		var areaId='${areaId}';
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
		function searchData() {
			var point_no = $("input[name='point_no']").val();
			var create_time = $("input[name='create_time']").val();
			var end_time = $("input[name='end_time']").val();
			var bill_status = $("select[name='bill_status']").val();
			var admin = $("input[name='admin']").val();
			var son_area_id = $("select[name='son_area']").val();
			var grid_name = $("input[name='grid_name']").val();
			var obj = {
				point_no : point_no,
				create_time : create_time,
				admin : admin,
				bill_status : bill_status,
				son_area_id:son_area_id,
				end_time : end_time,
				grid_name : grid_name
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Care/bill_query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				fit:true,
				columns : [ [ {
					field : 'BILL_ID',
					title : '工单ID',
					checkbox : true
				}, {
					field : 'POINT_NO',
					title : '隐患点编码',
					width : "10%",
					align : 'center'
				}, {
					field : 'POINT_NAME',
					title : '隐患点名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'STATUS_NAME',
					title : '工单状态',
					width : "8%",
					align : 'center'
				}, {
					field : 'BILL_TYPE',
					title : '工单类型',
					width : "8%",
					align : 'center'
				}, {
					field : 'DESCRP',
					title : '问题描述',
					width : "13%",
					align : 'center'
				}, {
					field : 'INSPECTOR',
					title : '巡检人员',
					width : "8%",
					align : 'center'
				}, {
					field : 'MAINTOR',
					title : '维护人员',
					width : "7%",
					align : 'center'
				}, {
					field : 'COMPLETE_TIME',
					title : '整改结束时间',
					width : "8%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
						if(value){
							var ct=new Date(value.replace(/-/g,"/"));
							var tt=new Date();
							ct.setDate(tt.getDate()+1);
                    		if (ct < tt)
                    		{
							return value+'<br><span style="color:red">(整改超时)</span>';
							} else {
							return value;
							}
                    	}else{
                    	return value;}
                    	}
				}, {
					field : 'END_TIME',
					title : '实际完成时间',
					width : "7%",
					align : 'center'
				}, {
					field : 'AUDITOR',
					title : '审核人员',
					width : "7%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区县',
					width : "6%",
					align : 'center'
				}, {
					field : 'GRID_NAME',
					title : '所属网格',
					width : "6%",
					align : 'center'
				}, {
					field : 'CREATE_TIME',
					title : '上报日期',
					width : "8%",
					align : 'center'
				}, {
					field : 'BILLID',
					title : '操作',
					width : "8%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
                    	return "<a href=\"javascript:show('info', " + value + ");\">详情</a>"
                    		+ "&nbsp;&nbsp;<a href=\"javascript:show('flow', " + value + ");\">流程</a>";  
                    } 
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickCell : onClickCell,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
					$(this).datagrid("fixRownumber");
					$("body").resize();			
				}
			});
		}
		
		function show(flag, billId){
			if(flag == 'info'){
				$('#win_staff').window({
					title : "【工单详情】",
					href : webPath + "Care/bill_info.do?billId=" + billId,
					width : 800,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			} else if(flag == 'flow'){
				$('#win_staff').window({
					title : "【流程跟踪】",
					href : webPath + "Care/bill_flow.do?billId=" + billId,
					width : 600,
					height : 300,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
		
		function showFlow(billId){
			alert(billId);
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

		function clearCondition() {
			$("input[name='point_no']").val("");
			$("input[name='create_time']").val("");
			$("select[name='bill_status']").val("");
		}
		function openWindow(id,title)
		{
			$('#'+id).window({
							title : title,
							width : 350,
							modal : true,
							shadow : true,
							closed : true,
							height : 250,
							collapsible:false,
							resizable : false,
							closed:true
						});
						$('#'+id).show();
					$('#'+id).window('open');
		}
		
		function closleWindow(id)
		{
			$('#'+id).window('close');
		}
		function doBill(operate){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择工单!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].BILL_ID;
					arr[arr.length] = value;
				}
				//remark
				$("input[name='selected']").val(arr);
				var remark = $("input[name='remark']").val();
				if(operate == 'return' || operate == 'archive' || operate == 'audit' 
						|| operate == 'receipt' || operate == 'audit_error'){
					$.ajax({
						type : 'POST',
						url : webPath + "Care/bill_handle.do?operate=" + operate + "&ids=" + arr,
						dataType : 'json',
						data:{remark:remark},
						success : function(json) {
							if(json.status){
								$.messager.show({
									title : '提  示',
									msg : '操作成功!',
									showType : 'show'
								});
								searchData();
							} else {
								$.messager.show({
									title : '提  示',
									msg : json.message,
									showType : 'show'
								});
							}
						}
					});
				} else {
					//openWindow('w','选择完成时间');
					$('#win_staff').window({
						title : "【选择人员】",
						href : webPath + "Care/bill_selectStaff.do?operate=" + operate + "&billIds=" + arr,
						width : 500,
						height : 500,
						zIndex : 2,
						region : "center",
						collapsible : false,
						cache : false,
						modal : true
					});
				}
			}
		}
		
		function exportBill() {
			var point_no = $("input[name='point_no']").val();
			point_no==""?null:point_no;
			var create_time = $("input[name='create_time']").val();
			create_time==""?null:create_time;
			var bill_status = $("select[name='bill_status']").val();
			bill_status==""?null:bill_status;
			var end_time = $("input[name='end_time']").val();
			end_time==""?null:end_time
			var admin = $("input[name='admin']").val();
			var son_area_id = $("select[name='son_area']").val();
			son_area_id==""?null:son_area_id;
			var pic =$("input[name='pic']").attr("checked");

			$("#hiddenIframe").attr("src", "/ins/Care/bill_export.do?point_no="+point_no+
										   "&create_time="+create_time+
										   "&bill_status="+bill_status+
										   "&complete_time="+end_time+
										   "&admin="+admin+
										   "&son_area_id="+son_area_id+
										   "&pic="+pic);
			/*$.ajax({
					type : 'POST',
					url : webPath + "Care/bill_export.do",
					dataType : 'json',
					data:obj,
					success : function(json) {
						$.messager.progress('close');
						$.messager.show({
									title : '提  示',
									msg : '导出成功!',
									showType : 'show'
						});
					},
					error:function(){
						$.messager.progress('close');
						$.messager.show({
									title : '提  示',
									msg : '导出失败!',
									showType : 'show'
						});
					}
			});*/
		}
	</script>
</body>
</html>