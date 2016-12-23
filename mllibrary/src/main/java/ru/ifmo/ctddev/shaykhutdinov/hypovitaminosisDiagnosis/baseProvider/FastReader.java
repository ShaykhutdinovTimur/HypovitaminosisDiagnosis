package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by timur
 */
class FastReader {
    private boolean eof;
    private BufferedReader br;
    private StringTokenizer st;
    private String name;

    FastReader(String name) {
        this.name = name;
    }

    boolean hasNext() {
        try {
            return !eof || (st.hasMoreElements() && br.ready());
        } catch (IOException e) {
            return false;
        }
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return "-1";
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    private String nextLine() throws IOException {
        return br.readLine();
    }

    void start() throws IOException {
        InputStream input = System.in;
        try {
            File f = new File(name);
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
            }
        } catch (Throwable ignored) {
        }
        br = new BufferedReader(new InputStreamReader(input));
    }

    void finish() {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
