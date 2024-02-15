package com.api.jobstracker.commons.constant;

public class FormatConstant {
    public static final String BOGOTA_ZONE_ID = "America/Bogota";
    public static final String ERROR_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEBT_DATE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String ONLY_LETTERS_PATTERN = "^[A-Za-z\\s]+$";
    public static final String ONLY_NUMBERS_PATTERN = "^[0-9]+$";
    public static final String ONLY_NUMBERS_AND_LETTERS_PATTERN = "^[A-Za-z0-9\\s]+$";
    public static final String PAW_PATTERN = "^[A-Za-z0-9@#$%-&+=]+$";
    public static final String USERNAME_PATTERN = "^[A-Za-z0-9\\s\\.\\_\\-]+$";
    public static final String URL_PATTERN = "[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?";

    private FormatConstant() {
    }
}
