package icom.util.GISutil.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@SuppressWarnings("all")
@Repository
public interface GisDao {
	public List<Map> queryMap(Map map);	
	
	public List<Map> getGLByEleRoad(Map map);
	
	public List<Map> get_cbl_sect_ByNo(Map map);

	public Map queryGisUrlByAreaId(Map map);
	
	public List<Map> getHisGLByBusiId(Map map);

	public List<Map> querySameSect(Map busiMap);
}
