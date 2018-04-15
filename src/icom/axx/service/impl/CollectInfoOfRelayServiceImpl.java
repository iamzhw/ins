package icom.axx.service.impl;

import icom.axx.dao.CollectInfoOfRelayDao;
import icom.axx.dao.OutSiteInterfaceDao;
import icom.axx.service.CollectInfoOfRelayService;
import icom.util.Result;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.linePatrol.dao.DangerOrderDao;
import com.linePatrol.util.DateUtil;
import com.system.dao.StaffDao;
import com.system.sys.OnlineUserListener;
import com.util.MapDistance;


@Transactional
@Service
@SuppressWarnings("all")
public class CollectInfoOfRelayServiceImpl implements CollectInfoOfRelayService {

	@Autowired
	private CollectInfoOfRelayDao collectInfoOfRelayDao;
	
	@Autowired
	private StaffDao staffDao;
	
	@Autowired
	private DangerOrderDao dangerOrderDao;
	
	@Resource
	private OutSiteInterfaceDao outSiteInterfaceDao;
	
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
		resultMap.put("collect_info", collectInfoOfRelayDao.getInfoOfRelayCollection(map));
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
		Object longitudegps = equip_info_json.get("longitudegps");
		Object latitudegps = equip_info_json.get("latitudegps");
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
		equip.put("longitudegps", longitudegps);
		equip.put("latitudegps", latitudegps);
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
		//孙敏 2016/3/29号增加，因表中增加线路段id两个字段所以增加进去
		equip.put("cable_id", cable_id);
		equip.put("relay_id", relay_id);

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
		return collectInfoOfRelayDao.selEquipListForWEB(query);
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
		//孙敏 第一次不传中继段id时做个判断
		if(jsonObject.has("relay_id"))//查看这个jsonobject对象中是否有这个参数
	    {
		  String relay_id = jsonObject.getString("relay_id");
	      paramap.put("relay_id", relay_id);
	    } 
		String latitude = jsonObject.getString("latitude");// 获取用户的经纬度
		String longitude = jsonObject.getString("longitude");
		//用这个BigDecimal这个类来做计算，double型会丢失精度
		BigDecimal lonB=BigDecimal.valueOf(Double.valueOf(longitude));
	    BigDecimal latB=BigDecimal.valueOf(Double.valueOf(latitude));
	    BigDecimal maxlonB=lonB.add(BigDecimal.valueOf(0.005));
	    BigDecimal maxlatB=latB.add(BigDecimal.valueOf(0.005));
	    BigDecimal minlonB=lonB.subtract(BigDecimal.valueOf(0.005));
	    BigDecimal minlatB=latB.subtract(BigDecimal.valueOf(0.005));
	    paramap.put("maxlonB", maxlonB.toString());
	    paramap.put("maxlatB", maxlatB.toString());
	    paramap.put("minlonB", minlonB.toString());
	    paramap.put("minlatB", minlatB.toString());
	    
//		List<Map<String, Object>> allEquipsByTask = selAllStepEquipByUserId(paramap);// 按地区以及中继段查询所有路由设施点
	    List<Map<String, Object>> allEquipsByTask = selXYStepEquipByUserId(paramap);// 修改之后的按1000平方米查询所有路由设施点
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
				if(distance<=1000){
					equipsNearUser.add(mp);
				}
			}
			if(equipsNearUser.size()>0){
				Map<String, Object> minEquip = sort(equipsNearUser, latitude, longitude);//排序得到离用户最近的路由点
				minEquip.put("area_id", area_id);
				List<Map<String, Object>> equipPartNearUser=selXYStepEquipByUserId(minEquip);//得到距离用户最近的点所在的路由设施段落
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
				otherList=equipsNearUser;  //手机端更具中继段查询最近5个点，则不需要查询其他中继段信息
				if (equipPartNearUser.size() > 0) {// 获取离用户200米以内的至多五个点信息  关联非路由点
					List<Map<String, Object>> equipsInfo = getEquipInfoNearUser(equipPartNearUser, minEquip); // 获取用户周边至多5个设施点
					Iterator<Map<String, Object>> it=equipsInfo.iterator();
					while (it.hasNext()) {
						Map<String, Object> map=it.next();
						//获取关联采集的标石，宣传牌等设施
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
			resultMap.put("otherList", otherList);  //otherList   手机端更具中继段查询最近5个点，则不需要查询其他中继段信息
			resultMap.put("result", "000");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "001");
		} finally {
			return JSONObject.fromObject(resultMap).toString();
		}
	}

	// 以一个点（有可能是离用户最近的点）为中心找到前后至多各两个点
	private List<Map<String, Object>> getEquipInfoNearUser(List<Map<String, Object>> equipLists, Map<String, Object> minEquip) {
		List<Map<String, Object>> equipsInfo = new ArrayList<Map<String, Object>>();
		int length = equipLists.size();
		int index = -1;
		for (int i = 0; i < equipLists.size(); i++) {
			if (equipLists.get(i).get("EQUIP_ID").toString().equals(minEquip.get("EQUIP_ID").toString())) {
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
		// String sn = jsonObject.getString("sn");
		// if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
		// return Result.returnCode("002");
		// }
		JSONObject jsonObject = JSONObject.fromObject(para);
		String user_id = jsonObject.getString("user_id");
		Object cable_id = jsonObject.get("cable_id");
		Object relay_id = jsonObject.get("relay_id");
		String latitude = jsonObject.getString("latitude");
		String longitude = jsonObject.getString("longitude");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cable_id", cable_id);
		map.put("relay_id", relay_id);
		map.put("is_equip", 1); //路由设施标识
		
		List<Map<String, Object>> lists = collectInfoOfRelayDao.selEquipList(map); //该中继段上所有已经采集的路有点
		List<Map<String, Object>> listsNearUser = new ArrayList<Map<String, Object>>();
		double distance = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (lists != null) {
			for (int i = 0; i < lists.size(); i++) {
				distance = MapDistance.getDistance(
								Double.valueOf(latitude),
								Double.valueOf(longitude),
								Double.valueOf(lists.get(i).get("LATITUDE").toString()),
								Double.valueOf(lists.get(i).get("LONGITUDE").toString()));
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

	public String selEquipInfo(UIPage page, HttpServletRequest request, String para) {
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

	@Override
	public String updEquipInfo(String para) {
		JSONObject jsonObject = JSONObject.fromObject(para);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		String user_id = jsonObject.getString("user_id");
		String sn = jsonObject.getString("sn");
//		if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
//			return Result.returnCode("002");
//		}
		String danger_type = jsonObject.getString("danger_type");
		Object equip_info = jsonObject.get("equip_info");
		JSONObject equip_info_json = JSONObject.fromObject(equip_info);
		Object description_info = jsonObject.get("description");
		JSONObject description_info_json = JSONObject.fromObject(description_info);
		StringBuffer sb = new StringBuffer("");
		String description = "";
		if (description_info_json.size() > 0) {
			for (int i = 0; i < description_info_json.size(); i++) {
				sb.append(description_info_json.get("txt" + (i + 1)) + ",");
			}
		}
		String str = sb.toString();
		description = str.substring(0, str.lastIndexOf(","));
		Object updator = equip_info_json.get("userId");
		Object equip_code = equip_info_json.get("equip_code");
		Object equip_address = equip_info_json.get("equip_address");
		Object owner_name = equip_info_json.get("owner_name");
		Object owner_tel = equip_info_json.get("owner_tel");
		Object protecter = equip_info_json.get("protecter");
		Object protect_tel = equip_info_json.get("protect_tel");
		String update_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Object cable_id = equip_info_json.get("cable_id");
		Object relay_id = equip_info_json.get("relay_id");
		Object equip_id = equip_info_json.get("equip_id");
		Map<String, Object> equip = new HashMap<String, Object>();
		equip.put("cable_id", cable_id);
		equip.put("relay_id", relay_id);
		equip.put("equip_id", equip_id);

		equip.put("updator", updator);
		equip.put("equip_code", equip_code);
		equip.put("equip_address", equip_address);
		equip.put("description", description);
		equip.put("owner_name", owner_name);
		equip.put("owner_tel", owner_tel);
		equip.put("protecter", protecter);
		equip.put("protect_tel", protect_tel);
		equip.put("update_date", update_date);
		if(danger_type.equals("2")){
			equip.put("is_lose", 0);
		}
		// TODO 日志报错 本地无法复现 后续处理
		collectInfoOfRelayDao.updEquipInfo(equip);
		
		if(danger_type.equals("2")){
			String order_id = jsonObject.getString("order_id");
			String deal_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_id", order_id);
			map.put("repair_time", deal_time);
			map.put("order_status", 2);// 2为已经处理 待审核
			// 更新隐患单
			dangerOrderDao.dangerOrderUpdate(map);
		}
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
			String sn = jsonObject.getString("sn");
			if (!OnlineUserListener.isLogin(user_id, sn)) {// 登录校验
			 return Result.returnCode("002");
			}
			String cable_id = jsonObject.getString("cable_id");
			String relay_id = jsonObject.getString("relay_id");
			String area_id = jsonObject.getString("area_id");
			Map<String, Object> paramap=new HashMap<String, Object>();
			paramap.put("cable_id", cable_id);
			paramap.put("relay_id", relay_id);
			paramap.put("area_id", area_id);
			List<Map<String, Object>> list = selAllStepEquipByUserId(paramap); // 查询该用户所有任务对应的巡线信息
			List<Map<String, Object>> outSites = outSiteInterfaceDao.getOutSitesByMap(paramap);
			resultMap.put("equipList", list);
			resultMap.put("outSites", outSites);
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
		List<Map<String, Object>> lists = collectInfoOfRelayDao.selEquipPartByTaskId(paramap); // 更具条件查询路由设施点
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
			String longitudegps = jsonObject.getString("longitudegps");
			String latitudegps = jsonObject.getString("latitudegps");
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
			//因为修改过表结构所以在这边增加线路段id 孙敏 2016/3/29
			String cable_id = equip_info_json.getString("cable_id");
			String relay_id = equip_info_json.getString("relay_id");
			Map<String, Object> equip = new HashMap<String, Object>();
			equip.put("equip_id", equip_id);
			equip.put("creator", creator);
			equip.put("updator", updator);
			equip.put("equip_code", equip_code);
			equip.put("equip_address", equip_address);
			equip.put("description", description);
			equip.put("longitude", longitude);
			equip.put("latitude", latitude);
			equip.put("longitudegps", longitudegps);
			equip.put("latitudegps", latitudegps);
			equip.put("equip_type", equip_type);
			equip.put("owner_name", owner_name);
			equip.put("owner_tel", owner_tel);
			equip.put("protecter", protecter);
			equip.put("protect_tel", protect_tel);
			equip.put("area_id", area_id);
			equip.put("is_lose", is_lose);
			equip.put("cable_id", cable_id);
			equip.put("relay_id", relay_id);
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
				map.put("latitudegps", latitudegps);
				map.put("longitudegps", longitudegps);
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
			//判断一下当前的这个设施点的检查时间是否在任务的时间之内，
			//如果是的话就把任务路由设施关联表中的任务状态给置1，并且插上完成时间为当前时间
			List<Map<String, Object>> ComEffective=collectInfoOfRelayDao.selComEffective(equip_id);
			if(ComEffective.size()>0){
				collectInfoOfRelayDao.updTaskStatus(equip_id);	
			}	
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
	

	/**
	 * 查询已自己所在坐标1000平方米正方形之内的所有的路由设施点
	 */
	@Override
	public List<Map<String, Object>> selXYStepEquipByUserId(Map<String, Object> paramap) {
		List<Map<String, Object>> lists = collectInfoOfRelayDao.selEquipPartByXYTaskId(paramap); // 更具条件查询路由设施点
		return lists;
	}
	
}
