package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by timur
 */
public class ImageProvider {
    private String sourceDir;
    private double characteristics;
    private BufferedImage image;
    private int cur;
    FastReader info;

    public double getCharacteristics() {
        return characteristics;
    }

    public BufferedImage getImage() {
        return image;
    }

    public ImageProvider() {
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
        this.cur = 0;
        info = new FastReader(this.sourceDir + "info.txt");
    }

    public ImageProvider(String sourceDir) {
        setSourceDir(sourceDir);
    }

    public void ready() {
        try {
            if (info != null) {
                info.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void next() {
        try {
            image = ImageIO.read(new File(sourceDir + (cur + 1) + ".jpg"));
            cur++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        characteristics = info.nextDouble();
    }

    public void finish() {
        if (info != null) {
            info.finish();
        }
    }
}
