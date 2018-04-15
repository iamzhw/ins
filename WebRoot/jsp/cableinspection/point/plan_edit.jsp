<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>修改外力点看护</title>
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
<input type="hidden" name="plan_id" id="plan_id" value="${planInfo.PLAN_ID}"/>
<input type="hidden" name="plan_circle" id="plan_circle" value="${planInfo.PLAN_CIRCLE}"/>
<input type="hidden" name="plan_frequency" id="plan_frequency" value="${planInfo.PLAN_FREQUENCY}"/>
<input type="hidden" name="plan_type" id="plan_type" value="${planInfo.PLAN_TYPE}"/>
<input type="hidden" name="plan_kind" id="plan_kind" value="${planInfo.PLAN_KIND}"/>
<input type="hidden" name="area_id" id="area_id" value="${area_id}"/>
<input type="hidden" name="longitude" id="longitude" value="${longitude}"/>
<input type="hidden" name="latitude" id="latitude" value="${latitude}"/>
<input type="hidden" name="custom_time" id="custom_time" value="${planInfo.CUSTOM_TIME}" />
		<div id="l-map"></div>
		<div id="r-result">
			<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
				<div class="btn-operation" onClick="updatePlan();">更新</div>
				<div class="btn-operation" onClick="history.back();">返回</div>
				<!-- <div class="btn-operation" onClick="showPoints();">显示</div> -->
			</div>
			<div style="border-bottom: 1px solid #d2d2d2; padding: 10px;">
			<form id="ff">
				<table id="tbl1">
				<tr>
					<td>计划名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="plan_name" value="${planInfo.PLAN_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>计划编码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="plan_no" value="${planInfo.PLAN_NO}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>计划类型：</td>
					<td>
							<select class="condition-select" name="plan_type" id="plan_type">
								<option value='0' selected="selected">周期性</option>	
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
								onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});" value="${planInfo.PLAN_START_TIME}"/>
						</div></td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition" value="${planInfo.PLAN_END_TIME}"
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
			<div style="height: auto; overflow: auto;">
				<div>
					<table class="datagrid-htable" style="height:41px;table-layout:fixed;" cellSpacing=0 cellPadding=0 border=0 id="show">
					</table>
				</div>
			</div>
		</div>
	<script type="text/javascript">
	var plan_kind = $("input[name='plan_kind']").val();//计划类型
	var area_id = $("input[name='area_id']").val();//地区
	var longitudeInit = $("input[name='longitude']").val();//经度
	var latitudeInit = $("input[name='latitude']").val();//纬度
	var cache = new CacheMap();
	var pointsSelected = new CacheMap();
	var map = new BMap.Map("l-map");
	var points;
	var isAll = true;
	var weekNum=0;
	var monthNum=0;
	//加载百度地图
	map.centerAndZoom('${AREA_NAME}', 14);
	$("#l-map").css("width", document.body.clientWidth - 290);
	$(window).resize(function(){
		$("#l-map").css("width", document.body.clientWidth - 290);
	});
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	
	$(function(){
		//获取已选择外力点
		$.ajax({
			type: "GET", 
			dataType:"json",
            url:webPath + "Care/plan_getPlanDetail.do?plan_id=" + $("#plan_id").val(),
            success:function(datas) {
            	$.each(datas, function(i, data){
            		var point_id = data["POINT_ID"];
            		var point_no = data["POINT_NO"];
            		var point_name = data["POINT_NAME"];
            		var point_type = data["POINT_TYPE"];
            		var trId = "point_selected_" + point_id;
            		var trHtml = "<tr class='datagrid-cell' style='height:40px;' id='" + trId + "'>"
    				+ "<td width='7%'>{index}</td>"
    				+ "<td width='25%' title='" + point_name + "' nowrap='nowrap'>" + point_name + "</td>"
    				+ "<td width='30%' title='" + point_no + "' nowrap='nowrap'>" + point_no + "</td>"
    				+ "<td width='38%' align='right'><div class='btn-operation' onClick='deletePoint(\"" + trId + "\", \"" + point_id + "\");'>删除</div></td></tr>";
    				cache.put(trId, trHtml);
    				pointsSelected.put(point_id, point_type);
            	});
            	showSelected();
            }
		});
		//初始化数据
		$("select[name='plan_type']").val($("#plan_type").val());
		$("select[name='plan_circle']").val($("#plan_circle").val());
		$("select[name='plan_frequency']").val($("#plan_frequency").val());
		
		var custom_time = $("#custom_time").val();
		var timeArray = custom_time.split(",");
		if($("#plan_circle").val() == 2){
			for(var i=0;i<timeArray.length-1;i++){
				$('table').append("<tr id='tr_custom_week'><td id='title_area"+i+"'></td><td id='time_area"+i+"'></td></tr>");
				$("#tr_custom_week #title_area"+i+"").append("巡检时间"+(i+1)+"：");
				//创建select
				var select = $("<select id='select_week"+i+"'  class='condition-select'></select>");
				select.append("<option value='周一'>周一</option>");
				select.append("<option value='周二'>周二</option>");
				select.append("<option value='周三'>周三</option>");
				select.append("<option value='周四'>周四</option>");
				select.append("<option value='周五'>周五</option>");
				select.append("<option value='周六'>周六</option>");
				select.append("<option value='周日'>周日</option>");
				//把创建好的select加载到div中
				$("#tr_custom_week #time_area"+i+"").html(select);
				$('#select_week'+i+'').val(timeArray[i]);
				weekNum++;
			}
			$('#div_custom').show();
		}else if($("#plan_circle").val() == 3){
			for(var i=0;i<timeArray.length-1;i++){
				$('table').append("<tr id='tr_custom_month'><td id='title_area"+i+"'></td><td id='time_area"+i+"'></td></tr>");
				$("#tr_custom_month #title_area"+i+"").append("巡检时间"+(i+1)+"：");
				//创建select
				var select = $("<select id='select_month"+i+"'  class='condition-select'></select>");
				for(var j=1;j<=30;j++){
					select.append("<option value='"+j+"日'>"+j+"日</option>");
				}
				//把创建好的select加载到div中
				$("#tr_custom_month #time_area"+i+"").html(select);
				$('#select_month'+i+'').val(timeArray[i]);
				monthNum++;
			}
			$('#div_custom').show();
		}
		
		//获取所有外力点
		$.ajax({
			type: "GET", 
			dataType:"json",
            url:webPath + "Care/plan_getPoints.do?type=2&area_id=" + area_id,
            success:function(datas) {
            	points = datas;
            	$.each(datas, function(i, data){
        			showPoint(data);
        		});
            },
            error:function(){
            }
        });
	});
	function showPoint(data){
		var greenIcon = new BMap.Icon(webPath + "images/icon_sign.png", new BMap.Size(23, 25));
		var POINT_ID = data["POINT_ID"];
		var POINT_NO = data["POINT_NO"];
		var POINT_NAME = data["POINT_NAME"];
		//var POINT_LEVEL = data["POINT_LEVEL"];
		var POINT_TYPE = data["POINT_TYPE"];
		var LONGITUDE = data["LONGITUDE"];
		var LATITUDE = data["LATITUDE"];
		var CREATE_TIME = data["CREATE_TIME"];
		var STAFF_NAME = data["STAFF_NAME"];
		//var radius = data["radius"];
		
		var info = "<div class='map_bubble_info'>编码：" + POINT_NO
					+ "<br/>经度：" + LONGITUDE
					+ "<br/>纬度：" + LATITUDE 
					+ "<br/>创建时间：" + CREATE_TIME 
					+ "<br/>创建者：" + STAFF_NAME 
					+ "</div>";
		var point = new BMap.Point(LONGITUDE, LATITUDE);
		var marker;
		
		if(POINT_TYPE == 2){//普通隐患点
			marker = new BMap.Marker(point, {icon: greenIcon});
		} else {
			marker = new BMap.Marker(point);
		}
		title = "<div class='map_bubble_title'>" + POINT_NAME + "</div>";
		
		map.addOverlay(marker); 
		var opts = {
				  width : 250,     // 信息窗口宽度
				  height: 0,     // 信息窗口高度
				  title : title, // 信息窗口标题
				  enableMessage:false//设置允许信息窗发送短息
				};
		var infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
		marker.addEventListener("mouseover", function(){
			map.openInfoWindow(infoWindow,point);
		});
		marker.addEventListener("click", function(){
		  if(plan_kind == 2 && POINT_TYPE == 2){
			  addPoint(POINT_ID,POINT_NAME,POINT_NO, POINT_TYPE);
		  } else if(plan_kind == 3 && POINT_TYPE == 3){
			  addPoint(POINT_ID,POINT_NAME,POINT_NO, POINT_TYPE);
		  }
		});
	}
	</script>
	<script type="text/javascript">
	function clearAll() {
	    for(var i = 0; i < overlays.length; i++){
	        map.removeOverlay(overlays[i]);
	    }
	    overlays.length = 0;
	};
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
				+ "<td width='25%' title='" + point_name + "' nowrap='nowrap'>" + point_name + "</td>"
				+ "<td width='30%' title='" + point_no + "' nowrap='nowrap'>" + point_no + "</td>"
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
	}
	function showSelected(){
		var trHtml = "";
		cache.each(function(k,v,index){
			trHtml += v.replace("{index}", (index + 1));
	    });
		$("#show").html(trHtml);
	}
	
	function updatePlan(){
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
			if(weekNum < plan_frequency){
				$.messager.show({
					title : '提  示',
					msg : '请按照计划次数添加检查时间!',
					showType : 'show'
				});
				return;
			}
		}else if(plan_circle == 3){
			if(monthNum < plan_frequency){
				$.messager.show({
					title : '提  示',
					msg : '请按照计划次数添加检查时间!',
					showType : 'show'
				});
				return;
			}
		}
		
		if($("#ff").form('validate')){
			$.messager.confirm('系统提示', '您确定要修改该计划吗?', function(r) {
				if(r){
					var pointsValue = "";
					pointsSelected.each(function(k,v,index){
						pointsValue += k + ":" + v + ",";
					});
					var plan_id = $("input[name='plan_id']").val();
					var plan_name = $("input[name='plan_name']").val();
					var plan_no = $("input[name='plan_no']").val();
					var plan_type = $("select[name='plan_type']").val();
					var plan_start_time = $("input[name='plan_start_time']").val();
					var plan_end_time = $("input[name='plan_end_time']").val();
					var custom_time = "";
					if(plan_circle == 2){
						$("select[id^='select_week']").each(function(i,v){
					     	 custom_time += $(this).val()+",";
					     });
					}else if(plan_circle == 3){
						$("select[id^='select_month']").each(function(i,v){
					     	 custom_time += $(this).val()+",";
					     });
					}
					$.ajax({
						type : 'POST',
						url : webPath + "Care/plan_update.do",
						data : {
							plan_id : plan_id,
							points : pointsValue,
							plan_name : plan_name,
							plan_no : plan_no,
							plan_type : plan_type,
							plan_start_time : plan_start_time,
							plan_end_time : plan_end_time,
							plan_circle : plan_circle,
							plan_frequency : plan_frequency,
							custom_time:custom_time
						},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '修改计划成功!',
									showType : 'show'
								});
							}
							//location.href = 'plan_index.do';
							closeTab();
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
	$("select[id=plan_frequency]").change(function(){
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
	$("select[id=plan_circle]").change( function() {
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
	//点击添加自定义时间按钮
	$("#btn_custom_add").click(function(){
		var plan_circle = $("select[id=plan_circle]").val();
		var plan_frequency = $('select[id=plan_frequency]').val();
		//添加周时间
		if(plan_circle == 2){
			//判断自定义时间个数是否大于计划次数
			if(weekNum < plan_frequency){
				$('#tbl1').append("<tr id='tr_custom_week'><td id='title_area"+weekNum+"'></td><td id='time_area"+weekNum+"'></td></tr>");
				$("#tr_custom_week #title_area"+weekNum+"").append("巡检时间"+(weekNum+1)+"：");
				//创建select
				var select = $("<select id='select_week'  class='condition-select'></select>");
				select.append("<option value='2'>周一</option>");
				select.append("<option value='3'>周二</option>");
				select.append("<option value='4'>周三</option>");
				select.append("<option value='5'>周四</option>");
				select.append("<option value='6'>周五</option>");
				select.append("<option value='7'>周六</option>");
				select.append("<option value='1'>周日</option>");
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
		var plan_circle = $("select[id=plan_circle]").val();
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
	</script>
</body>
</html>