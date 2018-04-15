<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title></title>
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

	<input type="hidden" name="area_id" id="area_id" value="${area_id}" />
	<input type="hidden" name="area_name" id="area_name"
		value="${AREA_NAME}" />



	<div id="l-map"></div>

	<div id="r-result">

		<div style="border-bottom: 1px solid #d2d2d2; padding:10px;">
			<form id="ff">
				<table id="tbl1">

					<tr>
						<td>光缆段：</td>
						<td><select class="condition-select" name="cable_id"
							id="cable_id" onchange="getRelayByCableId(this.value)">
								<option value=''>--请选择--</option>
								<c:forEach items="${calbeList}" var="res">
									<option value='${res.CABLE_ID}'>${res.CABLE_NAME}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td>中继段：</td>
						<td><select class="condition-select" name="relay_id" onchange="getLineInfoByRelay(this.value);"
							id="relay_id">
						</select></td>
					</tr>
					<tr>
						<td>巡线段：</td>
						<td><select class="condition-select" name="line_id" 
							id="line_id">
						</select></td>
					</tr>
					
					<tr>
						<td><input type="checkbox" name="site_type"
							value="1">关键点</td>
							<td><input type="checkbox" name="site_type"
							value="2">非关键点</td>

					</tr>
					

				</table>
			</form>
		</div>
		<div style="height: 35px; ">
			<div class="btn-operation" onClick="search();">查询</div>
			<div class="btn-operation" onClick="deleteSite();">删除</div>
		</div>


		
	</div>
    <div id="win"></div>

	<script type="text/javascript">
		var area_id = $("input[name='area_id']").val();//地区
		var area_name = $("input[name='area_name']").val();//地区

		var cache = new CacheMap();//缓存展示的点信息
		var pointsSelected = new CacheMap();//缓存选择的点信息
		var markerMap = new CacheMap();//缓存marker
		var iconMap = new CacheMap();//缓存关键点图标
		var points;
		var isAll = true;//当前是否显示全部点
		var overlays=[];
		
		var point2id={};
		
		//默认匹配距离
		var defaultDis;

		//初始化地图
		$("#l-map").css("width", document.body.clientWidth - 290);
		var map = new BMap.Map("l-map");
		map.centerAndZoom(area_name, 13);
		map.enableScrollWheelZoom(); //启用滚轮放大缩小

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
		var userdIcon = new BMap.Icon(webPath + "images/icon_fire_green.png",
				new BMap.Size(28, 40), {
					anchor : new BMap.Size(14, 40),
					infoWindowAnchor : new BMap.Size(14, 0)
				});

		
		

		

		$(window).resize(function() {
			$("#l-map").css("width", document.body.clientWidth - 290);
		});

		
	</script>
	<script type="text/javascript">
		
		var icon1 = new BMap.Icon(webPath + "images/waili.png", new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
		var icon2 = new BMap.Icon(webPath + "images/buxian.png", new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
		
		function deleteSite(){
			var relay_id = $("#relay_id").val();
			if (relay_id == null || relay_id == '') {
				alert("中继段不能为空！");
				return false;
			}
			addTab("删除巡线点", "<%=path%>/siteController/deleteSite.do?relayId="+relay_id);
			/* $.ajax({
				async : false,
				type : "post",
				url : webPath + "siteController/deleteSite.do",
				data : {
					relayId : relay_id
				},
				dataType : "json",
				success : function(data) {
					
				}
			}); */
			
		}
		
		function search() {
			
			var relay_id = $("#relay_id").val();
			if (relay_id == null || relay_id == '') {
				alert("中继段不能为空！");
				return false;
			}
			var site_type = '';
			$("[name=site_type]").each(function() {
				if(this.checked){
					site_type = site_type + $(this).val() + ",";
				}
				
			});
			if (site_type != '') {
				site_type = site_type.substring(0, site_type.length - 1);
			}
			
			var line_id = $("#line_id").val();
			
			clearAll();
			
			$.messager.progress({
				title : '请稍候',
				msg : '',
				text : ""
			});
			$.ajax({
				async : false,
				type : "post",
				url : webPath + "siteController/findSiteByLine.do",
				data : {
					relay_id : relay_id,
					site_type : site_type,
					line_id:line_id
				},
				dataType : "json",
				success : function(data) {
					$.messager.progress("close");
					
					defaultDis=data.defaultDis;
					$.each(data.res, function(i, item) {
						showPoint2(item);
					});
				}
			});
		}

		
		function showPoint2(data) {
			//                
			
			var SITE_ID = data["SITE_ID"];

			var SITE_NAME = data["SITE_NAME"];
			var LONGITUDE = data["LONGITUDE"];
			var LATITUDE = data["LATITUDE"];
			var ADDRESS = data["ADDRESS"];
			//var UPDATE_TIME = data["UPDATE_TIME"];
			var SITE_TYPE = data["SITE_TYPE"];
			//var MAINTAIN_RANK = data["MAINTAIN_RANK"];
			var SITE_MATCH = data["SITE_MATCH"];
			if (typeof (ADDRESS) == 'undefined') {
				ADDRESS = "";
			}
			if (typeof (SITE_MATCH) == 'undefined') {
				SITE_MATCH = defaultDis;
			}
			
		//存放点的id
			var key=LONGITUDE+""+LATITUDE;
			point2id[key]=SITE_ID;

			var info = "<div class='map_bubble_info'>名称：" + SITE_NAME
					+ "<br/>地址：" + ADDRESS + "<br/>匹配距离：" + SITE_MATCH+ "</div>";
			var point = new BMap.Point(LONGITUDE, LATITUDE);
			var icon;
			if(SITE_TYPE==1){
				icon =icon1;
			}else{
				icon= icon2;
			}
			var marker = new BMap.Marker(point, {
				icon : icon
			});
			
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
			marker.addEventListener("click", function(e) {
				var key=this.getPosition().lng+""+this.getPosition().lat;
				var site_id=point2id[key];
				
				 $('#win').window({
						title : "【巡线点编辑】",
						href : webPath + "siteController/siteEditUI.do?site_id="+site_id,
						width : 650,
						height : 400,
						zIndex : 2,
						region : "center",
						collapsible : false,
						cache : false,
						modal : true
					});
				
				
			});
			overlays.push(marker);
			//markerMap.put(POINT_ID, marker);
		}
		
		function clearAll(){
			for(var i in overlays){
				map.removeOverlay(overlays[i]);
			}
		}
		
		
		
	
		
		function getRelayByCableId(gldId){
			$.ajax({          async:false,
				  type:"post",
				  url :webPath + "lineInfoController/getRelayByGl.do",
				  data:{gldId:gldId},
				  dataType:"json",
				  success:function(data){
					  $("#relay_id").empty();
					  $("#relay_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.relayList,function(i,item){
						  $("#relay_id").append("<option value='"+item.RELAY_ID+"'>"+item.RELAY_NAME+"</option>");		
					  });
				  }
			  });
		}
		
		
		function updateForm() {
			var pass = $("#formEdit").form('validate');
			if (pass) {
				
				var site_match=$("#site_match").val();
				if(site_match!=''){
					//var defaultDis=$("#defaultDis").val();
					  if(Number(site_match)>Number(defaultDis)){
						  alert("默认距离是："+defaultDis+"，设置的距离必须比默认距离小");
						  return ;
					  }
				}
				
				  
				$.messager.confirm('系统提示', '您确定要更新该巡线点吗?', function(r) {
					if (r) {
						var obj=makeParamJson('formEdit');
						$.ajax({
							type : 'POST',
							url : webPath + "siteController/update.do",
							data : obj,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新成功!',
										showType : 'show',
										timeout:'1000'//ms
									});
								}
								$('#win').window('close');
								search();
							}
						})
					}
				});
			}
		}
		
		
		function getLineInfoByRelay(relay_id){
			
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "lineInfoController/getLineInfoByRelay.do",
				  data:{relay_id:relay_id},
				  dataType:"json",
				  success:function(data){
					  $("#line_id").empty();
					  $("#line_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.lineInfoList,function(i,item){
						  $("#line_id").append("<option value='"+item.LINE_ID+"'>"+item.STAFF_NAME+" "+item.LINE_NAME+"</option>");		
					  });
				  }
			  });
		}
	</script>
</body>
</html>