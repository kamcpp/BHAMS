package io.blueharvest.ams.domain.dtos;

public class Helper {

    public static int cantorPair(int a, int b) {
        return ((a + b) * (a + b + 1)) / 2 + b;
    }
}
