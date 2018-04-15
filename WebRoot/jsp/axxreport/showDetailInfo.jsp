<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	
    <div id="dgByInvalid"></div>
<script type="text/javascript">
		    $(function(){
		    	var obj={};
			    obj.plan_id='${plan_id}';
			    obj.start_query_time='${start_query_time}';
			    obj.end_query_time='${end_query_time}';
				
				$('#dgByInvalid').datagrid({
					//此选项打开，表格会自动适配宽度和高度。
					autoSize : true,
					fitColumns:true,
					url : webPath + "xxdReportController/detailInfo.do",
					queryParams : obj,
					method : 'post',
					rownumbers : true,
					singleSelect : false,
					columns : [ [ {
						field : 'PLAN_TIME',
						title : '看护日期',
						width : "10%",
						align : 'center'
					}, {
						field : 'timePart',
						title : '计划看护时间段',
						width : "10%",
						align : 'center'
					},
					{
						field : 'invalidTime1',
						title : '离开时间段',
						width : "8%",
						align : 'center'
					},
					{
						field : 'invalidTime2',
						title : '超过20分钟',
						width : "8%",
						align : 'center'
					}
					] ],
					nowrap : false,
					striped : true,
				});
		    })
	</script>

