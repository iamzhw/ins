package icom.system.action;

import icom.system.service.CableInterfaceService;
import icom.util.BaseServletTool;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.databinding.types.soapencoding.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cableInspection.service.ArrivalService;

import util.page.BaseAction;

/**
 * @Title:CableInterfaceController
 * @Descrition:
 * @date 2017-11-22 上午10:09:06
 */
/**
 * @Title:CableInterfaceController
 * @Descrition:
 * @date 2017-11-22 上午10:09:19
 */
@Controller
@RequestMapping("mobile/lxxj")
public class CableInterfaceController extends BaseAction {

	@Resource
	private CableInterfaceService cableInterfaceService;
	
	@Resource
	private ArrivalService arrivalService;

	/**
	 * 获取指定人的任务列表及任务下的点，这里的点包括关键点和隐患点
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("allTask")
	public void allTask(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String json = BaseServletTool.getParam(request);
//		String json = "{staffId:27250,terminalType:1,longitude:118.965287,latitude:32.109458}";
		String result = cableInterfaceService.allTask(json);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("allPonitsByTask")
	public void allPonitsByTask(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		String json = BaseServletTool.getParam(request);
//		String json = "{taskId:108293,staffId:181945,terminalType:0,longitude:118.826111,latitude:31.979058}";
		String result = cableInterfaceService.allPonitsByTask(json);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 上传照片
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadPhoto")
	public void uploadPhoto(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.uploadPhoto(request);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 上报文本信息的接口（生成工单前上传照片）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveGd")
	public void saveGd(HttpServletRequest request, HttpServletResponse response) {
		String result = cableInterfaceService.saveGd(request);
		//String result = "{terminalType:1,sn:355905073944507,staffId:50968,grid_id:10864,type:1,taskId:,taskDetailId:196913,pointId:275817,longitude:118.748519,latitude:31.986667,zoneid:100,troubleIds:5,description:测试,cableName:,consUnit:,consContent:道路施工,cableLevel:,isKeep:0,isOrder:0,photoIds:1178438,,afterChangePhotoIds:,equipCode:,equipType:,is_fix:,equipName:,keepRadius:}";
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取地区列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getZones")
	public void getZones(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.getZones(request);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取隐患点列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getTroubleTypes")
	public void getTroubleTypes(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.getTroubleTypes(request);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取某人的看护任务列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getConsPoints")
	public void getConsPoints(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.getConsPoints(request);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取某人的待回单列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getBills")
	public void getBills(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.getBills(request);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取隐患点列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getTroubles")
	public void getTroubles(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.getTroubles(request);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取客户端定位坐标的间隔，上传时间的间隔，间隔单位为秒
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getSetting")
	public void getSetting(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.getSetting(request);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取客户端定位坐标的间隔，上传时间的间隔，间隔单位为秒
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadPoints")
	public void uploadPoints(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.uploadPoints(request);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 根据设备（光交、电交、电杆、人井）编码模糊、类型模糊查询出设备编码，设备名称
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchEquipInfo")
	public void searchEquipInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.searchEquipInfoService(request);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 根据待办缆线任务ID,查询该缆线任务下所有点信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getNocTask")
	public void getNocTask(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.getNocTask(request);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/NOCArrivalRate.do")
	public void queryNOC(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String rs = arrivalService.NOCQuery(request);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 查询隐患列表接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryTroubleBills.do")
	public void queryTroubleBills(HttpServletRequest request, HttpServletResponse response){
		String json = BaseServletTool.getParam(request);
//		String json = "{staff_id:12810001217586,terminalType:1,longitude:118.965287,latitude:32.109458}";
		String rs = cableInterfaceService.queryTroubleBills(json);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 查询隐患工单详细接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryTroubleBillDetail.do")
	public void queryTroubleBillDetail(HttpServletRequest request, HttpServletResponse response){
		String json = BaseServletTool.getParam(request);
//		String json = "{staff_id:18989,terminalType:1,longitude:118.965287,latitude:32.109458,bill_id:4938}";
		String rs = cableInterfaceService.queryTroubleBillDetail(json);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 隐患工单操作接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/troubleBillOperation.do")
	public void troubleBillOperation(HttpServletRequest request, HttpServletResponse response){
		String json = BaseServletTool.getParam(request);
//		String json = "{staff_id:15339,terminalType:1,longitude:118.965287," +
//				"latitude:32.109458,bill_id:4938,area_id:3," +
//				"inspector:18989,work_type:1,complete_time:2017-03-20}";
		String rs = cableInterfaceService.troubleBillOperation(json);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 查询在线人员接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryOnLineMembers.do")
	public void queryOnLineMembers(HttpServletRequest request, HttpServletResponse response){
		String json = BaseServletTool.getParam(request);
//		String json = "{staff_id:50532,terminalType:1,longitude:118.965287,latitude:32.109458}";
		String rs = cableInterfaceService.queryOnLineMembers(json);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 看护任务签到接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/signKeepPoint.do")
	public void signKeepPoint(HttpServletRequest request, HttpServletResponse response){
		String json = BaseServletTool.getParam(request);
//		String json = "{staff_id:18989,terminalType:1,sn:865166029832782,longitude:118.74924,latitude:31.986401,task_id:127649,sign_type:2}";
		String rs = cableInterfaceService.signKeepPoint(json);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 获取检查项接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCheckCondition.do")
	public void getCheckCondition(HttpServletRequest request, HttpServletResponse response){
		String rs = cableInterfaceService.getCheckCondition(request);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 获取隐患类型接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getTroubleType.do")
	public void getTroubleType(HttpServletRequest request, HttpServletResponse response){
		String rs = cableInterfaceService.getTroubleType(request);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 查询光缆或光缆段是否存在
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCableExits.do")
	public void queryCableExits(HttpServletRequest request, HttpServletResponse response){
		String rs = cableInterfaceService.queryCableExits(request);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 采集光缆坐标
	 * @param request
	 * @param response
	 */
	@RequestMapping("/cableCoordinateRecord.do")
	public void cableCoordinateRecord(HttpServletRequest request, HttpServletResponse response){
		String rs = cableInterfaceService.cableCoordinateRecord(request);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 已采集的光缆段坐标查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCableCoordinate.do")
	public void queryCableCoordinate(HttpServletRequest request, HttpServletResponse response){
		String rs = cableInterfaceService.queryCableCoordinate(request);
		BaseServletTool.sendParam(response, rs);
	}
	
	/*
	 * 看护点列表接口
	 */
	@RequestMapping("/keepPoints.do")
	public void keepPoints(HttpServletRequest request, HttpServletResponse response){
		String json = BaseServletTool.getParam(request);
//		String json = "{staff_id:50532,terminalType:1,longitude:118.965287,latitude:32.109458}";
		String rs = cableInterfaceService.keepPoints(json);
		BaseServletTool.sendParam(response, rs);
	}
	
	/*
	 * 人员列表接口
	 */
	@RequestMapping("/getStaffList.do")
	public void getStaffList(HttpServletRequest request, HttpServletResponse response){
		String json = BaseServletTool.getParam(request);
//		String json = "{staff_id:50532,terminalType:1,longitude:118.965287,latitude:32.109458}";
		String rs = cableInterfaceService.getStaffList(json);
		BaseServletTool.sendParam(response, rs);
	}
	
	/*
	 * 看护点派发接口
	 */
	@RequestMapping("/keepPlanBuild.do")
	public void keepPlanBuild(HttpServletRequest request, HttpServletResponse response){
		String json = BaseServletTool.getParam(request);
//		String json = "{staff_id:50532,terminalType:1,longitude:118.965287,latitude:32.109458}";
		String rs = cableInterfaceService.keepPlanBuild(json);
		BaseServletTool.sendParam(response, rs);
	}
	/*
	 * 承包设备展示
	 */
	@RequestMapping("/queryOwnEqpByDis.do")
	public void queryOwnEqpByDis(HttpServletRequest request, HttpServletResponse response){
		String json = BaseServletTool.getParam(request);
		String rs = cableInterfaceService.queryOwnEqpByDis(json);
		BaseServletTool.sendParam(response, rs);
	}
	/**
	 * 修改光缆段上的路由
	* @Title: modifyPointInSect
	* @param @param request
	* @param @param response    设定文件
	* @return void    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 上午10:43:06
	 */
	@RequestMapping("/modifyPointInSect.do")
	public void modifyPointInSect(HttpServletRequest request, HttpServletResponse response){
		String rs = cableInterfaceService.modifyPointInSect(request);
		BaseServletTool.sendParam(response, rs);
	}
	/**
	 * 删除光缆段上指定路由
	* @Title: deletePointInSect
	* @param @param request
	* @param @param response    设定文件
	* @return void    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 上午10:43:19
	 */
	@RequestMapping("/deletePointInSect.do")
	public void deletePointInSect(HttpServletRequest request, HttpServletResponse response){
		String rs = cableInterfaceService.deletePointInSect(request);
		BaseServletTool.sendParam(response, rs);
	}
	/**
	 * 查询网格列表
	* @Title: queryGridList
	* @param @param request
	* @param @param response    设定文件
	* @return void    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 下午04:21:32
	 */
	@RequestMapping("/queryGridList.do")
	public void queryGridList(HttpServletRequest request, HttpServletResponse response){
		String rs = cableInterfaceService.queryGridList(request);
		BaseServletTool.sendParam(response, rs);
	}
	/**
	 * 新增光缆段上的路由
	* @Title: addPointInSect
	* @param @param request
	* @param @param response    设定文件
	* @return void    返回类型
	* @throws
	* @author zhangll
	* @date 2017-5-25 下午04:20:59
	 */
	@RequestMapping("/addPointInSect.do")
	public void addPointInSect(HttpServletRequest request, HttpServletResponse response){
		String rs = cableInterfaceService.addPointInSect(request);
		BaseServletTool.sendParam(response, rs);
	}
	
	/**
	 * 
	* @Title: signNormalPoint
	* @Description: 非关键点签到
	* @param @param request
	* @param @param response    设定文件
	* @return void    返回类型
	* @date 2017-11-22 上午10:25:58
	* @throws
	 */
	@RequestMapping("signNormalPoint")
	public void signNormalPoint(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.signNormalPoint(request);
		BaseServletTool.sendParam(response, result);
	}
	
	/**
	 * 
	* @Title: zhxjTask
	* @Description: TODO(综合巡检任务融合)
	* @param @param request
	* @param @param response    设定文件
	* @return void    返回类型
	* @date 2018-1-9 下午08:16:01
	* @throws
	 */
	@RequestMapping("zhxjTask")
	public void zhxjTask(HttpServletRequest request,
			HttpServletResponse response) {
		String result = cableInterfaceService.zhxjTask(request);
		BaseServletTool.sendParam(response, result);
	}
}
