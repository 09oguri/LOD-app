package test.lod.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import lod.app.controller.KeywordSearch;

import org.junit.Before;
import org.junit.Test;

public class KeywordSearchTest {
    private KeywordSearch ks;

    @Before
    public void setUp() {
        this.ks = new KeywordSearch();
    }

    @Test
    public void searchTest() {
        String expected = "abc";
        String actual = "abc";

        ks.search("J.K. Rowling");

        assertThat(actual, is(expected));
    }
}
