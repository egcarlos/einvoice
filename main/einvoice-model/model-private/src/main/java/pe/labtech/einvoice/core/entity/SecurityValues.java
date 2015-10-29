/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.core.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
* Clase SecurityValues.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@Entity
@Table(name = "CF_SECURITYVALUES")
public class SecurityValues implements Serializable {

    @Id
    @Column(length = 40)
    private String userId;

    @JoinColumn(name = "KEYSTORE_ID")
    @ManyToOne
    private KeystoreReference keystoreReference;

    @Column(name = "ALIAS", length = 100)
    private String alias;

    @Column(name = "PROTECTION", length = 100)
    private String protection;

    public SecurityValues() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.userId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SecurityValues other = (SecurityValues) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        return true;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public KeystoreReference getKeystoreReference() {
        return keystoreReference;
    }

    public void setKeystoreReference(KeystoreReference keystoreReference) {
        this.keystoreReference = keystoreReference;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getProtection() {
        return protection;
    }

    public void setProtection(String protection) {
        this.protection = protection;
    }

}
