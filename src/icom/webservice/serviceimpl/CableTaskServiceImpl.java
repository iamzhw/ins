package icom.webservice.serviceimpl;

import icom.util.JaxbUtil;
import icom.webservice.dao.CableTaskInterfaceDao;
import icom.webservice.model.AppFlowCountRequest;
import icom.webservice.model.AppFlowCountResponse;
import icom.webservice.model.AppFlowListRequest;
import icom.webservice.model.AppFlowListResponse;
import icom.webservice.model.CountResult;
import icom.webservice.model.TaskInfo;
import icom.webservice.model.TaskModel;
import icom.webservice.service.CableTaskService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.cableInspection.model.PointModel;
import com.util.MapDistance;
import com.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 缆线巡检对接智能网管,对外提供webservice接口
 */
@WebService(serviceName = "AppFlowService")
public class CableTaskServiceImpl implements CableTaskService {

	@Resource
	private CableTaskInterfaceDao cableTaskInterfaceDao;

	/**
	 * 日志服务
	 */
	private static final Logger LOGGER = Logger
			.getLogger(CableTaskServiceImpl.class);

	@Override
	public String appFlowCount(String appFlowRequest) {

		AppFlowCountResponse appFlowCountResponse = new AppFlowCountResponse();
		// 默认接口处理成功
		appFlowCountResponse.setIfResult(0);
		appFlowCountResponse.setIfResultInfo("处理成功");

		// 处理结果
		CountResult countResult = new CountResult();
		countResult.setType(3);
		countResult.setCnt(0);

		appFlowCountResponse.setCountResult(countResult);

		// 待办任务数量
		int taskSize = 0;

		// 接口返回报文
		String appFlowCountResponseXml = null;

		try {

			// 将xml报文转换为请求实体
			AppFlowCountRequest appFlowCountRequest = JaxbUtil.convertToObject(
					appFlowRequest, AppFlowCountRequest.class);

			InfoLogger("enter CableTaskServiceImpl.AppFlowCount(). AppFlowCountRequest is "
					+ appFlowCountRequest);

			if (null == appFlowCountRequest) {
				appFlowCountResponse.setIfResultInfo("参数解析错误!");
				appFlowCountResponseXml = JaxbUtil.convertToXml(
						appFlowCountResponse, "GB2312");
				InfoLogger("exit CableTaskServiceImpl.AppFlowCount(). AppFlowCountResponse is "
						+ appFlowCountResponseXml);
				return appFlowCountResponseXml;
			}

			// 用户账号
			String staffNo = appFlowCountRequest.getUserid();
			// 全量查询标识
			String fullFlag = appFlowCountRequest.getFull_flag();

			// 查询业务人员所有待办任务
			List<TaskModel> taskList = cableTaskInterfaceDao
					.getAllTaskByDeptNo(staffNo);

			if (CollectionUtils.isEmpty(taskList)) {
				appFlowCountResponse.setIfResultInfo("用户不存在或目前暂无待办巡检作业!");
				appFlowCountResponseXml = JaxbUtil.convertToXml(
						appFlowCountResponse, "GB2312");
				InfoLogger("exit CableTaskServiceImpl.AppFlowCount(). AppFlowCountResponse is "
						+ appFlowCountResponseXml);
				return appFlowCountResponseXml;
			}

			// 非全量查询
			if ("N".equals(fullFlag)) {

				// 查询距离范围
				double distanceRange = Double.parseDouble(appFlowCountRequest
						.getDis_range());
				// 经度
				double longitude = Double.parseDouble(appFlowCountRequest
						.getLongitude());
				// 纬度
				double latitude = Double.parseDouble(appFlowCountRequest
						.getLatitude());

				// 遍历待办任务列表
				for (TaskModel task : taskList) {

					// 获取计划ID
					String planId = task.getPlanId();
					if (StringUtils.isEmpty(planId))
						continue;

					// 获得该缆线计划下所有缆线点坐标信息
					List<PointModel> pointList = cableTaskInterfaceDao
							.getAllPointsByPlanId(planId);
					if (CollectionUtils.isEmpty(pointList))
						continue;

					// 遍历点坐标集合,如果存在点距离在distanceRange范围内,则返回true,否则返回false
					if (isExist(pointList, distanceRange, longitude, latitude))
						++taskSize;
				}

				if (taskSize == 0) {
					appFlowCountResponse.setIfResultInfo("用户 "
							+ appFlowCountRequest.getDis_range()
							+ "米范围内不存在待办巡检任务!");
					appFlowCountResponseXml = JaxbUtil.convertToXml(
							appFlowCountResponse, "GB2312");
					InfoLogger("exit CableTaskServiceImpl.AppFlowCount(). AppFlowCountResponse is "
							+ appFlowCountResponseXml);
					return appFlowCountResponseXml;
				}

			} else {
				// 其他情况均按全量查询处理,直接返回所有待办任务数量
				taskSize = taskList.size();
			}

			countResult.setCnt(taskSize);
			appFlowCountResponseXml = JaxbUtil.convertToXml(
					appFlowCountResponse, "GB2312");
			InfoLogger("exit CableTaskServiceImpl.AppFlowCount(). AppFlowCountResponse is "
					+ appFlowCountResponseXml);
			return appFlowCountResponseXml;

		} catch (Exception e) {

			LOGGER.error(
					"CableTaskServiceImpl.AppFlowCount(). got exception. ", e);
			appFlowCountResponse.setIfResult(1);
			appFlowCountResponse.setIfResultInfo("其他错误!");
			appFlowCountResponseXml = JaxbUtil.convertToXml(
					appFlowCountResponse, "GB2312");
			InfoLogger("exit CableTaskServiceImpl.AppFlowCount(). AppFlowCountResponse is "
					+ appFlowCountResponseXml);
			return appFlowCountResponseXml;
		}

	}

