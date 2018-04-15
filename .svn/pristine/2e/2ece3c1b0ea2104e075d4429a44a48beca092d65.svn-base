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
	
<title>非地标步巡段修改</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<div style="border-bottom: 1px solid #d2d2d2; padding:10px;">
		<form id="ff">
			<input type="hidden" name="inspact_id" id="inspact_id" />
			<table id="tbl1">
				<tr style="display: none">
					<td>步巡段id：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								type="text" id="allot_id" name="allot_id" value="${allot_id}"
								readonly="true" />
						</div></td>
				</tr>
				<tr style="display: none">
					<td>当前巡检人：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								type="text" id="old_inspect_id" name="old_inspect_id" value="${old_inspect_id}"
								readonly="true" />
						</div></td>
				</tr>
				<tr>
					<td>步巡段名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								type="text" id="steppart_name" name="steppart_name" value="${steppart_name}"
								onChange="judgeOnlyStepPartName()" />
						</div></td>
				</tr>
				<tr>
					<td>起点：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								type="text" id="start_equip" name="start_equip" value="${start_equip}"
								readonly="true" />
						</div></td>
				</tr>
				<tr>
					<td>终点：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								type="text" id="end_equip" name="end_equip" value="${end_equip}"
								readonly="true" />
						</div></td>
				</tr>
				<tr>
					<td>频次：</td>
					<td>
						<div>
							<select name="circle_id" id="circle_id" class="condition-select" onChange="judgeCircle()">
<!-- 								<c:if test="${empty circle_id}"> -->
<!-- 							      <option value="">请选择</option> -->
<!-- 								</c:if> -->
<!-- 								<c:if test="${not empty circle_id}">   -->
								  <option value="${circle_id}">${circle_id}个月</option>
