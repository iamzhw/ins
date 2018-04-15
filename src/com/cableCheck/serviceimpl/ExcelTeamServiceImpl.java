package com.cableCheck.serviceimpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cableCheck.dao.ExcelTeamDao;
import com.cableCheck.service.ExcelTeamService;
import com.util.ExcelUtil;
import com.util.ExcelUtil_New;

import net.sf.json.JSONObject;

@SuppressWarnings("all")
@Service
public class ExcelTeamServiceImpl implements ExcelTeamService{

	
	private String staffId = "0";

	private Boolean bool = true;
	
	@Override
	public void excel(String path) {
		try {  
            String filepath = path;  
            ExcelTeamServiceImpl excelReader = new ExcelTeamServiceImpl(filepath);     
            // 对读取Excel表格内容测试  
            Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();  
            System.out.println("获得Excel表格的内容:");  
            for (int i = 1; i <= map.size(); i++) {  
                System.out.println(map.get(i)); 
                calExcelValue(map.get(i));
            }  
        } catch (FileNotFoundException e) {  
            System.out.println("未找到指定路径的文件!");  
            e.printStackTrace();  
        }catch (Exception e) {  
            e.printStackTrace();  
        } 
	}

	@Resource
	private ExcelTeamDao excelTeamDao;

    private Logger logger = LoggerFactory.getLogger(ExcelTeamServiceImpl.class);  
    private Workbook wb;  
    private Sheet sheet;  
    private Row row;  
    
    
    public ExcelTeamServiceImpl(){
    	
    }
    
    public ExcelTeamServiceImpl(String filepath) {  
        if(filepath==null){  
            return;  
        }  
        String ext = filepath.substring(filepath.lastIndexOf("."));  
        try {  
            InputStream is = new FileInputStream(filepath);  
            if(".xls".equals(ext)){  
                wb = new HSSFWorkbook(is);  
            }else if(".xlsx".equals(ext)){  
                wb = new XSSFWorkbook(is);  
            }else{  
                wb=null;  
            }  
        } catch (FileNotFoundException e) {  
            logger.error("FileNotFoundException", e);  
        } catch (IOException e) {  
            logger.error("IOException", e);  
        }  
    }  
      
