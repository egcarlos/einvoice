/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.tasks.sign;

import pe.labtech.einvoice.core.tasks.tools.ServiceCommons;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import static pe.labtech.einvoice.core.tasks.tools.ParseCommons.map;
import static pe.labtech.einvoice.core.tasks.tools.ServiceCommons.signOnlineOrSync;
import pe.labtech.einvoice.core.tasks.tools.Tools;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.model.DocumentItem;

/**
 * Clase OnlineInvoice.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Stateless
@LocalBean
public class OnlineInvoice {

    @EJB
    private DocumentLoaderLocal loader;

    @EJB
    private PrivateDatabaseManagerLocal db;

    @Inject
    private EBizGenericInvoker invoker;

    public DocumentInfo handle(Document document) {
        String request = buildSignCommand(document.getId());
        return signOnlineOrSync(db, loader, invoker, document, request);
    }

    public String buildSignCommand(Long id) {
        pe.labtech.einvoice.core.ws.model.Document target = new pe.labtech.einvoice.core.ws.model.Document();
        Document entity = loader.loadForWork(id, source -> {
            map(source, () -> target, DocumentItem::new, (d, il) -> d.setItems(il));
        });
        return ServiceCommons.BUILDER.buildSign(
                Tools.buildClientID(entity.getClientId()), entity.getDocumentType(),
                "PDF", true, true, false, "",
                Arrays.asList(target)
        );
    }
}
