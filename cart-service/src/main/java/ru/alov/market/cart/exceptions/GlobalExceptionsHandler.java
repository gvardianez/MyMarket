package ru.alov.market.cart.exceptions;

import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.alov.market.api.exception.CartServiceAppError;
import ru.alov.market.api.exception.ResourceNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {
    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceErrors.CART_IS_BROKEN.name(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchCartIsBrokenException(CartIsBrokenException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceErrors.CART_IS_BROKEN.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchConnectTimeoutException(ConnectTimeoutException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceErrors.CART_SERVICE_CONNECT_TIMEOUT_EXCEEDED.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
