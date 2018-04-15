/**
 * OSSInterfaceForEDBProjServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.yy;

public class OSSInterfaceForEDBProjServiceLocator extends org.apache.axis.client.Service implements com.webservice.yy.OSSInterfaceForEDBProjService {

    public OSSInterfaceForEDBProjServiceLocator() {
    }


    public OSSInterfaceForEDBProjServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OSSInterfaceForEDBProjServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for OSSInterfaceForEDBProjPort
    //正式环境
    private java.lang.String OSSInterfaceForEDBProjPort_address = "http://js.res.jsoss.net/resweb/service/IOSSInterfaceForEDBProj";//正式环境
    //测试环境
    //private java.lang.String OSSInterfaceForEDBProjPort_address = "http://132.228.187.150/resweb/service/IOSSInterfaceForEDBProj";//测试环境

    public java.lang.String getOSSInterfaceForEDBProjPortAddress() {
        return OSSInterfaceForEDBProjPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OSSInterfaceForEDBProjPortWSDDServiceName = "OSSInterfaceForEDBProjPort";

    public java.lang.String getOSSInterfaceForEDBProjPortWSDDServiceName() {
        return OSSInterfaceForEDBProjPortWSDDServiceName;
    }

    public void setOSSInterfaceForEDBProjPortWSDDServiceName(java.lang.String name) {
        OSSInterfaceForEDBProjPortWSDDServiceName = name;
    }

    public com.webservice.yy.IOSSInterfaceForEDBProj getOSSInterfaceForEDBProjPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OSSInterfaceForEDBProjPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOSSInterfaceForEDBProjPort(endpoint);
    }

    public com.webservice.yy.IOSSInterfaceForEDBProj getOSSInterfaceForEDBProjPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.webservice.yy.OSSInterfaceForEDBProjServiceSoapBindingStub _stub = new com.webservice.yy.OSSInterfaceForEDBProjServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getOSSInterfaceForEDBProjPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOSSInterfaceForEDBProjPortEndpointAddress(java.lang.String address) {
        OSSInterfaceForEDBProjPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.webservice.yy.IOSSInterfaceForEDBProj.class.isAssignableFrom(serviceEndpointInterface)) {
                com.webservice.yy.OSSInterfaceForEDBProjServiceSoapBindingStub _stub = new com.webservice.yy.OSSInterfaceForEDBProjServiceSoapBindingStub(new java.net.URL(OSSInterfaceForEDBProjPort_address), this);
                _stub.setPortName(getOSSInterfaceForEDBProjPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("OSSInterfaceForEDBProjPort".equals(inputPortName)) {
            return getOSSInterfaceForEDBProjPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.bizwebservice.module.resmaster.ztesoft.com/", "OSSInterfaceForEDBProjService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.bizwebservice.module.resmaster.ztesoft.com/", "OSSInterfaceForEDBProjPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("OSSInterfaceForEDBProjPort".equals(portName)) {
            setOSSInterfaceForEDBProjPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
