	<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>派单关系</title>
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
    <input type="hidden" name="search_area_id" id="search_area_id" />
    <input type="hidden" name="search_month" id="search_month" />
    <input type="hidden" name="search_year" id="search_year" />
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr>
						<td width="10%">区域：</td>
						<td width="30%">
							<select name="AREA_ID" class="condition-select">
									<option value="">
										请选择
									</option>
									<c:forEach items="${areaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
						</td>
						
						<td width="10%">年份：</td>
						<td width="20%">
						<!-- <div class="condition-text-container"> -->
							  <select name="RECORD_YEAR" class="condition-select"> 
									<option value="2015">2015</option>
									<option value="2016">2016</option>
									<option value="2017" selected="selected">2017</option>
									<option value="2018">2018</option>
									<option value="2019">2019</option>
									<option value="2020">2020</option>
							 </select>
						</td>
						
						<td width="10%">月份：</td>
						<td width="20%">
						<!-- <div class="condition-text-container"> -->
							  <select name="RECORD_MONTH" class="condition-select">
									<option value="1" selected="selected">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
							 </select>
						</td>
						
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="exportRecord()">
					导出
				</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
<table id="dg" title="【分公司缆线巡检使用情况】" style="width:98%;height:480px">
</table>
<div id="win_staff"></div>
<script type="text/javascript">
	$(document).ready(function() {
		//searchData();
		var myDate = new Date();
		//获取当前年
// 		var year=myDate.getFullYear();
		//获取当前月
		var month=myDate.getMonth()+1;
		var RECORD_MONTH = $("#search_month").val();
		
		if(""==RECORD_MONTH){
			$("select[name='RECORD_MONTH']").val(month);
		}
	});
	
	//关键点签到详情
	function show(AREA_ID,RECORD_DAY){
	   
		var RECORD_MONTH = $("#search_month").val();
		
		var SEARCH_AREA_ID = $("#search_area_id").val();
		
		var RECORD_YEAR = $("#search_year").val();
		
		$('#win_staff').window({
			title : "【局部签到人员表】",
			href : webPath + "Record/selectRecordStaff.do?AREA_ID=" + AREA_ID + "&RECORD_DAY="+ RECORD_DAY + "&RECORD_MONTH="+RECORD_MONTH +"&RECORD_YEAR="+RECORD_YEAR+"&SEARCH_AREA_ID="+SEARCH_AREA_ID,
			width : 860,
			height : 480,
			zIndex : 2,
			region : "center",
			collapsible : false,
			cache : false,
			modal : true
		});
	};
	
	function searchData() {
		
		var AREA_ID = $("select[name='AREA_ID']").val();

		var RECORD_MONTH = $("select[name='RECORD_MONTH']").val();
		var RECORD_YEAR = $("select[name='RECORD_YEAR']").val();
		if(AREA_ID=="NaN"){
			AREA_ID="";
		}
		$("#search_area_id").val($("[name='AREA_ID']").val());
		$("#search_month").val($("[name='RECORD_MONTH']").val());
		$("#search_year").val($("[name='RECORD_YEAR']").val());
		/* if(begin_time==""||end_time==""){
			$.messager.show({
                            title: '提  示',
                            msg: '请输入起止时间!',
                            showType: 'show'
                        });
                        return;
		} */
		var searchDate = {
		    AREA_ID:AREA_ID,
			RECORD_MONTH:RECORD_MONTH,
			RECORD_YEAR:RECORD_YEAR
		};
		$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Record/getListByPage.do",
				queryParams : searchDate,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ,500],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit:true,
				singleSelect : false,
			columns : [ [ 
			{
				field : 'AREA_NAME',
				title : '地市',
				width : "5%",
				align : 'center'
			},{
				field : 'RECORD_1',
				title : '1日',
				width : "3%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 1 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_2',
				title : '2日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 2 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_3',
				title : '3日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 3 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_4',
				title : '4日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 4 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_5',
				title : '5日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 5 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_6',
				title : '6日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 6 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_7',
				title : '7日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 7 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_8',
				title : '8日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 8 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_9',
				title : '9日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 9 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_10',
				title : '10日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 10 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			}
			,{
				field : 'RECORD_11',
				title : '11日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 11 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_12',
				title : '12日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 12 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_13',
				title : '13日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 13 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_14',
				title : '14日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 14 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_15',
				title : '15日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 15 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_16',
				title : '16日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 16 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_17',
				title : '17日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 17 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_18',
				title : '18日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 18 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_19',
				title : '19日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 19 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_20',
				title : '20日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 20 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			}
			,{
				field : 'RECORD_21',
				title : '21日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 21 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_22',
				title : '22日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 22 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_23',
				title : '23日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 23 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_24',
				title : '24日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 24 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_25',
				title : '25日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 25 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_26',
				title : '26日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 26 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_27',
				title : '27日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 27 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_28',
				title : '28日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 28 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_29',
				title : '29日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 29 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_30',
				title : '30日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 30 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			},{
				field : 'RECORD_31',
				title : '31日',
				width : "5%",
				align : 'center',
				formatter:function(value,row,Index){
					if(null!=value&&""!=value){
		        		return "<a href=\"javascript:show("+ row.AREA_ID + "," + 31 +");\">"+value+"</a>";
					}else{
						return null;
					}
					
	        	}
			}
			]],
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

	function exportRecord() {
		var AREA_ID=$("#search_area_id").val();
		var RECORD_MONTH=$("#search_month").val();
		var RECORD_YEAR=$("#search_year").val();
		if(""==AREA_ID&&""==RECORD_MONTH){
			
		}
		var obj = {
				AREA_ID:AREA_ID,
				RECORD_YEAR:RECORD_YEAR,
				RECORD_MONTH:RECORD_MONTH
		};
		$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            if (r) {
                $('#form').form('submit', {
                    url: webPath + "Record/exportRecord.do",
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
