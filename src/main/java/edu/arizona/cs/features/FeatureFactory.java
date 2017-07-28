package edu.arizona.cs.features;

import edu.arizona.cs.datasuite.Article;

/**
 * Created by jasmine on 05/05/2017.
 */
public class FeatureFactory {
    private static FeatureFactory ourInstance = new FeatureFactory();

    public static final int FEATURE_TF_IDF = 0;
    public static final int FEATURE_AGREE = 1;
    public static final int FEATURE_DISAGREE = 2;
    public static final int FEATURE_DISCUSS = 3;
    public static final int FEATURE_JACCARDS = 4;
    public static final int FEATURE_COMMON_WORD = 5;
    public static final int FEATURE_BIGRAM = 6;
    public static final int FEATURE_TRIGRAM = 7;

    public static FeatureFactory getInstance() {
        return ourInstance;
    }

    private FeatureFactory() {
    }

    public AbstractFeature createFeature(int whichFeature, Article article) {
        AbstractFeature feature = null;
        switch (whichFeature) {
            case FEATURE_TF_IDF:
                feature = new TF_IDF();
                break;
            case FEATURE_DISCUSS:
                feature = new DiscussFeature();
                break;
            case FEATURE_AGREE:
                feature = new AgreeFeature();
                break;
            case FEATURE_DISAGREE:
                feature= new DisagreeFeature();
                break;
            case FEATURE_COMMON_WORD:
                feature = new CommonWordFeature();
                break;
            case FEATURE_BIGRAM:
                feature = new Bigram();
                break;
            case FEATURE_TRIGRAM:
                feature = new Trigram();
                break;
            case FEATURE_JACCARDS:
                feature = new Jaccards();
                break;
        }
        if(feature != null) {
            feature.setArticle(article);
            feature.calculate();
        }
        return feature;
    }
}
