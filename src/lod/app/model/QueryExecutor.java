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
    private String timeout;

    public QueryExecutor(String address, int port, String dataset) {
        this.address = address;
        this.port = port;
        this.dataset = dataset;
        this.timeout = "10000";
    }

    private QueryExecution createQueryExecution(Query query) {
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://"
                + address + ":" + port + "/" + dataset + "/sparql", query);
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
}
