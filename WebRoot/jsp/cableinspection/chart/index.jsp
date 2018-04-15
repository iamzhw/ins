<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>缆线巡检图表</title>
</head>
<body style="padding:3px;border:0px">
<div id="CoverRate" style="float:left;width:50%;height:300px;"></div>
<div id="EqpRate" style="float:left;width:50%;height:300px;"></div>
<div id="TroubleBill" style="float:left;width:100%;height:300px;"></div>
<script type="text/javascript" src="<%=path%>/js/highcharts.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	getDataForCharts();
})

function getDataForCharts(){
	var coverRateData = []
	var eqpRateData = [];
	var troubleBillData = [];
	var obj = {
			};
	$.ajax({
		type : "post",
		url : webPath + "Chart/queryData.do",
		dataType : "json",
		data : obj,
		success : function(data) {
		coverRateData = data.coverRateData;
		eqpRateData = data.eqpRateData;
		troubleBillData = data.troubleBillData;
		coverRateChart(coverRateData);
		eqpRateChart(eqpRateData);
		troubleBillChart(troubleBillData);
		},
		error : function(err) {
		}
		});
	}
	
	function coverRateChart(coverRateData){
		var chart;
		var names="";
		var rates="";
		for ( var i = 0; i < coverRateData.length; i++) {
			names+=",'"+coverRateData[i].NAME+"'";
			rates+=","+coverRateData[i].RATE+"";
		}
		names=names.substring(1);
		rates=rates.substring(1);
		names="["+names+"]";
		rates="["+rates+"]";
		var options= {  
        	chart: {  
                renderTo: 'CoverRate',
                type: 'column'
            }, 
            credits: {
                enabled: false
            },
            title: {  
                text: '本月覆盖率'
            }, 
            xAxis: {  
                categories: eval(names)
            },  
            yAxis: {
            	max:100,
                title: {
                    text: '百分比'
                }
            },
            tooltip: {
                formatter:function(){
                return this.y+"%";
                }
            },
            series: [{
            	name:"%",
            	data:eval(rates)
            }]
        };
         chart = new Highcharts.Chart(options); 
	}
	
	function eqpRateChart(eqpRateData){
		var chart;
		var names="";
		var rates="";
		for ( var i = 0; i < eqpRateData.length; i++) {
			names+=",'"+eqpRateData[i].NAME+"'";
			rates+=","+eqpRateData[i].RATE+"";
		}
		names=names.substring(1);
		rates=rates.substring(1);
		names="["+names+"]";
		rates="["+rates+"]";
		var options= {  
        	chart: {  
                renderTo: 'EqpRate',
                type: 'column'
            }, 
            credits: {
                enabled: false
            },
            title: {  
                text: '本月派发率'
            }, 
            xAxis: {  
                categories: eval(names)
            },  
            yAxis: {
            	max:100,
                title: {
                    text: '百分比'
                }
            },
            tooltip: {
                formatter:function(){
                return this.y+"%";
                }
            },
            series: [{
            	name:"%",
            	data:eval(rates)
            }]
        };
         chart = new Highcharts.Chart(options);
	}
	
	function troubleBillChart(troubleBillData){
		var chart;
		var sum1="";
		var sum2="";
		var sum3="";
		var sum4="";
		var sum5="";
		var names="";
		for ( var i = 0; i < troubleBillData.length; i++) {
			sum1+=","+troubleBillData[i].SUM1+"";
			sum2+=","+troubleBillData[i].SUM2+"";
			sum3+=","+troubleBillData[i].SUM3+"";
			sum4+=","+troubleBillData[i].SUM4+"";
			sum5+=","+troubleBillData[i].SUM5+"";
			names+=",'"+troubleBillData[i].NAME+"'";
		}
		names=names.substring(1);
		sum1=sum1.substring(1);
		sum2=sum2.substring(1);
		sum3=sum3.substring(1);
		sum4=sum4.substring(1);
		sum5=sum5.substring(1);
		names="["+names+"]";
		sum1="["+sum1+"]";
		sum2="["+sum2+"]";
		sum3="["+sum3+"]";
		sum4="["+sum4+"]";
		sum5="["+sum5+"]";
		var options= {  
        	chart: {  
                renderTo: 'TroubleBill',
                type: 'column'
            }, 
            credits: {
                enabled: false
            },
            title: {  
                text: '隐患工单处理情况'
            }, 
            xAxis: {  
                categories: eval(names)
            },  
            yAxis: {
                title: {
                    text: '百分比'
                }
            },
            tooltip: {
                formatter:function(){
                return this.y+"件";
                }
            }
        };
        options.series = [];
        // data它是从数据库中查出来的值的列表, 是一个list  
        	options.series[0] = {   
                  name: '未派发' ,  
                  data: eval(sum1)
               };
            options.series[1] = {   
                  name: '待回单' ,  
                  data: eval(sum2)
               };
            options.series[2] = {   
                  name: '待审核',  
                  data: eval(sum3)
               };
             options.series[3] = {   
                  name: '归档',  
                  data: eval(sum4)
               };
             options.series[4] = {   
                  name: '总数',  
                  data: eval(sum5)
               };
         chart = new Highcharts.Chart(options);
	}
</script>
</body>
</html>