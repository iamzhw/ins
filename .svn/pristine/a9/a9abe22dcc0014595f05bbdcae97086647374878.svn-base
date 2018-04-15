
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../util/head.jsp"%>
<title></title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【步巡设施管理】" style="width: 100%;">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
				<tr>
					<td width="10%">组织名称：</td>
						<td width="20%">
							<div class="">
								<select name="org_id" id="org_id"
									class="condition-select">
									<option value=''>--请选择--</option>
								</select>
							</div>
						</td>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name="p_start_time" id="p_start_time" 
								onClick="WdatePicker();" />
							</div>
						</td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name="p_end_time" id="p_end_time"
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'p_start_time\')}'});" />
							</div>
						</td>
					</tr>
					<tr id="tr_cityName">
						<td width="10%">步巡设施点名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_equip_name" id="p_equip_name" type="text"
									class="condition-text" />
							</div>
						</td>
						
						<td width="10%" style="display:none;">地市名称：</td>
						<td width="20%" style="display:none;">
							<div class="">
								<select name="area_id" id="area_id" onclick="getAllGroup(this.value)"
									class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach var="city" items="${cityModel }">
										<option value="${city.AREA_ID }" <c:if test="${city.AREA_ID eq localId }">selected</c:if>>${city.NAME }</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<td width="10%">巡线人：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input name="p_inspect_name" id="p_inspect_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">

		
			
			<div class="btn-operation" onClick="xxdReportByDown()">导出</div>

			<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win"></div>
	<div id="win_checkRecord"></div>
	
	

	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {

			if("${localId}"){
				getAllGroup("${localId}");	
			}
			else{
				$("#tr_cityName td").css("display",'');
			}
		});

		function getAllGroup(areaId){
			$("select[name='org_id']").find("option:not(:first)").remove();
			if(!areaId) return;
			$.ajax({
				url : webPath + "/xxdReportController/getAllGroup.do",  
				data : {
					areaId : areaId
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
				console.log('data:'+data);
					$(data).each(function(){
						$("select[name='org_id']").append("<option value="+this.ORG_ID+">"+this.ORG_NAME+"</option>");
					});
				}
			});
			
		}
		
		function searchData() {
                 if($("#p_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			
			if($("#p_end_time").val()==''){
				alert("截止日期不能为空！");
				return false;
			}
			var obj = makeParamJson('form');

			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				fitColumns:true,
				toolbar : '#tb',
				url : webPath + "xxdReportController/query.do",
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
					field : 'EQUIP_ID',
					title : '步巡设施ID',
					width : "10%",
					align : 'center'
				}, {
					field : 'EQUIP_CODE',
					title : '步巡设施编号',
					width : "10%",
					align : 'center'
				}, {
					field : 'EQUIP_TYPE',
					title : '步巡设施类型',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						if(value==1){
							return "标石";
						}else if(value==2){
							return "人井";
						}else if(value==3){
							return "地标";
						}else if(value==4){
							return "宣传牌";
						}else if(value==5){
							return "埋深点";
						}else if(value==6){
							return "电杆";
						}else if(value==7){
							return "警示牌";
						}else if(value==8){
							return "护坡";
						}else if(value==9){
							return "接头盒";
						}else{
						    return "非路由标志 ";
						}
						
					}
					
				}, {
					field : 'STATUS',
					title : '状态',
					width : "10%",
					align : 'center'
					
				}, {
					field : 'STAFF_NAME',
					title : '检查人',
					width : "8%",
					align : 'center'
					
				},{
					field : 'CHECK_TIME',
					title : '最新检查时间',
					width : "10%",
					align : 'center'
				},{
					field : 'opr',
					title : '操作',
					width : "20%",
					align : 'center',
					formatter : function(value, rec,
							index) {
							console.log(rec);
						return "&nbsp;&nbsp;<a  style='color:blue' onclick='getStepEquipLocation (\""
						+ rec.EQUIP_ID
						+ "\")'>地图位置</a>";
					}
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

		function clearCondition(form_id) {

			$("#" + form_id + "").form('reset', 'none');

		}

		

	

		
		
		
		
	
		
	
		function getStepEquipLocation(id){
		
			addTab("地图位置" ,webPath + "xxdReportController/getStepEquipLocation.do?equip_id="+id);
		
		}
		
		
		
		
	
		
		function getParamsForDownloadLocal(idOrDom) {
			if (!idOrDom) {
				return;
			}
			var o;
			if (typeof idOrDom == "string") {
				o = $("#" + idOrDom);
			} else {
				o = $(idOrDom);
			}
			var res = "?randomPara=1";
			o
					.find("input,select")
					.each(
							function() {
								var o = $(this);
								var tag = this.tagName.toLowerCase();
								var name = o.attr("name");
								if (name) {
									if (tag == "select") {
										if (o.find("option:selected").val() == 'all'
												|| o.find("option:selected")
														.val() == '') {
											res = res + "&" + name + "=";
										} else {
											res = res
													+ "&"
													+ name
													+ "="
													+ o.find("option:selected")
															.val();
										}

									} else if (tag == "input") {
										res = res + "&" + name + "=" + o.val();
									}
								}
							});
			return res;
		}

		function xxdReportByDown() {
		if($("#p_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#p_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			window.open(webPath
					+ "xxdReportController/stepequipdDownload.do"
					+ getParamsForDownloadLocal('form'));
		}
	</script>

</body>
</html>
