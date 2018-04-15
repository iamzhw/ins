package icom.cableCheck.model;

import java.util.ArrayList;
import java.util.List;

public class SensitiveLog {
	private static List<SensitiveLog> list=new ArrayList<SensitiveLog>();
	private static SensitiveLog sensitiveLog;
	private String sensitive_staff_id;
	private String sensitive_staff_no;
	private String sensitive_staff_name;
	private String parame;
	private String operate;
	private String sensitive_address;
	private String staffId;
	private String sn;
	
	public String getStaffId() {
		return staffId;
	}

	public String getSn() {
		return sn;
	}

	public static List<SensitiveLog> getList() {
		return list;
	}

	public String getSensitive_staff_id() {
		return sensitive_staff_id;
	}

	public String getSensitive_staff_no() {
		return sensitive_staff_no;
	}

	public String getSensitive_staff_name() {
		return sensitive_staff_name;
	}

	public String getParame() {
		return parame;
	}

	public String getOperate() {
		return operate;
	}

	public String getSensitive_address() {
		return sensitive_address;
	}

	private SensitiveLog(String sensitive_staff_id,String sensitive_staff_no,
			String sensitive_staff_name,String parame,String operate,String sensitive_address,
			String staffId,String sn) {
		this.sensitive_staff_id = sensitive_staff_id;
		this.sensitive_staff_no = sensitive_staff_no;
		this.sensitive_staff_name = sensitive_staff_name;
		this.parame = parame;
		this.operate = operate;
		this.sensitive_address = sensitive_address;
		this.staffId=staffId;
		this.sn=sn;
	}
	
	public static SensitiveLog createSensitiveLog(String sensitive_staff_id,String sensitive_staff_no,
			String sensitive_staff_name,String parame,String operate,String sensitive_address,
			String staffId,String sn){
		sensitiveLog = new SensitiveLog(sensitive_staff_id,sensitive_staff_no,
					sensitive_staff_name,parame,operate,sensitive_address,staffId,sn);
		return sensitiveLog;
	}
	
	/**
	 * 获取存放的对象
	 * @return
	 */
	public static SensitiveLog getSensitiveLog(){
		if(sensitiveLog==null){
			sensitiveLog = new SensitiveLog("","","","","","","","");
		}
		return sensitiveLog;
	}
	
}
