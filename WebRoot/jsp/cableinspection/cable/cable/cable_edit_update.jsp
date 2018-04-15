<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../../util/head.jsp"%>
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
	var myPoints = new Array();
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
			    r=180/3.14169
				var a1=e.point.lat/r;
				var a2=e.point.lng/r;
				var b1=linePointsList[linePointsList.length-1].LATITUDE/r;
				var b2=linePointsList[linePointsList.length-1].LONGITUDE/r;
				var c1=Math.acos(Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2)+
				Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2)+
				Math.sin(a1)*Math.sin(b1))*6366000
				var c2=c1;
				for(var i=myPoints.length-1;i>0;i--){
					var bb1=myPoints[i].lat/r;
					var bb2=myPoints[i].lng/r;
					var aa1=myPoints[i-1].lat/r;
					var aa2=myPoints[i-1].lng/r;
					if(myPoints[i].type<0){
						c2 += Math.acos(Math.cos(aa1)*Math.cos(aa2)*Math.cos(bb1)*Math.cos(bb2)+
				Math.cos(aa1)*Math.sin(aa2)*Math.cos(bb1)*Math.sin(bb2)+
				Math.sin(aa1)*Math.sin(bb1))*6366000
					}else if(i=1){
						c2 += Math.acos(Math.cos(aa1)*Math.cos(aa2)*Math.cos(bb1)*Math.cos(bb2)+
				Math.cos(aa1)*Math.sin(aa2)*Math.cos(bb1)*Math.sin(bb2)+
				Math.sin(aa1)*Math.sin(bb1))*6366000
				break;
					}else{
						break;
					}
				}
				if(c1>200){
					$.messager.show({
						title : '提  示',
						msg : '非关键点之间不能超过200米',
						showType : 'show'
					});
				}else if(c2>500){
					$.messager.show({
						title : '提  示',
						msg : '500米内必须有一个关键点',
						showType : 'show'
					});
				}else{
					linePointsList.push(pointInLine);
					myPoints[myPoints.length]= new myPoint(e.point.lng, e.point.lat,-1);
					showPoints(linePointsList);
				}
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
				$.messager.show({
							title : '提  示',
							msg : '第一个点必须为关键点',
							showType : 'show'
						});
				return;
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
				myPoints[myPoints.length]= new myPoint(e.point.lng, e.point.lat,-1);
			}
				polylinesss.setPath(points); 
				//两个点不超过200米
				if(points.length>1){
				    r=180/3.14169
					var a1=points[points.length-2].lat/r;
					var a2=points[points.length-2].lng/r;
					var b1=e.point.lat/r;
					var b2=e.point.lng/r;
					var c1=Math.acos(Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2)+
					Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2)+
					Math.sin(a1)*Math.sin(b1))*6366000
					if(c1>200){
						$.messager.show({
							title : '提  示',
							msg : '非关键点之间不能超过200米',
							showType : 'show'
						});
						var pointSize = points.length;
						points.pop();
						polylinesss.setPath(points);
					}
				}
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
					var showline = "<table class='xiazy' id='tb"+lineId+"' ><th>光缆段"
					+ lineId
					+ "<input type='hidden' name='line_id' value='"+lineId+"'></th>"
					+ "<tr><td width='30%'>编号：</td><td width='60%'><div class='condition-text-container'><input class='condition-text' type='text' name='line_no' value='"+data.lineNo+"'/></div></td></tr>"
					+ "<tr><td width='30%'>名称：</td><td width='60%'><div class='condition-text-container'><input class='condition-text' name='line_name' type='text' value='"+data.lineName+"'/></div></td></tr>"
					+ "<tr><td width='30%'>光缆等级：</td><td width='60%'><select class='condition-select' name='line_level' id='line_level'>"
					+ "<option value='1'>中继光缆</option>"
					+"<option value='2'>主干光缆</option>"
					+"<option value='3'>其他</option>"
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

						//关键点非关键点互改
						 marker.addEventListener("click", function() 
						{	
							editPoint(POINT_ID,POINT_NO,POINT_TYPE);
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
		map.clearOverlays();
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
						myPoints[myPoints.length]= new myPoint(data["LONGITUDE"], data["LATITUDE"],data["POINT_TYPE"]);
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
			
			//关键点非关键点互改
			 marker.addEventListener("click", function() 
			{	
				editPoint(POINT_ID,POINT_NO,POINT_TYPE);
			});
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
		polylinesss = new BMap.Polyline(linePoints, {
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
						//距离判断
						var update=true;
						 r=180/3.14169
						var a1=linePointsList[flag-1].LATITUDE/r;
						var a2=linePointsList[flag-1].LONGITUDE/r;
						var c1=100;
						if(flag<linePointsList.length-1){
							var d1=linePointsList[flag+1].LATITUDE/r;
							var d2=linePointsList[flag+1].LONGITUDE/r;
							c1=Math.acos(Math.cos(a1)*Math.cos(a2)*Math.cos(d1)*Math.cos(d2)+
							Math.cos(a1)*Math.sin(a2)*Math.cos(d1)*Math.sin(d2)+
							Math.sin(a1)*Math.sin(d1))*6366000
							if(linePointsList[flag-1].POINT_TYPE==4&&linePointsList[flag+1].POINT_TYPE==4){
								if(c1>500){
								$.messager.show({
									title : '提  示',
									msg : '关键点之间不能超过500米',
									showType : 'show'
								});
								update=false;
							}
						}
						}

						if(c1>200){
							$.messager.show({
								title : '提  示',
								msg : '两点之间不能超过200米',
								showType : 'show'
							});
							update=false;
						}
						if(update){
							linePointsList.remove(flag);
							showPoints(linePointsList);
						}
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
		var update=true;
		//距离判断
		 r=180/3.14169
		var a1=lat/r;
		var a2=lng/r;
		var b1=linePointsList[POINT_SEQ-2].LATITUDE/r;
		var b2=linePointsList[POINT_SEQ-2].LONGITUDE/r;
		var c2=100;
		if(POINT_SEQ<linePointsList.length-1){
			var d1=linePointsList[POINT_SEQ].LATITUDE/r;
			var d2=linePointsList[POINT_SEQ].LONGITUDE/r;
			c2=Math.acos(Math.cos(a1)*Math.cos(a2)*Math.cos(d1)*Math.cos(d2)+
			Math.cos(a1)*Math.sin(a2)*Math.cos(d1)*Math.sin(d2)+
			Math.sin(a1)*Math.sin(d1))*6366000
		}
		var c1=Math.acos(Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2)+
		Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2)+
		Math.sin(a1)*Math.sin(b1))*6366000
		if(linePointsList[POINT_SEQ-2].POINT_TYPE==4&&linePointsList[POINT_SEQ-1].POINT_TYPE==4){
			if(c1>500){
			$.messager.show({
				title : '提  示',
				msg : '关键点之间不能超过500米',
				showType : 'show'
			});
			update=false;
			return;
			}
		}
		if(linePointsList[POINT_SEQ].POINT_TYPE==4&&linePointsList[POINT_SEQ-1].POINT_TYPE==4){
			if(c2>500){
			$.messager.show({
				title : '提  示',
				msg : '关键点之间不能超过500米',
				showType : 'show'
			});
			update=false;
			return;
			}
		}
		if(c1>200||c2>200){
			$.messager.show({
				title : '提  示',
				msg : '两点之间不能超过200米',
				showType : 'show'
			});
			update=false;
			return;
		}
		if(update){
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
					msg : '请输入对应光缆段编号!',
					showType : 'show'
				});
				xzy.eq(i).find("input[name='line_no']").focus();
				return;
			}
			var linename = xzy.eq(i).find("input[name='line_name']").val();
			if (linename == "") {
				$.messager.show({
					title : '提  示',
					msg : '请输入对应光缆段名称!',
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
	//关键点非关键点互改
function editPoint(POINT_ID,POINT_NO,POINT_TYPE){
		if(POINT_TYPE==-1){
				$.messager.confirm('系统提示', '您确定要将'+POINT_NO+'修改为关键点吗?',
				function(r) {
					if (r) {
						$('#win').window({
							title : "【输入信息】",
							href : webPath + "Cable/editPointPage.do?point_id="+POINT_ID,
							width : 350,
							height : 400,
							zIndex : 2,
							region : "center",
							collapsible : false,
							cache : false,
							modal : true
						});
					}
				});
			}else{
				$.messager.confirm('系统提示', '您确定要将'+POINT_NO+'修改为非关键点吗?',
				function(r) {
					if (r) {
						$.ajax({
							type : 'POST',
							url : "editPoint.do",
							data : {
								POINT_ID : POINT_ID,
								POINT_TYPE : -1
							},
							dataType : 'json',
							success : function(json) {
								$.messager.show({
										title : '提  示',
										msg : '修改成功',
										showType : 'show'
								});
								marker.setIcon(normalIcon);
							}
						});
					}
				});
			}
				
}
	function update() {
		$.messager.confirm('系统提示', '确认修改?', function(r) {
			if (r) {
				var POINT_NO = $("input[name='POINT_NO']").val();
				var POINT_NAME = $("input[name='POINT_NAME']").val();
				var EQP_TYPE_ID = $("select[name='EQP_TYPE_ID']").val();
				var POINT_TYPE = $("select[name='POINT_TYPE']").val();
				var POINT_LEVEL = $("select[name='POINT_LEVEL']").val();
				var AREA_TYPE = $("select[name='AREA_TYPE']").val();
				var SON_AREA_ID = $("select[name='SON_AREA_ID']").val();
				if(POINT_NO==""||POINT_NO==null){
					$.messager.alert('提示', "请输入编码！",'info');
					return;
				}else if(POINT_NAME==""||POINT_NAME==null){
					$.messager.alert('提示', "请输入名称！",'info');
					return;
				}else if(EQP_TYPE_ID==""||EQP_TYPE_ID==null){
					$.messager.alert('提示', "请输入设备类型！",'info');
					return;
				}else if(POINT_TYPE==""||POINT_TYPE==null){
					$.messager.alert('提示', "请输入点类型！",'info');
					return;
				}/*else if(POINT_LEVEL==""||POINT_LEVEL==null){
					$.messager.alert('提示', "请输入维护等级！",'info');
					return;
				}else if(AREA_TYPE==""||AREA_TYPE==null){
					$.messager.alert('提示', "请输入设备位置！",'info');
					return;
				}else if(SON_AREA_ID==""||SON_AREA_ID==null){
					$.messager.alert('提示', "请输入区域！",'info');
					return;
				}*/
				$('#formp').form('submit', {
					url : "editPoint.do",
					onSubmit : function() {
						$.messager.progress();
					},
					success : function(data) {
					//var flag = eval("("+data+")");
						$.messager.progress('close'); // 如果提交成功则隐藏进度条
						//$('#win').window('close');
						$.messager.alert('提示', "更新成功！",'info');
						marker.setIcon(checkedIcon);
					}
				});
			}
		});
	}
function myPoint(lng,lat,type){
this.lng=lng; 
this.lat=lat;
this.type=type;
}
</script>
</body>
</html>
