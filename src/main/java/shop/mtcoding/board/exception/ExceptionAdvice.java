package shop.mtcoding.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception400.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ProblemDetail onException400(Exception400 exception400) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
                exception400.getMessage()
        );
    }

}
