<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
</head>

<body style="padding:3px;border:0px">
			<form id="detail_form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<input type="hidden" name="paras_equCode" value="${equCode}"/>
				<input type="hidden" name="paras_orderId" value="${orderId}"/>
			</form>
	<table id="dg" title="【问题上报】" ></table>
	<div id=win_role></div>
	<script type="text/javascript">
		$(document).ready(function() {
		searchQuestData();
		});
		function searchQuestData() {
			var equCode = $("input[name='paras_equCode']").val().trim();
			var orderId = $("input[name='paras_orderId']").val().trim();		
			var obj = {
					//taskName : taskName,
					equCode : equCode,
					orderId:orderId
					//staffNo : staffNo
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				//toolbar : '#tb',
				url : webPath + "inspect/getOrderDetail.do",
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
					field : 'equipmentId',
					title : '',
					hidden:true
				},{
					field : 'equipmentNo',
					title : '资源编码',
					width : "20%",
					align : 'center'
				}, {
					field : 'equipmentName',
					title : '资源名称',
					width : "25%",
					align : 'center'
				}, {
					field : 'address',
					title : '地址',
					width : "30%",
					align : 'center'
				},{
					field : 'button',
					title : '操作',
					width : "10%",
					align : 'center',
					formatter: function(value,row,index){
						if(row.checkState=="0"){
								return "<input type='button' class='button1 btncheck' value='填报' onclick='btncheck("+row.equipmentId+","+row.resTypeId+")'/>";
						}
						if(row.checkState=="1"){
								return "<input type='button' class='button1' value='已填报' />";
						}
					}
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}
	function btncheck(equipemtId,resTypeId){
	var orderId = $("input[name='paras_orderId']").val().trim();
		 $("#win_role").window({
            title: "【问题填报】",
            href : webPath + "inspect/oneEquTarget.do?equipmentId="+equipemtId+"&resTypeId="+resTypeId+"&orderId"+orderId,
			width : 660,
			height : 400,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: true,
            modal: true
        });
	}
	</script>
</body>
</html>