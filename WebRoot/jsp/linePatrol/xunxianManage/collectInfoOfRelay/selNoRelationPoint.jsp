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
	width: 291px;
	float: right;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>

<script type="text/javascript" 
    src="http://api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>
	
<title>未关联上线路段落的设施点</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<script type="text/javascript">
	var equip_list = eval(${equip_list});
	// 百度地图API功能
	$("#l-map").css("width", document.body.clientWidth - 19);
	var map = new BMap.Map("l-map");
	
	var checkedIcon1 = new BMap.Icon(webPath + "images/guanjian.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
	var checkedIcon2 = new BMap.Icon(webPath + "images/feiguanjian.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});

	var icon1 = new BMap.Icon(webPath + "images/waili.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
	
	var icon2 = new BMap.Icon(webPath + "images/buxian.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});

	//建立九种设施类型的图片 1.标石 2.人井 3.地标 4.宣传牌 5.埋深点 6.电杆 7.警示牌 8.护坡 9.接头盒
	var markStone=new BMap.Icon(webPath+"images/equiptype/biaoshi22-1.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});		
	
	var wells=new BMap.Icon(webPath+"images/equiptype/renjing22-1.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});	
			
	var landMark=new BMap.Icon(webPath+"images/equiptype/dibiao22-1.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
			
	var billBoards=new BMap.Icon(webPath+"images/equiptype/xuanchuanpai22-1.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
	
	var buriedDepthSpot=new BMap.Icon(webPath+"images/equiptype/maisheng22-1.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
	
	var Pole=new BMap.Icon(webPath+"images/equiptype/diangan22-1.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
			
	var WarningSign=new BMap.Icon(webPath+"images/equiptype/jingshipai22.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
			
	var slopeProtection=new BMap.Icon(webPath+"images/equiptype/hupo22-1.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
			
	var JointBox=new BMap.Icon(webPath+"images/equiptype/jietouhe22-1.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});	
			
	//建立九种设施类型置灰的图片 1.标石 2.人井 3.地标 4.宣传牌 5.埋深点 6.电杆 7.警示牌 8.护坡 9.接头盒											
	var markStone2=new BMap.Icon(webPath+"images/equiptype/biaoshi22-2.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});		
	
	var wells2=new BMap.Icon(webPath+"images/equiptype/renjing22-2.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});	
			
	var landMark2=new BMap.Icon(webPath+"images/equiptype/dibiao22-2.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
			
	var billBoards2=new BMap.Icon(webPath+"images/equiptype/xuanchuanpai22-2.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
	
	var buriedDepthSpot2=new BMap.Icon(webPath+"images/equiptype/maisheng22-2.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
	
	var Pole2=new BMap.Icon(webPath+"images/equiptype/diangan22-2.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
			
	var WarningSign2=new BMap.Icon(webPath+"images/equiptype/jingshipai22-2.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
			
	var slopeProtection2=new BMap.Icon(webPath+"images/equiptype/hupo22-2.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});
			
	var JointBox2=new BMap.Icon(webPath+"images/equiptype/jietouhe22-2.png",
			new BMap.Size(24,24),{anchor: new BMap.Size(11, 20), infoWindowAnchor: new BMap.Size(14, 0)});	
	
	
	
	$(window).resize(function(){
		$("#l-map").css("width", document.body.clientWidth - 20);
	});
	
	// 百度地图API功能
	var map = new BMap.Map("l-map");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(equip_list[0].LONGITUDE, equip_list[0].LATITUDE), 15);  // 初始化地图,设置中心点坐标和地图级别
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	
	var myDis = new BMapLib.DistanceTool(map);
	
	//添加控件
	var cr = new BMap.CopyrightControl({anchor: BMAP_ANCHOR_TOP_RIGHT});   //设置版权控件位置
	map.addControl(cr); //添加版权控件

	//var bs = map.getBounds();   //返回地图可视区域
	cr.addCopyright({id: 1, content: "<div  class='bdmap-tool' onClick='measureDis();'>测    距</div>"});   
	function measureDis(){
		myDis.open();  //开启鼠标测距
	}


	var opts = {
			width : 250,     // 信息窗口宽度
			height: 140,     // 信息窗口高度
			title : "设施点信息窗口" , // 信息窗口标题
		   };

	function openInfo(content,e){ //展示设施点基本信息
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}	
		
		
	function addHandler(content,marker){ 
		marker.addEventListener("click",function(e){//鼠标悬浮上设施点，设施点事件绑定
			openInfo(content,e);
			}
		);
	}



	
	$(equip_list).each(function(){
		var point = new BMap.Point(Number(this.LONGITUDE), Number(this.LATITUDE));
// 		var marker = new BMap.Marker(point,{icon:checkedIcon1});  // 创建标注
		
		if(this.EQUIP_TYPE==1){
		var marker = new BMap.Marker(point,{icon:markStone});  // 创建标石
		}else if(this.EQUIP_TYPE==2){
		var marker = new BMap.Marker(point,{icon:wells});  // 创建人井
		}else if(this.EQUIP_TYPE==3){
		var marker = new BMap.Marker(point,{icon:landMark});  // 创建地标
		}else if(this.EQUIP_TYPE==4){
		var marker = new BMap.Marker(point,{icon:billBoards});  // 创建宣传牌
		}else if(this.EQUIP_TYPE==5){
		var marker = new BMap.Marker(point,{icon:buriedDepthSpot});  // 创建埋深点
		}else if(this.EQUIP_TYPE==6){
		var marker = new BMap.Marker(point,{icon:Pole});  // 创建电杆
		}else if(this.EQUIP_TYPE==7){
		var marker = new BMap.Marker(point,{icon:WarningSign});  // 创建警示牌
		}else if(this.EQUIP_TYPE==8){
		var marker = new BMap.Marker(point,{icon:slopeProtection});  // 创建护坡 
		}else if(this.EQUIP_TYPE==9){
		var marker = new BMap.Marker(point,{icon:JointBox});  // 创建接头盒
		}else{
		var marker = new BMap.Marker(point,{icon:checkedIcon1});  // 创建标注
		}
		
		map.addOverlay(marker);              // 将标注添加到地图中
		var content = "设施id:"+this.EQUIP_ID+"<br/>编号:"+this.EQUIP_CODE+"<br/>描述："+this.DESCRIPTION+
		"<br/>经度："+this.LONGITUDE+"<br/>纬度："+this.LATITUDE;;
		addHandler(content,marker);
	});
	
</script>
</body>
</html>
