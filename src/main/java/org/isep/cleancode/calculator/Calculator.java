package org.isep.cleancode.calculator;

import java.util.ArrayList;

public class Calculator {
    public double evaluateMathExpression(String expression) {
        ArrayList<String> expressionList = rewriteExpression(expression);
        return evaluateExpressionList(expressionList);
    }

    private ArrayList<String> rewriteExpression(String expression) {
        expression = expression.replace(" ", "");
        ArrayList<String> expressionList = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                currentNumber.append(ch);
            } else if (isOperator(ch)) {
                if (currentNumber.length() > 0) {
                    expressionList.add(currentNumber.toString());
                    currentNumber.setLength(0);
                }
                expressionList.add(String.valueOf(ch));
            }
        }

        if (currentNumber.length() > 0) {
            expressionList.add(currentNumber.toString());
        }
        System.out.println(expressionList);
        return expressionList;
    }

    private double evaluateExpressionList(ArrayList<String> expressionList) {
        return evaluate(expressionList, 0, expressionList.size() - 1);
    }

    private double evaluate(ArrayList<String> expressionList, int start, int end) {
        if (start == end) {
            return Double.parseDouble(expressionList.get(start));
        }

        int operatorIndex = findOperatorIndex(expressionList, start, end);
        double leftResult = evaluate(expressionList, start, operatorIndex - 1);
        double rightResult = evaluate(expressionList, operatorIndex + 1, end);

        char operator = expressionList.get(operatorIndex).charAt(0);
        return applyOperator(leftResult, rightResult, operator);
    }

    private int findOperatorIndex(ArrayList<String> expressionList, int start, int end) {
        int index = -1;
        for (int i = start; i <= end; i++) {
            if (isOperator(expressionList.get(i).charAt(0))) {
                index = i;
                break;
            }
        }
        return index;
    }

    private double applyOperator(double left, double right, char operator) {
        return switch (operator) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            case '/' -> left / right;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }
}
