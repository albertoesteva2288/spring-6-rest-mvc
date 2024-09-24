package guru.springframework.spring6restmvc.controller.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

    @ControllerAdvice
    public class CustomErrorController {

        @ExceptionHandler(TransactionSystemException.class)
        public ResponseEntity<?> handleJPAViolation(TransactionSystemException exception) {
            ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
            Throwable rootCause = exception.getRootCause();
            if (rootCause instanceof ConstraintViolationException) {
                ConstraintViolationException ve = (ConstraintViolationException) exception.getCause().getCause();

                List<Map<String, String>> errors = ve.getConstraintViolations().stream()
                        .map(constraintViolation -> {
                            Map<String, String> errorMap = new HashMap<>();
                            errorMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                            return errorMap;
                        }).toList();
                return responseEntity.body(errors);
            }

            return responseEntity.build();
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleBindErrors(MethodArgumentNotValidException exception){
            List<Map<String, String>> errorList = exception.getFieldErrors().stream()
                    .map(fieldError -> {
                        Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();
        return ResponseEntity.badRequest().body(errorList);
    }
}
