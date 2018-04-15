<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>角色管理</title>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/zTreeStyle3/zTreeStyle.css" />
<script type="text/javascript"
	src="<%=path%>/js/ZTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/ZTree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/ZTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/js/ZTree/ZTree.js"></script>
</head>
<body style="padding:3px;border:0px;">
	<table id="dg" title="【角色管理】" style="width:100%;height:480px">
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table style="width:700px;border:0px solid;" class="condition_">
					<tr>
						<td width="10%">角色名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="role_name" type="text" class="condition-text" />
							</div></td>
						<td width="10%">角色编号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="role_no" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addRole()">增加</div>
			<div class="btn-operation" onClick="editRole()">编辑</div>
			<div class="btn-operation" onClick="delRole()">删除</div>
			<div class="btn-operation" onClick="assignPermissions()">角色赋权</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div class="btn-operation" onClick="searchData()" style="float:right">查询</div>
		</div>
	</div>
	<div id="assign" style="border:0px solid;padding:3px 7px;">
		<div id="resourceTree" class="ztree"
			style="width:90%;height:80%;
			overflow-y:auto;overflow-x:hidden;"></div>
		<div class="btn-operation" onClick="assign()" style="float:right">提交</div>
	</div>
	<input type="hidden" name="selectedRole" />
	<div id="win_role"></div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#assign").hide();
	searchData();
});

function searchData(){
	var role_name = $("input[name='role_name']").val().trim();
	var role_no = $("input[name='role_no']").val().trim();
	var obj= {role_name:role_name,role_no:role_no};
    $('#dg').datagrid({
    	autoSize:true,
		toolbar: '#tb',
	    url: webPath+"Role/query.do",   
	   	queryParams : obj,
	    method:'post',
	    pagination:true,
	    pageNumber : 1,
		pageSize : 10,
		pageList : [ 20, 50 ],
		//loadMsg:'数据加载中.....',
	    rownumbers:true,
	    singleSelect:false, 
	    columns:[[
	        {field:'ROLE_ID',title:'角色ID',checkbox:true},
	       	{field:'ROLE_NAME',title:'角色名称',align:'center',width:"35%"},
	        {field:'ROLE_NO',title:'角色编号',align:'center',width:"35%"},    
	        {field:'STATUS',title:'状态',align:'center',width:"35%"}
	    ]],
		nowrap : false,
		striped : true,
		onLoadSuccess:function(data){
		
		}
	});
	
	
}
function addRole() {
    $('#win_role').window({
        title: "【新增角色】",
        href: webPath + "Role/add.do",
        width: 400,
        height: 250,
        zIndex: 2,
        region: "center",
        collapsible: false,
        cache: false,
        modal: true
    });
}
function editRole() {
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
        var role_id = selected[0].ROLE_ID;
        $('#win_role').window({
            title: "【编辑角色】",
            href: webPath + "Role/edit.do?role_id=" + role_id,
            width: 400,
            height: 250,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
    }

}

function clearCondition(form_id) {
    $("input[name='role_name']").val('');
    $("input[name='role_no']").val('');
}

//role-add.jsp
function clearForm() {
    $("input[name='vrole_name']").val('');
    $("input[name='vrole_no']").val('');
    $("select[name='vstatus']").val('1');
}
function saveForm() {
    var pass = $("#ff").form('validate');
    if (pass) {
        $.messager.confirm('系统提示', '您确定要新增角色吗?',
        function(r) {
            if (r) {
                var role_name = $("input[name='vrole_name']").val();
                var role_no = $("input[name='vrole_no']").val();
                var status = $("select[name='vstatus']").val();
                $.ajax({
                    type: 'POST',
                    url: webPath + "Role/save.do",
                    data: {
                        role_name: role_name,
                        role_no: role_no,
                        status: status
                    },
                    dataType: 'json',
                    success: function(json) {
                        if (json.status) {
                            $.messager.show({
                                title: '提  示',
                                msg: '新增角色成功!',
                                showType: 'show'
                            });
                        }
                        $('#win_role').window('close');
                        searchData();
                    }
                })
            }
        });
    }
}
function updateForm() {
    var pass = $("#ff").form('validate');
    if (pass) {
        $.messager.confirm('系统提示', '您确定要更新角色吗?',
        function(r) {
            if (r) {
                var role_id = $("input[name='vrole_id']").val();
                var role_name = $("input[name='vrole_name']").val();
                var role_no = $("input[name='vrole_no']").val();
                var status = $("select[name='vstatus']").val();
                $.ajax({
                    type: 'POST',
                    url: webPath + "Role/update.do",
                    data: {
                        role_id: role_id,
                        role_name: role_name,
                        role_no: role_no,
                        status: status
                    },
                    dataType: 'json',
                    success: function(json) {
                        if (json.status) {
                            $.messager.show({
                                title: '提  示',
                                msg: '更新角色成功!',
                                showType: 'show'
                            });
                        }
                        $('#win_role').window('close');
                        searchData();
                    }
                })
            }
        });
    }
}

function delRole() {
    var selected = $('#dg').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取要删除的数据!',
            showType: 'show'
        });
        return;
    } else {
        var arr = new Array();
        for (var i = 0; i < selected.length; i++) {
            var value = selected[i].ROLE_ID;
            arr[arr.length] = value;
        }
        $("input[name='selected']").val(arr);
        $('#form').form('submit', {
            url: webPath + "Role/delete.do",
            onSubmit: function() {
                $.messager.progress();
            },
            success: function() {
                $.messager.progress('close'); // 如果提交成功则隐藏进度条
                searchData();
                $.messager.show({
                    title: '提  示',
                    msg: '成功删除角色!',
                    showType: 'show'
                });
            }
        });

    }

}

