<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>新增关键点</title>
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
	width: 300px;
	float: left;
}

#r-result {
	height: 100%;
	width: 280px;
	float: right;
}

.map_bubble_title {
	font-weight: bold;
	font-size: 15px;
	float: left;
	line-height: 25px;
	border-bottom: 1px;
	border-bottom-color: #ccc;
	margin-left: 10px;
	padding-bottom: 8px;
	cursor: pointer;
}

.map_bubble_info {
	line-height: 25px;
}
</style>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" name="area" value="${area}" />
	<div id="l-map"></div>
	<div id="r-result">
		<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
			<div class="btn-operation" onClick="savePoint();">保存</div>
			<div class="btn-operation" onClick="history.back();">返回</div>
		</div>
		<div style="padding: 10px; overflow: auto;">
			<form id="ff">
				<table>
					<tr>
						<td>点编码：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="point_no"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>点名称：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="point_name"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>点类型：</td>
						<td><select name="point_type" class="condition-select"
							onchange="checkType(this)">
								<option value="">请选择</option>
								<c:forEach items="${pointTypeList }" var="type">
									<option value="${type.POINT_TYPE_ID }">${type.POINT_TYPE_NAME
										}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td>设备类型：</td>
						<td><select name="equip_type" class="condition-select" disabled="disabled">
								<option value="">请选择</option>
								<c:forEach items="${equipTypeList }" var="type1">
									<option value="${type1.EQUIPMENT_TYPE_ID }">${type1.EQUIPMENT_TYPE_NAME}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td>维护等级：</td>
						<td><select name="mnt_type" class="condition-select">
							
								<option value="">请选择</option>
								<c:forEach items="${mntLevel}" var="mntLevel">
									<option value="${mntLevel.LEVEL_ID }">${mntLevel.LEVEL_NAME}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td>经度：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="longitude" onblur="showPoint();"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>纬度：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="latitude" onblur="showPoint();"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>地址：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="address"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>所属区县：</td>
						<td><select name="son_area" class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${sonAreaList }" var="area">
									<option value="${area.AREA_ID }">${area.SON_AREA }</option>
								</c:forEach>
						</select></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$("#l-map").css("width", document.body.clientWidth - 290);
		$(window).resize(function() {
			$("#l-map").css("width", document.body.clientWidth - 290);
		});
		var area = $("input[name='area']").val();
		
		var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png", 
				new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
		
		//初始化地图
		var map = new BMap.Map("l-map");
		var markerTemp;
		map.centerAndZoom(area, 13);
		map.enableScrollWheelZoom(); //启用滚轮放大缩小
		map.addEventListener("click", function(e) {
			$("input[name='longitude']").val(e.point.lng);
			$("input[name='latitude']").val(e.point.lat);
			map.removeOverlay(markerTemp);
			var point = new BMap.Point(e.point.lng, e.point.lat);
			markerTemp = new BMap.Marker(point, {icon : fireIcon});
			map.addOverlay(markerTemp);
		});

		$(function() {
			//加载该用户所在区县的所有点
			$.ajax({
				type : 'GET',
				url : webPath + "Lxxj/point/manage/queryExistsPoint.do",
				dataType : 'json',
				success : function(datas) {
					$.each(datas, function(i, data) {
						var POINT_NO = data["POINT_NO"];
						var POINT_NAME = data["POINT_NAME"];
						var POINT_TYPE = data["POINT_TYPE"];
						var LONGITUDE = data["LONGITUDE"];
						var LATITUDE = data["LATITUDE"];
						var CREATE_TIME = data["CREATE_TIME"];
						var ADDRESS = data["ADDRESS"];

						if (typeof (ADDRESS) == 'undefined') {
							ADDRESS = "";
						}
						if (typeof (CREATE_TIME) == 'undefined') {
							CREATE_TIME = "";
						}

						var info = "<div class='map_bubble_info'>编码："
								+ POINT_NO + "<br/>名称：" + POINT_NAME
								+ "<br/>地址：" + ADDRESS + "<br/>创建时间："
								+ CREATE_TIME + "<br/></div>";
						var point = new BMap.Point(LONGITUDE, LATITUDE);
						var marker = new BMap.Marker(point, {
							icon : fireIcon
						});
						var title = "<div class='map_bubble_title'>"
								+ POINT_NAME + "</div>";
						var label = new BMap.Label(POINT_NAME, {
							position : point,
							offset : new BMap.Size(0, 40)
						});
						label.setStyle({
							border : "0",
							color : "#FF1111",
							offset : new BMap.Size(0, -40),
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
					});
				}
			});
		});

		function checkType(obj) {
			var self = $(obj);
			if (self.val() == 4) {
				$("select[name='equip_type']").removeAttr("disabled");
			} else {
				$("select[name='equip_type']").val("");
				$("select[name='equip_type']").attr("disabled", "disabled");
			}
		}

		function showPoint() {
			var longitude = $("input[name='longitude']").val().trim();
			var latitude = $("input[name='latitude']").val().trim();
			if (longitude != '' && latitude != '') {
				map.removeOverlay(markerTemp);
				var point1 = new BMap.Point(longitude, latitude);
				markerTemp = new BMap.Marker(point1, {icon : fireIcon});
				map.addOverlay(markerTemp);
			}
		}

		function savePoint() {
			var point_type = $("select[name='point_type']").val();
			if (point_type == '') {
				$.messager.show({
					title : '提  示',
					msg : '请选择点类型!',
					showType : 'show'
				});
				return;
			}
			var son_area = $("select[name='son_area']").val();
			if (son_area == '') {
				$.messager.show({
					title : '提  示',
					msg : '请选择所属区县!',
					showType : 'show'
				});
				return;
			}
			var mnt_type = $("select[name='mnt_type']").val();
			if (mnt_type == '') {
				$.messager.show({
					title : '提  示',
					msg : '请选择维护等级!',
					showType : 'show'
				});
				return;
			}
			if ($("#ff").form('validate')) {
				$.messager
						.confirm(
								'系统提示',
								'您确定要保存该点吗?',
								function(r) {
									if (r) {
										var point_no = $(
												"input[name='point_no']").val()
												.trim();
										var point_name = $(
												"input[name='point_name']")
												.val().trim();
										var equip_type = $(
												"select[name='equip_type']")
												.val().trim();
										var mnt_type = $("select[name='mnt_type']").val().trim();
										var longitude = $(
												"input[name='longitude']")
												.val().trim();
										var latitude = $(
												"input[name='latitude']").val()
												.trim();
										var address = $("input[name='address']")
												.val().trim();
										var point_level = $("select[name='point_level']")
										.val();
										$
												.ajax({
													type : 'POST',
													url : webPath
															+ "Lxxj/point/manage/save.do",
													data : {
														point_type : point_type,
														mnt_type:mnt_type,
														son_area : son_area,
														point_no : point_no,
														point_name : point_name,
														equip_type : equip_type,
														longitude : longitude,
														latitude : latitude,
														address : address,
														point_level : point_level
													},
													dataType : 'json',
													success : function(json) {
														if (json.status) {
															$.messager
																	.alert(
																			"操作提示",
																			"新增关键点成功！",
																			"info",
																			function() {
																				location.href = webPath
																						+ "Lxxj/point/manage/index.do";
																			});
														} else {
															$.messager
																	.show({
																		title : '提  示',
																		msg : json.message,
																		showType : 'show'
																	});
														}
													}
												});
									}
								});
			}
		}
	</script>
</body>
</html>