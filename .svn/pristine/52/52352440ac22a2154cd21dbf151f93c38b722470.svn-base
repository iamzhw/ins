package com.linePatrol.service.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.axxreport.util.ExcelUtil;
import com.axxreport.util.ExcelUtil03;
import com.linePatrol.dao.CutAndConnOfFiberDao;
import com.linePatrol.service.CutAndConnOfFiberService;
import com.axxreport.util.ExcelUtil07;

import util.page.Query;
import util.page.UIPage;

@Transactional
@Service
public class CutAndConnOfFiberServiceImpl implements CutAndConnOfFiberService {

	@Autowired
	private CutAndConnOfFiberDao cutAndConnOfFiberDao;

	public List<Map<String, Object>> query(Query query) {

		return cutAndConnOfFiberDao.query(query);
	}

	@Override
	public List<Map<String, Object>> getCityInfo(Map<String, Object> map) {
		return cutAndConnOfFiberDao.getCityInfo(map);
	}

	@Override
	public List<Map<String, Object>> getCable(String areaId) {
		return cutAndConnOfFiberDao.getCable(areaId);
	}

	@Override
	public List<Map<String, Object>> getRelay(String cableId) {
		return cutAndConnOfFiberDao.getRelay(cableId);
	}

	@Override
	public List<Map<String, Object>> showRelayDetailInfo(Query query) {
		return cutAndConnOfFiberDao.showRelayDetailInfo(query);
	}

	@Override
	public List<Map<String, Object>> getCuttingRecordOfFiber(Query query) {
		return cutAndConnOfFiberDao.getCuttingRecordOfFiber(query);
	}

	@Override
	public List<Map<String, Object>> getRecordOfSteps(Query query) {
		return cutAndConnOfFiberDao.getRecordOfSteps(query);
	}

	@Override
	public void delTestInfo(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relayinfoId", id);
		cutAndConnOfFiberDao.delTestDetail(map);
		cutAndConnOfFiberDao.delTest(id);
	}

	@Override
	public int delTestDetail(Map<String, Object> map) {
		return cutAndConnOfFiberDao.delTestDetail(map);
	}

	@Override
	public int updTestInfo(Map<String, Object> map) {
		return cutAndConnOfFiberDao.updTestInfo(map);
	}

	@Override
	public int updTestDetailInfo(Map<String, Object> map) {
		return cutAndConnOfFiberDao.updTestDetailInfo(map);
	}

	@Override
	public List<Map<String, Object>> getListOfSensitiveline(Query query) {
		return cutAndConnOfFiberDao.getListOfSensitiveline(query);
	}

	@Override
	public void addTestInfo(Map<String, Object> map) {
		cutAndConnOfFiberDao.addTestInfo(map);
	}

	@Override
	public void addTestDetailInfo(Map<String, Object> map) {
		cutAndConnOfFiberDao.addTestDetailInfo(map);
	}

	@Override
	public int delRecordOfFiber(Map<String, Object> map) {
		return cutAndConnOfFiberDao.delRecordOfFiber(map);
	}

	@Override
	public void addRecordOfFiber(Map<String, Object> map) {
		cutAndConnOfFiberDao.addRecordOfFiber(map);
	}

	@Override
	public void updRecordOfFiber(Map<String, Object> map) {
		cutAndConnOfFiberDao.updRecordOfFiber(map);
	}

	@Override
	public void delStepData(Map<String, Object> map) {
		cutAndConnOfFiberDao.delStepData(map);
	}

