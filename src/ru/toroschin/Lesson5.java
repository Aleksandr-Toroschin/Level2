package ru.toroschin;

public class Lesson5 {

    private static final int size = 10000000;

    private static final int h = size / 2;

    public static void main(String[] args) {
        // Урок 5.
        // Создать 2 метода, заполняющих массив по-разному, замерить время

        fillWholeArray();
        fillSplitArray();
    }

    private static void fillWholeArray() {
        // первый метод работает с целым массивом
        float[] arr = new float[size];
        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = 1;
        }

        long beginTime = System.currentTimeMillis();
        Calculate(arr, 0);
        System.out.println("Время работы первого метода: " + (System.currentTimeMillis() - beginTime));
    }

    private static void fillSplitArray() {
        // второй метод разбивает массив
        float[] arr = new float[size];
        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = 1;
        }

        float[] a1 = new float[h];
        float[] a2 = new float[h];

        long beginTime = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        var t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Calculate(a1, 0);
            }
        }
        );

        var t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Calculate(a2, h);
            }
        }
        );

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println("Время работы второго метода: " + (System.currentTimeMillis() - beginTime));
    }

    public static void Calculate(float[] arr, int shiftInd) {
        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (shiftInd+i) / 5) * Math.cos(0.2f + (shiftInd+i) / 5) * Math.cos(0.4f + (shiftInd+i) / 2));
        }
    }

}
