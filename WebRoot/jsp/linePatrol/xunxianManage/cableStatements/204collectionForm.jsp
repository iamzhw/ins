<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title></title>
</head>
<body style="padding: 3px; border: 0px">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr id="tr_cityName">
						<td width="10%">填报单位：</td>
						<td width="20%">
							<div>
								<select name="city_name"  class="condition-select" onchange="getCable(this.value)">
									<c:forEach var="city" items="${cityModel }">
										<option value="${city.AREA_ID }" <c:if test="${city.AREA_ID eq localId }">selected</c:if>>${city.NAME }</option>
									</c:forEach>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>报表期间:</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition" name="yearPart"
								type="text" onClick="WdatePicker({skin:'default',dateFmt:'yyyy'});" />
							</div>
						</td>
						<td width="20%">
							<div>
								<select name="upOrDown"  class="condition-select">
									<option value='01'>--上半年--</option>
									<option value='02'>--下半年--</option>
								</select>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	<div class="btn-operation-container">
		<div style="float: left;" class="btn-operation" onClick="serchData()">查询</div>
	</div>	
	<div id="dg" style="width: 98%; height: 80%;"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<div id="win"></div>
</body>
	<script type="text/javascript">
		function serchData(){
			var obj=makeParamJson('form');
			$('#dg').datagrid({
				fitColumns:true,
				autoSize : true,
				queryParams:obj,
				url : webPath + "/CableStatementsController/get204collection.do",
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [
					[ 
						{field:'UNIT',title:'填报单位',width:50,align:'center'},
						{field:'TOTAL',title:'测量光纤数',width:50,align:'center'},
						{field:'PART1',title:'合格数',width:50,align:'center'},
						{field:'PART2',title:'不合格数',width:50,align:'center'},
						{field:'YIELD',title:'合格率',width:50,align:'center'},
						{field:'detail',title:'',width:50,align:'center',
							formatter: function(value,row,index){
								return "<a href='javascript:void(0)' onclick='showDetail()'>详情</a>&nbsp;&nbsp;";
							}
						}
					]	
				]
// 				onLoadSuccess:function(data){
				
// 				}
			});
		}
			
		function showDetail(){
			var city_name=$("select[name='city_name']").val();
			var yearPart="";
			if($("input[name='yearPart']").val()){
				yearPart=$("input[name='yearPart']").val()+""+$("select[name='upOrDown']").val();	
			}
			addTab("架空光缆详情表",webPath+ "/CableStatementsController/report204Init.do?city_name="+city_name+"&yearPart="+yearPart);
		}
		
	</script>
</html>
