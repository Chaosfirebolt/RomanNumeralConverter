package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.ArithmeticMode;
import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.constants.Patterns;
import com.github.chaosfirebolt.converter.parser.ParserContainer;
import com.github.chaosfirebolt.converter.parser.impl.AbstractParser;
import com.github.chaosfirebolt.converter.util.DataTransferObject;
import com.github.chaosfirebolt.converter.util.Validator;

import java.util.Comparator;
import java.util.Objects;

/**
 * This class represents roman numerals.
 * Provides public constructors, constants for the seven basic roman numerals,
 * static methods for parsing and methods for simple arithmetic operations.
 * Comparison is done via arabic representation for this numeral(Integer).
 * RomanInteger objects are immutable.
 */
public class RomanInteger implements Comparable<RomanInteger>, Cloneable {

    public static final Comparator<RomanInteger> NATURAL_ORDER_COMPARATOR = Comparator.comparingInt(RomanInteger::getArabic);

    /**
     * Constant representing arabic number "1", roman numeral - "I".
     */
    public static final RomanInteger ONE = new RomanInteger("I", 1, ArithmeticMode.LOOSE);
    /**
     * Constant representing arabic number "5", roman numeral - "V".
     */
    public static final RomanInteger FIVE = new RomanInteger("V", 5, ArithmeticMode.LOOSE);
    /**
     * Constant representing arabic number "10", roman numeral - "X".
     */
    public static final RomanInteger TEN = new RomanInteger("X", 10, ArithmeticMode.LOOSE);
    /**
     * Constant representing arabic number "50", roman numeral - "L".
     */
    public static final RomanInteger FIFTY = new RomanInteger("L", 50, ArithmeticMode.LOOSE);
    /**
     * Constant representing arabic number "100", roman numeral - "C".
     */
    public static final RomanInteger HUNDRED = new RomanInteger("C", 100, ArithmeticMode.LOOSE);
    /**
     * Constant representing arabic number "500", roman numeral - "D".
     */
    public static final RomanInteger FIVE_HUNDRED = new RomanInteger("D", 500, ArithmeticMode.LOOSE);
    /**
     * Constant representing arabic number "1000", roman numeral - "M".
     */
    public static final RomanInteger THOUSAND = new RomanInteger("M", 1000, ArithmeticMode.LOOSE);

    /**
     * Roman numeral representation for this RomanInteger object.
     * Null values are not permitted.
     */
    private final String romanRepresentation;
    /**
     * Arabic number representing this roman numeral
     */
    private final int arabicRepresentation;
    /**
     * Represents how to parse result from arithmetic operations including this roman integer
     */
    private final ArithmeticMode arithmeticMode;

    /**
     * Initializes new {@link RomanInteger} object with provided roman string and arabic integer.
     * Throws exception if either argument is invalid.
     *
     * @param romanRepresentation string representing roman numeral.
     * @param arabicRepresentation integer representing arabic value for provided roman numeral.
     * @throws NullPointerException if romanRepresentation argument is null.
     * @throws NumberFormatException if provided roman numeral does not match required format.
     * @throws IllegalArgumentException if provided arabic and roman numerals do not represent same value
     *         or arabic number is not in valid range.
     */
    public RomanInteger(String romanRepresentation, int arabicRepresentation) {
        this(validate(romanRepresentation, arabicRepresentation));
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
        this(validate(romanRepresentation, Integer.parseInt(Objects.requireNonNull(arabicRepresentation))));
    }

    private static DataTransferObject validate(String romanRepresentation, int arabicRepresentation) {
        AbstractParser romanParser = ParserContainer.getInstance().getParser(IntegerType.ROMAN);
        DataTransferObject dto = romanParser.parse(Objects.requireNonNull(romanRepresentation));
        if (dto.getArabic() != arabicRepresentation) {
            throw new IllegalArgumentException("Roman numeral must represent same value as provided arabic representation.");
        }
        return dto;
    }

    /**
     * Copy constructor, initializes new {@link RomanInteger} object, representing the same roman integer, from provided {@link RomanInteger}.
     *
     * @param romanInteger roman integer to be copied.
     */
    public RomanInteger(RomanInteger romanInteger) {
        this(romanInteger.romanRepresentation, romanInteger.arabicRepresentation, romanInteger.arithmeticMode);
    }

    private RomanInteger(DataTransferObject dto) {
        this(dto.getRoman(), dto.getArabic(), ArithmeticMode.LOOSE);
    }

