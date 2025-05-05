package org.isep.cleancode.calculator;

import java.util.ArrayList;
import java.util.Stack;

public class Calculator {

    public double evaluateMathExpression(String expression) {
        ArrayList<String> expressionList = rewriteExpression(expression);
        return evaluateExpressionList(expressionList);
    }

    private ArrayList<String> rewriteExpression(String expression) {
        // Objective : a list of String with numbers and operators
        expression = expression.replace(" ", "");
        ArrayList<String> expressionList = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();
        boolean isNegative = false;

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                currentNumber.append(ch);
            } else if (ch == '-' && (i == 0 || expression.charAt(i - 1) == '(')) {
                isNegative = true;
            } else {
                if (!currentNumber.isEmpty()) {
                    if (isNegative) {
                        currentNumber.insert(0, '-');
                        isNegative = false;
                    }
                    expressionList.add(currentNumber.toString());
                    currentNumber.setLength(0);
                }
                if (isOperator(ch) || ch == '(' || ch == ')') {
                    expressionList.add(String.valueOf(ch));
                }
            }
        }

        if (!currentNumber.isEmpty()) {
            if (isNegative) {
                currentNumber.insert(0, '-');
            }
            expressionList.add(currentNumber.toString());
        }

        return expressionList;
    }

    private double evaluateExpressionList(ArrayList<String> expressionList) {
        while (expressionList.contains("(")) {
            expressionList = evaluateParentheses(expressionList);
        }
        while (expressionList.size() > 1) {
            expressionList = evaluatePriorityOperation(expressionList);
        }
        return Double.parseDouble(expressionList.getFirst());
    }

    private ArrayList<String> evaluateParentheses(ArrayList<String> expressionList) {
        Stack<String> stack = new Stack<>();
        ArrayList<String> newExpressionList = new ArrayList<>();
        for (String element : expressionList) {
            if (element.equals("(")) {
                stack.push(element);
            } else if (element.equals(")")) {
                ArrayList<String> parenteseExpression = new ArrayList<>();
                while (!stack.peek().equals("(")) {
                    parenteseExpression.addFirst(stack.pop());
                }
                stack.pop(); // Remove '('
                double subResult = evaluateExpressionList(parenteseExpression);
                stack.push(String.valueOf(subResult));
            } else {
                stack.push(element);
            }
        }
        while (!stack.isEmpty()) {
            newExpressionList.add(stack.pop());
        }
        ArrayList<String> reversedExpressionList = new ArrayList<>();
        for (int i = newExpressionList.size() - 1; i >= 0; i--) {
            reversedExpressionList.add(newExpressionList.get(i));
        }
        return reversedExpressionList;
    }

    private ArrayList<String> evaluatePriorityOperation(ArrayList<String> expressionList) {
        int operatorIndex = findPriorityOperationIndex(expressionList);
        if (operatorIndex == -1) {
            return expressionList; // No more operators to evaluate
        }

        double leftOperand = Double.parseDouble(expressionList.get(operatorIndex - 1));
        double rightOperand = Double.parseDouble(expressionList.get(operatorIndex + 1));
        char operator = expressionList.get(operatorIndex).charAt(0);
        double result = applyOperator(leftOperand, rightOperand, operator);

        ArrayList<String> newExpressionList = new ArrayList<>();
        for (int i = 0; i < operatorIndex - 1; i++) {
            newExpressionList.add(expressionList.get(i));
        }
        newExpressionList.add(String.valueOf(result));
        for (int i = operatorIndex + 2; i < expressionList.size(); i++) {
            newExpressionList.add(expressionList.get(i));
        }

        return newExpressionList;
    }

    private int findPriorityOperationIndex(ArrayList<String> expressionList) {
        int mulDivIndex = findPriorityIndex(expressionList);
        if (mulDivIndex != -1) {
            return mulDivIndex;
        }
        return findMinorIndex(expressionList);
    }

    private int findPriorityIndex(ArrayList<String> expressionList) {
        for (int i = 0; i < expressionList.size(); i++) {
            char ch = expressionList.get(i).charAt(0);
            if (ch == '*' || ch == '/') {
                return i;
            }
        }
        return -1;
    }

    private int findMinorIndex(ArrayList<String> expressionList) {
        for (int i = 0; i < expressionList.size(); i++) {
            char ch = expressionList.get(i).charAt(0);
            if (ch == '+' || ch == '-') {
                return i;
            }
        }
        return -1;
    }

    private double applyOperator(double left, double right, char operator) {
        return switch (operator) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            case '/' -> left / right;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }
}
