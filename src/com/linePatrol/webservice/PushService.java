/**
 * PushService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linePatrol.webservice;

public interface PushService extends javax.xml.rpc.Service {
    public java.lang.String getPushServiceHttpPortAddress();

    public com.linePatrol.webservice.PushServicePortType getPushServiceHttpPort() throws javax.xml.rpc.ServiceException;

    public com.linePatrol.webservice.PushServicePortType getPushServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;

    public String pushNotification(String str);
}
