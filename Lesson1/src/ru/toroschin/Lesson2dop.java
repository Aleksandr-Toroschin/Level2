package ru.toroschin;

import java.util.Arrays;

public class Lesson2dop {
    public static void main(String[] args) {
        makeIncrementArray(4,5);
    }

    public static void makeIncrementArray(int x, int y) {
        if ((x<1) || (y<1)) {
            System.out.println("Ошибка!");
            return;
        }

        // делаем обратную спираль
        int index=x*y+1;
        int i=-1;
        int j=0;
        int incrementI = 1;
        int incrementJ = 1;

        String format = "%"+(String.valueOf(x*y).length())+"d";

        String[][] array = new String[x][y];

        boolean iNotJ = true;

        //while (index<=(x*y-1)) {
        while (index>1) {
            if (iNotJ) {
                i += incrementI;
            } else {
                j += incrementJ;
            }
            if (isCellCorrect(i, j, x, y, array)) {
                index--;
                array[i][j] = String.format(format,index);
            } else {
                if (iNotJ) {
                    iNotJ = false;
                    i -= incrementI;
                    incrementI = -incrementI;
                } else {
                    iNotJ = true;
                    j -= incrementJ;
                    incrementJ = -incrementJ;
                }
            }
        }

        for (String[] strings : array) {
            System.out.println(Arrays.toString(strings));
        }
    }

    public static boolean isCellCorrect(int i, int j, int x, int y, String[][] array) {
        if ((i>=x) || (i<0)) {
            return false;
        } else if ((j>=y) || (j<0)) {
            return false;
        } else return array[i][j] == null;
    }
}
