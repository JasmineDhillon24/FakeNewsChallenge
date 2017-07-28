package edu.arizona.cs.classifier;

import de.bwaldvogel.liblinear.*;
import edu.arizona.cs.datasuite.Article;
import edu.arizona.cs.features.AbstractFeature;
import edu.arizona.cs.features.FeatureFactory;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by jasmine on 05/05/2017.
 */
public class RelevanceChecker {

   Model relevanceModel;

   Map<Article, List<AbstractFeature>> articleFeatures=new HashMap<Article, List<AbstractFeature>>();
    public void train(List<Article> articles)
    {
        System.out.println("Training");
        for (Article article:articles) {
            //System.out.println("Feature for: " + article.headline);
            List<AbstractFeature> features= new ArrayList<AbstractFeature>();
            AbstractFeature tfIdf = FeatureFactory.getInstance().
                    createFeature(FeatureFactory.FEATURE_TF_IDF, article);
            features.add(tfIdf);
            AbstractFeature jaccards = FeatureFactory.getInstance().
                    createFeature(FeatureFactory.FEATURE_JACCARDS, article);
            features.add(jaccards);
            AbstractFeature commonWords = FeatureFactory.getInstance().
                    createFeature(FeatureFactory.FEATURE_COMMON_WORD, article);
            features.add(commonWords);
            AbstractFeature bigrams = FeatureFactory.getInstance().
                    createFeature(FeatureFactory.FEATURE_BIGRAM, article);
            features.add(bigrams);
            articleFeatures.put(article, features);
        }

        Feature x[][] = new FeatureNode[articles.size()][];
        double y[] = new double[articles.size()];

        int k=0;
        for (Article article : articles) {
            List<Pair<Integer, Double>> featureVals = new ArrayList<Pair<Integer, Double>>();

            int i=1;
            for (AbstractFeature f : articleFeatures.get(article)) {
                if(f.getValue() != 0) {
                    Pair<Integer, Double> pair = new Pair<Integer, Double>(i, f.getValue());
                    featureVals.add(pair);
                }
                i++;
            }
            Pair<Integer, Double> pair = new Pair<Integer, Double>(i, 1.);
            featureVals.add(pair);

            Collections.sort(featureVals, new Comparator<Pair<Integer, Double>>() {
                public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2) {
                    return o1.getKey() - o2.getKey();
                }
            });

            x[k] = new FeatureNode[featureVals.size()];
            int j=0;
            for (Pair<Integer, Double> pair1 : featureVals) {
                x[k][j] = new FeatureNode(pair1.getKey(), pair1.getValue());
                j++;
            }

            y[k] = article.getStanceId() == Article.UNRELATED ? 0 : 1;

            k++;
        }

        System.out.println("Model");
        Problem problem = new Problem();
        problem.bias = 1;
        problem.l = articles.size();
        problem.n = 4 + (int)problem.bias;
        problem.y = y;
        problem.x = x;

        SolverType solverType = SolverType.L2R_L2LOSS_SVC;

        Parameter parameter = new Parameter(solverType, 1, 0.001);
        relevanceModel = Linear.train(problem, parameter);
    }

     public Map<Article, Integer> classify(List<Article> test_articles){
         System.out.println("Classify");

         Map<Article, Integer> resultStances = new HashMap<Article, Integer>();

         for (Article article:test_articles) {
             List<AbstractFeature> features= new ArrayList<AbstractFeature>();
             AbstractFeature tfIdf = FeatureFactory.getInstance().
                     createFeature(FeatureFactory.FEATURE_TF_IDF, article);
             features.add(tfIdf);
             AbstractFeature jaccards = FeatureFactory.getInstance().
                     createFeature(FeatureFactory.FEATURE_JACCARDS, article);
             features.add(jaccards);
             AbstractFeature commonWords = FeatureFactory.getInstance().
                     createFeature(FeatureFactory.FEATURE_COMMON_WORD, article);
             features.add(commonWords);
             AbstractFeature bigrams = FeatureFactory.getInstance().
                     createFeature(FeatureFactory.FEATURE_BIGRAM, article);
             features.add(bigrams);
             articleFeatures.put(article, features);
         }

         Feature x[][] = new FeatureNode[test_articles.size()][];

         int k=0;
         for (Article article : test_articles) {
             //System.out.println("Feature for: " + article.headline);
             List<Pair<Integer, Double>> featureVals = new ArrayList<Pair<Integer, Double>>();

             int i=1;
             for (AbstractFeature f : articleFeatures.get(article)) {
                 if(f.getValue() != 0) {
                     Pair<Integer, Double> pair = new Pair<Integer, Double>(i, f.getValue());
                     featureVals.add(pair);
                 }
                 i++;
             }
             Pair<Integer, Double> pair = new Pair<Integer, Double>(i, 1.);
             featureVals.add(pair);

             Collections.sort(featureVals, new Comparator<Pair<Integer, Double>>() {
                 public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2) {
                     return o1.getKey() - o2.getKey();
                 }
             });

             x[k] = new FeatureNode[featureVals.size()];
             int j=0;
             for (Pair<Integer, Double> pair1 : featureVals) {
                 x[k][j] = new FeatureNode(pair1.getKey(), pair1.getValue());
                 j++;
             }

             k++;
         }

         for (int i=0;i<x.length;i++) {
             int label = (int)Linear.predict(relevanceModel, x[i]);
             resultStances.put(test_articles.get(i), label == 0 ? Article.UNRELATED : -1);
         }

         return resultStances;
     }



}
