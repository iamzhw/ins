//画本端口的点
	function showResultsAndDraw0(results) {
		 showResultsAndDraw(results,0);
	}
	function showResultsAndDraw1(results) {
		 showResultsAndDraw(results,1);
	}
	function showResultsAndDraw2(results) {
		 showResultsAndDraw(results,2);
	}
	function showResultsAndDraw3(results) {
		 showResultsAndDraw(results,3);
	}
	function showResultsAndDraw4(results) {
		 showResultsAndDraw(results,4);
	}
	function showResultsAndDraw5(results) {
		 showResultsAndDraw(results,5);
	}
	function showResultsAndDraw6(results) {
		 showResultsAndDraw(results,6);
	}
	function showResultsAndDraw7(results) {
		 showResultsAndDraw(results,7);
	}
	function showResultsAndDraw8(results) {
		 showResultsAndDraw(results,8);
	}
	function mouseOverLayer(e){  
	    map.setMapCursor("pointer");  
	};  
	function mouseOutLayer(e){  
	    map.setMapCursor("default");  
	};  
	
	function onClickLayer(e){  
	    map.setMapCursor("pointer");  
	  // var mp = e.mapPoint;
	   //   alert(mp.x.toFixed(4) +","+ mp.y.toFixed(4));
	    //var scrPt = map.toScreen(e.mapPoint);  
	   //alert(scrPt.offsetX);
	  //  var textDiv = dojo.doc.createElement("div");  
	    //alert(  e.graphic._extent.xmax);
	  // var url = webPath+"/jsp/util/gis/info.jsp?resNo=";
	    $('#win').window({  
	    	title:"管道段信息",
	        //width:scrPt.offsetX+30,  
	    	//href: url+e.graphic.attributes.RESNAME, 
	    	width:200,
	        height:100,    
	        modal:true,
	        closable:true,
	        closed:true,
	        collapsible:false
	      
	    }); 
	    $('#resInfo').html ("管道段名称 : "+e.graphic.attributes.RESNAME);  
	    $('#win').window('open'); 
	 
	   // dojo.byId("map").appendChild(textDiv);  
	};  
	
	/**
	 * 判断此管道段是否存在于相同管道段LIST中
	 */
	function getSectExitsInSameSects(sectId, sameList)
	{
		var exitFlag = false;
		for ( var i = 0; i < sameList.length; i++) {
			if(sectId == sameList[i].CBL_SECT_ID)
			{
				//alert(sectId+"_________"+sameList[i].CBL_SECT_ID);
				exitFlag=true;
				return exitFlag;
			}
		}
		return exitFlag;
	};