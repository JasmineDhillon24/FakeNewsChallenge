package edu.arizona.cs.features;

import edu.arizona.cs.datasuite.BodyHelper;
import edu.arizona.cs.datasuite.TokenizerLemmatizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jasminedhillon on 5/9/17.
 */
public class Bigram extends AbstractFeature {
    public void calculate() {
        String headline=article.headline;
        String body= BodyHelper.getInstance().getBody(article.bodyID);
        List<String> headlineTokens = TokenizerLemmatizer.getInstance().tokenizeLemmatize2(headline);
        List<String> bodyTokens = TokenizerLemmatizer.getInstance().tokenizeLemmatize2(body);

        List<String> headlineBigrams = new ArrayList<String>();
        List<String> bodyBigrams = new ArrayList<String>();

        int i=0;
        for (String token : headlineTokens) {
            StringBuffer sb = new StringBuffer();
            sb.append(token);
            if(i+1<=headlineTokens.size()-1) {
                sb.append(" ");
                sb.append(headlineTokens.get(i+1));
            }
            headlineBigrams.add(sb.toString());
            i++;
        }

        i=0;
        for (String token : bodyTokens) {
            StringBuffer sb = new StringBuffer();
            sb.append(token);
            if(i+1<=bodyTokens.size()-1) {
                sb.append(" ");
                sb.append(bodyTokens.get(i+1));
            }
            bodyBigrams.add(sb.toString());
            i++;
        }

        int count=0;
        for (String token : bodyBigrams) {
            if(headlineBigrams.contains(token))
                count++;
        }

        value = count;

    }
}
