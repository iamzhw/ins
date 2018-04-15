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
<script type="text/javascript"
	src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
<title>光缆段新增</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<div id="r-result">
		<div style="border-bottom: 1px solid #d2d2d2; padding:10px;">
			<form id="ff">
				<table id="tbl1">

					<tr>
						<td>光缆：</td>
						<td><select class="condition-select" name="line_id"
							id="line_id" onchange="getRelayByGl(this.value)">
								<option value=''>--请选择--</option>

								<c:forEach items="${gldList}" var="res">
									<option value='${res.CABLE_ID}'>${res.CABLE_NAME}</option>
								</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td>中继段：</td>
						<td><select class="condition-select" name="relay_id"
							id="relay_id" onchange="getSiteByRelayId(this.value)">
								<option value=''>--请选择--</option>


						</select>
						</td>
					</tr>
					<tr>
						<td>长度：</td>
						<td><div class="condition-text-container"><input type="text" class='condition-text'  readonly="readonly" id="distance"></div>
						</td>
					</tr>



					<tr>
						<td>颜色：</td>
						<td><div class="condition-text-container">
								<input
									class="color{required:false,pickerClosable:true} condition-text  condition"
									type="text" name="line_color" id="line_color" value="2E1FFF"
									onchange="setStrokeColor(this.value)" />
							</div>
						</td>
					</tr>
					<tr>
						<td width='30%'>名称：</td>
						<td width='60%'><div class='condition-text-container'>
								<input class='condition-text' name='line_name' id='line_name'
									type='text'  />
							</div>
						</td>
					</tr>
					<tr>
						<td width='30%'>巡线人：</td>
						<td width='60%'><select class='condition-select'
							name='inspect_id' id='inspect_id'>
							<option value=''>--请选择--</option>
							<c:forEach items="${localInspactPerson}" var="res">
								    
								    <option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
								</c:forEach>
							
							</select>
						</td>
					</tr>
                    
                     

				</table>
			</form>
			
		</div >
		<%--<div style="border-bottom: 1px solid #d2d2d2; padding:10px;overflow-x: hidden; overflow-y: auto;height:190px;">
			<form id="ff">
				<table id="tbl2">

					
				</table>
			</form>
			
		</div>
		--%>
		<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="saveCable();">保存</div>
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="history.back();">返回</div>
		</div>
		<div
			style="border-bottom: 1px solid #d2d2d2; padding-left: 5px;overflow: auto;"
			id="ff"></div>
	</div>
    <div id="win"></div>

	<input id="lineId" type="hidden" value="${LINE_ID}" />
	<input id="areaName" type="hidden" value="${staffInfo.AREA_NAME}" />
	
	<input id="siteId" type="hidden" value="" />
	<input id="lng" type="hidden" value="" />
	<input id="lat" type="hidden" value="" />
	<input id="site_type" type="hidden" value="" />
	
	<script type="text/javascript">
  
	$("#showPointquery").hide();
	$(".searchEqp").hide();
	var ifhuitu = 1;
	var areaname = $("#areaName").val().trim();
	// 百度地图API功能
	$("#l-map").css("width", document.body.clientWidth - 290);
	$("#ff").css("max-height", $("#l-map").height() - 50);
	var map = new BMap.Map("l-map");
	if (areaname != null) {
		map.centerAndZoom(areaname, 11);
	} else {
		map.centerAndZoom("南京", 11);
	}
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	
	var checkedIcon1 = new BMap.Icon(webPath + "images/guanjian.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
	var checkedIcon2 = new BMap.Icon(webPath + "images/feiguanjian.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});

	var icon1 = new BMap.Icon(webPath + "images/waili.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
	
	var icon2 = new BMap.Icon(webPath + "images/buxian.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
	

	
	var lineId = $("#lineId").val();
	var points = new Array();
	var overlays = [];
	var deletelays = [];
	var cableJid = 0;
	var resume = 1;//0点击了关键点
	var polylinesss ;
	var overlaysList = new Array();
	
	var rightClickVisible = 0;
	
	$(window).resize(function(){
		$("#l-map").css("width", document.body.clientWidth - 290);
		$("#ff").css("max-height", $("#l-map").height() - 35);
	});
	
	
	
	//定义存放marker的数组  用于点击开始的时候绑定事件 否则一载入就绑定 了
	var markers=[];
	//存放点的id 经度纬度组合作为key值
	var markersIdMap={};
	var markersId=[];
	var selectedPointsCouple=[];//存放连接的点对  用于计算巡线段长度
	var newSelectedPointsCouple=[];//处理重复问题  ab ba ac ca
	var strokeColor ="blue";//默认颜色
	
	//开始控件
	function startControl(){
		this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;  
	    this.defaultOffset = new BMap.Size(20, 20);
	}
	startControl.prototype = new BMap.Control();
	startControl.prototype.initialize = function(map){
		var div = document.createElement("div");
		var img = document.createElement("image");
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
			points.length = 0;
			//map.disableDragging();
			//map.disableScrollWheelZoom();
			map.disableDoubleClickZoom();
			ifhuitu = 0;
			//var polyline = new BMap.Polyline(points, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
			polylinesss = new BMap.Polyline(points, {
		    strokeColor : "blue",//"#9D3071"
		    strokeWeight : 6,
		    strokeOpacity : 0.6
	        });
			map.addOverlay(polylinesss);
			//map.addEventListener("click", test);
			
			
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
			
		   //给每个巡线点绑定事件
		    for(var i in markers){
		    	var marker=markers[i];
		    	//alert(JSON.stringify(marker));
		    	
		    	
		    	marker.addEventListener("click", function(e){
		    		//alert(this.getPosition().lng);
			    	//alert(this.lng);
		    		test(e,this.getPosition().lng,this.getPosition().lat);
		    	});
		    }
					
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
		var img = document.createElement("image");
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
			
			//查询巡检人
			var inspactPerson="";
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "lineInfoController/getLocalInspactPerson.do",
				  //data:{consComId:consComId},
				  dataType:"json",
				  success:function(data){
					  	
					  $.each(data.localInspactPerson,function(i,item){
						  inspactPerson=inspactPerson+"<option value='"+item.STAFF_ID+"'>"+item.STAFF_NAME+"</option>";
					  });
					  
				  }
			  });
			
			var relay_id=$("#relay_id").val();
			//var line_name=$("#line_id option:selected").text().trim()+"/"+$("#relay_id option:selected").text().trim()+"/";
			
			
			//计算巡线段长度
			
			//去掉重复
			
			var len=selectedPointsCouple.length;
			//alert(JSON.stringify(selectedPointsCouple));
			for(var i=0;i<len;i=i+4){
				var j=i+2;
				var k=i+3;
				if(j<len&&k<len){
					var pointi=selectedPointsCouple[i];
					var pointi1=selectedPointsCouple[i+1];
					var pointi2=selectedPointsCouple[i+2];
					var pointi3=selectedPointsCouple[i+3];
					
					if(pointi.equals(pointi3)&&pointi1.equals(pointi2)){
						newSelectedPointsCouple.push(selectedPointsCouple[i]);
						newSelectedPointsCouple.push(selectedPointsCouple[i+1]);
					}else{
						newSelectedPointsCouple.push(pointi);
						newSelectedPointsCouple.push(pointi1);
						newSelectedPointsCouple.push(pointi2);
						newSelectedPointsCouple.push(pointi3);
						
					}
				}else{
					newSelectedPointsCouple.push(selectedPointsCouple[i]);
					newSelectedPointsCouple.push(selectedPointsCouple[i+1]);
				}
			}
			
			//alert(JSON.stringify(newSelectedPointsCouple));
			
			var count=newSelectedPointsCouple.length-2;
			var distance=0;
			for(var i=0;i<newSelectedPointsCouple.length;i=i+2){
				var j=i+1;
				var xa=newSelectedPointsCouple[i].lng;var ya=newSelectedPointsCouple[i].lat;
				var xb=newSelectedPointsCouple[j].lng;var yb=newSelectedPointsCouple[j].lat;
				var pointA = new BMap.Point(xa,ya); 
				var pointB = new BMap.Point(xb,yb); 
				
				//alert(map.getDistance(pointA,pointB));
				distance=distance+map.getDistance(pointA,pointB);
				
			}
			
			distance=distance+"";
			distance=distance.substring(0,distance.indexOf('.')+3);
		
			
			var showline = "<table class='xiazy' id='tb"+cableJid+"' ><th>巡线段"
					+ cableJid
					+ "<input type='hidden' name='linepoint' value='"+linepoint+"'/><input type='hidden' name='relay_id' value='"+relay_id+"'/><input type='hidden' name='markersId' value='"+markersId.join(',')+"'/></th><th><div class='btn-operation' id='"
					+ cableJid
					+ "' onClick='deleteLine(this);'>删除</div></th>"
					+ "<tr><td width='30%'>编号：</td><td width='60%'><div class='condition-text-container'><input class='condition-text' type='text' name='line_no' id='line_no'/></div></td></tr>"
					+ "<tr><td width='30%'>名称：</td><td width='60%'><div class='condition-text-container'><input class='condition-text' name='line_name' id='line_name' type='text' value='"+line_name+"'/></div></td></tr>"
					+ "<tr><td width='30%'>长度：</td><td width='60%'><div class='condition-text-container'><input class='condition-text' name='distance' id='distance' type='text' value='"+distance+"' readonly='readonly'/></div></td></tr>"
					
					+"<tr><td width='30%'>巡线人：</td><td width='60%'><select class='condition-select' name='inspect_id' id='inspect_id'>"
					+ inspactPerson+"</select></td></tr></table>";
			$("#ff").append(showline);
			
			
			//清空存储的id
			markersId=[];
			
			selectedPointsCouple=[];
            newSelectedPointsCouple=[];
            
			for(var i in markers){
				var maker=markers[i];
				maker.removeEventListener("click");
			}
			
			
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
		var img = document.createElement("image");
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
	//map.addControl(new startControl());
	//map.addControl(new completeControl());
	//map.addControl(new searchControl());
	
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

	
	
	function hideQueryPoint() {
		$("#showPointquery").hide();
	}
	function queryPoint() {
		$.ajax({
			url : "getPoints.do",
			//contentType:"application/json",
			type : "POST",
			dataType : "json",
			data : {
				roadName : $("input[name='road_name']").val().trim(),
				eqpName : $("input[name='eqp_name']").val().trim(),
				eqpType : $("select[name='eqp_type']").val().trim()
			},
			success : function(datas) {
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

						var info = "<div class='map_bubble_info'>编码：" + POINT_NO
						//+ "<br/>经度：" + LONGITUDE
						// + "<br/>纬度：" + LATITUDE
						//+ "<br/>dizhi：" + LATITUDE
						+ "<br/>创建时间：" + CREATE_TIME + "<br/>创建者：" + STAFF_NAME
								+ "</div>";
						var point = new BMap.Point(LONGITUDE, LATITUDE);
						vi[vi.length] = point;
						var marker = new BMap.Marker(point);
						var label = new BMap.Label(POINT_NAME, {position:point,offset:new BMap.Size(0, 20)});
						label.setStyle({
							border : "0",
							color : "#FF1111",
							offset : new BMap.Size(0, -20),
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
							offset : new BMap.Size(0, -20),
							enableMessage : false
						//设置允许信息窗发送短息
						};
						var infoWindow = new BMap.InfoWindow(info, opts); // 创建信息窗口对象

						marker.addEventListener("click", function() {
							if (ifhuitu == 0) {
								if(points.length > 0){
									if(points[points.length - 1].lng == point.lng 
											&& points[points.length - 1].lat == point.lat){
										return;
									}
								}
								if(points.length > 0){
									marker.setIcon(checkedIcon1);
								} else {
									marker.setIcon(icon1);
								}
								points[points.length] = point;
								polylinesss.setPath(points);
								resume = 0;
							}
						});
						marker.addEventListener("mouseover", function(e) {
							map.openInfoWindow(infoWindow, point);
						});
						marker.addEventListener("mouseout", function() {
							map.closeInfoWindow();
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
	         
		var jsonstring="[";
			var inspect_id = $("#inspect_id").val();
			var relay_id = $("#relay_id").val();
			var markersIds = markersId.join(",");
			var distance = $("#distance").val();
			//截取米
			distance=distance.substring(0,distance.length-2);
			var line_color = $("#line_color").val().trim();
			var line_name = $("#line_name").val().trim();
			
			if(inspect_id==''){
				alert("请选择巡线人！");
				return false;
			}
			if(markersIds==''){
				alert("请选择巡线点！");
				return false;
			}
			
			if(line_name==''){
				alert("请填写巡线段名称！");
				return false;
			}
			// var line_name_4_show=$("#line_id option:selected").text().trim()+"/"+$("#relay_id option:selected").text().trim()+"/"+line_name;
			
			

			 jsonstring += "{\"inspect_id\":\""
					+ inspect_id
					+ "\",\"relay_id\":\""
					+ relay_id
					+ "\",\"distance\":\""
					+ distance
					+ "\",\"markersId\":\""
					+ markersIds
					+ "\",\"line_color\":\""
					+ line_color
				
					+ "\",\"line_name\":\""
					+ line_name+ "\"}]";
	
		$.messager.confirm('系统提示', '您确定要保存巡线段?',
				function(r) {
					if (r) {

						
						//jsonstring += "]";
						//var obj =   eval('(' + jsonstring + ')');
						$.ajax({
							type : 'POST',
							url : "lineInfoSave.do",
							data : {
								cableObj : jsonstring
							},
							dataType : 'json',
							success : function(json) {
								if (json.ifsuccess) {
									$.messager.show({
										title : '提  示',
										msg : '保存巡线段成功!',
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
	function deleteLine(e) {
		var deleteId = e.id + "";
		map.removeOverlay(overlaysMap.get(deleteId));
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
		
		//
		selectedPointsCouple=[];

		newSelectedPointsCouple=[];
		markersId=[];
	}
	
	
	function getRelayByGl(gldId){
		$.ajax({          async:false,
			  type:"post",
			  url :webPath + "lineInfoController/getRelayByGl.do",
			  data:{gldId:gldId},
			  dataType:"json",
			  success:function(data){
				  $("#relay_id").empty();
				  $("#relay_id").append("<option value=''>--请选择--</option>");		
				  $.each(data.relayList,function(i,item){
					  $("#relay_id").append("<option value='"+item.RELAY_ID+"'>"+item.RELAY_NAME+"</option>");		
				  });
			  }
		  });
	}
	
	
	function getSiteByRelayId(relay_id){
		$.ajax({          async:false,
			  type:"post",
			  url :webPath + "lineInfoController/getSiteByRelayId.do",
			  data:{relay_id:relay_id},
			  dataType:"json",
			  success:function(data){
				//清除
				  clearAll();
				  points=[];
				  markersId=[];
				  polylinesss.setPath(points);
				  
				  // map.clearOverlays(); 该方法执行后 折线无法连接
				  
				  if(data.siteList.length==0){
					  return false;
				  }
				  
				  $.each(data.siteList, function(i, item) {
						showPoint(item);
						
						//巡线点列表
						//$("#tbl2").append("<tr><td><input type='checkbox' value='"+item.SITE_ID+":"+item.LONGITUDE+":"+item.LATITUDE+"' onmouseover='showTheCurrentSiteOnMap(this)' onchange='reactOnMap(this)' onmouseout='backToOldIcon(this)'/></td><td>"+item.ADDRESS+"</td></tr>");
					});
				  //设置地图中心
				 var point=new BMap.Point(data.siteList[0].LONGITUDE,data.siteList[0].LATITUDE);
				 map.centerAndZoom(point, 17);
				
				  
			  }
		  });
	}
	
	
	
	function showPoint(data) {
		//                
		
		var SITE_ID = data["SITE_ID"];

		var SITE_NAME = data["SITE_NAME"];
		var LONGITUDE = data["LONGITUDE"]; 
		var LATITUDE = data["LATITUDE"];
		var ADDRESS = data["ADDRESS"];
		var UPDATE_TIME = data["UPDATE_TIME"];
		var SITE_TYPE = data["SITE_TYPE"];
		var MAINTAIN_RANK = data["MAINTAIN_RANK"];

		if (typeof (ADDRESS) == 'undefined') {
			ADDRESS = "";
		}
		if (typeof (CREATE_TIME) == 'undefined') {
			CREATE_TIME = "";
		}

		var info = "<div class='map_bubble_info'>名称：" + SITE_NAME
				+ "<br/>地址：" + ADDRESS + "<br/>创建时间：" + UPDATE_TIME+ "</div>";
				
			
				
		var point = new BMap.Point(LONGITUDE, LATITUDE);

		var icon ;
		if(SITE_TYPE==1){
			icon = icon1;
		}else{
			icon = icon2;
			//icon = icon1;
		}

		var marker = new BMap.Marker(point, {
			icon : icon
		});
		//iconMap.put(POINT_ID, icon);//缓存图标
		title = "<div class='map_bubble_title'>" + SITE_NAME + "</div>";
		var label = new BMap.Label(SITE_NAME+ "<br>编号："+SITE_ID, {
			position : point,
			offset : new BMap.Size(0, 40)
		});
		label.setStyle({
			border : "0",
			color : "#FF1111",
			backgroundColor : "0.00"
		});
		marker.setLabel(label);
		map.addOverlay(marker);

		var opts = {
			width : 250, // 信息窗口宽度
			height : 0, // 信息窗口高度
			title : title, // 信息窗口标题
			offset : new BMap.Size(0, -40),
			enableMessage : false
		//设置允许信息窗发送短息
		};
		var infoWindow = new BMap.InfoWindow(info, opts); // 创建信息窗口对象

		marker.addEventListener("mouseover", function() {
			map.openInfoWindow(infoWindow, point);
		});
		marker.addEventListener("mouseout", function() {
			map.closeInfoWindow();
		});
		
		marker.addEventListener("click", function(e){
    		
    		test(e,this.getPosition().lng,this.getPosition().lat,SITE_TYPE);
    	});
		
		marker.addEventListener("rightclick", function(e){
    		
    		delMarker(e,this.getPosition().lng,this.getPosition().lat,SITE_TYPE);
    	});
		
		//创建右键菜单
		/*
		var removeMarker = function(e,ee,marker){
		map.removeOverlay(marker);
	}
	var markerMenu=new BMap.ContextMenu();
	markerMenu.addItem(new BMap.MenuItem('删除',function(e){
		
	}));
	marker.addContextMenu(markerMenu);
		*/
	    ////右键//////
	    
		markers.push(marker);
		//markerMap.put(POINT_ID, marker);
		var key=LONGITUDE+""+LATITUDE;
		markersIdMap[key]=SITE_ID;  
		
		overlays.push(marker);
		
		//alert(marker.getPosition().lng);
	}
	
	
function test(e,lng,lat,site_type) {
		
	
		var key=lng+""+lat;
		//不存在加入 存在不加
		markersId.push(markersIdMap[key]);
		
		var point = new BMap.Point(lng,lat);
		
		points[points.length] = point;
		
		var mkicon;
		if(site_type==1){
			mkicon=checkedIcon1;
		}else{
			mkicon=checkedIcon2;
		}
		var  overMarker=new BMap.Marker(point, {icon:mkicon});
		
		overMarker.addEventListener("rightclick", function(e){
    		
    		delMarker(e,lng,lat,site_type);
    	});
    	
		map.addOverlay(overMarker);
		overlays.push(overMarker);
		
		polylinesss.setPath(points);
		
		//var distance = BMapLib.GeoUtils.getPolylineDistance(polylinesss).toFixed(2)+"公里";
		
		var distance = BMapLib.GeoUtils.getPolylineDistance(polylinesss)/1000;
		distance =distance.toFixed(3)+"公里";
	    $("#distance").val(distance);
		
	  
	}
	
function delMarker(e,lng,lat,site_type) {
	
	var key=lng+""+lat;
	var siteId=markersIdMap[key];
	
	var flag=false;//true 已经加入到巡线段
	
	if(markersId.length>0){
		for(var i in markersId){
			if(markersId[i]==siteId){
				flag=true;
				break;
			}
		}
	}else{
		return false;
	}
	
	
	
	if(!flag){//未加入到巡线段中
		
		//
		$("#siteId").val(siteId);
		$("#lng").val(lng);
		$("#lat").val(lat);
		$("#site_type").val(site_type);
	
	
		//查询已经选中的点
		var markersIds=markersId.join(",")+"";
		 $('#win').window({
				title : "选择巡线点",
				href : webPath + "lineInfoController/getSitesByIdsUI.do?markersIds="+markersIds+"&lng="+lng+"&lat="+lat,
				width : 520,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
	}else{
		$.messager.confirm('系统提示', '您确定要移除该巡线点吗?',function(r) {
			if (r) {
				var key=lng+""+lat;
				var siteId=markersIdMap[key];
				var point = new BMap.Point(lng,lat);
				for(var i in points){
					if(point.equals(points[i])){
						points.splice(i,1);
						break;
					}
				}
				
				for(var i in markersId){
					if(markersId[i]==siteId){
						markersId.splice(i,1);
						break;
					}
				}
				
				//改变图标==========
				var mkicon;
				if(site_type==1){
					mkicon=icon1;
				}else{
					mkicon=icon2;
				}
				var  overMarker=new BMap.Marker(point, {icon:mkicon});
				
				overMarker.addEventListener("rightclick", function(e){
		    		
		    		delMarker(e,lng,lat,site_type);
		    	});
				
				overMarker.addEventListener("click", function(e){
		    		
		    		test(e,lng,lat,site_type);
		    	});
				
				map.addOverlay(overMarker);
				//============
				
				polylinesss.setPath(points);
				//var distance = BMapLib.GeoUtils.getPolylineDistance(polylinesss).toFixed(2)+"公里";
			   var distance = BMapLib.GeoUtils.getPolylineDistance(polylinesss)/1000;
				distance =distance.toFixed(3)+"公里";
				$("#distance").val(distance);
			}
		});
	}
	}
	
	
	function setStrokeColor(color){
		//var strokeColor="#"+color;
		
		polylinesss.setStrokeColor("#"+color);
	}
	
	$(function(){
		
		polylinesss = new BMap.Polyline(points, {
		    strokeColor : "blue",//"#9D3071"
		    strokeWeight : 6,
		    strokeOpacity : 0.6
	        });
		
		map.addOverlay(polylinesss);
	});
	
function reactOnMap(thiss) {
	
	var res=thiss.value;
	var siteInfo=res.split(":");
	var siteId=siteInfo[0];
	var lng=siteInfo[1];
	var lat=siteInfo[2];
	
	var point = new BMap.Point(lng,lat);
	
	if(thiss.checked){
		
		points.push(point);
		markersId.push(siteId);
		
	}else{
		for(var i in points){
			if(point.equals(points[i])){
				points.splice(i,1);
				break;
			}
		}
		
		for(var i in markersId){
			if(markersId[i]==siteId){
				markersId.splice(i,1);
				break;
			}
		}
		
	}
	polylinesss.setPath(points);
	
	
	//var distance = BMapLib.GeoUtils.getPolylineDistance(polylinesss).toFixed(2)+"公里";
	var distance = BMapLib.GeoUtils.getPolylineDistance(polylinesss)/1000;
	distance =distance.toFixed(3)+"公里";
    $("#distance").val(distance);
}
	
	
	function showTheCurrentSiteOnMap(thiss){
		var res=thiss.value;
		var siteInfo=res.split(":");
		var siteId=siteInfo[0];
		var lng=siteInfo[1];
		var lat=siteInfo[2];
		var point = new BMap.Point(lng,lat);
		
		
		var marker=new BMap.Marker(point,{icon:checkedIcon1});
		map.addOverlay(marker);
	}
	
	function backToOldIcon(thiss){
		var res=thiss.value;
		var siteInfo=res.split(":");
		var siteId=siteInfo[0];
		var lng=siteInfo[1];
		var lat=siteInfo[2];
		var point = new BMap.Point(lng,lat);
		
		
		var marker=new BMap.Marker(point,{icon:icon1});
		map.addOverlay(marker);
	}
	
	
	function insertSite() {
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
		}else if(count==1||count>2){
			$.messager.show({
				title : '提  示',
				msg : '一次只能选取两个点!',
				showType : 'show',
				timeout:'1000'//ms
			});
			return;
		}else {
			        
					var siteId1=selected[0].SITE_ID;
					var lng1=selected[0].LONGITUDE;
					var lat1=selected[0].LATITUDE;
					
					var siteId2=selected[1].SITE_ID;
					var lng2=selected[1].LONGITUDE;
					var lat2=selected[1].LATITUDE;
					
					//判断点是否连续
					var idx1=-1;var idx2=-1;
					for(var i in markersId){
						if(markersId[i]==siteId1){
							idx1=i;
						}
						if(markersId[i]==siteId2){
							idx2=i;
						}
						if(idx1!=-1&&idx2!=-1){
							break;
						}
					}
					
					if(!(idx1-idx2==1||idx1-idx2==-1)){
						alert("选中的点在巡线段上不连续，请重新选择！");
						return false;
					}
					
					//找到第一个点位置
					var idx;
					for(var j in markersId){
						if(markersId[j]==siteId1||markersId[j]==siteId2){
							idx=j;
							break;
						}
					}
					var site_id=$("#siteId").val();
					var lng=$("#lng").val();
					var lat=$("#lat").val();
					var site_type=$("#site_type").val();
					idx++;
					//alert(idx+1); idx+1 结果不对 ？？？？？
					markersId.splice(idx,0,site_id);
					
					var point=new BMap.Point(lng,lat);
					points.splice(idx,0,point);
					polylinesss.setPath(points);
					
					//改变图标==========
					var mkicon;
					if(site_type==1){
						mkicon=checkedIcon1;
					}else{
						mkicon=checkedIcon2;
					}
					var  overMarker=new BMap.Marker(point, {icon:mkicon});
					
					overMarker.addEventListener("rightclick", function(e){
			    		delMarker(e,lng,lat,site_type);
			    	});
					map.addOverlay(overMarker);
					//============
					
					//计算长度
					var distance = BMapLib.GeoUtils.getPolylineDistance(polylinesss)/1000;
					distance =distance.toFixed(3)+"公里";
				    $("#distance").val(distance);
					
					$("#win").window('close');
					
		}
	}
	
	
</script>
</body>
</html>
