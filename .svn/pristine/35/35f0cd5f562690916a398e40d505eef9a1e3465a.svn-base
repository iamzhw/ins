package com.cableInspection.serviceimpl;

import icom.system.dao.CableInterfaceDao;
import icom.system.dao.CalculateNormalDao;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.net.ftp.FtpClient;
import util.dataSource.SwitchDataSourceUtil;

import com.cableInspection.dao.DeptDao;
import com.cableInspection.dao.FtpResolveDao;
import com.cableInspection.dao.LxxjCityDao;
import com.cableInspection.service.FtpResolveService;
import com.system.constant.AppType;
import com.system.constant.SysSet;
import com.util.FtpBean;
import com.util.FtpUtil;
import com.util.MapDistance;
import com.util.Triangle;

import edu.emory.mathcs.backport.java.util.Collections;


@SuppressWarnings("all")
@Service
public class FtpResolveServiceImpl implements FtpResolveService {
	
	private static FTPClient ftp;
	
	private static List<Map> resultList =new ArrayList();
	
	@Resource
	private DeptDao deptdao;
	
	@Resource
	private FtpResolveDao ftpdao;
	
	@Resource
	private LxxjCityDao lxxjCityDao;
	
	@Resource
	private CableInterfaceDao cableInterfaceDao;
	
	@Resource
	private CalculateNormalDao caldao;
	
	private static Logger logger=Logger.getLogger(FtpResolveServiceImpl.class);
	private static File[] ftpfiles;
	
