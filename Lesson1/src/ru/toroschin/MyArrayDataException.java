package ru.toroschin;

public class MyArrayDataException extends Exception {
    private int x;
    private int y;

    public MyArrayDataException(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
