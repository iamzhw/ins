
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>关键点巡线</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr>
						<td width="10%">选择日期：</td>
						<td>
							<div class="condition-text-container">
								<input name="param_date" id="param_date" type="text" class="condition-text" onClick="WdatePicker({disabledDays:[0,1,2,3,4,5]});" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:left;" class="btn-operation"
				onClick="serchData()">查询</div>
			<div style="float: left;" class="btn-operation"
				onClick="keyPointDownLoad()">导出</div>
		</div>
	</div>
	
	<div id="dg" style="width: 98%"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
</body>
	<script type="text/javascript">
		function serchData(){
			var dateValue=$("input[name='param_date']").val();
			if(dateValue==''){
				alert("请填写日期");
				return;
			}	
			var obj=makeParamJson('form');
			$('#dg').datagrid({
				autoSize : true,
				queryParams: obj,
				url : webPath + "/keyPoint/keyPointList.do",
				fitColumns:true,
				method : 'post',
				rownumbers : true,
				singleSelect : false,
				nowrap : false,
				striped : true,
				columns : [[ 
						{field:'NAME',title:'分公司',rowspan:2,width:50,align:'center'},
						{field:'',title:'关键点个数', colspan:2, width:100,align:'center'},
						{field:'MATH',title:'关键点巡检到位率',rowspan:2,width:50,align:'center'}
					],
					[ 
						{field:'GRADEONE',title:'一干',width:50,align:'center'},
						{field:'GRADETWO',title:'二干',width:50,align:'center'}
					]
				]
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
							res=res+"&"+name+"="+o.find("option:selected").val();
						}
						
					} else if (tag == "input") {
						res=res+"&"+name+"="+ o.val();
					}
				}
			});
			return res;
		}
		
		//报表导出
		function keyPointDownLoad(){
			var dateValue=$("input[name='param_date']").val();
			if(dateValue==''){
				alert("请填写日期");
				return;
			}	
			window.open(webPath + "keyPoint/KeyPointInfoDownload.do"+getParamsForDownloadLocal('form'));
		}
		
	</script>
</html>
