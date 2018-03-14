package com.chaos.converter.constants;

import java.util.regex.Pattern;

/**
 * Created by ChaosFire on 14.3.2018 г.
 */
public class Patterns {

    public static final Pattern ARABIC_PATTERN = Pattern.compile("^\\d+$");
    public static final Pattern ROMAN_PATTERN = Pattern.compile("^[IVXLCDM]+$");
}