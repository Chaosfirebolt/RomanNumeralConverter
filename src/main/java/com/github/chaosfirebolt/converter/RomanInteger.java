package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.constants.Patterns;
import com.github.chaosfirebolt.converter.parser.ParserContainer;
import com.github.chaosfirebolt.converter.parser.impl.AbstractParser;
import com.github.chaosfirebolt.converter.util.DataTransferObject;
import com.github.chaosfirebolt.converter.util.Validator;

import java.util.Objects;

/**
 * Created by ChaosFire on 1.3.2018 г.
 *
 * This class represents roman numerals.
 * Provides public constructors, constants for the seven basic roman numerals,
 * static methods for parsing and methods for simple arithmetic operations.
 * Comparison is done via arabic representation for this numeral(Integer).
 * RomanInteger objects are immutable.
 */
public class RomanInteger implements Comparable<RomanInteger> {

    /**
     * Constant representing arabic number "1", roman numeral - "I".
     */
    public static final RomanInteger ONE = new RomanInteger("I", 1, Integer.hashCode(1));
    /**
     * Constant representing arabic number "5", roman numeral - "V".
     */
    public static final RomanInteger FIVE = new RomanInteger("V", 5, Integer.hashCode(5));
    /**
     * Constant representing arabic number "10", roman numeral - "X".
     */
    public static final RomanInteger TEN = new RomanInteger("X", 10, Integer.hashCode(10));
    /**
     * Constant representing arabic number "50", roman numeral - "L".
     */
    public static final RomanInteger FIFTY = new RomanInteger("L", 50, Integer.hashCode(50));
    /**
     * Constant representing arabic number "100", roman numeral - "C".
     */
    public static final RomanInteger HUNDRED = new RomanInteger("C", 100, Integer.hashCode(100));
    /**
     * Constant representing arabic number "500", roman numeral - "D".
     */
    public static final RomanInteger FIVE_HUNDRED = new RomanInteger("D", 500, Integer.hashCode(500));
    /**
     * Constant representing arabic number "1000", roman numeral - "M".
     */
    public static final RomanInteger THOUSAND = new RomanInteger("M", 1000, Integer.hashCode(1000));

    /**
     * Default value for hash cache.
     */
    private static final int DEFAULT_HASH = 0;

    /**
     * Roman numeral representation for this RomanInteger object.
     * Null values are not permitted.
     */
    private final String romanRepresentation;
    /**
     * Arabic number representing this roman numeral
     * Null values are not permitted.
     */
    private final Integer arabicRepresentation;
    /**
     * Cache for the hash code. Defaults to 0.
     */
    private int hash;

    /**
     * Initializes new {@link RomanInteger} object with provided roman string and arabic integer.
     * Throws exception if either argument is invalid.
     *
     * @param romanRepresentation string representing roman numeral.
     * @param arabicRepresentation integer representing arabic value for provided roman numeral.
     * @throws NullPointerException if either argument is null.
     * @throws NumberFormatException if provided roman numeral does not match required format.
     * @throws IllegalArgumentException if provided arabic and roman numerals do not represent same value
     *         or arabic number is not in valid range.
     */
    public RomanInteger(String romanRepresentation, Integer arabicRepresentation) {
        this(validate(Objects.requireNonNull(romanRepresentation), Objects.requireNonNull(arabicRepresentation)));
    }

    /**
     * Initializes new {@link RomanInteger} object with provided roman and arabic strings.
     * Throws exception if either argument is invalid.
     *
     * @param romanRepresentation string representing roman numeral.
     * @param arabicRepresentation string representing arabic value for provided roman numeral.
     * @throws NullPointerException if either argument is null.
     * @throws NumberFormatException if provided roman and arabic numerals do not match required format.
     * @throws IllegalArgumentException if provided arabic and roman numerals do not represent same value
     *         or arabic number is not in valid range.
     */
    public RomanInteger(String romanRepresentation, String arabicRepresentation) {
        this(validate(Objects.requireNonNull(romanRepresentation), Integer.parseInt(Objects.requireNonNull(arabicRepresentation))));
    }

    private RomanInteger(RomanInteger romanInteger) {
        this(romanInteger.romanRepresentation, romanInteger.arabicRepresentation, romanInteger.hash);
    }

    private RomanInteger(String romanRepresentation, Integer arabicRepresentation, int hash) {
        this.romanRepresentation = romanRepresentation;
        this.arabicRepresentation = arabicRepresentation;
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        if (this.hash == DEFAULT_HASH) {
            this.hash = this.arabicRepresentation.hashCode();
        }
        return this.hash;
    }

