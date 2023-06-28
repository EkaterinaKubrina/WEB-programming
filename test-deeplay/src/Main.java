import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        //task1();
        //task2(new int[]{1, 2, 2, 3, 3, 4, 5, 1});
        //task3(100);
        task4(new int[]{1, 12, 9}, 0);
    }

    private static final int MAX_VALUE_TASK1 = 10;

    public static void task1() {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите размер массива: ");
        int size = in.nextInt();

        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * MAX_VALUE_TASK1);
        }

        System.out.println("Сгенерированный массив: " + Arrays.toString(array));

        //сортируем нечетные числа в порядке возрастания
        IntStream oddNumbers = Arrays.stream(array)
                .filter(n -> n % 2 != 0)
                .sorted();

        //фильтруем нули
        IntStream zeroNumbers = Arrays.stream(array)
                .filter(n -> n == 0);

        //сортируем нечетные числа в порядке убывания
        IntStream evenNumbers = Arrays.stream(array)
                .filter(n -> n != 0 && n % 2 == 0)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue);


        int[] result = IntStream.concat(IntStream.concat(oddNumbers, zeroNumbers), evenNumbers).toArray();

        System.out.println("\nОтсортированный массив: " + Arrays.toString(result));
    }

    public static void task2(int[] array) {
        //группируем уникальные числа и их встречаемость
        Map<Integer, Long> map = Arrays.stream(array)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        //получаем максимальное значение встречаемости
        long max = map.values().stream().max(Long::compare).orElse(0L);

        System.out.println("Полученный массив: " + Arrays.toString(array));
        System.out.print("Максимальное колличество раз (" + max + ") встретились числа: ");
        map.forEach((k, v) -> {
            if (v == max) {
                System.out.print(k + " ");
            }
        });
    }

    private static final int MIN_VALUE_TASK3 = 1;
    private static final int MAX_VALUE_TASK3 = 5;
    private static final int SIZE_SEQUENCE = 3;

    public static void task3(int numberIteration) {
        //генерируем последовательности игроков
        int[] firstPlayerSequence = new int[SIZE_SEQUENCE];
        int[] secondPlayerSequence = new int[SIZE_SEQUENCE];
        for (int i = 0; i < 3; i++) {
            firstPlayerSequence[i] = (int) (MIN_VALUE_TASK3 + Math.random() * MAX_VALUE_TASK3);
            secondPlayerSequence[i] = (int) (MIN_VALUE_TASK3 + Math.random() * MAX_VALUE_TASK3);
        }

        //счетчики побед и ничьих
        int firstPlayerWinsCounter = 0;
        int secondPlayerWinsCounter = 0;
        int tieCounter = 0;

        //счетчики очков
        int firstPlayerPointsCounter = 0;
        int secondPlayerPointsCounter = 0;

        //счетчики считающие кол-во совпавших подряд значений с последовательностями игроков
        int firstMatchCounter = 0;
        int secondMatchCounter = 0;

        //Для экономии памяти не храним генерируемую последовательность чисел, а обрабатываем по одному генерируемому значению
        int generatedValue;
        int previousGeneratedValue = -1;
        for (int i = 0; i < numberIteration; i++) {
            generatedValue = (int) (MIN_VALUE_TASK3 + Math.random() * MAX_VALUE_TASK3);

            //Вывод генерируемой последовательности
            System.out.print(generatedValue + " ");

            //Если последовательность еще не совпала целиком, то проверяем совпадение на текущем элементе
            if (firstMatchCounter < SIZE_SEQUENCE) {
                if (firstPlayerSequence[firstMatchCounter] == generatedValue) {
                    firstMatchCounter++;
                } else if (firstMatchCounter != 0 && firstPlayerSequence[0] == previousGeneratedValue && firstPlayerSequence[1] == generatedValue) {
                    firstMatchCounter = 2;
                } else if (firstMatchCounter != 0 && firstPlayerSequence[0] == generatedValue) {
                    firstMatchCounter = 1;
                } else {
                    firstMatchCounter = 0;
                }
            }
            if (secondMatchCounter < SIZE_SEQUENCE) {
                if (secondPlayerSequence[secondMatchCounter] == generatedValue) {
                    secondMatchCounter++;
                } else if (secondMatchCounter != 0 && secondPlayerSequence[0] == previousGeneratedValue && secondPlayerSequence[1] == generatedValue) {
                    secondMatchCounter = 2;
                } else if (secondMatchCounter != 0 && secondPlayerSequence[0] == generatedValue) {
                    secondMatchCounter = 1;
                } else {
                    secondMatchCounter = 0;
                }
            }

            //увеличиваем счетчики побед или ничьих
            //счетчик побед для игрока увеличивается, только если его последовательность совпала полностью
            //и при этом последовательность его соперника не совпала полностью,
            //иначе - ничья
            if (firstMatchCounter == 3 && secondMatchCounter != 3) {
                firstPlayerWinsCounter++;
            } else if (secondMatchCounter == 3 && firstMatchCounter != 3) {
                secondPlayerWinsCounter++;
            } else {
                tieCounter++;
            }

            //если последовательность совпала полностью, обнуляем счетчик совпавших подряд значений
            if (firstMatchCounter == 3) {
                firstMatchCounter = 0;
                firstPlayerPointsCounter++;
            }
            if (secondMatchCounter == 3) {
                secondMatchCounter = 0;
                secondPlayerPointsCounter++;
            }

            previousGeneratedValue = generatedValue;
        }

        System.out.println();
        System.out.println(firstPlayerPointsCounter + " очков у первого игрока = " + Arrays.toString(firstPlayerSequence));
        System.out.println(secondPlayerPointsCounter + " очков у второго игрока = " + Arrays.toString(secondPlayerSequence));

        //вычисляем вероятности
        double pFirstWins = (double) firstPlayerWinsCounter / numberIteration;
        double pSecondWins = (double) secondPlayerWinsCounter / numberIteration;
        double pTie = (double) tieCounter / numberIteration;
        System.out.println("Вероятность что у первого игрока больше очков = " + pFirstWins);
        System.out.println("Вероятность что у второго игрока больше очков = " + pSecondWins);
        System.out.println("Вероятность ничьи = " + pTie);
    }

    public static void task4(int[] numbers, int countGroups) {
        System.out.println("Полученный набор чисел: " + Arrays.toString(numbers));

        int sum = Arrays.stream(numbers).sum();
        if (countGroups <= 0 || sum % countGroups != 0) {
            printUnableGetGroups();
            return;
        }

        // Вычисляем сумму, которая должна быть в каждой группе
        int sumForGroup = sum / countGroups;
        if (Arrays.stream(numbers).filter(x -> x > sumForGroup).count() > 0) {
            printUnableGetGroups();
            return;
        }

        // Сортируем числа по убыванию и сохраняем в список
        List<Integer> sortedNumbers = Arrays.stream(numbers).boxed().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        // Создаем список для хранения групп чисел
        List<List<Integer>> groupsResult = new ArrayList<>();

        // Добавляем числа в группы, пока не заполним все группы или не закончатся доступные числа
        for (int i = 0; i < countGroups; i++) {
            ArrayList<Integer> listResult = getGroup(sumForGroup, sortedNumbers);
            if (listResult == null) {
                printUnableGetGroups();
                return;
            }

            listResult.sort(Collections.reverseOrder());
            groupsResult.add(listResult);

            // Удаляем использованные числа из списка доступных
            for (Integer integer : listResult) {
                sortedNumbers.remove(integer);
            }
        }

        //выводим результат
        for (int i = 0; i < countGroups; i++) {
            System.out.println(Arrays.toString(groupsResult.get(i).toArray()));
        }
    }

    private static void printUnableGetGroups() {
        System.out.println("Невозможно разделить полученный набор на заданное кол-во групп");
    }

    private static ArrayList<Integer> getGroup(int sumForGroup, List<Integer> sortedNumbers) {
        for (int i = 0; i < sortedNumbers.size(); i++) {
            int currentElement = sortedNumbers.get(i);

            //если текущий элемент меньше целевой суммы
            if (sumForGroup - currentElement > 0) {
                //создаем sublist без текущего элемента
                List<Integer> remainingNumbers = new ArrayList<>(sortedNumbers);
                remainingNumbers.remove(i);

                //рекурсивно вызываем метод уменьшая целевую сумму и передавая sublist
                ArrayList<Integer> groups = getGroup(sumForGroup - currentElement, remainingNumbers);

                //если нашлась последовательность - добавляем к ней текущий элемент и возвращаем лист
                if (groups != null) {
                    groups.add(currentElement);
                    return groups;
                }
            } else if (sumForGroup - currentElement == 0) {
                //если текущий элемент равен целевой сумме - возвращаем его
                return new ArrayList<>(Collections.singletonList(currentElement));
            } else {
                //если текущий элемент больше целевой суммы - возвращаем null
                return null;
            }
        }
        return null;
    }
}