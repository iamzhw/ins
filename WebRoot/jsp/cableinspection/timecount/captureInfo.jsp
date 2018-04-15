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
	width: 85%;
	float: left;
}

#r-result {
	height: 100%;
	width: 15%;
	float: right;
}

</style>
		<script type="text/javascript"
			src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
		<title>光缆段查询</title>
	</head>
	<body>
		<div id="l-map"></div>
		<div id="r-result">
		<table>
			<tr>
				<td>
					地市:
				</td>
				<td>
					<select id="c_area" onchange="getSonAreaById()">
					<option value="">
						请选择
					</option>
					<c:forEach items="${areaList}" var="area">
						<option value="${area.AREA_ID}">${area.NAME}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					区域:
				</td>
				<td>
					<select id="c_son_area">
						<option value="">
							请选择
						</option>
						<c:forEach items="${sonAreaList}" var="area">
							<option value="${area.AREA_ID}">${area.NAME}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
				<div style="margin-left: 10px;" class="btn-operation"
				onClick="queryPoints();">查询</div>
				</td>
			</tr>
		</table>
		</div>
		<input id="areaName" type="hidden" value="${AREA_NAME}" />
	</body>
</html>
<script type="text/javascript">
// 百度地图API功能
var areaname=$("#areaName").val().trim();
// 百度地图API功能
var map = new BMap.Map("l-map");
if (areaname != null) {
	map.centerAndZoom(areaname, 14);
} else {
	map.centerAndZoom("南京", 14);
}
/*if(areaname!=null){
map.centerAndZoom(areaname, 15);
}else{
map.centerAndZoom("南京", 15);
}*/
map.enableScrollWheelZoom(); //启用滚轮放大缩小
map.enableDragging(); 
map.setMapStyle({style:'grayscale'});//地图样式
//map.addEventListener("moveend", queryPoints);//地图移动监听            
//map.addEventListener("zoomend", queryPoints);//地图缩放监听
function queryPoints(){
var area_id = $("#c_area").val();
var son_area_id = $("#c_son_area").val();
var cp = map.getBounds(); // 返回map可视区域，以地理坐标表示  
var sw = cp.getSouthWest(); // 返回矩形区域的西南角  
var ne = cp.getNorthEast(); // 返回矩形区域的东北角  
var zoom = map.getZoom();  
var param = {  
		area_id : area_id,
		son_area_id : son_area_id,
        swlng : sw.lng,  
        swlat : sw.lat,  
        nelng : ne.lng,  
        nelat : ne.lat  
 			}; 
var area_name=$("#c_area").find("option:selected").text();
var son_area_name=$("#c_son_area").find("option:selected").text();
$.ajax({
	url:webPath + "PersonalWork/queryAreaPoints.do",
   	contentType:"application/json",
    type:"get",
    dataType:"json",
    data:param,
  	beforeSend:function(){
       	$.messager.progress();
  	},
    success:function(data){
    	$.messager.progress('close'); // 如果提交成功则隐藏进度条
    	map.clearOverlays();        //清除地图覆盖物   
		getBoundary(area_name+son_area_name);
		if (document.createElement('canvas').getContext) {  // 判断当前浏览器是否支持绘制海量点
	        var points = [];  // 添加海量点数据
	        for (var i = 0; i < data.length; i++) {
	          points.push(new BMap.Point(data[i].LONGITUDE, data[i].LATITUDE));
	        }
	        var options = {
	            size: BMAP_POINT_SIZE_SMALL,
	            shape: BMap_Symbol_SHAPE_POINT,
	            color: '#d340c3'
	        }
	        var pointCollection = new BMap.PointCollection(points, options);  // 初始化PointCollection
	         /*pointCollection.addEventListener('click', function (e) {
	         		 // 监听点击事件
	         		$.messager.show({
						title : '提  示',
						msg : '点所属缆线：' + e.LINE_NAME,
						showType : 'show'
					}); 
		        });*/
	        map.addOverlay(pointCollection);  // 添加Overlay
	        map.setViewport(points);    //调整视野   
    	}
    }
});
}

function getBoundary(string){       
		var bdary = new BMap.Boundary();
		bdary.get(string, function(rs){       //获取行政区域
    
			var count = rs.boundaries.length; //行政区域的点有多少个
			if (count === 0) {
				return ;
			}
          	var pointArray = [];
			for (var i = 0; i < count; i++) {
				var ply = new BMap.Polyline(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
			}              
		});   
	}
	function getSonAreaById(){
		var area_id=$("#c_area").val().trim();
		if(area_id!=null&&area_id!=''&&area_id!=undefined){
			$.ajax({
			 url:webPath + "ArrivalRate/getSonAreaById.do?area_id="+area_id,
			 contentType:"application/json",
			 type:"post",
			 dataType:"json",
			 data:{
			 	area_id:area_id
			 },
			 success:function(json){
			  var list = json.sonAreaList;
				 $("#c_son_area option").remove();
				 $("#c_son_area").append("<option value=''>请选择</option>");
				 $.each(list,function(i,e){
				 	$("#c_son_area").append("<option value='"+e.AREA_ID+"'>"+e.NAME+"</option>");
				 });
			 }
			})
		}
	}
</script>
