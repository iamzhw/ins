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
	<form id="detailForm"  method="post">
		<table class="condition">
			<tr id="tr_cityName">
				<td width="10%">填报单位：</td>
				<td width="20%">
					<div>
						<select name="city_name" class="condition-select"
							onchange="getCable(this.value)">
							<c:forEach var="city" items="${cityModel }">
								<option value="${city.AREA_ID }"
									<c:if test="${city.AREA_ID eq localId }">selected</c:if>>${city.NAME }</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<td width="10%">报表期间:</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" name="yearPart" type="text" onClick="WdatePicker({skin:'default',dateFmt:'yyyy'});" />
					</div>
				</td>
				<td width="20%">
					<div>
						<select name="upOrDown" class="condition-select">
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
	<div style="float: left;" class="btn-operation" onclick="searchDetailData()">查询</div>
</div>
<div id="detailDG" style="width: 98%; height: 80%;"></div>
</body>
<script type="text/javascript">
function searchDetailData(){ 
	var obj=makeParamJson('detailForm');
	$('#detailDG').datagrid({
		fitColumns:true,
		autoSize : true,
		queryParams:obj,
		url : webPath + "/CableStatementsController/getFYPbyPart.do",
		method : 'post',
		pagination : false,
		pageNumber : 1,
		pageSize : 10,
		pageList : [ 20, 50 ],
		//loadMsg:'数据加载中.....',
		rownumbers : false,
		singleSelect : false,
		columns : [
			[  
				{field:'NAME',title:'局名',width:50,align:'center',rowspan:2},
				{field:'',title:'一线干线',width:50,align:'center',colspan:4},
				{field:'',title:'二线干线',width:50,align:'center',colspan:4},
				{field:'',title:'合计',width:50,align:'center',colspan:4},
				{field:'detail',title:'',width:50,align:'center',rowspan:2,
					formatter: function(value,row,index){
						return "<a href='javascript:void(0)' onclick='showDetail()'>详情</a>";
					}
				}
			],
			[
				{field:'TOTALOFLINE1',title:'应测光纤数',width:50,align:'center'},
				{field:'PART1OFLINE1',title:'合格数',width:50,align:'center'},
				{field:'PART2OFLINE1',title:'不合格数',width:50,align:'center'},
				{field:'PEROFLINE1',title:'合格率',width:50,align:'center'},
				
				{field:'TOTALOFLINE2',title:'应测光纤数',width:50,align:'center'},
				{field:'PART1OFLINE2',title:'合格数',width:50,align:'center'},
				{field:'PART2OFLINE2',title:'不合格数',width:50,align:'center'},
				{field:'PEROFLINE2',title:'合格率',width:50,align:'center'},
				
				{field:'TOTAL',title:'应测光纤数',width:50,align:'center'},
				{field:'PART1',title:'合格数',width:50,align:'center'},
				{field:'PART2',title:'不合格数',width:50,align:'center'},
				{field:'PER',title:'合格率',width:50,align:'center'}
			]
		]
// 			onLoadSuccess:function(data){
// 				alert(typeof(data));
// 			}
	});
}

function showDetail(){
	var yearPart="";
	if($("input[name='yearPart']").val()){
		yearPart+=$("input[name='yearPart']").val()+""+$("select[name='upOrDown']").val();
	}
	addTab("信号曲线检查详情表",webPath+ "/CableStatementsController/report203Init.do?localId="+$("select[name='city_name']").val()+"&yearPart="+yearPart);
}

</script>
</html>

