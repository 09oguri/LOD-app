package lod.app.controller;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import lod.app.model.QueryExecutor;
import lod.app.view.InputKeyword;

public class KeywordSearch {
    InputKeyword ik;

    public KeywordSearch() {
        this.ik = new InputKeyword();
    }

    public void search() {
        String keyword = ik.input();
        search(keyword);
    }

    public void search(String keyword) {
        Query query = QueryTemplate.select("?s", "?p", keyword);

        QueryExecutor qexecutor = new QueryExecutor("192.168.33.10", 3030,
                "data");
        ResultSet rs = qexecutor.execQuery(query);

        ik.output(rs);
    }

    public void searchCreates(String keyword) {
        long startTime = System.currentTimeMillis();

        String kw = "\"" + keyword + "\"@ja";
        String lodacQueryStr = "PREFIX crm: <http://purl.org/NET/cidoc-crm/core#>\nPREFIX dc: <http://purl.org/dc/terms/>\nPREFIX lodac: <http://lod.ac/ns/lodac#>\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n\nselect ?title ?locLabel\nwhere {\n?s rdfs:label "
                + kw
                + " .\n?s lodac:creates ?work .\n?work dc:title ?title .\n?work crm:P55_has_current_location ?loc .\n?loc rdfs:label ?locLabel .\n}\n";
        Query lodacQuery = QueryFactory.create(lodacQueryStr);

        // Executor Local
        QueryExecutor lodacQexecutor = new QueryExecutor("192.168.33.10", 3030,
                "data");
        QueryExecutor dbpediaQexecutor = new QueryExecutor("192.168.33.11",
                3030, "data");

        // Executor Web
        QueryExecutor lodacWebQexecutor = new QueryExecutor(
                "http://lod.ac/sparql");
        QueryExecutor dbpediaWebQexecutor = new QueryExecutor(
                "http://dbpedia.org/sparql");

        // ResultSet lodacRs = lodacQexecutor.execQuery(lodacQuery);
        ResultSet lodacRs = lodacWebQexecutor.execWebQuery(lodacQuery);
        ResultSet[] dbpediaRs = new ResultSet[100];
        int index = 0;
        while (lodacRs.hasNext()) {
            QuerySolution qs = lodacRs.next();
            RDFNode node = qs.get("locLabel");

            String locLabel = node.toString();
            locLabel = "\"" + locLabel.substring(0, locLabel.length() - 3)
                    + "\"@ja";
            String dbpediaQueryStr = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nPREFIX dbpedia: <http://dbpedia.org/ontology/>\n\nselect ?s ?abstract\nwhere {\n?s rdfs:label "
                    + locLabel + " .\n?s dbpedia:abstract ?abstract . \n}\n";
            Query dbpediaQuery = QueryFactory.create(dbpediaQueryStr);
            dbpediaRs[index] = dbpediaQexecutor.execQuery(dbpediaQuery);
            // dbpediaRs[index] =
            // dbpediaWebQexecutor.execWebQuery(dbpediaQuery);
            index++;
        }

        long endTime = System.currentTimeMillis();

        ik.output(lodacRs);
        for (int i = 0; i < index; i++) {
            ik.output(dbpediaRs[i]);
        }

        System.out.println("dbpedia: " + index);
        System.out.println(endTime - startTime + "[ms]");
    }
}
