/**
 * CoordWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cableInspection.webservice;

@SuppressWarnings(value = { "all" })
public class CoordWebServiceLocator extends org.apache.axis.client.Service
		implements CoordWebService {

	public CoordWebServiceLocator() {
	}

	public CoordWebServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public CoordWebServiceLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for CoordWebService
	private java.lang.String CoordWebService_address = "http://132.228.125.109/gisintf/services/CoordWebService";

	public java.lang.String getCoordWebServiceAddress() {
		return CoordWebService_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String CoordWebServiceWSDDServiceName = "CoordWebService";

	public java.lang.String getCoordWebServiceWSDDServiceName() {
		return CoordWebServiceWSDDServiceName;
	}

	public void setCoordWebServiceWSDDServiceName(java.lang.String name) {
		CoordWebServiceWSDDServiceName = name;
	}

	public CoordWebServicePortType getCoordWebService()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(CoordWebService_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getCoordWebService(endpoint);
	}

	public CoordWebServicePortType getCoordWebService(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			CoordWebServiceSoapBindingStub _stub = new CoordWebServiceSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getCoordWebServiceWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setCoordWebServiceEndpointAddress(java.lang.String address) {
		CoordWebService_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (CoordWebServicePortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				CoordWebServiceSoapBindingStub _stub = new CoordWebServiceSoapBindingStub(
						new java.net.URL(CoordWebService_address), this);
				_stub.setPortName(getCoordWebServiceWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("CoordWebService".equals(inputPortName)) {
			return getCoordWebService();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName(
				"http://132.228.125.109/gisintf/services/CoordWebService",
				"CoordWebServiceService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"http://132.228.125.109/gisintf/services/CoordWebService",
					"CoordWebService"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("CoordWebService".equals(portName)) {
			setCoordWebServiceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
