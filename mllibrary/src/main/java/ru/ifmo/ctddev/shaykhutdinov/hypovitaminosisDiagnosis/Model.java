package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis;

import java.io.InputStream;

/**
 * @author amir.
 */
public interface Model {

    /**
     * gets jpeg image as input stream and returns result
     */
    double getResult(InputStream photo) throws Exception;

    /**
     * gets jpeg image as input stream and trains model on it
     */
    void train(InputStream photo, double characteristic);

}
