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
	
	<!--加载鼠标绘制工具-->
<script type="text/javascript"
	src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet"
	href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />


<title></title>


</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<div id="r-result">
		<div style="border-bottom: 1px solid #d2d2d2; padding:0px;">
			<form id="ff">
				
				<table id="tbl1">

						<tr>
						<td>长度：</td>
						<td><div class="condition-text-container">
								<input name="" id="ebWidth" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
						<tr>
						<td>宽度：</td>  
						<td><div class="condition-text-container">
								<input name="" id="ebHeitht" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					
				</table>
			</form>
		</div>

		<div style="height: 35px; ">
			<div style="margin-left: 10px;margin-top: 10px" class="btn-operation"
				onClick="">bu1</div>
			<div style="margin-left: 10px;margin-top: 10px" class="btn-operation"
				onClick="history.back();">返回</div>
			<div style="margin-left: 10px;margin-top: 10px" class="btn-operation"
				onClick="">bu2</div>
		</div>
		
		<div style="border-bottom: 1px solid #d2d2d2; padding:10px;overflow-x: auto; overflow-y: auto;height:300px;" >
			<table id="tbl2"  cellpadding="1" boarder='1' cellspacing="1" width="100%" class="" style="font-size: 11px;table-layout:fixed;">
              

             </table>	
		</div>
		
	</div>
	<div id="win_staff"></div>


	<script type="text/javascript">
		var jb=jQuery.parseJSON('${jbStr}');
		var opts={};
		var infoWindow;
		if(jb.length>0){
			// 百度地图API功能
			var map = new BMap.Map("l-map");
			var point = new BMap.Point(jb[0].LONGITUDE, jb[0].LATITUDE);
			map.centerAndZoom(point, 15);
			opts = {
				width : 200, // 信息窗口宽度
				height : 100, // 信息窗口高度
				title : "设施点信息" // 信息窗口标题
			}
			// 向地图添加标注
			$(jb).each(
					function() {
						var marker = new BMap.Marker(new BMap.Point(
								this.LONGITUDE, this.LATITUDE)); // 创建标注
						var content ="设施名称:"+this.EQUIP_CODE+"</br>"+"设施地址:"+this.EQUIP_ADDRESS; 
						map.addOverlay(marker);     
						addClickHandler(content,marker);
					}
			);
		} else {
			alert("没有相应的设施点存在");
		}
		
		function addClickHandler(content,marker){
			marker.addEventListener("click",function(e){
				openInfo(content,e)}
			);
		}
		function openInfo(content,e){
			var p = e.target;
			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
			var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
			map.openInfoWindow(infoWindow,point); //开启信息窗口
		}
	</script>


</body>

</html>
