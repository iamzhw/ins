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
	
<title>光缆段新增</title>
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
			onClick="resultListToCommit();">提交</div>
		<div style="margin-left: 5px;margin-top:5px;" class="btn-operation"
			onClick="back();">返回</div>	
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
		<div id="dg1">
			<table>
				<tr>
					<td></td>
					<td>id</td>
					<td>编号</td>
					<td>类型</td>
				</tr>
			</table>
			<input type="button" value="确定1" onclick="changeEquip()"/>
		</div>
		
		<div id="dg2">
			<table>
				<tr>
					<td></td>
					<td>id</td>
					<td>编号</td>
					<td>类型</td>
				</tr>
			</table>
			<input type="button" value="确定2" onclick="changeRelationEquip()"/>
		</div>
	
	


	<script type="text/javascript">
	$("#dg1").window({
		closed:true
	})
	
	$("#dg2").window({
		closed:true
	})
	
	var relationEquips= eval(${el});  //路由非路由集合对象[[{},{}],[{},{}],[{},{}],[{},{}]]
	var equipList=[];  //存在非路由关联联系的路由设施点
	
	var page = ${model.page};
	var relay_id = ${model.relay_id};
	var area_id = ${model.area_id};
	var cable_id = ${model.cable_id};
	var count = ${model.count};
	
	

	// 百度地图API功能
	$("#l-map").css("width", document.body.clientWidth - 300);
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
		$("#l-map").css("width", document.body.clientWidth - 290);
	});
	
	// 百度地图API功能
	var map = new BMap.Map("l-map");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(relationEquips[0][0].LONGITUDE, relationEquips[0][0].LATITUDE), 20);  // 初始化地图,设置中心点坐标和地图级别
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
	
// 	[{"CABLE_ID":"278","EQUIP_TYPE":"人井","RELAY_ID":"302","DESCRIPTION":"圆井盖,铸铁,90*120  手孔,长途专用,普通井",
// 		"IS_EQUIP":1,"EQUIP_CODE":"宁淮埋式64芯马坝---盱眙015号井","ORDER":1,"LONGITUDE":"118.77120408","EQUIP_ID":"441",
// 		"LATITUDE":"32.97879963"}]    
	
	var points=[];
	var markers=[];
	
	var opts = {
			width : 250,     // 信息窗口宽度
			height: 140,     // 信息窗口高度
			title : "设施点信息窗口" , // 信息窗口标题
		   };
	
