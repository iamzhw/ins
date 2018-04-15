<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>我的工单</title>
</head>

<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container" style="height:140px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<input id="count1" type="hidden" name="count1" value="${count1 }"/>
				<input id="count2" type="hidden"  name ="count2" value="${count2 }"/>
				<input id="count2" type="hidden" name="area" value="${area}"/>
				<table class="condition">
					<tr>
						<td align="left" width="15%">
							OrderID：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="orderNo" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							设备类型：
						</td>
						 <td>
							<select name="resTypeId"  class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="703">光交接箱</option>
								<option value="411">ODF</option>
							</select>
						</td> 
						<td align="left" width="15%">
							检查项：
						</td>
						<td width="25%" align="left">
							<select name="targetId" class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${targetList}" var="v">
								<option value="${v.TARGET_ID}">${v.TARGET_NAME}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							设备编码：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="order_equ" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							状态：
						</td>
						 <td>
							<select name="stateId"  class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="0">未派发</option>
								<option value="1">待回单</option>
								<option value="2">待审核</option>
								<option value="3">归档</option>
								<option value="4">已退单</option>
							</select>
						</td> 
						<td align="left" width="15%">
							超时工单状态：
						</td>
						<td width="25%" align="left">
							<select name="overTime" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="(0,4)">未派发</option>
								<option value="(0,1,4)">未回单</option>
								<option value="(0,1,2,4)">未闭环</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							巡检公司：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="ownCompany" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							区域：
						</td>
						 <td>
							<select name="areaId"  class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${areaList}" var="v">
								<option value="${v.NAME}">${v.NAME}</option>
								</c:forEach>
							</select>
						</td> 
						<td align="left" width="15%">
							超时时间：
						</td>
						<td width="25%" align="left">
							<select name="overDay" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="2">1天</option>
								<option value="3">2天</option>
								<option value="4">3天</option>
								<option value="5">4天</option>
								<option value="6">5天</option>
							</select>
						</td>
					</tr>
					<tr id="overDayState">
						<td align="left" width="15%">
							审核人工号：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="checkStaffNo" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							超时转派工单：
						</td>
						 <td>
							<select name="overDayState"  class="condition-select">
									<option value="">----------请选择----------</option>
									<option value="1=1">全部</option>
									<option value="t.state=3">已完成</option>
									<option value="t.state!=3">未完成</option>
							</select>
						</td> 
						<td align="left" width="15%">
							上报方式：
						</td>
						<td width="25%" align="left">
							<select name="sn" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="PC">PC</option>
								<option value="PAD">PAD</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							维护人员：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="repairer" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							问题描述：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="remarks" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation btnzhuan" onClick="toAllot()">派单</div>
			<div class="btn-operation btnZPai" onClick="toReassignment()">转发</div>
			<div class="btn-operation btnhuidan" onClick="toHuidan()">回单</div>
			<div class="btn-operation btncheck" onClick="toCheck()">审核</div>
			<div class="btn-operation btnorderend" onClick="toArchive()">归档</div>
			<div class="btn-operation btntuidan" onClick="toChargeback()">退单</div>
			<div class="btn-operation btninfo" onClick="toShowDetail()">流程详情</div>
			<div class="btn-operation btnPLZPai" onClick="completeBtnReassign()">批量转派</div>
			<div class="btn-operation btnExport" onClick="exportOrder()">导出Excel</div>
			<div style="float:right;" class="btn-operation btnquery" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【我的工单】" style="width:100%;height:480px"></table>
	<div id="win_trouble"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			var count1 = document.getElementById("count1").value;
			var count2 = document.getElementById("count2").value;
			if(count1==0&&count2==1){
				$(".btnzhuan").hide();
				$(".btnZPai").hide();
				$(".btncheck").hide();
				$(".btnorderend").hide();
				$(".btnPLZPai").hide();
				$("#overDayState").hide();
			}else if(count1==1&&count2==0){
				$(".btnhuidan").hide();
				$(".btntuidan").hide();
				$(".btnPLZPai").hide();
				$("#overDayState").hide();
			} else if(count1==0&&count2==0){
				$(".btnzhuan").hide();
				$(".btnZPai").hide();
				$(".btncheck").hide();
				$(".btnhuidan").hide();
				$(".btntuidan").hide();
				$(".btnorderend").hide();
			} else if(count1==1&&count2==1){
				$(".btnPLZPai").hide();
			}
		});
	
		function searchData() {
			var orderNo = $("input[name='orderNo']").val();
			var resTypeId = $("select[name='resTypeId']").val();
			var targetId = $("select[name='targetId']").val();			
			var order_equ = $("input[name='order_equ']").val();
			var stateId = $("select[name='stateId']").val();
			var overTime = $("select[name='overTime']").val();
			var ownCompany = $("input[name='ownCompany']").val();
			var areaId = $("select[name='areaId']").val();
			var overDay = $("select[name='overDay']").val();
			var checkStaffNo = $("input[name='checkStaffNo']").val();
			var overDayState = $("select[name='overDayState']").val();
			var sn = $("select[name='sn']").val();
			var repairer = $("input[name='repairer']").val();
			var remarks = $("input[name='remarks']").val();
			var area = $("input[name='area']").val();
			
			var obj = {
					orderNo : orderNo,
					resTypeId : resTypeId,
					targetId : targetId,
					order_equ : order_equ,
					stateId : stateId,
					overTime : overTime,
					ownCompany : ownCompany,
					areaId : areaId,
					overDay : overDay,
					checkStaffNo : checkStaffNo,
					overDayState : overDayState,
					sn : sn,
					repairer : repairer,
					remarks : remarks,
					area : area
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "insOrderTask/query.do",
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
					field : 'TROUBLE_ID',
					title : '工单ID',
					checkbox : true
				},{
					field : 'ORDER_ID',
					title : 'OrderID',
					width : "5%",
					align : 'center'
				}, {
					field : 'TASK_CODE',
					title : '任务编码',
					width : "8%",
					align : 'center'
				}, {
					field : 'TRSTATE',
					title : '状态',
					width : "5%",
					align : 'center'
				}, {
					field : 'RES_TYPE',
					title : '资源类别',
					width : "6%",
					align : 'center'
				}, {
					field : 'EQUIPMENT_CODE',
					title : '设备编码',
					width : "8%",
					align : 'center',			
					styler:function(value,rowData,rowIndex){
						return 'word-break:break-all;word-wrap:break-word';
					}
					
				}, {
					field : 'EQUIPMENT_NAME',
					title : '设备名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区域',
					width : "8%",
					align : 'center'
				}, {
					field : 'UPLOAD_STAFF_NAME',
					title : '巡检人员',
					width : "6%",
					align : 'center'
				}, {
					field : 'INS_COMPANY',
					title : '巡检公司',
					width : "6%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '维护人员',
					width : "6%",
					align : 'center'
				}, {
					field : 'CHECK_STAFF_NAME',
					title : '审核人员',
					width : "6%",
					align : 'center'
				}, {
					field : 'TARGET_NAME',
					title : '检查项',
					width : "6%",
					align : 'center'
				}, {
					field : 'REMARKS',
					title : '问题描述',
					width : "6%",
					align : 'center'
				}, {
					field : 'SN',
					title : '上报方式',
					width : "6%",
					align : 'center'
				}, {
					field : 'HAPPEN_DATE',
					title : '上报日期',
					width : "8%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				},
				onDblClickRow : function(rowIndex, rowData){
					$('#win_trouble').window({
						title : "【工单详情】",
						href : webPath + "insOrderTask/toOrderDetail.do?selected=" + rowData.TROUBLE_ID,
						width : 800,
						height : 500,
						zIndex : 2,
						region : "center",
						collapsible : false,
						cache : false,
						modal : true
					});
				}
			});
		}
		
		function toAllot(){
			var selected = $("#dg").datagrid("getChecked");
			var count = selected.length;
			if(count==0){
				$.messager.show({
					title : '提  示',
					msg : '请选择要派发的工单!',
					showType : 'show'
				});
				return;
			}
			else{
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].TROUBLE_ID;
					var state = selected[i].TRSTATE;
					if(state!="未派发" && state!="已退单"){
						alert("选择的工单应该是未派发，请重新选择!");
						return;
					}
					arr[arr.length] = value;
				}
				$("input[name='selected']").val(arr);
				
				$('#win_trouble').window({
					title : "【工单派发】",
					href : webPath + "insOrderTask/toAllot.do",
					width : 800,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
		
		function toHuidan(){
			var selected = $("#dg").datagrid("getChecked");
			var count = selected.length;
			if(count==0){
				$.messager.show({
					title : '提  示',
					msg : '请选择要一个工单!',
					showType : 'show'
				});
				return;
			}
			else if(count > 1){
				$.messager.show({
					title : '提  示',
					msg : '一次只能选择一个工单！!',
					showType : 'show'
				});
				return;
			}
			else{
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].TROUBLE_ID;
					var state = selected[i].TRSTATE;
					if(state!="待回单"){
						alert("选择的工单应该是待回单，请重新选择!");
						return;
					}
					arr[arr.length] = value;
				}
				$("input[name='selected']").val(arr);
				
				$('#win_trouble').window({
					title : "【工单派发】",
					href : webPath + "insOrderTask/toHuidan.do",
					width : 800,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
		
	function toCheck(){
			var selected = $("#dg").datagrid("getChecked");
			var count = selected.length;
			if(count==0){
				$.messager.show({
					title : '提  示',
					msg : '请选择要审核的工单!',
					showType : 'show'
				});
				return;
			}
			else{
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].TROUBLE_ID;
					var state = selected[i].TRSTATE;
					if(state!="待审核"){
						alert("选择的工单应该是待审核，请重新选择!");
						return;
					}
					arr[arr.length] = value;
				}
				$("input[name='selected']").val(arr);
				var selected = $("input[name='selected']").val();
				
				$('#win_trouble').window({
					title : "【工单审核】",
					href : webPath + "insOrderTask/toCheck.do?selected=" + selected,
					width : 800,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
	
	function toArchive(){
		var selected = $("#dg").datagrid("getChecked");
		var count = selected.length;
		if(count==0){
			$.messager.show({
				title : '提  示',
				msg : '请选择要一个工单!',
				showType : 'show'
			});
			return;
		}
		else if(count > 1){
			$.messager.show({
				title : '提  示',
				msg : '一次只能选择一个工单！!',
				showType : 'show'
			});
			return;
		}
		else{
			if(window.confirm("提示：请务必确认问题已经全部解决了，一旦归档将不能回退！")){
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].TROUBLE_ID;
					arr[arr.length] = value;
				}
				$("input[name='selected']").val(arr);
				
				$('#win_trouble').window({
					title : "【工单归档】",
					href : webPath + "insOrderTask/toArchive.do",
					width : 800,
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
	
	function toChargeback(){
		var selected = $("#dg").datagrid("getChecked");
		var count = selected.length;
		if(count==0){
			$.messager.show({
				title : '提  示',
				msg : '请选择要一个工单!',
				showType : 'show'
			});
			return;
		}
		else if(count > 1){
			$.messager.show({
				title : '提  示',
				msg : '一次只能选择一个工单！!',
				showType : 'show'
			});
			return;
		}
		else{
			if(window.confirm("提示：确定将此工单退单吗？")){
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].TROUBLE_ID;
					var state = selected[i].TRSTATE;
					if(state!="待回单"){
						alert("选择的退单工单应该是待回单，请重新选择!");
						return;
					}
					
					arr[arr.length] = value;
				}
				$("input[name='selected']").val(arr);
				
				$('#win_trouble').window({
					title : "【工单退单】",
					href : webPath + "insOrderTask/toChargeback.do",
					width : 800,
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
	
	function toReassignment(){
		var selected = $("#dg").datagrid("getChecked");
		var count = selected.length;
		if(count==0){
			$.messager.show({
				title : '提  示',
				msg : '请选择要转派的工单!',
				showType : 'show'
			});
			return;
		}
		else{
			var arr = new Array();
			for ( var i = 0; i < selected.length; i++) {
				var value = selected[i].TROUBLE_ID;
				var state = selected[i].TRSTATE;
				if(state!="未派发" && state!="已退单"){
					alert("选择的工单应该是未派发，请重新选择!");
					return;
				}
				arr[arr.length] = value;
			}
			$("input[name='selected']").val(arr);
			
			$('#win_trouble').window({
				title : "【工单转派】",
				href : webPath + "insOrderTask/toReassignment.do",
				width : 800,
				height : 500,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
	}
	
	function completeBtnReassign(){
		var selected = $("#dg").datagrid("getChecked");
		var count = selected.length;
		if(count==0){
			$.messager.show({
				title : '提  示',
				msg : '请选择要转派的工单!',
				showType : 'show'
			});
			return;
		}
		else{
			$.messager.confirm('系统提示', '此操作将会将超时工单重新转派人员处理！', function(r) {
				if (r) {
					var arr = new Array();
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].TROUBLE_ID;
						arr[arr.length] = value;
					}
					$("input[name='selected']").val(arr);
					
					$('#form').form('submit', {
						url : webPath + "insOrderTask/completeBtnReassign.do",
						onSubmit : function() {
							$.messager.progress();
						},
						success : function() {
							$.messager.progress('close'); // 如果提交成功则隐藏进度条
							searchData();
							$.messager.show({
								title : '提  示',
								msg : '超时工单转派成功!',
								showType : 'show'
							});
						}
					});
				}
			});
		}
	}
	
	
	function toShowDetail(){
		var selected = $("#dg").datagrid("getChecked");
		var count = selected.length;
		if(count==0){
			$.messager.show({
				title : '提  示',
				msg : '请选择要一个工单!',
				showType : 'show'
			});
			return;
		}
		else if(count > 1){
			$.messager.show({
				title : '提  示',
				msg : '一次只能选择一个工单！!',
				showType : 'show'
			});
			return;
		}
		else{
				var trouble_id = selected[0].TROUBLE_ID;
				$('#win_trouble').window({
					title : "【流程详情】",
					href : webPath + "insOrderTask/toShowDetail.do?troubleId=" + trouble_id,
					width : 800,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
		}
	}
	
	function exportOrder() {
		var orderNo = $("input[name='orderNo']").val();
		var resTypeId = $("select[name='resTypeId']").val();
		var targetId = $("select[name='targetId']").val();			
		var order_equ = $("input[name='order_equ']").val();
		var stateId = $("select[name='stateId']").val();
		var overTime = $("select[name='overTime']").val();
		var ownCompany = $("input[name='ownCompany']").val();
		var areaId = $("select[name='areaId']").val();
		var overDay = $("select[name='overDay']").val();
		var checkStaffNo = $("input[name='checkStaffNo']").val();
		var overDayState = $("select[name='overDayState']").val();
		var sn = $("select[name='sn']").val();
		var repairer = $("input[name='repairer']").val();
		var remarks = $("input[name='remarks']").val();
		
		var obj = {
				orderNo : orderNo,
				resTypeId : resTypeId,
				targetId : targetId,
				order_equ : order_equ,
				stateId : stateId,
				overTime : overTime,
				ownCompany : ownCompany,
				areaId : areaId,
				overDay : overDay,
				checkStaffNo : checkStaffNo,
				overDayState : overDayState,
				sn : sn,
				repairer : repairer,
				remarks : remarks
		};
		
		$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            if (r) {
                
                $('#form').form('submit', {
                    url: webPath + "insOrderTask/export.do",
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
		
	</script>
</body>
</html>