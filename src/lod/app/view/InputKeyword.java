package lod.app.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

public class InputKeyword implements FrontEnd {
    public InputKeyword() {
    }

    @Override
    public String input() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Keyword: ");
        try {
            String keyword = br.readLine();
            return keyword;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void output(String out) {
        System.out.println(out);
    }

    public void output(ResultSet out) {
        ResultSetFormatter.out(System.out, out);
    }

    public void output(ArrayList<ResultSet> out) {
        for (ResultSet r : out) {
            ResultSetFormatter.out(System.out, r);
        }
    }
}