function addHandler(content,marker){ //鼠标悬浮上设施点，设施点事件绑定
	marker.addEventListener("mouseover",function(e){
// 			openInfo(content,e);
		
	});
	marker.addEventListener("rightclick",function(e){
			var mk = e.target;
			var point = mk.getPosition();
			var status=0;
			var equipObj=[];
			var roundEquip=[];
			var equipPoint=point.lng+","+point.lat;
			for(var i=0 ;i<equipList.length;i++){
				if(Number(equipList[i][0].LONGITUDE) == point.lng && Number(equipList[i][0].LATITUDE) == point.lat){
					 status=1;
					 equipObj=equipList[i];
				}
			}
			for(var i=0;i<relationEquips.length;i++){
				if(Number(relationEquips[i][0].LONGITUDE) == point.lng && Number(relationEquips[i][0].LATITUDE) == point.lat){
					if(i>0){
						roundEquip.push(relationEquips[i-1][0]); // [{},{}] 格式
					}
					if(i<relationEquips.length-1){
						roundEquip.push(relationEquips[i+1][0]);
					}
				}
			}
			if(status){ //路由设施点存在关联非路由设施点的情况
				$("#dg1 table tr").not(":first").remove();
				for(var i=1;i<equipObj.length;i++){
					$("#dg1").find("table").append("<tr><td><input value="+equipObj[0].EQUIP_ID+","+equipObj[i].EQUIP_ID+" type='radio' name='id' /></td><td>"+equipObj[i].EQUIP_ID+"</td><td>"+equipObj[i].EQUIP_CODE+"</td><td>"+equipObj[i].EQUIP_TYPE+"</td></tr>");
				}
				$("#dg1").window({
					width:600,    
				    height:400,    
				    modal:true,
				    closable:true,
				    closed:false,
				    collapsible:false,
				    title:"选择非路由设施点设置为路由设施点"
				}).window('center');
			}else{ //只有路由设施点的情况
				$("#dg2 table tr").not(":first").remove();
				for(var i=0;i<roundEquip.length;i++){
					$("#dg2").find("table").append("<tr><td><input value="+roundEquip[i].EQUIP_ID+","+equipPoint+" type='radio' name='id' /></td><td>"+roundEquip[i].EQUIP_ID+"</td><td>"+roundEquip[i].EQUIP_CODE+"</td><td>"+roundEquip[i].EQUIP_TYPE+"</td></tr>");
				}
				$("#dg2").window({
					width:600,    
				    height:400,    
				    modal:true,
				    closable:true,
				    closed:false,
				    collapsible:false,
				    title:"选择路由设施点设置为非路由设施点"
				}).window('center');
			}
	});
	marker.addEventListener("click",function(e){
		var mk = e.target;
		var status=0;
		for(var i=0;i<points.length;i++){
			if(points[i].lng==mk.getPosition().lng && points[i].lat==mk.getPosition().lat){
				status=1;   
			}
		}
		if(status){
			if(points[points.length-1].lng==mk.getPosition().lng && points[points.length-1].lat==mk.getPosition().lat){
				page=page+1;
				if(page>count){
					page=count;
					$.messager.alert("提示","该地图展示是最后一页");
					return ;
				}
				location.href="changeRelationEquipInit.do?page="+page+"&cable_id="+cable_id+"&relay_id="+relay_id+"&area_id="+area_id ;
			}if(points[0].lng==mk.getPosition().lng && points[0].lat==mk.getPosition().lat){
				page=page-1;
				if(page<=0){
					page=1;
					$.messager.alert("提示","已经是该地图的第一页");
					return;
				}
				location.href="changeRelationEquipInit.do?page="+page+"&cable_id="+cable_id+"&relay_id="+relay_id+"&area_id="+area_id ;
			}
		}
	}
	);
}

