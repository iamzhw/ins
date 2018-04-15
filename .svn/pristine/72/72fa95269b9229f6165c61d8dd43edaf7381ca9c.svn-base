<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/zTreeStyle3/zTreeStyle.css" />
<script type="text/javascript"
	src="<%=path%>/js/ZTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/js/ZTree/ZTree.js"></script>
<script type="text/javascript" src="<%=path%>/js/ZTree/jquery.popupSmallMenu.js"></script>
<style>
.small-menu {
    position: absolute;
	width: 120px;
	z-index: 99999;
	border: solid 1px #CCC;
	background: #EEE;
	padding: 0px;
	margin: 0px;
	display: none;
}

.small-menu li {
   list-style: none;
	padding: 0px;
	margin: 0px;
}
.small-menu li A {
	color: #333;
	text-decoration: none;
	display: block;
	line-height: 20px;
	height: 20px;
	background-position: 6px center;
	background-repeat: no-repeat;
	outline: none;
	padding: 1px 5px;
	padding-left: 28px;
}

.small-menu li a:hover {
	color: #FFF;
	background-color: #3399FF;
}

.small-menu-separator {
    padding-bottom:0;
    border-bottom: 1px solid #DDD;
}

.small-menu LI.edit A { background-image: url(/ins/images/ztreeRightClickimages/page_white_edit.png); }
.small-menu LI.cut A { background-image: url(/ins/images/ztreeRightClickimages/cut.png); }
.small-menu LI.copy A { background-image: url(/ins/images/ztreeRightClickimages/page_white_copy.png); }
.small-menu LI.paste A { background-image: url(/ins/images/ztreeRightClickimages/page_white_paste.png); }
.small-menu LI.delete A { background-image: url(/ins/images/ztreeRightClickimages/page_white_delete.png); }
.small-menu LI.add A { background-image: url(/ins/images/ztreeRightClickimages/page_white_add.png); }
</style>
<title>班组组织结构管理</title>
</head>
<body style="padding:3px;border:0px">
	<div class="easyui-layout" style="height:600px;">
		<div data-options="region:'center' "
			style="width:400px;padding:3px 7px;">
			<div id="resourceTree" class="ztree"></div>
		</div>
	</div>
	<!-- <div class="col-md-3 sidebar">  
    	<div class="row">  
	        <div>  
	            <input type="text"  id="key" class="empty form-control" placeholder="Search..." onkeyup="callNumber()">  
	        </div>  
	        <div>  
	            <label type="text"  id="resultKey" class="form-control"onclick="changeFocus()" >  
	                <div>  
	                    <a id="clickUp" class="glyphicon glyphicon-menu-up" onclick="clickUp()"></a>  
	                    <a id="clickDown" class="glyphicon glyphicon-menu-down" onclick="clickDown()"></a>  
	                </div>  
	                <label id="number"></label>  
	            </label>  
	        </div>
	    </div>  
	</div>   -->
	<div>
		<ul id="menu"  class="small-menu">
			<li class="small-menu-separator"></li>
			<li class="delete"><a href="#">删除</a></li>
			<li class="small-menu-separator"></li>
		</ul>
	</div>
