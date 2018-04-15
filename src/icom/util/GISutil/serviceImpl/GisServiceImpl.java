package icom.util.GISutil.serviceImpl;

import icom.util.GISutil.dao.GisDao;
import icom.util.GISutil.service.GisService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import util.dataSource.SwitchDataSourceUtil;



@SuppressWarnings("all")
@Service
public class GisServiceImpl implements GisService {
	
	private final static String JNDI ="cpf83";
	
	@Resource
	private GisDao gisdao;
	
	@Override
	public List<Map> queryMap(String s,String jndi) {
		
		List<Map> res = new ArrayList<Map>();
		//获取单条电路承载的光路
		String[] dlS = s.split(",");
		List<Map> resut = new ArrayList<Map>();
		for(int i = 0;i<dlS.length;i++){
			List resultL = new ArrayList();
			Map resultM = new HashMap();
			String dl = dlS[i];
			Map busiLinkId = new HashMap();
			busiLinkId.put("no", dl);
			busiLinkId.put("jndi", jndi);
			List<Map> dL = new ArrayList();
			
			try{
				SwitchDataSourceUtil.setCurrentDataSource(JNDI);
				dL = gisdao.getGLByEleRoad(busiLinkId);
				SwitchDataSourceUtil.clearDataSource();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				SwitchDataSourceUtil.clearDataSource();
			}
			
				for(int j=0;j<dL.size();j++){
					Map gL = (Map) dL.get(j);
					if(gL.get("RES_SPEC_ID").equals("1614")){
						if(!resultL.contains(gL)){
							resultL.add(gL);
						}
					}
				}
			resultM.put("glInfo", resultL);
			resultM.put("dl", dl);
			resut.add(resultM);
		}
		try{
			for(int i=0;i<resut.size();i++){
				List<Map> eqpAndSect = new ArrayList<Map>();
				List<Map> hisEqpAndSect = new ArrayList<Map>();
				Map dlResult = new HashMap();
				List<Map> dl = (List<Map>) resut.get(i).get("glInfo");
				
				for(int j=0;j<dl.size();j++){
					Map param1 = new HashMap();
					param1.put("busi_link_id", dl.get(j).get("BUSI_LINK_ID"));
					param1.put("jndi", jndi);
					SwitchDataSourceUtil.setCurrentDataSource(JNDI);
					eqpAndSect.addAll(getRouteL(param1));
					SwitchDataSourceUtil.clearDataSource();
					//hisEqpAndSect.addAll(gisdao.getHisGLByBusiId(param1));
				}
				dlResult.put("dlNo",  null == resut.get(i).get("dl")?"":resut.get(i).get("dl").toString());
				dlResult.put("dl", eqpAndSect);
				//dlResult.put("his_dl", hisEqpAndSect);
				res.add(dlResult);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
	
		return res;
	}
	
	public List<Map> getRouteL(Map param) {
		//Map result = new HashMap();
		List l = gisdao.queryMap(param);
		//result.put("eqp_and_sect", l);
		return l;
	}

	@Override
	public Map queryGisUrlByAreaId(Map param) {
		// TODO Auto-generated method stub
		return gisdao.queryGisUrlByAreaId(param);
	}

	@Override
	public List<Map> querySameSectMap(String busiNo, String jndi) {
		
		List SameSectList = new ArrayList();
		String busiNos="";
		String[] busiNoArr = busiNo.split(",");
		if (busiNoArr.length==0)
		{
			return SameSectList;
		}
		for(int i = 0;i<busiNoArr.length;i++){
			busiNos +="'"+busiNoArr[i]+"',";
			}
		if(busiNos.endsWith(","))
		{
			busiNos = busiNos.substring(0,busiNos.length()-1);
		}
		Map busiMap = new HashMap();
		busiMap.put("busiNos", busiNos);
		busiMap.put("jndi", jndi);
		
		try{
			SwitchDataSourceUtil.setCurrentDataSource(JNDI);
			SameSectList = gisdao.querySameSect(busiMap);
			SwitchDataSourceUtil.clearDataSource();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			SwitchDataSourceUtil.clearDataSource();
		}
		return SameSectList;
		}

}
