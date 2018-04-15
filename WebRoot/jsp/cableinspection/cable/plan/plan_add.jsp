
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
		<script type="text/javascript"
			src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
		<title>计划新增</title>
	</head>
<body style="padding: 3px; border: 0px">
	
	<div id="l-map"></div>
	<div id="r-result">
		<div style="height: 35px; border-bottom: 1px solid #d2d2d2;;">
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="savePlan();">保存</div>
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="history.back();">返回</div>
		</div>
		<div style="border-bottom: 1px solid #d2d2d2; padding-left: 10px;" id="detail">
			<form id="ff">
				<table id="tbl1">
					<tr>
						<td>计划名称：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="plan_name"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
					<!--<tr>
						<td>计划编码：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="plan_no"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
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
									type="text" name=plan_start_time id="plan_start_time"
									required="true"
									onClick="WdatePicker({minDate:'%y-%M-{%d}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td>结束时间：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name=plan_end_time id="plan_end_time"
									required="true"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'plan_start_time\')}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td>计划周期：</td>
						<td>
								<select class="condition-select" name="plan_circle"
									id="plan_circle">
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
								<select class="condition-select" name="plan_frequency"
									id="plan_frequency">
									<option value='1'>1</option>
									<option value='2'>2</option>
									<option value='3'>3</option>
								</select>
						</td>
					</tr>
					<tr>
						<td>计划所属网格：</td>
						<td>
								<select class="condition-select" name="dept"
									id="dept">
									<option value="">请选择</option>
								<c:forEach items="${dept}" var="d">
										<option value="${d.DEPT_NO}">
											${d.DEPT_NAME}
										</option>
									</c:forEach>
								</select>
						</td>
					</tr>
					<tr>
						<td>是否进入集约化后台评估</td>
						<td>
								<select class="condition-select" name="sendType"
									id="sendType">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
						</td>
					</tr>
					<!--  
					<tr>
						<td>区域：</td>
						<td>
								<select class="condition-select" name="son_area"
									id="son_area">
								<c:forEach items="${son_area}" var="a">
										<option value="${a.AREA_ID}">
											${a.NAME}
										</option>
									</c:forEach>
								</select>
						</td>
					</tr>
					-->
				</table>
			</form>
		</div>
		<div id="div_custom" style="border-bottom: 1px solid #d2d2d2; overflow: auto;display:none;">
				<div id="btn_custom_add" class="btn-operation">添加自定义时间</div>
				<div id="btn_custom_delete" class="btn-operation">删除自定义时间</div>
		</div>
		<div style="border-bottom: 1px solid #d2d2d2; padding-left: 5px; display: none; background-color: white;"
			id="showCableQuery">
			<table>
				<tr class="queryWell">
					<td>缆线名称</td>
					<td><div class="condition-text-container">
							<input name="cable_name" type="text" class="condition-text" />
						</div></td>
				</tr>
				<tr class="searchEqp">
					<td>缆线编码</td>
					<td><div class="condition-text-container">
							<input name="cable_no" type="text" class="condition-text" />
						</div>
					</td>
				</tr>
				<tr>
						<td>网格：</td>
						<td>
								<select class="condition-select" name="dept_s"
									id="dept_s">
									<option value="">请选择</option>
								<c:forEach items="${dept}" var="d">
										<option value="${d.DEPT_NO}">
											${d.DEPT_NAME}
										</option>
									</c:forEach>
								</select>
						</td>
					</tr>
					<tr>
						<td>区域：</td>
						<td>
								<select class="condition-select" name="son_area_s"
									id="son_area_s">
									<option value="">请选择</option>
								<c:forEach items="${son_area}" var="a">
										<option value="${a.AREA_ID}">
											${a.NAME}
										</option>
									</c:forEach>
								</select>
						</td>
					</tr>
				<tr>
					<td><div class="btn-operation" onClick="searCable();">
							搜索</div></td>
				</tr>
			</table>
		</div>
		<div
			style="border-bottom: 1px solid #d2d2d2; overflow: auto;" id="lines">
			<div>
				<table class="datagrid-htable"
					style="height: 41px; table-layout: fixed;" cellSpacing=0
					cellPadding=0 border=0 id="show">
				</table>
			</div>
		</div>
	</div>
	<input id="areaName" type="hidden" value="${AREA_NAME}" />
</body>
</html>
<script type="text/javascript">
$("#showCableQuery").hide();
$("#l-map").css("width", document.body.clientWidth - 290);
$("#lines").css("max-height", $("#l-map").height() - $("#detail").height() - 45);
// 百度地图API功能
var areaname=$("#areaName").val().trim();
// 百度地图API功能
var map = new BMap.Map("l-map");
if(areaname!=null){
map.centerAndZoom(areaname, 15);
}else{
map.centerAndZoom("南京", 15);
}
map.enableScrollWheelZoom(); //启用滚轮放大缩小
var cache = new CacheMap();
var pointsSelected = new CacheMap();
var polylineMap = new CacheMap();//缓存polyline
var colorMap = new CacheMap();//缓存polyline的颜色
var points;
//记录所选缆线的点数量
var pointNum;
var isAll = true;
$(window).resize(function(){
	$("#l-map").css("width", document.body.clientWidth - 290);
	$("#lines").css("max-height", $("#l-map").height() - $("#detail").height() - 45);
});

