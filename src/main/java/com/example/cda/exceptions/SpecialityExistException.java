package com.example.cda.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Speciality exists")

public class SpecialityExistException extends RuntimeException {
}
