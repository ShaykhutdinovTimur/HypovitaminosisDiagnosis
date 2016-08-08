package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import java.io.FileWriter;

/**
 * Created by timur
 */
public class BaseProvider {
    private ImageProvider imageProvider;
    private ImageProcessor imageProcessor;
    private CVSBaseAdaptor cvsBaseAdaptor;
    private FastReader reader;

    public static final void main(String[] args) {
        new BaseProvider().run();
    }

    public void run() {
        try {
            reader = new FastReader("data/baseInfo.txt");
            reader.start();
            imageProvider = new ImageProvider();
            imageProcessor = new ImageProcessor();
            cvsBaseAdaptor = new CVSBaseAdaptor();
            while (reader.hasNext()) {
                String name = reader.nextToken();
                if (name == "-1") {
                    break;
                }
                String dir = "data/" + name + "/";
                imageProvider.setSourceDir(dir);
                cvsBaseAdaptor.setHeaderCount(10001);
                cvsBaseAdaptor.setWriter(new FileWriter(name + "Base.csv"));
                imageProvider.ready();
                cvsBaseAdaptor.ready();
                String[] header = imageProcessor.getHeaderB();
                for (int i = 0; i < header.length; i++) {
                    cvsBaseAdaptor.append(header[i]);
                }
                int count = reader.nextInt();
                for (int i = 0; i < count; i++) {
                    imageProvider.next();
                    double x = imageProvider.getCharacteristics();
                    int[] pix = imageProcessor.processB(imageProvider.getImage());
                    cvsBaseAdaptor.append(x);
                    for (int j = 0; j < 10000; j++) {
                        cvsBaseAdaptor.append(pix[j]);
                    }
                }
                imageProvider.finish();
                cvsBaseAdaptor.finish();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
