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
						<td width="10%">开始时间：</td>
						<td width="20%">
						<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=begin_time id="begin_time" 
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
							</div>
						</td>
							<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name=end_time id="end_time"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
							</div></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="exportKeyPointsArrivaled()">
					导出
				</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
<table id="dg" title="【分公司缆线巡检使用情况】" style="width:98%;height:480px">
</table>
<div>
<ul>
<li>应派发的关键点数=已派发关键点+未派发有维护等级的关键数</li>
<li>派发率=已派发关键点数/应派发的关键点数*100%</li>
<li>应签到次数=已派发缆线任务应签到次数+未派发有维护等级的关键点*维护等级次数</li>
<li>缆线任务签到率=缆线任务实际签到次数/已派发缆线任务应签到次数*100%</li>
<li>巡检覆盖率=缆线任务实际签到次数/应签到次数*100%</li>
<li>注:如签到率超过100%可能为任务完成后,管理员修改过计划内容</li>
</ul>
</div>
<div id="keyPointsArrivaled"></div>
<script type="text/javascript">
	$(document).ready(function() {
		//searchData();
	});
	
	function searchData() {
		//var area_id=$("input[name='area_id']").val().trim();
		//var son_area_id=$("input[name='son_area_id']").val().trim();
		var begin_time=$("input[name='begin_time']").val();
		var end_time=$("input[name='end_time']").val();
		var area_id = $("select[name='son_area']").val();
		if(area_id=="NaN"){
			area_id="";
		}
		if(begin_time==""||end_time==""){
			$.messager.show({
                            title: '提  示',
                            msg: '请输入起止时间!',
                            showType: 'show'
                        });
                        return;
		}
		var obj = {
			area_id:area_id,
			end_time:end_time,
			begin_time:begin_time
		};
		$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "ArrivalRate/queryArrivalRateByDate.do",
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
				field : 'AREA',
				title : '地市',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM1',
				title : '新增人员',
				width : "5%",
				align : 'center'
				//checkbox : true
			}, {
				field : 'SUM2',
				title : '新增缆线',
				width : "5%",
				align : 'center'
			} ,{
				field : 'SUM3',
				title : '巡检任务数',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM4',
				title : '日常巡检任务数',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM5',
				title : '关键点总数',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM6',
				title : '已派发关键点数',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM7',
				title : '应派发的关键点数',
				width : "7%",
				align : 'center'
			},{
				field : 'SUM8',
				title : '有维护等级的关键点数',
				width : "8%",
				align : 'center'
			},{
				field : 'SUM9',
				title : '已派发有维护等级的关键点数',
				width : "8%",
				align : 'center'
			},{
				field : 'SUM10',
				title : '派发率',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM11',
				title : '隐患工单数',
				width : "5%",
				align : 'center'
			},{
				field : 'SUM12',
				title : '新增隐患点数',
				width : "5%",
				align : 'center'	
			},{
				field : 'SUM13',
				title : '新增看护点数',
				width : "5%",
				align : 'center'	
			},{
				field : 'SUM14',
				title : '检查任务数',
				width : "5%",
				align : 'center'	
			},{
				field : 'SUM15',
				title : '看护任务数',
				width : "5%",
				align : 'center'	
			},{
				field : 'SUM16',
				title : '新增坐标采集数',
				width : "7%",
				align : 'center'	
			},{
				field : 'SUM17',
				title : '已派发巡线任务应签到次数',
				width : "8%",
				align : 'center'	
			},{
				field : 'SUM18',
				title : '应签到次数',
				width : "7%",
				align : 'center'	
			},{
				field : 'SUM19',
				title : '实际签到关键点数',
				width : "7%",
				align : 'center'	
			},{
				field : 'SUM20',
				title : '缆线任务实际签到次数',
				width : "7%",
				align : 'center'	
			},{
				field : 'SUM21',
				title : '看护员人数',
				width : "5%",
				align : 'center'	
			},{
				field : 'SUM22',
				title : '巡线员人数',
				width : "6%",
				align : 'center'	
			},{
				field : 'SUM23',
				title : '巡线任务签到率',
				width : "5%",
				align : 'center'	
			},{
				field : 'SUM24',
				title : '巡检覆盖率',
				width : "5%",
				align : 'center'	
			},{
				field : 'SUM25',
				title : '参与巡线人数',
				width : "5%",
				align : 'center'	
			},{
				field : 'SUM26',
				title : '已归档隐患工单',
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

	function exportKeyPointsArrivaled() {
		var begin_time=$("input[name='begin_time']").val();
		var end_time=$("input[name='end_time']").val();
		var son_area = $("select[name='son_area']").val();
		if(begin_time==""||end_time==""){
			$.messager.show({
                            title: '提  示',
                            msg: '请输入起止时间!',
                            showType: 'show'
                        });
                        return;
		}
		var obj = {
			son_area:son_area,
			end_time:end_time,
			begin_time:begin_time
		};
		$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            if (r) {
                $('#form').form('submit', {
                    url: webPath + "ArrivalRate/exportArrivalRateByDate.do",
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
