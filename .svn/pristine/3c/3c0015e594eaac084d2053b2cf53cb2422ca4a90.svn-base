<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path2 = request.getContextPath();
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../util/head.jsp"%>
<title>步巡柱状图</title>
<script type="text/javascript" src="<%=path2%>/js/highcharts.js"></script>
<script type="text/javascript" src="<%=path2%>/js/highcharts-more.js"></script>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td >任务 ：</td>
						<td >
							<select name="task_id" id="task_id" style="width: 80%" class="condition-select condition"
							data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
								
								<c:forEach items="${effectiveTasks}" var="al">
									<option value="${al.TASK_ID}">${al.TASK_NAME}</option>
								</c:forEach>
							</select>
						</td>
						<td >步巡设施类型 ：</td>
						<td >
							<select name="equip_type_id" id="equip_type_id" class="condition-select condition"
							 data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
								<c:forEach items="${facTypes}" var="al">
									<option value="${al.EQUIP_TYPE_ID}">${al.EQUIP_TYPE_NAME}</option>
								</c:forEach>
							</select>
						</td>
						<td >
							<div  class="btn-operation" onClick="clearCondition('form')">重置</div>
							<div  class="btn-operation" onClick="loadPic()">查询</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="container" style="width: 100%; height: 450px; align: center" >
   <script type="text/javascript">
	var staff_id = ${staff_id};
		
	//柱状图后台取值
	function loadPic(){
		var task_id 	 =$("#task_id").val();
		var equip_type_id=$("#equip_type_id").val();
		$.ajax({
		async:false,
		dataType: "json",
		url : webPath + "xxdReportController/getStepDetailUI.do",
		data:{
			task_id:task_id,
			equip_type_id:equip_type_id,
			staff_id:staff_id
		},
		success : function(data) {
				var PropertyNames  = new Array();
				var PropertyValues = new Array();
				for(var i=0;i<data.length;i++){
				    var PropertyName = data[i].PropertyName;
				    PropertyNames.push(PropertyName);
				     var PropertyValue = data[i].PropertyValue;
				    PropertyValues.push(PropertyValue);
		    	}
		    	tree_chart(PropertyNames,PropertyValues);
			}
		});
	}
		
	
	function tree_chart(PropertyNames,PropertyValues){
				$('#container').highcharts({
				
				    chart: {
				        type: 'column',
              			margin: [ 50, 50, 100, 80]
				    },
				    
				    title: {
				        text: '步巡柱状图'
				    },
				    xAxis: {
				        categories: PropertyNames,
				        labels: {
			                rotation: -45,
			                align: 'right',
			                style: {
			                    fontSize: '13px',
			                    fontFamily: 'Verdana, sans-serif'
			                }
			            }
				    },
				    yAxis: {
				    	min: 0,
				        title: {
				            text: '数值(个)'
				        }
				    },
				
				    tooltip: {
						pointFormat: '<b>{point.y:.1f} 个</b>',
				    },
				    legend: {
				        enabled: false
				    },
				
				    series: [{
				        name: '数量',
	// 			        colorByPoint:true,
				        data: PropertyValues,
				        dataLabels: {
			                enabled: true,
			                rotation: -90,
			                color: '#FFFFFF',
			                align: 'right',
			                x: 4,
			                y: 10,
			                style: {
			                    fontSize: '13px',
			                    fontFamily: 'Verdana, sans-serif',
			                    textShadow: '0 0 3px black'
			                }
			            }
				    }]
				});
        
	}
	
	function clearCondition(form_id) {
		$("#" + form_id + "").form('reset', 'none');
	}
   </script>
 </body>
</html>
