<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>看护到位率报表</title>
</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="Vtask_name" value="" />
				<input type="hidden" name="Vstaff_name" value="" />
				<table class="condition">
					<tr>
					<td width="10%">地区：</td>
						<td width="20%">
							<select name="area"  id ="area"class="condition-select" onchange="javascript:getSonAreaList();">
									<c:forEach items="${areaList}" var="sl">
										<c:if test = "${curr_area ==  sl.AREA_ID}">
										<option value="${sl.AREA_ID}" 
										selected = "selected">
											${sl.NAME}
										</option>
										</c:if>
									</c:forEach>
							</select>
						</td>
						<td width="10%">区域：</td>
						<td width="20%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" id="start_date" name="start_date"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});" value="${yesterday_begin }"/>
							</div></td>
							<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name="end_date"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'start_date\')}'});" value="${yesterday_end }"/>
							</div>
						</td>
					</tr>
					<tr>
					<td width="10%">看护员：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name="staff_name" value=""/>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="exportStaff()">
					导出
				</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【看护到位率报表】" style="width:100%;height:480px">
	</table>
	<div id="win_staff"></div>
<script type="text/javascript">
	$(document).ready(function() {
	$("select[name='area']").change(function() {
				//根据area_id，获取区县
				getSonAreaList();
			});
		getSonAreaList();
	});

	function getSonAreaList() {
		var areaId=$("#area").val();
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
	function searchData() {
		var area = $("select[name='area']").val();
		var start_date = $("input[name='start_date']").val();
		var end_date = $("input[name='end_date']").val();
		var sonAreaId = $("select[name='son_area']").val();
		var staff_name = $("input[name='staff_name']").val();
		if (area == '' ||  (start_date == '' && end_date=='')) {
			$.messager.show({
				title : '提  示',
				msg : '区域和检查时间不能为空！',
				showType : 'show'
			});
			return;
		}
		var obj = {
			start_date : start_date,
			end_date : end_date,
			area : area,
			sonAreaId:sonAreaId,
			staff_name:staff_name
		};
		//return;
		$('#dg').datagrid({
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			toolbar : '#tb',
			url : webPath + "ArrivalRate/keep_query.do",
			queryParams : obj,
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
				field : 'AREANAME',
				title : '地区',
				width : "7%",
				align : 'center'
			},
			{
				field : 'SONAREANAME',
				title : '区域',
				width : "8%",
				align : 'center'
			}, {
				field : 'KEEP_STAFF',
				title : '看护员',
				width : "8%",
				align : 'center'
			//checkbox : true
			}, {
				field : 'POINT_NAME',
				title : '外力点名称',
				width : "15%",
				align : 'center'
			},
			   {
				field : 'START_TIME',
				title : '看护开始日期',
				width : "10%",
				align : 'center'
			},
			   {
				field : 'COMPLETE_TIME',
				title : '看护结束日期',
				width : "10%",
				align : 'center'
			},
			   {
				field : 'TIME_PERIOD',
				title : '时间段',
				width : "12%",
				align : 'center'
			}, {
				field : 'TIME_TOTAL',
				title : '计划看护时间',
				width : "8%",
				align : 'center'
			}, {
				field : 'TIME_COMPLETED',
				title : '实际看护时间',
				width : "12%",
				align : 'center'
			}, {
				field : 'COUNT_LEAVE',
				title : '离开次数',
				width : "6%",
				align : 'center'
			}, {
				field : 'RATE',
				title : '完成百分比',
				width : "7%",
				align : 'center'
			} , {
					field : 'PLAN_ID',
					title : '操作',
					width : "8%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
                    	return "<a href=\"javascript:photoInfo(" + value + ");\">照片</a>";  
                    } 
				}] ],
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

	function exportStaff() {
		var area = $("select[name='area']").val();
		var start_date = $("input[name='start_date']").val();
		var end_date = $("input[name='end_date']").val();
		var son_area = $("select[name='son_area']").val();
		var staff_name = $("input[name='staff_name']").val();
		if (area == '' || (start_date == '' && end_date=='')) {
			$.messager.show({
				title : '提  示',
				msg : '区域或检查时间不能为空！',
				showType : 'show'
			});
			return;
		}
		var obj = {
			area : area,
			start_date : start_date,
			end_date : end_date,
			son_area : son_area,
			staff_name : staff_name
		};
		$.messager.confirm('系统提示', '您确定要导出该报表吗?', function(r) {
			if (r) {

				$('#form').form('submit', {
					url : webPath + "ArrivalRate/keep_export.do",
					queryParams : obj,
					onSubmit : function() {
						//  $.messager.progress();
					},
					success : function() {
						//  $.messager.progress('close'); // 如果提交成功则隐藏进度条
						$.messager.show({
							title : '提  示',
							msg : '导出成功!',
							showType : 'show'
						});

					}
				});
			}
		});

	}
	function photoInfo(plan_id){
				$('#win_staff').window({
					title : "【看护照片】",
					href : webPath + "ArrivalRate/keep_query_photo.do?plan_id=" + plan_id,
					width : 500,
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

