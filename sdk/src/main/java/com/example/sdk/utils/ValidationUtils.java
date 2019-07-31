package com.example.sdk.utils;

public final class ValidationUtils {

    private ValidationUtils() {
        // This class is not publicly instantiable
    }

    public static boolean isAlpha(String string) {
        return string.matches("[a-zA-Z]+");
    }
}
