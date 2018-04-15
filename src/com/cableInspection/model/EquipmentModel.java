package com.cableInspection.model;

public class EquipmentModel {
	/**
	 * 资源设备ID
	 */
	private Long phy_eqp_id;

	/**
	 * 设备名称
	 */
	private String name;

	/**
	 * 设备编码
	 */
	private String no;

	/**
	 * 设备规格ID
	 */
	private String res_spec_id;

	/**
	 * 区域ID(市级)
	 */
	private Integer parent_area_id;

	/**
	 * 区域ID(区县级)
	 */
	private Integer area_id;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 设备点等级
	 */
	private String point_level;
	
	/**
	 * 设备所在子区域
	 */
	private String son_zone;

	public Long getPhy_eqp_id() {
		return phy_eqp_id;
	}

	public void setPhy_eqp_id(Long phyEqpId) {
		phy_eqp_id = phyEqpId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getRes_spec_id() {
		return res_spec_id;
	}

	public void setRes_spec_id(String resSpecId) {
		res_spec_id = resSpecId;
	}

	public Integer getParent_area_id() {
		return parent_area_id;
	}

	public void setParent_area_id(Integer parentAreaId) {
		parent_area_id = parentAreaId;
	}

	public Integer getArea_id() {
		return area_id;
	}

	public void setArea_id(Integer areaId) {
		area_id = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPoint_level() {
		return point_level;
	}

	public void setPoint_level(String point_level) {
		this.point_level = point_level;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EquipmentModel [address=");
		builder.append(address);
		builder.append(", area_id=");
		builder.append(area_id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", no=");
		builder.append(no);
		builder.append(", parent_area_id=");
		builder.append(parent_area_id);
		builder.append(", phy_eqp_id=");
		builder.append(phy_eqp_id);
		builder.append(", point_level=");
		builder.append(point_level);
		builder.append(", res_spec_id=");
		builder.append(res_spec_id);
		builder.append("]");
		return builder.toString();
	}

	public String getSon_zone() {
		
				return son_zone;
			
	}

	public void setSon_zone(String son_zone) {
		
				this.son_zone = son_zone;
			
	}
}
