package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import java.awt.image.BufferedImage;

/**
 * Created by timur
 */
public class ImageProcessor {
    public static int[] processB(BufferedImage originalImage) {
        int[] result = new int[10000];
        int topLeft = (originalImage.getHeight() - 100) / 2,
                topLeft2 = (originalImage.getWidth() - 100) / 2;
        int cur = 0;
        for (int i = topLeft; i < topLeft + 100; i++) {
            for (int j = topLeft2; j < topLeft2 + 100; j++) {
                int x = originalImage.getRGB(j, i) % (256 * 256);
                result[cur] = x - x % 256;
                cur++;
            }
        }
        return result;
    }

    public static String[] getHeaderB() {
        String[] result = new String[10001];
        result[0] = "clazz";
        int cur = 1;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                result[cur] = "i" + String.valueOf(i * 100 + j);
                cur++;
            }
        }
        return result;
    }

    public ImageProcessor() {
    }
}
