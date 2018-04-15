package com.linePatrol.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.util.PropertyFilter;


/**
 * Description: JSON对象工具类
 * </p>
 * 
 * @author zongjinxing
 * 2014-07-25
 */
public final class JSONUtil {
	private JSONUtil() {

	}


	

	/**
	 * <p>
	 * Title: Wms_common_utils
	 * </p>
	 * <p>
	 * Description: 对象转换JSON属性过滤器
	 * </p>
	 * 
	 * @author Jessy
	 * @version $Revision$ 2009-9-4
	 * @author (lastest modification by $Author$)
	 * @since 1.0
	 */
	public static class JSONPropertyFilter implements PropertyFilter {
		/**
		 * <p>
		 * Description:属性过滤，子类必须覆盖本方法
		 * </p>
		 * 
		 * @param name 属性名
		 * @param value 值
		 * @return 结果为false则略过当前属性
		 */
		protected boolean filter(String name, Object value) {
			return true;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see net.sf.json.util.PropertyFilter#apply(java.lang.Object, java.lang.String, java.lang.Object)
		 */
		public boolean apply(Object source, String name, Object value) {
			return !this.filter(name, value) || JSONNull.getInstance().equals(value) || value == null;
		}

	}

	

	

	/**
	 * <p>
	 * Description:将json格式的文件转化为JSON对象的字符串
	 * </p>
	 * 
	 * @param classPath JSON数据文件包路径
	 * @param encoding 编码格式
	 * @return
	 */
	public static String getObjectFromFileOfJSON(String classPath, String encoding) {
		StringBuffer str = new StringBuffer();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		 try {
			 
             File file=new File(classPath);
             if(file.isFile() && file.exists()){ //判断文件是否存在
                 read = new InputStreamReader(
                 new FileInputStream(file),encoding);//考虑到编码格式
                 bufferedReader = new BufferedReader(read);
                 String lineTxt = null;
                 while((lineTxt = bufferedReader.readLine()) != null){
                	 str.append(lineTxt+"\n");
//                     System.out.println(lineTxt);
                 }
                 read.close();
                 return str.toString();
             }else{
            	 System.out.println("找不到指定的文件");
            	 return null;
             }
		 } catch (Exception e) {
			 System.out.println("读取文件内容出错");
			 e.printStackTrace();
		 }finally{
			 try {
				if(read != null){
					read.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 return null;
	}
	
	
	/**
	 * 对JSONArray冒泡排序
	 * @param array JSON数组
	 * @param sortFiled 排序字段
	 * @return
	 */
	public static JSONArray sortArray(JSONArray temArray,String sortFiled){
		
//		Set<JSONObject> set = new TreeSet<JSONObject>();
//		for(int i=0;i<array.size();i++){
//			set.add(array.getJSONObject(i));
//		}
//		
//		
//		JSONArray temArray = new JSONArray();
//		for(Iterator<JSONObject> iterator = set.iterator();iterator.hasNext();){
//			temArray.add(iterator.next());
//		}
		
		for(int i=0;i<temArray.size();i++){
			for(int j=i+1;j<temArray.size();j++){
				long time = DateUtil.getDifferTime(temArray.getJSONObject(i).getString(sortFiled), 
						temArray.getJSONObject(j).getString(sortFiled), 1);
				if(time < 0){
					JSONObject temp = temArray.getJSONObject(i);
					temArray.set(i, temArray.getJSONObject(j));
					temArray.set(j, temp);
				}
			}
		}
		return temArray;
	}
	
	/**
	 * JSON数据按字段快速排序
	 * @param array JSON数组
	 * @param start 起始排序值
	 * @param end 终点排序值
	 * @param sortFiled 排序字段
	 */
	public static void quickSortArray(JSONArray array,int start,int end,String sortFiled){
		 if(start < end){
			 JSONObject key = array.getJSONObject(start);
             int low = start;
             int high = end;
             while(low < high){
                     while(low < high && isTimeFlag(key,array.getJSONObject(high),sortFiled)){
                             high--;
                     }
                     array.set(low, array.getJSONObject(high));
                     while(low < high && isTimeFlag(array.getJSONObject(low),key,sortFiled)){
                             low++;
                     }
                     array.set(high, array.getJSONObject(low));
             }
             array.set(low, key);
             quickSortArray(array,start,low-1,sortFiled);
             quickSortArray(array,low+1,end,sortFiled);
		 }
	}
	
	public static boolean isTimeFlag(JSONObject json1,JSONObject json2,String sortFiled){
		long time = DateUtil.getDifferTime(json1.getString(sortFiled), json2.getString(sortFiled), 1);
		
		if(time >=0){
			return true;
		}else{
			return false;
		}
	}
}
