package edu.arizona.cs.parser;

import edu.arizona.cs.datasuite.Article;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jasmine on 05/05/2017.
 */
public class Parser {
    public Map<Integer, String> parseBodies(String filepath) {
        System.out.println("Parsing bodies");
        Map<Integer, String> bodies = new HashMap<Integer, String>();
        try {
            Reader reader = new FileReader(filepath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            for (CSVRecord csvRecord : csvParser) {
                int id = Integer.parseInt(csvRecord.get("Body ID"));
                String text = csvRecord.get("articleBody");
                bodies.put(id, text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bodies;
    }

    public List<Article> parseArticles(String filepath) {
        System.out.println("Parsing articles");
        List<Article> articles = new ArrayList<Article>();
        try {
            Reader reader = new FileReader(filepath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            for (CSVRecord csvRecord : csvParser) {
                int id = Integer.parseInt(csvRecord.get("Body ID"));
                String headLine = csvRecord.get("Headline");
                String stance = csvRecord.get("Stance");
                Article article = new Article();
                article.bodyID = id;
                article.headline = headLine;
                article.stance = stance;
                articles.add(article);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articles;
    }

}
