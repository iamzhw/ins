
package com.linePatrol.localwebservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.webservice.changecoordinate.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QrySiteListResponse_QNAME = new QName("http://service.changeCoordinate.webservice.com/", "qrySiteListResponse");
    private final static QName _ChangeStepEquip_QNAME = new QName("http://service.changeCoordinate.webservice.com/", "changeStepEquip");
    private final static QName _QryStepEquipListResponse_QNAME = new QName("http://service.changeCoordinate.webservice.com/", "qryStepEquipListResponse");
    private final static QName _ChangeSiteResponse_QNAME = new QName("http://service.changeCoordinate.webservice.com/", "changeSiteResponse");
    private final static QName _ChangeStepEquipResponse_QNAME = new QName("http://service.changeCoordinate.webservice.com/", "changeStepEquipResponse");
    private final static QName _QryStepEquipList_QNAME = new QName("http://service.changeCoordinate.webservice.com/", "qryStepEquipList");
    private final static QName _ChangeSite_QNAME = new QName("http://service.changeCoordinate.webservice.com/", "changeSite");
    private final static QName _QrySiteList_QNAME = new QName("http://service.changeCoordinate.webservice.com/", "qrySiteList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.webservice.changecoordinate.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QrySiteList }
     * 
     */
    public QrySiteList createQrySiteList() {
        return new QrySiteList();
    }

    /**
     * Create an instance of {@link ChangeSite }
     * 
     */
    public ChangeSite createChangeSite() {
        return new ChangeSite();
    }

    /**
     * Create an instance of {@link ChangeStepEquipResponse }
     * 
     */
    public ChangeStepEquipResponse createChangeStepEquipResponse() {
        return new ChangeStepEquipResponse();
    }

    /**
     * Create an instance of {@link QryStepEquipList }
     * 
     */
    public QryStepEquipList createQryStepEquipList() {
        return new QryStepEquipList();
    }

    /**
     * Create an instance of {@link ChangeSiteResponse }
     * 
     */
    public ChangeSiteResponse createChangeSiteResponse() {
        return new ChangeSiteResponse();
    }

    /**
     * Create an instance of {@link ChangeStepEquip }
     * 
     */
    public ChangeStepEquip createChangeStepEquip() {
        return new ChangeStepEquip();
    }

    /**
     * Create an instance of {@link QrySiteListResponse }
     * 
     */
    public QrySiteListResponse createQrySiteListResponse() {
        return new QrySiteListResponse();
    }

    /**
     * Create an instance of {@link QryStepEquipListResponse }
     * 
     */
    public QryStepEquipListResponse createQryStepEquipListResponse() {
        return new QryStepEquipListResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QrySiteListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.changeCoordinate.webservice.com/", name = "qrySiteListResponse")
    public JAXBElement<QrySiteListResponse> createQrySiteListResponse(QrySiteListResponse value) {
        return new JAXBElement<QrySiteListResponse>(_QrySiteListResponse_QNAME, QrySiteListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeStepEquip }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.changeCoordinate.webservice.com/", name = "changeStepEquip")
    public JAXBElement<ChangeStepEquip> createChangeStepEquip(ChangeStepEquip value) {
        return new JAXBElement<ChangeStepEquip>(_ChangeStepEquip_QNAME, ChangeStepEquip.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QryStepEquipListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.changeCoordinate.webservice.com/", name = "qryStepEquipListResponse")
    public JAXBElement<QryStepEquipListResponse> createQryStepEquipListResponse(QryStepEquipListResponse value) {
        return new JAXBElement<QryStepEquipListResponse>(_QryStepEquipListResponse_QNAME, QryStepEquipListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeSiteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.changeCoordinate.webservice.com/", name = "changeSiteResponse")
    public JAXBElement<ChangeSiteResponse> createChangeSiteResponse(ChangeSiteResponse value) {
        return new JAXBElement<ChangeSiteResponse>(_ChangeSiteResponse_QNAME, ChangeSiteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeStepEquipResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.changeCoordinate.webservice.com/", name = "changeStepEquipResponse")
    public JAXBElement<ChangeStepEquipResponse> createChangeStepEquipResponse(ChangeStepEquipResponse value) {
        return new JAXBElement<ChangeStepEquipResponse>(_ChangeStepEquipResponse_QNAME, ChangeStepEquipResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QryStepEquipList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.changeCoordinate.webservice.com/", name = "qryStepEquipList")
    public JAXBElement<QryStepEquipList> createQryStepEquipList(QryStepEquipList value) {
        return new JAXBElement<QryStepEquipList>(_QryStepEquipList_QNAME, QryStepEquipList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeSite }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.changeCoordinate.webservice.com/", name = "changeSite")
    public JAXBElement<ChangeSite> createChangeSite(ChangeSite value) {
        return new JAXBElement<ChangeSite>(_ChangeSite_QNAME, ChangeSite.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QrySiteList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.changeCoordinate.webservice.com/", name = "qrySiteList")
    public JAXBElement<QrySiteList> createQrySiteList(QrySiteList value) {
        return new JAXBElement<QrySiteList>(_QrySiteList_QNAME, QrySiteList.class, null, value);
    }

}
