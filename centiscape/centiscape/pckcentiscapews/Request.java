
package pckcentiscapews;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Request complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pUser" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="lngCentralities" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Request", propOrder = {
    "pUser",
    "lngCentralities"
})
public class Request {

    @XmlElementRef(name = "pUser", type = JAXBElement.class)
    protected JAXBElement<byte[]> pUser;
    protected long lngCentralities;

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
     * Recupera il valore della proprietà lngCentralities.
     * 
     */
    public long getLngCentralities() {
        return lngCentralities;
    }

    /**
     * Imposta il valore della proprietà lngCentralities.
     * 
     */
    public void setLngCentralities(long value) {
        this.lngCentralities = value;
    }

}