	@Override
	public void solveFTP() {
		FtpBean ftp = new FtpBean();
		//FTP设置
		String path = "/export/home/ftpuser/grid_account";
		ftp.setIpAddr("132.228.198.143");
		ftp.setPath(path);
		ftp.setPwd("ftp123!@#");
		ftp.setUserName("ftpuser");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateName = "grid_user_"+sdf.format(new Date())+".txt";
		String localUrl = "excelFiles/";
		try {
			//连接FTP
			connectFtp(ftp);
			startDown(ftp,localUrl, ftp.getPath(),dateName);
			//关闭FTP
			closeFtp();
			//解析TXT更新数据
			 resolveTxt(localUrl);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<String> resolveTxt(String dirUrl){
		File dir = new File(dirUrl);
		String[] name = null; 
		String text =null;
		String[] time = null;
		List<String> list = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateName = "grid_user_"+sdf.format(new Date())+".txt";
        int num=0;
        InputStreamReader read=null;
        BufferedReader br=null;
        //处理人员
		ftpdao.deleteAllData();
		try {
			read = new InputStreamReader(new FileInputStream(dirUrl+dateName),"utf-8");
			br = new BufferedReader(read);
			while((text = br.readLine())!=null){
					list.add(text);
					num++;
					if(num==1000){	
					solveListForText(list);
					num=0;
					list.clear();
				    }
			}
			solveListForText(list);
			read.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (read != null) {
					read.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	private void solveListForText(List<String> list){
		List<Map> resultList = new ArrayList();
		List<Map> depttList = new ArrayList();
		Set<Map> set = new HashSet();
		String[] s = null;
		for (String str : list) {
			Map deptMap = new HashMap();
			Map map = new HashMap();
			s=str.split("\\^\\#\\^",-1);
			if(s.length>=13&&!s[0].replace(" ", "").equals("无锡")){
				map.put("area",s[0].replace(" ", "") );//地市
				deptMap.put("area",s[0].replace(" ", ""));
				map.put("dept_no",s[1].replace(" ", ""));//网格编码
				deptMap.put("DEPT_NO",s[1].replace(" ", ""));
				map.put("dept_code",s[2].replace(" ", "") );//网格ID
				map.put("dept_name",s[3].replace(" ", "") );//网格名称
				deptMap.put("DEPT_NAME",s[3].replace(" ", "") );
				map.put("staff_name",s[4].replace(" ", "") );//用户姓名
				map.put("staff_no",s[5].replace(" ", "") );//用户帐号
				map.put("tel",s[6].replace(" ", "") );//手机号码
				map.put("staff_type",s[7].replace(" ", "") );//用户类型
				map.put("staff_role",s[8].replace(" ", "") );//用户角色
				map.put("specialty",s[9].replace(" ", "") );//专业
				map.put("son_specialty",s[10].replace(" ", "") );//子专业
				map.put("skill",s[11].replace(" ", "") );//技能
				map.put("diploma",s[12].replace(" ", "") );//证书
				resultList.add(map);
				depttList.add(deptMap);					
			}

		}
		
		set.addAll(depttList);
		Iterator<Map> i = set.iterator();
		String[] area = null;
		int count=0;
		String son_area_id=null;
		String name=null;
		String no=null;
		Map rs = new HashMap();
	    //新增没有的班组
		while(i.hasNext()){
			rs = i.next();
			name=rs.get("DEPT_NAME").toString();
			no=rs.get("DEPT_NO").toString();
			count = deptdao.checkDeptExist(no);
			if(count==0){
				area = name.split("-");
				rs.put("AREA_ID", deptdao.getAreaIdByName(rs.get("area").toString()));
				if(null==son_area_id||"".equals(son_area_id)||area.length<2){
					rs.put("SON_AREA_ID",deptdao.getAreaIdByName(rs.get("area")+"市区"));
				}else{
					son_area_id = deptdao.getAreaIdByName(area[1]);
					rs.put("SON_AREA_ID", son_area_id);
				}
				rs.put("CREATE_STAFF", "3329");
				if(!"".equals(rs.get("DEPT_NO"))&&null!=rs.get("DEPT_NO")){
					deptdao.insert(rs);
				}	
			}
		}
		//插入中间表{
		ftpdao.addFtpData(resultList);
		//修改区域ID字段
		ftpdao.updateData();
		//修正区域ID
		ftpdao.correctSonAreaId();
		//插入人员
		ftpdao.addNewStaff();
		//修改STAFFID
		ftpdao.updateStaffId();
		//删除网格和人员关系
		ftpdao.deleteDeptRelationship();
		//增加新的人员和网格关系
		ftpdao.addDeptRelationship();
//		//删除权限
//		ftpdao.deleteRoleByStaffId();
		//添加权限
		ftpdao.addRoles();
		ftpdao.addSoftRole();
	}
	
	
	private void startDown(FtpBean f,String localBaseDir,String remoteBaseDir,String dateName) throws Exception{
        if (FtpUtil.connectFtp(f)) {
            try { 
            	File localDir = new File(localBaseDir);
                if(!localDir.isDirectory()){
                	localDir.mkdir();
                }
            	FTPFile[] files = null; 
                boolean changedir = ftp.changeWorkingDirectory(remoteBaseDir);
                if (changedir) { 
                    ftp.setControlEncoding("GBK"); 
                    files = ftp.listFiles(); 
                    for (int i = 0; i < files.length; i++) { 
                        try{ 
                        	if(files[i].getName().equals(dateName)){
                        		downloadFile(files[i], localBaseDir, remoteBaseDir); 
                        	}
                        }catch(Exception e){ 
                            logger.error(e); 
                            logger.error("<"+files[i].getName()+">下载失败"); 
                        } 
                    } 
                } 
            } catch (Exception e) { 
                logger.error(e); 
                logger.error("下载过程中出现异常"); 
            } 
        }else{
            logger.error("链接失败！");
        }
    }
	
	private void downloadFile(FTPFile ftpFile, String relativeLocalPath,String relativeRemotePath) { 
        if (ftpFile.isFile()) {
            if (ftpFile.getName().indexOf("?") == -1) { 
                OutputStream outputStream = null; 
                try { 
                    File locaFile= new File(relativeLocalPath+ ftpFile.getName());
                    //判断文件是否存在，存在则返回 
                    if(locaFile.exists()){ 
                        return; 
                    }else{ 
                        outputStream = new FileOutputStream(relativeLocalPath+ ftpFile.getName()); 
                        ftp.retrieveFile(ftpFile.getName(), outputStream); 
                        outputStream.flush(); 
                        outputStream.close(); 
                    } 
                } catch (Exception e) { 
                    logger.error(e);
                } finally { 
                    try { 
                        if (outputStream != null){ 
                            outputStream.close(); 
                        }
                    } catch (IOException e) { 
                       logger.error("输出文件流异常"); 
                    } 
                } 
            } 
        }
    } 
	
	 /**
     * 获取ftp连接
     * @param f
     * @return
     * @throws Exception
     */
    public static boolean connectFtp(FtpBean f) throws Exception{
        ftp=new FTPClient();
        boolean flag=false;
        int reply;
        if (f.getPort()==null) {
            ftp.connect(f.getIpAddr(),21);
        }else{
            ftp.connect(f.getIpAddr(),f.getPort());
        }
        ftp.login(f.getUserName(), f.getPwd());
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
              ftp.disconnect();      
              return flag;      
        }      
        ftp.changeWorkingDirectory(f.getPath()); 
        flag = true;      
        return flag;
    }
    
    /**
     * 关闭ftp连接
     */
    public static void closeFtp(){
        if (ftp!=null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	@Override
	public void solveDatFile() {
		FtpBean ftp = new FtpBean();
		//FTP设置
		String path = "/";
		ftp.setIpAddr("132.228.198.77");
		ftp.setPath(path);
		ftp.setPwd("local_m_pwd");
		ftp.setUserName("local_m_user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());
		String device_grid = "oss_device_grid_"+date+".dat";
		String dim_room = "oss_dim_room_"+date+".dat";
		String dim_station = "oss_dim_station_"+date+".dat";
		String dim_mainten_grid = "oss_dim_mainten_grid_"+date+".dat";
		String line_inspect_grid = "line_inspect_grid_"+date+".dat";
		String localUrl = "excelFiles/";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date yestoday = cal.getTime();
		String yestodayFile = "oss_device_grid_"+sdf.format(yestoday)+".dat";
		String yestodayLine_inspect = "line_inspect_grid_"+sdf.format(yestoday)+".dat";
		File file = new File(localUrl+yestodayFile);
		File lineFile = new File(localUrl+yestodayLine_inspect);
		try {
			if(file.isFile()){
				file.delete();
		    }
			if(lineFile.isFile()){
				lineFile.delete();
		    }
			//连接FTP
			connectFtp(ftp);
			
//			startDown(ftp,localUrl, ftp.getPath(),device_grid);
			startDown(ftp,localUrl, ftp.getPath(),line_inspect_grid);
//			startDown(ftp,localUrl, ftp.getPath(),dim_room);
//			startDown(ftp,localUrl, ftp.getPath(),dim_station);
//			startDown(ftp,localUrl, ftp.getPath(),dim_mainten_grid);
			//关闭FTP
			closeFtp();
			//解析dat
//			 resolveDat(localUrl,dim_mainten_grid,4);
//			 resolveDat(localUrl,device_grid,1);
			 resolveDat(localUrl,line_inspect_grid,5);
//			 resolveDat(localUrl,dim_room,2);
//			 resolveDat(localUrl,dim_station,3);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void resolveDat(String dir,String dateName,int i){
		
		String text =null;
		List<String> list = new ArrayList();
		File file = new File(dir+dateName);
		int num=0;
		//删除中间表
		ftpdao.delete_A_POINT_DEPT();
		InputStreamReader read =null;
		BufferedReader br=null;
		try {
			if(file.isFile()){
				read = new InputStreamReader(new FileInputStream(file),"GB2312");
				br = new BufferedReader(read);
				while((text = br.readLine())!=null){
					list.add(text);
					num++;
					if(num==1000){	
						solveListForDat(list,i);
						num=0;
						list.clear();
				    }
				}
				solveListForDat(list,i);
				br.close();
				read.close();
//				//去重
//				ftpdao.deleteEchoData();
				//调用存储过程
				ftpdao.import_point_dept();
				//重置序列
				ftpdao.resetSequence("SEQ_A_POINT_DEPT");
//				//删除关系表要插入的point
//				ftpdao.deletePointDeptByPointNo();
//				//插入设备与网格关系表
//				ftpdao.addEqpToDept();
//				//补齐设备ID和网格ID
//				ftpdao.updatePointIdAndDeptId();
			}
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}finally{
			try {
				if (read != null) {
					read.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		solveListForDat(list,i);
	}
	
	private void solveListForDat(List<String> list,int num){
		String[] s = null;
		String[] title = list.get(0).split("\\^\\#\\^",-1);
		Map deptMap = new HashMap();
		Map map = new HashMap();
		for (int i =1;i<list.size();i++) {
			s = list.get(i).split("\\^\\#\\^",-1);
			if(num==4){
//				map.put(key, value);
//				维护网格ID，维护网格编码，维护网格名称，维护网格类型，维护责任人，联系方式，本地网ID，区县ID，本地网分区ID，帐期，地市
			}else if (num==1){
				//设备逻辑规格名称，逻辑设备ID，逻辑设备名称，物理设备名称，OSS对应网管字段，维护网格名称，维护网格ID，经度信息，纬度信息，专业，地市。
				if(s.length<12){
					continue;	
				}
				map.put("phy_eqp_id", s[1].replace(" ", ""));
				map.put("point_name", s[2].replace(" ", ""));
				map.put("point_no", s[3].replace(" ", ""));
				map.put("address", s[4].replace(" ", ""));
				map.put("dept_name", s[6].replace(" ", ""));
				map.put("dept_no", s[7].replace(" ", ""));
				map.put("longitude", s[8].replace(" ", ""));
				map.put("latitude", s[9].replace(" ", ""));
				map.put("area", s[11].replace(" ", ""));
				if(map.get("phy_eqp_id").equals("")
								||map.get("point_name").equals("")
								||map.get("point_no").equals("")
								||map.get("dept_name").equals("")
								||map.get("dept_no").equals("")
								||map.get("area").equals("")){
					continue;
				}
//				map.put("dept_id", deptMap.get("DEPT_ID"));
//				map.put("area_id", deptMap.get("AREA_ID"));
//				map.put("son_area_id", deptMap.get("SON_AREA_ID"));
//				if(ftpdao.ifPointExists(map)==0){
//					ftpdao.addNewPoint(map);
//				}
				if(null!=ftpdao.getDeptIdByName(map)){
					deptMap = ftpdao.getDeptIdByName(map);
					map.put("dept_id", deptMap.get("DEPT_ID"));
				}else{
					map.put("dept_id",0);
				}
				if(null!=ftpdao.getPointIdByNo(map)){
					map.put("point_id",ftpdao.getPointIdByNo(map));
				}else{
					map.put("point_id",0);
				}
				if(ftpdao.ifPointExistsForDept(map)==0){
//					ftpdao.addEqpToDept(map);
					ftpdao.addTest(map);
				}
			}else if(num==5){
				if(s.length<6){
					continue;	
				}
				map.put("phy_eqp_id", s[0].replace(" ", ""));
				map.put("point_no", s[1].replace(" ", ""));
				map.put("type", s[2].replace(" ", ""));
				map.put("dept_no", s[3].replace(" ", ""));
				map.put("dept_name", s[4].replace(" ", ""));
				map.put("area", s[5].replace(" ", ""));
				if(map.get("phy_eqp_id").equals("")
								||map.get("point_no").equals("")
								||map.get("dept_name").equals("")
								||map.get("dept_no").equals("")){
					continue;
				}
				ftpdao.add_A_POINT_DEPT(map);
			}
		}
	}

	@Override
	public void personKPI() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String myToday = sdf2.format(new Date());
		StringBuffer content=new StringBuffer();
			
		List<Map<String,String>> jyh_no = new ArrayList<Map<String,String>>();
		//个人
		try {
			SwitchDataSourceUtil.setCurrentDataSource("orcl153");
			jyh_no=lxxjCityDao.queryjyh1();
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		}
		content=getStringLine(jyh_no,2,myToday);
		writer(content,"dwry_xjdwl_data_"+today+".txt");
		//班组长
		try {
			SwitchDataSourceUtil.setCurrentDataSource("orcl153");
			jyh_no=lxxjCityDao.queryjyh2();
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		}
		content=getStringLine(jyh_no,4,myToday);
		writer(content,"bzz_xjdwl_data_"+today+".txt");
		//班组
		try {
			SwitchDataSourceUtil.setCurrentDataSource("orcl153");
			jyh_no=lxxjCityDao.queryjyh2();
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		}
		content=getStringLine(jyh_no,6,myToday);
		writer(content,"bzjt_xjdwl_data_"+today+".txt");
		//网格
		try {
			SwitchDataSourceUtil.setCurrentDataSource("orcl153");
			jyh_no=lxxjCityDao.queryjyh4();
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		}
		content=getStringLine(jyh_no,8,myToday);
		writer(content,"dwgsjt_xjdwl_data_"+today+".txt");
		//网格
		try {
			SwitchDataSourceUtil.setCurrentDataSource("orcl153");
			jyh_no=lxxjCityDao.queryjyh6();
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			SwitchDataSourceUtil.clearDataSource();
		}
		content=getStringLine(jyh_no,9,myToday);
		writer(content,"wgzyjt_xjdwl_data_"+today+".txt");
	}
	
	private static void writer(StringBuffer content,String fileName){
//		PrintWriter pw = null;
		FtpBean myftp = new FtpBean();
		String path = "/chengbao_kpi_view_data";
		myftp.setIpAddr("132.228.198.77");
		myftp.setPath(path);
		myftp.setPwd("jyh_JScloud!@06");
		myftp.setUserName("jyh");
		myftp.setPort(21);
		InputStream is=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		try {
			//连接FTP
			connectFtp(myftp);
			boolean hasDir=ftp.changeWorkingDirectory(path+"/"+today);
			if(!hasDir){
				ftp.makeDirectory(path+"/"+today);
				ftp.changeWorkingDirectory(path+"/"+today);
			}
			ftp.setControlEncoding("utf-8");
			is = new ByteArrayInputStream(content.toString().getBytes());
			ftp.setFileType(ftp.BINARY_FILE_TYPE);
			ftp.storeFile(fileName,is);
			is.close();
//			OutputStream outs= ftp.appendFileStream(fileName);
//			OutputStreamWriter out = new OutputStreamWriter(outs,"utf-8");
//			pw = new PrintWriter(out,true);
//			pw.write(content.toString());
//			pw.flush();
//			pw.close();
//			outs.flush();
//			outs.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		//关闭FTP
		closeFtp();	
	}
	
	//非关键点到位计算
	public boolean arrivalNormalPoints(Map normal,List<Map> list2) {
		// 到位数量
		boolean flag = false;
		// 应到设备
		// int sums1=list1.size();
			Double px1 = Double.parseDouble(normal.get("LATITUDE").toString());
			Double py1 = Double.parseDouble(normal.get("LONGITUDE").toString());
			
			// 设备距离轨迹起点距离
			Double aa1 = 0d;
			// 设备距离轨迹终点距离
			Double aa2 = 0d;
			// 4分钟轨迹线段长度
			Double aa3 = 0d;
			// 轨迹距离关键点距离
			Double jul = 0d;
			//
			int inaccuracy = 50;// 签到误差距离
			int locate_span = Integer.valueOf(SysSet.CONFIG.get(AppType.LXXJ)
					.get(SysSet.LOCATE_SPAN));// 定位时间间隔
			int mid=0;
			
			Double px2 = 0.0;
			Double py2 = 0.0;
			Double px3 = 0.0;
			Double py3 = 0.0;
			for (int i = 0; i < list2.size() - 1; i++) {
				px2=Double.parseDouble(list2.get(i).get("LATITUDE").toString());
				py2=Double.parseDouble(list2.get(i).get("LONGITUDE").toString());
				px3=Double.parseDouble(list2.get(i + 1).get("LATITUDE").toString());
				py3=Double.parseDouble(list2.get(i + 1).get("LONGITUDE").toString());
				//如果上传两个连续相同的点，则继续遍历下一个点
				if(px2==px3&&py2==py3)
				{
					continue;
				}
				aa1 = MapDistance.getDistance(px1, py1,px2,py2);
				aa2 = MapDistance.getDistance(px1, py1,px3,py3);
				aa3 = MapDistance.getDistance(px2,py2,px3,py3);

				// 判断时速有没有超过20
				if ((aa3 / 1000) / (locate_span / 3600) > 20) {
					//continue;
				}
				
				// 若其中有一边小于50米，则改点到位
				if (aa1 <= inaccuracy || aa2 <= inaccuracy) {
					flag = true;
					break;
				}
				/*if ((aa1 > aa2 && aa1 > aa3) || (aa2 > aa1 && aa2 > aa3)) {
					// 距离关键点距离jul为最短边 aa2或aa1上面已判断过都大于50米
				} */
				if(aa3 < 0000000.1 || (aa2 * aa2 + aa3 * aa3 - aa1 * aa1)/(2*aa2*aa3)<0 ||
						(aa1 * aa1 + aa3 * aa3 - aa2 * aa2)/(2*aa1*aa3)<0)
				{
					continue;
				}else {
					jul = new Triangle(aa1, aa2, aa3).getArea() * 2 / aa3;
					if (jul < inaccuracy) {
						flag = true;
						break;
					}
				}
			}
		return flag;
	}
	
	private StringBuffer getStringLine(List<Map<String,String>> jyh_no,int type,String date){
		StringBuffer content = new StringBuffer();
		List<Map<String,Object>> tasks =new ArrayList<Map<String,Object>>();
		String sep="#_#";
		int rate=0;
		double num=0.0;
		int signPoints=0;
		int points=0;
		List<Map> allPoint = null;
		Map taskTime = new HashMap();
		List<Map> uploadPoints=null;
		List<Map<String,String>> bz=null;
		int avg=0;
		int person=0;
		for (Map<String,String> map : jyh_no) {
			if(type!=2&&type!=4){
				switch (type) {
				case 6:
					try {
						SwitchDataSourceUtil.setCurrentDataSource("orcl153");
						if(map.get("JYH_ACCOUNT")!=null){
							bz=lxxjCityDao.queryjyh3(map.get("JYH_ACCOUNT"));
						}
						SwitchDataSourceUtil.clearDataSource();
					} catch (Exception e) {
						SwitchDataSourceUtil.clearDataSource();
					}
					break;
				case 8:
					try {
						SwitchDataSourceUtil.setCurrentDataSource("orcl153");
						if(map.get("JYH_ACCOUNT")!=null){
							bz=lxxjCityDao.queryjyh5(map.get("JYH_ACCOUNT"));
						}
						SwitchDataSourceUtil.clearDataSource();
					} catch (Exception e) {
						SwitchDataSourceUtil.clearDataSource();
					}
					break;
				case 9:
					try {
						SwitchDataSourceUtil.setCurrentDataSource("orcl153");
						if(map.get("JYH_ACCOUNT")!=null){
							bz=lxxjCityDao.queryjyh7(map.get("JYH_ACCOUNT"));
						}
						SwitchDataSourceUtil.clearDataSource();
					} catch (Exception e) {
						SwitchDataSourceUtil.clearDataSource();
					}
					break;
				default:
					break;
				}
				if(bz!=null&&bz.size()!=0){
					for (Map<String,String> bzz : bz) {
						tasks.clear();
						if(map.get("JYH_ACCOUNT")!=null){
							tasks=lxxjCityDao.getTaskByJYHNo(map);
						}
						if(tasks==null||tasks.size()==0){
							rate=0;
						}else{
							for (Map<String, Object> task : tasks) {
//								allPoint = lxxjCityDao.allPoints(task);
//								taskTime.put("staffId",task.get("INSPECTOR"));
//								uploadPoints = caldao.getUploadForTaskPoint(taskTime);
//								for (int i=0;i<allPoint.size();i++ ) {
//									if(null != allPoint.get(i).get("POINT_TYPE_ID") && !"-1".equals(allPoint.get(i).get("POINT_TYPE_ID").toString())){
//									}else{
//										if(allPoint.get(i).get("NORMALCOMPLETED").toString().equals("0")){
//											taskTime.put("LONGITUDE", allPoint.get(i).get("LONGITUDE").toString());
//											taskTime.put("LATITUDE", allPoint.get(i).get("LATITUDE").toString());
//											if( arrivalNormalPoints(taskTime,uploadPoints)){
//												signPoints++;
//											}
//										}else if(allPoint.get(i).get("NORMALCOMPLETED").toString().equals("1")){
//											signPoints++;
//										}
//									}
//								}
								points+=(Integer.parseInt(task.get("NORMALCOUNT").toString())+Integer.parseInt(task.get("MAINCOUNT").toString()));
								signPoints+=Integer.parseInt(task.get("SIGNINCOUNT").toString())+Integer.parseInt(task.get("SIGNNORMAL").toString());
							}
							points=points==0?1:points;
							num=(double)signPoints/(double)points*100;
							rate=(int) Math.round(num);
							person++;
							avg+=rate;
						}
					}
				}
				rate=person==0?0:(rate=avg/person);
				person=0;
			}else{
				tasks.clear();
				if(map.get("JYH_ACCOUNT")!=null){
					tasks=lxxjCityDao.getTaskByJYHNo(map);
				}
				if(tasks==null||tasks.size()==0){
					rate=0;
				}else{
					for (Map<String, Object> task : tasks) {
//						allPoint = lxxjCityDao.allPoints(task);
//						taskTime.put("staffId",task.get("INSPECTOR"));
//						uploadPoints = caldao.getUploadForTaskPoint(taskTime);
//						for (int i=0;i<allPoint.size();i++ ) {
//							if(null != allPoint.get(i).get("POINT_TYPE_ID") && !"-1".equals(allPoint.get(i).get("POINT_TYPE_ID").toString())){
//							}else{
//								if(allPoint.get(i).get("NORMALCOMPLETED").toString().equals("0")){
//									taskTime.put("LONGITUDE", allPoint.get(i).get("LONGITUDE").toString());
//									taskTime.put("LATITUDE", allPoint.get(i).get("LATITUDE").toString());
//									if( arrivalNormalPoints(taskTime,uploadPoints)){
//										signPoints++;
//									}
//								}else if(allPoint.get(i).get("NORMALCOMPLETED").toString().equals("1")){
//									signPoints++;
//								}
//							}
//						}SIGNNORMAL
						points+=(Integer.parseInt(task.get("NORMALCOUNT").toString())+Integer.parseInt(task.get("MAINCOUNT").toString()));
						signPoints+=Integer.parseInt(task.get("SIGNINCOUNT").toString())+Integer.parseInt(task.get("SIGNNORMAL").toString());
					}
					points=points==0?1:points;
					num=(double)signPoints/(double)points*100;
					rate=(int) Math.round(num);
				}
			}
			content.append(map.get("JYH_ACCOUNT")+sep+rate+sep+type+sep+date+"\n");
		}
		return content;
	}
}
 
