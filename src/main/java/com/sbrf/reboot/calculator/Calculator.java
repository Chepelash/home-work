package com.sbrf.reboot.calculator;

public class Calculator {
    public static int getAddition(int a, int b) {
        return a+b;
    }

    public static int getSubtraction(int a, int b) {
        return a-b;
    }

    public static int getMultiplication(int a, int b) {
        return a*b;
    }

    public static int getDivision(int a, int b) {
        return a/b;
    }

    public static int getReminder(int a, int b) {
        return a%b;
    }

    public static int getPower(int a, int b) {
        int result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }
        return result;
    }

    public static int getPowerOfTen(int a) {
        return Calculator.getPower(10, a);
    }


}
