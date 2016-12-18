package com.tyq_code.compiler;

import java.util.LinkedList;

class Calculator {

    private class Stack<T> {
        private LinkedList<T> list = new LinkedList<>();

        private void push(T t) {
            list.addLast(t);
        }

        private T pop() {
            return list.removeLast();
        }

        private T top() {
            return list.peekLast();
        }

        private boolean isEmpty() {
            return list.isEmpty();
        }
    }

    private int priority(Node node) {
        if (node.tag == Definition.Token_Type.FUNC) {
            return 4;
        } else if (node.tag == Definition.Token_Type.OPERATOR) {
            if (node.operator == Definition.Oper_Type.POWER)
                return 3;
            else if (node.operator == Definition.Oper_Type.MUL || node.operator == Definition.Oper_Type.DIV)
                return 2;
            else if (node.operator == Definition.Oper_Type.PLUS || node.operator == Definition.Oper_Type.MINUS)
                return 1;
            else
                return 0;
        }
        return 0;
    }

    private boolean leftPriorityIsNotLess(Node node1, Node node2) {
        return node1 != null && priority(node1) >= priority(node2);
    }

    private boolean isNumber(Node n) {
        return n.tag == Definition.Token_Type.NUMBER;
    }

    private boolean isT(Node n) {
        return n.tag == Definition.Token_Type.T;
    }

    private boolean isLeftBracket(Node n) {
        return n.operator == Definition.Oper_Type.L_BRACKET;
    }

    private boolean isRightBracket(Node n) {
        return n.operator == Definition.Oper_Type.R_BRACKET;
    }

    private boolean isOperator(Node n) {
        return n.tag == Definition.Token_Type.OPERATOR;
    }

    private boolean isFunction(Node n) {
        return n.tag == Definition.Token_Type.FUNC;
    }

    LinkedList<Node> parse(LinkedList<Node> list) throws MyException {
        Stack<Node> stack = new Stack<>();
        LinkedList<Node> node_list = new LinkedList<>();

        try {
            while (!list.isEmpty()) {
                Node n = list.getFirst();
                if (isNumber(n) || isT(n)) {
                    node_list.add(list.removeFirst());
                } else if (isLeftBracket(n)) {
                    stack.push(list.removeFirst());
                } else if (isRightBracket(n)) {
                    list.removeFirst();
                    while (!isLeftBracket(stack.top())) {
                        node_list.add(stack.pop());
                    }
                    stack.pop();
                } else if (isOperator(n) || isFunction(n)) {
                    while (leftPriorityIsNotLess(stack.top(), n)) {
                        node_list.add(stack.pop());
                    }
                    stack.push(list.removeFirst());
                }
            }
            while (!stack.isEmpty()) {
                node_list.add(stack.pop());
            }
        } catch (Exception e) {
            throw new MyException(Definition.Error_Type.EXPRESSION_ERROR);
        }
        return node_list;
    }

    double calculate(LinkedList<Node> list) throws MyException {
        Stack<Node> stack = new Stack<>();
        try {
            while (!list.isEmpty()) {
                Node n = list.getFirst();
                if (isNumber(n) || isT(n)) {
                    stack.push(list.removeFirst());
                } else if (isOperator(n)) {
                    Node right = stack.pop();
                    Node left = stack.pop();
                    if (n.operator == Definition.Oper_Type.PLUS)
                        left.number = left.number + right.number;
                    else if (n.operator == Definition.Oper_Type.MINUS)
                        left.number = left.number - right.number;
                    else if (n.operator == Definition.Oper_Type.MUL)
                        left.number = left.number * right.number;
                    else if (n.operator == Definition.Oper_Type.DIV)
                        left.number = left.number / right.number;
                    else if (n.operator == Definition.Oper_Type.POWER)
                        left.number = Math.pow(left.number, right.number);

                    stack.push(left);
                    list.removeFirst();
                } else if (isFunction(n)) {
                    Node node = stack.pop();
                    if (n.func_name == Definition.Func_Type.SIN)
                        node.number = Math.sin(node.number);
                    else if (n.func_name == Definition.Func_Type.COS)
                        node.number = Math.cos(node.number);

                    stack.push(node);
                    list.removeFirst();
                }
            }
        } catch (Exception e) {
            throw new MyException(Definition.Error_Type.EXPRESSION_ERROR);
        }
        return stack.pop().number;
    }
}