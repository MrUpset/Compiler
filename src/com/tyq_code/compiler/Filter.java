package com.tyq_code.compiler;

class Filter {
    String work(String string){
        String str = string;
        char ch;
        int len = str.length();
        boolean matched = false;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<len; i++){ //去除注释和行首空白，以及字间多于一个的空白
            ch = str.charAt(i);
            if (ch == '-'){
                matched = true;
                if(i+1<len && str.charAt(i+1) == '-'){
                    break;
                } else {
                    sb.append('-');
                }
            }
            else if (ch == ' '){
                if (matched)
                    if (str.charAt(i+1) != ' ')
                        sb.append(ch);
            }
            else {
                matched = true;
                sb.append(ch);
            }
        }
        str = sb.toString();
        len = str.length();
        sb = new StringBuilder();
        int i;
        for (i=len-1; i>=0; i--) //去除行末多余的空白
            if (str.charAt(i) != ' '&& str.charAt(i) != '\t') break;
        for (int j=0; j<=i; j++)
            sb.append(str.charAt(j));
        str = sb.toString();
        return str;
    }
}
