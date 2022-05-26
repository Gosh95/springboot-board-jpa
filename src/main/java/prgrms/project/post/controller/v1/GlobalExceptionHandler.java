package prgrms.project.post.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prgrms.project.post.controller.response.ErrorCode;
import prgrms.project.post.controller.response.ErrorResponse;
import prgrms.project.post.util.exception.EntityNotFoundException;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static prgrms.project.post.controller.response.ErrorCode.*;

@Slf4j
@RestControllerAdvice(basePackages = "prgrms.project.post.controller")
public class GlobalExceptionHandler {

    private String cause;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var fieldError = ex.getFieldError();
        cause = format("field: {0}, input: {1}", requireNonNull(fieldError).getField(), fieldError.getRejectedValue());
        log.warn("Got MethodArgumentNotValidException: {}", cause, ex);

        return responseOf(INVALID_INPUT_VALUE, cause);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        var supported = ex.getSupportedMediaTypes().stream().map(MimeType::toString).collect(joining());
        cause = format("{0} is not supported, supported: {1}", ex.getContentType(), supported);
        log.warn("Got HttpMediaTypeNotSupportedException: {}", cause, ex);

        return responseOf(NOT_SUPPORTED_MEDIA_TYPE, cause);
    }

    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleTypeMismatch(TypeMismatchException ex) {
        cause = format("{0} is different from the requested type({1})", ex.getValue(), requireNonNull(ex.getRequiredType()).getSimpleName());
        log.warn("Got TypeMismatchException: {}", cause, ex);

        return responseOf(TYPE_MISMATCH, cause);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleExceptionInternal(Exception ex) {
        log.error("Got unknown server error: {}", ex.getMessage(), ex);

        return responseOf(SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("Got EntityNotFoundException: {}", ex.getMessage(), ex);

        return responseOf(ENTITY_NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> responseOf(ErrorCode errorCode, String cause) {
        return ResponseEntity.status(errorCode.getStatusCode()).body(ErrorResponse.of(errorCode, cause));
    }
}
