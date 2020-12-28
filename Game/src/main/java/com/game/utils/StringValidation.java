package com.game.utils;

import org.apache.commons.lang3.StringUtils;

public class StringValidation {
    StringValidation() {
        throw new UnsupportedOperationException("You are trying to create a singleton object");
    }

    public static boolean validateString(String input){
        return StringUtils.isNotEmpty(input)
                && StringUtils.isNotBlank(input)
                && input.matches("^[a-zA-Z]*$");
    }
}
