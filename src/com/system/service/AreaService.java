package com.system.service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface AreaService {

    public List<Map<String, Object>> getAllArea();

    /**
     * @param areaId
     * @return
     */
    public List<Map<String, Object>> getSonArea(String areaId);

}
