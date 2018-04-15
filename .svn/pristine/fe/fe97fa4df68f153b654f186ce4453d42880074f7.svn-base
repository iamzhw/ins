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
		<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="saveCable();">保存</div>
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="javascript:closeTab();">返回</div>
		</div>
		<div style="border-bottom: 1px solid #d2d2d2; padding-left: 5px;overflow: auto;" id="ff">
			
		</div>
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
					<td>所属综合化维护班组</td>
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
					<td><div class="btn-operation" onClick="queryPoint();">
							搜索</div></td>
					<td><div class="btn-operation" onClick="hideQueryPoint();">
							隐藏</div></td>
				</tr>

			</table>
		</div>
		 <div id="win"></div>
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
	 /* if (areaname != null) {
		map.centerAndZoom(areaname, 15);
	} else {
		map.centerAndZoom("南京", 15);
	}  */
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	
	var checkedIcon = new BMap.Icon(webPath + "images/icon_checked.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var greenIcon = new BMap.Icon(webPath + "images/icon_sign.png", 
			new BMap.Size(21, 25), {anchor: new BMap.Size(10, 21), infoWindowAnchor: new BMap.Size(10, 0)});
		var normalIcon = new BMap.Icon(webPath + "images/feiguanjian.png", 
	new BMap.Size(21, 25), {anchor: new BMap.Size(10, 21), infoWindowAnchor: new BMap.Size(10, 0)});
	
	var lineId = $("#lineId").val();
	var points = new Array();
	var linePointsList = new Array(); 
	var mapPointsList =new HashMap();
	var pointInLine ={};
	var overlays = [];
	var deletelays = [];
	var cableJid = 0;
	var resume = 1;//0点击了关键点
	var polylinesss;
	var overlaysList = new Array();
	var overlaysMap = new HashMap();
	var line
	//点击开始，右键可用置1，结束后，右键置0，未开始
	var rightClickVisible = 0;
	//点击开始后不展示点信息
	var showInfoWindowFlag = 0;
	var pointsListJson;//缆线中的点的JSON对象
	var clickPointJson;//在地图上点击的点的JSON对象
	
	$(window).resize(function(){
		$("#l-map").css("width", document.body.clientWidth - 290);
		$("#ff").css("max-height", $("#l-map").height() - 35);
	});
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
	map.addControl(new searchControl());
	
	//地图上右键点击非覆盖物
	map.addEventListener("rightclick",function(e){
    	if(e.overlay){
      		
      	}else{
      		var pointInLine ={};
			pointInLine.LONGITUDE = e.point.lng;
			pointInLine.LATITUDE = e.point.lat;
			pointInLine.SITE=e.point.lng+""+e.point.lat;
			pointInLine.POINT_TYPE = "-1";
	        delMarker(e,e.point.lng,e.point.lat,-1,pointInLine);
     	}
   	}); 
   	//地图上右键点击非覆盖物
	map.addEventListener("click",function(e){
		var key = e.point.lng+""+e.point.lat;
		var flag = false;
    	if(e.overlay){
    		//alert("覆盖物");
    		//var key =e.overlay
    		/* if(mapPointsList.containsKey(key))
    		{
    			//alert("包含");
    			var maker = mapPointsList.get(key);
    			var pointInLine ={};
				pointInLine.POINT_NO = maker.getLabel.context;
				pointInLine.POINT_NAME =maker.getLabel.context;
				pointInLine.LONGITUDE = e.point.lng;
				pointInLine.LATITUDE = e.point.lat;
				pointInLine.SITE=key;
				pointInLine.POINT_TYPE = 4;
				linePointsList.push(pointInLine);
				showPoints(linePointsList);
    		} */
    		
      		
      	}else{
      		var pointInLine ={};
			pointInLine.LONGITUDE = e.point.lng;
			pointInLine.LATITUDE = e.point.lat;
			pointInLine.SITE=key;
			pointInLine.POINT_TYPE = "-1";
	        for(var i=0;i<linePointsList.length;i++)
			{
				if(linePointsList[i].SITE  == key)
				{
					flag = i;
					break;
				}
			}
			if(!flag)
			{
				linePointsList.push(pointInLine);
				showPoints(linePointsList);
			}
     	}
   	}); 
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
						linePoints[linePoints.length] = new BMap.Point(points1.latitude, points1.longitude);
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
					polylinesss = new BMap.Polyline(linePoints, {
						strokeColor : color,
						strokeWeight : 6,
						strokeOpacity : 0.8
					});
					map.addOverlay(polylinesss);
					var info = "<div class='map_bubble_info'>缆线ID：" + data.lineId
							+ "<br/>缆线编码：" + data.lineNo + "<br/>缆线名称："
							+ data.lineName + "<br/>创建时间：" + data.createTime
							+ "<br/>创建者：" + data.createStaff + "</div>";
					var title = "<div class='map_bubble_title'>" + data.lineId
							+ "</div>";
					var showline = "<table class='xiazy' id='tb"+lineId+"' ><th>光缆"
					+ lineId
					+ "<input type='hidden' name='line_id' value='"+lineId+"'></th>"
					+ "<tr><td width='30%'>编号：</td><td width='60%'><div class='condition-text-container'><input class='condition-text' type='text' name='line_no' value='"+data.lineNo+"'/></div></td></tr>"
					+ "<tr><td width='30%'>名称：</td><td width='60%'><div class='condition-text-container'><input class='condition-text' name='line_name' type='text' value='"+data.lineName+"'/></div></td></tr>"
					+ "<tr><td width='30%'>光缆等级：</td><td width='60%'><select class='condition-select' name='line_level' id='line_level'>"
					+ "<option value='1'>1</option>"
					+"<option value='2'>2</option>"
					+"<option value='3'>3</option>"
					+"</select></td></tr></table>";
					$("#ff").append(showline);
					
					$("select[name='line_level']").val(data.lineLevel);
					var opts = {
						width : 250, // 信息窗口宽度
						height : 0, // 信息窗口高度
						title : title, // 信息窗口标题
						enableMessage : false
					//设置允许信息窗发送短息
					};
					var infoWindow = new BMap.InfoWindow(info, opts);
					polylinesss.addEventListener("mouseover", function(e) {
						 if (map.getInfoWindow() == null || map.getInfoWindow() != infoWindow) {
							var point = new BMap.Point(e.point.lng, e.point.lat);
							map.openInfoWindow(infoWindow, point);
						} 
					});
					polylinesss.addEventListener("mouseout", function() {
						map.closeInfoWindow();
					});
					map.setViewport(linePoints);
					queryPointByLineId();
				});
			} 
		}
	});
	
	function hideQueryPoint() {
		$("#showPointquery").hide();
	}
	//搜索点
	function queryPoint() {
		//map.clearOverlays();
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
				dept_no : $("select[name='dept_no']").val().trim()
			},
			success : function(datas) {
				$.messager.progress('close'); 
				if(datas != null && datas.length > 0) {
					var vi = new Array();
					$.each(datas, function(i, data) {
						var POINT_ID = data["POINT_ID"];
						var POINT_NO = data["POINT_NO"];
						var POINT_NAME = data["POINT_NAME"];
						var POINT_TYPE = data["POINT_TYPE"];
						var LONGITUDE = data["LONGITUDE"];
						var LATITUDE = data["LATITUDE"];
						var CREATE_TIME = data["CREATE_TIME2"];
						var STAFF_NAME = data["STAFF_NAME"];
						//var radius = data["radius"];
						var IS_USED = data["IS_USED"];
						//转换成对象存到mapPointsList中
						var pointInLine ={};
						pointInLine.POINT_ID = data["POINT_ID"];
						pointInLine.POINT_NO = data["POINT_NO"];
						pointInLine.POINT_NAME = data["POINT_NAME"];
						pointInLine.LONGITUDE = data["LONGITUDE"];
						pointInLine.LATITUDE = data["LATITUDE"];
						pointInLine.SITE=data["LONGITUDE"]+""+data["LATITUDE"];
						pointInLine.POINT_TYPE = data["POINT_TYPE"];

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
						//	如果页面的点已包含此点，则删除后再重新显示
						if(mapPointsList.containsKey(LONGITUDE+""+LATITUDE))
						{
							
							map.removeOverlay(mapPointsList.get(LONGITUDE+""+LATITUDE));
							mapPointsList.remove(LONGITUDE+""+LATITUDE);
							
						}
						mapPointsList.put(LONGITUDE+""+LATITUDE,marker);
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
							/* if (ifhuitu == 0) {
								var samePointFlag = 0;
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
								resume = 0;
							} */
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
						marker.addEventListener("rightclick", function(e) {
							delMarker(e,this.getPosition().lng,this.getPosition().lat,POINT_TYPE,pointInLine);
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
	function queryPointByLineId() {
		//map.clearOverlays();
		//$.messager.progress();
		$.ajax({
			url : "getPointsInCable.do",
			//contentType:"application/json",
			type : "POST",
			dataType : "json",
			data : {
				lineId : lineId
			},
			success : function(datas) 
			{
				$.messager.progress('close'); 
				if(datas != null && datas.length > 0) 
				{
					$.each(datas, function(i, data) {
						var pointInLine ={};
						pointInLine.POINT_ID = data["POINT_ID"];
						pointInLine.POINT_NO = data["POINT_NO"];
						pointInLine.POINT_NAME = data["POINT_NAME"];
						pointInLine.LONGITUDE = data["LONGITUDE"];
						pointInLine.LATITUDE = data["LATITUDE"];
						pointInLine.SITE=data["LONGITUDE"]+""+data["LATITUDE"];
						pointInLine.POINT_TYPE = data["POINT_TYPE"];
						pointInLine.POINT_SEQ = data["POINT_SEQ"];
						linePointsList.push(pointInLine);
					});
					showPoints(linePointsList);
				}
			}
		});
	}
	function showPoints(linePointsList)
	{
		polylinesss.length=0;
		points.length=0;
		var vi = new Array();
		var linePoints = new Array();
		$.each(linePointsList, function(i, data) 
		{
			var POINT_ID = data.POINT_ID;
			var POINT_NO = data.POINT_NO;
			var POINT_NAME = data.POINT_NAME;
			var LONGITUDE = data.LONGITUDE;
			var LATITUDE = data.LATITUDE;
			var POINT_TYPE = data.POINT_TYPE;
			var POINT_SEQ = i+1;
			//var radius = data["radius"];
			//var IS_USED = data["IS_USED"];
			var info = "<div class='map_bubble_info'>"+   (POINT_NO == undefined ?"":("编码："+POINT_NO+"<br/>")) 
			+ "点类型：" + (POINT_TYPE == -1 ?"非关键点":"关键点") 
			+ "<br/>在缆线中序号：" + POINT_SEQ
			+ "</div>";
			
			linePoints[linePoints.length] = new BMap.Point(LONGITUDE, LATITUDE);
			var point = new BMap.Point(LONGITUDE, LATITUDE);
			vi[vi.length] = point;
			var marker;
			var labelColor;
			if (POINT_TYPE == -1) {
				
				marker = new BMap.Marker(point,{icon : normalIcon});
				marker.enableDragging();
				labelColor = "gray";
			} else {
				if(POINT_SEQ==1)
				{
					marker = new BMap.Marker(point, {icon : fireIcon});
				}
				else
				{
					marker = new BMap.Marker(point, {icon : checkedIcon});
				}
				labelColor = "#097F09";
			}
			
			var label = new BMap.Label( POINT_SEQ+(POINT_NO == undefined ?"":("__编码："+POINT_NO)), {position:point,offset:new BMap.Size(0, 20)});
			label.setStyle({
				border : "0",
				color : labelColor,
				offset : new BMap.Size(0, -30),
				backgroundColor :"0.00"
			});
			marker.setLabel(label);
			title = "<div class='map_bubble_title'>" + POINT_SEQ+(POINT_NO == undefined ?"":("__编码："+POINT_NO))
					+ "</div>";
		//	如果页面的点已包含此点，则删除后再重新显示
			 if(mapPointsList.containsKey(LONGITUDE+""+LATITUDE))
			{
				map.removeOverlay(mapPointsList.get(LONGITUDE+""+LATITUDE));
				mapPointsList.remove(LONGITUDE+""+LATITUDE);
				
			}
			mapPointsList.put(LONGITUDE+""+LATITUDE,marker); 
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

			/* marker.addEventListener("click", function() 
			{
				if (ifhuitu == 0) {
					var samePointFlag = 0;
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
					resume = 0;
					}
			}); */
			marker.addEventListener("mouseover", function(e) {
			//如 果右键处在开始-结束之间，则不展示点的详细信息
				if (showInfoWindowFlag==0){
					if (map.getInfoWindow() == null || map.getInfoWindow() != infoWindow) {
						map.openInfoWindow(infoWindow, point);
					}
				}
			});
			marker.addEventListener("rightclick", function(e)
			{
 		
 				delMarker(e,this.getPosition().lng,this.getPosition().lat,POINT_TYPE,data);
 			});
			marker.addEventListener("mouseout", function() {
				map.closeInfoWindow();
			});
			//拖拽非关键点，改变折线
			marker.addEventListener("dragend", function(e) {
				changeMarkerPosition(e,this.getPosition().lng,this.getPosition().lat,POINT_TYPE,POINT_SEQ)
			});
		});
		map.removeOverlay(polylinesss);
		polylinesss = new BMap.Polygon(linePoints, {
						strokeColor : "red",
						strokeWeight : 6,
						strokeOpacity : 0.8
					});
		polylinesss.addEventListener("mouseup", function(e) {//当鼠标双击时：结束绘制，并可以编辑曲线
        	//alert("1111");
    	});
		map.addOverlay(polylinesss);
		//polylinesss.enableEditing();
		 
		//map.setViewport(vi);
	}
	//地图上右键处理
	function delMarker(e,lng,lat,site_type,data) {
		var key=lng+""+lat;
		var flag=-1;//>-1 已经加入到巡线段
		var windowInfoContent = '是否增加为关键点?';
		var windowInfoTitle = '新增加关键点';
		for(var i=0;i<linePointsList.length;i++)
		{
			if(linePointsList[i].SITE  == key)
			{
				flag = i;
				break;
			}
		}
		var currentIconUrl =  mapPointsList.get(key) == null?"":mapPointsList.get(key).getIcon().imageUrl;
		if(currentIconUrl.indexOf('marker_red_sprite.png') ==-1 && flag==-1 && site_type !=-1)
		{
			windowInfoContent = "关键点已存在别的缆线中，是否增加为关键点?";
		}
		if(site_type == -1)
		{
			windowInfoContent = '是否增加为非关键点?';
			windowInfoTitle = '新增加非关键点';
		}
		//缆线中不存在点
		if(flag==-1){
			$.messager.confirm('系统提示', windowInfoContent,
				function(r) {
					if(r){
						pointsListJson =JSON.stringify(linePointsList);
						clickPointJson =JSON.stringify(data);
						 $('#win').window({
							title : windowInfoTitle,
							href : webPath + "Cable/getSitesByIdsUI.do",
							width : 620,
							height : 400,
							zIndex : 2,
							region : "center",
							collapsible : false,
							cache : false,
							modal : true
						});
					}
				});
		}
		else{
			$.messager.confirm('系统提示', '您确定删除此点?',
				function(r) {
					if(r){
						linePointsList.remove(flag);
						showPoints(linePointsList);
					}
				});
			}
	}
	function changeMarkerPosition(e,lng,lat,site_type,POINT_SEQ) {
		var key=lng+""+lat;
		var siteId=linePointsList[POINT_SEQ-1].SITE;
		var flag=false;//true 已经加入到巡线段
		//var =LONGITUDE;
		//删除l
		if(mapPointsList.containsKey(siteId))
		{
			map.removeOverlay(mapPointsList.get(siteId));
			mapPointsList.remove(siteId);
		}
		linePointsList[POINT_SEQ-1].SITE=key;
		linePointsList[POINT_SEQ-1].LONGITUDE=lng;
		linePointsList[POINT_SEQ-1].LATITUDE=lat;
		
		showPoints(linePointsList);
	}
	/* 
 *  方法:Array.remove(dx) 
 *  功能:根据元素位置值删除数组元素. 
 *  参数:元素值 
 *  返回:在原数组上修改数组 
 *  作者：pxp 
 */
	Array.prototype.remove = function (dx) {  
    	if (isNaN(dx) || dx > this.length) {  
        	return false;  
   		 }  
   	 	for (var i = 0, n = 0; i < this.length; i++) {  
       	 	if (this[i] != this[dx]) {  
           		this[n++] = this[i];  
        	}  
    	}  
    	this.length -= 1;  
	};  
	function saveCable() {
		var xzy = $(".xiazy");
		if (xzy.length <= 0) {
			$.messager.show({
				title : '提  示',
				msg : '请先新增光缆段!',
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
					msg : '请输入对应光缆编号!',
					showType : 'show'
				});
				xzy.eq(i).find("input[name='line_no']").focus();
				return;
			}
			var linename = xzy.eq(i).find("input[name='line_name']").val();
			if (linename == "") {
				$.messager.show({
					title : '提  示',
					msg : '请输入对应光缆名称!',
					showType : 'show'
				});
				xzy.eq(i).find("input[name='line_name']").focus();
				return;
			}
			var linepoint = "";
			for ( var i = 0; i < linePointsList.length; i++) {
				linepoint += linePointsList[i].LONGITUDE + ":" + linePointsList[i].LATITUDE + ",";
			}
			if (linepoint > 1) {
				linepoint = linepoint.substring(0, linepoint.length - 1);
			}
			var linelevel = $("select[name='line_level']").val();
			jsonstring += "{\"line_no\":\""
					+lineno
					+ "\",\"line_name\":\""
					+ linename
					+ "\",\"linepoint\":\""
					+ linepoint
					+ "\",\"line_level\":\""
					+ linelevel
					+ "\",\"line_id\":\""
					+ lineId
					+ "\"},";
		}
		$.messager.confirm('系统提示', '您确定要保存光缆段?',
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
							url : "saveEditedCable.do",
							data : {
								cableObj : jsonstring
							},
							dataType : 'json',
							success : function(json) {
								if (json.ifsuccess) {
									$.messager.show({
										title : '提  示',
										msg : '保存缆线成功!',
										showType : 'show'
									});
									//location.href = 'index.do';
								}
								else {
									$.messager.show({
										title : '提  示',
										msg : '更新失败，请重试!',
										showType : 'show'
									});
									//location.href = 'index.do';
								}
								//$("#tabs" ).tabs({selected:2});
								//parent.$("#tabs").tabs("select", "缆线管理");
								//closeTab();

								//setTimeout(closeTab(),5000);
								
							}
						});
					}
				});

	}
	//选择的点插入操作
	function insertSite() {
		var pointInLine ={};
		var data = eval("("+clickPointJson+")");
		pointInLine.POINT_ID = data.POINT_ID;
		pointInLine.POINT_NO = data.POINT_NO;
		pointInLine.POINT_NAME = data.POINT_NAME;
		pointInLine.LONGITUDE = data.LONGITUDE;
		pointInLine.LATITUDE = data.LATITUDE;
		pointInLine.SITE = data.LONGITUDE +""+ data.LATITUDE;
		pointInLine.POINT_TYPE = data.POINT_TYPE;
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		if (count == 0) {
			$.messager.show({
				title : '提  示',
				msg : '请选取要添加的点!',
				showType : 'show',
				timeout:'1000'//ms
			});
			return;
		}
		else if(count>2)
		{
			$.messager.show({
				title : '提  示',
				msg : '一次最多只能选取两个点!',
				showType : 'show',
				timeout:'1000'//ms
			});
			return;
		}
		else 
		{
			        
			var siteId1 = $('#dg').datagrid('getRowIndex', selected[0]);
			if(count==1)
			{
				if( siteId1==0)//如果，只选择第一个点，默认增加为首点点
				{
					
					linePointsList.splice(0,0,pointInLine);
					showPoints(linePointsList);
				}
				else if(siteId1==(linePointsList.length-1))//如果，只选择最后一个点，默认增加为最后一个点
				{
					linePointsList.push(pointInLine);
					showPoints(linePointsList);
				}
				else
				{
					$.messager.show({
						title : '提  示',
						msg : '请选择首点、末点或相邻两个点！',
						showType : 'show',
						timeout:'1000'//ms
					});
					return;
				}
			}
			else{
				var siteId2=$('#dg').datagrid('getRowIndex', selected[1]);
				var lng2=selected[1].LONGITUDE;
				var lat2=selected[1].LATITUDE;
				if((siteId2-siteId1)!=1)
				{
					$.messager.show({
						title : '提  示',
						msg : '请选择首点、末点或相邻两个点！',
						showType : 'show',
						timeout:'1000'//ms
					});
					return;
				}
				else
				{
					linePointsList.splice(siteId2,0,pointInLine);
					showPoints(linePointsList);
				}
					
			}    
			$("#win").window('close');
					
		}
	}
</script>
</body>
</html>
