import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите два операнда и один оператор (+, -, /, *) через пробел или слово 'stop' для выхода из программы:");
        String line = in.nextLine();
        while(!line.equals("stop")){
            System.out.println(calc(line));
            line = in.nextLine();
        }
    }

    public static String calc(String input) {
        int operand1, operand2, res;
        boolean rimMode = false;
        input = input.trim();
        String[] arguments = input.split(" ");
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
        if (operand1 <= 10 && operand2 <= 10 && operand1 > 0 && operand2 > 0) {
            switch (arguments[1]) {
                case ("+"):
                    res = operand1 + operand2;
                    break;
                case ("-"):
                    res = operand1 - operand2;
                    break;
                case ("*"):
                    res = operand1 * operand2;
                    break;
                case ("/"):
                    res = operand1 / operand2;
                    break;
                default:
                    throw new IllegalArgumentException("Некорректный символ операции");
            }
            if (rimMode) {
                if (res > 0) {
                    return toRim(res);
                }
                throw new IllegalArgumentException("В римской системе нет отрицательных чисел и нуля");
            }
            return String.valueOf(res);
        }
        throw new IllegalArgumentException("Операнды не могут быть больше 10 и меньше 1");

    }

    private static int toArab(String rimStr) {
        if (rimStr.matches("[IVX]+")) {
            int res = 0;
            List<Integer> list = new ArrayList<>();
            for (String s : rimStr.split("")) {
                if (s.equals("I"))
                    list.add(1);
                else if (s.equals("V"))
                    list.add(5);
                else
                    list.add(10);
            }
            for (int n : list) {
                if (n == 1) {
                    res++;
                } else if (n == 5) {
                    if (res != 0 && res < 2) {
                        res = 5 - res;
                    } else {
                        res = 5;
                    }
                } else {
                    if (res != 0 && res < 2) {
                        res = 10 - res;
                    } else {
                        res = 10;
                    }
                }
            }
            return res;
        }
        throw new IllegalArgumentException("Некорректные входные параметры");
    }

    private static String toRim(int num) {
        //т.к на входе максимальные операнды 10 и 10, то максимальный результат - 100
            String res = "";
            if (num == 100) {
                res = "C";
            } else if (num >= 90) {
                res = "LC" + toRim(num - 90);
            } else if (num >= 50) {
                res = "L" + toRim(num - 50);
            } else if (num >= 40) {
                res = "XL" + toRim(num - 40);
            } else if (num >= 10) {
                res = "X" + toRim(num - 10);
            } else if (num >= 9) {
                res = "IX" + toRim(num - 9);
            } else if (num >= 5) {
                res = "V" + toRim(num - 5);
            } else if (num >= 4) {
                res = "IV" + toRim(num - 4);
            } else if (num >= 1) {
                res = "I" + toRim(num - 1);
            }
            return res;
    }
}