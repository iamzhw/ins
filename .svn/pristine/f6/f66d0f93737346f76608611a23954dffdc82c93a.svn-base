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
								<input name="" id="ebWidth" type="text" class="condition-text" value="${ELEBAR_LENGTH}"/>
							</div>
						</td>
					</tr>
						<tr>
						<td>宽度：</td>  
						<td><div class="condition-text-container">
								<input name="" id="ebHeitht" type="text" class="condition-text" value="${ELEBAR_WIDTH}"/>
							</div>
						</td>
					</tr>
					
				</table>
			</form>
		</div>

		<div style="height: 35px; ">
			<div style="margin-left: 10px;margin-top: 10px" class="btn-operation"
				onClick="edit_elebar();">保存</div>
			<div style="margin-left: 10px;margin-top: 10px" class="btn-operation"
				onClick="history.back();">返回</div>
			<div style="margin-left: 10px;margin-top: 10px" class="btn-operation"
				onClick="del_elebar();">删除</div>
		</div>
		
		<div style="border-bottom: 1px solid #d2d2d2; padding:10px;overflow-x: auto; overflow-y: auto;height:300px;" >
			<table id="tbl2"  cellpadding="1" boarder='1' cellspacing="1" width="100%" class="" style="font-size: 11px;table-layout:fixed;">
              

             </table>	
		</div>
		
	</div>
	<div id="win_staff"></div>


	<script type="text/javascript">
		
		// 百度地图API功能
		$("#l-map").css("width", document.body.clientWidth - 290);
		$("#ff").css("max-height", $("#l-map").height() - 50);
		var map = new BMap.Map("l-map");
		//map.centerAndZoom("南京");
		
		
		map.enableScrollWheelZoom(); //启用滚轮放大缩小

		var overlays = [];
		  var p={};//存放矩形的坐标
		
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
		
		
		
		///===============画图工具========================///
		var overlaycomplete = function(e) {
			overlays.push(e.overlay);
	           
//          e.overlay.getPath()[0].lng    e.overlay.getPath()[0].lat
         
	        var p0 = e.overlay.getPath()[0];		//左上
	        var p1 = e.overlay.getPath()[1];		//右上
	        var p2 = e.overlay.getPath()[2];		//右下
	        var p3 = e.overlay.getPath()[3];
		    
	        p.points=e.overlay.getPath()
            //计算长度 宽度
            
            var p0lng=p0.lng;
            var p0lat=p0.lat;
            var p1lng=p1.lng;
            var p1lat=p1.lat;
            var p2lng=p2.lng;
            var p2lat=p2.lat;
           
            var obj={};
            obj.p0lng=p0lng;
            obj.p0lat=p0lat;
            obj.p1lng=p1lng;
            obj.p1lat=p1lat;
            obj.p2lng=p2lng;
            obj.p2lat=p2lat;
            
            var d1,d2;
            $.ajax({          
 			  async:false,
 			  type:"post",
 			  url :webPath + "outsitePlanManage/getElebarlw.do",
 			  data:obj,
 			  dataType:"json",
 			  success:function(data){
 				 d1=data.d1;
 				 d2=data.d2;
 			  }
 		  });
           ebWidth=d1+"米";
           ebHeight=d2+"米";
           $("#ebWidth").val(ebWidth);
           $("#ebHeitht").val(ebHeight);
		};
		
		
		var styleOptions = {
				strokeColor : "red", //边线颜色。
				fillColor : "yellow", //填充颜色。当参数为空时，圆形将没有填充效果。
				strokeWeight : 3, //边线的宽度，以像素为单位。
				strokeOpacity : 0.8, //边线透明度，取值范围0 - 1。
				fillOpacity : 0.6, //填充的透明度，取值范围0 - 1。
				strokeStyle : 'solid', //边线的样式，solid或dashed。
				
			};
		
		//实例化鼠标绘制工具
		var drawingManager = new BMapLib.DrawingManager(map, {
			isOpen : false, //是否开启绘制模式
			enableDrawingTool : true, //是否显示工具栏
			drawingToolOptions : {
				anchor : BMAP_ANCHOR_TOP_RIGHT, //位置
				offset : new BMap.Size(5, 5), //偏离值
				
			},
			//enableCalculate: true, //面积
			circleOptions : styleOptions, //圆的样式
			polylineOptions : styleOptions, //线的样式
			polygonOptions : styleOptions, //多边形的样式
			rectangleOptions : styleOptions,//矩形的样式
			
		});
		
		
		
		//添加鼠标绘制工具监听事件，用于获取绘制结果
		drawingManager.addEventListener('overlaycomplete', overlaycomplete);
		function clearAll() {
			for ( var i = 0; i < overlays.length; i++) {
				map.removeOverlay(overlays[i]);
			}
			overlays.length = 0
		}
		
		
		
		
		$(function() {
			



			//显示电子围栏
	  var array=new Array();
	 
      elebar=JSON.parse('${elebar}');
	  $.each(elebar.ebar,function(i,item){
		  var point1=new BMap.Point(item.X,item.Y);
		  array.push(point1);
	  });
	  var  polygon=new BMap.Polygon(array);
	  polygon.setStrokeColor("red");
	  polygon.setFillColor("yellow");
	  overlays.push(polygon);
	 map.addOverlay(polygon);
	
	
	  //移除多余工具
	  $(".BMapLib_marker").remove();
	  $(".BMapLib_circle").remove();
	  $(".BMapLib_polyline").remove();
	  $(".BMapLib_polygon").remove();
	  
	  //显示外力点
		var SITE_NAME ='${SITE_NAME}';
		var LONGITUDE = ${X};
		var LATITUDE =${Y};
		

		var info = "<div class='map_bubble_info'>名称：" + SITE_NAME
				+  "</div>";

		 var point = new BMap.Point(LONGITUDE, LATITUDE);

		var icon = new BMap.Icon(webPath + "images/icon_fire.png",
				new BMap.Size(28, 40), {
					anchor : new BMap.Size(14, 40),
					infoWindowAnchor : new BMap.Size(14, 0)
				});

		var marker = new BMap.Marker(point);
		
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
		map.centerAndZoom(point,19);
		

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
		
		
		function edit_elebar(){
			
			//ebWidth 为空就可判断还没有添加围栏
			if($("#ebWidth").val()==''){
				alert("您还没有没有添加电子围栏！请添加之后再保存");
				return false;
			}
			
			var ebWidth=$("#ebHeitht").val();
			var ebLength=$("#ebWidth").val();
			
			
			var allowWidth=500;
			if(!${isAdmin}){
				if(parseFloat(ebWidth)>allowWidth){
					alert("电子围栏宽度超过限定的距离："+allowWidth);
					return false;
				}
				if(parseFloat(ebLength)>allowWidth){
					alert("电子围栏长度超过限定的距离："+allowWidth);
					return false;
				}
			}
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "mainOutSiteController/updateElebar.do",
				  data:{res:JSON.stringify(p),plan_id:${plan_id},out_site_id:${out_site_id},ebWidth:ebWidth,ebLength:ebLength},
				  dataType:"json",
				  success:function(data){
					  if(data.status){
						  $.messager.show({
								title : '提  示',
								msg : '操作成功!',
								showType : 'show',
								timeout : '1000'//ms

							});
					  }
				  }
			  });
			
			
		}
		
		function del_elebar(){
			
			clearAll();
			p={};
			 $("#ebWidth").val('');
	         $("#ebHeitht").val('');
			 
		}
	</script>
	

</body>

</html>
