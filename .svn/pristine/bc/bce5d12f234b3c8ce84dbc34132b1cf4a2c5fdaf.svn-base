package icom.axx.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.Query;
import util.page.UIPage;


public interface StepCheckService {
	
	public String getInfoOfRelayCollection(String para);
	
	public String getDetailInfoOfRelayCollection(String para);

	public void collectInfoOfRelay(String para);
	
	public String getCurrentEquipId();
	
	public List<Map<String, Object>> selEquipListForWEB(Query query);

	public String equipNearTwoHundredMeter(UIPage page, HttpServletRequest request, String para);

	public String selCollectPartNearUser(HttpServletRequest request, String para);
	
	public List<Map<String, Object>> selListOfCollectPartNearUser(List<Map<String, Object>> list);
	
	public String selEquipInfo(UIPage page, HttpServletRequest request,String para);

	public String changeViewOfMap(String para);
	
	public String selEquipsByCenterId(String para);

	public String changeEquipsLine(String para);

	public String isReferencePoint(String para);

	public List<Map<String, Object>> selAllMainEquip(Map<String, Object> map);

	public String collectInfoOfMinorRelay(String para);

	List<Map<String, Object>> selAllStepEquipByUserId(Map<String, Object> paramap);

	public String equipReviews(String para);

	public String recordOfEquipReviews(String para);

	public String picOfEquipReviews(String para);

	public String insertLostEquip(String para);

	public String showByMap(Map<String, Object> paramap);

	public List<Map<String, Object>> is_equip(Map<String, Object> paramap);
	
	public List<Map<String, Object>> no_equip(Map<String, Object> paramap);

	public void saveEquipByChange(String id,String cable_id,String relay_id,String area_id,String page,String pageSize,HttpServletRequest request);

	public List<List<Map<String, Object>>> changeRelationEquipInit(HttpServletRequest request,
			Map<String, Object> paramap);
	
	public void changeRelationEquip(HttpServletRequest request,String para);
	
	public Map<String, Object> stepEquipPart(Map<String, Object> map);
	/**
	 * 根据光缆、中继段获取步巡设施点
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getStepEquip(Map<String, Object> map);
	
	/**
	 * 查看所有的非路由点
	 * @param map
	 * @return
	 */
	public Map<String,Object> selNoRouthEquip(Map<String, Object> map);
	
	public int selEquipCount(Map<String, Object> map);

	public Map<String, Object> stepAllEquipPart(Map<String, Object> paramap);

	public void saveAllEquipByChange(String ids, String cable_id,
			String relay_id, String area_id, HttpServletRequest request);

	/**
     * @Title: edit
     * @Description:
     * @param: @param staffId
     * @param: @return
     * @return: Map<String,Object>
     * @throws
     */
    public Map<String, Object> edit(HttpServletRequest request);
    
    
    /**
     * @throws Exception
     * @Title: update
     * @Description:
     * @param: @param request
     * @return: void
     * @throws
     */
    public void update(HttpServletRequest request) throws Exception;
    
    /**
     * 孙敏
     * 2016/6/12
     * 界面按区域显示未关联上线路段落的点
     * @param areaId
     * @return
     */
    public Map<String, Object> selNoRelationPoint(String areaId);
    
    public String deleteStepEquip(String ids);
	
}
