


//动态更新参数
function setEditable(){
	  var xSetting=demoTree.getSetting();
	  xSetting.editable=true;
	  demoTree.updateSetting(xSetting);
}

function setFont(treeId, treeNode) {
   if (treeNode && treeNode.isParent) {
		  return {color: "blue"};
   } else {
		  return null;
   }
}

function zTreeOnChange(event, treeId, treeNode) {
	alert("发生改变的节点的ID值是："+treeNode["id"]);
}

function zTreeOnRename(event, treeId, treeNode){
   alert("重命名的节点的ID值是："+treeNode["id"]);
}

function zTreeOnClick(event, treeId, treeNode){
	//var node=demoTree.getSelectedNode();
	//alert(node);
	//alert(node["name"]);
	//alert("你单击的节点是："+treeNode["id"]+"    "+treeNode["name"]);
}

function zTreeOnRemove(event, treeId, treeNode){
   alert("你删除的节点是："+treeNode["id"]+"    "+treeNode["name"]);
}


