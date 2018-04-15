
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title></title>
	</head>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />

		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
						<td width="10%">地市：</td>
						<td width="20%"><select name="area" class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${areaList}" var="al">
									<option value="${al.AREA_ID}">${al.NAME}</option>
								</c:forEach>
						</select></td>

							<td width="10%">
								光缆段：
							</td>
							<td width="20%">
								<div>
									<select name="p_cable_id" id="p_cable_id"
										class="condition-select" onchange="getRelay(this.value)">
										<option value=''>
											--请选择--
										</option>
										<c:forEach items="${cableList}" var="res">
											<option value='${res.CABLE_ID}'>
												${res.CABLE_NAME}
											</option>
										</c:forEach>
									</select>
								</div>
							</td>
							<td width="10%">
								中继段：
							</td>
							<td width="20%">
								<div>
									<select name="p_relay_id" id="p_relay_id"
										class="condition-select">
									</select>
								</div>
							</td>

						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div style="float: left;" class="btn-operation"
					onClick="inspectArDownload()">
					导出
				</div>
				<div style="float: right;" class="btn-operation"
					onClick="searchData()">
					查询
				</div>
			</div>
		</div>
		<table id="dg" title="" style="width: 100%; height: 480px">
		</table>
		<div id="win"></div>
		<div id="win_distribute"></div>

		<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
		<script type="text/javascript">
		$(document).ready(function() {
			selectSelected();
			searchData();
		});

		function searchData() {
		

			var obj = makeParamJson('form');

			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "xxdReportController/getCableRoutingFacility.do",
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
					field : 'CABLE_NAME',
					title : '光缆名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'RELAY_NAME',
					title : '中继段名称',
					width : "8%",
					align : 'center'
				}, {
					field : 'MARKSTONE',
					title : '标石',
					width : "8%",
					align : 'center'
				}, {
					field : 'LANDMARK',
					title : '地标',
					width : "8%",
					align : 'center'
				}, {
					field : 'POLE',
					title : '电杆',
					width : "8%",
					align : 'center'  
				},{
					field : 'BURIED',
					title : '埋深点',
					width : "8%",
					align : 'center'
				},{
					field : 'ROUNTFLAG',
					title : '非路由标志',
					width : "8%",
					align : 'center'
				},{
					field : 'SCARP_PROTECTION',
					title : '护坎（坡）',
					width : "8%",
					align : 'center'
				},{
					field : 'CLOSURE',
					title : '接头盒',
					width : "8%",
					align : 'center'
				},{
					field : 'CAUTION',
					title : '警示牌',
					width : "8%",
					align : 'center'
				},{
					field : 'WELLS',
					title : '人井',
					width : "8%",
					align : 'center'
				},{
					field : 'BILLBOARD',
					title : '宣传牌',
					width : "8%",
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

		function clearCondition(form_id) {

			$("#" + form_id + "").form('reset', 'none');

		}

		
		
		function selectSelected(){
			$.ajax({
				type : 'POST',
				url : webPath + "Staff/selectSelected.do",
				dataType : 'json',
				async:false,
				success : function(json) {
					if(json[0].ifGly==1){			
						$("#ifGLY").val("0");
					}else{
						$("select[name='area']").val(${areaId});
						$("select[name='area']").attr("disabled","disabled");
						$("#ifGLY").val("1");
					}
				}
			});
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
			o.find("input,select").each(function() {
				var o = $(this);
				var tag = this.tagName.toLowerCase();
				var name = o.attr("name");
				if (name) {
					if (tag == "select") {
						if(o.find("option:selected").val()=='all' || o.find("option:selected").val()=='' ){
							res=res+"&"+name+"=";
						}else{
						     var value = o.find("option:selected").val();
						     if(value==undefined){
						        value='';
						     }
							res=res+"&"+name+"="+value;
						}
						
					} else if (tag == "input") {
			 
						res=res+"&"+name+"="+ o.val();
					}
				}
			});
			return res;
		}
	
		
		
	function getRelay(cable_id){
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "lineInfoController/getRelay.do",
				  data:{cable_id:cable_id},
				  dataType:"json",
				  success:function(data){
					  $("#p_relay_id").empty();
					  $("#p_relay_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.relayList,function(i,item){
						  $("#p_relay_id").append("<option value='"+item.RELAY_ID+"'>"+item.RELAY_NAME+"</option>");		
					  });
				  }
			  });
		}
		
		
		
		function inspectArDownload(){
	
		
			
			window.open(webPath + "xxdReportController/getCableRoutingFacilityDown.do"+getParamsForDownloadLocal('form'));
		}
		
	</script>

	</body>
</html>

