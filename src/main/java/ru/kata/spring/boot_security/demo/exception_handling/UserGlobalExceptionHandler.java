package ru.kata.spring.boot_security.demo.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    // обработчик нашего кастомного exception
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException (
            NoSuchUserException noSuchUserException) {
        UserIncorrectData userIncorrectData = new UserIncorrectData(); // наше искл
        userIncorrectData.setInfo(noSuchUserException.getMessage());  // через сеттер --- сообщение из искл

        return new ResponseEntity<>(userIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException (
            Exception exception) {
        UserIncorrectData userIncorrectData = new UserIncorrectData(); // наше искл
        userIncorrectData.setInfo(exception.getMessage());  // через сеттер --- сообщение из искл

        return new ResponseEntity<>(userIncorrectData, HttpStatus.BAD_REQUEST);
    }
}
