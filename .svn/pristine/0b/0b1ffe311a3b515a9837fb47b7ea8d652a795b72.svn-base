/**
 * PushServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linePatrol.webservice;

import com.system.tool.GlobalValue;

public class PushServiceLocator extends org.apache.axis.client.Service implements com.linePatrol.webservice.PushService {

    public PushServiceLocator() {
    }


    public PushServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PushServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PushServiceHttpPort
    /***ma_xiangyang add 2015-07-20***/
    //private java.lang.String PushServiceHttpPort_address = "http://132.228.176.114:8081/ts/pushService.ws";
    private java.lang.String PushServiceHttpPort_address =GlobalValue.ZBTS;
   
    public java.lang.String getPushServiceHttpPortAddress() {
        return PushServiceHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PushServiceHttpPortWSDDServiceName = "PushServiceHttpPort";

    public java.lang.String getPushServiceHttpPortWSDDServiceName() {
        return PushServiceHttpPortWSDDServiceName;
    }

    public void setPushServiceHttpPortWSDDServiceName(java.lang.String name) {
        PushServiceHttpPortWSDDServiceName = name;
    }

    public com.linePatrol.webservice.PushServicePortType getPushServiceHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PushServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPushServiceHttpPort(endpoint);
    }

    public com.linePatrol.webservice.PushServicePortType getPushServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.linePatrol.webservice.PushServiceHttpBindingStub _stub = new com.linePatrol.webservice.PushServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getPushServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPushServiceHttpPortEndpointAddress(java.lang.String address) {
        PushServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.linePatrol.webservice.PushServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.linePatrol.webservice.PushServiceHttpBindingStub _stub = new com.linePatrol.webservice.PushServiceHttpBindingStub(new java.net.URL(PushServiceHttpPort_address), this);
                _stub.setPortName(getPushServiceHttpPortWSDDServiceName());
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
        if ("PushServiceHttpPort".equals(inputPortName)) {
            return getPushServiceHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.pn.com", "PushService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.pn.com", "PushServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PushServiceHttpPort".equals(portName)) {
            setPushServiceHttpPortEndpointAddress(address);
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

    public String pushNotification(String str){
    	return "";
    }
}
