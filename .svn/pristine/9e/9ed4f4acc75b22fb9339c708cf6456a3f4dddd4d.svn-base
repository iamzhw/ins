package icom.axx.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.event.ChangeListener;

import icom.axx.dao.CollectInfoOfRelayDao;
import icom.axx.dao.StepCheckDao;
import icom.axx.service.CollectInfoOfRelayService;
import icom.axx.service.StepCheckService;
import icom.util.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.cxf.ws.addressing.MAPAggregator;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import util.page.Query;
import util.page.UIPage;
import util.password.MD5;

import com.linePatrol.dao.DangerOrderDao;
import com.linePatrol.dao.StepPartDao;
import com.linePatrol.dao.StepPartTaskDao;
import com.linePatrol.util.DateUtil;
import com.linePatrol.util.StaffUtil;
import com.system.dao.StaffDao;
import com.system.sys.OnlineUserListener;
import com.util.MapDistance;


@Transactional
@Service
@SuppressWarnings("all")
public class StepCheckServiceImpl implements StepCheckService {

	@Autowired
	private CollectInfoOfRelayDao collectInfoOfRelayDao;
	
	@Autowired
	private StaffDao staffDao;
	
	@Autowired
	private StepCheckDao stepCheckDao;
	
	@Autowired
	private DangerOrderDao dangerOrderDao;

	@Resource
	private StepPartTaskDao stepPartTaskDao;
	
	@Resource
	private StepPartDao StepPartDao;
	
