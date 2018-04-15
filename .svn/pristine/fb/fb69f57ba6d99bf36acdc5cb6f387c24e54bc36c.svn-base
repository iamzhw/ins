<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@include file="/jsp/util/head.jsp"%>
		<link rel="stylesheet" type="text/css"
			href="http://132.230.9.200:8081/GISService/arcgis_js_api_3.2/library/3.2/jsapi/js/dojo/dijit/themes/claro/claro.css">
		<link rel="stylesheet" type="text/css"
			href="http://132.230.9.200:8081/GISService/arcgis_js_api_3.2/library/3.2/jsapi/js/esri/css/esri.css" />
		<script type="text/javascript"
			src="http://132.230.9.200:8081/GISService/arcgis_js_api/library/3.0/jsapi/js/dojo/dojo/dojo.js"></script>
		<script type="text/javascript"
			src="http://132.230.9.200:8081/GISService/arcgis_js_api/library/3.0/jsapi/js/esri/jsapi.js"></script>
		<script type="text/javascript" src="<%=path%>/jsp/util/gis/gis.js"></script>
		<script type="text/javascript">
	dojo.require("dijit.layout.BorderContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("esri.map");
	dojo.require("dojo.fx.easing");

	//find 查询模块
	dojo.require("esri.tasks.find");
	//var res = $("#hidden").val();
	var result = jQuery.parseJSON('${result}');
	//alert(${sameSect});
	var sameSectList = jQuery.parseJSON('${sameSect}');
	var findTask;
	var findParams;
	var map;
	//地图列表
	//苏州市区 21
	//var resUrl = "http://132.228.224.46:9008/arcgis/rest/services/res/wxres/MapServer";
	var resUrl = "${resUrl}";
	//var mapUrl = "http://132.228.224.46:9008/arcgis/rest/services/map/wuxi/MapServer";
	
	var mapUrl ="${mapUrl}";
/* 	var sz_tt = "http://132.228.224.46:9008/arcgis/rest/services/map/sz_tt/MapServer";
	//昆山 74
	var kunshan = "http://132.228.224.46:9008/arcgis/rest/services/map/sz_changshu/MapServer";
	//常熟 76
	var changshu = "http://132.228.224.46:9008/arcgis/rest/services/map/sz_kunshan/MapServer";
	//太仓 75
	var taicang = "http://132.228.224.46:9008/arcgis/rest/services/map/sz_taicang/MapServer";
	//张家港 77
	var zhangjiagang = "http://132.228.224.46:9008/arcgis/rest/services/map/sz_zhangjiagang/MapServer";
	//吴江 23
	var wujiang = "http://132.228.224.46:9008/arcgis/rest/services/res/wjres_emf/MapServer"; */


		var lineSymbolArr= new Array();
var curBusiIndex =0;
	function init() {

		map = new esri.Map("map");
		var imageParameters = new esri.layers.ImageParameters();
		imageParameters.format = "jpeg";
		var tiledMapServiceLayer = new esri.layers.ArcGISTiledMapServiceLayer(
				mapUrl);
		map.addLayer(tiledMapServiceLayer);

		//实例化查找任务
		findTask = new esri.tasks.FindTask(resUrl);
		//查找参数
		findParams = new esri.tasks.FindParameters();
		//返回几何对象
		findParams.returnGeometry = true;
		//查找图层的ID
		findParams.layerIds = [0 ];
		//查找的字段
		findParams.searchFields = [ "RESID" ];
		//是否完全匹配
		findParams.contains = false;
		var glInfoHtml ="";
		for ( var i = 0; i < result.length; i++) {
			
			switch (i) {
			case 0:glInfoHtml += "<div>"+result[i].dlNo+" : 蓝色</div>";break;
			case 1:glInfoHtml += "<div>"+result[i].dlNo+" : 绿色</div>";break;
			case 2:glInfoHtml += "<div>"+result[i].dlNo+" : 墨绿色</div>";break;
			case 3:glInfoHtml += "<div>"+result[i].dlNo+" : 紫色</div>";break;
			case 4:glInfoHtml += "<div>"+result[i].dlNo+" : 黄色</div>";break;
			case 5:glInfoHtml += "<div>"+result[i].dlNo+" : 黑色</div>";break;
			case 6:glInfoHtml += "<div>"+result[i].dlNo+" : 粉红色</div>";break;
			case 7:glInfoHtml += "<div>"+result[i].dlNo+" : 棕色</div>";break;
			default :glInfoHtml += "<div>"+result[i].dlNo+" : 天蓝色</div>";
			}
			 var gl = result[i].dl;
			 //curColor=new Object();
			 //curColor = colorArr[i];
			 
			if(undefined != gl)
			{
					for(var j=0;j<gl.length;j++){
					 	/* 	findParams.layerIds = [ 2 ];
					 		if(undefined != gl[j].A_EQP){
								findParams.searchText = gl[j].A_EQP;
								findTask.execute(findParams, showResultsAndDraw);
							}
							findParams.searchText = gl[j].Z_EQP;
							if(undefined != gl[j].Z_EQP){
								findTask.execute(findParams, showResultsAndDraw);
							} */
							findParams.layerIds = [6];
							
							if(undefined != gl[j].CBL_SECT_ID){
								findParams.searchText =  gl[j].CBL_SECT_ID;
								if (i==0) findTask.execute(findParams, showResultsAndDraw0);
								else if (i==1) findTask.execute(findParams, showResultsAndDraw1);
								else if (i==2) findTask.execute(findParams, showResultsAndDraw2);
								else if (i==3) findTask.execute(findParams, showResultsAndDraw3);
								else if (i==4) findTask.execute(findParams, showResultsAndDraw4);
								else if (i==5) findTask.execute(findParams, showResultsAndDraw5);
								else if (i==6) findTask.execute(findParams, showResultsAndDraw6);
								else if (i==7) findTask.execute(findParams, showResultsAndDraw7);
								else
								findTask.execute(findParams, showResultsAndDraw8);
							}
					}
					
				} 
				/* var his_gl = result[i].his_dl;
				if(undefined != his_gl)
				{
					for(var j=0;j<his_gl.length;j++){
					 		findParams.layerIds = [ 2 ];
					 		if(undefined != his_gl[j].A_EQP){
								findParams.searchText = his_gl[j].A_EQP;
								findTask.execute(findParams, showResultsAndDrawHis);
							}
							findParams.searchText = his_gl[j].Z_EQP;
							if(undefined != his_gl[j].Z_EQP){
								findTask.execute(findParams, showResultsAndDrawHis);
							}
							findParams.layerIds = [ 8 ];
							if(undefined != his_gl[j].CBL_SECT_ID){
								findParams.searchText = his_gl[j].CBL_SECT_ID;
								findTask.execute(findParams, showResultsAndDrawHis)
							}
					} 
				} */
		}
		glInfoHtml += "<div>"+"重复管道段"+" : 红色</div>";
		$('#glList').html(glInfoHtml);
	}
	dojo.addOnLoad(init);

	//画本端口的点
	function showResultsAndDraw(results,index) {
			lineSymbolArr[0]= new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("blue"), 4);
		lineSymbolArr[1]= new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("#80ff00"), 4);
		lineSymbolArr[2] = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("#336600"), 4);
		lineSymbolArr[3] = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("#993399"), 4);
		lineSymbolArr[4] = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("yellow"), 4);
		lineSymbolArr[5] = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("black"),4);
		lineSymbolArr[6] = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("#ff99ff"), 4);
		lineSymbolArr[7] = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("#993333"), 4);
		lineSymbolArr[8] = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("#ccffff"), 4);
		
		//symbology for graphics
		var lineSymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("red"), 6);
		var symbol = new esri.symbol.SimpleMarkerSymbol(
					 esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 
					 10,
				     lineSymbol,
				     new dojo.Color("red"));
		var polygonSymbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_NONE, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255,0,0]), 2), new dojo.Color([255,255,0,0.25]));
		// 清除上一次的高亮显示
		//map.graphics.clear();
		for ( var i = 0; i < results.length; i++) {
			var curFeature = results[i];
			//得到ID
			layerID = curFeature.layerId;
			var graphic = curFeature.feature;
			//点击显示结果
		 	var infoTemplate = new esri.InfoTemplate();
			infoTemplate.setTitle(graphic.attributes.RESNO);
			infoTemplate.setContent("RESNAME :" + graphic.attributes.RESNAME);
			graphic.setInfoTemplate(infoTemplate);
			// 根据类型设置显示样式
			switch (graphic.attributes.SHAPE) {
			case "Point":
				graphic.setSymbol(symbol);
				break;
			case "Polyline":
				true == getSectExitsInSameSects(graphic.attributes.RESID,sameSectList)?graphic.setSymbol(lineSymbol):graphic.setSymbol(lineSymbolArr[index]);
				break;
			 case "Polygon":
			graphic.setSymbol(polygonSymbol);
			break; 
			} 

			//添加到graphics进行高亮显示
			//alert(graphic.getTitle());
			map.graphics.add(graphic);
			dojo.connect(map.graphics, "onClick",onClickLayer);
			dojo.connect(map.graphics, "onMouseOver",mouseOutLayer);
		    dojo.connect(map.graphics, "onMouseOut",mouseOutLayer);
			graphic._extent.xmax = graphic._extent.xmax + 2000;
			graphic._extent.xmin = graphic._extent.xmin - 2000;
			graphic._extent.ymax = graphic._extent.ymax + 2000;
			graphic._extent.ymin = graphic._extent.ymin - 2000;
			map.setExtent(graphic._extent);
		}
	}
	
		//画出历史光路
	function showResultsAndDrawHis(results) {
		//symbology for graphics
		var lineSymbol = new esri.symbol.SimpleLineSymbol(
				esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("#333300"), 5);
		var symbol = new esri.symbol.SimpleMarkerSymbol(
					 esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 
					 10,
				     lineSymbol,
				     new dojo.Color("#99FF66"));
		var polygonSymbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_NONE, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255,0,0]), 2), new dojo.Color([255,255,0,0.25]));
		// 清除上一次的高亮显示
		//map.graphics.clear();
		for ( var i = 0; i < results.length; i++) {
			var curFeature = results[i];
			//alert("shebeiID"+eqpid);
			//得到ID
			layerID = curFeature.layerId;
			var graphic = curFeature.feature;
			//点击显示结果
			infoTemplate = new esri.InfoTemplate();
			infoTemplate.setTitle(graphic.attributes.RESNO);
			infoTemplate.setContent("RESNAME :" + graphic.attributes.RESNAME);
			graphic.setInfoTemplate(infoTemplate);
			// 根据类型设置显示样式
			switch (graphic.attributes.SHAPE) {
			case "Point":
				graphic.setSymbol(symbol);
				break;
			case "Polyline":
				graphic.setSymbol(lineSymbol);
				break;
			 case "Polygon":
			graphic.setSymbol(polygonSymbol);
			break; 
			}

			//添加到graphics进行高亮显示
			//alert(graphic.getTitle());
			map.graphics.add(graphic);
			graphic._extent.xmax = graphic._extent.xmax + 1000;
			graphic._extent.xmin = graphic._extent.xmin - 1000;
			graphic._extent.ymax = graphic._extent.ymax + 1000;
			graphic._extent.ymin = graphic._extent.ymin - 1000;
			map.setExtent(graphic._extent);
		}
	}
	window.onload=function()
	{
		var error = "${error}";;
		if(error != undefined && "" !=error && error != 'undefined')
		{
			alert(error);
		}
	}
</script>
	</head>
	<body class="tundra">
		<div id="map"
			style="position: relative; width: 100%; height: 100%; border: 0px solid #000;">
		</div>
		<div id="win">
			<div id="resInfo">
			</div>
		</div> 
		 <div id="glList" style="padding-left: 10px;">
		</div>
	</body>
</html>