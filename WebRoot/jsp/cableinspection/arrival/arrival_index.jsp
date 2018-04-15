	<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>到位率报表</title>
</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="Vtask_name" value="" />
				<input type="hidden" name="Vstaff_name" value="" />
				<table class="condition">
					<tr>
					<td width="10%">巡检员：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div></td>
						<td width="10%">任务计划：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="task_name" type="text" class="condition-text" />
							</div></td>
							<td width="10%">开始时间：</td>
						<td width="20%">
						<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=start_time id="start_time" 
								onClick="WdatePicker();" />
							</div>
						</td>
							<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name=end_time id="end_time"
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'});" />
							</div></td>
					</tr>
					<tr>
						<td width="10%">地市：</td>
						<td width="30%">
							<select name="area_id" class="condition-select" onchange="getSonAreaById()">
									<option value="">
										请选择
									</option>
									<c:forEach items="${areaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
						</td>
						<td width="10%">区县：</td>
						<td width="30%">
							<select name="son_area_id" class="condition-select">
								<option value="">
										请选择
									</option>
									<c:forEach items="${sonAreaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
						</td>
						<td width="10%">计划类型：</td>
						<td width="30%">
							<select name="type" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="1">
										缆线任务
									</option>
									<option value="3">
										关键点任务
									</option>
									<option value="0">
										其他任务
									</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="exportStaff()">
					导出
				</div>
			<div class="btn-operation" onClick="exportKeyPointsArrivaled()">
					导出签到情况
			</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
