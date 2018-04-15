package com.inspecthelper.service;


import java.rmi.RemoteException;

import org.apache.axis.description.OperationDesc;



public class OIPServiceSoapBindingStub extends org.apache.axis.client.Stub implements ServiceForOIP{

	private String methodName;
	private String namespace;


	private OperationDesc [] _operations;


	private  void _initOperationDesc1(String methodName,Object[] obj,String namespace,String[] paramName){
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName(methodName);
		for(int i=0;i<obj.length;i++)
		{
			if(obj[i] instanceof Integer)
			{
				if(paramName != null && paramName.length == obj.length){
					param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", paramName[i]), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);	
				}else{
					param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName(namespace, "arg"+i), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);	
				}
				
			}else
			{
				if(paramName != null && paramName.length == obj.length){
					param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", paramName[i]), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
				}else{
					if(methodName.equals("checkPwd")||methodName.equals("queryDesignWos")||methodName.equals("insertEqp")||methodName.equals("deleteEqp")){
						param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg"+i), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);	
					}else{
					param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName(namespace, "arg"+i), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);	
				}
				}
				
			}

			param.setOmittable(true);
			oper.addParameter(param);
		}

		oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		oper.setReturnClass(java.lang.String.class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[0] = oper;

	}


	public OIPServiceSoapBindingStub(String methodName,java.net.URL endpointURL,String namespace, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		this(service);
		this.methodName=methodName;
		this.namespace=namespace;
		super.cachedEndpoint = endpointURL;
		_operations = new OperationDesc[1];

	}
	public OIPServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
	}

	protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
		try {
			org.apache.axis.client.Call _call = super._createCall();
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
			java.util.Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				java.lang.String key = (java.lang.String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			return _call;
		}
		catch (java.lang.Throwable _t) {
			throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public OperationDesc[] get_operations() {
		return _operations;
	}

	public void set_operations(OperationDesc[] operations) {
		_operations = operations;
	}


	public String getNamespace() {
		return namespace;
	}


	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}


	public String methodInvoke(Object[] obj,String[] paraName)
	throws RemoteException {
		_initOperationDesc1(methodName,obj,namespace,paraName);
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		if(namespace==null||namespace.equals("")){
			_call.setOperationName(new javax.xml.namespace.QName("http://webservice.bizwebservice.module.resmaster.ztesoft.com/",this.methodName));
		}else{
		_call.setOperationName(new javax.xml.namespace.QName(this.namespace,this.methodName));
		}
		setRequestHeaders(_call);
		setAttachments(_call);
		try {        
			java.lang.Object _resp = _call.invoke(obj);

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException)_resp;
			}
			else {
				extractAttachments(_call);
				try {
					return (java.lang.String) _resp;
				} catch (java.lang.Exception _exception) {
					return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

}
