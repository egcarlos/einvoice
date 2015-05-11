/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.console.trace;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pe.labtech.einvoice.core.entity.EventTrace;

/**
 *
 * @author Carlos
 */
@Named
@ViewScoped
public class TraceManager implements Serializable {

    @Inject
    TraceBeanLocal bean;

    private String query;
    private List<EventTrace> results;

    @PostConstruct
    public void search() {
        results = bean.search(query);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<EventTrace> getResults() {
        return results;
    }

    public void setResults(List<EventTrace> results) {
        this.results = results;
    }

    public String reduce(String text) {
        if (text == null) {
            return "<null value>";
        }
        if (text.length() > 20) {
            return text.substring(0, 20) + "...";
        }
        return text;
    }

}
