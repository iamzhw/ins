package icom.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cableInspection.model.PointModel;


public interface CableInterfaceService {

	String allTask(String json);
	
	String allPonitsByTask(String json);

	String uploadPhoto(HttpServletRequest request);

	String saveGd(HttpServletRequest request);

	String getZones(HttpServletRequest request);

	String getTroubleTypes(HttpServletRequest request);

	String getConsPoints(HttpServletRequest request);

	String getBills(HttpServletRequest request);

	String getTroubles(HttpServletRequest request);

	String getSetting(HttpServletRequest request);

	String uploadPoints(HttpServletRequest request);

	String searchEquipInfoService(HttpServletRequest request);

	String getNocTask(HttpServletRequest request);

	String queryTroubleBills(String json);

	String queryTroubleBillDetail(String json);

	String troubleBillOperation(String json);
	
	String queryOnLineMembers(String json);
	
	String signKeepPoint(String json);
	
	/**
	 * 获取检查项接口
	 * @param request
	 * @return
	 */
	String getCheckCondition(HttpServletRequest request);
	
	/**
	 * 获取隐患类型接口
	 * @param request
	 * @return
	 */
	String getTroubleType(HttpServletRequest request);

	String keepPoints(String json);

	String getStaffList(String json);

	String keepPlanBuild(String json);
	
	/**
	 * 查询光缆或光缆段是否存在
	 * @param request
	 * @return
	 */
	String queryCableExits(HttpServletRequest request);
	
	/**
	 * 采集光缆坐标
	 * @param request
	 * @return
	 */
	String cableCoordinateRecord(HttpServletRequest request);
	
	/**
	 * 已采集的光缆段坐标查询
	 * @param request
	 * @return
	 */
	String queryCableCoordinate(HttpServletRequest request);
	
	String queryOwnEqpByDis(String json);

	/**
	 * 修改光缆段上的路由
	* @Title: modifyPointInSect
	* @param @param request
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 上午10:52:29
	 */
	String modifyPointInSect(HttpServletRequest request);
	/**
	 * 删除光缆段上指定路由
	* @Title: deletePointInSect
	* @param @param request
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 上午10:54:00
	 */
	String deletePointInSect(HttpServletRequest request);
	/**
	* 查询网格列表
	* @Title: queryCableExits
	* @param @param request
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 下午04:22:09
	 */
	String queryGridList(HttpServletRequest request);
	/**
	 * 新增光缆段上的路由
	* @Title: addPointInSect
	* @param @param request
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 下午03:52:06
	 */
	String addPointInSect(HttpServletRequest request);
	
	String signNormalPoint(HttpServletRequest request);
	
	String zhxjTask(HttpServletRequest request);
	
}
