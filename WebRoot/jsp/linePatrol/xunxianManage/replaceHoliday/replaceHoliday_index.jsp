<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+
					  request.getServerName()+":"+
					  request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>代巡休假管理管理</title>


<link rel='stylesheet' href='<%=path%>/js/fullcalendar-2.3/lib/cupertino/jquery-ui.min.css' />
<link href='<%=path%>/js/fullcalendar-2.3/fullcalendar.css' rel='stylesheet' />
<link href='<%=path%>/js/fullcalendar-2.3/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='<%=path%>/js/fullcalendar-2.3/lib/moment.min.js'></script>
<script src='<%=path%>/js/fullcalendar-2.3/lib/jquery.min.js'></script>

<script src='<%=path%>/js/fullcalendar-2.3/fullcalendar.min.js'></script>
<script src='<%=path%>/js/fullcalendar-2.3/lang-all.js'></script>


<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/umeng/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/new_style.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/main.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/nyroModal.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/jquery.autocomplete.css">
<!--
<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/demo.css">11
-->
<%--<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
--%><!-- <script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script> -->
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.modified.js"></script>
<script type="text/javascript" src="<%=path%>/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.nyroModal.custom.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<style>

	body {
		margin: 0;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#top {
		background: #eee;
		border-bottom: 1px solid #ddd;
		padding: 0 10px;
		line-height: 40px;
		font-size: 12px;
	}

	#calendar {
		max-width: 900px;
		margin: 5px auto;
		padding: 0 10px;
	}

</style>

