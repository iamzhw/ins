/**
 * OSSInterfaceForPHONEServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.oss;

public class OSSInterfaceForPHONEServiceLocator extends org.apache.axis.client.Service implements com.webservice.oss.OSSInterfaceForPHONEService {

    public OSSInterfaceForPHONEServiceLocator() {
    }


    public OSSInterfaceForPHONEServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OSSInterfaceForPHONEServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for OSSInterfaceForPHONEPort
    private java.lang.String OSSInterfaceForPHONEPort_address = "http://js.res.jsoss.net/resweb/service/phoneInterface";

    public java.lang.String getOSSInterfaceForPHONEPortAddress() {
        return OSSInterfaceForPHONEPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OSSInterfaceForPHONEPortWSDDServiceName = "OSSInterfaceForPHONEPort";

    public java.lang.String getOSSInterfaceForPHONEPortWSDDServiceName() {
        return OSSInterfaceForPHONEPortWSDDServiceName;
    }

    public void setOSSInterfaceForPHONEPortWSDDServiceName(java.lang.String name) {
        OSSInterfaceForPHONEPortWSDDServiceName = name;
    }

    public com.webservice.oss.IOSSInterfaceForPHONE getOSSInterfaceForPHONEPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OSSInterfaceForPHONEPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOSSInterfaceForPHONEPort(endpoint);
    }

    public com.webservice.oss.IOSSInterfaceForPHONE getOSSInterfaceForPHONEPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.webservice.oss.OSSInterfaceForPHONEServiceSoapBindingStub _stub = new com.webservice.oss.OSSInterfaceForPHONEServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getOSSInterfaceForPHONEPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOSSInterfaceForPHONEPortEndpointAddress(java.lang.String address) {
        OSSInterfaceForPHONEPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.webservice.oss.IOSSInterfaceForPHONE.class.isAssignableFrom(serviceEndpointInterface)) {
                com.webservice.oss.OSSInterfaceForPHONEServiceSoapBindingStub _stub = new com.webservice.oss.OSSInterfaceForPHONEServiceSoapBindingStub(new java.net.URL(OSSInterfaceForPHONEPort_address), this);
                _stub.setPortName(getOSSInterfaceForPHONEPortWSDDServiceName());
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
        if ("OSSInterfaceForPHONEPort".equals(inputPortName)) {
            return getOSSInterfaceForPHONEPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.bizwebservice.module.resmaster.ztesoft.com/", "OSSInterfaceForPHONEService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.bizwebservice.module.resmaster.ztesoft.com/", "OSSInterfaceForPHONEPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("OSSInterfaceForPHONEPort".equals(portName)) {
            setOSSInterfaceForPHONEPortEndpointAddress(address);
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
