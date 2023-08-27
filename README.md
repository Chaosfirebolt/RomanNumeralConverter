# RomanNumeralConverter
Library for converting roman numerals to arabic and vice versa.

# Latest version
Current latest version is 2.1.0
<br/>
Maven dependency
```
<dependency>
    <groupId>com.github.chaosfirebolt.converter</groupId>
    <artifactId>roman-numeral-converter</artifactId>
    <version>2.1.0</version>
</dependency>
```

# Usage
Main class dealing with roman numerals is [RomanInteger](src/main/java/com/github/chaosfirebolt/converter/RomanInteger.java).
It provides constants for basic numerals, used in roman system, public constructors, parsing methods, overrides for hashCode and equals, implementations of Comparable and Cloneable, and simple arithmetic operations. RomanInteger objects are immutable.

# Examples
Valid
```
RomanInteger validObject = new RomanInteger("XV", "15");
RomanInteger validArabicParse = RomanInteger.parse("16");
RomanInteger validRomanParse = RomanInteger.parse("XVI");

RomanInteger res = validObject.add(validArabicParse);
//result will be null
RomanInteger result = RomanInteger.ONE.subtract(RomanInteger.TEN);
```

Invalid(exceptions are thrown)
```
RomanInteger invalidObject = new RomanInteger("XV", 16);
RomanInteger invalidRomanParse = RomanInteger.parse("XG");
RomanInteger invalidArabicParse = RomanInteger.parse("4000");
//exception is thrown since arithmetic mode is set to strict for at least one operand
RomanInteger result = RomanInteger.ONE.setArithmeticMode(ArithmeticMode.STRICT).subtract(RomanInteger.TEN);
```

# Licence
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details.
