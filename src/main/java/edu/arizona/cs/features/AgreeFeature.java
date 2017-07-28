package edu.arizona.cs.features;

import edu.arizona.cs.datasuite.BodyHelper;
import edu.arizona.cs.datasuite.TokenizerLemmatizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jasmine on 06/05/2017.
 */
public class AgreeFeature extends AbstractFeature {

    private Set<String> agree_words;
    public AgreeFeature() {
        agree_words = new HashSet<String>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/agree_words.txt"));
            String line;
            while((line = bf.readLine()) != null) {
            agree_words.add(line);
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculate() {


        String body= BodyHelper.getInstance().getBody(article.bodyID);
        Set<String> bodyTokens = TokenizerLemmatizer.getInstance().tokenizeLemmatize(body);
        int count=0;
        for (String token : bodyTokens) {
            if(agree_words.contains(token))
            {
                count++;
            }
        }
        value= count;
    }
}
