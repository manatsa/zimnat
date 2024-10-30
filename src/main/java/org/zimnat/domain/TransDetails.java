package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.zimnat.domain.enumeration.BusType;
import org.zimnat.domain.enumeration.CurType;
import org.zimnat.domain.enumeration.PayType;
import org.zimnat.domain.enumeration.Status;

/**
 * TransDetails entity.
 * @description - The TransDetails entity holding policy transaction information
 * @author CodeMaster.
 */
@Schema(
    description = "TransDetails entity.\n@description - The TransDetails entity holding policy transaction information\n@author CodeMaster."
)
@Entity
@Table(name = "trans_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(min = 6, max = 30)
    @Column(name = "policy_ref", length = 30)
    private String policyRef;

    @NotNull
    @Column(name = "cover_start_date", nullable = false)
    private LocalDate coverStartDate;

    @NotNull
    @Column(name = "cover_end_date", nullable = false)
    private LocalDate coverEndDate;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "premium", precision = 21, scale = 2, nullable = false)
    private BigDecimal premium;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pay_type", nullable = false)
    private PayType payType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bus_type", nullable = false)
    private BusType busType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ext_status", nullable = false)
    private Status extStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trans_status", nullable = false)
    private Status transStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status", nullable = false)
    private Status syncStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurType currency;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "address" }, allowSetters = true)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "familyCasket" }, allowSetters = true)
    private Branch branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyRef() {
        return this.policyRef;
    }

    public TransDetails policyRef(String policyRef) {
        this.setPolicyRef(policyRef);
        return this;
    }

    public void setPolicyRef(String policyRef) {
        this.policyRef = policyRef;
    }

    public LocalDate getCoverStartDate() {
        return this.coverStartDate;
    }

    public TransDetails coverStartDate(LocalDate coverStartDate) {
        this.setCoverStartDate(coverStartDate);
        return this;
    }

    public void setCoverStartDate(LocalDate coverStartDate) {
        this.coverStartDate = coverStartDate;
    }

    public LocalDate getCoverEndDate() {
        return this.coverEndDate;
    }

    public TransDetails coverEndDate(LocalDate coverEndDate) {
        this.setCoverEndDate(coverEndDate);
        return this;
    }

    public void setCoverEndDate(LocalDate coverEndDate) {
        this.coverEndDate = coverEndDate;
    }

    public BigDecimal getPremium() {
        return this.premium;
    }

    public TransDetails premium(BigDecimal premium) {
        this.setPremium(premium);
        return this;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public PayType getPayType() {
        return this.payType;
    }

    public TransDetails payType(PayType payType) {
        this.setPayType(payType);
        return this;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public BusType getBusType() {
        return this.busType;
    }

    public TransDetails busType(BusType busType) {
        this.setBusType(busType);
        return this;
    }

    public void setBusType(BusType busType) {
        this.busType = busType;
    }

    public Status getExtStatus() {
        return this.extStatus;
    }

    public TransDetails extStatus(Status extStatus) {
        this.setExtStatus(extStatus);
        return this;
    }

    public void setExtStatus(Status extStatus) {
        this.extStatus = extStatus;
    }

    public Status getTransStatus() {
        return this.transStatus;
    }

    public TransDetails transStatus(Status transStatus) {
        this.setTransStatus(transStatus);
        return this;
    }

    public void setTransStatus(Status transStatus) {
        this.transStatus = transStatus;
    }

    public Status getSyncStatus() {
        return this.syncStatus;
    }

    public TransDetails syncStatus(Status syncStatus) {
        this.setSyncStatus(syncStatus);
        return this;
    }

    public void setSyncStatus(Status syncStatus) {
        this.syncStatus = syncStatus;
    }

    public CurType getCurrency() {
        return this.currency;
    }

    public TransDetails currency(CurType currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(CurType currency) {
        this.currency = currency;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TransDetails product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TransDetails client(Client client) {
        this.setClient(client);
        return this;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public TransDetails branch(Branch branch) {
        this.setBranch(branch);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransDetails)) {
            return false;
        }
        return getId() != null && getId().equals(((TransDetails) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransDetails{" +
            "id=" + getId() +
            ", policyRef='" + getPolicyRef() + "'" +
            ", coverStartDate='" + getCoverStartDate() + "'" +
            ", coverEndDate='" + getCoverEndDate() + "'" +
            ", premium=" + getPremium() +
            ", payType='" + getPayType() + "'" +
            ", busType='" + getBusType() + "'" +
            ", extStatus='" + getExtStatus() + "'" +
            ", transStatus='" + getTransStatus() + "'" +
            ", syncStatus='" + getSyncStatus() + "'" +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
