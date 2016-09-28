package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis;

import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.RegSMOImproved;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.IOException;

/**
 * Created by timur
 */
public class RegressorInvocator {

    public static void main(String[] args) {
        new RegressorInvocator().run();
    }


    SMOreg regression;
    CSVLoader loader;
    Instances i;

    public void run() {
        loader = new CSVLoader();
        try {
            loader.setSource(getClass().getClassLoader().getResourceAsStream("fissureBase.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        regression = new SMOreg();
        regression.setKernel(new PolyKernel());
        regression.setRegOptimizer(new RegSMOImproved());
        try {
            i = loader.getDataSet();
            i.setClassIndex(0);
            regression.buildClassifier(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
