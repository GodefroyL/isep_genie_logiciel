// A rendre compte.florian@gmail.com

package org.isep.cleancode.calculator;

public class Calculator {
    public double evaluateMathExpression(String expression) {
        return 0;
    }

    private List rewriteExpression(String expression) {
        expression = expression.replace(" ","");
        List<String> expressionList = new List();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                currentNumber.append(ch);
            } else if (isOperator(ch)) {
                expressionList.add(currentNumber.toString());
                currentNumber.setLength(0);
                }
                expressionList.add(ch);
            }
        return expressionList
    }

    private evaluateExpressionList(List expressionList) {
        double resultat = 0
        for (int i = 0; i<expressionList.length(); i++) {
            char ch = expressionList.charAt(i)

            if (isOperator(ch)) {
                switch(ch) {
                    case '+':
                        resultat = resultat + 
                }
            }
        }
    } 
        
    private static boolean isOperator(char ch) {
    return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '(' || ch == ')';
    }
}
