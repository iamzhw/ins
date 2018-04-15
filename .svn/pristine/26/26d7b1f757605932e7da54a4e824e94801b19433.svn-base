<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../../util/head.jsp"%>
<title>关键点统计报表</title>
</head>
<body  style="padding: 3px; border: 0px">
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="12%">关键点编码：</td>
						<td width="18%">
							<div class="condition-text-container">
								<input name="point_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="12%">关键点名称：</td>
						<td width="18%">
							<div class="condition-text-container">
								<input name="point_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="12%">点创建者：</td>
						<td width="18%">
							<div class="condition-text-container">
								<input name="point_creator" type="text" class="condition-text" />
							</div>
						</td>
						<td width="12%">区域：</td>
						<td width="18%">
							<div class="condition-select-container">
								<select name="son_area" class="condition-select">
										<option value="">
											请选择
										</option>
								</select>
							</div>
						</td>
					</tr>
					<tr>
					<td width="12%">缆线编码：</td>
					<td width="18%">
						<div class="condition-text-container">
							<input class="condition-text condition"
							type="text" name="line_no" value=""/>
						</div>
					</td>
					<td width="12%">缆线名称：</td>
					<td width="18%">
						<div class="condition-text-container">
							<input class="condition-text condition"
							type="text" name="line_name" value=""/>
						</div>
					</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="exportPoint()">导出</div>
			<div style="float: right;" class="btn-operation"
				onClick="clearCondition()">重置</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【关键点统计报表】" style="width: 100%; height: 480px">
	</table>

	<script type="text/javascript">
		$(document).ready(function() {
			//searchData();
			getSonAreaList();
		});
		function getSonAreaList() {
		var areaId='${areaId}';
		$.ajax({
			type : 'POST',
			url : webPath + "General/getSonAreaList.do",
			data : {
				areaId : areaId
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json.sonAreaList;
				$("select[name='son_area'] option").remove();
				var GLY ='${GLY}';
				var LXXJ_ADMIN ='${LXXJ_ADMIN}';
				var LXXJ_ADMIN_AREA ='${LXXJ_ADMIN_AREA}';
				if (GLY == 'true' || LXXJ_ADMIN =='true' || LXXJ_ADMIN_AREA=='true')
				{
					$("select[name='son_area']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
			}
		});
	}
		function searchData() {
			var point_no = $("input[name='point_no']").val();
			var point_name = $("input[name='point_name']").val();
			var point_creator = $("input[name='point_creator']").val();
			var line_no = $("input[name='line_no']").val();
			var line_name = $("input[name='line_name']").val();
			var son_area_id = $("select[name='son_area']").val();
			
			var obj = {
				point_no   	 : point_no,
				point_name   : point_name,
				point_creator: point_creator,
				line_no		 : line_no,
				line_name	 : line_name,
				son_area	 : son_area_id
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Lxxj/point/manage/queryKeyPoints.do",
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
				columns : [ [ {
					field : 'POINT_ID',
					title : '点ID',
					checkbox : true
				}, {
					field : 'POINT_NO',
					title : '点编码',
					width : "12%",
					align : 'center'
				}, {
					field : 'POINT_NAME',
					title : '点名称',
					width : "12%",
					align : 'center',
					editor:{type:'text'}
				}, {
					field : 'LONGITUDE',
					title : '经度',
					width : "8%",
					align : 'center'
				}, {
					field : 'LATITUDE',
					title : '纬度',
					width : "8%",
					align : 'center'
				}, {
					field : 'POINT_CREATE_TIME',
					title : '点创建时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'POINT_CREATOR',
					title : '点创建者',
					width : "8%",
					align : 'center'
				}, {
					field : 'ADDRESS',
					title : '地址',
					width : "4%",
					align : 'center'
				}, {
					field : 'POINT_SON_AREA',
					title : '点属区域',
					width : "8%",
					align : 'center'
				}, {
					field : 'LINE_NO',
					title : '缆线编码',
					width : "25%",
					align : 'center'
				} , {
					field : 'LINE_NAME',
					title : '缆线名称',
					width : "25%",
					align : 'center'
				} , {
					field : 'LINE_CREATE_TIME', 
					title : '缆线创建时间',
					width : "8%",
					align : 'center'
				} , {
					field : 'LINE_CREATOR',
					title : '缆线创建者',
					width : "8%",
					align : 'center'
				} , {
					field : 'LINE_SON_AREA',
					title : '缆线属区域',
					width : "8%",
					align : 'center'
				} , {
					field : 'COUNT',
					title : '同一缆线出现次数',
					width : "12%",
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
					// 插入一行空的值
					if (data.total == 0) {
					    $(this).datagrid('insertRow', {
					        row : {}
					    });
					    $("tr[datagrid-row-index='0']").css({
					        "visibility" : "hidden"
					    });
					}
					$("body").resize();
				}
			});
		}
		/**选择行触发**/
		function onClickRow(index, row) {
			alert("onClickRow");
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

		function clearCondition() {
			$("input[name='point_no']").val("");
			$("input[name='point_name']").val("");
			$("input[name='point_creator']").val("");
			$("input[name='line_no']").val("");
			$("input[name='line_name']").val("");
		}
		
		function exportPoint() {
			var point_no = $("input[name='point_no']").val();
			var point_name = $("input[name='point_name']").val();
			var point_creator = $("input[name='point_creator']").val();
			var line_no = $("input[name='line_no']").val();
			var line_name = $("input[name='line_name']").val();
			var son_area_id = $("select[name='son_area']").val();
			var obj = {
				point_no     : point_no,
				point_name   : point_name,
				point_creator: point_creator,
				line_no		 : line_no,
				line_name	 : line_name,
				son_area     : son_area_id
			};
			
			$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            	if (r) {
                
	                $('#form').form('submit', {
	                    url: webPath + "Lxxj/point/manage/exportKeyPoints.do",
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