function changeEquip(){
	$("#dg1").window({
		closed:true
	})
	$("#dg1").find("input:radio").each(function(){
		if(this.checked==true){
			var equipId=this.value.split(",")[0];  //路由设施
			var relationEquipId=this.value.split(",")[1]; //非路由设施
			var modelEquip = delRelationEquip(equipId,relationEquipId,relationEquips);//清除路由设施点上关联的给定的非路由设施点 并返回该非路由设施点信息
			insertEquip(equipId,modelEquip,relationEquips);//非路由设施改为路由设施在地图上展示
			map.clearOverlays();
			points.length=0;
			markers.length=0;
			equipList.length=0;
			$("#dg tr:not(:first)").remove();
			$(relationEquips).each(function(){
				var point;
				var marker;
				if(this.length>1){
// 				    console.log(this);
					point = new BMap.Point(Number(this[0].LONGITUDE), Number(this[0].LATITUDE));	//获取路由设施点生成point对象
// 					marker = new BMap.Marker(point,{icon:checkedIcon2});  // 创建路由设施标注（该路由设施有关联非路由设施点）

					if(this[0].EQUIP_TYPE==1){
					var marker = new BMap.Marker(point,{icon:markStone2});  // 创建标石灰
					}else if(this[0].EQUIP_TYPE==2){
					var marker = new BMap.Marker(point,{icon:wells2});  // 创建人井灰
					}else if(this[0].EQUIP_TYPE==3){
					var marker = new BMap.Marker(point,{icon:landMark2});  // 创建地标灰
					}else if(this[0].EQUIP_TYPE==4){
					var marker = new BMap.Marker(point,{icon:billBoards2});  // 创建宣传牌灰
					}else if(this[0].EQUIP_TYPE==5){
					var marker = new BMap.Marker(point,{icon:buriedDepthSpot2});  // 创建埋深点灰
					}else if(this[0].EQUIP_TYPE==6){
					var marker = new BMap.Marker(point,{icon:Pole2});  // 创建电杆灰
					}else if(this[0].EQUIP_TYPE==7){
					var marker = new BMap.Marker(point,{icon:WarningSign2});  // 创建警示牌灰
					}else if(this[0].EQUIP_TYPE==8){
					var marker = new BMap.Marker(point,{icon:slopeProtection2});  // 创建护坡灰 
					}else if(this[0].EQUIP_TYPE==9){
					var marker = new BMap.Marker(point,{icon:JointBox2});  // 创建接头盒灰
					}else{
					var marker = new BMap.Marker(point,{icon:checkedIcon2});  // 创建路由设施标注（该路由设施有关联非路由设施点）
					}
										
					equipList.push(this);
				}else{
					point = new BMap.Point(Number(this[0].LONGITUDE), Number(this[0].LATITUDE));	//获取路由设施点生成point对象
// 					marker = new BMap.Marker(point,{icon:checkedIcon1});  // 创建路由标注（该路由标示下没有关联的非路由设施点）
					
					if(this[0].EQUIP_TYPE==1){
					var marker = new BMap.Marker(point,{icon:markStone});  // 创建标石灰
					}else if(this[0].EQUIP_TYPE==2){
					var marker = new BMap.Marker(point,{icon:wells});  // 创建人井灰
					}else if(this[0].EQUIP_TYPE==3){
					var marker = new BMap.Marker(point,{icon:landMark});  // 创建地标灰
					}else if(this[0].EQUIP_TYPE==4){
					var marker = new BMap.Marker(point,{icon:billBoards});  // 创建宣传牌灰
					}else if(this[0].EQUIP_TYPE==5){
					var marker = new BMap.Marker(point,{icon:buriedDepthSpot});  // 创建埋深点灰
					}else if(this[0].EQUIP_TYPE==6){
					var marker = new BMap.Marker(point,{icon:Pole});  // 创建电杆灰
					}else if(this[0].EQUIP_TYPE==7){
					var marker = new BMap.Marker(point,{icon:WarningSign});  // 创建警示牌灰
					}else if(this[0].EQUIP_TYPE==8){
					var marker = new BMap.Marker(point,{icon:slopeProtection});  // 创建护坡灰 
					}else if(this[0].EQUIP_TYPE==9){
					var marker = new BMap.Marker(point,{icon:JointBox});  // 创建接头盒灰
					}else{
					var marker = new BMap.Marker(point,{icon:checkedIcon1});  // 创建路由标注（该路由标示下没有关联的非路由设施点）
					}	
					
				}
				points.push(point);  //所有路由设施点对象集合
				map.addOverlay(marker); // 将标注添加到地图中
				addHandler(content,marker);  //事件绑定	
		 		markers.push(marker);  //路由设施marker集合
		 		$("#dg").append("<tr><td>"+this[0].EQUIP_ID+"</td><td>"+this[0].EQUIP_CODE+"</td><td>"+this[0].EQUIP_TYPE+"</td><td>"+this[0].LONGITUDE+","+this[0].LATITUDE+"</td></tr>");
			});
			$("#dg tr:not(:first)").bind('click',showAnimation);
		 		var polyline = new BMap.Polyline(points,    
					     {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5}    
			    );    
			    map.addOverlay(polyline);
			
			
			return false;  //跳出循环
		}
	});
}


