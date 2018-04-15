//bind province flash
$(function() {
	bindProvince();
});
function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}
var contextPath = getContextPath();

function bindProvince() {
	var miniMapColorData = "徐州,0xfb000d,province_onClick\r"
			+ "南京,0xff4500,province_onClick\r"
			+ "无锡,0xff7400,province_onClick\r"
			+ "常州,0xffaa00,province_onClick\r"
			+ "苏州,0xffbf00,province_onClick\r"
			+ "泰州,0xffd200,province_onClick\r"
			+ "镇江,0xffe800,province_onClick\r"
			+ "南通,0xffff00,province_onClick\r"
			+ "宿迁,0xc9f600,province_onClick\r"
			+ "扬州,0x9fee00,province_onClick\r"
			+ "盐城,0x67e300,province_onClick\r"
			+ "连云港,0x00c90d,province_onClick\r"
			+ "淮安,0xff9200,province_onClick";
	var colorBox = "0xfb000d\r" + "0xff4500\r" + "0xff7400\r" + "0xff9200\r"
			+ "0xffaa00\r" + "0xffbf00\r" + "0xffd200\r" + "0xffe800\r"
			+ "0xffff00\r" + "0xc9f600\r" + "0x9fee00\r" + "0x67e300\r"
			+ "0x00c90d";
	var info = "";
	swfobject.embedSWF("province.swf", "province", "100%", "100%", "10.0.0",
			"expressInstall.swf", {
				miniMapColorData : miniMapColorData,
				colorBox : colorBox,
				info : info
			}, {
				menu : "false",
				wmode : "transparent"
			});
}

function province_onClick(city) {
	alert("您点击的是：" + city);
}

// bind pie, column and line charts
$(function() {
	drawPieChart();
	drawColumnChart();
	drawLine();
});

function drawPieChart() {
	var data = eval("[{'city':'徐州','users':'749'},{'city':'南京','users':'816'},"
			+ "{'city':'无锡','users':'224'},{'city':'常州','users':'399'},"
			+ "{'city':'苏州','users':'21'},{'city':'泰州','users':'798'},"
			+ "{'city':'镇江','users':'171'},{'city':'南通','users':'623'},"
			+ "{'city':'宿迁','users':'820'},{'city':'扬州','users':'10'},"
			+ "{'city':'盐城','users':'674'},{'city':'连云港','users':'172'},"
			+ "{'city':'淮安','users':'456'}];");

	$("#left").highcharts({
		chart : {
			backgroundColor : "#ffffff",
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		title : {
			text : "",
			style : {
				color : "#000000",
				fontSize : "1px"
			}
		},
		tooltip : {
			pointFormat : "{series.name}: {point.percentage:.1f}%"
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : "pointer",
				dataLabels : {
					enabled : true,
					color : "#000000",
					connectorColor : "#000000",
					format : "{point.name}: <br/>{point.percentage:.1f} %"
				}
			}
		},
		series : [ {
			type : "pie",
			name : "用户量",
			data : convertJsonToPieData(data, "city", "users")
		} ]
	});
}

function convertJsonToPieData(arr, key, val) {
	var data = new Array();
	for ( var i in arr) {
		var json = arr[i];
		var k = json[key];
		var v = json[val];
		if (k && v) {
			var data1 = new Array();
			data1.push(k);
			data1.push(parseInt(v));
			data.push(data1);
		}
	}
	return data;
}

function drawColumnChart() {
	var data = eval("[{'city':'徐州','users':'749'},{'city':'南京','users':'816'},"
			+ "{'city':'无锡','users':'224'},{'city':'常州','users':'399'},"
			+ "{'city':'苏州','users':'21'},{'city':'泰州','users':'798'},"
			+ "{'city':'镇江','users':'171'},{'city':'南通','users':'623'},"
			+ "{'city':'宿迁','users':'820'},{'city':'扬州','users':'10'},"
			+ "{'city':'盐城','users':'674'},{'city':'连云港','users':'172'},"
			+ "{'city':'淮安','users':'456'}];");
	$("#right").highcharts({
		chart : {
			backgroundColor : "#ffffff",
			type : "column"
		},
		title : {
			text : "",
			style : {
				color : "#000000",
				fontSize : "1px"
			}
		},
		xAxis : {
			categories : getArrByKey(data, "city", false),
			labels : {
				style : {
					fontSize : "12px"
				}
			}
		},
		yAxis : {
			title : "num",
			labels : {
				style : {
					fontSize : "12px"
				}
			}
		},
		plotOptions : {
			series : {
			// groupPadding: 0.2
			}
		},
		series : [ {
			data : getArrByKey(data, "users", true),
			name : "地市"
		} ],
		legend : {
			enabled : false
		}
	});
}

function getArrByKey(jsonArray, key, toInt) {
	var arr = new Array();
	for ( var i in jsonArray) {
		var json = jsonArray[i];
		var val = json[key];
		if (val) {
			if (toInt) {
				val = parseInt(val);
			}
			arr.push(val);
		}
	}
	return arr;
}

function drawLine() {
	$("#bottom").highcharts({
		chart : {
			backgroundColor : "#ffffff",
			type : "line"
		},
		title : {
			text : "",
			style : {
				fontSize : "1px"
			}
		},
		xAxis : {
			categories : [ "2011", "2012", "2013", "2014" ]
		},
		yAxis : {
			title : {
				text : "业务量"
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : "#808080"
			} ]
		},
		legend : {
			enabled : false
		},
		series : [ {
			name : "使用量",
			data : [ 100, 200, 300, 500 ]
		} ]
	});
}

function cableChart(){
	location.href =contextPath+"/Chart/index.do";
}