	@Override
	public String appFlowList(String appFlowRequest) {

		AppFlowListResponse appFlowListResponse = new AppFlowListResponse();
		// 默认接口处理成功
		appFlowListResponse.setIfResult(0);
		appFlowListResponse.setIfResultInfo("处理成功");
		appFlowListResponse.setType(3);

		// 接口返回报文
		String appFlowListResponseXml = null;

		// 存放待办任务列表数据
		List<TaskInfo> taskInfoList = new ArrayList<TaskInfo>();

		// 分页参数,页码
		String pageCount = null;
		// 分页参数,每页数
		String pageRow = null;

		try {

			// 将xml报文转换为请求实体
			AppFlowListRequest appFlowListRequest = JaxbUtil.convertToObject(
					appFlowRequest, AppFlowListRequest.class);

			InfoLogger("enter CableTaskServiceImpl.AppFlowList(). AppFlowListRequest is "
					+ appFlowListRequest);

			if (null == appFlowListRequest) {
				appFlowListResponse.setIfResultInfo("参数解析错误!");
				appFlowListResponseXml = JaxbUtil.convertToXml(
						appFlowListResponse, "GB2312");
				InfoLogger("exit CableTaskServiceImpl.AppFlowList(). AppFlowListRequest is "
						+ appFlowListResponseXml);
				return appFlowListResponseXml;
			}

			// 用户账号
			String staffNo = appFlowListRequest.getUserid();

			// 全量查询标识
			String fullFlag = appFlowListRequest.getFull_flag();
			pageCount = appFlowListRequest.getPageCount();
			pageRow = appFlowListRequest.getPageRow();

			// 返回用户ID
			appFlowListResponse.setUserid(staffNo);

			// 查询业务人员所有待办任务
			List<TaskModel> taskList = cableTaskInterfaceDao
					.getAllTaskByDeptNo(staffNo);

			if (CollectionUtils.isEmpty(taskList)) {
				appFlowListResponse.setIfResultInfo("用户不存在或目前暂无待办巡检作业");
				appFlowListResponseXml = JaxbUtil.convertToXml(
						appFlowListResponse, "GB2312");
				InfoLogger("exit CableTaskServiceImpl.AppFlowList(). AppFlowListResponse is "
						+ appFlowListResponseXml);
				return appFlowListResponseXml;
			}

			// 临时对象,存放待办任务信息
			TaskInfo taskInfo = null;

			// 非全量查询
			if ("N".equals(fullFlag)) {

				// 查询距离范围
				double distanceRange = Double.parseDouble(appFlowListRequest
						.getDis_range());
				// 经度
				double longitude = Double.parseDouble(appFlowListRequest
						.getLongitude());
				// 纬度
				double latitude = Double.parseDouble(appFlowListRequest
						.getLatitude());

				// 遍历待办任务列表
				for (TaskModel task : taskList) {

					// 获取计划ID
					String planId = task.getPlanId();
					if (StringUtils.isEmpty(planId))
						continue;

					// 获得该缆线计划下所有缆线点坐标信息
					List<PointModel> pointList = cableTaskInterfaceDao
							.getAllPointsByPlanId(planId);
					if (CollectionUtils.isEmpty(pointList))
						continue;

					// 对点坐标集合按照距离排序(从小到大),返回距离最小的点
					PointModel nearestPoint = getNearestPoint(pointList,
							longitude, latitude);

					// 计算最小点的距离
					double nearestDistance = MapDistance.getDistance(latitude,
							longitude, Double.parseDouble(nearestPoint
									.getLatitude()), Double
									.parseDouble(nearestPoint.getLongitude()));

					if (nearestDistance <= distanceRange) {
						taskInfo = new TaskInfo();
						// 构造任务信息
						fillTaskInfo(taskInfo, task, nearestPoint, String
								.valueOf(nearestDistance));

						// 将符合条件的任务放入taskInfoMap
						taskInfoList.add(taskInfo);
					}
				}

				if (CollectionUtils.isEmpty(taskInfoList)) {
					appFlowListResponse.setIfResultInfo("用户 "
							+ appFlowListRequest.getDis_range()
							+ "米范围内不存在待办巡检任务!");
					appFlowListResponseXml = JaxbUtil.convertToXml(
							appFlowListResponse, "GB2312");
					InfoLogger("exit CableTaskServiceImpl.AppFlowList(). AppFlowListResponse is "
							+ appFlowListResponseXml);
					return appFlowListResponseXml;
				}

			} else {
				double longitude =0.0;
				double latitude =0.0;
				if(!StringUtil.isEmpty(appFlowListRequest.getLongitude()))
				{
					// 经度
					 longitude = Double.parseDouble(appFlowListRequest.getLongitude());
				}
				if(!StringUtil.isEmpty(appFlowListRequest.getLongitude())){
					// 纬度
					latitude = Double.parseDouble(appFlowListRequest
							.getLatitude());
				}
				// 遍历待办任务列表
				for (TaskModel task : taskList) {

					// 获取计划ID
					String planId = task.getPlanId();
					if (StringUtils.isEmpty(planId))
						continue;

					// 获得该缆线计划下所有缆线点坐标信息
					List<PointModel> pointList = cableTaskInterfaceDao
							.getAllPointsByPlanId(planId);
					if (CollectionUtils.isEmpty(pointList))
						continue;
					// 对点坐标集合按照距离排序(从小到大),返回距离最小的点
					PointModel nearestPoint = getNearestPoint(pointList,
							longitude, latitude);

					// 计算最小点的距离
					double nearestDistance = MapDistance.getDistance(latitude,
							longitude, Double.parseDouble(nearestPoint
									.getLatitude()), Double
									.parseDouble(nearestPoint.getLongitude()));
					
					taskInfo = new TaskInfo();
					// 构造任务信息
					fillTaskInfo(taskInfo, task, nearestPoint, String
							.valueOf(nearestDistance));

					// 将符合条件的任务放入taskInfoMap
					taskInfoList.add(taskInfo);

					
				}
			}

			// 返回分页列表信息
			List<TaskInfo> subTaskList = paginationList(taskInfoList,
					pageCount, pageRow);
			TaskInfo[] taskInfosArray = new TaskInfo[subTaskList==null?0:subTaskList.size()];// (TaskInfo[]) subTaskList.toArray();
			if(subTaskList!=null&&subTaskList.size()>0){
			for (int i = 0; i < subTaskList.size(); i++) {
				taskInfosArray[i] = subTaskList.get(i);
			}
			}
			TaskInfo[] taskInfosArray2 = subTaskList.toArray(new TaskInfo[subTaskList.size()==0?0:subTaskList.size()]);
			
			
			appFlowListResponse.setTaskInfos(taskInfosArray);
             
			appFlowListResponseXml = JaxbUtil.convertToXml(appFlowListResponse,
					"GB2312");
			InfoLogger("exit CableTaskServiceImpl.AppFlowList(). AppFlowListResponse is "
					+ appFlowListResponseXml);
			return appFlowListResponseXml;

		} catch (Exception e) {
			LOGGER.error("CableTaskServiceImpl.AppFlowList(). got exception. ",
					e);
			appFlowListResponse.setIfResult(1);
			appFlowListResponse.setIfResultInfo("其他错误!");
			appFlowListResponseXml = JaxbUtil.convertToXml(appFlowListResponse,
					"GB2312");
			InfoLogger("exit CableTaskServiceImpl.AppFlowList(). AppFlowListResponse is "
					+ appFlowListResponseXml);
			return appFlowListResponseXml;
		}

	}

