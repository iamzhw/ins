<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>巡检设备维护</title>
</head>

<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container" style="height:140px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="equipIds" value="" />
				<input type="hidden" name="count" value="" />
				<input type="hidden" name="type" value="" />
				<table class="condition">
					<tr>
						<td align="left" width="15%">
							设备位置：
						</td>
						<td width="25%" align="left">
							<select name="equpAddress" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="路边">路边</option>
								<option value="小区">小区</option>
								<option value="交接间">交接间</option>
								<option value="网监">网监</option>
								<option value="其他">其他</option>
							</select>
						</td> 
						<td align="left" width="15%">
							填报方式：
						</td>
						<td align="left" width="25%">
							<select name="sn" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="PAD">PAD</option>
								<option value="PC">PC</option>
							</select>
						</td>
						<td align="left" width="15%">
							是否ODF动态：
						</td>
						<td align="left" width="25%">
							<select name="odfCheck" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="1">动态</option>
								<option value="null">非动态</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							巡检公司：
						</td>
						<td align="left" width="25%">
							<select name="ownCompany" class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${companyList}" var="v">
									<option value="${v.COMPANY_NAME}">${v.COMPANY_NAME}</option>
								</c:forEach>
							</select>
						</td>
						<td align="left" width="15%">
								巡检人员：
						</td>
						<td width="20%" align="left">
							<div class="condition-text-container">
							<input name="staffName" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							是否健康：
						</td>
						<td align="left" width="25%">
							<select name="state" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							资源编码：
						</td>
						<td width="20%" align="left">
							<div class="condition-text-container">
							<input name="equipmentCode" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							巡检起始时间：
						</td>
						<td width="20%" align="left">
							<div class="condition-text-container">
							<input name="startDate" class="condition-text" onclick="WdatePicker({el:'startDate'});"/>
							</div>
						</td>
						<td align="left" width="15%">
							巡检终止时间：
						</td>
						<td width="20%" align="left">
							<div class="condition-text-container">
							<input name="endDate" class="condition-text" onclick="WdatePicker({el:'endDate'});"/>
							</div>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							巡检类别：
						</td>
						<td align="left" width="25%">
							<select name="type" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="0">临时</option>
								<option value="1">任务</option>
							</select>
						</td>
						<td align="left" width="15%">
							健康标签起始时间：
						</td>
						<td width="20%" align="left">
							<div class="condition-text-container">
							<input name="startDate1" class="condition-text" onclick="WdatePicker({el:'startDate1'});"/>
							</div>
						</td>
						<td align="left" width="15%">
							巡检终止时间：
						</td>
						<td width="20%" align="left">
							<div class="condition-text-container">
							<input name="endDate1" class="condition-text" onclick="WdatePicker({el:'endDate1'});"/>
							</div>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							资源类别：
						</td>
						<td align="left" width="25%">
							<select name="resType" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="光交接箱">光交接箱</option>
								<option value="ODF">ODF</option>
							</select>
						</td>
						<td align="left" width="15%">
							巡检人员所属公司：
						</td>
						<td width="20%" align="left">
							<div class="condition-text-container">
							<input name="staffOwnCompany" class="condition-text" />
							</div>
						</td>
						
						<td align="left" width="15%">
							地区：
						</td>
						<td align="left" width="25%">
							<select name="areaName" class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${areaList}" var="v">
									<option value="${v.NAME}">${v.NAME}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<c:if test="${areaId eq '20'}">
					<tr>
						<td align="left" width="15%">
							区域：
						</td>
						<td align="left" width="25%">
							<select name="sonAreaName" class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${sonAreaList}" var="v">
									<option value="${v.NAME}">${v.NAME}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					</c:if>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:right;" class="btn-operation"  onClick="exportOrder()">导出Excel</div>
			<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【设备巡检记录】" style="width:100%;height:480px"></table>
	<div id="win_equip"></div>

	<script type="text/javascript">
	$(document).ready(function() {
			//searchData();
		});
		
		function searchData() {
			var equpAddress = $("select[name='equpAddress']").val();
			var sn = $("select[name='sn']").val();
			var odfCheck = $("select[name='odfCheck']").val();
			var resType = $("select[name='resType']").val();
			var areaName = $("select[name='areaName']").val();
			var sonAreaName = $("select[name='sonAreaName']").val();
			var ownCompany = $("select[name='ownCompany']").val();
			var state = $("select[name='state']").val();
			var type = $("select[name='type']").val();
			var staffName = $("input[name='staffName']").val();
			var equipmentCode = $("input[name='equipmentCode']").val();
			var startDate = $("input[name='startDate']").val();
			var endDate = $("input[name='endDate']").val();
			var startDate1 = $("input[name='startDate1']").val();
			var endDate1 = $("input[name='endDate1']").val();
			var staffOwnCompany = $("input[name='staffOwnCompany']").val();
			
			var obj = {
					equpAddress : equpAddress,
					sn : sn,
					odfCheck : odfCheck,
					resType : resType,
					areaName : areaName,
					sonAreaName : sonAreaName,
					ownCompany : ownCompany,
					state : state,
					type : type,
					staffName : staffName,
					equipmentCode : equipmentCode,
					startDate : startDate,
					endDate : endDate,
					startDate1 : startDate1,
					endDate1 : endDate1,
					staffOwnCompany : staffOwnCompany
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "equip/queryHis.do",
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
					field : 'EQUIPMENT_ID',
					title : '巡检设备ID',
					checkbox : true
				},{
					field : 'EQUIPMENT_CODE',
					title : '资源编码',
					width : "9%",
					align : 'center',
					styler:function(value,rowData,rowIndex){
						return 'word-break:break-all;word-wrap:break-word';
					}
				}, {
					field : 'EQUIPMENT_NAME',
					title : '资源名称',
					width : "8%",
					align : 'center'
				}, {
					field : 'RES_TYPE',
					title : '资源类别',
					width : "6%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区域',
					width : "6%",
					align : 'center'
				}, {
					field : 'EQUP_ADDRESS',
					title : '设备位置',
					width : "7%",
					align : 'center'
				}, {
					field : 'STATE',
					title : '健康状态',
					width : "5%",
					align : 'center'
				}, {
					field : 'STATE_CHANGE_DATE',
					title : '健康更新时间',
					width : "8%",
					align : 'center'
				}, {
					field : 'COUNT',
					title : '问题数量',
					width : "5%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '巡检人员',
					width : "6%",
					align : 'center'
				}, {
					field : 'OWN_COMPANY',
					title : '巡检公司',
					width : "7%",
					align : 'center'
				}, {
					field : 'STAFF_OWN_COMPANY',
					title : '所属公司',
					width : "8%",
					align : 'center'
				}, {
					field : 'CHECK_DATE',
					title : '巡检时间',
					width : "7%",
					align : 'center'
				}, {
					field : 'SN',
					title : '填报方式',
					width : "5%",
					align : 'center'
				}, {
					field : 'TYPE',
					title : '任务类型',
					width : "5%",
					align : 'center'
				}, {
					field : 'ODF_CHECK',
					title : '是否动态巡检',
					width : "8%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}
		
		
		function exportOrder() {
			var equpAddress = $("select[name='equpAddress']").val();
			var sn = $("select[name='sn']").val();
			var odfCheck = $("select[name='odfCheck']").val();
			var resType = $("select[name='resType']").val();
			var areaName = $("select[name='areaName']").val();
			var sonAreaName = $("select[name='sonAreaName']").val();
			var ownCompany = $("select[name='ownCompany']").val();
			var state = $("select[name='state']").val();
			var type = $("select[name='type']").val();
			var staffName = $("input[name='staffName']").val();
			var equipmentCode = $("input[name='equipmentCode']").val();
			var startDate = $("input[name='startDate']").val();
			var endDate = $("input[name='endDate']").val();
			var startDate1 = $("input[name='startDate1']").val();
			var endDate1 = $("input[name='endDate1']").val();
			var staffOwnCompany = $("input[name='staffOwnCompany']").val();
			
			var obj = {
					equpAddress : equpAddress,
					sn : sn,
					odfCheck : odfCheck,
					resType : resType,
					areaName : areaName,
					sonAreaName : sonAreaName,
					ownCompany : ownCompany,
					state : state,
					type : type,
					staffName : staffName,
					equipmentCode : equipmentCode,
					startDate : startDate,
					endDate : endDate,
					startDate1 : startDate1,
					endDate1 : endDate1,
					staffOwnCompany : staffOwnCompany
			};
			
			$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
	            if (r) {
	                
	                $('#form').form('submit', {
	                    url: webPath + "equip/exportHis.do",
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