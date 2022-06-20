package ru.yandex.goods.exceptions;

import org.springframework.http.HttpStatus;
import ru.yandex.goods.exceptions.models.Response;

public class NotFoundException extends RuntimeException{

    private static final String MESSAGE = "Item not found";

    public NotFoundException() {
        super(MESSAGE);
    }

    public Response toResponse() {
        return new Response(HttpStatus.NOT_FOUND.value(), MESSAGE);
    }
}
