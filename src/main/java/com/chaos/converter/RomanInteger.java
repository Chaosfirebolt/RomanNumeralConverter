package com.chaos.converter;

import com.chaos.converter.constants.IntegerType;
import com.chaos.converter.constants.Patterns;
import com.chaos.converter.parser.ParserContainer;
import com.chaos.converter.parser.impl.AbstractParser;
import com.chaos.converter.util.DataTransferObject;
import com.chaos.converter.util.Validator;

import java.util.Objects;

/**
 * Created by ChaosFire on 1.3.2018 г.
 */
public class RomanInteger implements Comparable<RomanInteger> {

    public static final RomanInteger ONE = new RomanInteger("I", 1, Integer.hashCode(1));
    public static final RomanInteger FIVE = new RomanInteger("V", 5, Integer.hashCode(5));
    public static final RomanInteger TEN = new RomanInteger("X", 10, Integer.hashCode(10));
    public static final RomanInteger FIFTY = new RomanInteger("L", 50, Integer.hashCode(50));
    public static final RomanInteger HUNDRED = new RomanInteger("C", 100, Integer.hashCode(100));
    public static final RomanInteger FIVE_HUNDRED = new RomanInteger("D", 500, Integer.hashCode(500));
    public static final RomanInteger THOUSAND = new RomanInteger("M", 1000, Integer.hashCode(1000));

    private final String romanRepresentation;
    private final Integer arabicRepresentation;
    private Integer hash;

    public RomanInteger(String romanRepresentation, Integer arabicRepresentation) {
        this(validate(romanRepresentation, Objects.requireNonNull(arabicRepresentation)));
    }

    public RomanInteger(String romanRepresentation, String arabicRepresentation) {
        this(validate(romanRepresentation, Integer.parseInt(Objects.requireNonNull(arabicRepresentation))));
    }

    private RomanInteger(RomanInteger romanInteger) {
        this(romanInteger.romanRepresentation, romanInteger.arabicRepresentation, romanInteger.hash);
    }

    private RomanInteger(String romanRepresentation, Integer arabicRepresentation, Integer hash) {
        this.romanRepresentation = romanRepresentation;
        this.arabicRepresentation = arabicRepresentation;
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        if (this.hash == null) {
            this.hash = this.arabicRepresentation.hashCode();
        }
        return this.hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RomanInteger)) {
            return false;
        }
        RomanInteger other = (RomanInteger) obj;
        return this.arabicRepresentation.equals(other.arabicRepresentation);
    }

    @Override
    public String toString() {
        return this.romanRepresentation;
    }

    @Override
    public int compareTo(RomanInteger other) {
        return this.arabicRepresentation.compareTo(other.arabicRepresentation);
    }

    public Integer getArabic() {
        return this.arabicRepresentation;
    }

    public static RomanInteger parse(String number) {
        IntegerType type = resolveType(number);
        DataTransferObject dto =  ParserContainer.getInstance().getParser(type).parse(number);
        return new RomanInteger(dto.getRoman(), dto.getArabic(), null);
    }

    public static RomanInteger parse(String number, IntegerType integerType) {
        DataTransferObject dto = ParserContainer.getInstance().getParser(integerType).parse(number);
        return new RomanInteger(dto.getRoman(), dto.getArabic(), null);
    }

    private static IntegerType resolveType(String number) {
        try {
            Validator.numberFormat(number, Patterns.ARABIC_PATTERN);
            return IntegerType.ARABIC;
        } catch (NumberFormatException ignored) {
        }
        Validator.numberFormat(number, Patterns.ROMAN_PATTERN);
        return IntegerType.ROMAN;
    }

    public RomanInteger add(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation + anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    public RomanInteger subtract(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation - anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    public RomanInteger multiply(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation * anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    public RomanInteger divide(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation / anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    public RomanInteger remainder(RomanInteger anotherInteger) {
        Integer result = this.arabicRepresentation % anotherInteger.arabicRepresentation;
        return parseResult(result);
    }

    private static RomanInteger parseResult(Integer result) {
        try {
            DataTransferObject dto = ParserContainer.getInstance().getParser(IntegerType.ARABIC).parse(result.toString());
            return new RomanInteger(dto.getRoman(), dto.getArabic(), null);
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
        return new RomanInteger(dto.getRoman(), dto.getArabic(), null);
    }
}