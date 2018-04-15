
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title></title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<div style="margin-top: 5px">
		<table id="dgSite" title="【】" style="width: 100%; height: 450px">
		</table>
	</div>
	<div id="tbSite" style="padding: 5px; height: auto">
		
		<div style="padding:20px 0 10px 25px">
			<form id="newSites" method="post" enctype ="multipart/form-data" >
				<input type="hidden" id="lineId" name="lineId" value="${lineId}" />
				<input type="hidden" id="relayId" name="relayId" value="${relayId}" />
				<input type="hidden" id="startSiteId" name="startSiteId" />
				<input type="hidden" id="endSiteId" name="endSiteId" />
				<table style="line-height: 30px;">
					<tr>
						<td>请先下载样例文件：<a href="javascript:void(0);" onclick="downloadExample();">【巡线点导入样例】</a></td>
						<td width="50"></td>
						<td>导入：</td>
						<td>
							<input style="width: 200px" type="file" name="file" id="excel"/>
						</td>
						<td>
							<div class="btn-operation" style="line-height: 20px" onClick="importUsers();">确定</div>
						</td>
						<td>
							<div class="btn-operation" style="line-height: 20px" onClick="exportSite();">导出</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		
			searchSiteData();
		});
		
		
		function searchSiteData() {
			$('#dgSite').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 800,
				height : 400,
				url : webPath + "lineInfoController/querySiteList.do",
				queryParams : {
					lineId : '${lineId}'
				},
				method : 'post',
				
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				columns : [ [ {
					field : 'SITE_ID',
					title : '巡线点ID',
					checkbox : true
				}, {
					field : 'LONGITUDE',
					title : '经度',
					width : "10%",
					align : 'center'
				},{
					field : 'LATITUDE',
					title : '纬度',
					width : "10%",
					align : 'center'
				},{
					field : 'SITE_NAME',
					title : '巡线点名称',
					width : "25%",
					align : 'center'
				}, {
					field : 'ADDRESS',
					title : '巡线点地址',
					width : "22%",
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
       			// $(this).datagrid("fixRownumber");
       			 
				
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
		
		//导入巡线点
		function importUsers(){
			var allDate = $('#dgSite').datagrid('getRows');
			var allCount = allDate.length;
			var selected = $('#dgSite').datagrid('getChecked');
			var count = selected.length;
			if (count == 0 || count > 2) {
				$.messager.show({
					title : '提  示',
					msg : '请选择一到两条数据!',
					showType : 'show'
				});
				return;
			}else if(count == 1){
				if($('#dgSite').datagrid('getRowIndex',selected[0]) == 0){					//只选了第一个点
					if(allCount == 1){
						$.messager.defaults = { ok : '该条数据之前', cancel : '该条数据之后'};
						$.messager.confirm("提  示","插入到哪?",function(r){
							if(r){
								$('#endSiteId').val(selected[0].SITE_ID);
								submitForm();
							}else{
								$('#startSiteId').val(selected[0].SITE_ID);
								submitForm();
							}
						});
						$.messager.defaults = { ok : '确定', cancel : '取消'};
					}else{
						$('#endSiteId').val(selected[0].SITE_ID);
						submitForm();
					}
				}else if($('#dgSite').datagrid('getRowIndex',selected[0]) == $('#dgSite').datagrid('getRows').length-1){		//只选了最后一个点
					$('#startSiteId').val(selected[0].SITE_ID);
					submitForm();
				}else{
					$.messager.show({
						title : '提  示',
						msg : '请再选择一条数据!',
						showType : 'show'
					});
					return;
				}
			}else {
				if(Math.abs($('#dgSite').datagrid('getRowIndex',selected[0])-$('#dgSite').datagrid('getRowIndex',selected[1])) != 1){	//选了两个不相邻的点
					$.messager.show({
						title : '提  示',
						msg : '请选择两条相邻的数据!',
						showType : 'show'
					});
					return;
				}
				$('#startSiteId').val(selected[0].SITE_ID);
				$('#endSiteId').val(selected[1].SITE_ID);
				submitForm();
			}
		}
		
		function submitForm(){
			$.messager.confirm('系统提示', '您确定要导入巡线点吗?', function(r) {
				if (r) {
					 $('#newSites').form('submit', {
		                 url: webPath + "lineInfoController/import_save.do",
		                 onSubmit: function () {
		                     $.messager.progress();
		                 },
		                 success: function (data) {
		                     $.messager.progress('close'); // 如果提交成功则隐藏进度条
		                     var json = eval('(' + data + ')');
		                     if(json.status){
		                    	 $.messager.show({
			                         title: '提  示',
			                         msg: '导入巡线点成功,请一个小时之后查看!',
			                         showType: 'show'
			                     });
		                     } else {
		                    	 $.messager.show({
			                         title: '提  示',
			                         msg: json.message,
			                         showType: 'show',
			                         width : 350,
			                         height : 150
			                     });
		                     }
		                 }
		             });
				}
				$('#startSiteId').val('');
				$('#endSiteId').val('');
			});
		}
		//下载模板
		function downloadExample(){
			$("#hiddenIframe").attr("src", "./downloadExample.do");
		}
		
		//导出巡线点
		function exportSite() {
			$.messager.confirm('系统提示', '您确定要导出巡线点信息吗?', function (r) {
				
				if (r) {
	                
	                $('#newSites').form('submit', {
	                    url: webPath + "lineInfoController/export.do",
	                    onSubmit: function () {
	                        //$.messager.progress();
	                    },
	                    success: function () {
	                        //$.messager.progress('close'); // 如果提交成功则隐藏进度条
	                        $.messager.show({
	                            title: '提  示',
	                            msg: '导出巡线点信息成功!',
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