<!-- 								</c:if> -->
<!-- 								<option value="1">1个月</option> -->
<!-- 								<option value="2">2个月</option> -->
<!-- 								<option value="3">3个月</option> -->
<!-- 								<option value="6">半年</option> -->
<!-- 								<option value="12">一年</option> -->
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>巡线人：</td>
					<td>
					<select name="inspect_id" id="inspect_id" class="condition-select condition" 
					 data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">	        
					        <c:if test="${not empty staff_id}">  
								 <option value="${staff_id}">当前人员 ：${staff_name}</option>
							</c:if>
							<c:forEach items="${inspects}" var="al">
								<option value="${al.STAFF_ID}">${al.STAFF_NAME}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>光缆名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								type="text" id="cable_name" name="cable_name"
								value="${cable_name}" readonly="true" />
						</div>
					</td>
				</tr>
				<tr>
					<td>中继段名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								type="text" id="relay_name" name="relay_name"
								value="${relay_name}" readonly="true" />
						</div></td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height: 40px;">
		<div style="margin-left: 5px;margin-top:5px;" class="btn-operation"
			onClick="resultListToCommit();">保存步巡段</div>
		<div style="margin-left: 5px;margin-top:5px;" class="btn-operation"
			onClick="back();">返回</div>
	</div>
	<script type="text/javascript">
	var is_equip_list = eval(${is_equip_list});
	var relay_id = ${relay_id};
	var cable_id = ${cable_id};
	var trunkcircle= ${trunkcircle};
	
	
	// 百度地图API功能
	$("#l-map").css("width", document.body.clientWidth - 300);
	var map = new BMap.Map("l-map");
	
	var checkedIcon1 = new BMap.Icon(webPath + "images/guanjian.png", 
			new BMap.Size(25, 30), {anchor: new BMap.Size(12.5, 30), infoWindowAnchor: new BMap.Size(14, 0)});
	var checkedIcon2 = new BMap.Icon(webPath + "images/feiguanjian.png", 
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
	map.centerAndZoom(new BMap.Point(is_equip_list[0].LONGITUDE, is_equip_list[0].LATITUDE), 15);  // 初始化地图,设置中心点坐标和地图级别
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
	var resultEquipList=[];


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
	
	//判断是否唯一的步巡段名称
	function judgeOnlyStepPartName(){
		var steppart_name = $("input[name='steppart_name']").val();
    	$.ajax({
			url : webPath + "/StepPart/judgeOnlyStepPartName.do",// 跳转到 action    
			data : {steppart_name:steppart_name},
			type : 'post',
			cache : false,
			dataType : 'json',
// 			async: false,
			success : function(data) {
				if(data.status){
					var end_equip=$("#end_equip").val();
					var relay_name=$("#relay_name").val();
					var strat_equip=$("#start_equip").val();
					$.messager.alert("提示","步巡段名称不能重复");
					$("#steppart_name").val(relay_name+":"+strat_equip+"-"+end_equip);
				}else{
					
				}
			}
		});
    }	


	//选取起点的方法
	var selStartEquip = function(e,ee,marker){
		var end_equip=$("#end_equip").val();
		var relay_name=$("#relay_name").val();
		var cable_name=$("#cable_name").val();
		if(end_equip == marker.equip_id){
		$.messager.alert("提示","起点和终点不能一样");
		}else{
	    $("#start_equip").val(marker.equip_id);
		}
		if(end_equip == "" || end_equip == marker.equip_id){
			
		}else{
		$("#steppart_name").val(cable_name+"/"+relay_name+":"+marker.equip_id+"-"+end_equip);
		}
	}
	
	//选取终点的方法
	var selEndEquip = function(e,ee,marker){
	    var strat_equip=$("#start_equip").val();
	    var relay_name=$("#relay_name").val();
	    var cable_name=$("#cable_name").val();
	    if(strat_equip == marker.equip_id){
			$.messager.alert("提示","终点和起点不能一样");	
		 }else{
			$("#end_equip").val(marker.equip_id);
		 }
		  if(strat_equip == "" || strat_equip == marker.equip_id){
			
		 }else{
		    $("#steppart_name").val(cable_name+"/"+relay_name+":"+strat_equip+"-"+marker.equip_id);
	     }
	}
	
	function judgeCircle(){
		var circle_id=$("#circle_id").val();
		if(circle_id>trunkcircle){
		    $.messager.alert("提示","所选周期不能大于区域干线频次");
		    $("#circle_id").val(trunkcircle);
		}
	}

	$(is_equip_list).each(function(){
		var point = new BMap.Point(Number(this.LONGITUDE), Number(this.LATITUDE));
		
		if(this.EQUIP_TYPE==1){
		  if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:markStone});  // 创建标石
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:markStone2});  // 创建标石
		  }	
		}else if(this.EQUIP_TYPE==2){
		   if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:wells});  // 创建人井
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:wells2});  // 创建人井
		  }	  	 
		}else if(this.EQUIP_TYPE==3){
		  if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:landMark});  // 创建地标
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:landMark2});  // 创建地标
		  }	  
		}else if(this.EQUIP_TYPE==4){
		  if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:billBoards});  // 创建宣传牌
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:billBoards2});  // 创建宣传牌
		  }	
		}else if(this.EQUIP_TYPE==5){
		  if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:buriedDepthSpot});  // 创建埋深点
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:buriedDepthSpot2});  // 创建埋深点
		  }
		}else if(this.EQUIP_TYPE==6){
		  if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:Pole});  // 创建电杆
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:Pole2});  // 创建电杆
		  }
		}else if(this.EQUIP_TYPE==7){
		  if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:WarningSign});  // 创建警示牌
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:WarningSign2});  // 创建警示牌
		  }
		}else if(this.EQUIP_TYPE==8){
		  if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:slopeProtection});  // 创建护坡
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:slopeProtection2});  // 创建护坡
		  }
		}else if(this.EQUIP_TYPE==9){
		 if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:JointBox});  // 创建接头盒
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:JointBox2});  // 创建接头盒
		  }
		}else{
		  if(this.IS_EQUIP == 1 && this.IS_PART==0){
			 var marker = new BMap.Marker(point,{icon:checkedIcon1});  // 创建标注
		  }else{
		 	 var marker = new BMap.Marker(point,{icon:checkedIcon2});  // 创建标注
		  }
		}
		$(marker).attr("equip_id",this.EQUIP_ID);
		$(marker).attr("equip_type",this.EQUIP_TYPE);
		$(marker).attr("is_equip",this.IS_EQUIP);
		$(marker).attr("is_part",this.IS_PART);
		$(marker).attr("order",this.ORDER);
		map.addOverlay(marker);              // 将标注添加到地图中
		
		//修改时所有的设施点都给加上右键菜单
		if(this.IS_EQUIP == 1){
		var markerMenu=new BMap.ContextMenu();
		markerMenu.addItem(new BMap.MenuItem('选取起点',selStartEquip.bind(marker)));
		markerMenu.addItem(new BMap.MenuItem('选取终点',selEndEquip.bind(marker)));
		marker.addContextMenu(markerMenu);
		}
		
		
		var content = "设施id:"+this.EQUIP_ID+"<br/>编号:"+this.EQUIP_CODE+"<br/>描述："+this.DESCRIPTION+
		"<br/>排序："+this.ORDER+"<br/>是否路由:"+this.IS_EQUIP;
		addHandler(content,marker);
		points.push(point);
		$("#dg").append("<tr><td>"+this.EQUIP_ID+"</td><td>"+this.LONGITUDE+","+this.LATITUDE+"</td></tr>");
	});
	
		
	var polyline = new BMap.Polyline(points,    
     {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5}    
    );    
    map.addOverlay(polyline);
    
    
    function resultListToCommit(){
    	var pass = $("#ff").form('validate');
    	var allot_id=$("input[name='allot_id']").val();
    	var steppart_name = $("input[name='steppart_name']").val();
		var start_equip = $("input[name='start_equip']").val();
		var end_equip = $("input[name='end_equip']").val();
		var circle_id = $("select[name='circle_id']").val();
		var inspect_id = $("select[name='inspect_id']").val();
		var cable_name = $("input[name='cable_name']").val();
		var relay_name = $("input[name='relay_name']").val();
		var old_inspect_id = $("input[name='old_inspect_id']").val();
		var is_change_persion = 1;//默认改变
		if(inspect_id == old_inspect_id){//如果人员变更传回去1
		  is_change_persion = 0;
		}else{
		  is_change_persion = 1;
		}
		
        if (pass) {
         if(steppart_name != "" && start_equip !="" && end_equip !="" &&circle_id !="" &&  inspect_id !=""){
          $.ajax({
	      url : webPath + "/StepPart/judgeIsTaskEquip.do",// 跳转到 action    
	      data : {
	        strat_equip  :start_equip,
	        cable_id     :cable_id,
	        relay_id     :relay_id,
	        end_equip    :end_equip,
	        allot_id     :allot_id
	            },
		      type : 'post',
		      cache : false,
		      dataType : 'json',
		      async: false,
		      success : function(data) {
		        if(data.status){
		         	$.messager.confirm('系统提示', '您确定要更新信息點信息吗?', function(r) {
					if (r) {
						$.ajax({
							type : 'POST',
							url : webPath + "/StepPart/upSaveStepPart.do",
							data : {
								allot_id     :allot_id,
								steppart_name:steppart_name,
								start_equip : start_equip,
								end_equip : end_equip,
								circle_id : circle_id,
								inspect_id : inspect_id,
								cable_name : cable_name,
								relay_name : relay_name,
								cable_id : cable_id,
								relay_id : relay_id,
								is_change_persion:is_change_persion
							},
							dataType : 'json',
							success : function(data) {
							  if(data.status){
								$.messager.alert("提示","保存成功");
								back();
							  }else{
								$.messager.alert("提示","保存失敗");
							  }	 
							}
						})
					}
					});
		        }else{
		           $.messager.alert("提示","不能划分重复的步巡设施点");
		           return; 
		        }
	     	 }
	    	});
         	
         }else{
         	$.messager.alert("提示","请给字段赋值");
         }
         
        }
    }
    
	
    function back(){
    	location.href="index.do";
    }
    
</script>
</body>
</html>
