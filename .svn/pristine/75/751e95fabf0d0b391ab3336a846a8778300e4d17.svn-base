package icom.cableCheck.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cableInspection.model.PointModel;

public interface CheckTaskService {


	/**
	 * 获取设备列表
	 * 
	 * @param jsonStr
	 * @return
	 */
	
	
	
	public String getCheckEqp(String jsonStr);
	
	/**
	 * @description 获取区域人员
	 */
	public String selectStaff(String jsonStr);
	
	/**
	 * @description 查询当前位置的网格单元（集团对标）
	 */
	public String selectGrid(String jsonStr);
	
	/**
	 * @description 归属网络设施展示（集团对标）
	 */
	public String selectGridQum(String jsonStr);
	
	/**
	 * @description 获取网格设备所属工单（集团对标）
	 */
	public String getEquWorkOrder(String jsonStr);
	
	/**
	 * @description 点击工单检查按钮，显示工单端子信息（集团对标）
	 */
	public String getEquWorkOrderList(String jsonStr);
	

	/**
	 * @description 点击工单检查按钮，显示设备端子信息（集团对标）
	 */
	public String getEquWorkList(String jsonStr);
	
	/**
	 * @description 工单端子信息页面-正确端子-使用下拉框展示，端子编码让检查员自己选择	
	 */
	public String getPortsByAreaEqu(String jsonStr);
	
	/**
	 * @description 对工单端子信息检查完成后提交（集团对标）
	 */
	public String submitWorkOrderPort(String jsonStr);
	
	/**
	 * @description 查询工单详细信息（集团对标）
	 */
	public String selectWorkOrderList(String jsonStr);
	
	/**
	 * @description 作业计划实施-现场资源查询（集团对标）
	 */
	public String selectResourcesCheck(String jsonStr);
	
	/**
	 * @description 作业计划实施-工单查询（集团对标）
	 */
	public String getWPWorkOrder(String jsonStr);
	
	/**
	 * @description 作业计划实施-工单详细信息查询（集团对标）
	 */
	public String selectWPWorkOrderList(String jsonStr);
	
	/**
	 * @description 作业计划实施-修改信息校验（集团对标）
	 */
	public String updatecheck(String jsonStr);
	
	/**
	 * @description 作业计划实施-修改痕迹记录（集团对标）
	 */
	public String modifytraces(String jsonStr);
	
	/**
	 * @description 集约化工单
	 */
	public String intensificationworkoder(String jsonStr);
	
	/**
	 * @description 集约化工单详情
	 */
	public String workoderdetail(String jsonStr);
	
	/**
	 * 集约化工单打分的详细信息
	 * @param jsonStr
	 * @return
	 */
	public String workoderScoredetail(String jsonStr);
	
	/**
	 * @description 集约化工单检查结果接口
	 */
	public String workOderResult(String jsonStr);
	
	public String workOderResult_new(String jsonStr);
	
	/**
	 * @description 光网助手系统生成检查任务，主业人员检查
	 */
	public String TaskCheck(String jsonStr);
	
	
	/**
	 * @description 作业计划实施-错误数据修改业务单（集团对标）
	 */
	public String ErrorWorkOrder(String jsonStr);
	
	/**
	 * @description 已完成的检查任务，将工作量推送给集约化平台
	 */
	public String outSysDispatchTask(String jsonStr);
	
	/**
	 * @description 隐患整治工单接口
	 */
	public String dangerRemediationOder(String jsonStr);
	
	/**
	 * @description 隐患整治工单审核结果接口
	 */
	public String outSysAppOperating(String jsonStr);
	
	/**
	 * @description 错误信息记录(集团对标)
	 */
	public String saveError(String jsonStr);
	/**
	 * @description 网格工单查询排序(集团对标)
	 */
	public String getOrderNumByGrid(String jsonStr);
	
	/**
	 * @description 作业计划回单(集团对标)
	 */
	public String workPlanBackOder(String jsonStr);
	
	/**
	 * @description 作业计划实施-错误数据修改业务单(集团对标)
	 */
	public String errorDataOder(String jsonStr);
	
	/**
	 * @description 作业计划实施-错误信息修改(集团对标)
	 */
	public String errorDataModify(String jsonStr);
	
	/**
	 * 获取网格设备下的工单
	 * @param jsonStr
	 * @return
	 */
	public String getEquCSVIOMOrder(String jsonStr);
	
	/**
	 * 获取设备的端子信息
	 * @param jsonStr
	 * @return
	 */
	public String getPortsByEqu(String jsonStr);
	
	/**
	 * 根据人员id和网格id获取任务设备
	 * @param jsonStr
	 * @return
	 */
	public String getEqpBySG(String jsonStr);
	
	/**
	 * 点击工单检查时间，显示这条工单的所有流程记录
	 */
	public String getProcessRecord(String jsonStr);
	
	/**
	 * 获取设备详情
	 * @param jsonStr
	 * @return
	 */
	public String getEqpDetail(String jsonStr);
	
}
