package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author amir.
 */
public class Processor {
    private RegressorInvocator regression = new RegressorInvocator();

    /**
     * gets jpeg image as input stream and returns result
     */
    public double getResult(InputStream is) throws Exception {
        BufferedImage image = ImageIO.read(is);
        Instance ins = new DenseInstance(6);
        double[] features = ImageProcessor.processMono(image);
        ArrayList<Attribute> atts = new ArrayList<>();
        atts.add(regression.i.attribute(0));
        for (int i = 1; i < 6; i++) {
            ins.setValue(regression.i.attribute(i), features[i - 1]);
            atts.add(regression.i.attribute(i));
        }
        Instances dataUnlabeled = new Instances("TestInstances", atts, 0);
        dataUnlabeled.add(ins);
        dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1);
        return regression.regression.classifyInstance(dataUnlabeled.firstInstance());
    }

}
