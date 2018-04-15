/**
 * AsigAxisServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.util.webservice;

import com.system.tool.GlobalValue;

public class AsigAxisServiceServiceLocator extends org.apache.axis.client.Service implements com.util.webservice.AsigAxisServiceService {

    public AsigAxisServiceServiceLocator() {
    }


    public AsigAxisServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AsigAxisServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AsigAxisService
    private java.lang.String AsigAxisService_address = GlobalValue.SEND_MESSAGE_URL;

    public java.lang.String getAsigAxisServiceAddress() {
        return AsigAxisService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AsigAxisServiceWSDDServiceName = "AsigAxisService";

    public java.lang.String getAsigAxisServiceWSDDServiceName() {
        return AsigAxisServiceWSDDServiceName;
    }

    public void setAsigAxisServiceWSDDServiceName(java.lang.String name) {
        AsigAxisServiceWSDDServiceName = name;
    }

    public com.util.webservice.AsigAxisService_PortType getAsigAxisService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AsigAxisService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAsigAxisService(endpoint);
    }

    public com.util.webservice.AsigAxisService_PortType getAsigAxisService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.util.webservice.AsigAxisServiceSoapBindingStub _stub = new com.util.webservice.AsigAxisServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getAsigAxisServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAsigAxisServiceEndpointAddress(java.lang.String address) {
        AsigAxisService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.util.webservice.AsigAxisService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.util.webservice.AsigAxisServiceSoapBindingStub _stub = new com.util.webservice.AsigAxisServiceSoapBindingStub(new java.net.URL(AsigAxisService_address), this);
                _stub.setPortName(getAsigAxisServiceWSDDServiceName());
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
        if ("AsigAxisService".equals(inputPortName)) {
            return getAsigAxisService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.regaltec.com", "AsigAxisServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.regaltec.com", "AsigAxisService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AsigAxisService".equals(portName)) {
            setAsigAxisServiceEndpointAddress(address);
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
