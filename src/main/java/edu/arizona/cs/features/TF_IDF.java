package edu.arizona.cs.features;

import edu.arizona.cs.datasuite.BodyHelper;
import edu.arizona.cs.datasuite.TfIdfHelper;
import edu.arizona.cs.datasuite.TokenizerLemmatizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jasmine on 05/05/2017.
 */
public class TF_IDF extends AbstractFeature {


    public void calculate() {
        String headline=article.headline;
        String body= BodyHelper.getInstance().getBody(article.bodyID);
        Set<String> headlineTokens = TokenizerLemmatizer.getInstance().tokenizeLemmatize(headline);
        List<String> bodyTokens = TokenizerLemmatizer.getInstance().tokenizeLemmatize2(body);
        Map<String, Integer> bodyTokenCount = new HashMap<String, Integer>();

        for (String token : bodyTokens) {
            if(bodyTokenCount.containsKey(token)) {
                int count = bodyTokenCount.get(token);
                count++;
                bodyTokenCount.put(token, count);
            } else {
                int count = 1;
                bodyTokenCount.put(token, count);
            }
        }

        double tfidf = 0;
        for (String token : headlineTokens) {
            float freq = 0;
            if(bodyTokenCount.containsKey(token)) {
                freq = bodyTokenCount.get(token) / (float)bodyTokens.size();
            }
            tfidf += TfIdfHelper.getInstance().getTfIdfScore(token, freq);
        }

        value = tfidf;
    }
}
