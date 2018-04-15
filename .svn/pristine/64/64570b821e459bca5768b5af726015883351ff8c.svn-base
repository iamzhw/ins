
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
					<td width="10%" style="display: none;">地区名：</td>
					<td width="20%" style="display: none;">
						<div>
							<select name="city_name" class="condition-select"
								onchange="getCable(this.value)">
								<option value=''>--请选择--</option>
								<c:forEach var="city" items="${cityModel }">
									<option value="${city.AREA_ID }">${city.NAME }</option>
								</c:forEach>
							</select>


						</div>
					</td>
					<td width="10%">敏感系统名：</td>
					<td width="20%">
						<div class="condition-text-container">
							<input class="condition-text condition" type="text"
								name="senstivesegname" id="" />
						</div>
					</td>
					<td width="10%">线路段名：</td>
					<td width="20%">
						<div>
							<select name="cable_name" class="condition-select"
								onchange="getRelay(this.value)">
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


							</div></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:left;" class="btn-operation"
				onClick="serchData()">查询</div>
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
				url : webPath + "/CutAndConnOfFiberController/getListOfSensitiveline.do",
				method : 'post',
				queryParams:obj,
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [
					[  
						{field:'SENSTIVEID',title:'',width:30,align:'center',hidden:true},
						{field:'SENSTIVESEGNAME',title:'敏感系统名',width:50,align:'center'},
						{field:'MULTIPLEXSEGNAME',title:'复用段',width:50,align:'center'},
						{field:'SENSTIVELENGTH',title:'系统竣工-总纤芯长度(公里)',width:50,align:'center'},
						{field:'aa',title:'目前-总纤芯长度(公里)',width:50,align:'center'},
						{field:'bb',title:'目前比系统竣工时增加了(公里)',width:50,align:'center'},
						{field:'CABLE_NAME',title:'所属干线',width:50,align:'center'},
						{field:'RELAY_NAME',title:'敏感中继段',width:50,align:'center'},
						{field:'FIBERSTRUCTURE',title:'光缆结构',width:50,align:'center'},
						{field:'FIBERMANUFACTURER',title:'光缆生产厂家',width:50,align:'center'},
						{field:'FIBERCOREMANUFACTURER',title:'纤芯生产厂家',width:50,align:'center'},
						{field:'FIBERSPECTRUM',title:'光缆程式色谱',width:50,align:'center'},
						{field:'FIBERLENGTHATCOMPLETED',title:'竣工时光缆长度(公里)',width:50,align:'center'},
						{field:'FIBERLENGTHATNOW',title:'即时光缆长度(公里)',width:50,align:'center'}
					]	
				],
				onLoadSuccess:function(data){
					
				}
			});
		}
		$(function(){
			if("${localId}"){ //非省管理员登陆  查看本地光缆信息
				getCable("${localId}");
			}else{ //省管理员登陆  查看各地 select下拉框显示（取消隐藏状态）
				$("#tr_cityName td").css("display",'');
			}
		});
		
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
		
		
	</script>
</html>
