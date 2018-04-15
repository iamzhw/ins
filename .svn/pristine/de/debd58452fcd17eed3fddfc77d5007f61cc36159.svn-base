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
			<form id="form"  method="post">
				<table class="condition">
					<tr id="tr_cityName">
						<td width="10%">地区：</td>
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
								<select name="cable_id"  class="condition-select" onchange="getRelay(this.value)">
									<option value=''>--请选择--</option>
								</select>
							</div>
						</td>
						<td width="10%">中继段名：</td>
						<td width="20%">
							<div>
								<select name="relay_id"  class="condition-select">
									<option value=''>--请选择--</option>
								</select>
							</div>
						</td>
						<td width="10%">设施类型：</td>
						<td width="20%">
							<div>
								<select name="equip_type"  class="condition-select">
									<option value="">请选择</option>
									<option value="1">标石</option>
									<option value="2">人井</option>
									<option value="3">地标</option>
									<option value="4">宣传牌</option>
									<option value="5">埋深点</option>
									<option value="6">电杆</option>
									<option value="7">警示牌</option>
									<option value="8">护坡</option>
									<option value="9">接头盒</option>
									<option value="10">非路由标志</option>
								</select>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	<div class="btn-operation-container">
		<div style="float: left;" class="btn-operation" onClick="serchData()">查询</div>
		<div style="float: left;" class="btn-operation" onClick="showByMap()">设施段展示</div>
	</div>	
	<div id="dg" style="width: 98%; height: 80%;"></div>
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
				url : webPath + "/CollectInfoOfRelay/selEquipListForWEB.do",
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
						{field:'EQUIP_ID',title:'',width:50,align:'center',hidden:false},
						{field:'EQUIP_CODE',title:'设施编号',width:30,align:'center'},
						{field:'EQUIP_TYPE',title:'设施类型',width:50,align:'center',
							formatter: function(value,row,index){
								switch(value){
									case 1:
										return "标石";
									break;
									case 2:
										return "人井";
									break;
									case 3:
										return "地标";
									break;
									case 4:
										return "宣传牌";
									break;
									case 5:
										return "埋深点";
									break;
									case 6:
										return "电杆";
									break;
									case 7:
										return "警示牌";
									break;
									case 8:
										return "护坡";
									break;
									case 9:
										return "接头盒";
									break;
									case 10:
										return "非路由标志";
									break;
								}
							}
						},
						{field:'OWNER_NAME',title:'户主姓名',width:50,align:'center'},
						{field:'OWNER_TEL',title:'户主电话',width:50,align:'center'},
						{field:'PROTECTER',title:'义务护线员姓名',width:50,align:'center'},
						{field:'PROTECT_TEL',title:'义务护线员电话',width:50,align:'center'},
						
						{field:'IS_EQUIP',title:'是否为路由设施',width:50,align:'center',
							formatter: function(value,row,index){
								if(value==1){
									return "是";
								}else{
									return "否";
								}
							}
						},
						{field:'IS_LOSE',title:'是否为缺失路由设施',width:50,align:'center',
							formatter: function(value,row,index){
								if(value==1){
									return "是";
								}else{
									return "否";
								}
							}
						},
						
						{field:'detail',title:'',width:50,align:'center',
							formatter: function(value,row,index){
								return "<a href='javascript:void(0)' onclick='showDetail("+row.EQUIP_ID+")'>详情</a>"
							}
	
						}
					]	
				]
//					onLoadSuccess:function(data){
				
//					}
			});
		}
			
		
		//根据地区获得地区光缆
		function getCable(areaId){
			$("select[name='cable_id']").find("option:not(:first)").remove();
			$("select[name='relay_id']").find("option:not(:first)").remove();
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
						$("select[name='cable_id']").append("<option value="+this.CABLE_ID+">"+this.CABLE_NAME+"</option>");
					});
					getRelay($("select[name='cable_id']").val());
				}
			});
		}
		
		//根据光缆获得中继段
		function getRelay(cableId){
			$("select[name='relay_id']").find("option:not(:first)").remove();
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
						$("select[name='relay_id']").append("<option value="+this.RELAY_ID+">"+this.RELAY_NAME+"</option>");
					});
				}
			});
		}
		
		function showDetail(equip_id){
			$('#win').window({
				title : "设施详情页",
				href : webPath+ "/CollectInfoOfRelay/showDetail.do?equip_id="+equip_id,
				width : 900,
				height : 200,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		
// 		地图页面展示设施点
		function showByMap(){
			var area_id=$("select[name='city_name']").val();
			if(!area_id){
				return ;
			}
			var obj=makeParamJson('form');
			var jsonStr = JSON.stringify(obj);
			location.href="showByMap.do?jsonStr="+jsonStr ;
		}
		
	</script>
</html>
