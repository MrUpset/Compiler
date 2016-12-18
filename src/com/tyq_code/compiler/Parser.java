package com.tyq_code.compiler;

import java.util.LinkedList;

class Parser {
    private String str;
    private static int index;
    private int len;
    private LinkedList<Node> list;
    private Calculator calc = new Calculator();

    Parser(String str) {
        this.str = str;
        this.index = 0;
        this.len = str.length();
        this.list = Definition.list;
    }

    /**
     * 递归下降分析
     * E -> {A}
     * A -> B{(+|-)B}
     * B -> C{(*|/)C}
     * C -> D**D
     * D -> FUNC(A)|(A)|T|NUM|-D|+D|E|PI|BLANK
     */

    void analysis() throws MyException{
        try {
            if (str.matches("ORIGIN.*")) {
                origin_statement();
            } else if (str.matches("ROT.*")) {
                rot_statement();
            } else if (str.matches("SCALE.*")) {
                scale_statement();
            } else if (str.matches("FOR.*")) {
                for_statement();
            } else {
                throw new MyException(Definition.Error_Type.SYNTAX_ERROR);
            }
        } catch (Exception e) {
            throw new MyException(Definition.Error_Type.SYNTAX_ERROR);
        }
    }

    private void origin_statement() throws MyException {
        System.out.println("ORIGIN START");
        index = 11; // "ORIGIN IS (".length()
        if (str.charAt(index) == ' ') index++;
        Definition.origin_x = match_expression();
        if (str.charAt(index) == ',') index++;
        if (str.charAt(index) == ' ') index++;
        Definition.origin_y = match_expression();
        if (str.charAt(index) == ')') index++;
        if (str.charAt(index) == ' ') index++;
        if (str.charAt(index) == ';') System.out.println("ORIGIN END");
        else throw new MyException(Definition.Error_Type.SYNTAX_ERROR);
    }

    private void rot_statement() throws MyException {
        System.out.println("ROT START");
        index = 7; // "ROT IS ".length()
        Definition.rot = match_expression();
        if (str.charAt(index) == ';')
            System.out.println("ROT END");
        else throw new MyException(Definition.Error_Type.SYNTAX_ERROR);
    }

    private void scale_statement() throws MyException {
        System.out.println("SCALE START");
        index = 10; // "SCALE IS (".length()
        if (str.charAt(index) == ' ') index++;
        Definition.scale_x = match_expression();
        if (str.charAt(index) == ',') index++;
        if (str.charAt(index) == ' ') index++;
        Definition.scale_y = match_expression();
        if (str.charAt(index) == ')') index++;
        if (str.charAt(index) == ' ') index++;
        if (str.charAt(index) == ';') System.out.println("SCALE END");
        else throw new MyException(Definition.Error_Type.SYNTAX_ERROR);
    }

    private void for_statement() throws MyException { // 绘图
        System.out.println("FOR START");
        index = 11; // "FOR T FROM ".length()
        Definition.from = match_expression();
        index += 3; // "TO ".length()
        Definition.to = match_expression();
        index += 5; // "STEP ".length()
        Definition.step = match_expression();
        index += 4; // "DRAW".length()
        if (str.charAt(index) == ' ') index++;
        index += 1; // "(".length()
        if (str.charAt(index) == ' ') index++;
        int i = index;
        for (Definition.T = Definition.from; Definition.T < Definition.to; Definition.T += Definition.step) {
            index = i;
            Definition.draw_x = match_expression();
            if (str.charAt(index) == ',') index++;
            if (str.charAt(index) == ' ') index++;
            Definition.draw_y = match_expression();
            Definition.drawer.drawPoint(Definition.draw_x, Definition.draw_y);
        }
        if (str.charAt(index) == ')') index++;
        if (str.charAt(index) == ' ') index++;
        if (str.charAt(index) == ';') System.out.println("FOR END");
        else throw new MyException(Definition.Error_Type.SYNTAX_ERROR);
    }

    private double match_expression() throws MyException {
        if (!list.isEmpty()) list.clear();
//        System.out.println("    Match Expression Start");
        match_A();
//        for (Node n : list) {
//            System.out.print("        " + n.tag + ": ");
//            if (n.tag == Definition.Token_Type.NUMBER) System.out.println("  " + n.number);
//            else if (n.tag == Definition.Token_Type.OPERATOR) System.out.println(n.operator);
//            else System.out.println("    " + n.func_name);
//        }
//        System.out.println("    Match Expression End");

//        System.out.println("    Expression Change To Postfix Start");
        list = calc.parse(list);
//        for (Node n : list) {
//            System.out.print("        " + n.tag + ": ");
//            if (n.tag == Definition.Token_Type.NUMBER) System.out.println("  " + n.number);
//            else if (n.tag == Definition.Token_Type.OPERATOR) System.out.println(n.operator);
//            else System.out.println("    " + n.func_name);
//        }
//        System.out.println("    Expression Change To Postfix End");

//        System.out.println("    Expression Calculation Start");
        double ans = calc.calculate(list);
//        System.out.println("        Answer = " + ans);
//        System.out.println("    Expression Calculation End");
        return ans;
    }

