<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../../../util/head.jsp"%>
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

#l-map {
	height: 100%;
	width: 300px;
	float: left;
}

#r-result {
	height: 100%;
	width: 280px;
	float: right;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<title>光缆段新增</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<div id="r-result">
		<div style="border-bottom: 1px solid #d2d2d2; padding:10px;">
			<form id="ff">
				<input type="hidden" name="inspact_id" id="inspact_id" />
				<input type="hidden" name="inspact_type" id="inspact_type" />
				<table id="tbl1">
					<tr>
						<td>任务执行人或岗位：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="inspact_name" id="inspact_name"
									onClick="getInspactPerson()"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>开始日期：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="start_date" id="start_date"
									onClick="WdatePicker();"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>结束日期：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="end_date" id="end_date"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'});"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>任务所属网格：</td>
						<td>
				
							<select class="condition-select" name="dept" id="dept" >
							<option value="">请选择</option>
							<c:forEach items="${deptList}" var="dept">
								<option value="${dept.DEPT_NO}">${dept.DEPT_NAME }</option>
							</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
	
		<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="saveCable();">保存</div>
			<div style"margin-left: 10px;" class="btn-operation"
				onClick="history.back();">返回</div>
		</div>
		<div>----设备显示----</div>
		<div id="pointsInfos"  name="pointsInfos" style="height:50%;width:100%;overflow:auto" ></div>
		<!--
			<div style="border-bottom: 1px solid #d2d2d2; padding-left: 5px;overflow: auto;" id="ff">
			</div>
		-->
		<div id="win_staff"></div>
	</div>
	<div style="border-bottom: 1px solid #d2d2d2; padding-left: 5px; display: none; background-color: white;"
			id="showPointquery">
			<table>
				<tr>
					<td nowrap="nowrap" width="80px">搜索类型</td>
					<td><select class="condition-select" name="query_type"
						id="query_type">
							<option value='0'>按道路名称搜索</option>
							<option value='1'>按设备名称搜索</option>
					</select></td>
				</tr>
				<tr class="queryRode">
					<td>道路名称</td>
					<td><div class="condition-text-container">
							<input name="road_name" type="text" class="condition-text" />
						</div></td>
				</tr>
				<tr class="searchEqp">
					<td>设备规格</td>
					<td><select class="condition-select" name="eqp_type" id="eqp_type">
								<option value="">--请选择--</option>
								<c:forEach items="${eqpList}" var="al">
									<option value="${al.EQUIPMENT_TYPE_ID}">
										${al.EQUIPMENT_TYPE_NAME}</option>
								</c:forEach>
							</select>
					</td>
				</tr>
				<tr class="searchEqp">
					<td>设备名称</td>
					<td><div class="condition-text-container">
							<input name="eqp_name" type="text" class="condition-text" />
						</div></td>
				</tr>
				<tr>
					<td>创建情况</td>
					<td><select class="condition-select" name="isUsed" id="isUsed">
								<option value="">--请选择--</option>
								<option value="1">已创建</option>
								<option value="0">未创建</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>所属区县</td>
					<td><select class="condition-select" name="sonArea" id="sonArea">
								<c:if test="${fn:length(sonAreaList) > 1}">
								<option value="">--请选择--</option>
								</c:if>
								<c:forEach items="${sonAreaList}" var="a">
									<option value="${a.AREA_ID}">
										${a.NAME}</option>
								</c:forEach>
							</select>
					</td>
				</tr>
				
				<tr>
					<td>所属网格</td>
					<td><select class="condition-select" name="dept_no" id="dept_no">
								<option value="">--请选择--</option>
								<c:forEach items="${deptList}" var="d">
									<option value="${d.DEPT_NO}">
										${d.DEPT_NAME}</option>
								</c:forEach>
							</select>
					</td>
				</tr>
				<tr>
				<td>光交类型</td>
					<td><select class="condition-select" name="area_type" id="area_type">
								<option value="">--请选择--</option>
								<option value="1">小区</option>
								<option value="2">路边</option>
							</select>
					</td>
				</tr>
				<tr>
					<td><div class="btn-operation" onClick="queryPoint();">
							搜索</div></td>
					<td><div class="btn-operation" onClick="hideQueryPoint();">
							隐藏</div></td>
				</tr>

			</table>
		</div>
	<input id="lineId" type="hidden" value="${LINE_ID}" />
	<input id="areaName" type="hidden" value="${AREA_NAME}" />
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
	$("#showPointquery").hide();
	$(".searchEqp").hide();
	//开始绘图
	var ifhuitu = 1;
	var areaname = $("#areaName").val().trim();
	// 百度地图API功能
	$("#l-map").css("width", document.body.clientWidth - 290);
	$("#ff").css("max-height", $("#l-map").height() - 50);
	var map = new BMap.Map("l-map");
	if (areaname != null) {
		map.centerAndZoom(areaname, 15);
	} else {
		map.centerAndZoom("南京", 15);
	}
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	
	var checkedIcon = new BMap.Icon(webPath + "images/icon_checked.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var greenIcon = new BMap.Icon(webPath + "images/icon_sign.png", 
			new BMap.Size(21, 25), {anchor: new BMap.Size(10, 21), infoWindowAnchor: new BMap.Size(10, 0)});
	
	var lineId = $("#lineId").val();
	var points = new Array();
	var overlays = [];
	var deletelays = [];
	var cableJid = 0;
	var resume = 1;//0点击了关键点
	var polylinesss;
	var overlaysList = new Array();
	var overlaysMap = new HashMap();
	//点击开始，右键可用置1，结束后，右键置0，未开始
	var rightClickVisible = 0;
	//点击开始后不展示点信息
	var showInfoWindowFlag = 0;
	
	$(window).resize(function(){
		$("#l-map").css("width", document.body.clientWidth - 290);
		$("#ff").css("max-height", $("#l-map").height() - 35);
	});
	
	//开始控件
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
			$("#pointsInfos tbody").html("");
			points.length = 0;
			// map.disableDragging();
			//map.disableScrollWheelZoom();
			map.disableDoubleClickZoom();
			ifhuitu = 0;
			//var polyline = new BMap.Polyline(points, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
			polylinesss = new BMap.Polyline(points, {
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
			if(ifhuitu == 1 || points.length < 2){
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
			for ( var i = 0; i < points.length; i++) {
				linepoint += points[i].lng + ":" + points[i].lat + ",";
			}
			if (linepoint > 1) {
				linepoint = linepoint
						.substring(0, linepoint.length - 1);
			}
			cableJid = cableJid + 1;
			var showline = "<table class='xiazy' id='tb"+cableJid+"' ><th>区域"
					+ cableJid
					+ "<input type='hidden' name='linepoint' value='"+linepoint+"'></th><th><div class='btn-operation' id='"
					+ cableJid
					+ "' onClick='deleteLine(this);'>删除</div></th>"
					+ "<tr><td width='30%'>编号：</td><td width='60%'><div class='condition-text-container'><input class='condition-text' type='text' name='line_no'/></div></td></tr>"
					+ "<tr><td width='30%'>名称：</td><td width='60%'><div class='condition-text-container'><input class='condition-text' name='line_name' type='text'/></div></td></tr>"
					+ "<tr><td width='30%'>维护等级：</td><td width='60%'><select class='condition-select' name='line_level' id='plan_circle'>"
					+ "<option value='1'>1</option><option value='2'>2</option><option value='3'>3</option></select></td></tr></table>";
			$("#ff").append(showline);
			map.removeEventListener("click", test);
			//清除线段
			points = new Array();
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
			var xzy = $(".xiazy");
			var jsonstring = "[";
		for ( var i = 0; i < xzy.length; i++) {
			jsonstring += "{\"linepoint\":\""
					+ xzy.eq(i).find("input[name='linepoint']").val()
					+ "\"},";
		}
		jsonstring+="]";
			var pointStr  = null;
			var sonArea = $("#sonArea").val();
						$.ajax({
							type : 'POST',
							url : "getPontsByZone.do",
							data : {
								ponintsObj : jsonstring,
								sonArea : sonArea						
							},
							dataType : 'json',
							success : function(json) {
								
									$("#pointsInfos").html("");
									pointStr="<table id=\"eqp_infos\"><tr><td>类型</td><td>等级</td><td>名称</td></tr>"
									for(var i = 0;i<json.length;i++){
										pointStr+="<tr><td>"
											    +json[i].NAME
											    +"</td><td>"
											    +json[i].LEVELS
											    +"</td><td>"
											    +json[i].TYPE
											    +"</td></tr>"
									}
									pointStr+="</table>"
									$("#pointsInfos").append(pointStr);
									
									}
									
									});
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
	map.addControl(new startControl());
	map.addControl(new completeControl());
	map.addControl(new searchControl());
	
	$('#query_type').change(function() {
		var h = $(this).children('option:selected').val();
		if (h == 1) {
			$(".searchEqp").show();
			$("input[name='road_name']").val('');
			$(".queryRode").hide();
		} else {
			$(".searchEqp").hide();
			$("input[name='eqp_no']").val('');
			$("select[name='eqp_type']").val('');
			$(".queryRode").show();
		}
	});
	function test(e) {
		 if (resume == 1) {
			if(points.length == 0){
				var point = new BMap.Point(e.point.lng, e.point.lat);
				points[points.length] = point;
				var  overMarker=new BMap.Marker(point, {icon: fireIcon});
				overlaysList.push(overMarker);
				map.addOverlay(overMarker);
				overlaysMap.put(cableJid +1,overMarker);
			} else {
				
				if(points[points.length - 1].lng == e.point.lng 
						&& points[points.length - 1].lat == e.point.lat){
					return;
				}
				points[points.length] = new BMap.Point(e.point.lng, e.point.lat);
			}
				polylinesss.setPath(points); 
		} else {
			resume = 1;
		}
		rightClickVisible = 1;
	}
	
	function clearAll() {
		for ( var i = 0; i < overlays.length; i++) {
			map.removeOverlay(overlays[i]);
		}
		overlays.length = 0;
	};
	//删除最后一个点 add by ningruofan
	function clearLastPoint() {
		if(points.length > 1&&rightClickVisible == 1){
			//map.removeOverlay(overlaysList[overlaysList.length-1]);
			var pointSize = points.length;
			points.pop();
			polylinesss.setPath(points);
		}
	};
		//删除最后一条缆线 add by ningruofan
	function clearLastLine() {
		//var allOverlay = map.getOverlays();
		//alert(overlaysList.length);
		if(rightClickVisible == 1){
			map.removeOverlay(overlaysList[overlaysList.length-1]);
			//overlaysList.pop();
			points.length=0;
			polylinesss.setPath(points);
		}
	};

	$.ajax({
		url : "getCable.do",
		contentType : "application/json",
		type : "get",
		dataType : "json",
		data : {
			"line_id" : lineId
		},
		success : function(datas) {
			if(datas.length > 0){
				$.each(datas, function(i, data) {
					var pointList = data.pointMode;
					var linePoints = new Array();
					$.each(pointList, function(j, points1) {
						linePoints[linePoints.length] = new BMap.Point(
								points1.latitude, points1.longitude);
					});
					var color = "gray";
					if(data.isExistPlan !=0){
						if(data.lineLevel == 1) {
							color = "#FF0000";
						} 
						if(data.lineLevel == 2) {
							color = "#1739C7";
						} else if(data.lineLevel == 3) {
							color = "#28833A";
						}
					}
					var polyline1 = new BMap.Polyline(linePoints, {
						strokeColor : color,
						strokeWeight : 6,
						strokeOpacity : 0.8
					});
					map.addOverlay(polyline1);
					var info = "<div class='map_bubble_info'>缆线ID：" + data.lineId
							+ "<br/>缆线编码：" + data.lineNo + "<br/>缆线名称："
							+ data.lineName + "<br/>创建时间：" + data.createTime
							+ "<br/>创建者：" + data.createStaff + "</div>";
					var title = "<div class='map_bubble_title'>" + data.lineId
							+ "</div>";
					var opts = {
						width : 250, // 信息窗口宽度
						height : 0, // 信息窗口高度
						title : title, // 信息窗口标题
						enableMessage : false
					//设置允许信息窗发送短息
					};
					var infoWindow = new BMap.InfoWindow(info, opts);
					polyline1.addEventListener("mouseover", function(e) {
						if (map.getInfoWindow() == null || map.getInfoWindow() != infoWindow) {
							var point = new BMap.Point(e.point.lng, e.point.lat);
							map.openInfoWindow(infoWindow, point);
						}
					});
					polyline1.addEventListener("mouseout", function() {
						//map.closeInfoWindow();
					});
				});
			} 
		}
	});
	
	function hideQueryPoint() {
		$("#showPointquery").hide();
	}
	function queryPoint() {
		map.clearOverlays();
		$.messager.progress();
		$.ajax({
			url : "getPoints.do",
			//contentType:"application/json",
			type : "POST",
			dataType : "json",
			data : {
				roadName : $("input[name='road_name']").val().trim(),
				eqpName : $("input[name='eqp_name']").val().trim(),
				eqpType : $("select[name='eqp_type']").val().trim(),
				isUsed : $("select[name='isUsed']").val().trim(),
				sonArea : $("select[name='sonArea']").val().trim(),
				dept_no : $("select[name='dept_no']").val().trim(),
				area_type :$("select[name='area_type']").val().trim(),
				play_kind:2
				
			},
			success : function(datas) {
				$.messager.progress('close'); 
				if(datas != null && datas.length > 0) {
					var vi = new Array();
					$.each(datas, function(i, data) {
						var POINT_ID = data["POINT_ID"];
						var POINT_NO = data["POINT_NO"];
						var POINT_NAME = data["POINT_NAME"];
						//var POINT_LEVEL = data["POINT_LEVEL"];
						//var POINT_TYPE = data["POINT_TYPE"];
						var LONGITUDE = data["LONGITUDE"];
						var LATITUDE = data["LATITUDE"];
						var CREATE_TIME = data["CREATE_TIME2"];
						var STAFF_NAME = data["STAFF_NAME"];
						//var radius = data["radius"];
						var IS_USED = data["IS_USED"];

						var info = "<div class='map_bubble_info'>编码：" + POINT_NO
						//+ "<br/>经度：" + LONGITUDE
						// + "<br/>纬度：" + LATITUDE
						//+ "<br/>dizhi：" + LATITUDE
						+ "<br/>创建时间：" + CREATE_TIME + "<br/>创建者：" + STAFF_NAME
								+ "</div>";
						var point = new BMap.Point(LONGITUDE, LATITUDE);
						vi[vi.length] = point;
						var marker;
						var labelColor;
						if (IS_USED == 0) {
							marker = new BMap.Marker(point);
							labelColor = "#FF1111";
						} else {
							marker = new BMap.Marker(point, {icon : greenIcon});
							labelColor = "#097F09";
						}
						
						var label = new BMap.Label(POINT_NAME, {position:point,offset:new BMap.Size(0, 20)});
						label.setStyle({
							border : "0",
							color : labelColor,
							offset : new BMap.Size(0, -30),
							backgroundColor :"0.00"
						});
						marker.setLabel(label);
						title = "<div class='map_bubble_title'>" + POINT_NAME
								+ "</div>";

						map.addOverlay(marker);
						var opts = {
							width : 250, // 信息窗口宽度
							height : 0, // 信息窗口高度
							title : title, // 信息窗口标题
							offset : new BMap.Size(0, -30),
							enableMessage : false
						//设置允许信息窗发送短息
						};
						var infoWindow = new BMap.InfoWindow(info, opts); // 创建信息窗口对象

						marker.addEventListener("click", function() {
							var currentIconUrl =  marker.getIcon().imageUrl;
							//alert(currentColor);
							if (ifhuitu == 0) {
								//最后一个点和当前点相同标志
								var samePointFlag = 0;
								//当前点存在当前缆线中
								var existPointFlag = 0;
								if(points.length > 0){
									if(points[points.length - 1].lng == point.lng 
											&& points[points.length - 1].lat == point.lat){
										samePointFlag=1;
									}
								}
								//遍历关键点，判断是否已存在
								 for ( var i = 0; i < points.length; i++) {
									if(points[i].lng == point.lng 
											&& points[i].lat == point.lat)
									{
										existPointFlag = 1;
										break;
									}
								} 
								//判断是否已存在
								 if(existPointFlag  == 1)
								{
									$.messager.show({
										title : '提  示',
										msg : '关键点已存在缆线中!',
										showType : 'show'
									});
								}
								//alert(currentIconUrl.indexOf('marker_red_sprite.png')+'existPointFlag:'+existPointFlag);
								if(currentIconUrl.indexOf('marker_red_sprite.png') ==-1  && existPointFlag  == 0)
								{
									$.messager.confirm('系统提示',
										'此关键已在其他缆线段中使用，是否确认添加?',
										function(r) {
											if (r) {
												//如果最后一点和点击的点不相同，且点击的点不存在当前缆线中
												if(samePointFlag  == 0 && existPointFlag == 0){
													if(points.length > 0){
														marker.setIcon(checkedIcon);
													} else {
														marker.setIcon(fireIcon);
													}
													points[points.length] = point;
													polylinesss.setPath(points);
												}
												
											}
											else{
												//existLineContinueFlag  = 0;
											}
										});
								}
								else
								{
									//如果最后一点和点击的点不相同，且点击的点不存在当前缆线中
									if(samePointFlag  == 0 && existPointFlag == 0){
										if(points.length > 0){
											marker.setIcon(checkedIcon);
										} else {
											marker.setIcon(fireIcon);
										}
										
										points[points.length] = point;
										polylinesss.setPath(points);
									}
								}
								resume = 0;
							}
						});
						marker.addEventListener("mouseover", function(e) {
						//如 果右键处在开始-结束之间，则不展示点的详细信息
							if (showInfoWindowFlag==0){
								if (map.getInfoWindow() == null || map.getInfoWindow() != infoWindow) {
									map.openInfoWindow(infoWindow, point);
								}
							}
						});
						marker.addEventListener("mouseout", function() {
							//map.closeInfoWindow();
						});
					});
					map.setViewport(vi);
				} else {
					$.messager.show({
						title : '提  示',
						msg : '没有检索到关键点!',
						showType : 'show'
					});
				}
			}
		});
		$("#showPointquery").hide();
	}
function saveCable() {
	if ($("#ff").form('validate')) {
				
		var planObj = JSON.stringify(makeParamJson('ff'));
		
		var xzy = $(".xiazy");
		if (xzy.length <= 0) {
			$.messager.show({
				title : '提  示',
				msg : '请先划分维护区域!',
				showType : 'show'
			});
			return;
		}
		var dept_no = $("select[name='dept']").val();
		if(dept_no==""){
			$.messager.show({
				title : '提  示',
				msg : '请选择网格!',
				showType : 'show'
			});
			return;
		}
		var jsonstring = "[";
		for ( var i = 0; i < xzy.length; i++) {
			var lineno = xzy.eq(i).find("input[name='line_no']").val();
			if (lineno.trim() == "") {
				$.messager.show({
					title : '提  示',
					msg : '请输入对应区域编号!',
					showType : 'show'
				});
				xzy.eq(i).find("input[name='line_no']").focus();
				return;
			}
			var linename = xzy.eq(i).find("input[name='line_name']").val();
			if (linename == "") {
				$.messager.show({
					title : '提  示',
					msg : '请输入对应区域名称!',
					showType : 'show'
				});
				xzy.eq(i).find("input[name='line_name']").focus();
				return;
			}
			var linelevel = xzy.eq(i).find("select[name='line_level']").val();

			jsonstring += "{\"line_no\":\""
					+ xzy.eq(i).find("input[name='line_no']").val()
					+ "\",\"line_name\":\""
					+ xzy.eq(i).find("input[name='line_name']").val()
					+ "\",\"linepoint\":\""
					+ xzy.eq(i).find("input[name='linepoint']").val()
					+ "\",\"line_level\":\""
					+ linelevel
					+ "\"},";
		}
		$.messager.confirm('系统提示', '您确定要保存维护区域?',
				function(r) {
					if (r) {

						if (jsonstring.length > 1) {
							jsonstring = jsonstring.substring(0,
									jsonstring.length - 1);
						}
						jsonstring += "]";
						//var obj =   eval('(' + jsonstring + ')');
						$.ajax({
							type : 'POST',
							url : "saveCable.do?shape=polygon",
							data : {
								cableObj : jsonstring,
								planObj	 : planObj								
							},
							dataType : 'json',
							success : function(json) {
								if (json.ifsuccess) {
									$.messager.show({
										title : '提  示',
										msg : '保存区域成功!',
										showType : 'show'
									});

								}
								//$("#tabs" ).tabs({selected:2});
								//parent.$("#tabs").tabs("select", "缆线管理");
								//closeTab();

								//setTimeout(closeTab(),5000);
								location.href = 'index.do';
							}
						});
					}
				});
		}
	}
	function deleteLine(e) {
		var deleteId = e.id + "";
		map.removeOverlay(overlaysMap.get(deleteId));
		map.removeOverlay(polylinesss);
		clearLastLine();
		overlaysMap.remove(deleteId);
		var points = $("#tb" + deleteId).find("input[name='linepoint']").val()
				+ "";
		var lineArray = new Array();
		points = points.substr(0, points.length - 1);
		var strs = new Array();
		strs = points.split(",");
		for ( var j = 0; j < strs.length; j++) {
			var strs1 = new Array();
			strs1 = strs[j].split(":");
			var point = new BMap.Point(strs1[0], strs1[1]);
			lineArray[lineArray.length] = point;
		}
		var linelength = lineArray.length;
		//alert(overlays[0].getPath().length);
		for ( var i = 0; i < deletelays.length; i++) {
			var len = deletelays[i].getPath().length;
			if (linelength != len) {
			} else {
				for ( var k = 0; k < len; k++) {
					if (lineArray[k].lng == deletelays[i].getPath()[k].lng
							&& lineArray[k].lat == deletelays[i].getPath()[k].lat) {
						if (k == len - 1) {
							map.removeOverlay(deletelays[i]);
						}
					} else {
						break;
					}
				}

			}
		}
		$("#tb" + deleteId).remove();
		$("#pointsInfos").empty();
	}
	
	function getInspactPerson() {
		$('#win_staff').window(
		{
			title : "【选择人员】",
			href : webPath + "Cable/index_cablePlanInspector.do",
			width : 580,
			height : 400,
			zIndex : 2,
			region : "center",
			collapsible : false,
			cache : false,
			modal : true
		});
		
		// alert(JSON.stringify(makeParamJson('ff')));
	}
	
</script>
</body>
</html>
