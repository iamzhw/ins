/**
 * ElectronArchivesServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.client;

import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

//import org.apache.axis.client.Service;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class ElectronArchivesServiceServiceLocator extends org.apache.axis.client.Service implements ElectronArchivesServiceService {

    public ElectronArchivesServiceServiceLocator() {
    }


    public ElectronArchivesServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ElectronArchivesServiceServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ElectronArchivesService
    private String ElectronArchivesService_address = "http://132.228.165.151/Axisl/ElectronArchivesService.jws";

    public String getElectronArchivesServiceAddress() {
        return ElectronArchivesService_address;
    }

    // The WSDD service name defaults to the port name.
    private String ElectronArchivesServiceWSDDServiceName = "ElectronArchivesService";

    public String getElectronArchivesServiceWSDDServiceName() {
        return ElectronArchivesServiceWSDDServiceName;
    }

    public void setElectronArchivesServiceWSDDServiceName(String name) {
        ElectronArchivesServiceWSDDServiceName = name;
    }

    public ElectronArchivesService getElectronArchivesService() throws ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ElectronArchivesService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new ServiceException(e);
        }
        return getElectronArchivesService(endpoint);
    }

    public  ElectronArchivesService getElectronArchivesService(java.net.URL portAddress) throws ServiceException {
        try {
             ElectronArchivesServiceSoapBindingStub _stub = new  ElectronArchivesServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getElectronArchivesServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setElectronArchivesServiceEndpointAddress(String address) {
        ElectronArchivesService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if ( ElectronArchivesService.class.isAssignableFrom(serviceEndpointInterface)) {
                 ElectronArchivesServiceSoapBindingStub _stub = new  ElectronArchivesServiceSoapBindingStub(new java.net.URL(ElectronArchivesService_address), this);
                _stub.setPortName(getElectronArchivesServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("ElectronArchivesService".equals(inputPortName)) {
            return getElectronArchivesService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public QName getServiceName() {
        return new QName("http://132.228.165.151/Axisl/ElectronArchivesService.jws", "ElectronArchivesServiceService");
    }

    private HashSet ports = null;

    public Iterator getPorts() {
        if (ports == null) {
            ports = new HashSet();
            ports.add(new QName("http://132.228.165.151/Axisl/ElectronArchivesService.jws", "ElectronArchivesService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("ElectronArchivesService".equals(portName)) {
            setElectronArchivesServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
