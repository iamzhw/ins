package icom.axx.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.page.Query;
import util.page.UIPage;


public interface CollectInfoOfRelayService {
	
	public String getInfoOfRelayCollection(String para);
	
	public String getDetailInfoOfRelayCollection(String para);

	public void collectInfoOfRelay(String para);
	
	public String getCurrentEquipId();
	
	public List<Map<String, Object>> selEquipListForWEB(Query query);

	public String equipNearTwoHundredMeter(UIPage page, HttpServletRequest request, String para);

	public String selCollectPartNearUser(HttpServletRequest request, String para);
	
	public List<Map<String, Object>> selListOfCollectPartNearUser(List<Map<String, Object>> list);
	
	public String selEquipInfo(UIPage page, HttpServletRequest request,String para);

	public String updEquipInfo(String para);

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
	
	/**
	 * 查询已自己所在坐标1000平方米正方形之内的所有的路由设施点
	 */
	List<Map<String, Object>> selXYStepEquipByUserId(Map<String, Object> paramap);

}
