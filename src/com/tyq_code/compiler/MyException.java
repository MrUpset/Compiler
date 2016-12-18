package com.tyq_code.compiler;

class MyException extends Exception {
    MyException(String message) {
        System.out.println("Line " + Definition.line + ": " + message);
    }

    MyException(Definition.Error_Type e) {
        System.out.print("Line " + Definition.line + ": ");
        if (e == Definition.Error_Type.EXPRESSION_ERROR) {
            System.out.println("Expression Error at Column: " + (Parser.getIndex() - 1) + " !");
        } else if (e == Definition.Error_Type.SYNTAX_ERROR) {
            System.out.println("Syntax Error !");
        }
        System.exit(-1);
    }
}
