package lod.app.model;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class QueryExecutor {
    private final String serviceURI;
    private final String timeout;

    public QueryExecutor(String serviceURI, String timeout) {
        this.serviceURI = serviceURI;
        this.timeout = timeout;
    }

    public ResultSet execQuery(Query query) {
        QueryExecution qexec = createQueryExecution(query);
        ResultSet rs = qexec.execSelect();
        rs = ResultSetFactory.copyResults(rs);
        qexec.close();
        return rs;
    }

    private QueryExecution createQueryExecution(Query query) {
        QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceURI,
                query);
        ((QueryEngineHTTP) qexec).addParam("timeout", timeout);
        return qexec;
    }
}
