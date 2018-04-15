<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../util/head.jsp"%>

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
<title>轨迹查询</title>
</head>
<body style="padding: 3px; border: 0px">

	<div id="l-map"></div>
	<div id="r-result">

		<!--<div style="margin-left: 10px;" class="btn-operation"
					onClick="history.back();">
					返回
				</div>
			-->
		<div style="border-bottom: 2px; padding-left: 10px;">
			<form id="ff">
				<table>
					<tr>
						<td>员工ID：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="vstaff_id" readonly="readonly" />
							</div>
						</td>
					</tr>
					<tr>
						<td>员工名称：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="vstaff_name" id="vstaff_name"
									readonly="readonly"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>

					<tr>
						<td>查询日期：</td>
						<td>
							<!--  <div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="query_time" id="query_time" required="true"
									onClick="WdatePicker();" />
							</div>
							-->
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="begin_time" id="begin_time" required="true"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'});" />
							</div>
							<div>--</div>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="end_time" id="end_time" required="true"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td>播放速度：</td>
						<td>
							<select name="speed" class="condition-select">
								<option value="1">1倍速</option>
								<option value="2">2倍速</option>
								<option value="3">3倍速</option>
								<option value="4">4倍速</option>
								<option value="5">5倍速</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center"><div
								style="margin-left: 80px;" class="btn-operation"
								onClick="queryTrail();">查询</div></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><div
								style="margin-left: 80px;" class="btn-operation"
								onClick="startTrail();">轨迹播放</div></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="win_staff"></div>
	<input id="areaName" type="hidden" value="${AREA_NAME}" />
	<script type="text/javascript"><!--
		// 百度地图API功能
		var areaname = $("#areaName").val().trim();
		// 百度地图API功能
		var map = new BMap.Map("l-map");
		if (areaname != null) {
			map.centerAndZoom(areaname, 15);
		} else {
			map.centerAndZoom("南京", 15);
		}
		var linePoints = new Array();
		map.enableScrollWheelZoom(); //启用滚轮放大缩小
		$("#l-map").css("width", document.body.clientWidth - 290);
		$(window).resize(function() {
			$("#l-map").css("width", document.body.clientWidth - 290);
		});
		$("#vstaff_name").focus(createTask);
		var overlays = [];
		function clearAll(overlay) {
			for ( var i = 0; i < overlays.length; i++) {
				map.removeOverlay(overlays[i]);
			}
			overlays.length = 0;
		};
		function startTrail() {
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
			var speed = 1000 / parseInt($("select[name='speed']").val());
			map.addOverlay(carMk);
			var paths = linePoints.length;
			i = 0;
			function resetMkPoint(i) {

				carMk.setPosition(linePoints[i]);
				if (i < paths) {
					setTimeout(function() {
						i++;
						resetMkPoint(i);
					}, speed);
				}
			}
			setTimeout(function() {
				resetMkPoint(1);
			}, speed);
			overlays.push(carMk);
		}
		function createTask() {
			$('#win_staff').window({
				title : "【选择人员】",
				href : webPath + "Trail/query_staff.do",
				width : 520,
				height : 520,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function queryTrail() {
			var staff_id = $("input[name='vstaff_id']").val();
			//var query_time = $("input[name='query_time']").val();
			var begin_time = $("input[name='begin_time']").val();
			var end_time = $("input[name='end_time']").val();
			if(begin_time==null||end_time==null){	
			$.messager.show( {
						title : '提  示',
						msg : '请选择查询时间!',
						showType : 'show'
					});
		}
		if ($("#ff").form('validate')) {
			//清空上次查询的轨迹点
			linePoints.length = 0;
			map.clearOverlays();
			$
					.ajax( {
						type : 'POST',
						url : webPath + "Trail/query_trail.do",
						data : {
							staff_id : staff_id,
							//query_time : query_time
							begin_time : begin_time,
							end_time : end_time
						},
						dataType : 'json',
						success : function(json) {
							if (json.ifsuccess) {
								var list = eval(json.list);
								var Icon;
								if (list.length > 0) {
									$.each(
													list,
													function(i, data) {
														if (data.LONGITUDE != '4.9E-324') {
															var point = new BMap.Point(
																	data.LONGITUDE,
																	data.LATITUDE);
															if (i == 0) {
																Icon = new BMap.Icon(
																		webPath
																				+ "images/_0000_2.png",
																		new BMap.Size(
																				24,
																				34));
															} else if (i == list.length - 1) {
																Icon = new BMap.Icon(
																		webPath
																				+ "images/_0001_1-2.png",
																		new BMap.Size(
																				24,
																				34));
															} else {
																Icon = new BMap.Icon(
																		webPath
																				+ "images/_0003_-.png",
																		new BMap.Size(
																				12,
																				12));
															}
															var marker2 = new BMap.Marker(
																	point,
																	{
																		icon : Icon
																	}); // 创建标注
															var timestamp3 = data.UPLOAD_TIME.time;
															var newDate = new Date();
															newDate.setTime(timestamp3);
															var label = new BMap.Label(newDate.toLocaleString(),     //为lable填写内容
														    {offset:new BMap.Size(-30,-30),                  //label的偏移量，为了让label的中心显示在点上
														    position:point});
														    label.setTitle(newDate.toLocaleString());
														    map.addOverlay(label);
															map.addOverlay(marker2);
															linePoints[linePoints.length] = point;
														}
													});
								} else {
									$.messager.show( {
										title : '提  示',
										msg : '没有查询到轨迹!',
										showType : 'show'
									});
								}
								var polyline1 = new BMap.Polyline(linePoints, {
									strokeColor : "red",
									strokeWeight : 5,
									strokeStyle : "dashed"
								});
								map.addOverlay(polyline1);
								map.setViewport(linePoints);
								//查询出对应当天任务的缆线段
								$.ajax( {
											type : 'POST',
											url : webPath
													+ "Trail/query_task.do",
											data : {
												staff_id : staff_id,
												query_time : query_time
											},
											dataType : 'json',
											success : function(datas) {
												$.each(datas,function(i,data) {
													var pointList = data.pointMode;
													var linePoints = new Array();
													$.each(pointList,function(j,points1) {
														linePoints[linePoints.length] = new BMap.Point(points1.latitude,points1.longitude);
														if (data.lineType == 2) {
															var marker = new BMap.Marker(new BMap.Point(points1.latitude,points1.longitude));
															map.addOverlay(marker);
													    }
														//alert(points.longitude+":"+points.latitude);
													});
													map.centerAndZoom(linePoints[0],14);
													var polyline1 = new BMap.Polyline(linePoints,
																			{
																				strokeColor : "red",
																				strokeWeight : 6,
																				strokeOpacity : 0.5
																			});
													if (data.lineType == 2) {
													   var parentId = data.parentId;
														obj = {
																"planId" : planId,
																"parentId" : parentId
															  };
														getPlanCable(obj)
													} else if (data.lineType != 2) {
														map.addOverlay(polyline1);
													}
													var info = "__tag_288$23_缆线ID："
																+ data.lineId
																+ "__tag_289$24_缆线名称："
																+ data.lineName
																+ "__tag_290$24_创建时间："
																+ data.createTime
																+ "__tag_291$24_创建者："
																+ data.createStaff
																+ "__tag_292$24_";
													var title = "__tag_293$28_"
																+ data.lineName
																+ "__tag_293$79_";
													var opts = {
																width : 250, // 信息窗口宽度
																height : 0, // 信息窗口高度
																title : title, // 信息窗口标题
																enableMessage : false
																	//设置允许信息窗发送短息
																};
													var infoWindow = new BMap.InfoWindow(info,opts);
													polyline1.addEventListener(
																			"click",
																			function(e) {
																			var point = new BMap.Point(
																						e.point.lng,
																						e.point.lat);
																			map.openInfoWindow(infoWindow,point);
																	           });
												 });
											}
										});
							} else {
								$.messager.show( {
									title : '提  示',
									msg : '查询出错，请稍后再试!',
									showType : 'show'
								});
							}
						}
					});
				}
		}
--></script>

</body>
</html>