import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите два операнда и один оператор (+, -, /, *) через пробел или слово 'stop' для выхода из программы:");
        String line = in.nextLine();
        while (!line.equals("stop")) {
            System.out.println(calc(line));
            line = in.nextLine();
        }
    }

    public static String calc(String input) {
        int operand1, operand2;
        Operator operation = null;
        boolean romanMode = false;
        String[] arguments = {};

        for (Operator o : Operator.values()) {
            if (input.contains(o.getOperator())) {
                arguments = input.replaceAll(" ", "").split(o.getOperatorForRegex());
                operation = o;
            }
        }
        if (operation == null) {
            throw new IllegalArgumentException("Некорректный символ операции");
        }
        if (arguments.length != 2) {
            throw new IllegalArgumentException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        try {
            operand1 = Integer.parseInt(arguments[0]);
            operand2 = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException e) {
            operand1 = toArab(arguments[0].toUpperCase());
            operand2 = toArab(arguments[1].toUpperCase());
            romanMode = true;
        }

        if (romanMode) {
            return toRim(calculate(operand1, operand2, operation));
        }
        return String.valueOf(calculate(operand1, operand2, operation));

    }

    //функция вычисляет значение операции по двум операндам и оператору
    private static int calculate(int operand1, int operand2, Operator operation) {
        int res = 0;
        if (operand1 <= 10 && operand2 <= 10 && operand1 > 0 && operand2 > 0) {
            switch (operation) {
                case PLUS:
                    res = operand1 + operand2;
                    break;
                case MINUS:
                    res = operand1 - operand2;
                    break;
                case MULTIPLICATION:
                    res = operand1 * operand2;
                    break;
                case DIVISION:
                    res = operand1 / operand2;
                    break;
                default:
                    throw new IllegalArgumentException("Некорректный символ операции");
            }
        }
        return res;
    }

    //переводит строку из римской системы счисления в арабскую (integer)
    private static int toArab(String rimStr) {
        if (rimStr.matches("[IVX]+")) {
            int res = 0;
            List<Integer> list = new ArrayList<>();
            for (String s : rimStr.split("")) {
                if (s.equals(RomanSystem.I.getValue()))
                    list.add(RomanSystem.I.getAnInt());
                else if (s.equals(RomanSystem.V.getValue()))
                    list.add(RomanSystem.V.getAnInt());
                else
                    list.add(RomanSystem.X.getAnInt());
            }

            for (int n : list) {
                if (n == RomanSystem.I.getAnInt()) {
                    res += RomanSystem.I.getAnInt();
                } else if (n == RomanSystem.V.getAnInt()) {
                    if (res != 0 && res < 2) {
                        res = RomanSystem.V.getAnInt() - res;
                    } else {
                        res = RomanSystem.V.getAnInt();
                    }
                } else {
                    if (res != 0 && res < 2) {
                        res = RomanSystem.X.getAnInt() - res;
                    } else {
                        res = RomanSystem.X.getAnInt();
                    }
                }
            }
            return res;
        }
        throw new IllegalArgumentException("Некорректные входные параметры");
    }

    //переводит число из арабской системы счисления в римскую (string)
    private static String toRim(int num) {
        //т.к на входе максимальные операнды 10 и 10, то максимальный результат - 100
        if (num > 0) {
            StringBuilder res = new StringBuilder();
            while (num > 0) {
                for (RomanSystem r : RomanSystem.values()) {
                    if (num >= r.getAnInt()) {
                        res.append(r.getValue());
                        num = num - r.getAnInt();
                        break;
                    }
                }
            }
            return res.toString();
        }
        throw new IllegalArgumentException("В римской системе нет отрицательных чисел и нуля");
    }
}