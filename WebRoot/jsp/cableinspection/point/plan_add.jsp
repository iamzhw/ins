<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>新增外力点计划</title>
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
	width:300px;
	float: left;
}

#r-result {
	height: 100%;
	width: 280px;
	float: right;
}
.map_bubble_title{
	font-weight: bold;
	font-size: 15px;
	float: left;
	line-height: 25px;
	border-bottom: 1px;
	border-bottom-color:#ccc; 
	margin-left:10px;
	padding-bottom: 8px;
	cursor: pointer;
}
.map_bubble_info{
	line-height: 25px;
}
</style>
</head>
<body style="padding: 3px; border: 0px">
<input type="hidden" name="plan_kind" id="plan_kind" value="${plan_kind}"/>
<input type="hidden" name="area_id" id="area_id" value="${area_id}"/>
<input type="hidden" name="area_name" id="area_name" value="${area_name}"/>
<input type="hidden" name="longitude" id="longitude" value="${longitude}"/>
<input type="hidden" name="latitude" id="latitude" value="${latitude}"/>
		<div id="l-map"></div>
		<div id="r-result">
			<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
				<div class="btn-operation" onClick="savePlan();">保存</div>
				<div class="btn-operation" onClick="history.back();">返回</div>
				<!-- div class="btn-operation" onClick="showPoints();">显示</div> -->
			</div>
			<div style="border-bottom: 1px solid #d2d2d2; padding:10px;">
			<form id="ff">
				<table id="tbl1">
				<tr>
					<td>计划名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="plan_name"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<!--<tr>
					<td>计划编码：</td>	
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="plan_no"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>-->
				<tr>
					<td>计划类型：</td>
					<td>
							<select class="condition-select" name="plan_type" id="plan_type">
								<option value='0'>周期性</option>	
								<option value='1'>临时性</option>	
							</select>
					</td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name=plan_start_time id="plan_start_time" required="true" 
								onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});" />
						</div></td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name=plan_end_time id="plan_end_time" required="true" 
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'plan_start_time\')}'});" />
						</div></td>
				</tr>
				<tr>
					<td>计划周期：</td>
					<td>
							<select class="condition-select" name="plan_circle" id="plan_circle">
								<option value='1'>日</option>	
								<option value='2'>周</option>	
								<option value='3'>月</option>	
								<option value='4'>年</option>	
							</select>
					</td>
				</tr>
				<tr>
					<td>计划次数：</td>
					<td>
							<select class="condition-select" name="plan_frequency" id="plan_frequency">
								<option value='1'>1</option>	
								<option value='2'>2</option>	
								<option value='3'>3</option>	
							</select>
					</td>
				</tr>
			</table></form>
			</div>
			<div id="div_custom" style="border-bottom: 1px solid #d2d2d2; overflow: auto;display:none;">
				<div id="btn_custom_add" class="btn-operation">添加自定义时间</div>
				<div id="btn_custom_delete" class="btn-operation">删除自定义时间</div>
			</div>
			<div style="border-bottom: 1px solid #d2d2d2; padding-left: 5px; display: none; background-color: white;"
			id="showPointquery">
			<table>
				<!-- <tr>
					<td nowrap="nowrap" width="80px">搜索类型</td>
					<td><select class="condition-select" name="query_type"
						id="query_type">
							<option value='0'>按道路名称搜索</option>
							<option value='1'>按设备名称搜索</option>
					</select></td>
				</tr> -->
				<tr class="queryRode">
					<td>检查点编码</td>
					<td><div class="condition-text-container">
							<input name="pointNo" type="text" class="condition-text" />
						</div></td>
				</tr>
				<tr class="searchEqp">
					<td>检查点名称</td>
					<td><div class="condition-text-container">
							<input name="pointName" type="text" class="condition-text" />
						</div></td>
				</tr>
				<tr>
					<td><div class="btn-operation" onClick="queryPoint();">
							搜索</div></td>
					<td><div class="btn-operation" onClick="hideQueryPoint();">
							隐藏</div></td>
				</tr>

			</table>
		</div>
			<div
			style="border-bottom: 1px solid #d2d2d2; overflow: auto;" id="lines">
				<div>
					<table class="datagrid-htable" style="height:41px;table-layout:fixed;" cellSpacing=0 cellPadding=0 border=0 id="show">
					</table>
				</div>
			</div>
		</div>
	<script type="text/javascript">
	var plan_kind = $("input[name='plan_kind']").val();//计划类型
	var area_id = $("input[name='area_id']").val();//地区
	var area_name = $("input[name='area_name']").val();//地区
	var longitudeInit = $("input[name='longitude']").val();//经度
	var latitudeInit = $("input[name='latitude']").val();//纬度
	var cache = new CacheMap();//缓存展示的点信息
	var pointsSelected = new CacheMap();//缓存选择的点信息
	var markerMap = new CacheMap();//缓存marker
	var iconMap = new CacheMap();//缓存关键点图标
	var points;
	var isAll = true;//当前是否显示全部点
	//初始化地图
	$("#l-map").css("width", document.body.clientWidth - 290);
	var map = new BMap.Map("l-map");
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	
	var checkedIcon = new BMap.Icon(webPath + "images/icon_checked.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var userdIcon = new BMap.Icon(webPath + "images/icon_fire_green.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	
	//搜索控件
	function searchControl(){
		this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	    this.defaultOffset = new BMap.Size(20, 20);
	}
	searchControl.prototype = new BMap.Control();
	searchControl.prototype.initialize = function(map){
		var div = document.createElement("div");
		var img = document.createElement("image");
		img.setAttribute('src', '../images/icon_query.png');	
		img.setAttribute('width', '48px');	
		img.setAttribute('height', '48px');
		img.setAttribute('title', '点击打开搜索框');
		img.style.cursor = "pointer";
		div.appendChild(img);
		div.onclick = function(){
			if(typeof(searchBoxContorl) == 'undefined'){
				searchBoxContorl = new searchBoxControl();
				map.addControl(searchBoxContorl);
			} else {
					//查询条件清空
				$("input[name='pointName']").val("");
				$("input[name='pointNo']").val("");
				$("#showPointquery").show();
			}
		};
		map.getContainer().appendChild(div);
		return div;
	};
	//搜索框控件
	function searchBoxControl(){
		this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	    this.defaultOffset = new BMap.Size(70, 20);
	}
	searchBoxControl.prototype = new BMap.Control();
	searchBoxControl.prototype.initialize = function(map){
		//var queryObj = $("#showPointquery").clone(true);
		var div = document.getElementById("showPointquery");
		map.getContainer().appendChild(div);
		$("#showPointquery").show();
		return div;
	};
	//将控件加入地图
	map.addControl(new searchControl());
	
	$(function(){
		$.ajax({
			type: "GET", 
			dataType:"json",
            url:webPath + "Care/plan_getPoints.do?type=2&area_id=" + area_id,
            success:function(datas) {
            	points = datas;
            	var vi = new Array();
            	$.each(datas, function(i, data){
            		var point = showPoint(data);
            		vi[vi.length] = point;
        		});
            	map.setViewport(vi);
            },
            error:function(){
            }
        });
	});
	
	$(window).resize(function(){
		$("#l-map").css("width", document.body.clientWidth - 290);
	});
	
	function showPoint(data){
		var POINT_ID = data["POINT_ID"];
		var POINT_NO = data["POINT_NO"];
		var POINT_NAME = data["POINT_NAME"];
		//var POINT_LEVEL = data["POINT_LEVEL"];
		var POINT_TYPE = data["POINT_TYPE"];
		var LONGITUDE = data["LONGITUDE"];
		var LATITUDE = data["LATITUDE"];
		var CREATE_TIME = data["CREATE_TIME"];
		var STAFF_NAME = data["STAFF_NAME"];
		var ADDRESS = data["ADDRESS"];
		var USERD_COUNT = data["USERD_COUNT"];
		
		if (typeof(ADDRESS) == 'undefined') {
			ADDRESS = "";
		}
		if (typeof(CREATE_TIME) == 'undefined') {
			CREATE_TIME = "";
		}
		
		var info = "<div class='map_bubble_info'>编码：" + POINT_NO
					+ "<br/>名称：" + POINT_NAME
					+ "<br/>地址：" + ADDRESS 
					+ "<br/>创建时间：" + CREATE_TIME 
					+ "<br/>创建者：" + STAFF_NAME 
					+ "</div>";
		var point = new BMap.Point(LONGITUDE, LATITUDE);
		//加入到要全部显示到地图的点
		pointList.push(point);
		var icon;
		if (USERD_COUNT > 0) {
			icon = userdIcon;
		} else {
			icon = fireIcon;
		}
		var marker = new BMap.Marker(point, {icon: icon});
		iconMap.put(POINT_ID, icon);//缓存图标
		title = "<div class='map_bubble_title'>" + POINT_NAME + "</div>";
		var label = new BMap.Label(POINT_NAME, {position:point,offset:new BMap.Size(0, 40)});
		label.setStyle({
			border : "0",
			color : "#FF1111",
			backgroundColor :"0.00"
		});
		marker.setLabel(label);
		map.addOverlay(marker); 
		var opts = {
				  width : 250,     // 信息窗口宽度
				  height: 0,     // 信息窗口高度
				  title : title, // 信息窗口标题
				  offset : new BMap.Size(0, -40),
				  enableMessage:false//设置允许信息窗发送短息
				};
		var infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
		marker.addEventListener("mouseover", function(){
			map.openInfoWindow(infoWindow,point);
		});
		marker.addEventListener("mouseout", function() {
			map.closeInfoWindow();
		});
		marker.addEventListener("click", function(){
			 addPoint(POINT_ID,POINT_NAME,POINT_NO, POINT_TYPE);
			 if(marker.getIcon() != checkedIcon){
				 marker.setIcon(checkedIcon);
			 }
		});
		markerMap.put(POINT_ID, marker);
		return point;
	}
	</script>
	<script type="text/javascript">
	function addPoint(point_id, point_name, point_no, point_type){
		var trId = "point_selected_" + point_id;
		if($("#" + trId).length > 0){
			$.messager.show({
				title : '提  示',
				msg : '该外力点 已存在!',
				showType : 'show'
			});
		} else{
			var trHtml = "<tr class='datagrid-cell' style='height:40px;' id='" + trId + "'>"
				+ "<td width='7%'>{index}</td>"
				+ "<td width='25%' title='" + point_name + "' nowrap='nowrap'>" + (point_name.length > 5 ? point_name.substring(0,5) : point_name) + "</td>"
				+ "<td width='30%' title='" + point_no + "' nowrap='nowrap'>" + (point_no.length > 5 ? point_no.substring(0,5) : point_no) + "</td>"
				+ "<td width='38%' align='right'><div class='btn-operation' onClick='deletePoint(\"" + trId + "\", \"" + point_id + "\");'>删除</div></td></tr>";
			cache.put(trId, trHtml);
			pointsSelected.put(point_id, point_type);
			showSelected();
		}
	};
	function deletePoint(trId, point_id){
		cache.remove(trId);
		pointsSelected.remove(point_id);
		showSelected();
		markerMap.get(point_id).setIcon(iconMap.get(point_id));
	}
	function showSelected(){
		var trHtml = "";
		cache.each(function(k,v,index){
			trHtml += v.replace("{index}", (index + 1));
	    });
		$("#show").html(trHtml);
	}
	
	function savePlan(){
		if(pointsSelected.length() == 0){
			$.messager.show({
				title : '提  示',
				msg : '请选择外力点!',
				showType : 'show'
			});
			return;
		}
		
		var plan_circle = $("select[name='plan_circle']").val();
		var plan_frequency = $("select[name='plan_frequency']").val();
		if(plan_circle == 2){
			if(weekNum > 0 && weekNum < plan_frequency){
				$.messager.show({
					title : '提  示',
					msg : '请按照计划次数添加检查时间!',
					showType : 'show'
				});
				return;
			}
		}else if(plan_circle == 3){
			if(monthNum > 0 && monthNum < plan_frequency){
				$.messager.show({
					title : '提  示',
					msg : '请按照计划次数添加检查时间!',
					showType : 'show'
				});
				return;
			}
		}
		if($("#ff").form('validate')){
			$.messager.confirm('系统提示', '您确定要保存该计划吗?', function(r) {
				if(r){
					var pointsValue = "";
					pointsSelected.each(function(k,v,index){
						pointsValue += k + ":" + v + ",";
					});
					var plan_name = $("input[name='plan_name']").val();
					var plan_no = $("input[name='plan_no']").val();
					var plan_type = $("select[name='plan_type']").val();
					var plan_start_time = $("input[name='plan_start_time']").val();
					var plan_end_time = $("input[name='plan_end_time']").val();
					var custom_time = "";
					if(plan_circle == 2){
						$("select[id='select_week']").each(function(i,v){
					     	 custom_time += $(this).val()+",";
					     });
					}else if(plan_circle == 3){
						$("select[id='select_month']").each(function(i,v){
					     	 custom_time += $(this).val()+",";
					     });
					}
					$.ajax({
						type : 'POST',
						url : webPath + "Care/plan_save.do",
						data : {
							points : pointsValue,
							plan_name : plan_name,
							plan_no : plan_no,
							plan_type : plan_type,
							plan_start_time : plan_start_time,
							plan_end_time : plan_end_time,
							plan_circle : plan_circle,
							plan_frequency : plan_frequency,
							plan_type : plan_type,
							plan_kind : plan_kind,
							custom_time : custom_time
						},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '保存计划成功!',
									showType : 'show'
								});
							}
							location.href = 'plan_index.do';
						}
					});
				}
			});
		}
	}
	function showPoints() {
		map.clearOverlays();//删除所有标记
		if(isAll){
			$.each(points, function(i, data){
				var point_id = data["POINT_ID"];
				if($("#point_selected_" + point_id).length > 0){
					showPoint(data);
				}
    		});
			isAll = false;
		} else {
			$.each(points, function(i, data){
        		showPoint(data);
    		});
			isAll = true;
		}
		
	};
	
	//改变计划次数，删除多余的自定义时间
	$("#plan_frequency").change(function(){
		var selfVal = $(this).val();
		if($("tr[id='tr_custom_week']").size()>selfVal){
		     $("tr[id='tr_custom_week']").each(function(i,v){
		     	 if(i >= selfVal){
		     	 	$(this).remove();
		     	 	weekNum--;
		     	 }
		     });
		}
		
		if($("tr[id='tr_custom_month']").size()>selfVal){
		     $("tr[id='tr_custom_month']").each(function(i,v){
		     	 if(i >= selfVal){
		     	 	$(this).remove();
		     	 	monthNum--;
		     	 }
		     });
		}
	});
	
	//计划周期，显示或隐藏自定义时间
	$("#plan_circle").change( function() {
		var self = $(this);
		var selfVal = self.val();
		if(selfVal == 2){
			$("#tbl1 tr").each(function(){
				$('#div_custom').show();
				if($(this).attr("id")=="tr_custom_week" || typeof($(this).attr("id"))=="undefined"){
					$(this).show();
				}else{
					$(this).hide();
				}
			});
		}else if(selfVal == 3){
			$('#div_custom').show();
			$("#tbl1 tr").each(function(){
				if($(this).attr("id")=="tr_custom_month"|| typeof($(this).attr("id"))=="undefined"){
					$(this).show();
				}else{
					$(this).hide();
				}
			});
		}else{
			$('#div_custom').hide();
			$("#tbl1 tr").each(function(){
				if(typeof($(this).attr("id"))=="undefined"){
					$(this).show();
				}else{
					$(this).hide();
				}
			});
		}
	});
	
	var weekNum = 0;
	var monthNum = 0;
	//点击添加自定义时间按钮
	$("#btn_custom_add").click(function(){
		var plan_circle = $("#plan_circle").val();
		var plan_frequency = $('#plan_frequency').val();
		//添加周时间
		if(plan_circle == 2){
			//判断自定义时间个数是否大于计划次数
			if(weekNum < plan_frequency){
				$('#tbl1').append("<tr id='tr_custom_week'><td id='title_area"+weekNum+"'></td><td id='time_area"+weekNum+"'></td></tr>");
				$("#tr_custom_week #title_area"+weekNum+"").append("巡检时间"+(weekNum+1)+"：");
				//创建select
				var select = $("<select id='select_week'  class='condition-select'></select>");
				select.append("<option value='1'>周一</option>");
				select.append("<option value='2'>周二</option>");
				select.append("<option value='3'>周三</option>");
				select.append("<option value='4'>周四</option>");
				select.append("<option value='5'>周五</option>");
				select.append("<option value='6'>周六</option>");
				select.append("<option value='7'>周日</option>");
				//把创建好的select加载到div中
				$("#tr_custom_week #time_area"+weekNum+"").html(select);
				weekNum++;
			}else{
				$.messager.show({
					title : '提  示',
					msg : '自定义时间个数不能大于计划次数!',
					showType : 'show'
				});
				return false;
			}
		//添加月时间
		}else if(plan_circle == 3){
			//判断自定义时间个数是否大于计划次数
			if(monthNum < plan_frequency){
				$('#tbl1').append("<tr id='tr_custom_month'><td id='title_area"+monthNum+"'></td><td id='time_area"+monthNum+"'></td></tr>");
				$("#tr_custom_month #title_area"+monthNum+"").append("巡检时间"+(monthNum+1)+"：");
				//创建select
				var select = $("<select id='select_month'  class='condition-select'></select>");
				for(var i=1;i<=30;i++){
					select.append("<option value='"+i+"'>"+i+"日</option>");
				}
				//把创建好的select加载到div中
				$("#tr_custom_month #time_area"+monthNum+"").html(select);
				monthNum++;
			}else{
				$.messager.show({
					title : '提  示',
					msg : '自定义时间个数不能大于计划次数!',
					showType : 'show'
				});
				return false;
			}
		}
	});
	
	//点击删除自定义时间按钮
	$("#btn_custom_delete").click(function(){
		var plan_circle = $("#plan_circle").val();
		if(plan_circle == 2){
			if($("tr[id='tr_custom_week']").size() >0){
				$("tr[id='tr_custom_week']").last().remove();
				weekNum--;
			}
		}else if(plan_circle == 3){
			if($("tr[id='tr_custom_month']").size() >0){
				$("tr[id='tr_custom_month']").last().remove();
				monthNum--;
			}
		}
	});
	//搜索检查点
	function queryPoint() {
		$.ajax({
			type: "POST", 
			dataType:"json",
			data : {
				pointName : $("input[name='pointName']").val().trim(),
				pointNo : $("input[name='pointNo']").val().trim(),
				pointType:2
			},
            url:webPath + "Care/plan_getPoints.do?type=2&area_id=" + area_id,
            success:function(datas) {
           		points = datas;
            	map.clearOverlays();//删除所有标记
            	if(datas.length<1)
            	{
            		$.messager.show({
						title : '提  示',
						msg : '没有检索到检查点!',
						showType : 'show'
					});
					return;
            	}
            	pointList.length = 0;
            	$.each(datas, function(i, data){
            		showPoint(data);
        		});
        		map.setViewport(pointList);
            },
            error:function(){
            }
        });
		$("#showPointquery").hide();
	}
	function hideQueryPoint() {
		$("#showPointquery").hide();
	}
	var pointList = new Array();
	</script>
</body>
</html>