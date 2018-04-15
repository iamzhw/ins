package com.inspecthelper.model;

import com.system.tool.GlobalValue;


public class PadUrl {
	// IOM 徐振华
	public static String IOM_SERVICE = GlobalValue.isFormal ? "http://132.228.166.60:8080/DemoProj/services/PFIntfForMobile"
			: "http://132.228.183.4:9082/DemoProj/services/PFIntfForMobile";
	// IOM 黄权烽
	public static String IOM_SERVICE_FLOWID = GlobalValue.isFormal ? "http://iom.jsoss.net/PFINTFFOROSS/services/LifeCycleService"
			: "http://132.228.183.4:9082/PFINTFFOROSS/services/LifeCycleService";
	// public static final String
	// ESB_SERVICE_TEST_ADDRESS="http://132.228.183.2:9000/proxy";//ESB测试环境
	// ESB
	public static final String ESB_SERVICE_ADDRESS = GlobalValue.isFormal ? "http://132.228.224.35:80/proxy"
			: "http://132.228.183.2:9000/proxy";// ESb正式环境
	public static final String GIS_namespace = "http://ResGraphInterface.gis.ztesoft.com/";// GIS环境
	
    public static  String getArcgisURL(String resid,String typeid){
    	String ARCGIS_URL =  GlobalValue.isFormal ? "http://132.228.224.46:9002/arcgis/rest/services/szres_arcsde/MapServer/"+typeid+"/query?f=pjson&&outFields=TYPEID+%2CRESNAME+%2CRESNO&where=RESID%3D"+resid
    			: "http://132.228.125.47:8399/arcgis/rest/services/szres_arcsde/MapServer/"+typeid+"/query?f=pjson&&outFields=TYPEID+%2CRESNAME+%2CRESNO&where=RESID%3D"+resid;
    return ARCGIS_URL;
    }

}
