/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.model;

/**
 *
 * @author Arturo
 */
public class DocumentReference {
    
    private String tipoDocumentoReferencia;
    
    private String numeroDocumentoReferencia;

    /**
     * @return the tipoDocumentoReferencia
     */
    public String getTipoDocumentoReferencia() {
        return tipoDocumentoReferencia;
    }

    /**
     * @param tipoDocumentoReferencia the tipoDocumentoReferencia to set
     */
    public void setTipoDocumentoReferencia(String tipoDocumentoReferencia) {
        this.tipoDocumentoReferencia = tipoDocumentoReferencia;
    }

    /**
     * @return the numeroDocumentoReferencia
     */
    public String getNumeroDocumentoReferencia() {
        return numeroDocumentoReferencia;
    }

    /**
     * @param numeroDocumentoReferencia the numeroDocumentoReferencia to set
     */
    public void setNumeroDocumentoReferencia(String numeroDocumentoReferencia) {
        this.numeroDocumentoReferencia = numeroDocumentoReferencia;
    }
    
    public DocumentReference(){
        
    }            
    
    
    
}
