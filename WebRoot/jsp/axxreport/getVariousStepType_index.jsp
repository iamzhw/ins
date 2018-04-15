
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>子类型数量统计</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">地市：</td>
						<td width="20%"><select name="area" class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${areaList}" var="al">
									<option value="${al.AREA_ID}">${al.NAME}</option>
								</c:forEach>
						</select></td>
						<td width="10%">
								光缆段：
							</td>
							<td width="20%">
								<div>
									<select name="p_cable_id" id="p_cable_id"
										class="condition-select" onchange="getRelay(this.value)">
										<option value=''>
											--请选择--
										</option>
										<c:forEach items="${cableList}" var="res">
											<option value='${res.CABLE_ID}'>
												${res.CABLE_NAME}
											</option>
										</c:forEach>
									</select>
								</div>
							</td>
							<td width="10%">
								中继段：
							</td>
							<td width="20%">
								<div>
									<select name="p_relay_id" id="p_relay_id"
										class="condition-select">
									</select>
								</div>
							</td>
						
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			
			<div style="float: left;" class="btn-operation"
				onClick="xxdDownload()">导出</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
    <table id="dg" title="【】" style="width: 100%; height: 480px">
	</table>
	<div id="win" ></div>
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		
			selectSelected();
		});
		
		
		function searchData() {
			
			var obj=makeParamJson('form');
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "xxdReportController/getVariousStepType.do",
				queryParams : obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				//
				columns : [ [ 
				{title:'标石',colspan:8,width:100},
				{title:'地标',colspan:4,width:200},
				{title:'人井',colspan:11,width:200},
				{title:'宣传牌',colspan:5,width:250},
				{title:'电杆',colspan:9,width:250},
				{title:'警示牌',colspan:4,width:250},
				{title:'护坡',colspan:5,width:250},
				{title:'接头盒',colspan:13,width:250},
				{title:'非路由标志',colspan:3,width:250},
				],
				[
				{field:'HIGHREMARK',title:'高标石'},
				{field:'LOWREMARK',title:'矮标石'},
				{field:'COVERREMARK',title:'有标石套'},
				{field:'UNCOVERREMARK',title:'无标石套'},
				{field:'COMMREMARK',title:'普通标石'},
				{field:'CONTECTREMARK',title:'接头标石'},
				{field:'CABLEREMARK',title:'余缆标石'},
				{field:'CORNERREMARK',title:'转角标石'},
				
				{field:'POLISHED',title:'不锈钢'},
				{field:'RUBBER',title:'橡胶'},
				{field:'CERAMIC',title:'瓷砖'},
				{field:'GROUND',title:'地砖'},
				
				
				{field:'ROUND',title:'圆井盖'},
				{field:'SQUARE',title:'方井盖'},
				{field:'DOUBLE',title:'双井盖'},
				{field:'THREE',title:'三井盖'},
				{field:'IRON',title:'铸铁'},
				{field:'COMPLEX',title:'复合'},
				{field:'SPECIAL',title:'长途专用'},
				{field:'UNSPECIAL',title:'非长途专用'},
				{field:'COMM',title:'普通井'},
				{field:'CONTECT',title:'接头井'},
				{field:'CABLE',title:'余缆井'},
				
				{field:'SMALL',title:'小号'},
				{field:'NUMBER1',title:'60x80'},
				{field:'MIDDLE',title:'中号'},
				{field:'FIGURE',title:'120x100'},
				{field:'LARGE',title:'大号'},
				
				{field:'HIGH1',title:'8米'},
				{field:'HIGH2',title:'8-10米'},
				{field:'HIGH3',title:'10米以上'},
				{field:'CONCRETE',title:'水泥电杆'},
				{field:'WOOD',title:'木电杆'},
				{field:'COMM',title:'普通杆'},
				{field:'UPPER',title:'引上(下)杆'},
				{field:'ANGLE',title:'角杆'},
				{field:'HPOLE',title:'H杆'},
				
				{field:'WARN',title:'警示牌'},
				{field:'SLOGAN',title:'宣传标语'},
				{field:'LIMIT',title:'限高牌'},
				{field:'LINE',title:'水线牌'},
				
				{field:'BRICK',title:'砖砌'},
				{field:'MASONRY',title:'石块砌'},
				{field:'SPARY',title:'喷漆'},
				{field:'PLASTER',title:'贴搪瓷牌'},
				{field:'TITLING',title:'贴瓷砖'},
				
				{field:'HORIZTONTAL',title:'卧式'},
				{field:'CAP',title:'帽式'},
				{field:'OVERHEAD',title:'架空'},
				{field:'FIGURE',title:'直埋'},
				{field:'TUNNEL',title:'管道'},
				{field:'M',title:'3M'},
				{field:'RAYCHEM',title:'瑞侃'},
				{field:'YEAR1',title:'5年内'},
				{field:'YEAR2',title:'5-10年'},
				{field:'YEAR3',title:'10-20年'},
				{field:'YEAR4',title:'>=20年'},
				{field:'BRACKET',title:'有托架'},
				{field:'NOBRACKET',title:'无托架'},
				
				{field:'IDENTIFE',title:'标识'},
				{field:'BRAND',title:'宣传牌'},
				{field:'SIGN',title:'警示牌'}
				
				] ],
				width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
       			// $(this).datagrid("fixRownumber");
       			 
				
				}
				
				
			});
		}
		/**选择行触发**/
		function onClickRow(index, row) {
			alert("onClickRow");
		}
		/**点击checkbox触发**/
		function onCheck(index, row) {
			alert("onCheck");
		}

		function onSelect(index, row) {
			alert("onSelect");
		}

		function onSelectAll(rows) {
			alert(rows);
			alert("onSelectAll");
		}

		function clearCondition(form_id) {
			
			$("#"+form_id+"").form('reset', 'none');
			
		}
		
			
		function selectSelected(){
			$.ajax({
				type : 'POST',
				url : webPath + "Staff/selectSelected.do",
				dataType : 'json',
				async:false,
				success : function(json) {
					if(json[0].ifGly==1){			
						$("#ifGLY").val("0");
					}else{
						$("select[name='area']").val(${areaId});
						$("select[name='area']").attr("disabled","disabled");
						$("#ifGLY").val("1");
					}
				}
			});
		}
			
			
			
				
		function getRelay(cable_id){
				$.ajax({          
					  async:false,
					  type:"post",
					  url :webPath + "lineInfoController/getRelay.do",
					  data:{cable_id:cable_id},
					  dataType:"json",
					  success:function(data){
						  $("#p_relay_id").empty();
						  $("#p_relay_id").append("<option value=''>--请选择--</option>");		
						  $.each(data.relayList,function(i,item){
							  $("#p_relay_id").append("<option value='"+item.RELAY_ID+"'>"+item.RELAY_NAME+"</option>");		
						  });
					  }
				  });
			}
		
	

			function getParamsForDownloadLocal(idOrDom) {
			if (!idOrDom) {
				return;
			}
			var o;
			if (typeof idOrDom == "string") {
				o = $("#" + idOrDom);
			} else {
				o = $(idOrDom);
			}
			var res = "?randomPara=1";
			o.find("input,select").each(function() {
				var o = $(this);
				var tag = this.tagName.toLowerCase();
				var name = o.attr("name");
				if (name) {
					if (tag == "select") {
						if(o.find("option:selected").val()=='all' || o.find("option:selected").val()=='' ){
							res=res+"&"+name+"=";
						}else{
						     var value = o.find("option:selected").val();
						     if(value==undefined){
						        value='';
						     }
							res=res+"&"+name+"="+value;
						}
						
					} else if (tag == "input") {
			 
						res=res+"&"+name+"="+ o.val();
					}
				}
			});
			return res;
		}



	
		
		function xxdDownload(){
			window.open(webPath + "xxdReportController/getVariousStepTypeDown.do"+getParamsForDownloadLocal('form'));
		}
	</script>

</body>
</html>