package icom.webservice.model;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 待办任务列表响应类
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IfInfo")
public class AppFlowListResponse {

	/**
	 * 处理结果 0:处理成功 1:处理失败
	 */
	@XmlElement(name = "IfResult")
	private Integer ifResult;

	/**
	 * 提示信息
	 */
	@XmlElement(name = "IfResultInfo")
	private String ifResultInfo;

	/**
	 * 用户Id
	 */
	private String userid;

	/**
	 * 功能类别
	 */
	private Integer type;

	/**
	 * 待办任务信息
	 */
	@XmlElement(name = "data")
	private TaskInfo[] taskInfos;

	public Integer getIfResult() {
		return ifResult;
	}

	public void setIfResult(Integer ifResult) {
		this.ifResult = ifResult;
	}

	public String getIfResultInfo() {
		return ifResultInfo;
	}

	public void setIfResultInfo(String ifResultInfo) {
		this.ifResultInfo = ifResultInfo;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public TaskInfo[] getTaskInfos() {
		return taskInfos;
	}

	public void setTaskInfos(TaskInfo[] taskInfos) {
		this.taskInfos = taskInfos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppFlowListResponse [ifResult=");
		builder.append(ifResult);
		builder.append(", ifResultInfo=");
		builder.append(ifResultInfo);
		builder.append(", taskInfos=");
		builder.append(Arrays.toString(taskInfos));
		builder.append(", type=");
		builder.append(type);
		builder.append(", userid=");
		builder.append(userid);
		builder.append("]");
		return builder.toString();
	}

}
