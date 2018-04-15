package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface LineConvertDao {
    /**
     * 获取转换站点
     * @param map
     * @return
     */
    public List<Map<String,Object>> getConvertSites(Map<String, Object> map);
    
    
    /**
     * 修改转换成功的站点
     * @param map
     */
    public void updateSites(Map<String, Object> map);
    
    /**
     * 保存转换失败站点
     * @param map
     */
    public void saveInvalidSites(Map<String, Object> map);
    
    
    /**
     * 获取转换临时站点
     * @param map
     * @return
     */
    public List<Map<String,Object>> getTempSites();
    
    /**
     * 修改转换临时的站点
     * @param map
     */
    public void updateTempSite(Map<String, Object> map);
}
