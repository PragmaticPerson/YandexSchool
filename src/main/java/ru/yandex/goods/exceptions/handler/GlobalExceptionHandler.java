package ru.yandex.goods.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.yandex.goods.exceptions.NotFoundException;
import ru.yandex.goods.exceptions.ValidationFailedException;
import ru.yandex.goods.exceptions.models.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> notFoundHandler(NotFoundException e) {
        return new ResponseEntity(e.toResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<Response> validationFailedHandler(ValidationFailedException e) {
        return new ResponseEntity(e.toResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> methodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException e) {
        return validationFailedHandler(new ValidationFailedException());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response> missingServletRequestParameterHandler(MissingServletRequestParameterException e) {
        return validationFailedHandler(new ValidationFailedException());
    }
}
