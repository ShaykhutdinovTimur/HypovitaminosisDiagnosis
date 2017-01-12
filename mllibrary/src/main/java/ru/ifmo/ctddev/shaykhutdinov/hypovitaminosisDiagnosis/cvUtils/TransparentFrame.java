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

    static void generate(int height, int width, int centerY, int centerX, int size, int frameWidth) {
        try {
            BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = dest.createGraphics();
            g2.setPaint(new Color(0, 0, 0, 0));
            g2.fillRect(0, 0, width, height);
            g2.setPaint(new Color(255, 255, 255, 190));
            g2.fillRect(centerX - size, centerY - size, size * 2, frameWidth);
            g2.fillRect(centerX - size, centerY - size, frameWidth, size * 2);
            g2.fillRect(centerX + size - frameWidth, centerY - size,
                    frameWidth, size * 2);
            g2.fillRect(centerX - size, centerY + size - frameWidth,
                    size * 2, frameWidth);

           /* g2.setPaint(new Color(0, 0, 0, 0));
            g2.fillRect(width / 2 - ImageProcessor.CENTER_SIZE / 2,
                    height / 2 - ImageProcessor.CENTER_SIZE / 2,
                    ImageProcessor.CENTER_SIZE, ImageProcessor.CENTER_SIZE);
            */
            File outputFile = new File("server/src/main/resources/windowCutter.png");
            ImageIO.write(dest, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generate(400, 500, 200, 250, 75, 5);
    }
}
