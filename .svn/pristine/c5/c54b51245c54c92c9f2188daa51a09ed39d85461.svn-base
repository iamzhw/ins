package icom.axx.service;

import javax.servlet.http.HttpServletRequest;

public interface AxxInterfaceService {

	/**
	 * 获取巡线员标准路线坐标
	 * @param param
	 * @return
	 */
	String getStandardRoute(String param);
	
	/**
	 * 获取当前用户当天的任务及巡线点（site_type=1关键点）
	 * @param param
	 * @return
	 */
	String getTaskByUserId(String param);
	
	/**
	 * 从手机端获取轨迹提醒信息
	 * @param param
	 * @return
	 */
	String uploadWarningMsgs(String param);
    /**
     * @param para
     * @return
     */
    String getRepeaters(String para);

    /**
     * @param para
     * @return
     */
    String saveInspInfo(String para);

    /**
     * @param request
     * @return
     */
    String uploadPhoto(HttpServletRequest request);

    /**
     * @param para
     * @return
     */
    String getArrivalRate(String para);

    /**
     * @param para
     * @return
     */
    String uploadDanger(String para);

    /**
     * @param para
     * @return
     */
    String dealDanger(String para);

    /**
     * @param para
     * @return
     */
    String getOrderList(String para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    String getCableInfos(String para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    String getStaffInfos(String para);

    // 巡线时长
    String getLineTimes(String para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    String getKeyArrivalRate(String para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    String uploadHoleCheck(String para);

    /**
     * 作用： 　　*作者：
     * 
     * @param para
     * @return
     */
    String getLineTasks(String para);

	String getStepArrivalRate(String para);
	
	/**
	 * 获取区域列表信息
	 * @param para
	 * @return
	 */
	String getAreaInfos(String para);
	
	/**
	 * 根据区域id查找光缆中继段
	 * @param para
	 * @return
	 */
	String getCRByAreaId(String para);
	
	/**
	 * 根据经纬度光缆中继段id等信息获取最近的一个点
	 * @param para
	 * @return
	 */
	String getLatelyStepPoint(String para);
}
