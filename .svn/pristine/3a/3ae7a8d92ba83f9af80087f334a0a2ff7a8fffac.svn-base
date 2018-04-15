<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>新增外力点计划</title>
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
function HashMap(){  
    //定义长度  
    var length = 0;  
    //创建一个对象  
    var obj = new Object();  
  
    /** 
    * 判断Map是否为空 
    */  
    this.isEmpty = function(){  
        return length == 0;  
    };  
  
    /** 
    * 判断对象中是否包含给定Key 
    */  
    this.containsKey=function(key){  
        return (key in obj);  
    };  
  
    /** 
    * 判断对象中是否包含给定的Value 
    */  
    this.containsValue=function(value){  
        for(var key in obj){  
            if(obj[key] == value){  
                return true;  
            }  
        }  
        return false;  
    };  
  
    /** 
    *向map中添加数据 
    */  
    this.put=function(key,value){  
        if(!this.containsKey(key)){  
            length++;  
        }  
        obj[key] = value;  
    };  
  
    /** 
    * 根据给定的Key获得Value 
    */  
    this.get=function(key){  
        return this.containsKey(key)?obj[key]:null;  
    };  
  
    /** 
    * 根据给定的Key删除一个值 
    */  
    this.remove=function(key){  
        if(this.containsKey(key)&&(delete obj[key])){  
            length--;  
        }  
    };  
  
    /** 
    * 获得Map中的所有Value 
    */  
    this.values=function(){  
        var _values= new Array();  
        for(var key in obj){  
            _values.push(obj[key]);  
        }  
        return _values;  
    };  
  
    /** 
    * 获得Map中的所有Key 
    */  
    this.keySet=function(){  
        var _keys = new Array();  
        for(var key in obj){  
            _keys.push(key);  
        }  
        return _keys;  
    };  
  
    /** 
    * 获得Map的长度 
    */  
    this.size = function(){  
        return length;  
    };  
  
    /** 
    * 清空Map 
    */  
    this.clear = function(){  
        length = 0;  
        obj = new Object();  
    };  
}  
$(function(){
	$("select").each(function(){
		var self = $(this);
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
						options += "<option value = '0" + i + "'>0" + i + "</option>";
					} else {
						options += "<option value = '" + i + "'>" + i + "</option>";
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
	num++;
	keep.after("<tr id = 'keep" + num + "'>" + $(keep).html() + "</tr>");
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
<input type="hidden" name="plan_kind" id="plan_kind" value="${plan_kind}"/>
<input type="hidden" name="area_id" id="area_id" value="${area_id}"/>
<input type="hidden" name="area_name" id="area_name" value="${area_name}"/>
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
				<input id="keepLine" name="keepLine" value="" type="hidden">
				<table>
				<tr>
					<td>计划名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="plan_name"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<!--<tr>
					<td>计划编码：</td>	
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="plan_no"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>-->
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
								onClick="WdatePicker({minDate:'%y-%M-{%d}'});" />
						</div></td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name=plan_end_time id="plan_end_time" required="true" 
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'plan_start_time\')}'});" />
						</div></td>
				</tr>
				<tr>
					<td>有效范围(米)：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="inaccuracy"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td colspan="2">看护时间段：</td>
				</tr>
				<tr id = "keep0">
					<td colspan="2">
						<select class="condition-select" name="start_time_h" style="width: 42px;" step = "h">
						</select> ：
						<select class="condition-select" name="start_time_m" style="width: 42px;">
							<option value="00">00</option>
							<option value="30">30</option>
							<option value="59">59</option>
						</select> 至
						<select class="condition-select" name="end_time_h" style="width: 42px;"  step = "h">
						</select> ：
						<select class="condition-select" name="end_time_m" style="width: 42px;">
							<option value="00">00</option>
							<option value="30">30</option>
							<option value="59">59</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2"></td>
				</tr>
			</table></form>
			<div>
				<div class="btn-operation" onClick="addKeep();">添加时间段</div>
				<div class="btn-operation" onClick="deleteKeep();">删除时间段</div>
			</div>
			</div>
			<div style="border-bottom: 1px solid #d2d2d2; padding-left: 5px; display: none; background-color: white;"
			id="showPointquery">
			<table>
				<!-- <tr>
					<td nowrap="nowrap" width="80px">搜索类型</td>
					<td><select class="condition-select" name="query_type"
						id="query_type">
							<option value='0'>按道路名称搜索</option>
							<option value='1'>按设备名称搜索</option>
					</select></td>
				</tr> -->
				<tr class="queryRode">
					<td>看护点编码</td>
					<td><div class="condition-text-container">
							<input name="pointNo" type="text" class="condition-text" />
						</div></td>
				</tr>
				<tr class="searchEqp">
					<td>看护点名称</td>
					<td><div class="condition-text-container">
							<input name="pointName" type="text" class="condition-text" />
						</div></td>
				</tr>
				<tr>
					<td><div class="btn-operation" onClick="queryPoint();">
							搜索</div></td>
					<td><div class="btn-operation" onClick="hideQueryPoint();">
							隐藏</div></td>
				</tr>

			</table>
		</div>
			<div style="overflow:auto;">
				<div>
					<table class="datagrid-htable" style="height:41px;table-layout:fixed;" cellSpacing=0 cellPadding=0 border=0 id="show">
					</table>
				</div>
			</div>
			
		</div>
	<script type="text/javascript">
	$("#l-map").css("width", document.body.clientWidth - 290);
	var plan_kind = $("input[name='plan_kind']").val();//计划类型
	var area_id = $("input[name='area_id']").val();//地区
	var area_name = $("input[name='area_name']").val();//地区
	var longitudeInit = $("input[name='longitude']").val();//经度
	var latitudeInit = $("input[name='latitude']").val();//纬度
	var cache = new CacheMap();//缓存展示的点信息
	var pointsSelected = new CacheMap();//缓存选择的点信息
	var markerMap = new CacheMap();//缓存marker
	var iconMap = new CacheMap();//缓存关键点图标
	var points;
	var isAll = true;//当前是否显示全部点
	//初始化地图
	var map = new BMap.Map("l-map");
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	
	var checkedIcon = new BMap.Icon(webPath + "images/icon_checked.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var userdIcon = new BMap.Icon(webPath + "images/icon_fire_green.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	
	$(window).resize(function(){
		$("#l-map").css("width", document.body.clientWidth - 290);
	});
	
		//看护区域划分
	var ifhuitu = 1;
	var polylinesss;
	var kpoints = new Array();
	var rightClickVisible = 0;
	var showInfoWindowFlag = 0;
	var resume = 1;
	var overlaysList = new Array();
	var overlaysMap = new HashMap();
	var deletelays = [];
	var overlays = [];
	var cableJid = 0;
	function startControl(){
		this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;  
	    this.defaultOffset = new BMap.Size(20, 20);
	}
	startControl.prototype = new BMap.Control();
	startControl.prototype.initialize = function(map){
		var div = document.createElement("div");
		var img = document.createElement("img");
		img.setAttribute('src', '../images/icon_start.png');	
		img.setAttribute('width', '48px');	
		img.setAttribute('height', '48px');
		img.setAttribute('title', '点击开始');
		img.style.cursor = "pointer";  
		div.appendChild(img);
		div.onclick = function(){
			if(ifhuitu == 0){
				return;
			}
			clearLastLine();
			kpoints.length = 0;
			// map.disableDragging();
			//map.disableScrollWheelZoom();
			map.disableDoubleClickZoom();
			ifhuitu = 0;
			//var polyline = new BMap.Polyline(points, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
			polylinesss = new BMap.Polyline(kpoints, {
				strokeColor : "#9D3071",
				strokeWeight : 6,
				strokeOpacity : 0.6
			});
			map.addOverlay(polylinesss);
			map.closeInfoWindow();//清楚点信息提示框
			showInfoWindowFlag = 1;
			map.addEventListener("click", test);
					var menu = new BMap.ContextMenu();
			var txtMenuItem = [
				{
					text:'回退',
					callback:clearLastPoint
				},
				{
					text:'删除',
					callback:clearLastLine
				}
			];
			for(var i=0; i < txtMenuItem.length; i++){
				menu.addItem(new BMap.MenuItem(txtMenuItem[i].text,txtMenuItem[i].callback,100));
			}
			map.addContextMenu(menu);
					map.removeControl(new completeControl());
				};
		map.getContainer().appendChild(div);
		return div;
	};
	//结束控件
	function completeControl(){
		this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
	    this.defaultOffset = new BMap.Size(100, 20);
	}
	completeControl.prototype = new BMap.Control();
	completeControl.prototype.initialize = function(map){
		var div = document.createElement("div");
		var img = document.createElement("img");
		img.setAttribute('src', '../images/icon_end.png');	
		img.setAttribute('width', '48px');	
		img.setAttribute('height', '48px');
		img.setAttribute('title', '点击结束');
		img.style.cursor = "pointer";
		div.appendChild(img);
		div.onclick = function(){
			if(ifhuitu == 1 || kpoints.length < 2){
				return;
			}
			ifhuitu = 1;
			rightClickVisible = 0;
			showInfoWindowFlag = 0;
			map.enableDragging();
			map.enableScrollWheelZoom();
			map.enableDoubleClickZoom();
			deletelays.push(polylinesss);

			overlays.length = 0;

			var linepoint = "";
			for ( var i = 0; i < kpoints.length; i++) {
				linepoint += kpoints[i].lng + ":" + kpoints[i].lat + ",";
			}
			if (linepoint > 1) {
				linepoint = linepoint
						.substring(0, linepoint.length - 1);
			}
			$('#keepLine').val(linepoint);
			map.removeEventListener("click", test);
			//清除线段
			kpoints = new Array();
			overlays = [];
			deletelays = [];
			cableJid = 0;
			resume = 1;//0点击了关键点
			overlaysList = new Array();
			overlaysMap = new HashMap();
			rightClickVisible = 0;
			//区域划分线保留
			//map.removeOverlay(polylinesss);
			//划分的区域
		};
						
		map.getContainer().appendChild(div);
		return div;
	};
	
		//搜索控件
	function searchControl(){
		this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	    this.defaultOffset = new BMap.Size(20, 20);
	}
	searchControl.prototype = new BMap.Control();
	searchControl.prototype.initialize = function(map){
		var div = document.createElement("div");
		var img = document.createElement("img");
		img.setAttribute('src', '../images/icon_query.png');	
		img.setAttribute('width', '48px');	
		img.setAttribute('height', '48px');
		img.setAttribute('title', '点击打开搜索框');
		img.style.cursor = "pointer";
		div.appendChild(img);
		div.onclick = function(){
			if(typeof(searchBoxContorl) == 'undefined'){
				searchBoxContorl = new searchBoxControl();
				map.addControl(searchBoxContorl);
			} else {
					//查询条件清空
				$("input[name='pointName']").val("");
				$("input[name='pointNo']").val("");
				$("#showPointquery").show();
			}
		};
		map.getContainer().appendChild(div);
		return div;
	};
	//搜索框控件
	function searchBoxControl(){
		this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	    this.defaultOffset = new BMap.Size(70, 20);
	}
	searchBoxControl.prototype = new BMap.Control();
	searchBoxControl.prototype.initialize = function(map){
		//var queryObj = $("#showPointquery").clone(true);
		var div = document.getElementById("showPointquery");
		map.getContainer().appendChild(div);
		$("#showPointquery").show();
		return div;
	};
	//将控件加入地图
	map.addControl(new searchControl());
	map.addControl(new startControl());
	map.addControl(new completeControl());
	$(function(){
		$.ajax({
			type: "GET", 
			dataType:"json",
            url:webPath + "Care/plan_getPoints.do?type=3&area_id=" + area_id,
            success:function(datas) {
            	points = datas;
            	var vi = new Array();
            	$.each(datas, function(i, data){
            		var point = showPoint(data);
            		vi[vi.length] = point;
        		});
            	map.setViewport(vi);
            },
            error:function(){
            }
        });
	});
	
	function showPoint(data){
		var POINT_ID = data["POINT_ID"];
		var POINT_NO = data["POINT_NO"];
		var POINT_NAME = data["POINT_NAME"];
		//var POINT_LEVEL = data["POINT_LEVEL"];
		var POINT_TYPE = data["POINT_TYPE"];
		var LONGITUDE = data["LONGITUDE"];
		var LATITUDE = data["LATITUDE"];
		var CREATE_TIME = data["CREATE_TIME"];
		var STAFF_NAME = data["STAFF_NAME"];
		var ADDRESS = data["ADDRESS"];
		var USERD_COUNT = data["USERD_COUNT"];
		var KEEP_RADIUS = data["KEEP_RADIUS"];
		
		if (typeof(ADDRESS) == 'undefined') {
			ADDRESS = "";
		}
		if (typeof(CREATE_TIME) == 'undefined') {
			CREATE_TIME = "";
		}
		
		var info = "<div class='map_bubble_info'>编码：" + POINT_NO
					+ "<br/>名称：" + POINT_NAME
					+ "<br/>地址：" + ADDRESS 
					+ "<br/>创建时间：" + CREATE_TIME 
					+ "<br/>创建者：" + STAFF_NAME 
					+ "</div>";
		var point = new BMap.Point(LONGITUDE, LATITUDE);
		//加入到要全部显示到地图的点
		pointList.push(point);
		var icon;
		if (USERD_COUNT > 0) {
			icon = userdIcon;
		} else {
			icon = fireIcon;
		}
		var marker = new BMap.Marker(point, {icon: icon});
		iconMap.put(POINT_ID, icon);//缓存图标
		title = "<div class='map_bubble_title'>" + POINT_NAME + "</div>";
		var label = new BMap.Label(POINT_NAME, {position:point,offset:new BMap.Size(0, 40)});
		label.setStyle({
			border : "0",
			color : "#FF1111",
			offset : new BMap.Size(0, -40),
			backgroundColor :"0.00"
		});
		marker.setLabel(label);
		map.addOverlay(marker); 
		var opts = {
				  width : 250,     // 信息窗口宽度
				  height: 0,     // 信息窗口高度
				  title : title, // 信息窗口标题
				  offset : new BMap.Size(0, -40),
				  enableMessage:false//设置允许信息窗发送短息
				};
		var infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
		marker.addEventListener("mouseover", function(){
			map.openInfoWindow(infoWindow,point);
		});
		marker.addEventListener("mouseout", function() {
			map.closeInfoWindow();
		});
		marker.addEventListener("click", function(){
			 addPoint(POINT_ID,POINT_NAME,POINT_NO, POINT_TYPE, KEEP_RADIUS);
		});
		markerMap.put(POINT_ID, marker);
		return point;
	}
	</script>
	<script type="text/javascript">
	function addPoint(point_id, point_name, point_no, point_type, keep_radius){
		/*if (pointsSelected.length() == 1) {
			$.messager.show({
				title : '提  示',
				msg : '只能选择一个外力点!',
				showType : 'show'
			});
			return;
		}*/
		
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
			+ "<td width='25%' title='" + point_name + "' nowrap='nowrap'>" + (point_name.length > 5 ? point_name.substring(0,5) : point_name) + "</td>"
			+ "<td width='30%' title='" + point_no + "' nowrap='nowrap'>" + (point_no.length > 5 ? point_no.substring(0,5) : point_no) + "</td>"
			+ "<td width='38%' align='right'><div class='btn-operation' onClick='deletePoint(\"" + trId + "\", \"" + point_id + "\");'>删除</div></td></tr>";
			markerMap.get(point_id).setIcon(checkedIcon);
			cache.put(trId, trHtml);
			pointsSelected.put(point_id, point_type);
			showSelected();
			if (typeof(keep_radius) != 'undefined' && keep_radius > 0) {
				$("input[name='inaccuracy']").val(keep_radius);	
			}
		}
	};
	function deletePoint(trId, point_id){
		cache.remove(trId);
		pointsSelected.remove(point_id);
		showSelected();
		markerMap.get(point_id).setIcon(iconMap.get(point_id));
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
		//看护区域划分检验
		var keepLine=$("#keepLine").val();
					if(keepLine==null||keepLine==""){
						$.messager.show({
						title : '提  示',
						msg : '未设置看护区！',
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
					var plan_name = $("input[name='plan_name']").val();
					var plan_no = $("input[name='plan_no']").val();
					var plan_type = $("select[name='plan_type']").val();
					var plan_start_time = $("input[name='plan_start_time']").val();
					var plan_end_time = $("input[name='plan_end_time']").val();
					var plan_circle = $("select[name='plan_circle']").val();
					var plan_frequency = $("select[name='plan_frequency']").val();
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
						url : webPath + "Care/plan_save.do",
						data : {
							points : pointsValue,
							plan_name : plan_name,
							plan_no : plan_no,
							plan_type : plan_type,
							plan_start_time : plan_start_time,
							plan_end_time : plan_end_time,
							plan_circle : plan_circle,
							plan_frequency : plan_frequency,
							plan_type : plan_type,
							plan_kind : plan_kind,
							start_time : start_time,
							end_time : end_time,
							inaccuracy : inaccuracy,
							keepLine : keepLine
						},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '保存计划成功!',
									showType : 'show'
								});
							}
							location.href = 'plan_index.do';
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
		//搜索看护点
	function queryPoint() {
		$.ajax({
			type: "POST", 
			dataType:"json",
			data : {
				pointName : $("input[name='pointName']").val().trim(),
				pointNo : $("input[name='pointNo']").val().trim(),
				pointType:3
			},
            url:webPath + "Care/plan_getPoints.do?type=3&area_id=" + area_id,
            success:function(datas) {
           		points = datas;
            	map.clearOverlays();//删除所有标记
            	if(datas.length<1)
            	{
            		$.messager.show({
						title : '提  示',
						msg : '没有检索到看护点!',
						showType : 'show'
					});
					return;
            	}
            	pointList.length = 0;
            	$.each(datas, function(i, data){
            		showPoint(data);
        		});
        		map.setViewport(pointList);
            },
            error:function(){
            }
        });
		$("#showPointquery").hide();
	}
	function hideQueryPoint() {
		$("#showPointquery").hide();
	}
	var pointList = new Array();

		//删除最后一条缆线 add by ningruofan
	function clearLastLine() {
		//var allOverlay = map.getOverlays();
		//alert(overlaysList.length);
		if(rightClickVisible == 1){
			map.removeOverlay(overlaysList[overlaysList.length-1]);
			//overlaysList.pop();
			kpoints.length=0;
			polylinesss.setPath(kpoints);
		}
	};
		function test(e) {
		 if (resume == 1) {
			if(kpoints.length == 0){
				var point = new BMap.Point(e.point.lng, e.point.lat);
				kpoints[kpoints.length] = point;
				var  overMarker=new BMap.Marker(point, {icon: fireIcon});
				overlaysList.push(overMarker);
				map.addOverlay(overMarker);
				overlaysMap.put(cableJid +1,overMarker);
			} else {
				
				if(kpoints[kpoints.length - 1].lng == e.point.lng 
						&& kpoints[kpoints.length - 1].lat == e.point.lat){
					return;
				}
				kpoints[kpoints.length] = new BMap.Point(e.point.lng, e.point.lat);
			}
				polylinesss.setPath(kpoints); 
		} else {
			resume = 1;
		}
		rightClickVisible = 1;
	}
	function clearLastPoint() {
		if(kpoints.length > 1&&rightClickVisible == 1){
			//map.removeOverlay(overlaysList[overlaysList.length-1]);
			var pointSize = kpoints.length;
			kpoints.pop();
			polylinesss.setPath(kpoints);
		}
	};
	</script>
</body>
</html>