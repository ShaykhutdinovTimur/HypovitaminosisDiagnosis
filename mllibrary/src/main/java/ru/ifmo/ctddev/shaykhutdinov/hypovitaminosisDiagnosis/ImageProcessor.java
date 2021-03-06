package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis;

import javafx.util.Pair;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by timur
 */
public class ImageProcessor {
    public final static double RED_VISION_FACTOR = 0.6;
    public final static double GREEN_VISION_FACTOR = 0.3;
    public final static double BLUE_VISION_FACTOR = 0.1;
    public final static int MINIMAL_INTENSITY = 70;
    public final static int CENTER_SIZE = 150;

    private static final int[] dx = {-1, 0, 1, 0, -1, -1, 1, 1};
    private static final int[] dy = {0, -1, 0, 1, 1, -1, -1, 1};


    public static String[] getHeaderMono() {
        String[] result = new String[6];
        result[0] = "clazz";
        for (int i = 0; i < 5; i++) {
            result[i + 1] = "i" + String.valueOf(i);
        }
        return result;
    }

    private static int[][] getIntensity(BufferedImage originalImage) {
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        int[][] result = new int[height][width];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                int pixel = originalImage.getRGB(w, h);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                result[h][w] = ((int) (red * RED_VISION_FACTOR +
                        green * GREEN_VISION_FACTOR +
                        blue * BLUE_VISION_FACTOR));
                if (result[h][w] < MINIMAL_INTENSITY) {
                    result[h][w] = 0;
                }
            }
        }
        return result;
    }

    private static Pair<int[][], Double> getReverseMonoMap(int[][] intensityMap) {

        int height = intensityMap.length;
        int width = intensityMap[0].length;

        int[][] prefixSum = new int[height][width],
                result = new int[height][width];

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                prefixSum[h][w] = intensityMap[h][w];
                result[h][w] = intensityMap[h][w];
                if (h > 0) {
                    prefixSum[h][w] += prefixSum[h - 1][w];
                }
                if (w > 0) {
                    prefixSum[h][w] += prefixSum[h][w - 1];
                }
                if (h > 0 && w > 0) {
                    prefixSum[h][w] -= prefixSum[h - 1][w - 1];
                }
            }
        }

        double maxIntensityDiff = 0.0;
        {
            int windowHalfSize = width / 16;
            double t = 0.1;
            long rectIntensitySum;
            int pixelCount;
            int x1, y1, x2, y2;


            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    x1 = i - windowHalfSize;
                    x2 = i + windowHalfSize;
                    y1 = j - windowHalfSize;
                    y2 = j + windowHalfSize;

                    if (x1 < 0)
                        x1 = 0;
                    if (x2 >= width)
                        x2 = width - 1;
                    if (y1 < 0)
                        y1 = 0;
                    if (y2 >= height)
                        y2 = height - 1;

                    pixelCount = (x2 - x1) * (y2 - y1);

                    rectIntensitySum = prefixSum[y2][x2] - prefixSum[y1][x2] -
                            prefixSum[y2][x1] + prefixSum[y1][x1];
                    if (result[j][i] > 0 &&
                            (long) (result[j][i] * pixelCount) < (long) (rectIntensitySum * (1.0 - t))) {
                        maxIntensityDiff = Math.max(maxIntensityDiff, 1 -
                                ((double) result[j][i]) * pixelCount / rectIntensitySum);
                        result[j][i] = 0;
                    } else
                        result[j][i] = 255;
                }
            }
        }
        return new Pair<>(result, maxIntensityDiff);
    }

    private static int getGreyCellCount(int[][] intensityMap) {
        int result = 0;
        for (int[] line : intensityMap) {
            for (int pixelIntensity : line) {
                if (pixelIntensity > 0) {
                    result++;
                }
            }
        }
        return result;
    }

    private static int markShapeSquare(int directionsCount, int[][] image, int posY, int posX) {
        int shapeSquare = 0;
        int height = image.length;
        int width = image[0].length;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(posY * width + posX);
        image[posY][posX] = 1;
        while (!q.isEmpty()) {
            int x = q.poll();
            int ix = x % width;
            int iy = x / width;
            shapeSquare++;
            for (int t = 0; t < directionsCount; t++) {
                if (iy + dy[t] > 0 && iy + dy[t] < height &&
                        ix + dx[t] > 0 && ix + dx[t] < width) {
                    if (image[iy + dy[t]][ix + dx[t]] == 0) {
                        image[iy + dy[t]][ix + dx[t]] = 1;
                        q.add((iy + dy[t]) * width + ix + dx[t]);
                    }
                }
            }
        }
        return shapeSquare;
    }

    private static int getMaxShapeSquare(int[][] image) {
        int height = image.length;
        int width = image[0].length;
        int max = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (image[i][j] == 0) {
                    int shapeSquare = markShapeSquare(8, image, i, j);
                    max = Math.max(max, shapeSquare);
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (image[i][j] == 1) {
                    image[i][j] = 0;
                }
            }
        }
        return max;
    }

    private static Pair<Double, Double> getDeepFissureFeatures(int[][] image, int objectCellsCount) {
        int height = image.length;
        int width = image[0].length;
        int max;
        int deepSquaresSum = 0;
        max = 0;
        double lim = objectCellsCount * 0.001;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (image[i][j] == 0) {
                    int deepSquare = markShapeSquare(4, image, i, j);
                    if (deepSquare > lim) {
                        deepSquaresSum += deepSquare;
                    }
                    max = Math.max(max, deepSquare);
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (image[i][j] == 1) {
                    image[i][j] = 0;
                }
            }
        }
        return new Pair<>(((double) max) / objectCellsCount, ((double) deepSquaresSum) / objectCellsCount);
    }

    private static double[] processMap(int[][] image) {
        int height = image.length;
        int width = image[0].length;

        int objectCellsCount = getGreyCellCount(image);

        Pair<int[][], Double> monoImage = getReverseMonoMap(image);
        image = monoImage.getKey();
        double maxIntensityDifference = monoImage.getValue();
        int functionalCellsCount = height * width - getGreyCellCount(image);


        double[] res = new double[5];
        res[0] = ((double) functionalCellsCount) / objectCellsCount;
        res[1] = maxIntensityDifference;
        res[2] = ((double) getMaxShapeSquare(image)) / objectCellsCount;

        Pair<Double, Double> deepFissureFeatures = getDeepFissureFeatures(image, objectCellsCount);

        res[3] = deepFissureFeatures.getKey();
        res[4] = deepFissureFeatures.getValue();
        for (int i = 0; i < res.length; i++) {
            res[i] *= 100.0;
        }
        return res;
    }

    public static double[] processMono(BufferedImage originalImage) {
        return processMap(getIntensity(originalImage));
    }

    public static double[] processCenterMono(BufferedImage originalImage) {
        int[][] image = getIntensity(originalImage);
        int height = image.length;
        int width = image[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < height / 2 - CENTER_SIZE / 2 ||
                        i > height / 2 + CENTER_SIZE / 2 ||
                        j < width / 2 - CENTER_SIZE / 2 ||
                        j > width / 2 + CENTER_SIZE / 2) {
                    image[i][j] = 0;
                }
            }
        }
        return processMap(image);
    }
}
