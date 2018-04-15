<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>隐患点检查到位率报表</title>
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
						<td width="30%">
							<select name="area"  id ="area" class="condition-select" onchange="javascript:getSonAreaList();">
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
						<td width="30%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">检查日期：</td>
						<td width="30%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name="date"
								onClick="WdatePicker();" value="${yesterday }"/>
							</div></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="exportStaff()">
					导出
				</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition()">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【隐患点检查到位率报表】" style="width:100%;height:480px">
	</table>
<script type="text/javascript">
	$(document).ready(function() {
	getSonAreaList();
	});
	
	function searchData() {
		var area = $("select[name='area']").val();
		var date = $("input[name='date']").val();
		var sonAreaId = $("select[name='son_area']").val();
		if(area == '' || date == ''){
			$.messager.show({
				title : '提  示',
				msg : '区域和检查时间不能为空！',
				showType : 'show'
			});
			return;
		}
		var obj = {
				date : date,
				area : area,
				sonAreaId:sonAreaId
		};
		//return;
		$('#dg').datagrid({
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			toolbar : '#tb',
			url : webPath + "ArrivalRate/trouble_query.do",
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
				field : 'STAFF_NAME',
				title : '巡检员',
				width : "15%",
				align : 'center'
				//checkbox : true
			}, {
				field : 'SON_AREA',
				title : '区域',
				width : "15%",
				align : 'center'
			} ,{
				field : 'DATE',
				title : '检查日期',
				width : "15%",
				align : 'center'
			},{
				field : 'TOTAL',
				title : '计划隐患点数',
				width : "20%",
				align : 'center'
			},{
				field : 'COMPLETED',
				title : '实际隐患点数',
				width : "20%",
				align : 'center'
			},{
				field : 'RATE',
				title : '检查到位率',
				width : "15%",
				align : 'center'
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
	function clearCondition() {
		$("input[name='area']").val('');
		$("input[name='date']").val('');
		$("select[name='son_area']").val("");
	}

	function exportStaff() {
		var area = $("select[name='area']").val();
		var date = $("input[name='date']").val();
		var sonAreaId = $("select[name='son_area']").val();
		if(area == '' || date == ''){
			$.messager.show({
				title : '提  示',
				msg : '区域或检查时间不能为空！',
				showType : 'show'
			});
			return;
		}
		var obj = {
				area : area,
				date : date
		};
		$.messager.confirm('系统提示', '您确定要导出该报表吗?', function (r) {
            if (r) {
                
                $('#form').form('submit', {
                    url: webPath + "ArrivalRate/trouble_export.do",
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
</script>
</body>
</html>

