<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>编辑外力点看护计划</title>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

#l-map {
	height: 100%;
	width:300px;
	float: left;
}

#r-result {
	height: 100%;
	width: 280px;
	float: right;
}
.map_bubble_title{
	font-weight: bold;
	font-size: 15px;
	float: left;
	line-height: 25px;
	border-bottom: 1px;
	border-bottom-color:#ccc; 
	margin-left:10px;
	padding-bottom: 8px;
	cursor: pointer;
}
.map_bubble_info{
	line-height: 25px;
}
</style>
<script type="text/javascript">
$(function(){
	$("select").each(function(){
		var self = $(this);
		var value = self.val();
		if(typeof(self.attr("step")) != 'undefined'){
			var options = "";
			if(self.attr("step") == 'h'){
				var min = self.attr("min");
				if(typeof(min) != 'undefined'){
					if(min.indexOf('0') > -1){
						min = min.substring(1);
					}
				} else {
					min = 0;
				}
				for(var i = min; i < 24; i ++){
					if(i < 10){
						if(('0'+i) == value)
						{
							options += "<option value = '0" + i + "' selected>0" + i + "</option>";
						}
						else{
							options += "<option value = '0" + i + "'>0" + i + "</option>";
						}
					} 
					else 
					{
						if(i == value)
						{					
							options += "<option value = '" + i + "' selected>" + i + "</option>";
						}
						else
						{
							options += "<option value = '" + i + "'>" + i + "</option>";
						}
					}
				}
				self.html(options);
			}
		}
	});
});

function parse(select, minH, minM){
	if(minH != 0){
		var step = select.attr("step");
		var options = "";
		if(step == 'h'){
			if(minH.indexOf('0') > -1){
				minH = minH.substring(1);
			}
			for(var i = minH; i < 24; i ++){
				if(i < 10){
					options += "<option value = '0" + i + "'>0" + i + "</option>";
				} else {
					options += "<option value = '" + i + "'>" + i + "</option>";
				}
			}
			select.html(options);
		}
	} else {
		select.val(minM);
	}
}

var num = 0;
function addKeep(){

	//验证开始时间是否小于结束时间
	if(! checkTime1()){
		$.messager.show({
			title : '提  示',
			msg : '开始时间不能大于或等于结束时间!',
			showType : 'show'
		});
		return;
	}
	//判断开始时间是否大于上一个时间段的结束时间
	if(num > 0 && ! checkTime(num)){
		return;
	}
	var keep = $("#keep" + num);
	var keepHTML= $("#keep" + num).html();
	
	keep.after("<tr id = 'keep" + (num+1)+ "'>" +  keep.html()+ "</tr>");
	num++;
	for(var i = 0; i < num; i ++){
		$("#keep" + i + " select[name='start_time_h']").attr("disabled", true);
		$("#keep" + i + " select[name='start_time_m']").attr("disabled", true);
		$("#keep" + i + " select[name='end_time_h']").attr("disabled", true);
		$("#keep" + i + " select[name='end_time_m']").attr("disabled", true);
	}
	var minH = $("#keep" + (num - 1) + " select[name='end_time_h']").val();
	var minM = $("#keep" + (num - 1) + " select[name='end_time_m']").val();
	parse($("#keep" + num + " select[name='start_time_h']"), minH, 0);
	parse($("#keep" + num + " select[name='start_time_m']"), 0, minM);
	parse($("#keep" + num + " select[name='end_time_h']"), minH, 0);
	parse($("#keep" + num + " select[name='end_time_m']"), 0, minM);
}

function deleteKeep(){
	if(num == 0){
		$.messager.show({
			title : '提  示',
			msg : '至少要保留一个看护时间段!',
			showType : 'show'
		});
		return;
	}
	$("#keep" + num).remove();
	num --;
	$("#keep" + num + " select[name='start_time_h']").attr("disabled", false);
	$("#keep" + num + " select[name='start_time_m']").attr("disabled", false);
	$("#keep" + num + " select[name='end_time_h']").attr("disabled", false);
	$("#keep" + num + " select[name='end_time_m']").attr("disabled", false);
}

