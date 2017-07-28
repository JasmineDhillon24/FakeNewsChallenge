package edu.arizona.cs.features;

import edu.arizona.cs.datasuite.BodyHelper;
import edu.arizona.cs.datasuite.TokenizerLemmatizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jasminedhillon on 5/6/17.
 */
public class CommonWordFeature extends AbstractFeature {
    public void calculate() {
        String headline=article.headline;
        String body= BodyHelper.getInstance().getBody(article.bodyID);
        Set<String> headlineTokens = TokenizerLemmatizer.getInstance().tokenizeLemmatize(headline);
        List<String> bodyTokens = TokenizerLemmatizer.getInstance().tokenizeLemmatize2(body);

        int count=0;
        for (String token : bodyTokens) {
            if(headlineTokens.contains(token))
                count++;
        }

        value = count; /// (double)headlineTokens.size();
    }
}
