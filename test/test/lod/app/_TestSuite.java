package test.lod.app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.lod.app.controller.KeywordAskSearchTest;
import test.lod.app.controller.KeywordSearchTest;
import test.lod.app.view.InputKeywordTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ InputKeywordTest.class, KeywordAskSearchTest.class, KeywordSearchTest.class, })
public class _TestSuite {

}
