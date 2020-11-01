package ru.toroschin;

public class Lesson2 {
    public static void main(String[] args) {

        // массив с ошибкой размера
        String[][] numbers1 = {{"12","4","3"},{"54","33","32"},{"43","7","66","13"},{"43","12","10","2"}};
        // массив с ошибкой данных
        String[][] numbers2 = {{"12","4","3у","9"},{"54","23","33","32"},{"43","7","66","13"},{"43","12","10","2"}};
        // правильный массив
        String[][] numbers3 = {{"12","4","3","9"},{"54","23","33","32"},{"43","7","66","13"},{"43","12","10","2"}};

        String[][][] arrays = {numbers1, numbers2, numbers3};

        for (int i = 0; i < arrays.length; i++) {
            System.out.println("массив "+i);
            try {
                System.out.println("Сумма чисел массива=" + getSumArray(arrays[i]));
            } catch (MyArraySizeException e) {
                System.out.println("Ошибка размера массива.");
            } catch (MyArrayDataException e) {
                System.out.println("Ошибка данных в массиве в ячейке " + e.getX() + ";" + e.getY());
            }
        }
    }

    public static int getSumArray(String[][] strings) throws MyArraySizeException, MyArrayDataException {
        int sum = 0;
        if (strings.length != 4) {
            throw new MyArraySizeException();
        }
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].length != 4) {
                throw new MyArraySizeException();
            }
            for (int j = 0; j < strings[i].length; j++) {
                try {
                    sum += Integer.parseInt(strings[i][j]);
                }
                catch(Exception e) {
                    throw new MyArrayDataException(i,j);
                }
            }
        }
        return sum;
    }
}
