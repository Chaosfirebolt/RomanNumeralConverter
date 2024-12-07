# RomanNumeralConverter
Library for converting roman numerals to arabic and vice versa.

Versions before `3.0.0` require java 8. Since `3.0.0` required java version is 17.

# Latest version
Current latest version - `3.6.0`.
<br/>
Maven dependency:
```
<dependency>
    <groupId>com.github.chaosfirebolt.converter</groupId>
    <artifactId>roman-numeral-converter</artifactId>
    <version>3.6.0</version>
</dependency>
```
[All artefacts in maven central](https://mvnrepository.com/artifact/com.github.chaosfirebolt.converter/roman-numeral-converter)

[Javadoc](https://javadoc.io/doc/com.github.chaosfirebolt.converter/roman-numeral-converter/latest/index.html)

# Usage
Main class dealing with roman numerals is [RomanInteger](src/main/java/com/github/chaosfirebolt/converter/RomanInteger.java).
It provides constants for basic numerals, used in roman system, public constructors, parsing methods, overrides for hashCode and equals, implementations of Comparable, Cloneable and Serializable.
Comparable implementation is **NOT** consistent with equals.

Caching of parse results can be enabled/disabled, default state is disabled. Supports plugging in custom [RomanIntegerCache](src/main/java/com/github/chaosfirebolt/converter/api/cache/RomanIntegerCache.java) implementations and caches initialized in advance.

Supports adding custom symbols for the next order of values, extending the supported range of roman numerals.

RomanInteger objects are immutable.

# Examples
Valid
```
RomanInteger validObject = new RomanInteger("XV", "15");
RomanInteger validArabicParse = RomanInteger.parse("16");
RomanInteger validRomanParse = RomanInteger.parse("XVI");
```
Invalid(exceptions are thrown)
```
RomanInteger invalidObject = new RomanInteger("XV", 16);
RomanInteger invalidRomanParse = RomanInteger.parse("XG");
RomanInteger invalidArabicParse = RomanInteger.parse("4000");
```
Caching
```
//enable caching of parsed instances
RomanInteger.enableCache();
RomanInteger romanInteger1 = RomanInteger.parse("11");
RomanInteger romanInteger2 = RomanInteger.parse("11");
//romanInteger2 is the same instance as romanInteger1

//disables caching and clears the cache
RomanInteger.disableCache();
RomanInteger romanInteger3 = RomanInteger.parse("11");
RomanInteger romanInteger4 = RomanInteger.parse("11");
//romanInteger3 and romanInteger4 are different instances

//plug in custom cache implementation
RomanInteger.setCache(parserCache -> new CustomCache(parserCache, otherDependency));
RomanInteger.setCache(BiDirectionalRomanIntegerCache::new);
//plug in cache initialized in advance with provided data
RomanInteger.setCache(parserCache -> new UniDirectionalRomanIntegerCache(parserCache, MapStorage.strict(TreeMap::new), new ProvidedInitializationData()));
//plugin cache with custom storage
RomanInteger.setCache(parserCache -> new DefaultRomanIntegerCache(parserCache, customStorage, new RomanIntegerArrayInitializationData(new BasicNumeralsInputSource())));
RomanInteger.setCache(parserCache -> new DefaultRomanIntegerCache(parserCache, customStorage, new NoOpMapData<>()));
```
Range extension
```
RomanInteger.extend('A', 'b');
//extends the range with A = 5000 and B = 10000, max value becomes 39999
RomanInteger res1 = RomanInteger.parse("29359");
System.out.println(res1);
//prints -> 29359 - BBMBCCCLIX
RomanInteger res2 = RomanInteger.parse("BBBACCLX");
System.out.println(res2);
//prints -> 35260 - BBBACCLX
RomanInteger.clearExtensions();
//clears all registred extensions, leaving only the basic symbols
```
# Licence
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details.
