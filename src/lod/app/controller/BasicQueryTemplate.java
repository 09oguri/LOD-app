package lod.app.controller;

import lod.app.util.Literal;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

public class BasicQueryTemplate {
    private BasicQueryTemplate() {
    }

    public static Query selectAll() {
        String queryStr = "select * where {?s ?p ?o .}";
        Query query = QueryFactory.create(queryStr);

        return query;
    }

    public static Query selectAll(int limit) {
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
        String s = isVariable(subject) ? subject : Literal.toLiteral(subject);
        String p = isVariable(predicate) ? predicate : Literal
                .toLiteral(predicate);
        String o = isVariable(object) ? object : Literal.toLiteral(object);

        String queryStr = "select * where {" + s + " " + p + " " + o + " .}";
        return queryStr;
    }

    private static boolean isVariable(String str) {
        return str.startsWith("?");
    }
}