    private void match_A() throws MyException {
        match_B();
        while (str.charAt(index) == '+' || str.charAt(index) == '-') {
//            System.out.println(str.charAt(index));
            if (str.charAt(index) == '+')
                list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.PLUS));
            else if (str.charAt(index) == '-')
                list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.MINUS));
            index++;
            if (str.charAt(index) == ' ') index++;
            match_B();
        }
    }

    private void match_B() throws MyException {
        match_C();
        while (str.charAt(index) == '*' || str.charAt(index) == '/') {
//            System.out.println(str.charAt(index));
            if (str.charAt(index) == '*')
                list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.MUL));
            else if (str.charAt(index) == '/')
                list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.DIV));
            index++;
            if (str.charAt(index) == ' ') index++;
            match_C();
        }
    }

    private void match_C() throws MyException {
        match_D();
        if (str.charAt(index) == '*' && str.charAt(index + 1) == '*') {
//            System.out.println("**");
            list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.POWER));
            index += 2;
            if (str.charAt(index) == ' ') index++;
            match_D();
        }
    }

    private void match_D() throws MyException {
        char[] chs = str.toCharArray();
        if (chs[index] == '(') { // (id)
            index++;
            list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.L_BRACKET));
            if (chs[index] == ' ') index++;
            match_A();
            if (chs[index] == ')') {
                list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.R_BRACKET));
                if (index < len - 1) index++;
            }
            if (index < len && chs[index] == ' ') index++;

        } else if (chs[index] >= '0' && chs[index] <= '9') { // digit
            int i = index;
            do {
//                System.out.print(chs[index]);
                index++;
            } while ((chs[index] >= '0' && chs[index] <= '9') || chs[index] == '.');
            char[] num = new char[index - i + 1];
            for (int j = i; j < index; j++)
                num[j - i] = chs[j];
            num[index - i] = '\0';
            double d;
            try{
                d = new Double(new String(num));
            } catch (Exception e){
                throw new MyException(Definition.Error_Type.EXPRESSION_ERROR);
            }
            list.add(new Node(Definition.Token_Type.NUMBER, d));
            if (chs[index] == ' ') index++;
//            System.out.println();

        } else if (chs[index] == '-') { // -D
//            System.out.print('-');
            list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.L_BRACKET));
            list.add(new Node(Definition.Token_Type.NUMBER, 0d));
            list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.MINUS));
            index++;
            match_D();
            list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.R_BRACKET));

        } else if (chs[index] == '+') { // +D
//            System.out.print('+');
            index++;
            match_D();

        } else if (chs[index] == ' ') { // blank
            index++;
            match_D();

        } else if (chs[index] == 'C') { // cos(A)
            if (chs[index + 1] == 'O') {
                if (chs[index + 2] == 'S') {
                    index += 3;
//                    System.out.println("COS");
                    list.add(new Node(Definition.Token_Type.FUNC, Definition.Func_Type.COS));
                    if (chs[index] == ' ') index++;
                    if (chs[index] == '(') {
                        index++;
                        list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.L_BRACKET));
                    }
                    if (chs[index] == ' ') index++;
                    match_A();
                    if (chs[index] == ')') {
                        list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.R_BRACKET));
                        if (index < len - 1) index++;
                    }
                    if (index < len && chs[index] == ' ') index++;
                }
            }
        } else if (chs[index] == 'E') { // E
//            System.out.println('E');
            list.add(new Node(Definition.Token_Type.NUMBER, Definition.E));
            index++;
        } else if (chs[index] == 'P') { // PI
            if (chs[index + 1] == 'I') {
                index += 2;
//                System.out.println("PI");
                list.add(new Node(Definition.Token_Type.NUMBER, Definition.PI));
            }
        } else if (chs[index] == 'S') { // sin(A)
            if (chs[index + 1] == 'I') {
                if (chs[index + 2] == 'N') {
                    index += 3;
//                    System.out.println("SIN");
                    list.add(new Node(Definition.Token_Type.FUNC, Definition.Func_Type.SIN));
                    if (chs[index] == ' ') index++;
                    if (chs[index] == '(') {
                        index++;
                        list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.L_BRACKET));
                    }
                    if (chs[index] == ' ') index++;
                    match_A();
                    if (chs[index] == ')') {
                        list.add(new Node(Definition.Token_Type.OPERATOR, Definition.Oper_Type.R_BRACKET));
                        if (index < len - 1) index++;
                    }
                    if (index < len && chs[index] == ' ') index++;
                }
            }
        } else if (chs[index] == 'T') { // T
//            System.out.println('T');
            list.add(new Node(Definition.Token_Type.T, Definition.T));
            index++;
        }
    }

    static int getIndex() {
        return index;
    }
}