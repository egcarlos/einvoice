/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "BL_MESSAGE_TRACE")
@SequenceGenerator(name = "EVENT_ID_GENERATOR", sequenceName = "BL_MESSAGE_TRACE_SEQ")
public class EventTrace implements Serializable {

    @Id
    @GeneratedValue(generator = "EVENT_ID_GENERATOR", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MESSAGE_TIME")
    private Date date;

    @Column(name = "MESSAGE_TYPE")
    private String type;

    @Lob
    @Column(name = "MESSAGE_VALUE")
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
