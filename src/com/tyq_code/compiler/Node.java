package com.tyq_code.compiler;

class Node {
    Definition.Token_Type tag;
    Definition.Oper_Type operator;
    Definition.Func_Type func_name;
    double number;

    Node(Definition.Token_Type tag, Definition.Func_Type func_name) {
        this.tag = tag;
        this.func_name = func_name;
    }

    Node(Definition.Token_Type tag, Definition.Oper_Type operator) {
        this.tag = tag;
        this.operator = operator;
    }

    Node(Definition.Token_Type tag, double number) {
        this.tag = tag;
        this.number = number;
    }

}
