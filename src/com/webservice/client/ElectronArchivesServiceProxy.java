package com.webservice.client;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import org.springframework.stereotype.Service;

@Service
public class ElectronArchivesServiceProxy implements ElectronArchivesService {
	
	private String _endpoint = null;
	private ElectronArchivesService electronArchivesService = null;
  
	public ElectronArchivesServiceProxy() {
		_initElectronArchivesServiceProxy();
	}
  
	public ElectronArchivesServiceProxy(String endpoint) {
		_endpoint = endpoint;
		_initElectronArchivesServiceProxy();
	}
	  
	private void _initElectronArchivesServiceProxy() {
		try {
			electronArchivesService = (new ElectronArchivesServiceServiceLocator()).getElectronArchivesService();
			if (electronArchivesService != null) {
				if (_endpoint != null)
					((Stub)electronArchivesService)._setProperty("service.endpoint.address", _endpoint);
				else
					_endpoint = (String)((Stub)electronArchivesService)._getProperty("service.endpoint.address");
			}
      
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
  
	public String getEndpoint() {
		return _endpoint;
	}
  
	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (electronArchivesService != null)
			((Stub)electronArchivesService)._setProperty("service.endpoint.address", _endpoint);
    
	}
  
	public ElectronArchivesService getElectronArchivesService() {
		if (electronArchivesService == null)
			_initElectronArchivesServiceProxy();
		return electronArchivesService;
	}
  
	public String queryPhotoDetail(String jParam) throws RemoteException{
		if (electronArchivesService == null)
			_initElectronArchivesServiceProxy();
		return electronArchivesService.queryPhotoDetail(jParam);
  }
  
	public String queryEqpInfo(String jParam) throws RemoteException{
		if (electronArchivesService == null)
			_initElectronArchivesServiceProxy();
		return electronArchivesService.queryEqpInfo(jParam);
  }
  
  
}