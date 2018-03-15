package com.chaos.converter.util;

/**
 * Created by ChaosFire on 15-Mar-18
 */
public class DataTransferObject {

    private final String roman;
    private final Integer arabic;

    public DataTransferObject(String roman, Integer arabic) {
        this.roman = roman;
        this.arabic = arabic;
    }

    public String getRoman() {
        return this.roman;
    }

    public Integer getArabic() {
        return this.arabic;
    }
}