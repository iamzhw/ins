package icom.webservice.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 待办数量接口响应类
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "task")
public class AppFlowCountResponse {

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
	 * 处理结果
	 */
	@XmlElement(name = "data")
	private CountResult countResult;

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

	public CountResult getCountResult() {
		return countResult;
	}

	public void setCountResult(CountResult countResult) {
		this.countResult = countResult;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppFlowCountResponse [countResult=");
		builder.append(countResult);
		builder.append(", ifResult=");
		builder.append(ifResult);
		builder.append(", ifResultInfo=");
		builder.append(ifResultInfo);
		builder.append("]");
		return builder.toString();
	}
}