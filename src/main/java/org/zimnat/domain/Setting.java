package org.zimnat.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Setting entity.
 * @description - The Setting entity holding system settings information
 * @author CodeMaster.
 */
@Schema(description = "Setting entity.\n@description - The Setting entity holding system settings information\n@author CodeMaster.")
@Entity
@Table(name = "setting")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Size(min = 4, max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "value", length = 50, nullable = false)
    private String value;

    @NotNull
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "last_date")
    private LocalDate lastDate;

    @Column(name = "admin_access")
    private Boolean adminAccess;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Setting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Setting name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Setting description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return this.value;
    }

    public Setting value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getEffectiveDate() {
        return this.effectiveDate;
    }

    public Setting effectiveDate(LocalDate effectiveDate) {
        this.setEffectiveDate(effectiveDate);
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getLastDate() {
        return this.lastDate;
    }

    public Setting lastDate(LocalDate lastDate) {
        this.setLastDate(lastDate);
        return this;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public Boolean getAdminAccess() {
        return this.adminAccess;
    }

    public Setting adminAccess(Boolean adminAccess) {
        this.setAdminAccess(adminAccess);
        return this;
    }

    public void setAdminAccess(Boolean adminAccess) {
        this.adminAccess = adminAccess;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Setting)) {
            return false;
        }
        return getId() != null && getId().equals(((Setting) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Setting{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", value='" + getValue() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", lastDate='" + getLastDate() + "'" +
            ", adminAccess='" + getAdminAccess() + "'" +
            "}";
    }
}
