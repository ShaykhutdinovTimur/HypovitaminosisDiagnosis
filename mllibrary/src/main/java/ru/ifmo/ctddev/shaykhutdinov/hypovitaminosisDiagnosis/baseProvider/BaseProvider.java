package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

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
            reader.start();
            ImageProvider imageProvider = new ImageProvider();
            CVSBaseAdaptor cvsBaseAdaptor = new CVSBaseAdaptor();
            while (reader.hasNext()) {
                String name = reader.nextToken();
                if ("-1".equals(name)) {
                    break;
                }
                String dir = "data/" + name + "/";
                imageProvider.setSourceDir(dir);
                cvsBaseAdaptor.setHeaderCount(6);
                cvsBaseAdaptor.setWriter(new FileWriter(name + "Base.csv"));
                imageProvider.ready();
                cvsBaseAdaptor.ready();
                String[] header = ImageProcessor.getHeaderMono();
                for (String aHeader : header) {
                    cvsBaseAdaptor.append(aHeader);
                }
                int count = reader.nextInt();
                for (int i = 0; i < count; i++) {
                    imageProvider.next();
                    double x = imageProvider.getCharacteristics();
                    double[] pix = ImageProcessor.processMono(imageProvider.getImage());
                    cvsBaseAdaptor.append2(x);
                    for (double aPix : pix) {
                        cvsBaseAdaptor.append2(aPix);
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