	/**
	 * 打印Info级别日志
	 * 
	 * @param message
	 *            日志内容
	 */
	private void InfoLogger(String message) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(message);
		}
	}

	/**
	 * 遍历点坐标集合,如果存在点距离在distanceRange范围内,则返回true,否则返回false
	 * 
	 * @param pointList
	 *            点坐标集合
	 * @param distanceRange
	 *            查询范围
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @return boolean 是否存在符合条件的点
	 */
	private boolean isExist(List<PointModel> pointList, double distanceRange,
			double longitude, double latitude) {

		// 遍历点坐标集合,直到发现距离在distanceRange范围内的点为止
		for (PointModel point : pointList) {
			if (MapDistance.getDistance(latitude, longitude, Double
					.parseDouble(point.getLatitude()), Double.parseDouble(point
					.getLongitude())) <= distanceRange) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 对点坐标集合按照距离排序(从小到大),返回距离最小的点坐标
	 * 
	 * @param pointList
	 *            点坐标集合
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @return 距离最小的点坐标信息
	 */
	private PointModel getNearestPoint(List<PointModel> pointList,
			final double longitude, final double latitude) throws Exception {

		// 排序
		Collections.sort(pointList, new Comparator<PointModel>() {
			// 临时变量
			double pointDistance1, pointDistance2;

			@Override
			public int compare(PointModel point1, PointModel point2) {

				pointDistance1 = MapDistance.getDistance(latitude, longitude,
						Double.parseDouble(point1.getLatitude()), Double
								.parseDouble(point1.getLongitude()));
				pointDistance2 = MapDistance.getDistance(latitude, longitude,
						Double.parseDouble(point2.getLatitude()), Double
								.parseDouble(point2.getLongitude()));

				return pointDistance1 > pointDistance2 ? 1 : -1;
			}
		});

		// 取出距离最小的点
		return pointList.get(0);
	}

	/**
	 * 填充任务信息 taskInfo
	 * 
	 * @param taskInfo
	 *            要填充的任务信息JavaBean
	 * @param task
	 *            任务信息model
	 * @param point
	 *            点坐标信息
	 * @param distance
	 *            距离
	 * 
	 */
	private void fillTaskInfo(TaskInfo taskInfo, TaskModel task,
			PointModel point, String distance) {

		// 基础数据
		taskInfo.setRt_id(task.getTaskId());
		taskInfo.setPlan_theme(task.getTaskName());
		taskInfo.setCycle(task.getPlanCircle());
		taskInfo.setStart_date(task.getStartTime());
		taskInfo.setEnd_date(task.getCompleteTime());

		// 距离入参点距离最近的点坐标及距离
		taskInfo
				.setLongitude(StringUtils.isNotEmpty(point.getLongitude()) ? point
						.getLongitude()
						: "");
		taskInfo
				.setLatitude(StringUtils.isNotEmpty(point.getLatitude()) ? point
						.getLatitude()
						: "");
		taskInfo.setDistance(distance);

		// 固定值
		taskInfo.setSpc_major_id("线路");
		taskInfo.setPosition_id("");
		taskInfo.setAccount_name("");
		taskInfo.setDeal_status("2");
		taskInfo.setNode_name("线缆巡检");
		taskInfo.setJob_flag("5");
	}

	/**
	 * 对List分页
	 * 
	 * @param list
	 *            要分页的List
	 * @param pageCount
	 *            页码
	 * @param pageRow
	 *            每页返回数
	 * @return
	 */
	private <T> List<T> paginationList(List<T> list, String pageCount,
			String pageRow) {

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		// 分页参数,页码
		int pageCountInt = NumberUtils.toInt(pageCount, 1);

		// 分页参数,每页返回数
		int pageRowInt = NumberUtils.toInt(pageRow, 10);

		// 计算页数
		int pageSize = (list.size() + (pageRowInt - 1)) / pageRowInt;
		if (pageCountInt > pageSize) {

			pageCountInt = pageSize;
		}

		// 起始下标
		int fromIndex = (pageCountInt - 1) * pageRowInt;

		// 结束下标
		int toIndex = pageCountInt * pageRowInt;
		// 判断是否是最后一页
		if (toIndex > list.size()) {

			toIndex = list.size();
		}

		return list.subList(fromIndex, toIndex);
	}
}
