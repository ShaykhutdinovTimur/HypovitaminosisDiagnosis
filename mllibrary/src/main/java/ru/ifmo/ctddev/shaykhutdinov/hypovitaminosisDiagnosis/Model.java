package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis;

import java.io.InputStream;

/**
 * @author amir.
 */
public interface Model {

    double getResult(InputStream photo);
    double learn(InputStream photo, double result);

}
