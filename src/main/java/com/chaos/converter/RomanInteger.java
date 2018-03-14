package com.chaos.converter;

import com.chaos.converter.constants.IntegerType;

/**
 * Created by ChaosFire on 1.3.2018 г.
 */
public class RomanInteger implements Comparable<RomanInteger> {

    public static final RomanInteger ONE = new RomanInteger("I", 1);
    public static final RomanInteger FIVE = new RomanInteger("V", 5);
    public static final RomanInteger TEN = new RomanInteger("X", 10);
    public static final RomanInteger FIFTY = new RomanInteger("L", 50);
    public static final RomanInteger HUNDRED = new RomanInteger("C", 100);
    public static final RomanInteger FIVE_HUNDRED = new RomanInteger("D", 500);
    public static final RomanInteger THOUSAND = new RomanInteger("M", 1000);

    private final String romanRepresentation;
    private final Integer arabicRepresentation;
    private Integer hash;

    private RomanInteger(String romanRepresentation, Integer arabicRepresentation) {
        this.romanRepresentation = romanRepresentation;
        this.arabicRepresentation = arabicRepresentation;
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

    public String toStringArabic() {
        return this.arabicRepresentation.toString();
    }

    public static RomanInteger parse(String number) {
        throw new UnsupportedOperationException();
    }

    public static RomanInteger parse(String number, IntegerType integerType) {
        throw new UnsupportedOperationException();
    }

    public RomanInteger add(RomanInteger anotherInteger) {
        throw new UnsupportedOperationException();
    }

    public RomanInteger subtract(RomanInteger anotherInteger) {
        throw new UnsupportedOperationException();
    }

    public RomanInteger multiply(RomanInteger anotherInteger) {
        throw new UnsupportedOperationException();
    }

    public RomanInteger divide(RomanInteger anotherInteger) {
        throw new UnsupportedOperationException();
    }

    public RomanInteger remainder(RomanInteger anotherInteger) {
        throw new UnsupportedOperationException();
    }
}