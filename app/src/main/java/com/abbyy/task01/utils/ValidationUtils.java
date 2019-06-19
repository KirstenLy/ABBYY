package com.abbyy.task01.utils;

public final class ValidationUtils {

    private ValidationUtils() {
        // This class is not publicly instantiable
    }

    public static boolean isAlpha(String string) {
        return string.matches("[a-zA-Z]+");
    }
}
