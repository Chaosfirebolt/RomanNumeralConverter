package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.RomanInteger;

/**
 * Dto class, holds roman, and it's corresponding arabic numeral, for the
 * purpose of transferring parsed data to private constructors in {@link RomanInteger}.
 *
 * @param roman  Roman representation of a number.
 * @param arabic Arabic representation for same number.
 */
public record ParsedData(String roman, int arabic) {
}