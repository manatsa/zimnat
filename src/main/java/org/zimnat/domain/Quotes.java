package org.zimnat.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Quotes entity.
 * @description - The Quotes entity holding licence quote response information
 * @author CodeMaster.
 */
@Schema(description = "Quotes entity.\n@description - The Quotes entity holding licence quote response information\n@author CodeMaster.")
@Entity
@Table(name = "quotes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Quotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "v_rn")
    private String vRN;

    @Column(name = "licence_id")
    private String licenceID;

    @Column(name = "result")
    private Integer result;

    @Column(name = "message")
    private String message;

    @Column(name = "i_d_number")
    private String iDNumber;

    @Column(name = "client_id_type")
    private String clientIDType;

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

    @Column(name = "lic_frequency")
    private String licFrequency;

    @Column(name = "radio_tv_usage")
    private String radioTVUsage;

    @Column(name = "radio_tv_frequency")
    private String radioTVFrequency;

    @Column(name = "tax_class")
    private String taxClass;

    @Column(name = "nett_mass")
    private String nettMass;

    @Column(name = "lic_expiry_date")
    private String licExpiryDate;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Quotes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getvRN() {
        return this.vRN;
    }

    public Quotes vRN(String vRN) {
        this.setvRN(vRN);
        return this;
    }

    public void setvRN(String vRN) {
        this.vRN = vRN;
    }

    public String getLicenceID() {
        return this.licenceID;
    }

    public Quotes licenceID(String licenceID) {
        this.setLicenceID(licenceID);
        return this;
    }

    public void setLicenceID(String licenceID) {
        this.licenceID = licenceID;
    }

    public Integer getResult() {
        return this.result;
    }

    public Quotes result(Integer result) {
        this.setResult(result);
        return this;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public Quotes message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getiDNumber() {
        return this.iDNumber;
    }

    public Quotes iDNumber(String iDNumber) {
        this.setiDNumber(iDNumber);
        return this;
    }

    public void setiDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    public String getClientIDType() {
        return this.clientIDType;
    }

    public Quotes clientIDType(String clientIDType) {
        this.setClientIDType(clientIDType);
        return this;
    }

    public void setClientIDType(String clientIDType) {
        this.clientIDType = clientIDType;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Quotes firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Quotes lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return this.address1;
    }

    public Quotes address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public Quotes address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getSuburbID() {
        return this.suburbID;
    }

    public Quotes suburbID(String suburbID) {
        this.setSuburbID(suburbID);
        return this;
    }

    public void setSuburbID(String suburbID) {
        this.suburbID = suburbID;
    }

    public String getLicFrequency() {
        return this.licFrequency;
    }

    public Quotes licFrequency(String licFrequency) {
        this.setLicFrequency(licFrequency);
        return this;
    }

    public void setLicFrequency(String licFrequency) {
        this.licFrequency = licFrequency;
    }

    public String getRadioTVUsage() {
        return this.radioTVUsage;
    }

    public Quotes radioTVUsage(String radioTVUsage) {
        this.setRadioTVUsage(radioTVUsage);
        return this;
    }

    public void setRadioTVUsage(String radioTVUsage) {
        this.radioTVUsage = radioTVUsage;
    }

    public String getRadioTVFrequency() {
        return this.radioTVFrequency;
    }

    public Quotes radioTVFrequency(String radioTVFrequency) {
        this.setRadioTVFrequency(radioTVFrequency);
        return this;
    }

    public void setRadioTVFrequency(String radioTVFrequency) {
        this.radioTVFrequency = radioTVFrequency;
    }

    public String getTaxClass() {
        return this.taxClass;
    }

    public Quotes taxClass(String taxClass) {
        this.setTaxClass(taxClass);
        return this;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getNettMass() {
        return this.nettMass;
    }

    public Quotes nettMass(String nettMass) {
        this.setNettMass(nettMass);
        return this;
    }

    public void setNettMass(String nettMass) {
        this.nettMass = nettMass;
    }

    public String getLicExpiryDate() {
        return this.licExpiryDate;
    }

    public Quotes licExpiryDate(String licExpiryDate) {
        this.setLicExpiryDate(licExpiryDate);
        return this;
    }

    public void setLicExpiryDate(String licExpiryDate) {
        this.licExpiryDate = licExpiryDate;
    }

    public String getTransactionAmt() {
        return this.transactionAmt;
    }

    public Quotes transactionAmt(String transactionAmt) {
        this.setTransactionAmt(transactionAmt);
        return this;
    }

    public void setTransactionAmt(String transactionAmt) {
        this.transactionAmt = transactionAmt;
    }

    public String getArrearsAmt() {
        return this.arrearsAmt;
    }

    public Quotes arrearsAmt(String arrearsAmt) {
        this.setArrearsAmt(arrearsAmt);
        return this;
    }

    public void setArrearsAmt(String arrearsAmt) {
        this.arrearsAmt = arrearsAmt;
    }

    public String getPenaltiesAmt() {
        return this.penaltiesAmt;
    }

    public Quotes penaltiesAmt(String penaltiesAmt) {
        this.setPenaltiesAmt(penaltiesAmt);
        return this;
    }

    public void setPenaltiesAmt(String penaltiesAmt) {
        this.penaltiesAmt = penaltiesAmt;
    }

    public String getAdministrationAmt() {
        return this.administrationAmt;
    }

    public Quotes administrationAmt(String administrationAmt) {
        this.setAdministrationAmt(administrationAmt);
        return this;
    }

    public void setAdministrationAmt(String administrationAmt) {
        this.administrationAmt = administrationAmt;
    }

    public String getTotalLicAmt() {
        return this.totalLicAmt;
    }

    public Quotes totalLicAmt(String totalLicAmt) {
        this.setTotalLicAmt(totalLicAmt);
        return this;
    }

    public void setTotalLicAmt(String totalLicAmt) {
        this.totalLicAmt = totalLicAmt;
    }

    public String getRadioTVAmt() {
        return this.radioTVAmt;
    }

    public Quotes radioTVAmt(String radioTVAmt) {
        this.setRadioTVAmt(radioTVAmt);
        return this;
    }

    public void setRadioTVAmt(String radioTVAmt) {
        this.radioTVAmt = radioTVAmt;
    }

    public String getRadioTVArrearsAmt() {
        return this.radioTVArrearsAmt;
    }

    public Quotes radioTVArrearsAmt(String radioTVArrearsAmt) {
        this.setRadioTVArrearsAmt(radioTVArrearsAmt);
        return this;
    }

    public void setRadioTVArrearsAmt(String radioTVArrearsAmt) {
        this.radioTVArrearsAmt = radioTVArrearsAmt;
    }

    public String getTotalRadioTVAmt() {
        return this.totalRadioTVAmt;
    }

    public Quotes totalRadioTVAmt(String totalRadioTVAmt) {
        this.setTotalRadioTVAmt(totalRadioTVAmt);
        return this;
    }

    public void setTotalRadioTVAmt(String totalRadioTVAmt) {
        this.totalRadioTVAmt = totalRadioTVAmt;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public Quotes totalAmount(String totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quotes)) {
            return false;
        }
        return getId() != null && getId().equals(((Quotes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quotes{" +
            "id=" + getId() +
            ", vRN='" + getvRN() + "'" +
            ", licenceID='" + getLicenceID() + "'" +
            ", result=" + getResult() +
            ", message='" + getMessage() + "'" +
            ", iDNumber='" + getiDNumber() + "'" +
            ", clientIDType='" + getClientIDType() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", suburbID='" + getSuburbID() + "'" +
            ", licFrequency='" + getLicFrequency() + "'" +
            ", radioTVUsage='" + getRadioTVUsage() + "'" +
            ", radioTVFrequency='" + getRadioTVFrequency() + "'" +
            ", taxClass='" + getTaxClass() + "'" +
            ", nettMass='" + getNettMass() + "'" +
            ", licExpiryDate='" + getLicExpiryDate() + "'" +
            ", transactionAmt='" + getTransactionAmt() + "'" +
            ", arrearsAmt='" + getArrearsAmt() + "'" +
            ", penaltiesAmt='" + getPenaltiesAmt() + "'" +
            ", administrationAmt='" + getAdministrationAmt() + "'" +
            ", totalLicAmt='" + getTotalLicAmt() + "'" +
            ", radioTVAmt='" + getRadioTVAmt() + "'" +
            ", radioTVArrearsAmt='" + getRadioTVArrearsAmt() + "'" +
            ", totalRadioTVAmt='" + getTotalRadioTVAmt() + "'" +
            ", totalAmount='" + getTotalAmount() + "'" +
            "}";
    }
}
