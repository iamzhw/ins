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
	width: 80%;
	float: left;
}

#r-result {
	height: 100%;
	width: 20%;
	float: right;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
<title></title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<div id="r-result">
		<div style="border-bottom: 1px solid #d2d2d2; padding:0px;">
			<form id="ff">
				<input type="hidden" name="inspact_id" id="inspact_id" />
				<table id="tbl1">

					<tr>
						<td>地市：</td>
						<td><div>
								<select name="area_id" id="area_id" class="condition-select" onchange="getSonArea(this.value)">
									
								  <c:forEach items="${areaList}" var="res">
								    <option value='${res.AREA_ID}'>${res.NAME}</option>
								 </c:forEach>
								</select>


							</div>
						</td>
					</tr>
					<tr>
						<td>区县：</td>
						<td><div>
								<select name="son_area_id" id="son_area_id" class="condition-select">
									
								     
								</select>


							</div>
						</td>
					</tr>
					<tr>
						<td>状态：</td>
						<td><div>
								<select name="state" id="state" class="condition-select">
									<option value=''>--请选择--</option>
								     
								</select>


							</div>
						</td>
					</tr>
					<tr>
						<td>姓名：</td>
						<td><div class="condition-text-container">
								<input name="inspector_name" id="inspector_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					
				</table>
			</form>
		</div>

		<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="search();">查询</div>
			
			
		</div>
		
		<div style="border-bottom: 1px solid #d2d2d2; padding:10px;overflow-x: auto; overflow-y: auto;height:300px;" >
			<table id="tbl2"  cellpadding="1" boarder='1' cellspacing="1" width="100%" class="" style="font-size: 11px;table-layout:fixed;">
              

             </table>	
		</div>
		
	</div>
	<div id="win_staff"></div>


	<script type="text/javascript">
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
			$('#win_staff')
					.window(
							{
								title : "【选择人员】",
								href : webPath
										+ "autoTrackController/getInspactPersonIndex.do",
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
			

			
				var obj = makeParamJson('ff');
				
				//清空上次查询的轨迹点
				linePoints.length = 0;
				map.clearOverlays();

				var newCenter;
				$.ajax({
					type : 'POST',
					url : webPath + "autoTrackController/getTheRealTimeTrack.do",
					data : obj,
					dataType : 'json',
					success : function(res) {
						
						if (res.status) {
							var trackList = res.trackList;
							var outList =res.outList;
							if(trackList.length==0&&outList.length==0){
								$.messager.show({
									title : '提  示',
									msg : '没有有效数据!',
									showType : 'show'
								});
								return;
							}
							
							
							var Icon=new BMap.Icon(webPath
									+ "images/_0000_2.png",
									new BMap.Size(24, 34));;
							$.each(trackList, function(i, data) {
								var point = new BMap.Point(data.LONGITUDE,
										data.LATITUDE);
								newCenter=point;
								
								var marker2 = new BMap.Marker(point, {
									icon : Icon
								}); // 创建标注
								map.addOverlay(marker2);
								linePoints[linePoints.length] = point;
								
								var info = "<div class='map_bubble_info'>速度："
								+ data.SPEED + "<br/>时间："+ data.TRACK_TIME + "<br/>姓名："+data.STAFF_NAME+"</div>";
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
							
							var Icon1=new BMap.Icon(webPath+ "images/guanjian.png",new BMap.Size(24, 34));;
							$.each(outList, function(i, data) {
								var point = new BMap.Point(data.X,
										data.Y);
								newCenter=point;
								
								var marker3 = new BMap.Marker(point, {
									icon : Icon1
								}); // 创建标注
								map.addOverlay(marker3);
								linePoints[linePoints.length] = point;
								
								var info = "<div class='map_bubble_info'>外力点名称："
								+ data.SITE_NAME + "<br/>施工单位："+ data.CON_COMPANY + "<br/>施工地址："+data.CON_ADDRESS+"</div>";
						       var title = "<div class='map_bubble_title'></div>";
						    	var opts = {
								width : 250, // 信息窗口宽度
								height : 0, // 信息窗口高度
								title : title, // 信息窗口标题
								enableMessage : false
							  };
							  var infoWindow = new BMap.InfoWindow(info, opts);
								marker3.addEventListener("mouseover", function(e) {
									map.openInfoWindow(infoWindow, point);
								});
								marker3.addEventListener("mouseout", function() {
									map.closeInfoWindow();
								});
								
								
								
								
							});
							
							
							
                            map.centerAndZoom(newCenter,13);
							
							 $("#tbl2").empty();
							  $("#tbl2").append("<tr><td width='15%'>工号</td><td width='20%'>姓名</td><td width='20%'>上报时间</td></tr>");		
							  $.each(trackList,function(i,item){
								  $("#tbl2").append("<tr><td width='15%' style='word-break:break-all;'>"+item.STAFF_NO+"</td><td width='20%'>"+item.STAFF_NAME+"</th><td width='20%'>"+item.TRACK_TIME+"</th></tr>");		
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
			var myIcon = new BMap.Icon(webPath + "images/_0004_3.png",
					new BMap.Size(28, 37));
			var carMk = new BMap.Marker(linePoints[0], {
				icon : myIcon
			});
			map.addOverlay(carMk);
			
			var paths = linePoints.length;
			//var i = 0;
			function resetMkPoint(i) {

				carMk.setPosition(linePoints[i]);
				if (i < paths) {
					setTimeout(function() {
						i++;
						resetMkPoint(i);
					}, 1000);
				}
			}
			setTimeout(function() {
				resetMkPoint(1);
			}, 1000);
			overlays.push(carMk);
		}
		
		
		function getSonArea(areaId){
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "areaController/getSonArea.do",
				  data:{areaId:areaId},
				  dataType:"json",
				  success:function(data){
					 
					  $("#son_area_id").empty();
					  $("#son_area_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.sonAreaList,function(i,item){
						  $("#son_area_id").append("<option value='"+item.AREA_ID+"'>"+item.NAME+"</option>");		
					  });
				  }
			  });
		}
		
		$(function(){
			
			var areaId=$("#area_id").val();
			getSonArea(areaId);
		});
		
		function test(){
			// getRepeaters
			// saveInspInfo
			$.post(webPath + "mobile/gxxj/saveOsMaintainScheme.do",{para:"{\"userId\":\"123\",\"sn\":\"123\"}"},function(data){
				
				
			});
		}
	</script>
</body>
</html>
