<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>网格管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">网格名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="Grid_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">网格编号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="Grid_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">地市：</td>
						<td width="20%"><select name="area" class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${areaList}" var="al">
									<option value="${al.AREA_ID}">${al.NAME}</option>
								</c:forEach>
						</select></td>
						<td width="10%">区县：</td>
						<td width="20%"><select name="son_area"
							class="condition-select">
								<option value="">请选择</option>
						</select>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			
			<div class="btn-operation" onClick="assignRolePermissions()">
				网格审核员</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【网格管理】" style="width: 100%; height: 480px">
	</table>
	<div id="win_staff"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		selectSelected();
		//searchData();
		});
		
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
		function searchData() {
			var Grid_name = $("input[name='Grid_name']").val().trim();
			var Grid_no = $("input[name='Grid_no']").val().trim();
			var area = $("select[name='area']").val();
			var son_area = $("select[name='son_area']").val();
			var obj = {
				grid_name : Grid_name,
				grid_no : Grid_no,
				area : area,
				son_area : son_area,
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Grid/query.do",
				queryParams : obj,
				fit : true,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				columns : [ [ {
					field : 'GRID_ID',
					title : '网格ID',
					checkbox : true
				}, {
					field : 'GRID_NAME',
					title : '网格姓名',
					width : "9%",
					align : 'center'
				}, {
					field : 'GRID_NO',
					title : '网格编号',
					width : "10%",
					align : 'center'
				}, {
					field : 'sh_name',
					title : '网格审核员',
					width : "15%",
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
       			 $(this).datagrid("fixRownumber");
				 $("body").resize();
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

		$(function() {
			//查询
			if($("#ifGLY").val()=="1"){
				getSonAreaList('query');
			}else{
			$("select[name='area']").change(function() {
				//根据area_id，获取区县
				getSonAreaList('query');
			});
			}
		});

		function getSonAreaList(operator) {
			//	var areaId = $("select[name='area']").val();
			var area_id = 0;
			if ('query' == operator) {
				area_id = $("select[name='area']").find("option:selected")
						.val();
			} else if ('add' == operator) {
				area_id = $("select[name='varea']").find("option:selected")
						.val();
			}
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : area_id
				},
				dataType : 'json',
				success : function(json) {
					var result = json.sonAreaList;
					if ('query' == operator) {
						$("select[name='son_area'] option").remove();
						$("select[name='son_area']").append(
								"<option value=''>请选择</option>");
						for ( var i = 0; i < result.length; i++) {
							$("select[name='son_area']").append(
									"<option value=" + result[i].AREA_ID + ">"
											+ result[i].NAME + "</option>");
						}
					} else if ('add' == operator) {
						if($("#ifGLY").val()=="1"){
							$("select[name='varea']").attr("disabled","disabled");
						}
						$("select[name='vson_area'] option").remove();
						for ( var i = 0; i < result.length; i++) {
							$("select[name='vson_area']").append(
									"<option value=" + result[i].AREA_ID + ">"
											+ result[i].NAME + "</option>");
						}
					}
				}
			})
			
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "General/getLocalCompanys.do",
				  data:{areaId:area_id},
				  dataType:"json",
				  success:function(data){
					  $("#own_company").empty();
					  $("#own_company").append("<option value=''>--请选择--</option>");		
					  $.each(data.companyList,function(i,item){
						  $("#own_company").append("<option value='"+item.ORG_ID+"'>"+item.ORG_NAME+"</option>");		
					  });
				  }
			  });
		}

		function clearCondition(form_id) {
			//$(form_id).form('reset','none');
			$("input[name='staff_name']").val('');
			$("input[name='staff_no']").val('');
			$("input[name='org_name']").val('');
			$("input[name='role_name']").val('');
			if($("#ifGLY").val()=="0"){
			$("select[name='area']").val('');
			$("select[name='son_area'] option").remove();
			$("select[name='son_area']").append("<option value=''>请选择</option>");
			}else{
			$("select[name='son_area']").val('');
			}
			
			
			
		}
function assignRolePermissions() {
    var selected = $('#dg').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取一条数据!',
            showType: 'show'
        });
        return;
    } else if (count > 1) {
        $.messager.show({
            title: '提  示',
            msg: '仅能选取一条数据!',
            showType: 'show'
        });
        return;
    } else {
        var grid_id = selected[0].GRID_ID;
        $("#win_staff").window({
            title: "【应用赋权】",
            href : webPath + "Grid/assignGridPermissions.do?grid_id="+grid_id,
			width : 500,
			height : 500,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
        
    }
}
</script>

</body>
</html>