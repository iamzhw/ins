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
/* 	overflow: hidden; */
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
	float: left;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>

<script type="text/javascript" src="http://api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>

<title>光缆段新增</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map">
	</div>
	<div id="r-result">
		<div style="border-bottom: 1px solid #d2d2d2; padding:10px;">
			<form id="ff">
				<input type="hidden" name="inspact_id" id="inspact_id" />
				<table id="tbl1">

					<tr>
						<td>员工姓名：</td>
						<td><div class="condition-text-container">


								<input class="condition-text easyui-validatebox condition"
									type="text" name="inspact_name" id="inspact_name"
									onClick="getInspactPerson()"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>查询日期：</td>
						<td><div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="inspact_date" id="inspact_date"
									onClick="WdatePicker();"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>轨迹时间段：</td>
						<td><div class="condition-text-container">
								<input type="text" class='condition-text'
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})"
									id="start_time" name="start_time" style="width:60px"> <span>至</span><input
									type="text" class='condition-text'
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})"
									id="end_time" name="end_time" style="width:60px">
							</div>
						</td>
					</tr>
                    <tr>
						<td><input type="checkbox" name="site_type"
							value="1">关键点</td>
							<td><input type="checkbox" name="site_type"
							value="2">非关键点</td>
							
					</tr>

				</table>
			</form>
		</div>

		<div style="height: 40px;">
			<div style="margin-left: 15px;" class="btn-operation" onClick="search();">查询</div>
			<div style="margin-left: 15px;" class="btn-operation"
				onClick="playTrack();">轨迹播放</div>
			<div style="margin-left: 15px;margin-top: 10px" class="btn-operation"
				onClick="playTrackFaser();" id="ptf">快进</div>
			<div style="margin-left: 15px;margin-top: 10px" class="btn-operation"
				onClick="pausuOrPlay();" id="pop">暂停</div>
		</div>
