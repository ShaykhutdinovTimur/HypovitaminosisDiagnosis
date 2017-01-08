package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.ImageProcessor;

import java.io.FileWriter;

/**
 * Created by timur
 */
public class BaseProvider {

    public static void main(String[] args) {
        new BaseProvider().run();
    }

    private void run() {
        try {
            FastReader reader = new FastReader("data/baseInfo.txt");
            while (reader.hasNext()) {
                String name = reader.nextToken();
                if (FastReader.END_CODE.equals(name)) {
                    break;
                }
                int headerSize = reader.nextInt();
                CVSBaseAdaptor cvsBaseAdaptor = new CVSBaseAdaptor(new FileWriter(name + "Base.csv"), headerSize);
                String[] header = ImageProcessor.getHeaderMono();
                for (String aHeader : header) {
                    cvsBaseAdaptor.append(aHeader);
                }
                int imageCount = reader.nextInt();
                ImageProvider imageProvider = new ImageProvider("data/" + name + "/");
                for (int i = 0; i < imageCount; i++) {
                    imageProvider.next();
                    double x = imageProvider.getCharacteristics();
                    double[] pix = ImageProcessor.processMono(imageProvider.getImage());
                    cvsBaseAdaptor.append2(x);
                    for (double aPix : pix) {
                        cvsBaseAdaptor.append2(aPix);
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
