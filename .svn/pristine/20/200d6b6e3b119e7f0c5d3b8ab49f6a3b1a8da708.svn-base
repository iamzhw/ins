<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>新增</title>

</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formAdd" method="post">
			<table>
				<tr>
					<td>外力点名称：</td>
					<td>
						<div class="condition-text-container">
							<input type="hidden" id="out_site_id" name="out_site_id" />
							<input class="condition-text easyui-validatebox condition"
								type="text" name="outsite_name" id="outsite_name" 
								onClick="get_outsite()"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<!--  
				<tr>
					<td>外力施工点类型：</td>
					<td>
						<div  class="condition-text-container">
							<input type="hidden" id="out_site_type_id" name="out_site_type_id" />
							<input class="condition-text easyui-validatebox condition"
								type="text" name="out_site_type" id="out_site_type" 
								onClick="get_outsite_type()"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				-->
				<tr>
					<td>开始时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="start_date" id="start_date"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " 
								onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="end_date" id="end_date"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " 
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'});"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>看护员：</td>
					<td>
						<div class="condition-text-container">
								<input type="hidden" id="kan_name_id" name="kan_name_id" />
								<input class="condition-text easyui-validatebox condition"
								type="text" name="kan_name" id="kan_name" 
								onClick="get_kan_name()"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						
						</div>
					</td>
				</tr>
				<tr>
					<td>看护员账号：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition" type="text" name="kan_no" id="kan_no_id" disabled="disabled"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>创建时间：</td>
					<td>
						<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
								type="text" name="creation_time" id="creation_time"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " 
								value="${createDate}" disabled="disabled" />
						</div>
					</td>
				</tr>
				<tr>
					<td>创建者：</td>
					<td>
						<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
								type="text" name="creator" id="creator" disabled="disabled" value="${staffNo}"/>
						</div>
					</td>
				</tr>
				<tr>
					<td style="height: 55px;width:58px">
						<input 	type="button" value="新增时间段:" onclick="select_kanhu_shijian()" />
						<input 	type="button" value="删除时间段:" onclick="del_kanhu_shijian()" />
					</td>
					
					<td >
						<div>
							<input 	type="hidden" id="kanhu_shijian_id" name="kanhu_shijian_id"/>
							<textarea disabled="disabled" style="font-size:12px" id="kanhu_shijian" name="kanhu_shijian" cols=20 rows=3></textarea>
						</div>
					</td>
				</tr>
				
				
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			
		</div>
	</div>
</body>
</html>