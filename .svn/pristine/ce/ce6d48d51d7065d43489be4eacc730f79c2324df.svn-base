	<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>分公司使用情况</title>
<style>
body{
	overflow: auto;
	width: 98%;
}
.panel-fit, .panel-fit body {
    height: 100%;
    margin: 0;
    padding: 0;
    border: 0;
    overflow: auto;
}
</style>
</head>
<body style="padding: 3px; border: 0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr>
						<td width="10%">区域：</td>
						<td width="30%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
									<c:forEach items="${areaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
						</td>
						<td width="10%">选择月份：</td>
						<td width="20%">
						<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=begin_time id="begin_time" 
								onClick="WdatePicker({dateFmt:'yyyy-MM'});" />
							</div>
						</td>
					</tr>
				</table>
				<input type="checkbox" name="check" id="check"/>市区统计合并
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
			<div style="float:right;" class="btn-operation"
				onClick="exportSumOfCood()">导出</div>
		</div>
	</div>
<table id="dg" title="【地市区县采集完成率】" style="width:98%;height:480px">
</table>
<div id="keyPointsArrivaled"></div>
<script type="text/javascript">
	$(document).ready(function() {
		//searchData();
	});
	
	function searchData() {
		//var area_id=$("input[name='area_id']").val().trim();
		//var son_area_id=$("input[name='son_area_id']").val().trim();
		var begin_time=$("input[name='begin_time']").val();
		var area_id = $("select[name='son_area']").val();
		var check = $("#check").is(':checked')==true?1:0;
		if(area_id=="NaN"){
			area_id="";
		}
		if(begin_time==""){
			$.messager.show({
                            title: '提  示',
                            msg: '请选择月份!',
                            showType: 'show'
                        });
                        return;
		}
		var obj = {
			area_id:area_id,
			begin_time:begin_time,
			check:check
		};
		$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Lxxj/coordinate/querySumOfCood.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit:true,
				singleSelect : false,
			columns : [ [ 
			{
				field : 'NAME',
				title : '地市',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM1',
				title : '光交',
				width : "5%",
				align : 'center'
				//checkbox : true
			}, {
				field : 'SUM2',
				title : '电交',
				width : "5%",
				align : 'center'
			} ,{
				field : 'SUM3',
				title : '人井',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM4',
				title : '电杆',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM5',
				title : '其他',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM7',
				title : '非关键点',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM6',
				title : '关键点',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM11',
				title : '设备点',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM9',
				title : '当月光缆线路的皮长数',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM12',
				title : '通过PC调整方式采集长度',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM10',
				title : '光缆线路的总皮长数',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM13',
				title : '中继光缆路由总长度',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM15',
				title : '中继光缆采集完成率',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM16',
				title : '非关键点',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM17',
				title : '关键点',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM18',
				title : '设备点',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM19',
				title : '当月采集缆线长度',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM20',
				title : '通过PC调整方式采集长度',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM21',
				title : '采集缆线总长度',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM22',
				title : '主干光缆路由总长度',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM23',
				title : '主干光缆采集完成率',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM8',
				title : '隐患点数',
				width : "5%",
				align : 'center'
			}]],
			//width : 'auto',
			nowrap : false,
			striped : true,
			//onClickRow:onClickRow,
			//onCheck:onCheck,
			//onSelect:onSelect,
			//onSelectAll:onSelectAll,
			onLoadSuccess : function(data) {
				$("body").resize();
			}
		});
	}
	function clearCondition(form_id) {
		$("input[name='staff_name']").val('');
		$("input[name='task_name']").val('');
		$("input[name='Vtask_name']").val('');
		$("input[name='Vstaff_name']").val('');
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
	
	function exportSumOfCood()
		{
			var begin_time=$("input[name='begin_time']").val();
			var area_id = $("select[name='son_area']").val();
			var check = $("#check").is(':checked')==true?1:0;
			if(area_id=="NaN"){
				area_id="";
			}
			if(begin_time==""){
				$.messager.show({
	                            title: '提  示',
	                            msg: '请选择月份!',
	                            showType: 'show'
	                        });
	                        return;
			}
			var obj = {
				area_id:area_id,
				begin_time:begin_time,
				check:check
			};
			$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            	if (r) {
                	$('#form').form('submit', {
                    url: webPath + "Lxxj/coordinate/exportSumOfCood.do",
                    queryParams : obj,
                    onSubmit: function () {   
                    //$.messager.progress();          
                    },
                    success: function () {
                        //$.messager.progress('close'); // 如果提交成功则隐藏进度条
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
