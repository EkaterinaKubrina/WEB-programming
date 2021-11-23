import java.util.*;

public class Main {

    public static void main(String[] args) {
        String str;

        do {
            Scanner in = new Scanner(System.in);
            /*System.out.print("Input p: ");
            int p = in.nextInt();*/
            int p = 7;
            System.out.print("Поле:" + p + "\n");

            if (p < 50) {
                //System.out.print("Input t: ");
                //int t = in.nextInt();
                int t = 2;
                System.out.print("Ошибок:" + t + "\n");
                if (t < 5) {

                    int n = p - 1;
                    int k = p - 1 - 2 * t;


                    //ввод данных
                    System.out.print("Введите данные (размер = " + k + "): ");
                    //in.nextLine();
                    String data_str = in.nextLine();
                    //String data_str = "12";

                    //проверочная матрица Рида-Соломона
                    System.out.print("матрица H:\n");
                    int[][] H = new int[n][t * 2];
                    for (int j = 0; j < t * 2; j++) {
                        for (int i = 0; i < n; i++) {
                            H[i][j] = (int) Math.pow(i + 1, j + 1) % p;
                            System.out.print(H[i][j]);
                            System.out.print(" ");
                        }
                        System.out.print("\n");
                    }

                    //порождающая матрица
                    System.out.print("матрица G:\n");
                    int[][] G = new int[n][k];
                    for (int j = 0; j < k; j++) {
                        for (int i = 0; i < k; i++) {
                            if (i == j) {
                                G[i][j] = 1;

                            } else {
                                G[i][j] = 0;
                            }
                            System.out.print(G[i][j]);
                            System.out.print(" ");
                        }
                        int[] b = new int[t * 2];
                        int[][] A = new int[t * 2][n - k];
                        for (int i = 0; i < t * 2; i++) {
                            b[i] = -H[j][i];
                            for (int e = 0; e < n - k; e++) {
                                A[i][e] = H[e + k][i];
                            }
                        }
                        int[] x = gauss(A, b, t * 2, n - k, p);
                        for (int i = k; i < n; i++) {
                            G[i][j] = x[i - k];
                            System.out.print(G[i][j]);
                            System.out.print(" ");
                        }
                        System.out.print("\n");
                    }


                    String[] data = data_str.split("");
                    ArrayList<Integer> result = new ArrayList<Integer>();
                    for (String s : data) {
                        result.add(Integer.parseInt(s));
                    }
                    System.out.print("Данные: " + result);

                    System.out.print("\nЗакодированные данные: ");
                    int[] coded = new int[n];
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < k; j++) {
                            coded[i] += (G[i][j] * (result.get(j) % p)) % p;
                        }
                        coded[i] = coded[i] % p;
                        System.out.print(coded[i]);
                    }


                    System.out.print("\nВведите колличество ошибок (не более "+t+"-х): ");
                    int num_err = in.nextInt();

                    //таблица соответствия
                    int[] coded_test = new int[n];
                    Map<String, Map<Integer, Integer>> map = new HashMap<>();
                    for (int j = 0; j < coded.length; j++) {
                        for (int i = 0; i < p; i++) {
                            System.arraycopy(coded, 0, coded_test, 0, n);
                            Map<Integer, Integer> map_help = new HashMap<>();

                            if (coded_test[j] != i) {
                                coded_test[j] = i;
                                map_help.put(j, coded[j] - i);


                                int[] decoded_test = decoded(t, n, p, H, coded_test);
                                addMap(map, decoded_test, map_help);


                                if (t > 1 && num_err > 1) {
                                    Deque<Integer> last = new ArrayDeque<>();
                                    last.addLast(j);
                                    addTable(last, n, t, p, coded_test, coded, H, map, num_err, map_help);
                                }
                            }
                            map_help.clear();


                        }
                    }


                    for (int i = 0; i < num_err; i++) {
                        coded[(int) (Math.random() * k)] = (int) (Math.random() * p);
                    }
                    System.out.print("\nЗакодированные данные с ошибкой: " + Arrays.toString(coded));

                    System.out.print("\nСиндром: ");
                    int[] decoded = new int[t * 2];
                    for (int i = 0; i < t * 2; i++) {
                        for (int j = 0; j < n; j++) {
                            decoded[i] += (H[j][i] * (coded[j])) % p;
                        }
                        decoded[i] = decoded[i] % p;
                        System.out.print(decoded[i]);
                    }

                    System.out.print("\nРаскодированные данные: ");

                    boolean no_error;
                    no_error = true;
                    for (int i: decoded) {
                        if (i != 0) {
                            no_error = false;
                            break;
                        }
                    }

                    if(!no_error){
                        Map<Integer, Integer> error = map.get(Arrays.toString(decoded));

                        for (int i = 0; i < result.size(); i++) {
                            if (error.get(i) != null) {
                                coded[i] += error.get(i);
                            }
                        }

                    }
                    for (int i = 0; i < k; i++) {
                        System.out.print(coded[i]);
                    }

                }

            }

