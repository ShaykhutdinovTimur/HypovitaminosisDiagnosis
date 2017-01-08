package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by timur
 */
class ImageProvider implements AutoCloseable {
    private String sourceDir;
    private double characteristics;
    private BufferedImage image;
    private int currentImageNumber;
    private FastReader info;

    public ImageProvider(String sourceDir) {
        this.sourceDir = sourceDir;
        this.currentImageNumber = 0;
        info = new FastReader(this.sourceDir + "info.txt");
    }

    double getCharacteristics() {
        return characteristics;
    }

    BufferedImage getImage() {
        return image;
    }

    void next() {
        try {
            image = ImageIO.read(new File(sourceDir + (currentImageNumber + 1) + ".jpg"));
            currentImageNumber++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        characteristics = info.nextDouble();
    }

    @Override
    public void close() throws Exception {
        info.close();
    }
}