function clearAll() {
    for(var i = 0; i < overlays.length; i++){
        map.removeOverlay(overlays[i]);
    }
    overlays.length = 0
};
	//搜索控件
	function searchControl(){
		this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	    this.defaultOffset = new BMap.Size(20, 20);
	}
	searchControl.prototype = new BMap.Control();
	searchControl.prototype.initialize = function(map){
		var div = document.createElement("div");
		var img = document.createElement("img");
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
				$("#showCableQuery").show();
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
		var div = document.getElementById("showCableQuery");
		map.getContainer().appendChild(div);
		$("#showCableQuery").show();
		return div;
	};
	//将控件加入地图
	map.addControl(new searchControl());
$.ajax({
	url:"getCable.do",
   	contentType:"application/json",
    type:"get",
    dataType:"json",
    //data:{"line_id":lineId},
    success:function(datas){
    $.each(datas, function(i, data){
    	var pointList=data.pointMode;
    	var linePoints = new Array();
    	$.each(pointList,function(j,points1){
    		linePoints[linePoints.length] = new BMap.Point(points1.latitude,points1.longitude);
    		//alert(points.longitude+":"+points.latitude);
    	});
    	var color = "gray";
		if(data.isExistPlan !=0){
			if(data.lineLevel == 1) {
				color = "#FF0000";
			} 
			if(data.lineLevel == 2) {
				color = "#1739C7";
			} else if(data.lineLevel == 3) {
				color = "#28833A";
			}
		}
    	var polyline1 = new BMap.Polyline(linePoints, {strokeColor:color, strokeWeight:6, strokeOpacity:0.8});
    	map.addOverlay(polyline1);
	  	var info = "<div class='map_bubble_info'>缆线ID：" + data.lineId
        						+ "<br/>缆线编码：" + data.lineNo
        						+ "<br/>缆线名称：" + data.lineName 
        						+ "<br/>创建时间：" + data.createTime 
        						+ "<br/>创建者：" + data.createStaff 
        						+ "</div>";
       	var title = "<div class='map_bubble_title'>" + data.lineName + "</div>";
        var opts = {
        	width : 250,     // 信息窗口宽度
        	height: 0,     // 信息窗口高度
        	title : title, // 信息窗口标题
        	offset : new BMap.Size(0, -20),
        	enableMessage:false//设置允许信息窗发送短息
        	};
        var infoWindow = new BMap.InfoWindow(info, opts);
        polyline1.addEventListener("click", function(e){
        	polyline1.setStrokeColor("#000000");
        	addLine(data.lineId,data.lineNo,data.lineName,linePoints.length);
        	});
        polyline1.addEventListener("mouseover", function(e){
        	var point = new BMap.Point(e.point.lng,e.point.lat);
        	map.openInfoWindow(infoWindow,point);
        });
		polyline1.addEventListener("mouseout", function(e){
			map.closeInfoWindow();
        });
        polylineMap.put(data.lineId, polyline1);
        colorMap.put(data.lineId, polyline1.getStrokeColor());
    });
    }
});

  function addLine(point_id, point_name, point_no,point_sum){
		var trId = "point_selected_" + point_id;
		if($("#" + trId).length > 0){
			$.messager.show({
				title : '提  示',
				msg : '该光缆段已存在!',
				showType : 'show'
			});
			
		}else if(pointsSelected.length()>4){
			$.messager.show({
				title : '提  示',
				msg : '最多选择五条缆线',
				showType : 'show'
			});
			return;
		}
		/*else if(pointsSelected.size()>2&&pointNum>500){
			$.messager.show({
				title : '提  示',
				msg : '所有已选择的缆线中点数量不能超过500',
				showType : 'show'
			});
		}*/
		else{
			pointNum+=point_sum;
			var trHtml = "<tr class='datagrid-cell' style='height:40px;' id='" + trId + "'>"
				+ "<td width='7%'>{index}</td>"
				+ "<td width='25%' title='" + point_name + "' nowrap='nowrap'>" + point_name + "</td>"
				+ "<td width='30%' title='" + point_no + "' nowrap='nowrap'>" + point_no + "</td>"
				+ "<td width='38%' align='right'><div class='btn-operation' onClick='deletePoint(\"" + trId + "\", \"" + point_id + "\",\""+point_sum+"\");'>删除</div></td></tr>";
			cache.put(trId, trHtml);
			pointsSelected.put(point_id, 2);
			showSelected();
		}
	};
	
