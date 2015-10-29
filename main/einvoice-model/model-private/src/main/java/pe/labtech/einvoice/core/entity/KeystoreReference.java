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
import javax.persistence.Lob;
import javax.persistence.Table;

/**
* Clase KeystoreReference.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@Entity
@Table(name = "CF_KEYSTORE")
public class KeystoreReference implements Serializable {

    @Id
    @Column(name = "ID", length = 40)
    private String id;

    @Lob
    @Column(name = "KEYSTORE_DATA")
    private byte[] data;

    @Column(name = "PROTECTION")
    private String protection;

    public KeystoreReference() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final KeystoreReference other = (KeystoreReference) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getProtection() {
        return protection;
    }

    public void setProtection(String protection) {
        this.protection = protection;
    }

}
