<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<%@include file="../../util/head.jsp"%>

		<title>修该外力点信息</title>

	</head>
	<body>
		<div style="padding: 20px 0 10px 50px">
			<form id="form_update" method="post">
				<table>
					<tr>
						<td>
							外力点名称：
						</td>
						<td>
							<div class="condition-text-container">
								<input type="hidden" id="plan_id" name="plan_id"
									value="${PLAN_ID}" />

								<input type="hidden" id="out_site_id" name="out_site_id"
									value="${OUT_SITE_ID}" />
								<input class="condition-text easyui-validatebox condition"
									type="text" name="outsite_name" id="outsite_name"
									value="${SITE_NAME}" onClick="get_outsite()"
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
						<td>
							开始时间：
						</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="start_date" id="start_date"
									value="${START_DATE}"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' "
									disabled="true "
									onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							结束时间：
						</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="end_date" id="end_date" value="${END_DATE}"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' "
									onClick="WdatePicker({minDate : '#F{$dp.$D(\'start_date\')}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							看护员：
						</td>
						<td>
							<div class="condition-text-container">
								<input type="hidden" id="kan_name_id" name="kan_name_id"
									value="${KAN_NAME_ID}" />
								<input class="condition-text easyui-validatebox condition"
									type="text" name="kan_name" id="kan_name" value="${KAN_NAME}"
									onClick=get_kan_name();
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />

							</div>
						</td>
					</tr>
					<tr>
						<td>
							看护员账号：
						</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="kan_no" id="kan_no_id" value="${KAN_NO}"
									disabled="disabled" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							修改时间：
						</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="update_time" id="update_time"
									value="${updateDate}"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' "
									disabled="disabled" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							修改者：
						</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="update_name" id="update_name"
									disabled="disabled" value="${staffNo}" draggable="false" />
							</div>
						</td>
					</tr>
					<tr>
						<td style="height: 55px; width: 58px">
							<input type="button" value="新增时间段:"
								onclick=select_kanhu_shijian(); />
							<input type="button" value="删除时间段:" onclick=del_kanhu_shijian(); />
						</td>

						<td>
							<div>
								<input type="hidden" id="kanhu_shijian_id"
									name="kanhu_shijian_id" />
								<input value="${kanhu_shijian}" type="hidden"
									id="kanhu_shijian_text" name="kanhu_shijian_text" />
								<textarea disabled="disabled" style="font-size: 12px"
									id="kanhu_shijian" name="kanhu_shijian" cols=20 rows=3></textarea>
							</div>
						</td>
					</tr>
				</table>
			</form>
			<div style="text-align: left; padding: 10px 0 10px 50px">
				<div class="btn-operation" onClick=saveForm_update();>
					保存
				</div>

			</div>
		</div>
		<script type="text/javascript">
	$(document).ready(function() {

		//$('#kanhu_shijian_id').val(${kanhu_shijian_id});
			$('#kanhu_shijian').val($('#kanhu_shijian_text').val());

		});
</script>
	</body>
</html>