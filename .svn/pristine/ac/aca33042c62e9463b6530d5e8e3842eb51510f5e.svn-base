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
						<td width="10%">地市：</td>
						<td width="20%">
							<div>
								<select name="city_id"  class="condition-select" onchange="getCable(this.value)">
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
<%--						<td width="10%">设施类型：</td>--%>
<%--						<td width="20%">--%>
<%--							<div>--%>
<%--								<select name="equip_type"  class="condition-select">--%>
<%--									<option value="">请选择</option>--%>
<%--									<option value="1">标石</option>--%>
<%--									<option value="2">人井</option>--%>
<%--									<option value="3">地标</option>--%>
<%--									<option value="4">宣传牌</option>--%>
<%--									<option value="5">埋深点</option>--%>
<%--									<option value="6">电杆</option>--%>
<%--									<option value="7">警示牌</option>--%>
<%--									<option value="8">护坡</option>--%>
<%--									<option value="9">接头盒</option>--%>
<%--									<option value="10">非路由标志</option>--%>
<%--								</select>--%>
<%--							</div>--%>
<%--						</td>--%>
					</tr>
				</table>
			</form>
		</div>
	<div class="btn-operation-container">
		<div style="float: right;" class="btn-operation" onClick="serchData()">查询</div>
<%--		<div style="float: left;" class="btn-operation" onClick="showByMap()">设施段展示</div>--%>
	</div>	
	<div id="dg" style="width: 98%; height: 80%;"></div>
	<div id="win"></div>
</body>
	<script type="text/javascript">
	$(function(){
		if($("select[name='city_id']").val()){
			getCable($("select[name='city_id']").val());
		}
	});
	
		function serchData(){
			var obj=makeParamJson('form');
			$('#dg').datagrid({
				fitColumns:true,
				autoSize : true,
				queryParams:obj,
				url : webPath + "/StepEquip/selEquipListForWEB.do",
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
						{field:'CABLE_ID',title:'CABLE_ID',align:'center',hidden:true},
						{field:'RELAY_ID',title:'ID',align:'center',hidden:true},
						{field:'CABLE_NAME',title:'光缆名称',align:'center',width:100},
						{field:'RELAY_NAME',title:'中继段名称',align:'center',width:100},
						{field:'NAME',title:'地市',align:'center',width:100},
						{field:'START_EQUIP',title:'起始设施点',align:'center',width:100},
						{field:'END_EQUIP',title:'结束设施点',align:'center',width:100},
						{field:'DISTANCE',title:'路由长度',align:'center',width:100},
						{field:'detail',title:'操作',align:'center',width:80,
							formatter: function(value,row,index){
								return ""
								+"<a href='javascript:void(0)' onclick='delStepEquip("+row.CABLE_ID+","+row.RELAY_ID+","+$("select[name='city_id']").val()+")'>删除步巡点</a>"
								+"&nbsp;<a href='javascript:void(0)' onclick='showDetail1("+row.CABLE_ID+","+row.RELAY_ID+","+$("select[name='city_id']").val()+")'>路由顺序调整</a>"
								+"<br/>&nbsp;"+ "<a href='javascript:void(0)' onclick='showDetail2("+row.CABLE_ID+","+row.RELAY_ID+","+$("select[name='city_id']").val()+")'>关联关系调整</a>"
								+"&nbsp;"+ "<a href='javascript:void(0)' onclick='showDetail3("+row.CABLE_ID+","+row.RELAY_ID+","+$("select[name='city_id']").val()+")'>全量关系调整</a>"
								+"<br/>&nbsp;"+"<a href='javascript:void(0)' onclick='showDetail4("+row.CABLE_ID+","+row.RELAY_ID+","+$("select[name='city_id']").val()+")'>非路由查看</a>"
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
		//删除步巡点
		function delStepEquip(cable_id,relay_id,area_id){
			//location.href="delStepEquip.do?relay_id="+relay_id+"&area_id="+area_id+"&cable_id="+cable_id;
			addTab("删除步巡点", "<%=path%>/StepEquip/delStepEquip.do?relay_id="+relay_id+"&area_id="+area_id+"&cable_id="+cable_id);
		}
		function showDetail1(cable_id,relay_id,area_id){
			location.href="changeEquipInit.do?relay_id="+relay_id+"&area_id="+area_id+"&cable_id="+cable_id;
		}
		
		function showDetail2(cable_id,relay_id,area_id){
			location.href="changeRelationEquipInit.do?relay_id="+relay_id+"&area_id="+area_id+"&cable_id="+cable_id;
		}
		
		function showDetail3(cable_id,relay_id,area_id){
			location.href="changeAllEquipInit.do?relay_id="+relay_id+"&area_id="+area_id+"&cable_id="+cable_id;
		}
		
		function showDetail4(cable_id,relay_id,area_id){
			location.href="selNoRouthEquip.do?relay_id="+relay_id+"&area_id="+area_id+"&cable_id="+cable_id;
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
