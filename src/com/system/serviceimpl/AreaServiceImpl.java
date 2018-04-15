package com.system.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.dao.AreaDao;
import com.system.service.AreaService;

@SuppressWarnings("all")
@Transactional
@Service
public class AreaServiceImpl implements AreaService {
    @Resource
    private AreaDao areaDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.system.service.areaService#getAllArea()
     */
    @Override
    public List<Map<String, Object>> getAllArea() {
	// TODO Auto-generated method stub
	return areaDao.getAllArea();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.system.service.AreaService#getSonArea(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> getSonArea(String areaId) {
	// TODO Auto-generated method stub
	return areaDao.getSonArea(areaId);
    }

}
