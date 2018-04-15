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
<title>新增</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<div id="r-result">
		<div style="border-bottom: 1px solid #d2d2d2; padding:10px;">
			<form id="ff">
				
				<table id="tbl1">

					<tr>
						<td>开始日期：</td>
						<td><div class="condition-text-container">


								<input class="condition-text easyui-validatebox condition"
									type="text" name="start_time" id="start_time"
									onClick="WdatePicker();" value="${today}"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<tr>
						<td>结束日期：</td>
						<td><div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="end_time" id="end_time" value="${today}"
									onClick="WdatePicker();"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
						<tr>
						<td>签到人：</td>
						<td><select class="condition-select" name="inspact_id"
							id="inspact_id">
								<option value=''>--请选择--</option>

								<c:forEach items="${localInspactPerson}" var="res">
									<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
								</c:forEach>
						</select>
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
			<table id="tbl2"  cellpadding="1" boarder='1' cellspacing="1" width="100%" class="" style="font-size: 10px;table-layout:fixed;">
              

             </table>	
		</div>
	</div>
	


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

		

		//var overlays=[];
		function search(userId){
			
			if($("#ff").form('validate')){ 
				clearAll();
				var obj = makeParamJson('ff');
				$.ajax({          
								  async:false,
								  type:"post",
								  url :webPath + "userSignController/getUserSignByUserid.do",
								  data:obj,
								  dataType:"json",
								  success:function(data){
									  $("#tbl2").empty();
									  $("#tbl2").append("<tr><td width='10%'>签到人</td><td width='18%'>签到时间</td><td width='10%'>距离</td></tr>");		
									  $.each(data.userSignList,function(i,item){
										  $("#tbl2").append("<tr><td width='10%'>"+item.STAFF_NAME+"</td><td width='18%'>"+item.SIGN_TIME+"</td><td width='10%'>"+item.SITE_DISTANCE+"</td></tr>");		
									  });
									  
									  //地图上展示签到点
									  var Icon=new BMap.Icon(webPath
												+ "images/icon_sign.png",
												new BMap.Size(30, 30));
									    var centerPoint;
										$.each(data.userSignList, function(i, data) {
											var point = new BMap.Point(data.LONGITUDE,
													data.LATITUDE);
											centerPoint=point;
											var marker2 = new BMap.Marker(point, {
												icon : Icon
											}); // 创建标注
											map.addOverlay(marker2);
											overlays.push(marker2);
											
											
											var info = "<div class='map_bubble_info'>签到时间：" + data.SIGN_TIME
											+ "<br/>到外力施工点距离：" + data.OUT_SITE_DISTANCE + "<br/>备注："
											+ data.REMARK + "</div>";
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
										map.centerAndZoom(centerPoint, 17);
										
								  }
							  });
			}
			
			
			
			
		}
		
		
	
	</script>
</body>
</html>
