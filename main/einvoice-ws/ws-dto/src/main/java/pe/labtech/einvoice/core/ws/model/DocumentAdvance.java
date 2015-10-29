package pe.labtech.einvoice.core.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "indicador",
    "tipoDocumentoEmisorAnticipo",
    "numeroDocumentoEmisorAnticipo",
    "tipoDocumentoAnticipo",
    "serieNumeroDocumentoAnticipo",
    "totalPrepagadoAnticipo"
})
public class DocumentAdvance {

    private String indicador;
    private String tipoDocumentoEmisorAnticipo;
    private String numeroDocumentoEmisorAnticipo;
    private String tipoDocumentoAnticipo;
    private String serieNumeroDocumentoAnticipo;
    private String totalPrepagadoAnticipo;

    public DocumentAdvance() {
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getTipoDocumentoEmisorAnticipo() {
        return tipoDocumentoEmisorAnticipo;
    }

    public void setTipoDocumentoEmisorAnticipo(String tipoDocumentoEmisorAnticipo) {
        this.tipoDocumentoEmisorAnticipo = tipoDocumentoEmisorAnticipo;
    }

    public String getNumeroDocumentoEmisorAnticipo() {
        return numeroDocumentoEmisorAnticipo;
    }

    public void setNumeroDocumentoEmisorAnticipo(String numeroDocumentoEmisorAnticipo) {
        this.numeroDocumentoEmisorAnticipo = numeroDocumentoEmisorAnticipo;
    }

    public String getTipoDocumentoAnticipo() {
        return tipoDocumentoAnticipo;
    }

    public void setTipoDocumentoAnticipo(String tipoDocumentoAnticipo) {
        this.tipoDocumentoAnticipo = tipoDocumentoAnticipo;
    }

    public String getSerieNumeroDocumentoAnticipo() {
        return serieNumeroDocumentoAnticipo;
    }

    public void setSerieNumeroDocumentoAnticipo(String serieNumeroDocumentoAnticipo) {
        this.serieNumeroDocumentoAnticipo = serieNumeroDocumentoAnticipo;
    }

    public String getTotalPrepagadoAnticipo() {
        return totalPrepagadoAnticipo;
    }

    public void setTotalPrepagadoAnticipo(String totalPrepagadoAnticipo) {
        this.totalPrepagadoAnticipo = totalPrepagadoAnticipo;
    }

}
