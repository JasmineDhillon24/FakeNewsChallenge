package edu.arizona.cs.features;

import edu.arizona.cs.datasuite.BodyHelper;
import edu.arizona.cs.datasuite.TokenizerLemmatizer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jasminedhillon on 5/6/17.
 */
public class Jaccards extends AbstractFeature {
    public void calculate() {
        String headline=article.headline;
        String body= BodyHelper.getInstance().getBody(article.bodyID);
        Set<String> headlineTokens = TokenizerLemmatizer.getInstance().tokenizeLemmatize(headline);
        Set<String> bodyTokens = TokenizerLemmatizer.getInstance().tokenizeLemmatize(body);

        int count=0;
        for (String token : headlineTokens) {
            if(bodyTokens.contains(token))
                count++;
        }

        Set<String> all = new HashSet<String>();
        all.addAll(headlineTokens);
        all.addAll(bodyTokens);


        value = count != 0 ? count / (double)all.size() : count;
    }
}
