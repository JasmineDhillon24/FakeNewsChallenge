package edu.arizona.cs.datasuite;

/**
 * Created by jasmine on 05/05/2017.
 */
public class Article {
    public String headline;
    public int bodyID;
    public String stance;

    public static final int UNRELATED=1;
    public static final int DISCUSS=2;
    public static final int AGREE=3;
    public static final int DISAGREE=4;
    public int getStanceId() {
        if(stance.compareToIgnoreCase("unrelated") == 0) {
            return UNRELATED;
        } else if(stance.compareToIgnoreCase("discuss") == 0) {
            return DISCUSS;
        } else if(stance.compareToIgnoreCase("agree") == 0) {
            return AGREE;
        } else if(stance.compareToIgnoreCase("disagree") == 0) {
            return DISAGREE;
        } else {
            return 0;
        }
    }

    public static String getStanceString(int stance) {
        switch (stance) {
            case UNRELATED:
                return "unrelated";
            case DISCUSS:
                return "discuss";
            case AGREE:
                return "agree";
            case DISAGREE:
                return "disagree";
        }
        return "";
    }
}
