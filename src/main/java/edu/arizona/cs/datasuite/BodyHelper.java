package edu.arizona.cs.datasuite;

import java.util.Map;

/**
 * Created by jasmine on 05/05/2017.
 */
public class BodyHelper {
    private static BodyHelper ourInstance = new BodyHelper();
    private Map<Integer, String> bodies;

    public static BodyHelper getInstance() {
        return ourInstance;
    }

    private BodyHelper() {
    }

    public void setBodies(Map<Integer, String> bodies) {
        this.bodies = bodies;
    }

    public String getBody(int body_id) {
        return bodies.get(body_id);
    }
}
