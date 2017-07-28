package edu.arizona.cs.parser;

import edu.arizona.cs.datasuite.Article;

import java.util.List;
import java.util.Map;

/**
 * Created by jasminedhillon on 5/7/17.
 */
public class ScoreReporter {

    public void reportScore(List<Article> articles, Map<Article, Integer> result) {
        float max = 0;
        float nullS = 0;
        float test = 0;

        int i=0;
        for (Article article : articles) {
            test += computeScore(result.get(article), article.getStanceId());
            i++;
        }

        for (Article article : articles) {
            max += computeScore(article.getStanceId(), article.getStanceId());
        }

        for(Article article : articles) {
            nullS += computeScore(Article.UNRELATED, article.getStanceId());
        }

        System.out.println("Max score: " + max);
        System.out.println("Null score: " + nullS);
        System.out.println("Test score: " + test);

        int unrealtedUnrelated = 0;
        int unrelatedDiscuss = 0;
        int unrelatedAgree = 0;
        int unrelatedDisagree = 0;

        int discussUnrelated = 0;
        int discussDiscuss = 0;
        int discussAgree = 0;
        int discussDisagree = 0;

        int agreeUnrelated = 0;
        int agreeDiscuss = 0;
        int agreeAgree = 0;
        int agreeDisagree = 0;

        int disagreeUnrelated = 0;
        int disagreeDiscuss = 0;
        int disagreeAgree = 0;
        int disagreeDisagree = 0;

        i=0;
        for (Article article : articles) {
            int predictedStance = result.get(article);
            int actualStance = article.getStanceId();

            if(predictedStance == Article.UNRELATED) {
                switch (actualStance) {
                    case Article.UNRELATED:
                        unrealtedUnrelated++;
                        break;

                    case Article.AGREE:
                        unrelatedAgree++;
                        break;

                    case Article.DISAGREE:
                        unrelatedDisagree++;
                        break;

                    case Article.DISCUSS:
                        unrelatedDiscuss++;
                        break;
                }
            } else if(predictedStance == Article.AGREE) {
                switch (actualStance) {
                    case Article.UNRELATED:
                        agreeUnrelated++;
                        break;

                    case Article.AGREE:
                        agreeAgree++;
                        break;

                    case Article.DISAGREE:
                        agreeDisagree++;
                        break;

                    case Article.DISCUSS:
                        agreeDiscuss++;
                        break;
                }
            } else if(predictedStance == Article.DISAGREE) {
                switch (actualStance) {
                    case Article.UNRELATED:
                        disagreeUnrelated++;
                        break;

                    case Article.AGREE:
                        disagreeAgree++;
                        break;

                    case Article.DISAGREE:
                        disagreeDisagree++;
                        break;

                    case Article.DISCUSS:
                        disagreeDiscuss++;
                        break;
                }
            } else {
                switch (actualStance) {
                    case Article.UNRELATED:
                        discussUnrelated++;
                        break;

                    case Article.AGREE:
                        discussAgree++;
                        break;

                    case Article.DISAGREE:
                        discussDisagree++;
                        break;

                    case Article.DISCUSS:
                        discussDiscuss++;
                        break;
                }
            }
            i++;
        }

        System.out.println("Confusion matrix: ");
        System.out.println("\t\t\t\tActual");
        System.out.println("\t\t\t\t\t\t\t\t\tUnrelated\tDiscuss\tAgree\tDisagree");
        System.out.print("Predicted");
        System.out.println("\t\tUnrelated\t\t" + unrealtedUnrelated + "\t\t" + unrelatedDiscuss + "\t\t" + unrelatedAgree + "\t\t" + unrelatedDisagree);
        System.out.println("\t\t\t\tDiscuss\t\t\t" + discussUnrelated + "\t\t\t" + discussDiscuss + "\t\t" + discussAgree + "\t\t" + discussDisagree);
        System.out.println("\t\t\t\tAgree\t\t\t" + agreeUnrelated + "\t\t\t" + agreeDiscuss + "\t\t" + agreeAgree + "\t\t" + agreeDisagree);
        System.out.println("\t\t\t\tDisagree\t\t" + disagreeUnrelated + "\t\t\t" + disagreeDiscuss + "\t\t" + disagreeAgree + "\t\t" + disagreeDisagree);

        System.out.println("FNC score:" + (float)(test/max));

        int correctGuess = (agreeAgree + disagreeDisagree + discussDiscuss + unrealtedUnrelated);
        System.out.println("Accuracy:" + (float)(correctGuess/(float)result.size()));
    }

    private float computeScore(int guessedRes, int correctRes) {
        float score = 0;
        if (correctRes == Article.UNRELATED) {
            if(guessedRes == Article.UNRELATED) {
                score = 0.25f;
            } else {
                score = 0;
            }
        } else {
            if(guessedRes != Article.UNRELATED) {
                score = 0.25f;
            }

            if(guessedRes == correctRes) {
                score += 0.5f;
            }
        }
        return score;
    }

}
