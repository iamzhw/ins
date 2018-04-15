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
			<input name="testdate" value="${testdate }"/>
				<table class="condition">
					<tr id="tr_cityName">
						<td width="10%">填报单位：</td>
						<td width="20%">
							<div>
								<select name="city_name"  class="condition-select" onchange="getCable(this.value)">
									<c:forEach var="city" items="${cityModel }">
										<option value="${city.AREA_ID }">${city.NAME }</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<td width="10%">线路段名：</td>
						<td width="20%">
							<div>
								<select name="cable_name"  class="condition-select" onchange="getRelay(this.value)">
								</select>
							</div>
						</td>
						<td width="10%">中继段名：</td>
						<td width="20%">
							<div>
								<select name="relay_name"  class="condition-select">
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>报表期间:</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition" name="yearPart"
								type="text" value="${yearPart }" readonly="readonly"/>
							</div>
						</td>
						<td width="20%">
							<div>
								<select name="upOrDown"  class="condition-select">
									<c:if test="${upOrDown eq '01' }">
										<option value='01'>--上半年--</option>	
									</c:if>
									<c:if test="${upOrDown eq '02' }">
										<option value='02'>--下半年--</option>
									</c:if>
								</select>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	<div class="btn-operation-container">
<!-- 		<div style="float: left;" class="btn-operation" onClick="serchData()">查询</div> -->
		<div style="float: left;" class="btn-operation" onClick="delReport207()">删除</div>
		<div style="float: left;" class="btn-operation" onClick="toUpdReport207()">修改</div>
<!-- 		<div style="float: left;" class="btn-operation" onClick="serchCollection()">查看统计表</div> -->
	</div>	
	<div id="dg" style="width: 98%; height: 80%;"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<div id="win"></div>
</body>
	<script type="text/javascript">
	$(function(){
// 		if($("select[name='city_name']").val()){
// 			getCable($("select[name='city_name']").val());
// 		}

		getCable($("select[name='city_name']").val());

		
	});
		
	function init(){
		var obj=makeParamJson('form');
		$('#dg').datagrid({
			fitColumns:true,
			autoSize : true,
			queryParams:obj,
			url : webPath + "/CableStatementsController/report207Query.do",
			method : 'post',
			pagination : true,
			pageNumber : 1,
			pageSize : 10,
			pageList : [ 20, 50 ],
			//loadMsg:'数据加载中.....',
			rownumbers : true,
			singleSelect : false,
			columns : [
				[ 
					{checkbox:true,field:'SUBID',title:'',width:50,align:'center'},
					{field:'CABLE_NAME',title:'线路名',width:50,align:'center'},
					{field:'RELAY_NAME',title:'中继段名',width:50,align:'center'},
					{field:'RESISTANCE',title:'金属护套对地绝缘电阻',width:50,align:'center'},
					{field:'RESISTANCESET',title:'接头盒检测电极间绝缘电阻',width:50,align:'center'},
					{field:'TESTDATE',title:'测试日期',width:50,align:'center'},
					{field:'TESTMETER',title:'测试仪器',width:50,align:'center'},
					{field:'WEATHER',title:'天气',width:50,align:'center'},
					{field:'FIBERLENGTHATNOW',title:'中继段长度(KM)',width:50,align:'center'},
					{field:'TESTLINELENGTH',title:'监测段缆长(KM)',width:50,align:'center'},
					{field:'TESTNUMBER',title:'监测标石号',width:50,align:'center'},
					{field:'TESTSTARTEND',title:'起/止接头标石号',width:50,align:'center'},
					{field:'EDITDATE',title:'填报日期',width:50,align:'center'},
					{field:'MOME',title:'备注',width:50,align:'center'}
				]	
			]
//				onLoadSuccess:function(data){
			
//				}
		});
		}
	
			
		
		//根据地区获得地区光缆
		function getCable(areaId){
			$("select[name='cable_name']").find("option").remove();
			$("select[name='relay_name']").find("option").remove();
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
						if("${cable_name}"==this.CABLE_ID){
							$("select[name='cable_name']").append("<option value="+this.CABLE_ID+">"+this.CABLE_NAME+"</option>");	
						}
					});
					getRelay("${cable_name}");
				}
			});
		}
		
		//根据光缆获得中继段
		function getRelay(cableId){
			$("select[name='relay_name']").find("option").remove();
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
						if("${relay_name}"==this.RELAY_ID){
							$("select[name='relay_name']").append("<option value="+this.RELAY_ID+">"+this.RELAY_NAME+"</option>");	
						}
					});
					init();
				}
			});
		}
		
		
// 		function serchCollection(){
// 			addTab("查看统计表",webPath+ "/CableStatementsController/report207Init.do");
// 		}
		
		
		function delReport207(){
			var rows=$('#dg').datagrid('getChecked');
			if(!rows.length){
				$.messager.alert("提示","请勾选删除的内容");
				return;
			}
			var str="";
			for(var i=0;i<rows.length;i++){
				str+=rows[i].SUBID+",";
			}
			$.messager.confirm("提示","是否删除",function(i){
				if(i){
					$.ajax({
						url : webPath + "/CableStatementsController/delReport207.do",// 跳转到 action    
						data : {
							"str" : str
						},
						type : 'post',
						cache : false,
						dataType : 'json',
						success : function(data) {
							if(data.status){
								$('#dg').datagrid('reload');
								$.messager.alert("提示","删除成功");
							}else{
								$.messager.alert("提示","删除失败");
							}
						}
					});
				}
			});
		}
		
		
		function toUpdReport207(){
			var rows=$('#dg').datagrid('getChecked');
			if(!rows.length){
				$.messager.alert("提示","请勾选修改的内容");
				return;
			}else if(rows.length>1){
				$.messager.alert("提示","只能勾选一条");
				return;
			}
 			var r_id=rows[0].SUBID;
 			$('#win').window({
				title : "测试记录修改",
				href : webPath+ "/CableStatementsController/toUpdReport207.do?r_id="+r_id,
				width : 1100,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
	</script>
</html>
