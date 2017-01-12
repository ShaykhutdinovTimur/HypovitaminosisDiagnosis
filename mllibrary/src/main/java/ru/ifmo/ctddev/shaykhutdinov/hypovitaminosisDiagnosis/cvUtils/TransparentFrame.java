package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.cvUtils;

import ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.ImageProcessor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by timur
 */
public class TransparentFrame {

    static void generate(int height, int width) {
        try {
            BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = dest.createGraphics();
            g2.setPaint(new Color(0, 0, 0, 50));
            g2.fillRect(0, 0, width, height);

            g2.setPaint(new Color(0, 0, 0, 0));
            g2.fillRect(width / 2 - ImageProcessor.CENTER_SIZE / 2,
                    height / 2 - ImageProcessor.CENTER_SIZE / 2,
                    ImageProcessor.CENTER_SIZE, ImageProcessor.CENTER_SIZE);

            File outputFile = new File("Frame.png");
            ImageIO.write(dest, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generate(400, 500);
    }
}
