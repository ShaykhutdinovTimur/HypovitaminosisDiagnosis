package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by timur
 */
class CVSBaseAdaptor implements AutoCloseable {
    private FileWriter writer;
    private int headerCount;
    private int currentPosition;

    public CVSBaseAdaptor(FileWriter writer, int headerCount) {
        this.writer = writer;
        this.headerCount = headerCount;
        currentPosition = 0;
    }

    void append2(double a) {
        try {
            currentPosition = (currentPosition + 1) % headerCount;
            if (currentPosition != 0) {
                writer.write(a + ",");
            } else {
                writer.write(a + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void append(Object a) {
        try {
            currentPosition = (currentPosition + 1) % headerCount;
            if (currentPosition != 0) {
                writer.write(a.toString() + ",");
            } else {
                writer.write(a.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        writer.flush();
        writer.close();
    }
}