</head>
<body style="padding: 3px; border: 0px">
	
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
					<table class="condition">
					<tr>
						<td width="10%">巡线员：</td>
						<td width="20%">
							
						 <div>
							<select name="p_inspactor_name" id="p_inspactor_name" class="condition-select">
								<option value=''>--请选择--</option>
								<c:forEach items="${localInspactStaff}" var="res">
									<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
								</c:forEach>
							</select>
						</div>	
					    </td>
						<td width="10%">月份：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="p_query_time" id="p_query_time" onClick="WdatePicker({dateFmt:'yyyy-MM'});"/>
							</div>
					    </td>

					</tr>
					
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="replaceHolidayAddUI();">增加</div>
			
			

			<%--<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			--%><div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win_addUI"></div>
	<div id="win_updateUI"></div>
	<div id="calendar"></div>
	
	<div id="win_distribute"></div>
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
	String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g,"");}
		
	$(document).ready(function() {
		
	});
	
	var webPath="<%=path%>/";
	
	
	function searchData(){
		var p_query_time=$("#p_query_time").val();
		if(p_query_time==''){
			alert('查询日期不能为空！');
			return false;
		}
		
		var obj=makeParamJson('form');
		$.ajax({          
			  async:false,
			  type:"post",
			  url :webPath + "replaceHolidayController/query.do",
			  data:obj,
			  dataType:"json",
			  success:function(data){
				  refreshCalendar(data);
			  }
		  });
	}
	
	function replaceHolidayAddUI() {
		 $('#win_addUI').window({
				title : "【新增代巡休假管理】",
				href :webPath + "replaceHolidayController/replaceHolidayAddUI.do",
				width : 400,
				height : 350,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		
	}
		

	function refreshCalendar(data){
		
		$('#calendar').fullCalendar('destroy');
		
		var currentLangCode = 'zh-cn';

		

		function renderCalendar() {
			
			var today=JSON.stringify(data.today);
			today=today.substring(1,today.length-1);//双引号--单引号
			
			//为events组织数据
			var res=[];
			$.each(data.allHoliday,function(i,item){
				//注意obj的组织  不同于一般json id title start
				//换行\r\n
				if(item.HOLIDAY_TYPE==0){
					if(item.HOLIDAY_DESC==null && item.REST_TYPE==0){
						var obj={id:item.HOLIDAY_ID,title:'人员：'+item.USER_NAME+'\r\n'+'类型：休假'+'\r\n休息类型：事假',start:item.HOLIDAY_DATE}	
					}else if(item.HOLIDAY_DESC==null && item.REST_TYPE==1){
						var obj={id:item.HOLIDAY_ID,title:'人员：'+item.USER_NAME+'\r\n'+'类型：休假'+'\r\n休息类型：调休',start:item.HOLIDAY_DATE}
					}else if(item.HOLIDAY_DESC !=null && item.REST_TYPE==0){
						var obj={id:item.HOLIDAY_ID,title:'人员：'+item.USER_NAME+'\r\n'+'类型：休假'+'\r\n休息类型：事假'+'\r\n休假原因：'+item.HOLIDAY_DESC,start:item.HOLIDAY_DATE}
					}else{
						var obj={id:item.HOLIDAY_ID,title:'人员：'+item.USER_NAME+'\r\n'+'类型：休假'+'\r\n休息类型：调休'+'\r\n休假原因：'+item.HOLIDAY_DESC,start:item.HOLIDAY_DATE}
					}
				}else{
					if(item.HOLIDAY_DESC==null && item.REST_TYPE==0){
						var obj={id:item.HOLIDAY_ID,title:'人员：'+item.USER_NAME+'\r\n'+'类型：代巡'+'\r\n代巡人：'+item.REPLACE_NAME+'\r\n休息类型：事假',start:item.HOLIDAY_DATE}	
					}else if(item.HOLIDAY_DESC==null && item.REST_TYPE==1){
						var obj={id:item.HOLIDAY_ID,title:'人员：'+item.USER_NAME+'\r\n'+'类型：代巡'+'\r\n代巡人：'+item.REPLACE_NAME+'\r\n休息类型：调休',start:item.HOLIDAY_DATE}
					}else if(item.HOLIDAY_DESC !=null && item.REST_TYPE==0){
						var obj={id:item.HOLIDAY_ID,title:'人员：'+item.USER_NAME+'\r\n'+'类型：代巡'+'\r\n代巡人：'+item.REPLACE_NAME+'\r\n休息类型：事假'+'\r\n休假原因：'+item.HOLIDAY_DESC,start:item.HOLIDAY_DATE}
					}else{
						var obj={id:item.HOLIDAY_ID,title:'人员：'+item.USER_NAME+'\r\n'+'类型：代巡'+'\r\n代巡人：'+item.REPLACE_NAME+'\r\n休息类型：调休'+'\r\n休假原因：'+item.HOLIDAY_DESC,start:item.HOLIDAY_DATE}
					}
				}
				res.push(obj);
			});
			
			
			$('#calendar').fullCalendar({
				header: {
					left: '',//prev,next today
					center: 'title',
					right: 'month'
				},
				defaultDate: today,
				lang: currentLangCode,
				buttonIcons: false, // show the prev/next text
				weekNumbers: true,
				editable: false,
				eventLimit: true, // allow "more" link when too many events
				dayClick:function( date, allDay, jsEvent, view ) {//点击空白处事件
					//alert(date);		
				},
				eventClick: function(calEvent, jsEvent, view) {
					
					replaceHolidayEditUI(calEvent.id);//点击日程内容事件
					//alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
					//alert('View: ' + view.name);
					// change the border color just for fun
					//$(this).css('border-color', 'red');

				},
				events: res
			});
		}
		renderCalendar();
		
	}

		

		function clearCondition(form_id) {
			
			$("#"+form_id+"").form('reset', 'none');
			
		}

		
		
		function saveForm() {
			var pass = $("#formAdd").form('validate');
			if (pass) {
				var holiday_type=$("#formAdd #holiday_type").val();
				if(holiday_type==1){
					var replace_id=$("#formAdd #replace_id").val();
					if(replace_id==''){
						alert("代巡时代巡人不能为空");
						return false;
					}
				}
				$.messager.confirm('系统提示', '您确定要新增代巡休假管理吗?', function(r) {
					if (r) {
						var data=makeParamJson('formAdd');
						$.ajax({
							type : 'POST',
							url : webPath + "replaceHolidayController/replaceHolidaySave.do",
							data : data,
							dataType : 'json',
							success : function(json) {
								
								if (json.status) {
									
									$.messager.show({
										title : '提  示',
										msg : '新增代巡/休假成功！',
										showType : 'show',
										timeout:'1000'//ms
									   
									});
									searchData();
								}
								else{
									$.messager.alert("提示","新增代巡/休假失败！","info");
									return;
								}
								$('#win_addUI').window('close');
// 								refreshCalendar(json);

							}
						})
					}
				});
			}
		}
		
		
		function replaceHolidayEditUI(holiday_id) {
				$('#win_updateUI').window({
					title : "【编辑代巡休假管理】",
					href : webPath + "replaceHolidayController/replaceHolidayEditUI.do?id=" + holiday_id,
					width : 400,
					height : 350,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				}); 
		

		}

		function replaceHolidayDeleteUI(holiday_id) {
			
				$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
					if (r) {
						
						$.messager.progress();
						$.ajax({          async:false,
							  type:"post",
							  url : webPath + "replaceHolidayController/replaceHolidayDelete.do",
							  data:{holiday_id:holiday_id},
							  dataType:"json",
							  success:function(data){
								  $.messager.progress('close');
								  if(data.status){
									
									  //refreshCalendar(data);
									  searchData();
									  $('#win_updateUI').window('close');
									  
										$.messager.show({
											title : '提  示',
											msg : '成功删除!',
											showType : 'show',
											timeout:'1000'//ms
										   
										});
									}else{
										alert("删除失败");
									}
							  }
						  });
						
						
					}
				});
			
		}


		
		
		function updateForm() {
			var pass = $("#formEdit").form('validate');
			if (pass) {
				
				var holiday_type=$("#formEdit #holiday_type").val();
				if(holiday_type==1){
					var replace_id=$("#formEdit #replace_id").val();
					if(replace_id==''){
						alert("代巡时代巡人不能为空");
						return false;
					}
				}
				
				$.messager.confirm('系统提示', '您确定要更新代巡休假管理吗?', function(r) {
					if (r) {
						var obj=makeParamJson('formEdit');
						$.ajax({
							type : 'POST',
							url : webPath + "replaceHolidayController/replaceHolidayUpdate.do",
							data : obj,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新代巡休假管理成功!',
										showType : 'show',
										timeout:'1000'//ms
									});
								searchData();
								}
								$('#win_updateUI').window('close');
// 								refreshCalendar(json);
								
							}
						})
					}
				});
			}
		}
		
</script>

</body>
</html>
