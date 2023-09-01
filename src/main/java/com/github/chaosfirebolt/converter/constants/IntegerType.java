package com.github.chaosfirebolt.converter.constants;

import com.github.chaosfirebolt.converter.Parser;
import com.github.chaosfirebolt.converter.ArabicParser;
import com.github.chaosfirebolt.converter.RomanParser;

import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Enumeration for supported integer types.
 */
public enum IntegerType {

    /**
     * Representation for arabic format integers.
     */
    ARABIC(ArabicParser::new, Pattern.compile("^\\d+$")),
    /**
     * Representation for roman format integers.
     */
    ROMAN(RomanParser::new, Pattern.compile("^[IVXLCDM]+$"));

    private static final IntegerType[] VALUES = IntegerType.values();

    /**
     * Supplier for instances of parsers.
     */
    private final Supplier<Parser> supplier;
    /**
     * The pattern that matches this integer type
     */
    private final Pattern typePattern;

    IntegerType(Supplier<Parser> supplier, Pattern typePattern) {
        this.supplier = supplier;
        this.typePattern = typePattern;
    }

    /**
     * Parses integer type from provided numeral
     * @param numeral the numeral to match against
     * @return the type
     * @throws NumberFormatException if provided numeral does not match any format
     */
    public static IntegerType fromNumeral(String numeral) {
        for (IntegerType integerType : VALUES) {
            if (integerType.typePattern.matcher(numeral).find()) {
                return integerType;
            }
        }
        throw new NumberFormatException("Numeral does not match any required format: " + numeral);
    }

    /**
     * Gets the corresponding parser for this integer type
     * @return the parser
     */
    public Parser getParser() {
        return this.supplier.get();
    }

    /**
     * Verifies that provided string the pattern for this type.
     * @param representation string to verify.
     * @return provided string if it matches pattern.
     * @throws NumberFormatException if provided string does not match pattern.
     */
    public String validateFormat(String representation) {
        if (!typePattern.matcher(representation).find()) {
            throw new NumberFormatException("Numeral does not match required format for string: " + representation);
        }
        return representation;
    }
}