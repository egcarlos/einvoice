/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Carlos Echeverria
 */
@Entity
@Table(name = "SPE_EINVOICEPREPAID")
public class DocumentAdvance implements Serializable {

    @EmbeddedId
    private DocumentAdvancePK id;

    @Column(name = "CANTICIPO", precision = 19, scale = 2)
    private BigDecimal totalDocumentoAnticipo;

    public DocumentAdvancePK getId() {
        return id;
    }

    public void setId(DocumentAdvancePK id) {
        this.id = id;
    }

    public BigDecimal getTotalDocumentoAnticipo() {
        return totalDocumentoAnticipo;
    }

    public void setTotalDocumentoAnticipo(BigDecimal totalDocumentoAnticipo) {
        this.totalDocumentoAnticipo = totalDocumentoAnticipo;
    }

}
