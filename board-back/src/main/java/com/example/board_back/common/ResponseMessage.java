package com.example.board_back.common;

public interface ResponseMessage {
    

    //HTTP Status Code 200
    String SUCCESS = "Success";

    //HTTP Status Code 400
    String VALIDATION_FAILED = "Validation failed";
    String DUPLICATE_EMAIL = "Duplicate email";
    String DUPLICATE_NICKNAME = "Duplicate nickname";
    String DUPLICATE_TEL_NUMBER = "Duplicate telephone number";
    String NOT_EXIST_USER = "This user does not exist";
    String NOT_EXIST_BOARD = "This board does Not exist";
     String NOT_EXIST_COMMENT = "This comment does not exist";

    //HTTP Status Code 401
    String SIGN_IN_FAIL = "Login information mismatch";
    String AUTHORIZATION_FAIL = "Authorization failed";

    //HTTP Status Code 403
    String NO_PERMISSION = "Do not have permission";

    //HTTP Status Code 500
    String DATABASE_ERROR = "Database error";
    
}