            System.out.print("\n\nTry again? [yes/no]: ");
            in.nextLine();
            str = in.nextLine();
        } while (!str.toLowerCase().equals("no"));
    }

    public static int[] decoded(int t, int n, int p, int[][] H, int[] coded_test) {
        int[] decoded_test = new int[t * 2];
        for (int b = 0; b < t * 2; b++) {
            for (int d = 0; d < n; d++) {
                decoded_test[b] += (H[d][b] * (coded_test[d]));
            }
            decoded_test[b] = decoded_test[b] % p;
        }
        return decoded_test;
    }

    public static void addMap(Map<String, Map<Integer, Integer>> map, int[] decoded_test, Map<Integer, Integer> map_help) {
        map.putIfAbsent(Arrays.toString(decoded_test), new HashMap<>(map_help));
    }


    public static void addTable(Deque<Integer> last, int n, int t, int p, int[] coded_test, int[] coded, int[][] H, Map<String, Map<Integer, Integer>> map, int num_err, Map<Integer, Integer> map_help) {
        for (int v = last.getLast() + 1; v < n; v++) {
            if (!last.contains(v)) {
                for (int w = 0; w < p; w++) {
                    if (coded[v] != w) {
                        coded_test[v] = w;
                        map_help.put(v, coded[v] - w);

                        if (last.size() <= num_err - 1) {
                            int[] decoded_test = decoded(t, n, p, H, coded_test);
                            addMap(map, decoded_test, map_help);
                        }
                        if (last.size() < t && num_err > last.size() + 1) {
                            last.addLast(v);
                            addTable(last, n, t, p, coded_test, coded, H, map, num_err, map_help);
                            last.removeLast();
                        }
                        map_help.remove(v, coded[v] - w);
                        coded_test[v] = coded[v];
                    }
                }
            }
        }
    }

    public static int[] gauss(int[][] A, int[] b, int n, int m, int pole) {
        int[] answer = new int[m];


        /* Метод Гаусса */

        int N = n;
        for (int l = 0; l < N; l++) {

            int max = l;
            for (int i = l + 1; i < N; i++) {
                if (Math.abs(A[i][l]) > Math.abs(A[max][l])) {
                    max = i;
                }
            }
            int[] temp = A[l];
            A[l] = A[max];
            A[max] = temp;
            int t = b[l];
            b[l] = b[max];
            b[max] = t;


            for (int i = l + 1; i < N; i++) {
                int alpha = (A[i][l] * obratn(A[l][l], pole)) % pole;
                b[i] -= (alpha * b[l]) % pole;
                b[i] = (pole + b[i]) % pole;
                for (int j = l; j < N; j++) {
                    A[i][j] -= (alpha * A[l][j]) % pole;
                    A[i][j] = (pole + A[i][j]) % pole;
                }
            }
        }

        // Обратный проход

        int[] x = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            int sum = 0;
            for (int j = i + 1; j < N; j++) {
                sum += (A[i][j] * x[j]) % pole;
                sum = sum % pole;
            }
            x[i] = ((pole + b[i] - sum) % pole * obratn(A[i][i], pole)) % pole;
        }

        /* Вывод результатов */

        for (int i = 0; i < N; i++) {
            answer[i] = x[i];
        }


        return answer;
    }

    public static int obratn(int a, int pole) {

        for (int i = 1; i <= pole; i++) {
            if (i * a % pole == 1) {
                return i;
            }
        }
        return a;

    }


}