function changeRelationEquip(){
	$("#dg2").window({
		closed:true
	})
	$("#dg2").find("input:radio").each(function(){
		if(this.checked==true){
			changeToRelationEquip(this.value);	
			map.clearOverlays();
			points.length=0;
			markers.length=0;
			equipList.length=0;
			$("#dg tr:not(:first)").remove();
			$(relationEquips).each(function(){
				var point;
				var marker;
				if(this.length>1){
					point = new BMap.Point(Number(this[0].LONGITUDE), Number(this[0].LATITUDE));	//获取路由设施点生成point对象
// 					marker = new BMap.Marker(point,{icon:checkedIcon2});  // 创建路由设施标注（该路由设施有关联非路由设施点）

					if(this[0].EQUIP_TYPE==1){
					var marker = new BMap.Marker(point,{icon:markStone2});  // 创建标石灰
					}else if(this[0].EQUIP_TYPE==2){
					var marker = new BMap.Marker(point,{icon:wells2});  // 创建人井灰
					}else if(this[0].EQUIP_TYPE==3){
					var marker = new BMap.Marker(point,{icon:landMark2});  // 创建地标灰
					}else if(this[0].EQUIP_TYPE==4){
					var marker = new BMap.Marker(point,{icon:billBoards2});  // 创建宣传牌灰
					}else if(this[0].EQUIP_TYPE==5){
					var marker = new BMap.Marker(point,{icon:buriedDepthSpot2});  // 创建埋深点灰
					}else if(this[0].EQUIP_TYPE==6){
					var marker = new BMap.Marker(point,{icon:Pole2});  // 创建电杆灰
					}else if(this[0].EQUIP_TYPE==7){
					var marker = new BMap.Marker(point,{icon:WarningSign2});  // 创建警示牌灰
					}else if(this[0].EQUIP_TYPE==8){
					var marker = new BMap.Marker(point,{icon:slopeProtection2});  // 创建护坡灰 
					}else if(this[0].EQUIP_TYPE==9){
					var marker = new BMap.Marker(point,{icon:JointBox2});  // 创建接头盒灰
					}else{
					var marker = new BMap.Marker(point,{icon:checkedIcon2});  // 创建路由设施标注（该路由设施有关联非路由设施点）
					}

					equipList.push(this);
				}else{
					point = new BMap.Point(Number(this[0].LONGITUDE), Number(this[0].LATITUDE));	//获取路由设施点生成point对象
// 					marker = new BMap.Marker(point,{icon:checkedIcon1});  // 创建路由标注（该路由标示下没有关联的非路由设施点）

					if(this[0].EQUIP_TYPE==1){
					var marker = new BMap.Marker(point,{icon:markStone});  // 创建标石灰
					}else if(this[0].EQUIP_TYPE==2){
					var marker = new BMap.Marker(point,{icon:wells});  // 创建人井灰
					}else if(this[0].EQUIP_TYPE==3){
					var marker = new BMap.Marker(point,{icon:landMark});  // 创建地标灰
					}else if(this[0].EQUIP_TYPE==4){
					var marker = new BMap.Marker(point,{icon:billBoards});  // 创建宣传牌灰
					}else if(this[0].EQUIP_TYPE==5){
					var marker = new BMap.Marker(point,{icon:buriedDepthSpot});  // 创建埋深点灰
					}else if(this[0].EQUIP_TYPE==6){
					var marker = new BMap.Marker(point,{icon:Pole});  // 创建电杆灰
					}else if(this[0].EQUIP_TYPE==7){
					var marker = new BMap.Marker(point,{icon:WarningSign});  // 创建警示牌灰
					}else if(this[0].EQUIP_TYPE==8){
					var marker = new BMap.Marker(point,{icon:slopeProtection});  // 创建护坡灰 
					}else if(this[0].EQUIP_TYPE==9){
					var marker = new BMap.Marker(point,{icon:JointBox});  // 创建接头盒灰
					}else{
					var marker = new BMap.Marker(point,{icon:checkedIcon1});  // 创建路由标注（该路由标示下没有关联的非路由设施点）
					}
					
				}
				points.push(point);  //所有路由设施点对象集合
				map.addOverlay(marker); // 将标注添加到地图中
				addHandler(content,marker);  //事件绑定	
		 		markers.push(marker);  //路由设施marker集合
		 		$("#dg").append("<tr><td>"+this[0].EQUIP_ID+"</td><td>"+this[0].EQUIP_CODE+"</td><td>"+this[0].EQUIP_TYPE+"</td><td>"+this[0].LONGITUDE+","+this[0].LATITUDE+"</td></tr>");
			});
			$("#dg tr:not(:first)").bind('click',showAnimation);
		 		var polyline = new BMap.Polyline(points,    
					     {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5}    
			    );    
			    map.addOverlay(polyline);
			
			
			return false;  //跳出循环
		}
	})
}

