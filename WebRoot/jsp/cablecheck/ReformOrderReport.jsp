<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
a{text-decoration:none;}
.div_content{
        display:none;
        position: absolute;
        /* top: 400px; */
        width: 190px;
        margin-left:0px;
        height: 100px;
        margin-top:3px;
        padding: 0;
        background-color: #F5F5F5; 
        z-index:1011;
        overflow: hidden;
        padding-top: 6px;
	}
	.wljb{
		margin-top: 10px;
	}
	.button {
		display: inline-block;
		position: relative;
		margin: 8px;
		margin-left: 14px;
		padding: 0 6px;
		text-align: center;
		text-decoration: none;		
		text-shadow: 1px 1px 1px rgba(255,255,255, .22);
		-webkit-border-radius: 8px;
		-moz-border-radius: 8px;
		border-radius: 8px;
		-webkit-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
		-moz-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
		box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);

		-webkit-transition: all 0.15s ease;
		-moz-transition: all 0.15s ease;
		-o-transition: all 0.15s ease;
		-ms-transition: all 0.15s ease;
		transition: all 0.15s ease;		
	}
</style>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<%-- <script type="text/javascript" src="<%=path%>/js/donetask/donetask-index.js"></script> --%>
		<title>整治单数据报告</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 50px;">
				<form id="form" action="" method="post">
					<table class="condition">
						<tr>	
							<td width="10%">地市：</td>
							<td width="20%">
								<div>
									<select name="area_id" id="area_id" class="condition-select" onchange="getSonAreaList()">
										<option value=''>--请选择--</option>																		
										<%-- <c:forEach items="${area}" var="areaInfo"> 
												<option value='${areaInfo.AREA_ID}'>${areaInfo.NAME}</option>										
										</c:forEach> --%>
									</select>
								</div>
								</td>
							<td width="10%">
								区域：
							</td>
							<td width="20%">
								<select name="SON_AREA_ID" id="son_area" class="condition-select" >
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="10%" nowrap="nowrap">上报开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=START_TIME id="task_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">上报结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=COMPLETE_TIME id="task_end_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
								</div>
							</td>														
												
						</tr>
						
						<tr>	
							<td width="10%" nowrap="nowrap">派发开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=PSTART_TIME id="task_pstart_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">派发结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=PCOMPLETE_TIME id="task_pend_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
								</div>
							</td>						
							<td width="10%" nowrap="nowrap">回单开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=MSTART_TIME id="task_mstart_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">回单结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=MCOMPLETE_TIME id="task_mend_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
								</div>
							</td>
							
							
							
							
						</tr>
						<tr>
							<td width="10%" nowrap="nowrap">审核开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=ASTART_TIME id="task_astart_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">审核结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=ACOMPLETE_TIME id="task_aend_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
								</div>
							</td>
							<td width="20%"  >网络级别：</td>
							<td width="20%"> 
								<input type="hidden" id="wljb_id" name="WLJB_ID" />
								<div class="div1 condition-text-container" style="width:84%" >
									<input type="text" id="wljb_content" class="condition-text" onclick="showDiv()"style="width:87%" />
								</div>	
								<div id="exchange" class="div_content" style="width:155px"  >	
									<input type="checkbox" id="wljb1" class="wljb" name="checkbox" value="80000302">接入层<br/>
									<input type="checkbox" id="wljb2" class="wljb" name="checkbox" value="81538172">网络层<br/>
									<input type="button" id="confirm" class="wljb button " value="确定"onclick=" getChoice()">  <input type="button" id="reset" class="wljb button "value="重置"onclick="removeChoice()"> 		
								</div>	
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div style="float: right;" class="btn-operation" onClick="reform()">重置</div>
				<div style="float: right;" class="btn-operation" onClick="exportExcel()">导出</div>
				<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
				<input name="area_id" type="hidden" value="" />
				
			</div>
			<div class="btn-operation-container" id = "initbutton" style="display:none;">
				<div style="float: right;" class="btn-operation" onClick="exportExcelByCity()">汇总导出</div>
				<div style="float: right;" class="btn-operation" onClick="searchDataByCity()">各地市汇总</div>
			</div>
		</div>
		<table id="dg" title="【整治单数据报告】" style="width: 100%; height: 480px"></table>
		<div>
			<ul>
				<li>上报时间:检查员发现问题上报时间段</li>
				<li>派发时间:审核员派发整改单到维护员时间段</li>
				<li>回单时间:维护员维护整改单时间段</li>
				<li>审核时间:审核员审核已整改任务时间段</li>
				<li>整治派发率(按设备)=(周期性检查上报派发+一键反馈上报派发+不预告抽查上报派发)/(周期性检查上报总数+一键反馈上报总数+不预告抽查上报总数)</li>
				<li>整治回单率(按设备)=(周期性检查已整治 + 一键反馈已整治 + 不可预告抽查已整治)/(周期性检查上报派发 + 一键反馈上报派发 + 不可预告抽查上报派发)</li>
				<li>整治完成率(按设备)=(周期性检查通过审核 + 一键反馈通过审核 + 不可预告抽查通过审核)/(周期性检查上报派发 + 一键反馈上报派发 + 不可预告抽查上报派发)</li>
			</ul>
		</div>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
		initbutton();
		initParameters();
		getAreaList();
			//searchData();
		});
		function initbutton(){
		var isAdmin1 ='${CABLE_ADMIN}';
		if(true ==isAdmin1 || isAdmin1 =='true'){
		
		$("#initbutton").show();
					
				}
		
		};
		function searchData() {
		    
			if($("#task_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
		    /* if($("#task_pstart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_pend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			if($("#task_mstart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_mend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			if($("#task_astart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_aend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			} */ 
			var AREA_ID = $("select[name='area_id']").val();
		
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			
			var static_month = $("input[name='static_month']").val();
		
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var MSTART_TIME = $("input[name='MSTART_TIME']").val();
			var MCOMPLETE_TIME = $("input[name='MCOMPLETE_TIME']").val();
			var ASTART_TIME = $("input[name='ASTART_TIME']").val();
			var ACOMPLETE_TIME = $("input[name='ACOMPLETE_TIME']").val();
			var son_area = $("#son_area").val();
			
			var WLJB_ID = ''; 
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			var obj ={
					START_TIME : START_TIME,
					COMPLETE_TIME:COMPLETE_TIME,
					AREA_ID:AREA_ID,
					static_month:static_month,
					PSTART_TIME:PSTART_TIME,
					son_area:son_area,
					PCOMPLETE_TIME:PCOMPLETE_TIME,
					MCOMPLETE_TIME:MCOMPLETE_TIME,
					ACOMPLETE_TIME:ACOMPLETE_TIME,
					ASTART_TIME:ASTART_TIME,
					MSTART_TIME:MSTART_TIME,
					WLJB_ID:WLJB_ID
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "ReformOrderReport/ReformOrderReport.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'CITYNAME',
					title : '地市',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'AREAID',
					title : '区域',
					width : "14%",
					rowspan : '2', 
				    align : 'center',
				    hidden:true
				},{
					field : 'NAME',
					title : '区域',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				}, {
					field : '周期性检查总数',
					title : '周期性检查上报总数',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
								return value;
						}else{
			       			return "<a href=\"javascript:show("+ row.AREAID + ");\">"+value+"</a>";
			        	}
		        	}
		        },{
					field : '周期性检查派发',
					title : '周期性检查上报后派发',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show1("+ row.AREAID + ");\">"+value+"</a>";
			        	}
		        	}
		        },{
					field : '周期性检查已整治',
					title : '周期性检查已回单',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show2("+ row.AREAID + ");\">"+value+"</a>";
		        		}
		      		 } 
				},{
					field : '周期性检查未通过审核',
					title : '周期性检查未通过审核',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show16("+ row.AREAID + ");\">"+value+"</a>";
		        		}
		      		 } 
				},{
					field : '周期性检查通过审核',
					title : '周期性检查通过审核',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
							return "<a href=\"javascript:show3("+ row.AREAID + ");\">"+value+"</a>";
						}
		        		
		        	} 
				}, {
					field : '一键反馈总数',
					title : '一键反馈上报总数',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show4("+ row.AREAID + ");\">"+value+"</a>";
		        		}
		        	} 
				},{
					field : '一键反馈派发',
					title : '一键反馈上报后派发',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show5("+ row.AREAID + ");\">"+value+"</a>";
		      	   		}
		      	    } 
				},{
					field : '一键反馈已整治',
					title : '一键反馈已回单',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show6("+ row.AREAID + ");\">"+value+"</a>";
		      	  		}
		      	    } 
				},{
					field : '一键反馈未通过审核',
					title : '一键反馈未通过审核',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show17("+ row.AREAID + ");\">"+value+"</a>";
		       	  	    }
		       	    } 
				}, {
					field : '一键反馈通过审核',
					title : '一键反馈通过审核',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show7("+ row.AREAID + ");\">"+value+"</a>";
		       	  	    }
		       	    } 
				},{
					field : '不可预告抽查总数',
					title : '不可预告抽查上报总数',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show8("+ row.AREAID + ");\">"+value+"</a>";
		       			}
		       		} 
				},{
					field : '不可预告抽查派发',
					title : '不可预告抽查上报后派发',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show9("+ row.AREAID + ");\">"+value+"</a>";
		       	    	}
		       	    } 
				}, {
					field : '不可预告抽查已整治',
					title : '不可预告抽查已回单',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show10("+ row.AREAID + ");\">"+value+"</a>";
		       	 	    }
		       	    } 
				},{
					field : '不可预告抽查未通过审核',
					title : '不可预告抽查未通过审核',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show18("+ row.AREAID + ");\">"+value+"</a>";
		       	 	    }
		       	    } 
				},{
					field : '不可预告抽查通过审核',
					title : '不可预告抽查通过审核',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show11("+ row.AREAID + ");\">"+value+"</a>";
		       	 	    }
		       	    } 
				},{
					field : '发现错误端子数',
					title : '发现错误端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show22("+ row.AREAID + ");\">"+value+"</a>";
		       	 	    }
		       	    } 
				},{
					field : '检查人员一键改端子数',
					title : '检查人员一键改端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show23("+ row.AREAID + ");\">"+value+"</a>";
		       	 	    }
		       	    } 
				},{
					field : '上报整改端子数',
					title : '上报整改端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show12("+ row.AREAID + ");\">"+value+"</a>";
		       	 	    }
		       	    } 
				},{
					field : '派发整改端子数',
					title : '派发整改端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
								return value;
						}else{
	        				return "<a href=\"javascript:show13("+ row.AREAID + ");\">"+value+"</a>";
		            	}
		             } 
				},{
					field : '整改回单端子数',
					title : '整改回单端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show14("+ row.AREAID + ");\">"+value+"</a>";
		         	    }
		            } 
				},{
					field : '整改成功端子数',
					title : '整改成功端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.CITYNAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show15("+ row.AREAID + ");\">"+value+"</a>";
		         		}
		            } 
				}, {
					field : '整治派发率按设备',
					title : '整治派发率（按设备）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治回单率按设备',
					title : '整治回单率（按设备）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治完成率按设备',
					title : '整治完成率（按设备）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治派发率按端子',
					title : '整治派发率（按端子）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治回单率按端子',
					title : '整治回单率（按端子）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治完成率按端子',
					title : '整治完成率（按端子）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				},{
					field : '2018年整改率',
					title : '2018年整改率',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}
				] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				console.log('obj:'+obj);
					$(this).datagrid("fixRownumber");
				var rows = $('#dg').datagrid('getRows');//获取当前的数据行
            var ZQSUM = 0//计算listprice的总和
            ,ZQPF=0,ZQZZ=0,ZQSHWTG=0,ZQSH=0,YJSUM=0,YJPF=0,YJZZ=0,YJSHWTG=0,YJSH=0,BKSUM=0,BKPF=0,BKZZ=0,BKSHWTG=0,BKSH=0,FXDZ=0,YJGDZ=0,SBDZ=0,PFDZ=0,HDDZ=0,SHDZ=0;//统计总和
            
            for (var i = 0; i < rows.length; i++) {
                ZQSUM += rows[i]['周期性检查总数'];
                ZQPF += rows[i]['周期性检查派发'];
                ZQZZ += rows[i]['周期性检查已整治'];
                ZQSHWTG +=rows[i]['周期性检查未通过审核'];
                ZQSH += rows[i]['周期性检查通过审核'];
                YJSUM += rows[i]['一键反馈总数'];
                YJPF += rows[i]['一键反馈派发'];
                YJZZ += rows[i]['一键反馈已整治'];
                YJSHWTG +=rows[i]['一键反馈未通过审核'];
                YJSH += rows[i]['一键反馈通过审核'];
                BKSUM += rows[i]['不可预告抽查总数'];
                BKPF += rows[i]['不可预告抽查派发'];
                BKZZ += rows[i]['不可预告抽查已整治'];
                BKSHWTG = rows[i]['不可预告抽查未通过审核'];
                BKSH += rows[i]['不可预告抽查通过审核'];
                FXDZ += rows[i]['发现错误端子数'];
                YJGDZ += rows[i]['检查人员一键改端子数'];
                SBDZ += rows[i]['上报整改端子数'];
                PFDZ += rows[i]['派发整改端子数'];
                HDDZ += rows[i]['整改回单端子数'];
                SHDZ += rows[i]['整改成功端子数'];
               
            }
            //新增一行显示统计信息
            
         
            if(ZQSUM+YJSUM+BKSUM==0){
           	 var PFLSB= 0;
               }else{
               	var PFLSB=(ZQPF+YJPF+BKPF)/(ZQSUM+YJSUM+BKSUM);
                   }
       	if(ZQPF+YJPF+BKPF==0){
			 var  HDLSB=0;
			 var  SHLSB=0;
       	}else{
				var HDLSB=(ZQZZ+YJZZ+BKZZ)/(ZQPF+YJPF+BKPF);
				var SHLSB=(ZQSH+YJSH+BKSH)/(ZQPF+YJPF+BKPF);
				}

			
			if(SBDZ==0){
				var  PFLDZ=0;}
			else{
			var PFLDZ=(PFDZ)/SBDZ;
			}
			if( PFDZ==0){
				var HDLDZ = 0;
				var SHLDZ = 0;}
			else{
				var HDLDZ=(HDDZ)/PFDZ;
				var SHLDZ=(SHDZ)/PFDZ;
				}
			if(FXDZ==0){
				var YJGLDZ=0;
			}else{
				var YJGLDZ=(SHDZ+YJGDZ)/FXDZ;
			}
           $('#dg').datagrid('appendRow', { itemid: '<b>统计：</b>', CITYNAME:'合计',周期性检查总数:  ZQSUM,周期性检查派发: ZQPF, 周期性检查已整治: ZQZZ,周期性检查未通过审核: ZQSHWTG,  周期性检查通过审核: ZQSH, 一键反馈总数: YJSUM, 一键反馈派发: YJPF, 一键反馈已整治: YJZZ,一键反馈未通过审核: YJSHWTG, 一键反馈通过审核: YJSH, 不可预告抽查总数: BKSUM, 不可预告抽查派发: BKPF, 不可预告抽查已整治: BKZZ, 不可预告抽查未通过审核: BKSHWTG, 不可预告抽查通过审核: BKSH,发现错误端子数: FXDZ,检查人员一键改端子数: YJGDZ,上报整改端子数: SBDZ,派发整改端子数: PFDZ,整改回单端子数: HDDZ,整改成功端子数:SHDZ,整治派发率按设备:Math.floor(PFLSB * 10000) / 100+'%', 整治回单率按设备:Math.floor(HDLSB * 10000) / 100+'%', 整治完成率按设备:Math.floor(SHLSB * 10000) / 100+'%', 整治派发率按端子:Math.floor(PFLDZ * 10000) / 100+'%', 整治回单率按端子:Math.floor(HDLDZ * 10000) / 100+'%', 
           			整治完成率按端子:Math.floor(SHLDZ * 10000) / 100+'%','2018年整改率': Math.round(YJGLDZ * 10000) / 100+'%'});	
				
				}
			});
		};
		function initParameters(){//初始化查询参数
		var now = new Date();
		//默认派发结束时间
		var pendYear = now.getFullYear();
		var pendMonth = now.getMonth()+1;
		pendMonth = pendMonth>9?pendMonth:"0"+pendMonth;
		var pendDay ='11';
		var pendTime = pendYear+"-"+pendMonth+"-"+pendDay;
		$("#p_end_time").val(pendTime);
		//默认派发任务开始时
		var start = new Date();
		var pstartYear = start.getFullYear();
		var pmonth = now.getMonth();
		var pstartMonth = start.getMonth();
		if(pstartMonth==0){pstartMonth=12;pstartYear=pstartYear-1;}
		pstartMonth = pstartMonth>9?pstartMonth:"0"+pstartMonth;
		var pstartDay = '01';
		var pstartTime = pstartYear+"-"+pstartMonth+"-"+pstartDay;
		$("#p_start_time").val(pstartTime);
		//默认任务结束时间
		var endYear = now.getFullYear();
		var endMonth = now.getMonth();
		//var endMonth = now.getMonth()+1;
		if(endMonth==0){endMonth=12;endYear=endYear-1;}
		endMonth = endMonth>9?endMonth:"0"+endMonth;
		var myDate = new Date(endYear, endMonth, 0);
        var endDay =  myDate.getDate();
        //var endDay =  now.getDate();
		endDay = endDay>9?endDay:"0"+endDay;
		var endTime = endYear+"-"+endMonth+"-"+endDay;
		$("#task_end_time").val(endTime);
		//默认任务开始时
		var start = new Date();
		var startYear = start.getFullYear();
		var month = now.getMonth();
		var startMonth = start.getMonth();
				if(startMonth==0){startMonth=12;startYear=startYear-1;}
		
		startMonth = startMonth>9?startMonth:"0"+startMonth;
		var startDay = '01';
		var startTime = startYear+"-"+startMonth+"-"+startDay;
		$("#task_start_time").val(startTime);
		//默认任务结束时间
		var endYear = now.getFullYear();
		//var endMonth = now.getMonth();
		var endMonth = now.getMonth()+1;
		//if(endMonth==0){endMonth=12;endYear=endYear-1;}
		endMonth = endMonth>9?endMonth:"0"+endMonth;
		// var myDate = new Date(endYear, endMonth, 0);
        //var endDay =  myDate.getDate();
        var endDay =  now.getDate();
		endDay = endDay>9?endDay:"0"+endDay;
		var endTime = endYear+"-"+endMonth+"-"+endDay;
		$("#task_mend_time").val(endTime);
		//默认任务开始时
		var start = new Date();
		var startYear = start.getFullYear();
		var month = now.getMonth();
		var startMonth = start.getMonth();
				if(startMonth==0){startMonth=12;startYear=startYear-1;}
		
		startMonth = startMonth>9?startMonth:"0"+startMonth;
		var startDay = '01';
		var startTime = startYear+"-"+startMonth+"-"+startDay;
		$("#task_mstart_time").val(startTime);
		//默认任务结束时间
		var endYear = now.getFullYear();
		//var endMonth = now.getMonth();
		var endMonth = now.getMonth()+1;
		//if(endMonth==0){endMonth=12;endYear=endYear-1;}
		endMonth = endMonth>9?endMonth:"0"+endMonth;
		 var myDate = new Date(endYear, endMonth, 0);
        //var endDay =  myDate.getDate();
		//endDay = endDay>9?endDay:"0"+endDay;
		var endDay =  now.getDate();
		endDay = endDay>9?endDay:"0"+endDay;
		var endTime = endYear+"-"+endMonth+"-"+endDay;
		$("#task_aend_time").val(endTime);
		//默认任务开始时
		var start = new Date();
		var startYear = start.getFullYear();
		var month = now.getMonth();
		var startMonth = start.getMonth();
				if(startMonth==0){startMonth=12;startYear=startYear-1;}
		
		startMonth = startMonth>9?startMonth:"0"+startMonth;
		var startDay = '01';
		var startTime = startYear+"-"+startMonth+"-"+startDay;
		$("#task_astart_time").val(startTime);
		//默认任务结束时间
		var endYear = now.getFullYear();
		var endMonth = now.getMonth();
		var endMonth = now.getMonth()+1;
		//if(endMonth==0){endMonth=12;endYear=endYear-1;}
		endMonth = endMonth>9?endMonth:"0"+endMonth;
		 var myDate = new Date(endYear, endMonth, 0);
       // var endDay =  myDate.getDate();
		//endDay = endDay>9?endDay:"0"+endDay;
		 var endDay =  now.getDate();
		 endDay = endDay>9?endDay:"0"+endDay;
		var endTime = endYear+"-"+endMonth+"-"+endDay;
		$("#task_pend_time").val(endTime);
		//默认任务开始时
		var start = new Date();
		var startYear = start.getFullYear();
		var month = now.getMonth();
		var startMonth = start.getMonth();
				if(startMonth==0){startMonth=12;startYear=startYear-1;}
		
		startMonth = startMonth>9?startMonth:"0"+startMonth;
		var startDay = '01';
		var startTime = startYear+"-"+startMonth+"-"+startDay;
		$("#task_pstart_time").val(startTime);
	}
		//获取地市
	function getAreaList() {
		 var areaId=2; 
		$.ajax({
			type : 'POST',
			url : webPath + "General/getSonAreaList.do",
			data : {
				areaId : areaId
			},
			dataType : 'json',
			success : function(json) 
			{
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				var result = json.sonAreaList;
				$("select[name='area_id'] option").remove();
				$("select[name='area_id']").append("<option value=''>请选择</option>");
				if( true ==isAdmin1 || isAdmin1 =='true'){
					for ( var i = 0; i < result.length; i++) {
							$("select[name='area_id']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}					
				}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true') {
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${areaId}){
							$("select[name='area_id']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}					
					}
				}else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${areaId}){
							$("select[name='area_id']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}						
					}
				}	
			}
		});
	}
		
		
		
		     	//获取区域
		function getSonAreaList() {
			var areaId = $("#area_id").val();
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{
					/* var result = json.sonAreaList;
					$("select[name='SON_AREA_ID'] option").remove();
					$("select[name='SON_AREA_ID']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
					} */
					var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
					var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
					var isAdmin1 ='${CABLE_ADMIN}';
					var result = json.sonAreaList;
					$("select[name='SON_AREA_ID'] option").remove();
					$("select[name='SON_AREA_ID']").append("<option value=''>请选择</option>");
					if(true ==isAdmin1 || isAdmin1 =='true'){	
						for ( var i = 0; i < result.length; i++) {					
							$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");												
						}
					}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true'){
						for ( var i = 0; i < result.length; i++) {
							$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
						}
					}else{
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='SON_AREA_ID'] option").remove();
								$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}						
						}
					}
				}
			});
		}
		
		function reform(){			
			$("input[name='START_TIME']").val('');
			$("select[name='SON_AREA_ID']").val('');
			$("input[name='COMPLETE_TIME']").val('');
			$("select[name='area_id']").val('');
		};
		
	
		//周期性检查上报
		function show(AREAID){
		   var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 0;
		   var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }	
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time+"&WLJB_ID="+WLJB_ID);
		};
		//周期性检查上报后派发
		function show1(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 1;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&WLJB_ID="+WLJB_ID);
		};
		//周期性检查回单
		function show2(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 2;
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }	
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time+"&WLJB_ID="+WLJB_ID);
		};
		//周期性检查上报后审核
		function show3(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 3;
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }	
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
		   var task_astart_time=$('#task_astart_time').val();//审核开始时间
		   var task_aend_time=$('#task_aend_time').val();//回单结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time
				+"&task_astart_time="+task_astart_time+"&task_aend_time="+task_aend_time+"&WLJB_ID="+WLJB_ID);
		};
		//一键反馈上报
		function show4(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 4;
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }	
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time+"&WLJB_ID="+WLJB_ID);
		};
		//一键反馈上报后派发
		function show5(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 5;
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }	
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&WLJB_ID="+WLJB_ID);
		};
		//一键反馈上报后回单
		function show6(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 6;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time+"&WLJB_ID="+WLJB_ID);
		};
		//一键反馈上报后审核
		function show7(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 7;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
		   var task_astart_time=$('#task_astart_time').val();//审核开始时间
		   var task_aend_time=$('#task_aend_time').val();//回单结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time
				+"&task_astart_time="+task_astart_time+"&task_aend_time="+task_aend_time+"&WLJB_ID="+WLJB_ID);
		};
		//不预告上报
		function show8(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 8;
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }	
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time+"&WLJB_ID="+WLJB_ID);
		};
		//不预告上报后派发
		function show9(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 9;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&WLJB_ID="+WLJB_ID);
		};
		//不预告上报后回单
		function show10(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 10;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time+"&WLJB_ID="+WLJB_ID);
		};
		//不预告上报后审核
		function show11(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 11;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
		   var task_astart_time=$('#task_astart_time').val();//审核开始时间
		   var task_aend_time=$('#task_aend_time').val();//回单结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time
				+"&task_astart_time="+task_astart_time+"&task_aend_time="+task_aend_time+"&WLJB_ID="+WLJB_ID);
		};
		//上报整改端子数
		function show12(AREAID){
			var portList="端子清单";
		   myCloseTab(portList);
		   var type = 12;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
			addTab(portList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time+"&WLJB_ID="+WLJB_ID);
		};
		//派发整改端子数
		function show13(AREAID){
			var portList="端子清单";
		   myCloseTab(portList);
		   var type = 13;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
			addTab(portList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&WLJB_ID="+WLJB_ID);
		};
		//整改回单端子数
		function show14(AREAID){
			var portList="端子清单";
		   myCloseTab(portList);
		   var type = 14;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
			var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间		                  
			addTab(portList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time+"&WLJB_ID="+WLJB_ID);
		};
		//整改成功端子数
		function show15(AREAID){
			var portList="端子清单";
		   myCloseTab(portList);
		   var type = 15;	
		   var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
		   var task_astart_time=$('#task_astart_time').val();//审核开始时间
		   var task_aend_time=$('#task_aend_time').val();//回单结束时间
			addTab(portList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time
				+"&task_astart_time="+task_astart_time+"&task_aend_time="+task_aend_time+"&WLJB_ID="+WLJB_ID);
		};
		
		//周期性检查上报后审核不通过
		function show16(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 16;
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }	
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
		   var task_astart_time=$('#task_astart_time').val();//审核开始时间
		   var task_aend_time=$('#task_aend_time').val();//回单结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time
				+"&task_astart_time="+task_astart_time+"&task_aend_time="+task_aend_time+"&WLJB_ID="+WLJB_ID);
		};
		//一键反馈上报后审核不通过
		function show17(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 17;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
		   var task_astart_time=$('#task_astart_time').val();//审核开始时间
		   var task_aend_time=$('#task_aend_time').val();//回单结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time
				+"&task_astart_time="+task_astart_time+"&task_aend_time="+task_aend_time+"&WLJB_ID="+WLJB_ID);
		};
		//不预告上报后审核不通过
		function show18(AREAID){
			var eqpList="设备清单";
		   myCloseTab(eqpList);
		   var type = 18;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
		   var task_pstart_time=$('#task_pstart_time').val();//派发开始时间
		   var task_pend_time=$('#task_pend_time').val();//派发结束时间
		   var task_mstart_time=$('#task_mstart_time').val();//回单开始时间
		   var task_mend_time=$('#task_mend_time').val();//回单结束时间
		   var task_astart_time=$('#task_astart_time').val();//审核开始时间
		   var task_aend_time=$('#task_aend_time').val();//回单结束时间
			addTab(eqpList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time
				+"&task_pstart_time="+task_pstart_time+"&task_pend_time="+task_pend_time+"&task_mstart_time="+task_mstart_time+"&task_mend_time="+task_mend_time
				+"&task_astart_time="+task_astart_time+"&task_aend_time="+task_aend_time+"&WLJB_ID="+WLJB_ID);
		};
		//发现错误端子数
		function show22(AREAID){
			var portList="端子清单";
		   myCloseTab(portList);
		   var type = 22;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
			addTab(portList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time+"&WLJB_ID="+WLJB_ID);
		};
		//一键改端子数
		function show23(AREAID){
			var portList="端子清单";
		   myCloseTab(portList);
		   var type = 23;	
		    var WLJB_ID = ''; 
		   var wljb_id=$("#wljb_id").val();
		   if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
		    }
		   var task_start_time=$('#task_start_time').val();//上报开始时间
		   var task_end_time=$('#task_end_time').val();//上报结束时间
			addTab(portList, "<%=path%>/ReformOrderReport/queryZgEqp.do?AREAID="+AREAID+"&type="+type+"&task_start_time="+task_start_time+"&task_end_time="+task_end_time+"&WLJB_ID="+WLJB_ID);
		};
		<%-- function show1(AREAID){
		   var type = 1;
			   var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME);
		};
		function show2(AREAID){
		   var type = 2;
		   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME);
	};
		function show3(AREAID){
		   var type = 3;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME);
	};
		function show4(AREAID){
		   var type = 4;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME);
	};
	function show7(AREAID){
		   var type = 5;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME);
	};
	function show8(AREAID){
		   var type = 6;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME);
	};
	function show9(AREAID){
		   var type = 7;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME);
	};
	function show5(AREAID){
		   var type = 0;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			addTab("端子清单", "<%=path%>/CheckQualityReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME);
	};
	function show6(AREAID){
		   var type = 1;  
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			addTab("端子清单", "<%=path%>/CheckQualityReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME);
	}; --%>
	
		
		function exportExcel() {
		
		if($("#task_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			/* if($("#task_pstart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_pend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			if($("#task_mstart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_mend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			if($("#task_astart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_aend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			} */
			window.open(webPath
					+ "ReformOrderReport/exportExcel.do"
					+ getParamsForDownloadLocal('form'));
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
			o
					.find("input,select")
					.each(
							function() {
								var o = $(this);
								var tag = this.tagName.toLowerCase();
								var name = o.attr("name");
								if (name) {
									if (tag == "select") {
										if (o.find("option:selected").val() == 'all'
												|| o.find("option:selected")
														.val() == '') {
											res = res + "&" + name + "=";
										} else {
											res = res
													+ "&"
													+ name
													+ "="
													+ o.find("option:selected")
															.val();
										}

									} else if (tag == "input") {
										res = res + "&" + name + "=" + o.val();
									}
								}
							});
			return res;
		}
		
		function searchDataByCity() {
		    
			if($("#task_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			/* if($("#task_pstart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_pend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			if($("#task_mstart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_mend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			if($("#task_astart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_aend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			} */
			var AREA_ID = $("select[name='area_id']").val();
		
		
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			
			var static_month = $("input[name='static_month']").val();
		
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var son_area = $("#son_area").val();
			var MSTART_TIME = $("input[name='MSTART_TIME']").val();
			var MCOMPLETE_TIME = $("input[name='MCOMPLETE_TIME']").val();
			var ASTART_TIME = $("input[name='ASTART_TIME']").val();
			var ACOMPLETE_TIME = $("input[name='ACOMPLETE_TIME']").val();
			var WLJB_ID = ''; 
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			var obj ={
					START_TIME : START_TIME,
					COMPLETE_TIME:COMPLETE_TIME,
					AREA_ID:AREA_ID,
					static_month:static_month,
					PSTART_TIME:PSTART_TIME,
					son_area:son_area,
					PCOMPLETE_TIME:PCOMPLETE_TIME,
					MCOMPLETE_TIME:MCOMPLETE_TIME,
					ACOMPLETE_TIME:ACOMPLETE_TIME,
					ASTART_TIME:ASTART_TIME,
					MSTART_TIME:MSTART_TIME,
					WLJB_ID:WLJB_ID
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "ReformOrderReport/ReformOrderReportByCity.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'NAME',
					title : '地市',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'AREAID',
					title : '地市',
					width : "14%",
					rowspan : '2', 
				    align : 'center',
				    hidden:true
				}, {
					field : '周期性检查总数',
					title : '周期性检查上报总数',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
									return value;
						}else{
			        		return "<a href=\"javascript:show("+ row.AREAID + ");\">"+value+"</a>";
			        	}
		        	}
		        },{
					field : '周期性检查派发',
					title : '周期性检查上报后派发',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
									return value;
						}else{
			        		return "<a href=\"javascript:show1("+ row.AREAID + ");\">"+value+"</a>";
			        	}
		        	}
		        },{
					field : '周期性检查已整治',
					title : '周期性检查已回单',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show2("+ row.AREAID + ");\">"+value+"</a>";
		       	   		}
		       	    } 
				},{
					field : '周期性检查未通过审核',
					title : '周期性检查未通过审核',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
									return value;
						}else{
		        			return "<a href=\"javascript:show16("+ row.AREAID + ");\">"+value+"</a>";
		        		}
		        	} 
				},{
					field : '周期性检查通过审核',
					title : '周期性检查通过审核',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
									return value;
						}else{
		        			return "<a href=\"javascript:show3("+ row.AREAID + ");\">"+value+"</a>";
		        		}
		        	} 
				},{
					field : '一键反馈总数',
					title : '一键反馈上报总数',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show4("+ row.AREAID + ");\">"+value+"</a>";
		      		   }
		      	    } 
				}, {
					field : '一键反馈派发',
					title : '一键反馈上报后派发',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show5("+ row.AREAID + ");\">"+value+"</a>";
		       			}
		       		} 
				},{
					field : '一键反馈已整治',
					title : '一键反馈已回单',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show6("+ row.AREAID + ");\">"+value+"</a>";
		      	   		}
		      	    } 
				}, {
					field : '一键反馈未通过审核',
					title : '一键反馈上报未通过审核',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show17("+ row.AREAID + ");\">"+value+"</a>";
		       			}
		       		} 
				},{
					field : '一键反馈通过审核',
					title : '一键反馈上报通过审核',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show7("+ row.AREAID + ");\">"+value+"</a>";
		       			}
		       		} 
				},{
					field : '不可预告抽查总数',
					title : '不可预告抽查上报总数',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show8("+ row.AREAID + ");\">"+value+"</a>";
		       	   		}
		       	    } 
				},{
					field : '不可预告抽查派发',
					title : '不可预告抽查上报后派发',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show9("+ row.AREAID + ");\">"+value+"</a>";
		       			}
		       		} 
				}, {
					field : '不可预告抽查已整治',
					title : '不可预告抽查已回单',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show10("+ row.AREAID + ");\">"+value+"</a>";
		       		   }
		       	    } 
				},{
					field : '不可预告抽查未通过审核',
					title : '不可预告抽查未通过审核',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show18("+ row.AREAID + ");\">"+value+"</a>";
		       			}
		       		} 
				},{
					field : '不可预告抽查通过审核',
					title : '不可预告抽查通过审核',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show11("+ row.AREAID + ");\">"+value+"</a>";
		       			}
		       		} 
				},{
					field : '发现错误端子数',
					title : '发现错误端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show22("+ row.AREAID + ");\">"+value+"</a>";
		       	    	}
		       	    } 
				},{
					field : '检查人员一键改端子数',
					title : '检查人员一键改端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show23("+ row.AREAID + ");\">"+value+"</a>";
		       	    	}
		       	    } 
				},{
					field : '上报整改端子数',
					title : '上报整改端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show12("+ row.AREAID + ");\">"+value+"</a>";
		       	    	}
		       	    } 
				},{
					field : '派发整改端子数',
					title : '派发整改端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
							return value;
						}else{
		        			return "<a href=\"javascript:show13("+ row.AREAID + ");\">"+value+"</a>";		       		       
		 		        }
		        	} 
				},{
					field : '整改回单端子数',
					title : '整改回单端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show14("+ row.AREAID + ");\">"+value+"</a>";
		       			}
		       	     } 
				},{
					field : '整改成功端子数',
					title : '整改成功端子数',
					width : "8%",
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if(row.NAME=="合计"){
								return value;
						}else{
		        			return "<a href=\"javascript:show15("+ row.AREAID + ");\">"+value+"</a>";
		      		 	}
		        	} 
				}, {
					field : '整治派发率按设备',
					title : '整治派发率（按设备）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治回单率按设备',
					title : '整治回单率（按设备）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治完成率按设备',
					title : '整治完成率（按设备）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治派发率按端子',
					title : '整治派发率（按端子）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治回单率按端子',
					title : '整治回单率（按端子）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '整治完成率按端子',
					title : '整治完成率（按端子）',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '2018年整改率',
					title : '2018年整改率',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}
				] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				console.log('obj:'+obj);
					$(this).datagrid("fixRownumber");
					var rows = $('#dg').datagrid('getRows')//获取当前的数据行
            var ZQSUM = 0//计算listprice的总和
            ,ZQPF=0,ZQZZ=0,ZQSHWTG=0,ZQSH=0,YJSUM=0,YJPF=0,YJZZ=0,YJSHWTG=0,YJSH=0,BKSUM=0,BKPF=0,BKZZ=0,BKSHWTG=0,BKSH=0,FXDZ=0,YJGDZ=0,SBDZ=0,PFDZ=0,HDDZ=0,SHDZ=0;//统计总和
            
            for (var i = 0; i < rows.length; i++) {
                ZQSUM += rows[i]['周期性检查总数'];
                ZQPF += rows[i]['周期性检查派发'];
                ZQZZ += rows[i]['周期性检查已整治'];
                ZQSHWTG += rows[i]['周期性检查未通过审核'];
                ZQSH += rows[i]['周期性检查通过审核'];
                YJSUM += rows[i]['一键反馈总数'];
                YJPF += rows[i]['一键反馈派发'];
                YJZZ += rows[i]['一键反馈已整治'];
                YJSHWTG += rows[i]['一键反馈未通过审核'];
                YJSH += rows[i]['一键反馈通过审核'];
                BKSUM += rows[i]['不可预告抽查总数'];
                BKPF += rows[i]['不可预告抽查派发'];
                BKZZ += rows[i]['不可预告抽查已整治'];
                BKSHWTG += rows[i]['不可预告抽查未通过审核'];
                BKSH += rows[i]['不可预告抽查通过审核'];
                FXDZ += rows[i]['发现错误端子数'];
                YJGDZ += rows[i]['检查人员一键改端子数'];
                SBDZ += rows[i]['上报整改端子数'];
                PFDZ += rows[i]['派发整改端子数'];
                HDDZ += rows[i]['整改回单端子数'];
                SHDZ += rows[i]['整改成功端子数'];
               
            }
            //新增一行显示统计信息
            
                if(ZQSUM+YJSUM+BKSUM==0){
                	 var PFLSB= 0;
                    }else{
                    	var PFLSB=(ZQPF+YJPF+BKPF)/(ZQSUM+YJSUM+BKSUM);
                        }
            	if(ZQPF+YJPF+BKPF==0){
				 var  HDLSB=0;
				 var  SHLSB=0;}
				else{
					var HDLSB=(ZQZZ+YJZZ+BKZZ)/(ZQPF+YJPF+BKPF);
					var SHLSB=(ZQSH+YJSH+BKSH)/(ZQPF+YJPF+BKPF);
					}

				
				if(SBDZ==0){
					var  PFLDZ=0;}
				else{
				var PFLDZ=(PFDZ)/SBDZ;
				}
				if( PFDZ==0){
					var HDLDZ = 0;
					var SHLDZ = 0;}
				else{
					var HDLDZ=(HDDZ)/PFDZ;
					var SHLDZ=(SHDZ)/PFDZ;
					}
				if(FXDZ==0){
					var YJGLDZ=0;
				}else{
					var YJGLDZ=(YJGDZ+SHDZ)/FXDZ;
				}
				
				
           $('#dg').datagrid('appendRow', { itemid: '<b>统计：</b>', NAME:'合计',周期性检查总数:  ZQSUM,周期性检查派发: ZQPF, 周期性检查已整治: ZQZZ,周期性检查未通过审核: ZQSHWTG,  周期性检查通过审核: ZQSH, 一键反馈总数: YJSUM, 一键反馈派发: YJPF, 一键反馈已整治: YJZZ, 一键反馈未通过审核: YJSHWTG,一键反馈通过审核: YJSH, 不可预告抽查总数: BKSUM, 不可预告抽查派发: BKPF, 不可预告抽查已整治: BKZZ, 不可预告抽查未通过审核: BKSHWTG, 不可预告抽查通过审核: BKSH,发现错误端子数: FXDZ,检查人员一键改端子数: YJGDZ,上报整改端子数: SBDZ,派发整改端子数: PFDZ,整改回单端子数: HDDZ,整改成功端子数:SHDZ,整治派发率按设备:Math.floor(PFLSB * 10000) / 100+'%', 整治回单率按设备:Math.floor(HDLSB * 10000) / 100+'%', 整治完成率按设备:Math.floor(SHLSB * 10000) / 100+'%', 整治派发率按端子:Math.floor(PFLDZ * 10000) / 100+'%', 整治回单率按端子:Math.floor(HDLDZ * 10000) / 100+'%', 
           		整治完成率按端子:Math.floor(SHLDZ * 10000) / 100+'%','2018年整改率': Math.round(YJGLDZ * 10000) / 100+'%'});	
				
				}
			});
			
			
			
		};
			function exportExcelByCity() {
		    
			if($("#task_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			/* if($("#task_pstart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_pend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			if($("#task_mstart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_mend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			if($("#task_astart_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_aend_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			} */
		
			window.open(webPath
					+ "ReformOrderReport/exportExcelByCity.do"
					+ getParamsForDownloadLocal('form'));
		
			}
			
			function showDiv(){
			$('#exchange').toggle();
		}
		function getChoice(){
			var arr=document.getElementsByName("checkbox");
			var values="";
			var contents="";
			for(i=0;i<arr.length;i++){
				if(arr[i].checked){
				var value = arr[i].value;
				var content= arr[i].nextSibling.nodeValue;
				values=values+value+",";
				contents=contents+content+",";			
				}
			}
			values=values.substring(0,values.length-1);
			contents=contents.substring(0,contents.length-1);
			$('#wljb_id').val(values);
			$('#wljb_content').val(contents);
			$('#exchange').css('display', 'none');
		    
		}
		function removeChoice(){
			$("[name='checkbox']").removeAttr('checked');
			$('#wljb').val('');
			$('#wljb_content').val('');
		}
		
		function myCloseTab(titleList) {  
			if(parent.$('#tabs').tabs('exists',titleList)){
				parent.$('#tabs').tabs('close',titleList);
			}
        } 
			
	</script>
</html>