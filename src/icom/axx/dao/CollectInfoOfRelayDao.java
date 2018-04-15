package icom.axx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
public interface CollectInfoOfRelayDao {
	
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

	public void updEquipInfo(Map<String, Object> equip);
	
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

	public List<Map<String, Object>> selStepStatus(Map<String, Object> pmap);
	
	//2016/3/31 孙敏 添加 查询 1000平方米之内的所有路由设施点
	public List<Map<String, Object>> selEquipPartByXYTaskId(Map<String, Object> map);
	
	//20160513 孙敏 查询点评记录是否有效
	public List<Map<String, Object>> selComEffective(String equip_id);
	
	//20160514 孙敏修改任务状态
	public void updTaskStatus(String equip_id);
}
