<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/jsp/util/head.jsp"%>
<title>角色赋权</title>
</head>
<body style="padding:3px;border:0px" >
	<form id="form_dept" action="" method="post">
	<input name="asp_staff_id" type="hidden" value="${asp_staff_id}" />
	<input name="selected_depts" type="hidden" />
	</form>
	<table id="dg_dept" title="【维护网格添加】" style="width:100%;height:480px">
	</table>
	<div id="tb_dept" style="padding:5px;height:auto">
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="saveDept()">修改</div>

		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			queryDept();

		});
		function queryDept() {
			staff_id=$("input[name='asp_staff_id']").val();
			var obj = {
				staff_id:staff_id
			};
			//return;
			$('#dg_dept').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 450,
				height : 480,
				toolbar : '#tb_dept',
				url : webPath + "unifiedPage/queryDept.do",
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
					field : 'DEPT_ID',
					title : '网格ID',
					checkbox : true
				}, {
					field : 'DEPT_NAME',
					title : '网格名称',
					align : 'center'
				}, {
					field : 'DEPT_NO',
					title : '网格编码',
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
					var rows = $("#dg_dept").datagrid("getRows"); 
					var depts = ${deptList};
					for(var i=0;i<rows.length;i++){
					    var dept_id = rows[i].DEPT_ID;
					    var flag = 0;
						for(var j=0;j<depts.length;j++){
							if(dept_id==depts[j]){
								flag = 1;
							}
						}
						if(flag == 1){
							$('#dg_dept').datagrid('selectRow',i);
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
			alert(rows);
			alert("onSelectAll");
		}
function saveDept() {
    var selected = $('#dg_dept').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取网格!',
            showType: 'show'
        });
        return;
    } else {
        $.messager.confirm('系统提示', '您确定要修改网格吗?', function (r) {
            if (r) {
                var arr = new Array();
                for (var i = 0; i < selected.length; i++) {
                    var value = selected[i].DEPT_ID;
                    arr[arr.length] = value;
                }
                $("input[name='selected_depts']").val(arr);
                $('#form_dept').form('submit', {
                    url: webPath + "unifiedPage/updateDept.do",
                    onSubmit: function () {
                        $.messager.progress();
                    },
                    success: function () {
                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '成功!',
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