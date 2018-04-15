package com.linePatrol.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import util.page.Query;

import com.axxreport.util.ExcelUtil03;
import com.axxreport.util.ExcelUtil07;
import com.linePatrol.dao.CableStatementsDao;
import com.linePatrol.dao.CutAndConnOfFiberDao;
import com.linePatrol.service.CableStatementsService;

@Transactional
@Service
public class CableStatementsServiceImpl implements CableStatementsService{

	@Autowired
	private CableStatementsDao cableStatementsDao;
	
	@Autowired
	private CutAndConnOfFiberDao cutAndConnOfFiberDao;

	/**
	 * 光纤通道后向散射信号曲线检查表的填报人，填报时间等信息
	 */
	@Override
	public List<Map<String, Object>> BaseInfoOfReport203(Map<String, Object> map) {
		return cableStatementsDao.BaseInfoOfReport203(map);
	}

	@Override
	public List<Map<String, Object>> report203Query(Query query) {
		return cableStatementsDao.report203Query(query);
	}

	@Override
	public List<Map<String, Object>> report207Query(Query query) {
		return cableStatementsDao.report207Query(query);
	}

	@Override
	public List<Map<String, Object>> get207collection(Query query) {
		return cableStatementsDao.get207collection(query);
	}

	@Override
	public List<Map<String, Object>> report204Query(Query query) {
		return cableStatementsDao.report204Query(query);
	}

	@Override
	public List<Map<String, Object>> get204collection(Query query) {
		return cableStatementsDao.get204collection(query);
	}

	@Override
	public void delReport203(String str) {
		Map<String, Object> paraMap = null;
		JSONArray jArray=JSONArray.fromObject(str);  
		for (int i = 0; i < jArray.size(); i++) {
			paraMap = new HashMap<String, Object>();
			JSONObject json=JSONObject.fromObject(jArray.get(i));
			paraMap.put("rowIndex", json.get("rowIndex"));
			paraMap.put("cityKey", json.get("cityKey"));
			paraMap.put("yearPart", json.get("yearPart"));
			cableStatementsDao.delReport203(paraMap);
		}
	}

	@Override
	public void delReport204(String str) {
		String[] params=str.split(",");
		Map<String, Object> paraMap=new HashMap<String, Object>();
		for (int i = 0; i < params.length; i++) {
			paraMap.put("r_id", params[i]);
			cableStatementsDao.delReport204(paraMap);
		}
	}

	@Override
	public void delReport207(String str) {
		String[] params=str.split(",");
		Map<String, Object> paraMap=new HashMap<String, Object>();
		for (int i = 0; i < params.length; i++) {
			paraMap.put("subId", params[i]);
			cableStatementsDao.delReport207(paraMap);
		}
	}

	@Override
	public void updReport203Info(Map<String, Object> map) {
		cableStatementsDao.updReport203Info(map);
	}

	@Override
	public void updReport204Info(Map<String, Object> map) {
		cableStatementsDao.updReport204Info(map);
	}

	@Override
	public void updReport207Info(Map<String, Object> map) {
		cableStatementsDao.updReport207Info(map);
	}

	@Override
	public List<Map<String, Object>> getFYPbyPart(Map<String, Object> map) {
		return cableStatementsDao.getFYPbyPart(map);
	}

	@Override
	public void addReport203(Map<String, Object> map) {
		cableStatementsDao.addReport203(map);
	}

	@Override
	public void addReport204(Map<String, Object> map) {
		cableStatementsDao.addReport204(map);
	}
	
	
	@Override
	public String importDo_Fiber(HttpServletRequest request,
			MultipartFile file) {
		String info="";
		JSONObject result = new JSONObject();
		try {
			info=importMainSub_Fiber(request, file);
			result.put("info", info);
			result.put("status", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", false);
			result.put("message", "数据格式错误！");
		}
	
		return result.toString();
	}
	
	
	public String importMainSub_Fiber(HttpServletRequest request, MultipartFile file) throws Exception {
		String tip="";
		int row_num = 1;
		Map<String, Object> params = null;
		List<List<String>> datas=null;
		String filename=file.getOriginalFilename();
	    String suffix = filename.substring(filename.lastIndexOf("."),filename.length());
	    if (".xls".equals(suffix)) {
	    	ExcelUtil03 parse = new ExcelUtil03(file.getInputStream());
			datas = parse.getDatas(0, 1, 41);
	    }
	    else if (".xlsx".equals(suffix)) {
	    	ExcelUtil07 parse = new ExcelUtil07(file.getInputStream());
			datas = parse.getDatas(0, 1, 11);
		}
		
		Map<String, Object> temp = null;
		List<String> data = null;

		for (int i = 0, j = datas.size(); i < j; i++) {
			params = new HashMap<String, Object>();
			temp = new HashMap<String, Object>();
			data = datas.get(i);

			if(String.valueOf(data.get(0).trim()).equals("")){
				continue;
			}
			String codeId = String.valueOf(data.get(0).trim());
			
			temp.put("city_name", String.valueOf(data.get(0).trim()));
			List<Map<String, Object>> city = cutAndConnOfFiberDao.getCityName(temp);
			if(city.size()!=1){
				row_num++;
				tip+="----------【批量导入模板】Excel第【"+row_num+"】行数据"+codeId+"导入失败，地区名不对----------<br>";
				continue;
			}
			
			temp.put("cable_name", String.valueOf(data.get(1).trim()));
			List<Map<String, Object>> cable = cutAndConnOfFiberDao.getCableName(temp);
			if(cable.size()!=1){
				row_num++;
				tip+="----------【批量导入模板】Excel第【"+row_num+"】行数据"+codeId+"导入失败，干线光缆名称不对----------<br>";
				continue;
			}
			
			temp.put("cable_id", cable.get(0).get("CABLE_ID"));
			temp.put("relay_name", String.valueOf(data.get(2).trim()));
			List<Map<String, Object>> relay = cutAndConnOfFiberDao.getRelayName(temp);
			if(relay.size()!=1){
				row_num++;
				tip+="----------【批量导入模板】Excel第【"+row_num+"】行数据"+codeId+"导入失败，中继段名称不对----------<br>";
				continue;
			}
			params.put("city_name", city.get(0).get("AREA_ID"));//地区名
			params.put("cable_name", cable.get(0).get("CABLE_ID"));//干线光缆
			params.put("relay_name", relay.get(0).get("RELAY_ID"));//中继段
			params.put("yearpart", data.get(3).trim());//年份
			params.put("xinnumber", data.get(4).trim());//纤芯号:
			params.put("onenumber", data.get(5).trim());//每公里衰耗值dB/km:
			params.put("junnumber", data.get(6).trim());//竣工衰耗基准值dB/km:
			cableStatementsDao.addReport203(params);
			row_num++;
			tip+="----------【批量导入模板】Excel第【"+row_num+"】行数据"+codeId+"导入成功----------<br>";
			
		}
		return tip;
	}
}