function checkTime1(){
	var start_time = $("#keep" + num + " select[name='start_time_h']").val() + ':' + $("#keep" + num + " select[name='start_time_m']").val();
	var end_time = $("#keep" + num + " select[name='end_time_h']").val() + ':' + $("#keep" + num + " select[name='end_time_m']").val();
	if(start_time >= end_time){
		return false;
	}
	return true;
}

function checkTime(n){
	var end_time_old = $("#keep" + (n - 1) + " select[name='end_time_h']").val() 
		+ ':' + $("#keep" + (n - 1) + " select[name='end_time_m']").val();
	var start_time = $("#keep" + num + " select[name='start_time_h']").val() 
		+ ':' + $("#keep" + num + " select[name='start_time_m']").val();
	if(start_time < end_time_old){
		$.messager.show({
			title : '提  示',
			msg : '开始时间必须大于或等于上一个时间段的结束时间!',
			showType : 'show'
		});
		return false;
	}
	return true;
}

</script>
</head>
<body style="padding: 3px; border: 0px">
<input type="hidden" name="plan_id" id="plan_id" value="${planInfo.PLAN_ID}"/>
	<input type="hidden" name="plan_circle" id="plan_circle" value="${planInfo.PLAN_CIRCLE}"/>
	<input type="hidden" name="plan_type" id="plan_type" value="${planInfo.PLAN_TYPE}"/>
	<input type="hidden" name="plan_frequency" id="plan_frequency" value="${planInfo.PLAN_FREQUENCY}"/>
	
	<input type="hidden" name="plan_kind" id="plan_kind" value="${plan_kind}"/>