function changeToRelationEquip(info){
	var equipId=info.split(",")[0]; //待关联设施点id
	var longitude=info.split(",")[1]; 
	var latitude=info.split(",")[2];
	var relationEquip={};
	for(var i=0;i<relationEquips.length;i++){
		if(Number(relationEquips[i][0].LONGITUDE)==longitude && Number(relationEquips[i][0].LATITUDE)==latitude){
			relationEquip=relationEquips[i][0];
			relationEquips.splice(i,1);  //清除该设施点
		}
	}
	for(var i=0;i<relationEquips.length;i++){
		if(Number(relationEquips[i][0].EQUIP_ID)==equipId){
			relationEquips[i].push(relationEquip); //该设施点关联最近的设施点 做非路由处理
		}
	}
	
}




function delRelationEquip(equipId,relationEquipId,relationEquips){ 	//清除路由设施点上关联的给定的非路由设施点 并返回该非路由设施点信息
	for(var i=0;i<relationEquips.length;i++){
		if(Number(relationEquips[i][0].EQUIP_ID)==equipId){
			for(var j=0;j<relationEquips[i].length;j++){
				if(Number(relationEquips[i][j].EQUIP_ID)==relationEquipId){
					var modelEquip=[];
					modelEquip.push(relationEquips[i][j]);
					relationEquips[i].splice(j,1);
					return modelEquip;
				}
			}
			return false;
		}
	}
}

function insertEquip(equipId,modelEquip,relationEquips){ //非路由设施改为路由设施在地图上展示
	for(var i=0;i<relationEquips.length;i++){
		if(Number(relationEquips[i][0].EQUIP_ID)==equipId){
			relationEquips.splice(i, 0, modelEquip);  
			return false;
		}
	}
}

