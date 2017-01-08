package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by timur
 */
class FastReader implements AutoCloseable {
    public static final String END_CODE = "-1";
    private boolean eof;
    private BufferedReader bufferedReader;
    private StringTokenizer stringTokenizer;

    FastReader(String name) {
        InputStream input = System.in;
        try {
            File file = new File(name);
            if (file.exists() && file.canRead()) {
                input = new FileInputStream(file);
            }
        } catch (Throwable ignored) {
        }
        bufferedReader = new BufferedReader(new InputStreamReader(input));
    }

    boolean hasNext() {
        try {
            return !eof || (stringTokenizer.hasMoreElements() && bufferedReader.ready());
        } catch (IOException e) {
            return false;
        }
    }

    String nextToken() {
        while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
            try {
                stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            } catch (Exception e) {
                eof = true;
                return END_CODE;
            }
        }
        return stringTokenizer.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(nextToken());
    }

    double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
    }
}
