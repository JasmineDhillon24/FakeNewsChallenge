package edu.arizona.cs.features;

import edu.arizona.cs.datasuite.Article;

/**
 * Created by jasmine on 05/05/2017.
 */
public abstract class AbstractFeature {
   protected Article article;
   protected double value;

    public void setArticle(Article article){
        this.article = article;
    }

    public double getValue() {
        return value;
    }
    public abstract void calculate();



}
