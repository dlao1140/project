enum ResponseCode{

    //HTTP Status Code 200
    SUCCESS = "SU",

    //HTTP Status Code 400
    VALIDATION_FAILED = "VF",
    DUPLICATE_EMAIL = "DE",
    DUPLICATE_NICKNAME = "DN",
    DUPLICATE_TEL_NUMBER = "DT",
    NOT_EXIST_USER = "NU",
    NOT_EXIST_BOARD = "NB",

    //HTTP Status Code 401
    SIGN_IN_FAIL = "SF",
    AUTHRIZATION_FAIL = "AF",

    //HTTP Status Code 403
    NO_PERMISSION = "NP",

    //HTTP Status Code 500
    DATABASE_ERROR = "DBE",
}

export default ResponseCode;