package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by timur
 */
public class CVSBaseAdaptor {
    private FileWriter writer;
    private int headerCount;
    private int cur;

    public void setWriter(FileWriter writer) {
        this.writer = writer;
    }

    public FileWriter getWriter() {
        return writer;
    }

    public int getHeaderCount() {
        return headerCount;
    }

    public void setHeaderCount(int headerCount) {
        this.headerCount = headerCount;
    }

    public CVSBaseAdaptor() {
    }

    public CVSBaseAdaptor(FileWriter writer, int headerCount) {
        this.writer = writer;
        this.headerCount = headerCount;
        cur = 0;
    }

    public void append(Object a) {
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

    public boolean ready() {
        return (writer != null);
    }

    public boolean finish() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (cur == 0);
    }
}
