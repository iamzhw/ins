package icom.axx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
public interface StepCheckDao {
	
	public List<Map<String, Object>> getInfoOfRelayCollection(Map<String, Object> map);
	
	public List<Map<String, Object>> getDetailInfoOfRelayCollection(Map<String, Object> map);
	
	public void addTourEquip(Map<String, Object> map);
	
	public void addLineEquipByEnd(Map<String, Object> map);
	
	public String selOrder(Map<String, Object> map);
	
	public void updLineEquip(Map<String, Object> map);
	
	public void addLineEquipByBefore(Map<String, Object> map);
	
	public void delStepTour(Map<String, Object> map);
	
	public List<Map<String, Object>> selEquipList(Map<String, Object> map);
	
	public void addStepTour(Map<String, Object> map);
	
	public String getCurrentEquipId();
	
	public String selEndEquipId(Map<String, Object> map);
	
	public List<Map<String, Object>> selEquipListForWEB(Query query);
	
	public Map<String, Object> selCollectPartNearUser(Map<String, Object> map);
	
	public List<Map<String, Object>> selEquipPartByTaskId(Map<String, Object> map);
    
    public String selEndByStart(String equip_id);
    
    public List<Map<String, Object>> selEquipInfoByTask(Map<String, Object> map);
    
    public List<Map<String, Object>> selAllMainEquip(Map<String, Object> map);

	public void collectInfoOfMinorRelay(Map<String, Object> paraMap);
	
	public List<Map<String, Object>> selMinorEquips(Map<String, Object> map);

	public void saveEquipReviewsInfo(Map<String, Object> paramap);

	public List<Map<String, Object>> recordOfEquipReviews(Map<String, Object> paramap);
	
	public List<Map<String, Object>> selEquipImgs(Map<String, Object> paramap);


	public void updStepStatus(Map<String, Object> pmap);

	public Map<String, Object> selParamForTaskId(Map<String, Object> paramap);

	public Map<String, Object> selStepStatus(Map<String, Object> pmap);

	public List<Map<String, Object>> is_equip(Map<String, Object> paramap);
	
	public List<Map<String, Object>> no_equip(Map<String, Object> paramap);
	
	public void updEqiupStuOfDel(int equip_id);
	
	public void updEqiupStuOfAdd(int equip_id);
	
	public void delLineEquip(Map<String, Object> map);
	
	public void delStepTourByEquipId(Map<String, Object> map);
	
	public void changeLineEquip(Map<String, Object> map);
	
	public void changeStepTour(Map<String, Object> map);
	
	public List<Map<String, Object>> selRelationEquips(Map<String, Object> paramap);
	
	public void initEquipStatus(Map<String, Object> map);
	
	public void updRelationStatus(Map<String, Object> map);
	
	public int selEquipCount(Map<String, Object> map);
	
	public void updIsEquip(int param);
	
	public Map<String, Object> selByEquipId(int para);

	public List<Map<String, Object>> stepAllEquipPart(Map<String, Object> map);
	
	public Map getEquip(String equip_id);
	
	public List<Map<String, String>> getCheckList(String equip_id);
	
	public List<Map<String, String>> getPhotoList(String equip_id);
	
	/**
	 * 根据光缆、中继段获取步巡设施点
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getStepEquip(Map map);
	
	/**
     * @Title: update
     * @Description:
     * @param: @param map
     * @return: void
     * @throws
     */
    public void update(Map map);
    
    /**
     * 凭借check_id获取隐患隐患字段拼接的字符串
     * @param check_id
     * @return
     */
    public String selTroubleByCheckId(String check_id);
    
    /**
     * 孙敏
     * 2016/6/12
     * 界面按区域显示未关联上线路段落的点
     * @param areaId
     * @return
     */
    public List<Map<String, Object>> selNoRelationPoint(String areaId);
    
    /**
     * 查询该区域下所有的非路由点用作地图展示
     * @param paramap
     * @return
     */
    public List<Map<String, Object>> noRouthEquip(Map<String, Object> paramap);
    
    /**
     * 步巡巡线完成情况查看未完成点
     * @param param
     * @return
     */
    public List<Map<String, Object>> getUnfinishStepSitesByUser(
			Map<String, Object> param);
    /**
     * 判断是否是步巡段起始点
     * @param map
     * @return
     */
    public List<Map> ifStartOrEndEquip(Map map);
    /**
     * 判断是否在步巡段中
     * @param map
     * @return
     */
    public List<Map> ifEquipInPart(Map map);
    /**
     * 删除光缆路由设施关系表
     * @param ids
     */
    public void deleteLineEquip(Map map);
    /**
     * 删除步巡点表
     * @param ids
     */
    public void deleteStepEquip(Map map);
    
}
