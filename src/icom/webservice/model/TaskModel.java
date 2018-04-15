package icom.webservice.model;

/**
 * 待办任务实体模型
 */
public class TaskModel {

	/**
	 * 任务Id
	 */
	private String taskId;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 计划Id
	 */
	private String planId;

	/**
	 * 计划周期
	 */
	private String planCircle;

	/**
	 * 任务开始时间
	 */
	private String startTime;

	/**
	 * 任务结束时间
	 */
	private String completeTime;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanCircle() {
		return planCircle;
	}

	public void setPlanCircle(String planCircle) {
		this.planCircle = planCircle;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskModel [completeTime=");
		builder.append(completeTime);
		builder.append(", planCircle=");
		builder.append(planCircle);
		builder.append(", planId=");
		builder.append(planId);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", taskId=");
		builder.append(taskId);
		builder.append(", taskName=");
		builder.append(taskName);
		builder.append("]");
		return builder.toString();
	}

}
