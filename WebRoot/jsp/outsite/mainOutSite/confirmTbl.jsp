<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<style type="text/css">

</style>

<script type="text/javascript">
	
</script>

<head>
<%@include file="../../util/head.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/print.css" media="print">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/iframe.css" media="screen">
<title>外力点编辑</title>
</head>
<body>
<!--startprint-->
	<div id="printObj">
	<center><h2>方案确认表格</h2></center>
	<table>
			<c:if test="${!empty model }">		
				<tr><td style="border: 1px black solid;" width="20" rowspan='11'>外力影响点详情</td><td style="border: 1px black solid;">外力点名称</td><td style="border: 1px black solid;"><c:out value="${model[0].SITE_NAME }" /></td><td style="border: 1px black solid;">外力点影响等级</td><td style="border: 1px black solid;"><c:out value="${model[0].SITE_DANGER_LEVEL }" /></td></tr>
				<tr><td style="border: 1px black solid;">受影响光缆</td><td style="border: 1px black solid;">${model[0].CABLE_NAME}</td><td style="border: 1px black solid;">光缆等级</td><td style="border: 1px black solid;">${model[0].PROTECT_GRADE}</td></tr>
				<tr><td style="border: 1px black solid;">中继段名称</td><td style="border: 1px black solid;">${model[0].RELAY_NAME}</td><td style="border: 1px black solid;">影响段落</td><td style="border: 1px black solid;">${model[0].LINE_PART}</td></tr>
				<tr><td style="border: 1px black solid;">外力点责任人</td><td style="border: 1px black solid;">${model[0].FIBER_EPONSIBLE_BY}</td><td style="border: 1px black solid;">上报时间</td><td style="border: 1px black solid;">${model[0].TIMETUP}</td></tr>
				<tr><td style="border: 1px black solid;">施工单位</td>
					<td style="border: 1px black solid;" colspan='3'>${model[0].CON_COMPANY}
						<c:if test="${!empty model[0].CON_REPONSIBLE_BY}">
							(${model[0].CON_REPONSIBLE_BY}
						</c:if>
						<c:if test="${!empty model[0].CON_REPONSIBLE_BY_TEL}">
							${model[0].CON_REPONSIBLE_BY_TEL})
						</c:if>
					</td>
				</tr>
				<tr><td style="border: 1px black solid;">施工内容及影响</td><td style="border: 1px black solid;" colspan='3'>${model[0].CON_CONTENT}</td></tr>
				
				<tr><td style="border: 1px black solid;">线路十米范围内是否有机械</td><td style="border: 1px black solid;" colspan='3' style="border: 1px #DDD solid;border-color: black;"><c:if test="${model[0].IS_MACHAINE eq 1 }">是</c:if><c:if test="${model[0].IS_MACHAINE eq 0 }">否</c:if></td></tr>
				<tr><td style="border: 1px black solid;">影响段落是否有直埋光缆</td><td style="border: 1px black solid;" colspan='3' style="border: 1px #DDD solid;border-color: black;"><c:if test="${model[0].RESIDUAL_CABLE eq 1 }">是</c:if><c:if test="${model[0].RESIDUAL_CABLE eq 0 }">否</c:if></td></tr>
				<tr><td style="border: 1px black solid;">光缆中断是否影响业务</td><td style="border: 1px black solid;" colspan='3' style="border: 1px #DDD solid;border-color: black;"><c:if test="${model[0].EFFECT_SERVICE eq 1 }">是</c:if><c:if test="${model[0].EFFECT_SERVICE eq 0 }">否</c:if></td></tr>
				<tr><td style="border: 1px black solid;">现场是否需要安排看护</td><td style="border: 1px black solid;" style="border: 1px #DDD solid;border-color: black;">否</td><td style="border: 1px black solid;">看护人及电话</td><td style="border: 1px black solid;" style="border: 1px #DDD solid;border-color: black;">无</td></tr>
				<tr><td style="border: 1px black solid;">现场采取的措施</td><td style="border: 1px black solid;" colspan='3'>${model[0].SCENE_MEASURE}</td></tr>
				
				
				<tr><td style="border: 1px black solid;" width="20" rowspan='2'>防护方案内容</td><td style="border: 1px black solid;">外力点防护方案</td><td style="border: 1px black solid;" colspan='3'>${model[0].MS_CONTENT}</td></tr>
				<tr><td style="border: 1px black solid;">方案制定人</td><td style="border: 1px black solid;">${model[0].SCHEMECREATOR}</td><td style="border: 1px black solid;">制定时间</td><td style="border: 1px black solid;">${model[0].CREATION_TIME}</td></tr>
				
				<c:forEach var="item" items="${model }">
				<c:if test="${item.FLOW_STATUS eq 2 }">
					<tr><td style="border: 1px black solid;" rowspan="2" width="20">防护方案确认</td><td style="border: 1px black solid;">县公司分管领导确认意见</td><td style="border: 1px black solid;">${item.OPINON }</td><td style="border: 1px black solid;">是否通过</td><td style="border: 1px black solid;"><c:if test="${item.REVIEW_STATUS eq 1 }">通过</c:if><c:if test="${item.REVIEW_STATUS eq 0 }">驳回</c:if></td></tr>
					<tr><td style="border: 1px black solid;">确认人签字及确认时间</td>
						<td style="border: 1px black solid;">
							<c:if test="${!empty item.CONFIRMNAME }">
								${item.CONFIRMNAME }&nbsp;&nbsp; ${item.REVIEW_TIME }	
							</c:if>
						</td>
						<td style="border: 1px black solid;">是否现场确认方案 </td><td style="border: 1px black solid;"><c:if test="${item.IS_SCENCE eq 1 }">是</c:if><c:if test="${item.IS_SCENCE eq 0 }">否</c:if></td>
					</tr>
				</c:if>
				<c:if test="${item.FLOW_STATUS eq 3 }">
					<tr><td style="border: 1px black solid;" rowspan="2" width="20">防护方案确认</td><td style="border: 1px black solid;">市公司分管领导确认意见</td><td style="border: 1px black solid;">${item.OPINON }</td><td style="border: 1px black solid;">是否通过</td><td style="border: 1px black solid;"><c:if test="${item.REVIEW_STATUS eq 1 }">通过</c:if><c:if test="${item.REVIEW_STATUS eq 0 }">驳回</c:if></td></tr>
					<tr><td style="border: 1px black solid;">确认人签字及确认时间</td>
						<td style="border: 1px black solid;">
							<c:if test="${!empty item.CONFIRMNAME }">
								${item.CONFIRMNAME }&nbsp;&nbsp; ${item.REVIEW_TIME }	
							</c:if>
						</td>
						<td style="border: 1px black solid;">是否现场确认方案 </td><td style="border: 1px black solid;"><c:if test="${item.IS_SCENCE eq 1 }">是</c:if><c:if test="${item.IS_SCENCE eq 0 }">否</c:if></td>
					</tr>
				</c:if>
				<c:if test="${item.FLOW_STATUS eq 4 }">
					<tr><td style="border: 1px black solid;" rowspan="2" width="20">防护方案确认</td><td style="border: 1px black solid;">县公司维护部主任确认意见</td><td style="border: 1px black solid;">${item.OPINON }</td><td style="border: 1px black solid;">是否通过</td><td style="border: 1px black solid;"><c:if test="${item.REVIEW_STATUS eq 1 }">通过</c:if><c:if test="${item.REVIEW_STATUS eq 0 }">驳回</c:if></td></tr>
					<tr><td style="border: 1px black solid;">确认人签字及确认时间</td>
						<td style="border: 1px black solid;">
							<c:if test="${!empty item.CONFIRMNAME }">
								${item.CONFIRMNAME }&nbsp;&nbsp; ${item.REVIEW_TIME }	
							</c:if>
						</td>
						<td style="border: 1px black solid;">是否现场确认方案 </td><td style="border: 1px black solid;"><c:if test="${item.IS_SCENCE eq 1 }">是</c:if><c:if test="${item.IS_SCENCE eq 0 }">否</c:if></td>
					</tr>
				</c:if>
				<c:if test="${item.FLOW_STATUS eq 5 }">
					<tr><td style="border: 1px black solid;" rowspan="2" width="20">防护方案确认</td><td style="border: 1px black solid;">市公司部门主管确认意见</td><td style="border: 1px black solid;">${item.OPINON }</td><td style="border: 1px black solid;">是否通过</td><td style="border: 1px black solid;"><c:if test="${item.REVIEW_STATUS eq 1 }">通过</c:if><c:if test="${item.REVIEW_STATUS eq 0 }">驳回</c:if></td></tr>
					<tr><td style="border: 1px black solid;">确认人签字及确认时间</td>
						<td style="border: 1px black solid;">
							<c:if test="${!empty item.CONFIRMNAME }">
								${item.CONFIRMNAME }&nbsp;&nbsp; ${item.REVIEW_TIME }	
							</c:if>
						</td>
						<td style="border: 1px black solid;">是否现场确认方案 </td><td style="border: 1px black solid;"><c:if test="${item.IS_SCENCE eq 1 }">是</c:if><c:if test="${item.IS_SCENCE eq 0 }">否</c:if></td>
					</tr>
				</c:if>
				</c:forEach>
		    </c:if>
	</table>
	</div>
	<!--endprint-->
	
	<div class="btn-operation" onClick="doPrint()" style="margin-left:45%;">打印</div>
</body>
<script type="text/javascript">
function doPrint() {
	$("#printObj").jqprint({
		debug : true, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
		importCSS : true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
		printContainer : true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
// 		operaSupport: true //表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
	});
}


// function preview()         
// {  
// 	bdhtml=window.document.body.innerHTML;//获取当前页的html代码  
// 	sprnstr="<!--startprint-->";//设置打印开始区域  
// 	eprnstr="<!--endprint-->";//设置打印结束区域  
// 	prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+17); //从开始代码向后取html  
	  
// 	prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html  
// 	window.document.body.innerHTML=prnhtml;  
// 	window.print();  
// 	window.document.body.innerHTML=bdhtml;  
// } 

</script>

</html>
