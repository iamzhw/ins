package icom.axx.action;

import icom.axx.service.AxxInterfaceService;
import icom.axx.service.CollectInfoOfRelayService;
import icom.axx.service.LineSiteInterfaceService;
import icom.axx.service.OutSiteInterfaceService;
import icom.util.BaseServletTool;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.page.BaseAction;
import util.page.UIPage;

import com.linePatrol.service.AutoTrackService;
import com.linePatrol.util.DateUtil;
import com.outsite.service.OutsitePlanManage;
import com.util.sendMessage.PropertiesUtil;

@Controller
@RequestMapping("mobile/gxxj")
public class AxxInterfaceController extends BaseAction {

	@Resource
	private AxxInterfaceService axxInterfaceService;

	@Resource
	private LineSiteInterfaceService lineSiteInterfaceService;

	@Resource
	private OutSiteInterfaceService outSiteInterfaceService;

	@Autowired
	OutsitePlanManage outsitePlanManage;

	@Autowired
	private CollectInfoOfRelayService collectInfoOfRelayService;

	@Autowired
	private AutoTrackService autoTrackService;

	/**
	 * 获取当前用户当天的任务及巡线点（site_type=1关键点）
	 */
	@RequestMapping("/getTaskByUserId")
	public void getTaskByUserId(HttpServletRequest request, HttpServletResponse response) {
		String param = BaseServletTool.getParam(request);
		System.out.println(DateUtil.getDateAndTime() + ":getTaskByUserId入参信息" + param);
		String result = axxInterfaceService.getTaskByUserId(param);
		System.out.println(DateUtil.getDateAndTime() + ":getTaskByUserId出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取巡线员标准路线坐标 http://localhost:8080/ins/mobile/gxxj/getStandardRoute.do
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getStandardRoute")
	public void getStandardRoute(HttpServletRequest request, HttpServletResponse response) {
		String param = BaseServletTool.getParam(request);
		// param = "{\"user_id\":\"18032\",\"inspact_date\":\"2017-12-25\"}";
		System.out.println(DateUtil.getDateAndTime() + ":getStandardRoute入参信息" + param);
		String result = axxInterfaceService.getStandardRoute(param);
		System.out.println(DateUtil.getDateAndTime() + ":getStandardRoute出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 从手机端获取轨迹提醒信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uploadWarningMsgs")
	public void uploadWarningMsgs(HttpServletRequest request, HttpServletResponse response) {
		String param = BaseServletTool.getParam(request);
		// param = "{\"user_id\":\"18032\",\"inspact_date\":\"2017-12-25\"}";
		System.out.println(DateUtil.getDateAndTime() + ":uploadWarningMsgs入参信息" + param);
		String result = axxInterfaceService.uploadWarningMsgs(param);
		System.out.println(DateUtil.getDateAndTime() + ":uploadWarningMsgs出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取中继段
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getRepeaters")
	public void getRepeaters(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// String para = "{\"userId\":\"16698\",\"sn\":\"123\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getRepeaters入参信息" + para);
		String result = axxInterfaceService.getRepeaters(para);
		System.out.println(DateUtil.getDateAndTime() + ":getRepeaters出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 保存巡线点信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveInspInfo")
	public void saveInspInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);

		// test
		// para =
		// "{\"userId\":\"16698\",\"sn\":\"123\",\"repeaterId\":\"123\",\"latitude\":\"123\",\"longitude\":\"123\",\"inspInfo\":\"测试测试\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":saveInspInfo入参信息" + para);
		String result = axxInterfaceService.saveInspInfo(para);
		System.out.println(DateUtil.getDateAndTime() + ":saveInspInfo出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 保存巡线点图片
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uploadPhoto")
	public void uploadPhoto(HttpServletRequest request, HttpServletResponse response) {
		String result = axxInterfaceService.uploadPhoto(request);
		printParam("出参信息:" + result);
		System.out.println(DateUtil.getDateAndTime() + ":uploadPhoto出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取干线任务
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getLineTask")
	public void getLineTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);

		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":getLineTask入参信息" + jsonStr);
		String result = lineSiteInterfaceService.saveAutoTrack(jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取签到点
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getSignSites")
	public void getSignSites(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		//
		// jsonStr =
		// "{\"userId\":\"16698\",\"sn\":\"123\",\"area_id\":\"3\",\"latitude\":\"31.885188\",\"longitude\":\"118.6032828\",\"query_time\":\"2015-03-03
		// 03:02:01\"}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":getSignSites入参信息" + jsonStr);
		String result = lineSiteInterfaceService.getSignSites(jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 巡线完成情况查询
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getTaskCondition")
	public void getTaskCondition(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);

		printParam("入参信息:" + jsonStr);
		// jsonStr =
		// "{\"userId\":\"16697\",\"sn\":\"123\",\"repeaterId\":\"123\",\"latitude\":\"123\",\"longitude\":\"123\",\"inspInfo\":\"临时点信息\"}";
		System.out.println(DateUtil.getDateAndTime() + ":getTaskCondition入参信息" + jsonStr);
		String result = lineSiteInterfaceService.getTaskCondition(jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 自动轨迹上传接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/autoUploadTrack")
	public void autoUploadTrack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// String jsonStr ="{\"upload_time\":\"2015-06-03
		// 09:01:34\",\"sn\":\"a000004260081e\",\"area_id\":\"3\",\"trackList\":[{\"gps_flag\":\"1\",\"is_gps\":\"0\",\"longitude\":\"118.826773\",\"latitude\":\"32.080153\",\"track_time\":\"2015-06-02
		// 15:51:08\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"0\",\"longitude\":\"118.826773\",\"latitude\":\"32.080153\",\"track_time\":\"2015-06-02
		// 15:53:10\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"0\",\"longitude\":\"118.826773\",\"latitude\":\"32.080153\",\"track_time\":\"2015-06-02
		// 15:55:15\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.86536\",\"latitude\":\"32.098282\",\"track_time\":\"2015-06-03
		// 08:15:39\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.867746\",\"latitude\":\"32.098517\",\"track_time\":\"2015-06-03
		// 08:17:39\",\"speed\":\"18.08977508544922\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.874663\",\"latitude\":\"32.099224\",\"track_time\":\"2015-06-03
		// 08:19:39\",\"speed\":\"21.600000381469727\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.882059\",\"latitude\":\"32.100498\",\"track_time\":\"2015-06-03
		// 08:21:39\",\"speed\":\"20.778112411499023\"},{\"gps_flag\":\"1\",\"is_gps\":\"0\",\"longitude\":\"118.887353\",\"latitude\":\"32.101845\",\"track_time\":\"2015-06-03
		// 08:27:55\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.887001\",\"latitude\":\"32.101596\",\"track_time\":\"2015-06-03
		// 08:29:57\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"0\",\"longitude\":\"118.886999\",\"latitude\":\"32.101591\",\"track_time\":\"2015-06-03
		// 08:31:57\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.88965\",\"latitude\":\"32.102335\",\"track_time\":\"2015-06-03
		// 08:33:58\",\"speed\":\"13.619471549987793\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.894572\",\"latitude\":\"32.104245\",\"track_time\":\"2015-06-03
		// 08:35:58\",\"speed\":\"18.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.898857\",\"latitude\":\"32.107597\",\"track_time\":\"2015-06-03
		// 08:37:59\",\"speed\":\"14.512063980102539\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.901385\",\"latitude\":\"32.11089\",\"track_time\":\"2015-06-03
		// 08:39:59\",\"speed\":\"16.299694061279297\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.904354\",\"latitude\":\"32.115013\",\"track_time\":\"2015-06-03
		// 08:41:59\",\"speed\":\"14.512063980102539\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.90745\",\"latitude\":\"32.118469\",\"track_time\":\"2015-06-03
		// 08:43:59\",\"speed\":\"1.7999999523162842\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.908364\",\"latitude\":\"32.119389\",\"track_time\":\"2015-06-03
		// 08:46:00\",\"speed\":\"4.846648216247559\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.909254\",\"latitude\":\"32.12019\",\"track_time\":\"2015-06-03
		// 08:48:00\",\"speed\":\"4.5\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.910232\",\"latitude\":\"32.121059\",\"track_time\":\"2015-06-03
		// 08:50:01\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"0\",\"longitude\":\"118.909189\",\"latitude\":\"32.120585\",\"track_time\":\"2015-06-03
		// 08:52:01\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"0\",\"longitude\":\"118.912103\",\"latitude\":\"32.122782\",\"track_time\":\"2015-06-03
		// 08:54:01\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.912394\",\"latitude\":\"32.122755\",\"track_time\":\"2015-06-03
		// 08:56:01\",\"speed\":\"11.699999809265137\"},{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.918027\",\"latitude\":\"32.126424\",\"track_time\":\"2015-06-03
		// 09:01:31\",\"speed\":\"12.600000381469727\"}],\"userId\":\"123\"}";
		// "{\"userId\":\"123\",\"area_id\":\"3\",\"sn\":\"123\",\"upload_time\":\"2015-03-12
		// 08:15:33\",\"trackList\":[{\"longitude\":\"4.9E-324\",\"latitude\":\"78.8\",\"track_time\":\"2015-03-12
		// 08:15:33\",\"speed\":\"20\"}]}";

		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":autoUploadTrack入参信息" + jsonStr);
		String result = lineSiteInterfaceService.saveAutoTrack(jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 手动坐标上传接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/handUploadTrack")
	public void handUploadTrack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// String jsonStr ="{\"upload_time\":\"2015-06-03
		// 09:01:35\",\"longitude\":\"118.918899\",\"area_id\":\"3\",\"sn\":\"a000004260081e\",\"latitude\":\"32.126867\",\"speed\":\"0.0\",\"userId\":\"18037\"}";

		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":handUploadTrack入参信息" + jsonStr);
		String result = lineSiteInterfaceService.handUploadTrack(jsonStr);
		printParam("出参信息:" + result);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 关键点签到接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveRegisterSite")
	public void saveRegisterSite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);

		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveRegisterSite入参信息" + jsonStr);
		String result = lineSiteInterfaceService.saveRegisterSite(jsonStr);
		printParam("出参信息:" + result);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/*
	 * 到位率查询
	 */
	@RequestMapping("/getArrivalRate")
	public void getArrivalRate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);

		// para =
		// "{\"userId\":\"18769\",\"sn\":\"123\",\"query_time\":\"2015-05-22\",\"end_time\":\"2015-04-01\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getArrivalRate入参信息" + para);
		String result = axxInterfaceService.getArrivalRate(para);
		printParam("出参信息:" + result);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 上传隐患单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/uploadDanger")
	public void uploadDanger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);

		// para
		// ="{\"userId\":\"18669\",\"sn\":\"355905073944507\",\"danger_name\":\"ceshi\",\"latitude\":\"31.986683\",\"longitude\":\"118.748359\",\"danger_type\":\"0\",\"high_risk\":\"0\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":uploadDanger入参信息" + para);
		String result = axxInterfaceService.uploadDanger(para);
		printParam("出参信息:" + result);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		BaseServletTool.sendParam(response, result);

	}

	/**
	 * 填写隐患处理信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealDanger")
	public void dealDanger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);

		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":dealDanger入参信息" + para);
		String result = axxInterfaceService.dealDanger(para);
		printParam("出参信息:" + result);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		BaseServletTool.sendParam(response, result);

	}

	/**
	 * 获取
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getOrderList")
	public void getOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// test
		// para = "{\"userId\":\"18173\",\"sn\":\"123\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getOrderList入参信息" + para);
		String result = axxInterfaceService.getOrderList(para);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 外力点施工，基本信息填写
	 * 
	 */
	@RequestMapping("/saveBasicOuteSite")
	public void saveBasicOuteSite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// String jsonStr
		// ="{\"site_name\":\"测试\",\"x\":120.72086793,\"y\":31.68380395,\"info_source\":0,\"affected_fiber\":0,\"relay_part\":\"1\",\"fiber_level\":1,\"line_part\":\"201\",\"landmarkno\":\"aa\",\"con_company\":\"中邮建\",\"con_address\":\"大明路\",\"con_content\":\"aa\",\"is_agreement\":0,\"site_danger_level\":1,\"con_startdate\":\"2015-05-12
		// 09:32:12\",\"pre_enddate\":\"2015-03-1202:32:12\",\"con_reponsible_by\":\"张海\",\"con_reponsible_by_tel\":\"13269896936\",\"guardian\":\"李斯\",\"guardian_tel\":\"15896963654\",\"scene_measure\":\"xxxx\",\"fiber_eponsible_by\":18769,\"fiber_eponsible_tel\":\"15489986936\",\"user_id\":\"16698\",\"fiber_uid\":\"电信现场负责人工号\",\"stay_time_part\":\"监护人时段\",\"is_plan\":0,\"area_id\":3,\"operator_name\":\"张三\",\"car_no\":\"皖A001\",\"car_type\":\"宝马\",\"mobile\":\"30000099999\"}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveBasicOuteSite入参信息" + jsonStr);
		String result = outSiteInterfaceService.saveBasicOuteSite(jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 班组长或段长审核外力点
	 * 
	 */
	@RequestMapping("/saveAuditOuteSite")
	public void saveAuditOuteSite(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr
		// ="{\"site_id\":\"4001\",\"site_name\":\"hhbk\",\"info_source\":1,\"affected_fiber\":301,\"relay_part\":\"375\",\"fiber_level\":2,\"line_part\":\"zdfgg\",\"landmarkno\":\"\",\"con_company\":\"\",\"con_address\":\"\",\"con_content\":\"\",\"is_agreement\":1,\"site_danger_level\":1,\"con_startdate\":\"2016-10-11\",\"pre_enddate\":\"2016-10-11\",\"con_reponsible_by\":\"\",\"con_reponsible_by_tel\":\"\",\"guardian\":\"\",\"guardian_tel\":\"\",\"scene_measure\":\"fjfjffg\",\"fiber_eponsible_by\":18669,\"fiber_eponsible_tel\":\"\",\"userId\":\"18669\",\"fiber_uid\":\"test_axx
		// 海燕\",\"stay_time_part\":\"\",\"is_plan\":0,\"area_id\":3,\"operator_name\":\"张三\",\"car_no\":\"皖A001\",\"car_type\":\"宝马\",\"mobile\":\"30000099999\",\"is_machaine\":\"0\",\"machaine_name\":\"\",\"residual_cable\":\"\",\"effect_service\":\"0\",\"schemeinfo\":{\"content\":\"111\",\"scheme_name\":\"哈哈哈\"}}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveAuditOuteSite入参信息" + jsonStr);
		String result = outSiteInterfaceService.saveAuditOuteSite(jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);

	}

	/**
	 * 机械手信息填报
	 * 
	 */
	@RequestMapping("/saveOperatorInfo")
	public void saveOperatorInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr =
		// "{\"out_site_id\":2,\"operator_name\":\"张三\",\"car_no\":\"苏axx\",\"car_type\":\"卡车\",\"mobile\":\"13269696589\",\"tel1\":\"小灵通\",\"tel2\":\"固话\",\"info\":\"外力影响信息\",\"user_id\":3}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveOperatorInfo入参信息" + jsonStr);
		String result = outSiteInterfaceService.saveOperatorInfo(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 外力维护方案信息填报
	 * 
	 */
	@RequestMapping("/saveOsMaintainScheme")
	public void saveOsMaintainScheme(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr
		// ="{\"scheme_name\":\"xx\",\"out_site_id\":4001,\"out_site_type\":\"\",\"content\":\"xx\",\"user_id\":18669,\"area_id\":3,\"creation_time\":\"2015-03-23
		// 23:23:23\"}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveOsMaintainScheme入参信息" + jsonStr);
		String result = outSiteInterfaceService.saveOsMaintainScheme(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 外力维护方案确认
	 * 
	 */
	@RequestMapping("/makesureScheme")
	public void makesureScheme(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr =
		// "{\"scheme_id\":1,\"commit_uid\":2,\"commit_x\":12.3,\"commit_y\":2.3,\"is_timeout\":0,}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":makesureScheme入参信息" + jsonStr);
		String result = outSiteInterfaceService.makesureScheme(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 电子围栏数据采集
	 * 
	 */
	/*
	 * @RequestMapping("/saveOsInputPoints") public void
	 * saveOsInputPoints(HttpServletRequest request, HttpServletResponse response)
	 * throws Exception { String jsonStr = BaseServletTool.getParam(request); String
	 * areaId = null; // jsonStr = //
	 * "{\"out_site_id\":1,\"plan_id\":44,\"out_site_tyle\":0,\"x\":\"11.123457\",\"y\":32.123456,\"input_userid\":1,\"input_time\":\"\",\"parent_city\":1,\"created_by\":1,\"sn\":\"123\"}";
	 * printParam("入参信息:" + jsonStr); String result =
	 * outSiteInterfaceService.saveOsInputPoints(jsonStr, areaId);
	 * printParam("出参信息:" + result); BaseServletTool.sendParam(response, result); }
	 */

	/**
	 * 埋深探位填写
	 * 
	 */
	@RequestMapping("/saveDepthProbe")
	public void saveDepthProbe(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr =
		// "{\"out_site_id\":2,\"markstone\":\"aa\",\"mstw_depth\":25.3,\"user_id\":1,\"longitude\":123.23,\"latitude\":23.3,\"remark\":\"aa\",\"sn\":\"123\"}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveDepthProbe入参信息" + jsonStr);
		String result = outSiteInterfaceService.saveDepthProbe(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 埋深探位查询
	 * 
	 */
	@RequestMapping("/getDepthProbe")
	public void getDepthProbe(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr =
		// "{\"out_site_id\":341,\"markstone\":\"aa\",\"mstw_depth\":25.3,\"user_id\":1,\"longitude\":123.23,\"latitude\":23.3,\"remark\":\"aa\",\"sn\":\"123\"}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":getDepthProbe入参信息" + jsonStr);
		String result = outSiteInterfaceService.getDepthProbe(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 埋深探位填写
	 * 
	 */
	@RequestMapping("/saveOsCheckRecord")
	public void saveOsCheckRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr =
		// "{\"user_id\":1, \"out_site_id\":2,
		// \"out_site_name\":\"xx\",\"check_time\":\"2015-01-01
		// 01:01:01\",\"is_on_out\":1,\"is_construction\":1,\"is_complete_equip\":1,\"is_complete_look\":1,\"is_know_con\":1,\"is_know\":1,\"is_single\":1,\"is_send_info\":1,\"problem_mes\":\"xxx\",\"side_progress\":\"xx\",\"rectification\":\"xx\",\"assess\":\"\",\"area_id\":3,\"created_by\":1,\"sn\":\"123\"}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveOsCheckRecord入参信息" + jsonStr);
		String result = outSiteInterfaceService.saveOsCheckRecord(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取外力点看护任务
	 */
	@RequestMapping("/getOsProtectTaskList")
	public void getOsProtectTaskList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr = "{\"userId\":1}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":getOsProtectTaskList入参信息" + jsonStr);
		String result = outSiteInterfaceService.getOsProtectTaskList(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 外力点看护坐标自动上传
	 * 
	 */
	@RequestMapping("/saveOsProtectCoordinateByAuto")
	public void saveOsProtectCoordinateByAuto(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr =
		// "{\"plan_id\":2,\"x\":123.23,\"y\":32.3,\"track_time\":\"2015-05-13
		// 12:22:12\",\"plan_time\":\"2015-05-12
		// 12:12:12\",\"parent_city\":3,\"created_by\":17017,\"sn\":\"123\",\"is_guard\":1}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveOsProtectCoordinateByAuto入参信息" + jsonStr);
		String result = outSiteInterfaceService.saveOsProtectCoordinateByAuto2(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 外力点看护坐标上传
	 * 
	 */
	@RequestMapping("/saveOsProtectCoordinate")
	public void saveOsProtectCoordinate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);

		// jsonStr =
		// "{\"plan_id\":2,\"x\":123.23,\"y\":32.3,\"track_time\":\"2015-05-13
		// 12:22:12\",\"plan_time\":\"2015-05-12
		// 12:12:12\",\"parent_city\":3,\"created_by\":17017,\"sn\":\"123\","
		// +
		// "\"is_guard\":[{\"longitude\":118,\"latitude\":10},{\"longitude\":119,\"latitude\":11},{\"longitude\":120,\"latitude\":12},{\"longitude\":121,\"latitude\":13}],\"is_gps\":1,\"gps_switch\":1}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveOsProtectCoordinate入参信息" + jsonStr);
		String result = outSiteInterfaceService.saveOsProtectCoordinate(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 查询所有光缆和中继段
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getCableInfos")
	public void getCableInfos(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// test
		// para = "{\"userId\":\"16698\",\"area_id\":\"3\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getCableInfos入参信息" + para);
		String result = axxInterfaceService.getCableInfos(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 查询所有外力点信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getOutSites")
	public void getOutSites(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// test
		// para =
		// "{\"userId\":\"16698\",\"area_id\":\"3\",\"latitude\":\"31.68022\",\"longitude\":\"119.027124\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getOutSites入参信息" + para);
		String result = outSiteInterfaceService.getOutSites(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 查询爱巡线人员信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getStaffInfos")
	public void getStaffInfos(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// test
		// para =
		// "{\"userId\":\"16698\",\"area_id\":\"3\",\"role_id\":\"146,147\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getStaffInfos入参信息" + para);
		String result = axxInterfaceService.getStaffInfos(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 外力点迁移
	 * 
	 */
	@RequestMapping("/saveOutSiteMoveInfo")
	public void saveOutSiteMoveInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr =
		// "{\"userId\":1,\"outsite_id\":2,\"old_longitude\":123.23,\"old_latitude\":32.3,\"move_time\":\"2015-02-03
		// 12:12:12\",\"new_longitude\":3.12,\"new_latitude\":3.54,\"sn\":\"123\"}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveOutSiteMoveInfo入参信息" + jsonStr);
		String result = outSiteInterfaceService.saveOutSiteMoveInfo(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 巡线时长查询
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getLineTimes")
	public void getLineTimes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// String jsonStr =
		// "{\"userId\":\"18082\",\"area_id\":\"3\",\"query_time\":\"2015-06-14\",\"personId\":\"18082\"}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":getLineTimes入参信息" + jsonStr);
		String result = lineSiteInterfaceService.getInvalidLineTime(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 查询维护方案信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getOutSchemes")
	public void getOutSchemes(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// test
		// para =
		// "{\"userId\":\"17038\",\"area_id\":\"3\",\"longitude\":\"123\",\"latitude\":\"1\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getOutSchemes入参信息" + para);
		String result = outSiteInterfaceService.getOutSchemes(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 关键点到位率查询
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getKeyArrivalRate")
	public void getKeyArrivalRate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// test
		// para =
		// "{\"userId\":\"18029\",\"start_time\":\"2015-08-21\",\"end_time\":\"2015-08-21\",\"query_time\":\"2015-08-21\",\"personId\":\"18029\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getKeyArrivalRate入参信息" + para);
		String result = axxInterfaceService.getKeyArrivalRate(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 外力点到位率查询
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getOutCheckArrivalRate")
	public void getOutCheckArrivalRate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// para =
		// "{\"userId\":\"18033\",\"query_time\":\"2015-07-10\",\"end_time\":\"2015-04-01\",\"personId\":\"18033\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getOutCheckArrivalRate入参信息" + para);
		String result = outSiteInterfaceService.getOutCheckArrivalRate(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 步巡到位率查询
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getStepArrivalRate")
	public void getStepArrivalRate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// para =
		// "{\"userId\":\"18034\",\"query_time\":\"2016-07-10\",\"end_time\":\"2015-08-31\",\"personId\":\"18034\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getStepArrivalRate入参信息" + para);
		String result = axxInterfaceService.getStepArrivalRate(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 上传人孔检查信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/uploadHoleCheck")
	public void uploadHoleCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// para =
		// "{\"userId\":\"123\",\"sn\":\"123\",\"check_time\":\"2014-04-16\",\"load_name\":\"DDD\",\"hole_no\":\"1\",\"cable_id\":\"1\",\"memo1\":\"33\",\"memo2\":\"44\",\"memo3\":\"555\",\"memo4\":\"666\",\"memo5\":\"777\",\"memo6\":\"888\",\"memo7\":\"999\",\"memo8\":\"0000\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":uploadHoleCheck入参信息" + para);
		String result = axxInterfaceService.uploadHoleCheck(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);

	}

	/**
	 * 外力点迁移信息获取 外力点迁移之前先获取范围内的外力点
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getOutsiteMoveInfo")
	public void getOutsiteMoveInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// para =
		// "{\"userId\":\"16698\",\"longitude\":118.787328,\"latitude\":32.030506}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getOutsiteMoveInfo入参信息" + para);
		String result = outSiteInterfaceService.getOutsiteMoveInfo(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取外力点列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getBasicOuteSites")
	public void getBasicOuteSites(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Logger logA = Logger.getLogger("myTest1");
		String para = BaseServletTool.getParam(request);
		logA.info(para);
		// para
		// ="{\"userId\":\"18115\",\"longitude\":118.821014,\"latitude\":32.030105,\"sn\":154645464}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getBasicOuteSites入参信息" + para);
		String result = outSiteInterfaceService.getBasicOuteSites(para);
		printParam("出参信息:" + result);
		logA.info(result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 确认外力点
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/confirmOutsite")
	public void confirmOutsite(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// para =
		// "{\"userId\":\"18077\",\"site_id\":683,\"x\":118.77770671,\"y\":32.84256799,\"longitude\":118.77770671,\"latitude\":32.84256799,\"commit_time\":\"2015-04-12
		// 09:20:13\",\"commit_opnion\":\"aaaa\",\"commit_status\":\"0\",\"flow_status\":\"5\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":confirmOutsite入参信息" + para);
		String result = outSiteInterfaceService.confirmOutsite(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取外力点电子围栏
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getElebarCoordinate")
	public void getElebarCoordinate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// para = "{\"userId\":\"18164\"}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getElebarCoordinate入参信息" + para);
		String result = outSiteInterfaceService.getElebarCoordinate(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取看护任务
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getNurseTasks")
	public void getNurseTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// para = "{\"userId\":\"16698\",\"site_id\":2}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getNurseTasks入参信息" + para);
		String result = outSiteInterfaceService.getNurseTasks(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 上传看护备注信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/uploadRemark")
	public void uploadRemark(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		// para =
		// "{\"userId\":\"17017\",\"remark\":\"aaa\",\"plan_id\":521}";
		// test end
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":uploadRemark入参信息" + para);
		String result = outSiteInterfaceService.uploadRemark(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取巡线任务
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getLineTasks")
	public void getLineTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);

		// para
		// ="{\"userId\":\"18034\",\"remark\":\"xxxxx\",\"outsite_id\":2,\"sn\":2}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getLineTasks入参信息" + para);
		String result = axxInterfaceService.getLineTasks(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 从推送消息获取完整信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getBasicOuteSiteInfo")
	public void getBasicOuteSiteInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String para = BaseServletTool.getParam(request);
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getBasicOuteSiteInfo入参信息" + para);
		String result = outSiteInterfaceService.getBasicOuteSiteInfo(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 查看中继段下的采集段的信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getInfoOfRelayCollection")
	public void getInfoOfRelayCollection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// para = "{\"cable_id\":\"266\",\"relay_id\":\"185\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getInfoOfRelayCollection入参信息" + para);
		String result = collectInfoOfRelayService.getInfoOfRelayCollection(para);
		System.out.println(DateUtil.getDateAndTime() + ":getInfoOfRelayCollection入参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 查看中继段下的采集段的详细信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getDetailInfoOfRelayCollection")
	public void getDetailInfoOfRelayCollection(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"cable_id\":\"275\",\"relay_id\":\"276\",\"create_date\":\"2015-07-09\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getDetailInfoOfRelayCollection入参信息" + para);
		String result = collectInfoOfRelayService.getDetailInfoOfRelayCollection(para);
		System.out.println(DateUtil.getDateAndTime() + ":getDetailInfoOfRelayCollection出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 判断新采集的设施点周边有没有关联的路由点并返回
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/isReferencePoint")
	public void isReferencePoint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"relay_id\":\"339\",\"longitude\":\"118.748486\",\"cable_id\":\"291\",\"sn\":\"356524057214313\",\"user_id\":\"18669\",\"latitude\":\"31.986631\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":isReferencePoint入参信息" + para);
		String result = collectInfoOfRelayService.isReferencePoint(para);
		System.out.println(DateUtil.getDateAndTime() + ":isReferencePoint出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 非路由设施采集详细信息操作
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/collectInfoOfMinorRelay")
	public void collectInfoOfMinorRelay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"equip_id\":\"137\",\"longitude\":\"118.748411\",\"latitude\":\"31.996704\",\"equip_info\":{\"equip_code\":\"不锈钢6\",\"equip_address\":\"safsadf\",\"owner_name\":\"张三3\",\"owner_tel\":\"112233\",\"protecter\":\"李四2\",\"protect_tel\":\"18551859020\",\"equip_type\":\"4\"},\"description\":{\"txt1\":\"145\",\"txt2\":\"高标石\",\"txt3\":\"有标石套\",\"txt4\":\"普通标石\"}}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":collectInfoOfMinorRelay入参信息" + para);
		String result = collectInfoOfRelayService.collectInfoOfMinorRelay(para);
		System.out.println(DateUtil.getDateAndTime() + ":collectInfoOfMinorRelay出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 采集路由设施详细信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/collectInfoOfRelay")
	public void collectInfoOfRelay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"status\":{\"after\":\"11\",\"before\":\"12\",\"key\":\"0\"},\"equip_info\":{\"protecter\":\"阿双飞\",\"relay_id\":\"339\",\"equip_type\":1,\"protect_tel\":\"123\",\"cable_id\":\"291\",\"area_id\":\"3\",\"sn\":\"356524057214313\",\"owner_tel\":\"147414\",\"owner_name\":\"我\",\"create_date\":\"\",\"equip_code\":\"111\",\"userId\":\"18669\",\"equip_address\":\"准备\",\"longitude\":\"118.748486\",\"latitude\":\"31.986631\"},\"description\":{\"txt4\":\"普通标石\",\"txt1\":\"1.1\",\"txt2\":\"高标石\",\"txt3\":\"有标石套\"}}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":collectInfoOfRelay入参信息" + para);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			collectInfoOfRelayService.collectInfoOfRelay(para);
			map.put("result", "000");
			map.put("equip_id", collectInfoOfRelayService.getCurrentEquipId());
		} catch (Exception e) {
			map.put("result", "001");
			e.printStackTrace();
		} finally {
			String result = JSONObject.fromObject(map).toString();
			System.out.println(DateUtil.getDateAndTime() + ":collectInfoOfRelay出参信息" + result);
			BaseServletTool.sendParam(response, result);
		}
	}

	/**
	 * 距离手机用户两百米以内的最近5个设施点
	 * 
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping("/equipNearTwoHundredMeter")
	public void equipNearTwoHundredMeter(UIPage page, HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// String para=
		// "{\"longitude\":\"120.37479\",\"latitude\":\"31.951204\",\"user_id\":\"19316\",\"area_id\":\"15\",\"sn\":\"862002036321019\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":equipNearTwoHundredMeter入参信息" + para);
		String result = collectInfoOfRelayService.equipNearTwoHundredMeter(page, request, para);
		System.out.println(DateUtil.getDateAndTime() + ":equipNearTwoHundredMeter出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 找到距离用户30米内采集段落集合
	 * 
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping("/selCollectPartNearUser")
	public void selCollectPartNearUser(UIPage page, HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para= "{\"longitude\":\"118.748411\",\"latitude\":\"31.99657\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":selCollectPartNearUser入参信息" + para);
		String result = collectInfoOfRelayService.selCollectPartNearUser(request, para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);

	}

	/**
	 * 通过equip_id获取该设施点的信息
	 * 
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping("/selEquipInfo")
	public void selEquipInfo(HttpServletRequest request, HttpServletResponse response, UIPage page) {
		String para = BaseServletTool.getParam(request);
		// String para=
		// "{\"relay_id\":\"\",\"cable_id\":\"\",\"sn\":\"355905073944507\",\"user_id\":\"18669\",\"equip_id\":\"31883\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":selEquipInfo入参信息" + para);
		String result = collectInfoOfRelayService.selEquipInfo(page, request, para);
		System.out.println(DateUtil.getDateAndTime() + ":selEquipInfo出参信息" + result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);

	}

	/**
	 * 设施点修改功能
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updEquipInfo")
	public void updEquipInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String para = BaseServletTool.getParam(request);
		// String para=
		// "{\"equip_info\":{\"equip_id\":\"119\",\"cable_id\":\"211\",\"relay_id\":\"62\",\"equip_code\":\"不锈钢4\",\"equip_address\":\"safsadf\",\"owner_name\":\"张三3\",\"owner_tel\":\"112233\",\"protecter\":\"李四2\",\"protect_tel\":\"18551859020\",\"longitude\":\"110\",\"latitude\":\"123\",\"equip_type\":\"2\",\"update_date\":\"2015-07-09
		// 10:27:56\"}"
		// + ",\"description\":{\"txt1\":\"高标石\",\"txt2\":\"有标石套\",\"txt3\":\"普通标石\"}}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":updEquipInfo入参信息重要" + para);
		String result = collectInfoOfRelayService.updEquipInfo(para);
		System.out.println(DateUtil.getDateAndTime() + ":出参信息" + para);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 检查页面跳转到地图页面获取所有设施点
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/changeViewOfMap")
	public void changeViewOfMap(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"user_id\":\"110\",\"cable_id\":\"265\",\"relay_id\":\"176\",\"sn\":\"111\",\"area_id\":\"3\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":changeViewOfMap入参信息" + para);
		String result = collectInfoOfRelayService.changeViewOfMap(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 查询该巡线员同组人员的定位坐标
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPositionOfTeam")
	public void getPositionOfTeam(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para= "{\"user_id\":\"18029\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getPositionOfTeam入参信息" + para);
		String result = autoTrackService.getPositionOfTeam(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 查询同组所有人员
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/selPersonOfTeam")
	public void selPersonOfTeam(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para= "{\"user_id\":\"18029\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":selPersonOfTeam入参信息" + para);
		String result = autoTrackService.selPersonOfTeam(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 通过中心点获取前后至多两个采集点的集合
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/selEquipsByCenterId")
	public void selEquipsByCenterId(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"relay_id\":\"319\",\"area_id\":\"39\",\"cable_id\":\"282\",\"sn\":\"869340026272963\",\"user_id\":\"18497\",\"equip_id\":\"329972\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":selEquipsByCenterId入参信息" + para);
		String result = collectInfoOfRelayService.selEquipsByCenterId(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 线路切换
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/changeEquipsLine")
	public void changeEquipsLine(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"longitude\":\"118.748411\",\"latitude\":\"31.995671\",\"user_id\":\"110\",\"cable_id\":\"263\",\"relay_id\":\"164\",\"area_id\":\"3\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":changeEquipsLine入参信息" + para);
		String result = collectInfoOfRelayService.changeEquipsLine(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 设施点评操作
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/equipReviews")
	public void equipReviews(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"equip_id\":\"315375\",\"equip_type\":\"6\",\"user_id\":\"110\",\"area_id\":\"48\",\"status\":\"0\",\"check_field1\":\"1\",\"check_field2\":\"2\",\"check_field3\":\"3\",\"check_field4\":\"4\",\"check_field5\":\"5\",\"check_field6\":\"6\",\"check_field7\":\"7\",\"check_field8\":\"8\",\"other_trouble\":\"other\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":equipReviews入参信息" + para);
		String result = collectInfoOfRelayService.equipReviews(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 设施点评历史记录查看
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/recordOfEquipReviews")
	public void recordOfEquipReviews(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"equip_id\":\"152\",\"equip_type\":\"5\",\"user_id\":\"110\",\"area_id\":\"3\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":recordOfEquipReviews入参信息" + para);
		String result = collectInfoOfRelayService.recordOfEquipReviews(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 设施点评图片查看
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/picOfEquipReviews")
	public void picOfEquipReviews(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"equip_id\":\"152\",\"check_time\":\"5\",\"user_id\":\"110\",\"area_id\":\"3\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":picOfEquipReviews入参信息" + para);
		String result = collectInfoOfRelayService.picOfEquipReviews(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 缺失设施点插入操作
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/insertLostEquip")
	public void insertLostEquip(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"before\":\"131\",\"after\":\"132\",\"user_id\":\"110\",\"equip_type\":\"3\",\"cable_id\":\"263\",\"relay_id\":\"155\""
		// +
		// ",\"longitude\":\"118.748449\",\"latitude\":\"31.985768\",\"is_equip\":\"1\",\"equip_id\":\"\",\"create_date\":\"2015-09-11
		// 17:25:00\"}";
		printParam("入参信息:" + para);
		System.out.println(DateUtil.getDateAndTime() + ":insertLostEquip入参信息" + para);
		String result = collectInfoOfRelayService.insertLostEquip(para);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取地市区域信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getAreaInfos")
	public void getAreaInfos(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para= "{\"sn\":\"864895020417489\",\"user_id\":\"17037\"}";
		printParam("入参信息" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getAreaInfos入参信息" + para);
		String result = axxInterfaceService.getAreaInfos(para);
		printParam("出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 根据区域id查询底下的光缆和中继段
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCRByAreaId")
	public void getCRByAreaId(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para= "{\"sn\":\"864895020417489\",\"user_id\":\"17037\",\"area_id\":\"3\"}";
		printParam("入参信息" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getCRByAreaId入参信息" + para);
		String result = axxInterfaceService.getCRByAreaId(para);
		printParam("出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 根据相关信息查询最近1000米内的一个路由设施点
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getLatelyStepPoint")
	public void getLatelyStepPoint(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"sn\":\"864\",\"user_id\":\"17037\",\"area_id\":\"3\",\"longitude\":\"119.026\",\"latitude\":\"31.721\",\"cable_id\":\"338\",\"relay_id\":\"453\"}";
		printParam("入参信息" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getLatelyStepPoint入参信息" + para);
		String result = axxInterfaceService.getLatelyStepPoint(para);
		printParam("出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 获取区域下所有外力点与该外力点的第一张照片
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getOutSitesAndPhoto")
	public void getOutSitesAndPhotos(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"sn\":\"864\",\"user_id\":\"17037\",\"area_id\":\"3\",\"out_site_name\":\"幸福\",\"org_id\":\"2265\",\"page\":\"1\"}";
		printParam("入参信息" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getOutSitesAndPhoto入参信息" + para);
		String result = outSiteInterfaceService.getOutSitesAndPhoto(para);
		printParam("出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 根据外力点id获取底下所有的照片
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getOutSitePhotos")
	public void getOutSitePhotos(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para= "{\"sn\":\"864\",\"user_id\":\"17037\",\"out_site_id\":\"424\"}";
		printParam("入参信息" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getOutSitePhotos入参信息" + para);
		String result = outSiteInterfaceService.getOutSitePhotos(para);
		printParam("出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 远程照片点评以及派发整治单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/remotePhotoComment")
	public void remotePhotoComment(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"sn\":\"864\",\"user_id\":\"18044\",\"out_site_id\":\"424\",\"photo_id\":\"1237755\""
		// +",\"is_praise\":\"1\",\"questionType\":\"问题测试\",\"other_trouble\":\"没毛病\",\"sendRenovation\":\"1\"}";
		printParam("入参信息" + para);
		System.out.println(DateUtil.getDateAndTime() + ":remotePhotoComment入参信息" + para);
		String result = outSiteInterfaceService.remotePhotoComment(para);
		printParam("出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 历史照片点评查看
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getHistoryPhotoComment")
	public void getHistoryPhotoComment(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"sn\":\"864\",\"user_id\":\"18340\",\"out_site_id\":\"1123\",\"photo_id\":\"19760\"}";
		printParam("入参信息" + para);
		System.out.println(DateUtil.getDateAndTime() + ":getHistoryPhotoComment入参信息" + para);
		String result = outSiteInterfaceService.getHistoryPhotoComment(para);
		printParam("出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 整治单查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getRepairOrder")
	public void getRepairOrder(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para= "{\"sn\":\"864\",\"user_id\":\"18169\",\"area_id\":\"3\"}";
		System.out.println(DateUtil.getDateAndTime() + ":getRepairOrder入参信息" + para);
		String result = outSiteInterfaceService.getRepairOrder(para);
		printParam("出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 整治单回单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dealRepairOrder")
	public void dealRepairOrder(HttpServletRequest request, HttpServletResponse response) {
		String para = BaseServletTool.getParam(request);
		// para=
		// "{\"sn\":\"864\",\"user_id\":\"18169\",\"process_id\":\"45\",\"deal_result\":\"第二次测试处理\",\"fixorder_id\":\"21\"}";
		System.out.println(DateUtil.getDateAndTime() + ":dealRepairOrder入参信息" + para);
		String result = outSiteInterfaceService.dealRepairOrder(para);
		printParam("出参信息" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 自动上传高铁轨迹接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/autoUploadGtTrack")
	public void autoUploadGtTrack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr ="{\"upload_time\":\"2016-08-24
		// 09:01:34\",\"sn\":\"a000004260081e\",\"area_id\":\"3\",\"trackList\":[{\"gps_flag\":\"1\",\"is_gps\":\"1\",\"longitude\":\"118.826773\",\"latitude\":\"32.080153\",\"track_time\":\"2016-08-23
		// 15:51:08\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"161\",\"longitude\":\"118.826773\",\"latitude\":\"32.080153\",\"track_time\":\"2016-08-23
		// 15:53:10\",\"speed\":\"0.0\"},{\"gps_flag\":\"1\",\"is_gps\":\"0\",\"longitude\":\"118.826773\",\"latitude\":\"32.080153\",\"track_time\":\"2016-08-23
		// 15:55:15\",\"speed\":\"0.0\"}],\"userId\":\"18033\"}";
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":autoUploadGtTrack入参信息" + jsonStr);
		String result = lineSiteInterfaceService.saveAutoGtTrack(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	/**
	 * 手动坐标上传高铁轨迹接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveHandUploadGtTrack")
	public void saveHandUploadGtTrack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
		// jsonStr ="{\"upload_time\":\"2015-08-24
		// 09:01:35\",\"longitude\":\"118.918899\",\"area_id\":\"3\",\"sn\":\"a000004260081e\",\"latitude\":\"32.126867\",\"speed\":\"0.0\",\"userId\":\"18033\"}";

		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime() + ":saveHandUploadGtTrack入参信息" + jsonStr);
		String result = lineSiteInterfaceService.saveHandUploadGtTrack(jsonStr);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
	}

	private void printParam(String param) {
		if (PropertiesUtil.getPropertyBoolean("printSwitch", true)) {
			System.out.println(param);
		}
	}

}
