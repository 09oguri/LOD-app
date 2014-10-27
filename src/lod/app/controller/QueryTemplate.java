package lod.app.controller;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

public class QueryTemplate {
    private QueryTemplate() {
    }

    public static Query selectAll() {
        String queryStr = "select * where {?s ?p ?o .}";
        Query query = QueryFactory.create(queryStr);

        return query;
    }

    public static Query selectAll(int limit) {
        if (limit < 0) {
            limit = 1;
        }

        String queryStr = "select * where {?s ?p ?o .} LIMIT " + limit;
        Query query = QueryFactory.create(queryStr);

        return query;
    }

    public static Query select(String subject, String predicate, String object) {
        String queryStr = createSelectQuery(subject, predicate, object);
        Query query = QueryFactory.create(queryStr);
        return query;
    }

    public static Query select(String subject, String predicate, String object,
            int limit) {
        String queryStr = createSelectQuery(subject, predicate, object)
                + " LIMIT " + limit;
        Query query = QueryFactory.create(queryStr);
        return query;
    }

    private static String createSelectQuery(String subject, String predicate,
            String object) {
        String s = isVariable(subject) ? subject : toLiteral(subject);
        String p = isVariable(predicate) ? predicate : toLiteral(predicate);
        String o = isVariable(object) ? object : toLiteral(object);

        String queryStr = "select * where {" + s + " " + p + " " + o + " .}";
        return queryStr;
    }

    private static boolean isVariable(String str) {
        return str.startsWith("?");
    }

    private static String toLiteral(String str) {
        return "\"" + str + "\"";
    }
}
