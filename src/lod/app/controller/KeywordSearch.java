package lod.app.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import lod.app.model.QueryExecutor;
import lod.app.util.Literal;
import lod.app.view.InputKeyword;

public class KeywordSearch {
    private Logger logger = Logger.getLogger(KeywordSearch.class.getName());
    private InputKeyword ik;

    private final String lodacEndpoint;
    private final String lodacTimeout;
    private final String lodacQueryFile;

    private final String dbpediaEndpoint;
    private final String dbpediaTimeout;
    private final String dbpediaQueryFile;

    public KeywordSearch(String configFilePath) throws FileNotFoundException,
            IOException {
        PropertyConfigurator.configure("./config/logger.properties");

        Properties config = new Properties();
        config.load(new FileInputStream(configFilePath));

        this.ik = new InputKeyword();

        this.lodacEndpoint = config.getProperty("lod.lodac.endpoint");
        this.lodacTimeout = config.getProperty("lod.lodac.timeout");
        this.lodacQueryFile = config.getProperty("lod.lodac.queryfile");

        this.dbpediaEndpoint = config.getProperty("lod.dbpedia.endpoint");
        this.dbpediaTimeout = config.getProperty("lod.dbpedia.timeout");
        this.dbpediaQueryFile = config.getProperty("lod.dbpedia.queryfile");

        logger.info("lodacEndpoint: " + this.lodacEndpoint);
        logger.info("lodacTimeout: " + this.lodacTimeout + " [ms]");
        logger.info("lodacQueryFile: " + this.lodacQueryFile);

        logger.info("dbpediaEndpoint: " + this.dbpediaEndpoint);
        logger.info("dbpediaTimeout: " + this.dbpediaTimeout + " [ms]");
        logger.info("dbpediaQueryFile: " + this.dbpediaQueryFile);
    }

    public void search() {
        String keyword = ik.input();
        search(keyword);
    }

    public void search(String keyword) {
        long startTime = System.currentTimeMillis();
        logger.info("START: " + startTime + " [ms]");

        QueryExecutor lodacQexecutor = new QueryExecutor(lodacEndpoint,
                lodacTimeout);
        QueryExecutor dbpediaQexecutor = new QueryExecutor(dbpediaEndpoint,
                dbpediaTimeout);

        String kw = Literal.toJaLiteral(keyword);
        Query lodacQuery = QueryTemplate.toQuery(lodacQueryFile, kw);
        logger.info(lodacQuery.toString());

        ResultSet lodacRs = lodacQexecutor.execQuery(lodacQuery);
        ArrayList<ResultSet> dbpediaRs = new ArrayList<ResultSet>();
        int index = 0;
        while (lodacRs.hasNext()) {
            QuerySolution qs = lodacRs.next();
            RDFNode node = qs.get("locLabel");

            String locLabel = node.toString();
            locLabel = Literal.notQuoteJaToJaLiteral(locLabel);

            Query dbpediaQuery = QueryTemplate.toQuery(dbpediaQueryFile,
                    locLabel);
            logger.info(dbpediaQuery.toString());

            dbpediaRs.add(dbpediaQexecutor.execQuery(dbpediaQuery));
            index++;
        }

        long endTime = System.currentTimeMillis();
        logger.info("DBpediaAccessTimes: " + index);
        logger.info("ExecutionTime: " + (endTime - startTime) + " [ms]");
        logger.info("END: " + endTime + " [ms]");

        ik.output(lodacRs);
        ik.output(dbpediaRs);
    }
}