    /**
     * Tests this object and provided object for equality.
     * This object is considered equal to provided object, if argument is
     * instance of {@link RomanInteger} and their arabic representations are considered equal.
     * E.g. instance holding roman - "XVIII" and arabic - 18 is equal to instance holding roman - "IIXX" and arabic - 18,
     * because both represent same arabic value - 18.
     *
     * @param obj object to test against for equality.
     * @return {@code true} if objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RomanInteger)) {
            return false;
        }
        RomanInteger other = (RomanInteger) obj;
        return this.arabicRepresentation.equals(other.arabicRepresentation);
    }

    /**
     * String representation of this object.
     *
     * @return roman numeral.
     */
    @Override
    public String toString() {
        return this.romanRepresentation;
    }

    /**
     * Compares numerically and provided {@link RomanInteger} object.
     *
     * @param other another {@link RomanInteger}
     * @return {@code -1} if this {@link #arabicRepresentation} is numerically less than
     *         other {@link #arabicRepresentation}, {@code 0} if arabic representations are equal
     *         and {@code 1} this arabic number is numerically grater than other arabic number.
     *         Method returns {@code 0} only if {@link #equals(Object)} would return {@code true}.
     */
    @Override
    public int compareTo(RomanInteger other) {
        return this.arabicRepresentation.compareTo(other.arabicRepresentation);
    }

    /**
     * Getter for arabic representation.
     *
     * @return arabic number representing this RomanInteger.
     */
    public Integer getArabic() {
        return this.arabicRepresentation;
    }

    /**
     * Parses provided string to a RomanInteger object.
     * Convenience method resolving by itself the integer type of provided string and which parser should be used.
     * Throws exceptions in case of invalid input.
     *
     * @param number string to parse.
     * @return {@link RomanInteger} object in case of valid input.
     * @throws NumberFormatException if provided string does not match roman or arabic formats.
     * @throws IllegalArgumentException if arabic representation is not in valid range.
     * @throws NullPointerException if argument is null.
     */
    public static RomanInteger parse(String number) {
        IntegerType type = resolveType(Objects.requireNonNull(number));
        DataTransferObject dto =  ParserContainer.getInstance().getParser(type).parse(number);
        return new RomanInteger(dto.getRoman(), dto.getArabic(), DEFAULT_HASH);
    }

    /**
     * Parses provided string to a RomanInteger object using parser for provided {@link IntegerType}.
     * Throws exceptions in case of invalid input.
     *
     * @param number string to parse.
     * @param integerType integer type of provided string.
     * @return {@link RomanInteger} object in case of valid input.
     * @throws NumberFormatException if provided string does not match required format for specified {@link IntegerType}.
     * @throws IllegalArgumentException if arabic representation is not in valid range.
     * @throws NullPointerException if either argument is null.
     */
    public static RomanInteger parse(String number, IntegerType integerType) {
        DataTransferObject dto = ParserContainer.getInstance().getParser(Objects.requireNonNull(integerType)).parse(Objects.requireNonNull(number));
        return new RomanInteger(dto.getRoman(), dto.getArabic(), DEFAULT_HASH);
    }

    private static IntegerType resolveType(String number) {
        try {
            Validator.numberFormat(number, Patterns.ARABIC_PATTERN);
            return IntegerType.ARABIC;
        } catch (NumberFormatException ignored) {
        }
        Validator.numberFormat(number.toUpperCase(), Patterns.ROMAN_PATTERN);
        return IntegerType.ROMAN;
    }

    /**
     * Adds two roman integers.
     *
     * @param anotherInteger roman integer to be added to this roman integer.
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     */
    public RomanInteger add(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation + anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    /**
     * Subtracts provided roman integer from this roman integer.
     *
     * @param anotherInteger roman integer to be subtracted from this object.
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     */
    public RomanInteger subtract(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation - anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    /**
     * Multiplies two roman integers.
     *
     * @param anotherInteger multiplicand
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     */
    public RomanInteger multiply(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation * anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    /**
     * Divides this roman integer by provided roman integer.
     *
     * @param anotherInteger divisor
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     */
    public RomanInteger divide(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation / anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    /**
     * Remainder from division of this object by provided object.
     *
     * @param anotherInteger divisor
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     */
    public RomanInteger remainder(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation % anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    private static RomanInteger parseResult(Integer result) {
        try {
            DataTransferObject dto = ParserContainer.getInstance().getParser(IntegerType.ARABIC).parse(result.toString());
            return new RomanInteger(dto.getRoman(), dto.getArabic(), DEFAULT_HASH);
        } catch (IllegalArgumentException exc) {
            return null;
        }
    }

    private static RomanInteger validate(String romanRepresentation, Integer arabicRepresentation) {
        AbstractParser romanParser = ParserContainer.getInstance().getParser(IntegerType.ROMAN);
        DataTransferObject dto = romanParser.parse(romanRepresentation);
        if (!dto.getArabic().equals(arabicRepresentation)) {
            throw new IllegalArgumentException("Roman numeral must represent same value as provided arabic representation.");
        }
        return new RomanInteger(dto.getRoman(), dto.getArabic(), DEFAULT_HASH);
    }
}