function assignPermissions() {
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
    	$("#assign").show();
        var role_id = selected[0].ROLE_ID;
        $("input[name='selectedRole']").val(role_id);
        $("#assign").window({
            title: "【角色赋权】",
            width: 500,
            height: 400,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
        initTree(role_id);
    }
}

function initTree(role_id) {
    var setting = {
        async: {
            enable: true,
            url: webPath + 'Role/getAllGns.do?role_id=' + role_id,
            autoParam: ["id=id", "name"],
            //otherParam: {},
            dataFilter: filter
        },
        callback: {
            beforeAsync: beforeAsync,
            onAsyncSuccess: onAsyncSuccess,
            onAsyncError: onAsyncError
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: 0
            }
        },
        check: {
            enable: true,
            expandFlag: true,
            autoCheckTrigger: true,
            chkStyle: "checkbox",
            chkboxType: {
                "Y": "ps",
                "N": "ps"
            },
            nocheckInherit: true,
            chkDisabledInherit: true
        },
        view: {
            showLine: true
        }

    };
    var nodes;
    $.fn.zTree.init($("#resourceTree"), setting);

}
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i = 0,
    l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}

function beforeAsync() {
    curAsyncCount++;
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
    curAsyncCount--;
    if (curStatus == "expand") {
        expandNodes(treeNode.children);
    } else if (curStatus == "async") {
        asyncNodes(treeNode.children);
    }
    if (curAsyncCount <= 0) {
        if (curStatus != "init" && curStatus != "") {
            asyncForAll = true;
        }
        curStatus = "";
    }
    expandAll();
}

function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    curAsyncCount--;
    if (curAsyncCount <= 0) {
        curStatus = "";
        if (treeNode != null) asyncForAll = true;
    }
}

var curStatus = "init",
curAsyncCount = 0,
asyncForAll = false,
goAsync = false;
function expandAll() {
    if (!check()) {
        return;
    }
    var zTree = $.fn.zTree.getZTreeObj("resourceTree");
    if (asyncForAll) {
        zTree.expandAll(true);
    } else {
        expandNodes(zTree.getNodes());
        if (!goAsync) {
            curStatus = "";
        }
    }

    curStatus = "init",
    curAsyncCount = 0,
    asyncForAll = false,
    goAsync = false;
}
function expandNodes(nodes) {
    if (!nodes) return;
    curStatus = "expand";
    var zTree = $.fn.zTree.getZTreeObj("resourceTree");
    for (var i = 0,
    l = nodes.length; i < l; i++) {
        zTree.expandNode(nodes[i], true, false, false);
        if (nodes[i].isParent && nodes[i].zAsync) {
            expandNodes(nodes[i].children);
        } else {
            goAsync = true;
        }
    }
}

function asyncAll() {
    if (!check()) {
        return;
    }
    var zTree = $.fn.zTree.getZTreeObj("resourceTree");
    if (asyncForAll) {} else {
        asyncNodes(zTree.getNodes());
        if (!goAsync) {
            curStatus = "";
        }
    }
}
function asyncNodes(nodes) {
    if (!nodes) return;
    curStatus = "async";
    var zTree = $.fn.zTree.getZTreeObj("resourceTree");
    for (var i = 0,
    l = nodes.length; i < l; i++) {
        if (nodes[i].isParent && nodes[i].zAsync) {
            asyncNodes(nodes[i].children);
        } else {
            goAsync = true;
            zTree.reAsyncChildNodes(nodes[i], "refresh", true);
        }
    }
}
function check() {
    if (curAsyncCount > 0) {
        return false;
    }
    return true;
}

function assign() {
    var role_id = $("input[name='selectedRole']").val();
    var treeObj = $.fn.zTree.getZTreeObj("resourceTree");
    var treeNode = treeObj.getCheckedNodes(true);
    var resources = "";
    for (var i = 0; i < treeNode.length; i++) {
        if (i == 0) {
            resources = treeNode[i]["id"];
        } else {
            resources = resources + "=" + treeNode[i]["id"];
        }
    }
    $.ajax({
        type: 'post',
        url: webPath + 'Role/assignPermissions.do',
        data: {
            role_id: role_id,
            resources: resources
        },
        dataType: 'json',
        success: function(json) {
        	$("#assign").hide();
        	$("#assign").window("close");
            if (json.flag == "1") {
                $.messager.show({
                    title: '提  示',
                    msg: '分配成功!',
                    showType: 'show'
                });
            } else {
                $.messager.show({
                    title: '提  示',
                    msg: '分配失败!',
                    showType: 'show'
                });
            }
        }
    });
}
</script>
