package com.linePatrol.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import util.page.UIPage;

public interface StepPartService {
	/**
	 * 计划查询
	 * 
	 * @param request
	 * @param pager
	 * @return
	 */
	public Map<String,Object> query(Map<String, Object> para, UIPage pager);
	
	/**
	 * 查询该段落线路下的非地标步巡设施点
	 * @param paramap
	 * @return
	 */
	public Map<String, Object> addStepPart(Map<String, Object> paramap);
	
	
	/**
	 * 查询该区域下面的光缆
	 * @param paramap
	 * @return
	 */
	List<Map<String, Object>> getGldByAreaId(String area_id);
	
	/**
	 * 查询该光缆下面的中继段id
     * @param cable_id
     * @return
     */
    List<Map<String, Object>> getRelayByCableId(Map<String, Object> map);
    
    /**
	 * 查询该区域下面的步巡段名称是否重复
	 * @param paramap
	 * @return
	 */
	List<Map<String, Object>> selOnlyStepPartName(String steppart_name);
	
	/**
	 * 查询该线路段落下的非地标步巡点是否已派发任务
	 * @param paramap
	 * @return
	 */
	List<Map<String, Object>> judgeIsTaskEquip(Map<String, Object> paramap);
	
	
	/**
     * 查询该区域下光缆对应的干线频次
     * @param cable_id
     * @param areaId
     * @return
     */
    public String judgeCircle(Map<String, Object> paramap);
    
    /**
     * 根据步巡设施点id查找order
     * @param equip_Id
     * @return
     */
    public String selOrderByEquipID(String equip_Id);
    
    /**
     * 插入步巡段
     * @param map
     */
    public void insertEquipAllot(Map<String, Object> map);
    
    /**
     * 修改刚插入的非地标步巡段中的设施点的is_part字段状态为1
     * @param map
     */
    public void upIsPartByMap(Map<String, Object> map);
    
    
    /**
     * 根据步巡段id和区域id来查找当前线路的周期
     * @param AllotID
     * @param areaId
     * @return
     */
    public String judgeCircleByAllotID(Map<String, Object> map);
    
	
	/**
	 * 修改时查询该段落线路下的设施点
	 * @param paramap
	 * @return
	 */
	public Map<String, Object> upSelEquip(Map<String, Object> map);
	
	/**
     * 将需要修改的步巡段的的is_part字段状态为0，用作重新划分
     * @param map
     */
    public void upIsPartByAllotID(Map<String, Object> map);
    
    /**
     * 根据步巡段idx修改步巡段相应信息
     * @param map
     */
    public void upStepAllotByAllotID(Map<String,Object> map);
    
    /**
     * 删除步巡段
     * @param allot_id
     */
    public void delStepPart(String allot_id);
    
    /**
     * 查询光缆和中继段名称
     * @param cable_id
     * @param relay_id
     * @return
     */
    public Map<String, Object> selNameByCRID(Map<String, Object> map);
    
    /**
	 * 根据步巡段id找光缆段id和名称
	 * @param allot_id 
	 * @return
	 */
    Map<String, Object> selCRByAllotID(String allot_id);

    /**
     * 将删除步巡段的点给备份到历史表中
     * @param map
     */
    public void intoHisDelAllot(Map<String,Object> map);
    
    /**
     * 将删除步巡段的点删除掉
     * @param map
     */
    public void delHisDelAllot(Map<String,Object> map);
    
    /**查询设施点集合
     * @param para
     * @return
     * @
     */
    Map<String, Object> queryStepEquipList(Map<String, Object> para, UIPage pager);
    
    /**导入设施点from步巡段管理
     * @param request,file
     * @return
     * @
     */
    public Object importDo(HttpServletRequest request, MultipartFile file);
}
