package lod.app.model;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class QueryExecutor {
    private final String address;
    private final int port;
    private final String dataset;
    private final String serviceURI;
    private String timeout;

    public QueryExecutor(String address, int port, String dataset) {
        this.address = address;
        this.port = port;
        this.dataset = dataset;
        this.serviceURI = "";
        this.timeout = "10000";
    }

    public QueryExecutor(String serviceURI) {
        this.address = "";
        this.port = 0;
        this.dataset = "";
        this.serviceURI = serviceURI;
        this.timeout = "10000";
    }

    private QueryExecution createQueryExecution(Query query) {
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://"
                + address + ":" + port + "/" + dataset + "/sparql", query);
        ((QueryEngineHTTP) qexec).addParam("timeout", timeout);
        return qexec;
    }

    private QueryExecution createWebQueryExecution(Query query) {
        QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceURI,
                query);
        ((QueryEngineHTTP) qexec).addParam("timeout", timeout);
        return qexec;
    }

    public ResultSet execQuery(Query query) {
        QueryExecution qexec = createQueryExecution(query);
        ResultSet rs = qexec.execSelect();
        rs = ResultSetFactory.copyResults(rs);
        qexec.close();
        return rs;
    }

    public ResultSet execWebQuery(Query query) {
        QueryExecution qexec = createWebQueryExecution(query);
        ResultSet rs = qexec.execSelect();
        rs = ResultSetFactory.copyResults(rs);
        qexec.close();
        return rs;
    }
}
