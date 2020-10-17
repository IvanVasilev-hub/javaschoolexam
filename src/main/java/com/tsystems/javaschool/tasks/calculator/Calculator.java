package com.tsystems.javaschool.tasks.calculator;

import java.util.Stack;

public class Calculator {

  /**
   * Evaluate statement represented as string.
   *
   * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
   *                  parentheses, operations signs '+', '-', '*', '/'<br>
   *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
   * @return string value containing result of evaluation or null if statement is invalid
   */
  public String evaluate(String statement) {
    if (!isStatementValid(statement)) {
      return null;
    }
    String statementInRpn = statementToRpn(statement);
    double result = rpnToResult(statementInRpn);
    if (Double.valueOf(result).isInfinite()) {
      return null;
    }
    return isResultInt(result) ? String.valueOf((int) result) : String.valueOf(result);
  }

  private boolean isStatementValid(String statement) {
    if (statement == null || statement.length() == 0) {
      return false;
    }

    if (statement.substring(0, 1).matches("[+,*,/]") ||
        statement.substring(statement.length() - 1).matches("[+,\\-,*,/]")) {
      return false;
    }

    int numOfParenthesis = 0;
    for (char ch : statement.toCharArray()) {
      if (ch == '(' || ch == ')') {
        numOfParenthesis++;
      }
    }
    if (numOfParenthesis % 2 != 0) {
      return false;
    }

    if (statement.contains(",") ||
        statement.contains("++") ||
        statement.contains("--") ||
        statement.contains("**") ||
        statement.contains("//") ||
        statement.contains("..")) {
      return false;
    }
    return true;
  }

  public static String statementToRpn(String statement) {
    String current = "";
    Stack<Character> stack = new Stack<>();
    int priority;

    for (Character ch : statement.toCharArray()) {
      priority = getPriority(ch);
      switch (priority) {
        case 0:
          current += ch;
          break;
        case 1:
          stack.push(ch);
          break;
        case 2:
        case 3:
          current += ' ';
          while (!stack.isEmpty()) {
            if (getPriority(stack.peek()) >= priority) {
              current += stack.pop();
            } else {
              break;
            }
          }
          stack.push(ch);
          break;
        case -1:
          current += ' ';
          while (getPriority(stack.peek()) != 1) {
            current += stack.pop();
          }
          stack.pop();
      }
    }
    while (!stack.isEmpty()) {
      current += stack.pop();
    }
    return current;
  }

  public static double rpnToResult(String rpn) {
    String operand = "";
    Stack<Double> stack = new Stack<>();

    for (int i = 0; i < rpn.length(); i++) {
      if (rpn.charAt(i) == ' ') {
        continue;
      }

      if (getPriority(rpn.charAt(i)) == 0) {
        while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
          operand += rpn.charAt(i++);
          if (i == rpn.length()) {
            break;
          }
        }
        stack.push(Double.parseDouble(operand));
        operand = "";
      }

      if (getPriority(rpn.charAt(i)) > 1) {
        double a = stack.pop();
        double b = stack.pop();
        if (rpn.charAt(i) == '+') {
          stack.push(b + a);
        }
        if (rpn.charAt(i) == '-') {
          stack.push(b - a);
        }
        if (rpn.charAt(i) == '*') {
          stack.push(b * a);
        }
        if (rpn.charAt(i) == '/') {
          stack.push(b / a);
        }
      }
    }
    return stack.pop();
  }

  private static int getPriority(char token) {
    switch (token) {
      case '*':
      case '/':
        return 3;
      case '+':
      case '-':
        return 2;
      case '(':
        return 1;
      case ')':
        return -1;
      default:
        return 0;
    }
  }

  private static boolean isResultInt(double result) {
    return (int) result == result;
  }
}
