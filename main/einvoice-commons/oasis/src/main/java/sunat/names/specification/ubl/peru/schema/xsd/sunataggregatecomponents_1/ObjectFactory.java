
package sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PaymentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.RoadTransportType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeliveryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IndicatorType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.CodeType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.IdentifierType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.TextType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1 package. 
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

    private final static QName _EndDocumentNumberID_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "EndDocumentNumberID");
    private final static QName _SUNATVerificationCode_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATVerificationCode");
    private final static QName _ReferenceAmount_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "ReferenceAmount");
    private final static QName _DeliveryCarrierTime_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "DeliveryCarrierTime");
    private final static QName _SUNATDespatchLine_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATDespatchLine");
    private final static QName _TotalAmount_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "TotalAmount");
    private final static QName _OriginalDespatchDocumentReference_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "OriginalDespatchDocumentReference");
    private final static QName _SUNATShipment_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATShipment");
    private final static QName _StartDocumentNumberID_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "StartDocumentNumberID");
    private final static QName _DocumentSerialID_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "DocumentSerialID");
    private final static QName _VoidReasonDescription_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "VoidReasonDescription");
    private final static QName _TransportReasonCode_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "TransportReasonCode");
    private final static QName _AdditionalInformation_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "AdditionalInformation");
    private final static QName _BillingPayment_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "BillingPayment");
    private final static QName _DespatchChangeReason_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "DespatchChangeReason");
    private final static QName _VoidedDocumentsLine_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "VoidedDocumentsLine");
    private final static QName _AdditionalMonetaryTotal_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "AdditionalMonetaryTotal");
    private final static QName _SUNATTransportMeans_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATTransportMeans");
    private final static QName _DocumentNumberID_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "DocumentNumberID");
    private final static QName _DriverParty_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "DriverParty");
    private final static QName _SUNATFiscalPath_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATFiscalPath");
    private final static QName _OldDeliveryAddress_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "OldDeliveryAddress");
    private final static QName _OldOriginAddress_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "OldOriginAddress");
    private final static QName _SUNATShipmentStage_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATShipmentStage");
    private final static QName _ShipmentStageTypeCode_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "ShipmentStageTypeCode");
    private final static QName _MultiStageIndicator_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "MultiStageIndicator");
    private final static QName _DeliveryCarrierDate_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "DeliveryCarrierDate");
    private final static QName _SUNATRoadTransport_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATRoadTransport");
    private final static QName _SUNATCarrierParty_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATCarrierParty");
    private final static QName _SUNATTransaction_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATTransaction");
    private final static QName _OutsourcedIndicator_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "OutsourcedIndicator");
    private final static QName _SummaryDocumentsLine_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SummaryDocumentsLine");
    private final static QName _PeriodID_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "PeriodID");
    private final static QName _SUNATEmbededDespatchAdvice_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SUNATEmbededDespatchAdvice");
    private final static QName _SemiTrailer_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "SemiTrailer");
    private final static QName _AdditionalProperty_QNAME = new QName("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", "AdditionalProperty");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SUNATCarrierPartyType }
     * 
     */
    public SUNATCarrierPartyType createSUNATCarrierPartyType() {
        return new SUNATCarrierPartyType();
    }

    /**
     * Create an instance of {@link SUNATEmbededDespatchAdviceType }
     * 
     */
    public SUNATEmbededDespatchAdviceType createSUNATEmbededDespatchAdviceType() {
        return new SUNATEmbededDespatchAdviceType();
    }

    /**
     * Create an instance of {@link SUNATTransportMeansType }
     * 
     */
    public SUNATTransportMeansType createSUNATTransportMeansType() {
        return new SUNATTransportMeansType();
    }

    /**
     * Create an instance of {@link SUNATShipmentType }
     * 
     */
    public SUNATShipmentType createSUNATShipmentType() {
        return new SUNATShipmentType();
    }

    /**
     * Create an instance of {@link DeliveryCarrierDateType }
     * 
     */
    public DeliveryCarrierDateType createDeliveryCarrierDateType() {
        return new DeliveryCarrierDateType();
    }

    /**
     * Create an instance of {@link SUNATFiscalPathType }
     * 
     */
    public SUNATFiscalPathType createSUNATFiscalPathType() {
        return new SUNATFiscalPathType();
    }

    /**
     * Create an instance of {@link SummaryDocumentsLineType }
     * 
     */
    public SummaryDocumentsLineType createSummaryDocumentsLineType() {
        return new SummaryDocumentsLineType();
    }

    /**
     * Create an instance of {@link AdditionalInformationType }
     * 
     */
    public AdditionalInformationType createAdditionalInformationType() {
        return new AdditionalInformationType();
    }

    /**
     * Create an instance of {@link SUNATRoadTransportType }
     * 
     */
    public SUNATRoadTransportType createSUNATRoadTransportType() {
        return new SUNATRoadTransportType();
    }

    /**
     * Create an instance of {@link SUNATTransactionType }
     * 
     */
    public SUNATTransactionType createSUNATTransactionType() {
        return new SUNATTransactionType();
    }

    /**
     * Create an instance of {@link AdditionalMonetaryTotalType }
     * 
     */
    public AdditionalMonetaryTotalType createAdditionalMonetaryTotalType() {
        return new AdditionalMonetaryTotalType();
    }

    /**
     * Create an instance of {@link SUNATShipmentStageType }
     * 
     */
    public SUNATShipmentStageType createSUNATShipmentStageType() {
        return new SUNATShipmentStageType();
    }

    /**
     * Create an instance of {@link VoidedDocumentsLineType }
     * 
     */
    public VoidedDocumentsLineType createVoidedDocumentsLineType() {
        return new VoidedDocumentsLineType();
    }

    /**
     * Create an instance of {@link SUNATDespatchLineType }
     * 
     */
    public SUNATDespatchLineType createSUNATDespatchLineType() {
        return new SUNATDespatchLineType();
    }

    /**
     * Create an instance of {@link AdditionalPropertyType }
     * 
     */
    public AdditionalPropertyType createAdditionalPropertyType() {
        return new AdditionalPropertyType();
    }

    /**
     * Create an instance of {@link DriverPartyType }
     * 
     */
    public DriverPartyType createDriverPartyType() {
        return new DriverPartyType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "EndDocumentNumberID")
    public JAXBElement<IdentifierType> createEndDocumentNumberID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_EndDocumentNumberID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IDType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATVerificationCode")
    public JAXBElement<IDType> createSUNATVerificationCode(IDType value) {
        return new JAXBElement<IDType>(_SUNATVerificationCode_QNAME, IDType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AmountType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "ReferenceAmount")
    public JAXBElement<AmountType> createReferenceAmount(AmountType value) {
        return new JAXBElement<AmountType>(_ReferenceAmount_QNAME, AmountType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliveryTimeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "DeliveryCarrierTime")
    public JAXBElement<DeliveryTimeType> createDeliveryCarrierTime(DeliveryTimeType value) {
        return new JAXBElement<DeliveryTimeType>(_DeliveryCarrierTime_QNAME, DeliveryTimeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SUNATDespatchLineType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATDespatchLine")
    public JAXBElement<SUNATDespatchLineType> createSUNATDespatchLine(SUNATDespatchLineType value) {
        return new JAXBElement<SUNATDespatchLineType>(_SUNATDespatchLine_QNAME, SUNATDespatchLineType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AmountType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "TotalAmount")
    public JAXBElement<AmountType> createTotalAmount(AmountType value) {
        return new JAXBElement<AmountType>(_TotalAmount_QNAME, AmountType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "OriginalDespatchDocumentReference")
    public JAXBElement<DocumentReferenceType> createOriginalDespatchDocumentReference(DocumentReferenceType value) {
        return new JAXBElement<DocumentReferenceType>(_OriginalDespatchDocumentReference_QNAME, DocumentReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SUNATShipmentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATShipment")
    public JAXBElement<SUNATShipmentType> createSUNATShipment(SUNATShipmentType value) {
        return new JAXBElement<SUNATShipmentType>(_SUNATShipment_QNAME, SUNATShipmentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "StartDocumentNumberID")
    public JAXBElement<IdentifierType> createStartDocumentNumberID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_StartDocumentNumberID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "DocumentSerialID")
    public JAXBElement<IdentifierType> createDocumentSerialID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_DocumentSerialID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "VoidReasonDescription")
    public JAXBElement<TextType> createVoidReasonDescription(TextType value) {
        return new JAXBElement<TextType>(_VoidReasonDescription_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "TransportReasonCode")
    public JAXBElement<CodeType> createTransportReasonCode(CodeType value) {
        return new JAXBElement<CodeType>(_TransportReasonCode_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdditionalInformationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "AdditionalInformation")
    public JAXBElement<AdditionalInformationType> createAdditionalInformation(AdditionalInformationType value) {
        return new JAXBElement<AdditionalInformationType>(_AdditionalInformation_QNAME, AdditionalInformationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "BillingPayment")
    public JAXBElement<PaymentType> createBillingPayment(PaymentType value) {
        return new JAXBElement<PaymentType>(_BillingPayment_QNAME, PaymentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IDType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "DespatchChangeReason")
    public JAXBElement<IDType> createDespatchChangeReason(IDType value) {
        return new JAXBElement<IDType>(_DespatchChangeReason_QNAME, IDType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoidedDocumentsLineType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "VoidedDocumentsLine")
    public JAXBElement<VoidedDocumentsLineType> createVoidedDocumentsLine(VoidedDocumentsLineType value) {
        return new JAXBElement<VoidedDocumentsLineType>(_VoidedDocumentsLine_QNAME, VoidedDocumentsLineType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdditionalMonetaryTotalType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "AdditionalMonetaryTotal")
    public JAXBElement<AdditionalMonetaryTotalType> createAdditionalMonetaryTotal(AdditionalMonetaryTotalType value) {
        return new JAXBElement<AdditionalMonetaryTotalType>(_AdditionalMonetaryTotal_QNAME, AdditionalMonetaryTotalType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SUNATTransportMeansType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATTransportMeans")
    public JAXBElement<SUNATTransportMeansType> createSUNATTransportMeans(SUNATTransportMeansType value) {
        return new JAXBElement<SUNATTransportMeansType>(_SUNATTransportMeans_QNAME, SUNATTransportMeansType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "DocumentNumberID")
    public JAXBElement<IdentifierType> createDocumentNumberID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_DocumentNumberID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DriverPartyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "DriverParty")
    public JAXBElement<DriverPartyType> createDriverParty(DriverPartyType value) {
        return new JAXBElement<DriverPartyType>(_DriverParty_QNAME, DriverPartyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SUNATFiscalPathType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATFiscalPath")
    public JAXBElement<SUNATFiscalPathType> createSUNATFiscalPath(SUNATFiscalPathType value) {
        return new JAXBElement<SUNATFiscalPathType>(_SUNATFiscalPath_QNAME, SUNATFiscalPathType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "OldDeliveryAddress")
    public JAXBElement<AddressType> createOldDeliveryAddress(AddressType value) {
        return new JAXBElement<AddressType>(_OldDeliveryAddress_QNAME, AddressType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "OldOriginAddress")
    public JAXBElement<AddressType> createOldOriginAddress(AddressType value) {
        return new JAXBElement<AddressType>(_OldOriginAddress_QNAME, AddressType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SUNATShipmentStageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATShipmentStage")
    public JAXBElement<SUNATShipmentStageType> createSUNATShipmentStage(SUNATShipmentStageType value) {
        return new JAXBElement<SUNATShipmentStageType>(_SUNATShipmentStage_QNAME, SUNATShipmentStageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IDType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "ShipmentStageTypeCode")
    public JAXBElement<IDType> createShipmentStageTypeCode(IDType value) {
        return new JAXBElement<IDType>(_ShipmentStageTypeCode_QNAME, IDType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IndicatorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "MultiStageIndicator")
    public JAXBElement<IndicatorType> createMultiStageIndicator(IndicatorType value) {
        return new JAXBElement<IndicatorType>(_MultiStageIndicator_QNAME, IndicatorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliveryCarrierDateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "DeliveryCarrierDate")
    public JAXBElement<DeliveryCarrierDateType> createDeliveryCarrierDate(DeliveryCarrierDateType value) {
        return new JAXBElement<DeliveryCarrierDateType>(_DeliveryCarrierDate_QNAME, DeliveryCarrierDateType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SUNATRoadTransportType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATRoadTransport")
    public JAXBElement<SUNATRoadTransportType> createSUNATRoadTransport(SUNATRoadTransportType value) {
        return new JAXBElement<SUNATRoadTransportType>(_SUNATRoadTransport_QNAME, SUNATRoadTransportType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SUNATCarrierPartyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATCarrierParty")
    public JAXBElement<SUNATCarrierPartyType> createSUNATCarrierParty(SUNATCarrierPartyType value) {
        return new JAXBElement<SUNATCarrierPartyType>(_SUNATCarrierParty_QNAME, SUNATCarrierPartyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SUNATTransactionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATTransaction")
    public JAXBElement<SUNATTransactionType> createSUNATTransaction(SUNATTransactionType value) {
        return new JAXBElement<SUNATTransactionType>(_SUNATTransaction_QNAME, SUNATTransactionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IndicatorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "OutsourcedIndicator")
    public JAXBElement<IndicatorType> createOutsourcedIndicator(IndicatorType value) {
        return new JAXBElement<IndicatorType>(_OutsourcedIndicator_QNAME, IndicatorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SummaryDocumentsLineType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SummaryDocumentsLine")
    public JAXBElement<SummaryDocumentsLineType> createSummaryDocumentsLine(SummaryDocumentsLineType value) {
        return new JAXBElement<SummaryDocumentsLineType>(_SummaryDocumentsLine_QNAME, SummaryDocumentsLineType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "PeriodID")
    public JAXBElement<IdentifierType> createPeriodID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_PeriodID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SUNATEmbededDespatchAdviceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SUNATEmbededDespatchAdvice")
    public JAXBElement<SUNATEmbededDespatchAdviceType> createSUNATEmbededDespatchAdvice(SUNATEmbededDespatchAdviceType value) {
        return new JAXBElement<SUNATEmbededDespatchAdviceType>(_SUNATEmbededDespatchAdvice_QNAME, SUNATEmbededDespatchAdviceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RoadTransportType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "SemiTrailer")
    public JAXBElement<RoadTransportType> createSemiTrailer(RoadTransportType value) {
        return new JAXBElement<RoadTransportType>(_SemiTrailer_QNAME, RoadTransportType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdditionalPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", name = "AdditionalProperty")
    public JAXBElement<AdditionalPropertyType> createAdditionalProperty(AdditionalPropertyType value) {
        return new JAXBElement<AdditionalPropertyType>(_AdditionalProperty_QNAME, AdditionalPropertyType.class, null, value);
    }

}
