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
						<td width="10%">线路段名：</td>
						<td width="20%">
							<div>
								<select name="cable_name"  class="condition-select" onchange="getRelay(this.value)">
									<option value=''>--请选择--</option>
								</select>
							</div>
						</td>
						<td width="10%">中继段名：</td>
						<td width="20%">
							<div>
								<select name="relay_name"  class="condition-select">
									<option value=''>--请选择--</option>
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
	$(function(){
		if($("select[name='city_name']").val()){
			getCable($("select[name='city_name']").val());
		}
	});
		function serchData(){
			var obj=makeParamJson('form');
			$('#dg').datagrid({
				fitColumns:true,
				autoSize : true,
				queryParams:obj,
				url : webPath + "/CableStatementsController/get207collection.do",
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
// 						{field:'MAINID',title:'',width:50,align:'center',hidden:true},
						{field:'LINEID',title:'',width:50,align:'center',hidden:true},
						{field:'RELAYID',title:'',width:50,align:'center',hidden:true},
						{field:'CABLE_NAME',title:'线路名',width:50,align:'center'},
						{field:'RELAY_NAME',title:'中继段名',width:50,align:'center'},
						{field:'TESTDATE',title:'测试日期',width:50,align:'center'},
						{field:'TESTER',title:'测试人',width:50,align:'center'},
						{field:'TOTAL',title:'总测试数',width:50,align:'center'},
						{field:'PART1',title:'合格数',width:50,align:'center'},
						{field:'PART2',title:'不合格数',width:50,align:'center'},
						{field:'YIELD',title:'合格率',width:50,align:'center'},
						{field:'detail',title:'',width:50,align:'center',
							formatter: function(value,row,index){
								var date=row.TESTDATE;
								var testdate='';
								if(date){
									 testdate=date.replace(/-/g,'');
								}
								return "<a href='javascript:void(0)' onclick='showDetail("+row.LINEID+","+row.RELAYID+","+testdate+")'>详情</a>&nbsp;&nbsp;";
							}	
						}
					]	
				]
// 				onLoadSuccess:function(data){
				
// 				}
			});
		}
			
		
		//根据地区获得地区光缆
		function getCable(areaId){
			$("select[name='cable_name']").find("option:not(:first)").remove();
			$("select[name='relay_name']").find("option:not(:first)").remove();
			if(!areaId) return;
			$.ajax({
				url : webPath + "/CutAndConnOfFiberController/getCable.do",// 跳转到 action    
				data : {
					areaId : areaId
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$(data).each(function(){
						$("select[name='cable_name']").append("<option value="+this.CABLE_ID+">"+this.CABLE_NAME+"</option>");
					});
				}
			});
		}
		
		//根据光缆获得中继段
		function getRelay(cableId){
			$("select[name='relay_name']").find("option:not(:first)").remove();
			if(!cableId) return;
			$.ajax({
				url : webPath + "/CutAndConnOfFiberController/getRelay.do",// 跳转到 action    
				data : {
					cableId : cableId
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$(data).each(function(){
						$("select[name='relay_name']").append("<option value="+this.RELAY_ID+">"+this.RELAY_NAME+"</option>");
					});
				}
			});
		}
		
		function showDetail(lineId,relayId,date){
			var testdate=""
			if(date){
				testdate=date.toString().substring(0,4)+"-";
				testdate+=date.toString().substring(4,6)+"-";
				testdate+=date.toString().substring(6,8);
			}
			var city_name=$("select[name='city_name']").val();
			var cable_name=lineId;
			var relay_name=relayId;
			var yearPart="";
			if($("input[name='yearPart']").val()){
				yearPart=$("input[name='yearPart']").val()+""+$("select[name='upOrDown']").val();	
			}
			addTab("绝缘电阻详情表",webPath+ "/CableStatementsController/report207Init.do?city_name="+
						city_name+"&cable_name="+cable_name+"&relay_name="+relay_name+"&yearPart="+yearPart+"&testdate="+testdate);
		}
		
	</script>
</html>