    private RomanInteger(String romanRepresentation, int arabicRepresentation, ArithmeticMode arithmeticMode) {
        this.romanRepresentation = romanRepresentation;
        this.arabicRepresentation = arabicRepresentation;
        this.arithmeticMode = arithmeticMode;
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
        return new RomanInteger(dto);
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
        return new RomanInteger(dto);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.arabicRepresentation);
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
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        RomanInteger other = (RomanInteger) obj;
        return this.arabicRepresentation == other.arabicRepresentation;
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
     * Compares numerically this and provided {@link RomanInteger} objects.
     *
     * @param other another {@link RomanInteger}
     * @return {@code -1} if this {@link #arabicRepresentation} is numerically less than
     *         other {@link #arabicRepresentation}, {@code 0} if arabic representations are equal
     *         and {@code 1} this arabic number is numerically grater than other arabic number.
     *         Method returns {@code 0} only if {@link #equals(Object)} would return {@code true}.
     */
    @Override
    public int compareTo(RomanInteger other) {
        return NATURAL_ORDER_COMPARATOR.compare(this, other);
    }

    /**
     * Getter for arabic representation.
     *
     * @return arabic number representing this RomanInteger.
     */
    public int getArabic() {
        return this.arabicRepresentation;
    }

    /**
     * Adds two roman integers.
     *
     * @param anotherInteger roman integer to be added to this roman integer.
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     * @throws ArithmeticException if result is outside permitted bounds for roman integers and either this roman integer or argument is set to STRICT {@link ArithmeticMode}
     * @deprecated since 2.0.2
     */
    @Deprecated
    public RomanInteger add(RomanInteger anotherInteger) {
        int result = this.arabicRepresentation + anotherInteger.arabicRepresentation;
        return parseResult(result, shouldParseStrict(this, anotherInteger));
    }

    /**
     * Subtracts provided roman integer from this roman integer.
     *
     * @param anotherInteger roman integer to be subtracted from this object.
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     * @throws ArithmeticException if result is outside permitted bounds for roman integers and either this roman integer or argument is set to STRICT {@link ArithmeticMode}
     * @deprecated since 2.0.2
     */
    @Deprecated
    public RomanInteger subtract(RomanInteger anotherInteger) {
        int result = this.arabicRepresentation - anotherInteger.arabicRepresentation;
        return parseResult(result, shouldParseStrict(this, anotherInteger));
    }

    /**
     * Multiplies two roman integers.
     *
     * @param anotherInteger multiplicand
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     * @throws ArithmeticException if result is outside permitted bounds for roman integers and either this roman integer or argument is set to STRICT {@link ArithmeticMode}
     * @deprecated since 2.0.2
     */
    @Deprecated
    public RomanInteger multiply(RomanInteger anotherInteger) {
        int result = this.arabicRepresentation * anotherInteger.arabicRepresentation;
        return parseResult(result, shouldParseStrict(this, anotherInteger));
    }

    /**
     * Divides this roman integer by provided roman integer.
     *
     * @param anotherInteger divisor
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     * @throws ArithmeticException if result is outside permitted bounds for roman integers and either this roman integer or argument is set to STRICT {@link ArithmeticMode}
     * @deprecated since 2.0.2
     */
    @Deprecated
    public RomanInteger divide(RomanInteger anotherInteger) {
        int result = this.arabicRepresentation / anotherInteger.arabicRepresentation;
        return parseResult(result, shouldParseStrict(this, anotherInteger));
    }

    /**
     * Remainder from division of this object by provided object.
     *
     * @param anotherInteger divisor
     * @return New {@link RomanInteger} object representing resulting arabic value from
     *         this operation, or null if result is not in valid range.
     * @throws ArithmeticException if result is outside permitted bounds for roman integers and either this roman integer or argument is set to STRICT {@link ArithmeticMode}
     * @deprecated since 2.0.2
     */
    @Deprecated
    public RomanInteger remainder(RomanInteger anotherInteger) {
        int result = this.arabicRepresentation % anotherInteger.arabicRepresentation;
        return parseResult(result, shouldParseStrict(this, anotherInteger));
    }

    private static boolean shouldParseStrict(RomanInteger firstArgument, RomanInteger secondArgument) {
        return firstArgument.arithmeticMode == ArithmeticMode.STRICT || secondArgument.arithmeticMode == ArithmeticMode.STRICT;
    }

    private static RomanInteger parseResult(int result, boolean parseStrict) {
        try {
            DataTransferObject dto = ParserContainer.getInstance().getParser(IntegerType.ARABIC).parse(Integer.toString(result));
            return new RomanInteger(dto);
        } catch (IllegalArgumentException exc) {
            if (parseStrict) {
                throw new ArithmeticException("Resulting roman integer out of valid range for arabic value: " + result);
            }
            return null;
        }
    }

    /**
     * Returns roman integer, whose arithmetic mode is equal to the supplied one.
     * If the arithmetic mode of this instance is equal to the supplied mode, then returns this instance,
     * otherwise returns new instance, representing the same roman integer with the desired arithmetic mode.
     *
     * @param arithmeticMode the desired arithmetic mode
     * @return Roman integer with arithmetic mode set to teh desired one
     */
    public RomanInteger setArithmeticMode(ArithmeticMode arithmeticMode) {
        if (this.arithmeticMode == arithmeticMode) {
            return this;
        }
        return new RomanInteger(this.romanRepresentation, this.arabicRepresentation, arithmeticMode);
    }

    @Override
    public RomanInteger clone() {
        try {
            return (RomanInteger) super.clone();
        } catch (CloneNotSupportedException exc) {
            //should never happen
            return new RomanInteger(this);
        }
    }
}