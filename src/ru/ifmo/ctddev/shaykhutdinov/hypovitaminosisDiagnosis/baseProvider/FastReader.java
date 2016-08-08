package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by timur
 */
public class FastReader {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    String name;

    public FastReader(String name) {
        this.name = name;
    }

    public boolean hasNext() {
        try {
            return !eof || (st.hasMoreElements() && br.ready());
        } catch (IOException e) {
            return false;
        }
    }

    public String nextToken() {
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

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    public double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    private String nextLine() throws IOException {
        return br.readLine();
    }

    public void start() throws IOException {
        InputStream input = System.in;
        try {
            File f = new File(name);
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
    }

    public void finish() {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