<input type="hidden" name="area_id" id="area_id" value="${area_id}"/>
<input type="hidden" name="longitude" id="longitude" value="${longitude}"/>
<input type="hidden" name="latitude" id="latitude" value="${latitude}"/>
		<div id="l-map"></div>
		<div id="r-result">
			<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
				<div class="btn-operation" onClick="savePlan();">保存</div>
				<div class="btn-operation" onClick="history.back();">返回</div>
				<!-- div class="btn-operation" onClick="showPoints();">显示</div> -->
			</div>
			<div style="border-bottom: 1px solid #d2d2d2; padding:10px; overflow: auto; max-height: 300px;">
			<form id="ff">
				<table>
				<tr>
					<td>计划名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="plan_name" value="${planInfo.PLAN_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>计划编码：</td>	
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="plan_no" value="${planInfo.PLAN_NO}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>计划类型：</td>
					<td>
						<select class="condition-select" name="plan_type" id="plan_type">
								<option value='0'>周期性</option>	
								<option value='1'>临时性</option>	
						</select>
					</td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name=plan_start_time id="plan_start_time" required="true" 
								onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});" value="${planInfo.PLAN_START_TIME}"/>
						</div></td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name=plan_end_time id="plan_end_time" required="true" 
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'plan_start_time\')}'});" 
								value="${planInfo.PLAN_END_TIME}"/>
						</div></td>
				</tr>
				<tr>
					<td>有效范围(米)：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="inaccuracy" value="${planInfo.INACCURACY}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td colspan="2">看护时间段：</td>
				</tr>
				<c:forEach  var="timeList" items="${timeList}" varStatus="status">
				<tr id = "keep${status.index}">
				
					<td colspan="2">
							<script type="text/javascript">
							num = ${status.index};
							var startHours = '${timeList.START_TIME}'.split(':')[0];
							var startMinite = '${timeList.START_TIME}'.split(':')[1];
							var endHours = '${timeList.END_TIME}'.split(':')[0];
							var endMinite = '${timeList.END_TIME}'.split(':')[1];
							
							for(var i = 0; i < num; i ++){
								$("#keep" + i + " select[name='start_time_h']").attr("disabled", true);
								$("#keep" + i + " select[name='start_time_m']").attr("disabled", true);
								$("#keep" + i + " select[name='end_time_h']").attr("disabled", true);
								$("#keep" + i + " select[name='end_time_m']").attr("disabled", true);
						}
							//alert(endMinite);
							</script>
						<select class="condition-select" name="start_time_h" style="width: 42px;" step = "h">
						<option value="${fn:split(timeList.START_TIME,':')[0]}" selected>${fn:split(timeList.START_TIME,':')[0]}</option>
						</select> ：
						<select class="condition-select" name="start_time_m" style="width: 42px;">
							
							<option value="00" <c:if test='${fn:split(timeList.START_TIME,":")[1]=="00"}'>selected</c:if>>00</option>
							<option value="30" <c:if test='${fn:split(timeList.START_TIME,":")[1]=="30"}'>selected</c:if>>30</option>
							<option value="59" <c:if test='${fn:split(timeList.START_TIME,":")[1]=="59"}'>selected</c:if>>59</option>
							
						</select> 至
						<select class="condition-select" name="end_time_h" style="width: 42px;"  step = "h">
						<option value="${fn:split(timeList.END_TIME,':')[0]}" selected>${fn:split(timeList.END_TIME,':')[0]}</option>
						</select> ：
						<select class="condition-select" name="end_time_m" style="width: 42px;">
							<option value="00" <c:if test='${fn:split(timeList.END_TIME,":")[1]=="00"}'>selected</c:if>>00</option>
							<option value="30" <c:if test='${fn:split(timeList.END_TIME,":")[1]=="30"}'>selected</c:if> >30</option>
							<option value="59" <c:if test='${fn:split(timeList.END_TIME,":")[1]=="59"}'>selected</c:if>>59</option>
						</select>
					</td>
					
				</tr>
				</c:forEach>
				<tr>
					<td colspan="2"></td>
				</tr>
			</table></form>
			<div>
				<div class="btn-operation" onClick="addKeep();">添加时间段</div>
				<div class="btn-operation" onClick="deleteKeep();">删除时间段</div>
			</div>
			</div>
			<div style="overflow:auto;">
				<div>
					<table class="datagrid-htable" style="height:41px;table-layout:fixed;" cellSpacing=0 cellPadding=0 border=0 id="show">
					</table>
				</div>
			</div>
		</div>
	<script type="text/javascript">
	var plan_kind = 3;//计划类型
	var area_id = $("input[name='area_id']").val();//地区
	var longitudeInit = $("input[name='longitude']").val();//经度
	var latitudeInit = $("input[name='latitude']").val();//纬度
	var cache = new CacheMap();//缓存展示的点信息
	var pointsSelected = new CacheMap();//缓存选择的点信息
	var points;
	var isAll = true;//当前是否显示全部点
	//初始化地图
	var map = new BMap.Map("l-map");
	$("#l-map").css("width", document.body.clientWidth - 290);
	$(window).resize(function(){
		$("#l-map").css("width", document.body.clientWidth - 290);
	});
	map.centerAndZoom('${area_name}', 14);
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	
	$("select[name='plan_type']").val($("#plan_type").val());
		$("select[name='plan_circle']").val($("#plan_circle").val());
		$("select[name='plan_frequency']").val($("#plan_frequency").val());
	$(function(){
		//获取已选择外力点
		$.ajax({
			type: "GET", 
			dataType:"json",
            url:webPath + "Care/plan_getPlanDetail.do?plan_id=" + $("#plan_id").val(),
            success:function(datas) {
            	$.each(datas, function(i, data){
            		var point_id = data["POINT_ID"];
            		var point_no = data["POINT_NO"];
            		var point_name = data["POINT_NAME"];
            		var point_type = data["POINT_TYPE"];
            		var trId = "point_selected_" + point_id;
            		var trHtml = "<tr class='datagrid-cell' style='height:40px;' id='" + trId + "'>"
    				+ "<td width='7%'>{index}</td>"
    				+ "<td width='25%' title='" + point_name + "' nowrap='nowrap'>" + point_name + "</td>"
    				+ "<td width='30%' title='" + point_no + "' nowrap='nowrap'>" + point_no + "</td>"
    				+ "<td width='38%' align='right'><div class='btn-operation' onClick='deletePoint(\"" + trId + "\", \"" + point_id + "\");'>删除</div></td></tr>";
    				cache.put(trId, trHtml);
    				pointsSelected.put(point_id, point_type);
            	});
            	showSelected();
            }
		});
	
		$.ajax({
			type: "GET", 
			dataType:"json",
            url:webPath + "Care/plan_getPoints.do?type=3&area_id=" + area_id,
            success:function(datas) {
            	points = datas;
            	$.each(datas, function(i, data){
            		showPoint(data);
        		});
            },
            error:function(){
            }
        });
		
		
	});
	
	function showPoint(data){
		var greenIcon = new BMap.Icon(webPath + "images/icon_sign.png", new BMap.Size(23, 25));
		var POINT_ID = data["POINT_ID"];
		var POINT_NO = data["POINT_NO"];
		var POINT_NAME = data["POINT_NAME"];
		//var POINT_LEVEL = data["POINT_LEVEL"];
		var POINT_TYPE = data["POINT_TYPE"];
		var LONGITUDE = data["LONGITUDE"];
		var LATITUDE = data["LATITUDE"];
		var CREATE_TIME = data["CREATE_TIME"];
		var STAFF_NAME = data["STAFF_NAME"];
		//var radius = data["radius"];
		
		var info = "<div class='map_bubble_info'>编码：" + POINT_NO
					+ "<br/>经度：" + LONGITUDE
					+ "<br/>纬度：" + LATITUDE 
					+ "<br/>创建时间：" + CREATE_TIME 
					+ "<br/>创建者：" + STAFF_NAME 
					+ "</div>";
		var point = new BMap.Point(LONGITUDE, LATITUDE);
		var marker;
		if(POINT_TYPE == 2){//普通隐患点
			marker = new BMap.Marker(point, {icon: greenIcon});
		} else {
			marker = new BMap.Marker(point);
		}
		title = "<div class='map_bubble_title'>" + POINT_NAME + "</div>";
		
		map.addOverlay(marker); 
		var opts = {
				  width : 250,     // 信息窗口宽度
				  height: 0,     // 信息窗口高度
				  title : title, // 信息窗口标题
				  enableMessage:false//设置允许信息窗发送短息
				};
		var infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
		marker.addEventListener("mouseover", function(){
			map.openInfoWindow(infoWindow,point);
		});
		marker.addEventListener("click", function(){
			if(plan_kind == 2 && POINT_TYPE == 2){
				  addPoint(POINT_ID,POINT_NAME,POINT_NO, POINT_TYPE);
			 } else if(plan_kind == 3 && POINT_TYPE == 3){
				  addPoint(POINT_ID,POINT_NAME,POINT_NO, POINT_TYPE);
			 }
		});
	}
	</script>
	<script type="text/javascript">
	function addPoint(point_id, point_name, point_no, point_type){
		if (pointsSelected.length() == 1) {
			$.messager.show({
				title : '提  示',
				msg : '只能选择一个外力点!',
				showType : 'show'
			});
			return;
		}
		
		var trId = "point_selected_" + point_id;
		if($("#" + trId).length > 0){
			$.messager.show({
				title : '提  示',
				msg : '该外力点 已存在!',
				showType : 'show'
			});
		} else{
			var trHtml = "<tr class='datagrid-cell' style='height:40px;' id='" + trId + "'>"
				+ "<td width='7%'>{index}</td>"
				+ "<td width='25%' title='" + point_name + "' nowrap='nowrap'>" + point_name + "</td>"
				+ "<td width='30%' title='" + point_no + "' nowrap='nowrap'>" + point_no + "</td>"
				+ "<td width='38%' align='right'><div class='btn-operation' onClick='deletePoint(\"" + trId + "\", \"" + point_id + "\");'>删除</div></td></tr>";
			cache.put(trId, trHtml);
			pointsSelected.put(point_id, point_type);
			showSelected();
		}
	};
	function deletePoint(trId, point_id){
		cache.remove(trId);
		pointsSelected.remove(point_id);
		showSelected();
	}
	function showSelected(){
		var trHtml = "";
		cache.each(function(k,v,index){
			trHtml += v.replace("{index}", (index + 1));
	    });
		$("#show").html(trHtml);
	}
	
	function savePlan(){
		if(num == 0){
			checkTime1();
		} else {
			checkTime(num);
		}
		if(pointsSelected.length() == 0){
			$.messager.show({
				title : '提  示',
				msg : '请选择外力点!',
				showType : 'show'
			});
			return;
		} 
		var inaccuracy = $("input[name='inaccuracy']").val();
		//看护半径校验
		if(''!=inaccuracy&&500<inaccuracy)
		{
			$.messager.show({
						title : '提  示',
						msg : '看护半径不能超过500米！',
						showType : 'show'
			});
			return; 
		}
		if($("#ff").form('validate')){
			$.messager.confirm('系统提示', '您确定要保存该计划吗?', function(r) {
				if(r){
					var pointsValue = "";
					pointsSelected.each(function(k,v,index){
						pointsValue += k + ":" + v + ",";
					});
					var plan_id = $("input[name='plan_id']").val();
					var plan_name = $("input[name='plan_name']").val();
					var plan_no = $("input[name='plan_no']").val();
					var plan_type = $("select[name='plan_type']").val();
					var plan_start_time = $("input[name='plan_start_time']").val();
					var plan_end_time = $("input[name='plan_end_time']").val();
					//var plan_circle = $("select[name='plan_circle']").val();
					//var plan_frequency = $("select[name='plan_frequency']").val();
					//var inaccuracy = $("input[name='inaccuracy']").val();
					var start_time = '';
					var end_time = '';
					var start_time_temp;
					var end_time_temp;
					
					for(var i = 0; i <= num; i ++){
						start_time_temp = $("#keep" + i + " select[name='start_time_h']").val() + ':' + $("#keep" + i + " select[name='start_time_m']").val();
						end_time_temp = $("#keep" + i + " select[name='end_time_h']").val() + ':' + $("#keep" + i + " select[name='end_time_m']").val();
						start_time += "," + start_time_temp;
						end_time += "," + end_time_temp;
					}
					$.ajax({
						type : 'POST',
						url : webPath + "Care/editPointKeep.do",
						data : {
							points : pointsValue,
							plan_name : plan_name,
							plan_id : plan_id,
							plan_no : plan_no,
							plan_type : plan_type,
							plan_start_time : plan_start_time,
							plan_end_time : plan_end_time,
							//plan_circle : plan_circle,
							//plan_frequency : plan_frequency,
							plan_type : plan_type,
							plan_kind : plan_kind,
							start_time : start_time,
							end_time : end_time,
							inaccuracy : inaccuracy
						},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
							$.messager.alert('提示','保存计划成功!','info',function(r){
								location.href = 'plan_index.do';
							});
							}
							else
							{
							$.messager.alert('提示','保存计划失败!','info');
							}
							
						}
					});
				}
			});
		}
	}
	function showPoints() {
		map.clearOverlays();//删除所有标记
		if(isAll){
			$.each(points, function(i, data){
				var point_id = data["POINT_ID"];
				if($("#point_selected_" + point_id).length > 0){
					showPoint(data);
				}
    		});
			isAll = false;
		} else {
			$.each(points, function(i, data){
        		showPoint(data);
    		});
			isAll = true;
		}
		
	};
	</script>
</body>
</html>