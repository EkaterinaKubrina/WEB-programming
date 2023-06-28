import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task4Test {
    @Test
    void task4Test_0() {
        int[] numbers = {17, 12, 10, 4, 9, 8};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(17, 9, 4), Arrays.asList(12, 10, 8));
        List<List<Integer>> actual = task4(numbers, 2);
        assertEquals(expected, actual);
    }

    @Test
    void task4Test_1() {
        int[] numbers = {5, 5, 5, 5, 5, 5};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(5, 5, 5), Arrays.asList(5, 5, 5));
        List<List<Integer>> actual = task4(numbers, 2);
        assertEquals(expected, actual);
    }

    @Test
    void task4Test_2() {
        int[] numbers = {4, 7, 5, 8, 6};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(8, 7, 6, 5, 4));
        List<List<Integer>> actual = task4(numbers, 1);
        assertEquals(expected, actual);
    }

    @Test
    void task4Test_3() {
        int[] numbers = {8, 7, 7, 6, 6, 5, 3, 3, 3};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(8, 5, 3), Arrays.asList(7, 6, 3), Arrays.asList(7, 6, 3));
        List<List<Integer>> actual = task4(numbers, 3);
        assertEquals(expected, actual);
    }

    @Test
    void task4Test_4() {
        int[] numbers = {8, 5, 5, 4, 4, 2, 2, 2};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(8, 4, 4), Arrays.asList(5, 5, 2, 2, 2));
        List<List<Integer>> actual = task4(numbers, 2);
        assertEquals(expected, actual);
    }

    @Test
    void task4Test_5() {
        int[] numbers = {3, 2};
        List<List<Integer>> expected = null;
        List<List<Integer>> actual = task4(numbers, 2);
        assertEquals(expected, actual);
    }

    @Test
    void task4Test_6() {
        int[] numbers = {10, 11, 9, 3, 3, 3};
        List<List<Integer>> expected = null;
        List<List<Integer>> actual = task4(numbers, 3);
        assertEquals(expected, actual);
    }

    @Test
    void task4Test_7() {
        int[] numbers = {4, 4, 4};
        List<List<Integer>> expected = null;
        List<List<Integer>> actual = task4(numbers, 4);
        assertEquals(expected, actual);
    }

    @Test
    void task4Test_8() {
        int[] numbers = {100, 56, 55, 46, 40, 10, 7};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(100, 40, 10, 7), Arrays.asList(56, 55, 46));
        List<List<Integer>> actual = task4(numbers, 2);
        assertEquals(expected, actual);
    }


    @Test
    void task4Test_9() {
        int[] numbers = {100, 50, 40, 40, 40, 31, 6, 5, 2};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(100, 50, 5, 2), Arrays.asList(40, 40, 40, 31, 6));
        List<List<Integer>> actual = task4(numbers, 2);
        assertEquals(expected, actual);
    }

    private static List<List<Integer>> task4(int[] numbers, int countGroups) {
        int sum = Arrays.stream(numbers).sum();
        if (countGroups == 0 || sum % countGroups != 0) {
            return null;
        }

        int sumForGroup = sum / countGroups;
        if (Arrays.stream(numbers).filter(x -> x > sumForGroup).count() > 0) {
            System.out.println("Невозможно разделить полученный набор на заданное кол-во групп");
            return null;
        }
        List<Integer> sortedNumbers = Arrays.stream(numbers).boxed().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        List<List<Integer>> groupsResult = new ArrayList<>();

        for (int i = 0; i < countGroups; i++) {
            ArrayList<Integer> listResult = getGroup(sumForGroup, sortedNumbers);
            if (listResult == null) return null;

            listResult.sort(Collections.reverseOrder());
            groupsResult.add(listResult);

            // Удаляем использованные числа из списка доступных
            for (Integer integer : listResult) {
                sortedNumbers.remove(integer);
            }
        }

        return groupsResult;
    }

    private static ArrayList<Integer> getGroup(int sumForGroup, List<Integer> sortedNumbers) {
        for (int i = 0; i < sortedNumbers.size(); i++) {
            int currentElement = sortedNumbers.get(i);
            if (sumForGroup - currentElement > 0) {
                List<Integer> remainingNumbers = new ArrayList<>(sortedNumbers);
                remainingNumbers.remove(i);

                ArrayList<Integer> groups = getGroup(sumForGroup - currentElement, remainingNumbers);
                if (groups != null) {
                    groups.add(currentElement);
                    return groups;
                }
            } else if (sumForGroup - currentElement == 0){
                return new ArrayList<>(Collections.singletonList(currentElement));
            } else {
                return null;
            }
        }
        return null;
    }
}