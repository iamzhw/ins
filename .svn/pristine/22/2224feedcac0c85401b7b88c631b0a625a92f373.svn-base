package com.cableInspection.model;

import net.sf.json.JSONObject;


public class WellModel {
	private int BSE_EQP_ID;
	private String NAME;
	private String NO;
	private String LONGITUDE;
	private String LATITUDE;
	private JSONObject params;
	private WellModel(){
		params=JSONObject.fromObject("{\"objTypeID\":\"3\","
		         +"\"token\":\"PAD_8SAF77804D2BA1322C33E0122109\","
		         +"\"objSubTypeID\":\"508\","
		         +"\"coordType\":\"3\","
		         +"\"bss_area_id\":\"3\","
		         +"\"objID\":\""+BSE_EQP_ID+"\","
		         +"\"bss_area_id_4\":\"\"}"
		);
	}
	public JSONObject getParams() {
		return params;
	}
	public void setParams(JSONObject params) {
		this.params = params;
	}
	public void setLONGITUDE(String lONGITUDE) {
		LONGITUDE = lONGITUDE;
	}
	public void setLATITUDE(String lATITUDE) {
		LATITUDE = lATITUDE;
	}
	public int getBse_eqp_id() {
		return BSE_EQP_ID;
	}
	public void setBse_eqp_id(int bseEqpId) {
		BSE_EQP_ID = bseEqpId;
	}
	
	public String getName() {
		return NAME;
	}
	public void setName(String name) {
		this.NAME = NAME;
	}
	public String getNo() {
		return NO;
	}
	public void setNo(String no) {
		this.NO = no;
	}
	public String getLongitude() {
		return LONGITUDE;
	}
	public String getLatitude() {
		return LATITUDE;
	}
}
