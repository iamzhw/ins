package icom.webservice.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlType;

/**
 * 待办任务数量接口处理结果
 */
@XmlType(propOrder = { "cnt", "type" })
public class CountResult {

	/**
	 * 待办任务数量
	 */
	private Integer cnt;

	
	/**
	 * 功能类别
	 */
	private Integer type;

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CountResult [cnt=");
		builder.append(cnt);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
}
