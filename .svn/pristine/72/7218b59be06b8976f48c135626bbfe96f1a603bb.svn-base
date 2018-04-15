package com.linePatrol.action;

import icom.axx.dao.AxxInterfaceDao;
import icom.system.dao.TaskInterfaceDao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linePatrol.service.DangerOrderService;
import com.linePatrol.service.FixOrderService;
import com.linePatrol.util.DateUtil;
import com.linePatrol.util.MapUtil;
import com.linePatrol.util.StaffUtil;

import util.page.BaseAction;

@SuppressWarnings("all")
@RequestMapping(value = "/FixOrder")
@Controller
public class FixOrderController extends BaseAction {
	
	@Resource
	private FixOrderService fixOrderService;
	
	@Resource
    private DangerOrderService dangerOrderService;
	
	@Resource
	private TaskInterfaceDao taskInterfaceDao;
	
	@Resource
	private AxxInterfaceDao axxInterfaceDao;
	
	 @RequestMapping(value = "/index.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
	    Map<String, Object> para=new HashMap<String, Object>();
		String localId = StaffUtil.getStaffAreaId(request);
		para.put("area_id", localId);
		List<Map<String, Object>> scopeList =  dangerOrderService.getScopeList(para);
		model.put("scopeList", scopeList);	
//		// 准备数据
//		List<Map<String, Object>> fixOrder = fixOrderService.selAllFixOrder();
//	
//		model.put("fixOrder", fixOrder);
	
		return new ModelAndView("linePatrol/xunxianManage/fixOrder/fixOrder_index", model);
    }
  
	@RequestMapping(value = "/query.do")
    public @ResponseBody List<Map<String,Object>> query(HttpServletRequest request, HttpServletResponse response,ModelMap model){
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
	
		para.put("areaId", StaffUtil.getStaffAreaId(request));

		List<Map<String, Object>> olists  = fixOrderService.query(para);
		return olists;
    }
	 
	 @RequestMapping(value = "/auditUI.do")
    public ModelAndView auditUI(HttpServletRequest request, HttpServletResponse response,String fixorder_id) {
		Map<String, Object> map = fixOrderService.findDetail(fixorder_id);
		return new ModelAndView("linePatrol/xunxianManage/fixOrder/fixOrder_auditUI", map);

    }
	 
