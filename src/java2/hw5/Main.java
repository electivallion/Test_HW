package java2.hw5;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Скорость программы в 1 поток: %d мс\n", method1());
        System.out.printf("Скорость программы в 2 потока: %d мс\n", method2());
    }

    public static long method1() {
        final int SIZE = 10000000;
        float[] array = new float[SIZE];
        for (int i = 0; i < array.length; i++) {
            array[i] = 1;
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] *
                    Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis() - start;
        return end;
    }

    public static long method2() {
        final int SIZE = 10000000;
        float[] array = new float[SIZE];
        for (int i = 0; i < array.length; i++) {
            array[i] = 1;
        }
        long start = System.currentTimeMillis();
        Thread thread2 = new Thread(() -> {
            float[] arr2 = new float[array.length - array.length / 2];
            System.arraycopy(array, array.length / 2, arr2, 0, array.length - array.length / 2);
            for (int i = 0; i < arr2.length; i++) {
                arr2[i] = (float) (arr2[i] *
                        Math.sin(0.2f + (array.length - array.length / 2 + i) / 5) *
                        Math.cos(0.2f + (array.length - array.length / 2 + i) / 5) *
                        Math.cos(0.4f + (array.length - array.length / 2 + i) / 2));
            }
            System.arraycopy(arr2, 0, array, array.length / 2, arr2.length);
        });
        thread2.start();
        float[] arr1 = new float[array.length / 2];
        System.arraycopy(array, 0, arr1, 0, array.length / 2);
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = (float) (arr1[i] *
                    Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
        System.arraycopy(arr1, 0, array, 0, arr1.length);
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis() - start;
        return end;
    }
}