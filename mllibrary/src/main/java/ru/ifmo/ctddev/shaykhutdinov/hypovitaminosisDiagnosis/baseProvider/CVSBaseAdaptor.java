package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by timur
 */
class CVSBaseAdaptor {
    private FileWriter writer;
    private int headerCount;
    private int cur;

    void setWriter(FileWriter writer) {
        this.writer = writer;
    }

    public FileWriter getWriter() {
        return writer;
    }

    public int getHeaderCount() {
        return headerCount;
    }

    void setHeaderCount(int headerCount) {
        this.headerCount = headerCount;
    }

    CVSBaseAdaptor() {
    }

    public CVSBaseAdaptor(FileWriter writer, int headerCount) {
        this.writer = writer;
        this.headerCount = headerCount;
        cur = 0;
    }

    void append2(double a) {
        try {
            cur = (cur + 1) % headerCount;
            if (cur != 0) {
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
            cur = (cur + 1) % headerCount;
            if (cur != 0) {
                writer.write(a.toString() + ",");
            } else {
                writer.write(a.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean ready() {
        return (writer != null);
    }

    boolean finish() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (cur == 0);
    }
}
