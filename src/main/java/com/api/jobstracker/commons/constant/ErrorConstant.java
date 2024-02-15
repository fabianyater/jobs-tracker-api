package com.api.jobstracker.commons.constant;

public class ErrorConstant {
    public static final Integer GENERIC_ERROR_CODE = 9009;
    public static final String GENERIC_ERROR_MESSAGE = "Ocurrió un error desconocido";
    public static final Integer BAD_REQUEST_CODE = 9002;
    public static final Integer RECORD_NOT_FOUND_CODE = 9003;
    public static final String RECORD_NOT_FOUND_MESSAGE = "No se encontró el registro";
    public static final int INVALID_CREDENTIAL_USER_CODE = 9000;
    public static final String INVALID_CREDENTIAL_USER_MESSAGE = "Usuario/contraseña inválida";
    public static final int INVALID_TOKEN_CODE = 9001;
    public static final String INVALID_TOKEN_MESSAGE = "Acceso Denegado";
    public static final int ACCESS_DENIED_CODE = 9008;
    public static final String ACCESS_DENIED_MESSAGE = "Acceso Denegado.";
    public static final String PREFIX_DETAIL_MESSAGE = "Detail";
    public static final int DATA_EXIST_CODE = 9007;
    public static final int DATA_NOT_EXIST = 9008;
    public static final String ENTERPRISE_NOT_EXIST = "La empresa no existe";
    public static final String ENTERPRISE_EXIST = "La empresa ya existe con nombre {0}";
    public static final String USER_EXIST = "El usuario ya esta registrado";
    public static final String USER_NOT_FOUND = "El usuario no esta registrado";
    public static final String PROFILE_NOT_FOUND_MESSAGE = "El perfil no existe";
    public static final String PROFILE_REGISTER_MESSAGE = "El Perfil ya se Encuentra Registrado";
    public static final String COMPANY_NOT_FOUND_MESSAGE = "La compañía no existe";
    public static final String STATUS_NOT_FOUND_MESSAGE = "El estado no existe";
    public static final String POINT_SALE_REGISTER_MESSAGE =
            "El Punto de venta ya se encuentra registrado";
    public static final String POINT_SALE_NOT_FOUND_MESSAGE = "El punto de venta no existe";
    public static final String METHOD_PAYMENT_NOT_FOUND_MESSAGE = "El método de pago no existe";
    public static final String METHOD_PAYMENT_REGISTER_MESSAGE =
            "El método de pago ya se encuentra registrado.";
    public static final String CUSTOMER_REGISTER_MESSAGE = "El Cliente ya se encuentra registrado";
    public static final String CUSTOMER_NOT_EXIST = "El cliente no existe";
    public static final String SUPPLIER_REGISTER_MESSAGE =
            "El RFC del proveedor ya se encuentra registrado";
    public static final String DEBT_ACTIVE_MESSAGE = "El Cliente ya tienen una deuda activa";
    public static final String DEBT_INVOICE_NOT_REGISTER_MESSAGE = "Folio de deuda no encontrado";
    public static final String POINT_SALE_STARTED = "La caja ya inicio operaciones";
    public static final String POINT_SALE_CLOSED = "La caja ya cerró operaciones";
    public static final String POINT_SALE_CANNOT_STARTED = "La caja aún no ha iniciado operaciones";
    public static final String WITHDRAWAL_NOT_EXIST = "El folio del retiro no se encuentra";
    public static final String PRODUCT_TYPE_EXIST = "El tipo de producto ya fue registrado";
    public static final String PRODUCT_TYPE_NOT_EXIST = "El tipo de producto no esta registrado";
    public static final String PRODUCT_EXIST = "El producto ya fue registrado";
    public static final String PRODUCT_NOT_EXIST = "El producto no existe";
    public static final String PRODUCT_STOCK_EXIST = "El producto ya fue registrado en el inventario";
    public static final String PRODUCT_STOCK_NOT_EXIST =
            "El producto no ha sido registrado en el inventario";
    public static final String PRODUCT_OUT_STOCK = "El producto esta agotado";

    private ErrorConstant() {
    }
}
