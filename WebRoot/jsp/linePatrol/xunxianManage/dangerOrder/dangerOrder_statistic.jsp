
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>隐患单统计</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />

	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">月份：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="static_month" id="static_month"
									onFocus="WdatePicker({dateFmt:'yyyy-MM'})" type="text"
									class="condition-text" />
							</div>
						</td>
						<c:if test="${isAdmin==0}">
						     <td width="10%">地区：</td>
						<td width="20%">
							<div>
								<select name="area_id" id="area_id"
									class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${areaList}" var="res">
										<option value='${res.AREA_ID}'>${res.NAME}</option>
									</c:forEach>
								</select>
							</div></td>
						</c:if>
						<c:if test="${isAdmin==1}">
						   <td width="10%">巡检人：</td>
						<td width="20%">
							<div>
								<select name="check_person" id="check_person"
									class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${localInspacPeople}" var="res">
										<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
									</c:forEach>
								</select>
							</div></td>
						
						</c:if>
						
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">


			<div style="float: left;" class="btn-operation"
				onClick="doDownload(${isAdmin})">导出</div>
			<div style="float: right;" class="btn-operation"
				onClick="search(${isAdmin})">查询</div>
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
		
		function search(isAdmin){
			if(isAdmin==0){//0全省
				searchByAdmin();
			}else{
				searchData();
			}
		}
		
		function searchData() {
			var static_month=$("#static_month").val();
			if(static_month==''){
				alert("必须选择月份！");
				return false;
			}
			
			var obj=makeParamJson('form');
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "dangerOrderController/doStatistic.do",
				  data:obj,
				  dataType:"json",
				  success:function(data){
					  $("#tsh").empty();
					  $("#tsb").empty();
					 //标题
					  $("#tsh").append("<tr id='ts'></tr>");
					   $.each(data.title,function(i,item){
						   
						   $("#ts").append("<td>"+item+"</td>");
					   });
					 //数据
					   $.each(data.data,function(i,item){
						   if(data.count==4){
							   $("#tsb").append("<tr><td>"+item.FOUNDER_NAME+"</td><td>"+item.W1+"</td><td>"+item.W2+"</td><td>"+item.W3+"</td><td>"+item.W4+"</td></tr>"); 
						   }else{
							   $("#tsb").append("<tr><td>"+item.FOUNDER_NAME+"</td><td>"+item.W1+"</td><td>"+item.W2+"</td><td>"+item.W3+"</td><td>"+item.W4+"</td><td>"+item.W5+"</td></tr>");
						   }
						  
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
		
		
		function doDownload(isAdmin){
			
			var static_month=$("#static_month").val();
			if(static_month==''){
				alert("必须选择月份！");
				return false;
			}
			if(isAdmin==1){
				window.open(webPath + "dangerOrderController/doDownload.do"+getParamsForDownloadLocal('form'));
			}else{
				window.open(webPath + "dangerOrderController/downloadByAdmin.do"+getParamsForDownloadLocal('form'));
			}
			
		}
		
		
		function searchByAdmin(){
			
			var static_month=$("#static_month").val();
			if(static_month==''){
				alert("必须选择月份！");
				return false;
			}
			
			var obj=makeParamJson('form');
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "dangerOrderController/searchByAdmin.do",
				  data:obj,
				  dataType:"json",
				  success:function(data){
					  $("#tsh").empty();
					  $("#tsb").empty();
					  if(data.count==4){
						//标题
						   $("#tsh").append("<tr><td  rowspan='2'>地区</td><td  rowspan='2'>巡线员总数</td><td colspan='3'>"+data.title[1]+"</td><td colspan='3'>"+data.title[2]+"</td><td colspan='3'>"+data.title[3]+"</td><td colspan='3'>"+data.title[4]+"</td></tr><tr><td >上报数量</td><td >未上报数量</td><td >扣分</td><td >上报数量</td><td >未上报数量</td><td >扣分</td><td >上报数量</td><td >未上报数量</td><td >扣分</td><td >上报数量</td><td >未上报数量</td><td >扣分</td></tr>");
						 //数据
						   $.each(data.data,function(i,item){
							 $("#tsb").append("<tr><td>"+item.AREA_NAME+"</td><td>"+item.INSPACTOR_COUNT+"</td><td>"+item.W1+"</td><td>"+item.W11+"</td><td></td><td>"+item.W2+"</td><td>"+item.W22+"</td><td></td><td>"+item.W3+"</td><td>"+item.W33+"</td><td></td><td>"+item.W4+"</td><td>"+item.W44+"</td><td></td></tr>");
						   });
					  }else{
						//标题
						   $("#tsh").append("<tr><td  rowspan='2'>地区</td><td  rowspan='2'>巡线员总数</td><td colspan='3'>"+data.title[1]+"</td><td colspan='3'>"+data.title[2]+"</td><td colspan='3'>"+data.title[3]+"</td><td colspan='3'>"+data.title[4]+"</td><td colspan='3'>"+data.title[5]+"</td></tr><tr><td >上报数量</td><td >未上报数量</td><td >扣分</td><td >上报数量</td><td >未上报数量</td><td >扣分</td><td >上报数量</td><td >未上报数量</td><td >扣分</td><td >上报数量</td><td >未上报数量</td><td >扣分</td><td >上报数量</td><td >未上报数量</td><td >扣分</td></tr>");
						 //数据
						   $.each(data.data,function(i,item){
							 $("#tsb").append("<tr><td>"+item.AREA_NAME+"</td><td>"+item.INSPACTOR_COUNT+"</td><td>"+item.W1+"</td><td>"+item.W11+"</td><td></td><td>"+item.W2+"</td><td>"+item.W22+"</td><td></td><td>"+item.W3+"</td><td>"+item.W33+"</td><td></td><td>"+item.W4+"</td><td>"+item.W44+"</td><td></td><td>"+item.W5+"</td><td>"+item.W55+"</td><td></td></tr>");
						   });
					  }
					 
				  }
			  });
			
		}

	</script>

</body>
</html>
