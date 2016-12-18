package com.tyq_code.compiler;

import java.util.LinkedList;

class Definition {
    enum Token_Type {
        OPERATOR, NUMBER, FUNC, T
    }

    enum Func_Type {
        SIN, COS,
    }

    enum Oper_Type {
        PLUS, MINUS, MUL, DIV, POWER, L_BRACKET, R_BRACKET
    }

    enum Error_Type {
        SYNTAX_ERROR, EXPRESSION_ERROR
    }

    static final double PI = 3.1415926;
    static final double E = 2.71828;

    static double origin_x = 0;
    static double origin_y = 0;
    static double rot = 0;
    static double scale_x = 1;
    static double scale_y = 1;
    static double T;
    static double from;
    static double to;
    static double step;
    static double draw_x;
    static double draw_y;

    static Drawer drawer = new Drawer();
    static LinkedList<Node> list = new LinkedList<>();
    static int line = 0;
}
