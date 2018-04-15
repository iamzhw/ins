/**
 * ElectronArchivesServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.client;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;

import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

@SuppressWarnings("all")
public class ElectronArchivesServiceSoapBindingStub extends Stub implements  ElectronArchivesService {
	
    private  Vector cachedSerClasses = new  Vector();
    private  Vector cachedSerQNames = new  Vector();
    private  Vector cachedSerFactories = new  Vector();
    private  Vector cachedDeserFactories = new  Vector();
    static OperationDesc [] _operations;
    static {
        _operations = new OperationDesc[2];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        OperationDesc oper;
        ParameterDesc param;
        oper = new OperationDesc();
        oper.setName("queryPhotoDetail");
        param = new ParameterDesc(new  QName("", "jParam"), ParameterDesc.IN, new  QName("http://www.w3.org/2001/XMLSchema", "string"),  String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new  QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass( String.class);
        oper.setReturnQName(new  QName("", "queryPhotoDetailReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new OperationDesc();
        oper.setName("queryEqpInfo");
        param = new ParameterDesc(new  QName("", "jParam"), ParameterDesc.IN, new  QName("http://www.w3.org/2001/XMLSchema", "string"),  String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new  QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass( String.class);
        oper.setReturnQName(new  QName("", "queryEqpInfoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

    }

    public ElectronArchivesServiceSoapBindingStub() throws AxisFault {
         this(null);
    }

    public ElectronArchivesServiceSoapBindingStub(URL endpointURL, Service service) throws AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ElectronArchivesServiceSoapBindingStub(Service service) throws AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
    }

    protected Call createCall() throws RemoteException {
        try {
            Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
             Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                 String key = ( String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            return _call;
        }
        catch (Throwable _t) {
            throw new AxisFault("Failure trying to get the Call object", _t);
        }
    }

	public String queryPhotoDetail(String jParam) throws  RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("http://DefaultNamespace", "queryPhotoDetail"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
        	
        	Object _resp = _call.invoke(new Object[] {jParam});
        	if (_resp instanceof  RemoteException) {
        		throw ( RemoteException)_resp;
        	} else {
        		extractAttachments(_call);
        		try {
        			return ( String) _resp;
        		} catch ( Exception _exception) {
        			return ( String) JavaUtils.convert(_resp,  String.class);
        		}
        	}
        } catch (AxisFault axisFaultException) {
        	throw axisFaultException;
        }
	}

    public  String queryEqpInfo( String jParam) throws  RemoteException {
    	if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion( SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new  QName("http://DefaultNamespace", "queryEqpInfo"));
        _call.setTimeout(6000);
        setRequestHeaders(_call);
        setAttachments(_call);
        try {
        	Object _resp = _call.invoke(new  Object[] {jParam});

        	if (_resp instanceof  RemoteException) {
        		throw ( RemoteException)_resp;
        	} else {
        		extractAttachments(_call);
	            try {
	            	return ( String) _resp;
	            } catch ( Exception _exception) {
	                return ( String) JavaUtils.convert(_resp,  String.class);
	            }
        	}
        } catch (AxisFault axisFaultException) {
        	throw axisFaultException;
        }
    }

}
