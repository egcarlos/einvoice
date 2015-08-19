/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import static pe.labtech.einvoice.api.restful.RestTools.*;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.commons.entity.*;

import pe.labtech.einvoice.core.entity.*;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.sign.SignTaskLocal;
import pe.labtech.einvoice.core.ws.messages.response.ResponseMessage;
import pe.labtech.einvoice.replicator.entity.*;

/**
 *
 * @author Carlos
 */
@Stateless
public class RestHelper implements RestHelperLocal {

    private static final Logger logger = Logger.getLogger(RestHelper.class.getName());

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @EJB
    private SignTaskLocal sign;

    @Override
    public DocumentInfo find(String issuerType, String issuerId, String documentType, String documentNumber) {
        //buscar el mapeo de datos
        Long id = findDocumentId(issuerType, issuerId, documentType, documentNumber);
        //si no se encuentra retornar invalid
        if (id == null) {
            return invalid(issuerType, issuerId, documentType, documentNumber, "MISSING");
        }
        //retornar la respuesta
        return buildDocumentInfo(id);
    }

    @Override
    public DocumentInfo sign(DocumentHeader content, String source) {
        //replicar
        replicate(content, content.getItem(), "REST-API", "LOADED-WS", source,
                d -> {
                    d.setClientId(content.getId().getTipoDocumentoEmisor() + "-" + content.getId().getNumeroDocumentoEmisor());
                    d.setDocumentType(content.getId().getTipoDocumento());
                    d.setDocumentNumber(content.getId().getSerieNumero());
                },
                i -> ((DocumentDetail) i).getId().getNumeroOrdenItem()
        );
        //firmar
        Long id = findDocumentId(
                content.getId().getTipoDocumentoEmisor(),
                content.getId().getNumeroDocumentoEmisor(),
                content.getId().getTipoDocumento(),
                content.getId().getSerieNumero()
        );
        sign.handle(id);
        //retornar la respuesta
        return buildDocumentInfo(id);
    }

    @Override
    public DocumentInfo sign(SummaryHeader content, String source) {
        replicate(content, content.getItem(), "REST-API", "LOADED-WS", source,
                d -> {
                    d.setClientId(content.getId().getTipoDocumentoEmisor() + "-" + content.getId().getNumeroDocumentoEmisor());
                    d.setDocumentType("RC");
                    d.setDocumentNumber(content.getId().getResumenId());
                },
                i -> ((SummaryDetail) i).getId().getNumeroFila());
        //firmar
        Long id = findDocumentId(
                content.getId().getTipoDocumentoEmisor(),
                content.getId().getNumeroDocumentoEmisor(),
                "RC",
                content.getId().getResumenId()
        );
        sign.handle(id);
        //retornar la respuesta
        return buildDocumentInfo(id);
    }

    @Override
    public DocumentInfo sign(CancelHeader content, String source) {
        replicate(content, content.getItem(), "REST-API", "LOADED-WS", source,
                d -> {
                    d.setClientId(content.getId().getTipoDocumentoEmisor() + "-" + content.getId().getNumeroDocumentoEmisor());
                    d.setDocumentType("RC");
                    d.setDocumentNumber(content.getId().getResumenId());
                },
                i -> ((SummaryDetail) i).getId().getNumeroFila());
        //firmar
        Long id = findDocumentId(
                content.getId().getTipoDocumentoEmisor(),
                content.getId().getNumeroDocumentoEmisor(),
                "RA",
                content.getId().getResumenId()
        );
        sign.handle(id);
        //retornar la respuesta
        return buildDocumentInfo(id);
    }

    private DocumentInfo buildDocumentInfo(Long id) {
        //construir la respuesta
        DocumentInfo di = new DocumentInfo();
        prv
                .handle(e
                        -> e.createQuery(
                                "SELECT R.name, R.value FROM DocumentResponse R WHERE R.document.id = :id",
                                Object[].class
                        )
                        .setParameter("id", id)
                        .getResultList()
                        .forEach(r -> {
                            switch (r[0].toString()) {
                                case "messages":
                                    Type t = new TypeToken<LinkedList<ResponseMessage>>() {
                                    }.getType();
                                    List<ResponseMessage> list;
                                    try {
                                        list = new Gson().fromJson((String) r[1], t);
                                    } catch (JsonSyntaxException ex) {
                                        list = Arrays.asList(
                                                new ResponseMessage("500", "Check Messages", "9999", (String) r[1], null)
                                        );
                                    }
                                    tryset(di, r[0], list);
                                    break;
                                default:
                                    tryset(di, r[0], r[1]);
                            }
                        })
                );
        return di;
    }

    private Long findDocumentId(String issuerType, String issuerId, String documentType, String documentNumber) {
        //buscar el id del documento interno, si no existe el mapeo no se pudo realizar y hay que devolver invalid
        Long id = prv.seek(e -> e
                .createQuery(
                        "SELECT MAX(D.id) FROM Document D WHERE D.clientId = :clientId AND D.documentType = :documentType AND D.documentNumber = :documentNumber",
                        Long.class
                )
                //issuer type and issuer id are built in order to reflect database
                .setParameter("clientId", issuerType + "-" + issuerId)
                .setParameter("documentType", documentType)
                //for summaries prepend the document type
                .setParameter("documentNumber", (documentType.startsWith("R") ? (documentType + "-") : "") + documentNumber)
                .getSingleResult());
        return id;
    }