function openInfo(content,e){ //展示设施点基本信息
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
	map.openInfoWindow(infoWindow,point); //开启信息窗口
}


		$(relationEquips).each(function(){
			var point;
			var marker;
			if(this.length>1){ //存在非路由关联联系的路由设施点
				point = new BMap.Point(Number(this[0].LONGITUDE), Number(this[0].LATITUDE));	//获取路由设施点生成point对象
// 				marker = new BMap.Marker(point,{icon:checkedIcon2}); 
 
			    if(this[0].EQUIP_TYPE==1){
				var marker = new BMap.Marker(point,{icon:markStone2});  // 创建标石灰
				}else if(this[0].EQUIP_TYPE==2){
				var marker = new BMap.Marker(point,{icon:wells2});  // 创建人井灰
				}else if(this[0].EQUIP_TYPE==3){
				var marker = new BMap.Marker(point,{icon:landMark2});  // 创建地标灰
				}else if(this[0].EQUIP_TYPE==4){
				var marker = new BMap.Marker(point,{icon:billBoards2});  // 创建宣传牌灰
				}else if(this[0].EQUIP_TYPE==5){
				var marker = new BMap.Marker(point,{icon:buriedDepthSpot2});  // 创建埋深点灰
				}else if(this[0].EQUIP_TYPE==6){
				var marker = new BMap.Marker(point,{icon:Pole2});  // 创建电杆灰
				}else if(this[0].EQUIP_TYPE==7){
				var marker = new BMap.Marker(point,{icon:WarningSign2});  // 创建警示牌灰
				}else if(this[0].EQUIP_TYPE==8){
				var marker = new BMap.Marker(point,{icon:slopeProtection2});  // 创建护坡灰 
				}else if(this[0].EQUIP_TYPE==9){
				var marker = new BMap.Marker(point,{icon:JointBox2});  // 创建接头盒灰
				}else{
				var marker = new BMap.Marker(point,{icon:checkedIcon2});  // 创建路由设施标注（该路由设施有关联非路由设施点）
				}
				
				var content = "设施id:"+this[0].EQUIP_ID+"<br/>设施编号："+this[0].EQUIP_CODE+"<br/>设施类型："+this[0].EQUIP_TYPE+"<br/>设施描述:"+this[0].DESCRIPTION+"<br/>排序："+this[0].ORDER;
				equipList.push(this);
			}else{  //只是路由设施点，没有关联关系
				point = new BMap.Point(Number(this[0].LONGITUDE), Number(this[0].LATITUDE));	//获取路由设施点生成point对象
// 				marker = new BMap.Marker(point,{icon:checkedIcon1});  
				
				if(this[0].EQUIP_TYPE==1){
				var marker = new BMap.Marker(point,{icon:markStone});  // 创建标石灰
				}else if(this[0].EQUIP_TYPE==2){
				var marker = new BMap.Marker(point,{icon:wells});  // 创建人井灰
				}else if(this[0].EQUIP_TYPE==3){
				var marker = new BMap.Marker(point,{icon:landMark});  // 创建地标灰
				}else if(this[0].EQUIP_TYPE==4){
				var marker = new BMap.Marker(point,{icon:billBoards});  // 创建宣传牌灰
				}else if(this[0].EQUIP_TYPE==5){
				var marker = new BMap.Marker(point,{icon:buriedDepthSpot});  // 创建埋深点灰
				}else if(this[0].EQUIP_TYPE==6){
				var marker = new BMap.Marker(point,{icon:Pole});  // 创建电杆灰
				}else if(this[0].EQUIP_TYPE==7){
				var marker = new BMap.Marker(point,{icon:WarningSign});  // 创建警示牌灰
				}else if(this[0].EQUIP_TYPE==8){
				var marker = new BMap.Marker(point,{icon:slopeProtection});  // 创建护坡灰 
				}else if(this[0].EQUIP_TYPE==9){
				var marker = new BMap.Marker(point,{icon:JointBox});  // 创建接头盒灰
				}else{
				var marker = new BMap.Marker(point,{icon:checkedIcon1});  // 创建路由标注（该路由标示下没有关联的非路由设施点）
				}
				
				var content = "设施id:"+this[0].EQUIP_ID+"<br/>设施编号："+this[0].EQUIP_CODE+"<br/>设施类型："+this[0].EQUIP_TYPE+"<br/>设施描述:"+this[0].DESCRIPTION+"<br/>排序："+this[0].ORDER;
			}
			points.push(point);  //所有路由设施点对象集合
			map.addOverlay(marker); // 将标注添加到地图中
			addHandler(content,marker);  //事件绑定	
	 		markers.push(marker);  //路由设施marker集合
	 		$("#dg").append("<tr><td>"+this[0].EQUIP_ID+"</td><td>"+this[0].EQUIP_CODE+"</td><td>"+this[0].EQUIP_TYPE+"</td><td>"+this[0].LONGITUDE+","+this[0].LATITUDE+"</td></tr>");
		});
		$("#dg tr:not(:first)").bind('click',showAnimation);
	 		var polyline = new BMap.Polyline(points,    
				     {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5}    
		    );    
		    map.addOverlay(polyline);
	
	
    function resultListToCommit(){
    	$.ajax({
			url : webPath + "/StepEquip/changeRelationEquip.do",// 跳转到 action    
			data : {
				equiplist:JSON.stringify(relationEquips),
				page:page		
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			async: false,
			success : function(data) {
				if(data.status){
					$.messager.alert("提示","修改成功");
					location.href="changeRelationEquipInit.do?page="+page+"&cable_id="+cable_id+"&relay_id="+relay_id+"&area_id="+area_id ;
				}else{
					$.messager.alert("提示","修改失败");
				}
			}
		});
    }
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
