package edu.arizona.cs;

import de.bwaldvogel.liblinear.Parameter;
import edu.arizona.cs.classifier.DiscussChecker;
import edu.arizona.cs.classifier.RelevanceChecker;
import edu.arizona.cs.datasuite.Article;
import edu.arizona.cs.datasuite.BodyHelper;
import edu.arizona.cs.parser.Parser;
import edu.arizona.cs.parser.ScoreReporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jasmine on 05/05/2017.
 */
public class App {
    static Parser parser;
    static List<Article> articleList;
    static Map<Integer, String> bodies;
    static RelevanceChecker relevanceChecker;
    static DiscussChecker discussChecker;
    static List<Article> testArticles;

    public static void main(String[] args) {
        parser = new Parser();
        bodies = parser.parseBodies("src/main/resources/train_bodies.csv");
        articleList = parser.parseArticles("src/main/resources/train_stances_csc483583.csv");
        BodyHelper.getInstance().setBodies(bodies);
        relevanceChecker = new RelevanceChecker();
        relevanceChecker.train(articleList);

        List<Article> discussArticles = new ArrayList<Article>();

        for (Article article : articleList) {
            if(article.getStanceId() == Article.DISCUSS ||
                    article.getStanceId() == Article.AGREE || article.getStanceId() == Article.DISAGREE) {
                discussArticles.add(article);
            }
        }

        discussChecker = new DiscussChecker();
        discussChecker.train(discussArticles);

        testArticles = parser.parseArticles("src/main/resources/test_stances_csc483583.csv");
        Map<Article, Integer> results = relevanceChecker.classify(testArticles);
        int correctGuess = 0;
        int i=0;

        List<Article> testDiscussArticles = new ArrayList<Article>();
        for (Map.Entry<Article, Integer> entry : results.entrySet()) {
            if(entry.getValue() == Article.UNRELATED && testArticles.get(i).getStanceId() == Article.UNRELATED) {
                correctGuess++;
            } else if(entry.getValue() != Article.UNRELATED && testArticles.get(i).getStanceId() != Article.UNRELATED) {
                correctGuess++;
            }

            if(entry.getValue() != Article.UNRELATED) {
                // for next classifier
                testDiscussArticles.add(testArticles.get(i));
            }
            i++;
        }

        System.out.println("1: result: " + correctGuess + " : " + testArticles.size());

        Map<Article, Integer> discussResults = discussChecker.classify(testDiscussArticles);

        correctGuess = 0;
        i=0;
        for (Map.Entry<Article, Integer> entry : discussResults.entrySet()) {
            if(entry.getValue() == testDiscussArticles.get(i).getStanceId()) {
                correctGuess++;
            }

            //results.put(entry.getKey(), entry.getValue());
            i++;
        }
        results.putAll(discussResults);

        System.out.println("2: result: " + correctGuess + " : " + testDiscussArticles.size());

        ScoreReporter scoreReporter = new ScoreReporter();
        scoreReporter.reportScore(testArticles, results);

    }
}