    public void replicate(
            //datos para replicar
            Header header,
            List<? extends Detail> details,
            //datos de control
            String step,
            String status,
            String source,
            //funcionales para agrgar comportamiento adicional
            Consumer<Document> setIdFields,
            Function<Detail, String> itemorder
    ) {
        Document document = new Document();
        setIdFields.accept(document);
        document.setHash(source);

        try {
            List<DocumentAttribute> attrs = new LinkedList<>();
            List<DocumentAuxiliar> auxs = new LinkedList<>();
            List<DocumentLegend> legs = new LinkedList<>();

            //la llave primaria se infiere dependiendo del tipo de documento
            attrs.add(new DocumentAttribute(document, "tipoDocumentoEmisor", document.getClientId().split("-")[0]));
            attrs.add(new DocumentAttribute(document, "numeroDocumentoEmisor", document.getClientId().split("-")[1]));
            switch (document.getDocumentType()) {
                case "RC":
                case "RA":
                    attrs.add(new DocumentAttribute(document, "resumenId", document.getDocumentNumber()));
                    break;
                default:
                    attrs.add(new DocumentAttribute(document, "tipoDocumento", document.getDocumentType()));
                    attrs.add(new DocumentAttribute(document, "serieNumero", document.getDocumentNumber()));
            }

            //mapeo automático del cuerpo
            final Map<String, String> bean = BeanUtils.describe(header);

            bean.entrySet().stream()
                    .filter(RestHelper::validEntry)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                    .forEach((key, value) -> {
                        logger.info(() -> MessageFormat.format("property({0},{1})", key, value));
                        if (key.matches(".+Leyenda_\\d+")) {
                            Long order = Long.parseLong(key.split("_")[1]);
                            DocumentLegend dl = buildLegend(bean, order, document);
                            if (dl.getValue() != null) {
                                legs.add(dl);
                            }
                        } else if (key.matches(".+Auxiliar\\d+_\\d+")) {
                            String s = key.substring(14);
                            String length = s.split("_")[0];
                            Long order = Long.parseLong(s.split("_")[1]);
                            DocumentAuxiliar da = buildAuxiliar(bean, length, order, document);
                            if (da.getValue() != null) {
                                auxs.add(da);
                            }
                        } else {
                            attrs.add(
                                    new DocumentAttribute(
                                            document,
                                            key,
                                            StringUtils.trimToNull(value)
                                    )
                            );

                        }
                    });

            document.setAttributes(attrs);
            document.setAuxiliars(auxs);
            document.setLegends(legs);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            logger.log(Level.SEVERE, null, ex);
            //TODO mark as error... y decidir que hacer luego con el mapeo!
        }

        List<Item> items = details.stream().map(detail -> {
            Item item = new Item();
            item.setDocument(document);
            item.setId(Long.parseLong(itemorder.apply(detail), 10));

            try {
                List<ItemAttribute> attrs = BeanUtils.describe(detail).entrySet().stream()
                        .filter(e -> e.getValue() != null)
                        .filter(e -> !"id".equals(e.getKey()))
                        .filter(e -> !"class".equals(e.getKey()))
                        .filter(e -> !e.getKey().startsWith("bl_"))
                        .map(e -> new ItemAttribute(item, e.getKey(), e.getValue()))
                        .collect(Collectors.toList());
                attrs.add(new ItemAttribute(item, "numeroOrdenItem", "" + item.getId()));
                item.setAttributes(attrs);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                logger.log(Level.SEVERE, null, ex);
                //TODO mark as error
            }
            return item;
        }).collect(Collectors.toList());
        document.setItems(items);
        document.setStep(step);
        document.setStatus(status);

        prv.handle(e -> e.persist(document));

    }

    //Metodos de soporte para el armado de datos relacionados al documento
    public DocumentAuxiliar buildAuxiliar(final Map<String, String> bean, String length, Long order, Document document) {
        DocumentAuxiliar da = new DocumentAuxiliar(
                bean.get("codigoAuxiliar" + length + "_" + order),
                length,
                order,
                bean.get("textoAuxiliar" + length + "_" + order)
        );
        da.setDocument(document);
        return da;
    }

    public DocumentLegend buildLegend(final Map<String, String> bean, Long order, Document document) {
        DocumentLegend dl = new DocumentLegend(
                bean.get("codigoLeyenda_" + order),
                order,
                bean.get("textoLeyenda_" + order),
                bean.get("textoAdicionalLeyenda_" + order)
        );
        dl.setDocument(document);
        return dl;
    }

    //metodos de soporte para el filtrado de datos en el bean
    public static boolean validEntry(Map.Entry<String, String> entry) {
        return !skippedKey(entry.getKey()) && !skippedValue(entry.getValue());
    }

    public static boolean skippedKey(String k) {
        return "id".equals(k) || "class".equals(k) || k.startsWith("bl_") || k.startsWith("textoLeyenda") || k.startsWith("textoAdicionalLeyenda") || k.startsWith("textoAuxiliar");
    }

    public static boolean skippedValue(String v) {
        return StringUtils.trimToNull(v) == null;
    }
}
