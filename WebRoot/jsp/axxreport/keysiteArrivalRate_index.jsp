
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
						<td width="10%">开始日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="start_time" id="start_time" onFocus="WdatePicker()" type="text" class="condition-text" />
							</div></td>
						
						<td width="10%">结束日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_time" id="end_time" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'})" type="text" class="condition-text" />
							</div></td>
							
							<td width="10%">组织名称：</td>
							<td width="20%">
							<div>
								<select name="org_id" id="org_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${orgList}" var="org">
										<option value='${org.ORG_ID}'>${org.ORG_NAME}</option>
									</c:forEach>
								</select>
							</div>
						   </td>
							<td width="10%">巡检人：</td>
						<td width="20%">
							<div>
								<select name="inspect_id" id="inspect_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${localInspactStaff}" var="res">
										<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
									</c:forEach>
								</select>
							</div>
							
							</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			
			<div style="float: left;" class="btn-operation"
				onClick="keysiteArDownload()">导出</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	
    <table id="dg" title="【】" style="width:100%" class="common-table">
		<thead id="tsh">
		   
		  </thead>
		<tbody id="tsb">
		</tbody>
	</table>
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		
			//searchData();
		});
			
		/*** 在整个页面创建加载遮盖层
		 */
		function createMaskBody(){
			$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:1000}).appendTo(window.top.document.body); 
			$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理数据，请稍候。。。").appendTo(window.top.document.body).css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 1.5});
		}
		/**
		 * 取消整个页面遮盖层
		 * @return
		 */
		function cancelMaskBody(){
			 $(window.top.document.body).find(".datagrid-mask-msg").remove();  
			 $(window.top.document.body).find(".datagrid-mask").remove(); 
		}	
		function searchData() {
			
			if($("#start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			var obj=makeParamJson('form');
			
		
			$.ajax({          
				  type:"post",
				  url :webPath + "xxdReportController/getKeysiteArrate.do",
				  data:obj,
				  dataType:"json",
				  beforeSend: function(XMLHttpRequest) {
              	  	createMaskBody();
          		  },
				  success:function(data){
				  	  cancelMaskBody();
					  $("#tsh").empty();
					  $("#tsb").empty();
					//标题
					   $("#tsh").append("<tr><td rowspan='2'>巡线员</td><td rowspan='2'>区域</td><td colspan='2'>一干</td><td colspan='2'>二干</td><td rowspan='2'>关键点到位率</td><td rowspan='2'>全市关键点平均到位率</td></tr><tr><td>计划关键点巡检数</td><td>实际关键点巡检数</td><td>计划关键点巡检数</td><td>实际关键点巡检数</td></tr>");
					 //数据
					   $.each(data.data,function(i,item){
						 $("#tsb").append("<tr><td>"+item.STAFF_NAME+"</td><td>"+item.ORG_NAME+"</td><td>"+item.GP1+"</td><td>"+item.GA1+"</td><td>"+item.GP2+"</td><td>"+item.GA2+"</td><td>"+item.ARRIVAL_RATE+"</td><td>"+item.AVG_ARRIVAL_RATE+"</td></tr>");
					   });
				  }
				  
			  });
			
		}
		
		
 
		
		
		
		function clearCondition(form_id) {
			
			$("#"+form_id+"").form('reset', 'none');
			
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

		
		function keysiteArDownload(){
			
			window.open(webPath + "xxdReportController/keysiteArDownload.do"+getParamsForDownloadLocal('form'));
		}
	</script>

</body>
</html>
