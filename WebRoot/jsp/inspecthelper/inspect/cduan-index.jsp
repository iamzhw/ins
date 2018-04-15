<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<%@include file="../../util/head.jsp"%>
	</head>
	<body style="width: 1100px;">
		<form id="query-form" name="query-form" method="post">
		<input type="hidden" id="orderId" name="orderId" value="${orderId }"/>
		<input type="hidden" id="equipmentId" name="equipmentId" value="${equipmentId }"/>
		<table class="condition">
					<tr>
						<td align="left" width="15%">
							时间选择：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<select name="date" id="paras_date" class="condition-select condition">
										<option value="">----------请选择----------</option>
											<option value="30">1个月</option>
											<option value="60">2个月</option>
											<option value="90">3个月</option>
											<option value="120">4个月</option>
											<option value="150">5个月</option>
											<option value="180">6个月</option>
											<option value="lastCheck">上次巡检至今</option>
									</select>
							</div>
						</td>
						<td >
						<div class="btn-operation-container">
							<div class="btn-operation btnzhuan" name="Submit" value="查　询" onClick="queryData();">查询</div>
							</div>
						</td>
						<td >
						<div class="btn-operation-container">
							<div class="btn-operation btnzhuan" onClick="btnexport()">导出Excel</div>
							</div>
						</td>
					</tr>
				</table>
		</form>
		<table id="dg_cduan" title="【问题查看】" ></table>
		</body>
		<script type="text/javascript">
		
		function queryData(){
			var date = $("#paras_date").val();
			var equipmentId = $("#equipmentId").val(); 
			var orderId = $("#orderId").val();
			var obj = {
					//taskName : taskName,
					orderId : orderId,
					equipmentId:equipmentId,
					date : date
			};
			//return;
			$('#dg_cduan').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				
				//toolbar : '#tb',
				url : webPath + "inspect/cDuanTable.do",
				queryParams : obj,
				method : 'post',
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'PROJECT_TYPE',
					title : '工程属性',
					width : "60",
					align : 'center'
				},{
					field : 'RES_NO',
					title : '设备编码',
					width : "150",
					align : 'center'
				}, {
					field : 'PROJECT_NO',
					title : '项目编号',
					width : "150",
					align : 'center'
				}, {
					field : 'PROJECT_NAME',
					title : '项目名称',
					width : "200",
					align : 'center'
				}, {
					field : 'MANAGE_COMPANY',
					title : '施工公司',
					width : "70",
					align : 'center'
				}, {
					field : 'END_DATE',
					title : '竣工日期',
					width : "100",
					align : 'center'
				}, {
					field : 'EXPORT_DATE',
					title : '录入日期',
					width : "100",
					align : 'center'
				}, {
					field : 'CHANGE_STATUS',
					title : '变动情况',
					width : "100",
					align : 'center'
				},{
					field : 'button',
					title : '操作',
					width : "70",
					align : 'center',
					formatter: function(value,row,index){
								return 	"<input type='button' class='btndesc' value='描述' onclick='btndesc(\""+row.CHANGE_STATUS+"\")'/>";
					}
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
					
		};
		
		
		function btnexport(){
					var url =  webPath+"inspect/exportCDuanExcel.do";
					$("#query-form").attr("action",url);
					$("#query-form").submit();
				}
				function  btndesc(changeStatus){
					var orderId = document.getElementById("orderId").value; 
					var equipmentId = document.getElementById("equipmentId").value; 
					var url = webPath+"inspect/cduanTrou.do?changeStatus="+changeStatus+"&orderId="+orderId+"&equipmentId="+equipmentId;
					//alert("url:"+url);
					var obj =new Object();
					window.showModalDialog(url ,obj , "status=no;center=yes;dialogWidth=550px;dialogHeight=400px" );
				};
			
</script>
</html>
