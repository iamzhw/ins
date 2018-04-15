
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%
	String path2 = request.getContextPath();
	
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>巡线无效时间图</title>
<script type="text/javascript" src="<%=path2%>/js/highcharts.js"></script>
<script type="text/javascript" src="<%=path2%>/js/highcharts-more.js"></script>

</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<!--  <div  class="btn-operation" onClick="up()">上午</div>
	<div  class="btn-operation" onClick="down()">下午</div>-->
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						
						<td >巡线时间：</td>
						<td >
							<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="line_date" id="line_date" 
								onClick="WdatePicker();"/>
							</div>
						</td>
						<td >巡线员：</td>
						<td >
							<div class="condition-text-container">
								<input type="hidden" name="user_id" id="user_id" />
								<input name="user_name" id="user_name" class="condition-text" 
								onClick="get_kan_name()" />
							</div>
						</td>
						<td >
							<div  class="btn-operation" onClick="clearCondition('form')">重置</div>
							<div  class="btn-operation" onClick="loadPic()">查询</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id=win_name></div>
	</div>
	<div id="container" style="width: 100%; height: 450px; align: center" >
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
			var now_year = new Date().getFullYear();//获取当前年份
	    	var now_month = new Date().getMonth()+1;//获取当前月份
	    
	    	var today = new Date();
	    	var day = today.getDate()-1;
		    	day = day<10?"0"+day:day;
			var month = today.getMonth()+1;
		   	 	month = month<10?"0"+month:month;
	    	var timetody = now_year+month+day;
	     	var daty = now_year+'-'+month+'-'+day;
	     		$("#line_date").val(daty);
		});


	function timeToNumber(timeDate){
		if(timeDate!=null && timeDate != ''){
			var time_number_array = timeDate.split(':');
			var timeToNumber =   parseFloat(time_number_array[0])+parseFloat(time_number_array[1]/60)+parseFloat(time_number_array[2]/3600);
			return Math.round(timeToNumber*1000000)/1000000 //保留两位小数
		}
	}	
		
		//柱状图后台取值
	function loadPic(){
		var line_date =$("#line_date").val();
		var user_id=$("#user_id").val();
		if(line_date==''){
			$.messager.alert("提示","请选择巡线日期");
			return;
		}
		if(user_id==''){
			$.messager.alert("提示","请选择巡线员");
			return;
		}
		$.ajax({
		async:false,
		dataType: "json",
		url : webPath + "outsitePlanManage/list_no_picture.do",
		data:{
			'line_date':line_date,
			'user_id':user_id
		},
		success : function(data) {
				var array = new Array();
				
				for(var i=0;i<data.length;i++){
					if(data[i].INVALID_TYPE==0){
						var arr = new Array(0,timeToNumber(data[i].START_TIME),timeToNumber(data[i].END_TIME));
						array.push(arr);
					}else if(data[i].INVALID_TYPE==1){
						var arr=new Array(1,timeToNumber(data[i].START_TIME),timeToNumber(data[i].END_TIME));
						array.push(arr);
					}else if(data[i].INVALID_TYPE==2){
						var arr=new Array(2,timeToNumber(data[i].START_TIME),timeToNumber(data[i].END_TIME));
						array.push(arr);
					}else{
						var arr=new Array(3,timeToNumber(data[i].START_TIME),timeToNumber(data[i].END_TIME));
						array.push(arr);
					}
		    			
		    	}
		    	tree_chart(array);
			}
		});
    
	}
		
	
	function tree_chart(array){
		
			$('#container').highcharts({
			
			    chart: {
			        type: 'columnrange',
			        inverted: true
			    },
			    
			    title: {
			        text: ''
			    },
			    
				subtitle: {
			        text: ''
			    },
			
			    xAxis: {
			        categories: ['GPS未打开', 'GPS连续丢失信号', '未匹配到巡线点','停留时间超过40分钟']
			    },
			    
			    yAxis: {
			    	 min:6,
			    	 max:20,
			        title: {
// 			            text: 'Temperature ( °C )'
			        }
			    },
			
			    tooltip: {
			    	 formatter: function () {
			    		 var low = this.point.low;
			    		 var high=this.point.high;
			    		 var lowTime=get_shi_fen_miao(low);
			    		 var highTime=get_shi_fen_miao(high);
			    		 return  (this.x)+'<br></br>' + lowTime+'-'+highTime;
			                    
		             }
			    },
			    
			    plotOptions: {
			        columnrange: {
			        	dataLabels: {
			        		enabled: true,
			        		formatter: function () {
// 			        			return this.y ;
			        		}
			        	}
			        }
			    },
			    
			    legend: {
			        enabled: false
			    },
			
			    series: [{
			        name: '时间',
// 			        colorByPoint:true,
			        data: 
						array
			    }]
			});
        
	}
	
	function get_shi_fen_miao(date){
		var secondLow=date*3600;   //得到多少秒
		var hour = Math.floor (secondLow / 3600);
		var other = secondLow % 3600;
		var minute = Math.floor (other / 60);
		var second = (other % 60).toFixed (0);
		return  hour + '时' + minute + '分' + second+'秒';
	}
	
		function get_kan_name() {
			$('#win_name').window({
				title : "【选择巡线人员】",
				href : webPath + "outsitePlanManage/select_xunxian_name.do",
				width : 650,
				height : 400,
				zIndex : 0,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}

		function clearCondition(form_id) {
			$("#" + form_id + "").form('reset', 'none');
		}
	</script>
</body>
</html>
