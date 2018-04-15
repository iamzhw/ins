package icom.webservice.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 待办数量接口请求类
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IfInfo")
public class AppFlowCountRequest {

	/**
	 * 用户Id
	 */
	private String userid;

	/**
	 * 是否全量查询 Y：是，N：否
	 */
	private String full_flag;

	/**
	 * 距离范围 full_flag=N，则该字段必填
	 */
	private String dis_range;
	
	/**
	 * 用户登录名
	 */
	private String staff_no;
	
	/**
	 * 身份证号码
	 */
	private String idCard;

	/**
	 * 经度
	 */
	@XmlElement(name = "LONGITUDE")
	private String longitude;

	/**
	 * 纬度
	 */
	@XmlElement(name = "LATITUDE")
	private String latitude;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFull_flag() {
		return full_flag;
	}

	public void setFull_flag(String fullFlag) {
		full_flag = fullFlag;
	}

	public String getDis_range() {
		return dis_range;
	}

	public void setDis_range(String disRange) {
		dis_range = disRange;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staffNo) {
		staff_no = staffNo;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppFlowCountRequest [dis_range=");
		builder.append(dis_range);
		builder.append(", full_flag=");
		builder.append(full_flag);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", userid=");
		builder.append(userid);
		builder.append(", staff_no=");
		builder.append(staff_no);
		builder.append(", idCard=");
		builder.append(idCard);
		builder.append("]");
		return builder.toString();
	}

}
