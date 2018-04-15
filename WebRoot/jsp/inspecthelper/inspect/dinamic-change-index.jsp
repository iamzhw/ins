<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<%@include file="../../util/head.jsp"%>
	</head>
	<body style="width: 1150px;" >
		<form id="query-form" name="query-form" method="post" enctype="multipart/form-data">
		<input type="hidden" id="orderId" name="orderId" value="${orderId }"/>
		<input type="hidden" id="equipmentId" name="equipmentId" value="${equipmentId }"/>
			<table width="100%" style="margin-left:10px;margin-top: 5px">
			<tr>
						<td align="left" width="15%">
							时间选择：
						</td>
						<td width="25%" colspan="2" align="left">
							<div class="condition-text-container">
							<select name="date" id="paras_date" class="condition-select condition">
										<option value="">----------请选择----------</option>
											<option value="30">1个月</option>
											<option value="60">2个月</option>
											<option value="90">3个月</option>
											<option value="120">4个月</option>
											<option value="150">5个月</option>
											<option value="180">6个月</option>
											<option value="lastCheck">上次巡检至今</option>
									</select>
							</div>
						</td>
					</tr>
					<tr><td>上传Excel</td><td>
										<input type="file" name="excel" id ="excel"/>
									</td><td width="50%"><div class="btn-operation-container">
							<div style="float: left;" class="btn-operation btnzhuan" name="Submit" value="查　询" onClick="queryDinamic();">查询</div>
							<div style="float: left;" class="btn-operation btnzhuan" onClick="btnupload();">上传Excel</div>
							<div style="float: left;" class="btn-operation btnzhuan" onClick="btnexport();">导出Excel</div>
							</div></td>
							</tr>
				</table>
		</form>
		<table id="dg_dinamic" ></table>
		<div class="btn-operation-container" align="center" style="text-align: center;margin-left: 400px;display: none;" id="showbutton" >
		<div style="float: left;" class="btn-operation btnzhuan" onClick="OkBtn();">确定</div>
		<div style="float: left;" class="btn-operation btnzhuan" onClick="cancelBtn();">取消</div>
		</div>
		<script type="text/javascript">
		function queryDinamic(){
			var date = $("#paras_date").val();
			var equipmentId = $("#equipmentId").val(); 
			var orderId = $("#orderId").val();
			var obj = {
					//taskName : taskName,
					orderId : orderId,
					equipmentId:equipmentId,
					date : date
			};
			//return;
			$('#dg_dinamic').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//toolbar : '#tb',
				url : webPath + "inspect/dinamicChangeTable.do",
				queryParams : obj,
				method : 'post',
				//loadMsg:'数据加载中.....',
				rownumbers : false,
				singleSelect : false,
				columns : [ [ {
					field : 'ID',
					title : '工程属性',
					checkbox:true
				},{
					field : '光路编码_A',
					title : '原光路编码',
					width : "150",
					align : 'center'
				}, {
					field : '光路编码_B',
					title : '动态数据',
					width : "150",
					align : 'center'
				}, {
					field : 'NO',
					title : '实时光路',
					width : "150",
					align : 'center',
					formatter: function(value,row,index){
						if(row.NO!=row.光路编码_B){
								return 	"<font color='#FF4500'>"+row.NO+"</font>";
						}
					}
				}, {
					field : '端口_B',
					title : '端口',
					width : "70",
					align : 'center'
				}, {
					field : 'INSERTDATE',
					title : '比对时间',
					width : "100",
					align : 'center'
				}, {
					field : 'STATE_A',
					title : '1级状态',
					width : "100",
					align : 'center'
				}, {
					field : 'STATE_B',
					title : '2级状态',
					width : "100",
					align : 'center'
				}, {
					field : 'STATE_C',
					title : '3级状态',
					width : "100",
					align : 'center'
				}, {
					field : 'STATE_D',
					title : '4级状态',
					width : "100",
					align : 'center'
				},{
					field : 'button',
					title : '操作',
					width : "70",
					align : 'center',
					formatter: function(value,row,index){
								return 	"<input type='button' class='btndesc' value='描述' onclick='btndesc(\""+row.CHANGE_STATUS+"\",\""+row.INSERTDATE++"\",\""+row.端口_B+"\")'/>";
					}
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$("#showbutton").show();
				}
			});
					
		};
	function btnexport(){
			//var date = $("#paras_date").val();
			//var equipmentId = $("#equipmentId").val(); 
			//var orderId = $("#orderId").val();
			//var url =  webPath+"inspect/exportCDuanExcel.do?date="+date+"&equipmentId="+equipmentId+"&orderId="+orderId;
		var url =  webPath+"inspect/exportCDuanExcel.do";
		$("#query-form").attr("action",url);
		$("#query-form").submit();
	}	
	function btnupload(){
	var excel = document.getElementById("excel").value; 
					if(excel.length==0){
						alert("请选择正确的Excel文件！");
						return false;
					}
			var url = webPath+"inspect/analysisODFDinamicExcel.do";				
			$('#query-form').form("submit",{
						url : url,
						success : function(json) {
							if(json=="true"){
								alert('上传成功!');
							}else{
								alert('上传失败！');
							}
						},
					error:function (){
						alert('上传文件错误！');
					}
					});
	}
function cancelBtn(){
	close();
}
function btndesc(id,changeStatus,equCode){
					var equipmentId = $("#equipmentId").val(); 
					var orderId = $("#orderId").val();	
					var url = webPath+"inspect/dinamicTrou.do?changeStatus="+changeStatus+"&orderId="+orderId+"&equipmentId="+equipmentId+"&equCode="+equCode+"&id="+id;
					//alert("url:"+url);
					var obj =new Object();
					window.showModalDialog(url ,obj , "status=no;center=yes;dialogWidth=600px;dialogHeight=400px" );
					queryDinamic();
				}	
function OkBtn(){
	var checkedItems = $('#dg_dinamic').datagrid('getChecked');
	var trueIds = "";
	if(checkedItems.length==1){
	trueIds=item.ID;
	}else{
	$.each(checkedItems, function(index, item){
	trueIds += ","+item.ID;
	//names.push(item.productname);
	})
	};   
	var url = webPath="inspect/updateDinamicChange.do?trueIds="+trueIds;
	$('#query-form').form("submit",{
						url : url,
						success : function(json) {
							if(json=="true"){
								alert('检查成功!');
							}else{
								alert('检查失败！');
							}
						},
					error:function (){
						alert('检查错误！');
					}
					});
}
</script>
	</body>
</html>
