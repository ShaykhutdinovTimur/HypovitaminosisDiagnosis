package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

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
                int x = originalImage.getRGB(j, i) ;
                result[cur] = (x >> 16) & 0xff;
                cur++;
            }
        }
        return result;
    }

    /*
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
    */

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

    public static String[] getHeaderC() {
        String[] result = new String[2501];
        result[0] = "clazz";
        for (int i = 0; i < 2500; i++) {
                result[i + 1] = "i" + String.valueOf(i);
            }
        return result;
    }

    public static int[] processC(BufferedImage originalImage) {
        int[] result = new int[2500];
        int topLeft = (originalImage.getHeight() - 100) / 2,
                topLeft2 = (originalImage.getWidth() - 100) / 2;
        int cur = 0;
        for (int tl1 = topLeft; tl1 < topLeft + 100; tl1 += 4) {
            for (int tl2 = topLeft2; tl2 < topLeft2 + 100; tl2 += 4) {
                int dx = 0, dy = 0, vx = 0, vy = 0;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        dx += (originalImage.getRGB(tl2 + j, tl1 + i + 1) >> 16) & 0xff -
                                (originalImage.getRGB(tl2 + j, tl1 + i - 1) >> 16) & 0xff;
                        vx += Math.abs((originalImage.getRGB(tl2 + j, tl1 + i + 1) >> 16) & 0xff -
                                (originalImage.getRGB(tl2 + j, tl1 + i - 1) >> 16) & 0xff);
                        dy += (originalImage.getRGB(tl2 + j + 1, tl1 + i) >> 16) & 0xff -
                                (originalImage.getRGB(tl2 + j - 1, tl1 + i) >> 16) & 0xff;
                        vy += Math.abs((originalImage.getRGB(tl2 + j + 1, tl1 + i) >> 16) & 0xff -
                                (originalImage.getRGB(tl2 + j - 1, tl1 + i) >> 16) & 0xff);
                    }
                }
                //int x = originalImage.getRGB(j, i) ;

                result[cur] = dx * vx;
                result[cur + 1] = dy * vy;
                result[cur + 2] = vx * dx;
                result[cur + 3] = vy * dy;
                cur += 4;
            }
        }
        return result;
    }



    public static String[] getHeaderD() {
        String[] result = new String[801];
        result[0] = "clazz";
        for (int i = 0; i < 800; i++) {
            result[i + 1] = "i" + String.valueOf(i);
        }
        return result;
    }

    public static int[] processD(BufferedImage originalImage) {
        int[] result = new int[800];
        int topLeft = (originalImage.getHeight() - 100) / 2,
                topLeft2 = (originalImage.getWidth() - 100) / 2;
        int cur = 0;
        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0}, dy = {-1, 0, 1, 1, 1, 0, -1, -1};
        for (int tl1 = topLeft; tl1 < topLeft + 100; tl1 += 10) {
            for (int tl2 = topLeft2; tl2 < topLeft2 + 100; tl2 += 10) {
                for (int ddx = 0; ddx < 10; ddx++) {
                    for (int ddy = 0; ddy < 10; ddy++) {
                        int c = 0, t = 0;
                        for (int i = 0; i < 8; i++) {
                            int x = (originalImage.getRGB(tl2 + ddx + dx[i], tl1 + dy[i] + ddy) >> 16) & 0xff -
                                    (originalImage.getRGB(tl2 + ddx, tl1 + ddy) >> 16) & 0xff;
                            if (x > c) {
                                t = i;
                                c = x;
                            }
                        }
                        result[cur + t] += c;
                    }
                }
                cur += 8;
            }
        }
        return result;
    }


    public static String[] getHeaderMono() {
        String[] result = new String[6];
        result[0] = "clazz";
        for (int i = 0; i < 5; i++) {
            result[i + 1] = "i" + String.valueOf(i);
        }
        return result;
    }

    private static int colour(int r, int g, int b) {
        return ((int)( r * 0.5 + g * 0.4 + b * 0.1));
    }


    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("data/fissure/52.jpg"));
            processMono(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double[] processMono(BufferedImage originalImage) {
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        int[][] result = new int[height][width], sum = new int[height][width];
        int ccount = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                int pixel = originalImage.getRGB(w, h);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                result[h][w] = colour(red, green, blue);
                if (result[h][w] > 70) {
                    ccount++;
                } else if (result[h][w] > 0) {
                    result[h][w] = 0;
                }
                sum[h][w] = result[h][w];
                if (h > 0) {
                    sum[h][w] += sum[h - 1][w];
                }
                if (w > 0) {
                    sum[h][w] += sum[h][w - 1];
                }
                if (h > 0 && w > 0) {
                    sum[h][w] -= sum[h - 1][w - 1];
                }
            }
        }

        int fcount = 0;
        double mdif = 0.0;
        {
            int S = width / 8;
            int s2 = S / 2;
            double t = 0.1;
            long summ = 0;
            int count = 0;
            int x1, y1, x2, y2;


            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    x1 = i - s2;
                    x2 = i + s2;
                    y1 = j - s2;
                    y2 = j + s2;

                    if (x1 < 0)
                        x1 = 0;
                    if (x2 >= width)
                        x2 = width - 1;
                    if (y1 < 0)
                        y1 = 0;
                    if (y2 >= height)
                        y2 = height - 1;

                    count = (x2 - x1) * (y2 - y1);

                    summ = sum[y2][x2] - sum[y1][x2] -
                            sum[y2][x1] + sum[y1][x1];
                    if (result[j][i] > 0 && (long) (result[j][i] * count) < (long) (summ * (1.0 - t))) {
                        mdif = Math.max(mdif, 1 - ((double) result[j][i]) * count / summ);
                        result[j][i] = 0;
                        fcount++;
                    } else
                        result[j][i] = 255;
                }
            }
        }


        double [] res = new double[5];
        res[0] = ((double) fcount) / ccount;
        res[1] = mdif;
        int max = 0;
        int[] dx = {-1, 0, 1, 0, -1, -1, 1, 1};
        int[] dy = {0, -1, 0, 1, 1, -1, -1, 1};
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (result[i][j] == 0) {
                    int ffcount = 0;
                    Queue<Integer> q = new ArrayDeque<>();
                    q.add(i * height + width);
                    result[i][j] = 1;
                    while (!q.isEmpty()) {
                        int x = q.poll();
                        int ix = x % height;
                        int iy = x / height;
                        ffcount++;
                        for (int t = 0; t < 8; t++) {
                            if (iy + dy[t] > 0 && iy + dy[t] < height &&
                                    ix + dx[t] > 0 && ix + dx[t] < width) {
                                if (result[iy + dy[t]][ix + dx[t]] == 0) {
                                    result[iy + dy[t]][ix + dx[t]] = 1;
                                    q.add((iy + dy[t]) * height + ix + dx[t]);
                                }
                            }
                        }
                    }
                    max = Math.max(max, ffcount);
                }
            }
        }
        res[2] = ((double) max) / ccount;
        fcount = 0;
        max = 0;
        double lim = ccount * 0.00025;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (result[i][j] == 1) {
                    int ffcount = 0;
                    Queue<Integer> q = new ArrayDeque<>();
                    q.add(i * height + width);
                    result[i][j] = 2;
                    while (!q.isEmpty()) {
                        int x = q.poll();
                        int ix = x % height;
                        int iy = x / height;
                        ffcount++;
                        for (int t = 0; t < 4; t++) {
                            if (iy + dy[t] > 0 && iy + dy[t] < height &&
                                    ix + dx[t] > 0 && ix + dx[t] < width) {
                                if (result[iy + dy[t]][ix + dx[t]] == 1) {
                                    result[iy + dy[t]][ix + dx[t]] = 2;
                                    q.add((iy + dy[t]) * height + ix + dx[t]);
                                }
                            }
                        }
                        if (ffcount > lim) {
                            fcount += ffcount;
                        }
                    }
                    max = Math.max(max, ffcount);
                }
            }
        }
        res[3] = ((double) max) / ccount;
        res[4] = ((double) fcount) / ccount;
        for (int i = 0; i < res.length; i++) {
            res[i] *= 100.0;
        }
        return res;
    }
}
