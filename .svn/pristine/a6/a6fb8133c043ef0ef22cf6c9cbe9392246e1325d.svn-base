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
	
<title>非路由显示页面</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<div style="border-bottom: 1px solid #d2d2d2; padding:10px;">
			<form id="ff">
				<input type="hidden" name="inspact_id" id="inspact_id" />
				<table id="tbl1">

					<tr>
						<td>光缆名称：</td>
						<td><div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition" type="text" value="${cable_name}" readonly="readonly"/>
							</div>
						</td>
					</tr>
					<tr>
						<td>中继段名称：</td>
						<td><div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition" type="text" value="${relay_name}" readonly="readonly"/>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	<div style="height: 40px;" >
		<div style="margin-left: 5px;margin-top:5px;" class="btn-operation"
			onClick="back();">返回</div>	
	</div>

	<div id="win">
		
	</div>

	<div
		style="border-bottom: 1px solid #d2d2d2; padding: 10px; overflow-x: auto; overflow-y: auto; height: 300px; margin-top:40px;">
		<table id="dg" cellpadding="1" boarder='1' cellspacing="1"
			width="100%" class="" style="font-size: 11px; table-layout: fixed;">
			<tr>
				<td>设施id</td>
				<td>设施编号</td>
				<td>设施类型</td>
				<td>经纬度</td>
			</tr>


		</table>
	</div>


	<script type="text/javascript">
	var no_equip_list= eval(${no_equip_list});
	var relay_id = ${relay_id};
	var area_id = ${area_id};
	var cable_id = ${cable_id};
	
	// 百度地图API功能
	$("#l-map").css("width", document.body.clientWidth - 300);
	var map = new BMap.Map("l-map");
	
	var checkedIcon1 = new BMap.Icon(webPath + "images/guanjian.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});

	var icon1 = new BMap.Icon(webPath + "images/waili.png", 
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
	
	$(window).resize(function(){
		$("#l-map").css("width", document.body.clientWidth - 290);
	});
	
	// 百度地图API功能
	var map = new BMap.Map("l-map");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(no_equip_list[0].LONGITUDE, no_equip_list[0].LATITUDE), 15);  // 初始化地图,设置中心点坐标和地图级别
// 	map.setCurrentCity("淮安市");          // 设置地图显示的城市 此项是必须设置的
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
	
	
// 	{"EQUIP_TYPE":"人井","DESCRIPTION":"圆井盖,铸铁,90*120  手孔,长途专用,普通井","IS_EQUIP":1,
// 		"EQUIP_CODE":"宁淮埋式64芯马坝---盱眙015号井","ORDER":15,"LONGITUDE":"118.77120408","EQUIP_ID":"441","LATITUDE":"32.97879963"}
	var points=[];
	var markers=[];
	var resultEquipList=[];
	
	
function addHandler(content,marker){

}



	
	$(no_equip_list).each(function(){
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
		addHandler(null,marker);
		points.push(point);
		markers.push(marker);
		$("#dg").append("<tr><td>"+this.EQUIP_ID+"</td><td>"+this.EQUIP_CODE+"</td><td>"+this.EQUIP_TYPE_NAME+"</td><td>"+this.LONGITUDE+","+this.LATITUDE+"</td></tr>");
	});
	$("#dg tr:not(:first)").bind('click',showAnimation);
	
	var polyline = new BMap.Polyline(points,    
     {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5}    
    );    
    map.addOverlay(polyline);
   
    function showAnimation(){
    	$("#dg").find("tr").css("background-color","#FFFFFF");
    	$(this).css("background-color","red");
    	var location = $(this).find("td:last").text().split(",");
    	for(var i=0 ;i<markers.length;i++){
    		markers[i].setAnimation(null);
    	}
    	for(var i=0 ;i<markers.length;i++){
    		if(markers[i].getPosition().lng==location[0] && markers[i].getPosition().lat==location[1]){
    			markers[i].setAnimation(BMAP_ANIMATION_BOUNCE);
    			map.centerAndZoom(markers[i].getPosition(), 20);  // 初始化地图,设置中心点坐标和地图级别
    			return ;
    		}
    	}
    	
    }
    function back(){
    	location.href="init.do";
    }
    
</script>
</body>
</html>
