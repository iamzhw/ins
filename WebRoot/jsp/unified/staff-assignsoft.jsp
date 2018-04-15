<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/jsp/util/head.jsp"%>
<title>应用赋权</title>
</head>
<body style="padding:3px;border:0px" >
	<form id="form_soft" action="" method="post">
	<input name="asp_staff_id" type="hidden" value="${asp_staff_id}" />
	<input name="selected_softs" type="hidden" />
	</form>
	<table id="dg_soft" title="【应用赋权】" style="width:100%;height:480px">
	</table>
	<div id="tb_soft" style="padding:5px;height:auto">
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="saveSoftPermissions()">赋权</div>

		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			querySoftPermissions();

		});
		function querySoftPermissions() {
			var obj = {
				
			};
			//return;
			$('#dg_soft').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 450,
				height : 480,
				toolbar : '#tb_soft',
				url : webPath + "unifiedPage/querySoftPermissions.do",
				queryParams : obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'SOFTWARE_ID',
					title : '应用ID',
					checkbox : true
				}, {
					field : 'SOFTWARE_NAME',
					title : '应用名称',
					align : 'center'
				}, {
					field : 'PACKAGE_NAME',
					title : '包名',
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
					var rows = $("#dg_soft").datagrid("getRows"); 
					var softs = ${softs};
					for(var i=0;i<rows.length;i++){
					    var soft_id = rows[i].SOFTWARE_ID;
					    var flag = 0;
						for(var j=0;j<softs.length;j++){
							if(soft_id==softs[j]){
								flag = 1;
							}
						}
						if(flag == 1){
							$('#dg_soft').datagrid('selectRow',i);
						}
					}
					
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
			//alert(rows);
			alert("onSelectAll");
		}
function saveSoftPermissions() {
    var selected = $('#dg_soft').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取应用权限!',
            showType: 'show'
        });
        return;
    } else {
        $.messager.confirm('系统提示', '您确定要应用赋权吗?', function (r) {
            if (r) {
                var arr = new Array();
                for (var i = 0; i < selected.length; i++) {
                    var value = selected[i].SOFTWARE_ID;
                    arr[arr.length] = value;
                }
                $("input[name='selected_softs']").val(arr);
                $('#form_soft').form('submit', {
                    url: webPath + "unifiedPage/saveSoftPermissions.do",
                    onSubmit: function () {
                        $.messager.progress();
                    },
                    success: function () {
                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '应用赋权成功!',
                            showType: 'show'
                        });
                        $('#win_staff').window('close');
                        
                    }
                });
            }
        });
    }
}
		
	</script>
</body>
</html>