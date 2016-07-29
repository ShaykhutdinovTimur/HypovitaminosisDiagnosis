package ru.ifmo.ctddev.shaykhutdinov.HypovitaminosisDiagnosis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
/**
 * Created by timur
 */
public class SimpleBImageProcessor {
    public static void main(String[] args) {
        try {

            FileWriter outM = new FileWriter("base.csv");
            outM.write("clazz");
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    outM.write("," + (i * 100 + j));
                }
            }
            for (int tr = 0; tr < 2; tr++) {
                for (int iter = 0; iter < 7; iter++) {
                    outM.write("\n" + tr);
                    BufferedImage originalImage = ImageIO.read(new File("data/simpleB/" + tr + "" + iter + ".jpeg"));
                    int topLeft = (originalImage.getHeight() - 100) / 2,
                            topLeft2 = (originalImage.getWidth() - 100) / 2;
                    //FileWriter writer = new FileWriter(tr + "" + iter + ".txt");
                    System.out.println(tr + "" + iter);
                    for (int i = topLeft; i < topLeft + 100; i++) {
                        for (int j = topLeft2; j < topLeft2 + 100; j++) {
                            int x = originalImage.getRGB(j, i) % (256 * 256);
                            x = x - x % 256;
                            outM.write("," + x);
                        }
                    }
                    //ImageIO.write(originalImage, "jpg", new File("GU.jpg"));
                }
            }

            outM.flush();
            outM.close();
            System.out.println("poneslas");
            FileWriter outG = new FileWriter("test.csv");
            outG.write("clazz");
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    outG.write("," + (i * 100 + j));
                }
            }
            for (int tr = 1; tr < 2; tr++) {
                for (int iter = 7; iter < 10; iter++) {
                    outG.write("\n" + tr);
                    BufferedImage originalImage = ImageIO.read(new File("data/simpleB/" + tr + "" + iter + ".jpeg"));
                    int topLeft = (originalImage.getHeight() - 100) / 2,
                            topLeft2 = (originalImage.getWidth() - 100) / 2;
                    //FileWriter writer = new FileWriter(tr + "" + iter + ".txt");
                    System.out.println(tr + "" + iter);
                    for (int i = topLeft; i < topLeft + 100; i++) {
                        for (int j = topLeft2; j < topLeft2 + 100; j++) {
                            int x = originalImage.getRGB(j, i) % (256 * 256);
                            x = x - x % 256;
                            outG.write("," + x);
                        }
                    }
                    //ImageIO.write(originalImage, "jpg", new File("GU.jpg"));
                }
            }
            outG.flush();
            outG.close();
            System.out.print("USPOKOIZA");
        } catch (IOException e) {
            System.out.println("LOSHARA SLAVIC");
            System.out.println(e.getMessage());

        }
    }
}
