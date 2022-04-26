package com.kmvpsolutions.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author kalil.peixoto
 * @date 4/26/22 5:55 PM
 * @email kalilmvp@gmail.com
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String msg) {
        super(msg);
    }
}
