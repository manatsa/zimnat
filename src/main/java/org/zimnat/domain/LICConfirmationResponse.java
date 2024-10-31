package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * LICQuoteUpdate entity.
 * @description - The LICQuoteUpdate entity holding licence confirmation response information inside of LICResultResponse
 * @author CodeMaster.
 */
@Schema(
    description = "LICQuoteUpdate entity.\n@description - The LICQuoteUpdate entity holding licence confirmation response information inside of LICResultResponse\n@author CodeMaster."
)
@Entity
@Table(name = "lic_confirmation_response")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LICConfirmationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "result")
    private String result;

    @Column(name = "message")
    private String message;

    @Column(name = "licence_id")
    private String licenceID;

    @Column(name = "receipt_id")
    private String receiptID;

    @Column(name = "v_rn")
    private String vRN;

    @Column(name = "status")
    private String status;

    @Column(name = "loaded_by")
    private String loadedBy;

    @Column(name = "loaded_date")
    private String loadedDate;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_date")
    private String approvedDate;

    @Column(name = "i_d_number")
    private String iDNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "suburb_id")
    private String suburbID;

    @Column(name = "transaction_amt")
    private String transactionAmt;

    @Column(name = "arrears_amt")
    private String arrearsAmt;

    @Column(name = "penalties_amt")
    private String penaltiesAmt;

    @Column(name = "administration_amt")
    private String administrationAmt;

    @Column(name = "total_lic_amt")
    private String totalLicAmt;

    @Column(name = "radio_tv_amt")
    private String radioTVAmt;

    @Column(name = "radio_tv_arrears_amt")
    private String radioTVArrearsAmt;

    @Column(name = "total_radio_tv_amt")
    private String totalRadioTVAmt;

    @Column(name = "total_amount")
    private String totalAmount;

    @Column(name = "tax_class")
    private String taxClass;

    @Column(name = "nett_mass")
    private String nettMass;

    @Column(name = "lic_expiry_date")
    private String licExpiryDate;

    @Column(name = "radio_tv_expiry_date")
    private String radioTVExpiryDate;

    @JsonIgnoreProperties(value = { "response" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "response")
    private LICResultResponse lICResultResponse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LICConfirmationResponse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return this.result;
    }

    public LICConfirmationResponse result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public LICConfirmationResponse message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLicenceID() {
        return this.licenceID;
    }

    public LICConfirmationResponse licenceID(String licenceID) {
        this.setLicenceID(licenceID);
        return this;
    }

    public void setLicenceID(String licenceID) {
        this.licenceID = licenceID;
    }

    public String getReceiptID() {
        return this.receiptID;
    }

    public LICConfirmationResponse receiptID(String receiptID) {
        this.setReceiptID(receiptID);
        return this;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public String getvRN() {
        return this.vRN;
    }

    public LICConfirmationResponse vRN(String vRN) {
        this.setvRN(vRN);
        return this;
    }

    public void setvRN(String vRN) {
        this.vRN = vRN;
    }

    public String getStatus() {
        return this.status;
    }

    public LICConfirmationResponse status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoadedBy() {
        return this.loadedBy;
    }

    public LICConfirmationResponse loadedBy(String loadedBy) {
        this.setLoadedBy(loadedBy);
        return this;
    }

    public void setLoadedBy(String loadedBy) {
        this.loadedBy = loadedBy;
    }

    public String getLoadedDate() {
        return this.loadedDate;
    }

    public LICConfirmationResponse loadedDate(String loadedDate) {
        this.setLoadedDate(loadedDate);
        return this;
    }

    public void setLoadedDate(String loadedDate) {
        this.loadedDate = loadedDate;
    }

    public String getApprovedBy() {
        return this.approvedBy;
    }

    public LICConfirmationResponse approvedBy(String approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return this.approvedDate;
    }

    public LICConfirmationResponse approvedDate(String approvedDate) {
        this.setApprovedDate(approvedDate);
        return this;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getiDNumber() {
        return this.iDNumber;
    }

    public LICConfirmationResponse iDNumber(String iDNumber) {
        this.setiDNumber(iDNumber);
        return this;
    }

    public void setiDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public LICConfirmationResponse firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public LICConfirmationResponse lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return this.address1;
    }

    public LICConfirmationResponse address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public LICConfirmationResponse address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getSuburbID() {
        return this.suburbID;
    }

    public LICConfirmationResponse suburbID(String suburbID) {
        this.setSuburbID(suburbID);
        return this;
    }

    public void setSuburbID(String suburbID) {
        this.suburbID = suburbID;
    }

    public String getTransactionAmt() {
        return this.transactionAmt;
    }

    public LICConfirmationResponse transactionAmt(String transactionAmt) {
        this.setTransactionAmt(transactionAmt);
        return this;
    }

    public void setTransactionAmt(String transactionAmt) {
        this.transactionAmt = transactionAmt;
    }

    public String getArrearsAmt() {
        return this.arrearsAmt;
    }

    public LICConfirmationResponse arrearsAmt(String arrearsAmt) {
        this.setArrearsAmt(arrearsAmt);
        return this;
    }

    public void setArrearsAmt(String arrearsAmt) {
        this.arrearsAmt = arrearsAmt;
    }

    public String getPenaltiesAmt() {
        return this.penaltiesAmt;
    }

    public LICConfirmationResponse penaltiesAmt(String penaltiesAmt) {
        this.setPenaltiesAmt(penaltiesAmt);
        return this;
    }

    public void setPenaltiesAmt(String penaltiesAmt) {
        this.penaltiesAmt = penaltiesAmt;
    }

    public String getAdministrationAmt() {
        return this.administrationAmt;
    }

    public LICConfirmationResponse administrationAmt(String administrationAmt) {
        this.setAdministrationAmt(administrationAmt);
        return this;
    }

    public void setAdministrationAmt(String administrationAmt) {
        this.administrationAmt = administrationAmt;
    }

    public String getTotalLicAmt() {
        return this.totalLicAmt;
    }

    public LICConfirmationResponse totalLicAmt(String totalLicAmt) {
        this.setTotalLicAmt(totalLicAmt);
        return this;
    }

    public void setTotalLicAmt(String totalLicAmt) {
        this.totalLicAmt = totalLicAmt;
    }

    public String getRadioTVAmt() {
        return this.radioTVAmt;
    }

    public LICConfirmationResponse radioTVAmt(String radioTVAmt) {
        this.setRadioTVAmt(radioTVAmt);
        return this;
    }

    public void setRadioTVAmt(String radioTVAmt) {
        this.radioTVAmt = radioTVAmt;
    }

    public String getRadioTVArrearsAmt() {
        return this.radioTVArrearsAmt;
    }

    public LICConfirmationResponse radioTVArrearsAmt(String radioTVArrearsAmt) {
        this.setRadioTVArrearsAmt(radioTVArrearsAmt);
        return this;
    }

    public void setRadioTVArrearsAmt(String radioTVArrearsAmt) {
        this.radioTVArrearsAmt = radioTVArrearsAmt;
    }

    public String getTotalRadioTVAmt() {
        return this.totalRadioTVAmt;
    }

    public LICConfirmationResponse totalRadioTVAmt(String totalRadioTVAmt) {
        this.setTotalRadioTVAmt(totalRadioTVAmt);
        return this;
    }

    public void setTotalRadioTVAmt(String totalRadioTVAmt) {
        this.totalRadioTVAmt = totalRadioTVAmt;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public LICConfirmationResponse totalAmount(String totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTaxClass() {
        return this.taxClass;
    }

    public LICConfirmationResponse taxClass(String taxClass) {
        this.setTaxClass(taxClass);
        return this;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getNettMass() {
        return this.nettMass;
    }

    public LICConfirmationResponse nettMass(String nettMass) {
        this.setNettMass(nettMass);
        return this;
    }

    public void setNettMass(String nettMass) {
        this.nettMass = nettMass;
    }

    public String getLicExpiryDate() {
        return this.licExpiryDate;
    }

    public LICConfirmationResponse licExpiryDate(String licExpiryDate) {
        this.setLicExpiryDate(licExpiryDate);
        return this;
    }

    public void setLicExpiryDate(String licExpiryDate) {
        this.licExpiryDate = licExpiryDate;
    }

    public String getRadioTVExpiryDate() {
        return this.radioTVExpiryDate;
    }

    public LICConfirmationResponse radioTVExpiryDate(String radioTVExpiryDate) {
        this.setRadioTVExpiryDate(radioTVExpiryDate);
        return this;
    }

    public void setRadioTVExpiryDate(String radioTVExpiryDate) {
        this.radioTVExpiryDate = radioTVExpiryDate;
    }

    public LICResultResponse getLICResultResponse() {
        return this.lICResultResponse;
    }

    public void setLICResultResponse(LICResultResponse lICResultResponse) {
        if (this.lICResultResponse != null) {
            this.lICResultResponse.setResponse(null);
        }
        if (lICResultResponse != null) {
            lICResultResponse.setResponse(this);
        }
        this.lICResultResponse = lICResultResponse;
    }

    public LICConfirmationResponse lICResultResponse(LICResultResponse lICResultResponse) {
        this.setLICResultResponse(lICResultResponse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LICConfirmationResponse)) {
            return false;
        }
        return getId() != null && getId().equals(((LICConfirmationResponse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LICConfirmationResponse{" +
            "id=" + getId() +
            ", result='" + getResult() + "'" +
            ", message='" + getMessage() + "'" +
            ", licenceID='" + getLicenceID() + "'" +
            ", receiptID='" + getReceiptID() + "'" +
            ", vRN='" + getvRN() + "'" +
            ", status='" + getStatus() + "'" +
            ", loadedBy='" + getLoadedBy() + "'" +
            ", loadedDate='" + getLoadedDate() + "'" +
            ", approvedBy='" + getApprovedBy() + "'" +
            ", approvedDate='" + getApprovedDate() + "'" +
            ", iDNumber='" + getiDNumber() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", suburbID='" + getSuburbID() + "'" +
            ", transactionAmt='" + getTransactionAmt() + "'" +
            ", arrearsAmt='" + getArrearsAmt() + "'" +
            ", penaltiesAmt='" + getPenaltiesAmt() + "'" +
            ", administrationAmt='" + getAdministrationAmt() + "'" +
            ", totalLicAmt='" + getTotalLicAmt() + "'" +
            ", radioTVAmt='" + getRadioTVAmt() + "'" +
            ", radioTVArrearsAmt='" + getRadioTVArrearsAmt() + "'" +
            ", totalRadioTVAmt='" + getTotalRadioTVAmt() + "'" +
            ", totalAmount='" + getTotalAmount() + "'" +
            ", taxClass='" + getTaxClass() + "'" +
            ", nettMass='" + getNettMass() + "'" +
            ", licExpiryDate='" + getLicExpiryDate() + "'" +
            ", radioTVExpiryDate='" + getRadioTVExpiryDate() + "'" +
            "}";
    }
}
