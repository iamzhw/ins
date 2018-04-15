<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>全省隐患上报情况统计表</title>
</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="Vstart_time" value="" />
				<input type="hidden" name="Vend_time" value="" />
				<table class="condition">
					<tr>
					<td width="10%">开始时间：</td>
						<td width="40%">
							<div class="condition-text-container">
							<input type="text" name=start_time  class="condition-text" 
								onClick="WdatePicker({maxDate:'%y-%M-{%d}'});" />
							</div></td>
						<td width="10%">结束时间：</td>
						<td width="40%">
							<div class="condition-text-container">
								<input name="end_time" type="text" onClick="WdatePicker({maxDate:'%y-%M-{%d}'});" class="condition-text" />
							</div></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="exportStaff()">
					导出
				</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【全省隐患上报情况统计表】" style="width:100%;height:480px">
	</table>
<script type="text/javascript">
	$(document).ready(function() {
		//searchData();
	});
	
	function searchData() {
		var start_time = $("input[name='start_time']").val().trim();
		var end_time = $("input[name='end_time']").val().trim();
		$("input[name='Vstart_time']").val(start_time);
		$("input[name='Vend_time']").val(end_time);
		//var area_id=$("input[name='area_id']").val().trim();
		//var son_area_id=$("input[name='son_area_id']").val().trim();
		var obj = {
			start_time : start_time,
			end_time : end_time
		};
		//return;
		$('#dg').datagrid({
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			toolbar : '#tb',
			url : webPath + "Trouble/query.do",
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
				field : 'AREA_NAME',
				title : '地区',
				width : "10%",
				align : 'center'
				//checkbox : true
			}, {
				field : 'SUM0',
				title : '巡线人员总数',
				width : "10%",
				align : 'center'
			} ,{
				field : 'SUM1',
				title : '上报数量',
				width : "10%",
				align : 'center'
			},{
				field : 'SUM2',
				title : '归档',
				width : "10%",
				align : 'center'
			},{
				field : 'NOTRETURNBILLCOUNT',
				title : '待回单',
				width : "10%",
				align : 'center'
			},{
				field : 'SUM4',
				title : '未派单',
				width : "10%",
				align : 'center'
			},
			{
				field : 'NOTAUDITBILLCOUNT',
				title : '待审核',
				width : "10%",
				align : 'center'
			},
			{
				field : 'SUM5',
				title : '未上报隐患点人数（要求每个巡线人员每周最少上传一个隐患点）',
				width : "50%",
				align : 'center'
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
	function clearCondition(form_id) {
		$("input[name='start_time']").val('');
		$("input[name='end_time']").val('');
		$("input[name='Vstart_time']").val('');
		$("input[name='Vend_time']").val('');
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
	function exportStaff() {
		var start_time = $("input[name='Vstart_time']").val().trim();
		var end_time = $("input[name='Vend_time']").val().trim();
		//var area_id=$("input[name='area_id']").val().trim();
		//var son_area_id=$("input[name='son_area_id']").val().trim();
		var obj = {
			start_time : start_time,
			end_time : end_time
		};
	$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            if (r) {
                
                $('#form').form('submit', {
                    url: webPath + "Trouble/export.do",
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
