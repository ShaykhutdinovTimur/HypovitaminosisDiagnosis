package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis;

import weka.classifiers.meta.AdditiveRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by timur
 */
public class RegressorInvocator {
    public RegressorInvocator() {
    }

    public static void main(String[] args) {
        new RegressorInvocator().run();
    }

    AdditiveRegression regression;
    CSVLoader loader;
    Instances i;

    public void run() {
        loader = new CSVLoader();
        try {
            loader.setSource(new FileInputStream("fissureBase.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        regression = new AdditiveRegression();
        try {
            i = loader.getDataSet();
            i.setClassIndex(0);
            regression.buildClassifier(i);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