function showSelected(){
		var trHtml = "";
		cache.each(function(k,v,index){
			trHtml += v.replace("{index}", (index + 1));
	    });
		$("#show").html(trHtml);
	}	
	
function deletePoint(trId, point_id,point_sum){
		pointNum=pointNum-point_sum
		cache.remove(trId);
		pointsSelected.remove(point_id);
		showSelected();
		polylineMap.get(point_id).setStrokeColor(colorMap.get(point_id));
	}	
  
function savePlan(){
		if(pointsSelected.length() == 0){
			$.messager.show({
				title : '提  示',
				msg : '请为任务指定光缆!',
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
					var lines = "";
					pointsSelected.each(function(k,v,index){
						lines += k + ",";
					});
					var plan_name = $("input[name='plan_name']").val();
					var plan_no = $("input[name='plan_no']").val();
					var plan_type = $("select[name='plan_type']").val();
					var plan_start_time = $("input[name='plan_start_time']").val();
					var plan_end_time = $("input[name='plan_end_time']").val();
					var dept_no=$("select[name='dept']").val();
					var son_area_id=$("select[name='son_area']").val();
					var sendType = $("select[name='sendType']").val();
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
					if(son_area_id==""||son_area_id==undefined){
						$.messager.show({
									title : '提  示',
									msg : '请选择区域',
									showType : 'show'
								});
					}
					$.ajax({
						type : 'POST',
						url : webPath + "CablePlan/plan_save.do",
						data : {
							lines : lines,
							plan_name : plan_name,
							plan_no : plan_no,
							plan_type : plan_type,
							plan_start_time : plan_start_time,
							plan_end_time : plan_end_time,
							plan_circle : plan_circle,
							plan_frequency : plan_frequency,
							custom_time : custom_time,
							dept_no:dept_no,
							son_area_id:son_area_id,
							sendType:sendType
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
							//parent.$("#tabs").tabs("select", "计划管理");
							//closeTab();
							location.href = 'index.do';
						}
					});
				}
			});
		}
	}
	
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
function searCable(){
var cable_no=encodeURIComponent($("input[name='cable_no']").val());
var cable_name=encodeURIComponent($("input[name='cable_name']").val());
var son_area_id=encodeURIComponent($("select[name='son_area_s']").val());
var dept_no=encodeURIComponent($("select[name='dept_s']").val());
$.ajax({
	url:webPath + "Cable/getCable.do",
   	contentType:"application/json",
    type:"get",
    dataType:"json",
    data:{cable_no:cable_no,
    		cable_name:cable_name,
    		son_area_id:son_area_id,
    		dept_no:dept_no
    		},
    success:function(datas){
    if(datas.length<1){
    $.messager.show({
					title : '提  示',
					msg : '未找到缆线',
					showType : 'show'
				});
	return;
    }
    map.clearOverlays();
    $.each(datas, function(i, data){
    	var pointList=data.pointMode;
    	var linePoints = new Array();
    	$.each(pointList,function(j,points1){
    		linePoints[linePoints.length] = new BMap.Point(points1.latitude,points1.longitude);
    		//alert(points.longitude+":"+points.latitude);
    	});
    	var color = "gray";
		if(data.isExistPlan !=0){
			if(data.lineLevel == 1) {
				color = "#FF0000";
			} 
			if(data.lineLevel == 2) {
				color = "#1739C7";
			} else if(data.lineLevel == 3) {
				color = "#28833A";
			}
		}
    	var polyline1 = new BMap.Polyline(linePoints, {strokeColor:color, strokeWeight:6, strokeOpacity:0.8});
    	map.addOverlay(polyline1);
	  	var info = "<div class='map_bubble_info'>缆线ID：" + data.lineId
        						+ "<br/>缆线编码：" + data.lineNo
        						+ "<br/>缆线名称：" + data.lineName 
        						+ "<br/>创建时间：" + data.createTime 
        						+ "<br/>创建者：" + data.createStaff 
        						+ "</div>";
       	var title = "<div class='map_bubble_title'>" + data.lineName + "</div>";
        var opts = {
        	width : 250,     // 信息窗口宽度
        	height: 0,     // 信息窗口高度
        	title : title, // 信息窗口标题
        	offset : new BMap.Size(0, -20),
        	enableMessage:false//设置允许信息窗发送短息
        	};
        var infoWindow = new BMap.InfoWindow(info, opts);
        polyline1.addEventListener("click", function(e){
        	polyline1.setStrokeColor("#000000");
        	addLine(data.lineId,data.lineNo,data.lineName);
        	});
        polyline1.addEventListener("mouseover", function(e){
        	var point = new BMap.Point(e.point.lng,e.point.lat);
        	map.openInfoWindow(infoWindow,point);
        });
		polyline1.addEventListener("mouseout", function(e){
			map.closeInfoWindow();
        });
        polylineMap.put(data.lineId, polyline1);
        colorMap.put(data.lineId, polyline1.getStrokeColor());
    });
    }
});
$("#showCableQuery").hide();
}
</script>
