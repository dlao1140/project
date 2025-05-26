package com.example.board_back.common;

public interface ResponseCode {
    //HTTP Status Code 200
    String SUCCESS = "SU";

    //HTTP Status Code 400
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_NICKNAME = "DN";
    String DUPLICATE_TEL_NUMBER = "DT";
    String NOT_EXIST_USER = "NU";
    String NOT_EXIST_BOARD = "NB";
    String NOT_EXIST_COMMENT = "NC"; 

    //HTTP Status Code 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";

    //HTTP Status Code 403
    String NO_PERMISSION = "NP";

    //HTTP Status Code 500
    String DATABASE_ERROR = "DBE";
    
}
