<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/datagrid-detailview.js"></script>
<title>看护到位率报表</title>
</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="vphoto" value="" />
				<input type="hidden" name="vurl" value="" />
				<table class="condition">
					<tr>
					<td width="10%">地区：</td>
						<td width="20%">
							<select name="area_id"  id ="area_id"class="condition-select" onchange="javascript:getSonAreaList();">
									<c:forEach items="${areaList}" var="sl">
										<c:if test = "${curr_area ==  sl.AREA_ID}">
										<option value="${sl.AREA_ID}" 
										selected = "selected">
											${sl.NAME}
										</option>
										</c:if>
										<c:if test = "${curr_area !=  sl.AREA_ID}">
										<option value="${sl.AREA_ID}">
											${sl.NAME}
										</option>
										</c:if>
									</c:forEach>
							</select>
						</td>
						<td width="10%">区域：</td>
						<td width="20%">
							<select name="son_area_id" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" id="start_time" name="start_time"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});"/>
							</div></td>
							<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name="end_time"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'start_date\')}'});"/>
							</div>
						</td>
					</tr>
					<tr>
					<td width="10%">签到人：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name="staff_name" value=""/>
							</div>
						</td>
					<td width="10%">缆线任务名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name="task_name" value=""/>
							</div>
						</td>
					<td width="10%">缆线名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name="line_name" value=""/>
							</div>
						</td>
					<td width="10%">关键点名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition"
								type="text" name="point_name" value=""/>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【照片点评】" style="width:100%;height:480px">
	</table>
	<div id="win_add"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("select[name='area_id']").change(function() {
						//根据area_id，获取区县
						getSonAreaList();
			});
			getSonAreaList();
		});
		function getSonAreaList() {
		var areaId=$("#area_id").val();
		if(null==areaId||""==areaId)
		{
			$("select[name='son_area_id'] option").remove();
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
				$("select[name='son_area_id'] option").remove();
				var admin ='${admin}';
				if (undefined !=admin && admin!='true'){
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='son_area_id']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
				else{
					$("select[name='son_area_id']").append(
							"<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area_id']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
				}
			}
		});
	}
function searchData() {
		var area_id = $("select[name='area_id']").val();
		var start_time = $("input[name='start_time']").val();
		var end_time = $("input[name='end_time']").val();
		var son_area_id = $("select[name='son_area_id']").val();
		var staff_name = $("input[name='staff_name']").val();
		var task_name = $("input[name='task_name']").val();
		var line_name = $("input[name='line_name']").val();
		var point_name = $("input[name='point_name']").val();
		var obj = {
			start_time : start_time,
			end_time : end_time,
			area_id : area_id,
			son_area_id : son_area_id,
			staff_name:staff_name,
			task_name : task_name,
			line_name : line_name,
			point_name : point_name
		};
		//return;
		$('#dg').datagrid({
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			view: detailview,
			toolbar : '#tb',
			url : webPath + "Remark/query.do",
			queryParams : obj,
			method : 'post',
			pagination : true,
			pageNumber : 1,
			pageSize : 10,
			pageList : [ 20, 50 ],
			//loadMsg:'数据加载中.....',
			rownumbers : true,
			singleSelect : true,
			fitColumns:true,
			columns : [ [ 
			{
				field : 'MICRO_PHOTO_PATH',
				title : '照片',
				width : "7%",
				align : 'center',
				formatter:function(value,rowData,rowIndex){
                    	return "<a href=\""+rowData.PHOTO_PATH+"\" title=\"照片预览\" class=\"nyroModal\"><img src=\""+value+"\"/></a>"
                    } 
			},
			{
				field : 'POINT_NAME',
				title : '点名称',
				width : "8%",
				align : 'center'
			}, {
				field : 'STAFF_NAME',
				title : '签到人',
				width : "8%",
				align : 'center'
			//checkbox : true
			}, {
				field : 'LINE_NAME',
				title : '缆线名称',
				width : "15%",
				align : 'center'
			},
			   {
				field : 'TASK_NAME',
				title : '任务名称',
				width : "10%",
				align : 'center'
			},
			   {
				field : 'CREATE_TIME',
				title : '签到时间',
				width : "10%",
				align : 'center'
			},
			   {
				field : 'SON_NAME',
				title : '区域',
				width : "12%",
				align : 'center'
			}, {
				field : 'NAME',
				title : '地市',
				width : "8%",
				align : 'center'
			}, {
					field : 'PHOTO_ID',
					title : '操作',
					width : "8%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
                    	return "<a href=\"javascript:addRemark("+value+");\">点评</a>";  
                    } 
				}] ],
			//width : 'auto',
			nowrap : false,
			striped : true,
    		detailFormatter: function(rowIndex, rowData){
    			return '<div style="padding:2px"><table id="ddv-' + rowIndex + '"></table></div>';
		    },
		    onExpandRow:function(rowIndex, rowData){
		    	 $('#ddv-'+rowIndex).datagrid({  
                    url:webPath + "Remark/querydetail.do?photo_id="+rowData.PHOTO_ID,  
                    fitColumns:true,  
                    singleSelect:true,  
                    height:'auto', 
                    columns:[[  
                        {field:'STAFF_NAME',title:'点评人',width:'8%',align : 'center'},  
                        {field:'STATUS',title:'状态',width:'8%',align : 'center'},
                        {field:'REMARK',title:'点评内容',width:'8%',align : 'center'},
                        {field:'CTEATE_TIME',title:'点评时间',width:'8%',align : 'center'} 
                    ]],
                    onResize:function(){  
                        $('#dg').datagrid('fixDetailRowHeight',rowIndex);  
                    },  
                    onLoadSuccess:function(){  
                        setTimeout(function(){  
                            $('#dg').datagrid('fixDetailRowHeight',rowIndex);  
                        },0);  
                    }  
                 });
		    },
			//onClickRow:onClickRow,
			//onCheck:onCheck,
			//onSelect:onSelect,
			//onSelectAll:onSelectAll,
			onLoadSuccess : function(data) {
			$("body").resize();
			}
		});
	}
	function addRemark(id){
	$("input[name='vphoto']").val(id);
		$('#win_add').window({
				title : "【新增】",
				href : webPath + "Remark/add.do",
				width : 500,
				height : 400,
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