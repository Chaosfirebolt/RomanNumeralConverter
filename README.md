# RomanNumeralConverter
Library for converting roman numerals to arabic and vice versa.

# Usage
Main class dealing with roman numerals is [RomanInteger](src/main/java/com/github/chaosfirebolt/converter/RomanInteger.java).
It provides constants for basic numerals, used in roman system, public constructors, parsing methods, overrides for hashCode and equals, implementation of Comparable and simple arithmethic operations. RomanInteger objects are immutable.

# Examples
Valid
```
RomanInteger validObject = new RomanInteger("XV", "15");
RomanInteger validArabicParse = RomanInteger.parse("16");
RomanInteger validRomanParse = RomanInteger.parse("XVI");

RomanInteger res = validObject.add(validArabicParse);
RomanInteger result = RomanInteger.ONE.subtract(RomanInteger.TEN);//result is null
```

Invalid(exceptions are thrown)
```
RomanInteger invalidObject = new RomanInteger("XV", 16);
RomanInteger invalidRomanParse = RomanInteger.parse("XG");
RomanInteger invalidArabicParse = RomanInteger.parse("4000");
```

# Licence
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details.
