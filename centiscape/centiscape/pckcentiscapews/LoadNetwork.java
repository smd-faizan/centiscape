
package pckcentiscapews;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per LoadNetwork complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="LoadNetwork">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lngIdRequest" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="pUser" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="pNetworkName" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="pNetworkStream" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="intStreamLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadNetwork", propOrder = {
    "lngIdRequest",
    "pUser",
    "pNetworkName",
    "pNetworkStream",
    "intStreamLength"
})
public class LoadNetwork {

    protected long lngIdRequest;
    @XmlElementRef(name = "pUser", type = JAXBElement.class)
    protected JAXBElement<byte[]> pUser;
    @XmlElementRef(name = "pNetworkName", type = JAXBElement.class)
    protected JAXBElement<byte[]> pNetworkName;
    @XmlElementRef(name = "pNetworkStream", type = JAXBElement.class)
    protected JAXBElement<byte[]> pNetworkStream;
    protected int intStreamLength;

    /**
     * Recupera il valore della proprietà lngIdRequest.
     * 
     */
    public long getLngIdRequest() {
        return lngIdRequest;
    }

    /**
     * Imposta il valore della proprietà lngIdRequest.
     * 
     */
    public void setLngIdRequest(long value) {
        this.lngIdRequest = value;
    }

    /**
     * Recupera il valore della proprietà pUser.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public JAXBElement<byte[]> getPUser() {
        return pUser;
    }

    /**
     * Imposta il valore della proprietà pUser.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public void setPUser(JAXBElement<byte[]> value) {
        this.pUser = value;
    }

    /**
     * Recupera il valore della proprietà pNetworkName.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public JAXBElement<byte[]> getPNetworkName() {
        return pNetworkName;
    }

    /**
     * Imposta il valore della proprietà pNetworkName.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public void setPNetworkName(JAXBElement<byte[]> value) {
        this.pNetworkName = value;
    }

    /**
     * Recupera il valore della proprietà pNetworkStream.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public JAXBElement<byte[]> getPNetworkStream() {
        return pNetworkStream;
    }

    /**
     * Imposta il valore della proprietà pNetworkStream.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public void setPNetworkStream(JAXBElement<byte[]> value) {
        this.pNetworkStream = value;
    }

    /**
     * Recupera il valore della proprietà intStreamLength.
     * 
     */
    public int getIntStreamLength() {
        return intStreamLength;
    }

    /**
     * Imposta il valore della proprietà intStreamLength.
     * 
     */
    public void setIntStreamLength(int value) {
        this.intStreamLength = value;
    }

}