	@Override
	public String getInfoOfRelayCollection(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("userId");
		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
//			return Result.returnCode("002");
//		}
		Object cable_id = jsonObject.get("cable_id");
		Object relay_id = jsonObject.get("relay_id");

		// String userId = jsonObject.getString("userId");
		// String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
		// return Result.returnCode("002");
		// }

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cable_id", cable_id);
		map.put("relay_id", relay_id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("collect_info",
				collectInfoOfRelayDao.getInfoOfRelayCollection(map));
		resultMap.put("result", "000");
		return JSONObject.fromObject(resultMap).toString();

	}

	@Override
	public String getDetailInfoOfRelayCollection(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("userId");
//		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
//			return Result.returnCode("002");
//		}
		Object cable_id = jsonObject.get("cable_id");
		Object relay_id = jsonObject.get("relay_id");
		Object create_date = jsonObject.get("create_date");

		// String userId = jsonObject.getString("userId");
		// String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
		// return Result.returnCode("002");
		// }

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cable_id", cable_id);
		map.put("relay_id", relay_id);
		map.put("create_date", create_date);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("collect_detail",
				collectInfoOfRelayDao.getDetailInfoOfRelayCollection(map));
		resultMap.put("result", "000");
		return JSONObject.fromObject(resultMap).toString();
	}

	@Override
	public void collectInfoOfRelay(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		Object equip_info = jsonObject.get("equip_info");
		JSONObject equip_info_json = JSONObject.fromObject(equip_info);
		Object status = jsonObject.get("status");
		JSONObject status_json = JSONObject.fromObject(status);
		Object description_info = jsonObject.get("description");
		JSONObject description_info_json = JSONObject
				.fromObject(description_info);
		StringBuffer sb = new StringBuffer("");
		String description = "";
		if (description_info_json.size() > 0) {
			for (int i = 0; i < description_info_json.size(); i++) {
				sb.append(description_info_json.get("txt" + (i + 1)) + ",");
			}
		}
		String str = sb.toString();
		description = str.substring(0, str.lastIndexOf(","));
		Object creator = equip_info_json.get("userId");
		Object updator = equip_info_json.get("userId");
		Object equip_code = equip_info_json.get("equip_code");
		Object equip_address = equip_info_json.get("equip_address");
		Object longitude = equip_info_json.get("longitude");
		Object latitude = equip_info_json.get("latitude");
		Object equip_type = equip_info_json.get("equip_type");
		Object owner_name = equip_info_json.get("owner_name");
		Object owner_tel = equip_info_json.get("owner_tel");
		Object protecter = equip_info_json.get("protecter");
		Object protect_tel = equip_info_json.get("protect_tel");
		String create_date = equip_info_json.getString("create_date");
		String area_id = equip_info_json.getString("area_id");
		if ("0".equals(status_json.getString("key"))) {
			create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
		}
		String update_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		Object cable_id = equip_info_json.get("cable_id");
		Object relay_id = equip_info_json.get("relay_id");

		String key = status_json.getString("key");
		String before = status_json.getString("before");
		String after = status_json.getString("after");

		Map<String, Object> equip = new HashMap<String, Object>();
		equip.put("creator", creator);
		equip.put("updator", updator);
		equip.put("equip_code", equip_code);
		equip.put("equip_address", equip_address);
		equip.put("description", description);
		equip.put("longitude", longitude);
		equip.put("latitude", latitude);
		equip.put("equip_type", equip_type);
		equip.put("owner_name", owner_name);
		equip.put("owner_tel", owner_tel);
		equip.put("protecter", protecter);
		equip.put("protect_tel", protect_tel);
		equip.put("create_date", create_date);
		equip.put("update_date", update_date);
		equip.put("area_id", area_id);
		equip.put("is_equip", 1);
		equip.put("is_lose", 0);

		Map<String, Object> addStatus = new HashMap<String, Object>();
		addStatus.put("cable_id", cable_id);
		addStatus.put("relay_id", relay_id);
		addStatus.put("key", key);
		addStatus.put("before", before);
		addStatus.put("after", after);

		collectInfoOfRelayDao.addTourEquip(equip); //插入该路由设施
		if ("0".equals(key)) { // 在最后追加采集
			collectInfoOfRelayDao.addLineEquipByEnd(addStatus);
		} else if ("1".equals(key)) { // 段前采集
			addStatus.put("point", addStatus.get("before"));
			String order = collectInfoOfRelayDao.selOrder(addStatus);
			addStatus.put("order", order);
			collectInfoOfRelayDao.updLineEquip(addStatus);
			collectInfoOfRelayDao.addLineEquipByBefore(addStatus);
		} else if ("2".equals(key)) {// 段中采集
			addStatus.put("point", addStatus.get("after"));
			String order = collectInfoOfRelayDao.selOrder(addStatus);
			addStatus.put("order", order);
			collectInfoOfRelayDao.updLineEquip(addStatus);
			collectInfoOfRelayDao.addLineEquipByBefore(addStatus);
		} else if ("3".equals(key)) {// 段后采集
			Map<String, Object> endMap = new HashMap<String, Object>();
			endMap.put("cable_id", cable_id);
			endMap.put("relay_id", relay_id);
			endMap.put("start_euqip", after);
			String end_equip = collectInfoOfRelayDao.selEndEquipId(endMap);
			if (end_equip != null && !"".equals(end_equip)) {
				addStatus.put("point", end_equip);
				String order = collectInfoOfRelayDao.selOrder(addStatus);
				addStatus.put("order", order);
				collectInfoOfRelayDao.updLineEquip(addStatus);
				collectInfoOfRelayDao.addLineEquipByBefore(addStatus);
			} else {
				collectInfoOfRelayDao.addLineEquipByEnd(addStatus);
			}
		}

		Map<String, Object> delMap = new HashMap<String, Object>();
		delMap.put("cable_id", cable_id);
		delMap.put("relay_id", relay_id);
//		delMap.put("create_date", create_date.split(" ")[0]);
		Map<String, Object> line = new HashMap<String, Object>();
		List<Map<String, Object>> equipList = collectInfoOfRelayDao
				.selEquipList(delMap);
		collectInfoOfRelayDao.delStepTour(delMap);
		Map<String, Object> point = equipList.get(0);
		for (int i = 1; i < equipList.size(); i++) {
			Object cableId = equipList.get(i).get("CABLE_ID");
			Object relayId = equipList.get(i).get("RELAY_ID");
			Object end_equip_id = equipList.get(i).get("EQUIP_ID");
			Object start_equip_id = point.get("EQUIP_ID");
			double lat1 = Double.valueOf(point.get("LATITUDE").toString());
			double lng1 = Double.valueOf(point.get("LONGITUDE").toString());
			double lat2 = Double.valueOf(equipList.get(i).get("LATITUDE")
					.toString());
			double lng2 = Double.valueOf(equipList.get(i).get("LONGITUDE")
					.toString());
			Object distance = (int) MapDistance.getDistance(lat1, lng1, lat2,
					lng2);
			Object order = i;
			Object createDate = equipList.get(i).get("CREATE_DATE");
			Object updateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
			line.put("cable_id", cableId);
			line.put("relay_id", relayId);
			line.put("start_equip", start_equip_id);
			line.put("end_equip", end_equip_id);
			line.put("distance", distance);
			line.put("order", order);
			line.put("create_date", createDate);
			line.put("update_date", updateDate);
			collectInfoOfRelayDao.addStepTour(line);
			point = equipList.get(i);
		}
	}

	@Override
	public String getCurrentEquipId() {
		return collectInfoOfRelayDao.getCurrentEquipId();
	}

	@Override
	public List<Map<String, Object>> selEquipListForWEB(Query query) {
		return stepCheckDao.selEquipListForWEB(query);
	}

	@SuppressWarnings("finally")
	@Override
	public String equipNearTwoHundredMeter(UIPage page,
			HttpServletRequest request, String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("user_id");
		String area_id=jsonObject.getString("area_id");
		// String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
		// return Result.returnCode("002");
		// }
		Map<String, Object> paramap=new HashMap<String, Object>();
		paramap.put("area_id", area_id);
		String latitude = jsonObject.getString("latitude");// 获取用户的经纬度
		String longitude = jsonObject.getString("longitude");
		List<Map<String, Object>> allEquipsByTask = selAllStepEquipByUserId(paramap);// 按地区查询所有路由设施点
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> equipsNearUser = new ArrayList<Map<String,Object>>(); //200米以内所有路由点集合
		List<Map<String, Object>> otherList=new ArrayList<Map<String,Object>>();//200米以内其它点所在路由设施段的信息
		Map<String, Object> nearPart=new HashMap<String, Object>();
		try {
			for (Map<String, Object> mp : allEquipsByTask) { //筛选出离用户200米以内所有的路由点
				double distance = MapDistance.getDistance(
						 Double.valueOf(latitude), Double.valueOf(longitude),
						 Double.valueOf(mp.get("LATITUDE").toString()),
						 Double.valueOf(mp.get("LONGITUDE").toString()));
				if(distance<=200){
					equipsNearUser.add(mp);
				}
			}
			if(equipsNearUser.size()>0){
				Map<String, Object> minEquip = sort(equipsNearUser, latitude, longitude);//排序得到离用户最近的路由点
				List<Map<String, Object>> equipPartNearUser=selAllStepEquipByUserId(minEquip);//得到距离用户最近的点所在的路由设施段落
				List<Map<String, Object>> delList1=new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> delList2=new ArrayList<Map<String,Object>>();
				for (Map<String, Object> map : equipsNearUser) { //和minEquip在同一个电缆中继段上的路由点 加入dellist集合待删除
					if(map.get("CABLE_ID").toString().equals(minEquip.get("CABLE_ID").toString()) &&
							map.get("RELAY_ID").toString().equals(minEquip.get("RELAY_ID").toString()))
					{
						delList1.add(map);
					}
				}
				equipsNearUser.removeAll(delList1);//删除和minEquip在同一个电缆中继段上的路由点
				for (int i = 0; i < equipsNearUser.size(); i++) {//去同
					for (int j = i+1; j < equipsNearUser.size(); j++) {
						if(equipsNearUser.get(j).get("CABLE_ID").toString().equals(equipsNearUser.get(i).get("CABLE_ID").toString()) &&
								equipsNearUser.get(j).get("RELAY_ID").toString().equals(equipsNearUser.get(i).get("RELAY_ID").toString()))
						{
							delList2.add(equipsNearUser.get(j));
						}
					}
				} 
				equipsNearUser.removeAll(delList2);//不在同一电缆中继段上的用户附近的路由点
				otherList=equipsNearUser;
				if (equipPartNearUser.size() > 0) {// 获取离用户200米以内的至多五个点信息  关联非路由点
					List<Map<String, Object>> equipsInfo = getEquipInfoNearUser(equipPartNearUser, minEquip); // 获取用户周边至多5个设施点
					Iterator<Map<String, Object>> it=equipsInfo.iterator();
					while (it.hasNext()) {
						Map<String, Object> map=it.next();
						List<Map<String, Object>> minorList = collectInfoOfRelayDao.selMinorEquips(map);
						map.put("minorList", minorList);
					}
					nearPart.put("cable_id", equipPartNearUser.get(0).get("CABLE_ID"));
					nearPart.put("cable_name", equipPartNearUser.get(0).get("CABLE_NAME"));
					nearPart.put("relay_id", equipPartNearUser.get(0).get("RELAY_ID"));
					nearPart.put("relay_name", equipPartNearUser.get(0).get("RELAY_NAME"));
					nearPart.put("equipList", equipsInfo);
				}
			}
			resultMap.put("list", nearPart);
			resultMap.put("otherList", otherList);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	// 以一个点（有可能是离用户最近的点）为中心找到前后至多各两个点
	private List<Map<String, Object>> getEquipInfoNearUser(
			List<Map<String, Object>> equipLists, Map<String, Object> minEquip) {
		List<Map<String, Object>> equipsInfo = new ArrayList<Map<String, Object>>();
		int length = equipLists.size();
		int index = -1;
		for (int i = 0; i < equipLists.size(); i++) {
			if (equipLists.get(i).get("EQUIP_ID").toString()
					.equals(minEquip.get("EQUIP_ID").toString())) {
				index = i;
				break;
			}
		}
		if (index - 2 >= 0)
			equipsInfo.add(equipLists.get(index - 2));
		if (index - 1 >= 0) {
			equipsInfo.add(equipLists.get(index - 1));
		}
		equipsInfo.add(minEquip);
		if (index + 1 < length)
			equipsInfo.add(equipLists.get(index + 1));
		if (index + 2 < length)
			equipsInfo.add(equipLists.get(index + 2));
		return equipsInfo;
	}

	/**
	 * 找到距离最近的点，在list中标示出该点，用于在移动端高亮显示
	 * 
	 * @param lists
	 */
	public Map<String, Object> sort(List<Map<String, Object>> lists,
			String latitude, String longitude) {
		Map<String, Object> min = lists.get(0);
		for (int i = 1; i < lists.size(); i++) {
			Double distance1 = MapDistance.getDistance(
					Double.valueOf(latitude), Double.valueOf(longitude),
					Double.valueOf(lists.get(i).get("LATITUDE").toString()),
					Double.valueOf(lists.get(i).get("LONGITUDE").toString()));
			Double distance2 = MapDistance.getDistance(
					Double.valueOf(latitude), Double.valueOf(longitude),
					Double.valueOf(min.get("LATITUDE").toString()),
					Double.valueOf(min.get("LONGITUDE").toString()));
			if (distance1 < distance2) {
				min = lists.get(i);
			}
		}
		min.put("min", true);
		return min;
	}

	@Override
	public String selCollectPartNearUser(HttpServletRequest request, String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("user_id");
		// String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
		// return Result.returnCode("002");
		// }
		Map<String, Object> map = new HashMap<String, Object>();
		Object cable_id = jsonObject.get("cable_id");
		Object relay_id = jsonObject.get("relay_id");
		String latitude = jsonObject.getString("latitude");
		String longitude = jsonObject.getString("longitude");
		map.put("cable_id", cable_id);
		map.put("relay_id", relay_id);
		map.put("is_equip", 1); //路由设施标识
		List<Map<String, Object>> lists = collectInfoOfRelayDao.selEquipList(map); //该中继段上所有已经采集的路有点
		List<Map<String, Object>> listsNearUser = new ArrayList<Map<String, Object>>();
		double distance = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (lists != null) {
			for (int i = 0; i < lists.size(); i++) {
				distance = MapDistance
						.getDistance(
								Double.valueOf(latitude),
								Double.valueOf(longitude),
								Double.valueOf(lists.get(i).get("LATITUDE")
										.toString()),
								Double.valueOf(lists.get(i).get("LONGITUDE")
										.toString()));
				if (distance <= 30) { // 距离手机端用户30米以内的所有设施点
					lists.get(i).put("distance", distance);
					lists.get(i).put("cable_id", cable_id);
					lists.get(i).put("relay_id", relay_id);
					listsNearUser.add(lists.get(i));
				}
				;
			}
			if (listsNearUser.size() > 0) {
				List<Map<String, Object>> resultList = selListOfCollectPartNearUser(listsNearUser);
				resultMap.put("collectPartNearUser", resultList);
				resultMap.put("result", "000");
			} else {// 未找到30米以内的设施点
				resultMap.put("collectPartNearUser", listsNearUser);
				resultMap.put("result", "001");
			}
		}
		return JSONObject.fromObject(resultMap).toString();
	}

	/**
	 * 查询距离手机用户最近的30米内的设施点段落
	 */
	@Override
	public List<Map<String, Object>> selListOfCollectPartNearUser(
			List<Map<String, Object>> list) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> paramap : list) {
			Map<String, Object> resultmap = collectInfoOfRelayDao
					.selCollectPartNearUser(paramap);
			if (resultmap != null) {
				resultList.add(resultmap);
			}
		}
		return resultList;
	}

	public String selEquipInfo(UIPage page, HttpServletRequest request,
			String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("user_id");
		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
//			return Result.returnCode("002");
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Object cable_id = jsonObject.get("cable_id");
			Object relay_id = jsonObject.get("relay_id");
			Object equip_id = jsonObject.get("equip_id");
			map.put("cable_id", cable_id);
			map.put("relay_id", relay_id);
			map.put("equip_id", equip_id);
			map.put("photo_type", 8);
			List<Map<String, Object>> imgList = collectInfoOfRelayDao.selEquipImgs(map);
			Query query = new Query();
			query.setQueryParams(map);
			query.setPager(page);
			resultMap.put("equip_info", selEquipListForWEB(query));
			resultMap.put("imgList",imgList);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
			e.printStackTrace();
		}
		return JSONObject.fromObject(resultMap).toString();
	}


