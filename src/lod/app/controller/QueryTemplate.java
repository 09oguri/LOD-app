package lod.app.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

public class QueryTemplate {
    private QueryTemplate() {
    }

    public static Query toQuery(String filepath, String... variables) {
        String textQuery = getTextQuery(filepath);
        String queryStr = replaceVars(textQuery, variables);
        Query query = QueryFactory.create(queryStr);
        return query;
    }

    private static String getTextQuery(String filepath) {
        Path path = Paths.get(filepath);
        String textQuery;
        try {
            byte[] bytes = Files.readAllBytes(path);
            textQuery = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            textQuery = "";
        }
        return textQuery;
    }

    private static String replaceVars(String textQuery, String[] vars) {
        String queryStr = textQuery;
        for (String var : vars) {
            queryStr = queryStr.replaceFirst("<<.+>>", var);
        }
        return queryStr;
    }
}