<table id="dg" title="【到位率报表】" style="width:100%;height:480px">
</table>
<div id="keyPointsArrivaled"></div>
<script type="text/javascript">
	$(document).ready(function() {
		//searchData();
		getSonAreaById();
	});
	
	function searchData() {
		var staff_name = $("input[name='staff_name']").val().trim();
		var task_name = $("input[name='task_name']").val().trim();
		$("input[name='Vstaff_name']").val(staff_name);
		$("input[name='Vtask_name']").val(task_name);
		var area_id=$("select[name='area_id']").val().trim();
		var son_area_id=$("select[name='son_area_id']").val().trim();
		var TYPE=$("select[name='type']").val().trim();
		var start_time=$("input[name='start_time']").val().trim();
		var end_time = $("input[name='end_time']").val().trim();
		if(start_time==""||end_time==""){
			$.messager.show({
                            title: '提  示',
                            msg: '请输入起止时间!',
                            showType: 'show'
                        });
                        return;
		}
		var obj = {
			staff_name : staff_name,
			task_name : task_name,
			end_time:end_time,
			start_time:start_time,
			area_id:area_id,
			son_area_id:son_area_id,
			TYPE:TYPE
		};
		//return;
		$('#dg').datagrid({
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			toolbar : '#tb',
			url : webPath + "ArrivalRate/query.do",
			queryParams : obj,
			fit : true,
			method : 'post',
			pagination : true,
			pageNumber : 1,
			pageSize : 10,
			pageList : [ 20, 50 ],
			//loadMsg:'数据加载中.....',
			rownumbers : true,
			singleSelect : false,
			columns : [ [ 
			{
				field : 'TASK_ID',
				title : '任务ID',
				width : "10%",
				align : 'center',
				checkbox : true
			},{
				field : 'TYPE',
				title : '任务类型',
				width : "10%",
				align : 'center',
				formatter:function(value,row,Index){
					    //alert(jQuery.type(value)+"  "+row.PLAN_TYPE);
						/* if (null!=row.PLAN_TYPE.toString()&&""!=row.PLAN_TYPE.toString()){
							if("0"==row.PLAN_TYPE||0==row.PLAN_TYPE){
								return "缆线任务";
							}else if("1"==row.PLAN_TYPE){
								return "区域任务";
							}else{
								return "点任务";
							}
						}else{
							return row.PLAN_TYPE;
						} */
						if (null!=row.TYPE.toString()&&""!=row.TYPE.toString()){
							if("0"==row.TYPE||0==row.TYPE){
								return "其他任务";
							}else if("1"==row.TYPE){
								return "缆线任务";
							}else if("3"==row.TYPE){
								return "关键点任务";
							}else{
								return null;
							}
						}else{
							return row.TYPE;
						}
		         }
			},{
				field : 'STAFFNAME',
				title : '巡检员',
				width : "10%",
				align : 'center'
				//checkbox : true
			}, {
				field : 'AREANAME',
				title : '区域',
				width : "10%",
				align : 'center'
			} ,{
				field : 'TASKNAME',
				title : '任务计划',
				width : "18%",
				align : 'center'
			},{
				field : 'TIMES',
				title : '时间段',
				width : "20%",
				align : 'center'
			},{
				field : 'PLANPOINTS',
				title : '计划巡线点数',
				width : "10%",
				align : 'center'
			},{
				field : 'REALPOINTS',
				title : '实际巡线点数',
				width : "10%",
				align : 'center'
			},{
				field : 'ARRIVALRATE',
				title : '巡线到位率',
				width : "10%",
				align : 'center'
			},{
				field : 'PLANPOINTS2',
				title : '计划关键点数',
				width : "10%",
				align : 'center'
			},{
				field : 'REALPOINTS2',
				title : '实际到位关键点数',
				width : "10%",
				align : 'center'
			},{
				field : 'ARRIVALRATE2',
				title : '关键点到位率',
				width : "10%",
				align : 'center'
			},{
				field : 'KILOMETRE',
				title : '巡线里程',
				width : "10%",
				align : 'center'
			},{
				field : 'TIMECOUNT',
				title : '巡线时长',
				width : "10%",
				align : 'center'
			},{
				field : 'TROUBLEPOINTS',
				title : '上报隐患点数',
				width : "10%",
				align : 'center'
			},{
				field : 'TASKID',
				title : '操作',
				width : "10%",
				align : 'center',
				formatter:function(value,rowData,rowIndex){
	                  	return "<a href=\"javascript:show(" + rowData.TASK_ID + ");\">签到详情</a>";
	           } 
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
	
	function show(taskId){
		$('#keyPointsArrivaled').window({
			title : "关键点签到情况",
			href : webPath + "ArrivalRate/keyPointsArrivaledStatistics.do?taskId=" + taskId,
			width : 600,
			height : 480,
			zIndex : 2,
			region : "center",
			collapsible : false,
			cache : false,
			modal : true
		});
	}
	
	function exportStaff() {
		var staff_name = $("input[name='staff_name']").val().trim();
		var task_name = $("input[name='task_name']").val().trim();
		$("input[name='Vstaff_name']").val(staff_name);
		$("input[name='Vtask_name']").val(task_name);
		var area_id=$("select[name='area_id']").val().trim();
		var son_area_id=$("select[name='son_area_id']").val().trim();
		var TYPE=$("select[name='type']").val().trim();
		var start_time=$("input[name='start_time']").val().trim();
		var end_time = $("input[name='end_time']").val().trim();
		if(start_time==""||end_time==""){
			$.messager.show({
                            title: '提  示',
                            msg: '请输入起止时间!',
                            showType: 'show'
                        });
                        return;
		}
		var obj = {
			staff_name : staff_name,
			task_name : task_name,
			end_time:end_time,
			start_time:start_time,
			area_id:area_id,
			son_area_id:son_area_id,
			TYPE:TYPE
		};
	$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            if (r) {
                
                $('#form').form('submit', {
                    url: webPath + "ArrivalRate/export.do",
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

	function exportKeyPointsArrivaled() {
		var staff_name = $("input[name='Vstaff_name']").val().trim();
		var task_name = $("input[name='Vtask_name']").val().trim();
		var area_id=$("select[name='area_id']").val().trim();
		var son_area_id=$("select[name='son_area_id']").val().trim();
		var obj = {
			staff_name	: staff_name,
			task_name	: task_name,
			end_time	: $("input[name='end_time']").val().trim(),
			start_time	: $("input[name='start_time']").val().trim(),
			area_id:area_id,
			son_area_id:son_area_id
		}
		$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            if (r) {
                $('#form').form('submit', {
                    url: webPath + "ArrivalRate/exportKeyPointsArrivaledStatistics.do",
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
	function getSonAreaById(){
		var area_id=$("select[name='area_id']").val().trim();
		if(area_id!=null&&area_id!=''&&area_id!=undefined){
			$.ajax({
			 url:webPath + "ArrivalRate/getSonAreaById.do?area_id="+area_id,
			 contentType:"application/json",
			 type:"post",
			 dataType:"json",
			 data:{
			 	area_id:area_id
			 },
			 success:function(data){
			  var list = data.sonAreaList;
				 $("select[name='son_area_id'] option").remove();
				 $("select[name='son_area_id']").append("<option value=''>请选择</option>");
				 $.each(list,function(i,e){
				 	$("select[name='son_area_id']").append("<option value='"+e.AREA_ID+"'>"+e.NAME+"</option>");
				 });
			 }
			})
		}
	}
</script>
</body>
</html>
