
package pckcentiscapews;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pckcentiscapews package. 
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

    private final static QName _LoadNetwork_QNAME = new QName("http://pckCentiscapeWS/", "LoadNetwork");
    private final static QName _Request_QNAME = new QName("http://pckCentiscapeWS/", "Request");
    private final static QName _RequestResponse_QNAME = new QName("http://pckCentiscapeWS/", "RequestResponse");
    private final static QName _LoadNetworkResponse_QNAME = new QName("http://pckCentiscapeWS/", "LoadNetworkResponse");
    private final static QName _RequestPUser_QNAME = new QName("", "pUser");
    private final static QName _LoadNetworkPNetworkStream_QNAME = new QName("", "pNetworkStream");
    private final static QName _LoadNetworkPNetworkName_QNAME = new QName("", "pNetworkName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pckcentiscapews
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LoadNetworkResponse }
     * 
     */
    public LoadNetworkResponse createLoadNetworkResponse() {
        return new LoadNetworkResponse();
    }

    /**
     * Create an instance of {@link LoadNetwork }
     * 
     */
    public LoadNetwork createLoadNetwork() {
        return new LoadNetwork();
    }

    /**
     * Create an instance of {@link RequestResponse }
     * 
     */
    public RequestResponse createRequestResponse() {
        return new RequestResponse();
    }

    /**
     * Create an instance of {@link Request }
     * 
     */
    public Request createRequest() {
        return new Request();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadNetwork }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pckCentiscapeWS/", name = "LoadNetwork")
    public JAXBElement<LoadNetwork> createLoadNetwork(LoadNetwork value) {
        return new JAXBElement<LoadNetwork>(_LoadNetwork_QNAME, LoadNetwork.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Request }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pckCentiscapeWS/", name = "Request")
    public JAXBElement<Request> createRequest(Request value) {
        return new JAXBElement<Request>(_Request_QNAME, Request.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pckCentiscapeWS/", name = "RequestResponse")
    public JAXBElement<RequestResponse> createRequestResponse(RequestResponse value) {
        return new JAXBElement<RequestResponse>(_RequestResponse_QNAME, RequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadNetworkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pckCentiscapeWS/", name = "LoadNetworkResponse")
    public JAXBElement<LoadNetworkResponse> createLoadNetworkResponse(LoadNetworkResponse value) {
        return new JAXBElement<LoadNetworkResponse>(_LoadNetworkResponse_QNAME, LoadNetworkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pUser", scope = Request.class)
    public JAXBElement<byte[]> createRequestPUser(byte[] value) {
        return new JAXBElement<byte[]>(_RequestPUser_QNAME, byte[].class, Request.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pNetworkStream", scope = LoadNetwork.class)
    public JAXBElement<byte[]> createLoadNetworkPNetworkStream(byte[] value) {
        return new JAXBElement<byte[]>(_LoadNetworkPNetworkStream_QNAME, byte[].class, LoadNetwork.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pNetworkName", scope = LoadNetwork.class)
    public JAXBElement<byte[]> createLoadNetworkPNetworkName(byte[] value) {
        return new JAXBElement<byte[]>(_LoadNetworkPNetworkName_QNAME, byte[].class, LoadNetwork.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pUser", scope = LoadNetwork.class)
    public JAXBElement<byte[]> createLoadNetworkPUser(byte[] value) {
        return new JAXBElement<byte[]>(_RequestPUser_QNAME, byte[].class, LoadNetwork.class, ((byte[]) value));
    }

}
