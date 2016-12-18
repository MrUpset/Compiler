package com.tyq_code.compiler;

import java.io.*;

class Main {
    public static void main(String[] args){
//        String file = "src\\regular.txt";
//        String file = "src\\circle.txt";
        String file = "src\\error.txt";
        BufferedReader br;
        String str;
        Parser parser;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((str = br.readLine()) != null) {
                Filter filter = new Filter();
                str = filter.work(str);
                Definition.line++;
                if (!str.isEmpty()) {
                    str = str.toUpperCase();
                    parser = new Parser(str);
                    //System.out.println(str);
                    parser.analysis();
                    Definition.list.clear();
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: Can\'t open the file.");
        } catch (MyException ignored) {
        }
    }
}

