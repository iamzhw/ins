package com.util;

import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 此方法只适用于ftp,不适用于sftp方式
 * @author tw
 *
 */

public class FTPUpload {
	private FTPClient ftp;
	private String path;
	private String addr;
	private int port;
	private String userName;
	private String pwd;
	
	public FTPUpload(){
		this.path="/work/ftpfile/edw/";
		this.addr="192.228.198.67";
		this.port=21;
		this.userName="ftpuser";
		this.pwd="JSxen!@06";
		
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

}
