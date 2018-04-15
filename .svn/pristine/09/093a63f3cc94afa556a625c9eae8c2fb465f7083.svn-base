package icom.util.GISutil.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface GisService {

	/**
	 * 查询所有光路中所有的管道段的ID
	 * @param busiNo 光路编码
	 * @param jndi 数据源
	 * @return
	 */
	public List<Map> queryMap(String s,String jndi);

	public Map queryGisUrlByAreaId(Map param);

	/**
	 * 查询所有光路中相同管道段的ID
	 * @param busiNo 光路编码
	 * @param jndi 数据源
	 * @return
	 */
	public List<Map> querySameSectMap(String busiNo, String jndi);
}
