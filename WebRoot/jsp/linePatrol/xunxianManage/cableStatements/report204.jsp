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
								type="text" value="${yearPart }" readonly="readonly" />
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
		<div style="float: left;" class="btn-operation" onClick="serchData()">查询</div>
		<div style="float: left;" class="btn-operation" onClick="delReport204()">删除</div>
		<div style="float: left;" class="btn-operation" onClick="toUpdReport204()">修改</div>
		<div style="float: left;" class="btn-operation" onClick="toAddReport204()">增加</div>
<!-- 		<div style="float: left;" class="btn-operation" onClick="serchCollection()">查看统计表</div> -->
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
		var obj=makeParamJson('form');
		$('#dg').datagrid({
			fitColumns:true,
			autoSize : true,
			queryParams:obj,
			url : webPath + "/CableStatementsController/report204Query.do",
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
					{checkbox:true,field:'R_ID',title:'',width:50,align:'center'},
					{field:'UNIT',title:'填报单位',width:50,align:'center'},
					{field:'CABLE_NAME',title:'干线',width:50,align:'center'},
					{field:'RELAY_NAME',title:'中继段',width:50,align:'center'},
					{field:'TESTDATE',title:'测试日期',width:50,align:'center'},
					{field:'WEATHER',title:'天气',width:50,align:'center'},
					{field:'POLENUM',title:'杆号',width:50,align:'center'},
					{field:'RESISTANCE',title:'接地电阻值',width:50,align:'center'},
				]	
			]
//				onLoadSuccess:function(data){
			
//				}
		});
		
	});
		
			function serchData(){
				var obj=makeParamJson('form');
				$('#dg').datagrid('load',obj);
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
		
		
// 		function serchCollection(){
// 			addTab("查看统计表",webPath+ "/CableStatementsController/to204CollectionForm.do");
// 		}
		
		
		function delReport204(){
			var rows=$('#dg').datagrid('getChecked');
			if(!rows.length){
				$.messager.alert("提示","请勾选删除的内容");
				return;
			}
			var str="";
			for(var i=0;i<rows.length;i++){
				str+=rows[i].R_ID+",";
			}
			$.messager.confirm("提示","是否删除",function(i){
				if(i){
					$.ajax({
						url : webPath + "/CableStatementsController/delReport204.do",// 跳转到 action    
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
		
		
		function toUpdReport204(){
			var rows=$('#dg').datagrid('getChecked');
			if(!rows.length){
				$.messager.alert("提示","请勾选修改的内容");
				return;
			}else if(rows.length>1){
				$.messager.alert("提示","只能勾选一条");
				return;
			}
			var r_id = rows[0].R_ID;
 			$('#win').window({
				title : "测试记录修改",
				href : webPath+ "/CableStatementsController/toUpdReport204.do?r_id="+r_id,
				width : 900,
				height : 250,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		
		function toAddReport204(){
			$('#win').window({
				title : "测试记录新增",
				href : webPath+ "/CableStatementsController/toAddReport204.do?localId="+$("select[name='city_name']").val(),
				width : 1200,
				height : 300,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
	</script>
</html>
