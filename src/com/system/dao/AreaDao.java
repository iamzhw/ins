package com.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@SuppressWarnings("all")
@Repository
public interface AreaDao {

    /**
     * @return
     */
    List<Map<String, Object>> getAllArea();

    /**
     * @param areaId
     * @return
     */
    List<Map<String, Object>> getSonArea(String areaId);
    
    /**
     * @param areaId
     * @return
     */
    String getSonAreaId(Map map);

}
