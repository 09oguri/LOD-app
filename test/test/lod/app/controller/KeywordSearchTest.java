package test.lod.app.controller;

import static org.junit.Assert.fail;

import lod.app.controller.KeywordSearch;

import org.junit.Ignore;
import org.junit.Test;

public class KeywordSearchTest {
    private final String debugConfigFile = "./config/lodac_local_dbpedia_local.properties";
    private final String webConfigFile = "./config/lodac_web_dbpedia_web.properties";
    private final String keyword = "葛飾北斎";

    private KeywordSearch ks;

    @Test
    public void searchLocalTest() {
        try {
            this.ks = new KeywordSearch(debugConfigFile);
            ks.search(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Ignore
    public void searchWebTest() {
        try {
            this.ks = new KeywordSearch(webConfigFile);
            ks.search(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
