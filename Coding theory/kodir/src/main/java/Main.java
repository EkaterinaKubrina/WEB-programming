import java.util.*;

public class Main {
    public static int n;
    public static int t;
    public static int p;
    public static int[][] H;


    public static void main(String[] args) {
        String str;
        do {
            Scanner in = new Scanner(System.in);
            System.out.print("Input data (size = 4): ");
            int data = in.nextInt();

            //добавляем проверочные биты
            List<Integer> coded = new ArrayList<>();
            coded.add(data / 1000);
            coded.add(data % 1000 / 100);
            coded.add(data % 100 / 10);
            coded.add(data % 10);
            coded.add((coded.get(0) + coded.get(2) + coded.get(3)) % 2);
            coded.add((coded.get(0) + coded.get(1) + coded.get(2)) % 2);
            coded.add((coded.get(1) + coded.get(2) + coded.get(3)) % 2);

            System.out.print("Coded data: ");
            for (int i = 0; i < 7; i++) {
                System.out.print(coded.get(i));
            }

            int n = (int) (Math.random() * coded.size());
            if (coded.get(n) == 0) {
                coded.set(n, 1);
            } else {
                coded.set(n, 0);
            }

            System.out.print("\nData with error: ");
            for (int i = 0; i < 7; i++) {
                System.out.print(coded.get(i));
            }

            //Вычисляем синдром
            int code_error = (coded.get(0) + coded.get(2) + coded.get(3) + coded.get(4)) % 2 * 100 +
                    (coded.get(0) + coded.get(1) + coded.get(2) + coded.get(5)) % 2 * 10 +
                    (coded.get(1) + coded.get(2) + coded.get(3) + coded.get(6)) % 2;

            switch (code_error) {
                case (1):
                    if (coded.get(6) == 0) {
                        coded.set(6, 1);
                    } else {
                        coded.set(6, 0);
                    }
                    break;
                case (10):
                    if (coded.get(5) == 0) {
                        coded.set(5, 1);
                    } else {
                        coded.set(5, 0);
                    }
                    break;
                case (100):
                    if (coded.get(4) == 0) {
                        coded.set(4, 1);
                    } else {
                        coded.set(4, 0);
                    }
                    break;
                case (101):
                    if (coded.get(3) == 0) {
                        coded.set(3, 1);
                    } else {
                        coded.set(3, 0);
                    }
                    break;
                case (111):
                    if (coded.get(2) == 0) {
                        coded.set(2, 1);
                    } else {
                        coded.set(2, 0);
                    }
                    break;
                case (11):
                    if (coded.get(1) == 0) {
                        coded.set(1, 1);
                    } else {
                        coded.set(1, 0);
                    }
                    break;
                case (110):
                    if (coded.get(0) == 0) {
                        coded.set(0, 1);
                    } else {
                        coded.set(0, 0);
                    }
                    break;
            }


            System.out.print("\nDecoded data: ");
            for (int i = 0; i < 4; i++) {
                System.out.print(coded.get(i));
            }


            System.out.print("\n\nTry again? [yes/no]: ");
            in.nextLine();
            str = in.nextLine();

        } while (!str.toLowerCase().equals("no"));

    }




}
