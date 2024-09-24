package guru.springframework.spring6restmvc.controller.error;

import org.springframework.http.ResponseEntity;

//@ControllerAdvice
public class ExceptionController {

  //  @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}
