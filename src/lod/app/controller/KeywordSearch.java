package lod.app.controller;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;

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
}
