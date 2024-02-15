package com.api.jobstracker.commons.constant;

public abstract class ApiConstant {
    public static final String COMMA = ",";
    public static final String HEADER_UUID = "uuid";
    public static final String START_REQUEST = "Inicia Llamado [{}]";
    public static final String TIME_REQ_ATTRIBUTE = "req.time";
    public static final String UUID_MDC_LABEL = "mdc.uuid";
    public static final String TIME_ELAPSED_MESSAGE =
            "Time elapsed for request round trip [{}]: {} ms";
    public static final String PROPERTY_PREFIX = "api";
    public static final String SWAGGER_PROPERTIES = "swagger";
    public static final String WHITE_SPACE = " ";
    public static final String USER_SYSTEM = "System";
    public static final String PROPERTIES_MAIL = "api.mail";
    public static final String MAIL_URL_PROPERTY = "url";
    public static final String MAIL_PHONE_PROPERTY = "phone";
    public static final String MAIL_USER_PROPERTY = "user";
    public static final String MAIL_ENTERPRISE_NAME_PROPERTY = "enterprise";
    public static final String MAIL_FIRSTNAME_PROPERTY = "firstname";
    public static final String MAIL_LASTNAME_PROPERTY = "lastname";

    private ApiConstant() {
    }
}
