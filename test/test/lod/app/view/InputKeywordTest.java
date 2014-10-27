package test.lod.app.view;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;

import lod.app.view.InputKeyword;

public class InputKeywordTest {
    private InputKeyword ik;

    @Before
    public void setUp() {
        this.ik = new InputKeyword();
    }

    @Test
    public void inputTest() {
        String expected = "abc";

        ByteArrayInputStream inMock = new ByteArrayInputStream("abc".getBytes());
        System.setIn(inMock);
        String actual = ik.input();
        System.setIn(System.in);

        assertThat(actual, is(expected));
    }
}