<!-- 		 <div style="border-bottom: 1px solid #d2d2d2;margin-left: 10px;margin-top: 10px; overflow: auto;height: 350px;width:240px;" id="auto_div"></div>  -->
		<div id="dg" style="width:270px;height:400px;"></div>
	</div>
	<div id="win_staff"></div>


	<script type="text/javascript">
		var tipMarker="";
		var areaname = '${staffInfo.AREA_NAME}';
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

		var myDis = new BMapLib.DistanceTool(map);
		
		
		//添加控件
		var cr = new BMap.CopyrightControl({anchor: BMAP_ANCHOR_TOP_RIGHT});   //设置版权控件位置
		map.addControl(cr); //添加版权控件

		//var bs = map.getBounds();   //返回地图可视区域
		cr.addCopyright({id: 1, content: "<div  class='bdmap-tool' onClick='measureDis();'>测    距</div>"});   
		//-------
		function measureDis(){
			myDis.open();  //开启鼠标测距
		}
		
		var overlays = [];
		function clearAll() {
			for ( var i = 0; i < overlays.length; i++) {
				map.removeOverlay(overlays[i]);
			}
			overlays.length = 0;
		};
		
		var checkedIcon = new BMap.Icon(webPath + "images/icon_checked.png",
				new BMap.Size(28, 40), {
					anchor : new BMap.Size(14, 40),
					infoWindowAnchor : new BMap.Size(14, 0)
				});
		var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png",
				new BMap.Size(28, 40), {
					anchor : new BMap.Size(14, 40),
					infoWindowAnchor : new BMap.Size(14, 0)
				});

		var linePoints = new Array();

		var polylinesss;

		$(window).resize(function() {
			$("#l-map").css("width", document.body.clientWidth - 290);
			$("#ff").css("max-height", $("#l-map").height() - 35);
		});

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
					+ "<br/>地址：" + ADDRESS + "<br/>创建时间：" + UPDATE_TIME
					+ "</div>";

			var point = new BMap.Point(LONGITUDE, LATITUDE);

			var icon = new BMap.Icon(webPath + "images/icon_fire.png",
					new BMap.Size(28, 40), {
						anchor : new BMap.Size(14, 40),
						infoWindowAnchor : new BMap.Size(14, 0)
					});

			var marker = new BMap.Marker(point, {
				icon : icon
			});
			//iconMap.put(POINT_ID, icon);//缓存图标
			title = "<div class='map_bubble_title'>" + SITE_NAME + "</div>";
			var label = new BMap.Label(SITE_NAME, {
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

			markers.push(marker);
			//markerMap.put(POINT_ID, marker);
			var key = LONGITUDE + "" + LATITUDE;
			markersIdMap[key] = SITE_ID;

			//alert(marker.getPosition().lng);
		}

		$(function() {

			
		});

		function getInspactPerson() {
			$('#win_staff').window(
			{
				title : "【选择人员】",
				href : webPath + "autoTrackController/getInspactPersonIndex.do",
				width : 580,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}

		function search() {
			if ($("#ff").form('validate')) {
				var site_type = '';
				$("[name=site_type]").each(function() {
					if(this.checked){
						site_type = site_type + $(this).val() + ",";
					}
					
				});
				if (site_type != '') {
					site_type = site_type.substring(0, site_type.length - 1);//??????
				}
				
				var obj = makeParamJson('ff');//??????
				obj.site_type=site_type;//??????
				
				//加载dg数据
				$('#dg').datagrid({
					fitColumns:false,
					autoSize : false,
					queryParams:obj,
					url : webPath + "/autoTrackController/selTrackForDG.do",
					method : 'post',
// 					pagination : false,
// 					pageNumber : 1,
// 					pageSize : 10,
// 					pageList : [ 5, 10, 20, 50 ],
					//loadMsg:'数据加载中.....',
					rownumbers : true,
					singleSelect : true,
					columns : [
						[  
							{field:'TRACK_TIME',title:'轨迹时间',align:'center'},
							{field:'TRACK_TYPE',title:'轨迹类型',align:'center',
								formatter: function(value,row,index){
									if (value == '0'){
										return '自动';
									} else {
										return '手动';
									}
								}
							},
							{field:'LONGITUDE',title:'经度',align:'center',hidden:true},
							{field:'LATITUDE',title:'纬度',align:'center',hidden:true}
						]	
					],
//						onLoadSuccess:function(data){
					
//						}
						
					onClickRow:function(rowIndex, rowData){
						map.removeOverlay(tipMarker);
						var point = new BMap.Point(rowData.LONGITUDE, rowData.LATITUDE);
						map.centerAndZoom(point, 15);
						var marker = new BMap.Marker(point);  // 创建标注
						tipMarker= marker;
						map.addOverlay(marker);               // 将标注添加到地图中
						marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
						
					}	
				});
				
				
				//清空上次查询的轨迹点
				linePoints.length = 0;
				map.clearOverlays();

				$.ajax({
					type : 'POST',
					url : webPath + "autoTrackController/getTheTrack.do",
					data : obj,
					dataType : 'json',
					success : function(res) {
						if (res.status) {
							var trackList = res.trackList;
							
							if(trackList.length==0){
								$.messager.show({
									title : '提  示',
									msg : '未查到数据，请更改查询条件再试!',
									showType : 'show'
								});
								return;
							}
							var Icon;
							$.each(trackList, function(i, data) {
								var point = new BMap.Point(data.LONGITUDE,data.LATITUDE);
// 								$("#auto_div").append("<label onclick=theLocation("+data.LONGITUDE+","+data.LATITUDE+")>" + data.TRACK_TIME + "</label><br/>");
								var trackType;
								var is_gps;
								if (i == 0) {
									Icon = new BMap.Icon(webPath+ "images/kaishi.png",new BMap.Size(25, 30));
								} else if (i == trackList.length - 1) {
									Icon = new BMap.Icon(webPath+ "images/jieshu.png",new BMap.Size(25, 30));
								} else {
									if(data.TRACK_TYPE==0){
										Icon = new BMap.Icon(webPath + "images/_0003_-.png",new BMap.Size(12, 12));
										trackType='自动';
									}else if(data.TRACK_TYPE==1){
										Icon = new BMap.Icon(webPath + "images/_0003_1.png",new BMap.Size(12, 12));
										trackType='手动';
									}else{
										Icon = new BMap.Icon(webPath + "images/_0003_2.png",new BMap.Size(12, 12));
										trackType='签到';
									}if(data.IS_GPS == 1){
										is_gps='gps定位';
									}else if(data.IS_GPS == 161){
										is_gps='混合定位';
									}else{
										is_gps='其他原因造成的无效轨迹点';
									}
								}
								var marker2 = new BMap.Marker(point, {
									icon : Icon
								}); // 创建标注
								map.addOverlay(marker2);
								linePoints[linePoints.length] = point;
								var info = "<div class='map_bubble_info'>速度："
								+ data.SPEED + "<br/>时间："+ data.TRACK_TIME + "<br/>轨迹类型："+ trackType+"<br/>定位类型："+is_gps+"</div>";
						        var title = "<div class='map_bubble_title'></div>";
						    	var opts = {
								width : 250, // 信息窗口宽度
								height : 0, // 信息窗口高度
								title : title, // 信息窗口标题
								enableMessage : false
							  };
								var infoWindow = new BMap.InfoWindow(info, opts);
								marker2.addEventListener("mouseover", function(e) {
									map.openInfoWindow(infoWindow, point);
								});
								marker2.addEventListener("mouseout", function() {
									map.closeInfoWindow();
								});
							});

							var polyline1 = new BMap.Polyline(linePoints, {
								strokeColor : "red",
								strokeWeight : 5,
								strokeStyle : "dashed"
							});
							map.addOverlay(polyline1);
							map.setViewport(linePoints);
							
							
							
							//查询出对应当天任务的巡线段
							$.ajax({
								type : 'POST',
								url : webPath + "autoTrackController/getTheveryDayLineInfo.do",
								data : obj,
								dataType : 'json',
								success : function(res) {
									if(res.status){
										var datas=res.theveryDayLineInfo;
										var lineIds=JSON.stringify(res.lineIds);
										lineIds=lineIds.substring(1,lineIds.length-1);
										var lineIdArray=lineIds.split(",");
										
										var lineColor=res.lineColor;
										
										if(datas.length > 0){
											
											var icon1 = new BMap.Icon(webPath + "images/auto_key.png", 
													new BMap.Size(12, 14), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
											
											var icon2 = new BMap.Icon(webPath + "images/auto_nokey.png", 
													new BMap.Size(12, 14), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});

											$.each(datas, function(i, data) {
												var lineid=lineIdArray[i];
												var pointList = data[lineid];
												var linePoints1 = new Array();
												$.each(pointList, function(j, points1) {
													var point=new BMap.Point(points1.LONGITUDE, points1.LATITUDE);
													linePoints1[linePoints1.length] = point;
													
													var icon ;
													if(points1.SITE_TYPE==1){
														icon = icon1;
													}else{
														icon = icon2;
													}
													var marker = new BMap.Marker(point, {
														icon : icon
													});
													map.addOverlay(marker);
													
													var info = "<div class='map_bubble_info'>点ID:"+ points1.SITE_ID + "<br/>点名称："+points1.SITE_NAME+"</div>";
												       var title = "<div class='map_bubble_title'></div>";
												    	var opts = {
														width : 250, // 信息窗口宽度
														height : 0, // 信息窗口高度
														title : title, // 信息窗口标题
														enableMessage : false
													  };
														var infoWindow = new BMap.InfoWindow(info, opts);
														marker.addEventListener("click", function(e) {
															map.openInfoWindow(infoWindow, point);
														});
														marker.addEventListener("mouseout", function() {
															map.closeInfoWindow();
														});
												});
												var color = "#"+lineColor[lineid];
												var polyline2 = new BMap.Polyline(linePoints1, {
													strokeColor : color,
													strokeWeight : 6,
													strokeOpacity : 0.8
												});
												//map.addOverlay(polyline2);
												
											});
										} 
										
										//外力点
										
										$.each(res.outSiteList,function(i,item){
											var point=new BMap.Point(
													item.X, item.Y);
											var icon =new BMap.Icon(webPath + "images/waili.png", 
													new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
											
											var marker = new BMap.Marker(point, {
												icon : icon
											});

											var info = "<div class='map_bubble_info'>名称：" + item.SITE_NAME
															+ "</div>";

											var opts = {
														width : 250, // 信息窗口宽度
														height : 0, // 信息窗口高度
														title : '外力点', // 信息窗口标题
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
													
											map.addOverlay(marker);
										});
										
										
									}
									
								}
							});

						} else {
							$.messager.show({
								title : '提  示',
								msg : '查询出错，请稍后再试!',
								showType : 'show'
							});
						}
					}
				});
				
				
			}
		}
		
		var t=1000;//间隔时间
		var sto;
		var curp;//记下当前动态位置
		var carMk;
		var myIcon;
		var paths;
		function playTrack() {
			if (linePoints.length == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请先查询出员工运动轨迹再试!',
					showType : 'show'
				});
				return;
			}
			clearAll();
			 myIcon = new BMap.Icon(webPath + "images/_0004_3.png",
					new BMap.Size(28, 37));
			 carMk = new BMap.Marker(linePoints[0], {
				icon : myIcon
			});
			map.addOverlay(carMk);
			
			paths = linePoints.length;
			//var i = 0;
			function resetMkPoint(i) {

				carMk.setPosition(linePoints[i]);
				if (i < paths) {
					sto=setTimeout(function() {
						curp=i++;
						resetMkPoint(i);
					}, t);
				}else{
					t=1000;
					$("#ptf").text("快进");
					$.messager.show({
						title : '提  示',
						msg : '轨迹播放结束!',
						showType : 'show',
						timeout:'1500'//ms
					});
				}
			}
			setTimeout(function() {
				resetMkPoint(1);
			}, 1000);
			overlays.push(carMk);
		}
		
		function theLocation(longitude,latitude){
			if(longitude != "" && longitude != ""){
				var new_point = new BMap.Point(longitude,latitude);
				Icon = new BMap.Icon(webPath + "images/_0003_1.png",new BMap.Size(12, 12));
				var marker2 = new BMap.Marker(new_point, {icon : Icon}); // 创建标注
				map.addOverlay(marker2);
				map.panTo(new_point); 
			}
		}
		
		//快进     1停止函数执行 2记录当前i  3设置时间 从新调用
		//两级快进 分别是500ms 250ms
		var count=0;//表示点击次数
		function playTrackFaser(){
			clearTimeout(sto);
			count++;
			if(count==1){//第一次点击两倍快进 以后都是四倍快进
				t=500;
				$("#ptf").text("快进:1×");
			}else{
				t=250;
				$("#ptf").text("快进:2××");
				
			}
			
			resetMkPoint2(curp);
			
		}
		
		function resetMkPoint2(i) {

			carMk.setPosition(linePoints[i]);
			if (i < paths) {
				sto=setTimeout(function() {
					curp=i++;
					resetMkPoint2(i);
				}, t);
			}else{
				t=1000;//相关值重置为初始值
				count=0;
				$("#ptf").text("快进");
			}
		}
		
		function pausuOrPlay(){
			
			if($("#pop").text()=="暂停"){
				clearTimeout(sto);
				$("#pop").text("重新播放");
			}else{
				resetMkPoint2(curp);
				$("#pop").text("暂停");
			}
			
			
		}
		
	</script>
</body>
</html>