	@SuppressWarnings("finally")
	@Override
	public String changeViewOfMap(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(para);
			String user_id = jsonObject.getString("user_id");
			String cable_id = jsonObject.getString("cable_id");
			String relay_id = jsonObject.getString("relay_id");
			Map<String, Object> paramap=new HashMap<String, Object>();
			paramap.put("cable_id", cable_id);
			paramap.put("relay_id", relay_id);
			// String sn = jsonObject.getString("sn");
			// if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			// return Result.returnCode("002");
			// }
			List<Map<String, Object>> list = selAllStepEquipByUserId(paramap); // 查询该用户所有任务对应的巡线信息
			resultMap.put("equipList", list);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	/**
	 * 查询设施点集合
	 */
	@Override
	public List<Map<String, Object>> selAllStepEquipByUserId(Map<String, Object> paramap) {
		List<Map<String, Object>> lists = collectInfoOfRelayDao
				.selEquipPartByTaskId(paramap); // 更具条件查询路由设施点
		return lists;
	}

	@SuppressWarnings("finally")
	@Override
	public String selEquipsByCenterId(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();;
		try {
			String user_id = JSONObject.fromObject(para).getString("user_id");
			String cable_id = JSONObject.fromObject(para).getString("cable_id");
			String relay_id = JSONObject.fromObject(para).getString("relay_id");
			String equip_id = JSONObject.fromObject(para).getString("equip_id");
			String area_id = JSONObject.fromObject(para).getString("area_id");
			Map<String, Object> paramap=new HashMap<String, Object>();
			Map<String, Object> centermap=new HashMap<String, Object>();
			centermap.put("equip_id", equip_id);
			Map<String, Object> centerEquip = collectInfoOfRelayDao.selEquipPartByTaskId(centermap).get(0);
			centerEquip.put("min", true);
			paramap.put("area_id", area_id);
			paramap.put("cable_id", cable_id);
			paramap.put("relay_id", relay_id);
			List<Map<String, Object>> list = selAllStepEquipByUserId(paramap); //查询 该电缆中继段上所有的路由点
			List<Map<String, Object>> lists = this.getEquipInfoNearUser(list,centerEquip); // 该中心点以内周边至多5个路有点
			Iterator<Map<String, Object>> it=lists.iterator();
			while (it.hasNext()) {
				Map<String, Object> map=it.next();
				List<Map<String, Object>> minorList = collectInfoOfRelayDao.selMinorEquips(map);
				map.put("minorList", minorList);
			}
			resultMap.put("equipList", lists);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		}finally{
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String changeEquipsLine(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("user_id");
		String cable_id = jsonObject.getString("cable_id");
		String relay_id = jsonObject.getString("relay_id");
		String area_id = jsonObject.getString("area_id");
		// String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
		// return Result.returnCode("002");
		// }
		Map<String, Object> paramap=new HashMap<String, Object>();
		paramap.put("area_id", area_id);
		String latitude = jsonObject.getString("latitude");// 获取用户的经纬度
		String longitude = jsonObject.getString("longitude");
		List<Map<String, Object>> allEquipsByTask = selAllStepEquipByUserId(paramap);// 按地区查询所有路由设施点
		paramap.put("cable_id", cable_id);
		paramap.put("relay_id", relay_id);
		List<Map<String, Object>> allEquips = selAllStepEquipByUserId(paramap);// 按area_id,cable_id,relay_id查询所有路由设施点
		Map<String, Object> minEquip = sort(allEquips, latitude, longitude);//在指定的光缆中继段上排序得到离用户最近的路由点
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> equipsNearUser = new ArrayList<Map<String,Object>>(); //200米以内所有路由点集合
		List<Map<String, Object>> otherList=new ArrayList<Map<String,Object>>();//200米以内其它点所在路由设施段的信息
		Map<String, Object> nearPart=new HashMap<String, Object>();
		try {
			if (allEquips.size() > 0) {// 至多五个点信息  关联非路由点
				List<Map<String, Object>> equipsInfo = getEquipInfoNearUser(allEquips, minEquip); // 获取用户周边至多5个设施点
				Iterator<Map<String, Object>> it=equipsInfo.iterator();
				while (it.hasNext()) {
					Map<String, Object> map=it.next();
					List<Map<String, Object>> minorList = collectInfoOfRelayDao.selMinorEquips(map);
					map.put("minorList", minorList);
				}
				nearPart.put("cable_id", allEquips.get(0).get("CABLE_ID"));
				nearPart.put("cable_name", allEquips.get(0).get("CABLE_NAME"));
				nearPart.put("relay_id", allEquips.get(0).get("RELAY_ID"));
				nearPart.put("relay_name", allEquips.get(0).get("RELAY_NAME"));
				nearPart.put("equipList", equipsInfo);
			}
			
			
			for (Map<String, Object> mp : allEquipsByTask) { //筛选出离用户200米以内所有的路由点
				double distance = MapDistance.getDistance(
						 Double.valueOf(latitude), Double.valueOf(longitude),
						 Double.valueOf(mp.get("LATITUDE").toString()),
						 Double.valueOf(mp.get("LONGITUDE").toString()));
				if(distance<=200){
					equipsNearUser.add(mp);
				}
			}
			if(equipsNearUser.size()>0){
				List<Map<String, Object>> delList1=new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> delList2=new ArrayList<Map<String,Object>>();
				for (Map<String, Object> map : equipsNearUser) { //和minEquip在同一个电缆中继段上的路由点 加入dellist集合待删除
					if(map.get("CABLE_ID").toString().equals(cable_id) &&
							map.get("RELAY_ID").toString().equals(relay_id))
					{
						delList1.add(map);
					}
				}
				equipsNearUser.removeAll(delList1);//删除和minEquip在同一个电缆中继段上的路由点
				for (int i = 0; i < equipsNearUser.size(); i++) {//去同
					for (int j = i+1; j < equipsNearUser.size(); j++) {
						if(equipsNearUser.get(j).get("CABLE_ID").toString().equals(equipsNearUser.get(i).get("CABLE_ID").toString()) &&
								equipsNearUser.get(j).get("RELAY_ID").toString().equals(equipsNearUser.get(i).get("RELAY_ID").toString()))
						{
							delList2.add(equipsNearUser.get(j));
						}
					}
				} 
				equipsNearUser.removeAll(delList2);//不在同一电缆中继段上的用户附近的路由点
				otherList=equipsNearUser;
				
			}
			resultMap.put("list", nearPart);
			resultMap.put("otherList", otherList);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	@Override
	public String isReferencePoint(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("user_id");
		// String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
		// return Result.returnCode("002");
		// }
		String latitude = jsonObject.getString("latitude");// 获取设施点的经纬度
		String longitude = jsonObject.getString("longitude");
		String cable_id = jsonObject.getString("cable_id");
		String relay_id = jsonObject.getString("relay_id");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("cable_id", cable_id);
		paraMap.put("relay_id", relay_id);
		List<Map<String, Object>> allMainEquip = collectInfoOfRelayDao
				.selAllMainEquip(paraMap);// 得到该光缆中继段上已经采集的所有路由设施点
		Map<String, Object> minEquip = new HashMap<String, Object>();
		for (Map<String, Object> map : allMainEquip) {
			int distance = (int) MapDistance.getDistance(
					Double.valueOf(latitude), Double.valueOf(longitude),
					Double.valueOf(map.get("LATITUDE").toString()),
					Double.valueOf(map.get("LONGITUDE").toString()));
			if (distance <= 5) {
				minEquip = map;
				break;
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("minEquip", minEquip);
		resultMap.put("result", "000");
		return JSONObject.fromObject(resultMap).toString();
	}

	/**
	 * 更具光缆和中继段获取所有路由设施点集合信息
	 */
	@Override
	public List<Map<String, Object>> selAllMainEquip(Map<String, Object> map) {
		return collectInfoOfRelayDao.selAllMainEquip(map);
	}

	@SuppressWarnings("finally")
	@Override
	public String collectInfoOfMinorRelay(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(para);
			String is_lose=jsonObject.getString("is_lose");
			String longitude = jsonObject.getString("longitude");
			String latitude = jsonObject.getString("latitude");
			String equip_id = jsonObject.getString("equip_id");
			Object equip_info = jsonObject.get("equip_info");
			JSONObject equip_info_json = JSONObject.fromObject(equip_info);
			Object description_info = jsonObject.get("description");
			JSONObject description_info_json = JSONObject
					.fromObject(description_info);
			StringBuffer sb = new StringBuffer("");
			String description = "";
			if (description_info_json.size() > 0) {
				for (int i = 0; i < description_info_json.size(); i++) {
					sb.append(description_info_json.get("txt" + (i + 1)) + ",");
				}
			}
			String str = sb.toString();
			description = str.substring(0, str.lastIndexOf(","));
			Object creator = equip_info_json.get("userId");
			Object updator = equip_info_json.get("userId");
			Object equip_code = equip_info_json.get("equip_code");
			Object equip_address = equip_info_json.get("equip_address");
			Object equip_type = equip_info_json.get("equip_type");
			Object owner_name = equip_info_json.get("owner_name");
			Object owner_tel = equip_info_json.get("owner_tel");
			Object protecter = equip_info_json.get("protecter");
			Object protect_tel = equip_info_json.get("protect_tel");
			String area_id = equip_info_json.getString("area_id"); 
			Map<String, Object> equip = new HashMap<String, Object>();
			equip.put("equip_id", equip_id);
			equip.put("creator", creator);
			equip.put("updator", updator);
			equip.put("equip_code", equip_code);
			equip.put("equip_address", equip_address);
			equip.put("description", description);
			equip.put("longitude", longitude);
			equip.put("latitude", latitude);
			equip.put("equip_type", equip_type);
			equip.put("owner_name", owner_name);
			equip.put("owner_tel", owner_tel);
			equip.put("protecter", protecter);
			equip.put("protect_tel", protect_tel);
			equip.put("area_id", area_id);
			equip.put("is_lose", is_lose);
			equip.put("create_date",
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date()));
			equip.put("update_date",
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date()));
			collectInfoOfRelayDao.collectInfoOfMinorRelay(equip);
			String lost_equip_id = collectInfoOfRelayDao.getCurrentEquipId();
			
			if(is_lose.equals("1")){ //如果为缺失点，在隐患流程建立隐患信息
				String userId = equip_info_json.getString("userId");
				String highRisk = jsonObject.getString("high_risk");

				// 用户信息
				Map<String, Object> userInfo = staffDao.findPersonalInfo(userId);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("danger_type", 2);
				map.put("equip_id", lost_equip_id);
				map.put("equip_type", equip_type);
				
				map.put("latitude", latitude);
				map.put("longitude", longitude);
				map.put("created_by", userId);
				map.put("founder_uid", userId);
				map.put("high_risk", highRisk);
				map.put("found_time", DateUtil.getDateAndTime());
				map.put("parent_city", userInfo.get("AREA_ID"));

				// 保存隐患点
				String creation_time = DateUtil.getDateAndTime();
				map.put("creation_time", creation_time);

				String danger_id = dangerOrderDao.getDangerId();
				map.put("danger_id", danger_id);
				dangerOrderDao.saveDanger(map);

				// 保存隐患单
				dangerOrderDao.dangerOrderSave(map);
				
			}
			resultMap.put("result", "000");
			resultMap.put("equip_id", lost_equip_id);// 获取当前该非路由点的设施ID
		} catch (Exception e) {
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String equipReviews(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject jb = JSONObject.fromObject(para);
		String user_id = jb.getString("user_id");
//		String sn = jb.getString("sn");
		Map<String, Object> paramap=new HashMap<String, Object>();
//		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
//			return Result.returnCode("002");
//		}
		try {
			String equip_id = jb.getString("equip_id");
			String equip_type = jb.getString("equip_type");
			String status = jb.getString("status");
			if(jb.containsKey("check_field1")){
				String check_field1=jb.getString("check_field1");
				paramap.put("check_field1", check_field1);
			}
			if(jb.containsKey("check_field2")){
				String check_field2=jb.getString("check_field2");
				paramap.put("check_field2", check_field2);
			}
			if(jb.containsKey("check_field3")){
				String check_field3=jb.getString("check_field3");
				paramap.put("check_field3", check_field3);
			}
			if(jb.containsKey("check_field4")){
				String check_field4=jb.getString("check_field4");
				paramap.put("check_field4", check_field4);
			}
			if(jb.containsKey("check_field5")){
				String check_field5=jb.getString("check_field5");
				paramap.put("check_field5", check_field5);
			}
			if(jb.containsKey("check_field6")){
				String check_field6=jb.getString("check_field6");
				paramap.put("check_field6", check_field6);
			}
			if(jb.containsKey("check_field7")){
				String check_field7=jb.getString("check_field7");
				paramap.put("check_field7", check_field7);
			}
			if(jb.containsKey("check_field8")){
				String check_field8=jb.getString("check_field8");
				paramap.put("check_field8", check_field8);
			}
			if(jb.containsKey("other_trouble")){
				String other_trouble = jb.getString("other_trouble");
				paramap.put("other_trouble", other_trouble);
			}
			String check_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			paramap.put("equip_id", equip_id);
			paramap.put("equip_type", equip_type);
			paramap.put("check_staff_id", user_id);
			paramap.put("check_time", check_time);
			paramap.put("status", status);
			collectInfoOfRelayDao.saveEquipReviewsInfo(paramap); //新增该设施的检查记录
			Map<String, Object> pmap = collectInfoOfRelayDao.selParamForTaskId(paramap);  //该task_id 的对应的基本数据
			pmap.put("check_time", check_time.split(" ")[0]);
			List<Map<String, Object>> stepStatus = collectInfoOfRelayDao.selStepStatus(pmap); //查询该设施点是否已经巡检的状态
			if(stepStatus.size()>0){
				for(int i= 0;i<stepStatus.size();i++){
					if(Double.valueOf(stepStatus.get(i).get("STATUS").toString())==0){ //未巡检 完成巡检操作   已经巡检过了 不做修改
	//					pmap.put("check_time", check_time);
						collectInfoOfRelayDao.updStepStatus(pmap);//修改设施巡检状态为已完成
					}
				}	
			}
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
			e.printStackTrace();
		}finally{
			return JSONObject.fromObject(resultMap).toString();
		}
			
	}

	@SuppressWarnings("finally")
	@Override
	public String recordOfEquipReviews(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject jb = JSONObject.fromObject(para);
		String equip_id = jb.getString("equip_id");
		String user_id = jb.getString("user_id");
		Map<String, Object> paramap=new HashMap<String, Object>();
//		String sn = jb.getString("sn");
//		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
//			return Result.returnCode("002");
//		}
		try {
			paramap.put("equip_id", equip_id);
			List<Map<String, Object>> recordList=collectInfoOfRelayDao.recordOfEquipReviews(paramap);
			resultMap.put("recordList", recordList);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		}finally{
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String picOfEquipReviews(String para) {
		JSONObject jb = JSONObject.fromObject(para);
		String equip_id = jb.getString("equip_id");
		String user_id = jb.getString("user_id");
		String check_time = jb.getString("check_time");
//		String sn = jb.getString("sn");
//		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
//			return Result.returnCode("002");
//		}
		Map<String, Object> paramap=new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramap.put("equip_id", equip_id);
			paramap.put("query_time", check_time);
			paramap.put("photo_type", 9);
			List<Map<String, Object>> map = collectInfoOfRelayDao.selEquipImgs(paramap);
			resultMap.put("imgList", map);
			resultMap.put("result", "000");
		} catch (Exception e) {
			resultMap.put("result", "001");
		}
		finally{
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public String insertLostEquip(String para) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(para);
			Object creator = jsonObject.get("user_id");
			Object updator = jsonObject.get("user_id");
			Object longitude = jsonObject.get("longitude");
			Object latitude = jsonObject.get("latitude");
			Object equip_type = jsonObject.get("equip_type");
			String create_date = jsonObject.getString("create_date");
			String update_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			

			Object cable_id = jsonObject.get("cable_id");
			Object relay_id = jsonObject.get("relay_id");
			String area_id = jsonObject.getString("area_id");
			String before = jsonObject.getString("before");
			String after = jsonObject.getString("after");
			String is_equip = jsonObject.getString("is_equip");
			String equip_id = jsonObject.getString("equip_id");
			Map<String, Object> equip = new HashMap<String, Object>();
			equip.put("creator", creator);
			equip.put("updator", updator);
			equip.put("longitude", longitude);
			equip.put("latitude", latitude);
			equip.put("equip_type", equip_type);
			equip.put("create_date", create_date);
			equip.put("update_date", update_date);
			equip.put("is_equip", is_equip);  //判断是路由设施还是非路由
			equip.put("is_lose", 1);        //该插入点为缺失设施点
			equip.put("relation_equip", equip_id);
			equip.put("area_id", area_id);
			Map<String, Object> addStatus = new HashMap<String, Object>();
			addStatus.put("cable_id", cable_id);
			addStatus.put("relay_id", relay_id);
			addStatus.put("before", before);
			addStatus.put("after", after);

			collectInfoOfRelayDao.addTourEquip(equip); //缺失设施点添加
			
			
			
			addStatus.put("point", addStatus.get("after"));
			String order = collectInfoOfRelayDao.selOrder(addStatus);
			addStatus.put("order", order);
			collectInfoOfRelayDao.updLineEquip(addStatus);
			collectInfoOfRelayDao.addLineEquipByBefore(addStatus);

			Map<String, Object> delMap = new HashMap<String, Object>();
			delMap.put("cable_id", cable_id);
			delMap.put("relay_id", relay_id);
//			delMap.put("create_date", create_date.split(" ")[0]);
			Map<String, Object> line = new HashMap<String, Object>();
			List<Map<String, Object>> equipList = collectInfoOfRelayDao
					.selEquipList(delMap);
			collectInfoOfRelayDao.delStepTour(delMap);
			Map<String, Object> point = equipList.get(0);
			for (int i = 1; i < equipList.size(); i++) {
				Object cableId = equipList.get(i).get("CABLE_ID");
				Object relayId = equipList.get(i).get("RELAY_ID");
				Object end_equip_id = equipList.get(i).get("EQUIP_ID");
				Object start_equip_id = point.get("EQUIP_ID");
				double lat1 = Double.valueOf(point.get("LATITUDE").toString());
				double lng1 = Double.valueOf(point.get("LONGITUDE").toString());
				double lat2 = Double.valueOf(equipList.get(i).get("LATITUDE")
						.toString());
				double lng2 = Double.valueOf(equipList.get(i).get("LONGITUDE")
						.toString());
				Object distance = (int) MapDistance.getDistance(lat1, lng1, lat2,
						lng2);
				Object order1 = i;
				Object createDate = equipList.get(i).get("CREATE_DATE");
				Object updateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				line.put("cable_id", cableId);
				line.put("relay_id", relayId);
				line.put("start_equip", start_equip_id);
				line.put("end_equip", end_equip_id);
				line.put("distance", distance);
				line.put("order", order1);
				line.put("create_date", createDate);
				line.put("update_date", updateDate);
				collectInfoOfRelayDao.addStepTour(line);
				point = equipList.get(i);
			}
			
			equip_id = collectInfoOfRelayDao.getCurrentEquipId(); //该设施点id
			
			String userId = jsonObject.getString("user_id");
//			String sn = jsonObject.getString("sn");
//			if (!OnlineUserListener.isLogin(userId, sn)) {//登录校验
//				return Result.returnCode("002");
//			}
			
			String highRisk = jsonObject.getString("high_risk");
			

			// 用户信息
			Map<String, Object> userInfo = staffDao.findPersonalInfo(userId);

			Map<String, Object> map = new HashMap<String, Object>();
			if(jsonObject.containsKey("danger_type")){
				String danger_type=jsonObject.getString("danger_type");
				map.put("danger_type", danger_type);
			}
			map.put("equip_id", equip_id);
			map.put("equip_type", equip_type);
			
			map.put("latitude", latitude);
			map.put("longitude", longitude);
			map.put("created_by", userId);
			map.put("founder_uid", userId);
			map.put("high_risk", highRisk);
			map.put("found_time", DateUtil.getDateAndTime());
			map.put("parent_city", userInfo.get("AREA_ID"));

			// 保存隐患点
			String creation_time = DateUtil.getDateAndTime();
			map.put("creation_time", creation_time);

			String danger_id = dangerOrderDao.getDangerId();
			map.put("danger_id", danger_id);
			map.put("danger_type", 2);
			dangerOrderDao.saveDanger(map);

			// 保存隐患单
			dangerOrderDao.dangerOrderSave(map);
			
			resultMap.put("euqip_id", equip_id);
			resultMap.put("result", "000");
		} catch (NumberFormatException e) {
			resultMap.put("result", "001");
			e.printStackTrace();
		} finally{
			return JSONObject.fromObject(resultMap).toString();
		}
		
	}

	@Override
	public String showByMap(Map<String, Object> paramap) {
		paramap.put("is_equip", 1);
		List<Map<String, Object>> list = collectInfoOfRelayDao.selEquipList(paramap);
		String jstr=JSONArray.fromObject(list).toString();
		return jstr;
	}

	@Override
	public List<Map<String, Object>> is_equip(Map<String, Object> paramap) {
		return stepCheckDao.is_equip(paramap);
	}

	@Override
	public List<Map<String, Object>> no_equip(Map<String, Object> paramap) {
		return stepCheckDao.no_equip(paramap);
	}

	@Override
	public void saveEquipByChange(String id,String cable_id,String relay_id,String area_id,String page,
			String pageSize,HttpServletRequest request) {
		Map<String, Object> mp=new HashMap<String, Object>();
		mp.put("relay_id", relay_id);
		mp.put("area_id", area_id);
		mp.put("cable_id", cable_id);
		mp.put("page", page);
		mp.put("pageSize", pageSize);
		List<Map<String, Object>> equipList = this.is_equip(mp); //该区域中继段下未删除的路由设施点 共200个
		List<Map<String, Object>> equipListModel = new ArrayList<Map<String,Object>>(); //该区域中继段下未删除的路由设施点 共200个
		for (Map<String, Object> map : equipList) {
			equipListModel.add(map);
		}
		List<Map<String, Object>> noEquipList = this.no_equip(mp); //该区域中继段下删除的路由设施点 
		mp.put("page", null);
		List<Map<String, Object>> allEquips = this.is_equip(mp);  //该地区所有未删除的路由设施点
		equipList.addAll(noEquipList); //包含删除和未删除（200个）的所有设施点
		List<Map<String, Object>> modelList=new ArrayList<Map<String,Object>>();
		String[] ids=id.split(",");
		for (int i = 0; i < ids.length; i++) { // 过滤后得到即将修改删除状态的设施点
			for (Map<String, Object> map : equipList) {
				if(ids[i].equals(map.get("EQUIP_ID").toString())){
					equipList.remove(map); //待删除设施点集合
					modelList.add(map);	//待插入设施排序，步巡路由设施表内的设施点集合
					break;
				}
				
			}
		}
		for (Map<String, Object> equipMap : equipList) {
			stepCheckDao.updEqiupStuOfDel(Integer.valueOf(equipMap.get("EQUIP_ID").toString())); //修改设施点删除状态
		}
		
		stepCheckDao.delLineEquip(mp); //删除该设施对应光缆中继段上所有设施排序关系
		stepCheckDao.delStepTourByEquipId(mp); //删除路由段
		
		for (int i = 0; i < modelList.size(); i++) {
			stepCheckDao.updEqiupStuOfAdd(Integer.valueOf(modelList.get(i).get("EQUIP_ID").toString()));
		}
		List<Map<String, Object>> ml = this.changeApart(allEquips, equipListModel, modelList); //替换一段落路由设施点 返回该区域该中继段所有路由 设施点
		
		for(int i = 0; i < ml.size(); i++){
			ml.get(i).put("ORDER", i+1);
			stepCheckDao.changeLineEquip(ml.get(i)); //插入设施排序
		}
		
		Map<String, Object> firstMap=ml.get(0);
		Map<String, Object> paramap=new HashMap<String, Object>();
		for (int i = 1; i < ml.size(); i++) {
			paramap.put("cable_id", ml.get(i).get("CABLE_ID"));
			paramap.put("relay_id", ml.get(i).get("RELAY_ID"));
			paramap.put("start_equip", firstMap.get("EQUIP_ID"));
			paramap.put("end_equip", ml.get(i).get("EQUIP_ID"));
			int distance = (int) MapDistance.getDistance(
					Double.valueOf(firstMap.get("LATITUDE").toString()),
					Double.valueOf(firstMap.get("LONGITUDE").toString()),
					Double.valueOf(ml.get(i).get("LATITUDE").toString()),
					Double.valueOf(ml.get(i).get("LONGITUDE").toString()));
			paramap.put("distance", distance);
			paramap.put("order", i);
			paramap.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			paramap.put("creator", StaffUtil.getStaffId(request));
			paramap.put("update_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			paramap.put("updator", StaffUtil.getStaffId(request));
			stepCheckDao.changeStepTour(paramap);
			firstMap=ml.get(i);
		}
	}
	
	
	public List<Map<String, Object>> changeApart(List<Map<String, Object>> allEquips,List<Map<String, Object>> equipList,List<Map<String, Object>> modelEquips){
		int start=0;
		int delSize=equipList.size();
		for(int i=0 ;i<allEquips.size();i++){
			System.out.println(Integer.valueOf(equipList.get(0).get("EQUIP_ID").toString()));
			System.out.println(Integer.valueOf(allEquips.get(i).get("EQUIP_ID").toString()));
			System.out.println("-------------------------------");
			if(Integer.parseInt(equipList.get(0).get("EQUIP_ID").toString())==Integer.parseInt(allEquips.get(i).get("EQUIP_ID").toString())){
				start=i;
				break;
			}
		}
		for (int j=0; j <delSize; j++) {
			allEquips.remove(start);
		}
		allEquips.addAll(start, modelEquips);
		
		return allEquips;
	}
	
	

	@Override
	public List<List<Map<String, Object>>> changeRelationEquipInit(HttpServletRequest request,
			Map<String, Object> paramap) {
		List<Map<String, Object>> equipList = stepCheckDao.is_equip(paramap);
		List<List<Map<String, Object>>> allEquips = new ArrayList<List<Map<String,Object>>>();
		Map<String, Object> pm=new HashMap<String, Object>();
		for (Map<String, Object> equip : equipList) {
			pm.put("equip_id", equip.get("EQUIP_ID"));
			pm.put("cable_id", equip.get("CABLE_ID"));
			pm.put("relay_id", equip.get("RELAY_ID"));
			pm.put("area_id", paramap.get("area_id"));
			List<Map<String, Object>> eq=stepCheckDao.selRelationEquips(pm);
			allEquips.add(eq);    //路由非路由设施集合
		}
		return allEquips;
	}

	@Override
	public void changeRelationEquip(HttpServletRequest request,String para) {
		JSONArray json = JSONArray.fromObject(request.getParameter("equiplist"));
		String page = request.getParameter("page");
		String pageSize = "200";
		JSONArray js = null;
		Map<String, Object> paramap=new HashMap<String, Object>();
		//初始化该地区中继段上所有设施点都为路由设施点
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cable_id", JSONObject.fromObject(JSONArray.fromObject(json.get(0)).get(0)).getInt("CABLE_ID"));
		map.put("relay_id", JSONObject.fromObject(JSONArray.fromObject(json.get(0)).get(0)).getInt("RELAY_ID"));
		map.put("area_id", JSONObject.fromObject(JSONArray.fromObject(json.get(0)).get(0)).getInt("AREA_ID"));
		map.put("page", page);
		map.put("pageSize", pageSize);
		List<Map<String, Object>> beforeEquips = this.is_equip(map); //原先集合
		map.put("page", null);
		List<Map<String, Object>> modelList = this.is_equip(map); //中继段上的所有集合
		List<Map<String, Object>> equipListModel =new ArrayList<Map<String,Object>>();
		//		stepCheckDao.initEquipStatus(map);  //该区域该中继段上所有设施点设置为路由设施点
		for(int i=0;i<json.size();i++){
			 js = JSONArray.fromObject(json.get(i));
			 Map<String, Object> m = stepCheckDao.selByEquipId(Integer.parseInt(JSONObject.fromObject(js.get(0)).get("EQUIP_ID").toString()));
			 m.put("CABLE_ID", map.get("cable_id"));
			 m.put("RELAY_ID", map.get("relay_id"));
			 equipListModel.add(m);  //页面传回equip_id属性查询出全部的路由信息放入集合
			 int relation_id = JSONObject.fromObject(js.get(0)).getInt("EQUIP_ID");
			 stepCheckDao.updIsEquip(relation_id);// 设施点设置为路由设施点
			 paramap.put("relation_id", relation_id); //路由设施id
			 for(int j=1;j<js.size();j++){
				 int equip_id = JSONObject.fromObject(js.get(j)).getInt("EQUIP_ID");
				 paramap.put("equip_id", equip_id);  //非路由设施id
				 //设置非路由信息
				 stepCheckDao.updRelationStatus(paramap);  //设置非路由和路由的关联关系
				 stepPartTaskDao.intoRelationTaskEquip(equip_id);//先将该任务点数据备份
				 stepPartTaskDao.delRelationTaskEquip(equip_id);//删除该点在任务点表中的数据
			 }
		}
		
		
		stepCheckDao.delLineEquip(map); //删除该设施对应光缆中继段上所有设施排序关系
		stepCheckDao.delStepTourByEquipId(map); //删除路由段
		
		modelList = this.changeApart(modelList, beforeEquips, equipListModel);
		
		for (int i = 1; i <= modelList.size(); i++) {
			modelList.get(i-1).put("ORDER", i);
			stepCheckDao.changeLineEquip(modelList.get(i-1)); //插入设施排序
		}
		Map<String, Object> firstMap=modelList.get(0);
		Map<String, Object> pm=new HashMap<String, Object>();
		for (int i = 1; i < modelList.size(); i++) {
			pm.put("cable_id", modelList.get(i).get("CABLE_ID"));
			pm.put("relay_id", modelList.get(i).get("RELAY_ID"));
			pm.put("start_equip", firstMap.get("EQUIP_ID"));
			pm.put("end_equip", modelList.get(i).get("EQUIP_ID"));
			int distance = (int) MapDistance.getDistance(
					Double.valueOf(firstMap.get("LATITUDE").toString()),
					Double.valueOf(firstMap.get("LONGITUDE").toString()),
					Double.valueOf(modelList.get(i).get("LATITUDE").toString()),
					Double.valueOf(modelList.get(i).get("LONGITUDE").toString()));
			pm.put("distance", distance);
			pm.put("order", i);
			pm.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			pm.put("creator", StaffUtil.getStaffId(request));
			pm.put("update_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			pm.put("updator", StaffUtil.getStaffId(request));
			stepCheckDao.changeStepTour(pm);
			firstMap=modelList.get(i);
		}
		
	}
	/**
	 * 根据光缆、中继段获取步巡设施点
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, String>> getStepEquip(Map<String, Object> map){
		
		List<Map<String, String>> result = stepCheckDao.getStepEquip(map);
		return result;
	}
	/**
	 * TODO 删除步巡设施点
	 */
	@Override
	public String deleteStepEquip(String ids){
		
		String msg = "001";//操作成功
		
		Map param = new HashMap();
		param.put("ids", ids);
		//是否是步巡段起始、终止点
//		List<Map> map = stepCheckDao.ifStartOrEndEquip(param);
		//判断是否在步巡段中
		List<Map> map = stepCheckDao.ifEquipInPart(param);
		if(map.size()>0 && map != null){
			msg = "002";//已分配步巡段
		}else{
			try{
				//删除光缆路由设施关系表
				stepCheckDao.deleteLineEquip(param);
				//删除步巡设施表
				stepCheckDao.deleteStepEquip(param);
			}catch(Exception e){
				msg = "003";//操作失败
				e.printStackTrace();
			}
			
		}
		
		return msg;
	}
	
	@Override
	public Map<String, Object> stepEquipPart(Map<String, Object> map) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("cable_id", map.get("cable_id"));
		resultMap.put("relay_id", map.get("relay_id"));
		Map<String, Object> m = StepPartDao.selNameByCRID(resultMap);
		resultMap.put("cable_name", m.get("CABLE_NAME"));
		resultMap.put("relay_name", m.get("RELAY_NAME"));
		resultMap.put("is_equip_list", JSONArray.fromObject(stepCheckDao.is_equip(map)).toString());
		resultMap.put("no_equip_list", JSONArray.fromObject(stepCheckDao.no_equip(map)).toString());
		resultMap.put("page",map.get("page"));
		resultMap.put("area_id", map.get("area_id"));
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		resultMap.put("pageSize", pageSize);
		resultMap.put("count", stepCheckDao.selEquipCount(resultMap)%pageSize==0?
					stepCheckDao.selEquipCount(resultMap)/pageSize:stepCheckDao.selEquipCount(resultMap)/pageSize+1);
		return resultMap;
	}

	@Override
	public int selEquipCount(Map<String, Object> map) {
		return stepCheckDao.selEquipCount(map);
	}

	@Override
	public Map<String, Object> stepAllEquipPart(Map<String, Object> map) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("cable_id", map.get("cable_id"));
		resultMap.put("relay_id", map.get("relay_id"));
		Map<String, Object> m = StepPartDao.selNameByCRID(resultMap);
		resultMap.put("cable_name", m.get("CABLE_NAME"));
		resultMap.put("relay_name", m.get("RELAY_NAME"));
		resultMap.put("is_equip_list", JSONArray.fromObject(stepCheckDao.is_equip(map)).toString());
		resultMap.put("no_equip_list", JSONArray.fromObject(stepCheckDao.no_equip(map)).toString());
		resultMap.put("area_id", map.get("area_id"));
		return resultMap;
	}

	@Override
	public void saveAllEquipByChange(String id, String cable_id,
			String relay_id, String area_id, HttpServletRequest request) {
		Map<String, Object> mp=new HashMap<String, Object>();
		mp.put("relay_id", relay_id);
		mp.put("area_id", area_id);
		mp.put("cable_id", cable_id);
		List<Map<String, Object>> equipList = this.is_equip(mp); //该区域中继段下未删除的路由设施点 共200个
		List<Map<String, Object>> equipListModel = new ArrayList<Map<String,Object>>(); //该区域中继段下未删除的路由设施点 共200个
		for (Map<String, Object> map : equipList) {
			equipListModel.add(map);
		}
		List<Map<String, Object>> noEquipList = this.no_equip(mp); //该区域中继段下删除的路由设施点 
		equipList.addAll(noEquipList); //包含删除和未删除（200个）的所有设施点
		List<Map<String, Object>> ml=new ArrayList<Map<String,Object>>();
		String[] ids=id.split(",");
		for (int i = 0; i < ids.length; i++) { // 过滤后得到即将修改删除状态的设施点
			for (Map<String, Object> map : equipList) {
				if(ids[i].equals(map.get("EQUIP_ID").toString())){
					equipList.remove(map); //待删除设施点集合
					ml.add(map);	//待插入设施排序，步巡路由设施表内的设施点集合
					break;
				}
				
			}
		}
		for (Map<String, Object> equipMap : equipList) {
			stepCheckDao.updEqiupStuOfDel(Integer.valueOf(equipMap.get("EQUIP_ID").toString())); //修改设施点删除状态
		}
		
		stepCheckDao.delLineEquip(mp); //删除该设施对应光缆中继段上所有设施排序关系
		stepCheckDao.delStepTourByEquipId(mp); //删除路由段
		
		for (int i = 0; i < ml.size(); i++) {
			stepCheckDao.updEqiupStuOfAdd(Integer.valueOf(ml.get(i).get("EQUIP_ID").toString()));
		}

		for(int i = 0; i < ml.size(); i++){
			ml.get(i).put("ORDER", i+1);
			stepCheckDao.changeLineEquip(ml.get(i)); //插入设施排序
		}
		
		Map<String, Object> firstMap=ml.get(0);
		Map<String, Object> paramap=new HashMap<String, Object>();
		for (int i = 1; i < ml.size(); i++) {
			paramap.put("cable_id", ml.get(i).get("CABLE_ID"));
			paramap.put("relay_id", ml.get(i).get("RELAY_ID"));
			paramap.put("start_equip", firstMap.get("EQUIP_ID"));
			paramap.put("end_equip", ml.get(i).get("EQUIP_ID"));
			int distance = (int) MapDistance.getDistance(
					Double.valueOf(firstMap.get("LATITUDE").toString()),
					Double.valueOf(firstMap.get("LONGITUDE").toString()),
					Double.valueOf(ml.get(i).get("LATITUDE").toString()),
					Double.valueOf(ml.get(i).get("LONGITUDE").toString()));
			paramap.put("distance", distance);
			paramap.put("order", i);
			paramap.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			paramap.put("creator", StaffUtil.getStaffId(request));
			paramap.put("update_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			paramap.put("updator", StaffUtil.getStaffId(request));
			stepCheckDao.changeStepTour(paramap);
			firstMap=ml.get(i);
		}
		
	}
	
	
	/**
	 * 展示双击点修改界面
	 */
	@Override
	public Map<String, Object> edit(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		//根据设施点id查询相应基础数据信息,然后去查询点评与照片。点评如有就是已点评。按时间顺序倒叙排列展示
		String equip_id=request.getParameter("equip_id").toString();
		Map equip = stepCheckDao.getEquip(equip_id);
		List<Map<String, String>> checkList = stepCheckDao.getCheckList(equip_id);
		if(checkList.size()>0){
			rs.put("is_check", 1);
			Map check=checkList.get(0);
			//查询该检查记录的id，凭借这条id去后台查询8条check字段拼接起来的字符串
			String check_id= check.get("CHECK_ID").toString();
			String keydescription=stepCheckDao.selTroubleByCheckId(check_id);
			rs.put("keydescription", keydescription);
			rs.put("check", check);
		}else
		{
			rs.put("is_check", 0);
		}
		
		List<Map<String, String>> photoList = stepCheckDao.getPhotoList(equip_id);
		int photosize=0;
		if(photoList.size()>0){
			photosize=photoList.size();
			Map photo=photoList.get(0);
			rs.put("photo", photo);
		}
		rs.put("photosize", photosize);
		rs.put("equip", equip);
		rs.put("photoList", photoList);
		return rs;
	}

	
	/**
     * <p>
     * Title: update
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param request
     * @throws Exception
     */
    @SuppressWarnings("all")
	@Override
    public void update(HttpServletRequest request) throws Exception {
	    String equip_id = request.getParameter("equip_id");	
		String equip_code = request.getParameter("equip_code");
		String depth = request.getParameter("depth");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String equip_address = request.getParameter("equip_address");
		String description = request.getParameter("description");
		String owner_name = request.getParameter("owner_name");
		String owner_tel = request.getParameter("owner_tel");
		String protecter = request.getParameter("protecter");
		String protect_tel = request.getParameter("protect_tel");
		Map map = new HashMap();
		map.put("equip_id", equip_id);
		map.put("equip_code", equip_code);
		map.put("depth", depth);
		map.put("longitude", longitude);
		map.put("latitude", latitude);
		map.put("equip_address", equip_address);
		map.put("description", description);
		map.put("owner_name", owner_name);
		map.put("owner_tel", owner_tel);
		map.put("protecter", protecter);
		map.put("protect_tel", protect_tel);
		stepCheckDao.update(map);
    }
	
	@Override
	public Map<String, Object> selNoRelationPoint(String areaId) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("equip_list", JSONArray.fromObject(stepCheckDao.selNoRelationPoint(areaId)).toString());
		return resultMap;
	}

	@Override
	public Map<String, Object> selNoRouthEquip(Map<String, Object> map) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("cable_id", map.get("cable_id"));
		resultMap.put("relay_id", map.get("relay_id"));
		Map<String, Object> m = StepPartDao.selNameByCRID(resultMap);
		resultMap.put("cable_name", m.get("CABLE_NAME"));
		resultMap.put("relay_name", m.get("RELAY_NAME"));
		resultMap.put("no_equip_list", JSONArray.fromObject(stepCheckDao.noRouthEquip(map)).toString());
		resultMap.put("area_id", map.get("area_id"));
		return resultMap;
	
	}
    
}
