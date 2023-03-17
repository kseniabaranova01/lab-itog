import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FractionCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите выражение с простыми дробями:");
            String expression = scanner.nextLine();

            if (expression.equals("quit")) {
                System.out.println("До свидания!");
                break;
            }

            try {
                Fraction result = evaluateExpression(expression);
                System.out.println("Результат: " + result.toString());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static Fraction evaluateExpression(String expression) {
        // Удаляем пробелы из выражения
        expression = expression.replaceAll("\\s", "");
    
        // Раскрываем скобки в выражении
        while (expression.contains("(")) {
            int openParenIndex = expression.lastIndexOf("(");
            int closeParenIndex = expression.indexOf(")", openParenIndex);
    
            if (closeParenIndex == -1) {
                throw new IllegalArgumentException("Не удалось раскрыть скобки: " + expression);
            }
    
            String subExpression = expression.substring(openParenIndex + 1, closeParenIndex);
            Fraction subResult = evaluateExpression(subExpression);
    
            expression = expression.substring(0, openParenIndex) + subResult.toString() + expression.substring(closeParenIndex + 1);
        }
    
        // Выполняем умножение и деление
        String[] addSubTokens = expression.split("(?=[+-])|(?<=[+-])");
        Fraction result = evaluateExpressionMultDiv(addSubTokens[0]);
        
        for (int i = 1; i < addSubTokens.length; i += 2) {
            char operator = addSubTokens[i].charAt(0);
            Fraction operand = evaluateExpressionMultDiv(addSubTokens[i + 1]);
            if (operator == '+') {
                result = result.add(operand);
            } else if (operator == '-') {
                result = result.subtract(operand);
            } else {
                throw new IllegalArgumentException("Неизвестный оператор: " + operator);
            }
        }
    
        return result;
    }
    
    private static Fraction evaluateExpressionMultDiv(String expression) {
        // Выполняем сложение и вычитание
        String[] multDivTokens = expression.split("(?=[*/])|(?<=[*/])");
        Fraction result = evaluateExpressionAddSub(multDivTokens[0]);
    
        for (int i = 1; i < multDivTokens.length; i += 2) {
            char operator = multDivTokens[i].charAt(0);
            Fraction operand = evaluateExpressionAddSub(multDivTokens[i + 1]);
    
            if (operator == '*') {
                result = result.multiply(operand);
            } else if (operator == '/') {
                result = result.divide(operand);
            } else {
                throw new IllegalArgumentException("Неизвестный оператор: " + operator);
            }
        }
    
        return result;
    }
    
    private static Fraction evaluateExpressionAddSub(String expression) {
        return Fraction.fromString(expression);
    }




}