    /** 
     * 读取Excel表格表头的内容 
     *  
     * @param InputStream 
     * @return String 表头内容的数组 
     * @author zengwendong 
     */  
    public String[] readExcelTitle() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        sheet = wb.getSheetAt(0);  
        row = sheet.getRow(0);  
        // 标题总列数  
        int colNum = row.getPhysicalNumberOfCells();  
        System.out.println("colNum:" + colNum);  
        String[] title = new String[colNum];  
        for (int i = 0; i < colNum; i++) {  
            // title[i] = getStringCellValue(row.getCell((short) i));  
            title[i] = row.getCell(i).getCellFormula();  
        }  
        return title;  
    }  
  
    /** 
     * 读取Excel数据内容 
     *  
     * @param InputStream 
     * @return Map 包含单元格数据内容的Map对象 
     * @author zengwendong 
     */  
    public Map<Integer, Map<Integer,Object>> readExcelContent() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        Map<Integer, Map<Integer,Object>> content = new HashMap<Integer, Map<Integer,Object>>();  
          
        sheet = wb.getSheetAt(0);  
        // 得到总行数  
        int rowNum = sheet.getLastRowNum();  
        row = sheet.getRow(0);  
        int colNum = row.getPhysicalNumberOfCells();  
        // 正文内容应该从第二行开始,第一行为表头的标题  
        for (int i = 1; i <= rowNum; i++) {  
            row = sheet.getRow(i);  
            int j = 0;  
            Map<Integer,Object> cellValue = new HashMap<Integer, Object>();  
            while (j < colNum) {  
                Object obj = getCellFormatValue(row.getCell(j));  
                cellValue.put(j, obj);  
                j++;  
            }  
            content.put(i, cellValue);  
        }  
        return content;  
    }  
  
    /** 
     *  
     * 根据Cell类型设置数据 
     *  
     * @param cell 
     * @return 
     * @author zengwendong 
     */  
    private Object getCellFormatValue(Cell cell) {  
        Object cellvalue = "";  
        if (cell != null) {  
            // 判断当前Cell的Type  
            switch (cell.getCellType()) {  
            case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC  
            case Cell.CELL_TYPE_FORMULA: {  
                // 判断当前的cell是否为Date  
                if (DateUtil.isCellDateFormatted(cell)) {  
                    // 如果是Date类型则，转化为Data格式  
                    // data格式是带时分秒的：2013-7-10 0:00:00  
                    // cellvalue = cell.getDateCellValue().toLocaleString();  
                    // data格式是不带带时分秒的：2013-7-10  
                    Date date = cell.getDateCellValue();  
                    cellvalue = date;  
                } else {// 如果是纯数字  
  
                    // 取得当前Cell的数值  
                    cellvalue = String.valueOf(cell.getNumericCellValue());  
                }  
                break;  
            }  
            case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING  
                // 取得当前的Cell字符串  
                cellvalue = cell.getRichStringCellValue().getString();  
                break;  
            default:// 默认的Cell值  
                cellvalue = "";  
            }  
        } else {  
            cellvalue = "";  
        }  
        return cellvalue;  
    }  
  
    
    
    /*{0=2.0, 1=南京市区, 2=政支汉中门班组, 3=网盈, 4=wynj2111, 5=张柳明, 6=1.806170077E9, 7=, 8=, 9=}*/
    public void calExcelValue(Map<Integer,Object> map){
    	String teamName = map.get(2)==null?"":map.get(2).toString();
    	String areaName = map.get(1)==null?"":map.get(1).toString();
    	if(StringUtils.isNotBlank(teamName)){
    		Map map_param = new HashMap();
    		map_param.put("teamName", teamName);
    		map_param.put("areaName", areaName);
    		Map map_teamId = excelTeamDao.getTeamId(map_param);
    		
    		String teamId = map_teamId==null?"":map_teamId.get("TEAM_ID").toString();
    		if(StringUtils.isNotBlank(teamId)){
    			updateLeader(map, teamId);
    			updateComRelation(map, teamId);
    		}else{
    			logger.debug("\"" + teamName + " \"  在装维班组表中找不到记录 ");
    		}
    	}else{
    		logger.debug("\"第" + map.get(0).toString() + "行的班组为空 ");
    	}
    }

    
    private void updateComRelation(Map<Integer, Object> map, String teamId) {
		String comName = map.get(3)==null?"":map.get(3).toString();
		if(StringUtils.isNotBlank(comName)){
			Map map_comId = excelTeamDao.getCompanyId(comName);
			String comId = map_comId==null?"":map_comId.get("COMPANY_ID").toString();
			if(StringUtils.isBlank(comId)){
				logger.debug("\"" + comName + " \"  在代维公司表中找不到记录 ");
				Integer comId_tem = excelTeamDao.addCompany(comName);
				comId  = comId_tem.toString();
			}
			
			Map map_para = new HashMap();
			map_para.put("comId", comId);
			map_para.put("teamId", teamId);
			Integer cou = excelTeamDao.ifExistRelation(map_para);
			if(cou > 0){
				//如果班组和代维公司的关联关系表中存在数据，则不新增
			}else{
				excelTeamDao.addRelation(map_para);
			}
		}else{
			logger.debug("\"第" + map.get(0).toString() + "行的 代维公司 为空 ");
		}
	}
    
    
    /**
     * 光网助手审核员
     * 赋权
     */
    public void grantCableRole(Map map){
    	//先判断该账户是否有"官网助手装维审核员"的权限，没有则新增
    	if(excelTeamDao.isExistCableRole(map)>0){
    		logger.debug("该账户已存在官网助手审核员的权限");
    	}else{
    		excelTeamDao.insertCableRole(map);	
    	}
    }
    
    
	private void updateLeader(Map<Integer, Object> map, String teamId) {
		String staffNo = map.get(4)==null?"":map.get(4).toString();
		if(StringUtils.isNotBlank(staffNo)){
			Map<String, String> map_para = new HashMap<String, String>();
			map_para.put("staffNo", staffNo);
			map_para.put("teamId", teamId);
			Map map_res = excelTeamDao.getStaffId(map_para);
			if(map_res!=null && map_res.size()>0){
				//update审核接单岗之前，需要将班组下的审核接单岗置为空
				map_para.put("staffNo", "");
				map_para.put("upType", "0");
				excelTeamDao.updateLeader(map_para);
				
				//update
				map_para.put("staffNo", staffNo);
				map_para.put("upType", "1");
				excelTeamDao.updateLeader(map_para);
			}else{
				//默认staffNo账户在装维人员表中是存在账户的
				//add
				map_para.put("teamIdNew", teamId);
				excelTeamDao.addStaffZw(map_para);
			}
			
			//给账户赋光网助手审核员的权限
			grantCableRole(map_para);
			
		}else{
			logger.debug("\"第" + map.get(0).toString() + "行的 审核员综调工号 为空 ");
		}
	}
    
    public static void main(String[] args) {  
        try {  
            String filepath = "C:\\Users\\Zhufc\\Desktop\\team.xlsx";  
            ExcelTeamServiceImpl excelReader = new ExcelTeamServiceImpl(filepath);     
            // 对读取Excel表格内容测试  
            Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();  
            System.out.println("获得Excel表格的内容:");  
            for (int i = 1; i <= map.size(); i++) {  
                System.out.println(map.get(i)); 
            }  
        } catch (FileNotFoundException e) {  
            System.out.println("未找到指定路径的文件!");  
            e.printStackTrace();  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

    
    /***********************************************页面导入************************************************************/
	@Override
	public Object importExcel(HttpServletRequest request, MultipartFile file) {
		JSONObject result = new JSONObject();
		String message = "导入成功";
		result.put("status", true);
		try {
			String staffId = request.getSession().getAttribute("staffId").toString();
			this.staffId = staffId;
//            String filepath = "";
//            ExcelTeamServiceImpl excelReader = new ExcelTeamServiceImpl(filepath);     
////           对读取Excel表格内容测试  
//            Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();  
//			for (int i = 1; i <= map.size(); i++) {
//              calExcelValue(map.get(i));
//			}     
            
			
			ExcelUtil_New parse = new ExcelUtil_New(file.getInputStream(),file.getOriginalFilename());
			List<List<String>> datas = parse.getDatas(6);
			
			///插入importLog日志之前，先删除上次的记录
			Map map_p = new HashMap();
			map_p.put("staffId",staffId);
			excelTeamDao.deleteImportLog(map_p);
            
			for (int i = 0; i < datas.size(); i++) {
				this.bool=true;//每次执行逻辑之前，先重置全局变量
                calExcelValue_jsp(datas.get(i));
                if(this.bool){
                	insertImportLog(datas.get(i),0,"更新成功");
                }
            }
        } catch (FileNotFoundException e) {  
            System.out.println("未找到指定路径的文件!");  
            e.printStackTrace();  
            message = "未找到指定路径的文件!";
            result.put("status", false);
        }catch (Exception e) {  
            e.printStackTrace();  
            message="文件解析失败";
            result.put("status", false);
        }
		result.put("message", message);
		return result;
	}
	
	/*{0=2.0, 1=南京市区, 2=政支汉中门班组, 3=网盈, 4=wynj2111, 5=张柳明, 6=1.806170077E9, 7=, 8=, 9=}*/
    public void calExcelValue_jsp(List<String> map){
    	String teamName = map.get(2)==null?"":map.get(2).toString();
    	String areaName = map.get(1)==null?"":map.get(1).toString();
    	if(StringUtils.isNotBlank(teamName)){
    		Map map_param = new HashMap();
    		map_param.put("teamName", teamName);
    		map_param.put("areaName", areaName);
    		Map map_teamId = excelTeamDao.getTeamId(map_param);
    		
    		String teamId = map_teamId==null?"":map_teamId.get("TEAM_ID").toString();
    		if(StringUtils.isNotBlank(teamId)){
    			updateLeader_jsp(map, teamId);
    			updateComRelation_jsp(map, teamId);
    		}else{
    			logger.debug("\"" + teamName + " \"  在装维班组表中找不到记录 ");
    			insertImportLog(map,1,"光网助手中不存在该班组");
    			this.bool = false;
    		}
    	}else{
    		logger.debug("\"第" + map.get(0).toString() + "行的班组为空 ");
    		insertImportLog(map,1,"班组名称为空");
    		this.bool = false;
    	}
    }
    
    
	private void updateLeader_jsp(List<String> map, String teamId) {
		String staffNo = map.get(4)==null?"":map.get(4).toString();
		String staffName = map.get(4)==null?"":map.get(5).toString();
		if(StringUtils.isNotBlank(staffNo)){
			
			
			
			/**
			 * 2018年1月25日14:26:53 
			 * 根据账户判断装维人员表中是否有该账户
			 */
			Map map_exist = new HashMap();
			map_exist.put("staffNo",staffNo);
			Map map_zw = excelTeamDao.isZwExists(map_exist);
			if(map_zw!=null && map_zw.size()>0){
				
			}else{
				insertImportLog(map,2,"账户:"+staffNo+",在装维人员表中不存在");
				this.bool = false;
				return;
			}
			
			/**
			 * 2018年1月25日14:26:18 如果excel的账户姓名不为空，
			 * 则需要对账户和姓名做一致性的判断，如果不匹配则返回提示
			 */
			if(StringUtils.isNotBlank(staffName)){
				Map<String,String> map_staff = new HashMap<String, String>();
				map_staff.put("staffNo", staffNo);
				map_staff.put("staffName", staffName);
				Map map_staff_result = excelTeamDao.ifNoWithName(map_staff);
				if(map_staff_result!=null && map_staff_result.size()>0){
					
				}else{
					insertImportLog(map,2,"账户 '"+staffNo+"' 与姓名 '"+staffName+"' 不匹配");
					this.bool = false;
					return;
				}
			}
			
			Map<String, String> map_para = new HashMap<String, String>();
			map_para.put("staffNo", staffNo);
			map_para.put("teamId", teamId);
			Map map_res = excelTeamDao.getStaffId(map_para);//判断班组下的是否存在该账户
			if(map_res!=null && map_res.size()>0){
//				//update审核接单岗之前，需要将班组下的审核接单岗置为空
//				map_para.put("staffNo", "");
//				map_para.put("upType", "0");
//				excelTeamDao.updateLeader(map_para);
				
				//update
				map_para.put("staffNo", staffNo);
				map_para.put("upType", "1");
				excelTeamDao.updateLeader(map_para);
			}else{
				
				//前面已经做了判断，故而账户肯定存在，不再判断
				//存在账户则执行插入操作
				//add
				map_para.put("teamIdNew", teamId);
				excelTeamDao.addStaffZw(map_para);
			}
			
			//给账户赋光网助手审核员的权限
			grantCableRole(map_para);
			
		}else{
			logger.debug("\"第" + map.get(0).toString() + "行的 审核员综调工号 为空 ");
			insertImportLog(map,2,"审核员综调工号为空");
			this.bool = false;
		}
	}
	
	private void updateComRelation_jsp(List<String> map, String teamId) {
		String comName = map.get(3)==null?"":map.get(3).toString();
		if(StringUtils.isNotBlank(comName)){
			Map map_comId = excelTeamDao.getCompanyId(comName);
			String comId = map_comId==null?"":map_comId.get("COMPANY_ID").toString();
			if(StringUtils.isBlank(comId)){
				logger.debug("\"" + comName + " \"  在代维公司表中找不到记录 ");
				Integer comId_tem = excelTeamDao.addCompany(comName);
//				comId  = comId_tem.toString();
				
				Map map_comId_res = excelTeamDao.getCompanyId(comName);
				//默认网盈
				comId = map_comId_res==null?"1023":map_comId_res.get("COMPANY_ID").toString();	
			}
			
			Map map_para = new HashMap();
			map_para.put("comId", comId);
			map_para.put("teamId", teamId);
			Integer cou = excelTeamDao.ifExistRelation(map_para);
			if(cou > 0){
				//如果班组和代维公司的关联关系表中存在数据，则不新增
			}else{
				excelTeamDao.addRelation(map_para);
			}
		}else{
			logger.debug("\"第" + map.get(0).toString() + "行的 代维公司 为空 ");
			insertImportLog(map,2,"代维公司为空");
			this.bool = false;
		}
	}

	
	/**
	 * 导入的时候，插入log表
	 * @param map
	 * @param failStatus
	 * @param failDesc
	 */
	public void insertImportLog(List<String> map,int status,String failDesc) {
		String areaName = map.get(1)==null?"":map.get(1).toString();
		String teamName = map.get(2)==null?"":map.get(2).toString();
		String comName = map.get(3)==null?"":map.get(3).toString();
		String staffNo = map.get(4)==null?"":map.get(4).toString();
		String staffName = map.get(5)==null?"":map.get(5).toString();
		//String tel = map.get(5)==null?"":map.get(5).toString();
		Map map_param = new HashMap();
		map_param.put("areaName",areaName);//地市
		map_param.put("teamName",teamName);//班组名称
		map_param.put("comName",comName);//代维公司名称
		map_param.put("staffNo",staffNo);//人员账户
		map_param.put("staffName",staffName);//人员姓名
//		map_param.put("tel",tel);//手机号/
		map_param.put("staffId",this.staffId);//create_staff
		map_param.put("failDesc",failDesc);//失败原因
		map_param.put("status",status);//成功与否，状态
		excelTeamDao.insertImportLog(map_param);
	}
	
	
	/***********************************************页面导入************************************************************/	
}
