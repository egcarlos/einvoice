/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.driver.ss;

import java.util.Map;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import pe.labtech.einvoice.commons.entity.BLResponseImpl_;
import pe.labtech.einvoice.commons.jndi.JNDI;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK_;
import pe.labtech.einvoice.replicator.entity.DocumentHeader_;

/**
 *
 * @author Carlos Echeverria
 */
public class SaveDocumentTask implements Runnable {

    private final String tde;
    private final String nde;
    private final String td;
    private final String sn;
    private final Map<String, String> responses;

    public SaveDocumentTask(String tde, String nde, String td, String sn, Map<String, String> responses) {
        this.tde = tde;
        this.nde = nde;
        this.td = td;
        this.sn = sn;
        this.responses = responses;
    }

    @Override
    public void run() {
        Logger.getAnonymousLogger().info(() -> tde + "-" + nde + "-" + td + "-" + sn);
        DatabaseManager db = JNDI.getInstance().lookup("java:app/model-public/PublicDatabaseManager");
        //proceed with updates in the bean
        db.handle(e -> {
            CriteriaBuilder cb = e.getCriteriaBuilder();
            CriteriaUpdate<DocumentHeader> cu = cb.createCriteriaUpdate(DocumentHeader.class);
            Root<DocumentHeader> h = cu.from(DocumentHeader.class);
            Path<DocumentHeaderPK> pk = h.get(DocumentHeader_.id);

            responses.forEach((k, v) -> {
                SingularAttribute<? super DocumentHeader, String> att = map(k);
                if (att != null) {
                    cu.set(att, v);
                }
            });

            cu.where(
                    cb.equal(pk.get(d_tde), tde),
                    cb.equal(pk.get(d_nde), nde),
                    cb.equal(pk.get(d_td), td),
                    cb.equal(pk.get(d_sn), sn)
            );

            int affected = e.createQuery(cu).executeUpdate();

            Logger.getAnonymousLogger().info(() -> "Affected rows " + affected);

        });

    }

    //Campos de utilidad
    private static final SingularAttribute<DocumentHeaderPK, String> d_tde = DocumentHeaderPK_.tipoDocumentoEmisor;
    private static final SingularAttribute<DocumentHeaderPK, String> d_nde = DocumentHeaderPK_.numeroDocumentoEmisor;
    private static final SingularAttribute<DocumentHeaderPK, String> d_td = DocumentHeaderPK_.tipoDocumento;
    private static final SingularAttribute<DocumentHeaderPK, String> d_sn = DocumentHeaderPK_.serieNumero;

    //TODO move to a configuration file and use this a defaults...
    private static SingularAttribute<? super DocumentHeader, String> map(String tag) {
        switch (tag) {
            case "hashCode":
                return BLResponseImpl_.bl_hashFirma;
            case "integratedStatus":
                return BLResponseImpl_.bl_estadoProceso;
            case "recordStatus":
                return BLResponseImpl_.bl_estadoRegistro;
            case "pdfFileUrl":
                return BLResponseImpl_.bl_urlpdf;
            case "signatureValue":
                return BLResponseImpl_.bl_firma;
            case "xmlFileSignUrl":
                return BLResponseImpl_.bl_urlxmlubl;
            case "xmlFileSunatUrl":
                return BLResponseImpl_.bl_urlcdr;
            case "sunatMessage":
                return BLResponseImpl_.bl_mensajeSunat;
            case "messages":
                return BLResponseImpl_.bl_mensaje;
        }
        return null;
    }
}
