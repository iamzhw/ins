<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../util/head.jsp"%>
		<title>选择外力点</title>
	</head>
	<body style="padding: 3px; border: 0px">
		
		<div id="tb_plan_outsite" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="10%">
								外力点：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="select_out_name" type="text" class="condition-text" />
								</div>
							</td>
							
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="query_outsite()">
					查询
				</div>
				<div class="btn-operation" onClick="submit_outsite()">
					确定
				</div>
			</div>
		</div>
		<table id="dg_plan_outsite" title="【选择外力点】" style="width: 100%; height: 480px">
		</table>
		<script type="text/javascript">
		$(document).ready(function() {
			query_outsite();
		});
		
		function query_outsite() {
			var select_out_name = $("input[name='select_out_name']").val().trim();
			var obj = {
				site_name : select_out_name
			};
			$('#dg_plan_outsite').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 760,
				height : 480,
				toolbar : '#tb_plan_outsite',
				url : webPath + "outsitePlanManage/out_site_data.do",
				queryParams : obj,
				method : 'post',
				pagination : false,
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'OUT_SITE_ID',
					title : 'ID',
					checkbox : true
				}, {
					field : 'SITE_NAME',
					title : '外力点名称',
					width : "24%",
					align : 'center'
				}, {
					field : 'X',
					title : '经度',
					width : "18%",
					align : 'center'
				}, {
					field : 'Y',
					title : '纬度',
					width : "18%",
					align : 'center'
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
				
				}
			});
			
		}
		
function submit_outsite() {
    var selected = $('#dg_plan_outsite').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取查询外力点!',
            showType: 'show'
        });
        return;
    } else {
                $("input[name='out_site_id']").val(selected[0].OUT_SITE_ID);
                $("input[name='outsite_name']").val(selected[0].SITE_NAME);
                $('#win_outsite').window('close');
    }
}



		
	</script>
	</body>
</html>