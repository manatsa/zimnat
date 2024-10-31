package org.zimnat.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.zimnat.domain.enumeration.DeliveryMethod;
import org.zimnat.domain.enumeration.PayType;

/**
 * LICQuoteUpdate entity.
 * @description - The LICQuoteUpdate entity holding licence quote update (approve or reject) request parameters
 * @author CodeMaster.
 */
@Schema(
    description = "LICQuoteUpdate entity.\n@description - The LICQuoteUpdate entity holding licence quote update (approve or reject) request parameters\n@author CodeMaster."
)
@Entity
@Table(name = "lic_quote_update")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LICQuoteUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "partner_reference", nullable = false)
    private String partnerReference;

    @NotNull
    @Column(name = "the_date", nullable = false)
    private String theDate;

    @NotNull
    @Column(name = "the_version", nullable = false)
    private String theVersion;

    @NotNull
    @Column(name = "partner_token", nullable = false)
    private String partnerToken;

    @NotNull
    @Column(name = "licence_id", nullable = false)
    private String licenceID;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PayType paymentMethod;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_method", nullable = false)
    private DeliveryMethod deliveryMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LICQuoteUpdate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerReference() {
        return this.partnerReference;
    }

    public LICQuoteUpdate partnerReference(String partnerReference) {
        this.setPartnerReference(partnerReference);
        return this;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public String getTheDate() {
        return this.theDate;
    }

    public LICQuoteUpdate theDate(String theDate) {
        this.setTheDate(theDate);
        return this;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getTheVersion() {
        return this.theVersion;
    }

    public LICQuoteUpdate theVersion(String theVersion) {
        this.setTheVersion(theVersion);
        return this;
    }

    public void setTheVersion(String theVersion) {
        this.theVersion = theVersion;
    }

    public String getPartnerToken() {
        return this.partnerToken;
    }

    public LICQuoteUpdate partnerToken(String partnerToken) {
        this.setPartnerToken(partnerToken);
        return this;
    }

    public void setPartnerToken(String partnerToken) {
        this.partnerToken = partnerToken;
    }

    public String getLicenceID() {
        return this.licenceID;
    }

    public LICQuoteUpdate licenceID(String licenceID) {
        this.setLicenceID(licenceID);
        return this;
    }

    public void setLicenceID(String licenceID) {
        this.licenceID = licenceID;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public LICQuoteUpdate status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PayType getPaymentMethod() {
        return this.paymentMethod;
    }

    public LICQuoteUpdate paymentMethod(PayType paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(PayType paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public DeliveryMethod getDeliveryMethod() {
        return this.deliveryMethod;
    }

    public LICQuoteUpdate deliveryMethod(DeliveryMethod deliveryMethod) {
        this.setDeliveryMethod(deliveryMethod);
        return this;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LICQuoteUpdate)) {
            return false;
        }
        return getId() != null && getId().equals(((LICQuoteUpdate) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LICQuoteUpdate{" +
            "id=" + getId() +
            ", partnerReference='" + getPartnerReference() + "'" +
            ", theDate='" + getTheDate() + "'" +
            ", theVersion='" + getTheVersion() + "'" +
            ", partnerToken='" + getPartnerToken() + "'" +
            ", licenceID='" + getLicenceID() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", deliveryMethod='" + getDeliveryMethod() + "'" +
            "}";
    }
}
