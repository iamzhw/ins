package icom.webservice.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 待办任务实体类
 */
@XmlType(propOrder = { "rt_id", "spc_major_id", "position_id", "account_name",
		"deal_status", "plan_theme", "start_date", "end_date", "node_name",
		"job_flag", "cycle", "longitude", "latitude", "distance" })
public class TaskInfo {

	/**
	 * 工单编号
	 */
	private String rt_id;

	/**
	 * 专业(固定值：线路)
	 */
	private String spc_major_id;

	/**
	 * 岗位ID(传空)
	 */
	private String position_id;

	/**
	 * OA账号ID(传空)
	 */
	private String account_name;

	/**
	 * 待办任务状态(固定值：2)
	 */
	private String deal_status;

	/**
	 * 作业计划主题
	 */
	private String plan_theme;

	/**
	 * 派发时间
	 */
	private String start_date;

	/**
	 * 执行截止日期
	 */
	private String end_date;

	/**
	 * 节点/设备/网元节点名称(固定值：缆线巡检)
	 */
	private String node_name;

	/**
	 * 作业项标示(固定值：5)
	 */
	private String job_flag;

	/**
	 * 周期
	 */
	private String cycle;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 距离
	 */
	private String distance;

	@XmlElement(name = "RT_ID")
	public String getRt_id() {
		return rt_id;
	}

	public void setRt_id(String rtId) {
		rt_id = rtId;
	}

	@XmlElement(name = "SPC_MAJOR_ID")
	public String getSpc_major_id() {
		return spc_major_id;
	}

	public void setSpc_major_id(String spcMajorId) {
		spc_major_id = spcMajorId;
	}

	@XmlElement(name = "POSITION_ID")
	public String getPosition_id() {
		return position_id;
	}

	public void setPosition_id(String positionId) {
		position_id = positionId;
	}

	@XmlElement(name = "ACCOUNT_NAME")
	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String accountName) {
		account_name = accountName;
	}

	@XmlElement(name = "DEAL_STATUS")
	public String getDeal_status() {
		return deal_status;
	}

	public void setDeal_status(String dealStatus) {
		deal_status = dealStatus;
	}

	@XmlElement(name = "PLAN_Theme")
	public String getPlan_theme() {
		return plan_theme;
	}

	public void setPlan_theme(String planTheme) {
		plan_theme = planTheme;
	}

	@XmlElement(name = "START_DATE")
	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String startDate) {
		start_date = startDate;
	}

	@XmlElement(name = "END_DATE")
	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String endDate) {
		end_date = endDate;
	}

	@XmlElement(name = "NODE_NAME")
	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String nodeName) {
		node_name = nodeName;
	}

	@XmlElement(name = "JOB_FLAG")
	public String getJob_flag() {
		return job_flag;
	}

	public void setJob_flag(String jobFlag) {
		job_flag = jobFlag;
	}

	@XmlElement(name = "CYCLE")
	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	@XmlElement(name = "LONGITUDE")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@XmlElement(name = "LATITUDE")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@XmlElement(name = "DISTANCE")
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskInfo [account_name=");
		builder.append(account_name);
		builder.append(", cycle=");
		builder.append(cycle);
		builder.append(", deal_status=");
		builder.append(deal_status);
		builder.append(", distance=");
		builder.append(distance);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", job_flag=");
		builder.append(job_flag);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", node_name=");
		builder.append(node_name);
		builder.append(", plan_theme=");
		builder.append(plan_theme);
		builder.append(", position_id=");
		builder.append(position_id);
		builder.append(", rt_id=");
		builder.append(rt_id);
		builder.append(", spc_major_id=");
		builder.append(spc_major_id);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append("]");
		return builder.toString();
	}
}
