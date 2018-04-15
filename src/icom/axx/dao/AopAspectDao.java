package icom.axx.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 爱巡线切面类Dao
 * @author wangxiangyu
 *
 */
@Repository("AopAspectDao")
public interface AopAspectDao {
	/**
	 * 保存接口调用信息
	 * @param photoMap
	 */
	public void saveInvokeInfo(Map<String, String> param);
}
