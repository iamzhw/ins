package com.cableInspection.model;

import java.util.List;

public class CableModel {
	private int lineId;
	private String lineNo;
	private String lineName;
	private int lineLevel;
	private long createStaff;
	private String createTime;
	private int modifyStaff;
	private String modifyTime;
	private List<PointModel> pointMode;
	private int isExistPlan;
	private int parentId;
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	private int lineType;
	public long getCreateStaff() {
		return createStaff;
	}

	public void setCreateStaff(long createStaff) {
		this.createStaff = createStaff;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getModifyStaff() {
		return modifyStaff;
	}

	public void setModifyStaff(int modifyStaff) {
		this.modifyStaff = modifyStaff;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public List<PointModel> getPointMode() {
		return pointMode;
	}

	public void setPointMode(List<PointModel> pointMode) {
		this.pointMode = pointMode;
	}

	public int getLineLevel() {
		return lineLevel;
	}

	public void setLineLevel(int lineLevel) {
		this.lineLevel = lineLevel;
	}

	public int getIsExistPlan() {
		
		return isExistPlan;
			
	}

	public void setIsExistPlan(int isExistPlan) {
		
		this.isExistPlan = isExistPlan;
			
	}

	public int getLineType() {
		
				return lineType;
			
	}

	public void setLineType(int lineType) {
		
				this.lineType = lineType;
			
	}
}
