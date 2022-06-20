package ru.yandex.goods.exceptions;

import org.springframework.http.HttpStatus;
import ru.yandex.goods.exceptions.models.Response;

public class ValidationFailedException extends RuntimeException {

    private static final String MESSAGE = "Validation Failed";

    public ValidationFailedException() {
        super(MESSAGE);
    }

    public Response toResponse() {
        return new Response(HttpStatus.NOT_FOUND.value(), MESSAGE);
    }
}
