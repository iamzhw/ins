/**
 * wangyan created @ 20140609 for base util functions
 * 
 */

// make json param expression from form id
function makeParamJson(idOrDom) {
	if (!idOrDom) {
		return;
	}
	var o;
	if (typeof idOrDom == "string") {
		o = $("#" + idOrDom);
	} else {
		o = $(idOrDom);
	}
	var json = {};
	o.find("input,select,textarea").each(function() {
		var o = $(this);
		var tag = this.tagName.toLowerCase();
		var name = o.attr("name");
		if (name) {
			if (tag == "select") {
				json[name] = o.find("option:selected").val();
			} else if (tag == "input"||tag=="textarea") {
				json[name] = o.val().trim();
			}
		}
	});
	return json;
}

function makeParamJson2(idOrDom) {
	if (!idOrDom) {
		return;
	}
	var o;
	if (typeof idOrDom == "string") {
		o = $("#" + idOrDom);
	} else {
		o = $(idOrDom);
	}
	var json = {};
	o.find("input,select").each(function() {
		var o = $(this);
		var tag = this.tagName.toLowerCase();
		var name = o.attr("name");
		if (name) {
			if (tag == "select") {
				if(o.find("option:selected").val()=='all' || o.find("option:selected").val()=='' ){
					json[name] = '';
				}else{
					json[name] = o.find("option:selected").text();
				}
				
			} else if (tag == "input") {
				json[name] = o.val().trim();
			}
		}
	});
	return json;
}
//select--text
function getParamsForDownload(idOrDom) {
	if (!idOrDom) {
		return;
	}
	var o;
	if (typeof idOrDom == "string") {
		o = $("#" + idOrDom);
	} else {
		o = $(idOrDom);
	}
	var res = "?randomPara=1";
	o.find("input,select").each(function() {
		var o = $(this);
		var tag = this.tagName.toLowerCase();
		var name = o.attr("name");
		if (name) {
			if (tag == "select") {
				if(o.find("option:selected").val()=='all' || o.find("option:selected").val()=='' ){
					res=res+"&"+name+"=";
				}else{
					res=res+"&"+name+"="+o.find("option:selected").text();
					//json[name] = o.find("option:selected").text();
				}
				
			} else if (tag == "input") {
				res=res+"&"+name+"="+ o.val();
				
			}
		}
	});
	return res;
}


// date formatter and parser for yyyy-MM-dd
function dateFormatter(date) {
	if (!date) {
		date = new Date();
	}
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}
function dateParser(s) {
	if (!s)
		return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}

//clear the input and select elements in form
function clearForm(formId) {
	clearInputAndSelect(formId);
}

//clear the input and select elements
function clearInputAndSelect(idOrDom) {
	if (!idOrDom) {
		return;
	}
	var oo;
	if (typeof idOrDom == "string") {
		oo = $("#" + idOrDom);
	} else {
		oo = $(idOrDom);
	}
	oo.find("input,select").each(function() {
		var o = $(this);
		var tagName = this.tagName.toLowerCase();
		if (tagName == "input") {
			o.val("");
		} else if (tagName == "select") {
			o.find("option[value='']").attr("selected", "selected");
		}
	});
}

// call the function to show a msgbox
function msgbox(title, text) {
	if (!text) {
		text = title;
		title = "提  示";
	}
	$.messager.show({
		title : title,
		msg : text,
		showType : 'show'
	});
}

// show loading msgbox
function loading(title, text) {
	if (!text) {
		text = title;
		title = "提  示";
	}
	$.messager.progress({
		title : '请稍候',
		msg : text,
		text : ""
	});
}
function closeLoading() {
	$.messager.progress("close");
}


function mergeCellsByField(tableID,colList,numColList){

    var ColArray = colList.split(",");  
    var tTable = $('#'+tableID);  
    var TableRowCnts=tTable.datagrid("getRows").length;  
    var tmpA;  
    var tmpB;  
    var PerTxt = "";  
    var CurTxt = "";  
    var alertStr = "";  
    //for (j=0;j<=ColArray.length-1 ;j++ )  
    for (j=ColArray.length-1;j>=0 ;j-- ){  
        //当循环至某新的列时，变量复位。  
        PerTxt="";  
        tmpA=1;  
        tmpB=0;  
          
        //从第一行（表头为第0行）开始循环，循环至行尾(溢出一位)  
        for (i=0;i<=TableRowCnts ;i++ ){  
            if (i==TableRowCnts){  
                CurTxt="";  
            }else{  
                CurTxt=tTable.datagrid("getRows")[i][ColArray[j]];  
            }  
            if (PerTxt==CurTxt){  
                tmpA+=1;  
            }else{  
                tmpB+=tmpA;  
                tTable.datagrid('mergeCells',{  
                    index:i-tmpA,  
                    field:ColArray[j],  
                    rowspan:tmpA,  
                    colspan:null  
                });
                var numColArray = numColList.split(",");
                for (m=numColArray.length-1;m>=0 ;m-- ){ 
                	 tTable.datagrid('mergeCells',{  
                         index:i-tmpA,  
                         field:numColArray[m],  
                         rowspan:tmpA,  
                         colspan:null  
                     });
                }
                
                tmpA=1;  
           }  
            PerTxt=CurTxt;  
        }  
    }  
}
