package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by timur
 */
class ImageProvider {
    private String sourceDir;
    private double characteristics;
    private BufferedImage image;
    private int cur;
    private FastReader info;

    double getCharacteristics() {
        return characteristics;
    }

    BufferedImage getImage() {
        return image;
    }

    ImageProvider() {
    }

    void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
        this.cur = 0;
        info = new FastReader(this.sourceDir + "info.txt");
    }

    public ImageProvider(String sourceDir) {
        setSourceDir(sourceDir);
    }

    void ready() {
        try {
            if (info != null) {
                info.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void next() {
        try {
            image = ImageIO.read(new File(sourceDir + (cur + 1) + ".jpg"));
            cur++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        characteristics = info.nextDouble();
    }

    void finish() {
        if (info != null) {
            info.finish();
        }
    }
}
