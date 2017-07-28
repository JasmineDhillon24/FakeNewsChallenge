package edu.arizona.cs.datasuite;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.PropertiesUtils;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by jasmine on 05/05/2017.
 */
public class TokenizerLemmatizer {
    private static TokenizerLemmatizer ourInstance = new TokenizerLemmatizer();

    public static TokenizerLemmatizer getInstance() {
        return ourInstance;
    }

    private StanfordCoreNLP pipeline;
    private boolean isStemmingEnabled = true;
    private boolean isLemmatizationEnabled = false;
    private PorterStemmer stemmer;

    private TokenizerLemmatizer() {
        pipeline = new StanfordCoreNLP(
                PropertiesUtils.asProperties(
                        "annotators", "tokenize,ssplit,pos,lemma,parse,natlog",
                        "ssplit.isOneSentence", "false",
                        "tokenize.language", "en"));
        stemmer = new PorterStemmer();
    }

    public Set<String> tokenizeLemmatize(String text) {

        if(isLemmatizationEnabled) {
            Annotation annotation = new Annotation(text);
            pipeline.annotate(annotation);

            List<CoreLabel> coreLabels = annotation.get(CoreAnnotations.TokensAnnotation.class);

            StringBuilder sb = new StringBuilder();
            for (CoreLabel coreLabel : coreLabels) {
                sb.append(coreLabel.get(CoreAnnotations.LemmaAnnotation.class) + " ");
            }

            text = sb.toString();
        }

        Set<String> tokens = new HashSet<String>();


        StandardTokenizer tokenizer = new StandardTokenizer();
        tokenizer.setReader(new StringReader(text/*sb.toString()*/));

        try {
            TokenStream tokenStream = new StopFilter(
                    new ASCIIFoldingFilter(new ClassicFilter(new LowerCaseFilter(tokenizer))),
                    EnglishAnalyzer.getDefaultStopSet());
            tokenStream.reset();

            while (tokenStream.incrementToken()) {
                String token = tokenStream.getAttribute(CharTermAttribute.class).toString();

                if(isStemmingEnabled) {
                    stemmer.setCurrent(token);
                    stemmer.stem();
                    token = stemmer.getCurrent();
                }

                tokens.add(token);
            }
            tokenStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    public List<String> tokenizeLemmatize2(String text) {
        if(isLemmatizationEnabled) {
            Annotation annotation = new Annotation(text);
            pipeline.annotate(annotation);

            List<CoreLabel> coreLabels = annotation.get(CoreAnnotations.TokensAnnotation.class);

            StringBuilder sb = new StringBuilder();
            for (CoreLabel coreLabel : coreLabels) {
                sb.append(coreLabel.get(CoreAnnotations.LemmaAnnotation.class) + " ");
            }

            text = sb.toString();
        }

        List<String> tokens = new ArrayList<String>();


        StandardTokenizer tokenizer = new StandardTokenizer();
        tokenizer.setReader(new StringReader(text/*sb.toString()*/));

        try {
            TokenStream tokenStream = new StopFilter(
                    new ASCIIFoldingFilter(new ClassicFilter(new LowerCaseFilter(tokenizer))),
                    EnglishAnalyzer.getDefaultStopSet());
            tokenStream.reset();

            while (tokenStream.incrementToken()) {
                String token = tokenStream.getAttribute(CharTermAttribute.class).toString();

                if(isStemmingEnabled) {
                    stemmer.setCurrent(token);
                    stemmer.stem();
                    token = stemmer.getCurrent();
                }

                tokens.add(token);
            }
            tokenStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }

}
