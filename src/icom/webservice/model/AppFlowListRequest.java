package icom.webservice.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 待办任务列表请求类
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IfInfo")
public class AppFlowListRequest {

	/**
	 * 用户Id
	 */
	private String userid;

	/**
	 * 功能类别
	 */
	private String type;

	/**
	 * 页码
	 */
	private String pageCount;

	/**
	 * 每页返回数
	 */
	private String pageRow;

	/**
	 * 是否全量查询 Y：是，N：否
	 */
	private String full_flag;

	/**
	 * 距离范围 full_flag=N，则该字段必填
	 */
	private String dis_range;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getPageRow() {
		return pageRow;
	}

	public void setPageRow(String pageRow) {
		this.pageRow = pageRow;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppFlowListRequest [dis_range=");
		builder.append(dis_range);
		builder.append(", full_flag=");
		builder.append(full_flag);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", pageCount=");
		builder.append(pageCount);
		builder.append(", pageRow=");
		builder.append(pageRow);
		builder.append(", type=");
		builder.append(type);
		builder.append(", userid=");
		builder.append(userid);
		builder.append("]");
		return builder.toString();
	}

}
