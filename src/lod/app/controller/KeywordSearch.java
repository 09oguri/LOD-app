package lod.app.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import lod.app.model.QueryExecutor;
import lod.app.util.Literal;
import lod.app.view.InputKeyword;

public class KeywordSearch {
    private InputKeyword ik;
    
    private final String lodacEndpoint;
    private final String lodacTimeout;
    private final String lodacQueryFile;
    
    private final String dbpediaEndpoint;
    private final String dbpediaTimeout;
    private final String dbpediaQueryFile;

    public KeywordSearch(String configFilePath) throws FileNotFoundException, IOException {
        Properties config = new Properties();
        config.load(new FileInputStream(configFilePath));
        
        this.ik = new InputKeyword();
        
        this.lodacEndpoint = config.getProperty("lod.lodac.endpoint");
        this.lodacTimeout = config.getProperty("lod.lodac.timeout");
        this.lodacQueryFile = config.getProperty("lod.lodac.queryfile");
        
        this.dbpediaEndpoint = config.getProperty("lod.dbpedia.endpoint");
        this.dbpediaTimeout = config.getProperty("lod.dbpedia.timeout");
        this.dbpediaQueryFile = config.getProperty("lod.dbpedia.queryfile");
    }

    public void search() {
        String keyword = ik.input();
        search(keyword);
    }

    public void search(String keyword) {
        long startTime = System.currentTimeMillis();

        String kw = Literal.toJaLiteral(keyword);
        Query lodacQuery = QueryTemplate.toQuery(lodacQueryFile, kw);

        QueryExecutor lodacQexecutor = new QueryExecutor(lodacEndpoint, lodacTimeout);
        QueryExecutor dbpediaQexecutor = new QueryExecutor(dbpediaEndpoint, dbpediaTimeout);

        ResultSet lodacRs = lodacQexecutor.execQuery(lodacQuery);
        ArrayList<ResultSet> dbpediaRs = new ArrayList<ResultSet>();
        int index = 0;
        while (lodacRs.hasNext()) {
            QuerySolution qs = lodacRs.next();
            RDFNode node = qs.get("locLabel");

            String locLabel = node.toString();
            locLabel = Literal.notQuoteJaToJaLiteral(locLabel);

            Query dbpediaQuery = QueryTemplate.toQuery(dbpediaQueryFile, locLabel);

            dbpediaRs.add(dbpediaQexecutor.execQuery(dbpediaQuery));
            index++;
        }

        long endTime = System.currentTimeMillis();

        ik.output(lodacRs);
        ik.output(dbpediaRs);
        System.out.println("dbpedia: " + index);
        System.out.println(endTime - startTime + "[ms]");
    }
}
