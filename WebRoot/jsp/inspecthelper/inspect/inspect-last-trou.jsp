<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>查看照片</title>
</head>
<body>

	<table  class="main_table" id="content_tablee" style="width: 100%;">		     
		<tr> 
			<td class="head"> 日期</td>
			<td class="head"> 问题类别</td> 
			<td class="head"> 问题描述</td> 
			<td class="head"> 处理结果</td> 
		</tr> 
		<% List<Map> list1=(List<Map>)request.getAttribute("rows");
				if(null!=list1){ 
					for(int k=0;k<list1.size();k++){
					%>
					<tr>
				<td class="textMiddle"> 
					<%=list1.get(k).get("HAPPENDATE") %>
				</td> 
					<%if(list1.get(k).get("TYPE").equals("1")){ %>
					<td class="textMiddle"> 
						已处理
					</td> 
					<%}else{ %>
				 	<td class="textMiddle"> 
						问题上报
					</td>
					<%}%>
				<td class="textMiddle"> 
					<%=list1.get(k).get("REMARKS") %>
				</td>	        
				<td class="textMiddle"> 
				</td>
			</tr> 
					
					<%
					}
				}%>
</table>
	<table>
			<tr>
					<td nowrap="nowrap" width="5%" >
						查看照片：
					</td>

					<td >

						<div style="height: 145px; width: 95%; overflow-y: scroll; border: 1px solid #aaa;">
							<% int i = 0; 
							List<Map> list=(List<Map>)request.getAttribute("photoL");
							if(null!=list){
							for(int j=0;j<list.size();j++){ %>
							<% 
									if(i%3==0) out.print("<div>");
								%>
							<div style="margin: 0px 5px 5px 0; float: left; border: 1px solid #bbb; padding: 2px;">
								<img onclick="openImg('<%=list.get(j).get("PHOTO_PATH") %>')"
										src="<%=list.get(j).get("MICRO_PHOTO_PATH") %>"
										style="cursor: pointer;" height="120" border="0" />
							</div>
							<%
									i++;
									if(i%3==0){
										out.print("<div style=\"clear: both\"></div></div>");
									}
								%>
							<% }
							}
							%>
							
							
							
						</div>

					</td>
</tr>
	 </table>
	<script type="text/javascript">
		function openImg(url){
		window.open (url, "newwindow", "height=340, width=500, toolbar=no, top=200, left=400, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
	}
		
	</script>
</body>
</html>