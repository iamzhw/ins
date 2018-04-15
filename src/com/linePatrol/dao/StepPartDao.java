package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
public interface StepPartDao {
	/**
	 * 步巡段查询
	 * 
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> query(Query query);
	
	/**
     * @param area_id
     * @return
     */
    List<Map<String, Object>> getGldByAreaId(String area_id);
	
    
    /**
     * @param map
     * @return
     */
    List<Map<String, Object>> getRelayByCableId(Map<String, Object> map);
    
    /**
     * 查询光缆和中继段名称
     * @param cable_id
     * @param relay_id
     * @return
     */
    public Map<String, Object> selNameByCRID(Map<String, Object> map);
    
    /**
     * 
     * @param relay_id
     * @return
     */
    public List<Map<String, Object>> stepPartEquip(Map<String, Object> map);
    
    /**
     * @param steppart_name
     * @return
     */
    List<Map<String, Object>> selOnlyStepPartName(String steppart_name);
    
    
    /**
     * 
     * @param cable_id
     * @param relay_id
     * @return
     */
    public List<Map<String, Object>> judgeIsTaskEquip(Map<String, Object> paramap);
    
    
    /**
     * 
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
     * 修改刚插入的步巡段中的非地标设施点的is_part字段状态为1
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
	 * 根据步巡段id找光缆段id和名称
	 * @param allot_id 
	 * @return
	 */
    Map<String, Object> selCRByAllotID(String allot_id);
    
    
    /**
     * 根据步巡段id查找出相应修改的list集合
     * @param allot_id
     * @return
     */
    public List<Map<String, Object>> upSelEquip(Map<String, Object> map);
    
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
     * 将删除步巡段的点给备份到历史表中
     * @param map
     */
    public void intoHisDelAllot(Map<String,Object> map);
    
    /**
     * 将删除步巡段的点删除掉
     * @param map
     */
    public void delHisDelAllot(Map<String,Object> map);
    
    /**
     * 查询设施点列表
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> queryStepEquipList(Map<String, Object> map);
    
    /**更新设施点顺序
     * @param para
     */
    void updateEquipOrder(Map<String, Object> para);
    
    /**新增设施点顺序
     * @param para
     */
    void insertEquipOrder(Map<String, Object> para);
    
    /**新增设施点
     * @param para
     */
    void addStepEquip(Map<String, Object> para);
    
    /**
     *查询设施点序列 
     */
    int getStepEquipSeq();
    
    /**
     * 查询设施类型
     * @param map
     * @return
     */
    List<Map<String, Object>> queryEquipType(Map<String, Object> map);
    
    /**修改步巡段
     * @param para
     */
    void updateStepPart(Map<String, Object> para);
    
}
