package com.system.dao;

import com.system.model.SoftwareVersion;
import com.system.model.SoftwareVersionExample;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
public interface SoftwareVersionDao {
    int countByExample(SoftwareVersionExample example);

    int deleteByExample(SoftwareVersionExample example);

    int deleteByPrimaryKey(BigDecimal softwareVersionId);

    int insert(SoftwareVersion record);

    int insertSelective(SoftwareVersion record);

    List<SoftwareVersion> selectByExample(SoftwareVersionExample example);

    SoftwareVersion selectByPrimaryKey(BigDecimal softwareVersionId);

    int updateByExampleSelective(@Param("record") SoftwareVersion record, @Param("example") SoftwareVersionExample example);

    int updateByExample(@Param("record") SoftwareVersion record, @Param("example") SoftwareVersionExample example);

    int updateByPrimaryKeySelective(SoftwareVersion record);

    int updateByPrimaryKey(SoftwareVersion record);
    
    String getSoftwareVersionId();

	public String getAppDownLoadPath(String softId);
}