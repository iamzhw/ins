
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>设施密度</title>
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
							<td width="20%"><select name="area" id="area_id" class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${areaList}" var="al">
									<option value="${al.AREA_ID}">${al.NAME}</option>
								</c:forEach>
						</select></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">

				<div style="float: left;" class="btn-operation"
					onClick=inspectArDownload();>
					导出
				</div>
				<div style="float: right;" class="btn-operation"
					onClick=searchData();>
					查询
				</div>
			</div>
		</div>
		<table id="dg" title="【】" style="width: 100%" class="common-table">
			<thead id="tsh">

			</thead>
			<tbody id="tsb">
			</tbody>
		</table>


		<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
		<script type="text/javascript">
	$(document).ready(function() {
		selectSelected();
		searchData();
		});

	function searchData() {
		

		var obj = makeParamJson('form');

		//return;
		$('#dg').datagrid( {
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			fitColumns : true,
			toolbar : '#tb',
			url : webPath + "xxdReportController/getFacilityDensity.do",
			queryParams : obj,
			method : 'post',
			rownumbers : true,
			singleSelect : false,
			columns : [ [ {
				field : 'NAME',
				title : '分公司',
				width : 80,
				align : 'center'
			}, {
				field : 'MARKSTONE',
				title : '标石间距超标（>50米）',
				width : 80,
				align : 'center'
			}, {
				field : 'LANDMARK',
				title : '地标间距超（>7米）',
				width : 80,
				align : 'center'
			}, {
				field : 'BILLBOARD',
				title : '宣传牌间距超标（>200米）',
				width : 80,
				align : 'center'
			} ,{
					field : 'PLANID',
					title : '操作',
					width : "6%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
	              	return "<a  style='color:blue' onclick='flowDetailUI(\""
						+ rowData.AREA_ID
						+ "\")'>详情</a>";
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
	

	function flowDetailUI(id){
			addTab("设施密度详情",webPath+ "xxdReportController/getDetailUI_index.do?area_id="+ id);
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
		o.find("input,select").each(
				function() {
					var o = $(this);
					var tag = this.tagName.toLowerCase();
					var name = o.attr("name");
					if (name) {
						if (tag == "select") {
							if (o.find("option:selected").val() == 'all'
									|| o.find("option:selected").val() == '') {
								res = res + "&" + name + "=";
							} else {
								res = res + "&" + name + "="
										+ o.find("option:selected").val();
							}

						} else if (tag == "input") {
							res = res + "&" + name + "=" + o.val();
						}
					}
				});
		return res;
	}

	function inspectArDownload() {
		
		window.open(webPath
				+ "xxdReportController/getFacilityDensityDown.do"
				+ getParamsForDownloadLocal('form'));
	}
</script>

	</body>
</html>
