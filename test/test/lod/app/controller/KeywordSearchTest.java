package test.lod.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;

import lod.app.controller.KeywordSearch;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class KeywordSearchTest {
    private KeywordSearch ks;

    @Before
    public void setUp() throws FileNotFoundException, IOException {
        this.ks = new KeywordSearch(
                "./config/lodac_local_dbpedia_local.properties");
        // this.ks = new
        // KeywordSearch("./config/lodac_web_dbpedia_web.properties");
    }

    @Test
    @Ignore
    public void searchTest() {
        String expected = "abc";
        String actual = "abc";

        ks.search("J.K. Rowling");

        assertThat(actual, is(expected));
    }

    @Test
    public void searchCreatesTest() {
        String expected = "abc";
        String actual = "abc";

        ks.search("葛飾北斎");

        assertThat(actual, is(expected));
    }
}