	@Override
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, Query query) {
		List<String> title = Arrays.asList(new String[] { "编号", "测试方向", "纤号 ",
				"台阶位置", "耗损", "计划整改时间", "实际整改时间", "光缆线路名称", "中继段" });
		List<String> code = Arrays.asList(new String[] { "STEPID", "DIRECTION",
				"FIBERNO", "STEPPLACE", "LOSS", "PLANDATE", "REALDATE",
				"CABLE_NAME", "RELAY_NAME" });
		List<Map<String, Object>> data = this.getRecordOfSteps(query);
		String fileName = "大台阶数据记录";
		String firstLine = "大台阶数据记录";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String importDo(HttpServletRequest request, MultipartFile file) {
		String info = "";
		try {
			info = importMainSub(request, file);
		} catch (Exception e) {
			e.printStackTrace();
			info = "数据格式错误";
		}

		return info;
	}

	@Override
	public String importDo_Fiber(HttpServletRequest request, MultipartFile file) {
		String info = "";
		try {
			info = importMainSub_Fiber(request, file);
		} catch (Exception e) {
			e.printStackTrace();
			info = "数据格式错误";
		}

		return info;
	}

	@Override
	public String importDo_Step(HttpServletRequest request, MultipartFile file) {
		String info = "";
		try {
			info = importMainSub_Step(request, file);
		} catch (Exception e) {
			e.printStackTrace();
			info = "数据格式错误";
		}

		return info;
	}

	public String importMainSub(HttpServletRequest request, MultipartFile file)
			throws Exception {
		String tip = "";
		Map<String, Object> params = null;
		String RELAYINFOID = request.getParameter("relayinfoId");
		List<List<String>> datas = null;
		String filename = file.getOriginalFilename();
		String suffix = filename.substring(filename.lastIndexOf("."), filename
				.length());
		if (".xls".equals(suffix)) {
			ExcelUtil03 parse = new ExcelUtil03(file.getInputStream());
			datas = parse.getDatas(0, 2, 21);
		} else if (".xlsx".equals(suffix)) {
			ExcelUtil07 parse = new ExcelUtil07(file.getInputStream());
			datas = parse.getDatas(0, 2, 21);
		}

		Map<String, Object> cityMap = null;
		List<String> data = null;

		for (int i = 0, j = datas.size(); i < j; i++) {
			params = new HashMap<String, Object>();
			cityMap = new HashMap<String, Object>();
			data = datas.get(i);

			// if(String.valueOf(data.get(0).trim()).equals("")){
			// continue;
			// }
			params.put("relayinfoId", RELAYINFOID);
			params.put("directiona1", data.get(0).trim());
			params.put("directionb1", data.get(1).trim());
			params.put("avg1", data.get(2).trim());
			params.put("directiona2", data.get(3).trim());
			params.put("directionb2", data.get(4).trim());
			params.put("avg2", data.get(5).trim());
			params.put("directiona3", data.get(6).trim());
			params.put("directionb3", data.get(7).trim());
			params.put("avg3", data.get(8).trim());
			params.put("directiona4", data.get(9).trim());
			params.put("directionb4", data.get(10).trim());
			params.put("avg4", data.get(11).trim());
			params.put("directiona5", data.get(12).trim());
			params.put("directionb5", data.get(13).trim());
			params.put("avg5", data.get(14).trim());
			params.put("directiona6", data.get(15).trim());
			params.put("directionb6", data.get(16).trim());
			params.put("avg6", data.get(17).trim());
			params.put("beforedb", data.get(18).trim());
			params.put("db", data.get(19).trim());
			params.put("dbkm", data.get(20).trim());

			// String codeId = String.valueOf(data.get(0).trim());
			cutAndConnOfFiberDao.addTestDetailInfo(params);
			tip += "----------【批量导入模板】Excel数据第" + i + "行数据导入成功----------<br>";

		}
		return tip;
	}

	public String importMainSub_Fiber(HttpServletRequest request,
			MultipartFile file) throws Exception {
		String tip = "";
		Map<String, Object> params = null;
		List<List<String>> datas = null;
		String filename = file.getOriginalFilename();
		String suffix = filename.substring(filename.lastIndexOf("."), filename
				.length());
		if (".xls".equals(suffix)) {
			ExcelUtil03 parse = new ExcelUtil03(file.getInputStream());
			datas = parse.getDatas(0, 1, 41);
		} else if (".xlsx".equals(suffix)) {
			ExcelUtil07 parse = new ExcelUtil07(file.getInputStream());
			datas = parse.getDatas(0, 1, 41);
		}

		Map<String, Object> temp = null;
		List<String> data = null;

		for (int i = 0, j = datas.size(); i < j; i++) {
			params = new HashMap<String, Object>();
			temp = new HashMap<String, Object>();
			data = datas.get(i);

			if (String.valueOf(data.get(0).trim()).equals("")) {
				continue;
			}
			String codeId = String.valueOf(data.get(0).trim());

			temp.put("city_name", String.valueOf(data.get(0).trim()));
			List<Map<String, Object>> city = cutAndConnOfFiberDao
					.getCityName(temp);
			if (city.size() != 1) {
				tip += "----------【批量导入模板】Excel数据" + codeId
						+ "导入失败，地区名不对----------<br>";
				continue;
			}

			temp.put("cable_name", String.valueOf(data.get(1).trim()));
			List<Map<String, Object>> cable = cutAndConnOfFiberDao
					.getCableName(temp);
			if (cable.size() != 1) {
				tip += "----------【批量导入模板】Excel数据" + codeId
						+ "导入失败，干线光缆名称不对----------<br>";
				continue;
			}

			temp.put("cable_id", cable.get(0).get("CABLE_ID"));
			temp.put("relay_name", String.valueOf(data.get(3).trim()));
			List<Map<String, Object>> relay = cutAndConnOfFiberDao
					.getRelayName(temp);
			if (relay.size() != 1) {
				tip += "----------【批量导入模板】Excel数据" + codeId
						+ "导入失败，中继段名称不对----------<br>";
				continue;
			}

			params.put("localId", city.get(0).get("AREA_ID"));// 地区名
			params.put("cable_name", cable.get(0).get("CABLE_ID"));// 干线光缆名称
			params.put("cutdate", data.get(2).trim());// 割接日期
			params.put("relay_name", relay.get(0).get("RELAY_ID"));// 中继段
			params.put("changedate", data.get(4).trim());// 机务人员开始调度
			params.put("fiberlengthatnow", data.get(5).trim());// 原即时长度
			params.put("fiberpiecing", data.get(6).trim());// 原接头号
			params.put("fibercurdate", data.get(7).trim());// 光缆割接开始时间
			params.put("newfibername", data.get(8).trim());// 新光缆厂家
			params.put("curstartdate", data.get(9).trim());// 接续开始时间
			params.put("newxinname", data.get(10).trim());// 新纤芯厂家
			params.put("curenddate", data.get(11).trim());// 接续完成时间
			params.put("oldfiberxinsp", data.get(12).trim());// 原光缆纤芯色谱
			params.put("datedistain", data.get(13).trim());// 光缆割接时长
			params.put("newfiberxinsp", data.get(14).trim());// 新光缆纤芯色谱
			params.put("regaindate", data.get(15).trim());// 所有系统恢复正常时间
			params.put("oldcutoverlength", data.get(16).trim());// 割接前纤芯长度（测试）
			params.put("newcutoverlength", data.get(17).trim());// 割接后纤芯长度（全程）
			params.put("lineminsunhao", data.get(18).trim());// 接头最小耗损
			params.put("sunhao3", data.get(19).trim());// 接头耗损>0.3db纤芯数
			params.put("linemaxsunhao", data.get(20).trim());// 接头最大耗损
			params.put("changelength", data.get(21).trim());// 割接后纤芯长度变化
			params.put("sunhao5", data.get(22).trim());// 接头耗损>0.5db纤芯数
			params.put("tester", data.get(23).trim());// 测试人员
			params.put("testdate", data.get(24).trim());// 测试时间
			params.put("peoplenumber", data.get(25).trim());// 参加割接总人数
			params.put("curleader", data.get(26).trim());// 割接现场指挥
			params.put("jwpeoples", data.get(27).trim());// 机务人员名单
			params.put("jifang1", data.get(28).trim());// 本端机房
			params.put("jwtestpeoples", data.get(29).trim());// 机房测试人员名单
			params.put("jifang2", data.get(30).trim());// 对端机房1
			params.put("curoverpeoplenumber", data.get(31).trim());// 割接现场人员总数
			params.put("jifang3", data.get(32).trim());// 对端机房2
			params.put("curoverpeoples", data.get(33).trim());// 割接现场人员名单
			params.put("zhenshipeo", data.get(34).trim());// 正式员工人数
			params.put("raowupeo", data.get(35).trim());// 劳务工人数
			params.put("qitapeo", data.get(36).trim());// 其它人员数
			params.put("tianbiaoren", data.get(37).trim());// 填表人
			params.put("jifangname", data.get(38).trim());// 本端机房名称

			cutAndConnOfFiberDao.addRecordOfFiber(params);
			tip += "----------【批量导入模板】Excel数据" + codeId + "导入成功----------<br>";

		}
		return tip;
	}
	//TODO
	public String importMainSub_Step(HttpServletRequest request, MultipartFile file) throws Exception {
		String tip = "";
		int row_num = 2;
		Map<String, Object> params = null;
		List<List<String>> datas = null;
		String filename = file.getOriginalFilename();
		String suffix = filename.substring(filename.lastIndexOf("."), filename.length());
		if (".xls".equals(suffix)) {
			ExcelUtil03 parse = new ExcelUtil03(file.getInputStream());
			datas = parse.getDatas(0, 2, 12);
		} else if (".xlsx".equals(suffix)) {
			ExcelUtil07 parse = new ExcelUtil07(file.getInputStream());
			datas = parse.getDatas(0, 2, 12);
		}

		Map<String, Object> temp = null;
		List<String> data = null;

		for (int i = 0, j = datas.size(); i < j; i++) {
			params = new HashMap<String, Object>();
			temp = new HashMap<String, Object>();
			data = datas.get(i);

			if (String.valueOf(data.get(2).trim()).equals("")) {
				continue;
			}
			String codeId = String.valueOf(data.get(2).trim());

			temp.put("city_name", String.valueOf(data.get(0).trim()));
			List<Map<String, Object>> city = cutAndConnOfFiberDao.getCityName(temp);
			if (city.size() != 1) {
				row_num++;
				tip += "----------【批量导入模板】Excel第【"+row_num+"】行数据" + codeId + "导入失败，地区名不对----------<br>";
				continue;
			}

			temp.put("cable_name", String.valueOf(data.get(1).trim()));
			List<Map<String, Object>> cable = cutAndConnOfFiberDao.getCableName(temp);
			if (cable.size() != 1) {
				row_num++;
				tip += "----------【批量导入模板】Excel第【"+row_num+"】行数据" + codeId + "导入失败，干线光缆名称不对----------<br>";
				continue;
			}
			temp.put("cable_id", cable.get(0).get("CABLE_ID"));
			temp.put("relay_name", String.valueOf(data.get(2).trim()));
			List<Map<String, Object>> relay = cutAndConnOfFiberDao.getRelayName(temp);
			if (relay.size() != 1) {
				row_num++;
				tip += "----------【批量导入模板】Excel第【"+row_num+"】行数据" + codeId + "导入失败，中继段名称不对----------<br>";
				continue;
			}

			params.put("city_name", city.get(0).get("AREA_ID"));// 地区名
			params.put("cable_name", cable.get(0).get("CABLE_ID"));// 干线光缆名称
			params.put("relay_name", relay.get(0).get("RELAY_ID"));// 中继段
			params.put("direction", data.get(3).trim());// 测试方向
			params.put("fiberno", data.get(4).trim());// 纤号
			params.put("stepplace", data.get(5).trim());// 台阶位置
			params.put("loss", data.get(6).trim());// 损耗
			params.put("plandate", data.get(7).trim());// 计划整改时间
			params.put("realdate", data.get(8).trim());// 实际整改时间

			cutAndConnOfFiberDao.addStepData(params);
			row_num++;
			tip += "----------【批量导入模板】Excel第【"+row_num+"】行数据" + codeId + "导入成功----------<br>";

		}
		return tip;
	}

	@Override
	public void addStepData(Map<String, Object> map) {
		cutAndConnOfFiberDao.addStepData(map);
	}

	@Override
	public void updStepData(Map<String, Object> map) {
		cutAndConnOfFiberDao.updStepData(map);
	}

	@Override
	public void exportTextInfoExcel(HttpServletRequest request,
			HttpServletResponse response, Query query, Map<String, Object> para) {
		String relayinfoIds = para.get("relayinfoids").toString();
		String[] ids = relayinfoIds.split(",");
		List<Map<String, Object>> resultLists = new ArrayList<Map<String, Object>>();
		for (String relayinfoId : ids) {
			para.put("relayinfoId", relayinfoId);
			resultLists.addAll(cutAndConnOfFiberDao.exportTextInfoExcel(para));
		}
		List<String> title = Arrays.asList(new String[] { "编号", "光缆线路名称",
				"中继段 ", "中继段全长 ", "测试日期", "测试地点1", "测试人员1", "测试仪表1", "测试地点2",
				"测试人员2", "测试仪表2", "测试窗口", "折射率", "接头1：A向km", "接头1：B向km",
				"接头1：平均", "接头2：A向km", "接头2：B向km", "接头2：平均", "接头3：A向km",
				"接头3：B向km", "接头3：平均", "接头4：A向km", "接头4：B向km", "接头4：平均",
				"接头5：A向km", "接头5：B向km", "接头5：平均", "接头6：A向km", "接头6：B向km",
				"接头6：平均", "总耗损dB", "衰减系数dB/km" });
		List<String> code = Arrays.asList(new String[] { "RELAYINFOID",
				"CABLE_NAME", "RELAY_NAME", "RELAYDISTANCE", "TESTDATE",
				"PLACE1", "TESTER1", "TESTMETER1", "PLACE2", "TESTER2",
				"TESTMETER2", "TESTFORM", "REFRACTION", "DIRECTIONA1",
				"DIRECTIONB1", "AVG1", "DIRECTIONA2", "DIRECTIONB2", "AVG2",
				"DIRECTIONA3", "DIRECTIONB3", "AVG3", "DIRECTIONA4",
				"DIRECTIONB4", "AVG4", "DIRECTIONA5", "DIRECTIONB5", "AVG5",
				"DIRECTIONA6", "DIRECTIONB6", "AVG6", "DB", "DBKM"

		});

		String fileName = "aaaa";
		String firstLine = "接头耗损及中继段测试记录表";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, resultLists,
					request, response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Object importCutAndConnOfFiber(HttpServletRequest request,
			MultipartFile file) {
		JSONObject result = new JSONObject();
		String info = "";
		try {
			info = importMain(request, file);
			result.put("status", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", false);
			result.put("message", "文件导入错误！");
		}

		return result;
	}

	public String importMain(HttpServletRequest request, MultipartFile file)
			throws Exception {
		String tip = "";
		Map<String, Object> params = null;
		String staffId = request.getSession().getAttribute("staffId")
				.toString();
		// String roleNo = String.valueOf(request.getSession().getAttribute(
		// "roleNo"));
		List<List<String>> datas = null;
		String filename = file.getOriginalFilename();
		String suffix = filename.substring(filename.lastIndexOf("."), filename
				.length());
		if (".xls".equals(suffix)) {
			ExcelUtil03 parse = new ExcelUtil03(file.getInputStream());
			datas = parse.getDatas(0, 1, 11);
		} else if (".xlsx".equals(suffix)) {
			ExcelUtil07 parse = new ExcelUtil07(file.getInputStream());
			datas = parse.getDatas(0, 1, 20);
		}

		Map<String, Object> temp = null;
		List<String> data = null;

		for (int i = 0, j = datas.size(); i < j; i++) {
			params = new HashMap<String, Object>();
			temp = new HashMap<String, Object>();
			data = datas.get(i);

			if (String.valueOf(data.get(0).trim()).equals("")) {
				continue;
			}
			String codeId = String.valueOf(data.get(0).trim());

			temp.put("city_name", String.valueOf(data.get(0).trim()));
			List<Map<String, Object>> city = cutAndConnOfFiberDao
					.getCityName(temp);
			if (city.size() != 1) {
				tip += "----------【批量导入模板】Excel数据" + codeId
						+ "导入失败，地区名不对----------<br>";
				continue;
			}

			temp.put("cable_name", String.valueOf(data.get(1).trim()));
			List<Map<String, Object>> cable = cutAndConnOfFiberDao
					.getCableName(temp);
			if (cable.size() != 1) {
				tip += "----------【批量导入模板】Excel数据" + codeId
						+ "导入失败，干线光缆名称不对----------<br>";
				continue;
			}

			temp.put("cable_id", cable.get(0).get("CABLE_ID"));
			temp.put("relay_name", String.valueOf(data.get(2).trim()));
			List<Map<String, Object>> relay = cutAndConnOfFiberDao
					.getRelayName(temp);
			if (relay.size() != 1) {
				tip += "----------【批量导入模板】Excel数据" + codeId
						+ "导入失败，中继段名称不对----------<br>";
				continue;
			}
			params.put("city_name", city.get(0).get("AREA_ID"));// 地区名
			params.put("cable_name", cable.get(0).get("CABLE_ID"));// 干线光缆
			params.put("relay_name", relay.get(0).get("RELAY_ID"));// 中继段
			params.put("testdate", data.get(3).trim());// 测试时间
			params.put("relaydistance", data.get(4).trim());// 中继段全长
			params.put("place1", data.get(5).trim());// 测试地点1
			params.put("tester1", data.get(6).trim());// 测试人1
			params.put("testmeter1", data.get(7).trim());// 测试仪表1
			params.put("place2", data.get(8).trim());// 测试地点2
			params.put("tester2", data.get(9).trim());// 测试人2
			params.put("testmeter2", data.get(10).trim());// 测试仪表2
			params.put("testform", data.get(11).trim());// 测试窗口
			params.put("refraction", data.get(12).trim());// 折射率
			cutAndConnOfFiberDao.addTestInfo(params);
			tip += "----------【批量导入模板】Excel数据" + codeId + "导入成功----------<br>";
		}
		return tip;
	}

	@Override
	public void exportFiberRecordExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> para) {
		List<Map<String, Object>> data = cutAndConnOfFiberDao
				.exportFiberRecordExcel(para);
		List<String> title = Arrays.asList(new String[] { "光缆线路名称", "割切日期",
				"中继段 ", "机务人员开始调度 ", "原即时长度", "原接头号", "光缆割接开始时间", "新光缆厂家",
				"接续开始时间", "新纤芯厂家", "接续完成时间", "原光缆纤芯色谱", "光缆割接时长", "新光缆纤芯色谱",
				"所有系统恢复正常时间", "割接前纤芯长度（测试）", "割接后纤芯长度（全程）", "接头最小耗损",
				"接头耗损>0.3db纤芯数", "接头最大耗损", "割接后纤芯长度变化", "接头耗损>0.5db纤芯数",
				"测试人员", "测试时间", "参加割接总人数", "割接现场指挥", "机务人员名单", "本端机房",
				"机房测试人员名单", "对端机房1", "割接现场人员总数", "对端机房2", "割接现场人员名单", "正式员工人数",
				"劳务工人数", "其它人员数", "填表人", "本端机房名称" });
		List<String> code = Arrays.asList(new String[] { "CABLE_NAME",
				"CUTDATE", "RELAY_NAME", "CHANGEDATE", "FIBERLENGTHATNOW",
				"FIBERPIECING", "FIBERCURDATE", "NEWFIBERNAME", "CURSTARTDATE",
				"NEWXINNAME", "CURSTARTDATE", "OLDFIBERXINSP", "DATEDISTAIN",
				"NEWFIBERXINSP", "REGAINDATE", "OLDCUTOVERLENGTH",
				"NEWCUTOVERLENGTH", "LINEMINSUNHAO", "SUNHAO3",
				"LINEMAXSUNHAO", "CHANGELENGTH", "SUNHAO5", "TESTER",
				"TESTDATE", "PEOPLENUMBER", "CURLEADER", "JWPEOPLES",
				"JIFANG1", "JWTESTPEOPLES", "JIFANG2", "CUROVERPEOPLENUMBER",
				"JIFANG3", "CUROVERPEOPLES", "ZHENSHIPEO", "RAOWUPEO",
				"QITAPEO", "TIANBIAOREN", "JIFANGNAME" });
		String fileName = "接头耗损及中继段测试记录表";
		String firstLine = "接头耗损及中继段测试记录表";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
