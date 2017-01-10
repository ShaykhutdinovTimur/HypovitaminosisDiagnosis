package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis;

import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.RegSMOImproved;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by timur
 */
public class RegressorInvocator implements Model {
    SMOreg regression;
    Instances i;

    public RegressorInvocator() {
        CSVLoader loader = new CSVLoader();
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

    @Override
    public void train(InputStream inputStream, double characteristic) {
        try {
            BufferedImage image = ImageIO.read(inputStream);
            Instance ins = new DenseInstance(6);
            double[] features = ImageProcessor.processMono(image);
            ArrayList<Attribute> atts = new ArrayList<>();
            atts.add(i.attribute(0));
            ins.setValue(0, characteristic);
            for (int j = 1; j < 6; j++) {
                ins.setValue(i.attribute(j), features[j - 1]);
                atts.add(i.attribute(j));
            }
            i.add(ins);
            regression.buildClassifier(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getResult(InputStream is) throws Exception {
        BufferedImage image = ImageIO.read(is);
        Instance ins = new DenseInstance(6);
        double[] features = ImageProcessor.processMono(image);
        ArrayList<Attribute> atts = new ArrayList<>();
        atts.add(i.attribute(0));
        for (int j = 1; j < 6; j++) {
            ins.setValue(i.attribute(j), features[j - 1]);
            atts.add(i.attribute(j));
        }
        Instances dataUnlabeled = new Instances("TestInstances", atts, 0);
        dataUnlabeled.add(ins);
        dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1);
        return regression.classifyInstance(dataUnlabeled.firstInstance());
    }
}