</body>
</html>
<script type="text/javascript">
	
	var lastValue = "", nodeList = [], fontCss = {};  
	var setting;
	//参数设置
	setting = {
		async : {
			enable : true,
			url : webPath + "/MainTainCompany/getDeptTree.do",
			autoParam : [ "id=id", "name","actionName" ]
		//otherParam: {},
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId",
				rootPId : 0
			}
		},
		callback: {
			onRightClick:OnRightClick
		}
	};
	
	var ztree ;
	$(document).ready(function() {
		$.fn.zTree.init($("#resourceTree"), setting);
		ztree = $.fn.zTree.getZTreeObj("resourceTree");
		
		
	    document.getElementById("key").value = ""; //清空搜索框中的内容  
	    //绑定事件  
	    key = $("#key");  
	    key.bind("focus", focusKey)  
        	.bind("blur", blurKey)  
        	.bind("propertychange", searchNode) //property(属性)change(改变)的时候，触发事件  
	        .bind("input", searchNode);  
	});
	
	
	function OnRightClick(event, treeId, treeNode) {
		ztree.selectNode(treeNode);
		if(treeNode) {
			//console.log(treeNode.tId);
			//console.dir($('#'+treeNode.tId));

			$("#menu").popupSmallMenu({
				event : event,
				onClickItem  : function(item) {
					operate(treeNode,item);
				}
			});
		}	 
	}

	//选择右击菜单进行ajax处理
	function operate(treeNode,item){
		console.info(treeNode);
		$.messager.confirm('系统提示', '您确定删除 选择的班组 吗?', function(r) {
			if (r) {
				$.ajax({
					type : 'POST',
					url : webPath + "MainTainCompany/updateTreeStatus.do",
					data : {
						node_id:treeNode.id,
						parent_id:treeNode.parentId,
						isParent:treeNode.isParent
					},
					dataType : 'json',
					success : function(json) {
						$.messager.alert("提示",json.desc,"info");
					}
				})	
			}
		});
	}
	

	function focusKey(e) {  
	    if (key.hasClass("empty")) {  
	        key.removeClass("empty");  
	    }  
	}  
	function blurKey(e) {  
	    if (key.get(0).value === "") {  
	        key.addClass("empty");  
	    }  
	}

	
	function callNumber(){  
	    var zTree = $.fn.zTree.getZTreeObj("resourceTree");  
	  	
	    //如果结果有值，则显示比例；如果结果为0，则显示"[0/0]"；如果结果为空，则清空标签框；  
	    if(nodeList.length){  
	        //让结果集里边的第一个获取焦点（主要为了设置背景色），再把焦点返回给搜索框  
	        zTree.selectNode(nodeList[0],false );  
	        document.getElementById("key").focus();  
	  
	        clickCount=1; //防止重新输入的搜索信息的时候，没有清空上一次记录  
	  
	        //显示当前所在的是第一条  
	        document.getElementById("number").innerHTML="["+clickCount+"/"+nodeList.length+"]";  
	  
	    }else if(nodeList.length == 0){  
	        document.getElementById("number").innerHTML="[0/0]";  
	        zTree.cancelSelectedNode(); //取消焦点  
	    }
	  
	    //如果输入框中没有搜索内容，则清空标签框  
	    if(document.getElementById("key").value ==""){  
	        document.getElementById("number").innerHTML="";  
	        zTree.cancelSelectedNode();  
	    }  
	}
	
	//搜索节点  
	function searchNode(e) { 
	    var zTree = $.fn.zTree.getZTreeObj("resourceTree"); 
	    
	    var value = $.trim(key.get(0).value);  
	    var keyType = "name";  
	  	
	    if (key.hasClass("empty")) {  
	        value = "";  
	    }
	    
	    if (lastValue === value) return;  
	    lastValue = value;  
	    
	    if (value === ""){  
	        updateNodes(false);  
	        return;  
	    };  
	    nodeList = zTree.getNodesByParamFuzzy(keyType, value); //调用ztree的模糊查询功能，得到符合条件的节点  
	    updateNodes(true); //更新节点  
	}
	
	
	//高亮显示被搜索到的节点  
	function updateNodes(highlight) {
	    var zTree = $.fn.zTree.getZTreeObj("resourceTree");
	    for( var i=0, l=nodeList.length; i<l; i++) {  
	    	console.info(l);
	        nodeList[i].highlight = highlight; /* 亮显示搜索到的节点(highlight是自己设置的一个属性) */  
	        zTree.expandNode(nodeList[i].getParentNode(), true, false, false); /* 将搜索到的节点的父节点展开   */
	        zTree.updateNode(nodeList[i]); /* 更新节点数据，主要用于该节点显示属性的更新 */  
	    }
	}
	
	function clickUp(){  
	    var zTree = $.fn.zTree.getZTreeObj("resourceTree");  
	    //如果焦点已经移动到了最后一条数据上，就返回第一条重新开始，否则继续移动到下一条  
	    if(nodeList.length==0){  
	        alert("没有搜索结果！");  
	        return ;  
	    }else if(clickCount==1) {  
	        alert("您已位于第一条记录上！");  
	        return;  
	        //让结果集里边的下一个节点获取焦点（主要为了设置背景色），再把焦点返回给搜索框  
	        //zTree.selectNode(nodeList[clickCount], false)  
	    }else{  
	        //让结果集里边的第一个获取焦点（主要为了设置背景色），再把焦点返回给搜索框  
	        zTree.selectNode(nodeList[clickCount], false);  
	        clickCount --;  
	    }  
	    document.getElementById("key").focus();  
	    //显示当前所在的是条数  
	    document.getElementById("number").innerHTML = "[" + clickCount + "/" + nodeList.length + "]";  
	}  
	
	//点击向上按钮时，将焦点移向下一条数据  
	function clickDown(){  
		alert();
	    var zTree = $.fn.zTree.getZTreeObj("resourceTree");  
	    //如果焦点已经移动到了最后一条数据上，则提示用户（或者返回第一条重新开始），否则继续移动到下一条  
	    if(nodeList.length==0){  
	        alert("没有搜索结果！");  
	        return ;  
	    }else if(nodeList.length==clickCount)  
	    {  
	        alert("您已位于最后一条记录上！")  
	        return;  
	    }else{  
	        //让结果集里边的第一个获取焦点（主要为了设置背景色），再把焦点返回给搜索框  
	        zTree.selectNode(nodeList[clickCount], false)  
	        clickCount ++;  
	    }  
	    document.getElementById("key").focus();  
	    //显示当前所在的条数  
	    document.getElementById("number").innerHTML = "[" + clickCount + "/" + nodeList.length + "]";  
	}
	
</script>