    @RequestMapping(value = "/audit.do")
    public void audit(HttpServletRequest request, HttpServletResponse response) {

		Map map = new HashMap();
		map.put("status", true);
		Map<String, Object> para = MapUtil.parameterMapToCommonMap(request);
		String handle_person = StaffUtil.getStaffId(request);
		String fixorder_id = para.get("fixorder_id").toString();
		Map<String,Object> fixOrderMap = fixOrderService.selFixOrderByID(fixorder_id);//查询出整治单详细信息
		try {
			Map<String,Object> parammap = new HashMap<String, Object>();
			String node_id = fixOrderService.getSingelNodeListNextVal();	
			parammap.put("node_id", node_id);
			parammap.put("fixorder_id", fixorder_id);//整治单id
			parammap.put("handle_person",handle_person);
			parammap.put("status",3);//流程状态置为已审核
			parammap.put("repair_remark", para.get("repair_remark"));//审核意见
			if(Integer.parseInt(para.get("is_pass").toString()) == 1){
				parammap.put("fingings_of_audit", "1");//插入审核结果1通过
				fixOrderService.intoSingelNodeList(parammap);//插入结点
				
				//修改整治单状态
				parammap.put("file_time", DateUtil.getDateAndTime());
				parammap.put("is_file", 1);
				parammap.put("file_people", handle_person);
				fixOrderService.upFixOrderStatusFile(parammap);//修改整治单为已归档
				
				//生成新图片为外力点检查图片
				List<Map<String,Object>> photos = fixOrderService.selNoAuditPhotos(fixorder_id);//所有待审核图片
				for(Map<String,Object> photo : photos){
					Map<String,Object> photoMap = new HashMap<String, Object>();//生成图片
					int photoId = taskInterfaceDao.getPicId();// 获取photo主键
        			photoMap.put("pic_id", photoId);
        			photoMap.put("staff_id",fixOrderMap.get("DEALING_WITH_PEOPLE"));//照片的处理人,巡线人员
        			photoMap.put("site_id", fixOrderMap.get("SPOT_ID"));//生成关于这张外力点的照片
        			photoMap.put("upload_time", DateUtil.getDateAndTime());
        			photoMap.put("photo_type", 15);//外力点整治后图片
        			photoMap.put("pic_path",photo.get("PHOTO_PATH"));
					photoMap.put("micro_pic_path",photo.get("MICRO_PHOTO"));
                	axxInterfaceDao.insertPic(photoMap);//插入第一次流程发起时候的图片为当前图片下面还得再次插入一张待处理时候的图片
				}
				
				//将发起图片的状态修改为1
				fixOrderService.upPhotoStatu(fixOrderMap.get("PHOTO_ID").toString());
				
			}else if(Integer.parseInt(para.get("is_pass").toString()) == 0){
				parammap.put("fingings_of_audit", "0");//插入审核结果驳回
				fixOrderService.intoSingelNodeList(parammap);//插入驳回结点
				
				//插入驳回图片,为上面待审核照片，先查询 出上一个日期最近的待审核结点所对应的所有图片
				//生成新图片为外力点检查图片
				List<Map<String,Object>> photos = fixOrderService.selNoAuditPhotos(fixorder_id);//所有待审核图片
				for(Map<String,Object> photo : photos){
					Map<String,Object> photoMap = new HashMap<String, Object>();//生成图片
					int photoId = taskInterfaceDao.getPicId();// 获取photo主键
        			photoMap.put("pic_id", photoId);
        			photoMap.put("staff_id",fixOrderMap.get("DEALING_WITH_PEOPLE"));//照片的处理人,巡线人员
        			photoMap.put("site_id", node_id);//生成关于这张外力点的照片
        			photoMap.put("upload_time", DateUtil.getDateAndTime());
        			photoMap.put("photo_type", 14);//整治单类型
        			photoMap.put("pic_path",photo.get("PHOTO_PATH"));
					photoMap.put("micro_pic_path",photo.get("MICRO_PHOTO"));
                	axxInterfaceDao.insertPic(photoMap);//插入第一次流程发起时候的图片为当前图片下面还得再次插入一张待处理时候的图片
				}
				
				//生成一条待处理的结点，后面图片为当前待审核的图片
				Map<String,Object> parammap2 = new HashMap<String, Object>();
				String node_id2 = fixOrderService.getSingelNodeListNextVal();	
				parammap2.put("node_id", node_id2);
				parammap2.put("fixorder_id", fixorder_id);//整治单id
				parammap2.put("handle_person",handle_person);//处理人设为当前审核人员
				parammap2.put("status",1);//流程状态置为已派发
				parammap2.put("repair_remark", para.get("repair_remark"));//审核意见
				fixOrderService.intoSingelNodeList(parammap2);//插入待处理结点
				
				for(Map<String,Object> photo : photos){
					Map<String,Object> photoMap = new HashMap<String, Object>();//生成图片
					int photoId = taskInterfaceDao.getPicId();// 获取photo主键
        			photoMap.put("pic_id", photoId);
        			photoMap.put("staff_id",handle_person);//照片的处理人,巡线人员
        			photoMap.put("site_id", node_id2);//生成关于这张外力点的照片
        			photoMap.put("upload_time", DateUtil.getDateAndTime());
        			photoMap.put("photo_type", 14);//整治单类型
        			photoMap.put("pic_path",photo.get("PHOTO_PATH"));
					photoMap.put("micro_pic_path",photo.get("MICRO_PHOTO"));
                	axxInterfaceDao.insertPic(photoMap);//插入第一次流程发起时候的图片为当前图片下面还得再次插入一张待处理时候的图片
				}
				
				//将整治单状态改为待处理
				parammap.put("status",1);//流程状态置为已派发
				fixOrderService.upFixOrderStatus(parammap);
			}
		} catch (Exception e) {
		    e.printStackTrace();
		    map.put("status", false);
		}
		try {
		    response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
	
    @RequestMapping(value = "/detailUI.do")
    public ModelAndView detailUI(HttpServletRequest request, HttpServletResponse response,String fixorder_id) {

		Map<String, Object> map = new HashMap<String, Object>();//查询三个集合1结点集合2整改前图片集合3整改后图片集合
		List<Map<String,Object>> nodes = fixOrderService.selFixNodesByFixId(fixorder_id);//结点集合
	
		Map<String, Object> photoParam = new HashMap<String, Object>();
		photoParam.put("fixorder_id", fixorder_id);
		photoParam.put("photo_type", 14);// 整治单类型
		photoParam.put("status", 1);// 状态1已派发2已处理
		List<Map<String, Object>> photoList_zzq = fixOrderService.getPhotos(photoParam);//整治前
		
		photoParam.put("status", 2);// 状态1已派发2已处理
		List<Map<String, Object>> photoList_zzh = fixOrderService.getPhotos(photoParam);//整治后
		
		map.put("nodes", nodes);
		map.put("photoList_zzq", photoList_zzq);
		map.put("photoList_zzh", photoList_zzh);
		return new ModelAndView("linePatrol/xunxianManage/fixOrder/fixOrder_detailUI", map);

    }
}
