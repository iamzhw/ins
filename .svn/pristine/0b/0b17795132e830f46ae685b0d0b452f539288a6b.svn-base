<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>日常巡检</title>
</head>

<body style="padding:3px;border:0px">
		<div id="marDiv" style="display:none;width:100%;;bgColor:#f0fff0;">
			<marquee id="mar" height="20" onmouseover="stop()" onmouseout="start()" bgColor="#f0fff0" behavior="alternate" scrollAmount="2">
			</marquee>
		</div>
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container" style="height:60px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<input name="paras_staffNo" type="hidden" value="${staffNo}" id="staffNo"/>
				<table class="condition">
				<tr></tr>
					<tr>
						<td align="left" width="15%">
							任务名称：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="paras_taskCode" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							设备编码：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="paras_equCode" class="condition-text" />
							</div>
						</td>
						<td >
						<div class="btn-operation-container">
							<div class="btn-operation btnzhuan" onClick="searchData()">查询</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
	<table id="dgrc" title="【日常巡检】" style="width:100%;height:auto;"></table>
	<div id="win_rcxj"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			initMarquee();
		});
		function initMarquee(){
				var param = "";
				var tUrl = webPath+"inspect/getOrderCount.do"; 
				$.post(tUrl,function(data){
					if(typeof data=="undefined"){
						$("#marDiv").hide();
					}
					else{
						$("#marDiv").show();
						var value = "当前有"+data[0].order_list_count+"张待处理巡检工单和"+data[0].order_count+"张维护工单，请及时处理，谢谢！";
						$("#mar").html(value);
					}
				},'json');
		}
	
		function searchData() {
			var taskName = $("input[name='paras_taskCode']").val().trim();
			var equCode = $("input[name='paras_equCode']").val().trim();
			//var staffNo = $("input[name='paras_staffNo']").val().trim();		
			var staffNo = $("input[name='paras_staffNo']").val().trim();				
			var obj = {
					taskName : taskName,
					equCode : equCode,
					staffNo : staffNo
			};
			//return;
			$('#dgrc').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "inspect/query.do",
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
					field : 'ORDER_ID',
					title : '工单ID',
					align : 'center',
					hidden : true
				},{
					field : 'TASK_CODE',
					title : '任务名称',
					width : "23%",
					align : 'center'
				}, {
					field : 'EQP_NUM',
					title : '资源数量',
					width : "10%",
					align : 'center'
				}, {
					field : 'CHECK_EQP_NUM',
					title : '已填报资源数量',
					width : "10%",
					align : 'center'
				}, {
					field : 'CYCLE',
					title : '周期',
					width : "10%",
					align : 'center'
				}, {
					field : 'START_DATE',
					title : '开始时间',
					width : "15%",
					align : 'center'
					
				}, {
					field : 'END_DATE',
					title : '结束时间',
					width : "15%",
					align : 'center'
				}, {
					field : 'TASKSTATE',
					title : '状态',
					width : "10%",
					align : 'center'
				}, {
					field : 'button',
					title : '操作',
					width : "10%",
					align : 'center',
					formatter: function(value,row,index){
								return "<input type='button' class='button1 btnExport' value='导出Excel' onclick='btnExport("+row.ORDER_ID+")'/>";
					}
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				},
				onDblClickRow:dbClickRow
			});
		}
	function dbClickRow(rowIndex, rowData){
		var orderId=rowData.ORDER_ID;
		var equCode = $("input[name='paras_equCode']").val().trim();
		$("#win_rcxj").window({
            title: "【问题上报】",
            href : webPath + "inspect/orderDetail.do?orderId="+orderId+"&equCode="+equCode,
			width : 980,
			height : 300,
            zIndex: 3,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
	}	
		
	function btnExport(order_id){
		var tUrl = webPath+"inspect/exportOrderDetailExcel.do?order_id="+order_id; 
		$('#form').form('submit', {
                    url: tUrl,
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
	</script>
</body>
</html>