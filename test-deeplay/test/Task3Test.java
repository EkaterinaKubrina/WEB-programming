import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Task3Test {
    private static final int SIZE_SEQUENCE = 3;

    @Test
    void task3_0() {
        int[] firstPlayerSequence = new int[]{1, 1, 2};
        int[] generatedValues = new int[]{1, 1, 1, 1, 2};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(1, res);
    }

    @Test
    void task3_1() {
        int[] firstPlayerSequence = new int[]{1, 2, 1};
        int[] generatedValues = new int[]{1, 2, 1, 2, 1};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(1, res);
    }

    @Test
    void task3_2() {
        int[] firstPlayerSequence = new int[]{4, 2, 4};
        int[] generatedValues = new int[]{1, 4, 2, 4, 4, 4, 4, 4, 2, 4};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(2, res);
    }

    @Test
    void task3_3() {
        int[] firstPlayerSequence = new int[]{4, 4, 4};
        int[] generatedValues = new int[]{1, 4, 2, 4, 4, 4, 4, 4, 2, 4};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(1, res);
    }

    @Test
    void task3_4() {
        int[] firstPlayerSequence = new int[]{4, 2, 4};
        int[] generatedValues = new int[]{1, 4, 2, 4, 4, 4, 4, 4, 4, 4};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(1, res);
    }

    @Test
    void task3_5() {
        int[] firstPlayerSequence = new int[]{4, 4, 4};
        int[] generatedValues = new int[]{1, 4, 2, 4, 4, 4, 4, 4, 4, 4};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(2, res);
    }


    @Test
    void task3_6() {
        int[] firstPlayerSequence = new int[]{4, 2, 4};
        int[] generatedValues = new int[]{4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 1};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(3, res);
    }

    @Test
    void task3_7() {
        int[] firstPlayerSequence = new int[]{2, 4, 2};
        int[] generatedValues = new int[]{4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 1};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(2, res);
    }

    @Test
    void task3_8() {
        int[] firstPlayerSequence = new int[]{1, 2, 3};
        int[] generatedValues = new int[]{2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(3, res);
    }

    @Test
    void task3_9() {
        int[] firstPlayerSequence = new int[]{2, 3, 1};
        int[] generatedValues = new int[]{2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(4, res);
    }


    @Test
    void task3_10() {
        int[] firstPlayerSequence = new int[]{1, 2, 3};
        int[] generatedValues = new int[]{1, 2, 4, 3, 5, 5, 2, 3, 4, 5, 5, 5};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(0, res);
    }

    @Test
    void task3_11() {
        int[] firstPlayerSequence = new int[]{4, 5, 5};
        int[] generatedValues = new int[]{1, 2, 4, 3, 5, 5, 2, 3, 4, 5, 5, 5};
        int res = task3(firstPlayerSequence, generatedValues);
        Assertions.assertEquals(1, res);
    }

    private static int task3(int[] firstPlayerSequence, int[] generatedValues) {
        int firstPlayerWinsCounter = 0;
        int firstMatchCounter = 0;

        for (int i = 0; i < generatedValues.length; i++) {
            if (firstMatchCounter < SIZE_SEQUENCE) {
                if (firstPlayerSequence[firstMatchCounter] == generatedValues[i]) {
                    firstMatchCounter++;
                } else if (firstMatchCounter != 0 && firstPlayerSequence[0] == generatedValues[i - 1] && firstPlayerSequence[1] == generatedValues[i]) {
                    firstMatchCounter = 2;
                } else if (firstMatchCounter != 0 && firstPlayerSequence[0] == generatedValues[i]) {
                    firstMatchCounter = 1;
                } else {
                    firstMatchCounter = 0;
                }
            }

            if (firstMatchCounter == 3) {
                firstPlayerWinsCounter++;
                firstMatchCounter = 0;
            }
        }

        return firstPlayerWinsCounter;
    }
}