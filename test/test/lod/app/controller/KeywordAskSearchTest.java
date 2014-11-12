package test.lod.app.controller;

import static org.junit.Assert.fail;
import lod.app.controller.KeywordAskSearch;
import org.junit.Test;

public class KeywordAskSearchTest {
    private final String askConfigFile = "./config/ask_cache_lodac_dbpedia.properties";
    private final String keyword = "葛飾北斎";

    private KeywordAskSearch ks;

    @Test
    public void searchAskTest() {
        try {
            this.ks = new KeywordAskSearch(askConfigFile);
            ks.search(keyword);
        } catch (Exception e) {
            fail();
        }
    }

}
