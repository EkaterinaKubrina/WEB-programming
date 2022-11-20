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
        boolean rimMode = false;

        String[] arguments = input.trim().split(" ");
        if (arguments.length != 3) {
            throw new IllegalArgumentException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        try {
            operand1 = Integer.parseInt(arguments[0]);
            operand2 = Integer.parseInt(arguments[2]);
        } catch (NumberFormatException e) {
            operand1 = toArab(arguments[0].toUpperCase());
            operand2 = toArab(arguments[2].toUpperCase());
            rimMode = true;
        }

        if (rimMode) {
            return toRim(calculate(operand1, operand2, arguments[1]));
        }
        return String.valueOf(calculate(operand1, operand2, arguments[1]));

    }

    //функция вычисляет значение операции по двум операндам и оператору
    private static int calculate(int operand1, int operand2, String operation) {
        int res = 0;
        if (operand1 <= 10 && operand2 <= 10 && operand1 > 0 && operand2 > 0) {
            switch (Operator.getOperator(operation)) {
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
                if (num == RomanSystem.C.getAnInt()) {
                    res.append(RomanSystem.C.getValue());
                } else if (num >= RomanSystem.C.getAnInt() - RomanSystem.L.getAnInt()) {
                    res.append(RomanSystem.L.getValue());
                    res.append(RomanSystem.C.getValue());
                    num = num - RomanSystem.C.getAnInt() - RomanSystem.L.getAnInt();
                } else if (num >= RomanSystem.L.getAnInt()) {
                    res.append(RomanSystem.L.getValue());
                    num = num - RomanSystem.L.getAnInt();
                } else if (num >= RomanSystem.L.getAnInt() - RomanSystem.X.getAnInt()) {
                    res.append(RomanSystem.X.getValue());
                    res.append(RomanSystem.L.getValue());
                    num = num - RomanSystem.L.getAnInt() - RomanSystem.X.getAnInt();
                } else if (num >= RomanSystem.X.getAnInt()) {
                    res.append(RomanSystem.X.getValue());
                    num = num - RomanSystem.X.getAnInt();
                } else if (num >= RomanSystem.X.getAnInt() - RomanSystem.I.getAnInt()) {
                    res.append(RomanSystem.I.getValue());
                    res.append(RomanSystem.X.getValue());
                    num = num - RomanSystem.X.getAnInt() - RomanSystem.I.getAnInt();
                } else if (num >= RomanSystem.V.getAnInt()) {
                    res.append(RomanSystem.V.getValue());
                    num = num - RomanSystem.V.getAnInt();
                } else if (num >= RomanSystem.V.getAnInt() - RomanSystem.I.getAnInt()) {
                    res.append(RomanSystem.I.getValue());
                    res.append(RomanSystem.V.getValue());
                    num = num - RomanSystem.V.getAnInt() - RomanSystem.I.getAnInt();
                } else {
                    res.append(RomanSystem.I.getValue());
                    num = num - RomanSystem.I.getAnInt();
                }
            }
            return res.toString();
        }
        throw new IllegalArgumentException("В римской системе нет отрицательных чисел и нуля");
    }
}