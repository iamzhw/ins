package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@SuppressWarnings("all")
@Repository
public interface FixOrderDao {
  /**
   * 生成整治单	
   */
  void intoFixOrder(Map<String,Object> map);
  
  /**
   * 生成结点
   * @param map
   */
  void intoSingelNodeList(Map<String,Object> map);
  
  /**
   * 获取整治单的下一个序列
   * @return
   */
  String getFixOrderNextVal();
  
  /**
   * 获取整治结点表的下一个序列
   * @return
   */
  String getSingelNodeListNextVal();
  
  /**
   * 查询地市管理员
   * @return
   */
  List<Map<String,Object>> selAreaAdmin(String area_id); 
  
  /**
   * 插入数据到人员整治单关联表
   * @param map
   */
  void intoFixOrderStaff(Map<String,Object> map);
  
  /**
   * 根据人员id查询整治单
   * @return
   */
  List<Map<String,Object>> selFixOrderByPerson(Map<String,Object> map); 
  
  /**
   * 更改整治单状态
   * @param map
   */
  void upFixOrderStatus(Map<String,Object> map);
  
  /**
   * 查询所有的整治单
   * @return
   */
  List<Map<String, Object>> selAllFixOrder();
  
  /**
   * 查询整治单信息页面展示
   * @param map
   * @return
   */
  public List<Map<String, Object>> query(Map<String, Object> map);
  
  /**
   *查询审核内容 
   * @param order_id
   * @return
   */
  Map<String, Object> findDetail(String fixorder_id);
  
  /**
   * 修改整治单状态是否归档
   * @param map
   */
  void upFixOrderStatusFile(Map<String, Object> map);
  
  /**
   * 查询时间最近的待审核的所有图片
   * @return
   */
  List<Map<String, Object>> selNoAuditPhotos(String fixorder_id);
  
  /**
   * 查询隐患单信息
   * @param fixorder_id
   * @return
   */
  Map<String, Object> selFixOrderByID(String fixorder_id);
  
  /**
   * 将已审核照片的状态给修改为1，以后手机端查询时候就不展示了
   * @param photo_id
   */
  void upPhotoStatu(String photo_id);
  
  /**
   * 根据整治单id去查询所有的结点
   * @param fixorder_id
   * @return
   */
  List<Map<String,Object>> selFixNodesByFixId(String fixorder_id);
  
  /**
   * 获取图片
   * @param map
   * @return
   */
  List<Map<String,Object>> getPhotos(Map<String, Object> map);
  
}
