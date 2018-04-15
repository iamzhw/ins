package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 此方法只适用于ftp,不适用于sftp方式
 * @author tw
 *
 */

public class FTPUploadJYH {
	private FTPClient ftp;
	private String path;
	private String addr;
	private int port;
	private String userName;
	private String pwd;
	
	
	
	public FTPClient getFtp() {
		return ftp;
	}

	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public FTPUploadJYH(String path,String addr,int port,String userName,String pwd){
		this.path=path==null?"/gwzs_service_resultInfo/":path;
		this.addr=addr==null?"132.228.198.77":addr;
		this.port=port==0?21:port;
		this.userName=userName==null?"jyh":userName;
		this.pwd=pwd==null?"jyh_123":pwd;
		
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean connect() throws Exception {
		boolean result=false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, port);
		ftp.login(userName, pwd);
		
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply =ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(reply)){
			ftp.disconnect();
			return result;
		}
		ftp.changeWorkingDirectory(path);
		result=true;
		return result;
	}
	
	public void upload(File file) throws Exception{
		if(file.isDirectory()){
			ftp.makeDirectory(file.getName());
			ftp.changeWorkingDirectory(file.getName());
			String[] files=file.list();
			for (int i = 0; i < files.length; i++) {
				File file1=new File(file.getPath()+"\\"+files[i]);
				if(file1.isDirectory()){
					upload(file1);
					ftp.changeToParentDirectory();
				}else{
					File file2= new File(file.getPath()+"\\"+files[i]);
					FileInputStream input=new FileInputStream(file2);
					ftp.storeFile(file2.getName(), input);
					input.close();
				}
			}
		}else{
			File file2= new File(file.getPath());
			FileInputStream input= new FileInputStream(file2);
			ftp.storeFile(file2.getName(), input);
			input.close();
		}
	}
	
	public void makeDirectory(int dayNum) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        
		Calendar cal = Calendar.getInstance();
        for (int i = 0; i <= dayNum; i++) {
        	cal.setTime(new Date());
        	cal.set(Calendar.DATE,cal.get(Calendar.DATE)+i);
        	String fileTime=sdf.format(cal.getTime());	
        	String path1=path+fileTime+"/";
        	Boolean bool = ftp.makeDirectory(path1);
        }
		//ftp.changeWorkingDirectory(path);
		//setPath(path);
	}
	
	public void makeDirectory(int dayNum,String path) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        
		Calendar cal = Calendar.getInstance();
        for (int i = 0; i <= dayNum; i++) {
        	cal.setTime(new Date());
        	cal.set(Calendar.DATE,cal.get(Calendar.DATE)+i);
        	String fileTime=sdf.format(cal.getTime());	
        	String path1=path+"/"+fileTime+"/";
        	Boolean bool = ftp.makeDirectory(path1);
        }
		//ftp.changeWorkingDirectory(path);
		//setPath(path);
	}
	public static void main(String[] args) throws Exception {
		FTPUploadJYH f = new FTPUploadJYH(null,null,0,null,null);
		f.connect();
		f.makeDirectory(9);
	}
}