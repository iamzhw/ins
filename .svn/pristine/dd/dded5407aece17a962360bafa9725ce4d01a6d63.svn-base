package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface DotConvertDao {
   
	/**
     * 获取全部的步巡设施点中的id、经纬度、新加的状态值默认0未修改1修改过
     * @param map
     * @return
     */
    public List<Map<String,Object>> getAllEquip();
    
    /**
     * 修改转换步巡的经纬度
     * @param map
     */
    public void updatedDotXY(Map<String, Object> map);
    
    /**
     * 获取全部的步巡设施点总数
     * @param map
     * @return
     */
    public int getCountEquip();
    
    /**
     * 按分页来查询
     * @param map
     * @return
     */
    public List<Map<String,Object>> getPageCountEquip();
    
}
