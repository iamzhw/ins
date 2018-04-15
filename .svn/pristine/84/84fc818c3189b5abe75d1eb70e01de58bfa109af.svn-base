package com.util.sendMessage;

import java.rmi.RemoteException;
  

public class Text {

	 public static void main(String[] args) throws RemoteException {
		 SMServiceStub sss =new SMServiceStub();
		 
		 SMServiceStub.SmAuth sm= new SMServiceStub.SmAuth();
		 
		 SMServiceStub.SMAuthReq sq =new SMServiceStub.SMAuthReq();
		 sq.setUserName("ayw");
		 sq.setPassword("ayw123");
		 sm.setSMAuthReq(sq);
		 SMServiceStub.SmAuthResponse rret= sss.smAuth(sm);
		 SMServiceStub.SMAuthResp resp= rret.getResult();
		 System.out.println("resu:"+ resp.getIResult());
		 System.out.println("resu:"+ resp.getCheckNbr());
		 System.out.println("resu:"+ resp.getSmsg());
		    
	 }
}
