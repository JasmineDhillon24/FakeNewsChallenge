package edu.arizona.cs.datasuite;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by jasmine on 05/05/2017.
 */
public class TfIdfHelper {
    private static TfIdfHelper ourInstance = new TfIdfHelper();
    private static final int DOC_COUNT = 10000000;

    public static TfIdfHelper getInstance() {
        return ourInstance;
    }

    private Map<String, Integer> idf_map;
    private TfIdfHelper() {
    idf_map=new HashMap<String, Integer>();

        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/idf.txt"));
            String line;
            while((line = bf.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                idf_map.put(tokenizer.nextToken(), Integer.parseInt(tokenizer.nextToken()));
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getTfIdfScore(String word, float tf) {
        int df = 0;
        if(idf_map.containsKey(word)) {
            df = idf_map.get(word);
        }
        double idf = 0;
        if(df != 0) {
            idf = Math.log(DOC_COUNT / (double) df);
        }
        double wtf = 0;
        if(tf != 0) {
            wtf = Math.log(tf) + 1;
        }
        return wtf * idf;
    }

}
